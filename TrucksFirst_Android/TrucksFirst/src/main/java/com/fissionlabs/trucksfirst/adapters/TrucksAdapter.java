package com.fissionlabs.trucksfirst.adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
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
import com.fissionlabs.trucksfirst.pojo.DriverChecklist;
import com.fissionlabs.trucksfirst.util.TFUtils;
import com.fissionlabs.trucksfirst.webservices.WebServices;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Ashok on 7/29/2015.
 */
@SuppressWarnings("ALL")
public class TrucksAdapter extends RecyclerView.Adapter<TrucksAdapter.ViewHolder> {

    private Context mContext;
    private ArrayList<TruckDetails> mDataSet;
    private TFTruckFragment mTfTruckFragment;
    private DriverChecklist mDriverChecklist;
    private WebServices mWebServices;

    public TrucksAdapter(Context context, TFTruckFragment tfTruckFragment, ArrayList<TruckDetails> dataSet) {
        mContext = context;
        mTfTruckFragment = tfTruckFragment;
        mDataSet = dataSet;
    }

    public void setUpdateList(ArrayList<TruckDetails> dataSet) {
        mDataSet = dataSet;
    }

    public ArrayList<TruckDetails> getUpdatedList(){
        return  mDataSet;
    }

    @Override
    public TrucksAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.truck_details_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        holder.mRadioVehicleInHubYes.setTag(holder);
        holder.mRadioVehicleInHubNo.setTag(holder);
        holder.mVehicleNumber.setText(mDataSet.get(position).getVehicleNumber());
        holder.mVehicleRoute.setText(mDataSet.get(position).getVehicleRoute());
        holder.mClient.setText(mDataSet.get(position).getClient());
        holder.mEta.setText(changeTime(mDataSet.get(position).getEta()));
        holder.mAssignedPilot.setText(mDataSet.get(position).getAssignedPilot());
        if (mDataSet.get(position).getAssignedPilot() == null || mDataSet.get(position).getAssignedPilot().equalsIgnoreCase("null")) {
            holder.mVehicleNumber.setTextColor(mContext.getResources().getColor(android.R.color.holo_red_dark));
            holder.mVehicleRoute.setTextColor(mContext.getResources().getColor(android.R.color.holo_red_dark));
            holder.mClient.setTextColor(mContext.getResources().getColor(android.R.color.holo_red_dark));
            holder.mEta.setTextColor(mContext.getResources().getColor(android.R.color.holo_red_dark));
            holder.mAssignedPilot.setText("");
            holder.mPilotInHub.setVisibility(View.GONE);
            holder.mVehicleInHub.setVisibility(View.GONE);
            holder.mChecklist.setVisibility(View.GONE);
        } else {
            holder.mVehicleNumber.setTextColor(mContext.getResources().getColor(android.R.color.black));
            holder.mVehicleRoute.setTextColor(mContext.getResources().getColor(android.R.color.black));
            holder.mClient.setTextColor(mContext.getResources().getColor(android.R.color.black));
            holder.mEta.setTextColor(mContext.getResources().getColor(android.R.color.black));
            holder.mVehicleNumber.setText(mDataSet.get(position).getVehicleNumber());
            holder.mVehicleRoute.setText(mDataSet.get(position).getVehicleRoute());
            holder.mClient.setText(mDataSet.get(position).getClient());
            holder.mEta.setText(changeTime(mDataSet.get(position).getEta()));
            holder.mAssignedPilot.setText(mDataSet.get(position).getAssignedPilot());
            holder.mPilotInHub.setVisibility(View.VISIBLE);
            holder.mVehicleInHub.setVisibility(View.VISIBLE);
            holder.mChecklist.setVisibility(View.VISIBLE);
        }

