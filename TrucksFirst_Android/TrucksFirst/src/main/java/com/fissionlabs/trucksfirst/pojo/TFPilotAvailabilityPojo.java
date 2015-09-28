package com.fissionlabs.trucksfirst.pojo;

/**
 * Created by Lakshmi on 09-07-2015.
 */
class TFPilotAvailabilityPojo {

    private String pilotAvailability;
    private String vehicleNumber;
    private String vehicleRoute;
    private String eta;
    private String assignPilot;

    public TFPilotAvailabilityPojo(String pilotAvailability, String vehicleNumber,
                                   String vehicleRoute, String eta,
                                   String assignPilot) {
        this.pilotAvailability = pilotAvailability;
        this.vehicleNumber = vehicleNumber;
        this.vehicleRoute = vehicleRoute;
        this.eta = eta;
        this.assignPilot = assignPilot;
    }

    public String getAssignPilot() {
        return assignPilot;
    }

    public void setAssignPilot(String assignPilot) {
        this.assignPilot = assignPilot;
    }

    public String getEta() {
        return eta;
    }

    public void setEta(String eta) {
        this.eta = eta;
    }

    public String getPilotAvailability() {
        return pilotAvailability;
    }

    public void setPilotAvailability(String pilotAvailability) {
        this.pilotAvailability = pilotAvailability;
    }

    public String getVehicleRoute() {
        return vehicleRoute;
    }

    public void setVehicleRoute(String vehicleRoute) {
        this.vehicleRoute = vehicleRoute;
    }

    public String getVehicleNumber() {
        return vehicleNumber;
    }

    public void setVehicleNumber(String vehicleNumber) {
        this.vehicleNumber = vehicleNumber;
    }
}
