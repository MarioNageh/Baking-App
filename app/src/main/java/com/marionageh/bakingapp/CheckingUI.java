package com.marionageh.bakingapp;

import android.content.Context;


public class CheckingUI {
    public static final String Phone_P = "Phone_poratial";
    public static final String Phone_Land = "Phone_Land";
    public static final String Phone_Tablet = "Phone_tablet";

    public static String GetWhichScreen(Context context) {
        if (context.getResources().getBoolean(R.bool.IsTowPane)) {
            //Check Is LandScape or Not
            if (context.getResources().getBoolean(R.bool.ISLandScape)) {
                // This Is Phone WithLandSacpe
                return Phone_Land;
            } else {
                // This Is Phone Portial
                return Phone_P;

            }

        } else {
            //Is Tablet
            return Phone_Tablet;

        }
    }
}
