package com.fissionlabs.trucksfirst.model;

/**
 * Created by Lakshmi on 23-07-2015.
 */
public class PilotAvailability {
    String pilotId;
    String pilotName;
    String pilotAvailabilityStatus;
    String vehicleNumber;
    String vehicleRoute;
    String eta;

    public String getEta() {
        return eta;
    }

    public void setEta(String eta) {
        this.eta = eta;
    }

    public String getPilotAvailabilityStatus() {
        return pilotAvailabilityStatus;
    }

    public void setPilotAvailabilityStatus(String pilotAvailabilityStatus) {
        this.pilotAvailabilityStatus = pilotAvailabilityStatus;
    }

    public String getPilotId() {
        return pilotId;
    }

    public void setPilotId(String pilotId) {
        this.pilotId = pilotId;
    }

    public String getPilotName() {
        return pilotName;
    }

    public void setPilotName(String pilotName) {
        this.pilotName = pilotName;
    }

    public String getVehicleNumber() {
        return vehicleNumber;
    }

    public void setVehicleNumber(String vehicleNumber) {
        this.vehicleNumber = vehicleNumber;
    }

    public String getVehicleRoute() {
        return vehicleRoute;
    }

    public void setVehicleRoute(String vehicleRoute) {
        this.vehicleRoute = vehicleRoute;
    }
}
