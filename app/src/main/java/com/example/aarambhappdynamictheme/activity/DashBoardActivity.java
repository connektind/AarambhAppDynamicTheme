package com.example.aarambhappdynamictheme.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Handler;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.aarambhappdynamictheme.adapter.AdapterLive;
import com.example.aarambhappdynamictheme.model.CourseModel;
import com.example.aarambhappdynamictheme.model.LiveData;
import com.example.aarambhappdynamictheme.model.SchoolModel;
import com.example.aarambhappdynamictheme.sheardPrefrence.AarambhThemeSharedPrefreence;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.navigation.NavigationView;

import androidx.cardview.widget.CardView;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.StrictMode;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;
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
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.aarambhappdynamictheme.R;
import com.example.aarambhappdynamictheme.adapter.RecommandationVideoAdapter;
import com.example.aarambhappdynamictheme.adapter.SubjectNameAdapter;
import com.example.aarambhappdynamictheme.model.RecommandationVideoModel;
import com.example.aarambhappdynamictheme.model.SubjectNameModel;
import com.example.aarambhappdynamictheme.model.VideoDetails;
import com.example.aarambhappdynamictheme.model.YouTubePlayList;
import com.example.aarambhappdynamictheme.model.YouTubeTitle;
import com.example.aarambhappdynamictheme.sheardPrefrence.AarambhSharedPreference;
import com.example.aarambhappdynamictheme.textGradient.CircleTransform;
import com.example.aarambhappdynamictheme.textGradient.TextColorGradient;
import com.example.aarambhappdynamictheme.util.CommonUtilities;
import com.example.aarambhappdynamictheme.util.Global;
import com.example.aarambhappdynamictheme.util.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.example.aarambhappdynamictheme.util.Global.urlProfileImg;
import static com.example.aarambhappdynamictheme.util.Global.urlStudentImg;

