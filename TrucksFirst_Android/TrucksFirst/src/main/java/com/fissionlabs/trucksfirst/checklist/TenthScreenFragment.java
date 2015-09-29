package com.fissionlabs.trucksfirst.checklist;

import android.os.Bundle;
import android.os.ResultReceiver;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.fissionlabs.trucksfirst.R;
import com.fissionlabs.trucksfirst.common.TFConst;
import com.fissionlabs.trucksfirst.model.checklist.NewChecklist;
import com.fissionlabs.trucksfirst.util.TFUtils;
import com.fissionlabs.trucksfirst.webservices.WebServices;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @author ashok on 9/23/15.
 */
public class TenthScreenFragment extends CheckListCommonFragment {
    private RecyclerView listView;
    private TextView mTvTime;
    private int count = 10;
    private int timeTaken = 0;
    private boolean timeOver;
    private View view;
    private Button btnNext;
    private TenthScreenAdapter tsa;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_tenth_screen_checklist, container, false);
        btnNext = (Button) view.findViewById(R.id.btnNext);

        mTvTime = (TextView) view.findViewById(R.id.tvTime);
        TextView tvPageNumber = (TextView) view.findViewById(R.id.tvPageNumber);

        tvPageNumber.setText(String.format(getResources().getString(R.string.page_number), 10, 11));
        mTvTime.setText(String.format(getResources().getString(R.string.timer), count));

        listView = (RecyclerView) view.findViewById(R.id.list);
        listView.setLayoutManager(new LinearLayoutManager(getActivity()));

        TFUtils.showProgressBar(getActivity(), "Fetching data...");
        new WebServices().getChecklistDetailsNew(getActivity(), new ResultReceiver(null) {
            @Override
            protected void onReceiveResult(int resultCode, Bundle resultData) {
                if (resultCode == TFConst.SUCCESS) {
                    String responseStr = resultData.getString("response");
                    NewChecklist nc = new Gson().fromJson(responseStr, NewChecklist.class);
                    ArrayList<Boolean> b = getBooleansFromChecklist(nc);
                    ArrayList<String> s = getItemStrings();
                    tsa = new TenthScreenAdapter(b, s);
                    TFUtils.hideProgressBar();
                    listView.setAdapter(tsa);
                    btnNext.setOnClickListener(new NextButtonListener());
                } else {
                    TFUtils.hideProgressBar();
                    Toast.makeText(getActivity(), "" + getActivity().getResources().getString(R.string.issue_parsing_data), Toast.LENGTH_SHORT).show();
                }
            }
        });
        return view;
    }

    class NextButtonListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            NewChecklist nc = getNewChecklistFromBooleans(tsa.bools);
            String s = new Gson().toJson(nc, NewChecklist.class);
            try {
                new WebServices().postVehicleChecklistNew(new JSONObject(s));
            } catch (JSONException jsone) {
                Toast.makeText(getActivity(), "Could not save checklist data", Toast.LENGTH_SHORT).show();
                jsone.printStackTrace();
            }
            CheckListBaseFragment.moveToNext();
        }
    }
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if (isVisibleToUser) {
            startTimer();
        }
    }

    NewChecklist getNewChecklistFromBooleans(ArrayList<Boolean> b) {
        NewChecklist ret = new NewChecklist();
        // TODO: Implement
        return ret;
    }

    ArrayList<Boolean> getBooleansFromChecklist(NewChecklist nc) {
        ArrayList<Boolean> booleans = new ArrayList<Boolean>();
        // TODO: Implement
        return booleans;
    }

    ArrayList<String> getItemStrings(){
        ArrayList<String> strings = new ArrayList<String>();
        String[] itemsStrings = getResources().getStringArray(R.array.item_array);
        for (String s:itemsStrings) strings.add(s);
        return strings;
    }

    class TenthScreenAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        private ArrayList<Boolean> bools;
        private ArrayList<String> strings;

        TenthScreenAdapter(ArrayList<Boolean> b, ArrayList<String> s) {
            bools = b;
            strings = s;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View v = inflater.inflate(R.layout.tenth_screen_item, parent, false);
            return new FalseItemHolder(v);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
            FalseItemHolder fih = (FalseItemHolder) holder;
            if (!bools.get(position)) {
                fih.tv.setVisibility(View.GONE);
                fih.cb.setVisibility(View.GONE);
            } else {
                fih.tv.setText(strings.get(position));
                fih.cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked) {
                            bools.set(position, Boolean.TRUE);
                        }
                        else bools.set(position, Boolean.FALSE);
                    }
                });
            }
        }

        @Override
        public int getItemCount() {
            return bools.size();
        }
    }

    class FalseItemHolder extends RecyclerView.ViewHolder {
        public TextView tv;
        public CheckBox cb;

        public FalseItemHolder(View itemView) {
            super(itemView);
            tv = (TextView) itemView.findViewById(R.id.tv);
            cb = (CheckBox) itemView.findViewById(R.id.cb);
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
