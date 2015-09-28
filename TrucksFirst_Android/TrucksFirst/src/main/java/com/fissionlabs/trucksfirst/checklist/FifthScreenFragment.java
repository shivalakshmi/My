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

import java.util.Timer;
import java.util.TimerTask;

/**
 * @author ashok on 9/23/15.
 */
public class FifthScreenFragment extends CheckListCommonFragment {


    private Button btnNext;
    private TextView mTvTime;
    private int count = 10;
    private boolean timeOver;
    private int timeTaken = 0;
    private RadioGroup radio_group_jack, radio_group_jackrod,radio_group_tyreever,radio_group_stepenyRemover;
    private NewChecklist newChecklist = FirstScreenFragment.newChecklist;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_fifth_screen_checklist, container, false);
        btnNext = (Button) view.findViewById(R.id.btnNext);

        mTvTime = (TextView) view.findViewById(R.id.tvTime);
        TextView tvPageNumber = (TextView) view.findViewById(R.id.tvPageNumber);
        radio_group_jack = (RadioGroup)view.findViewById(R.id.radio_group_jack);
        radio_group_jackrod = (RadioGroup)view.findViewById(R.id.radio_group_jackrod);
        radio_group_tyreever = (RadioGroup)view.findViewById(R.id.radio_group_tyreever);
        radio_group_stepenyRemover = (RadioGroup)view.findViewById(R.id.radio_group_stepenyRemover);
        tvPageNumber.setText(String.format(getResources().getString(R.string.page_number), 5, 11));
        mTvTime.setText(String.format(getResources().getString(R.string.timer), count));

        radio_group_jack.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = (RadioButton) group.findViewById(checkedId);
                if (null != rb && checkedId > -1) {
                    if (rb.getText().toString().equalsIgnoreCase("OK")) {
                        newChecklist.data.screen5.jack = true;
                    } else {
                        newChecklist.data.screen5.jack = false;
                    }
                }

            }
        });

        radio_group_jackrod.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = (RadioButton) group.findViewById(checkedId);
                if (null != rb && checkedId > -1) {
                    if (rb.getText().toString().equalsIgnoreCase("OK")) {
                        newChecklist.data.screen5.jackRod = true;
                    } else {
                        newChecklist.data.screen5.jackRod = false;
                    }
                }

            }
        });

        radio_group_tyreever.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = (RadioButton) group.findViewById(checkedId);
                if (null != rb && checkedId > -1) {
                    if (rb.getText().toString().equalsIgnoreCase("OK")) {
                        newChecklist.data.screen5.jackRod = true;
                    } else {
                        newChecklist.data.screen5.jackRod = false;
                    }
                }

            }
        });

        radio_group_stepenyRemover.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = (RadioButton) group.findViewById(checkedId);
                if (null != rb && checkedId > -1) {
                    if (rb.getText().toString().equalsIgnoreCase("OK")) {
                        newChecklist.data.screen5.jackRod = true;
                    } else {
                        newChecklist.data.screen5.jackRod = false;
                    }
                }

            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                newChecklist.data.screen5.timeTaken = timeTaken;
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