public class DashBoardActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    RecyclerView recommend_recyclerview, subject_name_recycler_view, upcoming_recyclerview, live_recyclerview;
    RecommandationVideoAdapter recommandationVideoAdapter;
    ArrayList<RecommandationVideoModel> recommandationVideoModelArrayList;
    SubjectNameAdapter subjectNameAdapter;

    ArrayList<SubjectNameModel> subjectNameModelArrayList;
    ArrayList<YouTubeTitle> youtube_subjectNameModelArrayList;
    SubjectNameModel subjectNameModel;
    TextView hello_word, std_name_word, ready_to_learn_word,
            view_all_recommend_video, student_name_red, upcoming_viewall_red, recom_viewall_red;
    LinearLayout scrollView;
    ImageView menu_nav, img_arrow;
    String class_id;
    ProgressDialog progressdialog;
    ImageView imgProfile, tab_profile_picture;

    DrawerLayout drawer;
    private static final String TAG_HOME = "home";
    public static String CURRENT_TAG = TAG_HOME;
    NavigationView navigationView;
    private View navHeader;
    ImageView navigation_cancle_btn, notification_bell;
    TextView logout, student_name_navdra, bookmark, notification,student_class,
            shareapp, parent_connect, contact_us, subscribe_now, terms_condition;
    ArrayList<VideoDetails> videoDetailsArrayList;
    VideoDetails videoDetails;
    ArrayList<String> you_Subject, you_chapter, you_video;
    boolean doubleBackToExitPressedOnce = false;
    //theme dynamic try
    LinearLayout background_dashboard, transprent_dahboard, dashbord_nav_transrent,
            dashborad_nav_profile, edit_dashboard_nav;
    String base_image, transparent_color, base_color_one, base_color_two;
    Bitmap Background, menu_bitmap;
    Toolbar toolbar = null;
    ArrayList<SchoolModel> schoolModelSchoolList;
    CardView liveCardView;
    ArrayList<SchoolModel> liveDataArrayList;

    ArrayList<CourseModel> courseModelArrayList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);
        class_id = AarambhSharedPreference.loadClassIdFromPreference(this);
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        base_image = AarambhThemeSharedPrefreence.loadBaseImageFromPreference(this);
        transparent_color = AarambhThemeSharedPrefreence.loadBaseImageTransparentFromPreference(this);
        Log.e("transprent", transparent_color);

        base_color_one = AarambhThemeSharedPrefreence.loadBaseColorOneFromPreference(this);
        base_color_two = AarambhThemeSharedPrefreence.loadBaseColorTwoFromPreference(this);
        Log.e("iamge_dash", AarambhSharedPreference.loadStudentProfileFromPreference(this));
        checkOrientation();
        try {
            Log.e("profile", String.valueOf(imgProfile));
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.e("6thclasswalipostion", class_id);
        init();
        listner();
        loadNavHeader();
        //getSubjectsApi();
        //getYoutubeDatacall();
        getStudentCourses();

        try{
            liveDataArrayList = new ArrayList<>();
            Intent intent = getIntent();

            SchoolModel sc = (SchoolModel) intent.getSerializableExtra("SchoolDetail");
            liveDataArrayList.add(sc);
            AdapterLive al = new AdapterLive(this, liveDataArrayList);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
            live_recyclerview.setLayoutManager(linearLayoutManager);
            live_recyclerview.setAdapter(al);


        }catch (Exception e){e.printStackTrace();}
//        if (AarambhSharedPreference.loadClassIdFromPreference(this).equalsIgnoreCase("3")) {
//            try {
//                YouTubeTitle sixth_Details = (YouTubeTitle) getIntent().getSerializableExtra("8thDetails");
//                //ArrayList<VideoDetails> getVideoDetails = (ArrayList<VideoDetails>) getIntent().getSerializableExtra("VideoDetails");
//                ArrayList<YouTubePlayList> getPlayListDetails = (ArrayList<YouTubePlayList>) getIntent().getSerializableExtra("PlayListDetails");
//                you_Subject = new ArrayList<>();
//                you_chapter = new ArrayList<>();
//                you_video = new ArrayList<>();
//                Log.e("GetSubject", sixth_Details.getSubject().size() + "");
//                for (int x = 0; x < sixth_Details.getSubject().size(); x++) {
//                    you_Subject = (ArrayList<String>) sixth_Details.getSubject().clone();
//                }
//                subjectNameAdapter = new SubjectNameAdapter(this, you_Subject, getPlayListDetails);
//                liveDataArrayList = new ArrayList<>();
//                Intent intent = getIntent();
////                Bundle args = intent.getBundleExtra("BUNDLE8th");
////                schoolModelSchoolList = (ArrayList<SchoolModel>) args.getSerializable("ARRAYLIST");
////                for (int i=0;i<schoolModelSchoolList.size();i++) {
////                    SchoolModel sc = schoolModelSchoolList.get(i);
////                    Log.e("Data6th", sc.getSchoolName() + "");
////                    LiveData live = new LiveData(sc.getSchoolLogo());
////                    liveDataArrayList.add(live);
////                }
//                SchoolModel sc = (SchoolModel) intent.getSerializableExtra("SchoolDetail8th");
//                liveDataArrayList.add(sc);
//                AdapterLive al = new AdapterLive(this, liveDataArrayList);
//                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
//                live_recyclerview.setLayoutManager(linearLayoutManager);
//                live_recyclerview.setAdapter(al);
//
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        } else if (AarambhSharedPreference.loadClassIdFromPreference(this).equalsIgnoreCase("2")) {
//            try {
//                YouTubeTitle sixth_Details = (YouTubeTitle) getIntent().getSerializableExtra("7thDetails");
//                //ArrayList<VideoDetails> getVideoDetails = (ArrayList<VideoDetails>) getIntent().getSerializableExtra("VideoDetails");
//                ArrayList<YouTubePlayList> getPlayListDetails = (ArrayList<YouTubePlayList>) getIntent().getSerializableExtra("PlayListDetails");
//
//                you_Subject = new ArrayList<>();
//                you_chapter = new ArrayList<>();
//                you_video = new ArrayList<>();
//                Log.e("GetSubject", sixth_Details.getSubject().size() + "");
//                for (int x = 0; x < sixth_Details.getSubject().size(); x++) {
//                    you_Subject = (ArrayList<String>) sixth_Details.getSubject().clone();
//                }
//                subjectNameAdapter = new SubjectNameAdapter(this, you_Subject, getPlayListDetails);
//                liveDataArrayList = new ArrayList<>();
//                Intent intent = getIntent();
////                Bundle args = intent.getBundleExtra("BUNDLE7th");
////                schoolModelSchoolList = (ArrayList<SchoolModel>) args.getSerializable("ARRAYLIST");
////                for (int i=0;i<schoolModelSchoolList.size();i++) {
////                    SchoolModel sc = schoolModelSchoolList.get(i);
////                    Log.e("Data7th", sc.getSchoolName() + "");
////                    LiveData live = new LiveData(sc.getSchoolLogo());
////                    liveDataArrayList.add(live);
////                }
//                SchoolModel sc = (SchoolModel) intent.getSerializableExtra("SchoolDetail7th");
//                liveDataArrayList.add(sc);
//                AdapterLive al = new AdapterLive(this, liveDataArrayList);
//                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
//                live_recyclerview.setLayoutManager(linearLayoutManager);
//                live_recyclerview.setAdapter(al);

//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        } else if (AarambhSharedPreference.loadClassIdFromPreference(this).equalsIgnoreCase("1")) {
//            try {
//                YouTubeTitle sixth_Details = (YouTubeTitle) getIntent().getSerializableExtra("6thDetails");
//                //ArrayList<VideoDetails> getVideoDetails = (ArrayList<VideoDetails>) getIntent().getSerializableExtra("VideoDetails");
//                ArrayList<YouTubePlayList> getPlayListDetails = (ArrayList<YouTubePlayList>) getIntent().getSerializableExtra("PlayListDetails");
//                you_Subject = new ArrayList<>();
//                you_chapter = new ArrayList<>();
//                you_video = new ArrayList<>();
//                Log.e("GetSubject", sixth_Details.getSubject().size() + "");
//                for (int x = 0; x < sixth_Details.getSubject().size(); x++) {
//                    you_Subject = (ArrayList<String>) sixth_Details.getSubject().clone();
//                }
//                subjectNameAdapter = new SubjectNameAdapter(this, you_Subject, getPlayListDetails);
//                liveDataArrayList = new ArrayList<>();
//                Intent intent = getIntent();
////                Bundle args = intent.getBundleExtra("BUNDLE6th");
////                schoolModelSchoolList = (ArrayList<SchoolModel>) args.getSerializable("ARRAYLIST");
////                for (int i=0;i<schoolModelSchoolList.size();i++) {
////                    SchoolModel sc = schoolModelSchoolList.get(i);
////                    Log.e("Data6th", sc.getSchoolName() + "");
////                    LiveData live = new LiveData(sc.getSchoolLogo());
////                    liveDataArrayList.add(live);
////                }
//                SchoolModel sc = (SchoolModel) intent.getSerializableExtra("SchoolDetail6th");
//                liveDataArrayList.add(sc);
//                AdapterLive al = new AdapterLive(this, liveDataArrayList);
//                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
//                live_recyclerview.setLayoutManager(linearLayoutManager);
//                live_recyclerview.setAdapter(al);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
        //}
        try {
            if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                GridLayoutManager manager = new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false);
                subject_name_recycler_view.setLayoutManager(manager);
            } else {
                LinearLayoutManager manager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
                subject_name_recycler_view.setLayoutManager(manager);
            }
            subject_name_recycler_view.setAdapter(subjectNameAdapter);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

