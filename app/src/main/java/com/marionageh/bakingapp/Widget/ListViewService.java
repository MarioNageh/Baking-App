package com.marionageh.bakingapp.Widget;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.marionageh.bakingapp.Constants.Constant;
import com.marionageh.bakingapp.Module.Foods;
import com.marionageh.bakingapp.Module.Ingredients;
import com.marionageh.bakingapp.R;
import com.marionageh.bakingapp.Retrofit.ApiClient;
import com.marionageh.bakingapp.Retrofit.RetrofitApiInterface;
import com.marionageh.bakingapp.ShowIngridentsActivity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import timber.log.Timber;

import static com.marionageh.bakingapp.Constants.Constant.Get_Index_From_Action;

public class ListViewService extends RemoteViewsService {

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        int index = -1;
        try {
            index = FoodWidgetProviderConfigureActivity.loadTitlePref(getApplicationContext(), Integer.parseInt(intent.getAction()));
          Log.e("MArioooo",index+"");
        } catch (Exception e) {

        }

        return new ListRemoteViewsFactory(getApplicationContext(), index);
    }
}

class ListRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    Context mContext;
    List<Foods> foodsList;
    Foods foods;


    public ListRemoteViewsFactory(Context mContext, int index) {
        this.mContext = mContext;
        foodsList = Constant.CONSTAT_Foods;
        if (index == -1) {
          return;
        }
        if(foodsList==null){
            return;
        }
        foods = foodsList.get(index);
        Log.e("MMMMMario",foods.getName());
    }

    @Override
    public void onCreate() {

    }

    //called on start and when notifyAppWidgetViewDataChanged is called
    @Override
    public void onDataSetChanged() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        if (foods == null) return 0;
        Log.v("VVV", foods.getIngredients().size() + "");
        return foods.getIngredients().size();
    }

    /**
     * This method acts like the onBindViewHolder method in an Adapter
     *
     * @param position The current position of the item in the GridView to be displayed
     * @return The RemoteViews object to display for the provided postion
     */
    @Override
    public RemoteViews getViewAt(int position) {
        if (foods == null || foods.getIngredients().size() == 0) return null;


        RemoteViews views = new RemoteViews(mContext.getPackageName(), R.layout.text_widget);

        views.setTextViewText(R.id.text_widget_line, PutIngrients(foods.getIngredients().get(position)));

        Log.v("VVV", PutIngrients(foods.getIngredients().get(position)));

        // Fill in the onClick PendingIntent Template using the specific plant Id for each item individually
       Bundle extras = new Bundle();
        extras.putParcelableArrayList(ShowIngridentsActivity.FOODS_LIST, (ArrayList<? extends Parcelable>) foodsList);
        extras.putParcelable(ShowIngridentsActivity.FOODS, foods);
        Intent fillInIntent = new Intent();
        fillInIntent.putExtras(extras);
        views.setOnClickFillInIntent(R.id.text_widget_line, fillInIntent);

        return views;

    }

    public String PutIngrients(Ingredients ingredient) {
        // For Text
        StringBuilder builder = new StringBuilder();
        builder.append("-");
        builder.append(" ");
        builder.append(ingredient.getIngredient());
        builder.append(" ");
        builder.append("(");
        //   builder.append(" ");
        builder.append(ingredient.getQuantity());
        builder.append(" ");
        builder.append(ingredient.getMeasure());
        builder.append(")");

        return builder.toString();
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1; // Treat all items in the GridView the same
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}
