package com.example.myapplication2;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class HandwriteActivity extends AppCompatActivity {

    private DisplayMetrics mPhone;
    private ImageView imageGetPhoto;
    private Button handWriteButton;
    private TextView txtHandWrite;
    private String handWriteContext;
    private ProgressBar progressBarHandWrite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handwrite);
        final ImageButton imbtnReturnMain = findViewById(R.id.imbtnReturnMain);
        imbtnReturnMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HandwriteActivity.this,MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("id",1);
                startActivity(intent);
            }
        });
        mPhone = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(mPhone);
        final ImageButton getPhoto = findViewById(R.id.getPhoto);
        imageGetPhoto = findViewById(R.id.imageGetPhoto);
        getPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                //開啟Pictures畫面Type設定為image
                intent.setType("image/*");
                //使用Intent.ACTION_GET_CONTENT這個Action
                intent.setAction(Intent.ACTION_GET_CONTENT);
                //取得相片後返回本畫面
                startActivityForResult(intent, 1);
            }
        });

        progressBarHandWrite = findViewById(R.id.progressBarHandWrite);

        txtHandWrite = findViewById(R.id.txtHandWrite);

        handWriteButton = findViewById(R.id.handWriteButton);
        handWriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBarHandWrite.setVisibility(View.VISIBLE);
                handWriteContext = txtHandWrite.getText().toString();
                if(handWriteContext.equals("")){
                    progressBarHandWrite.setVisibility(View.INVISIBLE);
                }else {
                    DiaryInsert();
                }
            }
        });

    }

    public void DiaryInsert(){
        String currentDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        handWriteContext = txtHandWrite.getText().toString();
        Map<String,String> map = new HashMap<>();
        map.put("command", "newDiaryHandWrite");
        map.put("uid", sqlReturn.GetUserID);
        map.put("diaryContent",handWriteContext);
        map.put("diaryTag","手寫日記");
        map.put("diaryDate",currentDate);
        map.put("diaryMood","手寫日記心情");
        map.put("diaryOptionClass","手寫日記選項");
        new DiaryInsert(this).execute((HashMap)map);
    }

    private class DiaryInsert extends HttpURLConnection_AsyncTask {

        // 建立弱連結
        WeakReference<Activity> activityReference;
        DiaryInsert(Activity context){
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
                Toast.makeText(activity, "日記新增成功", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(HandwriteActivity.this, MainActivity.class);
                intent.putExtra("id",1);
                startActivity(intent);
                txtHandWrite.setText("");
                progressBarHandWrite.setVisibility(View.INVISIBLE);
            }else {
                new AlertDialog.Builder(activity)
                        .setTitle("伺服器擁擠中")
                        .setMessage("請重複點選結束按鈕!!")
                        .setPositiveButton("OK", null)
                        .show();
                progressBarHandWrite.setVisibility(View.INVISIBLE);
            }

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //當使用者按下確定後
        if (resultCode == RESULT_OK) {
            //取得圖檔的路徑位置
            Uri uri = data.getData();
            ContentResolver cr = this.getContentResolver();
            try
            {
                //讀取照片，型態為Bitmap
                Bitmap bitmap = BitmapFactory.decodeStream(cr.openInputStream(uri));

                //判斷照片為橫向或者為直向，並進入ScalePic判斷圖片是否要進行縮放
                if(bitmap.getWidth()>bitmap.getHeight())ScalePic(bitmap,mPhone.heightPixels);
                else ScalePic(bitmap,mPhone.widthPixels);
            }
            catch (FileNotFoundException e)
            {
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void ScalePic(Bitmap bitmap,int phone)
    {
        //縮放比例預設為1
        float mScale = 1 ;

        //如果圖片寬度大於手機寬度則進行縮放，否則直接將圖片放入ImageView內
        if(bitmap.getWidth() > phone )
        {
            //判斷縮放比例
            mScale = (float)phone/(float)bitmap.getWidth();

            Matrix mMat = new Matrix() ;
            mMat.setScale(mScale, mScale);

            Bitmap mScaleBitmap = Bitmap.createBitmap(bitmap,0,0,bitmap.getWidth(),bitmap.getHeight(),mMat,false);
            imageGetPhoto.setImageBitmap(mScaleBitmap);
        }
        else imageGetPhoto.setImageBitmap(bitmap);
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
