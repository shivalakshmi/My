package com.fissionlabs.trucksfirst.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.fissionlabs.trucksfirst.R;
import com.fissionlabs.trucksfirst.adapters.TrucksAdapter;
import com.fissionlabs.trucksfirst.common.TFCommonFragment;
import com.fissionlabs.trucksfirst.common.TFConst;
import com.fissionlabs.trucksfirst.home.TFHomeActivity;
import com.fissionlabs.trucksfirst.model.TruckDetails;
import com.fissionlabs.trucksfirst.model.checklist.NewChecklist;
import com.fissionlabs.trucksfirst.util.TFUtils;
import com.fissionlabs.trucksfirst.webservices.WebServices;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * A simple {@link Fragment} subclass.
 */
public class TFTruckFragment extends TFCommonFragment implements TFConst, View.OnClickListener, SearchView.OnQueryTextListener {

    public static ArrayList<TruckDetails> mTrucksList = null;
    public static TextView mDriversPlanned;
    public static TextView mDriversNotPlanned;
    public static TextView mLastUpdatedTime;
    public static int driverPlannedCount = 0;
    public static int drivetNotPlannedCount = 0;
    Timer timer = new Timer();
    private RecyclerView mTruckDetailsListView;
    private TextView mTVVehicleNo;
    private TextView mTVVehicleRoute;
    private TextView mTVEta;
    private TextView mTvClient;
    private TextView mTVAssignedPilot;
    private TFTruckFragment mTFragment;
    private TFHomeActivity homeActivity;
    private TrucksAdapter mAdapter;
    private TextView trucksPostionView;
    private TextView mVehicleCount;
    private SwipeRefreshLayout swipeRefreshLayout;

    static public boolean isOutbound(TruckDetails td) {
        String next = td.getNextHub();
        if (next == null || next.equalsIgnoreCase("null") || next.equals("")) return false;
        else return true;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        timer.cancel();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_truck, container, false);
        mTFragment = this;
        driverPlannedCount = 0;
        drivetNotPlannedCount = 0;

        mHomeActivity.mActionBar.setDisplayShowTitleEnabled(false);
        mTruckDetailsListView = (RecyclerView) view.findViewById(R.id.truck_details_list);
        mTruckDetailsListView.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mTruckDetailsListView.setLayoutManager(mLayoutManager);

