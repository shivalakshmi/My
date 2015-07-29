package com.fissionlabs.trucksfirst.common;


import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.fissionlabs.trucksfirst.home.TFHomeActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class TFCommonFragment extends Fragment {


    protected TFHomeActivity mHomeActivity;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mHomeActivity = (TFHomeActivity) getActivity();
    }

    public void startFragment(int fragmentResId, Bundle bundle) {
        mHomeActivity.loadFragment(fragmentResId, bundle);
    }
}
