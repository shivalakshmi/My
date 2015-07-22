package com.fissionlabs.trucksfirst.fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.fissionlabs.trucksfirst.R;
import com.fissionlabs.trucksfirst.common.TFCommonFragment;
import com.fissionlabs.trucksfirst.model.TruckDetails;
import com.fissionlabs.trucksfirst.pojo.TFTruckDetailsPojo;
import com.fissionlabs.trucksfirst.webservices.WebServices;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class TFTruckFragment extends TFCommonFragment {

    private ListView mTruckDetailsListView;
    private ArrayList<TFTruckDetailsPojo> mTruckList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_truck, container, false);
        mTruckDetailsListView = (ListView) view.findViewById(R.id.truck_details_list);
        WebServices webServices = new WebServices();
        webServices.getTruckDetails(getActivity(), new ResultReceiver(null) {
            @Override
            protected void onReceiveResult(int resultCode, Bundle resultData) {

                String responseStr = resultData.getString("response").contains("ï»¿") ? resultData.getString("response").replaceAll("ï»¿", "") : resultData.getString("response");

                Type listType = new TypeToken<ArrayList<TruckDetails>>(){}.getType();
                ArrayList<TruckDetails> myModelList = new Gson().fromJson(responseStr, listType);
                mTruckDetailsListView.setAdapter(new CustomTrucksAdapter(getActivity(),myModelList));
            }
        });

        return view;
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
        dialogBuilder.setTitle(title);
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

        alertDialog.show();
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
            ViewHolder holder;
            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(R.layout.truck_details_item, null);
                holder = new ViewHolder();

                holder.mVehicleNumber = (TextView) convertView.findViewById(R.id.vehicle_number);
                holder.mVehicleRoute = (TextView) convertView.findViewById(R.id.vehicle_route);
                holder.mEta = (TextView) convertView.findViewById(R.id.eta);
                holder.mAssignedPilot = (TextView) convertView.findViewById(R.id.assigned_pilot);
                holder.mPolitInHub = (RadioGroup) convertView.findViewById(R.id.polit_in_hub);
                holder.mRadioPolitInHubYes = (RadioButton) convertView.findViewById(R.id.polit_in_hub_yes);
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

            if(truckDetailsList.get(position).getAssignedPilot().equals("null"))
            {
                holder.mVehicleNumber.setTextColor(getResources().getColor(android.R.color.holo_red_dark));
                holder.mVehicleRoute.setTextColor(getResources().getColor(android.R.color.holo_red_dark));
                holder.mEta.setTextColor(getResources().getColor(android.R.color.holo_red_dark));
                holder.mAssignedPilot.setVisibility(View.GONE);
                holder.mAssignedPilot.setClickable(true);
                holder.mPolitInHub.setVisibility(View.GONE);
                holder.mVehicleInHub.setVisibility(View.GONE);
                holder.mChecklist.setVisibility(View.GONE);

            }
            else
            {
                holder.mVehicleNumber.setTextColor(getResources().getColor(android.R.color.black));
                holder.mVehicleRoute.setTextColor(getResources().getColor(android.R.color.black));
                holder.mEta.setTextColor(getResources().getColor(android.R.color.black));
                holder.mVehicleNumber.setText(truckDetailsList.get(position).getVehicleNumber());
                holder.mVehicleRoute.setText(truckDetailsList.get(position).getVehicleRoute());
                holder.mEta.setText(truckDetailsList.get(position).getEta());
                holder.mAssignedPilot.setText(truckDetailsList.get(position).getAssignedPilot());
                holder.mAssignedPilot.setVisibility(View.VISIBLE);
                holder.mAssignedPilot.setClickable(false);
                holder.mPolitInHub.setVisibility(View.VISIBLE);
                holder.mVehicleInHub.setVisibility(View.VISIBLE);
                holder.mChecklist.setVisibility(View.VISIBLE);
            }


            holder.mRadioPolitInHubYes.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    showPilotInHubAlertDialog(truckDetailsList.get(position).getAssignedPilot());
                }
            });

            holder.mAssignedPilot.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startFragment(R.layout.fragment_pilot);

                    //navigate(new TFPilotAvailabilityFragment(),"TFPilotAvailabilityFragment");

                }
            });

            holder.mChecklist.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startFragment(R.layout.fragment_check_list);
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
            RadioGroup mPolitInHub;
            ImageView mChecklist;
            RadioButton mRadioPolitInHubYes;
            RadioButton mRadioPolitInHubNo;
        }
    }

}
