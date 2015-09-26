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
public class ThirdScreenFragment extends CheckListCommonFragment {

    private Button btnNext;
    private TextView mTvTime;
    private int count = 20;
    private boolean timeOver;
    private int timeTaken = 0;
    private RadioGroup radio_group_rc,radioBtnNationalPermit,radioBtnInsurance,radioBtnPollutionCerti,radioBtnDharamKaat,
            radioBtnRoadTax,radioBtnGoodsTax;
    private NewChecklist newChecklist = FirstScreenFragment.newChecklist;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_third_screen_checklist, container, false);
        btnNext = (Button) view.findViewById(R.id.btnNext);

        mTvTime = (TextView) view.findViewById(R.id.tvTime);
        TextView tvPageNumber = (TextView) view.findViewById(R.id.tvPageNumber);


        radio_group_rc = (RadioGroup)view.findViewById(R.id.radio_group_rc);
        radioBtnNationalPermit = (RadioGroup)view.findViewById(R.id.radioBtnNationalPermit);
        radioBtnInsurance = (RadioGroup)view.findViewById(R.id.radioBtnInsurance);
        radioBtnPollutionCerti = (RadioGroup)view.findViewById(R.id.radioBtnPollutionCerti);
        radioBtnDharamKaat = (RadioGroup)view.findViewById(R.id.radioBtnDharamKaat);
        radioBtnRoadTax = (RadioGroup)view.findViewById(R.id.radioBtnRoadTax);
        radioBtnGoodsTax = (RadioGroup)view.findViewById(R.id.radioBtnGoodsTax);

        tvPageNumber.setText(String.format(getResources().getString(R.string.page_number), 3, 11));
        mTvTime.setText(String.format(getResources().getString(R.string.timer), count));

        ((TextView) view.findViewById(R.id.tvScreenName)).setText(getResources().getString(R.string.truck_docs));

        radio_group_rc.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = (RadioButton) group.findViewById(checkedId);
                if (null != rb && checkedId > -1) {
                    if (rb.getText().toString().equalsIgnoreCase("OK")) {
                        newChecklist.data.screen3.registrationCertificate = true;
                    } else {
                        newChecklist.data.screen3.registrationCertificate = false;
                    }
                }

            }
        });

        radioBtnNationalPermit.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = (RadioButton) group.findViewById(checkedId);
                if (null != rb && checkedId > -1) {
                    if (rb.getText().toString().equalsIgnoreCase("OK")) {
                        newChecklist.data.screen3.nationalPermit = true;
                    } else {
                        newChecklist.data.screen3.nationalPermit = false;
                    }
                }

            }
        });

        radioBtnInsurance.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = (RadioButton) group.findViewById(checkedId);
                if (null != rb && checkedId > -1) {
                    if (rb.getText().toString().equalsIgnoreCase("OK")) {
                        newChecklist.data.screen3.insurance = true;
                    } else {
                        newChecklist.data.screen3.insurance = false;
                    }
                }

            }
        });

        radioBtnGoodsTax.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = (RadioButton) group.findViewById(checkedId);
                if (null != rb && checkedId > -1) {
                    if (rb.getText().toString().equalsIgnoreCase("OK")) {
                        newChecklist.data.screen3.goodsTaxRecipiet= true;
                    } else {
                        newChecklist.data.screen3.goodsTaxRecipiet = false;
                    }
                }

            }
        });

        radioBtnRoadTax.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = (RadioButton) group.findViewById(checkedId);
                if (null != rb && checkedId > -1) {
                    if (rb.getText().toString().equalsIgnoreCase("OK")) {
                        newChecklist.data.screen3.roadTaxBooklet = true;
                    } else {
                        newChecklist.data.screen3.roadTaxBooklet = false;
                    }
                }

            }
        });

        radioBtnPollutionCerti.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = (RadioButton) group.findViewById(checkedId);
                if (null != rb && checkedId > -1) {
                    if (rb.getText().toString().equalsIgnoreCase("OK")) {
                        newChecklist.data.screen3.pollutionCertificate = true;
                    } else {
                        newChecklist.data.screen3.pollutionCertificate = false;
                    }
                }

            }
        });

        radioBtnDharamKaat.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = (RadioButton) group.findViewById(checkedId);
                if (null != rb && checkedId > -1) {
                    if (rb.getText().toString().equalsIgnoreCase("OK")) {
                        newChecklist.data.screen3.dharamKaantaParchi = true;
                    } else {
                        newChecklist.data.screen3.dharamKaantaParchi = false;
                    }
                }

            }
        });


        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                newChecklist.data.screen3.timeTaken = timeTaken;
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
