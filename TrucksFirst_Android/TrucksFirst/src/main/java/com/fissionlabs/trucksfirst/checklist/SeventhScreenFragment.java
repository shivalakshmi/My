package com.fissionlabs.trucksfirst.checklist;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.fissionlabs.trucksfirst.R;
import com.fissionlabs.trucksfirst.model.checklist.NewChecklist;
import com.fissionlabs.trucksfirst.model.checklist.Screen7;

import java.util.Timer;
import java.util.TimerTask;

/**
 * @author ashok on 9/23/15.
 */
public class SeventhScreenFragment extends CheckListCommonFragment {
    private Button btnNext;
    private TextView mTvTime;
    transient private int count = 10;
    private boolean timeOver;
    private int timeTaken = 0;
    private RadioGroup radio_group_anytouchExternaldamage,radio_group_sealIntact,radio_group_stepeney,radio_group_tyreCondition,radio_group_tyrePressure;
    private NewChecklist newChecklist = FirstScreenFragment.newChecklist;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        // CheckListBaseFragment.moveToNext();

        View view = inflater.inflate(R.layout.fragment_seventh_screen_checklist, container, false);
        btnNext = (Button) view.findViewById(R.id.btnNext);


        mTvTime = (TextView) view.findViewById(R.id.tvTime);
        TextView tvPageNumber = (TextView) view.findViewById(R.id.tvPageNumber);

     ;

        radio_group_anytouchExternaldamage =(RadioGroup)view.findViewById(R.id.radio_group_anytouchExternaldamage);
        if (newChecklist.data.screen7.touchingDamage) radio_group_anytouchExternaldamage.check(R.id.cross);
        radio_group_anytouchExternaldamage.check(R.id.tick);
        radio_group_anytouchExternaldamage.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId > -1 && checkedId == R.id.cross)
                    newChecklist.data.screen7.touchingDamage = true;
                else if (checkedId > -1 && checkedId == R.id.tick)
                    newChecklist.data.screen7.touchingDamage = false;
            }
        });

        radio_group_sealIntact =(RadioGroup)view.findViewById(R.id.radio_group_sealIntact);
        if (newChecklist.data.screen7.sealIntactness) radio_group_sealIntact.check(R.id.rdWipertick);
        else radio_group_sealIntact.check(R.id.rdWipercross);
        radio_group_sealIntact.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId > -1 && checkedId == R.id.rdWipertick) newChecklist.data.screen7.sealIntactness = true;
                else if (checkedId > -1 && checkedId == R.id.rdWipercross) newChecklist.data.screen7.sealIntactness = false;
            }
        });

        radio_group_stepeney =(RadioGroup)view.findViewById(R.id.radio_group_stepeney);
        if (newChecklist.data.screen7.stepeneyInPlace) radio_group_stepeney.check(R.id.rdHeadLighttick);
        else radio_group_stepeney.check(R.id.rdHeadlightcross);
        radio_group_stepeney.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId > -1 && checkedId == R.id.rdHeadLighttick) newChecklist.data.screen7.stepeneyInPlace = true;
                else if (checkedId > -1 && checkedId == R.id.rdHeadlightcross) newChecklist.data.screen7.stepeneyInPlace = false;
            }
        });

        radio_group_tyreCondition =(RadioGroup)view.findViewById(R.id.radio_group_tyreCondition);
        if (newChecklist.data.screen7.tyrePuncture) radio_group_tyreCondition.check(R.id.rdRHeadlightcross);
        else radio_group_tyreCondition.check(R.id.rdRHeadLight);
        radio_group_tyreCondition.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId > -1 && checkedId == R.id.rdRHeadlightcross) newChecklist.data.screen7.tyrePuncture = true;
                else if (checkedId > -1 && checkedId == R.id.rdRHeadLight) newChecklist.data.screen7.tyrePuncture = false;
            }
        });

        radio_group_tyrePressure = (RadioGroup)view.findViewById(R.id.radio_group_tyrePressure);
        if (newChecklist.data.screen7.tyrePressure) radio_group_tyrePressure.check(R.id.rdLslidetick);
        else radio_group_tyrePressure.check(R.id.rdLslidecross);
        radio_group_tyrePressure.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId > -1 && checkedId == R.id.rdLslidetick) newChecklist.data.screen7.tyrePressure = true;
                else if (checkedId > -1 && checkedId == R.id.rdLslidecross) newChecklist.data.screen7.tyrePressure = false;
            }
        });

        tvPageNumber.setText(String.format(getResources().getString(R.string.page_number), 7, 11));
        mTvTime.setText(String.format(getResources().getString(R.string.timer), count));

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newChecklist.data.screen7.timeTaken = 10-count;
                checklist.data.screen7 = newChecklist.data.screen7;
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
