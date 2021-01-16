package com.example.aarambhappdynamictheme.activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.aarambhappdynamictheme.R;
import com.example.aarambhappdynamictheme.model.VideoDetails;
import com.example.aarambhappdynamictheme.model.YouTubePlayList;
import com.example.aarambhappdynamictheme.model.YouTubeTitle;
import com.example.aarambhappdynamictheme.sheardPrefrence.AarambhSharedPreference;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;

public class SplashScreenActivity extends AppCompatActivity {
    String student_name, student_class_id,parent_id;
    public final int SPLASH_DISPLAY_LENGTH = 3000; //splash screen will be shown for 3 seconds
    ArrayList<VideoDetails> videoDetailsArrayList;
    //String URL = "https://www.googleapis.com/youtube/v3/search?part=snippet&channelId=UCAfot-yupAQvVqAuZw-Pctg&maxResults=25&key=AIzaSyDFPE3uXcXmik1L9FEDVHuwcA8NiHw0WBk";
    String pageToken="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        checkOrientation();
        init();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        System.runFinalization();
        Runtime.getRuntime().gc();
        System.gc();
        finish();
    }

    @Override
    protected void onPause() {
// TODO Auto-generated method stub
        super.onPause();
        finish();
    }

    private void init() {
        videoDetailsArrayList=new ArrayList<>();
        student_name = AarambhSharedPreference.loadStudentMobileFromPreference(this);
        student_class_id = AarambhSharedPreference.loadClassIdFromPreference(this);
        parent_id=AarambhSharedPreference.loadParentIdFromPreference(this);

        if (!(student_name.equalsIgnoreCase("NA")) && !(student_class_id.equalsIgnoreCase("NA"))) {
            boolean b = new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    actionBox();
                }
            }, SPLASH_DISPLAY_LENGTH);
        } else if (!(parent_id.equalsIgnoreCase("NA"))) {
            boolean b = new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    actionBox();
                }
            }, SPLASH_DISPLAY_LENGTH);
        }
        else {
            new Handler().postDelayed(new Runnable() {


                @Override
                public void run() {
                    // This method will be executed once the timer is over
                    Intent i = new Intent(SplashScreenActivity.this, LoginActivity.class);
                    startActivity(i);
                    finish();
                }
            }, 3000);
        }
    }

    private void actionBox() {

        if (!student_class_id.equalsIgnoreCase("NA")) {
            try {
                Intent intent = new Intent(SplashScreenActivity.this, ViewLiveClassActivity.class);
                    startActivity(intent);

        } catch (Exception e) {
            e.printStackTrace();
        }
          //  getChannelPlayListVideos();
            //getChannelVideos();
            //getYoutubeDataUrl();
//            switch (student_class_id) {
//                case "1":
//                    Intent intent = new Intent(SpaleshScreenActivity.this, DashBoardSixthStandardActivity.class);
//                    startActivity(intent);
//                    finishAffinity();
//                    break;
//                case "2":
//                    Intent intent1 = new Intent(SpaleshScreenActivity.this, DashBoardSaventhStandardActivity.class);
//                    startActivity(intent1);
//                    finishAffinity();
//                    break;
//                case "3":
//                    Intent intent2 = new Intent(SpaleshScreenActivity.this, DashBoardEighthStandardActivity.class);
//                    startActivity(intent2);
//                    finishAffinity();
//                    break;
//                default:
//                    Intent intent3 = new Intent(SpaleshScreenActivity.this, DashBoardSixthStandardActivity.class);
//                    startActivity(intent3);
//                    finishAffinity();
//                    break;
//            }
        } else if (!parent_id.equalsIgnoreCase("NA")){
            Intent intent = new Intent(SplashScreenActivity.this, ParentDashBoardActivity.class);
            startActivity(intent);
        }
        else {
            Intent intent = new Intent(SplashScreenActivity.this, LoginActivity.class);
            startActivity(intent);
            finishAffinity();
        }
    }

    private void checkOrientation() {
        if (getResources().getBoolean(R.bool.portrait_only)) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
    }

