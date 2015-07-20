package com.fissionlabs.trucksfirst.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ashok on 7/14/2015.
 */
public class Truck {

    private ArrayList<TruckDetails> mTruckDetailsList = new ArrayList<>();

    class TruckDetails{
        String vehicleNumber;
        String vehicleRoute;
        String eta;
        String assignedPilot;
        String pilotInHub;
        String vehileInHub;

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

        public String getVehileInHub() {
            return vehileInHub;
        }

        public void setVehileInHub(String vehileInHub) {
            this.vehileInHub = vehileInHub;
        }
    }
}
