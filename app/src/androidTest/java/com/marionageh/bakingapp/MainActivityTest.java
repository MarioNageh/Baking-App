package com.marionageh.bakingapp;

import android.content.Intent;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.IdlingRegistry;
import android.support.test.espresso.IdlingResource;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    private IdlingResource idlingResource;

    @Rule
  public   ActivityTestRule<MainActivity> ruleMainActivity
            = new ActivityTestRule<>(MainActivity.class);

    @Before
    public void registerIdlingResource() {
        idlingResource = ruleMainActivity.getActivity()
                .getFragment().getIdlingResources();

        IdlingRegistry.getInstance().register(idlingResource);
    }

    @Before
    public void launchActivityWithFakeIntentExtras() {
        ruleMainActivity.getActivity()
                .getSupportFragmentManager().beginTransaction();
    }


    @Test
    public void checkExistenceOfRecyclerView(){
        Espresso.onView(ViewMatchers.withId(R.id.recylerview_mainfragment))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }
    @Test public void getFirstitemonadapter(){
        Espresso.onView(ViewMatchers.withId(R.id.recylerview_mainfragment))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0,
                        ViewActions.click()));
    }

    @After
    public void unregisterIdlingResource(){
        if (idlingResource != null){
            IdlingRegistry.getInstance().unregister(idlingResource);
        }
    }



}
