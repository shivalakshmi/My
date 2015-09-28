package com.fissionlabs.trucksfirst.checklist;

import android.os.Bundle;
import android.os.ResultReceiver;
import android.support.annotation.Nullable;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.fissionlabs.trucksfirst.R;
import com.fissionlabs.trucksfirst.common.TFConst;
import com.fissionlabs.trucksfirst.model.checklist.NewChecklist;
import com.fissionlabs.trucksfirst.model.checklist.TollAmount;
import com.fissionlabs.trucksfirst.util.TFUtils;
import com.fissionlabs.trucksfirst.webservices.WebServices;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @author ashok on 9/23/15.
 */
public class SixthScreenFragment extends CheckListCommonFragment {

    private TextView mTvTime;
    private int count = 10;
    private boolean timeOver;
    private int timeTaken = 0;
    private RadioGroup radio_group_tollcash, radio_group_tollReceipt, radio_group_tollcashtopup;
    private CheckBox chkNoEntry,chkMechanical, chkRepairExp, chkUnapprovedRtoExp, chkChallan, chkUnapprovedExp;
    private EditText edittext_tollcash;
    private WebServices mWebServices;
    private TollAmount mTollAmount;
    private TextView mTVRemainingTollAmount,mTVtollReceipt;
    private NewChecklist newChecklist = FirstScreenFragment.newChecklist;
    private ArrayList<String> mTollCashShortageList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // CheckListBaseFragment.moveToNext();

        View view = inflater.inflate(R.layout.fragment_sixth_screen_checklist, container, false);
        Button next = (Button) view.findViewById(R.id.btnNext);
        radio_group_tollcash = (RadioGroup) view.findViewById(R.id.radio_group_tollcash);
        radio_group_tollReceipt = (RadioGroup) view.findViewById(R.id.radio_group_tollReceipt);
        radio_group_tollcashtopup = (RadioGroup) view.findViewById(R.id.radio_group_tollcashtopup);
        chkNoEntry = (CheckBox) view.findViewById(R.id.chkNoEntry);
        chkMechanical = (CheckBox) view.findViewById(R.id.chkMechanical);
        chkRepairExp = (CheckBox) view.findViewById(R.id.chkRepairExp);
        chkUnapprovedRtoExp = (CheckBox) view.findViewById(R.id.chkUnapprovedRtoExp);
        chkChallan = (CheckBox) view.findViewById(R.id.chkChallan);
        chkUnapprovedExp = (CheckBox) view.findViewById(R.id.chkUnapprovedExp);

        edittext_tollcash = (EditText) view.findViewById(R.id.edittext_tollcash);
        mTvTime = (TextView) view.findViewById(R.id.tvTime);
        TextView tvPageNumber = (TextView) view.findViewById(R.id.tvPageNumber);
        mTVRemainingTollAmount = (TextView)view.findViewById(R.id.remaining_toll_amount);
        mTVtollReceipt = (TextView)view.findViewById(R.id.toll_receipt);

        tvPageNumber.setText(String.format(getResources().getString(R.string.page_number), 6, 11));
        mTvTime.setText(String.format(getResources().getString(R.string.timer), count));

        startTimer();


        mWebServices = new WebServices();
        mWebServices.getTollAmount(getActivity(), new ResultReceiver(null) {
            @Override
            protected void onReceiveResult(int resultCode, Bundle resultData) {
                if (resultCode == TFConst.SUCCESS) {

                    String responseStr = resultData.getString("response");

                    if (responseStr != null) {
                        mTollAmount = new Gson().fromJson(responseStr, TollAmount.class);
                        mTVRemainingTollAmount.setText(Html.fromHtml(getActivity().getString(R.string.isthe_tollcash,mTollAmount.remainingTollAmount)));
                        mTVtollReceipt.setText(Html.fromHtml(getActivity().getString(R.string.toll_receipt,mTollAmount.prevHubToCurrentHubTollAmount)));
                    }
                } else {
                    Toast.makeText(getActivity(), "" + getResources().getString(R.string.issue_parsing_data), Toast.LENGTH_SHORT).show();
                }
                TFUtils.hideProgressBar();
            }
        });

        radio_group_tollReceipt.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = (RadioButton) group.findViewById(checkedId);
                if (null != rb && checkedId > -1) {
                    if (rb.getText().toString().equalsIgnoreCase("OK")) {
                        newChecklist.data.screen6.tollReceipts = true;
                    } else {
                        newChecklist.data.screen6.tollReceipts = false;
                    }
                }

            }
        });

        radio_group_tollcashtopup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = (RadioButton) group.findViewById(checkedId);
                if (null != rb && checkedId > -1) {
                    if (rb.getText().toString().equalsIgnoreCase("OK")) {
                        newChecklist.data.screen6.isTopUpDone = true;
                    } else {
                        newChecklist.data.screen6.isTopUpDone = false;
                    }
                }

            }
        });

        radio_group_tollcash.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = (RadioButton) group.findViewById(checkedId);
                if (null != rb && checkedId > -1) {
                    if (rb.getText().toString().equalsIgnoreCase("OK")) {
                        newChecklist.data.screen6.isCashWithDriverOk = true;
                    } else {
                        newChecklist.data.screen6.isCashWithDriverOk = false;
                    }
                }

            }
        });


        chkNoEntry.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mTollCashShortageList.add(chkNoEntry.getText().toString());
                } else {
                    mTollCashShortageList.remove(chkNoEntry.getText().toString());

                }

            }
        });


        chkMechanical.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mTollCashShortageList.add(chkMechanical.getText().toString());
                } else {
                    mTollCashShortageList.remove(chkMechanical.getText().toString());

                }

            }
        });


        chkRepairExp.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mTollCashShortageList.add(chkRepairExp.getText().toString());
                } else {
                    mTollCashShortageList.remove(chkRepairExp.getText().toString());

                }

            }
        });


        chkUnapprovedRtoExp.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mTollCashShortageList.add(chkUnapprovedRtoExp.getText().toString());
                } else {
                    mTollCashShortageList.remove(chkUnapprovedRtoExp.getText().toString());

                }

            }
        });


        chkChallan.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mTollCashShortageList.add(chkChallan.getText().toString());
                } else {
                    mTollCashShortageList.remove(chkChallan.getText().toString());

                }

            }
        });


        chkUnapprovedExp.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mTollCashShortageList.add(chkUnapprovedExp.getText().toString());
                } else {
                    mTollCashShortageList.remove(chkUnapprovedExp.getText().toString());

                }

            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                newChecklist.data.screen6.tollCashWithDriver = mTollAmount.remainingTollAmount;
                newChecklist.data.screen6.inputAmount = Integer.parseInt(edittext_tollcash.getText().toString());
                newChecklist.data.screen6.timeTaken = timeTaken;
                if(mTollCashShortageList!=null) {
                    newChecklist.data.screen6.cashShortageReason = mTollCashShortageList.toString().replace("[", "").replace("]", "")
                            .replace(", ", ",");
                }
                else{
                    newChecklist.data.screen6.cashShortageReason = "";
                }

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
