package com.fissionlabs.trucksfirst.checklist;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TextView;

import com.fissionlabs.trucksfirst.R;

import java.util.Timer;
import java.util.TimerTask;

public class SecondScreenFragment extends CheckListCommonFragment {




    private final String[] mechanical_issues_array = {
            "Clutch",
            "Brake",
            "Accelerator",
            "Body & Tool related",
    };
    private final String[] tyreoil_issues_array = {
            "Tyre condition",
            "Stepeney condition",
    };
    private final String[] electrical_issues_array = {"Panel board lights"
    };
    private final String[] engine_issues_array = {
            "Abnormal sound",
            "Gear operation", "Streering related", "Engine heating"
    };
    private Button btnNext;
    private Spinner sp_mechanical_issues, sp_tyre_oil_issues, sp_electrical_issues, sp_engine_issues;
    private RadioGroup radioGroup;
    private TableLayout relative_dropdown_layout;
    private View view;
    private TextView mTvTime;
    private int count = 10;
    private boolean timeOver;
    private RadioGroup radio_group_wiperL,radio_group_wiperR,radio_group_headlightL,radio_group_headlightR,radio_group_side_indicatorL,radio_group_side_indicatorR,radio_group_brack_light,radio_group_horn;
    private CheckBox chkBoxClutch,chkBoxTyre_condition,chkBoxpanelboard_lights,chkBoxabnormal_sound,chkBoxgear_operation,
                    chkBoxStepeney,chkBoxAccelerator, chkBoxsteering_related,chkBoxBodytollRelated,chkBoxengine_heat_related,chkBoxBrake;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_second_screen_checklist, container, false);
        Button next = (Button) view.findViewById(R.id.btnNext);

        mTvTime = (TextView) view.findViewById(R.id.tvTime);
        TextView tvPageNumber = (TextView) view.findViewById(R.id.tvPageNumber);

        chkBoxClutch = (CheckBox)view.findViewById(R.id.chkBoxClutch);
        chkBoxBrake = (CheckBox)view.findViewById(R.id.chkBoxBrake);
        chkBoxTyre_condition = (CheckBox)view.findViewById(R.id.chkBoxTyre_condition);
        chkBoxpanelboard_lights = (CheckBox)view.findViewById(R.id.chkBoxpanelboard_lights);
        chkBoxabnormal_sound = (CheckBox)view.findViewById(R.id.chkBoxabnormal_sound);
        chkBoxgear_operation = (CheckBox)view.findViewById(R.id.chkBoxgear_operation);
        chkBoxStepeney = (CheckBox)view.findViewById(R.id.chkBoxStepeney);
        chkBoxAccelerator = (CheckBox)view.findViewById(R.id.chkBoxAccelerator);
        chkBoxsteering_related = (CheckBox)view.findViewById(R.id.chkBoxsteering_related);
        chkBoxBodytollRelated = (CheckBox)view.findViewById(R.id.chkBoxBodytollRelated);
        chkBoxengine_heat_related = (CheckBox)view.findViewById(R.id.chkBoxengine_heat_related);
        chkBoxpanelboard_lights = (CheckBox)view.findViewById(R.id.chkBoxpanelboard_lights);

        tvPageNumber.setText(String.format(getResources().getString(R.string.page_number), 2, 11));
        mTvTime.setText(String.format(getResources().getString(R.string.timer), count));


        relative_dropdown_layout = (TableLayout) view.findViewById(R.id.tableLayout);

        initUi();

        //* Attach CheckedChangeListener to radio group *//*
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = (RadioButton) group.findViewById(checkedId);
                if (null != rb && checkedId > -1) {
                    if (rb.getText().toString().equalsIgnoreCase("OK")) {

                        relative_dropdown_layout.setVisibility(View.INVISIBLE);
                    } else {

                        relative_dropdown_layout.setVisibility(View.VISIBLE);
                    }
                }

            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

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
                    }
                });
            }
        }, 20, 1000);
    }

    private void settingAdapter() {

        ArrayAdapter<String> mechanical_adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, mechanical_issues_array);
        ArrayAdapter<String> tyreoil_adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, tyreoil_issues_array);
        ArrayAdapter<String> electrical_adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, electrical_issues_array);
        ArrayAdapter<String> engine_adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, engine_issues_array);

        sp_mechanical_issues.setAdapter(mechanical_adapter);
        sp_tyre_oil_issues.setAdapter(tyreoil_adapter);
        sp_electrical_issues.setAdapter(electrical_adapter);
        sp_engine_issues.setAdapter(engine_adapter);

    }


    private void initUi() {

        //  relative_dropdown_layout = (RelativeLayout)view.findViewById(R.id.relative_dropdown_layout);
       /* sp_mechanical_issues = (Spinner) view.findViewById(R.id.sp_mechnical_clutch);
        sp_tyre_oil_issues = (Spinner) view.findViewById(R.id.sp_tyre);
        sp_electrical_issues = (Spinner) view.findViewById(R.id.sp_electrical);
        sp_engine_issues = (Spinner) view.findViewById(R.id.sp_engine);*/
        radioGroup = (RadioGroup) view.findViewById(R.id.radio_group_wiperL);


    }

}
