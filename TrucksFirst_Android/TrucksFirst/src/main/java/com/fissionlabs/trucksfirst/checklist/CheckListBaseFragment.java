package com.fissionlabs.trucksfirst.checklist;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fissionlabs.trucksfirst.R;

import java.util.ArrayList;

/**
 * @author ashok on 9/23/15.
 */
public class CheckListBaseFragment extends CheckListCommonFragment {

    private static CustomViewPager mViewPager;
    private ScreenSlidePagerAdapter mPagerAdapter;
    private ArrayList<CheckListCommonFragment> fragmentArrayList = new ArrayList<>();


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_base_checklist, container, false);
        mViewPager = (CustomViewPager) view.findViewById(R.id.vpBaseChecklist);

        fragmentArrayList = getFragmentArrayList();

        mPagerAdapter = new ScreenSlidePagerAdapter(getChildFragmentManager());


        mViewPager.setAdapter(mPagerAdapter);
        mViewPager.setPagingEnabled(false);
        return view;
    }

    public static void moveToNext() {

        mViewPager.setCurrentItem(mViewPager.getCurrentItem() + 1);
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
        fragmentArrayList.add(new NinethScreenFragment());
        fragmentArrayList.add(new TenthScreenFragment());

        return fragmentArrayList;


    }

}
