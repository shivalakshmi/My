package com.fissionlabs.trucksfirst.common;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.fissionlabs.trucksfirst.webservices.WebServices;

public class TFCheckLockService extends IntentService {

    private static final String ACTION_CHECK_LOCK = "com.fissionlabs.trucksfirst.common.action.ACTION_CHECK_LOCK";
    private static final String PREF_NAME = "com.fissionlabs.trucksfirst.common.TFCheckLockService_PREF";
    private static final String INSTALL_TIME_KEY = "INSTALL_TIME_KEY";

    private Context context;

    public TFCheckLockService() {
        super("TFCheckLockService");
    }

    public static void checkLockApp(Context context) {
        Intent intent = new Intent(context, TFCheckLockService.class);
        intent.setAction(ACTION_CHECK_LOCK);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_CHECK_LOCK.equals(action)) {
                handleActionCheckLock();
            }
        }
    }

    private void handleActionCheckLock() {
        // First check whether SharedPreference has value
        SharedPreferences sPref = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        long firstInstallTime = sPref.getLong(INSTALL_TIME_KEY, -1);
        long currInstallTime = -1;

        PackageManager pm = getPackageManager();
        try {
            PackageInfo info = pm.getPackageInfo("com.domobile.applock", PackageManager.GET_ACTIVITIES);
            currInstallTime = info.firstInstallTime;
        } catch (PackageManager.NameNotFoundException e) {
            //Toast.makeText(this, "App Lock is not installed.", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }

        if (firstInstallTime == -1l && currInstallTime != -1) {
            // Install time not set. Set it now.
            SharedPreferences.Editor sPrefEdit = sPref.edit();
            sPrefEdit.putLong(INSTALL_TIME_KEY, currInstallTime);
            sPrefEdit.commit();
        } else if (currInstallTime > firstInstallTime || currInstallTime == -1) {
            // App was re-installed
            new WebServices().reportAppLockTamper(this);
        }
    }
}