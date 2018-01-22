package com.example.warrier.readysetbake.Network;

import com.example.warrier.readysetbake.model.Bake;

import java.util.ArrayList;

import io.reactivex.Observable;
import retrofit2.http.GET;



public interface ApiObservable {
    @GET(" ")
    Observable<ArrayList<Bake>> getRecipeResult();

}
