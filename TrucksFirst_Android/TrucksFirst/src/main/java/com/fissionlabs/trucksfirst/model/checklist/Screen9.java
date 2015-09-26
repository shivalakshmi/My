package com.fissionlabs.trucksfirst.model.checklist;

/**
 * Created by Lakshmi on 26-09-2015.
 */
public class Screen9 {

    public boolean engineOilLeakage;
    public boolean steeringOilLeakage;
    public boolean temperatureCheck;
    public boolean fuelDieselLeakage;
    public boolean coolantLevel;
    public boolean visualInspection;
    public int timeTaken;

    public Screen9() {
        engineOilLeakage = false;
        steeringOilLeakage = false;
        temperatureCheck = true;
        fuelDieselLeakage = false;
        coolantLevel = true;
        visualInspection = true;
        timeTaken = 0;
    }
}
