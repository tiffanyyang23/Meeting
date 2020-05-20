package com.example.myapplication2.ui.home;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication2.MainActivity;
import com.example.myapplication2.R;
import com.example.myapplication2.sqlReturn;

public class HomeContextActivity extends AppCompatActivity {

    private TextView txtHistoryDiary,textTitle,textDescription;
    private ImageView ContextImageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_context);
        final ImageButton imbtnBackHome = findViewById(R.id.imbtnBackHome);
        imbtnBackHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeContextActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("id",1);
                startActivity(intent);
            }
        });

        txtHistoryDiary = findViewById(R.id.txtHistoryDiary);
        textTitle = findViewById(R.id.textTitle);
        textDescription = findViewById(R.id.textDescription);


        int Getdata = getIntent().getIntExtra("data",0);
        if(Getdata == 1){
            String total = "    "+sqlReturn.LoginContent[HomeFragment.homeTag];
            String mood = sqlReturn.LoginMood[HomeFragment.homeTag];
            String date = sqlReturn.LoginDate[HomeFragment.homeTag];
            txtHistoryDiary.setText(total);
            textTitle.setText(mood);
            textDescription.setText(date);
        }else if(Getdata == 2){
            String total = "    "+ sqlReturn.content1[HomeFragment.homeTag];
            String mood = sqlReturn.mood1[HomeFragment.homeTag];
            String date = sqlReturn.date1[HomeFragment.homeTag];
            txtHistoryDiary.setText(total);
            textTitle.setText(mood);
            textDescription.setText(date);
        }else if(Getdata == 3){
            String total = "    "+ sqlReturn.content2[HomeFragment.homeTag];
            String mood = sqlReturn.mood2[HomeFragment.homeTag];
            String date = sqlReturn.date2[HomeFragment.homeTag];
            txtHistoryDiary.setText(total);
            textTitle.setText(mood);
            textDescription.setText(date);
        }

        ContextImageView = findViewById(R.id.ContextImageView);
        int getHomeTag = HomeFragment.homeTag;
        if(getHomeTag == 0){
            ContextImageView.setImageResource(R.drawable.taiwan_icon);
        }else if(getHomeTag == 1){
            ContextImageView.setImageResource(R.drawable.ider_icon);
        }else{
            ContextImageView.setImageResource(R.drawable.china_icon);
        }

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
