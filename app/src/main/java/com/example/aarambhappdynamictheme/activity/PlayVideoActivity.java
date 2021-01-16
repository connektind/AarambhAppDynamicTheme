package com.example.aarambhappdynamictheme.activity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.StrictMode;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.BackgroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aarambhappdynamictheme.R;
import com.example.aarambhappdynamictheme.adapter.TopicvideoListAdapter;
import com.example.aarambhappdynamictheme.model.TopicvideoListModel;
import com.example.aarambhappdynamictheme.model.VideoDetails;
import com.example.aarambhappdynamictheme.sheardPrefrence.AarambhThemeSharedPrefreence;
import com.example.aarambhappdynamictheme.textGradient.TextColorGradient;
import com.example.aarambhappdynamictheme.textGradient.VideoPlayerConfig;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
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
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultAllocator;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class PlayVideoActivity extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener {
    PlayerView videoFullScreenPlayer;
    ProgressBar spinnerVideoDetails;
    TextView text_with_lyrics,topic_name_txt,txt_desc;
    String videoUri,topic_name;
    SimpleExoPlayer player;
    Handler mHandler;
    Runnable mRunnable;
    private static final String TAG = "ExoPlayerActivity";
    private static final String KEY_VIDEO_URI = "video_uri";
    private final int REQ_CODE = 100;
    ImageView back_btn,back_btn_youtube,full_screen_img;
    String API_KEY="AIzaSyAja-68gRGjnbSCsR5U1IuMKpUUO8rABmo";
    ArrayList<TopicvideoListModel> topicvideoListModelArrayList;
    TopicvideoListAdapter topicvideoListAdapter;
    RecyclerView upcoming_video_recyclerview;
    RelativeLayout relativeLayout;
    String youtube_url;
    String back_recom="";
    private View mContentView;
    VideoDetails getVideoDetails;
    LinearLayout play_video_background,transparent_play_video;
    String base_image,transparent_color,base_color_one,base_color_two;
    Bitmap Background;
    TextView description_text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_video);
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        checkOrientation();
        base_image= AarambhThemeSharedPrefreence.loadBaseImageFromPreference(this);
        transparent_color=AarambhThemeSharedPrefreence.loadBaseImageTransparentFromPreference(this);
        Log.e("transprent",transparent_color);

        base_color_one=AarambhThemeSharedPrefreence.loadBaseColorOneFromPreference(this);
        base_color_two=AarambhThemeSharedPrefreence.loadBaseColorTwoFromPreference(this);
        //topic_name=getIntent().getStringExtra("topic_name");
        //videoUri=getIntent().getStringExtra("video_link");
        init();
        listner();
        try {
            getVideoDetails = (VideoDetails) getIntent().getSerializableExtra("video_data");
            Log.e("RedThemen",getVideoDetails.getVideoId() + ":" + getVideoDetails.getVideoName() +" : "+ getVideoDetails.getVideoDesc());
            back_recom=getIntent().getStringExtra("recom_back_red");
            Log.e("back+sfs",back_recom);
            txt_desc.setText(getVideoDetails.getVideoDesc());
        }catch (Exception e){
            e.printStackTrace();
        }

        videoUri=getVideoDetails.getVideoId();
        Log.e("video_url",videoUri);
        //  videoUri=VideoPlayerConfig.DEFAULT_VIDEO_URL;


        relativeLayout=findViewById(R.id.exo_player_relative_layout);
        if (videoUri.contains(".mp4")){
            setUp();

        }else if (videoUri.contains("v=")){
            relativeLayout.removeAllViews();
            final LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view1 = (View) inflater.inflate(R.layout.youtube_player, null);
            relativeLayout.addView(view1);
            YouTubePlayerView youTubePlayerView = findViewById(R.id.youtube_player_view);
            youTubePlayerView.initialize(API_KEY, this);
        }else {
            relativeLayout.removeAllViews();
            final LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view1 = (View) inflater.inflate(R.layout.youtube_player, null);
            relativeLayout.addView(view1);
            YouTubePlayerView youTubePlayerView = findViewById(R.id.youtube_player_view);
            youTubePlayerView.initialize(API_KEY, this);


        }
        // }


