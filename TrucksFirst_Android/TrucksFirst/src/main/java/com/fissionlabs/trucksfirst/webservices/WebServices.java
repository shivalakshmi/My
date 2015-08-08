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
import com.fissionlabs.trucksfirst.TFApp;
import com.fissionlabs.trucksfirst.common.TFConst;
import com.fissionlabs.trucksfirst.util.LogConfig;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Ashok on 7/8/2015
 */

public class WebServices implements TFConst {

    public void userLogin(final JSONObject jsonObject, final ResultReceiver resultReceiver) {

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
                resultReceiver.send(ERROR, null);
            }
        });

        TFApp.getInstance().addToRequestQueue(request, TAG_USER_LOGIN);

    }

    public void getTruckDetails(final Context context, final ResultReceiver resultReceiver) {

        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_TRUCK_DETAILS,
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
                resultReceiver.send(ERROR, null);
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap();
                headers.put("Content-Type", "application/x-www-form-urlencoded");
                return headers;
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap();
                return params;
            }

        };
        TFApp.getInstance().addToRequestQueue(stringRequest, TAG_TRUCK_DETAILS);

    }


    public void getPilotAvailability(final Context context, final ResultReceiver resultReceiver) {

        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_PILOT_AVAILABILITY,
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
                HashMap<String, String> headers = new HashMap();
                headers.put("Content-Type", "application/x-www-form-urlencoded");
                return headers;
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap();
                return params;
            }

        };
        TFApp.getInstance().addToRequestQueue(stringRequest, TAG_PILOT_DETAILS);

    }

    public void getVehicleChecklistDetails(final Context context, final ResultReceiver resultReceiver) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_VEHICLE_CHECKLIST_DETAILS,
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
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap();
//                headers.put("Content-Type", "application/x-www-form-urlencoded");
                return headers;
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap();
                params.put("vehicleNo", "TFFL123");
                return params;
            }

        };
        TFApp.getInstance().addToRequestQueue(stringRequest, TAG_CHECKLIST_DETAILS);
    }

    public void updateVehicleChecklist(String jsonObj) {
        try {
            JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                    URL_UPDATE_VEHICLE_CHECKLIST_DETAILS, new JSONObject(jsonObj),
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
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap<String, String> headers = new HashMap<String, String>();
//                    headers.put("Content-Type", "application/json");
                    return headers;
                }

                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();
                    return params;
                }
            };
            TFApp.getInstance().addToRequestQueue(jsonObjReq, "TAG");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getDriverChecklistDetails(final Context context, final ResultReceiver resultReceiver) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_DRIVER_CHECKLIST,
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
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap();
//                headers.put("Content-Type", "application/x-www-form-urlencoded");
                return headers;
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap();
                params.put("vehicleNo", "HR55V1234");
                params.put("pilotNo","PTD/DR/H/0001");
                return params;
            }

        };
        TFApp.getInstance().addToRequestQueue(stringRequest, TAG_CHECKLIST_DETAILS);
    }

    public void updateDriverChecklist(String jsonObj) {
        try {
            JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                    URL_UPDATE_DRIVER_CHECKLIST, new JSONObject(jsonObj),
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
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap<String, String> headers = new HashMap<String, String>();
//                    headers.put("Content-Type", "application/json");
                    return headers;
                }

                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();
                    return params;
                }
            };
            TFApp.getInstance().addToRequestQueue(jsonObjReq, "TAG");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}



