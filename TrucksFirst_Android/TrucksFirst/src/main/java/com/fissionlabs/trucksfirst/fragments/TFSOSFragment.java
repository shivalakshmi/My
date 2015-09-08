package com.fissionlabs.trucksfirst.fragments;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.view.GravityCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.android.volley.toolbox.StringRequest;
import com.fissionlabs.trucksfirst.R;
import com.fissionlabs.trucksfirst.common.Mail;
import com.fissionlabs.trucksfirst.common.TFCommonFragment;
import com.fissionlabs.trucksfirst.common.TFConst;
import com.fissionlabs.trucksfirst.util.TFUtils;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;


public class TFSOSFragment extends TFCommonFragment implements TFConst{

    private static Mail mMail;
    private static Handler mHandler;
    private AutoCompleteTextView mEtVehivleNo;
    private EditText mEtReason;
    private RadioGroup mRadioGroup;
    private ArrayList<String> vehicleNumberList = new ArrayList<>();
    private Activity mActivity;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_sos,container,false);

        mHomeActivity.isHomeFragment = false;
        mHomeActivity.mActionBar.setDisplayShowTitleEnabled(true);
        mHomeActivity.imageView.setVisibility(View.GONE);

        mHomeActivity.toolbar.setNavigationIcon(R.drawable.ic_back);
        mHomeActivity.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mHomeActivity.isHomeFragment == false)
                    mHomeActivity.onBackPressed();
                else
                    mHomeActivity.mDrawerLayout.openDrawer(GravityCompat.START);
            }
        });

//        mMail = new Mail("rivigodev@gmail.com", "fissionlabs");
        mEtVehivleNo = (AutoCompleteTextView) view.findViewById(R.id.vehicle_number_edt);
        mEtReason = (EditText) view.findViewById(R.id.reason_edt);
        mRadioGroup = (RadioGroup) view.findViewById(R.id.sos_radiogroup);

        if(TFTruckFragment.mTrucksList.size()>0 && TFTruckFragment.mTrucksList!=null) {
            vehicleNumberList.clear();
            for (int i = 0; i < TFTruckFragment.mTrucksList.size(); i++) {
                vehicleNumberList.add(TFTruckFragment.mTrucksList.get(i).getVehicleNumber());
            }
            ArrayAdapter<String> adapter =  new ArrayAdapter<String>(mActivity,android.R.layout.simple_list_item_1, vehicleNumberList);
            mEtVehivleNo.setThreshold(1);
            mEtVehivleNo.setAdapter(adapter);
        }

        view.findViewById(R.id.send_mail_sms).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mEtVehivleNo.getText().toString().trim().isEmpty()) {
                    Toast.makeText(mActivity,""+getResources().getString(R.string.sos_vehicle_no),Toast.LENGTH_SHORT).show();
                    return;
                } else if(mEtReason.getText().toString().trim().isEmpty()) {
                    Toast.makeText(mActivity,""+getResources().getString(R.string.sos_reason),Toast.LENGTH_SHORT).show();
                    return;
                }
                int selectedId = mRadioGroup.getCheckedRadioButtonId();
                RadioButton radioButton = (RadioButton) view.findViewById(selectedId);
                String reason = "\nVehivleNo::"+mEtVehivleNo.getText().toString().trim()+"\nIncident::"+radioButton.getText()+"\nReason::"+mEtReason.getText().toString().trim();

                sendEmailAndSMS(mActivity,reason,"SOS Issue");
                mEtVehivleNo.setText("");
                mEtReason.setText("");
            }
        });
        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = activity;
    }

    public static void sendEmailAndSMS(final Context context,final String reason,final String subject){
        TFUtils.showProgressBar(context, context.getResources().getString(R.string.please_wait));
        mMail = new Mail("rivigodev@gmail.com", "fissionlabs");

        mHandler = new Handler(){
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if(msg.getData().getString("what") != null)
                Toast.makeText(context, "" + msg.getData().getString("what"), Toast.LENGTH_LONG).show();
            }
        };

        Thread thread = new Thread(new Runnable() {

            public void run() {
                 String sms = Uri.parse(URL_SMS).buildUpon()
                        .appendQueryParameter("user", "success")
                        .appendQueryParameter("pass", "654321")
                        .appendQueryParameter("sender", "BSHSMS")
                        .appendQueryParameter("phone", "9899222062")
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

                String[] toArr = {"sowjanya.guddeti@fissionlabs.com","shivalakshmi.yella@fissionlabs.com","murali.naru@fissionlabs.com"}; // This is an array, you can add more emails, just separate them with a coma
                mMail.setTo(toArr); // load array to setTo function
                mMail.setFrom("rivigodev@gmail.com"); // who is sending the email
                mMail.setSubject(subject);
                mMail.setBody(reason);
                Message message = mHandler.obtainMessage();
                Bundle b = new Bundle();
                try {
//                    new URL(sms).openConnection().connect();
//					m.addAttachment("/sdcard/myPicture.jpg");  // path to file you want to attach
                    if(mMail.send()) {
                        b.putString("what",context.getResources().getString(R.string.sos_email_success));
                    }/* else {
                        b.putString("what",context.getResources().getString(R.string.sos_email_failure));
                    }*/
                } catch(Exception e) {
                    e.printStackTrace();
                    b.putString("what",context.getResources().getString(R.string.sos_email_failure_problem));
                }
                message.setData(b);
                mHandler.sendMessage(message);
                TFUtils.hideProgressBar();
//                mActivity.onBackPressed();
            }
        });
        thread.start();
    }

    public static void sendSMS(final Context context,final String reason,final String contactNumber){
        TFUtils.showProgressBar(context, context.getResources().getString(R.string.please_wait));

        mHandler = new Handler(){
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                Toast.makeText(context, "" + msg.getData().getString("what"), Toast.LENGTH_LONG).show();
            }
        };

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


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mHomeActivity.toolbar.setNavigationIcon(R.drawable.ic_menu);
        mHomeActivity.isHomeFragment = true;
        mHomeActivity.mActionBar.setDisplayShowTitleEnabled(false);
        mHomeActivity.imageView.setVisibility(View.VISIBLE);
    }
}


