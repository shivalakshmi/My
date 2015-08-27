package com.fissionlabs.trucksfirst.model;

/**
 * Created by Lakshmi on 21-07-2015.
 */
public class TruckDetails {

    private String vehicleNumber;
    private String vehicleRoute;
    private String eta;
    private String assignedPilot;
    private String pilotInHub;
    private String vehicleInHub;
    private String client;

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public String getAssignedPilot() {
        return assignedPilot;
    }

    public void setAssignedPilot(String assignedPilot) {
        this.assignedPilot = assignedPilot;
    }

    public String getEta() {
        return eta;
    }

    public void setEta(String eta) {
        this.eta = eta;
    }

    public String getPilotInHub() {
        return pilotInHub;
    }

    public void setPilotInHub(String pilotInHub) {
        this.pilotInHub = pilotInHub;
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

    public String getVehicleInHub() {
        return vehicleInHub;
    }

    public void setVehicleInHub(String vehicleInHub) {
        this.vehicleInHub = vehicleInHub;
    }
}