package com.example.warrier.readysetbake;

import android.content.Intent;
import android.support.v4.app.Fragment;

import com.example.warrier.readysetbake.fragments.RecipeItem;
import com.example.warrier.readysetbake.model.Bake;
import com.example.warrier.readysetbake.presenter.BakeItemPresenter;
import com.example.warrier.readysetbake.widget.WidgetService;



public class BaseActivity extends MainActivity implements BakeItemPresenter.Callbacks{
    @Override
    protected Fragment createFragment() {
        return new RecipeItem();
    }

    @Override
    public void recipeSelected(Bake bake) {
        Intent intent = RecipeActivity.newIntent(this, bake);
        WidgetService.startActionUpdateRecipeWidgets(this, bake);
        startActivity(intent);

    }
}
