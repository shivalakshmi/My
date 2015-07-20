package com.fissionlabs.trucksfirst.util;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import com.fissionlabs.trucksfirst.R;

/**
 * Created by Ashok on 7/8/2015.
 */
public class TFUtils {

    public static ProgressDialog mProgressDialog;


    public static ProgressDialog showProgressBar(Context context, String msg) {
        hideProgressBar();

        try {
            if (mProgressDialog == null) {
                mProgressDialog = new ProgressDialog(context);
                mProgressDialog.setProgressStyle(android.R.style.Widget_ProgressBar_Large);
                mProgressDialog.setMessage(msg);
                mProgressDialog.setIndeterminate(true);
                mProgressDialog.setCancelable(false);
                mProgressDialog.show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return mProgressDialog;
    }

    public static void hideProgressBar() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            try {
                mProgressDialog.dismiss();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        mProgressDialog = null;
    }

    /*********************************************************************
     * NETWORK
     *********************************************************************/

    public static boolean isNetworkAvailable(Context context) {
        if (!isConnected(context)) {
            Toast.makeText(context, R.string.could_not_connect_to_server, Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    public static boolean isConnected(Context context) {
        ConnectivityManager connect = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        return connect.getNetworkInfo(0).getState() == NetworkInfo.State.CONNECTED
                || connect.getNetworkInfo(0).getState() == NetworkInfo.State.CONNECTING
                || connect.getNetworkInfo(1).getState() == NetworkInfo.State.CONNECTING
                || connect.getNetworkInfo(1).getState() == NetworkInfo.State.CONNECTED;
    }

}
