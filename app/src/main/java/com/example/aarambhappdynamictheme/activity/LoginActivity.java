package com.example.aarambhappdynamictheme.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatDelegate;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.aarambhappdynamictheme.R;
import com.example.aarambhappdynamictheme.model.ThemeModel;
import com.example.aarambhappdynamictheme.model.VideoDetails;
import com.example.aarambhappdynamictheme.model.YouTubePlayList;
import com.example.aarambhappdynamictheme.model.YouTubeTitle;
import com.example.aarambhappdynamictheme.sheardPrefrence.AarambhSharedPreference;
import com.example.aarambhappdynamictheme.sheardPrefrence.AarambhThemeSharedPrefreence;
import com.example.aarambhappdynamictheme.util.CommonUtilities;
import com.example.aarambhappdynamictheme.util.Global;
import com.example.aarambhappdynamictheme.util.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Connection;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    TextView signup, forgot_password;
    EditText login_mobile_no, login_password;
    Button btn_login;
    ProgressDialog progressdialog;
    ArrayList<VideoDetails> videoDetailsArrayList;
    public static final java.util.Random ID = new java.util.Random();
    public static final int range = 4;
    public static final int random_num = ID.nextInt(range);
    public static final String[] youTubeApiKey = {"AIzaSyAja-68gRGjnbSCsR5U1IuMKpUUO8rABmo", "AIzaSyAja-68gRGjnbSCsR5U1IuMKpUUO8rABmo", "AIzaSyAja-68gRGjnbSCsR5U1IuMKpUUO8rABmo", "AIzaSyAja-68gRGjnbSCsR5U1IuMKpUUO8rABmo"};
    public static final String youTubeApiKeyNew = youTubeApiKey[random_num];
    ArrayList<ThemeModel> themeModelArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        checkOrientation();
        init();
        listner();
        // getThemeData();
    }
