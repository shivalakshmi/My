package com.fissionlabs.trucksfirst.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.os.ResultReceiver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.fissionlabs.trucksfirst.R;
import com.fissionlabs.trucksfirst.common.TFCommonActivity;
import com.fissionlabs.trucksfirst.common.TFCommonFragment;
import com.fissionlabs.trucksfirst.home.TFHomeActivity;
import com.fissionlabs.trucksfirst.model.ForgotPassword;
import com.fissionlabs.trucksfirst.model.LoginResponse;
import com.fissionlabs.trucksfirst.util.TFUtils;
import com.fissionlabs.trucksfirst.webservices.WebServices;
import com.google.gson.Gson;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Created by Lakshmi on 05-09-2015.
 */
public class TFForgotPassword extends TFCommonActivity {

    private EditText username;
    private Button submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgot_password);

        username = (EditText)findViewById(R.id.username);
        submit = (Button)findViewById(R.id.btnSubmit);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               new WebServices().forgotPassword(username.getText().toString(), new ResultReceiver(null) {
                    @Override
                    protected void onReceiveResult(int resultCode, Bundle resultData) {

                        TFUtils.hideProgressBar();

                        if (resultData != null) {

                            ForgotPassword loginResponse = new Gson().fromJson(resultData.getString("response"), ForgotPassword.class);

                            final String reason = "Hi your password for the username " + loginResponse.getUserName() + "is "+ loginResponse.getPassword();

                            Thread thread = new Thread(new Runnable() {

                                public void run() {
                                    String sms = Uri.parse(URL_SMS).buildUpon()
                                            .appendQueryParameter("user", "success")
                                            .appendQueryParameter("pass", "654321")
                                            .appendQueryParameter("sender", "BSHSMS")
                                            .appendQueryParameter("phone", "9849729570")
                                            .appendQueryParameter("text", reason)
                                            .appendQueryParameter("priority", "ndnd")
                                            .appendQueryParameter("stype", "normal")
                                            .build().toString();

                                    try {
                                        HttpClient httpclient = new DefaultHttpClient();
                                        HttpResponse response = httpclient.execute(new HttpGet(sms));
                                        StatusLine statusLine = response.getStatusLine();
                                        if (statusLine.getStatusCode() == HttpStatus.SC_OK) {
                                            ByteArrayOutputStream out = new ByteArrayOutputStream();
                                            response.getEntity().writeTo(out);
                                            String responseString = out.toString();
                                            out.close();
                                        } else {
                                            //Closes the connection.
                                            response.getEntity().getContent().close();
                                            throw new IOException(statusLine.getReasonPhrase());
                                        }
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                    TFUtils.hideProgressBar();
                                }
                            });
                            thread.start();
                        }
                    }
                });


            }
        });




    }
}
