package com.fissionlabs.trucksfirst.common;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import com.fissionlabs.trucksfirst.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class TFPilotDelayAlertService extends Service {

    private final IBinder mBinder = new PDASBinder();
    private HashMap<String, AlertTimerTask> timerTaskHashMap = new HashMap<String, AlertTimerTask>();
    private Timer t;
    SharedPreferences sPref;

    public TFPilotDelayAlertService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.v("Kanj","onBind");
        return mBinder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.v("Kanj", "onUnbind");
        t.cancel();
        return super.onUnbind(intent);
    }

    public void startWaiting(String pilot, Date date, boolean persist) {
        if (!persist) Log.v("Kanj","waiting from old data");
        Log.v("Kanj","Start waiting - pilot "+pilot+" date="+date.toString());
        AlertTimerTask task = new AlertTimerTask(pilot, date);
        timerTaskHashMap.put(pilot, task);
        t.schedule(task, date);
        if (persist) {
            SharedPreferences.Editor sPrefEdit = sPref.edit();
            sPrefEdit.putLong(pilot, date.getTime());
            sPrefEdit.commit();
        }
    }

    public void stopWaiting(String pilot) {
        if (timerTaskHashMap.containsKey(pilot)) {
            timerTaskHashMap.get(pilot).cancel();
            timerTaskHashMap.remove(pilot);
        }
        SharedPreferences.Editor sPrefEdit = sPref.edit();
        sPrefEdit.remove(pilot);
        sPrefEdit.apply();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        // Load old data
        Log.v("Kanj","onCreate");
        t = new Timer("PILOT_DELAY_ALERT_SERVICE");
        sPref = getSharedPreferences("PILOT_DELAY_ALERT_SERVICE", Context.MODE_PRIVATE);
        Map<String, ?> oldData = sPref.getAll();
        long l;
        for (String s:oldData.keySet()) {
            try{
                l = Long.parseLong(oldData.get(s).toString());
                startWaiting(s, new Date(l), false);
            } catch (Exception e) {
                Log.e("Kanj",e.toString());
                Log.e("Kanj","value was "+oldData.get(s).toString());
            }
        }
    }

    public class PDASBinder extends Binder {
        public TFPilotDelayAlertService getService() {
            return TFPilotDelayAlertService.this;
        }
    }

    class AlertTimerTask extends TimerTask {

        private String pilot;
        private Date time;

        public AlertTimerTask(String pilotName, Date reportingTime) {
            super();
            pilot = pilotName;
            time = reportingTime;
        }

        @Override
        public void run() {
            //DateFormat df = DateFormat.getDateTimeInstance();
            SimpleDateFormat df = new SimpleDateFormat("MMM dd, hh:mm a", Locale.US);
            showNotification(pilot, df.format(time));
        }

        public long getTime() {
            return time.getTime();
        }
    }

    public void showNotification(String pilot, String time) {
        Log.v("Kanj", "alert");
        if (!timerTaskHashMap.containsKey(pilot)) {
            Log.v("Kanj",pilot+" is not in hashmap");
            return;
        }
        stopWaiting(pilot);
        Uri sound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        Notification.Builder mNotiBuilder = new Notification.Builder(this)
                .setSmallIcon(R.drawable.ic_dashboard)
                .setContentTitle(pilot+" is late")
                .setContentText("Reporting time of "+pilot+" was "+time)
                .setSound(sound);
        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(pilot.hashCode(), mNotiBuilder.build());
    }
}
