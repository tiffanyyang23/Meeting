package com.example.myapplication2.Diary;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication2.DiaryValue;
import com.example.myapplication2.R;

public class DiaryWhatActivity extends AppCompatActivity{

    //public static String txtFood = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diarywhat);

        // 反回上一頁
        final ImageButton imbtnReturnFrontPage1 = findViewById(R.id.imbtnReturnFrontPage1);
        imbtnReturnFrontPage1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DiaryWhatActivity.this, DiaryTagActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                DiaryWhatActivity.this.startActivity(intent);
            }
        });

        // 前往下一頁 taiwan
        final Button btnTaiwan = findViewById(R.id.btn_taiwan);
        btnTaiwan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DiaryValue.txtWhat = "台式料理";
                if(DiaryValue.WhatLock){
                    DiaryValue.option = DiaryValue.txtWhat;
                    DiaryValue.WhatLock = false;
                }
                Intent intent = new Intent(DiaryWhatActivity.this, DiaryWhyActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                DiaryWhatActivity.this.startActivity(intent);
            }
        });

        // 前往下一頁 china
        final Button btnChina = findViewById(R.id.btn_china);
        btnChina.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DiaryValue.txtWhat = "中式料理";
                if(DiaryValue.WhatLock){
                    DiaryValue.option = DiaryValue.txtWhat;
                    DiaryValue.WhatLock = false;
                }
                Intent intent = new Intent(DiaryWhatActivity.this, DiaryWhyActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                DiaryWhatActivity.this.startActivity(intent);
            }
        });

        // 前往下一頁 france
        final Button btnFrance = findViewById(R.id.btn_france);
        btnFrance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DiaryValue.txtWhat = "法式料理";
                if(DiaryValue.WhatLock){
                    DiaryValue.option = DiaryValue.txtWhat;
                    DiaryValue.WhatLock = false;
                }
                Intent intent = new Intent(DiaryWhatActivity.this, DiaryWhyActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                DiaryWhatActivity.this.startActivity(intent);
            }
        });

        // 前往下一頁 italy
        final Button btnItaly = findViewById(R.id.btn_italy);
        btnItaly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DiaryValue.txtWhat = "義式料理";
                if(DiaryValue.WhatLock){
                    DiaryValue.option = DiaryValue.txtWhat;
                    DiaryValue.WhatLock = false;
                }
                Intent intent = new Intent(DiaryWhatActivity.this, DiaryWhyActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                DiaryWhatActivity.this.startActivity(intent);
            }
        });

        // 前往下一頁 Kong
        final Button btnKong = findViewById(R.id.btn_kong);
        btnKong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DiaryValue.txtWhat = "港式料理";
                if(DiaryValue.WhatLock){
                    DiaryValue.option = DiaryValue.txtWhat;
                    DiaryValue.WhatLock = false;
                }
                Intent intent = new Intent(DiaryWhatActivity.this, DiaryWhyActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                DiaryWhatActivity.this.startActivity(intent);
            }
        });

        // 前往下一頁 japan
        final Button btnJapan = findViewById(R.id.btn_japan);
        btnJapan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DiaryValue.txtWhat = "日式料理";
                if(DiaryValue.WhatLock){
                    DiaryValue.option = DiaryValue.txtWhat;
                    DiaryValue.WhatLock = false;
                }
                Intent intent = new Intent(DiaryWhatActivity.this, DiaryWhyActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                DiaryWhatActivity.this.startActivity(intent);
            }
        });

        // 前往下一頁 korea
        final Button btnKorea = findViewById(R.id.btn_korea);
        btnKorea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DiaryValue.txtWhat = "韓式料理";
                if(DiaryValue.WhatLock){
                    DiaryValue.option = DiaryValue.txtWhat;
                    DiaryValue.WhatLock = false;
                }
                Intent intent = new Intent(DiaryWhatActivity.this, DiaryWhyActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                DiaryWhatActivity.this.startActivity(intent);
            }
        });

        // 前往下一頁 random
        final Button btnRandom = findViewById(R.id.btn_random);
        btnRandom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DiaryValue.txtWhat = "無菜單料理";
                if(DiaryValue.WhatLock){
                    DiaryValue.option = DiaryValue.txtWhat;
                    DiaryValue.WhatLock = false;
                }
                Intent intent = new Intent(DiaryWhatActivity.this, DiaryWhyActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                DiaryWhatActivity.this.startActivity(intent);
            }
        });

        // 前往下一頁 ider
        final Button btnider = findViewById(R.id.btn_ider);
        btnider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DiaryValue.txtWhat = "創意料理";
                if(DiaryValue.WhatLock){
                    DiaryValue.option = DiaryValue.txtWhat;
                    DiaryValue.WhatLock = false;
                }
                Intent intent = new Intent(DiaryWhatActivity.this, DiaryWhyActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                DiaryWhatActivity.this.startActivity(intent);
            }
        });

        // 前往preview
        final Button mPreview = findViewById(R.id.btn_preview_what);
        mPreview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DiaryValue.txtWhat = "";
                DiaryValue.txtWhy = "";
                Intent intent = new Intent();
                intent.setClass(DiaryWhatActivity.this,DiaryPreviewActivity.class);
                Bundle tagData = new Bundle();
                tagData.putString("1","DiaryWhatActivity");
                intent.putExtras(tagData);
                startActivity(intent);
            }
        });

        //跳題
        final Button btn_skip = findViewById(R.id.btn_skip);
        btn_skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DiaryValue.txtWhat = "";
                Intent intent = new Intent(DiaryWhatActivity.this, DiaryWhyActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                DiaryWhatActivity.this.startActivity(intent);
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
