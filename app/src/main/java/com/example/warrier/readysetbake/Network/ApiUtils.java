package com.example.warrier.readysetbake.Network;



public class ApiUtils {
    private ApiUtils(){}


    public static final String PROJECT_URL = "http://go.udacity.com/android-baking-app-json/";

    public static ApiObservable getApiObservable() {
        return RetrofitClient.getClient(PROJECT_URL).create(ApiObservable.class);
    }
}
