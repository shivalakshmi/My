package com.fissionlabs.trucksfirst.singup;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.fissionlabs.trucksfirst.R;
import com.fissionlabs.trucksfirst.common.TFCommonActivity;
import com.fissionlabs.trucksfirst.home.TFHomeActivity;

public class TFLoginActivity extends TFCommonActivity {
    private EditText mEtUserName;
    private EditText mEtPassword;
    private Button mBtnLogIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mEtUserName = (EditText) findViewById(R.id.username);
        mEtPassword = (EditText) findViewById(R.id.password);
        mBtnLogIn = (Button) findViewById(R.id.btnLogin);
        mBtnLogIn.setOnClickListener(new View.OnClickListener() {
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
                    mEtUserName.setError(getResources().getString(R.string.error_msg));
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
                    mEtPassword.setError(getResources().getString(R.string.error_msg));
                }

            }
        });
    }

    private void userLoginChecking() {
        if (mEtUserName.getText().toString().trim().isEmpty() || mEtPassword.getText().toString().trim().isEmpty()) {
            if (mEtUserName.getText().toString().trim().isEmpty()) {
                mEtUserName.setError(getResources().getString(R.string.error_msg));
                mEtPassword.requestFocus();
            }
            if (mEtPassword.getText().toString().trim().isEmpty())
                mEtPassword.setError(getResources().getString(R.string.error_msg));
            return;
        }

        Intent i = new Intent(TFLoginActivity.this, TFHomeActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
    }

}
