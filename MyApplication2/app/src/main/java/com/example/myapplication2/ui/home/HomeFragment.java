package com.example.myapplication2.ui.home;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.myapplication2.Diary.DiaryActivity;
import com.example.myapplication2.DiaryValue;
import com.example.myapplication2.HttpURLConnection_AsyncTask;
import com.example.myapplication2.Login.LoginActivity;
import com.example.myapplication2.MainActivity;
import com.example.myapplication2.R;
import com.example.myapplication2.sqlReturn;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.sql.Ref;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Locale;
import java.util.Map;

public class HomeFragment extends Fragment {

    private ConstraintLayout mLayout;
    private Button mBtnChange;
    private Button mGoToHandWrite;
    private Button mGoToDiary;
    private Button mGoToOCR;
    private boolean changeBtn = false;
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private LinkedList<HashMap<String,String>> data;
    private HomeFragment.MyAdapter myAdapter;
    private Button btnWriteTodayDiary;
    public static int homeTag;
    private Button searchBtnMood;
    private Button searchBtnTag;
    private Button searchBtn1;
    private Button searchBtn2;
    private Button searchBtn3;
    private Button searchBtn4;
    private Button searchBtn5;
    private Button buttonTest;
    private Button searchBtnHandWrite;
    private ProgressBar progressBarHome;
    private SwipeRefreshLayout RefreshLayoutHome;


    public View onCreateView(@NonNull final LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home, container, false);

        MainActivity mainActivity = (MainActivity) getActivity();
        mGoToHandWrite = mainActivity.findViewById(R.id.goToHandwritebutton);
        mGoToDiary = mainActivity.findViewById(R.id.goToDiarybutton);
        mGoToOCR = mainActivity.findViewById(R.id.goToOCRbutton);
        mLayout = mainActivity.findViewById(R.id.testConstraint);

        mLayout.setBackgroundColor(0xFFFFFFFF);
        mLayout.setVisibility(View.INVISIBLE);
        if(mGoToHandWrite.isEnabled()){
            mGoToHandWrite.setEnabled(false);
            mGoToHandWrite.setVisibility(View.INVISIBLE);
        }
        if(mGoToDiary.isEnabled()){
            mGoToDiary.setEnabled(false);
            mGoToDiary.setVisibility(View.INVISIBLE);
        }
        if(mGoToOCR.isEnabled()){
            mGoToOCR.setEnabled(false);
            mGoToOCR.setVisibility(View.INVISIBLE);
        }

