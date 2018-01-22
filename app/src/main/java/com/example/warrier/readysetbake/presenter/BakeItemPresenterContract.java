package com.example.warrier.readysetbake.presenter;

import android.support.annotation.StringRes;

import com.example.warrier.readysetbake.model.Bake;

import java.util.ArrayList;


public interface BakeItemPresenterContract {
    interface View {

        void updateAdapter(ArrayList<Bake> recipeList);

        void displaySnackbarMessage(@StringRes int stringResId);

        boolean isActive();

    }
    interface Presenter {

        void fetchRecipes();

        void recipeClicked(Bake recipe, android.view.View view);

        void viewDestroy();

    }


}
