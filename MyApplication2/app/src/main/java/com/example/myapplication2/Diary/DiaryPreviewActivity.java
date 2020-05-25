package com.example.myapplication2.Diary;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.myapplication2.DiaryValue;
import com.example.myapplication2.MainActivity;
import com.example.myapplication2.R;


public class DiaryPreviewActivity extends AppCompatActivity{


    public static String total = "";
    public static String totalPlus = "";
    private String finalTotal;
    private TextView textView;
    private static String a ="";
    private static String b;
    private Button btn_SaveEdit;
    private ImageButton imBtnBack;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitivity_diarypreview);

        Bundle tag = getIntent().getExtras();
        final String tag1 = tag.getString("1");
        textView = findViewById(R.id.txtPreview);
        final String edit = getIntent().getStringExtra("Edit");

        // 返回主題頁
        final Button mChangeTag = findViewById(R.id.btn_changeTag);
        mChangeTag.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                if(edit == null){
                    total += totalPlus;
                }else {
                    if(edit.equals(totalPlus)){
                        total = totalPlus;
                    }else{
                        total = edit;
                    }
                }
                Intent intent = new Intent(DiaryPreviewActivity.this, DiaryTagActivity.class);
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(DiaryPreviewActivity.this);
                startActivity(intent,options.toBundle());
            }
        });

        // 回上一頁
        imBtnBack = findViewById(R.id.imbtnReturn);
        imBtnBack.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                if (tag1.equals("DiaryTagActivity")){
                    Intent intent = new Intent(DiaryPreviewActivity.this,DiaryTagActivity.class);
                    ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(DiaryPreviewActivity.this);
                    DiaryPreviewActivity.this.startActivity(intent,options.toBundle());
                }else if(tag1.equals("DiaryWhatActivity")){

                    Intent intent = new Intent(DiaryPreviewActivity.this,DiaryWhatActivity.class);
                    ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(DiaryPreviewActivity.this);
                    DiaryPreviewActivity.this.startActivity(intent,options.toBundle());
                }else if(tag1.equals("DiaryWhyActivity")) {
                    Intent intent = new Intent(DiaryPreviewActivity.this, DiaryWhyActivity.class);
                    ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(DiaryPreviewActivity.this);
                    DiaryPreviewActivity.this.startActivity(intent,options.toBundle());
                }else if(tag1.equals("End")){
                    Intent intent = new Intent(DiaryPreviewActivity.this, DiaryWhyActivity.class);
                    ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(DiaryPreviewActivity.this);
                    DiaryPreviewActivity.this.startActivity(intent,options.toBundle());
                }
            }
        });


        // total
        final Guidor guidor = new Guidor(getApplicationContext(),"diary.db",null,1);
        String a = "";
        if(DiaryValue.txtTag.equals("")){
            if(DiaryValue.txtWhat.equals("")){
                if(DiaryValue.txtWhy.equals("")){
                    guidor.setMood(DiaryValue.txtMood);
                    a = guidor.getDiary();
                }else {
                    guidor.setMood(DiaryValue.txtMood);
                    guidor.setWhy(DiaryValue.txtWhy);
                    a = guidor.getDiary();
                }
            }else{
                if(DiaryValue.txtWhy.equals("")) {
                    guidor.setMood(DiaryValue.txtMood);
                    guidor.setWhat(DiaryValue.txtWhat);
                    a = guidor.getDiary();
                }else{
                    guidor.setMood(DiaryValue.txtMood);
                    guidor.setWhy(DiaryValue.txtWhy);
                    guidor.setWhat(DiaryValue.txtWhat);
                    a = guidor.getDiary();
                }
            }
        }else{
            if(DiaryValue.txtWhat.equals("")){
                if(DiaryValue.txtWhy.equals("")){
                    guidor.setMood(DiaryValue.txtMood);
                    guidor.setTag(DiaryValue.txtTag);
                    a = guidor.getDiary();
                }else {
                    guidor.setMood(DiaryValue.txtMood);
                    guidor.setTag(DiaryValue.txtTag);
                    guidor.setWhy(DiaryValue.txtWhy);
                    a = guidor.getDiary();
                }
            }else{
                if(DiaryValue.txtWhy.equals("")){
                    guidor.setMood(DiaryValue.txtMood);
                    guidor.setTag(DiaryValue.txtTag);
                    guidor.setWhat(DiaryValue.txtWhat);
                    a = guidor.getDiary();
                }else {
                    guidor.setMood(DiaryValue.txtMood);
                    guidor.setTag(DiaryValue.txtTag);
                    guidor.setWhy(DiaryValue.txtWhy);
                    guidor.setWhat(DiaryValue.txtWhat);
                    a = guidor.getDiary();
                }
            }
        }
        totalPlus = ("    "+ a+"\n");
        if(edit == null){
            finalTotal = total+ totalPlus;
            textView.setText(finalTotal);
        }else{
            finalTotal = edit;
            textView.setText(finalTotal);
        }



        // 結束
        final Button mEnd = findViewById(R.id.btn_end);
        mEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DiaryPreviewActivity.this, DiaryEndActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("total",textView.getText().toString());
                intent.putExtra("1",tag1);
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(DiaryPreviewActivity.this);
                startActivity(intent,options.toBundle());

            }
        });

    }

    // 擋住手機上回上一頁鍵
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (getApplicationInfo().targetSdkVersion >= Build.VERSION_CODES.ECLAIR) {
                event.startTracking();
            } else {
                onBackPressed(); // 是其他按鍵則再Call Back方法
            }
        }
        return false;
    }
    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        return super.onKeyUp(keyCode, event);
    }

}
