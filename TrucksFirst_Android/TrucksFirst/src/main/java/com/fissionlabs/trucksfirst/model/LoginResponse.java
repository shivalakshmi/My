package com.fissionlabs.trucksfirst.model;

/**
 * @author ashok on 8/8/15.
 */
public class LoginResponse {

    public String message;
    public String success;
    public HubDetails result;

    public class HubDetails {

        public String roleName;
        public String empId;
        public String hubName;
        public String userName;

        public String getRoleName() {
            return roleName;
        }

        public void setRoleName(String roleName) {
            this.roleName = roleName;
        }

        public String getEmpId() {
            return empId;
        }

        public void setEmpId(String empId) {
            this.empId = empId;
        }

        public String getHubName() {
            return hubName;
        }

        public void setHubName(String hubName) {
            this.hubName = hubName;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

    }
}
