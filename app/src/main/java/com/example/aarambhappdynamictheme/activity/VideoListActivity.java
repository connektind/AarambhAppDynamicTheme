package com.example.aarambhappdynamictheme.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.aarambhappdynamictheme.R;
import com.example.aarambhappdynamictheme.adapter.TopicvideoListAdapter;
import com.example.aarambhappdynamictheme.model.ChapterModel;
import com.example.aarambhappdynamictheme.model.SubjChapterTopicListModel;
import com.example.aarambhappdynamictheme.model.TopicvideoListModel;
import com.example.aarambhappdynamictheme.model.VideoDetails;
import com.example.aarambhappdynamictheme.model.YouTubeTitle;
import com.example.aarambhappdynamictheme.sheardPrefrence.AarambhSharedPreference;
import com.example.aarambhappdynamictheme.sheardPrefrence.AarambhThemeSharedPrefreence;
import com.example.aarambhappdynamictheme.textGradient.TextColorGradient;
import com.example.aarambhappdynamictheme.util.CommonUtilities;
import com.example.aarambhappdynamictheme.util.Global;
import com.example.aarambhappdynamictheme.util.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class VideoListActivity extends AppCompatActivity {
    //    ArrayList<TopicvideoListModel> topicvideoListModelArrayList;
//    ArrayList<SubjChapterTopicListModel> subjectChapterListModelArrayList;
//    ArrayList<VideoDetails>videoDetailsArrayList;
    TopicvideoListAdapter topicvideoListAdapter;
    RecyclerView topic_list_recyclerview;
    ImageView back_arrow;
    TextView textView, number_of_videos;
    Button back_btn;
    String class_postion, topic_name, topic_id;
    //youtube channel url
    String URL = "https://www.googleapis.com/youtube/v3/search?rel=0?part=snippet&channelId=UCAfot-yupAQvVqAuZw-Pctg&maxResults=25&key=AIzaSyAja-68gRGjnbSCsR5U1IuMKpUUO8rABmo";
    ArrayList<String> you_Topic;
    VideoDetails videoDetails;

    LinearLayout background_videolist;
    RelativeLayout transparent_videolist;
    String base_image, transparent_color, base_color_one, base_color_two;
    Bitmap Background;
    ImageView video_list_background;
    CardView pratices_Chapter, test_subjcet;
    TextView practice, test;
    AppCompatImageView pratice_background, test_profile;
    String sixth_Details;
    ArrayList<ChapterModel> chapterModelArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_list);
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        checkOrientation();
        class_postion = AarambhSharedPreference.loadClassIdFromPreference(this);
        topic_name = AarambhSharedPreference.loadTopicNameFromPreference(this);

        base_image = AarambhThemeSharedPrefreence.loadBaseImageFromPreference(this);
        transparent_color = AarambhThemeSharedPrefreence.loadBaseImageTransparentFromPreference(this);
        Log.e("transprent", transparent_color);

        base_color_one = AarambhThemeSharedPrefreence.loadBaseColorOneFromPreference(this);
        base_color_two = AarambhThemeSharedPrefreence.loadBaseColorTwoFromPreference(this);
        // topic_id = AarambhSharedPreference.loadTopicIdFromPreference(this);
        init();
        listner();
        getStudentChapters();
        //    getYoutubeDatacall();
        //getTopicApicalling();
