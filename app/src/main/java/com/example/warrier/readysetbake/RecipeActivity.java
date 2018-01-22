package com.example.warrier.readysetbake;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.warrier.readysetbake.fragments.Recipe;
import com.example.warrier.readysetbake.fragments.StepFrag;
import com.example.warrier.readysetbake.model.Bake;
import com.example.warrier.readysetbake.model.Step;
import com.example.warrier.readysetbake.presenter.RecipeDetailPresenter;

import java.util.ArrayList;


public class RecipeActivity extends AppCompatActivity implements RecipeDetailPresenter.Callbacks {
    private static final String BUNDLE_RECIPE_DATA =
            "com.example.warrier.readysetbake.recipe_data";

    public static Intent newIntent(Context packageContext, Bake recipe) {
        Intent intent = new Intent(packageContext, RecipeActivity.class);
        intent.putExtra(BUNDLE_RECIPE_DATA, recipe);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_masterdetail);

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragment_container);
        Bake recipe = getIntent().getExtras().getParcelable(BUNDLE_RECIPE_DATA);

        if (fragment == null) {
            fragment = Recipe.newInstance(recipe);
            fm.beginTransaction()
                    .add(R.id.fragment_container, fragment)
                    .commit();

            if (getResources().getBoolean(R.bool.isTablet)) {
                Fragment newDetail = StepFrag.newInstance(recipe.getSteps().get(0));
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.detail_fragment_container, newDetail)
                        .commit();
            }
        }


    }




    @Override
    public void stepSelected(ArrayList<Step> stepList, int currentStep, String recipeName) {
        if (!getResources().getBoolean(R.bool.isTablet)) {
            Intent intent = StepsActivity.newIntent(this, stepList, currentStep, recipeName);
            startActivity(intent);
        } else {
            Fragment newDetail = StepFrag.newInstance(stepList.get(currentStep));
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.detail_fragment_container, newDetail)
                    .commit();
        }
    }


}
