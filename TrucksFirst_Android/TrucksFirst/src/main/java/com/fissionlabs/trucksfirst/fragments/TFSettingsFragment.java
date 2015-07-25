package com.fissionlabs.trucksfirst.fragments;


import android.app.Fragment;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;

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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_settings, container, false);

        mSpinner = (Spinner) rootView.findViewById(R.id.spinner);
        mSpinner.setSelection(TFUtils.getIntFromSP(getActivity(),LANG_SELECTION));
        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {
                if (pos == 0) {
                    TFUtils.saveIntInSP(getActivity(),LANG_SELECTION,pos);
                    setLocale("en");
                } else if (pos == 1) {
                    TFUtils.saveIntInSP(getActivity(),LANG_SELECTION,pos);
                    setLocale("hi");
                }
            }
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }
        });

        return rootView;
    }
    public void setLocale(String lang) {
        Locale myLocale = new Locale(lang);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);
    }

}
