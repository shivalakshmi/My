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
import com.fissionlabs.trucksfirst.model.checklist.NewChecklist;

import java.util.Timer;
import java.util.TimerTask;

/**
 * @author ashok on 9/23/15.
 */
public class EleventhScreenFragment extends CheckListCommonFragment {

    private Button btnNext;
    private TextView mTvTime;
    private int count = 10;
    private boolean timeOver;
    private int timeTaken = 0;
    private RadioGroup radioBtnGroup_driver_onboard_ok,radioBtnGroup_truck_ready;

    private NewChecklist newChecklist = FirstScreenFragment.newChecklist;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        // CheckListBaseFragment.moveToNext();

        View view = inflater.inflate(R.layout.fragment_elewenth_screen_checklist, container, false);
        btnNext = (Button) view.findViewById(R.id.btnNext);

        mTvTime = (TextView) view.findViewById(R.id.tvTime);
        TextView tvPageNumber = (TextView) view.findViewById(R.id.tvPageNumber);
        radioBtnGroup_driver_onboard_ok = (RadioGroup)view.findViewById(R.id.radioBtnGroup_truck_ready);
        radioBtnGroup_truck_ready = (RadioGroup)view.findViewById(R.id.radioBtnGroup_truck_ready);

        if(newChecklist.data.screen10.driverOnBoarded)
            radioBtnGroup_driver_onboard_ok.check(R.id.radioBtn_driver_onboard_ok);
        else
            radioBtnGroup_driver_onboard_ok.check(R.id.radioBtn_driver_onboard_notok);

        radioBtnGroup_driver_onboard_ok.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId > -1 && checkedId == R.id.radioBtn_driver_onboard_ok)
                    newChecklist.data.screen10.driverOnBoarded = true;
                else if (checkedId > -1 && checkedId == R.id.radioBtn_driver_onboard_notok)
                    newChecklist.data.screen10.driverOnBoarded = false;
            }
        });

        if(newChecklist.data.screen10.readyToMove)
            radioBtnGroup_truck_ready.check(R.id.radioBtn_truck_ready_ok);
        else
            radioBtnGroup_truck_ready.check(R.id.radioBtn_truck_ready_notok);

        radioBtnGroup_truck_ready.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId > -1 && checkedId == R.id.radioBtn_truck_ready_ok)
                    newChecklist.data.screen10.readyToMove = true;
                else if (checkedId > -1 && checkedId == R.id.radioBtn_truck_ready_notok)
                    newChecklist.data.screen10.readyToMove = false;
            }
        });



        tvPageNumber.setText(String.format(getResources().getString(R.string.page_number), 11, 11));
        mTvTime.setText(String.format(getResources().getString(R.string.timer), count));


        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                newChecklist.data.screen10.timeTaken = timeTaken;
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
