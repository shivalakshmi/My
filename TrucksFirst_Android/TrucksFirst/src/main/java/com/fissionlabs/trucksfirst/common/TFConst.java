package com.fissionlabs.trucksfirst.common;

/**
 * Created by Ashok on 7/8/2015.
 */
public interface TFConst {
    String TAG = "TRUCKS_FIRST";

    String BASE_IP = "";

    String URL_TRUCK_DETAILS = "http://" + BASE_IP + "";
    String URL_PILOT_DETAILS = "http://" + BASE_IP + "";

    String TAG_TRUCK_DETAILS = "TRUCK_DETAILS";
    String TAG_PILOT_DETAILS = "PILOT_DETAILS";

    int SUCCESS = 200;
    int ERROR = 100;

    String SHARED_PREFERENCES_NAME = "RIVIGO";
    String LANG_SELECTION = "lang_selection";
}
