package com.example.haneenalawneh.bakingapp;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.squareup.picasso.Picasso;


public class StepDetailsFragment extends Fragment implements ExoPlayer.EventListener {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String stepVideo;
    private String stepThumbnail;
    private String stepDescription;
    private SimpleExoPlayer mExoPlayer;
    private SimpleExoPlayerView mPlayerView;
    private static MediaSessionCompat mMediaSession;
    private PlaybackStateCompat.Builder mStateBuilder;
    private TextView descriptionTV;
    private Context context;
    Button next;
    Long position = 0L;
    Button previous;
    boolean isPrevVisible = true;
    boolean isNextVisible = true;
    ImageView thumbnailImage;

    public StepDetailsFragment() {
        // Required empty public constructor
    }

    public void setStepVideo(String stepVideo) {
        this.stepVideo = stepVideo;

    }

    public void setThumbnail(String thubmnai) {
        stepThumbnail = thubmnai;

    }

    public void setStepDescription(String stepDescription) {
        this.stepDescription = stepDescription;

    }

    public void setIsNextVisible(boolean isNextVisible) {
        this.isNextVisible = isNextVisible;

    }

    public void setIsPrevVisible(boolean isPrevVisible) {
        this.isPrevVisible = isPrevVisible;

    }

    public static StepDetailsFragment newInstance(String stepVideo, String stepDescription) {
        StepDetailsFragment fragment = new StepDetailsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, stepVideo);
        args.putString(ARG_PARAM2, stepDescription);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            stepVideo = getArguments().getString(ARG_PARAM1);
            stepDescription = getArguments().getString(ARG_PARAM2);
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_step_details, container, false);
        mPlayerView = (SimpleExoPlayerView) view.findViewById(R.id.playerView);
        next = (Button) view.findViewById(R.id.next_button);
        previous = (Button) view.findViewById(R.id.prev_button);
        thumbnailImage = (ImageView) view.findViewById(R.id.thumbnail);
        if (savedInstanceState != null) {
            if (savedInstanceState.getString("video") != null) {
                stepVideo = savedInstanceState.getString("video");
                position = savedInstanceState.getLong("position");

            }
            stepDescription = savedInstanceState.getString("description");
            isNextVisible = savedInstanceState.getBoolean("next");
            isPrevVisible = savedInstanceState.getBoolean("prev");
            stepThumbnail = savedInstanceState.getString("thumbnail");


        }
        if (!isNextVisible) {
            next.setVisibility(View.INVISIBLE);
        } else {
            next.setVisibility(View.VISIBLE);

        }
        if (!isPrevVisible) {
            previous.setVisibility(View.INVISIBLE);
        } else {
            previous.setVisibility(View.VISIBLE);

        }


        descriptionTV = (TextView) view.findViewById(R.id.step_description);
        // Inflate the layout for this fragment
        descriptionTV.setText(stepDescription);
        context = getActivity().getApplicationContext();

        if (context != null) {
            initializeMediaSession();

            if (!stepVideo.equals("")) {
                Uri u = Uri.parse(stepVideo);

                initializePlayer(u);
            } else {
                mPlayerView.setVisibility(View.INVISIBLE);
                LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.linear);

                linearLayout.removeViewAt(1);
                linearLayout.addView(descriptionTV, 0);

                descriptionTV.setPadding(20, 20, 20, 20);
            }

            if (!stepThumbnail.equals("")) {
                Uri u = Uri.parse(stepThumbnail);
                Picasso.with(context).load(stepThumbnail).into(thumbnailImage);
            }

        }

        return view;

    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mExoPlayer = null;


    }


    @Override
    public void onDetach() {
        super.onDetach();


    }

    @Override
    public void onTimelineChanged(Timeline timeline, Object manifest) {

    }

    @Override
    public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {

    }

    @Override
    public void onLoadingChanged(boolean isLoading) {

    }

    @Override
    public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
        if ((playbackState == ExoPlayer.STATE_READY) && playWhenReady) {
            mStateBuilder.setState(PlaybackStateCompat.STATE_PLAYING,
                    mExoPlayer.getCurrentPosition(), 1f);
        } else if ((playbackState == ExoPlayer.STATE_READY)) {
            mStateBuilder.setState(PlaybackStateCompat.STATE_PAUSED,
                    mExoPlayer.getCurrentPosition(), 1f);
        }
        mMediaSession.setPlaybackState(mStateBuilder.build());

    }

    @Override
    public void onPlayerError(ExoPlaybackException error) {

    }

    @Override
    public void onPositionDiscontinuity() {

    }

    private void initializeMediaSession() {

        // Create a MediaSessionCompat.
        mMediaSession = new MediaSessionCompat(context, "media session");

        mMediaSession.setFlags(
                MediaSessionCompat.FLAG_HANDLES_MEDIA_BUTTONS |
                        MediaSessionCompat.FLAG_HANDLES_TRANSPORT_CONTROLS);

        // Do not let MediaButtons restart the player when the app is not visible.
        mMediaSession.setMediaButtonReceiver(null);

        // Set an initial PlaybackState with ACTION_PLAY, so media buttons can start the player.
        mStateBuilder = new PlaybackStateCompat.Builder()
                .setActions(
                        PlaybackStateCompat.ACTION_PLAY |
                                PlaybackStateCompat.ACTION_PAUSE |
                                PlaybackStateCompat.ACTION_SKIP_TO_PREVIOUS |
                                PlaybackStateCompat.ACTION_PLAY_PAUSE
                );

        mMediaSession.setPlaybackState(mStateBuilder.build());


        // MySessionCallback has methods that handle callbacks from a media controller.
        mMediaSession.setCallback(new MySessionCallback());

        // Start the Media Session since the activity is active.
        mMediaSession.setActive(true);

    }

    private class MySessionCallback extends MediaSessionCompat.Callback {
        @Override
        public void onPlay() {
            mExoPlayer.setPlayWhenReady(true);
        }

        @Override
        public void onPause() {
            mExoPlayer.setPlayWhenReady(false);
        }

        @Override
        public void onSkipToPrevious() {
            mExoPlayer.seekTo(0);
        }


    }

    public void initializePlayer(Uri mediaUri) {
        if (mExoPlayer == null && mPlayerView != null) {


            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();
            mExoPlayer = ExoPlayerFactory.newSimpleInstance(context, trackSelector, loadControl);

            mExoPlayer.addListener(this);

            // Prepare the MediaSource.

            String userAgent = Util.getUserAgent(context, "BakingApp");
            MediaSource mediaSource = new ExtractorMediaSource(mediaUri, new DefaultDataSourceFactory(
                    context, userAgent), new DefaultExtractorsFactory(), null, null);
            mExoPlayer.prepare(mediaSource);
            mExoPlayer.seekTo(position);
            mExoPlayer.setPlayWhenReady(true);
            mPlayerView.setPlayer(mExoPlayer);


        }

    }

    private void releasePlayer() {
        mExoPlayer.stop();
        mExoPlayer.release();
        mExoPlayer = null;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("description", stepDescription);
        outState.putBoolean("next", isNextVisible);
        outState.putBoolean("prev", isPrevVisible);
        outState.putString("thumbnail", stepThumbnail);
        if (mExoPlayer!=null) {
            outState.putLong("position", mExoPlayer.getCurrentPosition());
            outState.putString("video", stepVideo);

        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mExoPlayer!=null) {
            releasePlayer();
        }
    }


}
