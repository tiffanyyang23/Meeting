package com.example.myapplication2.Login;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication2.HttpURLConnection_AsyncTask;
import com.example.myapplication2.MainActivity;
import com.example.myapplication2.R;
import com.example.myapplication2.SplashLoginActivity;
import com.example.myapplication2.ui.home.HomeContextActivity;
import com.example.myapplication2.ui.home.HomeFragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    private static ProgressBar pr1;
    private String mail;
    private String pwd;
    public static String GetUserID;
    public static boolean a = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final TextView registerLink = (TextView) findViewById(R.id.register);
        registerLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerIntent = new Intent(LoginActivity.this,RegisterActivity.class);
                LoginActivity.this.startActivity(registerIntent);
            }
        });



    }
    public void login(View v){
        final EditText edUserEmail = findViewById(R.id.userEmail);
        final EditText edPasswd = findViewById(R.id.password);
        pr1 = findViewById(R.id.progressBar1);
        mail = edUserEmail.getText().toString();
        pwd = edPasswd.getText().toString();
        if(mail.equals("")){
            return;
        }else if(pwd.equals("")){
            return;
        }else{
            Map<String,String> map = new HashMap<>();
            map.put("command", "userLogin");
            map.put("email", mail);
            map.put("pwd", pwd);
            new login(this).execute((HashMap)map);
            pr1.setVisibility(View.VISIBLE);
        }


    }

    public class login extends HttpURLConnection_AsyncTask {

        // 建立弱連結
        WeakReference<Activity> activityReference;
        login(Activity context){
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
                GetUserID = jsonObject.getString("userID");
                status = jsonObject.getBoolean("status");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (status){
                MainActivity.login = true;
                a = true;
                //Toast.makeText(activity, "登入成功", Toast.LENGTH_LONG).show();
                // 對Context進行操作
                Intent intent = new Intent(LoginActivity.this, SplashLoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                startActivity(intent);

            }else {
                new AlertDialog.Builder(activity)
                        .setTitle("登入失敗")
                        .setMessage("請確認帳號或密碼是否正確!!")
                        .setPositiveButton("OK", null)
                        .show();
                pr1.setVisibility(View.INVISIBLE);
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

