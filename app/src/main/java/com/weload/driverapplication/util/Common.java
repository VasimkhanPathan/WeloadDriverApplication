package com.weload.driverapplication.util;

import com.weload.driverapplication.util.interfaces.IGoogleAPi;

public class Common {
    public static final String baseUrl="https://googleapis.com";
    public static IGoogleAPi getGoogleApi(){
        return RetrofitClient.getClient(baseUrl).create(IGoogleAPi.class);
    }
}
