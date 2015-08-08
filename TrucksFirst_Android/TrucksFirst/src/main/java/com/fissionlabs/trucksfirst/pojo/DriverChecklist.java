package com.fissionlabs.trucksfirst.pojo;

/**
 * Created by Lakshmi on 08-08-2015.
 */
public class DriverChecklist {
    String id;
    String pilotId;
    String vehiclenumber;
    String pilotName;
    boolean drivingLicence;
    boolean uniform;
    boolean nonAlchoholic;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPilotId() {
        return pilotId;
    }

    public void setPilotId(String pilotId) {
        this.pilotId = pilotId;
    }

    public String getVehiclenumber() {
        return vehiclenumber;
    }

    public void setVehiclenumber(String vehiclenumber) {
        this.vehiclenumber = vehiclenumber;
    }

    public String getPilotName() {
        return pilotName;
    }

    public void setPilotName(String pilotName) {
        this.pilotName = pilotName;
    }

    public boolean isDrivingLicence() {
        return drivingLicence;
    }

    public void setDrivingLicence(boolean drivingLicence) {
        this.drivingLicence = drivingLicence;
    }

    public boolean isUniform() {
        return uniform;
    }

    public void setUniform(boolean uniform) {
        this.uniform = uniform;
    }

    public boolean isNonAlchoholic() {
        return nonAlchoholic;
    }

    public void setNonAlchoholic(boolean nonAlchoholic) {
        this.nonAlchoholic = nonAlchoholic;
    }
}
