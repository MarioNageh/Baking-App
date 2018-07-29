package com.marionageh.bakingapp.Widget;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

import com.marionageh.bakingapp.Adapters.FoodsWidgetAdapter;
import com.marionageh.bakingapp.Constants.Constant;
import com.marionageh.bakingapp.Module.Foods;
import com.marionageh.bakingapp.Network.Network;
import com.marionageh.bakingapp.R;
import com.marionageh.bakingapp.Retrofit.ApiClient;
import com.marionageh.bakingapp.Retrofit.RetrofitApiInterface;
import com.marionageh.bakingapp.customClasses.CustomAsyanTask;
import com.marionageh.bakingapp.customClasses.SavingAsyncTask;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

public class FoodWidgetService extends IntentService implements CustomAsyanTask.CustomAsyncListener {
    public static final String ACTION_GET_DATA_And_UPDATE =
            "UPDATE_ACTION_GET_DATA";

    public FoodWidgetService() {
        super("FoodWidgetService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_GET_DATA_And_UPDATE.equals(action)) {
                handleActionGetUpdate();
            }
        }

    }

    public static void startActionGetUpdate(Context context) {
        Intent intent = new Intent(context, FoodWidgetService.class);
        intent.setAction(ACTION_GET_DATA_And_UPDATE);
        context.startService(intent);
    }

    //TODO UPDATE ALL WIGETE WHEN DATABASE CHANGED
    // This Is Fixed Data On Server So That we need's to wpdate widget every calling

    private void handleActionGetUpdate() {
            new CustomAsyanTask(this,this).execute();
    }

    @Override
    public void onListReceived(List<Foods> foodsList) {
        List<Foods> retunnerofthiscsope = new ArrayList<>();
        if (foodsList.size() == 0){ //That's Mean That DataBase Empty
            //First Check That There Internet Conncetion
            if (!Network.isNetworkConnected(this)) {
                //No Internet With No Database
                //Inflate No Internet Connection
                AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
                int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, FoodWidgetProvider.class));
                appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.list_itemes_widget);
                FoodWidgetProvider.UpdateAllFoodsWidgetsNoInterNet(this, appWidgetManager, appWidgetIds);
                return;

            } else {
                Retrofit retrofitApiClient = ApiClient.getClient();
                RetrofitApiInterface retrofitApiInterface = retrofitApiClient
                        .create(RetrofitApiInterface.class);

                Call<List<Foods>> call = retrofitApiInterface.getAllRecipes();
                try {
                    Response<List<Foods>> response = call.execute();

                    if (response.isSuccessful()) {
                        retunnerofthiscsope = response.body();
                        Constant.CONSTAT_Foods = retunnerofthiscsope;
                        new SavingAsyncTask(this,retunnerofthiscsope).execute();

                    }
                } catch (IOException e) {
                    // Maybe network failure.
                    Log.v("aaaa", e.getMessage());
                }
            }
        }else {
            //DataBase have Data
            retunnerofthiscsope=foodsList;
            Constant.CONSTAT_Foods=retunnerofthiscsope;
        }
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, FoodWidgetProvider.class));
        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.list_itemes_widget);
        FoodWidgetProvider.UpdateAllFoodsWidgets(this, appWidgetManager, retunnerofthiscsope, appWidgetIds);
        Log.e("Mariooo","DataTrue");
    }
}
