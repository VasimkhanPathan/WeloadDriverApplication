package com.weload.driverapplication.api_instance;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.weload.driverapplication.util.AppConstants;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DriverApiInstance {
    private static Retrofit retrofit;

    public static Retrofit getRetrofitInstance() {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

       /* HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();*/
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .client(new OkHttpClient.Builder().connectTimeout(60 * 5, TimeUnit.SECONDS).
                            readTimeout(60 * 5, TimeUnit.SECONDS)
                            .writeTimeout(60 * 5, TimeUnit.SECONDS)
                            .addNetworkInterceptor(new Interceptor() {
                                @Override
                                public Response intercept(Chain chain) throws IOException {
                                    Request.Builder builder = chain.request().newBuilder();
                                    builder.addHeader("Accept-Language", "en");
                                    Request request = builder.build();
                                    Response response = chain.proceed(request);
                                    return response;
                                }
                            }).build())
                    .baseUrl(AppConstants.ApiNames.API_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            /*retrofit = new Retrofit.Builder()
                    .baseUrl(AppConstants.ApiNames.API_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();*/
        }
        return retrofit;
    }
}
