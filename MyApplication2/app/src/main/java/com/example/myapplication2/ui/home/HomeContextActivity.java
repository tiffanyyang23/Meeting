package com.example.myapplication2.ui.home;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication2.HttpURLConnection_AsyncTask;
import com.example.myapplication2.MainActivity;
import com.example.myapplication2.R;
import com.example.myapplication2.sqlReturn;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;

public class HomeContextActivity extends AppCompatActivity {

    private TextView txtHistoryDiary,textTitle,textDescription;
    private ImageView ContextImageView;
    private Button btnDelete;
    private int position;

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

        btnDelete = findViewById(R.id.btnDelete);
        txtHistoryDiary = findViewById(R.id.txtHistoryDiary);
        textTitle = findViewById(R.id.textTitle);
        textDescription = findViewById(R.id.textDescription);
        ContextImageView = findViewById(R.id.ContextImageView);

        final int Getdata = getIntent().getIntExtra("data",0);
        if(Getdata == 1){
            String total = "    "+sqlReturn.LoginContent[HomeFragment.homeTag];
            String mood = sqlReturn.LoginMood[HomeFragment.homeTag];
            String date = sqlReturn.LoginDate[HomeFragment.homeTag];
            txtHistoryDiary.setText(total);
            textTitle.setText(mood);
            textDescription.setText(date);
            if(sqlReturn.LoginOption[HomeFragment.homeTag].equals("1")){
                ContextImageView.setImageResource(R.drawable.japan_icon);
            }else if(sqlReturn.LoginOption[HomeFragment.homeTag].equals("2")){
                ContextImageView.setImageResource(R.drawable.korea_icon);
            }else if(sqlReturn.LoginOption[HomeFragment.homeTag].equals("4")){
                ContextImageView.setImageResource(R.drawable.taiwan_icon);
            }else if(sqlReturn.LoginOption[HomeFragment.homeTag].equals("6")){
                ContextImageView.setImageResource(R.drawable.italy_icon);
            }else if(sqlReturn.LoginOption[HomeFragment.homeTag].equals("7")){
                ContextImageView.setImageResource(R.drawable.france_icon);
            }else if(sqlReturn.LoginOption[HomeFragment.homeTag].equals("8")){
                ContextImageView.setImageResource(R.drawable.china_icon);
            }else if(sqlReturn.LoginOption[HomeFragment.homeTag].equals("9")){
                ContextImageView.setImageResource(R.drawable.kong_icon);
            }else if(sqlReturn.LoginOption[HomeFragment.homeTag].equals("10")){
                ContextImageView.setImageResource(R.drawable.ider_icon);
            }else if(sqlReturn.LoginOption[HomeFragment.homeTag].equals("11")){
                ContextImageView.setImageResource(R.drawable.random_icon);
            }else if(sqlReturn.LoginOption[HomeFragment.homeTag].equals("42")){
                ContextImageView.setImageResource(R.drawable.random_icon);
            }
        }else if(Getdata == 2){
            String total = "    "+ sqlReturn.content1[HomeFragment.homeTag];
            String mood = sqlReturn.mood1[HomeFragment.homeTag];
            String date = sqlReturn.date1[HomeFragment.homeTag];
            txtHistoryDiary.setText(total);
            textTitle.setText(mood);
            textDescription.setText(date);
            if(sqlReturn.Option1[HomeFragment.homeTag].equals("1")){
                ContextImageView.setImageResource(R.drawable.japan_icon);
            }else if(sqlReturn.Option1[HomeFragment.homeTag].equals("2")){
                ContextImageView.setImageResource(R.drawable.korea_icon);
            }else if(sqlReturn.Option1[HomeFragment.homeTag].equals("4")){
                ContextImageView.setImageResource(R.drawable.taiwan_icon);
            }else if(sqlReturn.Option1[HomeFragment.homeTag].equals("6")){
                ContextImageView.setImageResource(R.drawable.italy_icon);
            }else if(sqlReturn.Option1[HomeFragment.homeTag].equals("7")){
                ContextImageView.setImageResource(R.drawable.france_icon);
            }else if(sqlReturn.Option1[HomeFragment.homeTag].equals("8")){
                ContextImageView.setImageResource(R.drawable.china_icon);
            }else if(sqlReturn.Option1[HomeFragment.homeTag].equals("9")){
                ContextImageView.setImageResource(R.drawable.kong_icon);
            }else if(sqlReturn.Option1[HomeFragment.homeTag].equals("10")){
                ContextImageView.setImageResource(R.drawable.ider_icon);
            }else if(sqlReturn.Option1[HomeFragment.homeTag].equals("11")){
                ContextImageView.setImageResource(R.drawable.random_icon);
            }else if(sqlReturn.Option1[HomeFragment.homeTag].equals("42")){
                ContextImageView.setImageResource(R.drawable.random_icon);
            }
        }else if(Getdata == 3){
            String total = "    "+ sqlReturn.content2[HomeFragment.homeTag];
            String mood = sqlReturn.mood2[HomeFragment.homeTag];
            String date = sqlReturn.date2[HomeFragment.homeTag];
            txtHistoryDiary.setText(total);
            textTitle.setText(mood);
            textDescription.setText(date);
            if(sqlReturn.Option2[HomeFragment.homeTag].equals("1")){
                ContextImageView.setImageResource(R.drawable.japan_icon);
            }else if(sqlReturn.Option2[HomeFragment.homeTag].equals("2")){
                ContextImageView.setImageResource(R.drawable.korea_icon);
            }else if(sqlReturn.Option2[HomeFragment.homeTag].equals("4")){
                ContextImageView.setImageResource(R.drawable.taiwan_icon);
            }else if(sqlReturn.Option2[HomeFragment.homeTag].equals("6")){
                ContextImageView.setImageResource(R.drawable.italy_icon);
            }else if(sqlReturn.Option2[HomeFragment.homeTag].equals("7")){
                ContextImageView.setImageResource(R.drawable.france_icon);
            }else if(sqlReturn.Option2[HomeFragment.homeTag].equals("8")){
                ContextImageView.setImageResource(R.drawable.china_icon);
            }else if(sqlReturn.Option2[HomeFragment.homeTag].equals("9")){
                ContextImageView.setImageResource(R.drawable.kong_icon);
            }else if(sqlReturn.Option2[HomeFragment.homeTag].equals("10")){
                ContextImageView.setImageResource(R.drawable.ider_icon);
            }else if(sqlReturn.Option2[HomeFragment.homeTag].equals("11")){
                ContextImageView.setImageResource(R.drawable.random_icon);
            }else if(sqlReturn.Option2[HomeFragment.homeTag].equals("42")){
                ContextImageView.setImageResource(R.drawable.random_icon);
            }
        }else if(Getdata == 4){
            String total = "    "+ sqlReturn.content3[HomeFragment.homeTag];
            String mood = sqlReturn.mood3[HomeFragment.homeTag];
            String date = sqlReturn.date3[HomeFragment.homeTag];
            txtHistoryDiary.setText(total);
            textTitle.setText(mood);
            textDescription.setText(date);
            if(sqlReturn.Option3[HomeFragment.homeTag].equals("1")){
                ContextImageView.setImageResource(R.drawable.japan_icon);
            }else if(sqlReturn.Option3[HomeFragment.homeTag].equals("2")){
                ContextImageView.setImageResource(R.drawable.korea_icon);
            }else if(sqlReturn.Option3[HomeFragment.homeTag].equals("4")){
                ContextImageView.setImageResource(R.drawable.taiwan_icon);
            }else if(sqlReturn.Option3[HomeFragment.homeTag].equals("6")){
                ContextImageView.setImageResource(R.drawable.italy_icon);
            }else if(sqlReturn.Option3[HomeFragment.homeTag].equals("7")){
                ContextImageView.setImageResource(R.drawable.france_icon);
            }else if(sqlReturn.Option3[HomeFragment.homeTag].equals("8")){
                ContextImageView.setImageResource(R.drawable.china_icon);
            }else if(sqlReturn.Option3[HomeFragment.homeTag].equals("9")){
                ContextImageView.setImageResource(R.drawable.kong_icon);
            }else if(sqlReturn.Option3[HomeFragment.homeTag].equals("10")){
                ContextImageView.setImageResource(R.drawable.ider_icon);
            }else if(sqlReturn.Option3[HomeFragment.homeTag].equals("11")){
                ContextImageView.setImageResource(R.drawable.random_icon);
            }else if(sqlReturn.Option3[HomeFragment.homeTag].equals("42")){
                ContextImageView.setImageResource(R.drawable.random_icon);
            }
        }
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //DeleteDiary();
            }
        });

    }

    public void DeleteDiary(){
        Map<String,String> map = new HashMap<>();
        map.put("command", "deleteDiary");
        map.put("content",txtHistoryDiary.getText().toString());
        map.put("mood",textTitle.getText().toString());
        map.put("date",textDescription.getText().toString());
        new DeleteDiary(this).execute((HashMap)map);
    }
    private class DeleteDiary extends HttpURLConnection_AsyncTask {
        // 建立弱連結
        WeakReference<Activity> activityReference;
        DeleteDiary(Activity context){
            activityReference = new WeakReference<>(context);
        }
        @Override
        protected void onPostExecute(String result) {
            JSONObject jsonObject = null;
            boolean status = false;
            // 取得弱連結的Context
            Activity activity = activityReference.get();
            if (activity == null || activity.isFinishing()) return;

            try {
                jsonObject = new JSONObject(result);
                status = jsonObject.getBoolean("status");

            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (status){
                Toast.makeText(activity, "刪除成功", Toast.LENGTH_LONG).show();
            }else {
                new AlertDialog.Builder(activity)
                        .setTitle("日記刪除失敗")
                        .setMessage("請確認網路是否連通!!")
                        .setPositiveButton("OK", null)
                        .show();
            }
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
