package com.fissionlabs.trucksfirst.model.checklist;

/**
 * Created by Lakshmi on 26-09-2015.
 */
public class Screen3 {

    public boolean registrationCertificate;
    public boolean nationalPermit;
    public boolean insurance;
    public boolean pollutionCertificate;
    public boolean goodsTaxRecipiet;
    public boolean roadTaxBooklet;
    public boolean dharamKaantaParchi;
    public int timeTaken;

    public Screen3 (){
        registrationCertificate = true;
        nationalPermit = true;
        insurance = true;
        pollutionCertificate = true;
        goodsTaxRecipiet = true;
        roadTaxBooklet = true;
        dharamKaantaParchi = true;
        timeTaken = 0;
    }
}
