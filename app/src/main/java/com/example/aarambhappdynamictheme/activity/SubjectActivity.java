package com.example.aarambhappdynamictheme.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
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
import com.example.aarambhappdynamictheme.adapter.SubjectChapterListAdapter;
import com.example.aarambhappdynamictheme.model.SubjectChapterListModel;
import com.example.aarambhappdynamictheme.model.TopicModel;
import com.example.aarambhappdynamictheme.model.VideoDetails;
import com.example.aarambhappdynamictheme.model.YouTubePlayList;
import com.example.aarambhappdynamictheme.model.YouTubeTitle;
import com.example.aarambhappdynamictheme.sheardPrefrence.AarambhSharedPreference;
import com.example.aarambhappdynamictheme.sheardPrefrence.AarambhThemeSharedPrefreence;
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

public class SubjectActivity extends AppCompatActivity {
    TextView subj_name, practice, test;
    LinearLayout linearLayout_back_theme;
    ArrayList<SubjectChapterListModel> subjectChapterListModelArrayList;
    SubjectChapterListModel subjectChapterListModel;
    SubjectChapterListAdapter subjectChapterListAdapter;
    RecyclerView chapter_list_Recyclerview;
    String subject_name, course_id;
    CardView pratices_Chapter, test_subjcet;
    ProgressDialog progressdialog;
    TextView number_chapters;
    //    Button back_btn;
    ImageView profile_subject_tab, back_btn;
    TextView student_name;
    ArrayList<VideoDetails> videoDetailsArrayList;
    ArrayList<String> you_Chapter;
    ArrayList<VideoDetails> new_arrayListVideoDetails;
    ArrayList<YouTubePlayList> playListArrayList;
    YouTubeTitle sixth_Details;
    //Dynamic Theme
    ArrayList<TopicModel>topicModelArrayList;
    LinearLayout background_theme, transprent_subject;
    Bitmap myImage;
    String base_image, transparent_color, base_color_one, base_color_two;
    AppCompatImageView pratice_background, test_profile;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject);
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        checkOrientation();
        base_image = AarambhThemeSharedPrefreence.loadBaseImageFromPreference(this);
        transparent_color = AarambhThemeSharedPrefreence.loadBaseImageTransparentFromPreference(this);
        Log.e("transprent", transparent_color);
        base_color_one = AarambhThemeSharedPrefreence.loadBaseColorOneFromPreference(this);
        base_color_two = AarambhThemeSharedPrefreence.loadBaseColorTwoFromPreference(this);
        subject_name = AarambhSharedPreference.loadSubjectNameFromPreference(this);
        //course_id = AarambhSharedPreference.loadCourseIdFromPreference(this);
        //  class_id = AarambhSharedPreference.loadClassIdFromPreference(this);
        init();
        listner();
        getStudentTopics();

        // getChapterApiCalling();

        //  getTopicApiCalling(subjectChapterListModel.getTopicId());
