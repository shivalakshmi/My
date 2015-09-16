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

import com.fissionlabs.trucksfirst.R;

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
        return mBinder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        t.cancel();
        return super.onUnbind(intent);
    }

    public void startWaiting(String pilot, Date date, boolean persist) {
        if (timerTaskHashMap.containsKey(pilot)) return;
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
        t = new Timer("PILOT_DELAY_ALERT_SERVICE");
        sPref = getSharedPreferences("PILOT_DELAY_ALERT_SERVICE", Context.MODE_PRIVATE);
        Map<String, ?> oldData = sPref.getAll();
        long l;
        for (String s:oldData.keySet()) {
            try{
                l = Long.parseLong(oldData.get(s).toString());
                startWaiting(s, new Date(l), false);
            } catch (Exception e) {
                e.printStackTrace();
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
        if (!timerTaskHashMap.containsKey(pilot)) {
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
