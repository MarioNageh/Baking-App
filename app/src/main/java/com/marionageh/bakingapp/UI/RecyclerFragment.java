package com.marionageh.bakingapp.UI;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.appwidget.AppWidgetManager;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.marionageh.bakingapp.Adapters.FoodsAdapter;
import com.marionageh.bakingapp.Constants.Constant;
import com.marionageh.bakingapp.DataBase.AppDatabase;
import com.marionageh.bakingapp.DataBase.FoodDao;
import com.marionageh.bakingapp.Module.Foods;
import com.marionageh.bakingapp.Module.Ingredients;
import com.marionageh.bakingapp.Module.Steps;
import com.marionageh.bakingapp.Network.Network;
import com.marionageh.bakingapp.R;
import com.marionageh.bakingapp.Retrofit.ApiClient;
import com.marionageh.bakingapp.Retrofit.RetrofitApiInterface;
import com.marionageh.bakingapp.ShowIngridentsActivity;
import com.marionageh.bakingapp.Widget.FoodWidgetProvider;
import com.marionageh.bakingapp.customClasses.CustomAsyanTask;
import com.marionageh.bakingapp.customClasses.SavingAsyncTask;
import com.marionageh.bakingapp.customClasses.SimpleIdlingResources;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.marionageh.bakingapp.Constants.Constant.CONSTAT_Food;
import static com.marionageh.bakingapp.Data.JsonPharsing.GetFoodsFromJson;


public class RecyclerFragment extends Fragment implements FoodsAdapter.ListItemClick,CustomAsyanTask.CustomAsyncListener {
    //Adapter
    FoodsAdapter foodsAdapter = new FoodsAdapter(null, this);
    // Ui Loading Message
    @BindView(R.id.pro_bar)
    ProgressBar progressBar;
    //The List Comming From Retrofit
    List<Foods> foodsList = new ArrayList<>();
    //Values For SaveState
    final String ON_SAVE_INSTANT_STATE_FOOD_LIST = "ON_SAVE_LIST";
    //RecyclerView
    @BindView(R.id.recylerview_mainfragment)
    RecyclerView recyclerView;
    //
    Call<List<Foods>> call;
    //For NointernetConncetion
    @BindView(R.id.no_internt_connection)
    ImageView NoInternet_Image;

    public RecyclerFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        //ini views
        View view = inflater.inflate(R.layout.fragment_recycler, container, false);
        //Bind ButterKnife
        ButterKnife.bind(this, view);

        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(foodsAdapter);


        if (savedInstanceState == null) {
            //Check Data From DatabBase
            progressBar.setVisibility(View.VISIBLE);
            new CustomAsyanTask(this,getContext()).execute();
        } else {
            foodsList = savedInstanceState.<Foods>getParcelableArrayList(ON_SAVE_INSTANT_STATE_FOOD_LIST);
            //If Rootate while getting data
            if (foodsList.size() == 0) {
                CallingTheServer();
            }
            foodsAdapter.Swapadapter(foodsList);
        }

        //This Part For Responsive Design
        //Is Phone
        SizeScreens();

        return view;
    }

    void CallingTheServer() {
        //Loading Display
        progressBar.setVisibility(View.VISIBLE);
        // Retrofit Calling
        CallRetrofit();
    }

    private void SizeScreens() {
        if (getContext().getResources().getBoolean(R.bool.IsTowPane)) {
            //Check Is LandScape or Not
            if (getContext().getResources().getBoolean(R.bool.ISLandScape)) {
                GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),
                        2, LinearLayoutManager.VERTICAL, false);
                recyclerView.setLayoutManager(gridLayoutManager);
            } else {
                LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
                recyclerView.setLayoutManager(layoutManager);
            }

        } else {
            //Is Tablet
            GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),
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
                    //For Test
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if(idlingResources!=null)
                            idlingResources.setIdleState(true);
                        }
                    }, 1000);

                    // Insert List to Db
                    new SavingAsyncTask(getContext(),foodsList).execute();
                }
            }

            @Override
            public void onFailure(Call<List<Foods>> call, Throwable t) {
                Log.e("Mario", t.getMessage());

            }
        });

    }

    @Override
    public void OnItemClick(int postion) {
        CONSTAT_Food = foodsList.get(postion);
        Constant.CONSTAT_STEPS = foodsList.get(postion).getSteps();
        Constant.Prapare_Ingrideinets(foodsList.get(postion).getIngredients());
        startActivity(new Intent(getContext(), ShowIngridentsActivity.class));

    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putParcelableArrayList(ON_SAVE_INSTANT_STATE_FOOD_LIST, (ArrayList<? extends Parcelable>) foodsList);
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();

        if (idlingResources != null){
            idlingResources.setIdleState(false);
        }

        if (foodsList == null)
            CallingTheServer();
    }


    @VisibleForTesting
    public SimpleIdlingResources getIdlingResources() {
        if (idlingResources == null){
            idlingResources = new SimpleIdlingResources();
        }

        return idlingResources;
    }
    private SimpleIdlingResources idlingResources;

    @Override
    public void onListReceived(List<Foods> foodsList) {
        if (foodsList.size() == 0){ //That's Mean That DataBase Empty
            //First Check That There Internet Conncetion
            if (!Network.isNetworkConnected(getContext())) {
                NoInternet_Image.setVisibility(View.VISIBLE);
                NoInternet_Image.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getActivity(). finish();
                    }
                });
            } else {
                CallingTheServer();
            }
        }else {
            progressBar.setVisibility(View.GONE);
            this.foodsList = foodsList;
            foodsAdapter.Swapadapter(this.foodsList);
            //For Test
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if(idlingResources!=null)
                        idlingResources.setIdleState(true);
                }
            }, 1000);
            Log.e("Mariooo","DataTrue");
        }
    }
}
