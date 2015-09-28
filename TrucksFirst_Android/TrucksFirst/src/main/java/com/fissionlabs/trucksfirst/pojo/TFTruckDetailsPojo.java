package com.fissionlabs.trucksfirst.pojo;

/**
 * Created by Lakshmi on 09-07-2015.
 */
class TFTruckDetailsPojo {

    private String vehicleNumber;
    private String vehicleRoute;
    private String eta;
    private String assignedPilot;
    private boolean pilotInHub;
    private boolean vehicleInHub;
    private String checklist;

    public TFTruckDetailsPojo(String vehicleNumber,
                              String vehicleRoute, String eta,
                              String assignedPilot, boolean pilotInHub,
                              boolean vehicleInHub, String checklist) {
        this.vehicleNumber = vehicleNumber;
        this.vehicleRoute = vehicleRoute;
        this.eta = eta;
        this.assignedPilot = assignedPilot;
        this.pilotInHub = pilotInHub;
        this.vehicleInHub = vehicleInHub;
        this.checklist = checklist;


    }

    public String getAssignedPilot() {
        return assignedPilot;
    }

    public void setAssignedPilot(String assignedPilot) {
        this.assignedPilot = assignedPilot;
    }

    public String getChecklist() {
        return checklist;
    }

    public void setChecklist(String checklist) {
        this.checklist = checklist;
    }

    public String getEta() {
        return eta;
    }

    public void setEta(String eta) {
        this.eta = eta;
    }

    public boolean isPilotInHub() {
        return pilotInHub;
    }

    public void setPilotInHub(boolean pilotInHub) {
        this.pilotInHub = pilotInHub;
    }

    public boolean isVehicleInHub() {
        return vehicleInHub;
    }

    public void setVehicleInHub(boolean vehicleInHub) {
        this.vehicleInHub = vehicleInHub;
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

