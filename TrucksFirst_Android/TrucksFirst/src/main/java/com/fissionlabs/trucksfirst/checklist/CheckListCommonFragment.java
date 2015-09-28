package com.fissionlabs.trucksfirst.checklist;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fissionlabs.trucksfirst.common.TFCommonFragment;
import com.fissionlabs.trucksfirst.model.checklist.NewChecklist;

/**
 * @author ashok on 9/23/15.
 */
public class CheckListCommonFragment extends TFCommonFragment {
    protected NewChecklist checklist;
    // TODO: Initialise it in createView of CheckListBaseFragement

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }
}
