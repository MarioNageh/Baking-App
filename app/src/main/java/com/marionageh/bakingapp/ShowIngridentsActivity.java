package com.marionageh.bakingapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.marionageh.bakingapp.Constants.Constant;
import com.marionageh.bakingapp.Module.Foods;

import java.util.List;

import static com.marionageh.bakingapp.Constants.Constant.CONSTAT_Food;

public class ShowIngridentsActivity extends AppCompatActivity {
 public static final String FOODS_LIST="foodsLsit";
 public static final String FOODS="foods";
 public static final String INDEX="INdex";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent=getIntent();
        if(intent!=null&&intent.getExtras()!=null){
            PrePareDataForWidget(intent);
        }
        setContentView(R.layout.activity_show_ingridents);

        // Will Make Arrow On ToolBar to back
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //Change Activity Label
        this.setTitle(Constant.CONSTAT_Food.getName());
    }

    private void PrePareDataForWidget(Intent intent) {
        CONSTAT_Food = intent.getExtras().getParcelable(FOODS);
        Constant.CONSTAT_STEPS =CONSTAT_Food.getSteps();
        Constant.Prapare_Ingrideinets(CONSTAT_Food.getIngredients());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Will Make Arrow On ToolBar to back
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return true;
    }


    @Override
    public void onBackPressed() {
        // Will Make Arrow On ToolBar to back
        finish();

    }
}
