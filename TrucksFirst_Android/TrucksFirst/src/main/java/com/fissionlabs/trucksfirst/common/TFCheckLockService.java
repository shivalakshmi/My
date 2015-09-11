package com.fissionlabs.trucksfirst.common;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Log;
import android.widget.Toast;

import com.fissionlabs.trucksfirst.webservices.WebServices;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p/>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class TFCheckLockService extends IntentService {

    private static final String ACTION_CHECK_LOCK = "com.fissionlabs.trucksfirst.common.action.ACTION_CHECK_LOCK";
    private static final String PREF_NAME = "com.fissionlabs.trucksfirst.common.TFCheckLockService_PREF";
    private static final String INSTALL_TIME_KEY = "INSTALL_TIME_KEY";

    private Context context;

    public static void checkLockApp(Context context) {
        Log.v("Kanj", "static method called");
        Intent intent = new Intent(context, TFCheckLockService.class);
        intent.setAction(ACTION_CHECK_LOCK);
        context.startService(intent);
    }

    public TFCheckLockService() {
        super("TFCheckLockService");
        Log.v("Kanj", "Constructor");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_CHECK_LOCK.equals(action)) {
                Log.v("Kanj", "got the intent");
                handleActionCheckLock();
            }
        }
    }

    private void handleActionCheckLock() {
        Log.v("Kanj", "handling");
        // First check whether SharedPreference has value
        SharedPreferences sPref = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        long firstInstallTime = sPref.getLong(INSTALL_TIME_KEY, -1);
        Log.v("Kanj", "firstInstallTime = "+firstInstallTime);
        long currInstallTime = -1;

        PackageManager pm = getPackageManager();
        try {
            PackageInfo info = pm.getPackageInfo("com.domobile.applock", PackageManager.GET_ACTIVITIES);
            currInstallTime = info.firstInstallTime;
            Log.v("Kanj", "currInstallTime = "+currInstallTime);
        } catch (PackageManager.NameNotFoundException e) {
            //Toast.makeText(this, "App Lock is not installed.", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }

        if (firstInstallTime == -1l && currInstallTime == -1l) {
            Log.v("Kanj", "Never installed");
        } else if (firstInstallTime == -1l) {
            // Install time not set. Set it now.
            Log.v("Kanj", "Lock app found for the first time");
            SharedPreferences.Editor sPrefEdit = sPref.edit();
            sPrefEdit.putLong(INSTALL_TIME_KEY, currInstallTime);
            sPrefEdit.commit();
        } else if (currInstallTime > firstInstallTime || currInstallTime == -1) {
            // App was re-installed
            Log.v("Kanj", "Re-installated or removed");
            new WebServices().reportAppLockTamper(this);
        }
    }
}