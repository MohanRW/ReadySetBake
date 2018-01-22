package com.example.warrier.readysetbake.presenter;

import android.view.View;

import com.example.warrier.readysetbake.model.Step;

import java.util.ArrayList;


public class RecipeDetailPresenter implements RecipeDetailPresenterContract.Presenter{
    private final RecipeDetailPresenterContract.View mView;

    public interface Callbacks {
        void stepSelected(ArrayList<Step> stepList, int currentStep, String recipeName);
    }

    public RecipeDetailPresenter(RecipeDetailPresenterContract.View view) {
        this.mView = view;
    }


    @Override
    public void stepClicked(ArrayList<Step> stepList, int currentStep, String recipeName, View view) {
        ((Callbacks) view.getContext()).stepSelected(stepList, currentStep, recipeName);

    }
}
