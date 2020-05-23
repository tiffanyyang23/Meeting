package com.example.myapplication2.Diary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.myapplication2.DiaryValue;
import com.example.myapplication2.R;
public class DiaryWhyActivity extends AppCompatActivity{

    //public static String txtWhy = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diarywhy);

        // 反回上一頁
        final ImageButton imbtnReturnFrontPage2 = findViewById(R.id.imbtnReturnFrontPage2);
        imbtnReturnFrontPage2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DiaryValue.WhatLock = true;
                Intent intent = new Intent(DiaryWhyActivity.this, DiaryWhatActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                DiaryWhyActivity.this.startActivity(intent);
            }
        });

        // 暫時前往結束頁
        final Button btn_nowhy = findViewById(R.id.btn_nowhy);
        btn_nowhy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DiaryValue.txtWhy = "沒為什麼";
                Intent intent = new Intent();
                intent.setClass(DiaryWhyActivity.this,DiaryPreviewActivity.class);
                Bundle tagData = new Bundle();
                tagData.putString("1","End");
                intent.putExtras(tagData);
                startActivity(intent);
            }
        });

        // 暫時前往結束頁
        final Button btn_hungry = findViewById(R.id.btn_hungry);
        btn_hungry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DiaryValue.txtWhy = "餓了";
                Intent intent = new Intent();
                intent.setClass(DiaryWhyActivity.this,DiaryPreviewActivity.class);
                Bundle tagData = new Bundle();
                tagData.putString("1","End");
                intent.putExtras(tagData);
                startActivity(intent);
            }
        });

        //跳題
        final Button btn_skip = findViewById(R.id.btn_skip);
        btn_skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DiaryValue.txtWhy = "";
                Intent intent = new Intent();
                intent.setClass(DiaryWhyActivity.this,DiaryPreviewActivity.class);
                Bundle tagData = new Bundle();
                tagData.putString("1","DiaryWhyActivity");
                intent.putExtras(tagData);
                startActivity(intent);
            }
        });

        // 前往preview
        final Button mPreview = findViewById(R.id.btn_preview_why);
        mPreview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DiaryValue.txtWhy = "";
                Intent intent = new Intent();
                intent.setClass(DiaryWhyActivity.this,DiaryPreviewActivity.class);
                Bundle tagData = new Bundle();
                tagData.putString("1","DiaryWhyActivity");
                intent.putExtras(tagData);
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
