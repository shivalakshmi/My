package com.fissionlabs.trucksfirst.fragments;

import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;
import com.fissionlabs.trucksfirst.R;
import com.fissionlabs.trucksfirst.common.Mail;
import com.fissionlabs.trucksfirst.common.TFCommonFragment;
import com.fissionlabs.trucksfirst.common.TFConst;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.ByteArrayOutputStream;
import java.io.IOException;


public class TFSOSFragment extends TFCommonFragment implements TFConst{

    private Mail mMail;
    private Handler mHandler;
    private EditText mEtVehivleNo;
    private EditText mEtReason;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sos,container,false);
        mMail = new Mail("rivigodev@gmail.com", "fissionlabs");
        mEtVehivleNo = (EditText) view.findViewById(R.id.vehicle_number_edt);
        mEtReason = (EditText) view.findViewById(R.id.reason_edt);

        mHandler = new Handler(){
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                Toast.makeText(getActivity(), "" + msg.getData().getString("what"), Toast.LENGTH_LONG).show();
            }
        };

        view.findViewById(R.id.send_mail_sms).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mEtVehivleNo.getText().toString().trim().isEmpty()) {
                    Toast.makeText(getActivity(),""+getResources().getString(R.string.sos_vehicle_no),Toast.LENGTH_SHORT).show();
                    return;
                } else if(mEtReason.getText().toString().trim().isEmpty()) {
                    Toast.makeText(getActivity(),""+getResources().getString(R.string.sos_reason),Toast.LENGTH_SHORT).show();
                    return;
                }
                sendEmailAndSMS(v);

            }
        });
        return view;
    }

    public void sendEmailAndSMS(View view){

        Thread thread = new Thread(new Runnable() {

            public void run() {
                String reason = "VehivleNo "+mEtVehivleNo.getText().toString().trim()+" Reason "+mEtReason.getText().toString().trim();
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
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }

                String[] toArr = {"shivalakshmi.yella@fissionlabs.com","murali.naru@fissionlabs.com"}; // This is an array, you can add more emails, just separate them with a coma
                mMail.setTo(toArr); // load array to setTo function
                mMail.setFrom("rivigodev@gmail.com"); // who is sending the email
                mMail.setSubject("subject");
                mMail.setBody("your message goes here");
                Message message = mHandler.obtainMessage();
                Bundle b = new Bundle();
                try {
//                    new URL(sms).openConnection().connect();
//					m.addAttachment("/sdcard/myPicture.jpg");  // path to file you want to attach
                    if(mMail.send()) {
                        b.putString("what",getResources().getString(R.string.sos_email_success));
                    } else {
                        b.putString("what",getResources().getString(R.string.sos_email_failure));
                    }
                } catch(Exception e) {
                    e.printStackTrace();
                    b.putString("what",getResources().getString(R.string.sos_email_failure_problem));
                }
                message.setData(b);
                mHandler.sendMessage(message);
//                getActivity().onBackPressed();
            }
        });
        thread.start();
    }
}


