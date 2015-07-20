package com.fissionlabs.trucksfirst.webservices;

import android.app.Activity;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.fissionlabs.trucksfirst.TFApp;
import com.fissionlabs.trucksfirst.common.TFConst;
import com.fissionlabs.trucksfirst.util.LogConfig;

import org.json.JSONArray;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Ashok on 7/8/2015.
 */
public class WebServices implements TFConst {


    public void getPilotData(final String caseObj, final ResultReceiver resultReceiver) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, "TFConst.URL",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Bundle bundle = new Bundle();
                        bundle.putString("response", response);

                        resultReceiver.send(200, bundle);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                resultReceiver.send(100, null);
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
                params.put("payment", caseObj);
                return params;
            }

        };
        TFApp.getInstance().addToRequestQueue(stringRequest, "TAG");

    }

    public void truckDetailsRequest(Activity context, final ResultReceiver resultReceiver) {

        JsonArrayRequest req = new JsonArrayRequest(Request.Method.GET, URL_TRUCK_DETAILS, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        if (response != null) {
                            if (LogConfig.D) {
                                Log.d(TAG, "================================ Truck details ==========================");
                                Log.d(TAG, response.toString());
                                Log.d(TAG, "================================ Truck details end ======================");
                            }

                            Bundle bundle = new Bundle();
                            bundle.putString("response", response.toString());
                            resultReceiver.send(TFConst.SUCCESS, bundle);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (LogConfig.D) {
                    Log.d(TAG, "" + error.getMessage() + ", " + error.toString());
                }
                resultReceiver.send(TFConst.ERROR, null);
            }
        });

        TFApp.getInstance().addToRequestQueue(req, TAG_TRUCK_DETAILS);
    }

}
