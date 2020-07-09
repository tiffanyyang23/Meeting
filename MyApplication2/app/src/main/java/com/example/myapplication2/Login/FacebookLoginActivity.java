package com.example.myapplication2.Login;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.facebook.AccessToken;
import com.example.myapplication2.R;
import com.facebook.CallbackManager;
import com.facebook.FacebookActivity;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.login.LoginBehavior;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class FacebookLoginActivity extends AppCompatActivity {
    private static final String TAG = FacebookActivity.class.getSimpleName();
    private CallbackManager callbackManager;
    private LoginManager loginManager;

    private TextView textView;
    private TextView textViewResult;
    private LoginButton loginButton;
    private Button logout_button;
    private ImageView mImgPhoto;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facebook_login);
        // init facebook
        FacebookSdk.sdkInitialize(getApplicationContext());
        // init LoginManager & CallbackManager
        loginManager = LoginManager.getInstance();
        //callbackManager = CallbackManager.Factory.create();
        callbackManager=CallbackManager.Factory.create();

        textView = findViewById(R.id.textView01);
        textViewResult = findViewById(R.id.textView02);
        logout_button = findViewById(R.id.logout_button);
        loginButton = (LoginButton) findViewById(R.id.login_button);
        mImgPhoto=findViewById(R.id.ImgPhoto);
        // loginButton.setReadPermissions("email");


        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Facebook Login
                loginFB();
            }
        });
        logout_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Facebook Logout
                loginManager.logOut();
                Glide.with(FacebookLoginActivity.this)
                        .load(R.drawable.ic_launcher_background)
                        .crossFade()
                        .into(mImgPhoto);
                textViewResult.setText("");

            }
        });
        // method_1.判斷用戶是否登入過
        if (Profile.getCurrentProfile() != null) {
            Profile profile = Profile.getCurrentProfile();
            // 取得用戶大頭照
            Uri userPhoto = profile.getProfilePictureUri(50, 50);
            String id = profile.getId();
            String name = profile.getName();
            Log.d(TAG, "Facebook userPhoto: " + userPhoto);
            Log.d(TAG, "Facebook id: " + id);
            Log.d(TAG, "Facebook name: " + name);
        }
    }
    private void loginFB(){
        // 設定FB login的顯示方式 ;預設是：NATIVE_WITH_FALLBACK
        loginManager.setLoginBehavior(LoginBehavior.NATIVE_WITH_FALLBACK);
        // 設定要跟用戶取得的權限，以下3個是基本可以取得，不需要經過FB的審核
        List<String> permissions = new ArrayList<>();
        permissions.add("public_profile");
        permissions.add("email");
        permissions.add("user_friends");
        // 設定要讀取的權限
        loginManager.logInWithReadPermissions(this, permissions);
        loginManager.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(final LoginResult loginResult) {
                // 登入成功
                // 透過GraphRequest來取得用戶的Facebook資訊
                GraphRequest graphRequest = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        try {
                            if (response.getConnection().getResponseCode() == 200) {
                                long id = object.getLong("id");
                                String name = object.getString("name");
                                String email = object.getString("email");
                                Log.d(TAG , "Facebook id:" + id);
                                Log.d(TAG, "Facebook name:" + name);
                                Log.d(TAG, "Facebook email:" + email);
                                // 此時如果登入成功，就可以順便取得用戶大頭照
                                Profile profile = Profile.getCurrentProfile();
                                // 設定大頭照大小
                                Uri userPhoto = profile.getProfilePictureUri(50, 50);
                                Glide.with(FacebookLoginActivity.this)
                                        .load(userPhoto.toString())
                                        .crossFade()
                                        .into(mImgPhoto);
                                textViewResult.setText(String.format(Locale.TAIWAN, "Name:%s\nE-mail:%s", name, email));
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
                // https://developers.facebook.com/docs/android/graph?locale=zh_TW
                // 如果要取得email，需透過添加參數的方式來獲取(如下)
                // 不添加只能取得id & name
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,email");
                graphRequest.setParameters(parameters);
                graphRequest.executeAsync();
            }

            @Override
            public void onCancel() {
                // 用戶取消
                Log.d(TAG, "Facebook onCancel");
            }

            @Override
            public void onError(FacebookException error) {
                // 登入失敗
                Log.d(TAG, "Facebook onError:" + error.toString());
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }
}
