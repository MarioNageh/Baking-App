package com.marionageh.bakingapp;

import android.content.Intent;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.marionageh.bakingapp.Constants.Constant;
import com.marionageh.bakingapp.Module.Ingredients;
import com.marionageh.bakingapp.Module.Steps;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;

@RunWith(AndroidJUnit4.class)
public class VideoActivityTest {
    @Rule
    public ActivityTestRule<VideoActivity> ruleMainActivity
            = new ActivityTestRule<>(VideoActivity.class, false, false);
    @Before
    public void initializeFakeVideoUrl(){
        Constant.CONSTAT_Video_URl
                = "https://d17h27t6h515a5.cloudfront.net/topher/2017/April/58ffd974_-intro-creampie/-intro-creampie.mp4";

        Constant.CONSTAT_STEPS=new ArrayList<>();
        for (int i=0; i<3; i++){
            Steps steps = new Steps(
                    "shortDesc", "desc", "", "");
            Constant.CONSTAT_STEPS.add(steps);
        }
        Constant.Last_Step=1;


        Intent intent = new Intent();


        ruleMainActivity.launchActivity(intent);
        ruleMainActivity.getActivity()
                .getSupportFragmentManager().beginTransaction();
    }
@Test
    public void checkVideoExistence(){
        Espresso.onView(ViewMatchers.withId(R.id.video_view))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }

}
