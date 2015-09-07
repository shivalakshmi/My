package com.fissionlabs.trucksfirst.signup;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.ResultReceiver;
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
import android.widget.LinearLayout;
import android.widget.Toast;

import com.fissionlabs.trucksfirst.R;
import com.fissionlabs.trucksfirst.common.TFCommonActivity;
import com.fissionlabs.trucksfirst.home.TFHomeActivity;
import com.fissionlabs.trucksfirst.model.LoginResponse;
import com.fissionlabs.trucksfirst.util.TFUtils;
import com.fissionlabs.trucksfirst.webservices.WebServices;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.HashMap;

public class TFLoginActivity extends TFCommonActivity {
    private EditText mEtUserName;
    private EditText mEtPassword;
    private LinearLayout mUserLoginLayout;
    private LinearLayout mUserForgotPasswordLayout;
    private EditText usernameForgot;
    private EditText contactNo;
    private EditText newPassword;
    private EditText newConfirmPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        mUserLoginLayout = (LinearLayout) findViewById(R.id.userLoginLayout);
        mUserForgotPasswordLayout = (LinearLayout) findViewById(R.id.forgotPasswordLayout);

        mUserLoginLayout.setVisibility(View.VISIBLE);
        mUserForgotPasswordLayout.setVisibility(View.GONE);
        mEtUserName = (EditText) findViewById(R.id.username);
        mEtPassword = (EditText) findViewById(R.id.password);

        usernameForgot = (EditText) findViewById(R.id.user_name_forgot);
        contactNo = (EditText) findViewById(R.id.contact_no);
        newPassword = (EditText) findViewById(R.id.new_password);
        newConfirmPassword = (EditText) findViewById(R.id.confirm_new_password);

