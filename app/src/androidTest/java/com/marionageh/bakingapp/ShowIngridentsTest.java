package com.marionageh.bakingapp;

import android.content.Intent;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.intent.Intents;
import android.support.test.espresso.intent.matcher.IntentMatchers;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.marionageh.bakingapp.Module.Foods;
import com.marionageh.bakingapp.Module.Ingredients;
import com.marionageh.bakingapp.Module.Steps;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@RunWith(AndroidJUnit4.class)
public class ShowIngridentsTest {

    @Rule
    public ActivityTestRule<ShowIngridentsActivity> ruleMainActivity
            = new ActivityTestRule<>(ShowIngridentsActivity.class, false, false);

    @Before
    public void intentsSetup(){
        Intents.init();
    }

    @Before
    public void launchActivityTheRightWay(){
        List<Ingredients> fakeIngredientList = new ArrayList<>();
        List<Steps> fakeStepList = new ArrayList<>();
        for (int i=0; i<3; i++){
            Ingredients ingredients = new Ingredients(
                    1.4f, "measure", "ingredient");
            fakeIngredientList.add(ingredients);

            Steps steps = new Steps(
                    "shortDesc", "desc", "", "");
            fakeStepList.add(steps);
        }

        Foods fakeFood = new Foods(8, "fakeName", fakeIngredientList, fakeStepList, 5);

        Intent intent = new Intent();
        intent.putExtra(ShowIngridentsActivity.FOODS,
                fakeFood);
        ruleMainActivity.launchActivity(intent);

        ruleMainActivity.getActivity()
                .getSupportFragmentManager().beginTransaction();
    }

    @Test
    public void recyclerViewTest(){
        Espresso.onView(ViewMatchers.withId(R.id.recyler_steps))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0,
                        ViewActions.click()));

        Intents.intended(
                IntentMatchers.isInternal());
    }

    @After
    public void intentsRelease(){
        Intents.release();
    }

}
