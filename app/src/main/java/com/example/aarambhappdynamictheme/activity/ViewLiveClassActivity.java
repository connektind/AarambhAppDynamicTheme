package com.example.aarambhappdynamictheme.activity;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.os.Handler;
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
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.aarambhappdynamictheme.R;
import com.example.aarambhappdynamictheme.adapter.LiveViewAdapter;
import com.example.aarambhappdynamictheme.adapter.SubjectNameAdapter;
import com.example.aarambhappdynamictheme.model.SchoolModel;
import com.example.aarambhappdynamictheme.model.StudentDetailModel;
import com.example.aarambhappdynamictheme.model.YouTubePlayList;
import com.example.aarambhappdynamictheme.model.YouTubeTitle;
import com.example.aarambhappdynamictheme.sheardPrefrence.AarambhSharedPreference;
import com.example.aarambhappdynamictheme.sheardPrefrence.AarambhThemeSharedPrefreence;
import com.example.aarambhappdynamictheme.textGradient.CircleTransform;
import com.example.aarambhappdynamictheme.util.CommonUtilities;
import com.example.aarambhappdynamictheme.util.Global;
import com.example.aarambhappdynamictheme.util.VolleySingleton;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.navigation.NavigationView;

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

public class ViewLiveClassActivity extends AppCompatActivity {

    RecyclerView live_class_view;
    LiveViewAdapter adapter;
    String base_image,transparent_color,base_color_one,base_color_two,class_id;
    ArrayList<String> you_Subject, you_chapter, you_video;
    SubjectNameAdapter subjectNameAdapter;
    YouTubeTitle sixth_Details,seventh_details,eighth_details;
    ArrayList<YouTubePlayList> getPlayListDetails_sixth,getPlayListDetails_seventh,getPlayListDetails_eigth;
    LinearLayout mainLinear;
    Bitmap Background,menu_bitmap;
    Toolbar toolbar;
    ImageView imgProfile;

    DrawerLayout drawer;
    private static final String TAG_HOME = "home";
    public static String CURRENT_TAG = TAG_HOME;
    NavigationView navigationView;
    private View navHeader;
    ImageView navigation_cancle_btn,notification_bell;
    TextView logout,student_name_navdra,bookmark,notification,student_class,
            shareapp,parent_connect,contact_us,subscribe_now,terms_condition,student_name_red_navdra,student_grade;
    ProgressDialog progressdialog;


    SchoolModel schoolModelSchool, schoolModelAarambh;
    StudentDetailModel studentDetailModel;
    ArrayList<SchoolModel> schoolModelSchoolList, schoolModelAarambhList;
    boolean doubleBackToExitPressedOnce = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_live_class);
        class_id= AarambhSharedPreference.loadClassIdFromPreference(this);
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        base_image= AarambhThemeSharedPrefreence.loadBaseImageFromPreference(this);
        transparent_color=AarambhThemeSharedPrefreence.loadBaseImageTransparentFromPreference(this);
        Log.e("transprent",transparent_color);

        base_color_one=AarambhThemeSharedPrefreence.loadBaseColorOneFromPreference(this);
        base_color_two=AarambhThemeSharedPrefreence.loadBaseColorTwoFromPreference(this);
        Log.e("iamge_dash",AarambhSharedPreference.loadStudentProfileFromPreference(this));
        //checkOrientation();
