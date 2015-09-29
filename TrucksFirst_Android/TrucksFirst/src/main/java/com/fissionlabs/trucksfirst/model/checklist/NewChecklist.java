package com.fissionlabs.trucksfirst.model.checklist;

import java.io.Serializable;
import java.math.BigInteger;

/**
 * Created by Lakshmi on 26-09-2015.
 */
public class NewChecklist implements Serializable{

    public String vehicleNumber;
    public long trackingId;
    public Data data = new Data();
}


