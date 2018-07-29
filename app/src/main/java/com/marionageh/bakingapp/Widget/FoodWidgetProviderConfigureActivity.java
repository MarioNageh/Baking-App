package com.marionageh.bakingapp.Widget;

import android.app.Activity;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.marionageh.bakingapp.Adapters.FoodsAdapter;
import com.marionageh.bakingapp.Adapters.FoodsWidgetAdapter;
import com.marionageh.bakingapp.Constants.Constant;
import com.marionageh.bakingapp.Module.Foods;
import com.marionageh.bakingapp.Network.Network;
import com.marionageh.bakingapp.R;
import com.marionageh.bakingapp.Retrofit.ApiClient;
import com.marionageh.bakingapp.Retrofit.RetrofitApiInterface;
import com.marionageh.bakingapp.customClasses.CustomAsyanTask;
import com.marionageh.bakingapp.customClasses.SavingAsyncTask;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * The configuration screen for the {@link FoodWidgetProvider FoodWidgetProvider} AppWidget.
 */
public class FoodWidgetProviderConfigureActivity extends Activity implements FoodsWidgetAdapter.ListItemClick,CustomAsyanTask.CustomAsyncListener {
    //Adapter
    FoodsWidgetAdapter foodsAdapter = new FoodsWidgetAdapter(null, this);
    // Ui Loading Message
    @BindView(R.id.pro_bar_config)
    ProgressBar progressBar;
    //The List Comming From Retrofit
    List<Foods> foodsList = new ArrayList<>();
    //Values For SaveState
    final String ON_SAVE_INSTANT_STATE_FOOD_LIST = "ON_SAVE_LIST";
    //RecyclerView
    @BindView(R.id.recylerview_mainfragment_config)
    RecyclerView recyclerView;
    //ImageView For InterNet Connection
    @BindView(R.id.no_internt_connection)
    ImageView NoInternet_imgea;
    //
    Call<List<Foods>> call;


    private static final String PREFS_NAME = "com.marionageh.bakingapp.Widget.FoodWidgetProvider";
    private static final String PREF_PREFIX_KEY = "appwidget_";
    int mAppWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID;
    EditText mAppWidgetText;

    public FoodWidgetProviderConfigureActivity() {
        super();
    }

    // Write the prefix to the SharedPreferences object for this widget
    static void saveTitlePref(Context context, int appWidgetId, int position) {
        SharedPreferences.Editor prefs = context.getSharedPreferences(PREFS_NAME, 0).edit();
        prefs.putInt(PREF_PREFIX_KEY + appWidgetId, position);
        prefs.apply();
    }

    // Read the prefix from the SharedPreferences object for this widget.
    // If there is no preference saved, get the default from a resource
    static int loadTitlePref(Context context, int appWidgetId) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, 0);
        int titleValue = prefs.getInt(PREF_PREFIX_KEY + appWidgetId, 0);
        if (titleValue != 0) {
            return titleValue;
        } else {
            return 0;
        }
    }

    static void deleteTitlePref(Context context, int appWidgetId) {
        SharedPreferences.Editor prefs = context.getSharedPreferences(PREFS_NAME, 0).edit();
        prefs.remove(PREF_PREFIX_KEY + appWidgetId);
        prefs.apply();
    }

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);

        // Set the result to CANCELED.  This will cause the widget host to cancel
        // out of the widget placement if the user presses the back button.
        setResult(RESULT_CANCELED);
        setContentView(R.layout.food_widget_provider_configure);
        ButterKnife.bind(this);


        // Find the widget id from the intent.
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            mAppWidgetId = extras.getInt(
                    AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
        }

        // If this activity was started with an intent without an app widget ID, finish with an error.
        if (mAppWidgetId == AppWidgetManager.INVALID_APPWIDGET_ID) {
            finish();
            return;
        }

        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(foodsAdapter);

        ///For Saving InstantState
        if (icicle == null) {

            //Check Data From DatabBase
            progressBar.setVisibility(View.VISIBLE);
            new CustomAsyanTask(this,this).execute();
        } else {
            foodsList = icicle.<Foods>getParcelableArrayList(ON_SAVE_INSTANT_STATE_FOOD_LIST);
            //If Rootate while getting data
            if (foodsList.size() == 0) {
                CallingTheServer();
            }
            foodsAdapter.Swapadapter(foodsList);
        }

        //This Part For Responsive Design
        //Is Phone
        SizeScreens();


