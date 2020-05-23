package com.example.myapplication2.ui.friend;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.myapplication2.Diary.DiaryActivity;
import com.example.myapplication2.HttpURLConnection_AsyncTask;
import com.example.myapplication2.Login.LoginActivity;
import com.example.myapplication2.MainActivity;
import com.example.myapplication2.R;
import com.example.myapplication2.sqlReturn;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;

public class FriendFragment extends Fragment {

    private ConstraintLayout mLayout;
    private Button mBtnChange, mGoToHandWrite, mGoToDiary, mGoToOCR;
    private boolean changeBtn = false;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private LinkedList<HashMap<String,String>> data;
    private MyAdapter myAdapter;
    private ProgressBar progressBarFriend;
    private SwipeRefreshLayout RefreshLayoutFriend;

    public static int FriendTag;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_friend, container, false);

        final MainActivity mainActivity = (MainActivity) getActivity();
        mGoToHandWrite = mainActivity.findViewById(R.id.goToHandwritebutton);
        mGoToDiary = mainActivity.findViewById(R.id.goToDiarybutton);
        mGoToOCR = mainActivity.findViewById(R.id.goToOCRbutton);
        progressBarFriend = root.findViewById(R.id.progressBarFriend);
        mLayout = mainActivity.findViewById(R.id.testConstraint);
        mLayout.setBackgroundColor(0xFFFFFFFF);
        mLayout.setVisibility(View.INVISIBLE);

        searchFriendList();

        RefreshLayoutFriend = root.findViewById(R.id.RefreshLayoutFriend);
        RefreshLayoutFriend.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                searchFriend();
            }
        });

        final ImageButton imBtnSearchFriend = root.findViewById(R.id.imBtnSearchFriend);
        imBtnSearchFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FriendFragment.super.getActivity(),FriendListActivity.class);
                startActivity(intent);
            }
        });

        if(mGoToHandWrite.isEnabled()){
            mGoToHandWrite.setEnabled(false);
            mGoToHandWrite.setVisibility(View.INVISIBLE);
        }
        if(mGoToDiary.isEnabled()){
            mGoToDiary.setEnabled(false);
            mGoToDiary.setVisibility(View.INVISIBLE);
        }
        if(mGoToOCR.isEnabled()){
            mGoToOCR.setEnabled(false);
            mGoToOCR.setVisibility(View.INVISIBLE);
        }

        mBtnChange = root.findViewById(R.id.btnChange3);
        mBtnChange.setOnClickListener(btnChangeColorOnClick);
        mRecyclerView = root.findViewById(R.id.RecyclerView_1);
        progressBarFriend.setVisibility(View.VISIBLE);
        searchFriend();


        return root;
    }

    private void doData(){

        data = new LinkedList<>();
        for(int i = 0; i < sqlReturn.SearchCountFriend; i++){
            HashMap<String,String> row = new HashMap<>();
            row.put("place_text",sqlReturn.friendName[i]);
            row.put("place_description_text",sqlReturn.dateFriend[i]);
            row.put("tag_text",sqlReturn.tagNameFriend[i]);
            data.add(row);
        }
    }

    private class  MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

        class MyViewHolder extends RecyclerView.ViewHolder{
            public View itemView;
            public TextView place_text ,place_description_text,tag_text;
            public ImageView photo_image;
            public MyViewHolder(View view){
                super(view);
                itemView = view;
                place_text = itemView.findViewById(R.id.place_text);
                place_description_text = itemView.findViewById(R.id.place_description_text);
                tag_text = itemView.findViewById(R.id.tag_text);
                photo_image = itemView.findViewById(R.id.photo_image);
                itemView.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View view) {
                        FriendTag = getAdapterPosition();
                        Intent intent = new Intent(FriendFragment.super.getActivity(),SocialArticalActivity.class);
                        startActivity(intent);
                    }
                });
            }
        }

        @NonNull
        @Override
        public MyAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item,parent,false);
            MyViewHolder vh = new MyViewHolder(itemView);
            return vh;
        }

        @Override
        public void onBindViewHolder(@NonNull MyAdapter.MyViewHolder holder, int position) {
            holder.place_text.setText(data.get(position).get("place_text"));
            holder.place_description_text.setText(data.get(position).get("place_description_text"));
            holder.tag_text.setText(data.get(position).get("tag_text"));
            if(position % 2 == 0){
                holder.photo_image.setImageResource(R.drawable.images);
            }else {
                holder.photo_image.setImageResource(R.drawable.image2);
            }

        }

        @Override
        public int getItemCount() {
            return data.size();
        }
    }

    // 此為社群好友貼文全抓
    public void searchFriend(){
        String uid = sqlReturn.GetUserID;
        Map<String,String> map = new HashMap<>();
        map.put("command", "friendList");
        map.put("uid", uid);
        new searchFriend(super.getActivity()).execute((HashMap)map);
    }
    private class searchFriend extends HttpURLConnection_AsyncTask {

        // 建立弱連結
        WeakReference<Activity> activityReference;
        searchFriend(Activity context){
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

                sqlReturn.textViewContextFriend = jsonObject.getString("results");
                sqlReturn.SearchCountFriend = jsonObject.getInt("rowcount");
                jsonArray = new JSONArray(sqlReturn.textViewContextFriend);
                sqlReturn.contentFriend = new String[sqlReturn.SearchCountFriend];
                sqlReturn.tagNameFriend = new String[sqlReturn.SearchCountFriend];
                sqlReturn.moodFriend = new String[sqlReturn.SearchCountFriend];
                sqlReturn.dateFriend = new String[sqlReturn.SearchCountFriend];
                sqlReturn.friendName = new String[sqlReturn.SearchCountFriend];
                for(int i = 0; i<sqlReturn.SearchCountFriend; i++){
                    JSONObject obj = new JSONObject(String.valueOf(jsonArray.get(i)));
                    sqlReturn.contentFriend[i] = obj.getString("content");
                    sqlReturn.tagNameFriend[i] = obj.getString("tagName");
                    sqlReturn.moodFriend[i] = obj.getString("mood");
                    sqlReturn.dateFriend[i] = obj.getString("date");
                    sqlReturn.friendName[i] = obj.getString("friendName01");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (sqlReturn.textViewContextFriend!=null){
                //Toast.makeText(activity, String.valueOf(sqlReturn.SearchCountMood), Toast.LENGTH_LONG).show();
                doData();
                // 這裡是 adapter
                mRecyclerView.setHasFixedSize(true);
                mLayoutManager = new LinearLayoutManager(FriendFragment.super.getActivity());
                mRecyclerView.setLayoutManager(mLayoutManager);
                myAdapter = new MyAdapter();
                mRecyclerView.setAdapter(myAdapter);
                progressBarFriend.setVisibility(View.INVISIBLE);
                RefreshLayoutFriend.setRefreshing(false);
            }else {
                progressBarFriend.setVisibility(View.INVISIBLE);
//                new AlertDialog.Builder(activity)
//                        .setTitle("日記載入失敗")
//                        .setMessage("請確認網路是否連通!!")
//                        .setPositiveButton("OK", null)
//                        .show();
            }
        }

    }

    public void searchFriendList(){
        String uid = sqlReturn.GetUserID;
        Map<String,String> map = new HashMap<>();
        map.put("command", "friendInfoList");
        map.put("uid", uid);
        new searchFriendList(super.getActivity()).execute((HashMap)map);
    }

    private class searchFriendList extends HttpURLConnection_AsyncTask {

        // 建立弱連結
        WeakReference<Activity> activityReference;
        searchFriendList(Activity context){
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

                sqlReturn.textViewContextFriendList = jsonObject.getString("results");
                sqlReturn.SearchCountFriendList = jsonObject.getInt("rowcount");
                jsonArray = new JSONArray(sqlReturn.textViewContextFriendList);
                sqlReturn.friendListName = new String[sqlReturn.SearchCountFriendList];
                for(int i = 0; i<sqlReturn.SearchCountFriendList; i++){
                    JSONObject obj = new JSONObject(String.valueOf(jsonArray.get(i)));
                    sqlReturn.friendListName[i] = obj.getString("friendName01");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (sqlReturn.textViewContextFriendList!=null){
                //Toast.makeText(activity, String.valueOf(sqlReturn.SearchCountMood), Toast.LENGTH_LONG).show();
            }else {
//                new AlertDialog.Builder(activity)
//                        .setTitle("日記載入失敗")
//                        .setMessage("請確認網路是否連通!!")
//                        .setPositiveButton("OK", null)
//                        .show();
            }
        }
    }

    // 變化背景動畫
    private View.OnClickListener btnChangeColorOnClick = new View.OnClickListener() {
        public void onClick(View v) {
            int iBackColorRedVal, iBackColorRedEnd;
            if(!changeBtn){
                mLayout.setVisibility(View.VISIBLE);
                mGoToHandWrite.setEnabled(true);
                mGoToHandWrite.setVisibility(View.VISIBLE);
                mGoToDiary.setEnabled(true);
                mGoToDiary.setVisibility(View.VISIBLE);
                mGoToOCR.setEnabled(true);
                mGoToOCR.setVisibility(View.VISIBLE);
                changeBtn = true;
            }else if(changeBtn == true){
                mGoToHandWrite.setEnabled(false);
                mGoToHandWrite.setVisibility(View.INVISIBLE);
                mGoToDiary.setEnabled(false);
                mGoToDiary.setVisibility(View.INVISIBLE);
                mGoToOCR.setEnabled(false);
                mGoToOCR.setVisibility(View.INVISIBLE);
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


}
