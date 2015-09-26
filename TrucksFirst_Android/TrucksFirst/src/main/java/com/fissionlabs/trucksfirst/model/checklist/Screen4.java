package com.fissionlabs.trucksfirst.model.checklist;

/**
 * Created by Lakshmi on 26-09-2015.
 */
public class Screen4 {
    public boolean invoice;
    public boolean lr;
    public boolean tp;
    public boolean gatiDocBag;
    public boolean areDocsAvailable;
    public boolean docsGivenToDriver;
    public int timeTaken;

    public Screen4() {
        invoice = true;
        lr = true;
        tp = true;
        gatiDocBag = true;
        areDocsAvailable = true;
        docsGivenToDriver = true;
        timeTaken = 0;
    }
}
