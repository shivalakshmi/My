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
import com.fissionlabs.trucksfirst.model.checklist.Screen9;

import java.util.Timer;
import java.util.TimerTask;

/**
 * @author ashok on 9/23/15.
 */
public class NinthScreenFragment extends CheckListCommonFragment {

    private Button btnNext;
    private TextView mTvTime;
    transient private int count = 10;
    private boolean timeOver;
    private int timeTaken = 0;

    private RadioGroup radio_group_tempcheck,radio_group_leakageOil,radio_group_leakageSteeringOil,radio_group_leakageDieselTank,radio_group_coolantLevel,radio_group_visualInspection;

    private NewChecklist newChecklist = FirstScreenFragment.newChecklist;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_ninth_screen_checklist, container, false);
        btnNext = (Button) view.findViewById(R.id.btnNext);

        mTvTime = (TextView) view.findViewById(R.id.tvTime);
        TextView tvPageNumber = (TextView) view.findViewById(R.id.tvPageNumber);

        radio_group_tempcheck = (RadioGroup)view.findViewById(R.id.radio_group_tempcheck);
        radio_group_leakageOil = (RadioGroup)view.findViewById(R.id.radio_group_leakageOil);
        radio_group_leakageSteeringOil = (RadioGroup)view.findViewById(R.id.radio_group_leakageSteeringOil);
        radio_group_leakageDieselTank = (RadioGroup)view.findViewById(R.id.radio_group_leakageDieselTank);
        radio_group_coolantLevel = (RadioGroup)view.findViewById(R.id.radio_group_collantLevel);



        if(newChecklist.data.screen9.temperatureCheck)
            radio_group_tempcheck.check(R.id.rdHeadLighttick);
        else
            radio_group_tempcheck.check(R.id.rdHeadlightcross);

        radio_group_tempcheck.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId > -1 && checkedId == R.id.rdHeadLighttick)
                    newChecklist.data.screen9.temperatureCheck = true;
                else if (checkedId > -1 && checkedId == R.id.rdHeadlightcross)
                    newChecklist.data.screen9.temperatureCheck = false;
            }
        });

        if(newChecklist.data.screen9.engineOilLeakage) radio_group_leakageOil.check(R.id.cross);
        else radio_group_leakageOil.check(R.id.tick);
        radio_group_leakageOil.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId > -1 && checkedId == R.id.cross) newChecklist.data.screen9.engineOilLeakage = true;
                else if (checkedId > -1 && checkedId == R.id.tick) newChecklist.data.screen9.engineOilLeakage = false;
            }
        });

        if(newChecklist.data.screen9.steeringOilLeakage) radio_group_leakageSteeringOil.check(R.id.rdWipercross);
        else radio_group_leakageSteeringOil.check(R.id.rdWipertick);
        radio_group_leakageSteeringOil.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId > -1 && checkedId == R.id.rdWipercross) newChecklist.data.screen9.steeringOilLeakage = true;
                else if (checkedId > -1 && checkedId == R.id.rdWipertick) newChecklist.data.screen9.steeringOilLeakage = false;
            }
        });

        if(newChecklist.data.screen9.fuelDieselLeakage) radio_group_leakageDieselTank.check(R.id.rdRHeadlightcross);
        else radio_group_leakageDieselTank.check(R.id.rdRHeadLight);
        radio_group_leakageDieselTank.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId > -1 && checkedId == R.id.rdRHeadlightcross) newChecklist.data.screen9.fuelDieselLeakage = true;
                else if (checkedId > -1 && checkedId == R.id.rdRHeadLight) newChecklist.data.screen9.fuelDieselLeakage = false;
            }
        });

        if(newChecklist.data.screen9.coolantLevel) radio_group_coolantLevel.check(R.id.rdLslidetick);
        else radio_group_coolantLevel.check(R.id.rdLslidecross);
        radio_group_coolantLevel.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId > -1 && checkedId == R.id.rdLslidetick) newChecklist.data.screen9.coolantLevel = true;
                else if (checkedId > -1 && checkedId == R.id.rdLslidecross) newChecklist.data.screen9.coolantLevel = false;
            }
        });

        tvPageNumber.setText(String.format(getResources().getString(R.string.page_number), 9, 11));
        mTvTime.setText(String.format(getResources().getString(R.string.timer), count));


        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newChecklist.data.screen9.timeTaken = timeTaken;
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
