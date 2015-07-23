package com.fissionlabs.trucksfirst.common;

/**
 * Created by Ashok on 7/8/2015.
 */
public interface TFConst {
    String TAG = "TRUCKS_FIRST";

    String BASE_IP = "rivigo-2037908901.ap-southeast-1.elb.amazonaws.com/rivigo/mobile/";

    String URL_TRUCK_DETAILS = "http://" + BASE_IP + "truckdetails";
    String URL_PILOT_AVAILABILITY = "http://" + BASE_IP + "pilotavaility";
    String URL_CHECKLIST = "http://" + BASE_IP + "checklist";

    String TAG_TRUCK_DETAILS = "TRUCK_DETAILS";
    String TAG_PILOT_DETAILS = "PILOT_DETAILS";

    int SUCCESS = 200;
    int ERROR = 100;

    String SHARED_PREFERENCES_NAME = "RIVIGO";
    String LANG_SELECTION = "lang_selection";
}