//    private void getYoutubeDatacall() {
//        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
//        StringRequest stringRequest=new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                try {
//                   // Log.e("response_youtube",response);
//                    JSONObject jsonObject=new JSONObject(response);
//                    JSONArray jsonArray=jsonObject.getJSONArray("items");
//                    for(int i=0;i<jsonArray.length();i++){
//                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
//                        JSONObject jsonVideoId=jsonObject1.getJSONObject("id");
//                  //      Log.e("jsonVideoId", String.valueOf(jsonVideoId));
//                        JSONObject jsonsnippet= jsonObject1.getJSONObject("snippet");
//                    //    Log.e("jsonsnippet", String.valueOf(jsonsnippet));
//                        JSONObject jsonObjectdefault = jsonsnippet.getJSONObject("thumbnails").getJSONObject("medium");
//                     //   Log.e("jsonObjectdefault", String.valueOf(jsonObjectdefault));
//                        VideoDetails videoDetails=new VideoDetails();
//                        //String videoid=jsonVideoId.getString("videoId");
//                        //Log.e("newVideoUrl"," New Video Id" +videoid);
//                        videoDetails.setURL(jsonObjectdefault.getString("url"));
//                        Log.e("videoDetails.getURL()",videoDetails.getURL());
//                        videoDetails.setVideoName(jsonsnippet.getString("title"));
//                        // subjectNameModel.setCourseName(jsonsnippet.getString("title"));
//                        Log.e("videoDetails.getVideoName()",videoDetails.getVideoName());
//                        String videoname=videoDetails.getVideoName();
//                        //6th_Maths_Chapter 6_ Part 1 Introduction to Integers
//
//                     try {
//                         String[] parts = videoname.split("_"); // escape .
//                         String part1 = parts[0];
//                         Log.e("part1",part1);
//                         String part2 = parts[1];
//                         Log.e("part2",part2);
//                     }catch (Exception e){
//                         e.printStackTrace();
//                     }
//
//                        videoDetails.setVideoDesc(jsonsnippet.getString("description"));
//                        Log.e("videoDetails.getVideoDesc()",videoDetails.getVideoDesc());
//
//                       // videoDetails.setVideoId(videoid);
//                        //subjectNameModel.setSubj_back_bg(R.drawable.subj_back_bg);
////                        videoDetailsArrayList.add(videoDetails);
////                        subjectNameModelArrayList.add(subjectNameModel);
//                    }
//                  //  subjectNameAdapter=new SubjectNameAdapter(DashBoardSixthStandardActivity.this,videoDetailsArrayList);
//                    if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
//                        GridLayoutManager manager = new GridLayoutManager(DashBoardSixthStandardActivity.this, 2, GridLayoutManager.VERTICAL, false);
//                        subject_name_recycler_view.setLayoutManager(manager);
//                    }else {
//                        LinearLayoutManager manager=new LinearLayoutManager(DashBoardSixthStandardActivity.this,RecyclerView.HORIZONTAL,false);
//                        subject_name_recycler_view.setLayoutManager(manager);
//
//                    }
//                    subject_name_recycler_view.setAdapter(subjectNameAdapter);
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

    private void loadNavHeader() {
        if (AarambhSharedPreference.loadStudentProfileFromPreference(this).equals("NA")) {
            Glide.with(this).load(urlProfileImg)
                    .crossFade()
                    .thumbnail(0.5f)
                    .bitmapTransform(new CircleTransform(this))
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(imgProfile);
        } else {
            Glide.with(this).load(urlStudentImg + AarambhSharedPreference.loadStudentProfileFromPreference(this))
                    .crossFade()
                    .thumbnail(0.5f)
                    .bitmapTransform(new CircleTransform(this))
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(imgProfile);
        }

//        // Loading profile image
//        Glide.with(this).load(urlProfileImg)
//                .crossFade()
//                .thumbnail(0.5f)
//                .bitmapTransform(new CircleTransform(this))
//                .diskCacheStrategy(DiskCacheStrategy.ALL)
//                .into(imgProfile);

        // showing dot next to notifications label
        //navigationView.getMenu().getItem(3).setActionView(R.layout.menu_dot);
    }

    private void checkOrientation() {
        if (getResources().getBoolean(R.bool.portrait_only)) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
    }

    private void getSubjectsApi() {
        if (!
                CommonUtilities.isOnline(this)) {
            Toast.makeText(DashBoardActivity.this, "Please On Your Internet Connection", Toast.LENGTH_LONG).show();
            return;
        }
        String url = Global.WEBBASE_URL + "getCourseByClassId?ClassId=" + class_id;
        final String string_json = "Result";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                String res = response.toString();
                parseResponseCourseApi(res, progressdialog);
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
                        parseResponseCourseApi(response.toString(), progressdialog);
                    } catch (UnsupportedEncodingException e1) {
                        // Couldn't properly decode data to string
                        e1.printStackTrace();
                    }
                }

            }
        }) {
            @Override
            public Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<String, String>();
                params.put("ClassId", class_id);
                return params;
            }

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
                headers.put("Authorization", "Bearer " + AarambhSharedPreference.loadUserTokenFromPreference(DashBoardActivity.this));
                return headers;
            }
        };
        VolleySingleton.getInstance(DashBoardActivity.this).addToRequestQueue(stringRequest, string_json);
    }

    private void parseResponseCourseApi(String response, ProgressDialog progressdialog) {
        Log.e("class_res", response);
        try {
            JSONArray jsonArray = new JSONArray(response);
            for (int i = 0; i < jsonArray.length(); i++) {
                Log.e("class_lenth", String.valueOf(i));
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String CourseId = jsonObject.getString("CourseId");
                Log.e("id", CourseId);
                String CourseName = jsonObject.getString("CourseName");
                Log.e("course_name", CourseName);
                String CourseDescription = jsonObject.getString("CourseDescription");
                String CourseOtherDetails = jsonObject.getString("CourseOtherDetails");
                String CourseImage = jsonObject.getString("CourseImage");
                String ClassId = jsonObject.getString("ClassId");
                String StatusId = jsonObject.getString("StatusId");
                String CreatedById = jsonObject.getString("CreatedById");
                String ModifiedById = jsonObject.getString("ModifiedById");
                String CreationDate = jsonObject.getString("CreationDate");
                String ModificationDate = jsonObject.getString("ModificationDate");
                subjectNameModel = new SubjectNameModel(CourseId, CourseName, CourseDescription, CourseOtherDetails, CourseImage, ClassId, StatusId, CreatedById, ModifiedById, CreationDate, ModificationDate, R.drawable.subj_back_bg);
                subjectNameModelArrayList.add(subjectNameModel);
            }
            //  subjectNameAdapter=new SubjectNameAdapter(this,subjectNameModelArrayList);
            if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                GridLayoutManager manager = new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false);
                subject_name_recycler_view.setLayoutManager(manager);
            } else {
                LinearLayoutManager manager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
                subject_name_recycler_view.setLayoutManager(manager);

            }
            subject_name_recycler_view.setAdapter(subjectNameAdapter);
