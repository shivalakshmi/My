package com.fissionlabs.trucksfirst.checklist;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.fissionlabs.trucksfirst.R;
import com.fissionlabs.trucksfirst.model.checklist.NewChecklist;

import java.util.Arrays;
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
    private int timeTaken = 0;
    private boolean timeOver;
    private NewChecklist newChecklist = FirstScreenFragment.newChecklist;
    private RadioGroup radio_group_wiperL, radio_group_wiperR, radio_group_headlightL, radio_group_headlightR, radio_group_side_indicatorL, radio_group_side_indicatorR, radio_group_brack_light, radio_group_horn;
    private CheckBox mechanicalClutch, tyreOilCondition, electricalPanelBoardLights, engineAbnormalSound, engineGearOperation,
            tyreOilStepeney, mechanicalAccelerator, engineSteeringRelated, mechanicalBodyTollRelated, engineHeatingRelated, mechanicalBrake;

    private String[] mechanicalResult = new String[4];
    private String[] tyreeOilResult = new String[2];
    private String[] electricalResult = new String[1];
    private String[] engineSoundResult = new String[4];

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_second_screen_checklist, container, false);
        Button next = (Button) view.findViewById(R.id.btnNext);

        mTvTime = (TextView) view.findViewById(R.id.tvTime);
        TextView tvPageNumber = (TextView) view.findViewById(R.id.tvPageNumber);

        mechanicalClutch = (CheckBox) view.findViewById(R.id.chkBoxClutch);
        mechanicalBrake = (CheckBox) view.findViewById(R.id.chkBoxBrake);
        tyreOilCondition = (CheckBox) view.findViewById(R.id.chkBoxTyre_condition);
        electricalPanelBoardLights = (CheckBox) view.findViewById(R.id.chkBoxpanelboard_lights);
        engineAbnormalSound = (CheckBox) view.findViewById(R.id.chkBoxabnormal_sound);
        engineGearOperation = (CheckBox) view.findViewById(R.id.chkBoxgear_operation);
        tyreOilStepeney = (CheckBox) view.findViewById(R.id.chkBoxStepeney);
        mechanicalAccelerator = (CheckBox) view.findViewById(R.id.chkBoxAccelerator);
        engineSteeringRelated = (CheckBox) view.findViewById(R.id.chkBoxsteering_related);
        mechanicalBodyTollRelated = (CheckBox) view.findViewById(R.id.chkBoxBodytollRelated);
        engineHeatingRelated = (CheckBox) view.findViewById(R.id.chkBoxengine_heat_related);
        electricalPanelBoardLights = (CheckBox) view.findViewById(R.id.chkBoxpanelboard_lights);

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


        mechanicalClutch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mechanicalResult[0] = mechanicalClutch.getText().toString();
                } else {
                    if (mechanicalResult[0].equals("Clutch"))
                        mechanicalResult[0] = null;

                }

            }
        });

        mechanicalBrake.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mechanicalResult[1] = mechanicalBrake.getText().toString();
                } else {
                    if (mechanicalResult[1].equals("Brake"))
                        mechanicalResult[1] = null;

                }


            }
        });

        mechanicalAccelerator.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mechanicalResult[2] = mechanicalAccelerator.getText().toString();
                } else {
                    if (mechanicalResult[2].equals("Accelarator"))
                        mechanicalResult[2] = null;

                }

            }
        });

        mechanicalBodyTollRelated.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mechanicalResult[3] = mechanicalBodyTollRelated.getText().toString();
                } else {
                    if (mechanicalResult[3].equals("Body Tool related"))
                        mechanicalResult[3] = null;

                }
            }
        });

        tyreOilCondition.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    tyreeOilResult[0] = tyreOilCondition.getText().toString();
                } else {
                    if (tyreeOilResult[0].equals("Tyre condition"))
                        tyreeOilResult[0] = null;

                }

            }
        });

        tyreOilStepeney.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    tyreeOilResult[1] = tyreOilStepeney.getText().toString();
                } else {
                    if (tyreeOilResult[1].equals("Stepeney condition"))
                        tyreeOilResult[1] = null;

                }

            }
        });

        electricalPanelBoardLights.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    electricalResult[0] = electricalPanelBoardLights.getText().toString();
                } else {
                    if (electricalResult[0].equals("Panel board lights"))
                        electricalResult[0] = null;

                }

            }
        });

        engineAbnormalSound.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    engineSoundResult[0] = engineAbnormalSound.getText().toString();
                } else {
                    if (engineSoundResult[0].equals("Abnormal Sound"))
                        engineSoundResult[0] = null;

                }
            }
        });

        engineGearOperation.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    engineSoundResult[1] = engineGearOperation.getText().toString();
                } else {
                    if (engineSoundResult[1].equals("Gear operation"))
                        engineSoundResult[1] = null;

                }
            }
        });

        engineSteeringRelated.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    engineSoundResult[2] = engineSteeringRelated.getText().toString();
                } else {
                    if (engineSoundResult[2].equals("Steering related"))
                        engineSoundResult[2] = null;

                }
            }
        });

        engineHeatingRelated.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    engineSoundResult[3] = engineHeatingRelated.getText().toString();
                } else {
                    if (engineSoundResult[3].equals("Engine heatingrelated"))
                        engineSoundResult[3] = null;

                }

            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (mechanicalClutch.isChecked() || mechanicalBrake.isChecked() || mechanicalAccelerator.isChecked() || mechanicalBodyTollRelated.isChecked())
                    newChecklist.data.screen2.mechanicalIssue = true;
                else
                    newChecklist.data.screen2.mechanicalIssue = false;

                if (tyreOilCondition.isChecked() || tyreOilStepeney.isChecked())
                    newChecklist.data.screen2.tyreOilIssue = true;
                else
                    newChecklist.data.screen2.tyreOilIssue = false;

                if (electricalPanelBoardLights.isChecked())
                    newChecklist.data.screen2.electricalIssue = true;
                else
                    newChecklist.data.screen2.electricalIssue = false;

                if (engineAbnormalSound.isChecked() || engineGearOperation.isChecked() || engineHeatingRelated.isChecked() || engineSteeringRelated.isChecked())
                    newChecklist.data.screen2.engineIssue = true;
                else
                    newChecklist.data.screen2.engineIssue = false;

                Toast.makeText(getActivity(),""+ Arrays.toString(mechanicalResult),Toast.LENGTH_LONG).show();


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