//        if (AarambhSharedPreference.loadClassIdFromPreference(this).equalsIgnoreCase("3")) {
//            try {
//                 sixth_Details = getIntent().getStringExtra("8th_Details");
//                //VideoDetails videoDetails = (VideoDetails) getIntent().getSerializableExtra("VDAR");
//                //ArrayList<VideoDetails> videoDetails = (ArrayList<VideoDetails>) getIntent().getSerializableExtra("VDAR");
//                you_Topic = new ArrayList<>();
//                ArrayList<String> videoName = new ArrayList<>();
//                Log.e("Red_PL_ID", sixth_Details);
//                getVideoAccordingToPlayListId(sixth_Details);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        } else if (AarambhSharedPreference.loadClassIdFromPreference(this).equalsIgnoreCase("2")) {
//            try {
//                 sixth_Details = getIntent().getStringExtra("7th_Details");
//                //VideoDetails videoDetails = (VideoDetails) getIntent().getSerializableExtra("VDAR");
//                //ArrayList<VideoDetails> videoDetails = (ArrayList<VideoDetails>) getIntent().getSerializableExtra("VDAR");
//                you_Topic = new ArrayList<>();
//                ArrayList<String> videoName = new ArrayList<>();
//                Log.e("Red_PL_ID", sixth_Details);
//                getVideoAccordingToPlayListId(sixth_Details);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        } else if (AarambhSharedPreference.loadClassIdFromPreference(this).equalsIgnoreCase("1")) {
//            try {
//                 sixth_Details = getIntent().getStringExtra("6th_Details");
//                //VideoDetails videoDetails = (VideoDetails) getIntent().getSerializableExtra("VDAR");
//                //ArrayList<VideoDetails> videoDetails = (ArrayList<VideoDetails>) getIntent().getSerializableExtra("VDAR");
//                you_Topic = new ArrayList<>();
//                ArrayList<String> videoName = new ArrayList<>();
//                Log.e("Red_PL_ID", sixth_Details);
//                getVideoAccordingToPlayListId(sixth_Details);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//            for(int x = 0;x<sixth_Details.getTopic().size();x++){
//                you_Topic = (ArrayList<String>) sixth_Details.getTopic().clone();
//            }
//            for(int x = 0;x<sixth_Details.getVideoTitle().size();x++){
//                videoName = (ArrayList<String>) sixth_Details.getVideoTitle().clone();
//            }
        //number_of_videos.setText(sixth_Details.getTopic().size() + " videos");
        //Log.e("image_video",videoDetails.getURL());

        //topicvideoListAdapter = new TopicvideoListAdapter(RedThemeTopicVideoActivity.this, you_Topic,videoDetails);


