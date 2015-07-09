package com.fissionlabs.trucksfirst;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import com.fissionlabs.trucksfirst.home.TFHomeActivity;
import com.fissionlabs.trucksfirst.singup.TFLoginActivity;

public class TFSplashActivity extends Activity {
    private static final long SPLASHTIME = 3000;
    TFSplashActivity _activity;
    private Handler splashTimeHandler;
    Runnable r;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
        _activity = this;


        // Show Splash screen for 3secs and navigate to login screen
        splashTimeHandler = new Handler();
        r = new Runnable() {
            @Override
            public void run() {
                finish();
                startActivity(new Intent(TFSplashActivity.this,
                        TFLoginActivity.class));
            }
        };
        splashTimeHandler.postDelayed(r, SPLASHTIME);
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
    }

    public void FinishActivity(View v) {
        splashTimeHandler.removeCallbacks(r);
        finish();
        startActivity(new Intent(TFSplashActivity.this, TFLoginActivity.class));
    }

}

