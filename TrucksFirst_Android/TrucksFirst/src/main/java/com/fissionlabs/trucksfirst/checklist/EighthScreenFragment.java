package com.fissionlabs.trucksfirst.checklist;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.fissionlabs.trucksfirst.R;
import com.fissionlabs.trucksfirst.model.checklist.Screen8;

import java.util.Timer;
import java.util.TimerTask;

/**
 * @author ashok on 9/23/15.
 */
public class EighthScreenFragment extends CheckListCommonFragment {
    Screen8 s8;
    private Button btnNext;
    private TextView mTvTime;
    transient private int count = 10;
    private boolean timeOver;
    private int timeTaken = 0;
    private RadioGroup radioBtnGroup_cabincleanCondition;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        // CheckListBaseFragment.moveToNext();

        View view = inflater.inflate(R.layout.fragment_eight_screen_checklist, container, false);
        btnNext = (Button) view.findViewById(R.id.btnNext);

        s8 = checklist.data.screen8;
        if (s8 == null) s8 = new Screen8();

        radioBtnGroup_cabincleanCondition = (RadioGroup)view.findViewById(R.id.radioBtnGroup_cabincleanCondition);
        if(s8.isCabinClean){
            radioBtnGroup_cabincleanCondition.check(R.id.radioBtn_driver_onboard_ok);
        } else{
            radioBtnGroup_cabincleanCondition.check(R.id.radioBtn_driver_onboard_notok);
        }
        radioBtnGroup_cabincleanCondition.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                if (checkedId > -1 && checkedId == R.id.radioBtn_driver_onboard_ok) {
                    s8.isCabinClean = true;
                } else if (checkedId > -1 && checkedId == R.id.radioBtn_driver_onboard_notok) {
                    {
                        s8.isCabinClean = false;
                    }
                }
            }
        });

        mTvTime = (TextView) view.findViewById(R.id.tvTime);
        TextView tvPageNumber = (TextView) view.findViewById(R.id.tvPageNumber);

        tvPageNumber.setText(String.format(getResources().getString(R.string.page_number), 8, 11));
        mTvTime.setText(String.format(getResources().getString(R.string.timer), count));


        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                s8.timeTaken = 10 - count;
                checklist.data.screen8 = s8;
                CheckListBaseFragment.moveToNext();
            }
        });


        return view;
    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if (isVisibleToUser) {
            startTimer();
        }
    }

    private void startTimer() {
        Timer T = new Timer();
        T.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {

                if (mHomeActivity == null) {
                    return;
                }

                mHomeActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        if (count == 0) {
                            timeOver = true;
                        }

                        if (timeOver) {
                            mTvTime.setTextColor(mHomeActivity.getResources().getColor(R.color.red));
                            mTvTime.setText(String.format(mHomeActivity.getResources().getString(R.string.timer), count));
                            count++;
                        } else {
                            mTvTime.setTextColor(mHomeActivity.getResources().getColor(R.color.black));
                            mTvTime.setText(String.format(mHomeActivity.getResources().getString(R.string.timer), count));
                            count--;
                        }

                        timeTaken++;
                    }
                });
            }
        }, 20, 1000);
    }
}
