package com.marionageh.bakingapp.DataBase;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;

import com.marionageh.bakingapp.DataBase.Converters.ConverterListIngredients;
import com.marionageh.bakingapp.DataBase.Converters.ConverterListSteps;
import com.marionageh.bakingapp.Module.Foods;

@Database(entities = {Foods.class}, version = 1, exportSchema = false)
@TypeConverters({ConverterListIngredients.class, ConverterListSteps.class})

public abstract class AppDatabase extends RoomDatabase {


    private static final String DATABASE_NAME = "app-database.db";
    private static AppDatabase appDatabaseInstance;

    public static AppDatabase getInstance(Context context) {
        if (appDatabaseInstance == null) {
            appDatabaseInstance = Room.databaseBuilder(context,
                    AppDatabase.class, DATABASE_NAME).build();

        }

        return appDatabaseInstance;
    }


    public abstract FoodDao getFoodsDao();

}