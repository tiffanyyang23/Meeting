package com.example.myapplication2.ui.dashboard;

import android.animation.ValueAnimator;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.myapplication2.MainActivity;
import com.example.myapplication2.R;

import static com.example.myapplication2.R.layout.fragment_dashboard;

public class DashboardFragment extends Fragment {

    private ConstraintLayout mLayout;
    private Button mBtnChange;
    private Button mGoToHandWrite;
    private Button mGoToDiary;
    private Button mGoToOCR;
    private boolean changeBtn = false;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(fragment_dashboard, container, false);

        MainActivity mainActivity = (MainActivity) getActivity();
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

        mBtnChange = root.findViewById(R.id.btnChange2);
        mBtnChange.setOnClickListener(btnChangeColorOnClick);

        return root;
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
