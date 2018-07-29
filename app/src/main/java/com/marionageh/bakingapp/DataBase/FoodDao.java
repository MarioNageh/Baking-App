package com.marionageh.bakingapp.DataBase;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.marionageh.bakingapp.Module.Foods;

import java.util.List;
@Dao
public interface FoodDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(List<Foods> list);

    @Query("SELECT * FROM Foods")
    List<Foods> getAll();
}
