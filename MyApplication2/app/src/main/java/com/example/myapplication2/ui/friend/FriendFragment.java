package com.example.myapplication2.ui.friend;

import android.animation.ValueAnimator;
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
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication2.Diary.DiaryActivity;
import com.example.myapplication2.MainActivity;
import com.example.myapplication2.R;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;

public class FriendFragment extends Fragment {

    private ConstraintLayout mLayout;
    private Button mBtnChange;
    private Button mGoToHandWrite;
    private Button mGoToDiary;
    private Button mGoToOCR;
    private boolean changeBtn = false;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private LinkedList<HashMap<String,String>> data;
    private MyAdapter myAdapter;



    public static int Tag;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_friend, container, false);

        final MainActivity mainActivity = (MainActivity) getActivity();
        mGoToHandWrite = mainActivity.findViewById(R.id.goToHandwritebutton);
        mGoToDiary = mainActivity.findViewById(R.id.goToDiarybutton);
        mGoToOCR = mainActivity.findViewById(R.id.goToOCRbutton);
        mLayout = mainActivity.findViewById(R.id.testConstraint);
        mLayout.setBackgroundColor(0xFFFFFFFF);
        mLayout.setVisibility(View.INVISIBLE);

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

        // 這裡是 adapter
        mRecyclerView = root.findViewById(R.id.RecyclerView_1);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(FriendFragment.super.getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        doData();
        myAdapter = new MyAdapter();
        mRecyclerView.setAdapter(myAdapter);





        return root;
    }

    private void doData(){

        String[] a = {"陳詩庭","陳昱","楊景婷","王振宇","藍允謙"};
        String[] b = {"2020/05/01","2020/05/02","2020/05/03","2020/05/04","2020/05/05"};
        String[] c = {"食","購物","旅遊","感情","休閒娛樂"};
        data = new LinkedList<>();
        for(int i = 0; i < 5; i++){
            HashMap<String,String> row = new HashMap<>();
            row.put("place_text",a[i]);
            row.put("place_description_text",b[i]);
            row.put("tag_text",c[i]);
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
                        Tag = getAdapterPosition();
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
            holder.photo_image.setImageResource(R.drawable.images);
        }

        @Override
        public int getItemCount() {
            return data.size();
        }
    }


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
