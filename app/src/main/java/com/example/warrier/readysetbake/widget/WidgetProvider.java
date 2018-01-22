package com.example.warrier.readysetbake.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.example.warrier.readysetbake.R;
import com.example.warrier.readysetbake.RecipeActivity;
import com.example.warrier.readysetbake.model.Bake;
import com.example.warrier.readysetbake.model.Ingredient;


public class WidgetProvider extends AppWidgetProvider {
    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                Bake bake, int appWidgetId) {

        Intent intent = RecipeActivity.newIntent(context, bake);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widgets);
        views.removeAllViews(R.id.widget_ingredient_list);
        views.setTextViewText(R.id.widget_title, bake.getName());
        views.setOnClickPendingIntent(R.id.widget_holder, pendingIntent);

        for(Ingredient ingredient : bake.getIngredients()) {
            RemoteViews rvIngredient = new RemoteViews(context.getPackageName(),
                    R.layout.widget_list);
            rvIngredient.setTextViewText(R.id.recipe_widget_ingredient_item,
                    String.valueOf(ingredient.getQuantity()) +
                    String.valueOf(ingredient.getMeasure()) + " " + ingredient.getIngredient());
            views.addView(R.id.widget_ingredient_list, rvIngredient);
        }

        appWidgetManager.updateAppWidget(appWidgetId, views);
    }
    public static void updateRecipeWidgets(Context context, AppWidgetManager appWidgetManager,
                                           Bake bake, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, bake, appWidgetId);
        }
    }
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {}

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {}

    @Override
    public void onEnabled(Context context) {}

    @Override
    public void onDisabled(Context context) {}



}
