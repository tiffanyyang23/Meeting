package com.example.myapplication2.Login;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication2.HttpURLConnection_AsyncTask;
import com.example.myapplication2.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    private static ProgressBar pr2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        final ImageButton imBtnBack = (ImageButton) findViewById(R.id.imbtnBackToLogin);

        imBtnBack.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                Intent registerIntent = new Intent(RegisterActivity.this, LoginActivity.class);
                registerIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                RegisterActivity.this.startActivity(registerIntent);
            }
        });
    }

    public void Register(View v){
        final EditText etName = findViewById(R.id.etName);
        final EditText etEmail = findViewById(R.id.etEmail);
        final EditText etPassword = findViewById(R.id.etPassword);
        final EditText etPasswordCheck = findViewById(R.id.etPasswordCheck);
        final TextView txtMessage = findViewById(R.id.txtMessage);
        pr2 = findViewById(R.id.progressBar2);
        String Name = etName.getText().toString();
        String Email = etEmail.getText().toString();
        String password1 = etPassword.getText().toString();
        String password2 = etPasswordCheck.getText().toString();
        if(Name.equals("")){
            if(Email.equals("")){
                if(password1.equals("")){
                    txtMessage.setText("未輸入基本資料");
                    return;
                }else {
                    txtMessage.setText("未輸入使用者名稱及電子信箱");
                    return;
                }
            }else if(password1.equals("")){
                txtMessage.setText("未輸入使用者名稱及密碼");
                return;
            }else{
                txtMessage.setText("未輸入使用者名稱");
                return;
            }
        }else if(Email.equals("")){
            if(password1.equals("")){
                txtMessage.setText("未輸入電子郵件及密碼");
            }else{
                txtMessage.setText("未輸入電子郵件信箱");
                return;
            }
        }else if(password1.equals("")){
            txtMessage.setText("未輸入密碼");
            return;
        }else{
            if(password1.equals(password2)){
                Map<String,String> map = new HashMap<>();
                map.put("command", "userRegister");
                map.put("pwd", password1);
                map.put("name", Name);
                map.put("email", Email);
                new Regsiter(this).execute((HashMap)map);
                pr2.setVisibility(View.VISIBLE);
            }else{
                txtMessage.setText("密碼不相同");
                return;
            }
        }

    }
    private static class Regsiter extends HttpURLConnection_AsyncTask{
        WeakReference<Activity> activityReference;
        Regsiter(Activity context){
            activityReference = new WeakReference<>(context);
        }
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
                Toast.makeText(activity, "註冊成功", Toast.LENGTH_LONG).show();
                // 對Context進行操作
                activity.finish();
                if (!activity.isDestroyed()){
                    Log.d("LOG TEST", "Activity未關閉");
                }
            }else {
                new AlertDialog.Builder(activity)
                        .setTitle("註冊失敗")
                        .setMessage("請確認網路是否開啟!!")
                        .setPositiveButton("OK", null)
                        .show();
                pr2.setVisibility(View.INVISIBLE);
            }


        }
    }
}
