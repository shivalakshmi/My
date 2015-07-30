package com.fissionlabs.trucksfirst.fragments;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.fissionlabs.trucksfirst.R;
import com.fissionlabs.trucksfirst.adapters.TrucksAdapter;
import com.fissionlabs.trucksfirst.common.TFCommonFragment;
import com.fissionlabs.trucksfirst.common.TFConst;
import com.fissionlabs.trucksfirst.model.TruckDetails;
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
public class TFTruckFragment extends TFCommonFragment implements TFConst, View.OnClickListener, SearchView.OnQueryTextListener {

    private RecyclerView mTruckDetailsListView;
    private TextView mTVVehicleNo;
    private TextView mTVVehicleRoute;
    private TextView mTVEta;
    private TextView mTVAssignedPilot;
    private ArrayList<TruckDetails> mTrucksList = null;
    private WebServices mWebServices;
    private TFTruckFragment mTFragment;
    private TrucksAdapter mAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_truck, container, false);
        mTFragment = this;

        mTruckDetailsListView = (RecyclerView) view.findViewById(R.id.truck_details_list);
        mTruckDetailsListView.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mTruckDetailsListView.setLayoutManager(mLayoutManager);

        mTVVehicleNo = (TextView) view.findViewById(R.id.vehicle_no);
        mTVVehicleRoute = (TextView) view.findViewById(R.id.vehicle_route);
        mTVEta = (TextView) view.findViewById(R.id.eta);
        mTVAssignedPilot = (TextView) view.findViewById(R.id.assign_pilot);
        mTVAssignedPilot.setOnClickListener(this);
        mTVEta.setOnClickListener(this);
        mTVVehicleRoute.setOnClickListener(this);
        mTVVehicleNo.setOnClickListener(this);
        TFUtils.showProgressBar(getActivity(), getResources().getString(R.string.please_wait));
        mWebServices = new WebServices();
        mWebServices.getTruckDetails(getActivity(), new ResultReceiver(null) {
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
                        mTrucksList = new Gson().fromJson(responseStr, listType);
                        mAdapter = new TrucksAdapter(getActivity(), mTFragment, mTrucksList);
                        mTruckDetailsListView.setAdapter(mAdapter);
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
        if (mTrucksList != null && mTrucksList.size() > 1) {
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
                Collections.sort(mAdapter.getUpdatedList(), new CustomComparator(Sort.VEHICLE_NO, asc_desc));
                mTruckDetailsListView.getAdapter().notifyDataSetChanged();
                break;
            case R.id.vehicle_route:
                drawables = mTVVehicleRoute.getCompoundDrawables();
                bitmap = ((BitmapDrawable) drawables[0]).getBitmap();
                asc_desc = getPresentIcon(bitmap, mTVVehicleRoute);
                mTVVehicleNo.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_arrow_right, 0, 0, 0);
                mTVEta.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_arrow_right, 0, 0, 0);
                mTVAssignedPilot.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_arrow_right, 0, 0, 0);
                Collections.sort(mAdapter.getUpdatedList(), new CustomComparator(Sort.VEHICLE_ROUTE, asc_desc));
                mTruckDetailsListView.getAdapter().notifyDataSetChanged();
                break;
            case R.id.eta:
                drawables = mTVEta.getCompoundDrawables();
                bitmap = ((BitmapDrawable) drawables[0]).getBitmap();
                asc_desc = getPresentIcon(bitmap, mTVEta);
                mTVVehicleNo.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_arrow_right, 0, 0, 0);
                mTVVehicleRoute.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_arrow_right, 0, 0, 0);
                mTVAssignedPilot.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_arrow_right, 0, 0, 0);
                Collections.sort(mAdapter.getUpdatedList(), new CustomComparator(Sort.ETA, asc_desc));
                mTruckDetailsListView.getAdapter().notifyDataSetChanged();
                break;
            case R.id.assign_pilot:
                drawables = mTVAssignedPilot.getCompoundDrawables();
                bitmap = ((BitmapDrawable) drawables[0]).getBitmap();
                asc_desc = getPresentIcon(bitmap, mTVAssignedPilot);
                mTVVehicleNo.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_arrow_right, 0, 0, 0);
                mTVVehicleRoute.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_arrow_right, 0, 0, 0);
                mTVEta.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_arrow_right, 0, 0, 0);
                Collections.sort(mAdapter.getUpdatedList(), new CustomComparator(Sort.ASSIGNED_PILOT, asc_desc));
                mTruckDetailsListView.getAdapter().notifyDataSetChanged();
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


    private enum Sort {
        VEHICLE_NO,
        VEHICLE_ROUTE,
        ETA,
        ASSIGNED_PILOT
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

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);

        final MenuItem item = menu.findItem(R.id.menu_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
        searchView.setOnQueryTextListener(this);
    }

    @Override
    public boolean onQueryTextChange(String query) {
        final ArrayList<TruckDetails> filteredModelList = filter(mAdapter.getUpdatedList(), query);
        mAdapter.setUpdateList(filteredModelList);
        mTruckDetailsListView.getAdapter().notifyDataSetChanged();
        mTruckDetailsListView.scrollToPosition(0);
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    private ArrayList<TruckDetails> filter(List<TruckDetails> models, String query) {
        query = query.toLowerCase();

        ArrayList<TruckDetails> filteredModelList = new ArrayList<>();

        for (TruckDetails model : models) {
            if (model.getVehicleNumber().toLowerCase().contains(query) ||
                    model.getVehicleRoute().toLowerCase().contains(query) ||
                    model.getEta().toLowerCase().contains(query) ||
                    model.getAssignedPilot().toLowerCase().contains(query)) {
                filteredModelList.add(model);
            }
        }
        return filteredModelList;
    }
}
