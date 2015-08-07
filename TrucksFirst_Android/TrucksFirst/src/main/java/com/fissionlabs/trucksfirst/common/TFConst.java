package com.fissionlabs.trucksfirst.common;

/**
 * @author Ashok on 7/8/2015
 */
public interface TFConst {
    String TAG = "TRUCKS_FIRST";

    String BASE_IP = "rivigo-dev.elasticbeanstalk.com/";

    String URL_TRUCK_DETAILS = "http://" + BASE_IP + "mobile/truckdetails";
    String URL_PILOT_AVAILABILITY = "http://" + BASE_IP + "mobile/pilotavailability";
    String URL_VEHICLE_CHECKLIST = "http://" + BASE_IP + "mobile/vehicle/checklist";
    String URL_DRIVER_CHECKLIST = "http://" + BASE_IP + "mobile/driver/checklist";
    String URL_CHECKLIST_DETAILS = "http://172.168.0.150:8080/rivigo-0.0.1-SNAPSHOT/mobile/vehicle/checklist";
    String URL_UPDATE_VEHICLE_CHECKLIST_DETAILS = "172.168.0.150:8080/rivigo-0.0.1-SNAPSHOT/mobile/vehicle/updatechecklist";
    String URL_LOGIN = "http://" + BASE_IP + "user/login";

    String TAG_TRUCK_DETAILS = "TRUCK_DETAILS";
    String TAG_PILOT_DETAILS = "PILOT_DETAILS";
    String TAG_VEHICLE_CHECKLIST = "VEHICLE_CHECKLIST";
    String TAG_DRIVER_CHECKLIST = "DRIVER_CHECKLIST";
    String TAG_CHECKLIST_DETAILS = "CHECKLIST DETAILS";

    int SUCCESS = 200;
    int ERROR = 100;

    String SHARED_PREFERENCES_NAME = "RIVIGO";
    String LANG_SELECTION = "lang_selection";
    String IS_USER_EXISTS = "is_user_exists";
}
