package com.fissionlabs.trucksfirst.adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.fissionlabs.trucksfirst.R;
import com.fissionlabs.trucksfirst.common.TFConst;
import com.fissionlabs.trucksfirst.fragments.TFTruckFragment;
import com.fissionlabs.trucksfirst.model.PilotAvailability;
import com.fissionlabs.trucksfirst.model.TruckDetails;
import com.fissionlabs.trucksfirst.util.TFUtils;
import com.fissionlabs.trucksfirst.webservices.WebServices;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ashok on 7/29/2015.
 */
public class TrucksAdapter extends RecyclerView.Adapter<TrucksAdapter.ViewHolder> {

    private Context mContext;
    private ArrayList<TruckDetails> mDataSet;
    private TFTruckFragment mTfTruckFragment;

    public TrucksAdapter(Context context, TFTruckFragment tfTruckFragment, ArrayList<TruckDetails> dataSet) {
        mContext = context;
        mTfTruckFragment = tfTruckFragment;
        mDataSet = dataSet;
    }

    @Override
    public TrucksAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.truck_details_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        holder.mVehicleNumber.setText(mDataSet.get(position).getVehicleNumber());
        holder.mVehicleRoute.setText(mDataSet.get(position).getVehicleRoute());
        holder.mEta.setText(mDataSet.get(position).getEta());
        holder.mAssignedPilot.setText(mDataSet.get(position).getAssignedPilot());
        if (mDataSet.get(position).getAssignedPilot() == null || mDataSet.get(position).getAssignedPilot().equalsIgnoreCase("null")) {
            holder.mVehicleNumber.setTextColor(mContext.getResources().getColor(android.R.color.holo_red_dark));
            holder.mVehicleRoute.setTextColor(mContext.getResources().getColor(android.R.color.holo_red_dark));
            holder.mEta.setTextColor(mContext.getResources().getColor(android.R.color.holo_red_dark));
            holder.mAssignedPilot.setText("");
            holder.mPilotInHub.setVisibility(View.GONE);
            holder.mVehicleInHub.setVisibility(View.GONE);
            holder.mChecklist.setVisibility(View.GONE);
        } else {
            holder.mVehicleNumber.setTextColor(mContext.getResources().getColor(android.R.color.black));
            holder.mVehicleRoute.setTextColor(mContext.getResources().getColor(android.R.color.black));
            holder.mEta.setTextColor(mContext.getResources().getColor(android.R.color.black));
            holder.mVehicleNumber.setText(mDataSet.get(position).getVehicleNumber());
            holder.mVehicleRoute.setText(mDataSet.get(position).getVehicleRoute());
            holder.mEta.setText(mDataSet.get(position).getEta());
            holder.mAssignedPilot.setText(mDataSet.get(position).getAssignedPilot());
            holder.mPilotInHub.setVisibility(View.VISIBLE);
            holder.mVehicleInHub.setVisibility(View.VISIBLE);
            holder.mChecklist.setVisibility(View.VISIBLE);
        }

