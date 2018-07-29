package com.marionageh.bakingapp.Widget;

import android.annotation.TargetApi;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;

import com.marionageh.bakingapp.Constants.Constant;
import com.marionageh.bakingapp.Module.Foods;
import com.marionageh.bakingapp.R;
import com.marionageh.bakingapp.ShowIngridentsActivity;

import java.util.List;

/**
 * Implementation of App Widget functionality.
 * App Widget Configuration implemented in {@link FoodWidgetProviderConfigureActivity FoodWidgetProviderConfigureActivity}
 */
public class FoodWidgetProvider extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        int widgetText = FoodWidgetProviderConfigureActivity.loadTitlePref(context, appWidgetId);
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.food_widget_provider);
        // Set the GridWidgetService intent to act as the adapter for the GridView
        Intent intent = new Intent(context, ListViewService.class);
        views.setRemoteAdapter(R.id.list_itemes_widget, intent);
        // Set the PlantDetailActivity intent to launch when clicked
        Intent appIntent = new Intent(context, ShowIngridentsActivity.class);
        PendingIntent appPendingIntent = PendingIntent.getActivity(context, 0, appIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        views.setPendingIntentTemplate(R.id.list_itemes_widget, appPendingIntent);
        // Handle empty gardens

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
      /*  for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }*/
        FoodWidgetService.startActionGetUpdate(context);
    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        // When the user deletes the widget, delete the preference associated with it.
        for (int appWidgetId : appWidgetIds) {
            FoodWidgetProviderConfigureActivity.deleteTitlePref(context, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    public static void UpdateAllFoodsWidgets(Context context, AppWidgetManager appWidgetManager, List<Foods> foods, int[] appWidgetIds) {

        for (int appWidgetId : appWidgetIds) {
            int index = FoodWidgetProviderConfigureActivity.loadTitlePref(context, appWidgetId);
            if (foods.size() != 0) {
                Log.e("Sizee",foods.get(index).getName());
                updateAppWidget(context, appWidgetManager, appWidgetId, foods.get(index));
            }
        }
    }

    public static void UpdateOneWidget(Context context, AppWidgetManager appWidgetManager, Foods foods, int appWidgetId) {
        updateAppWidget(context, appWidgetManager, appWidgetId, foods);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId, Foods foods) {

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.food_widget_provider);
        if (foods != null) {
            views.setTextViewText(R.id.text_name, foods.getName());

            // Set the GridWidgetService intent to act as the adapter for the GridView
            Intent intent = new Intent(context, ListViewService.class);
            intent.setAction(String.valueOf(appWidgetId));
            views.setRemoteAdapter(R.id.list_itemes_widget, intent);
            // Set the PlantDetailActivity intent to launch when clicked
            Intent appIntent = new Intent(context, ShowIngridentsActivity.class);
            PendingIntent appPendingIntent = PendingIntent.getActivity(context, 0, appIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            views.setPendingIntentTemplate(R.id.list_itemes_widget, appPendingIntent);
        }
        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);

    }


    public static void UpdateAllFoodsWidgetsNoInterNet(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {

        for (int appWidgetId : appWidgetIds) {
            updateAppWidgetNoNet(context, appWidgetManager, appWidgetId);
        }
    }

    private static void updateAppWidgetNoNet(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.food_widget_provider_no_inter);
        Intent appIntent = new Intent(context, FoodWidgetService.class);
        appIntent.setAction(FoodWidgetService.ACTION_GET_DATA_And_UPDATE);
        PendingIntent appPendingIntent = PendingIntent.getService(context, 0, appIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        views.setOnClickPendingIntent(R.id.no_internt_connection, appPendingIntent);
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }
}

