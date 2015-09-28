package com.fissionlabs.trucksfirst.adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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
import com.fissionlabs.trucksfirst.fragments.TFSOSFragment;
import com.fissionlabs.trucksfirst.fragments.TFTruckFragment;
import com.fissionlabs.trucksfirst.home.TFHomeActivity;
import com.fissionlabs.trucksfirst.model.PilotAvailability;
import com.fissionlabs.trucksfirst.model.TruckDetails;
import com.fissionlabs.trucksfirst.model.checklist.NewChecklist;
import com.fissionlabs.trucksfirst.pojo.DriverChecklist;
import com.fissionlabs.trucksfirst.util.TFUtils;
import com.fissionlabs.trucksfirst.webservices.WebServices;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Created by Ashok on 7/29/2015.
 */
@SuppressWarnings("ALL")
public class TrucksAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int WITH_PILOT = 0;
    private final int WITH_BUTTON = 1;
    private Context mContext;
    private TFHomeActivity mActivity;
    private ArrayList<TruckDetails> mDataSet;
    private TFTruckFragment mTfTruckFragment;
    private DriverChecklist mDriverChecklist;
    private WebServices mWebServices = new WebServices();
    private String spinnerText = "";

    public TrucksAdapter(Context context, TFHomeActivity activity, TFTruckFragment tfTruckFragment, ArrayList<TruckDetails> dataSet) {
        mContext = context;
        mActivity = activity;
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
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder;
        switch (viewType) {
            case WITH_PILOT:
                View v1 = LayoutInflater.from(parent.getContext()).inflate(R.layout.truck_details_item_with_pilot, parent, false);
                holder = new ViewHolderWithPilot(v1);
                break;
            case WITH_BUTTON:
                View v2 = LayoutInflater.from(parent.getContext()).inflate(R.layout.truck_details_item_with_button, parent, false);
                holder = new ViewHolderWithButton(v2);
                break;
            // default added just for compilation
            default:
                View v3 = LayoutInflater.from(parent.getContext()).inflate(R.layout.truck_details_item_with_pilot, parent, false);
                holder = new ViewHolderWithPilot(v3);
                break;
        }
        return holder;
    }

    @Override
    public int getItemViewType(int position) {
        //return super.getItemViewType(position);
        if (mDataSet.get(position).getAssignedPilot() == null || mDataSet.get(position).getAssignedPilot().equalsIgnoreCase("null"))
            return WITH_BUTTON;
        else return WITH_PILOT;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {

        switch (holder.getItemViewType()) {
            case WITH_BUTTON:
                ViewHolderWithButton vb = (ViewHolderWithButton) holder;
                loadViewWithButton(vb, position);
                break;
            case WITH_PILOT:
                ViewHolderWithPilot vp = (ViewHolderWithPilot) holder;
                loadViewWithPilot(vp, position);
                break;
        }
    }

    private void loadViewWithButton(ViewHolderWithButton vb, final int p) {
        TruckDetails td = mDataSet.get(p);
        vb.mVehicleNumber.setText(td.getVehicleNumber());
        vb.mVehicleNumber.setTextColor(mContext.getResources().getColor(android.R.color.holo_red_dark));
        vb.mVehicleNumber.setOnClickListener(new VehicleNumberListener(td));
        vb.mVehicleRoute.setText(td.getVehicleRoute());
        vb.mVehicleRoute.setTextColor(mContext.getResources().getColor(android.R.color.holo_red_dark));
        vb.mClient.setText(td.getClient());
        vb.mClient.setTextColor(mContext.getResources().getColor(android.R.color.holo_red_dark));
        vb.mEta.setText(TFUtils.changeTime(td.getEta()));
        vb.mEta.setTextColor(mContext.getResources().getColor(android.R.color.holo_red_dark));
        vb.mAssignPilot.setOnClickListener(new AssignedPilotListener(td, p));
    }

    private void loadViewWithPilot(ViewHolderWithPilot vp, final int p) {
        TruckDetails td = mDataSet.get(p);
        vp.mRadioVehicleInHubYes.setTag(vp);
        vp.mRadioVehicleInHubNo.setTag(vp);
        vp.mVehicleNumber.setText(td.getVehicleNumber());
        vp.mVehicleRoute.setText(td.getVehicleRoute());
        vp.mClient.setText(td.getClient());
        vp.mEta.setText(TFUtils.changeTime(td.getEta()));
        vp.mAssignedPilot.setText(td.getAssignedPilot());
        mActivity.startDelayTracking(td.getAssignedPilot(), td.getEta());

        if (td.getVehicleInHub() != null && td.getVehicleInHub().equals("true")) {

            vp.mRadioVehicleInHubYes.setChecked(true);
            vp.mChecklist.setImageDrawable(mContext.getResources().getDrawable(R.drawable.checklist_selector));
            vp.mChecklist.setClickable(true);
            vp.mChecklist.setEnabled(true);
        } else {
            vp.mRadioVehicleInHubNo.setChecked(true);
            //noinspection deprecation
            vp.mChecklist.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_check_list_disabled));
            vp.mChecklist.setClickable(false);
            vp.mChecklist.setEnabled(false);
        }

        if (td.getPilotInHub() != null && td.getPilotInHub().equalsIgnoreCase("true")) {
            vp.mRadioPilotInHubYes.setChecked(true);
        } else {
            vp.mRadioPilotInHubNo.setChecked(true);
            mWebServices.getPilotInHub(td.getVehicleTrackingId(), "false");
        }

        if (td.getCheckList() != null) {
            if (td.getCheckList().equals("true")) {
                vp.mChecklist.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_check_list_done));
                vp.mChecklist.setClickable(true);
                vp.mChecklist.setEnabled(true);
            }
        }

        vp.mRadioVehicleInHubYes.setOnClickListener(new VehicleInHubYesListener(td, vp));
        vp.mRadioVehicleInHubNo.setOnClickListener(new VehicleInHubNoListener(td, vp));
        vp.mRadioPilotInHubYes.setOnClickListener(new PilotInHubYesListener(td, vp));
        vp.mRadioPilotInHubNo.setOnClickListener(new PilotInHubNoListener(td, p));
        vp.mAssignedPilot.setOnClickListener(new AssignedPilotListener(td, p));
        vp.mChecklist.setOnClickListener(new ChecklistListener(td, vp));
        vp.mVehicleNumber.setOnClickListener(new VehicleNumberListener(td));
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    public void showPilotInHubAlertDialog(final String title, final int position, final RadioButton yes, final RadioButton no, final String vehicleNo, final String pilotNo, final ViewHolderWithPilot holder) {
        mWebServices.getDriverChecklistDetails(vehicleNo, pilotNo, new ResultReceiver(null) {
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

                    final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(mContext);
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
                            mDataSet.get(position).setPilotInHub("true");
                            notifyDataSetChanged();
                            mDriverChecklist.setPilotName(mDataSet.get(position).getAssignedPilot());
                            mDriverChecklist.setVehiclenumber(mDataSet.get(position).getVehicleNumber());
                            String jsonObject = new Gson().toJson(mDriverChecklist, DriverChecklist.class);
                            try {
                                mWebServices.updateDriverChecklist(new JSONObject(jsonObject));
                                mWebServices.getPilotInHub(mDataSet.get(position).getVehicleTrackingId(), "true");
                                holder.mRadioPilotInHubYes.setChecked(true);
                                // Pilot has arrived.
                                mActivity.stopDelayTracking(mDataSet.get(position).getAssignedPilot());
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        }
                    });

                    dialogBuilder.setNegativeButton(mContext.getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            no.setChecked(true);
                            mDataSet.get(position).setPilotInHub("false");
                        }
                    });

                    notifyItemChanged(position);
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

    //    private CharSequence items[] = null;
    public void showPilotAssignAlertDialog(final int positioin, final TruckDetails obj/*final String pilotName, final String eta, final String nextHub*/) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(mContext);
        dialogBuilder.setTitle(Html.fromHtml("<b>" + obj.getAssignedPilot() + "</b>"));
        View modifyPilot = LayoutInflater.from(mContext).inflate(R.layout.modify_pilot, null, false);
        dialogBuilder.setView(modifyPilot);
        if (mDataSet.get(positioin).getNextHub() == null || mDataSet.get(positioin).getNextHub().equals("null")) {
            ((Button) modifyPilot.findViewById(R.id.change_pilot)).setVisibility(View.GONE);
        } else {
            ((Button) modifyPilot.findViewById(R.id.change_pilot)).setVisibility(View.VISIBLE);
        }
        final TextView contactNo = (TextView) modifyPilot.findViewById(R.id.contact_no);
        String contact = "\nMobile Number:+91" + ((mDataSet.get(positioin).getContactNo() == null || mDataSet.get(positioin).getContactNo().equals("null")) ? "" : mDataSet.get(positioin).getContactNo());
        contactNo.setText(String.format(mContext.getString(R.string.pilot_contact_info), contact));

        dialogBuilder.setPositiveButton(mContext.getString(R.string.ok), null);
        final AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.setCanceledOnTouchOutside(true);
        alertDialog.getWindow().setLayout(500, LinearLayout.LayoutParams.WRAP_CONTENT);
        alertDialog.show();
        modifyPilot.findViewById(R.id.contact_no).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + contactNo.getText().toString().split(":")[1]));
                mContext.startActivity(intent);
            }
        });
        modifyPilot.findViewById(R.id.release_pilot).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                releasePilotAlertDialog(positioin, obj);
            }
        });
        modifyPilot.findViewById(R.id.change_pilot).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                assignPilotAlertDialog(positioin, obj, true);
            }
        });
    }

    public void assignPilotAlertDialog(final int trucksPosition, final TruckDetails obj, final boolean flag /*final String eta, final String nextHub*/) {
        TFUtils.showProgressBar(mContext, mContext.getString(R.string.loading));
        new WebServices().getPilotAvailability(mContext, obj.getEta(), obj.getNextHub(), new ResultReceiver(null) {
            @Override
            protected void onReceiveResult(int resultCode, Bundle resultData) {
                if (resultCode == TFConst.SUCCESS) {
                    String responseStr = resultData.getString("response");

                    Type listType = new TypeToken<ArrayList<PilotAvailability>>() {
                    }.getType();

                    final ArrayList<PilotAvailability> pilotAvailabilityList = new Gson().fromJson(responseStr, listType);
                    final ArrayList<PilotAvailability> listItemsPilot = new ArrayList<PilotAvailability>();
                    final ArrayList<PilotAvailability> sortedListItemsPilot = new ArrayList<PilotAvailability>();
                    final List<String> listItems = new ArrayList<>();
                    final List<String> sortedListItems = new ArrayList<>();

                    if (pilotAvailabilityList != null) {
                        for (int i = 0; i < pilotAvailabilityList.size(); i++) {
                            // NextAvailabilityTime <= ETA-1hour
                            if (i < 4 && Long.parseLong(pilotAvailabilityList.get(i).getNextAvailabilityTime()) <= (Long.parseLong(obj.getEta()) - 3600000)) {
                                sortedListItems.add(pilotAvailabilityList.get(i).getPilotFirstName() + "/" + pilotAvailabilityList.get(i).getPilotParentHub() + "\t\t" + TFUtils.changeTime(pilotAvailabilityList.get(i).getNextAvailabilityTime()));
                                sortedListItemsPilot.add(pilotAvailabilityList.get(i));
                            }
                            listItems.add(pilotAvailabilityList.get(i).getPilotFirstName() + "/" + pilotAvailabilityList.get(i).getPilotParentHub() + "\t\t" + TFUtils.changeTime(pilotAvailabilityList.get(i).getNextAvailabilityTime()));
                            listItemsPilot.add(pilotAvailabilityList.get(i));
                        }
                    }
                    if (listItems.size() == 0) {
                        Toast.makeText(mContext, mContext.getResources().getString(R.string.no_pilots_available), Toast.LENGTH_SHORT).show();
                        TFUtils.hideProgressBar();
                        return;
                    }
//                    final CharSequence[] items = listItems.toArray(new CharSequence[listItems.size()]);

                    View view = LayoutInflater.from(mContext).inflate(R.layout.pilot_availability, null, false);
                    AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(mContext);
                    dialogBuilder.setView(view);
                    dialogBuilder.setNegativeButton(mContext.getResources().getString(R.string.cancel), null);

                    dialogBuilder.setTitle(Html.fromHtml("<b>" + mContext.getString(R.string.pilot_availability_title) + "</b>"));
                    dialogBuilder.setPositiveButton(mContext.getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });

                    final AlertDialog alertDialog = dialogBuilder.create();
                    alertDialog.getWindow().setLayout(600, LinearLayout.LayoutParams.WRAP_CONTENT);
                    alertDialog.show();
                    final ListView availablePilots = (ListView) view.findViewById(R.id.pilot_availability);

                    final ArrayAdapter<String> adapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_list_item_single_choice, sortedListItems) {
                        @Override
                        public View getView(int position, View convertView, ViewGroup parent) {
                            TextView textView = (TextView) super.getView(position, convertView, parent);
                            if (sortedListItemsPilot.get(position).getPilotParentHub().equalsIgnoreCase(TFUtils.getStringFromSP(mContext, TFConst.HUB_NAME))) {
                                textView.setTextColor(Color.parseColor("#006700"));
                            } else {
                                textView.setTextColor(Color.parseColor("#000000"));
                            }
                            return textView;
                        }
                    };

                    availablePilots.setAdapter(adapter);

                    final View footerView = ((LayoutInflater) mContext.getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.pilot_availability_footer, null, false);
                    Button more_pilots = (Button) footerView.findViewById(R.id.more_pilots);
                    availablePilots.addFooterView(footerView);


                    more_pilots.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            sortedListItems.clear();
                            sortedListItems.addAll(listItems);
                            sortedListItemsPilot.clear();
                            sortedListItemsPilot.addAll(listItemsPilot);
                            adapter.notifyDataSetChanged();
                            availablePilots.removeFooterView(footerView);
                        }
                    });
                    final PilotAvailability pilot = new PilotAvailability();
                    availablePilots.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
