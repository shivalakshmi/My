package com.fissionlabs.trucksfirst.model.checklist;

/**
 * Created by Lakshmi on 26-09-2015.
 */
public class Screen2 {

    public boolean mechanicalIssue;
    public String mechanicalIssueList;
    public boolean tyreOilIssue;
    public String tyreOilIssueList;
    public boolean electricalIssue;
    public String electricalIssueList;
    public boolean engineIssue;
    public String engineIssueList;
    public int timeTaken;

    public Screen2() {
        mechanicalIssue = false;
        mechanicalIssueList = "";
        tyreOilIssue = false;
        tyreOilIssueList = "";
        electricalIssue = false;
        electricalIssueList = "";
        engineIssue = false;
        engineIssueList = "";
        timeTaken = 0;
    }
}
