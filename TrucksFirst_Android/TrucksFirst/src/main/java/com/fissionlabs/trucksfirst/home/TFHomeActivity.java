package com.fissionlabs.trucksfirst.home;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.fissionlabs.trucksfirst.R;
import com.fissionlabs.trucksfirst.common.TFCommonActivity;
import com.fissionlabs.trucksfirst.fragments.TFDashBoardFragment;
import com.fissionlabs.trucksfirst.fragments.TFPilotFragment;
import com.fissionlabs.trucksfirst.fragments.TFTruckFragment;

import junit.framework.Assert;

public class TFHomeActivity extends TFCommonActivity {

    private DrawerLayout mDrawerLayout;

    private TFDashBoardFragment mTFDashBoardFragment;
    private TFTruckFragment mTFTruckFragment;
    private TFPilotFragment mTFPilotFragment;
    private Fragment mSelectedFragment;

    private ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        if (navigationView != null) {
            setupDrawerContent(navigationView);
        }

        loadFragment(R.layout.fragment_truck);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {

                        menuItem.setChecked(true);
                        mDrawerLayout.closeDrawers();

                        displayView(menuItem);
                        return true;
                    }
                });
    }

    /**
     * Displaying fragment view for selected nav drawer list item
     */
    private void displayView(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.nav_dashboard:
                loadFragment(R.layout.fragment_dash_board);
                break;
            case R.id.nav_truck_details:
                loadFragment(R.layout.fragment_truck);
                break;
            case R.id.nav_pilot_details:
                loadFragment(R.layout.fragment_pilot);
                break;
            default:
                break;
        }

    }

    /**
     * ******************* Fragment ***********************
     */
    public void loadFragment(int fragResId) {

        Fragment selectedFragment = null;

        switch (fragResId) {

            case R.layout.fragment_dash_board:
                if (mTFDashBoardFragment == null) {
                    mTFDashBoardFragment = new TFDashBoardFragment();
                }

                selectedFragment = mTFDashBoardFragment;

                break;

            case R.layout.fragment_truck:
                if (mTFTruckFragment == null) {
                    mTFTruckFragment = new TFTruckFragment();
                }

                selectedFragment = mTFTruckFragment;

                break;
            case R.layout.fragment_pilot:
                if (mTFPilotFragment == null) {
                    mTFPilotFragment = new TFPilotFragment();
                }

                selectedFragment = mTFPilotFragment;

                break;
        }


        Assert.assertTrue(selectedFragment != null);
        loadFragment(selectedFragment);
    }

    public void loadFragment(Fragment fragment) {
        Assert.assertTrue(fragment != null);
        replaceFragment(fragment);
    }

    public void addFragment(Fragment fragment) {

        if (mSelectedFragment != fragment) {
            mSelectedFragment = fragment;

            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.frame_container, mSelectedFragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }
    }

    public void replaceFragment(Fragment fragment) {

        if (mSelectedFragment != fragment) {
            mSelectedFragment = fragment;

            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frame_container, mSelectedFragment);
            fragmentTransaction.commit();
        }
    }

}
