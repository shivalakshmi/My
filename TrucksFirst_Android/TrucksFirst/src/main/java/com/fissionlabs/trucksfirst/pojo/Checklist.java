package com.fissionlabs.trucksfirst.pojo;

/**
 * Created by Lakshmi on 24-07-2015.
 */
public class Checklist {

    String operational;
    String checklistItem;
    String status;
    String print;
    String email;

   public Checklist(String operational,String checklistItem,String status,String print, String email)
    {
        this.operational = operational;
        this.checklistItem = checklistItem;
        this.status = status;
        this.print = print;
        this.email = email;

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPrint() {
        return print;
    }

    public void setPrint(String print) {
        this.print = print;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
