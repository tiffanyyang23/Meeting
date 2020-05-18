package com.example.myapplication2.ui.friend;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication2.MainActivity;
import com.example.myapplication2.R;

public class SocialArticalActivity extends AppCompatActivity {

    private TextView SocialDiaryTitle,SocialUserName,SocialDiaryDateTime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_socialartical);

        String[] a = {"陳詩庭","陳昱","楊景婷","王振宇","藍允謙"};
        String[] b = {"2020/05/01","2020/05/02","2020/05/03","2020/05/04","2020/05/05"};
        String[] c = {"食","購物","旅遊","感情","休閒娛樂"};

        final ImageButton imbtnReturnToSocial = findViewById(R.id.imbtnReturnToSocial);
        imbtnReturnToSocial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SocialArticalActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("id",3);
                startActivity(intent);
            }
        });

        SocialUserName = findViewById(R.id.SocialUserName);
        SocialUserName.setText(a[FriendFragment.Tag]);
        SocialDiaryDateTime = findViewById(R.id.SocialDiaryDateTime);
        SocialDiaryDateTime.setText(b[FriendFragment.Tag]);
        SocialDiaryTitle = findViewById(R.id.SocialDiaryTitle);
        SocialDiaryTitle.setText(c[FriendFragment.Tag]);

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
