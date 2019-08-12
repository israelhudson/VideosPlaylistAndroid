package xyz.ihudapp.videosplaylist_android.video_player;

import android.media.session.PlaybackState;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import com.google.android.exoplayer2.*;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.*;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import xyz.ihudapp.videosplaylist_android.R;

import java.util.ArrayList;
import java.util.EventListener;
import java.util.List;

public class VideoExampleActivity extends AppCompatActivity implements Player.EventListener {

    SimpleExoPlayerView exoPlayerView;
    SimpleExoPlayer exoPlayer;
    DynamicConcatenatingMediaSource dynamicConcatenatingMediaSource;

    private String status;

    List<String> videoUrls;

    int currentVideo = 9;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_example);
        setTitle(String.valueOf(currentVideo));

        videoUrls = new ArrayList();
        videoUrls.add("https://onedrive.live.com/download?cid=A82DFC6ED9776AF4&resid=A82DFC6ED9776AF4%212082&authkey=AMCIqSB8zNNJvxM");
        videoUrls.add("https://flutter.github.io/assets-for-api-docs/assets/videos/butterfly.mp4");
        videoUrls.add("https://www.w3schools.com/html/mov_bbb.mp4");
        videoUrls.add("https://www.demonuts.com/Demonuts/smallvideo.mp4");

        exoPlayerView = findViewById(R.id.exo_player_view);

        try {
            dynamicConcatenatingMediaSource = new DynamicConcatenatingMediaSource();
            BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
            TrackSelector trackSelector = new DefaultTrackSelector(new AdaptiveTrackSelection.Factory(bandwidthMeter));
            exoPlayer = ExoPlayerFactory.newSimpleInstance(this, trackSelector);
            ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();
            exoPlayerView.setPlayer(exoPlayer);

            MediaSource[] mediaSources = new MediaSource[videoUrls.size()];

            for (int i = 0; i < videoUrls.size(); i++) {
                mediaSources[i] = new ExtractorMediaSource(Uri.parse(videoUrls.get(i)), new CacheDataSourceFactory(this, 100 * 1024 * 1024, 5 * 1024 * 1024), extractorsFactory, null, null);
            }

            MediaSource mediaSource = mediaSources.length == 1 ? mediaSources[0]
                    : new ConcatenatingMediaSource(mediaSources);

            exoPlayer.prepare(mediaSource);
            exoPlayer.setPlayWhenReady(true);
            //exoPlayer.setRepeatMode(exoPlayer.REPEAT_MODE_ALL);
            exoPlayer.addListener(this);
        }catch (Exception e){
            Log.e("MainAcvtivity"," exoplayer error "+ e.toString());
        }
    }


    @Override
    public void onTimelineChanged(Timeline timeline, Object manifest, int reason) {

    }

    @Override
    public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {

    }

    @Override
    public void onLoadingChanged(boolean isLoading) {
        if(isLoading)
            Log.d("NS", "CARREGANDO ");
        else
            Log.d("NS", "PRONTO! ");

    }

    @Override
    public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
        switch(playbackState) {
            case Player.STATE_BUFFERING:
                Log.d("NS", "STATE_BUFFERING");
                break;
            case Player.STATE_ENDED:
                Log.d("NS", "STATE_ENDED");

                //Here you do what you want
                break;
            case Player.STATE_IDLE:
                Log.d("NS", "STATE_IDLE");

                break;
            case Player.STATE_READY:
                Log.d("NS", "STATE_READY");

                break;
            default:
                break;
        }

    }


    @Override
    public void onRepeatModeChanged(int repeatMode) {

    }

    @Override
    public void onShuffleModeEnabledChanged(boolean shuffleModeEnabled) {

    }

    @Override
    public void onPlayerError(ExoPlaybackException error) {

    }

    @Override
    public void onPositionDiscontinuity(int reason) {
        Log.d("NS", "pos "+exoPlayer.getCurrentWindowIndex());
        this.currentVideo = exoPlayer.getCurrentWindowIndex();
        onResume();

    }

    @Override
    public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {

    }

    @Override
    public void onSeekProcessed() {

    }

    void alert(String text){
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }
}
