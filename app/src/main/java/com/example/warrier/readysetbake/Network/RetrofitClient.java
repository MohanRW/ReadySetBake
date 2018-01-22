package com.example.warrier.readysetbake.Network;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;



public class RetrofitClient {
    private static Retrofit mretrofit = null;

    public static Retrofit getClient(String baseUrl) {
        if (mretrofit==null) {
            mretrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
        }
        return mretrofit;
    }
}
