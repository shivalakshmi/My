package com.fissionlabs.trucksfirst.model.checklist;

/**
 * Created by Lakshmi on 26-09-2015.
 */
public class Screen1 {

    public boolean leftWiper;
    public boolean rightWiper;
    public boolean lowBeamHeadlight;
    public boolean highBeamHeadlight;
    public boolean leftSideIndicator;
    public boolean rightSideIndicator;
    public boolean brakeLight;
    public boolean horn;
    public int timeTaken;

    public Screen1 () {
        leftWiper = true;
        rightWiper = true;
        lowBeamHeadlight = true;
        highBeamHeadlight = true;
        leftSideIndicator = true;
        rightSideIndicator = true;
        brakeLight = true;
        horn = true;
        timeTaken = 0;
    }

}