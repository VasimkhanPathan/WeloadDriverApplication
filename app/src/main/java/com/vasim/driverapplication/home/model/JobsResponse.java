package com.vasim.driverapplication.home.model;

public class JobsResponse {
    public String jobsId;
    public String pickUpDate;
    public String pickUpLocation;
    public String deliveryDate;
    public String dropLocation;

    public JobsResponse(String jobsId, String pickUpDate, String pickUpLocation, String deliveryDate, String dropLocation) {
        this.jobsId = jobsId;
        this.pickUpDate = pickUpDate;
        this.pickUpLocation = pickUpLocation;
        this.deliveryDate = deliveryDate;
        this.dropLocation = dropLocation;
    }
}
