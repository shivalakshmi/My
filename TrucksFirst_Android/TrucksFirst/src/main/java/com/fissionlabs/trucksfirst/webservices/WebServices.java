package com.fissionlabs.trucksfirst.webservices;

import android.content.Context;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.fissionlabs.trucksfirst.R;
import com.fissionlabs.trucksfirst.TFApp;
import com.fissionlabs.trucksfirst.common.TFConst;
import com.fissionlabs.trucksfirst.model.TruckDetails;
import com.fissionlabs.trucksfirst.util.LogConfig;
import com.fissionlabs.trucksfirst.util.TFUtils;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Ashok on 7/8/2015
 */

public class WebServices implements TFConst {

    public void userLogin(final Context context, final JSONObject jsonObject, final ResultReceiver resultReceiver) {

        JsonObjectRequest request = new JsonObjectRequest(URL_LOGIN, jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        if (LogConfig.D) {
                            Log.d(TAG, "================================ User login ==========================");
                            Log.d(TAG, "" + response);
                            Log.d(TAG, "================================ User login end ======================");
                        }

                        Bundle bundle = new Bundle();
                        bundle.putString("response", response.toString());

                        resultReceiver.send(SUCCESS, bundle);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e("Error: ", error.getMessage());
                Toast.makeText(context, context.getResources().getString(R.string.server_not_responding), Toast.LENGTH_SHORT).show();
                resultReceiver.send(ERROR, null);
            }
        });

        TFApp.getInstance().addToRequestQueue(request, TAG_USER_LOGIN);

    }

    public void getTruckDetails(final Context context, final ResultReceiver resultReceiver) {

        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_TRUCK_DETAILS + "?hubName=" + TFUtils.getStringFromSP(context, HUB_NAME),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (LogConfig.D) {
                            Log.d(TAG, "================================ Truck details ==========================");
                            Log.d(TAG, response);
                            Log.d(TAG, "================================ Truck details end ======================");
                        }
                        Bundle bundle = new Bundle();
                        bundle.putString("response", response);

                        resultReceiver.send(SUCCESS, bundle);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (LogConfig.D) {
                    Log.d(TAG, "" + error.getMessage() + ", " + error.toString());
                }
                Toast.makeText(context, context.getResources().getString(R.string.server_not_responding), Toast.LENGTH_SHORT).show();
                resultReceiver.send(ERROR, null);
            }
        }) {


        };
        TFApp.getInstance().addToRequestQueue(stringRequest, TAG_TRUCK_DETAILS);

    }

    public void getPilotAvailability(final Context context, final String eta, final String nextHub, final ResultReceiver resultReceiver) {
        Log.e("Lakshmi", URL_PILOT_AVAILABILITY + "?currentHub=" + TFUtils.getStringFromSP(context, HUB_NAME) + "&ETA=" + eta + "&nextHub=" + nextHub);
        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                URL_PILOT_AVAILABILITY + "?currentHub=" + TFUtils.getStringFromSP(context, HUB_NAME) + "&ETA=" + eta + "&nextHub=" + nextHub,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (LogConfig.D) {
                            Log.d(TAG, "================================ Pilot details ==========================");
                            Log.d(TAG, response);
                            Log.d(TAG, "================================ Pilot details end ======================");
                        }
                        Bundle bundle = new Bundle();
                        bundle.putString("response", response);

                        resultReceiver.send(SUCCESS, bundle);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (LogConfig.D) {
                    Log.d(TAG, "" + error.getMessage() + ", " + error.toString());
                }
                resultReceiver.send(ERROR, null);
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<>();
//                headers.put("Content-Type", "application/x-www-form-urlencoded");
                return headers;
            }


         /*   @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("currentHub",TFUtils.getStringFromSP(context,HUB_NAME));
                params.put("ETA", eta);
                params.put("nextHub", nextHub);

                return params;
            }*/

        };
        TFApp.getInstance().addToRequestQueue(stringRequest, TAG_PILOT_DETAILS);

    }

    public void getPilotRelease(final Context context, final String currentHub, final String pilotId, final String vehicleTrackingId, final ResultReceiver resultReceiver) {

        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                URL_PILOT_RELEASE + "?currentHub=" + currentHub + "&pilotId=" + pilotId + "&vehicleTrackingId=" + vehicleTrackingId,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (LogConfig.D) {
                            Log.d(TAG, "================================ Pilot details ==========================");
                            Log.d(TAG, response);
                            Log.d(TAG, "================================ Pilot details end ======================");
                        }
                        Bundle bundle = new Bundle();
                        bundle.putString("response", response);

                        resultReceiver.send(SUCCESS, bundle);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (LogConfig.D) {
                    Log.d(TAG, "" + error.getMessage() + ", " + error.toString());
                }
                resultReceiver.send(ERROR, null);
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<>();
//                headers.put("Content-Type", "application/x-www-form-urlencoded");
                return headers;
            }

        };
        TFApp.getInstance().addToRequestQueue(stringRequest, TAG_PILOT_DETAILS);

    }


    public void getChangePilot(final Context context, TruckDetails obj, boolean flag, String existingPilotId, final ResultReceiver resultReceiver) {
        String params = "?vehicleTrackingId=" + obj.getVehicleTrackingId() + "&currentHub=" + obj.getCurrentHub()
                + "&currentHubEta=" + TFUtils.sendServerTime(obj.getEta()) + "&nextHub=" + obj.getNextHub()
                + "&nextHubEta=" + TFUtils.sendServerTime(obj.getNextHubEta()) +
                (flag ? "&existingPilotId=" + existingPilotId : "") + "&pilotId=" + obj.getPilotAvailability().getPilotId();
        params = params.replaceAll(" ", "%20");
        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                URL_CHANGE_PILOT + params,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (LogConfig.D) {
                            Log.d(TAG, "================================ Pilot details ==========================");
                            Log.d(TAG, response);
                            Log.d(TAG, "================================ Pilot details end ======================");
                        }
                        Bundle bundle = new Bundle();
                        bundle.putString("response", response);

                        resultReceiver.send(SUCCESS, bundle);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (LogConfig.D) {
                    Log.d(TAG, "" + error.getMessage() + ", " + error.toString());
                }
                resultReceiver.send(ERROR, null);
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<>();
//                headers.put("Content-Type", "application/x-www-form-urlencoded");
                return headers;
            }

        };
        TFApp.getInstance().addToRequestQueue(stringRequest, TAG_PILOT_DETAILS);

    }


    public void getVehicleChecklistDetails(final String vehicleNumber, final ResultReceiver resultReceiver) {

        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                URL_VEHICLE_CHECKLIST_DETAILS + "?vehicleNo=" + "HR55V7964",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (LogConfig.D) {
                            Log.d(TAG, "================================ Checklist details ==========================");
                            Log.d(TAG, response);
                            Log.d(TAG, "================================ Checklist details end ======================");
                        }
                        Bundle bundle = new Bundle();
                        bundle.putString("response", response);


                        resultReceiver.send(SUCCESS, bundle);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (LogConfig.D) {
                    Log.d(TAG, "" + error.getMessage() + ", " + error.toString());
                }
                resultReceiver.send(ERROR, null);
            }
        }) {

           /* @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("vehicleNo", vehicleNumber);
                return params;
            }*/

        };
        TFApp.getInstance().addToRequestQueue(stringRequest, TAG_CHECKLIST_DETAILS);
    }

    public void updateVehicleChecklist(JSONObject jsonObj) {
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                URL_UPDATE_VEHICLE_CHECKLIST_DETAILS, jsonObj,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("A", "" + response);
                        Log.e("JSON Object response", "" + response);

                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("A", "Error");
            }
        }) {
        };
        TFApp.getInstance().addToRequestQueue(jsonObjReq, "TAG");
    }

    public void getDriverChecklistDetails(final String vehicleNo, final String pilotNo, final ResultReceiver resultReceiver) {

        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                URL_DRIVER_CHECKLIST + "?pilotNo=" + pilotNo,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (LogConfig.D) {
                            Log.d(TAG, "================================ Checklist details ==========================");
                            Log.d(TAG, response);
                            Log.d(TAG, "================================ Checklist details end ======================");
                        }
                        Bundle bundle = new Bundle();
                        bundle.putString("response", response);

                        resultReceiver.send(SUCCESS, bundle);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (LogConfig.D) {
                    Log.d(TAG, "" + error.getMessage() + ", " + error.toString());
                }
                resultReceiver.send(ERROR, null);
            }
        }) {

          /*  @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("vehicleNo", "HR55V7225");
                params.put("pilotNo","55");
                return params;
            }
*/
        };
        TFApp.getInstance().addToRequestQueue(stringRequest, TAG_CHECKLIST_DETAILS);
    }

    public void updateDriverChecklist(JSONObject jsonObj) {
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                URL_UPDATE_DRIVER_CHECKLIST, jsonObj,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("A", "" + response);
                        Log.e("JSON Object response", "" + response);

                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("A", "Error");
            }
        }) {

        };
        TFApp.getInstance().addToRequestQueue(jsonObjReq, "TAG");
    }

    public void getPilotInHub(final String vehicleTrackingID, final String pilotInHub) {

        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                URL_PILOT_IN_HUB + "?vehicleTrackingId=" + vehicleTrackingID + "&pilotInHub=" + pilotInHub,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (LogConfig.D) {
                            Log.d(TAG, "================================ Checklist details ==========================");
                            Log.d(TAG, response);
                            Log.d(TAG, "================================ Checklist details end ======================");
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (LogConfig.D) {
                    Log.d(TAG, "" + error.getMessage() + ", " + error.toString());
                }
            }
        }) {


        };
        TFApp.getInstance().addToRequestQueue(stringRequest, TAG_CHECKLIST_DETAILS);
    }

}



