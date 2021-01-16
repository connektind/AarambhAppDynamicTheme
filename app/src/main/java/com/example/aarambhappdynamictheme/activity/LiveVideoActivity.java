package com.example.aarambhappdynamictheme.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.example.aarambhappdynamictheme.R;
import com.example.aarambhappdynamictheme.sheardPrefrence.AarambhSharedPreference;
import com.example.aarambhappdynamictheme.textGradient.VideoPlayerConfig;
import com.example.aarambhappdynamictheme.util.CommonUtilities;
import com.example.aarambhappdynamictheme.util.Global;
import com.example.aarambhappdynamictheme.util.VolleySingleton;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultAllocator;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class LiveVideoActivity extends AppCompatActivity {

    PlayerView playerView;
    SimpleExoPlayer player;

    Handler mHandler;
    Runnable mRunnable;
    private static final String TAG = "ExoPlayerActivity";
    private static final String KEY_VIDEO_URI = "video_uri";
    private final int REQ_CODE = 100;
    ImageView back_btn, back_btn_youtube, full_screen_img;
    String API_KEY = "AIzaSyAja-68gRGjnbSCsR5U1IuMKpUUO8rABmo";
    String videoUri;
    PlayerView videoFullScreenPlayer;
    String pref_class;
    Button btn_refresh;
    ImageView img_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_video);
        try {
            String url = getIntent().getStringExtra("live_url");
        } catch (Exception e) {
            e.printStackTrace();
        }
//        init();
//        listener();
        btn_refresh = findViewById(R.id.btn_Refresh);
        img_back = findViewById(R.id.img_back);
        checkOrientation();
        BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
        TrackSelection.Factory videoTrackSelectionFactory = new AdaptiveTrackSelection.Factory(bandwidthMeter);
        TrackSelector trackSelector = new DefaultTrackSelector(videoTrackSelectionFactory);
        player = ExoPlayerFactory.newSimpleInstance(this, trackSelector);
        pref_class = AarambhSharedPreference.loadClassIdFromPreference(this);
        playerView = findViewById(R.id.simple_player);
        //videoUri="rtmp://139.59.77.127/LiveApp/698392100281705556338849";
        //videoUri = "rtmp://139.59.77.127/LiveApp/931503717969522175393685";
        //callgetLiveId();