//ArrayList<VideoDetails> filterTopicDetails = new ArrayList<>();
//            for(int i=0;i<videoDetails.size();i++){
//                VideoDetails vd = videoDetails.get(i);
//                String test [] = vd.getVideoName().split("_");
//                String arrTopic = test[3];
//
//                if(you_Topic.contains(arrTopic)){
//                    Log.e("TVideo Details IF:-",vd.getVideoName()+" : "+ vd.getVideoDesc());
//                    filterTopicDetails.add(vd);
//                }
//
//            }
//
////            topicvideoListAdapter = new TopicvideoListAdapter(RedThemeTopicVideoActivity.this, you_Topic,filterTopicDetails);
//             try{
//            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
//            topic_list_recyclerview.setLayoutManager(linearLayoutManager);
//            topic_list_recyclerview.setAdapter(topicvideoListAdapter);
//        }catch (Exception e){
//            e.printStackTrace();
//        }
    }

    public void getStudentChapters() {
        if (!CommonUtilities.isOnline(VideoListActivity.this)) {
            Toast.makeText(VideoListActivity.this, "Please On Your Internet Connection", Toast.LENGTH_LONG).show();
            return;
        }
//        ProgressDialog progressdialog = new ProgressDialog(this);
//        progressdialog.setCancelable(false);
//        progressdialog.setMessage("Loading...");
//        progressdialog.setTitle("Wait...");
//        progressdialog.show();
        final String string_json = "Result";
        String url = Global.WEBBASE_URL + "getStudentChapters?page=1&size=2&SchoolId=" + Global.AARAMBH_ID;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                String res = response.toString();
                parseResponseChapters(res);
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
                        parseResponseChapters(response.toString());
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
                headers.put("Authorization", "Bearer " + AarambhSharedPreference.loadUserTokenFromPreference(VideoListActivity.this));
                return headers;
            }
        };
        VolleySingleton.getInstance(VideoListActivity.this).addToRequestQueue(stringRequest, string_json);
    }

    public void parseResponseChapters(String response) {
        Log.e("Response Course", response);
        // progressdialog.dismiss();
        try {
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

                    JSONArray jsonObjectTopicList = jsonObject1.getJSONArray("ChapterList");

                    for (int i = 0; i < jsonObjectTopicList.length(); i++) {
                        JSONObject topic = jsonObjectTopicList.getJSONObject(i);

                        String ChapterId = topic.getString("ChapterId");
                        String ChapterName = topic.getString("ChapterName");
                        String ChapterURLS = topic.getString("ChapterURLS");
                        String CourseId = topic.getString("CourseId");
                        String TopicId = topic.getString("TopicId");
                        String ClassId = topic.getString("ClassId");
                        String SchoolId = topic.getString("SchoolId");
                        String StatusId = topic.getString("StatusId");
                        String CreatedById = topic.getString("CreatedById");
                        String ModifiedById = topic.getString("ModifiedById");
                        String CreationDate = topic.getString("CreationDate");
                        String ModificationDate = topic.getString("ModificationDate");
                        ChapterModel tm = new ChapterModel(ChapterId, ChapterName, ChapterURLS, ClassId, TopicId, SchoolId, CourseId, StatusId, CreatedById, ModifiedById, CreationDate, ModificationDate);
                        chapterModelArrayList.add(tm);
                    }

                    number_of_videos.setText(chapterModelArrayList.size() + " videos");
                    topicvideoListAdapter = new TopicvideoListAdapter(VideoListActivity.this, chapterModelArrayList);
                    try {
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
                        topic_list_recyclerview.setLayoutManager(linearLayoutManager);
                        topic_list_recyclerview.setAdapter(topicvideoListAdapter);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
//    private void getYoutubeDatacall() {
//        RequestQueue requestQueue= Volley.newRequestQueue(this);
//        StringRequest stringRequest=new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                try {
//                    Log.e("response_youtube",response);
//                    JSONObject jsonObject=new JSONObject(response);
//                    JSONArray jsonArray=jsonObject.getJSONArray("items");
//                    for(int i=8;i<jsonArray.length();i++){
//                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
//                        JSONObject jsonVideoId=jsonObject1.getJSONObject("id");
//                        //      Log.e("jsonVideoId", String.valueOf(jsonVideoId));
//                        JSONObject jsonsnippet= jsonObject1.getJSONObject("snippet");
//                        //    Log.e("jsonsnippet", String.valueOf(jsonsnippet));
//                        JSONObject jsonObjectdefault = jsonsnippet.getJSONObject("thumbnails").getJSONObject("medium");
//                        //   Log.e("jsonObjectdefault", String.valueOf(jsonObjectdefault));
//                        VideoDetails videoDetails=new VideoDetails();
//                        String id_kind = jsonVideoId.getString("kind");
//                        if(id_kind.equalsIgnoreCase("youtube#playlist")) {
////                            videoDetails.setVideoId(jsonVideoId.getString("playlistId"));
////                            videoDetails.setURL(jsonObjectdefault.getString("url"));
////                            videoDetails.setVideoName(jsonsnippet.getString("title"));
////                            String title = jsonsnippet.getString("title");
//                        }
//
//                        else if(id_kind.equalsIgnoreCase("youtube#video")){
//                            videoDetails.setVideoId(jsonVideoId.getString("videoId"));
//                            videoDetails.setURL(jsonObjectdefault.getString("url"));
//                            videoDetails.setVideoName(jsonsnippet.getString("title"));
//                            // video_title = jsonsnippet.getString("title");
//                        }
//
//                        // String videoid=jsonVideoId.getString("videoId");
////                        String id_kind = jsonVideoId.getString("kind");
////                        if(id_kind.equalsIgnoreCase("youtube#playlist")) {
////                            videoDetails.setVideoId(jsonVideoId.getString("playlistId"));
////                        }
////                        else if(id_kind.equalsIgnoreCase("youtube#video")){
////                            videoDetails.setVideoId(jsonVideoId.getString("videoId"));
////                        }
////                        videoDetails.setURL(jsonObjectdefault.getString("url"));
////                        Log.e("videoDetails.getURL()",videoDetails.getURL());
////                        //subjChapterTopicListModel.setChapterURLS(jsonObjectdefault.getString("url"));
////                        videoDetails.setVideoName(jsonsnippet.getString("title"));
////                        Log.e("videoDetails.getVideoName()",videoDetails.getVideoName());
////                        // subjChapterTopicListModel.setChapterName(videoDetails.getVideoName());
//
//                        String videoname=videoDetails.getVideoName();
//                        try {
//                            String[] parts = videoname.split(" "); // escape .
//                            String part1 = parts[0];
//                            Log.e("part1",part1);
//                            String part2 = parts[1];
//                            Log.e("part2",part2);
//                            String part3 = parts[2];
//                            Log.e("part3",part3);
//                            String part4 = parts[4];
//                            Log.e("part4",part4);
//                        }catch (Exception e){
//                            e.printStackTrace();
//                        }
//
//                        //6th_Maths_Chapter 6_ Part 1 Introduction to Integers
//                        videoDetails.setVideoDesc(jsonsnippet.getString("description"));
//                        Log.e("videoDetails.getVideoDesc()",videoDetails.getVideoDesc());
//
//                        // videoDetails.setVideoId(videoid);
//                        //subjectNameModel.setSubj_back_bg(R.drawable.subj_back_bg);
//                        videoDetailsArrayList.add(videoDetails);
////                            subjChapterTopicListModel.setBlack_transparent_back(R.drawable.black_transparent_back);
////                            subjChapterTopicListModelArrayList.add(subjChapterTopicListModel);
//                    }
//                    number_of_videos.setText(jsonArray.length() + " videos");
//                    //  topicvideoListAdapter = new TopicvideoListAdapter(RedThemeTopicVideoActivity.this, videoDetailsArrayList);
//                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(VideoListActivity.this, RecyclerView.VERTICAL, false);
//                    topic_list_recyclerview.setLayoutManager(linearLayoutManager);
//                    topic_list_recyclerview.setAdapter(topicvideoListAdapter);
//
////                    lvVideo.setAdapter(customListAdapter);
////                    customListAdapter.notifyDataSetChanged();
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                error.printStackTrace();
//            }
//        });
//        int socketTimeout = 30000;
//        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
//        stringRequest.setRetryPolicy(policy);
//        requestQueue.add(stringRequest);
//    }

//    private void getTopicApicalling() {
//        {
//            if (!CommonUtilities.isOnline(VideoListActivity.this)) {
//                Toast.makeText(VideoListActivity.this, "Please On Your Internet Connection", Toast.LENGTH_LONG).show();
//                return;
//            }
//            Log.e("topic_id", topic_id);
//
//            String url = Global.WEBBASE_URL + "getChapterByTopicId?TopicId=" + topic_id;
//            Log.e("url", url);
//            final String string_json = "Result";
//            Log.e("string_json", string_json);
//            try {
//                StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        String res = response.toString();
//                        parseResponseTopicApi(res);
//                        Log.e("res", res);
//                    }
//                }, new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        //progressDialog.dismiss();
//                        NetworkResponse response = error.networkResponse;
//
//                        Log.e("com.Aarambh", "error response " + response);
//
//                        if (error instanceof TimeoutError || error instanceof NoConnectionError) {
//                            Log.e("mls", "VolleyError TimeoutError error or NoConnectionError");
//                        } else if (error instanceof AuthFailureError) {                    //TODO
//                            Log.e("mls", "VolleyError AuthFailureError");
//                        } else if (error instanceof ServerError) {
//                            Log.e("mls", "VolleyError ServerError");
//                        } else if (error instanceof NetworkError) {
//                            Log.e("mls", "VolleyError NetworkError");
//                        } else if (error instanceof ParseError || error instanceof VolleyError) {
//                            Log.e("mls", "VolleyError TParseError");
//                            Log.e("Volley Error", error.toString());
//                        }
//                        if (error instanceof ServerError && response != null) {
//                            try {
//                                String res = new String(response.data, HttpHeaderParser.parseCharset(response.headers, "utf-8"));
//                                // Now you can use any deserializer to make sense of data
//                                // progressDialog.show();
//                                parseResponseTopicApi(response.toString());
//
//                            } catch (UnsupportedEncodingException e1) {
//                                // Couldn't properly decode data to string
//                                e1.printStackTrace();
//                            }
//                        }
//                    }
//                }) {
//                    @Override
//                    public Map<String, String> getParams() throws AuthFailureError {
//                        HashMap<String, String> params = new HashMap<String, String>();
//                        params.put("TopicId", topic_id);
//                        return params;
//                    }
//
//                    @Override
//                    protected VolleyError parseNetworkError(VolleyError volleyError) {
//                        String json;
//                        if (volleyError.networkResponse != null && volleyError.networkResponse.data != null) {
//                            try {
//                                json = new String(volleyError.networkResponse.data,
//                                        HttpHeaderParser.parseCharset(volleyError.networkResponse.headers));
//                            } catch (UnsupportedEncodingException e) {
//                                return new VolleyError(e.getMessage());
//                            }
//                            return new VolleyError(json);
//                        }
//                        return volleyError;
//                    }
//
//
//                    @Override
//                    public Map<String, String> getHeaders() throws AuthFailureError {
//                        HashMap<String, String> headers = new HashMap<String, String>();
//                        headers.put("Content-Type", "application/json; charset=utf-8");
//                        headers.put("Authorization", "Bearer " + AarambhSharedPreference.loadUserTokenFromPreference(VideoListActivity.this));
//                        return headers;
//                    }
//                };
//                VolleySingleton.getInstance(VideoListActivity.this).addToRequestQueue(stringRequest, string_json);
//
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//    private void parseResponseTopicApi(String response) {
//        Log.e("topic_res", response);
//        try {
//            JSONArray jsonArray = new JSONArray(response);
//            for (int i = 0; i < jsonArray.length(); i++) {
//                Log.e("topic_length", String.valueOf(i));
//                JSONObject jsonObject = jsonArray.getJSONObject(i);
//                String ChapterId = jsonObject.getString("ChapterId");
//                String ChapterName = jsonObject.getString("ChapterName");
//                Log.e("ChapterName", ChapterName);
//                String ChapterURLS = jsonObject.getString("ChapterURLS");
//                String CourseId = jsonObject.getString("CourseId");
//                String TopicId = jsonObject.getString("TopicId");
//                String ClassId = jsonObject.getString("ClassId");
//                String StatusId = jsonObject.getString("StatusId");
//                String CreatedById = jsonObject.getString("CreatedById");
//                String ModifiedById = jsonObject.getString("ModifiedById");
//                String CreationDate = jsonObject.getString("CreationDate");
//                String ModificationDate = jsonObject.getString("ModificationDate");
//                SubjChapterTopicListModel subjChapterTopicListModel;
//                subjChapterTopicListModel = new SubjChapterTopicListModel(ChapterId, ChapterName, ChapterURLS, CourseId, TopicId, ClassId, StatusId, CreatedById, ModifiedById, CreationDate, ModificationDate, R.drawable.black_transparent_back);
//                subjectChapterListModelArrayList.add(subjChapterTopicListModel);
//                Log.e("---data", String.valueOf(subjectChapterListModelArrayList));
//            }
//            number_of_videos.setText(jsonArray.length() + " videos");
//            // topicvideoListAdapter = new TopicvideoListAdapter(this, subjectChapterListModelArrayList);
//            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
//            topic_list_recyclerview.setLayoutManager(linearLayoutManager);
//            topic_list_recyclerview.setAdapter(topicvideoListAdapter);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    // }


    private void checkOrientation() {
        if (getResources().getBoolean(R.bool.portrait_only)) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(VideoListActivity.this, SubjectActivity.class);
        startActivity(intent);
       // finish();
    }

    @Override
    public void finish() {
        Intent in = new Intent(VideoListActivity.this, SubjectActivity.class);
        in.putExtra("DataSubject6th", you_Topic);
        //  in.putExtra("LandDetail", landDetailModel);
        in.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        setResult(RESULT_OK, in);
        super.finish();
    }

    private void listner() {
        topic_list_recyclerview.setAdapter(topicvideoListAdapter);
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(VideoListActivity.this, SubjectActivity.class);
                startActivity(intent);
            }
        });

        pratices_Chapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(VideoListActivity.this, PraticesListActivity.class);
                startActivity(intent);
            }
        });
        test_subjcet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(VideoListActivity.this, TestListActivity.class);
                startActivity(intent);
            }
        });

    }

    private void init() {
chapterModelArrayList=new ArrayList<>();
        StrictMode.ThreadPolicy policy = new
                StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        test_subjcet = findViewById(R.id.test_red_cardview);
        pratices_Chapter = findViewById(R.id.practices_red_cardview);

        //videoDetailsArrayList=new ArrayList<>();
        number_of_videos = findViewById(R.id.number_of_video_red);
        back_btn = (Button) findViewById(R.id.back_btn);
        textView = findViewById(R.id.subj_one_name);
        textView.setText(topic_name);
        topic_list_recyclerview = findViewById(R.id.red_theme_topiclist_video);
        // topicvideoListModelArrayList=new ArrayList<>();
        // subjectChapterListModelArrayList = new ArrayList<>();
        background_videolist = findViewById(R.id.background_videolist);
        transparent_videolist = findViewById(R.id.transparent_videolist);
        transparent_videolist.setBackgroundColor(Color.parseColor(transparent_color));
        video_list_background = findViewById(R.id.video_list_background);
        practice = findViewById(R.id.pratice);
        test = findViewById(R.id.test);
        TextColorGradient textColorGradient = new TextColorGradient();
        textColorGradient.getColorTextGradient(practice, base_color_one, base_color_two);
        textColorGradient.getColorTextGradient(test, base_color_one, base_color_two);
        pratice_background = findViewById(R.id.pratice_background);
        test_profile = findViewById(R.id.test_profile);

        themeDynamicAdd();
        getDynamicData();
    }

    private void getDynamicData() {
        //Color.parseColor() method allow us to convert
        // a hexadecimal color string to an integer value (int color)
        int[] colors = {Color.parseColor(base_color_one), Color.parseColor(base_color_two)};

        //create a new gradient color
        GradientDrawable gd = new GradientDrawable(
                GradientDrawable.Orientation.TOP_BOTTOM, colors);

        gd.setCornerRadius(0f);
        //apply the button background to newly created drawable gradient
        video_list_background.setBackground(gd);
        pratice_background.setBackground(gd);
        test_profile.setBackground(gd);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 301 && resultCode == RESULT_OK) {
            try {
                YouTubeTitle rm = (YouTubeTitle) data.getSerializableExtra("DataFromLand");
                Log.e("Test Data", String.valueOf(rm.getChapter()));
//                landDetailModel = (LandDetailModel) data.getSerializableExtra("LandDetail");
//                Log.e("Land Data", landDetailModel.getArea());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void themeDynamicAdd() {

        //Bitmap myImage = getBitmapFromURL(image);
        try {
            Background = getBitmapFromURL(base_image);
            Drawable dr = new BitmapDrawable((Background));
            background_videolist.setBackgroundDrawable(dr);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static Bitmap getBitmapFromURL(String src) {
        try {
            java.net.URL url = new URL(src);
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

    //
    public void getVideoAccordingToPlayListId(String playListId) {
//        String URL = "https://www.googleapis.com/youtube/v3/playlistItems?playlistId="+playListId+"&part=snippet&channelId=UCAfot-yupAQvVqAuZw-Pctg&maxResults=50&rel=0&key=AIzaSyC6S6yg_KDDJOLVfzlO2v0MxvPcTVuR8oU";
//        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
//        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                ArrayList<VideoDetails> videoDetailsArrayList = new ArrayList<>();
//                try {
//                    Log.e("response_youtube", response);
//                    JSONObject jsonObject = new JSONObject(response);
//                    JSONArray jsonArray = jsonObject.getJSONArray("items");
//                    for (int i = 0; i < jsonArray.length(); i++) {
//                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
//                        String kind = jsonObject1.getString("kind");
//                        String etag = jsonObject1.getString("etag");
//                        JSONObject jsonsnippet = jsonObject1.getJSONObject("snippet");
//                        String video_title = jsonsnippet.getString("title");
//                        String video_desc = jsonsnippet.getString("description");
//                        JSONObject jsonthumbnail = jsonsnippet.getJSONObject("thumbnails");
//                        JSONObject highURL = jsonthumbnail.getJSONObject("high");
//                        String thumbURL = highURL.getString("url");
//                        JSONObject jsonresource = jsonsnippet.getJSONObject("resourceId");
//                        String videoId = jsonresource.getString("videoId");
//                        VideoDetails vd = new VideoDetails(video_title,video_desc,thumbURL,videoId);
//                        videoDetailsArrayList.add(vd);
//                    }
//                    number_of_videos.setText(jsonArray.length() + " Videos");
//                    topicvideoListAdapter = new TopicvideoListAdapter(VideoListActivity.this,videoDetailsArrayList);
//                    topic_list_recyclerview.setAdapter(topicvideoListAdapter);
//                    Log.e("PlayListSize Outside", videoDetailsArrayList.size() + "");
//                }catch (Exception e){e.printStackTrace();}
//
//                try {
//
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//
//            }
//
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//
//            }
//        });
//        int socketTimeout = 30000;
//        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
//        stringRequest.setRetryPolicy(policy);
//        requestQueue.add(stringRequest);
//    }
    }
}
