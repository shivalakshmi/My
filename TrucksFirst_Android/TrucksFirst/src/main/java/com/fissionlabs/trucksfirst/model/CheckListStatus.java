package com.fissionlabs.trucksfirst.model;

/**
 * Created by Lakshmi on 27-07-2015.
 */
public class CheckListStatus {

    private String vehicleNumber;
    private CheckListDocuments documents;
    private CheckListKits kits;
    private CheckListCleanliness cleanliness;
    private CheckListDriver driver;
    private CheckListTyreOil tyreOil;
    private CheckListElectrical electrical;
    private CheckListScratch scratch;

    public String getVehicleNumber() {
        return vehicleNumber;
    }

    public void setVehicleNumber(String vehicleNumber) {
        this.vehicleNumber = vehicleNumber;
    }

    public CheckListKits getKits() {
        return kits;
    }

    public void setKits(CheckListKits kits) {
        this.kits = kits;
    }

    public CheckListCleanliness getCleanliness() {
        return cleanliness;
    }

    public void setCleanliness(CheckListCleanliness cleanliness) {
        this.cleanliness = cleanliness;
    }

    public CheckListDriver getDriver() {
        return driver;
    }

    public void setDriver(CheckListDriver driver) {
        this.driver = driver;
    }

    public CheckListTyreOil getTyreOil() {
        return tyreOil;
    }

    public void setTyreOil(CheckListTyreOil tyreOil) {
        this.tyreOil = tyreOil;
    }

    public CheckListElectrical getElectrical() {
        return electrical;
    }

    public void setElectrical(CheckListElectrical electrical) {
        this.electrical = electrical;
    }

    public CheckListScratch getScratch() {
        return scratch;
    }

    public void setScratch(CheckListScratch scratch) {
        this.scratch = scratch;
    }

    public CheckListDocuments getDocuments() {
        return documents;
    }

    public void setDocuments(CheckListDocuments documents) {
        this.documents = documents;
    }
}
