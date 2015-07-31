package com.fissionlabs.trucksfirst.model;

import java.util.ArrayList;

/**
 * Created by Ashok on 7/14/2015.
 */
public class Truck {

    private ArrayList<TruckDetails> mTruckDetailsList;

    public ArrayList<TruckDetails> getTruckDetailsList() {
        return mTruckDetailsList;
    }

    public void setTruckDetailsList(ArrayList<TruckDetails> mTruckDetailsList) {
        this.mTruckDetailsList = mTruckDetailsList;
    }
}
