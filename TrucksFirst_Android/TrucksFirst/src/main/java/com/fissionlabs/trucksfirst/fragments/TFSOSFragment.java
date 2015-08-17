package com.fissionlabs.trucksfirst.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.fissionlabs.trucksfirst.R;
import com.fissionlabs.trucksfirst.common.Mail;
import com.fissionlabs.trucksfirst.common.TFCommonFragment;


public class TFSOSFragment extends TFCommonFragment {

    private Mail mMail;
    private Handler mHandler;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sos,container,false);
        mMail = new Mail("rivigodev@gmail.com", "fissionlabs");

        mHandler = new Handler(){
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                Toast.makeText(getActivity(), "" + msg.getData().getString("what"), Toast.LENGTH_LONG).show();
            }
        };

        view.findViewById(R.id.send_mail_sms).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendEmailAndSMS(v);
            }
        });
        return view;
    }

    public void sendEmailAndSMS(View view){

        Thread thread = new Thread(new Runnable() {

            public void run() {
                String[] toArr = {"shivalakshmi.yella@fissionlabs.com","murali.naru@fissionlabs.com"}; // This is an array, you can add more emails, just separate them with a coma
                mMail.setTo(toArr); // load array to setTo function
                mMail.setFrom("rivigodev@gmail.com"); // who is sending the email
                mMail.setSubject("subject");
                mMail.setBody("your message goes here");
                Message message = mHandler.obtainMessage();
                Bundle b = new Bundle();
                try {
//					m.addAttachment("/sdcard/myPicture.jpg");  // path to file you want to attach
                    if(mMail.send()) {
                        b.putString("what","Email was sent successfully.");
                    } else {
                        b.putString("what","Email was not sent.");
                    }
                } catch(Exception e) {
                    e.printStackTrace();
                    b.putString("what","There was a problem sending the email.");
                }
                message.setData(b);
                mHandler.sendMessage(message);
            }
        });
        thread.start();
    }
}


