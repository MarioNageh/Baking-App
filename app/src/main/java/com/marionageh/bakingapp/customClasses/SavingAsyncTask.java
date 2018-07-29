package com.marionageh.bakingapp.customClasses;

import android.content.Context;
import android.os.AsyncTask;

import com.marionageh.bakingapp.DataBase.AppDatabase;
import com.marionageh.bakingapp.DataBase.FoodDao;
import com.marionageh.bakingapp.Module.Foods;

import java.lang.ref.WeakReference;
import java.util.List;

public class SavingAsyncTask extends AsyncTask<Void,Void,Void> {

    private WeakReference<Context> contextWeakReference;
    private List<Foods> foodsList;

    public SavingAsyncTask(Context contextWeakReference, List<Foods> foodsList) {
        this.contextWeakReference = new WeakReference<>(contextWeakReference);
        this.foodsList = foodsList;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        if (contextWeakReference != null){
            Context context = contextWeakReference.get();

            AppDatabase appDatabase = AppDatabase.getInstance(contextWeakReference.get());
            FoodDao foodDao = appDatabase.getFoodsDao();
            foodDao.insert(foodsList);
        }

        return null;
    }
}
