package com.marionageh.bakingapp.customClasses;


import android.content.Context;
import android.os.AsyncTask;

import com.marionageh.bakingapp.DataBase.AppDatabase;
import com.marionageh.bakingapp.DataBase.FoodDao;
import com.marionageh.bakingapp.Module.Foods;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class CustomAsyanTask extends AsyncTask<Void , Void , List<Foods>> {

    private CustomAsyncListener listener;
    private WeakReference<Context> contextWeakReference;

    public CustomAsyanTask(CustomAsyncListener listener, Context contextWeakReference) {
        this.listener = listener;
        this.contextWeakReference = new WeakReference<>(contextWeakReference);
    }

    @Override
    protected List<Foods> doInBackground(Void... voids) {
        if (contextWeakReference == null){
            return null;
        }

        AppDatabase appDatabase = AppDatabase.getInstance(contextWeakReference.get());
        FoodDao foodDao = appDatabase.getFoodsDao();
        List<Foods> foodsList = foodDao.getAll();

        if (foodsList == null || foodsList.size() == 0){
            // Get from Internet
            foodsList = new ArrayList<>();
        }

        return foodsList;
    }

    @Override
    protected void onPostExecute(List<Foods> foodsList) {
        listener.onListReceived(foodsList);
    }

    // Listener

    public interface CustomAsyncListener {
        void onListReceived(List<Foods> foodsList);
    }

}

