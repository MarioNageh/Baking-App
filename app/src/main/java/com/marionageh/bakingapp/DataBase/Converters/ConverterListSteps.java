package com.marionageh.bakingapp.DataBase.Converters;

import android.arch.persistence.room.TypeConverter;

import com.google.gson.Gson;
import com.marionageh.bakingapp.Module.Steps;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import timber.log.Timber;

public class ConverterListSteps {

    @TypeConverter
    public static List<Steps> toListSteps(String jsonString){
        List<Steps> stepsList = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(jsonString);

            for (int i=0; i<jsonArray.length(); i++){
                JSONObject jsonObject = jsonArray.optJSONObject(i);

                String jsonStringItem = jsonObject.toString();
                Gson gson = new Gson();
                Steps steps = gson.fromJson(jsonStringItem, Steps.class);

                stepsList.add(steps);
            }
        } catch (JSONException e) {
            Timber.v(e.getMessage());
        }

        return stepsList;
    }

    @TypeConverter
    public static String toString(List<Steps> stepsList){
        Gson gson = new Gson();
        return gson.toJson(stepsList);
    }

}