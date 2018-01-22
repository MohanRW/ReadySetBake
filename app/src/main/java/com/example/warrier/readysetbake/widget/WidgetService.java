package com.example.warrier.readysetbake.widget;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;

import com.example.warrier.readysetbake.model.Bake;



public class WidgetService extends IntentService {

    public static final String WIDGET_ACTION_UPDATE =
            "com.example.warrier.readysetbake.action.update";
    private static final String BUNDLE_WIDGET_DATA =
            "com.example.warrier.readysetbake.recipe_widget_data";

    public WidgetService() {
        super("WidgetService");
    }

    public static void startActionUpdateRecipeWidgets(Context context, Bake bake) {
        Intent intent = new Intent(context, WidgetService.class);
        intent.setAction(WIDGET_ACTION_UPDATE);
        intent.putExtra(BUNDLE_WIDGET_DATA, bake);
        context.startService(intent);
    }
    @Override
    protected void onHandleIntent(Intent intent) {

        if (intent != null) {
            final String action = intent.getAction();
            if (WIDGET_ACTION_UPDATE.equals(action) &&
                    intent.getParcelableExtra(BUNDLE_WIDGET_DATA) != null) {
                handleActionUpdateWidgets((Bake)intent.getParcelableExtra(BUNDLE_WIDGET_DATA));
            }
        }

    }
    private void handleActionUpdateWidgets(Bake bake) {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, WidgetProvider.class));
        WidgetProvider.updateRecipeWidgets(this, appWidgetManager, bake, appWidgetIds);
    }


}
