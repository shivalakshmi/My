package com.fissionlabs.trucksfirst.model.checklist;

/**
 * Created by Lakshmi on 26-09-2015.
 */
public class Screen6 {

    public String tollCashWithDriver;
    public boolean tollReceipts;
    public String cashShortageReason;
    public boolean isTopUpDone;
    public String inputAmount;
    public int timeTaken;

    public Screen6() {
        tollCashWithDriver = "";
        tollReceipts = true;
        cashShortageReason = "";
        isTopUpDone = false;
        inputAmount = "";
        timeTaken = 0;
    }
}
