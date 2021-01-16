package com.example.aarambhappdynamictheme.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
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
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.aarambhappdynamictheme.R;
import com.example.aarambhappdynamictheme.model.VideoDetails;
import com.example.aarambhappdynamictheme.model.YouTubePlayList;
import com.example.aarambhappdynamictheme.model.YouTubeTitle;
import com.example.aarambhappdynamictheme.sheardPrefrence.AarambhSharedPreference;
import com.example.aarambhappdynamictheme.sheardPrefrence.AarambhThemeSharedPrefreence;
import com.example.aarambhappdynamictheme.textGradient.CircleTransform;
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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Map;

import static com.example.aarambhappdynamictheme.util.Global.urlProfileImg;
import static com.example.aarambhappdynamictheme.util.Global.urlStudentImg;

public class ProfileShowActivity extends AppCompatActivity {
    ImageView imgProfile,tab_profile_picture;
    ImageButton edit_profile,back_btn;
    TextView logout_profile;
    TextView student_name_profile,student_mobile,student_email,student_class,
            student_gender,student_DOB,student_address,student_profile_name;
    ArrayList<String> you_Chapter;
    RelativeLayout profile_background;
    String base_image,transparent_color,base_color_one,base_color_two;
    ImageView mobile_profile_image,name_profile_image
            ,email_profile_image,gender_profile_image,Dob_profile_image,address_profile_image,logout_image;
    String mobile_shrd,name_shrd,email_shrd,gender_shrd,Dob_shrd,address_shrd,logout_shrd;
    Bitmap mobile_bitmap,name_bitmap,email_bitmap,gender_bitmap,Dob_bitmap,address_bitmap,logout_bitmap;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_show);
        checkOrientation();

        base_image= AarambhThemeSharedPrefreence.loadBaseImageFromPreference(this);
        transparent_color=AarambhThemeSharedPrefreence.loadBaseImageTransparentFromPreference(this);
        Log.e("transprent",transparent_color);

        base_color_one=AarambhThemeSharedPrefreence.loadBaseColorOneFromPreference(this);
        base_color_two=AarambhThemeSharedPrefreence.loadBaseColorTwoFromPreference(this);
        init();
        listner();
        getUserDataAPIcalling();
    }
    private void checkOrientation() {
        if (getResources().getBoolean(R.bool.portrait_only)) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
    }

    private void getUserDataAPIcalling() {
        if (!CommonUtilities.isOnline(this)) {
            Toast.makeText(ProfileShowActivity.this, "Please On Your Internet Connection", Toast.LENGTH_LONG).show();
            return;
        }
        //  http://35.200.220.64:1231/aarambh/getStudentMainById?studentId=1
        String url = Global.WEBBASE_URL + "getStudentMainById?studentId="+ AarambhSharedPreference.loadStudentIdFromPreference(this);
        final String string_json = "Result";
        StringRequest stringRequest=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                String res = response.toString();
                parseResponseProfileApi(res);
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
                        parseResponseProfileApi(response.toString());

                    } catch (UnsupportedEncodingException e1) {
                        // Couldn't properly decode data to string
                        e1.printStackTrace();
                    }
                }

            }
        }){
            @Override
            public Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<String, String>();
                params.put("studentId", AarambhSharedPreference.loadStudentIdFromPreference(ProfileShowActivity.this));
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
                headers.put("Authorization", "Bearer "+ AarambhSharedPreference.loadUserTokenFromPreference(ProfileShowActivity.this));
                return headers;
            }
        };
        VolleySingleton.getInstance(ProfileShowActivity.this).addToRequestQueue(stringRequest, string_json);

    }
    private void parseResponseProfileApi(String response) {
        Log.e("class_res",response);
        try {
            JSONObject jsonObject1 = new JSONObject(response);
            String status=jsonObject1.getString("status");

            boolean success = jsonObject1.getBoolean("success");

            if(success==false){
                String error=jsonObject1.getString("error");
                Toast.makeText(this,error+"",Toast.LENGTH_LONG).show();
            }else if (success==true) {
                String Message = jsonObject1.getString("Message");
                if (Message.equalsIgnoreCase("Data Found")) {
                    //  if(status.equalsIgnoreCase("200")&&success==true&&Message.equalsIgnoreCase("Data Found")) {
                    JSONArray jsonArray = jsonObject1.getJSONArray("Student");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        Log.e("class_lenth", String.valueOf(i));
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String StudentId = jsonObject.getString("StudentId");
                        Log.e("StudentId", StudentId);
                        String StudentName = jsonObject.getString("StudentName");
                        Log.e("StudentName", StudentName);
                        student_name_profile.setText(StudentName);
                        student_profile_name.setText(StudentName);
                        String StudentGender = jsonObject.getString("StudentGender");
                        student_gender.setText(StudentGender);
                        String StudentMobile = jsonObject.getString("StudentMobile");
                        if (StudentMobile.contains("null")) {
                            student_mobile.setText("");
                        } else {
                            student_mobile.setText(StudentMobile);
                        }
                        String StudentUsername = jsonObject.getString("StudentUsername");
                        String StudentEmail = jsonObject.getString("StudentEmail");
                        if (StudentEmail.contains("null")) {
                            student_email.setText("");
                        } else {
                            student_email.setText(StudentEmail);
                        }
                        String StudentAddress = jsonObject.getString("StudentAddress");
                        student_address.setText(StudentAddress);
                        String StudentCity = jsonObject.getString("StudentCity");

                        String StudentDOB = jsonObject.getString("StudentDOB");
                        String firstFourChars = StudentDOB.substring(0, 10);
                        DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
                        DateFormat outputFormat = new SimpleDateFormat("dd-MM-yyyy");
                        Date date = inputFormat.parse(firstFourChars);
                        String outputDateStr = outputFormat.format(date);
                        student_DOB.setText(outputDateStr);
                        String StudentDORegis = jsonObject.getString("StudentDORegis");
                        // String StudentGuardianName = jsonObject.getString("StudentGuardianName");

                        String StudentPassword = jsonObject.getString("StudentPassword");
                        String StudentImage = jsonObject.getString("StudentImage");
                        if (StudentImage.equals("null")) {
                            AarambhSharedPreference.saveStudentProfileToPreference(this, "NA");
                        } else {
                            AarambhSharedPreference.saveStudentProfileToPreference(this, StudentImage);
                        }
                        if (AarambhSharedPreference.loadStudentProfileFromPreference(this).equals("NA")) {
                            Glide.with(this).load(urlProfileImg)
                                    .crossFade()
                                    .thumbnail(0.5f)
                                    .bitmapTransform(new CircleTransform(this))
                                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                                    .into(imgProfile);
                        } else {
                            Glide.with(this).load(urlStudentImg + StudentImage)
                                    .crossFade()
                                    .thumbnail(0.5f)
                                    .bitmapTransform(new CircleTransform(this))
                                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                                    .into(imgProfile);
                        }
                        String ClassId = jsonObject.getString("ClassId");
                        String StatusId = jsonObject.getString("StatusId");
                        String CreatedById = jsonObject.getString("CreatedById");
                        String ModifiedById = jsonObject.getString("ModifiedById");
                        String CreationDate = jsonObject.getString("CreationDate");
                        String ModificationDate = jsonObject.getString("ModificationDate");
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void listner() {
//        String student_profile=AarambhSharedPreference.loadStudentProfileFromPreference(this);
//        if (AarambhSharedPreference.loadStudentProfileFromPreference(this).equals("NA")){
//            Glide.with(this).load(urlProfileImg)
//                    .crossFade()
//                    .thumbnail(0.5f)
//                    .bitmapTransform(new CircleTransform(this))
//                    .diskCacheStrategy(DiskCacheStrategy.ALL)
//                    .into(imgProfile);
//        }else {
//            Glide.with(this).load(urlStudentImg+student_profile)
//                    .crossFade()
//                    .thumbnail(0.5f)
//                    .bitmapTransform(new CircleTransform(this))
//                    .diskCacheStrategy(DiskCacheStrategy.ALL)
//                    .into(imgProfile);
//        }
//        Glide.with(this).load(urlProfileImg)
//                .crossFade()
//                .thumbnail(0.5f)
//                .bitmapTransform(new CircleTransform(this))
//                .diskCacheStrategy(DiskCacheStrategy.ALL)
//                .into(imgProfile);
        edit_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ProfileShowActivity.this, UpdateProfileActivity.class);
                startActivity(intent);
            }
        });
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getChannelPlayListVideos();
//                Intent intent=new Intent(RedTheme_ProfileDataShowActivity.this, DashBoardSixthStandardActivity.class);
//                startActivity(intent);
            }
        });
        logout_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ProfileShowActivity.this);
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
                exitinformation.setText("Do you want to exit from this app ?");

                ll_cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alert.dismiss();
                    }
                });
                ll_ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(ProfileShowActivity.this, LoginActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        AarambhSharedPreference.saveClassIdToPreference(ProfileShowActivity.this, "NA");
                        AarambhSharedPreference.saveStudentNameToPreference(ProfileShowActivity.this, "NA");
                        AarambhSharedPreference.saveUserTokenToPreference(ProfileShowActivity.this,"NA");
                        startActivity(intent);
                        finish();
                    }
                });
            }
        });


    }

    @Override
    public void onBackPressed() {
        getChannelPlayListVideos();
    }
    @Override
    public void finish() {
        Intent intent=new Intent(ProfileShowActivity.this,DashBoardActivity.class);
        intent.putExtra("DataDashBoard6th", you_Chapter);
        //  in.putExtra("LandDetail", landDetailModel);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        setResult(RESULT_OK, intent);
        super.finish();
    }


    private void init() {
        student_class=findViewById(R.id.student_class);
        student_class.setText(AarambhSharedPreference.loadClassNameFromPreference(this)+" Grade");
        logout_profile=(TextView)findViewById(R.id.logout_profile);
        logout_profile.setTextColor(Color.parseColor(base_color_one));
        imgProfile=(ImageView)findViewById(R.id.img_profile_1);
        edit_profile=(ImageButton)findViewById(R.id.edit_profile_red);
        back_btn=(ImageButton) findViewById(R.id.back_btn_profile_show);
        student_profile_name=(TextView)findViewById(R.id.student_name_red_profile_view);

        student_address=(TextView)findViewById(R.id.student_adddress_Profile);
        student_address.setTextColor(Color.parseColor(base_color_one));
        student_DOB=(TextView)findViewById(R.id.student_date_of_birth_profile);
        student_DOB.setTextColor(Color.parseColor(base_color_one));
        student_email=(TextView)findViewById(R.id.student_profile_email);
        student_email.setTextColor(Color.parseColor(base_color_one));
        student_gender=(TextView)findViewById(R.id.student_profile_gender);
        student_gender.setTextColor(Color.parseColor(base_color_one));
        student_mobile=(TextView)findViewById(R.id.user_mobile_red);
        student_mobile.setTextColor(Color.parseColor(base_color_one));
        student_name_profile=(TextView)findViewById(R.id.student_name_profile_red);
        student_name_profile.setTextColor(Color.parseColor(base_color_one));
        profile_background=findViewById(R.id.profile_background);
        logout_image=findViewById(R.id.logout_image);
        mobile_profile_image=findViewById(R.id.mobile_profile_image);
        name_profile_image=findViewById(R.id.name_profile_image);
        email_profile_image=findViewById(R.id.email_profile_image);
        gender_profile_image=findViewById(R.id.gender_profile_image);
        Dob_profile_image=findViewById(R.id.Dob_profile_image);
        address_profile_image=findViewById(R.id.address_profile_image);
        getDynamicData();
        themeDynamicAdd();
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

    private void themeDynamicAdd() {

        //Bitmap myImage = getBitmapFromURL(image);
        try {
            mobile_shrd=AarambhThemeSharedPrefreence.loadMobileIconFromPreference(this);
            mobile_bitmap=getBitmapFromURL(mobile_shrd);
            Drawable dr = new BitmapDrawable((mobile_bitmap));
            mobile_profile_image.setBackgroundDrawable(dr);

        }catch (Exception e){
            e.printStackTrace();
        }
        try {
            name_shrd=AarambhThemeSharedPrefreence.loadUserIconFromPreference(this);
            name_bitmap=getBitmapFromURL(name_shrd);
            Drawable dr = new BitmapDrawable((name_bitmap));
            name_profile_image.setBackgroundDrawable(dr);

        }catch (Exception e){
            e.printStackTrace();
        }
        try {
            email_shrd=AarambhThemeSharedPrefreence.loadEmailIconFromPreference(this);
            email_bitmap=getBitmapFromURL(email_shrd);
            Drawable dr = new BitmapDrawable((email_bitmap));
            email_profile_image.setBackgroundDrawable(dr);

        }catch (Exception e){
            e.printStackTrace();
        }
        try {
            gender_shrd=AarambhThemeSharedPrefreence.loadGenderIconFromPreference(this);
            gender_bitmap=getBitmapFromURL(gender_shrd);
            Drawable dr = new BitmapDrawable((gender_bitmap));
            gender_profile_image.setBackgroundDrawable(dr);

        }catch (Exception e){
            e.printStackTrace();
        }

        try {
            Dob_shrd=AarambhThemeSharedPrefreence.loadDOBIconFromPreference(this);
            Dob_bitmap=getBitmapFromURL(Dob_shrd);
            Drawable dr = new BitmapDrawable((Dob_bitmap));
            Dob_profile_image.setBackgroundDrawable(dr);

        }catch (Exception e){
            e.printStackTrace();
        }
        try {
            address_shrd=AarambhThemeSharedPrefreence.loadAddressIconFromPreference(this);
            address_bitmap=getBitmapFromURL(address_shrd);
            Drawable dr = new BitmapDrawable((address_bitmap));
            address_profile_image.setBackgroundDrawable(dr);

        }catch (Exception e){
            e.printStackTrace();
        }

        try {
            logout_shrd=AarambhThemeSharedPrefreence.loadLogOutIconFromPreference(this);
            logout_bitmap=getBitmapFromURL(logout_shrd);
            Drawable dr = new BitmapDrawable((logout_bitmap));
            logout_image.setBackgroundDrawable(dr);

        }catch (Exception e){
            e.printStackTrace();
        }
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
        profile_background.setBackground(gd);
    }

    public void getChannelPlayListVideos() {
        //String URL = "https://www.googleapis.com/youtube/v3/search?part=snippet&channelId=UCAfot-yupAQvVqAuZw-Pctg&maxResults=50&key=AIzaSyDFPE3uXcXmik1L9FEDVHuwcA8NiHw0WBk";
        String URL = "https://www.googleapis.com/youtube/v3/playlists?part=snippet&channelId=UCAfot-yupAQvVqAuZw-Pctg&maxResults=50&key=AIzaSyDFPE3uXcXmik1L9FEDVHuwcA8NiHw0WBk";
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

                        String class_id = AarambhSharedPreference.loadClassIdFromPreference(ProfileShowActivity.this);
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
                    Log.e("classId", AarambhSharedPreference.loadClassIdFromPreference(ProfileShowActivity.this));

                    switch (AarambhSharedPreference.loadClassIdFromPreference(ProfileShowActivity.this)) {
                        case "1":
                            //btn_login.setClickable(false);
                            Intent intent = new Intent(ProfileShowActivity.this, DashBoardActivity.class);
                            //Log.e("6thDetails", String.valueOf(ytt6th.getSubject().size()));
                            intent.putExtra("6thDetails", ytt6th);
                            intent.putExtra("PlayListDetails", playListArrayList);
                            startActivity(intent);
                            //progressdialog.dismiss();
                            break;
                        case "2":
                            //btn_login.setClickable(false);
                            Intent intent1 = new Intent(ProfileShowActivity.this, DashBoardActivity.class);
                            intent1.putExtra("7thDetails", ytt7th);
                            intent1.putExtra("PlayListDetails", playListArrayList);
                            startActivity(intent1);
                            //progressdialog.dismiss();
                            break;
                        case "3":
                            //btn_login.setClickable(false);
                            Intent intent2 = new Intent(ProfileShowActivity.this, DashBoardActivity.class);
                            intent2.putExtra("8thDetails", ytt8th);
                            intent2.putExtra("PlayListDetails", playListArrayList);
                            startActivity(intent2);
                            //progressdialog.dismiss();
                            break;
                        default:
                            //btn_login.setClickable(false);
                            Intent intent3 = new Intent(ProfileShowActivity.this, DashBoardActivity.class);
                            intent3.putExtra("6thDetails", ytt6th);
                            intent3.putExtra("PlayListDetails", playListArrayList);
                            startActivity(intent3);
                            //progressdialog.dismiss();
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
