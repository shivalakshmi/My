package com.fissionlabs.trucksfirst.checklist;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.fissionlabs.trucksfirst.R;

/**
 * @author ashok on 9/23/15.
 */
public class FifthScreenFragment extends CheckListCommonFragment {

    private  Button btnNext;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {



       // CheckListBaseFragment.moveToNext();
        View view = inflater.inflate(R.layout.fragment_fifth_screen_checklist, container, false);
        btnNext = (Button) view.findViewById(R.id.btnNext);

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                CheckListBaseFragment.moveToNext();
            }
        });

        return view;
    }


}
