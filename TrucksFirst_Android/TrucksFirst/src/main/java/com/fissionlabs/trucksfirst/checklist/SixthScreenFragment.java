package com.fissionlabs.trucksfirst.checklist;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.fissionlabs.trucksfirst.R;

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
    private RadioGroup radio_group_tollcash,radio_group_tollReceipt,radio_group_tollcashtopup;
    private CheckBox chkMechanical,chkRepairExp,chkUnapprovedRtoExp,chkChallan,chkUnapprovedExp;
    private EditText edittext_tollcash;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // CheckListBaseFragment.moveToNext();

        View view = inflater.inflate(R.layout.fragment_sixth_screen_checklist, container, false);
        Button next = (Button) view.findViewById(R.id.btnNext);
        radio_group_tollcash= (RadioGroup)view.findViewById(R.id.radio_group_tollcash);
        radio_group_tollReceipt= (RadioGroup)view.findViewById(R.id.radio_group_tollReceipt);
        radio_group_tollcashtopup= (RadioGroup)view.findViewById(R.id.radio_group_tollcashtopup);
        chkMechanical= (CheckBox)view.findViewById(R.id.chkMechanical);
        chkRepairExp= (CheckBox)view.findViewById(R.id.chkRepairExp);
        chkUnapprovedRtoExp= (CheckBox)view.findViewById(R.id.chkUnapprovedRtoExp);
        chkChallan= (CheckBox)view.findViewById(R.id.chkChallan);
        chkUnapprovedExp= (CheckBox)view.findViewById(R.id.chkUnapprovedExp);

        edittext_tollcash = (EditText)view.findViewById(R.id.edittext_tollcash);
        mTvTime = (TextView) view.findViewById(R.id.tvTime);
        TextView tvPageNumber = (TextView) view.findViewById(R.id.tvPageNumber);


        tvPageNumber.setText(String.format(getResources().getString(R.string.page_number), 6, 11));
        mTvTime.setText(String.format(getResources().getString(R.string.timer), count));

        startTimer();

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

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
