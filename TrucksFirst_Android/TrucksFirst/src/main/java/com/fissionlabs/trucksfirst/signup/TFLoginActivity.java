package com.fissionlabs.trucksfirst.signup;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Looper;
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
import android.widget.Toast;

import com.fissionlabs.trucksfirst.R;
import com.fissionlabs.trucksfirst.common.TFCommonActivity;
import com.fissionlabs.trucksfirst.common.TFConst;
import com.fissionlabs.trucksfirst.home.TFHomeActivity;
import com.fissionlabs.trucksfirst.util.TFUtils;
import com.google.gson.Gson;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.protocol.HTTP;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

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
        new LoginAsynTask(mEtUserName.getText().toString(),mEtPassword.getText().toString()).execute();

    }

    class LoginAsynTask extends AsyncTask<Void,Void, InputStream>{
        String userName;
        String password;
        LoginAsynTask(String userName,String password){
            this.userName = userName;
            this.password = password;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            TFUtils.showProgressBar(getApplicationContext(), getResources().getString(R.string.please_wait));
        }

        @Override
        protected InputStream doInBackground(Void... params) {
            HttpClient client = new DefaultHttpClient();
            HttpConnectionParams.setConnectionTimeout(client.getParams(), 10000); //Timeout Limit
            HttpResponse response = null;
            JSONObject json = new JSONObject();

            try {
                HttpPost post = new HttpPost(TFConst.URL_LOGIN);
                json.put("userName", userName);
                json.put("password", password);
                StringEntity se = new StringEntity( json.toString());
                se.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
                post.setEntity(se);
                response = client.execute(post);

                    /*Checking response */
                if(response!=null){
                    InputStream in = response.getEntity().getContent(); //Get the data in the entity
                    return  in;
                }

            } catch(Exception e) {
                e.printStackTrace();
            }
            return null;

        }

        @Override
        protected void onPostExecute(InputStream inputStream) {
            super.onPostExecute(inputStream);
            TFUtils.hideProgressBar();
            if (inputStream != null) {
                String temp = convertStreamToString(inputStream);
                LoginResponse loginResponse = new Gson().fromJson(temp, LoginResponse.class);
                if(loginResponse.success.equalsIgnoreCase("true")){
                    TFUtils.saveBooleanInSP(TFLoginActivity.this,IS_USER_EXISTS,true);
                    Intent i = new Intent(TFLoginActivity.this, TFHomeActivity.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(i);
                } else {
                    Toast.makeText(getApplicationContext(), "" + loginResponse.message, Toast.LENGTH_SHORT).show();
                }
           }
        }
        public String convertStreamToString(InputStream is) {
            StringBuilder sb = new StringBuilder();
            try{
                BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                String line = null;
                while ((line = reader.readLine()) != null) {
                    sb.append(line).append("\n");
                }

                is.close();
            } catch(OutOfMemoryError om){
                om.printStackTrace();
            } catch(Exception ex){
                ex.printStackTrace();
            }
            return sb.toString();
        }//convertStreamToString()
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
    class LoginResponse {

        public String message;
        public String success;
    }
}
