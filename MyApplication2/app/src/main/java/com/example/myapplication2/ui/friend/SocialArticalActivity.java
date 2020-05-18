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
import com.example.myapplication2.sqlReturn;

public class SocialArticalActivity extends AppCompatActivity {

    private TextView SocialDiaryTitle,SocialUserName,SocialDiaryDateTime,txtSocialContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_socialartical);


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
        SocialUserName.setText(sqlReturn.friendName[FriendFragment.FriendTag]);
        SocialDiaryDateTime = findViewById(R.id.SocialDiaryDateTime);
        SocialDiaryDateTime.setText(sqlReturn.date3[FriendFragment.FriendTag]);
        SocialDiaryTitle = findViewById(R.id.SocialDiaryTitle);
        SocialDiaryTitle.setText(sqlReturn.tagName3[FriendFragment.FriendTag]);
        txtSocialContext = findViewById(R.id.txtSocialContext);
        txtSocialContext.setText(sqlReturn.content3[FriendFragment.FriendTag]);

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
