package com.example.aarambhappdynamictheme.activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;

import com.example.aarambhappdynamictheme.sheardPrefrence.AarambhThemeSharedPrefreence;
import com.google.android.material.textfield.TextInputEditText;
import androidx.appcompat.app.AppCompatActivity;

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
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.aarambhappdynamictheme.R;
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
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.example.aarambhappdynamictheme.util.Global.urlProfileImg;

public class ContactUsActivity extends AppCompatActivity {
    ImageView back_btn,tab_profile_icon;
    Button send_data;
    TextInputEditText user_msg,user_email;
    TextView contact_us_red_name,tab_profile_name;
    ArrayList<String> you_Chapter;
    LinearLayout contact_us_transprent,contact_us_background;
    String base_image,transparent_color,base_color_one,base_color_two;
    Bitmap Background;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);
        checkOrientation();
        base_image= AarambhThemeSharedPrefreence.loadBaseImageFromPreference(this);
        transparent_color=AarambhThemeSharedPrefreence.loadBaseImageTransparentFromPreference(this);
        Log.e("transprent",transparent_color);

        base_color_one=AarambhThemeSharedPrefreence.loadBaseColorOneFromPreference(this);
        base_color_two=AarambhThemeSharedPrefreence.loadBaseColorTwoFromPreference(this);
        init();
        listner();
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
                finish();
            }
        });
        send_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = user_msg.getText().toString().trim();
                String email = user_email.getText().toString().trim();
                String validemail = "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" + "\\@" + "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" + "(" + "\\." + "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" + ")+";
                Matcher matcherObj = Pattern.compile(validemail).matcher(email);
                if (email.isEmpty()) {
                    user_email.setError("Please Enter Email.");
                    user_email.requestFocus();
                } else if (!email.isEmpty()&&!matcherObj.matches()) {
                    user_email.setError("Please Enter vaild Email");
                    user_email.requestFocus();
                }
                else if (message.isEmpty()) {
                    user_msg.setError("Please Enter Message");
                    user_msg.requestFocus();
                } else {
                    send_data.setEnabled(true);
                    getApiCalling(message,email);

                }
            }
        });

    }

    private void getApiCalling(String msg,String email) {
        if (!CommonUtilities.isOnline(this)) {
            Toast.makeText(ContactUsActivity.this, "Please On Your Internet Connection", Toast.LENGTH_LONG).show();
            return;
        }

//        progressDialog = new ProgressDialog(Near_LAFARM_Unit.this);
//        progressDialog.setCancelable(false);
//        progressDialog.setMessage("Loading...");
//        progressDialog.setTitle("Wait...");
//        progressDialog.show();

        String url = Global.WEBBASE_URL + "insertFeedback_New";
        final String string_json = "Result";
//        progressDialog = new ProgressDialog(this);
//        progressDialog.setCancelable(false);
//        progressDialog.setMessage("Loading...");
//        progressDialog.setTitle("Wait...");
//        progressDialog.show();

        JSONObject params = new JSONObject();
        String mRequestBody = "";
        try {
            params.put("Message", msg);
            Log.e("Message", msg);
            params.put("username", AarambhSharedPreference.loadStudentNameFromPreference(this));
            Log.e("username", AarambhSharedPreference.loadStudentNameFromPreference(this));
            params.put("useremail", email);
            Log.e("useremail", "gaurd_mobile_num");
            params.put("StudentId", AarambhSharedPreference.loadStudentIdFromPreference(this));

            params.put("StatusId", "1");
            params.put("CreatedById", Integer.parseInt("1"));
            params.put("CreationDate", "");
            params.put("ModifiedById", Integer.parseInt("1"));
            params.put("ModificationDate", "2020-02-26");
            Log.e("apiData---", (params).toString());

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
                parseResponse(res);
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
                        parseResponse(response.toString());

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
                headers.put("Authorization", "Bearer "+AarambhSharedPreference.loadUserTokenFromPreference(ContactUsActivity.this));

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
        VolleySingleton.getInstance(ContactUsActivity.this).addToRequestQueue(jsonObjectRequest, string_json);
    }

    private void parseResponse(String response) {
        try {
            JSONObject obj = new JSONObject(response);
            String status=obj.getString("status");
            boolean success = obj.getBoolean("success");
            // String message = obj.getString("Message");
            Log.e("response", response);
            // Log.e("massage", message);
            // Log.e("Insert Response: ", result + "");
            if(success==false){
                String error = obj.getString("error");
                Log.e("error", error);
                Toast.makeText(this, error+"", Toast.LENGTH_LONG).show();

            }else if (success==true){
                String message = obj.getString("Message");
                Log.e("massage", message);
                if (message.equalsIgnoreCase("Feedback saved successfully.")){
                    Toast.makeText(this, "Feedback Successfully Submitted", Toast.LENGTH_LONG).show();
                    getChannelPlayListVideos();

                }else {
                    Toast.makeText(this, "Failed.Pls Try Again", Toast.LENGTH_LONG).show();
                }
            }else {
                Toast.makeText(this, "Submition Failed.Pls Try Again", Toast.LENGTH_LONG).show();
                //progressDialog.dismiss();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    private void init() {
        StrictMode.ThreadPolicy policy = new
                StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        you_Chapter=new ArrayList<>();
        contact_us_red_name=findViewById(R.id.contact_us_red_name);
        TextColorGradient textColorGradient =new TextColorGradient();
        textColorGradient.getColorTextGradient(contact_us_red_name,base_color_one,base_color_two);
        send_data=(Button)findViewById(R.id.send_data);
        back_btn=(ImageView) findViewById(R.id.back_btn_contact);
        // user_name=(TextInputEditText)findViewById(R.id.full_name_cont);
        //user_email=(TextInputEditText)findViewById(R.id.email_cont);
//        user_msg=(EditText)findViewById(R.id.message_cont);
        user_email=(TextInputEditText)findViewById(R.id.email_cont);
        user_msg=(TextInputEditText) findViewById(R.id.full_name_cont);
//        try {
//            tab_profile_icon=(ImageView) findViewById(R.id.img_profile_tab);
//            tab_profile_name=(TextView) findViewById(R.id.student_name_red);
//            tab_profile_name.setText(AarambhSharedPreference.loadStudentNameFromPreference(this));
//            Glide.with(this).load(urlProfileImg)
//                    .crossFade()
//                    .thumbnail(0.5f)
//                    .bitmapTransform(new CircleTransform(this))
//                    .diskCacheStrategy(DiskCacheStrategy.ALL)
//                    .into(tab_profile_icon);
//
//        }catch (Exception e){
//            e.printStackTrace();
//        }
        contact_us_transprent=findViewById(R.id.contact_us_transprent);
        contact_us_background=findViewById(R.id.contact_us_background);
        contact_us_transprent.setBackgroundColor(Color.parseColor(transparent_color));
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

        //apply the button background to newly created drawable gradient
        send_data.setBackground(gd);
        // previous_btn.setBackground(gd);
        // cancle_btn.setBackground(gd1);
    }

    @Override
    public void onBackPressed() {
        finish();
    }
    @Override
    public void finish() {
        Intent in=new Intent(ContactUsActivity.this, DashBoardActivity.class);
        in.putExtra("DataDashBoard6th", you_Chapter);
        //  in.putExtra("LandDetail", landDetailModel);
        in.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        setResult(RESULT_OK, in);
        super.finish();
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

                        String class_id = AarambhSharedPreference.loadClassIdFromPreference(ContactUsActivity.this);
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
                    Log.e("classId", AarambhSharedPreference.loadClassIdFromPreference(ContactUsActivity.this));

                    switch (AarambhSharedPreference.loadClassIdFromPreference(ContactUsActivity.this)) {
                        case "1":
                            //btn_login.setClickable(false);
                            Intent intent = new Intent(ContactUsActivity.this, DashBoardActivity.class);
                            //Log.e("6thDetails", String.valueOf(ytt6th.getSubject().size()));
                            intent.putExtra("6thDetails", ytt6th);
                            intent.putExtra("PlayListDetails", playListArrayList);
                            startActivity(intent);
                            //progressdialog.dismiss();
                            break;
                        case "2":
                            //btn_login.setClickable(false);
                            Intent intent1 = new Intent(ContactUsActivity.this, DashBoardActivity.class);
                            intent1.putExtra("7thDetails", ytt7th);
                            intent1.putExtra("PlayListDetails", playListArrayList);
                            startActivity(intent1);
                            //progressdialog.dismiss();
                            break;
                        case "3":
                            //btn_login.setClickable(false);
                            Intent intent2 = new Intent(ContactUsActivity.this, DashBoardActivity.class);
                            intent2.putExtra("8thDetails", ytt8th);
                            intent2.putExtra("PlayListDetails", playListArrayList);
                            startActivity(intent2);
                            //progressdialog.dismiss();
                            break;
                        default:
                            //btn_login.setClickable(false);
                            Intent intent3 = new Intent(ContactUsActivity.this, DashBoardActivity.class);
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
    private void themeDynamicAdd() {

        //Bitmap myImage = getBitmapFromURL(image);
        try {
            Background=getBitmapFromURL(base_image);
            Drawable dr = new BitmapDrawable((Background));
            contact_us_background.setBackgroundDrawable(dr);

        }catch (Exception e){
            e.printStackTrace();
        }
        Bitmap back_bitmap;
        String back_shrd=AarambhThemeSharedPrefreence.loadBackArrowIconFromPreference(this);
        try {
            back_bitmap=getBitmapFromURL(back_shrd);
            Drawable dr = new BitmapDrawable((back_bitmap));
            back_btn.setBackgroundDrawable(dr);

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
