package com.example.myapplication2.Diary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.myapplication2.MainActivity;
import com.example.myapplication2.R;


public class DiaryPreviewActivity extends AppCompatActivity{


    public static String total = "";
    public static String totalPlus = "";
    private ImageButton imBtnBack;
    private String finalTotal;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitivity_diarypreview);

        Bundle tag = getIntent().getExtras();
        final String tag1 = tag.getString("1");
        final TextView textView = findViewById(R.id.txtPreview);
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
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        // 回上一頁
        final ImageButton imBtnBack = findViewById(R.id.imbtnReturn);
        imBtnBack.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                if (tag1.equals("DiaryTagActivity")){
                    Intent intent = new Intent(DiaryPreviewActivity.this,DiaryTagActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    DiaryPreviewActivity.this.startActivity(intent);
                }else if(tag1.equals("DiaryWhatActivity")){
                    Intent intent = new Intent(DiaryPreviewActivity.this,DiaryWhatActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    DiaryPreviewActivity.this.startActivity(intent);
                }else if(tag1.equals("DiaryWhyActivity")) {
                    Intent intent = new Intent(DiaryPreviewActivity.this, DiaryWhyActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    DiaryPreviewActivity.this.startActivity(intent);
                }
            }
        });

        // total

        totalPlus = ("  "+DiaryActivity.txtMood +DiaryTagActivity.txtTag+DiaryWhatActivity.txtFood+"，"+DiaryWhyActivity.txtWhy+"。\n");
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
                intent.putExtra("total",finalTotal);
                intent.putExtra("1",tag1);
                startActivity(intent);

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
