package com.fissionlabs.trucksfirst.model;

/**
 * Created by Ashok on 7/25/2015.
 */
public class CheckList {

    public String vehicleNumber;

    public Documents documents;

    public Kits kits;

    public Cleanliness cleanliness;

    public Driver driver;

    public TyreOil tyreOil;

    public Electrical electrical;

    public Scratch scratch;

    public class Documents {
        public String fitnessCertificate;
        public String nationalPermit;
        public String pollutionCertificate;
        public String insurance;
        public String tollTaxReceiptAndCashBalance;
        public String GRNOrBilti;
        public String sealIntactness;
    }

    public class Kits {
        public String toolKit;
        public String others;
    }

    public class Cleanliness {
        public String cabinCleanliness;
    }

    public class Driver {
        public String engineStarting;
        public String engineSound;
        public String exhaustEmission;
        public String clutchWorking;
        public String gearMovement;
        public String brakeEffectiveness;
    }

    public class TyreOil {
        public String tyres;
        public String coolantLeakage;
        public String engineOilLeakage;
        public String gearOilLeakage;
        public String fuelDieselLeakage;
        public String differentialOilLeakage;
    }

    public class Electrical {
        public String headlight;
        public String indicatorAndHazard;
        public String horn;
        public String wiper;
        public String temperatureOnTemperatureGauge;
        public String alternatorChargerLight;
        public String oilPressureWarningLight;
    }

    public class Scratch {
        public String front;
        public String left;
        public String rear;
        public String right;
    }
}