        if(mDataSet.get(position).getVehicleInHub()!= null && mDataSet.get(position).getVehicleInHub().equals("true")) {
            holder.mRadioVehicleInHubYes.setChecked(true);
            //noinspection deprecation,deprecation
            holder.mChecklist.setImageDrawable(mContext.getResources().getDrawable(R.drawable.checklist_selector));
            holder.mChecklist.setClickable(true);
        } else {
            holder.mRadioVehicleInHubNo.setChecked(true);
            //noinspection deprecation
            holder.mChecklist.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_check_list_disabled));
            holder.mChecklist.setClickable(false);
        }

        holder.mRadioVehicleInHubYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDataSet.get(position).setVehicleInHub("true");
                //noinspection deprecation
                holder.mChecklist.setImageDrawable(mContext.getResources().getDrawable(R.drawable.checklist_selector));
                holder.mChecklist.setClickable(true);
                notifyItemChanged(position);
            }
        });
        holder.mRadioVehicleInHubNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDataSet.get(position).setVehicleInHub("false");
                //noinspection deprecation
                holder.mChecklist.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_check_list_disabled));
                holder.mChecklist.setClickable(false);
                notifyItemChanged(position);
            }
        });
        if(mDataSet.get(position).getPilotInHub() != null && mDataSet.get(position).getPilotInHub().equalsIgnoreCase("true")){
            holder.mRadioPilotInHubYes.setChecked(true);
        } else {
            holder.mRadioPilotInHubNo.setChecked(true);
        }

        holder.mRadioPilotInHubYes.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                showPilotInHubAlertDialog(mDataSet.get(position).getAssignedPilot(), position, holder.mRadioPilotInHubYes,holder.mRadioPilotInHubNo);
            }
        });
        holder.mRadioPilotInHubNo.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                mDataSet.get(position).setPilotInHub("false");
                notifyItemChanged(position);
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

    private String changeTime(String etaInMills) {
        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        long milliSeconds= Long.parseLong(etaInMills);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSeconds);
        return formatter.format(calendar.getTime());
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    public void showPilotInHubAlertDialog(final String title, final int positon, final RadioButton yes, final RadioButton no) {


        mWebServices = new WebServices();
        mWebServices.getDriverChecklistDetails(new ResultReceiver(null) {
            @Override
            protected void onReceiveResult(int resultCode, Bundle resultData) {
                if (resultCode == TFConst.SUCCESS) {

                    String responseStr = resultData.getString("response");
                    mDriverChecklist = new Gson().fromJson(responseStr, DriverChecklist.class);

                    final CharSequence items[] = {mContext.getString(R.string.driving_licence),
                            mContext.getString(R.string.uniform),
                            mContext.getString(R.string.non_alcoholic)};

                    boolean checkedItems[] = {mDriverChecklist.isDrivingLicence(),
                            mDriverChecklist.isUniform(),
                            mDriverChecklist.isNonAlchoholic()};

                    AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(mContext);
                    dialogBuilder.setTitle(Html.fromHtml("<b>" + title + "</b>"));
                    dialogBuilder.setMultiChoiceItems(items, checkedItems, new DialogInterface.OnMultiChoiceClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                            if(which == 0){
                                mDriverChecklist.setDrivingLicence(isChecked);
                            }else if(which == 1){
                                mDriverChecklist.setUniform(isChecked);
                            }else {
                                mDriverChecklist.setNonAlchoholic(isChecked);
                            }
                        }
                    });
                    dialogBuilder.setPositiveButton(mContext.getResources().getString(R.string.save), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            mDataSet.get(positon).setPilotInHub("true");
                            notifyDataSetChanged();
                            String jsonObject = new Gson().toJson(mDriverChecklist, DriverChecklist.class);
                            try {
                                mWebServices.updateDriverChecklist(new JSONObject(jsonObject));
                            }
                            catch(Exception e){
                                e.printStackTrace();
                            }

                        }
                    });
                    dialogBuilder.setNegativeButton(mContext.getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            no.setChecked(true);
                            mDataSet.get(positon).setPilotInHub("false");
                        }
                    });

                    notifyItemChanged(positon);
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
                        case 0:
                            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + items[0].toString().split(":")[1]));
                            mContext.startActivity(intent);
                            break;
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
        new WebServices().getPilotAvailability(new ResultReceiver(null) {
            @Override
            protected void onReceiveResult(int resultCode, Bundle resultData) {
                if (resultCode == TFConst.SUCCESS) {
                    String responseStr = resultData.getString("response");

                    Type listType = new TypeToken<ArrayList<PilotAvailability>>() {
                    }.getType();

                    ArrayList<PilotAvailability> pilotAvailabilityList = new Gson().fromJson(responseStr, listType);
                    List<String> listItems = new ArrayList<>();

                    if(pilotAvailabilityList != null) {
                        for (int i = 0; i < pilotAvailabilityList.size(); i++) {
                            listItems.add(pilotAvailabilityList.get(i).getPilotName() + "  -  " + pilotAvailabilityList.get(i).getPilotAvailabilityStatus());
                        }
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
        public TextView mClient;
        public TextView mVehicleRoute;
        public TextView mEta;
        public TextView mAssignedPilot;
        public RadioGroup mVehicleInHub;
        public RadioGroup mPilotInHub;
        public ImageView mChecklist;
        public RadioButton mRadioPilotInHubYes;
        public RadioButton mRadioPilotInHubNo;
        public RadioButton mRadioVehicleInHubYes;
        public RadioButton mRadioVehicleInHubNo;

        public ViewHolder(View view) {
            super(view);

            mVehicleNumber = (TextView) view.findViewById(R.id.vehicle_number);
            mVehicleRoute = (TextView) view.findViewById(R.id.vehicle_route);
            mClient = (TextView) view.findViewById(R.id.client);
            mEta = (TextView) view.findViewById(R.id.eta);
            mAssignedPilot = (TextView) view.findViewById(R.id.assigned_pilot);
            mPilotInHub = (RadioGroup) view.findViewById(R.id.polit_in_hub);
            mRadioPilotInHubYes = (RadioButton) view.findViewById(R.id.polit_in_hub_yes);
            mRadioPilotInHubNo = (RadioButton) view.findViewById(R.id.polit_in_hub_no);
            mVehicleInHub = (RadioGroup) view.findViewById(R.id.vehicle_in_hub);
            mChecklist = (ImageView) view.findViewById(R.id.checklist);
            mRadioVehicleInHubYes = (RadioButton)view.findViewById(R.id.vehicle_in_hub_yes);
            mRadioVehicleInHubNo = (RadioButton)view.findViewById(R.id.vehicle_in_hub_no);

        }
    }
}
