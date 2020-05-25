package com.example.myapplication2.Diary;

import android.app.ActivityOptions;
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

public class DiaryTagActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diarytag);

        // 返回心情
        final ImageButton imbtnReturnToDiary = (ImageButton) findViewById(R.id.imbtnReturnToDiary);
        imbtnReturnToDiary.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                Intent intent = new Intent(DiaryTagActivity.this, DiaryActivity.class);
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(DiaryTagActivity.this);
                DiaryTagActivity.this.startActivity(intent,options.toBundle());
            }
        });
//        // 前往下一頁 clothes
//        final Button btn_clothes = findViewById(R.id.btn_clothes);
//        btn_clothes.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                txtTag ="因為我今天買了";
//                Intent intent = new Intent(DiaryTagActivity.this, DiaryWhatActivity.class);
//                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                DiaryTagActivity.this.startActivity(intent);
//            }
//        });
        // 前往下一頁 food
        final Button btn_food = findViewById(R.id.btn_food);
        btn_food.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DiaryValue.txtTag ="美食";
                Intent intent = new Intent(DiaryTagActivity.this, DiaryWhatActivity.class);
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(DiaryTagActivity.this);
                DiaryTagActivity.this.startActivity(intent,options.toBundle());
            }
        });
//        // 前往下一頁 travel
//        final Button btn_travel = findViewById(R.id.btn_travel);
//        btn_travel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                txtTag ="因為我今天去了";
//                Intent intent = new Intent(DiaryTagActivity.this, DiaryWhatActivity.class);
//                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                DiaryTagActivity.this.startActivity(intent);
//            }
//        });
//        // 前往下一頁 emotion
//        final Button btn_emotion = findViewById(R.id.btn_emotion);
//        btn_emotion.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                txtTag ="因為我今天感情";
//                Intent intent = new Intent(DiaryTagActivity.this, DiaryWhatActivity.class);
//                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                DiaryTagActivity.this.startActivity(intent);
//            }
//        });
//        // 前往下一頁 play
//        final Button btn_play = findViewById(R.id.btn_play);
//        btn_play.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                txtTag ="因為我今天玩了";
//                Intent intent = new Intent(DiaryTagActivity.this, DiaryWhatActivity.class);
//                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                DiaryTagActivity.this.startActivity(intent);
//            }
//        });
        // 前往 preview
        final Button btn_preview = findViewById(R.id.btn_preview);
        btn_preview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DiaryValue.txtTag ="";
                DiaryValue.txtWhat = "";
                DiaryValue.txtWhy = "";
                Intent intent = new Intent();
                intent.setClass(DiaryTagActivity.this,DiaryPreviewActivity.class);
                Bundle tagData = new Bundle();
                tagData.putString("1","DiaryTagActivity");
                intent.putExtras(tagData);
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(DiaryTagActivity.this);
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
