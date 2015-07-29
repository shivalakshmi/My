package com.fissionlabs.trucksfirst.fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.fissionlabs.trucksfirst.R;
import com.fissionlabs.trucksfirst.common.TFCommonFragment;
import com.fissionlabs.trucksfirst.common.TFConst;
import com.fissionlabs.trucksfirst.model.PilotAvailability;
import com.fissionlabs.trucksfirst.model.TruckDetails;
import com.fissionlabs.trucksfirst.pojo.TFTruckDetailsPojo;
import com.fissionlabs.trucksfirst.util.TFUtils;
import com.fissionlabs.trucksfirst.webservices.WebServices;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class TFTruckFragment extends TFCommonFragment implements TFConst, View.OnClickListener {

    private ListView mTruckDetailsListView;
    private TextView mTVVehicleNo;
    private TextView mTVVehicleRoute;
    private TextView mTVEta;
    private TextView mTVAssignedPilot;
    private ArrayList<TruckDetails> myModelList = null;
    private ArrayList<TFTruckDetailsPojo> mTruckList = new ArrayList<>();
    private WebServices webServices;
    private ArrayList<PilotAvailability> pilotAvailabilityList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_truck, container, false);
        mTruckDetailsListView = (ListView) view.findViewById(R.id.truck_details_list);
        mTVVehicleNo = (TextView) view.findViewById(R.id.vehicle_no);
        mTVVehicleRoute = (TextView) view.findViewById(R.id.vehicle_route);
        mTVEta = (TextView) view.findViewById(R.id.eta);
        mTVAssignedPilot = (TextView) view.findViewById(R.id.assign_pilot);
        mTVAssignedPilot.setOnClickListener(this);
        mTVEta.setOnClickListener(this);
        mTVVehicleRoute.setOnClickListener(this);
        mTVVehicleNo.setOnClickListener(this);
        TFUtils.showProgressBar(getActivity(), getResources().getString(R.string.please_wait));
        webServices = new WebServices();
        webServices.getTruckDetails(getActivity(), new ResultReceiver(null) {
            @Override
            protected void onReceiveResult(int resultCode, Bundle resultData) {
                if (resultCode == SUCCESS) {

                    String responseStr = resultData.getString("response");

                    if (responseStr != null) {
                        String temp[] = responseStr.split("\\[");
                        if (temp.length > 1)
                            responseStr = "[" + temp[1];

                        Type listType = new TypeToken<ArrayList<TruckDetails>>() {
                        }.getType();
                        myModelList = new Gson().fromJson(responseStr, listType);
                        mTruckDetailsListView.setAdapter(new CustomTrucksAdapter(getActivity(), myModelList));
                    }
                } else {
                    Toast.makeText(getActivity(), "" + getResources().getString(R.string.issue_parsing_data), Toast.LENGTH_SHORT).show();
                }
                TFUtils.hideProgressBar();
            }
        });

        return view;
    }

    @Override
    public void onClick(View v) {
        if (myModelList != null && myModelList.size() > 1) {
            changeIconStatus(v);
        }
    }

    private void changeIconStatus(View v) {
        Drawable drawables[];
        Bitmap bitmap;
        int asc_desc;
        switch (v.getId()) {
            case R.id.vehicle_no:
                drawables = mTVVehicleNo.getCompoundDrawables();
                bitmap = ((BitmapDrawable) drawables[0]).getBitmap();
                asc_desc = getPresentIcon(bitmap, mTVVehicleNo);
                mTVVehicleRoute.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_arrow_right, 0, 0, 0);
                mTVEta.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_arrow_right, 0, 0, 0);
                mTVAssignedPilot.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_arrow_right, 0, 0, 0);
                Collections.sort(myModelList, new CustomComparator(Sort.VEHICLE_NO, asc_desc));
                ((CustomTrucksAdapter) mTruckDetailsListView.getAdapter()).notifyDataSetChanged();
                break;
            case R.id.vehicle_route:
                drawables = mTVVehicleRoute.getCompoundDrawables();
                bitmap = ((BitmapDrawable) drawables[0]).getBitmap();
                asc_desc = getPresentIcon(bitmap, mTVVehicleRoute);
                mTVVehicleNo.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_arrow_right, 0, 0, 0);
                mTVEta.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_arrow_right, 0, 0, 0);
                mTVAssignedPilot.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_arrow_right, 0, 0, 0);
                Collections.sort(myModelList, new CustomComparator(Sort.VEHICLE_ROUTE, asc_desc));
                ((CustomTrucksAdapter) mTruckDetailsListView.getAdapter()).notifyDataSetChanged();
                break;
            case R.id.eta:
                drawables = mTVEta.getCompoundDrawables();
                bitmap = ((BitmapDrawable) drawables[0]).getBitmap();
                asc_desc = getPresentIcon(bitmap, mTVEta);
                mTVVehicleNo.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_arrow_right, 0, 0, 0);
                mTVVehicleRoute.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_arrow_right, 0, 0, 0);
                mTVAssignedPilot.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_arrow_right, 0, 0, 0);
                Collections.sort(myModelList, new CustomComparator(Sort.ETA, asc_desc));
                ((CustomTrucksAdapter) mTruckDetailsListView.getAdapter()).notifyDataSetChanged();
                break;
            case R.id.assign_pilot:
                drawables = mTVAssignedPilot.getCompoundDrawables();
                bitmap = ((BitmapDrawable) drawables[0]).getBitmap();
                asc_desc = getPresentIcon(bitmap, mTVAssignedPilot);
                mTVVehicleNo.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_arrow_right, 0, 0, 0);
                mTVVehicleRoute.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_arrow_right, 0, 0, 0);
                mTVEta.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_arrow_right, 0, 0, 0);
                Collections.sort(myModelList, new CustomComparator(Sort.ASSIGNED_PILOT, asc_desc));
                ((CustomTrucksAdapter) mTruckDetailsListView.getAdapter()).notifyDataSetChanged();
                break;
        }
    }

    private int getPresentIcon(Bitmap bitmap, TextView tv) {
        Bitmap rightBitmap = ((BitmapDrawable) getResources().getDrawable(R.drawable.ic_arrow_right)).getBitmap();
        Bitmap upBitmap = ((BitmapDrawable) getResources().getDrawable(R.drawable.ic_arrow_up)).getBitmap();
        if (bitmap == rightBitmap) {
            tv.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_arrow_up, 0, 0, 0);
            return 0;
        } else if (bitmap == upBitmap) {
            tv.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_arrow_down, 0, 0, 0);
            return 1;
        } else {
            tv.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_arrow_up, 0, 0, 0);
            return 0;  //down bitmap
        }
    }

    public void showPilotInHubAlertDialog(String title) {

        final CharSequence items[] = {getString(R.string.driving_licence),
                getString(R.string.uniform),
                getString(R.string.non_alcoholic)};

        final ArrayList<Integer> selectedItems = new ArrayList();
        boolean checkedItems[] = new boolean[items.length];

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        //LayoutInflater inflater = getActivity().getLayoutInflater();
        //   View dialogView = inflater.inflate(R.layout.pilot_in_hub_alert, null);
        //   dialogBuilder.setView(dialogView);
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
        dialogBuilder.setPositiveButton(getResources().getString(R.string.save), null);
        dialogBuilder.setNegativeButton(getResources().getString(R.string.cancel), null);

        AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.getWindow().setLayout(500, LinearLayout.LayoutParams.WRAP_CONTENT);

        alertDialog.show();
    }

    public void showPilotAssignAlertDialog(final String pilotName) {
        if (pilotName == null || pilotName.trim().equalsIgnoreCase("") || TextUtils.isEmpty(pilotName) || pilotName.equalsIgnoreCase("null")) {
            assignPilotAlertDialog();
        } else {
            final CharSequence items[] = {String.format(getString(R.string.pilot_contact_info), "\nMobile Number:9999999999"),
                    getString(R.string.pilot_change_pilot),
                    getString(R.string.pilot_release_pilot)};

            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
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
            dialogBuilder.setPositiveButton(getString(R.string.ok), null);
            AlertDialog alertDialog = dialogBuilder.create();
            alertDialog.setCanceledOnTouchOutside(true);
            alertDialog.getWindow().setLayout(500, LinearLayout.LayoutParams.WRAP_CONTENT);
            alertDialog.show();
        }
    }

    public void assignPilotAlertDialog() {
        TFUtils.showProgressBar(getActivity(), getString(R.string.loading));
        webServices.getPilotAvailability(getActivity(), new ResultReceiver(null) {
            @Override
            protected void onReceiveResult(int resultCode, Bundle resultData) {
                if (resultCode == TFConst.SUCCESS) {
                    String responseStr = resultData.getString("response");

                    Type listType = new TypeToken<ArrayList<PilotAvailability>>() {
                    }.getType();

                    pilotAvailabilityList = new Gson().fromJson(responseStr, listType);
                    List<String> listItems = new ArrayList<>();

                    for (int i = 0; i < pilotAvailabilityList.size(); i++) {
                        listItems.add(pilotAvailabilityList.get(i).getPilotName() + "  -  " + pilotAvailabilityList.get(i).getPilotAvailabilityStatus());
                    }

                    final CharSequence[] items = listItems.toArray(new CharSequence[listItems.size()]);
                    AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
                    dialogBuilder.setSingleChoiceItems(items, -1, null);
                    dialogBuilder.setTitle(Html.fromHtml("<b>" + getString(R.string.pilot_availability_title) + "</b>"));
                    dialogBuilder.setPositiveButton(getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            //write login here for pilot assignment.
                        }
                    });
                    dialogBuilder.setNegativeButton(getResources().getString(R.string.cancel), null);

                    AlertDialog alertDialog = dialogBuilder.create();
                    alertDialog.getWindow().setLayout(500, LinearLayout.LayoutParams.WRAP_CONTENT);
                    alertDialog.show();

                } else {
                    Toast.makeText(getActivity(), "" + getResources().getString(R.string.issue_parsing_data), Toast.LENGTH_SHORT).show();
                }
                TFUtils.hideProgressBar();
            }
        });
    }

    public void releasePilotAlertDialog(String pilotName) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        dialogBuilder.setTitle(Html.fromHtml(String.format(getString(R.string.are_you_sure_release), pilotName)));
        dialogBuilder.setPositiveButton(getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                //write login here for pilot release.
            }
        });
        dialogBuilder.setNegativeButton(getResources().getString(R.string.no), null);
        final AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.getWindow().setLayout(500, 200);
        alertDialog.show();
    }

    private enum Sort {
        VEHICLE_NO,
        VEHICLE_ROUTE,
        ETA,
        ASSIGNED_PILOT
    }

    public class CustomTrucksAdapter extends ArrayAdapter<TruckDetails> {

        private Context context;
        private ArrayList<TruckDetails> truckDetailsList = new ArrayList<>();

        public CustomTrucksAdapter(Context context, ArrayList<TruckDetails> truckDetailsList) {
            super(context, R.layout.truck_details_item, truckDetailsList);
            this.context = context;
            this.truckDetailsList = truckDetailsList;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            final ViewHolder holder;
            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(R.layout.truck_details_item, parent, false);
                holder = new ViewHolder();

                holder.mVehicleNumber = (TextView) convertView.findViewById(R.id.vehicle_number);
                holder.mVehicleRoute = (TextView) convertView.findViewById(R.id.vehicle_route);
                holder.mEta = (TextView) convertView.findViewById(R.id.eta);
                holder.mAssignedPilot = (TextView) convertView.findViewById(R.id.assigned_pilot);
                holder.mPilotInHub = (RadioGroup) convertView.findViewById(R.id.polit_in_hub);
                holder.mRadioPilotInHubYes = (RadioButton) convertView.findViewById(R.id.polit_in_hub_yes);
                holder.mVehicleInHub = (RadioGroup) convertView.findViewById(R.id.vehicle_in_hub);
                holder.mChecklist = (ImageView) convertView.findViewById(R.id.checklist);

                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            holder.mVehicleNumber.setText(truckDetailsList.get(position).getVehicleNumber());
            holder.mVehicleRoute.setText(truckDetailsList.get(position).getVehicleRoute());
            holder.mEta.setText(truckDetailsList.get(position).getEta());
            holder.mAssignedPilot.setText(truckDetailsList.get(position).getAssignedPilot());
            if (truckDetailsList.get(position).getAssignedPilot() == null || truckDetailsList.get(position).getAssignedPilot().equalsIgnoreCase("null")) {
                holder.mVehicleNumber.setTextColor(getResources().getColor(android.R.color.holo_red_dark));
                holder.mVehicleRoute.setTextColor(getResources().getColor(android.R.color.holo_red_dark));
                holder.mEta.setTextColor(getResources().getColor(android.R.color.holo_red_dark));
                holder.mAssignedPilot.setText("");
                holder.mPilotInHub.setVisibility(View.GONE);
                holder.mVehicleInHub.setVisibility(View.GONE);
                holder.mChecklist.setVisibility(View.GONE);
            } else {
                holder.mVehicleNumber.setTextColor(getResources().getColor(android.R.color.black));
                holder.mVehicleRoute.setTextColor(getResources().getColor(android.R.color.black));
                holder.mEta.setTextColor(getResources().getColor(android.R.color.black));
                holder.mVehicleNumber.setText(truckDetailsList.get(position).getVehicleNumber());
                holder.mVehicleRoute.setText(truckDetailsList.get(position).getVehicleRoute());
                holder.mEta.setText(truckDetailsList.get(position).getEta());
                holder.mAssignedPilot.setText(truckDetailsList.get(position).getAssignedPilot());
                holder.mPilotInHub.setVisibility(View.VISIBLE);
                holder.mVehicleInHub.setVisibility(View.VISIBLE);
                holder.mChecklist.setVisibility(View.VISIBLE);
            }


            holder.mRadioPilotInHubYes.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    showPilotInHubAlertDialog(truckDetailsList.get(position).getAssignedPilot());
                }
            });

            holder.mAssignedPilot.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    startFragment(R.layout.fragment_pilot);
                    showPilotAssignAlertDialog(truckDetailsList.get(position).getAssignedPilot());
                }
            });

            holder.mChecklist.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle bundle = new Bundle();
                    bundle.putString("vehicle_number",holder.mVehicleNumber.getText().toString());
                    startFragment(R.layout.fragment_check_list, bundle);
                }
            });

            return convertView;
        }

        public class ViewHolder {

            TextView mVehicleNumber;
            TextView mVehicleRoute;
            TextView mEta;
            TextView mAssignedPilot;
            RadioGroup mVehicleInHub;
            RadioGroup mPilotInHub;
            ImageView mChecklist;
            RadioButton mRadioPilotInHubYes;
            RadioButton mRadioPilotInHubNo;
        }
    }

    public class CustomComparator implements Comparator<TruckDetails> {
        private Sort type;
        private int asc_desc;

        CustomComparator(Sort type, int asc_desc) {
            this.type = type;
            this.asc_desc = asc_desc;
        }

        @Override
        public int compare(TruckDetails o1, TruckDetails o2) {

            switch (type) {
                case VEHICLE_NO:
                    return asc_desc == 0 ? o1.getVehicleNumber().compareTo(o2.getVehicleNumber()) : o2.getVehicleNumber().compareTo(o1.getVehicleNumber());
                case VEHICLE_ROUTE:
                    return asc_desc == 0 ? o1.getVehicleRoute().compareTo(o2.getVehicleRoute()) : o2.getVehicleRoute().compareTo(o1.getVehicleRoute());
                case ETA:
                    return asc_desc == 0 ? o1.getEta().compareTo(o2.getEta()) : o2.getEta().compareTo(o1.getEta());
                case ASSIGNED_PILOT:
                    if (o1.getAssignedPilot() == null) {
                        if (o2.getAssignedPilot() == null)
                            return 0; //equal
                        else
                            return -1; // null is before other strings
                    } else {
                        if (o2.getAssignedPilot() == null)
                            return 1;  // all other strings are after null
                        else
                            return asc_desc == 0 ? o1.getAssignedPilot().compareTo(o2.getAssignedPilot()) : o2.getAssignedPilot().compareTo(o1.getAssignedPilot());
                    }
                default:
                    return 0;
            }

        }
    }
}
