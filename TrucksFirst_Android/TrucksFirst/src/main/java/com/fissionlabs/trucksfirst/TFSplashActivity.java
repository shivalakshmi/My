package com.fissionlabs.trucksfirst;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.fissionlabs.trucksfirst.singup.TFLoginActivity;

public class TFSplashActivity extends Activity {
    private static final long SPLASH_TIME = 3000;
    private TFSplashActivity _activity;
    private Handler splashTimeHandler;
    private Runnable r;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
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
        splashTimeHandler.postDelayed(r, SPLASH_TIME);
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
    }

    public void finishActivity(View v) {
        splashTimeHandler.removeCallbacks(r);
        finish();
        startActivity(new Intent(TFSplashActivity.this, TFLoginActivity.class));
    }

}

