package com.example.warrier.readysetbake.Network;

import com.example.warrier.readysetbake.model.Bake;

import java.util.ArrayList;

import io.reactivex.Observable;

public class NetworkService {
    private ApiObservable mApiObservable;

    public NetworkService(){
        mApiObservable = ApiUtils.getApiObservable();
    }

    public Observable<ArrayList<Bake>> networkApiRequestRecipes() {
        Observable observer = mApiObservable.getRecipeResult();
        return observer;
    }
}