//

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void listner() {
//        subjectNameAdapter=new SubjectNameAdapter(this,subjectNameModelArrayList);
//        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
//            GridLayoutManager manager = new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false);
//            subject_name_recycler_view.setLayoutManager(manager);
//        }else {
//            LinearLayoutManager manager=new LinearLayoutManager(this,RecyclerView.HORIZONTAL,false);
//            subject_name_recycler_view.setLayoutManager(manager);
//
//        }
//        subject_name_recycler_view.setAdapter(subjectNameAdapter);
//

        recommandationVideoModelArrayList.add(new RecommandationVideoModel("https://ak9.picdn.net/shutterstock/videos/12454199/thumb/10.jpg", R.drawable.black_transparent_back, "https://www.youtube.com/watch?v=V5So0Ah4Zsc"));
        recommandationVideoModelArrayList.add(new RecommandationVideoModel("https://ak9.picdn.net/shutterstock/videos/12454199/thumb/10.jpg", R.drawable.black_transparent_back, "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/TearsOfSteel.mp4"));
        recommandationVideoModelArrayList.add(new RecommandationVideoModel("https://ak9.picdn.net/shutterstock/videos/12454199/thumb/10.jpg", R.drawable.black_transparent_back, "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/SubaruOutbackOnStreetAndDirt.mp4"));
        recommandationVideoModelArrayList.add(new RecommandationVideoModel("https://ak9.picdn.net/shutterstock/videos/12454199/thumb/10.jpg", R.drawable.black_transparent_back, "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/TearsOfSteel.mp4"));
        recommandationVideoModelArrayList.add(new RecommandationVideoModel("https://ak9.picdn.net/shutterstock/videos/12454199/thumb/10.jpg", R.drawable.black_transparent_back, "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/SubaruOutbackOnStreetAndDirt.mp4"));
        recommandationVideoModelArrayList.add(new RecommandationVideoModel("https://ak9.picdn.net/shutterstock/videos/12454199/thumb/10.jpg", R.drawable.black_transparent_back, "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/Sintel.mp4"));

        recommandationVideoAdapter = new RecommandationVideoAdapter(this, recommandationVideoModelArrayList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        recommend_recyclerview.setLayoutManager(linearLayoutManager);
        recommend_recyclerview.setAdapter(recommandationVideoAdapter);
        recommandationVideoModelArrayList.add(new RecommandationVideoModel("https://ak9.picdn.net/shutterstock/videos/12454199/thumb/10.jpg", R.drawable.black_transparent_back, "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/Sintel.mp4"));
        recommandationVideoModelArrayList.add(new RecommandationVideoModel("https://ak9.picdn.net/shutterstock/videos/12454199/thumb/10.jpg", R.drawable.black_transparent_back, "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/TearsOfSteel.mp4"));
        recommandationVideoModelArrayList.add(new RecommandationVideoModel("https://ak9.picdn.net/shutterstock/videos/12454199/thumb/10.jpg", R.drawable.black_transparent_back, "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/SubaruOutbackOnStreetAndDirt.mp4"));
        recommandationVideoAdapter = new RecommandationVideoAdapter(this, recommandationVideoModelArrayList);
        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        upcoming_recyclerview.setLayoutManager(linearLayoutManager1);
        upcoming_recyclerview.setAdapter(recommandationVideoAdapter);

        navigation_cancle_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.closeDrawers();
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(DashBoardActivity.this);
                builder.setView(R.layout.exitpopup);
                final AlertDialog alert = builder.create();
                alert.show();
                //dialog.getWindow().setAttributes(windo);
                TextView dialog_cancel = (TextView) alert.findViewById(R.id.dialog_cancel);
                TextView dialog_ok = (TextView) alert.findViewById(R.id.dialog_ok);
                TextView dialog_exit = (TextView) alert.findViewById(R.id.tv_exit);
                TextView exitinformation = (TextView) alert.findViewById(R.id.exitinformation);
                LinearLayout ll_cancel = (LinearLayout) alert.findViewById(R.id.ll_cancel);
                LinearLayout ll_ok = (LinearLayout) alert.findViewById(R.id.ll_ok);
                exitinformation.setText("Do you want to exit from this App?");

                ll_cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alert.dismiss();
                    }
                });
                ll_ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(DashBoardActivity.this, LoginActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        AarambhSharedPreference.saveClassIdToPreference(DashBoardActivity.this, "NA");
                        AarambhSharedPreference.saveStudentNameToPreference(DashBoardActivity.this, "NA");
                        AarambhSharedPreference.saveStudentProfileToPreference(DashBoardActivity.this, "NA");
                        AarambhSharedPreference.saveUserTokenToPreference(DashBoardActivity.this, "NA");
                        startActivity(intent);
                        finish();
                    }
                });
            }
        });


        bookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.closeDrawers();
                Intent intent = new Intent(DashBoardActivity.this, BookMarkActivity.class);
                intent.putExtra("SubjectList", you_Subject);
                startActivity(intent);

            }
        });
        notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.closeDrawers();
                Intent intent = new Intent(DashBoardActivity.this, NotificationActivity.class);
                startActivity(intent);
            }
        });
        contact_us.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.closeDrawers();
                Intent intent = new Intent(DashBoardActivity.this, ContactUsActivity.class);
                startActivity(intent);
            }
        });
        notification_bell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.closeDrawers();
                notification_bell.setClickable(false);
                Intent intent = new Intent(DashBoardActivity.this, NotificationActivity.class);
                startActivity(intent);
            }
        });
        subscribe_now.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.closeDrawers();
                Intent intent = new Intent(DashBoardActivity.this, SubscribeNowActivity.class);
                startActivity(intent);
            }
        });
        terms_condition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.closeDrawers();
                Intent intent = new Intent(DashBoardActivity.this, TermsCondiationActivity.class);
                startActivity(intent);
            }
        });
        parent_connect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.closeDrawers();
                Intent intent = new Intent(DashBoardActivity.this, ParentContectUsActivity.class);
                startActivity(intent);
            }
        });
        shareapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_SEND);
                drawer.closeDrawers();
                i.setType("text/plain");
                //i.putExtra(Intent.ACTION_DEFAULT,R.layout.share_app_redtheme);
                i.putExtra(Intent.EXTRA_SUBJECT, "getString(applicationNameId)");
                String link = "https://play.google.com/store/apps/details?id=com.aarambhappfinal&hl=en";
                i.putExtra(Intent.EXTRA_TEXT, " " + link);
                i.putExtra(Intent.ACTION_VIEW, R.layout.share_app_redtheme);
                startActivity(Intent.createChooser(i, "Share link:"));
            }
        });

        recom_viewall_red.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(DashBoardActivity.this, "This feature is available with the full version", Toast.LENGTH_SHORT).show();
            }
        });

        upcoming_viewall_red.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(DashBoardActivity.this, "This feature is available with the full version", Toast.LENGTH_SHORT).show();
            }
        });

