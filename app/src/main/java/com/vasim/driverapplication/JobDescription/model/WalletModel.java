package com.vasim.driverapplication.JobDescription.model;

public class WalletModel {
    public String strDate;
    public String strJobID;
    public String strPickupLocation;
    public String strDropLocation;
    public String strPaymentStatus;
    public String strPyament;

    public WalletModel(String strDate, String strJobID, String strPickupLocation, String strDropLocation, String strPaymentStatus, String strPyament) {
        this.strDate = strDate;
        this.strJobID = strJobID;
        this.strPickupLocation = strPickupLocation;
        this.strDropLocation = strDropLocation;
        this.strPaymentStatus = strPaymentStatus;
        this.strPyament = strPyament;
    }
}