        holder.mRadioPilotInHubYes.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                showPilotInHubAlertDialog(mDataSet.get(position).getAssignedPilot());
            }
        });

        holder.mAssignedPilot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPilotAssignAlertDialog(mDataSet.get(position).getAssignedPilot());
            }
        });

        holder.mChecklist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("vehicle_number", holder.mVehicleNumber.getText().toString());
                mTfTruckFragment.startFragment(R.layout.fragment_check_list, bundle);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    public void showPilotInHubAlertDialog(String title) {

        final CharSequence items[] = {mContext.getString(R.string.driving_licence),
                mContext.getString(R.string.uniform),
                mContext.getString(R.string.non_alcoholic)};

        final ArrayList<Integer> selectedItems = new ArrayList();
        boolean checkedItems[] = new boolean[items.length];

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(mContext);
        dialogBuilder.setTitle(Html.fromHtml("<b>" + title + "</b>"));
        dialogBuilder.setMultiChoiceItems(items, checkedItems, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                if (isChecked) {
                    selectedItems.add(which);
                } else if (selectedItems.contains(which)) {
                    selectedItems.remove(Integer.valueOf(which));
                }
            }
        });
        dialogBuilder.setPositiveButton(mContext.getResources().getString(R.string.save), null);
        dialogBuilder.setNegativeButton(mContext.getResources().getString(R.string.cancel), null);

        AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.getWindow().setLayout(500, LinearLayout.LayoutParams.WRAP_CONTENT);

        alertDialog.show();
    }


    public void showPilotAssignAlertDialog(final String pilotName) {
        if (pilotName == null || pilotName.trim().equalsIgnoreCase("") || TextUtils.isEmpty(pilotName) || pilotName.equalsIgnoreCase("null")) {
            assignPilotAlertDialog();
        } else {
            final CharSequence items[] = {String.format(mContext.getString(R.string.pilot_contact_info), "\nMobile Number:9999999999"),
                    mContext.getString(R.string.pilot_change_pilot),
                    mContext.getString(R.string.pilot_release_pilot)};

            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(mContext);
            dialogBuilder.setTitle(Html.fromHtml("<b>" + pilotName + "</b>"));
            dialogBuilder.setItems(items, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    switch (which) {
                        case 1:
                            assignPilotAlertDialog();
                            break;
                        case 2:
                            releasePilotAlertDialog(pilotName);
                            break;
                    }
                }
            });
            dialogBuilder.setPositiveButton(mContext.getString(R.string.ok), null);
            AlertDialog alertDialog = dialogBuilder.create();
            alertDialog.setCanceledOnTouchOutside(true);
            alertDialog.getWindow().setLayout(500, LinearLayout.LayoutParams.WRAP_CONTENT);
            alertDialog.show();
        }
    }

    public void assignPilotAlertDialog() {
        TFUtils.showProgressBar(mContext, mContext.getString(R.string.loading));
        new WebServices().getPilotAvailability(mContext, new ResultReceiver(null) {
            @Override
            protected void onReceiveResult(int resultCode, Bundle resultData) {
                if (resultCode == TFConst.SUCCESS) {
                    String responseStr = resultData.getString("response");

                    Type listType = new TypeToken<ArrayList<PilotAvailability>>() {
                    }.getType();

                    ArrayList<PilotAvailability> pilotAvailabilityList = new Gson().fromJson(responseStr, listType);
                    List<String> listItems = new ArrayList<>();

                    for (int i = 0; i < pilotAvailabilityList.size(); i++) {
                        listItems.add(pilotAvailabilityList.get(i).getPilotName() + "  -  " + pilotAvailabilityList.get(i).getPilotAvailabilityStatus());
                    }

                    final CharSequence[] items = listItems.toArray(new CharSequence[listItems.size()]);
                    AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(mContext);
                    dialogBuilder.setSingleChoiceItems(items, -1, null);
                    dialogBuilder.setTitle(Html.fromHtml("<b>" + mContext.getString(R.string.pilot_availability_title) + "</b>"));
                    dialogBuilder.setPositiveButton(mContext.getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            //Todo pilot assignment.
                        }
                    });
                    dialogBuilder.setNegativeButton(mContext.getResources().getString(R.string.cancel), null);

                    AlertDialog alertDialog = dialogBuilder.create();
                    alertDialog.getWindow().setLayout(500, LinearLayout.LayoutParams.WRAP_CONTENT);
                    alertDialog.show();

                } else {
                    Toast.makeText(mContext, "" + mContext.getResources().getString(R.string.issue_parsing_data), Toast.LENGTH_SHORT).show();
                }
                TFUtils.hideProgressBar();
            }
        });
    }

    public void releasePilotAlertDialog(String pilotName) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(mContext);
        dialogBuilder.setTitle(Html.fromHtml(String.format(mContext.getString(R.string.are_you_sure_release), pilotName)));
        dialogBuilder.setPositiveButton(mContext.getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                //Todo pilot release
            }
        });
        dialogBuilder.setNegativeButton(mContext.getResources().getString(R.string.no), null);
        final AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.getWindow().setLayout(500, LinearLayout.LayoutParams.WRAP_CONTENT);
        alertDialog.show();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView mVehicleNumber;
        public TextView mVehicleRoute;
        public TextView mEta;
        public TextView mAssignedPilot;
        public RadioGroup mVehicleInHub;
        public RadioGroup mPilotInHub;
        public ImageView mChecklist;
        public RadioButton mRadioPilotInHubYes;
        public RadioButton mRadioPilotInHubNo;

        public ViewHolder(View view) {
            super(view);

            mVehicleNumber = (TextView) view.findViewById(R.id.vehicle_number);
            mVehicleRoute = (TextView) view.findViewById(R.id.vehicle_route);
            mEta = (TextView) view.findViewById(R.id.eta);
            mAssignedPilot = (TextView) view.findViewById(R.id.assigned_pilot);
            mPilotInHub = (RadioGroup) view.findViewById(R.id.polit_in_hub);
            mRadioPilotInHubYes = (RadioButton) view.findViewById(R.id.polit_in_hub_yes);
            mVehicleInHub = (RadioGroup) view.findViewById(R.id.vehicle_in_hub);
            mChecklist = (ImageView) view.findViewById(R.id.checklist);

        }
    }
}
