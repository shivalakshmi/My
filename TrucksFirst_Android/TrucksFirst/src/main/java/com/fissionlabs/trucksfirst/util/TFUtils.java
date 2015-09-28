package com.fissionlabs.trucksfirst.util;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.fissionlabs.trucksfirst.R;
import com.fissionlabs.trucksfirst.common.TFConst;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

/**
 * Created by Ashok on 7/8/2015.
 */
public class TFUtils implements TFConst {

    public static int SORT_ORDER = -1;
    public static int SORT_COLUMN_ENUM = -1;
    public static HashMap<String, String> hubCodeCityMap;
    private static ProgressDialog mProgressDialog;

    /**
     * This method is used to get the {@link String} data from the {@link SharedPreferences}.
     *
     * @param _activity {@link Activity} instance
     * @param key       key value
     * @return {@link String} value of the given key
     */
    public static String getStringFromSP(Context _activity, String key) {
        SharedPreferences preferences = _activity.getSharedPreferences(SHARED_PREFERENCES_NAME, android.content.Context.MODE_PRIVATE);
        return preferences.getString(key, null);
    }//getFromSP()

    /**
     * This method is used to save the {@link String} data in the form of key - value pair.
     *
     * @param _activity {@link Activity} instance
     * @param key       key name
     * @param value     value
     * @see SharedPreferences
     */
    public static void saveStringInSP(Activity _activity, String key, String value) {
        SharedPreferences preferences = _activity.getSharedPreferences(SHARED_PREFERENCES_NAME, android.content.Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, value);
        editor.apply();
    }//saveInSP()

    /**
     * This method is used to save the {@link Boolean} data in the form of key - value pair.
     *
     * @param _activity {@link Activity} instance
     * @param key       key name
     * @param value     value
     * @see SharedPreferences
     */
    public static void saveBooleanInSP(Activity _activity, String key, boolean value) {
        SharedPreferences preferences = _activity.getSharedPreferences(SHARED_PREFERENCES_NAME, android.content.Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }//saveInSP()

    /**
     * This method is used to get the {@link Boolean} data from the {@link SharedPreferences}.
     *
     * @param _activity {@link Activity} instance
     * @param key       key value
     * @return {@link Boolean} value of the given key
     */
    public static boolean getBooleanFromSP(Activity _activity, String key) {
        // TODO Auto-generated method stub
        SharedPreferences preferences = _activity.getSharedPreferences(SHARED_PREFERENCES_NAME, android.content.Context.MODE_PRIVATE);
        return preferences.getBoolean(key, false);
    }//getFromSP()

    /**
     * This method is used to delete the given key - value pair data from the {@link SharedPreferences}.
     * @param _activity {@link Activity} instance
     * @param key key value
     */

    /**
     * This method is used to get the {@link Integer} data from the {@link SharedPreferences}.
     *
     * @param _activity {@link Activity} instance
     * @param key       key value
     * @return {@link String} value of the given key
     */
    public static int getIntFromSP(Activity _activity, String key) {
        // TODO Auto-generated method stub
        SharedPreferences preferences = _activity.getSharedPreferences(SHARED_PREFERENCES_NAME, android.content.Context.MODE_PRIVATE);
        return preferences.getInt(key, 0);
    }//getIntFromSP()

    /**
     * This method is used to save the {@link Integer} data in the form of key - value pair.
     *
     * @param _activity {@link Activity} instance
     * @param key       key name
     * @param value     value
     * @see SharedPreferences
     */
    public static void saveIntInSP(Activity _activity, String key, int value) {
        SharedPreferences preferences = _activity.getSharedPreferences(SHARED_PREFERENCES_NAME, android.content.Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(key, value);
        editor.apply();
    }//saveIntInSP()

    public static void deleteFromSP(Activity _activity, String key) {
        // TODO Auto-generated method stub
        SharedPreferences preferences = _activity.getSharedPreferences(SHARED_PREFERENCES_NAME, android.content.Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.remove(key);
        editor.apply();
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

    private static boolean isConnected(Context context) {
        ConnectivityManager connect = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        return connect.getNetworkInfo(0).getState() == NetworkInfo.State.CONNECTED
                || connect.getNetworkInfo(0).getState() == NetworkInfo.State.CONNECTING
                || connect.getNetworkInfo(1).getState() == NetworkInfo.State.CONNECTING
                || connect.getNetworkInfo(1).getState() == NetworkInfo.State.CONNECTED;
    }

    public static String changeTime(String etaInMills) {
        if (etaInMills == null || etaInMills.equalsIgnoreCase("null")) {
            return "";
        }
        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        long milliSeconds = Long.parseLong(etaInMills);
        Calendar calendar = Calendar.getInstance();
        milliSeconds -= calendar.getTimeZone().getRawOffset();
        calendar.setTimeInMillis(milliSeconds);
        return formatter.format(calendar.getTime());
    }

    public static String sendServerTime(String etaInMills) {
        if (etaInMills == null || etaInMills.equalsIgnoreCase("null")) {
            return "null";
        }
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long milliSeconds = Long.parseLong(etaInMills);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSeconds);
        return formatter.format(calendar.getTime());
    }

    public static void hideKeyboard(Activity activity) {
        if (activity != null && activity.getWindow() != null && activity.getWindow().getDecorView() != null) {
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(activity.getWindow().getDecorView().getWindowToken(), 0);
        }
    }

    static {
        hubCodeCityMap = new HashMap<String, String>();
        hubCodeCityMap.put("AHMD","Ahmedabad");
        hubCodeCityMap.put("BGM","Belgaum");
        hubCodeCityMap.put("BHL","Bhilwara");
        hubCodeCityMap.put("BNG","Banglore");
        hubCodeCityMap.put("BRD","Baroda");
        hubCodeCityMap.put("BWD","Bhiwandi");
        hubCodeCityMap.put("DHU","Dhule");
        hubCodeCityMap.put("HBL","Hubli");
        hubCodeCityMap.put("HYD","Hyderabad");
        hubCodeCityMap.put("JPR","Jaipur");
        hubCodeCityMap.put("KHR","Kherwara");
        hubCodeCityMap.put("PTD","Pataudi");
        hubCodeCityMap.put("PUN","Pune");
        hubCodeCityMap.put("SLM","Salem");
        hubCodeCityMap.put("SLPR","Solapur");
        hubCodeCityMap.put("SRT","Surat");
        hubCodeCityMap.put("VAP","Vapi");
        hubCodeCityMap.put("PTR","Pitampura");
        hubCodeCityMap.put("INDR","Indore");
        hubCodeCityMap.put("AGRA","Agra");
        hubCodeCityMap.put("KOP","Kolhapur");
        hubCodeCityMap.put("JKR","Jakkur");
        hubCodeCityMap.put("MPR","Malkapur");
        hubCodeCityMap.put("NDR","Nanded");
        hubCodeCityMap.put("KPR","Kanpur");
        hubCodeCityMap.put("VARNS","Varanasi");
        hubCodeCityMap.put("AUGB","Aurangabad");
        hubCodeCityMap.put("DHB","Dhanbad");
        hubCodeCityMap.put("KOL","Kolkata");
        hubCodeCityMap.put("CHN","Chennai");
        hubCodeCityMap.put("JDH","Jodhpur");
        hubCodeCityMap.put("PTN","Patna");
        hubCodeCityMap.put("SSB","Shamsabad");
        hubCodeCityMap.put("PNVL","Panvel");
        hubCodeCityMap.put("LKNW","Lucknow");
        hubCodeCityMap.put("BPL","Bhopal");
        hubCodeCityMap.put("BHI","Bhiwani");
    }
}
