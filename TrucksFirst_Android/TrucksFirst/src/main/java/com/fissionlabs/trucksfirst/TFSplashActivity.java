package com.fissionlabs.trucksfirst;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.fissionlabs.trucksfirst.common.TFCommonActivity;
import com.fissionlabs.trucksfirst.home.TFHomeActivity;
import com.fissionlabs.trucksfirst.signup.TFLoginActivity;
import com.fissionlabs.trucksfirst.util.TFUtils;

public class TFSplashActivity extends TFCommonActivity {
    private static final long SPLASH_TIME = 3000;
    private Handler mSplashTimeHandler;
    private Runnable runnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // Show Splash screen for 3secs and navigate to login screen
        mSplashTimeHandler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                launchHome();
            }
        };
        mSplashTimeHandler.postDelayed(runnable, SPLASH_TIME);
    }

    public void finishActivity(View v) {
        mSplashTimeHandler.removeCallbacks(runnable);
        launchHome();
    }


    private void launchHome(){
        if(TFUtils.getBooleanFromSP(TFSplashActivity.this,IS_USER_EXISTS)){
            startActivity(new Intent(TFSplashActivity.this, TFHomeActivity.class));
            finish();
        } else {
            startActivity(new Intent(TFSplashActivity.this, TFLoginActivity.class));
            finish();
        }

    }

}