//            getLifecycle().addObserver(youTubePlayerView);
//            youTubePlayerView.setPressed(true);
//            youTubePlayerView.addYouTubePlayerListener(new YouTubePlayerListener() {
//            @Override
//            public void onVideoLoadedFraction(YouTubePlayer youTubePlayer, float v) {
//                spinnerVideoDetails.setVisibility(View.VISIBLE);
//
//            }
//
//            @Override
//            public void onVideoId(YouTubePlayer youTubePlayer, String s) {
//                Log.e("s",s);
//                youtube_url=s;
//                Log.e("youtube_videoId",youtube_url);
//            }
//
//            @Override
//            public void onVideoDuration(YouTubePlayer youTubePlayer, float v) {
//
//            }
//
//            @Override
//            public void onStateChange(YouTubePlayer youTubePlayer, PlayerConstants.PlayerState playerState) {
//
//            }
//
//            @Override
//            public void onPlaybackRateChange(YouTubePlayer youTubePlayer, PlayerConstants.PlaybackRate playbackRate) {
//
//            }
//
//            @Override
//            public void onPlaybackQualityChange(YouTubePlayer youTubePlayer, PlayerConstants.PlaybackQuality playbackQuality) {
//
//            }
//
//            @Override
//            public void onError(YouTubePlayer youTubePlayer, PlayerConstants.PlayerError playerError) {
//                  Log.e("youtube player_error", String.valueOf(playerError));
//            }
//
//            @Override
//            public void onCurrentSecond(YouTubePlayer youTubePlayer, float v) {
//
//            }
//
//            @Override
//            public void onApiChange(YouTubePlayer youTubePlayer) {
//
//            }
//
//            @Override
//            public void onReady(YouTubePlayer youTubePlayer) {
//                spinnerVideoDetails.setVisibility(View.INVISIBLE);
//
//                 String videourl=videoUri;
//                 Log.e("videoId",videourl);
//
//                  String str=videourl.replace("https://www.youtube.com/watch?v=","");
//                   Log.e("video_url_youtube", String.valueOf(str));
//               //  youTubePlayer.loadVideo(videoId, 0);
//                youTubePlayer.cueVideo(str,0);
//            }
//
//        });
//        //setUp();
        //}
    }
    private void listner() {
//        full_screen_img.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent =new Intent(RedThemeVideoPlayerActivity.this, FullscreenVideoActivity.class);
//                intent.putExtra("videoUrl",videoUri);
//                startActivity(intent);
//            }
//        });
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (back_recom) {
                    case "1":
                        Intent intent = new Intent(PlayVideoActivity.this, DashBoardActivity.class);
                        startActivity(intent);
                        finish();
                        break;
                    case "11":
                        Intent intent1 = new Intent(PlayVideoActivity.this, SubjectActivity.class);
                        startActivity(intent1);
                        finish();
                        break;

                    default:
                        Intent intent2 = new Intent(PlayVideoActivity.this, VideoListActivity.class);
                        startActivity(intent2);
                        finish();

                }
            }

        });
//            try {
//                back_btn.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        Intent intent = new Intent(RedThemeVideoPlayerActivity.this, RedThemeTopicVideoActivity.class);
//                        startActivity(intent);
//                        finish();
//                    }
//                });
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }

//        try {
//            back_btn_youtube.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Intent intent=new Intent(RedThemeVideoPlayerActivity.this, RedThemeTopicVideoActivity.class);
//                    startActivity(intent);
//                    finish();
//                }
//            });
//
//        }catch (Exception e){
//            e.printStackTrace();
//
//        }

