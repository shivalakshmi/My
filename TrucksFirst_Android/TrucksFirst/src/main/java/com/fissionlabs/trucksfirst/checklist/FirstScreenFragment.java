package com.fissionlabs.trucksfirst.checklist;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.fissionlabs.trucksfirst.R;
import com.fissionlabs.trucksfirst.model.checklist.NewChecklist;
import com.fissionlabs.trucksfirst.util.TFUtils;
import com.google.gson.Gson;

import java.util.Timer;
import java.util.TimerTask;


/**
 * @author ashok on 9/23/15.
 */
public class FirstScreenFragment extends CheckListCommonFragment {

    private TextView mTvTime;
    private int count = 10;
    private boolean timeOver;
    private int timeTaken = 0;
    private RadioGroup radio_group_wiperL,radio_group_wiperR,radio_group_headlightL,radio_group_headlightR,radio_group_side_indicatorL,radio_group_side_indicatorR,radio_group_brack_light,radio_group_horn;
    public static NewChecklist newChecklist = new NewChecklist();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_first_screen_checklist, container, false);
        newChecklist.vehicleNumber = TFUtils.getStringFromSP(getActivity(), "vehicle_number");
        newChecklist.trackingId = TFUtils.getStringFromSP(getActivity(),"vehicleTrackingId");
        Button next = (Button) view.findViewById(R.id.btnNext);

        mTvTime = (TextView) view.findViewById(R.id.tvTime);
        TextView tvPageNumber = (TextView) view.findViewById(R.id.tvPageNumber);
        radio_group_wiperL = (RadioGroup)view.findViewById(R.id.radio_group_wiperL);
        radio_group_wiperR = (RadioGroup)view.findViewById(R.id.radio_group_wiperR);
        radio_group_headlightL = (RadioGroup)view.findViewById(R.id.radio_group_headlightL);
        radio_group_headlightR = (RadioGroup)view.findViewById(R.id.radio_group_headlightR);
        radio_group_side_indicatorL = (RadioGroup)view.findViewById(R.id.radio_group_side_indicatorL);
        radio_group_side_indicatorR = (RadioGroup)view.findViewById(R.id.radio_group_side_indicatorR);
        radio_group_brack_light = (RadioGroup)view.findViewById(R.id.radio_group_brack_light);
        radio_group_horn = (RadioGroup)view.findViewById(R.id.radio_group_horn);

        radio_group_wiperL.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = (RadioButton) group.findViewById(checkedId);
                if (null != rb && checkedId > -1) {
                    if (rb.getText().toString().equalsIgnoreCase("OK")) {
                        newChecklist.data.screen1.leftWiper = true;
                    } else {
                        newChecklist.data.screen1.leftWiper = false;
                    }
                }

            }
        });
        radio_group_wiperR.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = (RadioButton) group.findViewById(checkedId);
                if (null != rb && checkedId > -1) {
                    if (rb.getText().toString().equalsIgnoreCase("OK")) {
                        newChecklist.data.screen1.rightWiper = true;
                    } else {
                        newChecklist.data.screen1.rightWiper = false;
                    }
                }

            }
        });
        radio_group_headlightL.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = (RadioButton) group.findViewById(checkedId);
                if (null != rb && checkedId > -1) {
                    if (rb.getText().toString().equalsIgnoreCase("OK")) {
                        newChecklist.data.screen1.lowBeamHeadlight = true;
                    } else {
                        newChecklist.data.screen1.lowBeamHeadlight = false;
                    }
                }

            }
        });
        radio_group_headlightR.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = (RadioButton) group.findViewById(checkedId);
                if (null != rb && checkedId > -1) {
                    if (rb.getText().toString().equalsIgnoreCase("OK")) {
                        newChecklist.data.screen1.highBeamHeadlight = true;
                    } else {
                        newChecklist.data.screen1.highBeamHeadlight = false;
                    }
                }

            }
        });
        radio_group_side_indicatorL.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = (RadioButton) group.findViewById(checkedId);
                if (null != rb && checkedId > -1) {
                    if (rb.getText().toString().equalsIgnoreCase("OK")) {
                        newChecklist.data.screen1.leftSideIndicator = true;
                    } else {
                        newChecklist.data.screen1.leftSideIndicator = false;
                    }
                }

            }
        });
        radio_group_side_indicatorR.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = (RadioButton) group.findViewById(checkedId);
                if (null != rb && checkedId > -1) {
                    if (rb.getText().toString().equalsIgnoreCase("OK")) {
                        newChecklist.data.screen1.rightSideIndicator = true;
                    } else {
                        newChecklist.data.screen1.rightSideIndicator = false;
                    }
                }

            }
        });
        radio_group_brack_light.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = (RadioButton) group.findViewById(checkedId);
                if (null != rb && checkedId > -1) {
                    if (rb.getText().toString().equalsIgnoreCase("OK")) {
                        newChecklist.data.screen1.brakeLight = true;
                    } else {
                        newChecklist.data.screen1.brakeLight = false;
                    }
                }

            }
        });
        radio_group_horn.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = (RadioButton) group.findViewById(checkedId);
                if (null != rb && checkedId > -1) {
                    if (rb.getText().toString().equalsIgnoreCase("OK")) {
                        newChecklist.data.screen1.horn = true;
                    } else {
                        newChecklist.data.screen1.horn = false;
                    }
                }

            }
        });


        tvPageNumber.setText(String.format(getResources().getString(R.string.page_number), 1, 11));
        mTvTime.setText(String.format(getResources().getString(R.string.timer), count));

        startTimer();



        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newChecklist.data.screen1.timeTaken = timeTaken;
                CheckListBaseFragment.moveToNext();
            }
        });
        return view;
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
