package com.fissionlabs.trucksfirst.webservices;

import android.content.Context;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.fissionlabs.trucksfirst.TFApp;
import com.fissionlabs.trucksfirst.common.TFConst;
import com.fissionlabs.trucksfirst.util.LogConfig;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Ashok on 7/8/2015
 */

public class WebServices implements TFConst {


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
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/x-www-form-urlencoded");
                return headers;
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
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
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/x-www-form-urlencoded");
                return headers;
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                return params;
            }

        };
        TFApp.getInstance().addToRequestQueue(stringRequest, TAG_PILOT_DETAILS);

    }

}
