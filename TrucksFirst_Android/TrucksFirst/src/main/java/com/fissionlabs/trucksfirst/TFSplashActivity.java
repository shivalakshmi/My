package com.fissionlabs.trucksfirst;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.fissionlabs.trucksfirst.common.TFCommonActivity;
import com.fissionlabs.trucksfirst.singup.TFLoginActivity;

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
                finish();
                startActivity(new Intent(TFSplashActivity.this,
                        TFLoginActivity.class));
            }
        };
        mSplashTimeHandler.postDelayed(runnable, SPLASH_TIME);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public void finishActivity(View v) {
        mSplashTimeHandler.removeCallbacks(runnable);
        finish();
        startActivity(new Intent(TFSplashActivity.this, TFLoginActivity.class));
    }

}

