package com.fissionlabs.trucksfirst.checklist;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import com.fissionlabs.trucksfirst.R;

public class SecondScreenFragment extends CheckListCommonFragment {

    private Button btnNext;
    private Spinner sp_mechanical_issues, sp_tyre_oil_issues, sp_electrical_issues, sp_engine_issues;


    private RadioGroup radioGroup;
    private RelativeLayout relative_dropdown_layout;
    private final  String[]  mechanical_issues_array = {
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
    private View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        // CheckListBaseFragment.moveToNext();
        view = inflater.inflate(R.layout.fragment_second_screen_checklist, container, false);
        btnNext = (Button) view.findViewById(R.id.btnNext);

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                CheckListBaseFragment.moveToNext();
            }
        });

        initUi();

         /* Attach CheckedChangeListener to radio group */
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
        sp_mechanical_issues.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                int position = sp_mechanical_issues.getSelectedItemPosition();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        sp_engine_issues.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                int position = sp_engine_issues.getSelectedItemPosition();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        sp_tyre_oil_issues.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                int position = sp_tyre_oil_issues.getSelectedItemPosition();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        sp_electrical_issues.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                int position = sp_electrical_issues.getSelectedItemPosition();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        settingAdapter();

        return view;
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

        relative_dropdown_layout = (RelativeLayout)view.findViewById(R.id.relative_dropdown_layout);
        sp_mechanical_issues = (Spinner) view.findViewById(R.id.sp_mechnical_clutch);
        sp_tyre_oil_issues = (Spinner) view.findViewById(R.id.sp_tyre);
        sp_electrical_issues = (Spinner) view.findViewById(R.id.sp_electrical);
        sp_engine_issues = (Spinner) view.findViewById(R.id.sp_engine);
        radioGroup = (RadioGroup) view.findViewById(R.id.radio_group);


    }

}