//        player = ExoPlayerFactory.newSimpleInstance(this, trackSelector);
//
//        playerView = findViewById(R.id.simple_player);
//        videoUri = "rtmp://139.59.77.127/LiveApp/698392100281705556338849";
//        setUp();
        callgetLiveId();

        playerView.setPlayer(player);


        final Handler handler = new Handler();
        final Runnable runnable = new Runnable(){
            @Override
            public void run() {
                Log.e("Get Duration", player.getDuration()+"");
                if(player.getDuration()>30000){
                    callgetLiveId();
                    Log.e("Timer","Test");
                    handler.postDelayed(this, 30000);
                }
            }
        };
        handler.postDelayed(runnable, 300);
        listener();
    }

    public void listener(){

        btn_refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callgetLiveId();
                btn_refresh.setClickable(false);

                if (btn_refresh.isClickable() == false){
                    Toast.makeText(LiveVideoActivity.this, "Please Wait", Toast.LENGTH_SHORT).show();
                    btn_refresh.setClickable(true);
                }
            }
        });

    }

    public void callgetLiveId() {
        if (!CommonUtilities.isOnline(LiveVideoActivity.this)) {
            Toast.makeText(LiveVideoActivity.this, "Please On Your Internet Connection", Toast.LENGTH_LONG).show();
            return;
        }
//        progressdialog = new ProgressDialog(this);
//        progressdialog.setCancelable(false);
//        progressdialog.setMessage("Loading...");
//        progressdialog.setTitle("Wait...");
//        progressdialog.show();
        final String string_json = "Result";
        String url = Global.WEBBASE_URL + "getLiveId?page=1&size=2&SchoolId=" + Global.SCHOOL_ID + "&ClassId=" + AarambhSharedPreference.loadClassIdFromPreference(LiveVideoActivity.this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                String res = response.toString();
                parseResponseLiveId(res);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //progressDialog.dismiss();
                NetworkResponse response = error.networkResponse;
                Log.e("com.Aarambh", "error response " + response);
                if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                    Log.e("mls", "VolleyError TimeoutError error or NoConnectionError");
                } else if (error instanceof AuthFailureError) {                    //TODO
                    Log.e("mls", "VolleyError AuthFailureError");
                } else if (error instanceof ServerError) {
                    Log.e("mls", "VolleyError ServerError");
                } else if (error instanceof NetworkError) {
                    Log.e("mls", "VolleyError NetworkError");
                } else if (error instanceof ParseError || error instanceof VolleyError) {
                    Log.e("mls", "VolleyError TParseError");
                    Log.e("Volley Error", error.toString());
                }
                if (error instanceof ServerError && response != null) {
                    try {
                        String res = new String(response.data, HttpHeaderParser.parseCharset(response.headers, "utf-8"));
                        // Now you can use any deserializer to make sense of data
                        // progressDialog.show();
                        parseResponseLiveId(response.toString());
                    } catch (UnsupportedEncodingException e1) {
                        // Couldn't properly decode data to string
                        e1.printStackTrace();
                    }
                }

            }
        }) {

            @Override
            protected VolleyError parseNetworkError(VolleyError volleyError) {
                String json;
                if (volleyError.networkResponse != null && volleyError.networkResponse.data != null) {
                    try {
                        json = new String(volleyError.networkResponse.data,
                                HttpHeaderParser.parseCharset(volleyError.networkResponse.headers));
                    } catch (UnsupportedEncodingException e) {
                        return new VolleyError(e.getMessage());
                    }
                    return new VolleyError(json);
                }
                return volleyError;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                headers.put("Authorization", "Bearer " + AarambhSharedPreference.loadUserTokenFromPreference(LiveVideoActivity.this));
                return headers;
            }
        };
        VolleySingleton.getInstance(LiveVideoActivity.this).addToRequestQueue(stringRequest, string_json);


    }

    public void parseResponseLiveId(String response) {
        try {
            Log.e("Response", response);
            JSONObject jsonObject1 = new JSONObject(response);
            String status = jsonObject1.getString("status");
            boolean success = jsonObject1.getBoolean("success");
            if (success == false) {
                String error = jsonObject1.getString("error");
                Toast.makeText(this, error + "", Toast.LENGTH_LONG).show();
            } else if (success == true) {
                String message = jsonObject1.getString("Message");
                if (message.equalsIgnoreCase("No Data Found.")) {
                    Toast.makeText(this, message + "", Toast.LENGTH_LONG).show();
                } else if (message.equalsIgnoreCase("Data Found")) {
                    JSONArray jsonArray = jsonObject1.getJSONArray("LinkData");
                    String ClassId = "", LiveClass = null;
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject obj = jsonArray.getJSONObject(i);
                        String LiveId = obj.getString("LiveId");
                        ClassId = obj.getString("ClassId");
                        String SchoolId = obj.getString("SchoolId");
                        LiveClass = obj.getString("LiveClass");
                    }
                    if (pref_class.equalsIgnoreCase(ClassId)) {
                        //videoUri="rtmp://139.59.77.127/LiveApp/698392100281705556338849";
                        if (LiveClass.equals("")) {
                            Toast.makeText(this, "No Live Class.", Toast.LENGTH_SHORT).show();
                            //if(player == null){}


                        }

                        else {
                            videoUri = LiveClass;
                            setUp();
                            playerView.setPlayer(player);

//                            Log.e("Player", player.getPlayWhenReady()+"");
//                            Log.e("Player", player.getPlaybackState()+"");
//                            Log.e("Player Loading", player.p+"");
//                            if(player.getPlayWhenReady() == false){
//                                img_back.setVisibility(View.VISIBLE);
//                            }
                            final String status_test = "";
                            player.addListener(new Player.EventListener() {
                                @Override
                                public void onTimelineChanged(Timeline timeline, @Nullable Object manifest, int reason) {

                                }

                                @Override
                                public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {

                                }

                                @Override
                                public void onLoadingChanged(boolean isLoading) {

                                }

                                @Override
                                public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
                                    switch (playbackState){
                                        case Player.STATE_BUFFERING:
                                            Log.e("LOADING","L");
                                            img_back.setVisibility(View.VISIBLE);
                                            break;
                                            case Player.STATE_ENDED:
                                            Log.e("STOPPED","S");
                                            break;
                                            case Player.STATE_IDLE:
                                            Log.e("IDLE","I");
                                            break;
                                            case Player.STATE_READY:
                                                img_back.setVisibility(View.GONE);
                                            Log.e("Ready","R");
                                            break;
                                            default:
                                                Log.e("IDLE","I");
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

                                }

                                @Override
                                public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {

                                }

                                @Override
                                public void onSeekProcessed() {

                                }
                            });
                            Log.e("Live Class", videoUri);
                            Log.e("PrefClass", pref_class);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void checkOrientation() {
        if (getResources().getBoolean(R.bool.portrait_only)) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
    }

    private void setUp() {
        initializePlayer();
        if (videoUri == null) {
            return;
        }
        buildMediaSource(Uri.parse(videoUri));
    }

    public void onViewClicked() {
        finish();
    }

    private void initializePlayer() {
        if (player == null) {
            // 1. Create a default TrackSelector
            LoadControl loadControl = new DefaultLoadControl(
                    new DefaultAllocator(true, 16),
                    VideoPlayerConfig.MIN_BUFFER_DURATION,
                    VideoPlayerConfig.MAX_BUFFER_DURATION,
                    VideoPlayerConfig.MIN_PLAYBACK_START_BUFFER,
                    VideoPlayerConfig.MIN_PLAYBACK_RESUME_BUFFER, -1, true);
            BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
            TrackSelection.Factory videoTrackSelectionFactory =
                    new AdaptiveTrackSelection.Factory(bandwidthMeter);
            TrackSelector trackSelector =
                    new DefaultTrackSelector(videoTrackSelectionFactory);
            // 2. Create the player
            player =
                    ExoPlayerFactory.newSimpleInstance(new DefaultRenderersFactory(this), trackSelector,
                            loadControl);
            videoFullScreenPlayer.setPlayer(player);

        }
    }

    private void buildMediaSource(Uri mUri) {
        // Measures bandwidth during playback. Can be null if not required.
        DefaultBandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
        // Produces DataSource instances through which media data is loaded.
        DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(this,
                Util.getUserAgent(this, getString(R.string.app_name)), bandwidthMeter);
        // This is the MediaSource representing the media to be played.
        MediaSource videoSource = new ExtractorMediaSource.Factory(dataSourceFactory)
                .createMediaSource(mUri);
        // Prepare the player with the source.
        player.prepare(videoSource);
        player.setPlayWhenReady(true);
        PlaybackParameters pbm = new PlaybackParameters(1f);

        //player.setPlaybackParameters();
        // player.addListener(this);

    }

    private void releasePlayer() {
        if (player != null) {
            player.release();
            player = null;
        }
    }

    private void pausePlayer() {
        if (player != null) {
            player.setPlayWhenReady(false);
            player.getPlaybackState();
        }
    }

    private void resumePlayer() {
        if (player != null) {
            player.setPlayWhenReady(true);
            player.getPlaybackState();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        pausePlayer();
        if (mRunnable != null) {
            mHandler.removeCallbacks(mRunnable);
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        resumePlayer();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        releasePlayer();
    }
}
