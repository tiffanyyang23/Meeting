package com.example.myapplication2;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import com.example.myapplication2.Diary.DiaryActivity;
import com.example.myapplication2.Login.LoginActivity;
import com.example.myapplication2.ui.dashboard.DashboardFragment;
import com.example.myapplication2.ui.friend.FriendFragment;
import com.example.myapplication2.ui.home.HomeFragment;
import com.example.myapplication2.ui.maybelike.MaybelikeFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    public static boolean login = false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (login == false){
            Intent intent = new Intent(MainActivity.this,LoginActivity.class);
            startActivity(intent);
        }


        BottomNavigationView navView = findViewById(R.id.nav_view);
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard,R.id.navigation_friend,R.id.navigation_maybelike)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupWithNavController(navView, navController);


        int id = getIntent().getIntExtra("id",0);
        if(id == 1){
            navController.navigate(R.id.navigation_home);
        }
        if(id == 2){
            navController.navigate(R.id.navigation_dashboard);
        }
        if(id == 3){
            navController.navigate(R.id.navigation_friend);
        }
        if(id == 4){
            navController.navigate(R.id.navigation_maybelike);
        }

        // 前往手寫日記
        final Button goToDiary = findViewById(R.id.goToDiarybutton);
        goToDiary.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent registerIntent = new Intent(MainActivity.this, DiaryActivity.class);
                MainActivity.this.startActivity(registerIntent);
            }
        });

        // 前往引導日記
        final Button goToHandWrite = findViewById(R.id.goToHandwritebutton);
        goToHandWrite.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent registerIntent = new Intent(MainActivity.this, HandwriteActivity.class);
                MainActivity.this.startActivity(registerIntent);
            }
        });

        // OCR暫時沒有
//        final Button goToOCR = findViewById(R.id.goToOCRbutton);
//        goToOCR.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View v) {
//                Intent registerIntent = new Intent(MainActivity.this, OCRActivity.class);
//                MainActivity.this.startActivity(registerIntent);
//            }
//        });


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
