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
import com.fissionlabs.trucksfirst.model.checklist.Screen9;

import java.util.Timer;
import java.util.TimerTask;

/**
 * @author ashok on 9/23/15.
 */
public class NinthScreenFragment extends CheckListCommonFragment {

    private Screen9 s9;
    private Button btnNext;
    private TextView mTvTime;
    transient private int count = 10;
    private boolean timeOver;
    private int timeTaken = 0;

    private RadioGroup radio_group_tempcheck,radio_group_leakageOil,radio_group_leakageSteeringOil,radio_group_leakageDieselTank,radio_group_coolantLevel,radio_group_visualInspection;


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

        s9 = checklist.data.screen9;
        if (s9 == null) s9 = new Screen9();

        if(s9.temperatureCheck) radio_group_tempcheck.check(R.id.rdHeadLighttick);
        else radio_group_tempcheck.check(R.id.rdHeadlightcross);
        radio_group_tempcheck.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId > -1 && checkedId == R.id.rdHeadLighttick) s9.temperatureCheck = true;
                else if (checkedId > -1 && checkedId == R.id.rdHeadlightcross) s9.temperatureCheck = false;
            }
        });

        if(s9.engineOilLeakage) radio_group_leakageOil.check(R.id.cross);
        else radio_group_leakageOil.check(R.id.tick);
        radio_group_leakageOil.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId > -1 && checkedId == R.id.cross) s9.engineOilLeakage = true;
                else if (checkedId > -1 && checkedId == R.id.tick) s9.engineOilLeakage = false;
            }
        });

        if(s9.steeringOilLeakage) radio_group_leakageSteeringOil.check(R.id.rdWipercross);
        else radio_group_leakageSteeringOil.check(R.id.rdWipertick);
        radio_group_leakageSteeringOil.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId > -1 && checkedId == R.id.rdWipercross) s9.steeringOilLeakage = true;
                else if (checkedId > -1 && checkedId == R.id.rdWipertick) s9.steeringOilLeakage = false;
            }
        });

        if(s9.fuelDieselLeakage) radio_group_leakageDieselTank.check(R.id.rdRHeadlightcross);
        else radio_group_leakageDieselTank.check(R.id.rdRHeadLight);
        radio_group_leakageDieselTank.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId > -1 && checkedId == R.id.rdRHeadlightcross) s9.fuelDieselLeakage = true;
                else if (checkedId > -1 && checkedId == R.id.rdRHeadLight) s9.fuelDieselLeakage = false;
            }
        });

        if(s9.coolantLevel) radio_group_coolantLevel.check(R.id.rdLslidetick);
        else radio_group_coolantLevel.check(R.id.rdLslidecross);
        radio_group_coolantLevel.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId > -1 && checkedId == R.id.rdLslidetick) s9.coolantLevel = true;
                else if (checkedId > -1 && checkedId == R.id.rdLslidecross) s9.coolantLevel = false;
            }
        });

        tvPageNumber.setText(String.format(getResources().getString(R.string.page_number), 9, 11));
        mTvTime.setText(String.format(getResources().getString(R.string.timer), count));


        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                s9.timeTaken = 10 - count;
                checklist.data.screen9 = s9;
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
