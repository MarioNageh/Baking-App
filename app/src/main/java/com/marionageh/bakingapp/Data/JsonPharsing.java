package com.marionageh.bakingapp.Data;

import android.content.Context;
import android.util.Log;

import com.marionageh.bakingapp.Module.Foods;
import com.marionageh.bakingapp.Module.Ingredients;
import com.marionageh.bakingapp.Module.Steps;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import static java.lang.System.in;

public class JsonPharsing {
    private static final String DATA_FILE_NAME = "data.txt";
    //Json Part
    private final static String name_JSON = "name";
    private final static String ingredients_JSON = "ingredients";
    private final static String steps_JSON = "steps";
    private final static String servings_JSON = "servings";
    //In The ingredients
    private final static String quantity_JSON = "quantity";
    private final static String measure_JSON = "measure";
    private final static String ingredient_JSON = "ingredient";
    //In The steps
    private final static String shortDescription_JSON = "shortDescription";
    private final static String description_JSON = "description";
    private final static String videoURL_JSON = "videoURL";
    private final static String thumbnailURL_JSON = "thumbnailURL";


    public static String GetJsonFromAssetts(Context context) {

        BufferedReader reader = null;
        try {
            reader = new BufferedReader(
                    new InputStreamReader(context.getAssets().open(DATA_FILE_NAME),"UTF-8"));

            // do reading, usually loop until end of file reading
            String mLine;
            StringBuilder builder = new StringBuilder();
            while ((mLine = reader.readLine()) != null) {
                builder.append(mLine);
            }
            return builder.toString();
        } catch (IOException e) {
            return "";
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                }
            }
        }
    }

    public static ArrayList<Foods> GetFoodsFromJson(Context context) {
        ArrayList<Foods> retunner = new ArrayList<>();

        try {
            JSONArray mainJson = new JSONArray(GetJsonFromAssetts(context));
            for (int i = 0; i < mainJson.length(); i++) {
                JSONObject oneFood = mainJson.getJSONObject(i);
                String Name = oneFood.optString(name_JSON);
                ArrayList<Ingredients> ingredients = GetIngridentsFromJson(oneFood);
                ArrayList<Steps> steps = GetStepsFromJson(oneFood);
                int Serving = oneFood.optInt(servings_JSON);
                retunner.add(new Foods(i,Name, ingredients, steps,Serving));

            }

        } catch (JSONException e) {
            Log.e("Marioo",e.getMessage());
            e.printStackTrace();
        }
        return retunner;
    }

    public static ArrayList<Ingredients> GetIngridentsFromJson(JSONObject OneFood) {
        ArrayList<Ingredients> retunner = new ArrayList<>();
        try {
            JSONArray mainJsonforIngridientxs = OneFood.getJSONArray(ingredients_JSON);

            for (int i = 0; i < mainJsonforIngridientxs.length(); i++) {
                retunner.add(new Ingredients(
                        mainJsonforIngridientxs.getJSONObject(i).optInt(quantity_JSON)
                        , mainJsonforIngridientxs.getJSONObject(i).optString(measure_JSON)
                        , mainJsonforIngridientxs.getJSONObject(i).optString(ingredient_JSON)));

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return retunner;
    }

    public static ArrayList<Steps> GetStepsFromJson(JSONObject OneFood) {
        ArrayList<Steps> retunner = new ArrayList<>();
        try {
            JSONArray mainJsonforIngridientxs = OneFood.getJSONArray(steps_JSON);

            for (int i = 0; i < mainJsonforIngridientxs.length(); i++) {
                retunner.add(new Steps(
                        mainJsonforIngridientxs.getJSONObject(i).optString(shortDescription_JSON)
                        , mainJsonforIngridientxs.getJSONObject(i).optString(description_JSON),
                        mainJsonforIngridientxs.getJSONObject(i).optString(videoURL_JSON)
                        , mainJsonforIngridientxs.getJSONObject(i).optString(thumbnailURL_JSON)));


            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return retunner;
    }


}