        usernameForgot.addTextChangedListener(new TextWatcher() {
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
                    usernameForgot.setError(null);
                } else {
                    usernameForgot.setError(Html.fromHtml("<font color='white'><big><b>" + getResources().getString(R.string.error_msg) + "</b></big></font>"));
                }
            }
        });
        contactNo.addTextChangedListener(new TextWatcher() {
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
                    contactNo.setError(null);
                } else {
                    contactNo.setError(Html.fromHtml("<font color='white'><big><b>" + getResources().getString(R.string.error_msg) + "</b></big></font>"));
                }
            }
        });
        newPassword.addTextChangedListener(new TextWatcher() {
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
                    newPassword.setError(null);
                } else {
                    newPassword.setError(Html.fromHtml("<font color='white'><big><b>" + getResources().getString(R.string.error_msg) + "</b></big></font>"));
                }
            }
        });
        newConfirmPassword.addTextChangedListener(new TextWatcher() {
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
                    newConfirmPassword.setError(null);
                } else {
                    newConfirmPassword.setError(Html.fromHtml("<font color='white'><big><b>" + getResources().getString(R.string.error_msg) + "</b></big></font>"));
                }
            }
        });

        findViewById(R.id.btnLogin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userLoginChecking();
            }
        });
        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mUserLoginLayout.setVisibility(View.VISIBLE);
                mUserForgotPasswordLayout.setVisibility(View.GONE);
            }
        });
        findViewById(R.id.submit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                forgotPassword();
            }
        });
        findViewById(R.id.tvForgotPassword).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mUserLoginLayout.setVisibility(View.GONE);
                mUserForgotPasswordLayout.setVisibility(View.VISIBLE);
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

    private void forgotPassword() {
        if (TextUtils.isEmpty(usernameForgot.getText().toString().trim()) || TextUtils.isEmpty(contactNo.getText().toString().trim())
              || TextUtils.isEmpty(newPassword.getText().toString().trim()) || TextUtils.isEmpty(newConfirmPassword.getText().toString().trim())) {
            if (TextUtils.isEmpty(usernameForgot.getText().toString().trim())) {
                usernameForgot.setError(Html.fromHtml("<font color='white'><big><b>" + getResources().getString(R.string.error_msg) + "</b></big></font>"));
                usernameForgot.requestFocus();
            }
            else if (TextUtils.isEmpty(contactNo.getText().toString().trim())) {
                contactNo.setError(Html.fromHtml("<font color='white'><big><b>" + getResources().getString(R.string.error_msg) + "</b></big></font>"));
            }
            else if (TextUtils.isEmpty(newPassword.getText().toString().trim())) {
                newPassword.setError(Html.fromHtml("<font color='white'><big><b>" + getResources().getString(R.string.error_msg) + "</b></big></font>"));
            }
            else if (TextUtils.isEmpty(newConfirmPassword.getText().toString().trim())) {
                newConfirmPassword.setError(Html.fromHtml("<font color='white'><big><b>" + getResources().getString(R.string.error_msg) + "</b></big></font>"));
            }
            return;
        }
        if(contactNo.getText().toString().trim().length()<10){
            Toast.makeText(TFLoginActivity.this,getResources().getString(R.string.contact_no_ten_digits),Toast.LENGTH_LONG).show();
            return;
        }
        if(!newPassword.getText().toString().trim().equals(newConfirmPassword.getText().toString().trim())){
            Toast.makeText(TFLoginActivity.this,getResources().getString(R.string.password_repassword_not_matched),Toast.LENGTH_LONG).show();
            return;
        }
        TFUtils.showProgressBar(TFLoginActivity.this,getResources().getString(R.string.please_wait));
        HashMap<String, String> params = new HashMap<>();
        params.put("userName", usernameForgot.getText().toString().trim());
        params.put("contactNo", contactNo.getText().toString().trim());
        params.put("newPassword", newPassword.getText().toString().trim());
        new WebServices().forgotPasswordUrl(TFLoginActivity.this, new JSONObject(params), new ResultReceiver(null) {
            @Override
            protected void onReceiveResult(int resultCode, Bundle resultData) {

                TFUtils.hideProgressBar();
                if (resultData != null) {
                    String responseStr = resultData.getString("response");
                    Toast.makeText(TFLoginActivity.this, getResources().getString(R.string.password_changed_sucess), Toast.LENGTH_SHORT).show();
                    mUserLoginLayout.setVisibility(View.VISIBLE);
                    mUserForgotPasswordLayout.setVisibility(View.GONE);
                    mEtUserName.setText(" ");
                }else{
                    Toast.makeText(TFLoginActivity.this, getResources().getString(R.string.worng_user_info_found), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void userLoginChecking() {

        if (TextUtils.isEmpty(mEtUserName.getText().toString().trim()) || TextUtils.isEmpty(mEtPassword.getText().toString().trim())) {
            if (TextUtils.isEmpty(mEtUserName.getText().toString().trim())) {
                mEtUserName.setError(Html.fromHtml("<font color='white'><big><b>" + getResources().getString(R.string.error_msg) + "</b></big></font>"));
                mEtPassword.requestFocus();
            }
            if (TextUtils.isEmpty(mEtPassword.getText().toString().trim())) {
                mEtPassword.setError(Html.fromHtml("<font color='white'><big><b>" + getResources().getString(R.string.error_msg) + "</b></big></font>"));
            }
            return;
        }

        HashMap<String, String> params = new HashMap<>();
        params.put("userName", mEtUserName.getText().toString().trim());
        params.put("password", mEtPassword.getText().toString().trim());

        TFUtils.showProgressBar(this, getString(R.string.please_wait));

        new WebServices().userLogin(getApplicationContext(), new JSONObject(params), new ResultReceiver(null) {
            @Override
            protected void onReceiveResult(int resultCode, Bundle resultData) {

                TFUtils.hideProgressBar();

                if (resultData != null) {

                    LoginResponse loginResponse = new Gson().fromJson(resultData.getString("response"), LoginResponse.class);

                    if (loginResponse.success.equalsIgnoreCase("true")) {
                        TFUtils.saveStringInSP(TFLoginActivity.this, HUB_NAME, loginResponse.result.getHubName());
                        TFUtils.saveStringInSP(TFLoginActivity.this,HS_NAME, loginResponse.result.getUserName());
                        TFUtils.saveStringInSP(TFLoginActivity.this, EMP_ID, loginResponse.result.getEmpId());
                        TFUtils.saveStringInSP(TFLoginActivity.this, EMP_USER_NAME, mEtUserName.getText().toString().trim());
                        TFUtils.saveStringInSP(TFLoginActivity.this, EMP_USER_PASSWORD, mEtPassword.getText().toString().trim());
                        TFUtils.saveBooleanInSP(TFLoginActivity.this, IS_USER_EXISTS, true);
                        Intent i = new Intent(TFLoginActivity.this, TFHomeActivity.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(i);
                    } else {
                        Toast.makeText(getApplicationContext(), "" + loginResponse.message, Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

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
