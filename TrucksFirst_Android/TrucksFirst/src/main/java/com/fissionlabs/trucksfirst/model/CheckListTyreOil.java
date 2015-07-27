package com.fissionlabs.trucksfirst.model;

/**
 * Created by Lakshmi on 27-07-2015.
 */
public class CheckListTyreOil {
    private String tyres;
    private String coolantLeakage;
    private String engineOilLeakage;
    private String gearOilLeakage;
    private String fuelDieselLeakage;
    private String differentialOilLeakage;

    public String getDifferentialOilLeakage() {
        return differentialOilLeakage;
    }

    public void setDifferentialOilLeakage(String differentialOilLeakage) {
        this.differentialOilLeakage = differentialOilLeakage;
    }

    public String getEngineOilLeakage() {
        return engineOilLeakage;
    }

    public void setEngineOilLeakage(String engineOilLeakage) {
        this.engineOilLeakage = engineOilLeakage;
    }

    public String getGearOilLeakage() {
        return gearOilLeakage;
    }

    public void setGearOilLeakage(String gearOilLeakage) {
        this.gearOilLeakage = gearOilLeakage;
    }

    public String getFuelDieselLeakage() {
        return fuelDieselLeakage;
    }

    public void setFuelDieselLeakage(String fuelDieselLeakage) {
        this.fuelDieselLeakage = fuelDieselLeakage;
    }

    public String getCoolantLeakage() {
        return coolantLeakage;
    }

    public void setCoolantLeakage(String coolantLeakage) {
        this.coolantLeakage = coolantLeakage;
    }

    public String getTyres() {
        return tyres;
    }

    public void setTyres(String tyres) {
        this.tyres = tyres;
    }
}
