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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_settings, container, false);

        Spinner mSpinner = (Spinner) rootView.findViewById(R.id.spinner);
        mSpinner.setSelection(TFUtils.getIntFromSP(getActivity(),LANG_SELECTION));
        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {
                switch (pos){
                    case 0:
                        TFUtils.saveIntInSP(getActivity(),LANG_SELECTION,pos);
                        setLocale("en");
                        break;
                    case 1:
                        TFUtils.saveIntInSP(getActivity(),LANG_SELECTION,pos);
                        setLocale("hi");
                        break;
                    default:
                        TFUtils.saveIntInSP(getActivity(),LANG_SELECTION,pos);
                        setLocale("en");
                        break;
                }
            }
            public void onNothingSelected(AdapterView<?> arg0) {
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
