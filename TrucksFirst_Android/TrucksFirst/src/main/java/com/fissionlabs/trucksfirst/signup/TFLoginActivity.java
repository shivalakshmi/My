package com.fissionlabs.trucksfirst.signup;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.Html;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.fissionlabs.trucksfirst.R;
import com.fissionlabs.trucksfirst.common.TFCommonActivity;
import com.fissionlabs.trucksfirst.home.TFHomeActivity;

public class TFLoginActivity extends TFCommonActivity {
    private EditText mEtUserName;
    private EditText mEtPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        mEtUserName = (EditText) findViewById(R.id.username);
        mEtPassword = (EditText) findViewById(R.id.password);
        findViewById(R.id.btnLogin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userLoginChecking();
            }
        });
        mEtUserName.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if (s.length() > 0) {
                    mEtUserName.setError(null);
                } else {
                    mEtUserName.setError(Html.fromHtml("<font color='white'><big><b>" + getResources().getString(R.string.error_msg) + "</b></big></font>"));
                }
            }
        });
        mEtPassword.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0) {
                    mEtPassword.setError(null);
                } else {
                    mEtPassword.setError(Html.fromHtml("<font color='white'><big><b>" + getResources().getString(R.string.error_msg) + "</b></big></font>"));
                }

            }
        });
    }

    private void userLoginChecking() {

        if (TextUtils.isEmpty(mEtUserName.getText().toString()) || TextUtils.isEmpty(mEtPassword.getText().toString())) {
            if (TextUtils.isEmpty(mEtUserName.getText().toString())) {
                mEtUserName.setError(Html.fromHtml("<font color='white'><big><b>" + getResources().getString(R.string.error_msg) + "</b></big></font>"));
                mEtPassword.requestFocus();
            }
            if (TextUtils.isEmpty(mEtPassword.getText().toString())) {
                mEtPassword.setError(Html.fromHtml("<font color='white'><big><b>" + getResources().getString(R.string.error_msg) + "</b></big></font>"));
            }
            return;
        }

        Intent i = new Intent(TFLoginActivity.this, TFHomeActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
    }

    /**
     * Called to process touch screen events.
     */
    @Override
    public boolean dispatchTouchEvent(@NonNull MotionEvent ev) {
        View v = getCurrentFocus();

        if (v != null &&
                (ev.getAction() == MotionEvent.ACTION_UP) &&
                v instanceof EditText &&
                !v.getClass().getName().startsWith("android.webkit.")) {
            int scrcoords[] = new int[2];
            v.getLocationOnScreen(scrcoords);
            float x = ev.getRawX() + v.getLeft() - scrcoords[0];
            float y = ev.getRawY() + v.getTop() - scrcoords[1];

            if (x < v.getLeft() || x > v.getRight() || y < v.getTop() || y > v.getBottom())
                hideKeyboard(this);
        }
        return super.dispatchTouchEvent(ev);
    }

    private void hideKeyboard(Activity activity) {
        if (activity != null && activity.getWindow() != null && activity.getWindow().getDecorView() != null) {
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(activity.getWindow().getDecorView().getWindowToken(), 0);
        }
    }

}