//        img_arrow.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent in = new Intent(DashBoardActivity.this, LiveVideoActivity.class);
//                startActivity(in);
//            }
//        });
    }

    private void init() {
//        Global.correct_answer_test=0;
//        Global.wrong_answer_test=0;
        StrictMode.ThreadPolicy policy = new
                StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        Global.total_question_number = 0;
        courseModelArrayList = new ArrayList<>();
        schoolModelSchoolList = new ArrayList<>();
        videoDetailsArrayList = new ArrayList<>();
        youtube_subjectNameModelArrayList = new ArrayList<>();
        subjectNameModelArrayList = new ArrayList<>();
        //menu_nav=(ImageView)findViewById(R.id.menu_nav);
        notification_bell = (ImageView) findViewById(R.id.notification_bell);
        navigationView = findViewById(R.id.nav_view);
        navHeader = navigationView.getHeaderView(0);
        recom_viewall_red = findViewById(R.id.recom_viewall_red);
        upcoming_viewall_red = findViewById(R.id.upcoming_viewall_red);
        imgProfile = (ImageView) navHeader.findViewById(R.id.img_profile_1);
        bookmark = (TextView) navHeader.findViewById(R.id.bookmark_red);
        notification = (TextView) navHeader.findViewById(R.id.notification_red);
        shareapp = (TextView) navHeader.findViewById(R.id.shareapp_red);
        parent_connect = (TextView) navHeader.findViewById(R.id.parent_connect_red);
        contact_us = (TextView) navHeader.findViewById(R.id.contactus_red);
        subscribe_now = (TextView) navHeader.findViewById(R.id.subscribe_now_red);
        terms_condition = (TextView) navHeader.findViewById(R.id.terms_red);
        navigation_cancle_btn = (ImageView) navHeader.findViewById(R.id.navi_cancle_btn);
        student_class=navHeader.findViewById(R.id.student_class);
        student_class.setText(AarambhSharedPreference.loadClassNameFromPreference(this)+" Grade");
        logout = (TextView) navHeader.findViewById(R.id.logout);
        imgProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.closeDrawers();
                Intent intent = new Intent(DashBoardActivity.this, ProfileShowActivity.class);
                startActivity(intent);
            }
        });


