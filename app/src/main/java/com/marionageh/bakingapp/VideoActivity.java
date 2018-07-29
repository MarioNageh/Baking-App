package com.marionageh.bakingapp;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;


import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.marionageh.bakingapp.Constants.Constant;
import com.marionageh.bakingapp.Network.Network;
import com.marionageh.bakingapp.UI.VideoFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

import static java.security.AccessController.getContext;

public class VideoActivity extends AppCompatActivity {
    @BindView(R.id.txt_video)
    TextView txt_displaydescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        ButterKnife.bind(this);

        //For Check The Instantce State
        if (savedInstanceState == null) {
            // For Inflating the dynamic fragment && Checke If The Video Null The Fragment Will Gone
            if (!Constant.CONSTAT_Video_URl.equals("")) {
                VideoFragment fragment = new VideoFragment();
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction().add(R.id.video_frame, fragment).commit();
            } else {
                if (!Network.isNetworkConnected(this)) {
                    findViewById(R.id.video_frame).setBackground(ContextCompat.getDrawable(this, R.drawable.nointer));

                } else {
                    findViewById(R.id.video_frame).setBackground(ContextCompat.getDrawable(this, R.drawable.soory));

                }
            }
        } else {
            if (!Network.isNetworkConnected(this)) {
                findViewById(R.id.video_frame).setBackground(ContextCompat.getDrawable(this, R.drawable.nointer));

            } else {
                if (Constant.CONSTAT_Video_URl.equals(""))
                    findViewById(R.id.video_frame).setBackground(ContextCompat.getDrawable(this, R.drawable.soory));

            }

        }

        // Will Make Arrow On ToolBar to back
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //For Text Discription
        switch (CheckingUI.GetWhichScreen(this)) {
            case CheckingUI.Phone_Land:
                LandScreen();
                break;
            case CheckingUI.Phone_P:
                PhoneScreen();
                break;
            case CheckingUI.Phone_Tablet:
                break;

        }
    }

    private void LandScreen() {


    }

    private void PhoneScreen() {
        txt_displaydescription.setText(Constant.CONSTAT_STEPS.get(Constant.Last_Step).getDescription());

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

    public void BackFun(View view) {
        int nextstep = Constant.Last_Step - 1;
        if (nextstep < 0) {
            Constant.Last_Step = Constant.Max_Steps - 1;
        } else {
            Constant.Last_Step = nextstep;

        }
        Constant.CONSTAT_Video_URl = Constant.CONSTAT_STEPS.get(Constant.Last_Step).getVideoURL();
        startActivity(new Intent(this, VideoActivity.class));
        finish();
    }

    public void NextFun(View view) {
        int nextstep = Constant.Last_Step + 1;
        if (nextstep >= Constant.Max_Steps) {
            Constant.Last_Step = 0;
        } else {
            Constant.Last_Step = nextstep;

        }
        Constant.CONSTAT_Video_URl = Constant.CONSTAT_STEPS.get(Constant.Last_Step).getVideoURL();
        startActivity(new Intent(this, VideoActivity.class));
        finish();
    }
}
