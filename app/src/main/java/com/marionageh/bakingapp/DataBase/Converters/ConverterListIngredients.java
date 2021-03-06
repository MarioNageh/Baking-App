package com.marionageh.bakingapp.DataBase.Converters;

import android.arch.persistence.room.TypeConverter;

import com.google.gson.Gson;
import com.marionageh.bakingapp.Module.Ingredients;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ConverterListIngredients {

    @TypeConverter
    public static List<Ingredients> toListIngredients(String jsonString){
        List<Ingredients> ingredientsList = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(jsonString);

            for (int i=0; i<jsonArray.length(); i++){
                JSONObject jsonObject = jsonArray.optJSONObject(i);

                String jsonStringItem = jsonObject.toString();
                Gson gson = new Gson();
                Ingredients ingredient = gson.fromJson(jsonStringItem, Ingredients.class);

                ingredientsList.add(ingredient);
            }
        } catch (JSONException e) {

        }

        return ingredientsList;
    }

    @TypeConverter
    public static String toString(List<Ingredients> ingredientsList){
        Gson gson = new Gson();
        return gson.toJson(ingredientsList);
    }

}