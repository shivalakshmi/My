package com.fissionlabs.trucksfirst.model;

/**
 * Created by Lakshmi on 23-07-2015.
 */
public class PilotAvailability {

    String pilotId;
    String pilotFirstName;
    String pilotLastName;
    String nextAvailabilityTime;
    String availabilityStatus;
    String contactNumber;

    public String getPilotId() {
        return pilotId;
    }

    public void setPilotId(String pilotId) {
        this.pilotId = pilotId;
    }

    public String getPilotFirstName() {
        return pilotFirstName;
    }

    public void setPilotFirstName(String pilotFirstName) {
        this.pilotFirstName = pilotFirstName;
    }

    public String getPilotLastName() {
        return pilotLastName;
    }

    public void setPilotLastName(String pilotLastName) {
        this.pilotLastName = pilotLastName;
    }

    public String getNextAvailabilityTime() {
        return nextAvailabilityTime;
    }

    public void setNextAvailabilityTime(String nextAvailabilityTime) {
        this.nextAvailabilityTime = nextAvailabilityTime;
    }

    public String getAvailabilityStatus() {
        return availabilityStatus;
    }

    public void setAvailabilityStatus(String availabilityStatus) {
        this.availabilityStatus = availabilityStatus;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }
}
