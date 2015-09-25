package com.fissionlabs.trucksfirst.model;

/**
 * Created by Kanj on 24-09-2015.
 */
public class ClosedTrip {
    private String id;
    private String truckNumber;
    private String tripCode;
    private String route;
    private String arrivalTime;

    private boolean pod;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTruckNumber() {
        return truckNumber;
    }

    public void setTruckNumber(String truckNumber) {
        this.truckNumber = truckNumber;
    }

    public String getTripCode() {
        return tripCode;
    }

    public void setTripCode(String tripCode) {
        this.tripCode = tripCode;
    }

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }

    public String getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(String arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public boolean isPod() {
        return pod;
    }

    public void setPod(boolean pod) {
        this.pod = pod;
    }
}