//                            if (position == 0) {
                            pilot.setPilotId(pilotAvailabilityList.get(position).getPilotId());
                            pilot.setPilotFirstName(pilotAvailabilityList.get(position).getPilotFirstName());
                            pilot.setPilotLastName(pilotAvailabilityList.get(position).getPilotLastName());
                            pilot.setNextAvailabilityTime(pilotAvailabilityList.get(position).getNextAvailabilityTime());
                            pilot.setAvailabilityStatus(pilotAvailabilityList.get(position).getAvailabilityStatus());
                            pilot.setContactNumber(pilotAvailabilityList.get(position).getContactNumber());
                            pilot.setPilotParentHub(pilotAvailabilityList.get(position).getPilotParentHub());
//                            } else {
                               /* View view2 = LayoutInflater.from(mContext).inflate(R.layout.wrong_pilot_assignment, null, false);
                                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(mContext);
                                dialogBuilder.setView(view2);
                                TextView pilotName = (TextView) view2.findViewById(R.id.pilot_name);
                                final EditText etReason = (EditText) view2.findViewById(R.id.reason_edt);
                                final Spinner spinner = (Spinner) view2.findViewById(R.id.spinner);

                                etReason.setHint(mContext.getResources().getString(R.string.pilot_selection_warning, "" + pilotAvailabilityList.get(0).getPilotFirstName()));
                                pilotName.setText(pilotAvailabilityList.get(0).getPilotFirstName());
                                dialogBuilder.setTitle(Html.fromHtml("<b>" + mContext.getString(R.string.warning) + "</b>"));
                                dialogBuilder.setPositiveButton(mContext.getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                });
                                dialogBuilder.setNegativeButton(mContext.getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        availablePilots.post(new Runnable() {
                                            @Override
                                            public void run() {
                                                availablePilots.setItemChecked(position, false);
                                            }
                                        });
                                    }
                                });
                                final AlertDialog alertDialog2 = dialogBuilder.create();
                                alertDialog2.getWindow().setLayout(600, LinearLayout.LayoutParams.WRAP_CONTENT);
                                alertDialog2.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                                alertDialog2.show();
                                alertDialog2.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        spinnerText = spinner.getSelectedItem().toString();
                                        if (spinnerText.equals("Others") && etReason.getText().toString().length() == 0) {
                                            Toast.makeText(mContext, "Please enter the reason", Toast.LENGTH_SHORT).show();
                                            return;
                                        } else {
                                            pilot.setPilotId(pilotAvailabilityList.get(position).getPilotId());
                                            pilot.setPilotFirstName(pilotAvailabilityList.get(position).getPilotFirstName());
                                            pilot.setPilotLastName(pilotAvailabilityList.get(position).getPilotLastName());
                                            pilot.setNextAvailabilityTime(pilotAvailabilityList.get(position).getNextAvailabilityTime());
                                            pilot.setAvailabilityStatus(pilotAvailabilityList.get(position).getAvailabilityStatus());
                                            pilot.setContactNumber(pilotAvailabilityList.get(position).getContactNumber());
                                            pilot.setPilotParentHub(pilotAvailabilityList.get(position).getPilotParentHub());
                                            spinnerText = spinner.getSelectedItem().toString();
                                            alertDialog2.dismiss();
                                            try {
                                                mWebServices.postSkippedPilotInfo(pilotAvailabilityList.get(0).getPilotId(), spinnerText, etReason.getText().toString());
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            } finally {
                                                alertDialog.dismiss();
                                                assignPilotAlertDialog(trucksPosition, obj, flag);
                                            }
                                        }
                                    }
                                });
                            }*/
                        }
                    });
                    alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (availablePilots.getCheckedItemPosition() == -1) {
                                Toast.makeText(mContext, mContext.getResources().getString(R.string.no_pilot_selected), Toast.LENGTH_SHORT).show();
                                return;
                            } else {
                                alertDialog.dismiss();
                                TFUtils.showProgressBar(mContext, mContext.getResources().getString(R.string.please_wait));
                                String existingPilotId = flag ? mDataSet.get(trucksPosition).getPilotId() : null;
                                mDataSet.get(trucksPosition).setAssignedPilot(pilot.getPilotFirstName());
                                mDataSet.get(trucksPosition).setContactNo(pilot.getContactNumber());
                                mDataSet.get(trucksPosition).setPilotId(pilot.getPilotId());
                                mDataSet.get(trucksPosition).setPilotAvailability(pilot);
                                new WebServices().getChangePilot(mContext, obj, flag, existingPilotId, new ResultReceiver(null) {
                                    @Override
                                    protected void onReceiveResult(int resultCode, Bundle resultData) {
                                        super.onReceiveResult(resultCode, resultData);
                                        if (resultCode == TFConst.SUCCESS) {
                                            String reportingTime = mDataSet.get(trucksPosition).getEta();
                                            long reportingTimeMillis = Long.parseLong(reportingTime);
                                            reportingTimeMillis -= 3600000l; // adjust time to get correct value
                                            String dateTime[] = TFUtils.changeTime(Long.toString(reportingTimeMillis)).split("\\s+");
                                            String reason = "Vehicle No: " + mDataSet.get(trucksPosition).getVehicleNumber() +
                                                    "\nDate of Duty: " + dateTime[0] +
                                                    "\nTime of Duty: " + dateTime[1] +
                                                    "\nSource: " + TFUtils.getStringFromSP(mActivity, TFConst.HUB_NAME) +
                                                    "\nDestination: " + mDataSet.get(trucksPosition).getNextHub();
                                            TFSOSFragment.sendSMS(mContext, reason, mDataSet.get(trucksPosition).getContactNo());
                                            notifyItemChanged(trucksPosition);
                                            TFTruckFragment.driverPlannedCount += 1;
                                            TFTruckFragment.drivetNotPlannedCount -= 1;
                                            TFTruckFragment.mDriversPlanned.setText(mContext.getResources().getString(R.string.drivers_planned) + " " + TFTruckFragment.driverPlannedCount);
                                            TFTruckFragment.mDriversNotPlanned.setText(mContext.getResources().getString(R.string.drivers_not_planned) + " " + TFTruckFragment.drivetNotPlannedCount);
                                            // Pilot has been assigned. Start delay tracking.
                                            mActivity.startDelayTracking(mDataSet.get(trucksPosition).getAssignedPilot(), mDataSet.get(trucksPosition).getEta());
                                        } else {
                                            Toast.makeText(mContext, mContext.getResources().getString(R.string.problem_assign_pilot), Toast.LENGTH_SHORT).show();
                                        }
                                        TFUtils.hideProgressBar();
                                    }
                                });
                            }
                        }
                    });

                } else {
                    Toast.makeText(mContext, "" + mContext.getResources().getString(R.string.issue_parsing_data), Toast.LENGTH_SHORT).show();
                }
                TFUtils.hideProgressBar();
            }
        });
    }

    public void assignWarehousePilotPTD(final int pos, final TruckDetails td, final boolean change) {
        Toast.makeText(mContext, "Warehouse pilot at PTD not implemented", Toast.LENGTH_SHORT).show();
    }

    public void releasePilotAlertDialog(final int position, TruckDetails obj) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(mContext);
        dialogBuilder.setTitle(Html.fromHtml(String.format(mContext.getString(R.string.are_you_sure_release), obj.getAssignedPilot())));
        dialogBuilder.setPositiveButton(mContext.getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                new WebServices().getPilotRelease(mContext, mDataSet.get(position).getCurrentHub(), mDataSet.get(position).getPilotId(), mDataSet.get(position).getVehicleTrackingId(), new ResultReceiver(null) {
                    @Override
                    protected void onReceiveResult(int resultCode, Bundle resultData) {
                        super.onReceiveResult(resultCode, resultData);
                        if (resultCode == 200) {
                            String reportingTime = mDataSet.get(position).getEta();
                            long reportingTimeMillis = Long.parseLong(reportingTime);
                            reportingTimeMillis -= 3600000l; // adjust time to get correct value
                            String dateTime[] = TFUtils.changeTime(Long.toString(reportingTimeMillis)).split("\\s+");
                            String reason = "Duty Cancelled..\nVehicle No: " + mDataSet.get(position).getVehicleNumber() +
                                    "\nDate of Duty: " + dateTime[0] +
                                    "\nTime of Duty: " + dateTime[1] +
                                    "\nSource: " + TFUtils.getStringFromSP(mActivity, TFConst.HUB_NAME) +
                                    "\nDestination: " + mDataSet.get(position).getNextHub();


                            TFSOSFragment.sendSMS(mContext, reason, mDataSet.get(position).getContactNo());
                            //Pilot is released, stop delay tracking.
                            mActivity.stopDelayTracking(mDataSet.get(position).getAssignedPilot());

                            mDataSet.get(position).setAssignedPilot(null);
                            notifyItemChanged(position);
                            TFTruckFragment.driverPlannedCount -= 1;
                            TFTruckFragment.drivetNotPlannedCount += 1;
                            TFTruckFragment.mDriversPlanned.setText(mContext.getResources().getString(R.string.drivers_planned) + " " + TFTruckFragment.driverPlannedCount);
                            TFTruckFragment.mDriversNotPlanned.setText(mContext.getResources().getString(R.string.drivers_not_planned) + " " + TFTruckFragment.drivetNotPlannedCount);
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

    private boolean isLastHub(TruckDetails td) {
        if (td.getNextHub() == null || td.getNextHub().equals("null") || td.getNextHub().equals(""))
            return true;
        else return false;
    }

    private boolean isInPTDNoEntry(String eta) {
        long etaMillis = Long.parseLong(eta);
        Calendar calendar = Calendar.getInstance();
        etaMillis -= calendar.getTimeZone().getRawOffset();
        calendar.setTimeInMillis(etaMillis);
        int h = calendar.get(Calendar.HOUR_OF_DAY);
        if ((h >= 7 && h <= 10) || (h >= 16 && h <= 20)) return true;// 7 to 11 AM and 4 to 9 PM
        else return false;
    }

    public class VehicleInHubYesListener implements View.OnClickListener {

        private TruckDetails td;
        private ViewHolderWithPilot holder;

        public VehicleInHubYesListener(TruckDetails td, ViewHolderWithPilot vp) {
            this.td = td;
            holder = vp;
        }

        @Override
        public void onClick(View v) {
            td.setVehicleInHub("true");
            holder.mChecklist.setImageDrawable(mContext.getResources().getDrawable(R.drawable.checklist_selector));
            holder.mChecklist.setClickable(true);
            holder.mChecklist.setEnabled(true);
            notifyItemChanged(holder.getAdapterPosition());
            mWebServices.getVehicleInHub(td.getVehicleTrackingId(), "true");
        }
    }

    public class VehicleInHubNoListener implements View.OnClickListener {

        private TruckDetails td;
        private ViewHolderWithPilot holder;

        public VehicleInHubNoListener(TruckDetails td, ViewHolderWithPilot vp) {
            this.td = td;
            holder = vp;
        }

        @Override
        public void onClick(View v) {
            td.setVehicleInHub("false");
            holder.mChecklist.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_check_list_disabled));
            holder.mChecklist.setClickable(false);
            holder.mChecklist.setEnabled(false);
            notifyItemChanged(holder.getAdapterPosition());
            mWebServices.getVehicleInHub(td.getVehicleTrackingId(), "false");
        }
    }

    public class PilotInHubYesListener implements View.OnClickListener {

        private TruckDetails td;
        private ViewHolderWithPilot holder;

        public PilotInHubYesListener(TruckDetails td, ViewHolderWithPilot vp) {
            this.td = td;
            holder = vp;
        }

        @Override
        public void onClick(View v) {
            showPilotInHubAlertDialog(td.getAssignedPilot(),
                    holder.getAdapterPosition(),
                    holder.mRadioPilotInHubYes,
                    holder.mRadioPilotInHubNo,
                    td.getVehicleNumber(),
                    td.getPilotId(),
                    holder);
        }
    }

    public class PilotInHubNoListener implements View.OnClickListener {

        private TruckDetails td;
        private int position;

        public PilotInHubNoListener(TruckDetails td, int pos) {
            this.td = td;
            position = pos;
        }

        @Override
        public void onClick(View v) {
            td.setPilotInHub("false");
            notifyItemChanged(position);
            // Pilot hasn't arrived
            mActivity.startDelayTracking(td.getAssignedPilot(), td.getEta());
        }
    }

    private class AssignedPilotListener implements View.OnClickListener {
        private TruckDetails td;
        private int position;

        public AssignedPilotListener(TruckDetails td, int pos) {
            this.td = td;
            position = pos;
        }

        @Override
        public void onClick(View v) {
            if (td.getAssignedPilot() == null || td.getAssignedPilot().trim().equalsIgnoreCase("") || TextUtils.isEmpty(td.getAssignedPilot()) || td.getAssignedPilot().equalsIgnoreCase("null")) {
                if (TFUtils.getStringFromSP(mContext, TFConst.HUB_NAME).equals("PTD") && isInPTDNoEntry(td.getEta())) {
                    assignWarehousePilotPTD(position, td, false);
                    return;
                } else if (isLastHub(td)) {
                    Toast.makeText(mContext, mContext.getResources().getString(R.string.final_hub_no_need_toassign_pilot), Toast.LENGTH_SHORT).show();
                    return;
                }
                assignPilotAlertDialog(position, td, false);
            } else showPilotAssignAlertDialog(position, td);
        }
    }

    private class ChecklistListener implements View.OnClickListener {
        private TruckDetails td;
        private ViewHolderWithPilot holder;

        public ChecklistListener(TruckDetails td, ViewHolderWithPilot vp) {
            this.td = td;
            holder = vp;
        }

        @Override
        public void onClick(View v) {
            Bundle bundle = new Bundle();
            bundle.putString("vehicle_number", holder.mVehicleNumber.getText().toString());
            bundle.putString("vehicleTrackingId", td.getVehicleTrackingId());
            //  mTfTruckFragment.startFragment(R.layout.fragment_check_list, bundle);
            TFUtils.showProgressBar(mContext, mContext.getResources().getString(R.string.please_wait));
            WebServices mWebServices = new WebServices();
            mWebServices.getChecklistDetailsNew(mContext, new ResultReceiver(null) {
                @Override
                protected void onReceiveResult(int resultCode, Bundle resultData) {
                    if (resultCode == TFConst.SUCCESS) {

                        String responseStr = resultData.getString("response");

                        Toast.makeText(mContext, "Response: " + responseStr, Toast.LENGTH_SHORT).show();

                        Log.e("Lucky Adapter",responseStr);
//
//                        NewChecklist newChecklist = new NewChecklist();
//                        newChecklist = new Gson().fromJson(responseStr,NewChecklist.class);

                        if(responseStr != null)
                        {
                            Toast.makeText(mContext,"Not Null",Toast.LENGTH_SHORT).show();
                        }else
                        {
                            Toast.makeText(mContext,"Null",Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        Toast.makeText(mContext, "" + mContext.getResources().getString(R.string.issue_parsing_data), Toast.LENGTH_SHORT).show();
                    }
                    TFUtils.hideProgressBar();

                }
            });


            mTfTruckFragment.startFragment(R.layout.fragment_base_checklist, bundle);
        }
    }

    private class VehicleNumberListener implements View.OnClickListener {
        private String tid;
        private String number;

        public VehicleNumberListener (TruckDetails td) {
            tid = td.getVehicleTrackingId();
            number = td.getVehicleNumber();
        }

        @Override
        public void onClick(View v) {
            /*TFUtils.showProgressBar(mContext, "Fetching data...");
            new WebServices().getPreviousChecklist(tid, new ResultReceiver(null) {
                @Override
                protected void onReceiveResult(int resultCode, Bundle resultData) {
                    TFUtils.hideProgressBar();
                    if (resultCode == TFConst.SUCCESS) {
                        //NewChecklist c = new Gson().fromJson(resultData.getString("response"), NewChecklist.class);*/
            Bundle b = new Bundle();
            b.putString("vehicle_number", number);
            b.putString("tracking_id", tid);
            //b.putString("json",resultData.getString("response"));
            //b.putSerializable("checklist",c);
            mTfTruckFragment.startFragment(R.layout.fragment_prev_checklist, b);/*
                    } else {
                        Toast.makeText(mContext, "" + mContext.getResources().getString(R.string.issue_parsing_data), Toast.LENGTH_SHORT).show();
                    }
                }
            });*/
        }
    }

    public class ViewHolderWithButton extends RecyclerView.ViewHolder {
        public TextView mVehicleNumber;
        public TextView mClient;
        public TextView mVehicleRoute;
        public TextView mEta;
        public Button mAssignPilot;

        public ViewHolderWithButton(View v) {
            super(v);
            mVehicleNumber = (TextView) v.findViewById(R.id.vehicle_number);
            mVehicleRoute = (TextView) v.findViewById(R.id.vehicle_route);
            mClient = (TextView) v.findViewById(R.id.client);
            mEta = (TextView) v.findViewById(R.id.eta);
            mAssignPilot = (Button) v.findViewById(R.id.assign_pilot);
        }
    }

    public class ViewHolderWithPilot extends RecyclerView.ViewHolder {
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

        public ViewHolderWithPilot(View view) {
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
