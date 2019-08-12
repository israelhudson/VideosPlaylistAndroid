package xyz.ihudapp.videosplaylist_android;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import com.google.android.exoplayer2.*;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.ConcatenatingMediaSource;
import com.google.android.exoplayer2.source.DynamicConcatenatingMediaSource;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;

import java.util.ArrayList;
import java.util.List;

public class VideoExampleActivity extends AppCompatActivity {

    SimpleExoPlayerView exoPlayerView;
    SimpleExoPlayer exoPlayer;
    //String videoURL = "http://blueappsoftware.in/layout_design_android_blog.mp4";
    String videoURL = "https://flutter.github.io/assets-for-api-docs/assets/videos/butterfly.mp4";
    DynamicConcatenatingMediaSource dynamicConcatenatingMediaSource;

    List<String> videoUrls;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_example);

        videoUrls = new ArrayList();
        videoUrls.add("https://flutter.github.io/assets-for-api-docs/assets/videos/butterfly.mp4");
        videoUrls.add("https://www.w3schools.com/html/mov_bbb.mp4");

        exoPlayerView = findViewById(R.id.exo_player_view);

        try {
            dynamicConcatenatingMediaSource = new DynamicConcatenatingMediaSource();
            BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
            TrackSelector trackSelector = new DefaultTrackSelector(new AdaptiveTrackSelection.Factory(bandwidthMeter));
            exoPlayer = ExoPlayerFactory.newSimpleInstance(this, trackSelector);
            DefaultHttpDataSourceFactory dataSourceFactory = new DefaultHttpDataSourceFactory("exoplayer_video");
            ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();
            exoPlayerView.setPlayer(exoPlayer);

            MediaSource[] mediaSources = new MediaSource[videoUrls.size()];

            for (int i = 0; i < videoUrls.size(); i++) {
                mediaSources[i] = new ExtractorMediaSource(Uri.parse(videoUrls.get(i)), dataSourceFactory, extractorsFactory, null, null);
            }

            MediaSource mediaSource = mediaSources.length == 1 ? mediaSources[0]
                    : new ConcatenatingMediaSource(mediaSources);

            exoPlayer.prepare(mediaSource);
            exoPlayer.setPlayWhenReady(true);
            exoPlayer.setRepeatMode(exoPlayer.REPEAT_MODE_ALL);
        }catch (Exception e){
            Log.e("MainAcvtivity"," exoplayer error "+ e.toString());
        }
    }



}
