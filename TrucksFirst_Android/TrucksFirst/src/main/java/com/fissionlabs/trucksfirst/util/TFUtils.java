package com.fissionlabs.trucksfirst.util;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;
import android.app.Activity;
import android.content.SharedPreferences;

import com.fissionlabs.trucksfirst.common.TFConst;

import com.fissionlabs.trucksfirst.R;

/**
 * Created by Ashok on 7/8/2015.
 */
public class TFUtils implements TFConst{

	public static ProgressDialog mProgressDialog;

    /**
     * This method is used to get the {@link String} data from the {@link SharedPreferences}.
     * @param _activity {@link Activity} instance
     * @param key key value
     * @return {@link String} value of the given key
     */
    public static String getStringFromSP(Activity _activity,String key) {
        SharedPreferences preferences = _activity.getSharedPreferences(SHARED_PREFERENCES_NAME, android.content.Context.MODE_PRIVATE);
        return preferences.getString(key, null);
    }//getFromSP()

    /**
     * This method is used to save the {@link String} data in the form of key - value pair.
     * @param _activity {@link Activity} instance
     * @param key key name
     * @param value value
     * @see SharedPreferences
     */
    public static void saveStringInSP(Activity _activity,String key, String value){
        SharedPreferences preferences = _activity.getSharedPreferences(SHARED_PREFERENCES_NAME, android.content.Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, value);
        editor.commit();
    }//saveInSP()

    /**
     * This method is used to save the {@link Boolean} data in the form of key - value pair.
     * @param _activity {@link Activity} instance
     * @param key key name
     * @param value value
     * @see SharedPreferences
     */
    public static void saveBooleanInSP(Activity _activity,String key, boolean value){
        SharedPreferences preferences = _activity.getSharedPreferences(SHARED_PREFERENCES_NAME, android.content.Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }//saveInSP()

    /**
     * This method is used to get the {@link Boolean} data from the {@link SharedPreferences}.
     * @param _activity {@link Activity} instance
     * @param key key value
     * @return {@link Boolean} value of the given key
     */
    public static boolean getBooleanFromSP(Activity _activity,String key) {
        // TODO Auto-generated method stub
        SharedPreferences preferences = _activity.getSharedPreferences(SHARED_PREFERENCES_NAME, android.content.Context.MODE_PRIVATE);
        return preferences.getBoolean(key, false);
    }//getFromSP()

    /**
     * This methos is used to delete the given key - value pair data from the {@link SharedPreferences}.
     * @param _activity {@link Activity} instance
     * @param key key value
     */

    /**
     * This method is used to get the {@link Integer} data from the {@link SharedPreferences}.
     * @param _activity {@link Activity} instance
     * @param key key value
     * @return {@link String} value of the given key
     */
    public static int getIntFromSP(Activity _activity, String key) {
        // TODO Auto-generated method stub
        SharedPreferences preferences = _activity.getSharedPreferences(SHARED_PREFERENCES_NAME, android.content.Context.MODE_PRIVATE);
        return preferences.getInt(key, 0);
    }//getIntFromSP()

    /**
     * This method is used to save the {@link Integer} data in the form of key - value pair.
     * @param _activity {@link Activity} instance
     * @param key key name
     * @param value value
     * @see SharedPreferences
     */
    public static void saveIntInSP(Activity _activity, String key, int value){
        SharedPreferences preferences = _activity.getSharedPreferences(SHARED_PREFERENCES_NAME, android.content.Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(key, value);
        editor.commit();
    }//saveIntInSP()
    
    public static void deleteFromSP(Activity _activity,String key){
        // TODO Auto-generated method stub
        SharedPreferences preferences = _activity.getSharedPreferences(SHARED_PREFERENCES_NAME, android.content.Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.remove(key);
        editor.commit();
    }

    
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
