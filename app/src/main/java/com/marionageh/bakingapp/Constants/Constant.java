package com.marionageh.bakingapp.Constants;

import com.marionageh.bakingapp.Module.Foods;
import com.marionageh.bakingapp.Module.Ingredients;
import com.marionageh.bakingapp.Module.Steps;

import java.util.ArrayList;
import java.util.List;

public class Constant {
    public static Foods CONSTAT_Food;
    public static List<Steps> CONSTAT_STEPS;

    public static List<Ingredients> CONSTAT_Ingredients;
    public static String CONSTAT_Video_URl;
    public static int Last_Step;
    public static int Max_Steps;
    public final static String PREFERNCES_NAMES="FOODS_PRE";
    public final static String PREFERNCES_INDEX_POSTION="FOODS_PRE_POSTIOn";
    public static Foods food;
    public static List<Foods> CONSTAT_Foods;
    public static final String Get_Index_From_Action="GET_INDEX";

    public static void  Prapare_Ingrideinets(List<Ingredients> ingredients){
        List<Ingredients> ingredients1=new ArrayList<>();
        ingredients1.add(new Ingredients(0,"","Ingredients"));
        ingredients1.addAll(ingredients);
        CONSTAT_Ingredients=ingredients1;
    }
}
