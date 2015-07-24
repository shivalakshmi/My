package com.fissionlabs.trucksfirst.common;

/**
 * @author Ashok on 7/8/2015
 */
public interface TFConst {
    String TAG = "TRUCKS_FIRST";

    String BASE_IP = "rivigo-dev.elasticbeanstalk.com/mobile/";

    String URL_TRUCK_DETAILS = "http://" + BASE_IP + "truckdetails";
    String URL_PILOT_AVAILABILITY = "http://" + BASE_IP + "pilotavailability";
    String URL_VEHICLE_CHECKLIST = "http://" + BASE_IP + "vehicle/checklist";
    String URL_DRIVER_CHECKLIST = "http://" + BASE_IP + "driver/checklist";

    String TAG_TRUCK_DETAILS = "TRUCK_DETAILS";
    String TAG_PILOT_DETAILS = "PILOT_DETAILS";

    int SUCCESS = 200;
    int ERROR = 100;

    String SHARED_PREFERENCES_NAME = "RIVIGO";
    String LANG_SELECTION = "lang_selection";
}
