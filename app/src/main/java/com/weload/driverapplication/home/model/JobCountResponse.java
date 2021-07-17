package com.weload.driverapplication.home.model;

public class JobCountResponse {
    public int status;
    public String message;
    public Data data;

    public class Data{
        public String pending;
        public String ongoing;
        public String completed;
    }
}
