package com.example.myapplication2;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.example.myapplication2.Login.LoginActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;

public class SplashLoginActivity extends AppCompatActivity {

    Animation anim;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_login);
        history();
        imageView=(ImageView)findViewById(R.id.imageView2); // Declare an imageView to show the animation.
        anim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in); // Create the animation.
        anim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {

                Intent intent = new Intent(SplashLoginActivity.this, MainActivity.class);
                intent.putExtra("id",1);
                startActivity(intent);
                // HomeActivity.class is the activity to go after showing the splash screen.
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
        imageView.startAnimation(anim);
    }


    public static String textViewContext = null;
    public static int count;
    public static String[] content;
    public static String[] mood;
    public static String[] tagName;
    public static String[] date;

    public void history(){
        while (LoginActivity.a){
            String uid = LoginActivity.GetUserID;
            Map<String,String> map = new HashMap<>();
            map.put("command", "history");
            map.put("uid", uid);
            new history(this).execute((HashMap)map);
            break;
        }

    }

    private static class history extends HttpURLConnection_AsyncTask {

        // 建立弱連結
        WeakReference<Activity> activityReference;
        history(Activity context){
            activityReference = new WeakReference<>(context);
        }
        @Override
        protected void onPostExecute(String result) {
            JSONObject jsonObject = null;
            JSONArray jsonArray = null;
            boolean status = false;
            // 取得弱連結的Context
            Activity activity = activityReference.get();
            if (activity == null || activity.isFinishing()) return;

            try {
                jsonObject = new JSONObject(result);

                textViewContext = jsonObject.getString("results");
                count = jsonObject.getInt("rowcount");
                jsonArray = new JSONArray(textViewContext);
                content = new String[count];
                tagName = new String[count];
                mood = new String[count];
                date = new String[count];
                for(int i = 0; i<count; i++){
                    JSONObject obj = new JSONObject(String.valueOf(jsonArray.get(i)));
                    content[i] = obj.getString("content");
                    tagName[i] = obj.getString("tagName");
                    mood[i] = obj.getString("mood");
                    date[i] = obj.getString("date");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (textViewContext!=null){
                //Toast.makeText(activity, "載入成功", Toast.LENGTH_LONG).show();
            }else {
                new AlertDialog.Builder(activity)
                        .setTitle("日記載入失敗")
                        .setMessage("請確認網路是否連通!!")
                        .setPositiveButton("OK", null)
                        .show();
            }
        }


    }
}