//    public static String AssetJSONFile (String filename, Context context) throws IOException {
//        AssetManager manager = context.getAssets();
//        InputStream file = manager.open(filename);
//        byte[] formArray = new byte[file.available()];
//        file.read(formArray);
//        file.close();
//
//        return new String(formArray);
//    }

    public String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = getAssets().open("formules.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    private void getThemeData() {
        try {
            JSONObject obj = new JSONObject(loadJSONFromAsset());
            JSONArray m_jArry = obj.getJSONArray("BaseTheme");
//            ArrayList<HashMap<String, String>> formList = new ArrayList<HashMap<String, String>>();
//            HashMap<String, String> m_li;
            ThemeModel themeModel;
            for (int i = 0; i < m_jArry.length(); i++) {
                JSONObject jo_inside = m_jArry.getJSONObject(i);
                Log.d("Details-->", jo_inside.getString("BaseImage"));
                String Class_id = jo_inside.getString("Class_ID");
                String BaseImage = jo_inside.getString("BaseImage");
                AarambhThemeSharedPrefreence.saveBaseImageToPreference(this, BaseImage);
                String Transpent_Color = jo_inside.getString("Transpent_Color");
                AarambhThemeSharedPrefreence.saveBaseImageTransparentToPreference(this, Transpent_Color);
                String Base_color_one = jo_inside.getString("Base_color_one");
                AarambhThemeSharedPrefreence.saveBaseColorOnePreference(this, Base_color_one);
                String base_color_two = jo_inside.getString("base_color_two");
                AarambhThemeSharedPrefreence.saveBaseColorTwoPreference(this, base_color_two);
                themeModel = new ThemeModel(Class_id, BaseImage, Transpent_Color, Base_color_one, base_color_two);
                themeModelArrayList.add(themeModel);
            }
            Log.e("list_theme", String.valueOf(themeModelArrayList));
            //AarambhThemeSharedPrefreence.saveBaseImageToPreference(this,);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    private void checkOrientation()
    {
        if (getResources().getBoolean(R.bool.portrait_only)) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
    }

    private void init() {
        themeModelArrayList = new ArrayList<>();
        forgot_password = (TextView) findViewById(R.id.forgot_password);
        signup = findViewById(R.id.signup);
        login_mobile_no = findViewById(R.id.login_mobile_no);
        login_password = findViewById(R.id.login_password);
        btn_login = findViewById(R.id.btn_login);
        progressdialog = new ProgressDialog(getApplicationContext());
        progressdialog.setMessage("Please Wait....");
        progressdialog.setCancelable(false);
        videoDetailsArrayList = new ArrayList<>();
    }

    private void listner() {
        forgot_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                forgot_password.setClickable(false);
                Intent intent = new Intent(LoginActivity.this, ForgetPasswordActivity.class);
                startActivity(intent);
            }
        });
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String mobile = login_mobile_no.getText().toString().trim();
                final String password = login_password.getText().toString().trim();
                if (mobile.isEmpty()) {
                    login_mobile_no.setError("Please Enter Username/Mobile Number");
                    login_mobile_no.requestFocus();
                } else if (password.length() < 6) {
                    login_password.setError("Please Enter Password");
                    login_password.requestFocus();
                } else {
//                    Intent intent=new Intent(LoginActivity.this, ParentDashBoard.class);
//                    startActivity(intent);
                    getLogin(mobile, password);
                }
            }
        });
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup.setClickable(false);
                Intent it = new Intent(getApplicationContext(), ParentRegisterActivity.class);
                startActivity(it);
            }
        });
    }

    private void getLogin(final String mobile, final String password) {
        if (!CommonUtilities.isOnline(LoginActivity.this)) {
            Toast.makeText(LoginActivity.this, "Please On Your Internet Connection", Toast.LENGTH_LONG).show();
            return;
        }
        progressdialog = new ProgressDialog(this);
        progressdialog.setCancelable(false);
        progressdialog.setMessage("Loading...");
        progressdialog.setTitle("Wait...");
        progressdialog.show();

        String url = Global.WEBBASE_URL + "appLoginNew";

        JSONObject params = new JSONObject();
        String mRequestBody = "";

        try {

            params.put("value", mobile);
            params.put("password", password);
        } catch (Exception e) {
            e.printStackTrace();
        }
        mRequestBody = params.toString();
        Log.e("Request Body", mRequestBody);
        final String finalMRequestBody = mRequestBody;


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, params, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                String res = response.toString();
                parseResponseLoginUser(res, progressdialog);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                //pd.dismiss();
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
                        parseResponseLoginUser(response.toString(), progressdialog);

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
                return headers;
            }

            @Override
            public Priority getPriority() {
                return Priority.IMMEDIATE;
            }

            // Adding request to request queue
            @Override
            public byte[] getBody() {
                try {
                    return finalMRequestBody == null ? null : finalMRequestBody.getBytes("utf-8");
                } catch (UnsupportedEncodingException uee) {
                    VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", finalMRequestBody, "utf-8");
                    return null;
                }
            }

        };

        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
                Global.WEBSERVICE_TIMEOUT_VALUE_IN_MILLIS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        ));
        jsonObjectRequest.setShouldCache(false);
        // Adding request to request queue
        VolleySingleton.getInstance(LoginActivity.this).addToRequestQueue(jsonObjectRequest);
    }

    private void parseResponseLoginUser(String response, ProgressDialog progressdialog) {
        try {
            //getting the whole json object from the response 4878704040
            JSONObject obj = new JSONObject(response);
            Log.e("login_resp", String.valueOf(obj));
            int status = obj.getInt("status");
            boolean success = obj.getBoolean("success");

            if (success == false) {
                String error = obj.getString("error");
                if (error.equalsIgnoreCase("Record does not exists.")) {
                    Toast.makeText(this, error + "", Toast.LENGTH_LONG).show();
                    progressdialog.dismiss();
                } else if (error.equalsIgnoreCase("Password Mismatch.")) {
                    Toast.makeText(this, error + "", Toast.LENGTH_LONG).show();
                    progressdialog.dismiss();
                } else {
                    Toast.makeText(this, error + "", Toast.LENGTH_LONG).show();
                    progressdialog.dismiss();
                }

            } else if (success == true) {
                String msg = obj.getString("Message");
                String token = "";
                boolean isParent = false;
                try {
                    isParent = obj.getBoolean("isParent");
                    token = obj.getString("Token");
                    Log.e("token_user", token);

                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (msg.equalsIgnoreCase("Login Successfull")) {
                    if (isParent == true) {
                        progressdialog.dismiss();
                        try {
                            JSONArray obj_array = obj.getJSONArray("Data");
                            Log.e("json_array:------", obj_array.toString());
                            for (int i = 0; i < obj_array.length(); i++) {
                                JSONObject jsonObject = obj_array.getJSONObject(i);
                                String ParentId = jsonObject.getString("ParentId");
                                String ParentName = jsonObject.getString("ParentName");
                                String ParentMobile = jsonObject.getString("ParentMobile");
                                String ParentPassword = jsonObject.getString("ParentEmail");
                                String ParentAddress = jsonObject.getString("ParentAddress");
                                String FBToken = jsonObject.getString("FBToken");
                                String SchoolId = jsonObject.getString("SchoolId");
                                AarambhSharedPreference.saveParentIDToPreference(this, ParentId);
                                AarambhSharedPreference.saveStudentNameToPreference(this, ParentName);
                                AarambhSharedPreference.saveStudentMobileToPreference(this, ParentMobile);
                                AarambhSharedPreference.saveUserTokenToPreference(this, token);
                                AarambhSharedPreference.saveSchoolIdToPreference(this,SchoolId);
                            }
                            Intent intent = new Intent(LoginActivity.this, ParentDashBoardActivity.class);
                            startActivity(intent);

                            //getYoutubeDataUrl();
                            //getChannelVideos();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else if (isParent == false) {
                        progressdialog.dismiss();
                        try {
                            JSONArray obj_array = obj.getJSONArray("StudentData");
                            Log.e("json_array:------", obj_array.toString());
                            for (int i = 0; i < obj_array.length(); i++) {
                                JSONObject jsonObject = obj_array.getJSONObject(i);
                                String StudentId = jsonObject.getString("StudentId");
                                String StudentName = jsonObject.getString("StudentName");
                                String StudentGender = jsonObject.getString("StudentGender");
                                String StudentUsername = jsonObject.getString("StudentUsername");
                                String StudentMobile = jsonObject.getString("StudentMobile");
                                // String StudentEmail = jsonObject.getString("StudentEmail");
                                //String StudentAddress = jsonObject.getString("StudentAddress");
                                // String StudentCity = jsonObject.getString("StudentCity");
                                String StudentDOB = jsonObject.getString("StudentDOB");
                                String StudentImage = jsonObject.getString("StudentImage");
                                String SchoolId = jsonObject.getString("SchoolId");
                                if (StudentImage.equals("null")) {
                                    AarambhSharedPreference.saveStudentProfileToPreference(this, "NA");
                                } else {
                                    AarambhSharedPreference.saveStudentProfileToPreference(this, StudentImage);
                                }
                                String ParentId = jsonObject.getString("ParentId");
                                String ClassId = jsonObject.getString("ClassId");
                                String StudentClass=jsonObject.getString("StudentClass");
                                Log.e("class_id_login", ClassId);
                                AarambhSharedPreference.saveClassNameToPreference(this,StudentClass);

                                AarambhSharedPreference.saveClassIdToPreference(this, ClassId);
                                AarambhSharedPreference.saveStudentNameToPreference(this, StudentName);
                                AarambhSharedPreference.saveUserTokenToPreference(this, token);
                                AarambhSharedPreference.saveStudentIdToPreference(this, StudentId);
                                AarambhSharedPreference.saveStudentMobileToPreference(this, StudentMobile);
                                AarambhSharedPreference.saveSchoolIdToPreference(this,SchoolId);
                                //getYoutubeDataUrl();
                                //getChannelVideos();
                                Intent intent = new Intent(LoginActivity.this, ViewLiveClassActivity.class);
                                startActivity(intent);

                                //getChannelPlayListVideos();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        try {
                            JSONArray obj_array = obj.getJSONArray("ClassTheme");
                            Log.e("json_array:------", obj_array.toString());
                            for (int i = 0; i < obj_array.length(); i++) {
                                JSONObject jsonObject = obj_array.getJSONObject(i);
                                String ClassThemeId = jsonObject.getString("ClassThemeId");
                                String ClassId = jsonObject.getString("ClassId");
                                String BaseColor = jsonObject.getString("BaseColor");
                                String BackgroundImage = jsonObject.getString("BackgroundImage");
                                AarambhThemeSharedPrefreence.saveBaseImageToPreference(this, Global.background_url + BackgroundImage);
                                String GradientOne = jsonObject.getString("GradientOne");
                                AarambhThemeSharedPrefreence.saveBaseColorOnePreference(this, GradientOne);
                                String GradientTwo = jsonObject.getString("GradientTwo");
                                AarambhThemeSharedPrefreence.saveBaseColorTwoPreference(this, GradientTwo);
                                String BackArrow = jsonObject.getString("BackArrow");
                                AarambhThemeSharedPrefreence.saveBackArrowIconToPreference(this, Global.background_url + BackArrow);
                                String TransparentCode = jsonObject.getString("TransparentCode");
                                AarambhThemeSharedPrefreence.saveBaseImageTransparentToPreference(this, TransparentCode);
                                String BookmarkIcon = jsonObject.getString("BookmarkIcon");
                                AarambhThemeSharedPrefreence.saveBookMarkImageToPreference(this, Global.background_url + BookmarkIcon);
                                String NotificationIcon = jsonObject.getString("NotificationIcon");
                                AarambhThemeSharedPrefreence.saveNotificationIconToPreference(this, Global.background_url + NotificationIcon);
                               String NotificationSmallIcon=jsonObject.getString("NotificationSmallIcon");
                               AarambhThemeSharedPrefreence.saveNotificationImageToPreference(this,Global.background_url+NotificationSmallIcon);
                              String LiveArrow=jsonObject.getString("LiveArrow");
                              AarambhThemeSharedPrefreence.saveLiveArrowPreference(this,Global.background_url+LiveArrow);
                                String FontFamily1 = jsonObject.getString("FontFamily1");
                                String FontFamily2 = jsonObject.getString("FontFamily2");
                                String CameraIcon = jsonObject.getString("CameraIcon");
                                AarambhThemeSharedPrefreence.saveCameraIconToPreference(this, Global.background_url + CameraIcon);
                                String EmailIcon = jsonObject.getString("EmailIcon");
                                AarambhThemeSharedPrefreence.saveEmailIconToPreference(this, Global.background_url + EmailIcon);
                                String MobileIcon = jsonObject.getString("MobileIcon");
                                AarambhThemeSharedPrefreence.saveMobileIconToPreference(this, Global.background_url + MobileIcon);
                                String UserIcon = jsonObject.getString("UserIcon");
                                AarambhThemeSharedPrefreence.saveUserIconToPreference(this, Global.background_url + UserIcon);
                                String GenderIcon = jsonObject.getString("GenderIcon");
                                AarambhThemeSharedPrefreence.saveGenderIconToPreference(this, Global.background_url + GenderIcon);
                                String DOBIcon = jsonObject.getString("DOBIcon");
                                AarambhThemeSharedPrefreence.saveDOBIconToPreference(this, Global.background_url + DOBIcon);
                                String AddressIcon = jsonObject.getString("AddressIcon");
                                AarambhThemeSharedPrefreence.saveAddressIconToPreference(this, Global.background_url + AddressIcon);
                                String LogoutIcon = jsonObject.getString("LogoutIcon");
                                AarambhThemeSharedPrefreence.saveLogOutIconToPreference(this, Global.background_url + LogoutIcon);
                                String NavigationMenuIcon = jsonObject.getString("NavigationMenuIcon");
                                AarambhThemeSharedPrefreence.saveMenuNavigationIconToPreference(this, Global.background_url + NavigationMenuIcon);
                                String ThankuBackground = jsonObject.getString("ThankuBackground");
                                AarambhThemeSharedPrefreence.saveThankuBackgroundToPreference(this, Global.background_url + ThankuBackground);
                                String CancelIcon = jsonObject.getString("CancelIcon");
                                AarambhThemeSharedPrefreence.saveCancleIconToPreference(this, Global.background_url + CancelIcon);
                                String StatusId = jsonObject.getString("StatusId");
                                String CreatedById = jsonObject.getString("CreatedById");
                                String ModifiedById = jsonObject.getString("ModifiedById");
                                String CreationDate = jsonObject.getString("CreationDate");
                                String ModificationDate = jsonObject.getString("ModificationDate");

//
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    Toast.makeText(this, msg + "", Toast.LENGTH_LONG).show();
                    progressdialog.dismiss();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


//            String token = "";
//            boolean isParent = false;
//            try {
//                 isParent = obj.getBoolean("isParent");
//                token = obj.getString("Token");
//                Log.e("token_user", token);
//
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//
//            if (code == 204 && msg.equals("Record does not exits.")) {
//                Toast.makeText(this, "Mobile number/Username does not exists", Toast.LENGTH_LONG).show();
//                progressdialog.dismiss();
//            } else if (code == 204 && msg.equals("Mobile Number or Password does not match")) {
//                Toast.makeText(this, "Mobile number/Username or password does not match", Toast.LENGTH_LONG).show();
//                progressdialog.dismiss();
//
//            } else if (code == 200 && msg.equals("Password Mismatch")) {
//                Toast.makeText(this, "Password mismatch.", Toast.LENGTH_LONG).show();
//                progressdialog.dismiss();
//            } else if (code == 200 && msg.equalsIgnoreCase("Login Successfull")&& isParent==false) {
//
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
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
//                                        String class_id=AarambhSharedPreference.loadClassIdFromPreference(LoginActivity.this);
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
//                                    Log.e("classId",AarambhSharedPreference.loadClassIdFromPreference(LoginActivity.this));
//
//                                    switch (AarambhSharedPreference.loadClassIdFromPreference(LoginActivity.this)) {
//                                        case "1":
//                                            btn_login.setClickable(false);
//                                            Intent intent = new Intent(LoginActivity.this, DashBoardSixthStandardActivity.class);
//                                            Log.e("6thDetails", String.valueOf(ytt6th.getSubject().size()));
//                                            intent.putExtra("6thDetails", ytt6th);
//                                            intent.putExtra("VideoDetails",video_Details_ArrayList);
//                                            startActivity(intent);
//                                            progressdialog.dismiss();
//                                            break;
//                                        case "2":
//                                            btn_login.setClickable(false);
//                                            Intent intent1 = new Intent(LoginActivity.this, DashBoardSaventhStandardActivity.class);
//                                            intent1.putExtra("7thDetails", ytt7th);
//                                            intent1.putExtra("VideoDetails",video_Details_ArrayList);
//                                            startActivity(intent1);
//                                            progressdialog.dismiss();
//                                            break;
//                                        case "3":
//                                            btn_login.setClickable(false);
//                                            Intent intent2 = new Intent(LoginActivity.this, DashBoardEighthStandardActivity.class);
//                                            intent2.putExtra("8thDetails", ytt8th);
//                                            intent2.putExtra("VideoDetails",video_Details_ArrayList);
//                                            startActivity(intent2);
//                                            progressdialog.dismiss();
//                                            break;
//                                        default:
//                                            btn_login.setClickable(false);
//                                            Intent intent3 = new Intent(LoginActivity.this, DashBoardSixthStandardActivity.class);
//                                            intent3.putExtra("6thDetails", ytt6th);
//                                            intent3.putExtra("VideoDetails",video_Details_ArrayList);
//                                            startActivity(intent3);
//                                            progressdialog.dismiss();
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
//                        String class_id=AarambhSharedPreference.loadClassIdFromPreference(LoginActivity.this);
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
//                        if(className.equalsIgnoreCase("7th")&& class_id.equalsIgnoreCase("2")&& vd.getVideoName().contains("7th")){
//                            String subject = title_arr[1];
//                            String chapter = title_arr[2];
//                            String topic = title_arr[3];
//                            Log.e("Subject", subject + ":" + chapter +" : "+ topic);
//                            subject_ArrayList.add(subject);
//                            chapter_ArrayList.add(chapter);
//                            video_ArrayList.add(video_title);
//                            video_Details_ArrayList.add(vd); //blunder mistake naina
//                            ytt7th = new YouTubeTitle(R.drawable.subj_one_gradient);
//                            ytt7th.setSubject(subject_ArrayList);
//                            //ytt.setChapter(sixth_Chapter_ArrayList);
//                            ytt7th.setVideoTitle(video_ArrayList);
//
//                        }
//                        if(className.equalsIgnoreCase("8th")&& class_id.equalsIgnoreCase("3")&& vd.getVideoName().contains("8th")){
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
//                    Log.e("classId",AarambhSharedPreference.loadClassIdFromPreference(LoginActivity.this));
//
//                    switch (AarambhSharedPreference.loadClassIdFromPreference(LoginActivity.this)) {
//                        case "1":
//                            btn_login.setClickable(false);
//                            Intent intent = new Intent(LoginActivity.this, DashBoardSixthStandardActivity.class);
//                            Log.e("6thDetails", String.valueOf(ytt6th.getSubject().size()));
//                            intent.putExtra("6thDetails", ytt6th);
//                            intent.putExtra("VideoDetails",video_Details_ArrayList);
//                            startActivity(intent);
//                            progressdialog.dismiss();
//                            break;
//                        case "2":
//                            btn_login.setClickable(false);
//                            Intent intent1 = new Intent(LoginActivity.this, DashBoardSaventhStandardActivity.class);
//                            intent1.putExtra("7thDetails", ytt7th);
//                            intent1.putExtra("VideoDetails",video_Details_ArrayList);
//                            startActivity(intent1);
//                            progressdialog.dismiss();
//                            break;
//                        case "3":
//                            btn_login.setClickable(false);
//                            Intent intent2 = new Intent(LoginActivity.this, DashBoardEighthStandardActivity.class);
//                            intent2.putExtra("8thDetails", ytt8th);
//                            intent2.putExtra("VideoDetails",video_Details_ArrayList);
//                            startActivity(intent2);
//                            progressdialog.dismiss();
//                            break;
//                        default:
//                            btn_login.setClickable(false);
//                            Intent intent3 = new Intent(LoginActivity.this, DashBoardSixthStandardActivity.class);
//                            intent3.putExtra("6thDetails", ytt6th);
//                            intent3.putExtra("VideoDetails",video_Details_ArrayList);
//                            startActivity(intent3);
//                            progressdialog.dismiss();
//                            break;
//                    }
//
////
////                    if(className.equalsIgnoreCase("6th")) {
////                        btn_login.setClickable(false);
////                        Intent intent = new Intent(LoginActivity.this, DashBoardSixthStandardActivity.class);
////                        intent.putExtra("6thDetails", ytt);
////                        startActivity(intent);
////                        progressdialog.dismiss();
////                    }
////                    if(className.equalsIgnoreCase("7th")) {
////                        btn_login.setClickable(false);
////                        Intent intent = new Intent(LoginActivity.this, DashBoardSaventhStandardActivity.class);
////                        //intent.putExtra("7thDetails", ytt);
////                        startActivity(intent);
////                        progressdialog.dismiss();
////                    }
////                    if(className.equalsIgnoreCase("8th")) {
////                        btn_login.setClickable(false);
////                        Intent intent = new Intent(LoginActivity.this, DashBoardEighthStandardActivity.class);
////                        //intent.putExtra("8thDetails", ytt);
////                        startActivity(intent);
////                        progressdialog.dismiss();
////                    }
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        finishAffinity();

    }

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

                        String class_id = AarambhSharedPreference.loadClassIdFromPreference(LoginActivity.this);
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
                    Log.e("classId", AarambhSharedPreference.loadClassIdFromPreference(LoginActivity.this));

                    switch (AarambhSharedPreference.loadClassIdFromPreference(LoginActivity.this)) {
                        case "1":
                            btn_login.setClickable(false);
                            //Intent intent = new Intent(LoginActivity.this, DashBoardActivity.class);
                            Intent intent = new Intent(LoginActivity.this, ViewLiveClassActivity.class);
                            //Log.e("6thDetails", String.valueOf(ytt6th.getSubject().size()));
                            intent.putExtra("6thDetails", ytt6th);
                            intent.putExtra("PlayListDetails", playListArrayList);
                            startActivity(intent);
                            progressdialog.dismiss();
                            break;
                        case "2":
                            btn_login.setClickable(false);
                            //Intent intent1 = new Intent(LoginActivity.this, DashBoardActivity.class);
                            Intent intent1 = new Intent(LoginActivity.this, ViewLiveClassActivity.class);
                            intent1.putExtra("7thDetails", ytt7th);
                            intent1.putExtra("PlayListDetails", playListArrayList);
                            startActivity(intent1);
                            progressdialog.dismiss();
                            break;
                        case "3":
                            btn_login.setClickable(false);
                            //Intent intent2 = new Intent(LoginActivity.this, DashBoardActivity.class);
                            Intent intent2 = new Intent(LoginActivity.this, ViewLiveClassActivity.class);
                            intent2.putExtra("8thDetails", ytt8th);
                            intent2.putExtra("PlayListDetails", playListArrayList);
                            startActivity(intent2);
                            progressdialog.dismiss();
                            break;
                        default:
                            btn_login.setClickable(false);
                            Intent intent3 = new Intent(LoginActivity.this, DashBoardActivity.class);
                            intent3.putExtra("6thDetails", ytt6th);
                            intent3.putExtra("PlayListDetails", playListArrayList);
                            startActivity(intent3);
                            progressdialog.dismiss();
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
