package com.marionageh.bakingapp.UI;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

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
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.marionageh.bakingapp.Adapters.IngridentsAdapter;
import com.marionageh.bakingapp.Adapters.StepsAdapter;
import com.marionageh.bakingapp.Constants.Constant;
import com.marionageh.bakingapp.Network.Network;
import com.marionageh.bakingapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class VideoFragment extends Fragment {
    @BindView(R.id.video_view)
    PlayerView mPlayerView;
    private SimpleExoPlayer mSimpleExoPlayer;
    TrackSelection.Factory videoTrackSelectionFactory;
    TrackSelector trackSelector;
    DataSource.Factory dataSourceFactory;
    MediaSource videoSource;
    DefaultBandwidthMeter bandwidthMeter;
    final String VIDEO_POSITION = "VIDEO_POSTION";
    final String VIDEO_STATUE = "VIDEO_STATUE";
    boolean State=false;
    long video_Postion = 0;

    public VideoFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //First Check The Internet Connection and inflate other layout with image
        if (!Network.isNetworkConnected(getContext())) {
            View view = inflater.inflate(R.layout.fragment_no_internet, container, false);
            //Bind ButterKnife
            return view;
        } else {
            //ini views
            View view = inflater.inflate(R.layout.fragment_video, container, false);
            FrameLayout.LayoutParams contentViewLayout = new FrameLayout.LayoutParams( FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT );
            view.setLayoutParams( contentViewLayout );
            //Bind ButterKnife
            ButterKnife.bind(this, view);
            if (savedInstanceState != null) {
                video_Postion = savedInstanceState.getLong(VIDEO_POSITION);
                State = savedInstanceState.getBoolean(VIDEO_STATUE);
            } else {
            }
            initializeVideoPlayer(Uri.parse(Constant.CONSTAT_Video_URl));
            return view;
        }
    }

    public void initializeVideoPlayer(Uri videoUri) {
        if (mSimpleExoPlayer == null) {
            // 1. Create a default TrackSelector

            // 1. Create a default TrackSelector
            DefaultBandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
            TrackSelection.Factory videoTrackSelectionFactory = new AdaptiveTrackSelection.Factory(
                    bandwidthMeter);
            trackSelector = new DefaultTrackSelector(
                    videoTrackSelectionFactory);

            // 2. Create the player
            mSimpleExoPlayer = ExoPlayerFactory.newSimpleInstance(
                    getContext(), trackSelector);

            // Bind the player to the view.
            mPlayerView.setPlayer(mSimpleExoPlayer);

            // Produces DataSource instances through which media data is loaded.
            dataSourceFactory = new DefaultDataSourceFactory(getContext(),
                    Util.getUserAgent(getContext(), getString(R.string.app_name)), bandwidthMeter);

            // This is the MediaSource representing the media to be played.
            videoSource = new ExtractorMediaSource.Factory(dataSourceFactory)
                    .createMediaSource(videoUri);
            // Prepare the player with the source.
            mSimpleExoPlayer.prepare(videoSource);
            mSimpleExoPlayer.setPlayWhenReady(true);
        }
    }

    private void releasePlayer() {
        if (mSimpleExoPlayer != null) {
            mSimpleExoPlayer.stop();
            mSimpleExoPlayer.release();
            mSimpleExoPlayer = null;
            dataSourceFactory = null;
            videoSource = null;
            trackSelector = null;
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mSimpleExoPlayer != null) {
            State=mSimpleExoPlayer.getPlayWhenReady();
           mSimpleExoPlayer.setPlayWhenReady(false);
            video_Postion = mSimpleExoPlayer.getCurrentPosition();
            Log.e("Mario", video_Postion + "");
        }
        releasePlayer();
    }

    @Override
    public void onStop() {
        super.onStop();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();

    }




    @Override
    public void onResume() {
        super.onResume();
        if (mSimpleExoPlayer != null) {
            mSimpleExoPlayer.seekTo(video_Postion);
           mSimpleExoPlayer.setPlayWhenReady(State);
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putLong(VIDEO_POSITION, video_Postion);
      outState.putBoolean(VIDEO_STATUE, State);
        Log.e("Mario", mSimpleExoPlayer.isPlayingAd() + "");

    }
}
