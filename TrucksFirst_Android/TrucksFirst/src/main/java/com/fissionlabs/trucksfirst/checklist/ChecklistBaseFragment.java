package com.fissionlabs.trucksfirst.checklist;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.fissionlabs.trucksfirst.R;
/**
 * @author ashok on 9/23/15.
 */
public class CheckListBaseFragment extends CheckListCommonFragment {

    private static ViewPager mViewPager;
    private ScreenSlidePagerAdapter mPagerAdapter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_base_checklist, container, false);
        mViewPager = (ViewPager) view.findViewById(R.id.vpBaseChecklist);
        mPagerAdapter = new ScreenSlidePagerAdapter(getActivity().getSupportFragmentManager());
        mViewPager.setAdapter(mPagerAdapter);
        return view;
    }

    public static void moveToNext() {

      //  try {
            mViewPager.setCurrentItem(mViewPager.getCurrentItem() + 1);

       // } catch (IllegalStateException e) {

       // }

    }

    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            Fragment f = null;

            switch (position) {

                case 0:
                    f = new FirstScreenFragment();
                    break;
                case 1:
                    f = new SecondScreenFragment();
                    break;
                case 2:
                    f = new ThirdScreenFragment();
                    break;
                case 3:
                    f = new FourthScreenFragment();
                    break;
                case 4:
                    f = new FifthScreenFragment();
                    break;
                case 5:
                    f = new SixthScreenFragment();
                    break;
                case 6:
                    f = new SeventhScreenFragment();
                    break;
                case 7:
                    f = new EighthScreenFragment();
                    break;
                case 8:
                    f = new NinethScreenFragment();
                    break;
                case 9:
                    f = new TenthScreenFragment();
                    break;
                case 10:
                    f = new ElewenthScreenFragment();
                    break;
            }

            return f;
        }

        @Override
        public int getCount() {
            return 11;
        }
    }

}