//        try {
//            student_name_navdra=(TextView)findViewById(R.id.student_name_red);
//            //student_name_navdra.setText(AarambhSharedPreference.loadStudentNameFromPreference(this));
        student_name_red = (TextView) navHeader.findViewById(R.id.student_name_red_navdra);
        student_name_red.setText(AarambhSharedPreference.loadStudentNameFromPreference(this));
        student_name_red.setTextColor(Color.parseColor(base_color_one));
//            tab_profile_picture=findViewById(R.id.img_profile_tab);
//            if (AarambhSharedPreference.loadStudentProfileFromPreference(this).equals("NA")){
//                Glide.with(this).load(urlProfileImg)
//                        .crossFade()
//                        .thumbnail(0.5f)
//                        .bitmapTransform(new CircleTransform(this))
//                        .diskCacheStrategy(DiskCacheStrategy.ALL)
//                        .into(tab_profile_picture);
//            }else {
//                Glide.with(this).load(urlStudentImg+AarambhSharedPreference.loadStudentProfileFromPreference(this))
//                        .crossFade()
//                        .thumbnail(0.5f)
//                        .bitmapTransform(new CircleTransform(this))
//                        .diskCacheStrategy(DiskCacheStrategy.ALL)
//                        .into(tab_profile_picture);
//            }
//
////            Glide.with(this).load(urlProfileImg)
////                    .crossFade()
////                    .thumbnail(0.5f)
////                    .bitmapTransform(new CircleTransform(this))
////                    .diskCacheStrategy(DiskCacheStrategy.ALL)
////                    .into(tab_profile_picture);
//
//        }catch (Exception e){
//            e.printStackTrace();
//        }
        drawer = (DrawerLayout) findViewById(R.id.drawerLayout);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setHomeButtonEnabled(true);
//        getSupportActionBar().setHomeAsUpIndicator(R.drawable.navigation_icon);

        CollapsingToolbarLayout collapsingToolbar = findViewById(R.id.collaspingToolbar);
        if (getSupportFragmentManager().findFragmentByTag(CURRENT_TAG) != null) {
            drawer.closeDrawers();

            // show or hide the fab button
            // toggleFab();
            return;
        }