//        topicvideoListModelArrayList.add(new TopicvideoListModel("https://i.ytimg.com/vi/lXia7fBogwI/maxresdefault.jpg","Parts OF a Plant.","7 min", ""));
//        topicvideoListModelArrayList.add(new TopicvideoListModel("https://i.ytimg.com/vi/zyQRZ_xgs6Q/hqdefault.jpg","Flowers,Fruits And Seeds.","10 min", ""));
//        topicvideoListModelArrayList.add(new TopicvideoListModel("https://i.ytimg.com/vi/i5EvvXjwkh8/maxresdefault.jpg","Animal Structure.","15 min", ""));
//        topicvideoListModelArrayList.add(new TopicvideoListModel("https://i.ytimg.com/vi/071lUxclTBw/maxresdefault.jpg","Recycler Water.","6 min", ""));
//
//        topicvideoListModelArrayList.add(new TopicvideoListModel("https://i.ytimg.com/vi/lXia7fBogwI/maxresdefault.jpg","Parts OF a Plant.","7 min", ""));
//        topicvideoListModelArrayList.add(new TopicvideoListModel("https://i.ytimg.com/vi/zyQRZ_xgs6Q/hqdefault.jpg","Flowers,Fruits And Seeds.","10 min", ""));
//        topicvideoListModelArrayList.add(new TopicvideoListModel("https://i.ytimg.com/vi/i5EvvXjwkh8/maxresdefault.jpg","Animal Structure.","15 min", ""));
//        topicvideoListModelArrayList.add(new TopicvideoListModel("https://i.ytimg.com/vi/071lUxclTBw/maxresdefault.jpg","Name","6 min", ""));
//
//
//        topicvideoListAdapter=new TopicvideoListAdapter(this,topicvideoListModelArrayList);
//        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this,RecyclerView.VERTICAL,false);
//        upcoming_video_recyclerview.setLayoutManager(linearLayoutManager);
//        upcoming_video_recyclerview.setAdapter(topicvideoListAdapter);
    }
    private void checkOrientation() {
        if (getResources().getBoolean(R.bool.portrait_only)) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
    }
    private void init() {
        StrictMode.ThreadPolicy policy = new
                StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        // full_screen_img=findViewById(R.id.exo_fullscreen_icon);
        // upcoming_video_recyclerview=findViewById(R.id.upcoming_video_recyclerview);
        topicvideoListModelArrayList=new ArrayList<>();
        videoFullScreenPlayer = findViewById(R.id.videoFullScreenPlayer);
        spinnerVideoDetails = findViewById(R.id.spinnerVideoDetails);
        //   text_with_lyrics=findViewById(R.id.text_lyrices);
        back_btn=findViewById(R.id.back_player);
        back_btn_youtube=findViewById(R.id.back_player_youtube);
        description_text=findViewById(R.id.description_text);
        //  topic_name_txt=findViewById(R.id.topic_name_player);
        txt_desc =  findViewById(R.id.txtDesc);
        try {
            topic_name_txt.setText(getVideoDetails.getVideoName());
            text_with_lyrics.setText(getVideoDetails.getVideoDesc());
            TextColorGradient textColorGradient=new TextColorGradient();
            textColorGradient.getColorTextGradient(text_with_lyrics,base_color_one,base_color_two);
        }catch (Exception e){
            e.printStackTrace();
        }
        play_video_background=findViewById(R.id.play_video_background);
        transparent_play_video=findViewById(R.id.transparent_play_video);
        transparent_play_video.setBackgroundColor(Color.parseColor(transparent_color));
        TextColorGradient textColorGradient=new TextColorGradient();
        textColorGradient.getColorTextGradient(description_text,base_color_one,base_color_two);
        themeDynamicAdd();

    }

    @Override
    public void onBackPressed() {
//        switch (back_recom) {
//            case "1":
//                Intent intent = new Intent(RedThemeVideoPlayerActivity.this, DashBoardSixthStandardActivity.class);
//                startActivity(intent);
//                finish();
//                break;
//            case "11":
//                Intent intent1 = new Intent(RedThemeVideoPlayerActivity.this, SubjectsSixthClassActivity.class);
//                startActivity(intent1);
//                finish();
//                break;
//
//            default:
//                Intent intent2 = new Intent(RedThemeVideoPlayerActivity.this, RedThemeTopicVideoActivity.class);
//                startActivity(intent2);
//                finish();
//                break;
//
//        }
        finish();
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
    public void setHighLightedText(TextView tv, String textToHighlight) {
        String tvt = tv.getText().toString();
        int ofe = tvt.indexOf(textToHighlight, 0);
        Spannable wordToSpan = new SpannableString(tv.getText());
        for (int ofs = 0; ofs < tvt.length() && ofe != -1; ofs = ofe + 1) {
            ofe = tvt.indexOf(textToHighlight, ofs);
            if (ofe == -1)
                break;
            else {
                // set color here
                wordToSpan.setSpan(new BackgroundColorSpan(0xFFFFFF00), ofe, ofe + textToHighlight.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                tv.setText(wordToSpan, TextView.BufferType.SPANNABLE);
            }
        }
    }




    public  void highlightString(String input){


//Get the text from text view and create a spannable string
        SpannableString spannableString = new SpannableString(text_with_lyrics.getText());
//Get the previous spans and remove them
        BackgroundColorSpan[] backgroundSpans = spannableString.getSpans(0, spannableString.length(), BackgroundColorSpan.class);

        for (BackgroundColorSpan span: backgroundSpans) {
            spannableString.removeSpan(span);
        }

//Search for all occurrences of the keyword in the string
        int indexOfKeyword = spannableString.toString().indexOf(input);

        while (indexOfKeyword > 0) {
            //Create a background color span on the keyword
            spannableString.setSpan(new BackgroundColorSpan(Color.YELLOW), indexOfKeyword, indexOfKeyword + input.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

            //Get the next index of the keyword
            indexOfKeyword = spannableString.toString().indexOf(input, indexOfKeyword + input.length());
        }

//Set the final text on TextView
        text_with_lyrics.setText(spannableString);
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, final YouTubePlayer youTubePlayer, boolean b) {
        Log.e("video_url_youtube", String.valueOf(videoUri));
        //  youTubePlayer.loadVideo(videoId, 0);
        //   youTubePlayer.cueVideo(videoUri);
        youTubePlayer.cueVideo(videoUri);
        // youTubePlayer.setShowFullscreenButton(false);
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
        Toast.makeText(this,"Try Again.",Toast.LENGTH_LONG).show();
    }

    @Override
    public void finish() {
        Intent in=new Intent(PlayVideoActivity.this, VideoListActivity.class);
        in.putExtra("DataSubject", getVideoDetails);
        //  in.putExtra("LandDetail", landDetailModel);
        in.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        setResult(RESULT_OK, in);
        super.finish();
    }
    private void themeDynamicAdd() {

        //Bitmap myImage = getBitmapFromURL(image);
        try {
            Background=getBitmapFromURL(base_image);
            Drawable dr = new BitmapDrawable((Background));
            play_video_background.setBackgroundDrawable(dr);

        }catch (Exception e){
            e.printStackTrace();
        }

    }
    public static Bitmap getBitmapFromURL(String src) {
        try {
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            // Log exception
            return null;
        }
    }

}
