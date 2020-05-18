package com.example.myapplication2.Diary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.myapplication2.R;

public class DiaryEndActivity extends AppCompatActivity {

    private TextView editText3;
    String EditDiaryContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary_end);

        final String LastView = getIntent().getStringExtra("1");

        //接收日記
        editText3 = findViewById(R.id.editText3);
        final String DiaryContext = getIntent().getStringExtra("total");
        editText3.setText(DiaryContext);

        //回到上一頁
        final ImageButton imbtnReturnLast = findViewById(R.id.imbtnReturnLast);
        imbtnReturnLast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editText3.getText().toString().equals(DiaryContext)){

                }else {
                    EditDiaryContext = editText3.getText().toString();
                }
                Intent intent = new Intent();
                intent.setClass(DiaryEndActivity.this,DiaryPreviewActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                Bundle tagData = new Bundle();
                tagData.putString("1",LastView);
                intent.putExtras(tagData);
                intent.putExtra("Edit", EditDiaryContext);
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
