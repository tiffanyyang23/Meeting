package com.example.myapplication2;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.transition.Explode;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import com.example.myapplication2.Diary.DiaryActivity;
import com.example.myapplication2.Login.LoginActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import android.graphics.Color;

public class MainActivity extends AppCompatActivity {

    private ConstraintLayout mLayout;
    private Button goToDiarybutton, goToOCRbutton, goToHandwritebutton, btnAnim;
    public static boolean changeBtn = false;
    public static boolean login = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

        // 前往引導日記
        final Button goToDiary = findViewById(R.id.goToDiarybutton);
        goToDiary.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                changeBtn = false;
                Intent registerIntent = new Intent(MainActivity.this, DiaryActivity.class);
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(MainActivity.this);
                MainActivity.this.startActivity(registerIntent,options.toBundle());
            }
        });

        // 前往手寫日記
        final Button goToHandWrite = findViewById(R.id.goToHandwritebutton);
        goToHandWrite.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                changeBtn = false;
                Intent registerIntent = new Intent(MainActivity.this, HandwriteActivity.class);
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(MainActivity.this);
                MainActivity.this.startActivity(registerIntent,options.toBundle());
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



        goToDiarybutton = findViewById(R.id.goToDiarybutton);
        goToOCRbutton = findViewById(R.id.goToOCRbutton);
        goToHandwritebutton = findViewById(R.id.goToHandwritebutton);
        btnAnim = findViewById(R.id.btnAnim);
        mLayout = findViewById(R.id.testConstraint);
        btnAnim.setOnClickListener(btnChangeColorOnClick);



    }
    // 變化背景動畫
    private View.OnClickListener btnChangeColorOnClick = new View.OnClickListener() {
        public void onClick(View v) {
            int iBackColorRedVal, iBackColorRedEnd;

            if(!changeBtn){
                btnAnim.animate().rotation(btnAnim.getRotation()+135).start();
                mLayout.setVisibility(View.VISIBLE);
                goToHandwritebutton.setEnabled(true);
                goToHandwritebutton.setVisibility(View.VISIBLE);
                goToDiarybutton.setEnabled(true);
                goToDiarybutton.setVisibility(View.VISIBLE);
                goToOCRbutton.setEnabled(true);
                goToOCRbutton.setVisibility(View.VISIBLE);
                changeBtn = true;
            }else if(changeBtn){
                btnAnim.animate().rotation(btnAnim.getRotation()-135).start();
                goToHandwritebutton.setEnabled(false);
                goToHandwritebutton.setVisibility(View.INVISIBLE);
                goToDiarybutton.setEnabled(false);
                goToDiarybutton.setVisibility(View.INVISIBLE);
                goToOCRbutton.setEnabled(false);
                goToOCRbutton.setVisibility(View.INVISIBLE);
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
