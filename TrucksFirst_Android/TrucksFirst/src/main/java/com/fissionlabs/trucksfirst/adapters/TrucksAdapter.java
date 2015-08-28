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
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
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
import com.fissionlabs.trucksfirst.util.LogConfig;
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

    public ArrayList<TruckDetails> getUpdatedList() {
        return mDataSet;
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
        holder.mEta.setText(TFUtils.changeTime(mDataSet.get(position).getEta()));
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
            holder.mEta.setText(TFUtils.changeTime(mDataSet.get(position).getEta()));
            holder.mAssignedPilot.setText(mDataSet.get(position).getAssignedPilot());
            holder.mPilotInHub.setVisibility(View.VISIBLE);
            holder.mVehicleInHub.setVisibility(View.VISIBLE);
            holder.mChecklist.setVisibility(View.VISIBLE);
        }

        if (mDataSet.get(position).getVehicleInHub() != null && mDataSet.get(position).getVehicleInHub().equals("true")) {
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
        if (mDataSet.get(position).getPilotInHub() != null && mDataSet.get(position).getPilotInHub().equalsIgnoreCase("true")) {
            holder.mRadioPilotInHubYes.setChecked(true);
        } else {
            holder.mRadioPilotInHubNo.setChecked(true);
        }

        holder.mRadioPilotInHubYes.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                showPilotInHubAlertDialog(mDataSet.get(position).getAssignedPilot(), position, holder.mRadioPilotInHubYes, holder.mRadioPilotInHubNo);
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
                showPilotAssignAlertDialog(position, mDataSet.get(position) /*.getAssignedPilot(),
                        mDataSet.get(position).getEta(),mDataSet.get(position).getNextHub()*/);
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
                            if (which == 0) {
                                mDriverChecklist.setDrivingLicence(isChecked);
                            } else if (which == 1) {
                                mDriverChecklist.setUniform(isChecked);
                            } else {
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
                            } catch (Exception e) {
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

    private CharSequence items[] = null;
    public void showPilotAssignAlertDialog(final int positioin, final TruckDetails obj/*final String pilotName, final String eta, final String nextHub*/) {
        if (obj.getAssignedPilot() == null || obj.getAssignedPilot().trim().equalsIgnoreCase("") || TextUtils.isEmpty(obj.getAssignedPilot()) || obj.getAssignedPilot().equalsIgnoreCase("null")) {
            assignPilotAlertDialog(positioin, obj, false);
        } else {

            if(mDataSet.get(positioin).getNextHub() == null || mDataSet.get(positioin).getNextHub().equals(null))
            {
                items= new CharSequence[]{String.format(mContext.getString(R.string.pilot_contact_info), "\nMobile Number:" + mDataSet.get(positioin).getContactNo().trim()),
                        mContext.getString(R.string.pilot_release_pilot)};
            } else {
                items= new CharSequence[]{String.format(mContext.getString(R.string.pilot_contact_info), "\nMobile Number:"+ mDataSet.get(positioin).getContactNo().trim()),
                        mContext.getString(R.string.pilot_release_pilot),
                        mContext.getString(R.string.pilot_change_pilot)};
            }

            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(mContext);
            dialogBuilder.setTitle(Html.fromHtml("<b>" + obj.getAssignedPilot() + "</b>"));
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
                            releasePilotAlertDialog(positioin, obj);
                            break;
                        case 2:
                            assignPilotAlertDialog(positioin, obj, true);
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

    public void assignPilotAlertDialog(final int position, final TruckDetails obj, final boolean flag /*final String eta, final String nextHub*/) {
        TFUtils.showProgressBar(mContext, mContext.getString(R.string.loading));
        new WebServices().getPilotAvailability(mContext, obj.getEta(), obj.getNextHub(), new ResultReceiver(null) {
            @Override
            protected void onReceiveResult(int resultCode, Bundle resultData) {
                if (resultCode == TFConst.SUCCESS) {
                    String responseStr = resultData.getString("response");

                    Type listType = new TypeToken<ArrayList<PilotAvailability>>() {
                    }.getType();

                    final ArrayList<PilotAvailability> pilotAvailabilityList = new Gson().fromJson(responseStr, listType);
                    final List<String> listItems = new ArrayList<>();
                    final List<String> sortedListItems = new ArrayList<>();

                    if (pilotAvailabilityList != null) {
                        for (int i = 0; i < pilotAvailabilityList.size(); i++) {
                            // NextAvailabilityTime <= ETA-1hour
                            if (Long.parseLong(pilotAvailabilityList.get(i).getNextAvailabilityTime()) <= (Long.parseLong(obj.getEta()) - 3600000)) {
                                sortedListItems.add(pilotAvailabilityList.get(i).getPilotFirstName() + "/" + pilotAvailabilityList.get(i).getPilotParentHub() + "\t\t" + TFUtils.changeTime(pilotAvailabilityList.get(i).getNextAvailabilityTime()));
                            }
                            listItems.add(pilotAvailabilityList.get(i).getPilotFirstName() + "/" + pilotAvailabilityList.get(i).getPilotParentHub() + "\t\t" + TFUtils.changeTime(pilotAvailabilityList.get(i).getNextAvailabilityTime()));
                        }
                    }
//                    final CharSequence[] items = listItems.toArray(new CharSequence[listItems.size()]);

                    View view = LayoutInflater.from(mContext).inflate(R.layout.pilot_availability, null, false);
                    AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(mContext);
                    dialogBuilder.setView(view);
                    final ListView availablePilots = (ListView) view.findViewById(R.id.pilot_availability);

                    final ArrayAdapter<String> adapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_list_item_single_choice, sortedListItems);

                    availablePilots.setAdapter(adapter);

                    final View footerView = ((LayoutInflater) mContext.getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.pilot_availability_footer, null, false);
                    Button more_pilots = (Button) footerView.findViewById(R.id.more_pilots);
                    availablePilots.addFooterView(footerView);


                    more_pilots.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            sortedListItems.clear();
                            sortedListItems.addAll(listItems);
                            adapter.notifyDataSetChanged();
                            availablePilots.removeFooterView(footerView);
                        }
                    });
                    final PilotAvailability pilot = new PilotAvailability();
                    availablePilots.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            if (position == 0) {
                                pilot.setPilotId(pilotAvailabilityList.get(position).getPilotId());
                                pilot.setPilotFirstName(pilotAvailabilityList.get(position).getPilotFirstName());
                                pilot.setPilotLastName(pilotAvailabilityList.get(position).getPilotLastName());
                                pilot.setNextAvailabilityTime(pilotAvailabilityList.get(position).getNextAvailabilityTime());
                                pilot.setAvailabilityStatus(pilotAvailabilityList.get(position).getAvailabilityStatus());
                                pilot.setContactNumber(pilotAvailabilityList.get(position).getContactNumber());
                                pilot.setPilotParentHub(pilotAvailabilityList.get(position).getPilotParentHub());
                            } else {
                                View view2 = LayoutInflater.from(mContext).inflate(R.layout.wrong_pilot_assignment, null, false);
                                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(mContext);
                                dialogBuilder.setView(view2);
                                dialogBuilder.setTitle(Html.fromHtml("<b>" + mContext.getString(R.string.warning) + "</b>"));
                                dialogBuilder.setPositiveButton(mContext.getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });
                                dialogBuilder.setNegativeButton(mContext.getResources().getString(R.string.cancel), null);
                                AlertDialog alertDialog2 = dialogBuilder.create();
                                alertDialog2.getWindow().setLayout(600, LinearLayout.LayoutParams.WRAP_CONTENT);
                                alertDialog2.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                                alertDialog2.show();
                            }
                        }
                    });

                    dialogBuilder.setTitle(Html.fromHtml("<b>" + mContext.getString(R.string.pilot_availability_title) + "</b>"));
                    dialogBuilder.setPositiveButton(mContext.getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            if (availablePilots.getCheckedItemPosition() == -1) {
                                Toast.makeText(mContext, mContext.getResources().getString(R.string.no_pilot_selected), Toast.LENGTH_SHORT).show();
                            } else {
                                TFUtils.showProgressBar(mContext, mContext.getResources().getString(R.string.please_wait));
                                String existingPilotId = flag ? mDataSet.get(position).getPilotAvailability().getPilotId() : null;
                                mDataSet.get(position).setAssignedPilot(pilot.getPilotFirstName());
                                mDataSet.get(position).setContactNo(pilot.getContactNumber());
                                mDataSet.get(position).setPilotId(pilot.getPilotId());
                                mDataSet.get(position).setPilotAvailability(pilot);
                                new WebServices().getChangePilot(mContext, obj, flag, existingPilotId, new ResultReceiver(null) {
                                    @Override
                                    protected void onReceiveResult(int resultCode, Bundle resultData) {
                                        super.onReceiveResult(resultCode, resultData);
                                        if (resultCode == TFConst.SUCCESS) {
                                            notifyItemChanged(position);
                                        } else {
                                            Toast.makeText(mContext, mContext.getResources().getString(R.string.problem_assign_pilot), Toast.LENGTH_SHORT).show();
                                        }
                                        TFUtils.hideProgressBar();
                                    }
                                });
                            }
                        }
                    });
                    dialogBuilder.setNegativeButton(mContext.getResources().getString(R.string.cancel), null);
                    final AlertDialog alertDialog = dialogBuilder.create();
                    alertDialog.getWindow().setLayout(600, LinearLayout.LayoutParams.WRAP_CONTENT);
                    alertDialog.show();

                } else {
                    Toast.makeText(mContext, "" + mContext.getResources().getString(R.string.issue_parsing_data), Toast.LENGTH_SHORT).show();
                }
                TFUtils.hideProgressBar();
            }
        });
    }

    public void releasePilotAlertDialog(final int position, TruckDetails obj) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(mContext);
        dialogBuilder.setTitle(Html.fromHtml(String.format(mContext.getString(R.string.are_you_sure_release), obj.getAssignedPilot())));
        dialogBuilder.setPositiveButton(mContext.getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                new WebServices().getPilotRelease(mContext, mDataSet.get(position).getCurrentHub(), mDataSet.get(position).getPilotId(), new ResultReceiver(null) {
                    @Override
                    protected void onReceiveResult(int resultCode, Bundle resultData) {
                        super.onReceiveResult(resultCode, resultData);
                        if (resultCode == 200) {
                            mDataSet.get(position).setAssignedPilot(null);
                            notifyItemChanged(position);

                        } else {
                            Toast.makeText(mContext, mContext.getResources().getString(R.string.error_releasing_pilot), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

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
            mRadioVehicleInHubYes = (RadioButton) view.findViewById(R.id.vehicle_in_hub_yes);
            mRadioVehicleInHubNo = (RadioButton) view.findViewById(R.id.vehicle_in_hub_no);

        }
    }
}
