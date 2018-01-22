package com.example.warrier.readysetbake.presenter;

import com.example.warrier.readysetbake.model.Step;

import java.util.ArrayList;



public interface RecipeDetailPresenterContract {
    interface View {}

    interface Presenter {

        void stepClicked(ArrayList<Step> stepList, int currentStep,
                         String recipeName, android.view.View view);

    }
}
