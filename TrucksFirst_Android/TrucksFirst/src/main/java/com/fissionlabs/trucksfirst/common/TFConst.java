package com.fissionlabs.trucksfirst.common;

/**
 * @author Ashok on 7/8/2015
 */
public interface TFConst {
    String TAG = "TRUCKS_FIRST";

//    String BASE_IP = "rivigoapistg-env.elasticbeanstalk.com/"; // Stagging URL
    String BASE_IP = "rivigo-dev.elasticbeanstalk.com/"; // Dev URL
    //String BASE_IP = "172.168.0.223:8080/com.rivigo.controller/"; // Local URL

    String URL_TRUCK_DETAILS = "http://" + BASE_IP + "mobile/truckdetails";
    String URL_PILOT_AVAILABILITY = "http://" + BASE_IP + "pilot/availablepilots";
    String URL_PILOT_RELEASE = "http://" + BASE_IP + "pilot/releasePilot";
    String URL_CHANGE_PILOT = "http://" + BASE_IP + "pilot/udpatePilotInfo";
    String URL_DRIVER_CHECKLIST = "http://" + BASE_IP + "mobile/pilot/checklist";
    String URL_UPDATE_DRIVER_CHECKLIST = "http://" + BASE_IP + "mobile/pilot/checklist";
    String URL_PILOT_IN_HUB = "http://" + BASE_IP + "mobile/pilotinhub";
    String URL_VEHICLE_IN_HUB = "http://" + BASE_IP + "mobile/vehicleinhub";
    String URL_VEHICLE_CHECKLIST_DETAILS = "http://" + BASE_IP + "mobile/vehicle/checklist";
    String URL_UPDATE_VEHICLE_CHECKLIST_DETAILS = "http://" + BASE_IP + "mobile/vehicle/checklist";
    String URL_LOGIN = "http://" + BASE_IP + "user/login";
    String URL_SKIPPED_PILOT_INFO = "http://" + BASE_IP + "pilot/updateskippedpilotinfo";

    String URL_SMS = "http://bhashsms.com/api/sendmsg.php";

    String TAG_TRUCK_DETAILS = "TRUCK_DETAILS";
    String TAG_PILOT_DETAILS = "PILOT_DETAILS";
    String TAG_VEHICLE_CHECKLIST = "VEHICLE_CHECKLIST";
    String TAG_DRIVER_CHECKLIST = "DRIVER_CHECKLIST";
    String TAG_CHECKLIST_DETAILS = "CHECKLIST DETAILS";
    String TAG_USER_LOGIN = "USER_LOGIN";

    int SUCCESS = 200;
    int ERROR = 100;

    String SHARED_PREFERENCES_NAME = "RIVIGO";
    String LANG_SELECTION = "lang_selection";
    String IS_USER_EXISTS = "is_user_exists";
    String HUB_NAME = "hub_name";
    String EMP_ID = "emp_id";
    String HS_NAME = "hs_name";
    String EMP_USER_NAME="hub_user_name";
    String EMP_USER_PASSWORD="hub_user_password";
//    String URL_PRINT_DOCUMENT = "https://s3-ap-southeast-1.amazonaws.com/rivigovehicledocuments/";
    String URL_PRINT_DOCUMENT = "http://52.76.55.8/";
}