//        mAppWidgetText.setText(loadTitlePref(FoodWidgetProviderConfigureActivity.this, mAppWidgetId));
    }

    @Override
    public void OnItemClick(int Postion) {
        Toast.makeText(FoodWidgetProviderConfigureActivity.this, "You Must Clcik Long on it", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void OnlongClick(int Postion) {

        final Context context = FoodWidgetProviderConfigureActivity.this;


        saveTitlePref(context, mAppWidgetId, Postion);

        // It is the responsibility of the configuration activity to update the app widget
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        FoodWidgetProvider.UpdateOneWidget(context, appWidgetManager, foodsList.get(Postion), mAppWidgetId);

        // Make sure we pass back the original appWidgetId
        Intent resultValue = new Intent();
        resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, mAppWidgetId);
        setResult(RESULT_OK, resultValue);
        finish();
    }


    void CallingTheServer() {
        //Loading Display
        progressBar.setVisibility(View.VISIBLE);
        // Retrofit Calling
        CallRetrofit();
    }

    private void SizeScreens() {
        if (this.getResources().getBoolean(R.bool.IsTowPane)) {
            //Check Is LandScape or Not
            if (this.getResources().getBoolean(R.bool.ISLandScape)) {
                GridLayoutManager gridLayoutManager = new GridLayoutManager(this,
                        2, LinearLayoutManager.VERTICAL, false);
                recyclerView.setLayoutManager(gridLayoutManager);
            } else {
                LinearLayoutManager layoutManager = new LinearLayoutManager(this);
                recyclerView.setLayoutManager(layoutManager);
            }

        } else {
            //Is Tablet
            GridLayoutManager gridLayoutManager = new GridLayoutManager(this,
                    3, LinearLayoutManager.VERTICAL, false);
            recyclerView.setLayoutManager(gridLayoutManager);

        }
    }

    private void CallRetrofit() {

        call = ApiClient.getClient().create(RetrofitApiInterface.class).getAllRecipes();
        call.enqueue(new Callback<List<Foods>>() {
            @Override
            public void onResponse(Call<List<Foods>> call, Response<List<Foods>> response) {
                if (response.isSuccessful()) {
                    progressBar.setVisibility(View.GONE);
                    foodsList = response.body();
                    foodsAdapter.Swapadapter(response.body());
                    Constant.CONSTAT_Foods = foodsList;
                    // Insert List to Db
                    new SavingAsyncTask(FoodWidgetProviderConfigureActivity.this,foodsList).execute();


                }
            }

            @Override
            public void onFailure(Call<List<Foods>> call, Throwable t) {
                Log.e("Mario", t.getMessage());

            }
        });

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(ON_SAVE_INSTANT_STATE_FOOD_LIST, (ArrayList<? extends Parcelable>) foodsList);

    }

    @Override
    public void onListReceived(List<Foods> foodsList) {

        if (foodsList.size() == 0){
            //First Check That There Internet Conncetion
            if (!Network.isNetworkConnected(this)) {
                NoInternet_imgea.setVisibility(View.VISIBLE);
                NoInternet_imgea.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                         finish();
                    }
                });
            } else {
                CallingTheServer();
            }
        }else {
            progressBar.setVisibility(View.GONE);
            Log.e("Mariooo","DataTrue");
            this.foodsList = foodsList;
            foodsAdapter.Swapadapter(this.foodsList);
        }

    }
}

