package com.marionageh.bakingapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.marionageh.bakingapp.Data.JsonPharsing;
import com.marionageh.bakingapp.Module.Foods;
import com.marionageh.bakingapp.UI.RecyclerFragment;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public RecyclerFragment getFragment() {
        return (RecyclerFragment) getSupportFragmentManager().findFragmentById(R.id.recyler_fragment);
    }
}
