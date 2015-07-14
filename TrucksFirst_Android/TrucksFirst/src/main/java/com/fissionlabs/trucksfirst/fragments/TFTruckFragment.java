package com.fissionlabs.trucksfirst.fragments;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.fissionlabs.trucksfirst.R;
import com.fissionlabs.trucksfirst.pojo.TFTruckDetailsPojo;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class TFTruckFragment extends Fragment {

    private ListView mTruckDetailsListView;
    private ArrayList<TFTruckDetailsPojo> truckDetailsPojoArrayList = new ArrayList<>();
    private CustomTrucksAdapter customTrucksAdapter;
    private TFTruckDetailsPojo tfTruckDetailsPojo;
    private FragmentManager fm;


    public TFTruckFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_truck,container,false);
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        mTruckDetailsListView = (ListView)view.findViewById(R.id.truck_details_list);
        manualDataToList();
        customTrucksAdapter = new CustomTrucksAdapter(getActivity(),truckDetailsPojoArrayList);
        mTruckDetailsListView.setAdapter(customTrucksAdapter);

        return view;
    }

    public class CustomTrucksAdapter extends ArrayAdapter<TFTruckDetailsPojo>{

        private Context context;
        private ArrayList<TFTruckDetailsPojo> truckDetailsList = new ArrayList<TFTruckDetailsPojo>();

        public CustomTrucksAdapter(Context context,ArrayList<TFTruckDetailsPojo> truckDetailsList) {
            super(context, R.layout.truck_details_item, truckDetailsList);
            this.context = context;
            this.truckDetailsList = truckDetailsList;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if(convertView==null)
            {
                convertView = LayoutInflater.from(context).inflate(R.layout.truck_details_item,null);
                holder = new ViewHolder();
                holder.mVehicleNumber = (TextView)convertView.findViewById(R.id.vehicle_number);
                holder.mVehileRoute = (TextView)convertView.findViewById(R.id.vehicle_route);
                holder.mEta = (TextView)convertView.findViewById(R.id.eta);
                holder.mAssignedPilot = (TextView)convertView.findViewById(R.id.assigned_pilot);
                holder.mPolitInHub = (RadioGroup)convertView.findViewById(R.id.polit_in_hub);
                holder.mRadioPolitInHubYes = (RadioButton)convertView.findViewById(R.id.polit_in_hub_yes);
                holder.mVehicleInHub = (RadioGroup)convertView.findViewById(R.id.vehicle_in_hub);
                holder.mChecklist = (ImageView)convertView.findViewById(R.id.checklist);
                convertView.setTag(holder);
            }
            else
            {
                holder = (ViewHolder)convertView.getTag();
            }

            holder.mVehicleNumber.setText(truckDetailsList.get(position).getVehicleNumber());
            holder.mVehileRoute.setText(truckDetailsList.get(position).getVehicleRoute());
            holder.mEta.setText(truckDetailsList.get(position).getEta());
            holder.mAssignedPilot.setText(truckDetailsList.get(position).getAssignedPilot());

            holder.mRadioPolitInHubYes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showPilotInHubAlertDialog(truckDetailsList.get(position).getAssignedPilot());
                }
            });

            holder.mAssignedPilot.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    navigate(new TFPilotAvailabilityFragment(),"TFPilotAvailabilityFragment");

                }
            });

            return convertView;
        }

        public class ViewHolder{

            TextView mVehicleNumber;
            TextView mVehileRoute;
            TextView mEta;
            TextView mAssignedPilot;
            RadioGroup mVehicleInHub;
            RadioGroup mPolitInHub;
            ImageView mChecklist;
            RadioButton mRadioPolitInHubYes;
            RadioButton mRadioPolitInHubNo;


        }
    }

    public void manualDataToList()
    {
        tfTruckDetailsPojo = new TFTruckDetailsPojo("HR55V1234","FKT:  PTD - BWD","28/07/2015 13:30","RAJ",true,true,"Fill Checklist");
        truckDetailsPojoArrayList.add(tfTruckDetailsPojo);
        tfTruckDetailsPojo = new TFTruckDetailsPojo("HR55V1234","FKT:  PTD - BWD","28/07/2015 13:30","BALRAM",true,true,"Fill Checklist");
        truckDetailsPojoArrayList.add(tfTruckDetailsPojo);
        tfTruckDetailsPojo = new TFTruckDetailsPojo("HR55V1234","FKT:  PTD - BWD","28/07/2015 13:30","OM",true,true,"Fill Checklist");
        truckDetailsPojoArrayList.add(tfTruckDetailsPojo);
        tfTruckDetailsPojo = new TFTruckDetailsPojo("HR55V1234","FKT:  PTD - BWD","28/07/2015 13:30","AKBAR",true,true,"Fill Checklist");
        truckDetailsPojoArrayList.add(tfTruckDetailsPojo);
        tfTruckDetailsPojo = new TFTruckDetailsPojo("HR55V1234","FKT:  PTD - BWD","28/07/2015 13:30","RAJ",true,true,"Fill Checklist");
        truckDetailsPojoArrayList.add(tfTruckDetailsPojo);
        tfTruckDetailsPojo = new TFTruckDetailsPojo("HR55V1234","FKT:  PTD - BWD","28/07/2015 13:30","RAJ",true,true,"Fill Checklist");
        truckDetailsPojoArrayList.add(tfTruckDetailsPojo);
        tfTruckDetailsPojo = new TFTruckDetailsPojo("HR55V1234","FKT:  PTD - BWD","28/07/2015 13:30","RAJ",true,true,"Fill Checklist");
        truckDetailsPojoArrayList.add(tfTruckDetailsPojo);
        tfTruckDetailsPojo = new TFTruckDetailsPojo("HR55V1234","FKT:  PTD - BWD","28/07/2015 13:30","RAJ",true,true,"Fill Checklist");
        truckDetailsPojoArrayList.add(tfTruckDetailsPojo);
        tfTruckDetailsPojo = new TFTruckDetailsPojo("HR55V1234","FKT:  PTD - BWD","28/07/2015 13:30","RAJ",true,true,"Fill Checklist");
        truckDetailsPojoArrayList.add(tfTruckDetailsPojo);
        tfTruckDetailsPojo = new TFTruckDetailsPojo("HR55V1234","FKT:  PTD - BWD","28/07/2015 13:30","RAJ",true,true,"Fill Checklist");
        truckDetailsPojoArrayList.add(tfTruckDetailsPojo);
        tfTruckDetailsPojo = new TFTruckDetailsPojo("HR55V1234","FKT:  PTD - BWD","28/07/2015 13:30","RAJ",true,true,"Fill Checklist");
        truckDetailsPojoArrayList.add(tfTruckDetailsPojo);
        tfTruckDetailsPojo = new TFTruckDetailsPojo("HR55V1234","FKT:  PTD - BWD","28/07/2015 13:30","AKBAR",true,true,"Fill Checklist");
        truckDetailsPojoArrayList.add(tfTruckDetailsPojo);
        tfTruckDetailsPojo = new TFTruckDetailsPojo("HR55V1234","FKT:  PTD - BWD","28/07/2015 13:30","RAJ",true,true,"Fill Checklist");
        truckDetailsPojoArrayList.add(tfTruckDetailsPojo);
        tfTruckDetailsPojo = new TFTruckDetailsPojo("HR55V1234","FKT:  PTD - BWD","28/07/2015 13:30","RAJ",true,true,"Fill Checklist");
        truckDetailsPojoArrayList.add(tfTruckDetailsPojo);
        tfTruckDetailsPojo = new TFTruckDetailsPojo("HR55V1234","FKT:  PTD - BWD","28/07/2015 13:30","RAJ",true,true,"Fill Checklist");
        truckDetailsPojoArrayList.add(tfTruckDetailsPojo);
        tfTruckDetailsPojo = new TFTruckDetailsPojo("HR55V1234","FKT:  PTD - BWD","28/07/2015 13:30","RAJ",true,true,"Fill Checklist");
        truckDetailsPojoArrayList.add(tfTruckDetailsPojo);
        tfTruckDetailsPojo = new TFTruckDetailsPojo("HR55V1234","FKT:  PTD - BWD","28/07/2015 13:30","RAJ",true,true,"Fill Checklist");
        truckDetailsPojoArrayList.add(tfTruckDetailsPojo);
        tfTruckDetailsPojo = new TFTruckDetailsPojo("HR55V1234","FKT:  PTD - BWD","28/07/2015 13:30","RAJ",true,true,"Fill Checklist");
        truckDetailsPojoArrayList.add(tfTruckDetailsPojo);
        tfTruckDetailsPojo = new TFTruckDetailsPojo("HR55V1234","FKT:  PTD - BWD","28/07/2015 13:30","RAJ",true,true,"Fill Checklist");
        truckDetailsPojoArrayList.add(tfTruckDetailsPojo);

    }

    public void showPilotInHubAlertDialog(String title)
    {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.pilot_in_hub_alert, null);
        dialogBuilder.setView(dialogView);
        dialogBuilder.setTitle(title);
        dialogBuilder.setPositiveButton("Save", null);
        dialogBuilder.setNegativeButton("Cancel", null);

        AlertDialog alertDialog = dialogBuilder.create();

        alertDialog.show();
    }

    void navigate(Fragment frag, String tag)	{
        fm = getActivity().getSupportFragmentManager();
        if (fm != null)	{
            FragmentTransaction ft = fm.beginTransaction();
            ft.add(R.id.frame_container, frag, tag);
            ft.addToBackStack(null);
            ft.commit();
        }
    }

}
