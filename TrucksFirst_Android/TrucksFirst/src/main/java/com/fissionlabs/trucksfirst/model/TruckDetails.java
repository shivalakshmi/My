package com.fissionlabs.trucksfirst.model;

/**
 * Created by Lakshmi on 21-07-2015.
 */
public class TruckDetails {

    private String vehicleTrackingId;
    private String vehicleNumber;
    private String vehicleRoute;
    private String eta;
    private String nextHubEta;
    private String assignedPilot;
    private String pilotInHub;
    private String vehicleInHub;
    private String client;
    private String sequence;
    private String nextHub;
    private String currentHub;
    private String vehiclePlanningId;
    private PilotAvailability pilotAvailability;

    public PilotAvailability getPilotAvailability() {
        return pilotAvailability;
    }

    public void setPilotAvailability(PilotAvailability pilotAvailability) {
        this.pilotAvailability = pilotAvailability;
    }


    public String getVehicleTrackingId() {
        return vehicleTrackingId;
    }

    public void setVehicleTrackingId(String vehicleTrackingId) {
        this.vehicleTrackingId = vehicleTrackingId;
    }

    public String getNextHubEta() {
        return nextHubEta;
    }

    public void setNextHubEta(String nextHubEta) {
        this.nextHubEta = nextHubEta;
    }

    public String getSequence() {
        return sequence;
    }

    public void setSequence(String sequence) {
        this.sequence = sequence;
    }

    public String getNextHub() {
        return nextHub;
    }

    public void setNextHub(String nextHub) {
        this.nextHub = nextHub;
    }

    public String getCurrentHub() {
        return currentHub;
    }

    public void setCurrentHub(String currentHub) {
        this.currentHub = currentHub;
    }

    public String getVehiclePlanningId() {
        return vehiclePlanningId;
    }

    public void setVehiclePlanningId(String vehiclePlanningId) {
        this.vehiclePlanningId = vehiclePlanningId;
    }

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