//    public void getChannelVideos(){
//        String URL = "https://www.googleapis.com/youtube/v3/search?part=snippet&channelId=UCAfot-yupAQvVqAuZw-Pctg&maxResults=50&key=AIzaSyDFPE3uXcXmik1L9FEDVHuwcA8NiHw0WBk";
//        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
//        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                try {
//                    Log.e("response_youtube", response);
//                    int count = 0;
//                    JSONObject jsonObject = new JSONObject(response);
//                    pageToken = jsonObject.getString("nextPageToken");
//                    Log.e("Page Token", pageToken);
//                    JSONObject pageInfo = jsonObject.getJSONObject("pageInfo");
//                    String totalResults = pageInfo.getString("totalResults");
//                    String resultsPerPage = pageInfo.getString("resultsPerPage");
//                    JSONArray jsonArray = jsonObject.getJSONArray("items");
//                    for (int i = 0; i < jsonArray.length(); i++) {
//                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
//                        JSONObject jsonVideoId = jsonObject1.getJSONObject("id");
//                        JSONObject jsonsnippet = jsonObject1.getJSONObject("snippet");
//                        JSONObject jsonObjectdefault = jsonsnippet.getJSONObject("thumbnails").getJSONObject("medium");
//                        String video_title = "", videoId = "", channelId = "", playListId = "", playListURL = "", playListTitle = "";
//                        String id_kind = jsonVideoId.getString("kind");
//                        Log.e("JSONObject1", jsonObject1.length() + " : " + jsonVideoId.length());
//                        Log.e("JSONVideoID", jsonVideoId.length() + "");
//
//                        if (id_kind.equalsIgnoreCase("youtube#video")) {
//                            videoId = jsonVideoId.getString("videoId");
//                            video_title = jsonsnippet.getString("title");
//                            String url = jsonObjectdefault.getString("url");
//                            String desc = jsonsnippet.getString("description");
//                            channelId = jsonsnippet.getString("channelId");
//                            VideoDetails vd = new VideoDetails(video_title, desc, url, videoId);
//                            videoDetailsArrayList.add(vd);
//                            Log.e("ArrayListSize Outside", videoDetailsArrayList.size() + ""); //1 - 41
//                        }
//                    }
//
//                    try{
//                        String URL_NEW = "https://www.googleapis.com/youtube/v3/search?pageToken="+pageToken+"&part=snippet&channelId=UCAfot-yupAQvVqAuZw-Pctg&maxResults=50&key=AIzaSyDFPE3uXcXmik1L9FEDVHuwcA8NiHw0WBk";
//                        RequestQueue requestQueue1 = Volley.newRequestQueue(getApplicationContext());
//                        StringRequest stringRequest1 = new StringRequest(Request.Method.GET, URL_NEW, new Response.Listener<String>() {
//                            @Override
//                            public void onResponse(String response) {
//                                Log.e("response_youtube1", response);
//                                JSONObject jsonObject1 = null;
//                                String pageToken1="", prevToken="";
//                                try {
//                                    jsonObject1 = new JSONObject(response);
//                                    pageToken1 = jsonObject1.getString("nextPageToken");
//                                    prevToken = jsonObject1.getString("prevPageToken");
//                                    Log.e("Page Token 1", pageToken1);
//                                    Log.e("Prev Token", prevToken);
//                                    JSONArray jsonArray = jsonObject1.getJSONArray("items");
//                                    for (int i = 0; i < jsonArray.length(); i++) {
//                                        JSONObject jsonObject2 = jsonArray.getJSONObject(i);
//                                        JSONObject jsonVideoId = jsonObject2.getJSONObject("id");
//                                        JSONObject jsonsnippet = jsonObject2.getJSONObject("snippet");
//                                        JSONObject jsonObjectdefault = jsonsnippet.getJSONObject("thumbnails").getJSONObject("medium");
//                                        String video_title = "", videoId = "", channelId = "", playListId = "", playListURL = "", playListTitle = "";
//                                        String id_kind = jsonVideoId.getString("kind");
//                                        Log.e("JSONObject2", jsonObject2.length() + " : " + jsonVideoId.length());
//                                        Log.e("JSONVideoID", jsonVideoId.length() + "");
//
//                                        if (id_kind.equalsIgnoreCase("youtube#video")) {
//                                            videoId = jsonVideoId.getString("videoId");
//                                            video_title = jsonsnippet.getString("title");
//                                            String url = jsonObjectdefault.getString("url");
//                                            String desc = jsonsnippet.getString("description");
//                                            channelId = jsonsnippet.getString("channelId");
//                                            VideoDetails vd = new VideoDetails(video_title, desc, url, videoId);
//                                            videoDetailsArrayList.add(vd);
//                                            Log.e("ArrayListSize Inside", videoDetailsArrayList.size() + ""); //42 - 91
//                                        }
//                                    }
//
//                                } catch (JSONException e) {
//                                    e.printStackTrace();
//                                }
//
//                                try {
//                                    Log.e("Total ArrayList Size",videoDetailsArrayList.size()+"");
//                                    ArrayList<String> subject_ArrayList  = new ArrayList<>();
//                                    ArrayList<String> chapter_ArrayList = new ArrayList<>();
//                                    ArrayList<String> video_ArrayList = new ArrayList<>();
//                                    ArrayList<VideoDetails> video_Details_ArrayList = new ArrayList<>();
//                                    ArrayList<String> video_Id_ArrayList = new ArrayList<>();
//                                    YouTubeTitle ytt6th = null,ytt7th = null,ytt8th = null;
//                                    String className="";
//                                    for(int x =0; x<videoDetailsArrayList.size();x++)
//                                    {
//                                        VideoDetails vd = videoDetailsArrayList.get(x);
//                                        Log.e("X:- ", vd.getVideoName());
//                                        String video_title = vd.getVideoName();
//                                        String title_arr[] = video_title.split("_");
//                                        Log.e("Title Arr:- ", title_arr.length + "");
//                                        className = title_arr[0];
//                                        Log.e("className", className);
//                                        Log.e("Condition", vd.getVideoId());
//                                        String class_id=AarambhSharedPreference.loadClassIdFromPreference(SpaleshScreenActivity.this);
//                                        if (className.equalsIgnoreCase("6th") && class_id.equalsIgnoreCase("1") && vd.getVideoName().contains("6th")) {
//                                            String subject = title_arr[1];
//                                            String chapter = title_arr[2];
//                                            String topic = title_arr[3];
//                                            Log.e("Subject", subject + ":" + chapter +" : "+topic);
//                                            subject_ArrayList.add(subject);
//                                            chapter_ArrayList.add(chapter);
//                                            video_ArrayList.add(video_title);
//                                            video_Details_ArrayList.add(vd);
//                                            //video_ArrayList.add();
//                                            ytt6th = new YouTubeTitle(R.drawable.subj_back_bg);
//                                            ytt6th.setSubject(subject_ArrayList);
//                                            //ytt.setChapter(sixth_Chapter_ArrayList);
//                                            ytt6th.setVideoTitle(video_ArrayList);
//                                        }
//                                        if(className.equalsIgnoreCase("7th")&& class_id.equalsIgnoreCase("2")&& vd.getVideoName().contains("7th")){
//                                            String subject = title_arr[1];
//                                            String chapter = title_arr[2];
//                                            String topic = title_arr[3];
//                                            Log.e("Subject", subject + ":" + chapter +" : "+ topic);
//                                            subject_ArrayList.add(subject);
//                                            chapter_ArrayList.add(chapter);
//                                            video_ArrayList.add(video_title);
//                                            video_Details_ArrayList.add(vd);
//                                            ytt7th = new YouTubeTitle(R.drawable.subj_one_gradient);
//                                            ytt7th.setSubject(subject_ArrayList);
//                                            //ytt.setChapter(sixth_Chapter_ArrayList);
//                                            ytt7th.setVideoTitle(video_ArrayList);
//
//                                        }
//                                        if(className.equalsIgnoreCase("8th")&& class_id.equalsIgnoreCase("3")&& vd.getVideoName().contains("8th")){
//                                            String subject = title_arr[1];
//                                            String chapter = title_arr[2];
//                                            String topic = title_arr[3];
//                                            Log.e("Subject", subject + ":" + chapter +" : "+ topic);
//                                            subject_ArrayList.add(subject);
//                                            chapter_ArrayList.add(chapter);
//                                            video_ArrayList.add(video_title);
//                                            video_Details_ArrayList.add(vd);
//                                            ytt8th = new YouTubeTitle(R.drawable.standard_eigth_bg);
//                                            ytt8th.setSubject(subject_ArrayList);
//                                            //ytt.setChapter(sixth_Chapter_ArrayList);
//                                            ytt8th.setVideoTitle(video_ArrayList);
//                                        }
//                                    }
//                                    HashSet<String> hashSet = new LinkedHashSet<String>(); // to remove duplicate
//                                    hashSet.addAll(subject_ArrayList);
//                                    subject_ArrayList.clear();
//                                    subject_ArrayList.addAll(hashSet);
//                                    for (int f = 0; f < subject_ArrayList.size(); f++) {
//                                        Log.e("New_Sub", hashSet.toString());
//                                    }
//                                    Log.e("classname",className);
//                                    Log.e("classId",AarambhSharedPreference.loadClassIdFromPreference(SpaleshScreenActivity.this));
//
//                                    switch (AarambhSharedPreference.loadClassIdFromPreference(SpaleshScreenActivity.this)) {
//                                        case "1":
//                                            Intent intent = new Intent(SpaleshScreenActivity.this, DashBoardSixthStandardActivity.class);
//                                            Log.e("6thDetails", String.valueOf(ytt6th.getSubject().size()));
//                                            intent.putExtra("6thDetails", ytt6th);
//                                            intent.putExtra("VideoDetails",video_Details_ArrayList);
//                                            startActivity(intent);
//                                            break;
//                                        case "2":
//                                            Intent intent1 = new Intent(SpaleshScreenActivity.this, DashBoardSaventhStandardActivity.class);
//                                            Log.e("7thDetails", String.valueOf(ytt7th.getSubject().size()));
//                                            intent1.putExtra("7thDetails", ytt7th);
//                                            intent1.putExtra("VideoDetails",video_Details_ArrayList);
//                                            startActivity(intent1);
//                                            break;
//                                        case "3":
//                                            Intent intent2 = new Intent(SpaleshScreenActivity.this, DashBoardEighthStandardActivity.class);
//                                            Log.e("8thDetails", String.valueOf(ytt8th.getSubject().size()));
//                                            intent2.putExtra("8thDetails", ytt8th);
//                                            intent2.putExtra("VideoDetails",video_Details_ArrayList);
//                                            startActivity(intent2);
//                                            break;
//                                        default:
//                                            Intent intent3 = new Intent(SpaleshScreenActivity.this, DashBoardSixthStandardActivity.class);
//                                            intent3.putExtra("6thDetails", ytt6th);
//                                            intent3.putExtra("VideoDetails",video_Details_ArrayList);
//                                            startActivity(intent3);
//                                            break;
//                                    }
//
//                                } catch (Exception e) {
//                                    e.printStackTrace();
//                                }
//
//                            }
//
//                        }, new Response.ErrorListener() {
//                            @Override
//                            public void onErrorResponse(VolleyError error) {
//
//                            }
//                        });
//                        int socketTimeout = 30000;
//                        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
//                        stringRequest1.setRetryPolicy(policy);
//                        requestQueue1.add(stringRequest1);
//                    }catch (Exception e){e.printStackTrace();}
//                }
//                catch (Exception e){e.printStackTrace();}
//            }
//
//
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
//    private void getYoutubeDataUrl() {
//        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
//        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                ArrayList<VideoDetails> videoDetailsArrayList = new ArrayList<>();
//                try {
//                    Log.e("response_youtube", response);
//                    int count = 0;
//
//                    JSONObject jsonObject = new JSONObject(response);
//                    JSONArray jsonArray = jsonObject.getJSONArray("items");
//                    for (int i = 0; i < jsonArray.length(); i++) {
//                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
//                        JSONObject jsonVideoId = jsonObject1.getJSONObject("id");
//                        JSONObject jsonsnippet = jsonObject1.getJSONObject("snippet");
//                        JSONObject jsonObjectdefault = jsonsnippet.getJSONObject("thumbnails").getJSONObject("medium");
//                        String video_title = "", videoId = "", channelId = "", playListId = "";
//                        String id_kind = jsonVideoId.getString("kind");
//                        Log.e("JSONObject1", jsonObject1.length() + " : " + jsonVideoId.length());
//                        Log.e("JSONVideoID", jsonVideoId.length() + "");
//
////                        if (id_kind.equalsIgnoreCase("youtube#playlist")) {
////                            playListId = jsonVideoId.getString("playlistId");
////                            //videoDetails.setVideoId(jsonVideoId.getString("playlistId"));
////                            //videoDetails.setURL(jsonObjectdefault.getString("url"));
////                            //videoDetails.setVideoName(jsonsnippet.getString("title"));
////                            String title = jsonsnippet.getString("title");
////                            Log.e("PlayList Title", title);
////
////                        }
//
//                        if (id_kind.equalsIgnoreCase("youtube#video")) {
//                            videoId = jsonVideoId.getString("videoId");
//                            video_title = jsonsnippet.getString("title");
//                            String url = jsonObjectdefault.getString("url");
//                            String desc = jsonsnippet.getString("description");
//                            channelId = jsonsnippet.getString("channelId");
//                            VideoDetails vd = new VideoDetails(video_title, desc, url, videoId);
//                            videoDetailsArrayList.add(vd);
//                            Log.e("ArrayList Size in If", videoDetailsArrayList.size() + ""); //19
//                            //count++;
//                            //Log.e("Count", count + "");
//                        }
//                    }
//
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//                try {
//                    Log.e("ArrayList Size",videoDetailsArrayList.size()+""); //0
//                    ArrayList<String> subject_ArrayList ;
//                    ArrayList<String> chapter_ArrayList;
//                    ArrayList<String> video_ArrayList;
//                    subject_ArrayList = new ArrayList<>();
//                    chapter_ArrayList = new ArrayList<>();
//                    video_ArrayList = new ArrayList<>();
//                    ArrayList<VideoDetails> video_Details_ArrayList = new ArrayList<>();
//                    ArrayList<String> video_Id_ArrayList = new ArrayList<>();
//                    YouTubeTitle ytt6th = null,ytt7th = null,ytt8th = null;
//                    String className="";
//                    for(int x =0; x<videoDetailsArrayList.size();x++) {
//                        VideoDetails vd = videoDetailsArrayList.get(x);
//                        Log.e("X:- ", vd.getVideoName());
//                        String video_title = vd.getVideoName();
//                        String title_arr[] = video_title.split("_");
//                        Log.e("Title Arr:- ", title_arr.length + "");
//                        className = title_arr[0];
//                        Log.e("className", className);
//                        Log.e("Condition", vd.getVideoId());
//                        String class_id=AarambhSharedPreference.loadClassIdFromPreference(SpaleshScreenActivity.this);
//                        if (className.equalsIgnoreCase("6th") && class_id.equalsIgnoreCase("1") && vd.getVideoName().contains("6th")) {
//                            String subject = title_arr[1];
//                            String chapter = title_arr[2];
//                            String topic = title_arr[3];
//                            Log.e("Subject", subject + ":" + chapter +" : "+ topic);
//                            subject_ArrayList.add(subject);
//                            chapter_ArrayList.add(chapter);
//                            video_ArrayList.add(video_title);
//                            video_Details_ArrayList.add(vd);
//                            //video_ArrayList.add();
//                            ytt6th = new YouTubeTitle(R.drawable.subj_back_bg);
//                            ytt6th.setSubject(subject_ArrayList);
//                            //ytt.setChapter(sixth_Chapter_ArrayList);
//                            ytt6th.setVideoTitle(video_ArrayList);
//                        }
//                        else if(className.equalsIgnoreCase("7th")&& class_id.equalsIgnoreCase("2")&& vd.getVideoName().contains("7th")){
//                          String subject = title_arr[1];
//                            String chapter = title_arr[2];
//                            String topic = title_arr[3];
//                            Log.e("Subject", subject + ":" + chapter +" : "+ topic);
//                            subject_ArrayList.add(subject);
//                            chapter_ArrayList.add(chapter);
//                            video_ArrayList.add(video_title);
//                            video_Details_ArrayList.add(vd);
//                            ytt7th = new YouTubeTitle(R.drawable.subj_one_gradient);
//                            ytt7th.setSubject(subject_ArrayList);
//                            //ytt.setChapter(sixth_Chapter_ArrayList);
//                            ytt7th.setVideoTitle(video_ArrayList);
//
//                        }
//                        else if(className.equalsIgnoreCase("8th")&& class_id.equalsIgnoreCase("3")&& vd.getVideoName().contains("8th")){
//                            String subject = title_arr[1];
//                            String chapter = title_arr[2];
//                            String topic = title_arr[3];
//                            Log.e("Subject", subject + ":" + chapter +" : "+ topic);
//                            subject_ArrayList.add(subject);
//                            chapter_ArrayList.add(chapter);
//                            video_ArrayList.add(video_title);
//                            video_Details_ArrayList.add(vd);
//                            ytt8th = new YouTubeTitle(R.drawable.standard_eigth_bg);
//                            ytt8th.setSubject(subject_ArrayList);
//                            //ytt.setChapter(sixth_Chapter_ArrayList);
//                            ytt8th.setVideoTitle(video_ArrayList);
////
//                        }
//                    }
//                    HashSet<String> hashSet = new LinkedHashSet<String>(); // to remove duplicate
//                    hashSet.addAll(subject_ArrayList);
//                    subject_ArrayList.clear();
//                    subject_ArrayList.addAll(hashSet);
//                    for (int f = 0; f < subject_ArrayList.size(); f++) {
//                        Log.e("New_Sub", hashSet.toString());
//                    }
//                    Log.e("classname",className);
//                    Log.e("classId",AarambhSharedPreference.loadClassIdFromPreference(SpaleshScreenActivity.this));
//
//                    switch (AarambhSharedPreference.loadClassIdFromPreference(SpaleshScreenActivity.this)) {
//                        case "1":
//                            Intent intent = new Intent(SpaleshScreenActivity.this, DashBoardSixthStandardActivity.class);
//                            Log.e("6thDetails", String.valueOf(ytt6th.getSubject().size()));
//                            intent.putExtra("6thDetails", ytt6th);
//                            intent.putExtra("VideoDetails",video_Details_ArrayList);
//                            startActivity(intent);
//                            break;
//                        case "2":
//                            Intent intent1 = new Intent(SpaleshScreenActivity.this, DashBoardSaventhStandardActivity.class);
//                            Log.e("7thDetails", String.valueOf(ytt7th.getSubject().size()));
//                            intent1.putExtra("7thDetails", ytt7th);
//                            intent1.putExtra("VideoDetails",video_Details_ArrayList);
//                            startActivity(intent1);
//                            break;
//                        case "3":
//                            Intent intent2 = new Intent(SpaleshScreenActivity.this, DashBoardEighthStandardActivity.class);
//                            Log.e("8thDetails", String.valueOf(ytt8th.getSubject().size()));
//                            intent2.putExtra("8thDetails", ytt8th);
//                            intent2.putExtra("VideoDetails",video_Details_ArrayList);
//                            startActivity(intent2);
//                            break;
//                        default:
//                            Intent intent3 = new Intent(SpaleshScreenActivity.this, DashBoardSixthStandardActivity.class);
//                            intent3.putExtra("6thDetails", ytt6th);
//                            intent3.putExtra("VideoDetails",video_Details_ArrayList);
//                            startActivity(intent3);
//                            break;
//                    }
//
//                } catch (Exception e) {
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


    public void getChannelPlayListVideos() {
        //String URL = "https://www.googleapis.com/youtube/v3/search?part=snippet&channelId=UCAfot-yupAQvVqAuZw-Pctg&maxResults=50&key=AIzaSyDFPE3uXcXmik1L9FEDVHuwcA8NiHw0WBk";
        String URL = "https://www.googleapis.com/youtube/v3/playlists?part=snippet&channelId=UCAfot-yupAQvVqAuZw-Pctg&maxResults=50&key=AIzaSyDFPE3uXcXmik1L9FEDVHuwcA8NiHw0WBk";
        //String URL = "https://www.googleapis.com/youtube/v3/playlists?part=snippet&channelId=UCAfot-yupAQvVqAuZw-Pctg&maxResults=50&key=AIzaSyC6S6yg_KDDJOLVfzlO2v0MxvPcTVuR8oU";
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                ArrayList<YouTubePlayList> playListArrayList = new ArrayList<>();
                try {
                    Log.e("response_youtube", response);

                    int count = 0;
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("items");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        String kind = jsonObject1.getString("kind");
                        String etag = jsonObject1.getString("etag");
                        String id = jsonObject1.getString("id");
                        JSONObject jsonsnippet = jsonObject1.getJSONObject("snippet");

                        String playList_title = jsonsnippet.getString("title");
                        JSONObject jsonthumbnail = jsonsnippet.getJSONObject("thumbnails");
                        JSONObject highURL = jsonthumbnail.getJSONObject("high");
                        String thumbURL = highURL.getString("url");
                        YouTubePlayList ypl = new YouTubePlayList(id, playList_title, thumbURL);
                        playListArrayList.add(ypl);
                    }

                    Log.e("PlayListSize Outside", playListArrayList.size() + "");
                } catch (Exception e) {
                    e.printStackTrace();
                }

                try {
                    Log.e("Total ArrayList Size", playListArrayList.size() + "");
                    ArrayList<String> subject_ArrayList = new ArrayList<>();
                    ArrayList<String> chapter_ArrayList = new ArrayList<>();
                    ArrayList<String> video_ArrayList = new ArrayList<>();
                    ArrayList<VideoDetails> video_Details_ArrayList = new ArrayList<>();
                    ArrayList<String> video_Id_ArrayList = new ArrayList<>();
                    YouTubeTitle ytt6th = null, ytt7th = null, ytt8th = null;
                    String className = "";
                    for (int x = 0; x < playListArrayList.size(); x++) {
                        YouTubePlayList vd = playListArrayList.get(x);
                        Log.e("X:- ", vd.getPlayListTitle());
                        String video_title = vd.getPlayListTitle();
                        String title_arr[] = video_title.split("_");
                        Log.e("Title Arr:- ", title_arr.length + "");
                        className = title_arr[0];
                        Log.e("className", className);

                        String class_id = AarambhSharedPreference.loadClassIdFromPreference(SplashScreenActivity.this);
                        if (className.equalsIgnoreCase("6th") && class_id.equalsIgnoreCase("1") && vd.getPlayListTitle().contains("6th")) {
                            String subject = title_arr[1].replace(" ", "");
                            //String chapter = title_arr[2];
                            //String topic = title_arr[3];
                            Log.e("Subject", subject);
                            subject_ArrayList.add(subject);
                            //chapter_ArrayList.add(chapter);
                            //video_ArrayList.add(video_title);
                            //video_Details_ArrayList.add(vd);
                            //video_ArrayList.add();
                            ytt6th = new YouTubeTitle(R.drawable.subj_back_bg);
                            ytt6th.setSubject(subject_ArrayList);
                            //ytt.setChapter(sixth_Chapter_ArrayList);
                            //ytt6th.setVideoTitle(video_ArrayList);
                        }
                        if (className.equalsIgnoreCase("7th") && class_id.equalsIgnoreCase("2") && vd.getPlayListTitle().contains("7th")) {
                            String subject = title_arr[1].replace(" ", "");
                            Log.e("Subject", subject);
                            subject_ArrayList.add(subject);
                            ytt7th = new YouTubeTitle(R.drawable.subj_one_gradient);
                            ytt7th.setSubject(subject_ArrayList);
                            //ytt.setChapter(sixth_Chapter_ArrayList);
                            // ytt7th.setVideoTitle(video_ArrayList);

                        }
                        if (className.equalsIgnoreCase("8th") && class_id.equalsIgnoreCase("3") && vd.getPlayListTitle().contains("8th")) {
                            String subject = title_arr[1].replace(" ", "");
                            Log.e("Subject", subject);
                            subject_ArrayList.add(subject);
                            ytt8th = new YouTubeTitle(R.drawable.standard_eigth_bg);
                            ytt8th.setSubject(subject_ArrayList);
                            //ytt.setChapter(sixth_Chapter_ArrayList);
                            // ytt8th.setVideoTitle(video_ArrayList);
                        }
                    }
                    HashSet<String> hashSet = new LinkedHashSet<String>(); // to remove duplicate
                    hashSet.addAll(subject_ArrayList);
                    subject_ArrayList.clear();
                    subject_ArrayList.addAll(hashSet);
                    for (int f = 0; f < subject_ArrayList.size(); f++) {
                        Log.e("New_Sub", hashSet.toString());
                    }
                    Log.e("classname", className);
                    Log.e("classId", AarambhSharedPreference.loadClassIdFromPreference(SplashScreenActivity.this));

                    switch (AarambhSharedPreference.loadClassIdFromPreference(SplashScreenActivity.this)) {
                        case "1":

                            Intent intent = new Intent(SplashScreenActivity.this, ViewLiveClassActivity.class);
                            //Log.e("6thDetails", String.valueOf(ytt6th.getSubject().size()));
                            intent.putExtra("6thDetails", ytt6th);
                            intent.putExtra("PlayListDetails", playListArrayList);
                            startActivity(intent);

                            break;
                        case "2":

                            Intent intent1 = new Intent(SplashScreenActivity.this, ViewLiveClassActivity.class);
                            intent1.putExtra("7thDetails", ytt7th);
                            intent1.putExtra("PlayListDetails", playListArrayList);
                            startActivity(intent1);
                            break;
                        case "3":
                            Intent intent2 = new Intent(SplashScreenActivity.this, ViewLiveClassActivity.class);
                            intent2.putExtra("8thDetails", ytt8th);
                            intent2.putExtra("PlayListDetails", playListArrayList);
                            startActivity(intent2);

                            break;
                        default:

                            Intent intent3 = new Intent(SplashScreenActivity.this, ViewLiveClassActivity.class);
                            intent3.putExtra("6thDetails", ytt6th);
                            intent3.putExtra("PlayListDetails", playListArrayList);
                            startActivity(intent3);
                            break;
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        int socketTimeout = 30000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        requestQueue.add(stringRequest);
    }

}
