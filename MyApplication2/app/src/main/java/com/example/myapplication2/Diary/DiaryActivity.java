package com.example.myapplication2.Diary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.myapplication2.Guidor;
import com.example.myapplication2.Login.LoginActivity;
import com.example.myapplication2.Login.RegisterActivity;
import com.example.myapplication2.MainActivity;
import com.example.myapplication2.R;

public class DiaryActivity extends AppCompatActivity {

    public static String txtMood ="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary);

        //final Guidor guidor = new Guidor(getApplicationContext(),"diary.db",null,1);


        // 前往下一頁 sun -----------------------------------------------
        final Button btnsun = findViewById(R.id.btn_sun);
        btnsun.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //guidor.setMood("心情1");
                txtMood ="今天心情超好";
                //txtMood = guidor.getDiary();
                Intent registerIntent = new Intent(DiaryActivity.this, DiaryTagActivity.class);
                DiaryActivity.this.startActivity(registerIntent);
            }
        });

        // 前往下一頁 suncloud -----------------------------------------------
        final Button btnsuncloud = findViewById(R.id.btn_suncloud);
        btnsuncloud.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                txtMood ="今天心情還不錯";
                Intent registerIntent = new Intent(DiaryActivity.this, DiaryTagActivity.class);
                DiaryActivity.this.startActivity(registerIntent);
            }
        });

        // 前往下一頁 cloud -----------------------------------------------
        final Button btncloud = findViewById(R.id.btn_cloud);
        btncloud.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                txtMood ="今天心情普通";
                Intent registerIntent = new Intent(DiaryActivity.this, DiaryTagActivity.class);
                DiaryActivity.this.startActivity(registerIntent);
            }
        });

        // 前往下一頁 rain -----------------------------------------------
        final Button btnrain = findViewById(R.id.btn_rain);
        btnrain.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                txtMood ="今天心情稍差";
                Intent registerIntent = new Intent(DiaryActivity.this, DiaryTagActivity.class);
                DiaryActivity.this.startActivity(registerIntent);
            }
        });

        // 前往下一頁 thunder -----------------------------------------------
        final Button btnthunder = findViewById(R.id.btn_thunder);
        btnthunder.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                txtMood ="今天心情很不好";
                Intent registerIntent = new Intent(DiaryActivity.this, DiaryTagActivity.class);
                DiaryActivity.this.startActivity(registerIntent);
            }
        });

        // 返回主畫面
        final ImageButton imbtnReturnFrontPage = findViewById(R.id.imbtnReturnFrontPage);
        imbtnReturnFrontPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DiaryPreviewActivity.totalPlus = "";
                DiaryPreviewActivity.total = "";
                Intent intent = new Intent(DiaryActivity.this,MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("id",1);
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
