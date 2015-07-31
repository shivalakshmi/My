package com.fissionlabs.trucksfirst.home;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.text.format.DateFormat;
import android.view.MenuItem;
import android.widget.TextView;

import com.fissionlabs.trucksfirst.R;
import com.fissionlabs.trucksfirst.common.TFCommonActivity;
import com.fissionlabs.trucksfirst.fragments.TFCheckListFragment;
import com.fissionlabs.trucksfirst.fragments.TFDashBoardFragment;
import com.fissionlabs.trucksfirst.fragments.TFPilotFragment;
import com.fissionlabs.trucksfirst.fragments.TFSettingsFragment;
import com.fissionlabs.trucksfirst.fragments.TFTruckFragment;
import com.fissionlabs.trucksfirst.signup.TFLoginActivity;
import com.fissionlabs.trucksfirst.util.TFUtils;

import junit.framework.Assert;

import java.util.Date;

public class TFHomeActivity extends TFCommonActivity {

    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;

    private TFDashBoardFragment mTFDashBoardFragment;
    private TFTruckFragment mTFTruckFragment;
    private TFPilotFragment mTFPilotFragment;
    private TFSettingsFragment mTFSettingsFragment;
    private TFCheckListFragment mTFCheckListFragment;
    private Fragment mSelectedFragment;

    public ActionBar mActionBar;
    private TextView mTvCurrentDateAndTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mActionBar = getSupportActionBar();

        if (mActionBar != null) {
            mActionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
            mActionBar.setDisplayHomeAsUpEnabled(true);
        }

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mTvCurrentDateAndTime = (TextView) findViewById(R.id.tv_date_time);

        mNavigationView = (NavigationView) findViewById(R.id.nav_view);
        if (mNavigationView != null) {
            setupDrawerContent(mNavigationView);
        }

        loadFragment(R.layout.fragment_truck, null);

        Thread thread = new Thread(new CountDownRunner());
        thread.start();

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
                loadFragment(R.layout.fragment_truck, null);
                break;
            case R.id.nav_settings:
                loadFragment(R.layout.fragment_settings, null);
                break;
            case R.id.nav_logout:

                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
                dialogBuilder.setTitle(getString(R.string.are_you_sure));
                dialogBuilder.setPositiveButton(getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        goToLogin();
                    }
                });
                dialogBuilder.setNegativeButton(getResources().getString(R.string.no), null);

                AlertDialog alertDialog = dialogBuilder.create();
                alertDialog.getWindow().setLayout(500, 200);

                alertDialog.show();

                break;
            default:
                break;
        }

    }

    /**
     * ******************* Fragment ***********************
     */
    public void loadFragment(int fragResId, Bundle bundle) {

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
            case R.layout.fragment_settings:
                if (mTFSettingsFragment == null) {
                    mTFSettingsFragment = new TFSettingsFragment();
                }

                selectedFragment = mTFSettingsFragment;

                break;
            case R.layout.fragment_check_list:
                if (mTFCheckListFragment == null) {
                    mTFCheckListFragment = new TFCheckListFragment();
                }

                selectedFragment = mTFCheckListFragment;
        }


        Assert.assertTrue(selectedFragment != null);
        loadFragment(selectedFragment, bundle);
    }

    public void loadFragment(Fragment fragment, Bundle bundle) {
        Assert.assertTrue(fragment != null);
        replaceFragment(fragment, bundle);
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

    private void replaceFragment(Fragment fragment, Bundle bundle) {
        String backStateName = fragment.getClass().getName();

        FragmentManager manager = getSupportFragmentManager();
        boolean fragmentPopped = manager.popBackStackImmediate(backStateName, 0);

        if(!fragment.isAdded()) {
            fragment.setArguments(bundle);
        }

        if (!fragmentPopped && manager.findFragmentByTag(backStateName) == null) {
            FragmentTransaction ft = manager.beginTransaction();
            ft.replace(R.id.frame_container, fragment, backStateName);
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            ft.addToBackStack(backStateName);
            ft.commit();
        }
    }

    private void goToLogin() {
        TFUtils.deleteFromSP(TFHomeActivity.this, LANG_SELECTION);
        Intent intent = new Intent(TFHomeActivity.this, TFLoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {

        if (mDrawerLayout.isDrawerOpen(mNavigationView)) {
            mDrawerLayout.closeDrawers();
            return;
        }

        if (getSupportFragmentManager().getBackStackEntryCount() == 1) {
            finish();
        } else {
            super.onBackPressed();
        }

    }

    public void doWork() {
        runOnUiThread(new Runnable() {
            public void run() {
                try {
                    Date date = new Date();
                    CharSequence dateFormat = DateFormat.format("dd/MM/yyyy k:mm:ss", date.getTime());
                    mTvCurrentDateAndTime.setText(" " + dateFormat);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    class CountDownRunner implements Runnable {

        @Override
        public void run() {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    doWork();
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