        btnWriteTodayDiary = root.findViewById(R.id.btnWriteTodayDiary);
        btnWriteTodayDiary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeFragment.super.getActivity(), DiaryActivity.class);
                startActivity(intent);
            }
        });
        mBtnChange = root.findViewById(R.id.btnChange);
        mBtnChange.setOnClickListener(btnChangeColorOnClick);

        // adapter
        mRecyclerView = root.findViewById(R.id.HistoryRecyclerview);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(HomeFragment.super.getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        RefreshLayoutHome = root.findViewById(R.id.RefreshLayoutHome);
        RefreshLayoutHome.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (buttonTest.getText().equals("晴天") || buttonTest.getText().equals("時晴") || buttonTest.getText().equals("多雲") || buttonTest.getText().equals("陣雨") || buttonTest.getText().equals("雷雨")) {
                    searchByMood();
                } else if (buttonTest.getText().equals("美食")) {
                    searchByTag();
                } else if(buttonTest.getText().equals("手寫日記")){
                    SearchHandWrite();
                } else{
                    history();
                }
            }
        });

        doData();

        myAdapter = new MyAdapter();
        mRecyclerView.setAdapter(myAdapter);

        // 搜尋切換
        searchBtnMood = root.findViewById(R.id.searchBtnMood);
        searchBtnTag = root.findViewById(R.id.searchBtnTag);
        searchBtnHandWrite = root.findViewById(R.id.searchBtnHandWrite);
        searchBtn1 = root.findViewById(R.id.searchBtn1);
        searchBtn2 = root.findViewById(R.id.searchBtn2);
        searchBtn3 = root.findViewById(R.id.searchBtn3);
        searchBtn4 = root.findViewById(R.id.searchBtn4);
        searchBtn5 = root.findViewById(R.id.searchBtn5);
        buttonTest = root.findViewById(R.id.buttonTest);
        progressBarHome = root.findViewById(R.id.progressBarHome);

        searchBtnTag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchBtn1.setText("晴天");
                searchBtn2.setText("時晴");
                searchBtn3.setText("多雲");
                searchBtn4.setText("陣雨");
                searchBtn5.setText("雷雨");
            }
        });
        searchBtnMood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchBtn1.setText("美食");
                searchBtn2.setText("購物");
                searchBtn3.setText("感情");
                searchBtn4.setText("旅遊");
                searchBtn5.setText("娛樂");
            }
        });
        searchBtnHandWrite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonTest.setText("手寫日記");
                progressBarHome.setVisibility(View.VISIBLE);
                SearchHandWrite();
            }
        });
        searchBtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(searchBtn1.getText().equals("晴天")){
                    buttonTest.setText("晴天");
                    progressBarHome.setVisibility(View.VISIBLE);
                    searchByMood();
                }else if(searchBtn1.getText().equals("美食")){
                    buttonTest.setText("美食");
                    progressBarHome.setVisibility(View.VISIBLE);
                    searchByTag();
                }

            }
        });
        searchBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(searchBtn2.getText().equals("時晴")){
                    buttonTest.setText("時晴");
                    progressBarHome.setVisibility(View.VISIBLE);
                    searchByMood();
                }else if(searchBtn2.getText().equals("購物")){
                    buttonTest.setText("購物");
                    //searchTag();
                }
            }
        });
        searchBtn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(searchBtn3.getText().equals("多雲")){
                    buttonTest.setText("多雲");
                    progressBarHome.setVisibility(View.VISIBLE);
                    searchByMood();
                    //doDataSearchMood();
                }else if(searchBtn3.getText().equals("感情")){
                    buttonTest.setText("感情");
                    //searchTag();
                }
            }
        });
        searchBtn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(searchBtn4.getText().equals("陣雨")){
                    buttonTest.setText("陣雨");
                    progressBarHome.setVisibility(View.VISIBLE);
                    searchByMood();
                    //doDataSearchMood();
                }else if(searchBtn4.getText().equals("旅遊")){
                    buttonTest.setText("旅遊");
                    //searchTag();
                }
            }
        });
        searchBtn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(searchBtn5.getText().equals("雷雨")){
                    buttonTest.setText("雷雨");
                    progressBarHome.setVisibility(View.VISIBLE);
                    searchByMood();
                    //doDataSearchMood();
                }else if(searchBtn5.getText().equals("娛樂")){
                    buttonTest.setText("娛樂");
                    //searchTag();
                }
            }
        });



        // 時間
        String currentDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        final TextView DateTime = root.findViewById(R.id.DateTime);
        DateTime.setText(currentDate);

        return root;
    }


    // 此為全抓
    //----------------------------------------------------------------------------------------------
    public void history(){
        String uid = sqlReturn.GetUserID;
        Map<String,String> map = new HashMap<>();
        map.put("command", "history");
        map.put("uid", uid);
        new history(super.getActivity()).execute((HashMap)map);
    }

    public class history extends HttpURLConnection_AsyncTask {

        // 建立弱連結
        WeakReference<Activity> activityReference;
        history(Activity context){
            activityReference = new WeakReference<>(context);
        }
        @Override
        protected void onPostExecute(String result) {
            JSONObject jsonObject = null;
            JSONArray jsonArray = null;
            boolean status = false;
            // 取得弱連結的Context
            Activity activity = activityReference.get();
            if (activity == null || activity.isFinishing()) return;

            try {
                jsonObject = new JSONObject(result);
                sqlReturn.LoginTextViewContext = jsonObject.getString("results");
                sqlReturn.LoginCount = jsonObject.getInt("rowcount");
                jsonArray = new JSONArray(sqlReturn.LoginTextViewContext);
                sqlReturn.LoginContent = new String[sqlReturn.LoginCount];
                sqlReturn.LoginTagName = new String[sqlReturn.LoginCount];
                sqlReturn.LoginMood = new String[sqlReturn.LoginCount];
                sqlReturn.LoginDate = new String[sqlReturn.LoginCount];
                sqlReturn.LoginOption = new String[sqlReturn.LoginCount];
                sqlReturn.LoginDiaryID = new String[sqlReturn.LoginCount];
                for(int i = 0; i<sqlReturn.LoginCount; i++){
                    JSONObject obj = new JSONObject(String.valueOf(jsonArray.get(i)));
                    sqlReturn.LoginContent[i] = obj.getString("content");
                    sqlReturn.LoginTagName[i] = obj.getString("tagName");
                    if(obj.getString("mood").equals("心情1")){
                        sqlReturn.LoginMood[i] = "晴天";
                    }else if(obj.getString("mood").equals("心情2")){
                        sqlReturn.LoginMood[i] = "時晴";
                    }else if(obj.getString("mood").equals("心情3")){
                        sqlReturn.LoginMood[i] = "多雲";
                    }else if(obj.getString("mood").equals("心情4")){
                        sqlReturn.LoginMood[i] = "陣雨";
                    }else if(obj.getString("mood").equals("心情5")){
                        sqlReturn.LoginMood[i] = "雷雨";
                    }else if(obj.getString("mood").equals("手寫日記心情")){
                        sqlReturn.LoginMood[i] = "手寫日記";
                    }
                    sqlReturn.LoginDate[i] = obj.getString("date");
                    sqlReturn.LoginOption[i] = obj.getString("optionNo");
                    sqlReturn.LoginDiaryID[i] = obj.getString("diaryNo");
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (sqlReturn.LoginTextViewContext!=null){
                doData();
                mRecyclerView.setHasFixedSize(true);
                mLayoutManager = new LinearLayoutManager(HomeFragment.super.getActivity());
                mRecyclerView.setLayoutManager(mLayoutManager);
                myAdapter = new MyAdapter();
                mRecyclerView.setAdapter(myAdapter);
                RefreshLayoutHome.setRefreshing(false);
            }else {

            }
        }

    }
    public void doData(){
        data = new LinkedList<>();
        for(int i = 0; i < sqlReturn.LoginCount; i++){
            HashMap<String,String> row = new HashMap<>();
            row.put("textTitle",sqlReturn.LoginMood[i]);
            row.put("textDescription",sqlReturn.LoginDate[i]);
            data.add(row);
            sqlReturn.model = 1;
        }
    }

    private class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

        class MyViewHolder extends RecyclerView.ViewHolder{
            public View itemView;
            public ImageView imageView;
            public TextView textTitle, textDescription;
            public MyViewHolder(View view){
                super(view);
                itemView = view;
                imageView = itemView.findViewById(R.id.imageView);
                textTitle = itemView.findViewById(R.id.textTitle);
                textDescription = itemView.findViewById(R.id.textDescription);
                itemView.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View view) {

                        if(sqlReturn.model==1){
                            homeTag = getAdapterPosition();
                            Intent intent = new Intent(HomeFragment.super.getActivity(),HomeContextActivity.class);
                            intent.putExtra("data",1);
                            startActivity(intent);
                        }else if(sqlReturn.model==2){
                            homeTag = getAdapterPosition();
                            Intent intent = new Intent(HomeFragment.super.getActivity(),HomeContextActivity.class);
                            intent.putExtra("data",2);
                            startActivity(intent);
                        }else if(sqlReturn.model==3){
                            homeTag = getAdapterPosition();
                            Intent intent = new Intent(HomeFragment.super.getActivity(),HomeContextActivity.class);
                            intent.putExtra("data",3);
                            startActivity(intent);
                        }else if(sqlReturn.model==4){
                            homeTag = getAdapterPosition();
                            Intent intent = new Intent(HomeFragment.super.getActivity(),HomeContextActivity.class);
                            intent.putExtra("data",4);
                            startActivity(intent);
                        }
                    }
                });
            }
        }

        @NonNull
        @Override
        public MyAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.homeitem1,parent,false);
            MyViewHolder vh = new MyViewHolder(itemView);
            return vh;
        }

        @Override
        public void onBindViewHolder(@NonNull MyAdapter.MyViewHolder holder, int position) {


            if(sqlReturn.model == 1){
                if(sqlReturn.LoginOption[position].equals("1")){
                    holder.imageView.setImageResource(R.drawable.japan_icon);
                }else if(sqlReturn.LoginOption[position].equals("2")){
                    holder.imageView.setImageResource(R.drawable.korea_icon);
                }else if(sqlReturn.LoginOption[position].equals("4")){
                    holder.imageView.setImageResource(R.drawable.taiwan_icon);
                }else if(sqlReturn.LoginOption[position].equals("6")){
                    holder.imageView.setImageResource(R.drawable.italy_icon);
                }else if(sqlReturn.LoginOption[position].equals("7")){
                    holder.imageView.setImageResource(R.drawable.france_icon);
                }else if(sqlReturn.LoginOption[position].equals("8")){
                    holder.imageView.setImageResource(R.drawable.china_icon);
                }else if(sqlReturn.LoginOption[position].equals("9")){
                    holder.imageView.setImageResource(R.drawable.kong_icon);
                }else if(sqlReturn.LoginOption[position].equals("10")){
                    holder.imageView.setImageResource(R.drawable.ider_icon);
                }else if(sqlReturn.LoginOption[position].equals("11")){
                    holder.imageView.setImageResource(R.drawable.random_icon);
                }else if(sqlReturn.LoginOption[position].equals("42")){
                    holder.imageView.setImageResource(R.drawable.random_icon);
                }
            }else if(sqlReturn.model == 2){
                if(sqlReturn.Option1[position].equals("1")){
                    holder.imageView.setImageResource(R.drawable.japan_icon);
                }else if(sqlReturn.Option1[position].equals("2")){
                    holder.imageView.setImageResource(R.drawable.korea_icon);
                }else if(sqlReturn.Option1[position].equals("4")){
                    holder.imageView.setImageResource(R.drawable.taiwan_icon);
                }else if(sqlReturn.Option1[position].equals("6")){
                    holder.imageView.setImageResource(R.drawable.italy_icon);
                }else if(sqlReturn.Option1[position].equals("7")){
                    holder.imageView.setImageResource(R.drawable.france_icon);
                }else if(sqlReturn.Option1[position].equals("8")){
                    holder.imageView.setImageResource(R.drawable.china_icon);
                }else if(sqlReturn.Option1[position].equals("9")){
                    holder.imageView.setImageResource(R.drawable.kong_icon);
                }else if(sqlReturn.Option1[position].equals("10")){
                    holder.imageView.setImageResource(R.drawable.ider_icon);
                }else if(sqlReturn.Option1[position].equals("11")){
                    holder.imageView.setImageResource(R.drawable.random_icon);
                }else if(sqlReturn.Option1[position].equals("42")){
                    holder.imageView.setImageResource(R.drawable.random_icon);
                }
            }else if(sqlReturn.model == 3){
                if(sqlReturn.Option2[position].equals("1")){
                    holder.imageView.setImageResource(R.drawable.japan_icon);
                }else if(sqlReturn.Option2[position].equals("2")){
                    holder.imageView.setImageResource(R.drawable.korea_icon);
                }else if(sqlReturn.Option2[position].equals("4")){
                    holder.imageView.setImageResource(R.drawable.taiwan_icon);
                }else if(sqlReturn.Option2[position].equals("6")){
                    holder.imageView.setImageResource(R.drawable.italy_icon);
                }else if(sqlReturn.Option2[position].equals("7")){
                    holder.imageView.setImageResource(R.drawable.france_icon);
                }else if(sqlReturn.Option2[position].equals("8")){
                    holder.imageView.setImageResource(R.drawable.china_icon);
                }else if(sqlReturn.Option2[position].equals("9")){
                    holder.imageView.setImageResource(R.drawable.kong_icon);
                }else if(sqlReturn.Option2[position].equals("10")){
                    holder.imageView.setImageResource(R.drawable.ider_icon);
                }else if(sqlReturn.Option2[position].equals("11")){
                    holder.imageView.setImageResource(R.drawable.random_icon);
                }else if(sqlReturn.Option2[position].equals("42")){
                    holder.imageView.setImageResource(R.drawable.random_icon);
                }
            }else if(sqlReturn.model == 4){
                if(sqlReturn.Option3[position].equals("1")){
                    holder.imageView.setImageResource(R.drawable.japan_icon);
                }else if(sqlReturn.Option3[position].equals("2")){
                    holder.imageView.setImageResource(R.drawable.korea_icon);
                }else if(sqlReturn.Option3[position].equals("4")){
                    holder.imageView.setImageResource(R.drawable.taiwan_icon);
                }else if(sqlReturn.Option3[position].equals("6")){
                    holder.imageView.setImageResource(R.drawable.italy_icon);
                }else if(sqlReturn.Option3[position].equals("7")){
                    holder.imageView.setImageResource(R.drawable.france_icon);
                }else if(sqlReturn.Option3[position].equals("8")){
                    holder.imageView.setImageResource(R.drawable.china_icon);
                }else if(sqlReturn.Option3[position].equals("9")){
                    holder.imageView.setImageResource(R.drawable.kong_icon);
                }else if(sqlReturn.Option3[position].equals("10")){
                    holder.imageView.setImageResource(R.drawable.ider_icon);
                }else if(sqlReturn.Option3[position].equals("11")){
                    holder.imageView.setImageResource(R.drawable.random_icon);
                }else if(sqlReturn.Option3[position].equals("42")){
                    holder.imageView.setImageResource(R.drawable.random_icon);
                }
            }

            holder.textTitle.setText(data.get(position).get("textTitle"));
            holder.textDescription.setText(data.get(position).get("textDescription"));
        }

        @Override
        public int getItemCount() {
            return data.size();
        }
    }
    //----------------------------------------------------------------------------------------------


    //----------------------------------------------------------------------------------------------
    // 此為抓Mood
    private String searchMood;
    public void searchByMood(){
        if(buttonTest.getText().equals("晴天")){
            searchMood = "心情1";
        }else if(buttonTest.getText().equals("時晴")){
            searchMood = "心情2";
        }else if(buttonTest.getText().equals("多雲")){
            searchMood = "心情3";
        }else if(buttonTest.getText().equals("陣雨")){
            searchMood = "心情4";
        }else if(buttonTest.getText().equals("雷雨")){
            searchMood = "心情5";
        }
        String uid = sqlReturn.GetUserID;
        Map<String,String> map = new HashMap<>();
        map.put("command", "historyMood");
        map.put("uid", uid);
        map.put("searchMood",searchMood);
        new searchMoodGo(super.getActivity()).execute((HashMap)map);
    }
    private class searchMoodGo extends HttpURLConnection_AsyncTask {

        // 建立弱連結
        WeakReference<Activity> activityReference;
        searchMoodGo(Activity context){
            activityReference = new WeakReference<>(context);
        }
        @Override
        protected void onPostExecute(String result) {
            JSONObject jsonObject = null;
            JSONArray jsonArray = null;
            boolean status = false;
            // 取得弱連結的Context
            Activity activity = activityReference.get();
            if (activity == null || activity.isFinishing()) return;

            try {
                jsonObject = new JSONObject(result);
                status = jsonObject.getBoolean("status");
                if(status){
                    sqlReturn.textViewContext1 = jsonObject.getString("results");
                    sqlReturn.SearchCountMood = jsonObject.getInt("rowcount");
                    jsonArray = new JSONArray(sqlReturn.textViewContext1);
                    sqlReturn.content1 = new String[sqlReturn.SearchCountMood];
                    sqlReturn.tagName1 = new String[sqlReturn.SearchCountMood];
                    sqlReturn.mood1 = new String[sqlReturn.SearchCountMood];
                    sqlReturn.date1 = new String[sqlReturn.SearchCountMood];
                    sqlReturn.Option1 = new String[sqlReturn.SearchCountMood];
                    sqlReturn.DiaryID1 = new String[sqlReturn.SearchCountMood];
                    for(int i = 0; i<sqlReturn.SearchCountMood; i++){
                        JSONObject obj = new JSONObject(String.valueOf(jsonArray.get(i)));
                        sqlReturn.content1[i] = obj.getString("content");
                        sqlReturn.tagName1[i] = obj.getString("tagName");
                        if(obj.getString("mood").equals("心情1")){
                            sqlReturn.mood1[i] = "晴天";
                        }else if(obj.getString("mood").equals("心情2")){
                            sqlReturn.mood1[i] = "時晴";
                        }else if(obj.getString("mood").equals("心情3")){
                            sqlReturn.mood1[i] = "多雲";
                        }else if(obj.getString("mood").equals("心情4")){
                            sqlReturn.mood1[i] = "陣雨";
                        }else if(obj.getString("mood").equals("心情5")){
                            sqlReturn.mood1[i] = "雷雨";
                        }else if(obj.getString("mood").equals("手寫日記心情")){
                            sqlReturn.mood1[i] = "手寫日記";
                        }
                        sqlReturn.date1[i] = obj.getString("date");
                        sqlReturn.Option1[i] = obj.getString("optionNo");
                        sqlReturn.DiaryID1[i] = obj.getString("diaryNo");
                    }
                }else {
                    sqlReturn.textViewContext1 = "";
                    sqlReturn.SearchCountMood = 0;
                    sqlReturn.content1 = new String[sqlReturn.SearchCountMood];
                    sqlReturn.tagName1 = new String[sqlReturn.SearchCountMood];
                    sqlReturn.mood1 = new String[sqlReturn.SearchCountMood];
                    sqlReturn.date1 = new String[sqlReturn.SearchCountMood];
                    sqlReturn.Option1 = new String[sqlReturn.SearchCountMood];
                    sqlReturn.DiaryID1 = new String[sqlReturn.SearchCountMood];
                    for(int i = 0; i<sqlReturn.SearchCountMood; i++){
                        sqlReturn.content1[i] = "";
                        sqlReturn.tagName1[i] = "";
                        sqlReturn.mood1[i] = "";
                        sqlReturn.date1[i] = "";
                        sqlReturn.Option1[i] = "";
                        sqlReturn.DiaryID1[i] = "";
                    }
                    new AlertDialog.Builder(activity)
                            .setTitle("提醒")
                            .setMessage("您沒有撰寫過心情為 ("+buttonTest.getText()+") 的日記")
                            .setPositiveButton("OK", null)
                            .show();
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (sqlReturn.textViewContext1!=null){
                //Toast.makeText(activity, String.valueOf(sqlReturn.SearchCountMood), Toast.LENGTH_LONG).show();
                doDataSearchMood();
                mRecyclerView.setHasFixedSize(true);
                mLayoutManager = new LinearLayoutManager(HomeFragment.super.getActivity());
                mRecyclerView.setLayoutManager(mLayoutManager);
                myAdapter = new MyAdapter();
                mRecyclerView.setAdapter(myAdapter);
                progressBarHome.setVisibility(View.INVISIBLE);
                RefreshLayoutHome.setRefreshing(false);
            }else {
                progressBarHome.setVisibility(View.INVISIBLE);
            }
        }

    }
    private void doDataSearchMood(){
        data = new LinkedList<>();
        final int SearchCountMood = sqlReturn.SearchCountMood;
        for(int i = 0; i < SearchCountMood; i++){
            HashMap<String,String> row = new HashMap<>();
            row.put("textTitle",sqlReturn.mood1[i]);
            row.put("textDescription",sqlReturn.date1[i]);
            data.add(row);
            sqlReturn.model = 2;
        }
    }


    // 此為抓Tag
    public String searchTag;
    public void searchByTag(){

        if(searchBtn1.getText().equals("美食")){
            searchTag = "美食";
        }
        String uid = sqlReturn.GetUserID;
        Map<String,String> map = new HashMap<>();
        map.put("command", "historyTag");
        map.put("uid", uid);
        map.put("searchTag",searchTag);
        new searchTagGo(super.getActivity()).execute((HashMap)map);
    }


    private class searchTagGo extends HttpURLConnection_AsyncTask {

        // 建立弱連結
        WeakReference<Activity> activityReference;
        searchTagGo(Activity context){
            activityReference = new WeakReference<>(context);
        }
        @Override
        protected void onPostExecute(String result) {
            JSONObject jsonObject = null;
            JSONArray jsonArray = null;
            boolean status = false;
            // 取得弱連結的Context
            Activity activity = activityReference.get();
            if (activity == null || activity.isFinishing()) return;

            try {
                jsonObject = new JSONObject(result);

                sqlReturn.textViewContext2 = jsonObject.getString("results");
                sqlReturn.SearchCountTag = jsonObject.getInt("rowcount");
                jsonArray = new JSONArray(sqlReturn.textViewContext2);
                sqlReturn.content2 = new String[sqlReturn.SearchCountTag];
                sqlReturn.tagName2 = new String[sqlReturn.SearchCountTag];
                sqlReturn.mood2 = new String[sqlReturn.SearchCountTag];
                sqlReturn.date2 = new String[sqlReturn.SearchCountTag];
                sqlReturn.Option2 = new String[sqlReturn.SearchCountTag];
                sqlReturn.DiaryID2 = new String[sqlReturn.SearchCountTag];
                for(int i = 0; i<sqlReturn.SearchCountTag; i++){
                    JSONObject obj = new JSONObject(String.valueOf(jsonArray.get(i)));
                    sqlReturn.content2[i] = obj.getString("content");
                    sqlReturn.tagName2[i] = obj.getString("tagName");
                    sqlReturn.mood2[i] = obj.getString("mood");
                    sqlReturn.date2[i] = obj.getString("date");
                    sqlReturn.Option2[i] = obj.getString("optionNo");
                    sqlReturn.DiaryID2[i] = obj.getString("diaryNo");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (sqlReturn.textViewContext2!=null){
                //Toast.makeText(activity, String.valueOf(sqlReturn.SearchCountTag), Toast.LENGTH_LONG).show();
                doDataSearchTag();
                mRecyclerView.setHasFixedSize(true);
                mLayoutManager = new LinearLayoutManager(HomeFragment.super.getActivity());
                mRecyclerView.setLayoutManager(mLayoutManager);
                myAdapter = new MyAdapter();
                mRecyclerView.setAdapter(myAdapter);
                progressBarHome.setVisibility(View.INVISIBLE);
                RefreshLayoutHome.setRefreshing(false);
            }else {
//                new AlertDialog.Builder(activity)
//                        .setTitle("日記載入失敗")
//                        .setMessage("請確認網路是否連通!!")
//                        .setPositiveButton("OK", null)
//                        .show();
            }
        }

    }
    private void doDataSearchTag(){
        data = new LinkedList<>();
        for(int i = 0; i < sqlReturn.SearchCountTag; i++){
            HashMap<String,String> row = new HashMap<>();
            row.put("textTitle",sqlReturn.tagName2[i]);
            row.put("textDescription",sqlReturn.date2[i]);
            data.add(row);
            sqlReturn.model = 3;
        }
    }


    // 此為抓手寫日記

    public void SearchHandWrite(){
        String uid = sqlReturn.GetUserID;
        Map<String,String> map = new HashMap<>();
        map.put("command", "historyHandWrite");
        map.put("uid", uid);
        map.put("searchHandWrite","手寫日記心情");
        new SearchHandWrite(super.getActivity()).execute((HashMap)map);
    }
    private class SearchHandWrite extends HttpURLConnection_AsyncTask {

        // 建立弱連結
        WeakReference<Activity> activityReference;
        SearchHandWrite(Activity context){
            activityReference = new WeakReference<>(context);
        }
        @Override
        protected void onPostExecute(String result) {
            JSONObject jsonObject = null;
            JSONArray jsonArray = null;
            boolean status = false;
            // 取得弱連結的Context
            Activity activity = activityReference.get();
            if (activity == null || activity.isFinishing()) return;

            try {
                jsonObject = new JSONObject(result);
                status = jsonObject.getBoolean("status");
                if(status){
                    sqlReturn.textViewContext3 = jsonObject.getString("results");
                    sqlReturn.SearchCountHandWrite = jsonObject.getInt("rowcount");
                    jsonArray = new JSONArray(sqlReturn.textViewContext3);
                    sqlReturn.content3 = new String[sqlReturn.SearchCountHandWrite];
                    sqlReturn.tagName3 = new String[sqlReturn.SearchCountHandWrite];
                    sqlReturn.mood3 = new String[sqlReturn.SearchCountHandWrite];
                    sqlReturn.date3 = new String[sqlReturn.SearchCountHandWrite];
                    sqlReturn.Option3 = new String[sqlReturn.SearchCountHandWrite];
                    sqlReturn.DiaryID3 = new String[sqlReturn.SearchCountHandWrite];
                    for(int i = 0; i<sqlReturn.SearchCountHandWrite; i++){
                        JSONObject obj = new JSONObject(String.valueOf(jsonArray.get(i)));
                        sqlReturn.content3[i] = obj.getString("content");
                        sqlReturn.tagName3[i] = obj.getString("tagName");
                        if(obj.getString("mood").equals("手寫日記心情")){
                            sqlReturn.mood3[i] = "手寫日記";
                        }
                        sqlReturn.date3[i] = obj.getString("date");
                        sqlReturn.Option3[i] = obj.getString("optionNo");
                        sqlReturn.DiaryID3[i] = obj.getString("diaryNo");
                    }
                }else {
                    sqlReturn.textViewContext3 = "";
                    sqlReturn.SearchCountHandWrite = 0;
                    sqlReturn.content3 = new String[sqlReturn.SearchCountHandWrite];
                    sqlReturn.tagName3 = new String[sqlReturn.SearchCountHandWrite];
                    sqlReturn.mood3 = new String[sqlReturn.SearchCountHandWrite];
                    sqlReturn.date3 = new String[sqlReturn.SearchCountHandWrite];
                    sqlReturn.Option3 = new String[sqlReturn.SearchCountHandWrite];
                    sqlReturn.DiaryID3 = new String[sqlReturn.SearchCountHandWrite];
                    for(int i = 0; i<sqlReturn.SearchCountHandWrite; i++){
                        sqlReturn.content3[i] = "";
                        sqlReturn.tagName3[i] = "";
                        sqlReturn.mood3[i] = "";
                        sqlReturn.date3[i] = "";
                        sqlReturn.Option3[i] = "";
                        sqlReturn.DiaryID3[i] = "";
                    }
                    new AlertDialog.Builder(activity)
                            .setTitle("提醒")
                            .setMessage("您沒有撰寫過手寫日記")
                            .setPositiveButton("OK", null)
                            .show();
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (sqlReturn.textViewContext3!=null){
                //Toast.makeText(activity, String.valueOf(sqlReturn.SearchCountMood), Toast.LENGTH_LONG).show();
                doDataSearchHandWrite();
                mRecyclerView.setHasFixedSize(true);
                mLayoutManager = new LinearLayoutManager(HomeFragment.super.getActivity());
                mRecyclerView.setLayoutManager(mLayoutManager);
                myAdapter = new MyAdapter();
                mRecyclerView.setAdapter(myAdapter);
                progressBarHome.setVisibility(View.INVISIBLE);
                RefreshLayoutHome.setRefreshing(false);
            }else {
                progressBarHome.setVisibility(View.INVISIBLE);
            }
        }

    }
    private void doDataSearchHandWrite(){
        data = new LinkedList<>();
        final int SearchCountHandWrite = sqlReturn.SearchCountHandWrite;
        for(int i = 0; i < SearchCountHandWrite; i++){
            HashMap<String,String> row = new HashMap<>();
            row.put("textTitle",sqlReturn.mood3[i]);
            row.put("textDescription",sqlReturn.date3[i]);
            data.add(row);
            sqlReturn.model = 4;
        }
    }



    //----------------------------------------------------------------------------------------------




    //背景顏色切換
    private View.OnClickListener btnChangeColorOnClick = new View.OnClickListener() {
        public void onClick(View v) {
            int iBackColorRedVal, iBackColorRedEnd;
            if(!changeBtn){
                mLayout.setVisibility(View.VISIBLE);
                mGoToHandWrite.setEnabled(true);
                mGoToHandWrite.setVisibility(View.VISIBLE);
                mGoToDiary.setEnabled(true);
                mGoToDiary.setVisibility(View.VISIBLE);
                mGoToOCR.setEnabled(true);
                mGoToOCR.setVisibility(View.VISIBLE);
                changeBtn = true;
            }else if(changeBtn == true){
                mGoToHandWrite.setEnabled(false);
                mGoToHandWrite.setVisibility(View.INVISIBLE);
                mGoToDiary.setEnabled(false);
                mGoToDiary.setVisibility(View.INVISIBLE);
                mGoToOCR.setEnabled(false);
                mGoToOCR.setVisibility(View.INVISIBLE);
                changeBtn = false;
            }
            final int iBackColor =
                    ((ColorDrawable)(mLayout.getBackground())).getColor();
            iBackColorRedVal = (iBackColor & 0xFF);

            if (iBackColorRedVal > 127)
                iBackColorRedEnd = 0;
            else
                iBackColorRedEnd = 255;
            ValueAnimator animScreenBackColor =
                    ValueAnimator.ofInt(iBackColorRedVal, iBackColorRedEnd);
            animScreenBackColor.setDuration(500);
            animScreenBackColor.setInterpolator(new LinearInterpolator());
            animScreenBackColor.start();
            animScreenBackColor.addUpdateListener(new ValueAnimator.AnimatorUpdateListener(){
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    int val = (Integer)animation.getAnimatedValue();

                    mLayout.setBackgroundColor(
                            iBackColor & 0x33000000 | val << 16 | val << 8 | val );
                }
            });
        }
    };

}
