package com.fissionlabs.trucksfirst.fragments;


import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.fissionlabs.trucksfirst.R;
import com.fissionlabs.trucksfirst.common.GPSTracker;

import org.w3c.dom.Text;

import java.util.List;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 */
public class TFDashBoardFragment extends Fragment implements View.OnClickListener{

    private TextView mTvLastLogin;
    private TextView mTvFetchLocation;
    private GPSTracker gps;
    private ImageView mIvFetchLocationIcon;

    public TFDashBoardFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootview =  inflater.inflate(R.layout.fragment_dash_board, container, false);
        mTvLastLogin = (TextView) rootview.findViewById(R.id.last_login);
        mTvFetchLocation = (TextView) rootview.findViewById(R.id.fetch_location);
        mIvFetchLocationIcon = (ImageView) rootview.findViewById(R.id.fetch_location_icon);
        final Animation myRotation = AnimationUtils.loadAnimation(getActivity(), R.anim.rotation);
        mTvFetchLocation.setOnClickListener(this);
        mIvFetchLocationIcon.setOnClickListener(this);
        rootview.findViewById(R.id.fetch_location_layout).setOnClickListener(this);

        return rootview;
    }

    public void FetchLocation(){
        final Animation myRotation = AnimationUtils.loadAnimation(getActivity(), R.anim.rotation);
        gps = new GPSTracker(getActivity());
        mIvFetchLocationIcon.startAnimation(myRotation);
        if(gps.canGetLocation()){
            double latitude = gps.getLatitude();
            double longitude = gps.getLongitude();
            Geocoder geocoder = new Geocoder(getActivity(), Locale.getDefault());
            try {
                List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
                String cityName = addresses.get(0).getAddressLine(0);
                String stateName = addresses.get(0).getAddressLine(1);
//                String countryName = addresses.get(0).getAddressLine(2);
                mTvFetchLocation.setText(cityName+","+stateName);
                gps.stopUsingGPS();
            }catch (Exception e){
                e.printStackTrace();
            }
        }else{
            gps.showSettingsAlert();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.fetch_location_layout:
            case R.id.fetch_location:
            case R.id.fetch_location_icon:
                FetchLocation();
                break;
        }
    }
}