//        if (AarambhSharedPreference.loadClassIdFromPreference(this).equalsIgnoreCase("3")) {
//            try {
//                sixth_Details = (YouTubeTitle) getIntent().getSerializableExtra("8thDetails_chapter");
//                //new_arrayListVideoDetails = (ArrayList<VideoDetails>) getIntent().getSerializableExtra("6thDetails_chapter_video");
//                playListArrayList = (ArrayList<YouTubePlayList>) getIntent().getSerializableExtra("PlayList");
//                you_Chapter = new ArrayList<>();
//                ArrayList<String> videoName = new ArrayList<>();
//
//                //you_Chapter = sixth_Details.getChapter();
//
////            for(int i=0;i<new_arrayListVideoDetails.size();i++){
////                Log.e("VideoDetailsAL",new_arrayListVideoDetails.get(i).getVideoId());
////            }
//
//                for (int x = 0; x < sixth_Details.getChapter().size(); x++) {
//                    you_Chapter = (ArrayList<String>) sixth_Details.getChapter().clone();
//                }
//
//                Log.e("subject_name", subject_name);
//                number_chapters.setText(sixth_Details.getChapter().size() + " Chapters");
//
//                //subjectChapterListAdapter = new SubjectChapterListAdapter(this, you_Chapter, new_arrayListVideoDetails);
//                subjectChapterListAdapter = new SubjectChapterListAdapter(this, you_Chapter, playListArrayList);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }else if (AarambhSharedPreference.loadClassIdFromPreference(this).equalsIgnoreCase("2")) {
//            try {
//                sixth_Details = (YouTubeTitle) getIntent().getSerializableExtra("7thDetails_chapter");
//                //new_arrayListVideoDetails = (ArrayList<VideoDetails>) getIntent().getSerializableExtra("6thDetails_chapter_video");
//                playListArrayList = (ArrayList<YouTubePlayList>) getIntent().getSerializableExtra("PlayList");
//                you_Chapter = new ArrayList<>();
//                ArrayList<String> videoName = new ArrayList<>();
//                for (int x = 0; x < sixth_Details.getChapter().size(); x++) {
//                    you_Chapter = (ArrayList<String>) sixth_Details.getChapter().clone();
//                }
//
//                Log.e("subject_name", subject_name);
//                number_chapters.setText(sixth_Details.getChapter().size() + " Chapters");
//                subjectChapterListAdapter = new SubjectChapterListAdapter(this, you_Chapter, playListArrayList);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }else if (AarambhSharedPreference.loadClassIdFromPreference(this).equalsIgnoreCase("1")) {
//
//            try {
//                sixth_Details = (YouTubeTitle) getIntent().getSerializableExtra("6thDetails_chapter");
//                //new_arrayListVideoDetails = (ArrayList<VideoDetails>) getIntent().getSerializableExtra("6thDetails_chapter_video");
//                playListArrayList = (ArrayList<YouTubePlayList>) getIntent().getSerializableExtra("PlayList");
//                you_Chapter = new ArrayList<>();
//                ArrayList<String> videoName = new ArrayList<>();
//
//                //you_Chapter = sixth_Details.getChapter();
//
////            for(int i=0;i<new_arrayListVideoDetails.size();i++){
////                Log.e("VideoDetailsAL",new_arrayListVideoDetails.get(i).getVideoId());
////            }
//
//                for (int x = 0; x < sixth_Details.getChapter().size(); x++) {
//                    you_Chapter = (ArrayList<String>) sixth_Details.getChapter().clone();
//                }
//
//                Log.e("subject_name", subject_name);
//
////            for(int x = 0;x<sixth_Details.getVideoTitle().size();x++){
////                videoName = (ArrayList<String>) sixth_Details.getVideoTitle().clone();
////            }
//                // youtube_subjectNameModelArrayList.add(sixth_Details);
//                //videoDetailsArrayList.add(sixth_Details);
//                // number_chapters.setText(jsonArray.length()+" Chapters");
//                number_chapters.setText(sixth_Details.getChapter().size() + " Chapters");
//
//                //subjectChapterListAdapter = new SubjectChapterListAdapter(this, you_Chapter, new_arrayListVideoDetails);
//                subjectChapterListAdapter = new SubjectChapterListAdapter(this, you_Chapter, playListArrayList);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//        try {
//            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
//            chapter_list_Recyclerview.setLayoutManager(linearLayoutManager);
//            chapter_list_Recyclerview.setAdapter(subjectChapterListAdapter);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

    public void getStudentTopics() {
        if (!CommonUtilities.isOnline(SubjectActivity.this)) {
            Toast.makeText(SubjectActivity.this, "Please On Your Internet Connection", Toast.LENGTH_LONG).show();
            return;
        }
        progressdialog = new ProgressDialog(this);
        progressdialog.setCancelable(false);
        progressdialog.setMessage("Loading...");
        progressdialog.setTitle("Wait...");
        progressdialog.show();
        final String string_json = "Result";
        String url = Global.WEBBASE_URL + "getStudentTopics?page=1&size=2&SchoolId=" + Global.AARAMBH_ID;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                String res = response.toString();
                parseResponseTopic(res);
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
                        parseResponseTopic(response.toString());
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
                headers.put("Authorization", "Bearer " + AarambhSharedPreference.loadUserTokenFromPreference(SubjectActivity.this));
                return headers;
            }
        };
        VolleySingleton.getInstance(SubjectActivity.this).addToRequestQueue(stringRequest, string_json);
    }

    public void parseResponseTopic(String response) {
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

                        String TopicId = topic.getString("TopicId");
                        String TopicName = topic.getString("TopicName");
                        String TopicImage = topic.getString("TopicImage");
                        String TopicOtherDetail = topic.getString("TopicOtherDetail");
                        String ClassId = topic.getString("ClassId");
                        String SchoolId = topic.getString("SchoolId");
                        String CourseId = topic.getString("CourseId");
                        String StatusId = topic.getString("StatusId");
                        String CreatedById = topic.getString("CreatedById");
                        String ModifiedById = topic.getString("ModifiedById");
                        String CreationDate = topic.getString("CreationDate");
                        String ModificationDate = topic.getString("ModificationDate");
                        TopicModel tm = new TopicModel(TopicId, TopicName, TopicImage, TopicOtherDetail, ClassId, SchoolId, CourseId, StatusId, CreatedById, ModifiedById, CreationDate, ModificationDate);
                           topicModelArrayList.add(tm);
                    }
                    subjectChapterListAdapter = new SubjectChapterListAdapter(this, topicModelArrayList);
                    number_chapters.setText(topicModelArrayList.size() + " Chapters");
                    try {
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
                        chapter_list_Recyclerview.setLayoutManager(linearLayoutManager);
                        chapter_list_Recyclerview.setAdapter(subjectChapterListAdapter);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 201 && resultCode == RESULT_OK) {
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

    private void checkOrientation() {
        if (getResources().getBoolean(R.bool.portrait_only)) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
    }

    private void listner() {
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(SubjectActivity.this, DashBoardActivity.class);
                startActivity(in);
                //finish();
                //  startActivity(new Intent(SubjectsSixthClassActivity.this, DashBoardSixthStandardActivity.class));
            }
        });
        pratices_Chapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent = new Intent(SubjectsSixthClassActivity.this, RedThemePracticeActivity.class);
                Intent intent = new Intent(SubjectActivity.this, PaticesChapterListActivity.class);
                intent.putExtra("PracticeChap", sixth_Details);
                intent.putExtra("PracticePlayList", playListArrayList);
                startActivity(intent);
            }
        });
        test_subjcet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent = new Intent(SubjectsSixthClassActivity.this, RedThemeTestActivity.class);
                Intent intent = new Intent(SubjectActivity.this, TestChapterListActivity.class);
                intent.putExtra("TestChap", sixth_Details);
                intent.putExtra("TestPlayList", playListArrayList);
                startActivity(intent);
            }
        });
    }

    private void getChapterApiCalling() {
        {
            if (!CommonUtilities.isOnline(this)) {
                Toast.makeText(SubjectActivity.this, "Please On Your Internet Connection", Toast.LENGTH_LONG).show();
                return;
            }
            Log.e("course_id", course_id);
            String url = Global.WEBBASE_URL + "getTopicByCourseId?CourseId=" + course_id;
            final String string_json = "Result";
//        JSONObject params = new JSONObject();
//        String mRequestBody = "";

//        mRequestBody = params.toString();
//        Log.e("Request Body", mRequestBody);
//        final String finalMRequestBody = mRequestBody;
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    String res = response.toString();
                    parseResponseChapterApi(res, progressdialog);
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
                            parseResponseChapterApi(response.toString(), progressdialog);

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
                    params.put("CourseId", course_id);
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
                    headers.put("Authorization", "Bearer " + AarambhSharedPreference.loadUserTokenFromPreference(SubjectActivity.this));
                    return headers;
                }
            };
            VolleySingleton.getInstance(SubjectActivity.this).addToRequestQueue(stringRequest, string_json);

        }
    }

    private void parseResponseChapterApi(String response, ProgressDialog progressdialog) {
        Log.e("chapter_res", response);
        try {
            JSONArray jsonArray = new JSONArray(response);
            for (int i = 0; i < jsonArray.length(); i++) {
                Log.e("chapter_length", String.valueOf(i));
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String TopicId = jsonObject.getString("TopicId");
                String TopicName = jsonObject.getString("TopicName");
                Log.e("TopicName", TopicName);
                String TopicOtherDetail = jsonObject.getString("TopicOtherDetail");
                String ClassId = jsonObject.getString("ClassId");
                String CourseId = jsonObject.getString("CourseId");
                String StatusId = jsonObject.getString("StatusId");
                String CreatedById = jsonObject.getString("CreatedById");
                String ModifiedById = jsonObject.getString("ModifiedById");
                String CreationDate = jsonObject.getString("CreationDate");
                String ModificationDate = jsonObject.getString("ModificationDate");
                Log.e("sf-----", String.valueOf(subjectChapterListModelArrayList));
                subjectChapterListModel = new SubjectChapterListModel(TopicId, TopicName, TopicOtherDetail, ClassId, CourseId, StatusId, CreatedById, ModifiedById, CreationDate, ModificationDate);
                subjectChapterListModelArrayList.add(subjectChapterListModel);
            }
//            number_chapters.setText(jsonArray.length()+" Chapters");
//            subjectChapterListAdapter = new SubjectChapterListAdapter(this, subjectChapterListModelArrayList);
//            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
//            chapter_list_Recyclerview.setLayoutManager(linearLayoutManager);
//            chapter_list_Recyclerview.setAdapter(subjectChapterListAdapter);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        Intent in = new Intent(SubjectActivity.this, DashBoardActivity.class);
           startActivity(in);
        //finish();
    }

    @Override
    public void finish() {
        Intent in = new Intent(SubjectActivity.this, DashBoardActivity.class);
        in.putExtra("DataDashBoard6th", you_Chapter);
        //  in.putExtra("LandDetail", landDetailModel);
        in.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        setResult(RESULT_OK, in);
        super.finish();
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

    private void init() {
        topicModelArrayList=new ArrayList<>();

        StrictMode.ThreadPolicy policy = new
                StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        Global.total_question_number = 0;
        videoDetailsArrayList = new ArrayList<>();

//        try {
//            profile_subject_tab=(ImageView) findViewById(R.id.img_profile_tab);
//            student_name=(TextView) findViewById(R.id.student_name_red);
//            student_name.setText(AarambhSharedPreference.loadStudentNameFromPreference(this));
//            Glide.with(this).load(urlProfileImg)
//                    .crossFade()
//                    .thumbnail(0.5f)
//                    .bitmapTransform(new CircleTransform(this))
//                    .diskCacheStrategy(DiskCacheStrategy.ALL)
//                    .into(profile_subject_tab);
//
//        }catch (Exception e){
//            e.printStackTrace();
//        }

        number_chapters = (TextView) findViewById(R.id.chapter_numbers_red);
        progressdialog = new ProgressDialog(getApplicationContext());
        progressdialog.setMessage("Please Wait....");
        progressdialog.setCancelable(false);
        test_subjcet = findViewById(R.id.test_red_cardview);
        pratices_Chapter = findViewById(R.id.practices_red_cardview);
        subj_name = findViewById(R.id.subj_one_name);
        subj_name.setText(subject_name);
        back_btn = findViewById(R.id.back_btn);
        chapter_list_Recyclerview = findViewById(R.id.chapter_list_recyclerview_sixth_std);
        subjectChapterListModelArrayList = new ArrayList<>();
        practice = (TextView) findViewById(R.id.pratice);
        test = (TextView) findViewById(R.id.test);
        TextColorGradient textColorGradient = new TextColorGradient();
        textColorGradient.getColorTextGradient(practice, base_color_one, base_color_two);
        textColorGradient.getColorTextGradient(test, base_color_one, base_color_two);
        textColorGradient.getColorTextGradient(subj_name, base_color_one, base_color_two);
        pratice_background = findViewById(R.id.pratice_background);
        test_profile = findViewById(R.id.test_profile);
        subj_name.setText(AarambhSharedPreference.loadSubjectNameFromPreference(this));

        getDynamicData();
        themeDynamicAdd();
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
        pratice_background.setBackground(gd);
        test_profile.setBackground(gd);
    }


    private void themeDynamicAdd() {

        //Bitmap myImage = getBitmapFromURL(image);
        try {
            background_theme = findViewById(R.id.background_theme);
            transprent_subject = findViewById(R.id.transprent_subject);
            myImage = getBitmapFromURL(base_image);
            Drawable dr = new BitmapDrawable((myImage));
            background_theme.setBackgroundDrawable(dr);
            transprent_subject.setBackgroundColor(Color.parseColor(transparent_color));

            Bitmap back_bitmap;
            String back_shrd = AarambhThemeSharedPrefreence.loadBackArrowIconFromPreference(this);
            back_bitmap = getBitmapFromURL(back_shrd);
            Drawable dr1 = new BitmapDrawable((back_bitmap));
            back_btn.setBackgroundDrawable(dr1);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
