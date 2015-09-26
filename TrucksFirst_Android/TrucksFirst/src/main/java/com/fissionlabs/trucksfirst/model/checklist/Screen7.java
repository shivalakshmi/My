package com.fissionlabs.trucksfirst.model.checklist;

/**
 * Created by Lakshmi on 26-09-2015.
 */
public class Screen7 {

    public boolean touchingDamage;
    public boolean sealIntactness;
    public boolean stepeneyInPlace;
    public boolean tyrePuncture;
    public boolean tyrePressure;
    public int timeTaken;

    public Screen7() {
        touchingDamage = false;
        sealIntactness = true;
        stepeneyInPlace = true;
        tyrePuncture = false;
        tyrePressure = true;
        timeTaken = 0;
    }
}
