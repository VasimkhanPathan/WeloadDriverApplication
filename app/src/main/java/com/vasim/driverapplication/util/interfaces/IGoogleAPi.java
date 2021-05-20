package com.vasim.driverapplication.util.interfaces;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface IGoogleAPi {
    @GET
    Call<String> getDataFromGoogleApi(@Url String url);
}
