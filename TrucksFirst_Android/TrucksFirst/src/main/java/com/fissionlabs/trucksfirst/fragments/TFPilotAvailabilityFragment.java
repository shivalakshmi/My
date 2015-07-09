package com.fissionlabs.trucksfirst.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.fissionlabs.trucksfirst.R;
import com.fissionlabs.trucksfirst.pojo.TFPilotAvailabilityPojo;

import java.util.ArrayList;

/**
 * Created by Lakshmi on 09-07-2015.
 */
public class TFPilotAvailabilityFragment extends Fragment {

    private ListView mPilotAvailabilityListView;
    private CustomTrucksAdapter customTrucksAdapter;
    private ArrayList<TFPilotAvailabilityPojo> pilotAvailabityPojoArrayList = new ArrayList<>();
    private TFPilotAvailabilityPojo tfPilotAvailabilityPojo;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_truck,container,false);
        mPilotAvailabilityListView = (ListView)view.findViewById(R.id.truck_details_list);
        manualDataToList();
        View headerView = inflater.inflate(R.layout.pilot_availabilty_header,null);
        mPilotAvailabilityListView.addHeaderView(headerView);
        customTrucksAdapter = new CustomTrucksAdapter(getActivity(),pilotAvailabityPojoArrayList);
        mPilotAvailabilityListView.setAdapter(customTrucksAdapter);

        return view;
    }

    public class CustomTrucksAdapter extends ArrayAdapter<TFPilotAvailabilityPojo> {

        private Context context;
        private ArrayList<TFPilotAvailabilityPojo> truckDetailsList = new ArrayList<TFPilotAvailabilityPojo>();

        public CustomTrucksAdapter(Context context,ArrayList<TFPilotAvailabilityPojo> truckDetailsList) {
            super(context, R.layout.pilot_availability_item, truckDetailsList);
            this.context = context;
            this.truckDetailsList = truckDetailsList;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if(convertView==null)
            {
                convertView = LayoutInflater.from(context).inflate(R.layout.pilot_availability_item,null);
                holder = new ViewHolder();
                holder.mVehicleNumber = (TextView)convertView.findViewById(R.id.vehicle_number);
                holder.mVehileRoute = (TextView)convertView.findViewById(R.id.vehicle_route);
                holder.mEta = (TextView)convertView.findViewById(R.id.eta);
                holder.mAssignPilot = (TextView)convertView.findViewById(R.id.assign_pilot);
                holder.mPilotAvailability = (TextView)convertView.findViewById(R.id.pilot_availability);
                convertView.setTag(holder);
            }
            else
            {
                holder = (ViewHolder)convertView.getTag();
            }

            holder.mVehicleNumber.setText(truckDetailsList.get(position).getVehicleNumber());
            holder.mVehileRoute.setText(truckDetailsList.get(position).getVehicleRoute());
            holder.mEta.setText(truckDetailsList.get(position).getEta());
            holder.mAssignPilot.setText(truckDetailsList.get(position).getAssignPilot());
            holder.mPilotAvailability.setText(truckDetailsList.get(position).getPilotAvailability());



            return convertView;
        }

        public class ViewHolder{

            TextView mVehicleNumber;
            TextView mVehileRoute;
            TextView mEta;
            TextView mAssignPilot;
            TextView mPilotAvailability;


        }
    }


    public void manualDataToList()
    {
        tfPilotAvailabilityPojo = new TFPilotAvailabilityPojo("RAJ\tIDLE\tBWD","HR55V1234","FKT:  PTD - BWD","28/07/2015 13:30","RAJ");
        pilotAvailabityPojoArrayList.add(tfPilotAvailabilityPojo);
        tfPilotAvailabilityPojo = new TFPilotAvailabilityPojo("BALRAM\tIDLE\tBNG","HR55V1235","FKT:  PTD - BNG","28/07/2015 15:30","BALRAM");
        pilotAvailabityPojoArrayList.add(tfPilotAvailabilityPojo);
        tfPilotAvailabilityPojo = new TFPilotAvailabilityPojo("RAJ\tIDLE\tBWD","HR55V1234","FKT:  PTD - BWD","28/07/2015 13:30","RAJ");
        pilotAvailabityPojoArrayList.add(tfPilotAvailabilityPojo);
        tfPilotAvailabilityPojo = new TFPilotAvailabilityPojo("BALRAM\tIDLE\tBNG","HR55V1235","FKT:  PTD - BNG","28/07/2015 15:30","BALRAM");
        pilotAvailabityPojoArrayList.add(tfPilotAvailabilityPojo);
        tfPilotAvailabilityPojo = new TFPilotAvailabilityPojo("RAJ\tIDLE\tBWD","HR55V1234","FKT:  PTD - BWD","28/07/2015 13:30","RAJ");
        pilotAvailabityPojoArrayList.add(tfPilotAvailabilityPojo);
        tfPilotAvailabilityPojo = new TFPilotAvailabilityPojo("BALRAM\tIDLE\tBNG","HR55V1235","FKT:  PTD - BNG","28/07/2015 15:30","BALRAM");
        pilotAvailabityPojoArrayList.add(tfPilotAvailabilityPojo);
        tfPilotAvailabilityPojo = new TFPilotAvailabilityPojo("RAJ\tIDLE\tBWD","HR55V1234","FKT:  PTD - BWD","28/07/2015 13:30","RAJ");
        pilotAvailabityPojoArrayList.add(tfPilotAvailabilityPojo);
        tfPilotAvailabilityPojo = new TFPilotAvailabilityPojo("BALRAM\tIDLE\tBNG","HR55V1235","FKT:  PTD - BNG","28/07/2015 15:30","BALRAM");
        pilotAvailabityPojoArrayList.add(tfPilotAvailabilityPojo);
        tfPilotAvailabilityPojo = new TFPilotAvailabilityPojo("RAJ\tIDLE\tBWD","HR55V1234","FKT:  PTD - BWD","28/07/2015 13:30","RAJ");
        pilotAvailabityPojoArrayList.add(tfPilotAvailabilityPojo);
        tfPilotAvailabilityPojo = new TFPilotAvailabilityPojo("BALRAM\tIDLE\tBNG","HR55V1235","FKT:  PTD - BNG","28/07/2015 15:30","BALRAM");
        pilotAvailabityPojoArrayList.add(tfPilotAvailabilityPojo); tfPilotAvailabilityPojo = new TFPilotAvailabilityPojo("RAJ\tIDLE\tBWD","HR55V1234","FKT:  PTD - BWD","28/07/2015 13:30","RAJ");
        pilotAvailabityPojoArrayList.add(tfPilotAvailabilityPojo);
        tfPilotAvailabilityPojo = new TFPilotAvailabilityPojo("BALRAM\tIDLE\tBNG","HR55V1235","FKT:  PTD - BNG","28/07/2015 15:30","BALRAM");
        pilotAvailabityPojoArrayList.add(tfPilotAvailabilityPojo);


    }
}
