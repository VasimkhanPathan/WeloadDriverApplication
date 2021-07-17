package com.weload.driverapplication.login.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginResponseModel {
    @SerializedName("status")
    @Expose
    public int status;
    @SerializedName("message")
    @Expose
    public String message;
    @SerializedName("data")
    @Expose
    public Data data;
   public class Data {
        @SerializedName("id")
        @Expose
       public String id;
        @SerializedName("name")
        @Expose
        public String name;
        @SerializedName("mobile")
        @Expose
        public String mobile;
        @SerializedName("session_token")
        @Expose
        public String session_token;
    }
}