//        actionBar.setDisplayUseLogoEnabled(true);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.hide_bottom_view_on_scroll_behavior, R.string.app_name) {
            @Override
            public void onDrawerClosed(View drawerView) {
                // Code here will be triggered once the drawer closes as we dont want anything to happen so we leave this blank
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                // Code here will be triggered once the drawer open as we dont want anything to happen so we leave this blank
                super.onDrawerOpened(drawerView);
            }
        };
        //Setting the actionbarToggle to drawer layout

        // view_all_recommend_video=(TextView)findViewById(R.id.view_all_recommend_video);
        progressdialog = new ProgressDialog(getApplicationContext());
        progressdialog.setMessage("Please Wait....");
        progressdialog.setCancelable(false);
        upcoming_recyclerview = (RecyclerView) findViewById(R.id.upcoming_recyclerview);

        subject_name_recycler_view = (RecyclerView) findViewById(R.id.subject_dash_recyclerview);
        //  subjectNameModelArrayList = new ArrayList<>();
        scrollView = findViewById(R.id.scrollview);
        recommandationVideoModelArrayList = new ArrayList<>();

        recommend_recyclerview = findViewById(R.id.recom_recyclerview);
        live_recyclerview = findViewById(R.id.live_recyclerview);
        hello_word = (TextView) findViewById(R.id.hello_word);
        std_name_word = (TextView) findViewById(R.id.stud_name_word);
        std_name_word.setText(AarambhSharedPreference.loadStudentNameFromPreference(this));
        ready_to_learn_word = (TextView) findViewById(R.id.ready_learn_word);
        TextColorGradient textColorGradient = new TextColorGradient();
        textColorGradient.getColorTextGradient(hello_word, base_color_one, base_color_two);
        textColorGradient.getColorTextGradient(recom_viewall_red, base_color_one, base_color_two);
        textColorGradient.getColorTextGradient(upcoming_viewall_red, base_color_one, base_color_two);
        textColorGradient.getColorTextGradient(std_name_word, base_color_one, base_color_two);
        textColorGradient.getColorTextGradient(ready_to_learn_word, base_color_one, base_color_two);

        transprent_dahboard = findViewById(R.id.transprent_dahboard);
        transprent_dahboard.setBackgroundColor(Color.parseColor(transparent_color));
        background_dashboard = findViewById(R.id.background_dashboard);
        dashbord_nav_transrent = navHeader.findViewById(R.id.dashbord_nav_transrent);
        dashbord_nav_transrent.setBackgroundColor(Color.parseColor(transparent_color));
        dashborad_nav_profile = navHeader.findViewById(R.id.dashborad_nav_profile);
        edit_dashboard_nav = navHeader.findViewById(R.id.edit_dashboard_nav);
        img_arrow = findViewById(R.id.img_arrow);
        //notification_bell.setBackgroundDrawable(R.drawable.notification_purpal);
        //notification_bell.setBackground(R.drawable.notification_purpal);
        //notification_bell.setImageResource(R.drawable.notification_purpal);
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


        gd.setCornerRadius(100f);

        GradientDrawable gd1 = new GradientDrawable(
                GradientDrawable.Orientation.TOP_BOTTOM, colors);


        gd1.setCornerRadius(100f);


        //apply the button background to newly created drawable gradient
        dashborad_nav_profile.setBackground(gd);
        edit_dashboard_nav.setBackground(gd1);


        //  thanku_label.setBackground(gd1);
        // cancle_btn.setBackground(gd1);
    }

    private void themeDynamicAdd() {

        //Bitmap myImage = getBitmapFromURL(image);
        try {
            Background = getBitmapFromURL(base_image);
            Drawable dr = new BitmapDrawable((Background));
            background_dashboard.setBackgroundDrawable(dr);
            // navigationView.setBackgroundDrawable(dr);

        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            Bitmap Background1 = getBitmapFromURL(base_image);
            Drawable dr1 = new BitmapDrawable((Background1));
            navigationView.setBackgroundDrawable(dr1);

        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            String menu = AarambhThemeSharedPrefreence.loadMenuNavigationIconFromPreference(this);
            menu_bitmap = getBitmapFromURL(menu);
            Drawable dr = new BitmapDrawable((menu_bitmap));
            //  background_dashboard.setBackgroundDrawable(dr);
            if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(false);
                toolbar.setNavigationIcon(dr);

            } else {
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(false);
        toolbar.setNavigationIcon(dr);

       // notification_bell.setImageDrawable(R.drawable.notification_purpal);

         }

        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            String cancle_btn = AarambhThemeSharedPrefreence.loadCancleIconFromPreference(this);
            Bitmap Background = getBitmapFromURL(cancle_btn);
            Drawable dr = new BitmapDrawable((Background));
            navigation_cancle_btn.setBackgroundDrawable(dr);
            // navigationView.setBackgroundDrawable(dr);

        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            String notification = AarambhThemeSharedPrefreence.loadNotificationIconFromPreference(this);
            Bitmap Background = getBitmapFromURL(notification);
            Drawable dr = new BitmapDrawable((Background));
            notification_bell.setBackgroundDrawable(dr);
            // navigationView.setBackgroundDrawable(dr);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    //}


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

    private void setAnimation(View viewToAnimate) {
        Animation animation = AnimationUtils.loadAnimation(DashBoardActivity.this, android.R.anim.fade_in);
        animation.setDuration(50 + 200);
        viewToAnimate.startAnimation(animation);
    }

//    @Override
//    public void onBackPressed() {
//        AlertDialog.Builder builder = new AlertDialog.Builder(DashBoardSixthStandardActivity.this);
//        builder.setView(R.layout.exitpopup);
//        final AlertDialog alert = builder.create();
//        alert.show();
//        TextView dialog_cancel = (TextView) alert.findViewById(R.id.dialog_cancel);
//        TextView dialog_ok = (TextView) alert.findViewById(R.id.dialog_ok);
//        TextView dialog_exit = (TextView) alert.findViewById(R.id.tv_exit);
//        TextView exitinformation = (TextView) alert.findViewById(R.id.exitinformation);
//        LinearLayout ll_cancel = (LinearLayout) alert.findViewById(R.id.ll_cancel);
//        LinearLayout ll_ok = (LinearLayout) alert.findViewById(R.id.ll_ok);
//        exitinformation.setText("Do you want to exit from this App?");
//
//        ll_cancel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                alert.dismiss();
//            }
//        });
//        ll_ok.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//                finishAffinity();
//            }
//        });

    //  }


    @Override
    public void onBackPressed() {
//        if (doubleBackToExitPressedOnce) {
//            finish();
//            finishAffinity();
//            return;
//        }
//        this.doubleBackToExitPressedOnce = true;
//        Toast.makeText(getApplicationContext(), "Tap again to exit",Toast.LENGTH_SHORT).show();
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                doubleBackToExitPressedOnce=false;
//            }
//        }, 2000);
        Intent in = new Intent(DashBoardActivity.this, ViewLiveClassActivity.class);
        startActivity(in);
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        return false;

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 101 && resultCode == RESULT_OK) {
            try {
                YouTubeTitle rm = (YouTubeTitle) data.getSerializableExtra("DataDashBoard");
                // Log.e("Test Data", String.valueOf(rm.getChapter()));
//                landDetailModel = (LandDetailModel) data.getSerializableExtra("LandDetail");
//                Log.e("Land Data", landDetailModel.getArea());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    public void getStudentCourses() {
        if (!CommonUtilities.isOnline(DashBoardActivity.this)) {
            Toast.makeText(DashBoardActivity.this, "Please On Your Internet Connection", Toast.LENGTH_LONG).show();
            return;
        }
        progressdialog = new ProgressDialog(this);
        progressdialog.setCancelable(false);
        progressdialog.setMessage("Loading...");
        progressdialog.setTitle("Wait...");
        progressdialog.show();
        final String string_json = "Result";
        //http://35.200.220.64:1500/aarambhTesting/getStudentCourses?SchoolId=43&page=1&size=2
        String url = Global.WEBBASE_URL + "getStudentCourses?SchoolId=" + Global.AARAMBH_ID + "&page=1&size=2";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                String res = response.toString();
                parseResponseCourse(res);
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
                        parseResponseCourse(response.toString());
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
                headers.put("Authorization", "Bearer " + AarambhSharedPreference.loadUserTokenFromPreference(DashBoardActivity.this));
                return headers;
            }
        };
        VolleySingleton.getInstance(DashBoardActivity.this).addToRequestQueue(stringRequest, string_json);


    }

    public void parseResponseCourse(String response) {
        Log.e("Response Course", response);
        progressdialog.dismiss();
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

                    JSONArray jsonObjectTopicList = jsonObject1.getJSONArray("TopicList");

                    for (int i = 0; i < jsonObjectTopicList.length(); i++) {
                        JSONObject topic = jsonObjectTopicList.getJSONObject(i);

                        String CourseId = topic.getString("CourseId");
                        String CourseName = topic.getString("CourseName");
                        String CourseDescription = topic.getString("CourseDescription");
                        String CourseOtherDetails = topic.getString("CourseOtherDetails");
                        String CourseImage = topic.getString("CourseImage");
                        String ClassId = topic.getString("ClassId");
                        String SchoolId = topic.getString("SchoolId");
                        String StatusId = topic.getString("StatusId");
                        String CreatedById = topic.getString("CreatedById");
                        String ModifiedById = topic.getString("ModifiedById");
                        String CreationDate = topic.getString("CreationDate");
                        String ModificationDate = topic.getString("ModificationDate");
                        CourseModel cm = new CourseModel(CourseId,CourseName,CourseDescription,
                                CourseOtherDetails,CourseImage,ClassId,SchoolId,StatusId,CreatedById,ModifiedById,CreationDate,ModificationDate);
                        courseModelArrayList.add(cm);
                    }
                    subjectNameAdapter = new SubjectNameAdapter(this, courseModelArrayList);
                    subject_name_recycler_view.setAdapter(subjectNameAdapter);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}