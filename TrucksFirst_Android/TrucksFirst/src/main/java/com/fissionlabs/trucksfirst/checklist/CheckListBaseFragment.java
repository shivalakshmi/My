package com.fissionlabs.trucksfirst.checklist;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fissionlabs.trucksfirst.R;
import com.fissionlabs.trucksfirst.util.TFUtils;

import java.util.ArrayList;

/**
 * @author ashok on 9/23/15.
 */
public class CheckListBaseFragment extends CheckListCommonFragment {

    private static CustomViewPager mViewPager;
    private ScreenSlidePagerAdapter mPagerAdapter;
    private ArrayList<CheckListCommonFragment> fragmentArrayList = new ArrayList<>();
    private Bundle bundle;
    private String vehicleNumber;

    public static void moveToNext() {

        mViewPager.setCurrentItem(mViewPager.getCurrentItem() + 1);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_base_checklist, container, false);
        mViewPager = (CustomViewPager) view.findViewById(R.id.vpBaseChecklist);

        bundle = this.getArguments();
        vehicleNumber = bundle.getString("vehicle_number");
        TFUtils.saveStringInSP(getActivity(), "vehicle_number", vehicleNumber);

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

        mHomeActivity.mActionBar.setTitle(vehicleNumber);



        fragmentArrayList = getFragmentArrayList();

        mPagerAdapter = new ScreenSlidePagerAdapter(getChildFragmentManager());


        mViewPager.setAdapter(mPagerAdapter);
         mViewPager.setPagingEnabled(false);
        return view;
    }

    private ArrayList<CheckListCommonFragment> getFragmentArrayList() {

        ArrayList<CheckListCommonFragment> fragmentArrayList = new ArrayList<>();

        fragmentArrayList.add(new FirstScreenFragment());
        fragmentArrayList.add(new SecondScreenFragment());
        fragmentArrayList.add(new ThirdScreenFragment());
        fragmentArrayList.add(new FourthScreenFragment());
        fragmentArrayList.add(new FifthScreenFragment());
        fragmentArrayList.add(new SixthScreenFragment());
        fragmentArrayList.add(new SeventhScreenFragment());
        fragmentArrayList.add(new EighthScreenFragment());
        fragmentArrayList.add(new NinthScreenFragment());
        fragmentArrayList.add(new TenthScreenFragment());
        fragmentArrayList.add(new EleventhScreenFragment());

        return fragmentArrayList;


    }

    private class ScreenSlidePagerAdapter extends FragmentPagerAdapter {
        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            return fragmentArrayList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentArrayList.size();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mHomeActivity.mActionBar.setTitle(getResources().getString(R.string.app_name));
        mHomeActivity.toolbar.setNavigationIcon(R.drawable.ic_menu);
        mHomeActivity.isHomeFragment = true;
        mHomeActivity.mActionBar.setDisplayShowTitleEnabled(false);
        mHomeActivity.imageView.setVisibility(View.VISIBLE);
    }

}
