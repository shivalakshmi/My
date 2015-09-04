package com.fissionlabs.trucksfirst.fragments;


import android.app.Fragment;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.text.Editable;
import android.text.Html;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.fissionlabs.trucksfirst.R;
import com.fissionlabs.trucksfirst.common.TFCommonFragment;
import com.fissionlabs.trucksfirst.common.TFConst;
import com.fissionlabs.trucksfirst.util.TFUtils;

import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 */
public class TFSettingsFragment extends TFCommonFragment implements TFConst{
    private Spinner mSpinner;
    private LinearLayout mLangSpinnerLayout;
    private Button mChangePasswordBtn;
    private LinearLayout mChangePasswordLayout;
    private EditText mOldPassword;
    private EditText mNewPassword;
    private EditText mConfirmNewPassword;
    private Button mSubmit;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_settings, container, false);

        mHomeActivity.isHomeFragment = false;

        mHomeActivity.mActionBar.setDisplayShowTitleEnabled(true);
        mHomeActivity.imageView.setVisibility(View.GONE);

        mHomeActivity.toolbar.setNavigationIcon(R.drawable.ic_back);
        mHomeActivity.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mHomeActivity.isHomeFragment == false)
                    mHomeActivity.onBackPressed();
                else
                    mHomeActivity.mDrawerLayout.openDrawer(GravityCompat.START);
            }
        });

        mSpinner = (Spinner) rootView.findViewById(R.id.spinner);
        mLangSpinnerLayout = (LinearLayout)rootView.findViewById(R.id.lang_spinner_layout);
        mChangePasswordBtn = (Button) rootView.findViewById(R.id.change_password_btn);
        mChangePasswordLayout = (LinearLayout) rootView.findViewById(R.id.change_password_layout);
        mOldPassword = (EditText) rootView.findViewById(R.id.old_password);
        mNewPassword = (EditText) rootView.findViewById(R.id.new_password);
        mConfirmNewPassword = (EditText) rootView.findViewById(R.id.confirm_new_password);
        mSubmit = (Button) rootView.findViewById(R.id.submit);

        mOldPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,int after) {  }
            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0) {
                    mOldPassword.setError(null);
                } else {
                    mOldPassword.setError(Html.fromHtml("<font color='white'><big><b>" + getResources().getString(R.string.error_msg) + "</b></big></font>"));
                }
            }
        });
        mNewPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,int after) {  }
            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0) {
                    mNewPassword.setError(null);
                } else {
                    mNewPassword.setError(Html.fromHtml("<font color='white'><big><b>" + getResources().getString(R.string.error_msg) + "</b></big></font>"));
                }
            }
        });
        mConfirmNewPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,int after) {  }
            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0) {
                    mConfirmNewPassword.setError(null);
                } else {
                    mConfirmNewPassword.setError(Html.fromHtml("<font color='white'><big><b>" + getResources().getString(R.string.error_msg) + "</b></big></font>"));
                }
            }
        });

        mChangePasswordBtn.setVisibility(View.VISIBLE);
        mLangSpinnerLayout.setVisibility(View.VISIBLE);
        mChangePasswordLayout.setVisibility(View.GONE);

        mChangePasswordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mChangePasswordBtn.setVisibility(View.GONE);
                mLangSpinnerLayout.setVisibility(View.GONE);
                mChangePasswordLayout.setVisibility(View.VISIBLE);
            }
        });
        mSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changePasswordSubmit();

            }
        });


        mSpinner.setSelection(TFUtils.getIntFromSP(getActivity(), LANG_SELECTION));
        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {
                switch (pos) {
                    case 0:
                        TFUtils.saveIntInSP(getActivity(), LANG_SELECTION, pos);
                        setLocale("en");
                        break;
                    case 1:
                        TFUtils.saveIntInSP(getActivity(), LANG_SELECTION, pos);
                        setLocale("hi");
                        break;
                    default:
                        TFUtils.saveIntInSP(getActivity(), LANG_SELECTION, pos);
                        setLocale("en");
                        break;
                }
            }

            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        return rootView;
    }

    private void changePasswordSubmit() {
        if (TextUtils.isEmpty(mOldPassword.getText().toString().trim()) || TextUtils.isEmpty(mNewPassword.getText().toString().trim())
                || TextUtils.isEmpty(mConfirmNewPassword.getText().toString().trim())) {
            if (TextUtils.isEmpty(mOldPassword.getText().toString().trim())) {
                mOldPassword.setError(Html.fromHtml("<font color='white'><big><b>" + getResources().getString(R.string.error_msg) + "</b></big></font>"));
                mOldPassword.requestFocus();
            }
            if (TextUtils.isEmpty(mNewPassword.getText().toString().trim())) {
                mNewPassword.setError(Html.fromHtml("<font color='white'><big><b>" + getResources().getString(R.string.error_msg) + "</b></big></font>"));
            }
            if (TextUtils.isEmpty(mConfirmNewPassword.getText().toString().trim())) {
                mConfirmNewPassword.setError(Html.fromHtml("<font color='white'><big><b>" + getResources().getString(R.string.error_msg) + "</b></big></font>"));
            }
            return;
        }
        if(!mOldPassword.getText().toString().trim().equals(TFUtils.getStringFromSP(getActivity(),EMP_USER_PASSWORD))){
            Toast.makeText(getActivity(),"Old password is not matched.",Toast.LENGTH_SHORT).show();
            return;
        }
        if(!mNewPassword.getText().toString().trim().equals(mConfirmNewPassword.getText().toString().trim())){
            Toast.makeText(getActivity(),"New password  and confirm password is not matched.",Toast.LENGTH_SHORT).show();
            return;
        }





    }

    private void setLocale(String lang) {
        Locale myLocale = new Locale(lang);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mHomeActivity.toolbar.setNavigationIcon(R.drawable.ic_menu);
        mHomeActivity.isHomeFragment = true;
        mHomeActivity.mActionBar.setDisplayShowTitleEnabled(false);
        mHomeActivity.imageView.setVisibility(View.VISIBLE);
    }

}