        mTVVehicleNo = (TextView) view.findViewById(R.id.vehicle_no);
        mTVVehicleRoute = (TextView) view.findViewById(R.id.vehicle_route);
        mTVEta = (TextView) view.findViewById(R.id.eta);
        mTvClient = (TextView) view.findViewById(R.id.client);
        mTVAssignedPilot = (TextView) view.findViewById(R.id.assign_pilot);
        mVehicleCount = (TextView) view.findViewById(R.id.vehicle_count);
        mDriversPlanned = (TextView) view.findViewById(R.id.drivers_planned);
        mDriversNotPlanned = (TextView) view.findViewById(R.id.drivers_not_planned);
        mLastUpdatedTime = (TextView) view.findViewById(R.id.last_updated_time);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefreshLayout);

        mTVAssignedPilot.setOnClickListener(this);
        mTVEta.setOnClickListener(this);
        mTvClient.setOnClickListener(this);
        mTVVehicleRoute.setOnClickListener(this);
        mTVVehicleNo.setOnClickListener(this);
        TFUtils.showProgressBar(getActivity(), getResources().getString(R.string.please_wait));

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Refresh items

                driverPlannedCount = 0;
                drivetNotPlannedCount = 0;

                refreshDashBoardData(true);
            }
        });
        timer.schedule(new RepeatEveryFiveMins(), 0, REFRESH_DASHBOARD_INTERVAL);
        refreshDashBoardData(false);
        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        homeActivity = (TFHomeActivity) activity;
    }

    private void refreshDashBoardData(final boolean flag) {

        WebServices mWebServices = new WebServices();
        mWebServices.getTruckDetails(getActivity(), new ResultReceiver(null) {
            @Override
            protected void onReceiveResult(int resultCode, Bundle resultData) {
                if (resultCode == SUCCESS) {
                    driverPlannedCount = 0;
                    drivetNotPlannedCount = 0;
                    String responseStr = resultData.getString("response");

                    if (responseStr != null) {
                        String temp[] = responseStr.split("\\[");
                        if (temp.length > 1)
                            responseStr = "[" + temp[1];

                        Type listType = new TypeToken<ArrayList<TruckDetails>>() {
                        }.getType();
                        mTrucksList = new Gson().fromJson(responseStr, listType);
                        mAdapter = new TrucksAdapter(getActivity(), homeActivity, mTFragment, mTrucksList);
                        for (int i = 0; i < mTrucksList.size(); i++) {
                            if (isOutbound(mTrucksList.get(i))) {
                                if (mTrucksList.get(i).getAssignedPilot() == null || mTrucksList.get(i).getAssignedPilot().equalsIgnoreCase("null")) {
                                    drivetNotPlannedCount += 1;
                                } else {
                                    driverPlannedCount += 1;
                                }
                            }
                        }

                        if(mTrucksList!=null) {
                            mVehicleCount.setText(getActivity().getResources().getString(R.string.vehicle_count) + " " + mTrucksList.size());
                        }

                        mDriversPlanned.setText(getActivity().getResources().getString(R.string.drivers_planned) + " " + driverPlannedCount);
                        mDriversNotPlanned.setText(getActivity().getResources().getString(R.string.drivers_not_planned) + " " + drivetNotPlannedCount);
                        Date date = new Date();
                        CharSequence dateFormat = DateFormat.format("kk:mm", date.getTime());
                        mLastUpdatedTime.setText(getActivity().getResources().getString(R.string.last_updated_time) + " " + dateFormat);

                        if (TFUtils.SORT_COLUMN_ENUM != -1) {
                            Collections.sort(mAdapter.getUpdatedList(), new CustomComparator(Sort.values()[TFUtils.SORT_COLUMN_ENUM], TFUtils.SORT_ORDER));
                            refreshSortingArrowIcon();
                        } else {
                            TFUtils.SORT_COLUMN_ENUM = 3;
                            TFUtils.SORT_ORDER = 0;
                            Collections.sort(mAdapter.getUpdatedList(), new CustomComparator(Sort.ETA, TFUtils.SORT_ORDER));
                            refreshSortingArrowIcon();
                        }
                        mTruckDetailsListView.setAdapter(mAdapter);
                    }
                } else {
                    Toast.makeText(getActivity(), "" + getResources().getString(R.string.issue_parsing_data), Toast.LENGTH_SHORT).show();
                }
                TFUtils.hideProgressBar();
                if (flag)
                    swipeRefreshLayout.setRefreshing(false);
            }
        });

    }

    private void refreshSortingArrowIcon() {
        int drawableID;
        String tag;
        if (TFUtils.SORT_ORDER == 0) {
            drawableID = R.drawable.ic_arrow_up;
            tag = "2";
        } else if (TFUtils.SORT_ORDER == 1) {
            tag = "1";
            drawableID = R.drawable.ic_arrow_down;
        } else {
            // We should never be here
            drawableID = R.drawable.ic_arrow_right;
            tag = "1";
        }

        switch (TFUtils.SORT_COLUMN_ENUM) {
            case 0:
                mTVVehicleNo.setCompoundDrawablesWithIntrinsicBounds(drawableID, 0, 0, 0);
                mTVVehicleNo.setTag(tag);
                break;
            case 1:
                mTvClient.setCompoundDrawablesWithIntrinsicBounds(drawableID, 0, 0, 0);
                mTvClient.setTag(tag);
                break;
            case 2:
                mTVVehicleRoute.setCompoundDrawablesWithIntrinsicBounds(drawableID, 0, 0, 0);
                mTVVehicleRoute.setTag(tag);
                break;
            case 3:
                mTVEta.setCompoundDrawablesWithIntrinsicBounds(drawableID, 0, 0, 0);
                mTVEta.setTag(tag);
                break;
            case 4:
                mTVAssignedPilot.setCompoundDrawablesWithIntrinsicBounds(drawableID, 0, 0, 0);
                mTVAssignedPilot.setTag(tag);
                break;
        }
    }

    @Override
    public void onClick(View v) {
        if (mTrucksList != null && mTrucksList.size() > 1) {
            changeIconStatus(v);
        }
    }

    private void changeIconStatus(View v) {
        int asc_desc;
        switch (v.getId()) {
            case R.id.vehicle_no:
                asc_desc = getPresentIcon(mTVVehicleNo);
                TFUtils.SORT_ORDER = asc_desc;
                TFUtils.SORT_COLUMN_ENUM = 0;
                mTVVehicleRoute.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_arrow_right, 0, 0, 0);
                mTvClient.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_arrow_right, 0, 0, 0);
                mTVEta.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_arrow_right, 0, 0, 0);
                mTVAssignedPilot.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_arrow_right, 0, 0, 0);
                Collections.sort(mAdapter.getUpdatedList(), new CustomComparator(Sort.VEHICLE_NO, asc_desc));
                mTruckDetailsListView.getAdapter().notifyDataSetChanged();
                break;
            case R.id.client:
                asc_desc = getPresentIcon(mTvClient);
                TFUtils.SORT_ORDER = asc_desc;
                TFUtils.SORT_COLUMN_ENUM = 1;
                mTVVehicleRoute.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_arrow_right, 0, 0, 0);
                mTVVehicleNo.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_arrow_right, 0, 0, 0);
                mTVEta.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_arrow_right, 0, 0, 0);
                mTVAssignedPilot.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_arrow_right, 0, 0, 0);
                Collections.sort(mAdapter.getUpdatedList(), new CustomComparator(Sort.CLIENT, asc_desc));
                mTruckDetailsListView.getAdapter().notifyDataSetChanged();
                break;
            case R.id.vehicle_route:
                asc_desc = getPresentIcon(mTVVehicleRoute);
                TFUtils.SORT_ORDER = asc_desc;
                TFUtils.SORT_COLUMN_ENUM = 2;
                mTVVehicleNo.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_arrow_right, 0, 0, 0);
                mTVEta.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_arrow_right, 0, 0, 0);
                mTvClient.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_arrow_right, 0, 0, 0);
                mTVAssignedPilot.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_arrow_right, 0, 0, 0);
                Collections.sort(mAdapter.getUpdatedList(), new CustomComparator(Sort.VEHICLE_ROUTE, asc_desc));
                mTruckDetailsListView.getAdapter().notifyDataSetChanged();
                break;
            case R.id.eta:
                asc_desc = getPresentIcon(mTVEta);
                TFUtils.SORT_ORDER = asc_desc;
                TFUtils.SORT_COLUMN_ENUM = 3;
                mTVVehicleNo.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_arrow_right, 0, 0, 0);
                mTVVehicleRoute.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_arrow_right, 0, 0, 0);
                mTvClient.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_arrow_right, 0, 0, 0);
                mTVAssignedPilot.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_arrow_right, 0, 0, 0);
                Collections.sort(mAdapter.getUpdatedList(), new CustomComparator(Sort.ETA, asc_desc));
                mTruckDetailsListView.getAdapter().notifyDataSetChanged();
                break;
            case R.id.assign_pilot:
                asc_desc = getPresentIcon(mTVAssignedPilot);
                TFUtils.SORT_ORDER = asc_desc;
                TFUtils.SORT_COLUMN_ENUM = 4;
                mTVVehicleNo.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_arrow_right, 0, 0, 0);
                mTVVehicleRoute.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_arrow_right, 0, 0, 0);
                mTvClient.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_arrow_right, 0, 0, 0);
                mTVEta.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_arrow_right, 0, 0, 0);
                Collections.sort(mAdapter.getUpdatedList(), new CustomComparator(Sort.ASSIGNED_PILOT, asc_desc));
                mTruckDetailsListView.getAdapter().notifyDataSetChanged();
                break;
        }
    }

    private int getPresentIcon(TextView tv) {
        if (tv.getTag().equals("1")) {
            tv.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_arrow_up, 0, 0, 0);
            tv.setTag("2");
            return 0;
        } else if (tv.getTag().equals("2")) {
            tv.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_arrow_down, 0, 0, 0);
            tv.setTag("1");
            return 1;
        } else {
            tv.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_arrow_up, 0, 0, 0);
            tv.setTag("1");
            return 0;  //down bitmap
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);

        final MenuItem item = menu.findItem(R.id.menu_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
        searchView.setOnQueryTextListener(this);
        searchView.findViewById(android.support.v7.appcompat.R.id.search_edit_frame).setBackgroundResource(R.drawable.abc_edit_text_material);
    }

    @Override
    public boolean onQueryTextChange(String query) {
        final ArrayList<TruckDetails> filteredModelList = filter(mTrucksList, query);
        if (mAdapter != null) {
            mAdapter.setUpdateList(filteredModelList);
            mTruckDetailsListView.getAdapter().notifyDataSetChanged();
            mTruckDetailsListView.scrollToPosition(0);
        }
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    private ArrayList<TruckDetails> filter(List<TruckDetails> models, String query) {
        query = query.toLowerCase();

        ArrayList<TruckDetails> filteredModelList = new ArrayList<>();

        if (models != null) {

            for (TruckDetails model : models) {
                boolean flag = ((model.getVehicleNumber() == null || model.getVehicleNumber().equals("null")) ? false : model.getVehicleNumber().toLowerCase().contains(query))
                        || ((model.getClient() == null || model.getClient().equals("null")) ? false : model.getClient().toLowerCase().contains(query))
                        || ((model.getVehicleRoute() == null || model.getVehicleRoute().equals("null")) ? false : model.getVehicleRoute().toLowerCase().contains(query))
                        || ((model.getEta() == null || model.getEta().equals("null")) ? false : TFUtils.changeTime(model.getEta()).toLowerCase().contains(query))
                        || ((model.getAssignedPilot() == null || model.getAssignedPilot().equals("null")) ? false : model.getAssignedPilot().toLowerCase().contains(query));
                if (flag) {
                    filteredModelList.add(model);
                }
            }
        }
        return filteredModelList;
    }

    private enum Sort {
        VEHICLE_NO,
        CLIENT,
        VEHICLE_ROUTE,
        ETA,
        ASSIGNED_PILOT
    }

    public class CustomComparator implements Comparator<TruckDetails> {
        private final Sort type;
        private final int asc_desc;

        CustomComparator(Sort type, int asc_desc) {
            this.type = type;
            this.asc_desc = asc_desc;
        }

        @Override
        public int compare(TruckDetails o1, TruckDetails o2) {

            switch (type) {
                case VEHICLE_NO:
                    return asc_desc == 0 ? o1.getVehicleNumber().compareTo(o2.getVehicleNumber()) : o2.getVehicleNumber().compareTo(o1.getVehicleNumber());
                case CLIENT:
                    return asc_desc == 0 ? o1.getClient().compareTo(o2.getClient()) : o2.getClient().compareTo(o1.getClient());
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

    class RepeatEveryFiveMins extends TimerTask {
        public void run() {
            refreshDashBoardData(false);
        }
    }

}

