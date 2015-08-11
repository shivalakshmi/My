package com.fissionlabs.trucksfirst.pojo;

/**
 * Created by Lakshmi on 24-07-2015.
 */
public class Checklist {

    private String operational;
    private String checklistItem;
    private boolean status;

   public Checklist(String operational,String checklistItem,boolean status)
    {
        this.operational = operational;
        this.checklistItem = checklistItem;
        this.status = status;
    }

    public String getOperational() {
        return operational;
    }

    public void setOperational(String operational) {
        this.operational = operational;
    }

    public String getChecklistItem() {
        return checklistItem;
    }

    public void setChecklistItem(String checklistItem) {
        this.checklistItem = checklistItem;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
