package com.fissionlabs.trucksfirst.fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.support.annotation.Nullable;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.fissionlabs.trucksfirst.R;
import com.fissionlabs.trucksfirst.common.TFCommonFragment;
import com.fissionlabs.trucksfirst.common.TFConst;
import com.fissionlabs.trucksfirst.model.ClosedTrip;
import com.fissionlabs.trucksfirst.util.TFUtils;
import com.fissionlabs.trucksfirst.webservices.WebServices;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.FieldPosition;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Kanj on 24-09-2015.
 */
public class TFClosedTripsFragment  extends TFCommonFragment implements TFConst {
    private ArrayList<ClosedTrip> closedTrips;
    private RecyclerView listView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mHomeActivity.isHomeFragment = false;
        mHomeActivity.toolbar.setNavigationIcon(R.drawable.ic_back);
        mHomeActivity.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mHomeActivity.isHomeFragment == false)
                    mHomeActivity.onBackPressed();
                else
                    mHomeActivity.mDrawerLayout.openDrawer(GravityCompat.START);
            }
        });
        View view = inflater.inflate(R.layout.fragment_closed_trips, container, false);
        listView = (RecyclerView) view.findViewById(R.id.closed_trip_list);
        listView.setLayoutManager(new LinearLayoutManager(getActivity()));
        TFUtils.showProgressBar(getActivity(), "Loading...");
        getDataAndRefresh();
        return view;
    }

    public void getDataAndRefresh() {
        new WebServices().getClosedTrips(TFUtils.getStringFromSP(getActivity(),HUB_NAME), new ResultReceiver(null){
            @Override
            protected void onReceiveResult(int code, Bundle data) {
                if (code == TFConst.SUCCESS) {
                    String responseStr = data.getString("response");
                    Type listType = new TypeToken<ArrayList<ClosedTrip>>() {
                    }.getType();

                    ArrayList<ClosedTrip> closedTrips = new Gson().fromJson(responseStr, listType);
                    displayList(closedTrips, listView);
                } else {
                    TFUtils.hideProgressBar();
                    Toast.makeText(getActivity(), "" + getActivity().getResources().getString(R.string.issue_parsing_data), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void displayList(ArrayList<ClosedTrip> list, RecyclerView v) {
        TFUtils.hideProgressBar();
        closedTrips = list;
        ClosedTripAdapter adapter = new ClosedTripAdapter(list);
        v.setAdapter(adapter);
    }

    private class ClosedTripAdapter extends RecyclerView.Adapter<ClosedTripViewHolder> {
        private ArrayList<ClosedTrip> list;

        public ClosedTripAdapter(ArrayList<ClosedTrip> list) {
            this.list = list;
         }

        @Override
        public ClosedTripViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            Context context = parent.getContext();
            LayoutInflater inflater = LayoutInflater.from(context);
            View v = inflater.inflate(R.layout.closed_trip_item, parent, false);

            ClosedTripViewHolder holder = new ClosedTripViewHolder(v);
            return holder;
        }

        @Override
        public void onBindViewHolder(ClosedTripViewHolder holder, int position) {
            ClosedTrip trip = list.get(position);
            holder.truckNo.setText(trip.getTruckNumber());
            holder.tripCode.setText(trip.getTripCode());
            holder.route.setText(trip.getRoute());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
            String time;
            try {
                Date d = sdf.parse(trip.getArrivalTime());
                sdf.applyPattern("hh:mm a, dd-MM-yyyy");
                StringBuffer buf = new StringBuffer();
                buf = sdf.format(d, buf, new FieldPosition(0));
                time = buf.toString();
            } catch (ParseException pe) {
                pe.printStackTrace();
                time = trip.getArrivalTime();
            }
            holder.arrivalTime.setText(time);
            holder.podStatus.setChecked(trip.isPod()); //Should always be false

            //holder.podStatus.setOnClickListener(holder);
            holder.podStatus.setOnCheckedChangeListener(holder);
        }

        @Override
        public int getItemCount() {
            return list.size();
        }
    }

    private class ClosedTripViewHolder extends RecyclerView.ViewHolder implements CompoundButton.OnCheckedChangeListener {
        public TextView truckNo;
        public TextView tripCode;
        public TextView route;
        public TextView arrivalTime;
        public CheckBox podStatus;

        public ClosedTripViewHolder(View v) {
            super(v);
            truckNo = (TextView) v.findViewById(R.id.truck_no);
            tripCode = (TextView) v.findViewById(R.id.trip_code);
            route = (TextView) v.findViewById(R.id.route);
            arrivalTime = (TextView) v.findViewById(R.id.time);

            podStatus = (CheckBox) v.findViewById(R.id.pod_status);
        }

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            final CheckBox cb = (CheckBox) buttonView;
            if (!cb.isChecked()) {
                // This should never happen
                return;
            }
            final int pos = getLayoutPosition();
            final ClosedTrip trip = closedTrips.get(pos);
            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
            dialogBuilder.setTitle("Are you sure POD is submitted for\n" + trip.getTripCode() + " ?");
            dialogBuilder.setPositiveButton("POD Submitted", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    TFUtils.showProgressBar(getActivity(), "Loading...");
                    new WebServices().setClosedTripPOD(trip.getId(), new ResultReceiver(null) {
                        @Override
                        protected void onReceiveResult(int resultCode, Bundle resultData) {
                            super.onReceiveResult(resultCode, resultData);
                            if (resultCode == TFConst.SUCCESS) {
                                getDataAndRefresh();
                            } else {
                                Toast.makeText(getActivity(), "Could not set POD status.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            });
            dialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    cb.setChecked(false);
                    dialog.dismiss();
                }
            });
            final AlertDialog alertDialog = dialogBuilder.create();
            alertDialog.getWindow().setLayout(500, LinearLayout.LayoutParams.WRAP_CONTENT);
            alertDialog.show();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mHomeActivity.toolbar.setNavigationIcon(R.drawable.ic_menu);
        mHomeActivity.isHomeFragment = true;
        mHomeActivity.mActionBar.setDisplayShowTitleEnabled(false);
    }
}
