package com.example.myapplication2;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

import androidx.annotation.Nullable;


public class Guidor {
    //DBHelper DBHelper = new DBHelper(getApplicationContext(), DB_NAME, null, 1);
    private final boolean debug = true;
    private com.example.myapplication2.DBHelper DBHelper = null;
    private JSONArray how_option = new JSONArray(), taste = new JSONArray(),
            visual = new JSONArray(), smell = new JSONArray(), tactile = new JSONArray(), hearing = new JSONArray();
    private ArrayList<String> howSeq = new ArrayList<>();
    private String mood_option, tag_option, what_option, who_option, when_option, why_option, where_option, diary;
    // curProp : what、when、who ... etc
    // curDiaryContentNo : 1、2、3 ... etc，即DB裡的Sentence-Pattern的編號
    //private String curProp = "", curDiaryContentNo = "";
    // propSeq : 5W1H的排序組合，總個數可能會少於 6
    // diaryContentNoSeq : 由DB裡的Sentence-Pattern的編號組成的排序組合，且每一個prop中只會有一個子元素被選取( 0 ~ 6 個編號被選取)
    private ArrayList<String> propSeq = new ArrayList<>(), diaryContentNoSeq = new ArrayList<>();
    public Guidor(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version){
        init();
        this.DBHelper = new DBHelper(context, name, factory, version);
        //openDB();
    }
    //public abstract void openDB();
    private void init(){
        this.mood_option = "";
        this.tag_option = "";
        this.what_option = "";
        this.who_option = "";
        this.when_option = "";
        this.why_option = "";
        this.where_option = "";
        try{
            this.taste.put(0, "味覺");
            this.visual.put(0, "視覺");
            this.smell.put(0, "嗅覺");
            this.tactile.put(0, "觸覺");
            this.hearing.put(0, "聽覺");
            this.how_option.put(0, this.taste);
            this.how_option.put(1, this.visual);
            this.how_option.put(2, this.smell);
            this.how_option.put(3, this.tactile);
            this.how_option.put(4, this.hearing);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        this.diary = "";
    }
    private boolean seqAuth(String prop){
        for (int i=0; i<propSeq.indexOf(prop); i++){
            if (propSeq.get(i).equals("")){return false;}
        }
        return true;
    }
    public void setMood(String mood){
        this.mood_option = mood;
    }
    public void setTag(String tag){
        this.tag_option = tag;
    }
    public void setWhat(String what){
        if (propSeq.indexOf("what") == -1)
            propSeq.add("what");
        this.what_option = what;
    }
    public void setWho(String who){
        if (propSeq.indexOf("who") == -1)
            propSeq.add("who");
        this.who_option = who;
    }
    public void setWhen(String when){
        if (propSeq.indexOf("when") == -1)
            propSeq.add("when");
        this.when_option = when;
    }
    public void setWhy(String why){
        if (propSeq.indexOf("why") == -1)
            propSeq.add("why");
        this.why_option = why;
    }
    public void setWhere(String where){
        if (propSeq.indexOf("where") == -1)
            propSeq.add("where");
        this.where_option = where;
    }
    public void setHow(String sense, String how){
        if (propSeq.indexOf("how") == -1)
            propSeq.add("how");
        try{
            switch (sense){
                case "味覺":
                    if (howSeq.indexOf("味覺") == -1)
                        howSeq.add("味覺");
                    taste.put(taste.length(), how);
                    break;
                case "視覺":
                    if (howSeq.indexOf("視覺") == -1)
                        howSeq.add("視覺");
                    visual.put(visual.length(), how);
                    break;
                case "嗅覺":
                    if (howSeq.indexOf("嗅覺") == -1)
                        howSeq.add("嗅覺");
                    smell.put(smell.length(), how);
                    break;
                case "觸覺":
                    if (howSeq.indexOf("觸覺") == -1)
                        howSeq.add("觸覺");
                    tactile.put(tactile.length(), how);
                    break;
                case "聽覺":
                    if (howSeq.indexOf("聽覺") == -1)
                        howSeq.add("聽覺");
                    hearing.put(hearing.length(), how);
                    break;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //this.how_option = how;
    }
    public void preQuestion(){
        if (propSeq.isEmpty())
            return;
        propSeq.remove(propSeq.size()-1);
        while(propSeq.size() < diaryContentNoSeq.size()){
            diaryContentNoSeq.remove(diaryContentNoSeq.size()-1);
        }
    }
    public String getDiary(){
        if (propSeq.size() == diaryContentNoSeq.size())
            return this.diary;
        if (propSeq.size() > diaryContentNoSeq.size())
            createDiary();
        if (propSeq.size() < diaryContentNoSeq.size())
            recreateDiary();

        return this.diary;
    }
    public void clearDiary(){
        diaryContentNoSeq.clear();
        this.diary = "";
    }
    private void recreateDiary(){

    }
    private void createDiary(){
        ArrayList<String> addDiaryContentNoSeq = new ArrayList<>();
        while(propSeq.size() > diaryContentNoSeq.size()){
            String index = getPatternIndex();
            diaryContentNoSeq.add(index);
            addDiaryContentNoSeq.add(index);
            if (debug)
                Log.d("prop diaryContentNoSeq", propSeq.size() + String.valueOf(diaryContentNoSeq.size()));
        }
        for (String s : diaryContentNoSeq){
            if (debug)
                Log.d("diaryContentNoSeq", s);
        }
        SQLiteDatabase db = DBHelper.getWritableDatabase();
        StringBuilder sb = new StringBuilder();
        Cursor cursor = null;
        if (this.diary.equals("")){
            String[] mood_pattern = {"我今天", "今天", "今天我"};
            String[] mood_pattern_1 = {};
            switch (mood_option){
                case "心情1":
                    mood_pattern_1 = new String[]{"心情很棒", "心情很讚", "心情很好", "心情特別好", "心情十分愉悅", "心情十分雀躍", "心情很愉快"};
                    break;
                case "心情2":
                    break;
                case "心情3":
                    break;
                case "心情4":
                    break;
                case "心情5":
                    break;
            }
            this.diary = mood_pattern[(int)(Math.random()*mood_pattern.length)] + mood_pattern_1[(int)(Math.random()*mood_pattern_1.length)];
        }
        sb.append(this.diary);
        for (int i=propSeq.size()-addDiaryContentNoSeq.size(); i<propSeq.size(); i++){
            String index = "", pattern = "", replace = "", punctuation = "";
            ArrayList<String> punctuations = new ArrayList<>();

            if (propSeq.get(i).equals("how")){
                String[] how_pattern = diaryContentNoSeq.get(i).split("_");

                for (int k=0; k<how_pattern.length; k++){
                    // append punctuation
                    if (i != 0){
                        String sentencePatternNo = "";
                        if (k!=0) {
                            if (debug)
                                Log.d("how_pattern[k - 1]", how_pattern[k - 1]);
                            sentencePatternNo = how_pattern[k - 1];
                        }
                        else
                            sentencePatternNo = diaryContentNoSeq.get(i-1);
                        if (debug)
                            Log.d("diaryContentNoSeq", diaryContentNoSeq.get(i-1));
                        if (debug)
                            Log.d("sentencePatternNo", sentencePatternNo);
                        cursor = db.rawQuery("SELECT punctuationMark\n" +
                                "FROM pattern_link\n" +
                                "WHERE sentencePatternNo = " + sentencePatternNo + " AND nextPattern = " + how_pattern[k] + "", null);
                        cursor.moveToFirst();
                        if (debug)
                            Log.d("NiCe", "SELECT punctuationMark\n" +
                                "FROM pattern_link\n" +
                                "WHERE sentencePatternNo = " + sentencePatternNo + " AND nextPattern = " + how_pattern[k] + "");
                        do{
                            punctuations.add(cursor.getString(0));
                        }while(cursor.moveToNext());
                        punctuation = choosePunctuation(punctuations.get((int)(Math.random()*punctuations.size())));
                        sb.append(punctuation);
                    }
                    // get pattern
                    index = how_pattern[k];
                    cursor = db.rawQuery("SELECT pattern\n" +
                            "FROM sentence_pattern\n" +
                            "WHERE sentencePatternNo = '" + index + "'", null);
                    cursor.moveToFirst();
                    pattern = cursor.getString(0);

                    if (pattern.contains("_multiOption_")){
                        StringBuilder replace_how = new StringBuilder();
                        switch (howSeq.get(0)){
                            case "味覺":
                                for (int p=1; p<taste.length(); p++){
                                    try {
                                        replace_how.append(taste.get(p));
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                    if (p != taste.length()-1){
                                        replace_how.append("、");
                                    }
                                }
                                break;
                            case "視覺":
                                for (int p=1; p<visual.length(); p++){
                                    try {
                                        replace_how.append(visual.get(p));
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                    if (p != visual.length()-1){
                                        replace_how.append("、");
                                    }
                                }
                                break;
                            case "嗅覺":
                                for (int p=1; p<smell.length(); p++){
                                    try {
                                        replace_how.append(smell.get(p));
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                    if (p != smell.length()-1){
                                        replace_how.append("、");
                                    }
                                }
                                break;
                            case "觸覺":
                                for (int p=1; p<tactile.length(); p++){
                                    try {
                                        replace_how.append(tactile.get(p));
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                    if (p != tactile.length()-1){
                                        replace_how.append("、");
                                    }
                                }
                                break;
                            case "聽覺":
                                for (int p=1; p<hearing.length(); p++){
                                    try {
                                        replace_how.append(hearing.get(p));
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                    if (p != hearing.length()-1){
                                        replace_how.append("、");
                                    }
                                }
                                break;
                            default:
                                replace_how.append("錯誤");
                        }
                        howSeq.remove(0);
                        sb.append(pattern.replace("_multiOption_", replace_how.toString()));
                    }else{

                    }
                }
            }else{
                index = diaryContentNoSeq.get(i);
                // append punctuation
                if (i != 0){
                    cursor = db.rawQuery("SELECT punctuationMark\n" +
                            "FROM pattern_link\n" +
                            "WHERE sentencePatternNo = " + diaryContentNoSeq.get(i-1) + " AND nextPattern = " + index + "", null);
                    cursor.moveToFirst();
                    if (debug)
                        Log.d("NiCe", "SELECT punctuationMark\n" +
                            "FROM pattern_link\n" +
                            "WHERE sentencePatternNo = " + diaryContentNoSeq.get(i-1) + " AND nextPattern = " + index + "");
                    do{
                        punctuations.add(cursor.getString(0));
                    }while(cursor.moveToNext());
                    punctuation = choosePunctuation(punctuations.get((int)(Math.random()*punctuations.size())));
                    sb.append(punctuation);
                }
                // get pattern
                cursor = db.rawQuery("SELECT pattern\n" +
                        "FROM sentence_pattern\n" +
                        "WHERE sentencePatternNo = '" + index + "'", null);
                cursor.moveToFirst();
                if (debug)
                    Log.d("NiCe", "SELECT pattern\n" +
                        "FROM sentence_pattern\n" +
                        "WHERE sentencePatternNo = '" + index + "'");
                pattern = cursor.getString(0);

                switch (propSeq.get(i)){
                    case "what":
                        replace = what_option;
                        break;
                    case "who":
                        replace = who_option;
                        break;
                    case "when":
                        replace = when_option;
                        break;
                    case "why":
                        replace = why_option;
                        break;
                    case "where":
                        replace = where_option;
                        break;
                }
                sb.append(pattern.replace("_option_", replace));
            }
        }
        sb.append("。");
        this.diary = sb.toString();
        assert cursor != null;
        cursor.close();
    }
    private String getPatternIndex(){
        String ret = "", option = "", index = "-1";
        ArrayList<String> indexes = new ArrayList<>();
        SQLiteDatabase db = DBHelper.getWritableDatabase();
        Cursor cursor = null;
        String prop = propSeq.get(diaryContentNoSeq.size());

        switch (prop){
            case "what":
                option = what_option;
                break;
            case "why":
                option = why_option;
                break;
            case "when":
                option = when_option;
                break;
            case "who":
                option = who_option;
                break;
            case "where":
                option = where_option;
                break;
        }
        if (prop.equals("how")){
            // 5 sense
            for (int i=0; i<how_option.length(); i++) {
                StringBuilder like = new StringBuilder();
                String optionClass = "";
                if (debug)
                    Log.d("FOR迴圈", String.valueOf(i));

                try {
                    for (int k=1; k<how_option.getJSONArray(i).length(); k++){
                        if (k==1){
                            like.append("%");
                        }
                        like.append("_option_");
                        like.append(k);
                        like.append("%");
                    }
                    optionClass = how_option.getJSONArray(i).getString(0);
                    if (debug)
                        Log.d("how_option", optionClass);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                switch (optionClass){
                    case "味覺":
                        if (taste.length() == 1)
                            continue;
                        break;
                    case "視覺":
                        if (visual.length() == 1)
                            continue;
                        break;
                    case "嗅覺":
                        if (smell.length() == 1)
                            continue;
                        break;
                    case "觸覺":
                        if (tactile.length() == 1)
                            continue;
                        break;
                    case "聽覺":
                        if (hearing.length() == 1)
                            continue;
                        break;
                }
                // get pattern index
                cursor = db.rawQuery("SELECT sentencePatternNo\n" +
                        "FROM sentence_pattern\n" +
                        "WHERE sentencePatternNo IN (\n" +
                        "\tSELECT sentencePatternNo\n" +
                        "\tFROM pattern_index\n" +
                        "\tWHERE optionNo = (\n" +
                        "\t\tSELECT optionNo\n" +
                        "\t\tFROM `option`\t\n" +
                        "\t\tWHERE questionClassNo = (\n" +
                        "\t\t\tSELECT questionClassNo\n" +
                        "\t\t\tFROM questionclass\n" +
                        "\t\t\tWHERE questionNo = (\n" +
                        "\t\t\t\tSELECT questionNo\n" +
                        "\t\t\t\tFROM question\n" +
                        "\t\t\t\tWHERE mood = '" + mood_option + "' AND tag = '" + tag_option + "'\n" +
                        "\t\t\t)\n" +
                        "\t\t\tAND questionClass = 'how'\n" +
                        "\t\t)\n" +
                        "\t\tAND optionClass = '" + optionClass + "'\n" +
                        "\t)\n" +
                        ")\n" +
                        "AND pattern LIKE '%_multiOption_%' OR pattern LIKE '" + like.toString() + "'", null);
                if (debug)
                    Log.d("NiCe", "SELECT sentencePatternNo\n" +
                        "FROM sentence_pattern\n" +
                        "WHERE sentencePatternNo IN (\n" +
                        "\tSELECT sentencePatternNo\n" +
                        "\tFROM pattern_index\n" +
                        "\tWHERE optionNo = (\n" +
                        "\t\tSELECT optionNo\n" +
                        "\t\tFROM `option`\t\n" +
                        "\t\tWHERE questionClassNo = (\n" +
                        "\t\t\tSELECT questionClassNo\n" +
                        "\t\t\tFROM questionclass\n" +
                        "\t\t\tWHERE questionNo = (\n" +
                        "\t\t\t\tSELECT questionNo\n" +
                        "\t\t\t\tFROM question\n" +
                        "\t\t\t\tWHERE mood = '" + mood_option + "' AND tag = '" + tag_option + "'\n" +
                        "\t\t\t)\n" +
                        "\t\t\tAND questionClass = 'how'\n" +
                        "\t\t)\n" +
                        "\t\tAND optionClass = '" + optionClass + "'\n" +
                        "\t)\n" +
                        ")\n" +
                        "AND pattern LIKE '%_multiOption_%' OR pattern LIKE '" + like.toString() + "'");
                cursor.moveToFirst();
                do {
                    indexes.add(cursor.getString(0));
                }while(cursor.moveToNext());
                int randomIndex = (int)(Math.random()*indexes.size());
                index = indexes.get(randomIndex);

//                cursor = db.rawQuery("SELECT pattern\n" +
//                        "FROM sentence_pattern\n" +
//                        "WHERE sentencePatternNo = '" + index + "'", null);
//                cursor.moveToFirst();
                ret += index;
                if (i != how_option.length()-1)
                    ret += "_";
                indexes.clear();
            }

        }else {
            int randomIndex;
            // get pattern index
            cursor = db.rawQuery("SELECT sentencePatternNo\n" +
                    "FROM `pattern_index`\n" +
                    "WHERE optionNo = (\n" +
                    "\tSELECT optionNo\n" +
                    "\tFROM `option`\n" +
                    "\tWHERE questionClassNo = (\n" +
                    "\t\tSELECT questionClassNo\n" +
                    "\t\tFROM questionclass\n" +
                    "\t\tWHERE questionNo = (\n" +
                    "\t\t\tSELECT questionNo\n" +
                    "\t\t\tFROM question\n" +
                    "\t\t\tWHERE mood = '" + mood_option + "' AND tag = '" + tag_option + "'\n" +
                    "\t\t)\n" +
                    "\t\tAND questionClass = '" + prop + "'\n" +
                    "\t)\n" +
                    "\tAND optionClass = '" + option + "'\n" +
                    ")", null);
            cursor.moveToFirst();
            if (debug)
                Log.d("NiCe", "SELECT sentencePatternNo\n" +
                    "FROM `pattern_index`\n" +
                    "WHERE optionNo = (\n" +
                    "\tSELECT optionNo\n" +
                    "\tFROM `option`\n" +
                    "\tWHERE questionClassNo = (\n" +
                    "\t\tSELECT questionClassNo\n" +
                    "\t\tFROM questionclass\n" +
                    "\t\tWHERE questionNo = (\n" +
                    "\t\t\tSELECT questionNo\n" +
                    "\t\t\tFROM question\n" +
                    "\t\t\tWHERE mood = '" + mood_option + "' AND tag = '" + tag_option + "'\n" +
                    "\t\t)\n" +
                    "\t\tAND questionClass = '" + prop + "'\n" +
                    "\t)\n" +
                    "\tAND optionClass = '" + option + "'\n" +
                    ")");
            do {
                indexes.add(cursor.getString(0));
                if (debug)
                    Log.d("indexes.add", cursor.getString(0));
            }while(cursor.moveToNext());

            if (diaryContentNoSeq.size() >= 1){
                if (!propSeq.get(diaryContentNoSeq.size()-1).equals("how")){
                    StringBuilder existProp = new StringBuilder(), selectedIndexes = new StringBuilder();
                    for (int i=0; i<diaryContentNoSeq.size(); i++){
                        existProp.append("'").append(propSeq.get(i)).append("'").append(", ");
                    }
                    for (int i=0; i<indexes.size(); i++){
                        selectedIndexes.append("'").append(indexes.get(i)).append("'").append(", ");
                    }
                    existProp.deleteCharAt(existProp.length()-2);
                    selectedIndexes.deleteCharAt(selectedIndexes.length()-2);
                    indexes.clear();

                    cursor = db.rawQuery("SELECT DISTINCT sentencePatternNo\n" +
                            "FROM pattern_link\n" +
                            "WHERE nextPattern in (\n" +
                            "\tSELECT nextPattern\n" +
                            "\tFROM pattern_link\n" +
                            "\tWHERE sentencePatternNo in (\n" +
                            "\t\tSELECT sentencePatternNo\n" +
                            "\t\tFROM sentence_pattern\n" +
                            "\t\tWHERE questionClassNo in (\n" +
                            "\t\t\tSELECT questionClassNo\n" +
                            "\t\t\tFROM questionclass\n" +
                            "\t\t\tWHERE questionNo = (\n" +
                            "\t\t\t\tSELECT questionNo\n" +
                            "\t\t\t\tFROM question\n" +
                            "\t\t\t\tWHERE mood = '" + mood_option + "' AND tag = '" + tag_option + "'\n" +
                            "\t\t\t)\n" +
                            "\t\t\tAND questionclass NOT IN (\n" +
                            "\t\t\t\t" + existProp.toString() + "\n" +
                            "\t\t\t)\n" +
                            "\t\t)\n" +
                            "\t)\n" +
                            ")\n" +
                            "AND sentencePatternNo IN (\n" +
                            "\t" + selectedIndexes.toString() + "\n" +
                            ")\n",null);
                    if (debug)
                        Log.d("NiCe", "SELECT DISTINCT sentencePatternNo\n" +
                            "FROM pattern_link\n" +
                            "WHERE nextPattern in (\n" +
                            "\tSELECT nextPattern\n" +
                            "\tFROM pattern_link\n" +
                            "\tWHERE sentencePatternNo in (\n" +
                            "\t\tSELECT sentencePatternNo\n" +
                            "\t\tFROM sentence_pattern\n" +
                            "\t\tWHERE questionClassNo in (\n" +
                            "\t\t\tSELECT questionClassNo\n" +
                            "\t\t\tFROM questionclass\n" +
                            "\t\t\tWHERE questionNo = (\n" +
                            "\t\t\t\tSELECT questionNo\n" +
                            "\t\t\t\tFROM question\n" +
                            "\t\t\t\tWHERE mood = '" + mood_option + "' AND tag = '" + tag_option + "'\n" +
                            "\t\t\t)\n" +
                            "\t\t\tAND questionclass NOT IN (\n" +
                            "\t\t\t\t" + existProp.toString() + "\n" +
                            "\t\t\t)\n" +
                            "\t\t)\n" +
                            "\t)\n" +
                            ")\n" +
                            "AND sentencePatternNo IN (\n" +
                            "\t" + selectedIndexes.toString() + "\n" +
                            ")\n");
                    cursor.moveToFirst();
                    do{
                        indexes.add(cursor.getString(0));
                        if (debug)
                            Log.d("indexes.add", cursor.getString(0));
                    }while(cursor.moveToNext());
                }
            }
            randomIndex = (int)(Math.random()*indexes.size());
            index = indexes.get(randomIndex);
//            cursor = db.rawQuery("SELECT pattern\n" +
//                    "FROM sentence_pattern\n" +
//                    "WHERE sentencePatternNo = '" + index + "'", null);
//            cursor.moveToFirst();
            ret = index;
        }
        assert cursor != null;
        cursor.close();
        return ret;
    }
    private String choosePunctuation(String s){
        switch (s){
            case "1":
                return "，";
            default:
                return "";
        }
    }

}