//        try {
//            Log.e("profile", String.valueOf(imgProfile));
//        }catch (Exception e){
//            e.printStackTrace();
//        }
        Log.e("6thclasswalipostion",class_id);

        init();
        callGetInformationApi();

        listener();
        loadNavHeader();
        //callGetInformationApi();

    }

    public void init(){

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        mainLinear = findViewById(R.id.mainLinear);
        live_class_view = findViewById(R.id.live_class_view);
        student_name_red_navdra = findViewById(R.id.student_name_red_navdra);
       // student_grade = findViewById(R.id.student_grade);

        try {
            student_name_red_navdra.setText(AarambhSharedPreference.loadStudentNameFromPreference(this));
            student_name_red_navdra.setTextColor(Color.parseColor(base_color_one));
        }catch (Exception e){e.printStackTrace();}
        schoolModelSchoolList = new ArrayList<>();
        schoolModelAarambhList = new ArrayList<>();
        drawer = (DrawerLayout) findViewById(R.id.drawerLayout);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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

        notification_bell=(ImageView)findViewById(R.id.notification_bell);
        navigationView=findViewById(R.id.nav_view);
        navHeader = navigationView.getHeaderView(0);
        imgProfile = (ImageView) navHeader.findViewById(R.id.img_profile_1);
        bookmark=(TextView)navHeader.findViewById(R.id.bookmark_red);
        notification=(TextView)navHeader.findViewById(R.id.notification_red);
        shareapp=(TextView)navHeader.findViewById(R.id.shareapp_red);
        parent_connect=(TextView)navHeader.findViewById(R.id.parent_connect_red);
        contact_us=(TextView)navHeader.findViewById(R.id.contactus_red);
        subscribe_now=(TextView)navHeader.findViewById(R.id.subscribe_now_red);
        terms_condition=(TextView)navHeader.findViewById(R.id.terms_red);
        navigation_cancle_btn=(ImageView)navHeader. findViewById(R.id.navi_cancle_btn);
        student_class=navHeader.findViewById(R.id.student_class);
        student_class.setText(AarambhSharedPreference.loadClassNameFromPreference(this)+" Grade");
        logout=(TextView)navHeader. findViewById(R.id.logout);
        imgProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.closeDrawers();
                Intent intent=new Intent(ViewLiveClassActivity.this, ProfileShowActivity.class);
                startActivity(intent);
            }
        });
        student_name_navdra = (TextView) navHeader.findViewById(R.id.student_name_red_navdra);
        student_name_navdra.setText(AarambhSharedPreference.loadStudentNameFromPreference(this));
        student_name_navdra.setTextColor(Color.parseColor(base_color_one));


        getDynamicData();
        themeDynamicAdd();


    }


    public void listener(){

        navigation_cancle_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.closeDrawers();
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ViewLiveClassActivity.this);
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
                        Intent intent = new Intent(ViewLiveClassActivity.this, LoginActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        AarambhSharedPreference.saveClassIdToPreference(ViewLiveClassActivity.this, "NA");
                        AarambhSharedPreference.saveStudentNameToPreference(ViewLiveClassActivity.this, "NA");
                        AarambhSharedPreference.saveStudentProfileToPreference(ViewLiveClassActivity.this,"NA");
                        AarambhSharedPreference.saveUserTokenToPreference(ViewLiveClassActivity.this,"NA");
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
//                Intent intent=new Intent(ViewLiveClassActivity.this, BookMarkActivity.class);
//                intent.putExtra("SubjectList",you_Subject);
//                startActivity(intent);
                Toast.makeText(ViewLiveClassActivity.this, "Currently No BookMark.", Toast.LENGTH_SHORT).show();

            }
        });
        notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.closeDrawers();
                Intent intent=new Intent(ViewLiveClassActivity.this, NotificationActivity.class);
                startActivity(intent);
            }
        });
        contact_us.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.closeDrawers();
                Intent intent=new Intent(ViewLiveClassActivity.this, ContactUsActivity.class);
                startActivity(intent);
            }
        });
        notification_bell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.closeDrawers();
                notification_bell.setClickable(false);
                Intent intent=new Intent(ViewLiveClassActivity.this,NotificationActivity.class);
                startActivity(intent);
            }
        });
        subscribe_now.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.closeDrawers();
                Intent intent=new Intent(ViewLiveClassActivity.this, SubscribeNowActivity.class);
                startActivity(intent);
            }
        });
        terms_condition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.closeDrawers();
                Intent intent=new Intent(ViewLiveClassActivity.this, TermsCondiationActivity.class);
                startActivity(intent);
            }
        });
        parent_connect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.closeDrawers();
                Intent intent=new Intent(ViewLiveClassActivity.this, ParentContectUsActivity.class);
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
                i.putExtra(Intent.EXTRA_TEXT,   " " + link);
                i.putExtra(Intent.ACTION_VIEW,R.layout.share_app_redtheme);
                startActivity(Intent.createChooser(i, "Share link:"));
            }
        });


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
//        dashborad_nav_profile.setBackground(gd);
//        edit_dashboard_nav.setBackground(gd1);
    }
    private void themeDynamicAdd() {

        //Bitmap myImage = getBitmapFromURL(image);
        try {
            Background = getBitmapFromURL(base_image);
            Drawable dr = new BitmapDrawable((Background));
            mainLinear.setBackgroundDrawable(dr);
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


//    private void themeDynamicAdd() {
//
//        //Bitmap myImage = getBitmapFromURL(image);
//        try {
//            Background=getBitmapFromURL(base_image);
//            Drawable dr = new BitmapDrawable((Background));
//            mainLinear.setBackgroundDrawable(dr);
//            // navigationView.setBackgroundDrawable(dr);
//
//        }catch (Exception e){
//            e.printStackTrace();
//        }
////        try {
////            Bitmap Background1=getBitmapFromURL(base_image);
////            Drawable dr1 = new BitmapDrawable((Background1));
////            navigationView.setBackgroundDrawable(dr1);
////
////        }catch (Exception e){
////            e.printStackTrace();
////        }
//        try {
//            String menu=AarambhThemeSharedPrefreence.loadMenuNavigationIconFromPreference(this);
//            menu_bitmap=getBitmapFromURL(menu);
//            Drawable dr = new BitmapDrawable((menu_bitmap));
//            //  background_dashboard.setBackgroundDrawable(dr);
//            if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
//                getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(false);
//                toolbar.setNavigationIcon(dr);
//
//            }else {
//                getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(false);
//                toolbar.setNavigationIcon(dr);
//            }
//
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//
//
//
//    }

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

    public void callGetInformationApi()
    {
        if (!CommonUtilities.isOnline(ViewLiveClassActivity.this)) {
            Toast.makeText(ViewLiveClassActivity.this, "Please On Your Internet Connection", Toast.LENGTH_LONG).show();
            return;
        }
        progressdialog = new ProgressDialog(this);
        progressdialog.setCancelable(false);
        progressdialog.setMessage("Loading...");
        progressdialog.setTitle("Wait...");
        progressdialog.show();
        final String string_json = "Result";
        String url = Global.WEBBASE_URL + "getInformation";
        StringRequest stringRequest=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                String res = response.toString();
                parseResponseGetInformation(res);
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
                        parseResponseGetInformation(response.toString());
                    } catch (UnsupportedEncodingException e1) {
                        // Couldn't properly decode data to string
                        e1.printStackTrace();
                    }
                }

            }
        }){

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
                headers.put("Authorization", "Bearer "+ AarambhSharedPreference.loadUserTokenFromPreference(ViewLiveClassActivity.this));
                return headers;
            }
        };
        VolleySingleton.getInstance(ViewLiveClassActivity.this).addToRequestQueue(stringRequest, string_json);



    }

    public void parseResponseGetInformation(String response)
    {
        Log.e("Response", response);
        progressdialog.dismiss();
        try {
            SchoolModel schoolModelSchool;
            StudentDetailModel studentDetailModel = null;

            JSONObject jsonObject1 = new JSONObject(response);
            String status=jsonObject1.getString("status");
            boolean success = jsonObject1.getBoolean("success");
            if(success==false){
                String error=jsonObject1.getString("error");
                Toast.makeText(this,error+"",Toast.LENGTH_LONG).show();
            }
            else if (success==true) {
                String message = jsonObject1.getString("message");
                if (message.equalsIgnoreCase("No Data Found.")) {
                    Toast.makeText(this, message + "", Toast.LENGTH_LONG).show();
                }
                else if (message.equalsIgnoreCase("Data Found")) {

                    JSONObject jsonObjectData = jsonObject1.getJSONObject("data");
                    JSONArray studentArray = jsonObjectData.getJSONArray("student");
                    JSONArray schoolArray = jsonObjectData.getJSONArray("school");

                    for (int i = 0; i < studentArray.length(); i++) {
                        Log.e("class_lenth", String.valueOf(i));
                        JSONObject jsonObject = studentArray.getJSONObject(i);
                        String StudentId = jsonObject.getString("StudentId");
                        String StudentName = jsonObject.getString("StudentName");
                        String StudentGender = jsonObject.getString("StudentGender");
                        String StudentMobile = jsonObject.getString("StudentMobile");
                        String StudentUsername = jsonObject.getString("StudentUsername");
                        String StudentDOB = jsonObject.getString("StudentDOB");
                        String StudentDORegis = jsonObject.getString("StudentDORegis");
                        String StudentPassword = jsonObject.getString("StudentPassword");
                        String StudentImage = jsonObject.getString("StudentImage");
//                        if (StudentImage.equals("null")) {
//                            AarambhSharedPreference.saveStudentProfileToPreference(this, "NA");
//                        } else {
//                            AarambhSharedPreference.saveStudentProfileToPreference(this, StudentImage);
//                        }
                        String ParentId = jsonObject.getString("ParentId");
                        String ClassId = jsonObject.getString("ClassId");
                        String StatusId = jsonObject.getString("StatusId");
                        String CreatedById = jsonObject.getString("CreatedById");
                        String ModifiedById = jsonObject.getString("ModifiedById");
                        String CreationDate = jsonObject.getString("CreationDate");
                        String ModificationDate = jsonObject.getString("ModificationDate");
                        String ParentName = jsonObject.getString("ParentName");

                        studentDetailModel = new StudentDetailModel(StudentId, StudentName, StudentGender, StudentMobile, StudentUsername, StudentDOB, StudentDORegis, StudentPassword, StudentImage, ParentId, ClassId, StatusId, CreatedById, ModifiedById, CreationDate, ModificationDate);
                        studentDetailModel.setParentName(ParentName);
                    }

                    for (int i = 0; i < schoolArray.length(); i++) {
                        JSONObject school = schoolArray.getJSONObject(i);
                        String SchoolId = school.getString("SchoolId");
                        String SchoolName = school.getString("SchoolName");
                        String SchoolAddress = school.getString("SchoolAddress");
                        String SchoolPhone = school.getString("SchoolPhone");
                        String SchoolBoard = school.getString("SchoolBoard");
                        String SchoolMail = school.getString("SchoolMail");
                        String SchoolLogo = school.getString("SchoolLogo");
                        AarambhThemeSharedPrefreence.saveSchoolLogoPreference(this,Global.school_pic+SchoolLogo);

                        String YoutubeChannelId = school.getString("YoutubeChannelId");
                        String YoutubeChannelKey = school.getString("YoutubeChannelKey");
                        String IncludeAarambh = school.getString("IncludeAarambh");
                        String Username = school.getString("Username");
                        String Password = school.getString("Password");
                        String FirebaseKey = school.getString("FirebaseKey");
                        String FirebaseSecretKey = school.getString("FirebaseSecretKey");

                        schoolModelSchool = new SchoolModel(SchoolId, SchoolName, SchoolAddress, SchoolPhone,
                                SchoolBoard, SchoolMail, SchoolLogo, YoutubeChannelId, YoutubeChannelKey,
                                IncludeAarambh, Username, Password, FirebaseKey, FirebaseSecretKey);

                        schoolModelSchoolList.add(schoolModelSchool);

                    }

                    if (AarambhSharedPreference.loadClassIdFromPreference(this).equalsIgnoreCase("3")) {
                        try {
                            eighth_details = (YouTubeTitle) getIntent().getSerializableExtra("8thDetails");
                            getPlayListDetails_eigth = (ArrayList<YouTubePlayList>) getIntent().getSerializableExtra("PlayListDetails");
                            adapter = new LiveViewAdapter(ViewLiveClassActivity.this,eighth_details,getPlayListDetails_eigth,studentDetailModel,schoolModelSchoolList);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    else if (AarambhSharedPreference.loadClassIdFromPreference(this).equalsIgnoreCase("2")) {
                        try {
                            seventh_details = (YouTubeTitle) getIntent().getSerializableExtra("7thDetails");
                            getPlayListDetails_seventh = (ArrayList<YouTubePlayList>) getIntent().getSerializableExtra("PlayListDetails");
                            adapter = new LiveViewAdapter(ViewLiveClassActivity.this,seventh_details,getPlayListDetails_seventh,studentDetailModel,schoolModelSchoolList);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    else if (AarambhSharedPreference.loadClassIdFromPreference(this).equalsIgnoreCase("1")) {
                        try {
                            sixth_Details = (YouTubeTitle) getIntent().getSerializableExtra("6thDetails");
                            getPlayListDetails_sixth = (ArrayList<YouTubePlayList>) getIntent().getSerializableExtra("PlayListDetails");
                            adapter = new LiveViewAdapter(ViewLiveClassActivity.this,sixth_Details,getPlayListDetails_sixth,studentDetailModel,schoolModelSchoolList);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    try {
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
                        live_class_view.setLayoutManager(linearLayoutManager);
                        live_class_view.setAdapter(adapter);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            finish();
            finishAffinity();
            return;
        }
        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(getApplicationContext(), "Tap again to exit",Toast.LENGTH_SHORT).show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }

    private void loadNavHeader() {
        if (AarambhSharedPreference.loadStudentProfileFromPreference(this).equals("NA")){
            Glide.with(this).load(urlProfileImg)
                    .crossFade()
                    .thumbnail(0.5f)
                    .bitmapTransform(new CircleTransform(this))
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(imgProfile);
        }else {
            Glide.with(this).load(urlStudentImg+AarambhSharedPreference.loadStudentProfileFromPreference(this))
                    .crossFade()
                    .thumbnail(0.5f)
                    .bitmapTransform(new CircleTransform(this))
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(imgProfile);
        }
    }
}