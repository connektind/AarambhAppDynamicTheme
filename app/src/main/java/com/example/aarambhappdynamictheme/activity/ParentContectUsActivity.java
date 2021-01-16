package com.example.aarambhappdynamictheme.activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
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
import com.example.aarambhappdynamictheme.sheardPrefrence.AarambhSharedPreference;
import com.example.aarambhappdynamictheme.sheardPrefrence.AarambhThemeSharedPrefreence;
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

public class ParentContectUsActivity extends AppCompatActivity {
    ImageView back_btn,tab_profile_icon,tab_notification_bell;
    TextView tab_profile_name,parent_contect_name;
    TextView grud_name,grud_mobile,graud_address;
    Button save;
    ArrayList<String> you_Chapter;
    LinearLayout parent_content_background,transprent_parent_contect;
    String base_image,transparent_color,base_color_one,base_color_two;
    Bitmap Background;
    ImageView parent_mobile_image,parent_user_image,parent_address_image,email_profile_image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent_contect_us);
        checkOrientation();
        base_image= AarambhThemeSharedPrefreence.loadBaseImageFromPreference(this);
        transparent_color=AarambhThemeSharedPrefreence.loadBaseImageTransparentFromPreference(this);
        Log.e("transprent",transparent_color);

        base_color_one=AarambhThemeSharedPrefreence.loadBaseColorOneFromPreference(this);
        base_color_two=AarambhThemeSharedPrefreence.loadBaseColorTwoFromPreference(this);
        init();
        listner();
        getDetailParent();
    }

    private void getDetailParent() {
        if (!CommonUtilities.isOnline(this)) {
            Toast.makeText(ParentContectUsActivity.this, "Please On Your Internet Connection", Toast.LENGTH_LONG).show();
            return;
        }
        String url = Global.WEBBASE_URL + "getParentById?parentId=" + AarambhSharedPreference.loadParentIdFromPreference(this);
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
                params.put("parentId", AarambhSharedPreference.loadParentIdFromPreference(ParentContectUsActivity.this));
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
                headers.put("Authorization", "Bearer "+ AarambhSharedPreference.loadUserTokenFromPreference(ParentContectUsActivity.this));
                return headers;
            }
        };
        VolleySingleton.getInstance(ParentContectUsActivity.this).addToRequestQueue(stringRequest, string_json);

    }
    private void parseResponseProfileApi(String response) {
        Log.e("class_res", response);

        try {
            JSONObject jsonObject1 = new JSONObject(response);
            String status = jsonObject1.getString("status");
            boolean success = jsonObject1.getBoolean("success");
            if (success == false) {
                String error = jsonObject1.getString("error");
                Toast.makeText(this, error + "", Toast.LENGTH_LONG).show();
            } else if (success == true) {
                String Message = jsonObject1.getString("Message");
                if (Message.equalsIgnoreCase("Data Found")) {
                    JSONArray jsonArray = jsonObject1.getJSONArray("Parent");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        Log.e("class_lenth", String.valueOf(i));
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String ParentId = jsonObject.getString("ParentId");
                        String ParentName = jsonObject.getString("ParentName");
                        Log.e("ParentName", ParentName);
                        grud_name.setText(ParentName);
                        String ParentMobile = jsonObject.getString("ParentMobile");
                        grud_mobile.setText(ParentMobile);
                        String ParentPassword = jsonObject.getString("ParentPassword");
                        String ParentEmail = jsonObject.getString("ParentEmail");
                        if (ParentEmail.contains("null")) {
                            //email_update_profile.setText("");
                        } else {
                            //email_update_profile.setText(ParentEmail);
                        }
                        String ParentAddress = jsonObject.getString("ParentAddress");
                        graud_address.setText(ParentAddress);
                        String FBToken = jsonObject.getString("FBToken");
                        String StatusId = jsonObject.getString("StatusId");
                        String CreatedById = jsonObject.getString("CreatedById");
                        String ModifiedById = jsonObject.getString("ModifiedById");
                        String CreationDate = jsonObject.getString("CreationDate");
                        String ModificationDate = jsonObject.getString("ModificationDate");
                    }
                } else {
                    Toast.makeText(this, Message + "", Toast.LENGTH_LONG).show();
                }

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
        back_btn=(ImageView)findViewById(R.id.back_btn_parent);
        grud_name=(TextView) findViewById(R.id.parent_name_edit);
        grud_mobile=(TextView) findViewById(R.id.parent_mobile_number);
        graud_address=(TextView)findViewById(R.id.parent_adddress_Profile);
        parent_content_background=findViewById(R.id.parent_content_background);
        transprent_parent_contect=findViewById(R.id.transprent_parent_contect);
        transprent_parent_contect.setBackgroundColor(Color.parseColor(transparent_color));
        parent_contect_name=findViewById(R.id.parent_contect_name);
        new TextColorGradient().getColorTextGradient(parent_contect_name,base_color_one,base_color_two);
        parent_mobile_image=findViewById(R.id.parent_mobile_image);
        parent_user_image=findViewById(R.id.parent_user_image);
        email_profile_image=findViewById(R.id.email_profile_image);
        parent_address_image=findViewById(R.id.parent_address_image);
        themeDynamicAdd();
        //save=(Button)findViewById(R.id.save_detail);
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


    }

    private void listner() {
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
//        save.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String parent_name=grud_name.getText().toString().trim();
//                String parent_number=grud_mobile.getText().toString().trim();
//                if (parent_name.isEmpty()){
//                    grud_name.setError("Please Enter Parent Name.");
//                    grud_name.requestFocus();
//                }else if (parent_number.isEmpty()){
//                    grud_mobile.setError("Please Enter Parent Mobile Number.");
//                    grud_mobile.requestFocus();
//                }else{
//                    parentDataUpdate();
//                }
//            }
//        });

    }
    //    private void parentDataUpdate() {
//        if (!CommonUtilities.isOnline(this)) {
//            Toast.makeText(RedThemeParentContectActivity.this, "Please On Your Internet Connection", Toast.LENGTH_LONG).show();
//            return;
//        }
//        String url = Global.WEBBASE_URL + "updateStudent";
//        VolleyMultipartRequest multipartRequest=new VolleyMultipartRequest(Request.Method.POST, url, new Response.Listener<NetworkResponse>() {
//            @Override
//            public void onResponse(NetworkResponse response) {
//                String resultResponse = new String(response.data);
//                parseResponseUpdateProfile(resultResponse);
//                Log.e("Response",resultResponse);
//                //  parseResponseUpdateProfile(String.valueOf(response));
//
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                NetworkResponse networkResponse = error.networkResponse;
//                String errorMessage = "Unknown error";
//                if (networkResponse == null) {
//                    if (error.getClass().equals(TimeoutError.class)) {
//                        errorMessage = "Request timeout";
//                    } else if (error.getClass().equals(NoConnectionError.class)) {
//                        errorMessage = "Failed to connect server";
//                    }
//                } else {
//                    try {
//                        String result = new String(networkResponse.data);
//                        Log.e("Result",result);
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                }
//                Log.i("Error", errorMessage);
//                error.printStackTrace();
//            }
//        }) {
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String, String> params = new HashMap<>();
//                params.put("StudentId", AarambhSharedPreference.loadStudentIdFromPreference(RedThemeParentContectActivity.this));
//                Log.e("student_id",AarambhSharedPreference.loadStudentIdFromPreference(RedThemeParentContectActivity.this));
//                params.put("StudentGuardianName", grud_name.getText().toString().trim());
//                params.put("StudentAltMob", grud_mobile.getText().toString().trim());
//                params.put("ClassId", AarambhSharedPreference.loadClassIdFromPreference(RedThemeParentContectActivity.this));
//                Log.e("ClassId", AarambhSharedPreference.loadClassIdFromPreference(RedThemeParentContectActivity.this));
//                params.put("StatusId", "1");
//                params.put("CreatedById", "1");
//                params.put("CreationDate", "");
//                params.put("ModifiedById", "1");
//                params.put("ModificationDate", "2020-02-26");
//                Log.e("apiData---", (params).toString());
//                return params;
//            }
//            @Override
//            protected Map<String, DataPart> getByteData() {
//                Map<String, DataPart> params = new HashMap<>();
//                // file name could found file base or direct access from real path
//                // for now just get bitmap data from ImageView
////                profile_uri=new DataPart("img", AppHelper.getBitmap(RedTheme_UpdateProfile.this, profilebitmap), "image/jpeg");
////                params.put("StudentImage", profile_uri);
////                Log.e("profileData", String.valueOf(profile_uri));
//                return params;
//            }
//
//            @Override
//            public Map<String, String> getHeaders() throws AuthFailureError {
//                HashMap<String, String> headers = new HashMap<String, String>();
//                //headers.put("Content-Type", "application/json; charset=utf-8");
//                headers.put("Authorization", "Bearer " + AarambhSharedPreference.loadUserTokenFromPreference(RedThemeParentContectActivity.this));
//                return headers;
//            }
//        };
//        multipartRequest.setRetryPolicy(new DefaultRetryPolicy(
//                Global.WEBSERVICE_TIMEOUT_VALUE_IN_MILLIS,
//                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
//                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
//        ));
//        multipartRequest.setShouldCache(false);
//        VolleySingletonImage.getInstance(RedThemeParentContectActivity.this).addToRequestQueue(multipartRequest);
//    }
//    private void parseResponseUpdateProfile(String response) {
//        try {
//            JSONObject obj = new JSONObject(response);
//            //boolean result = obj.getBoolean("Result");
//            String message = obj.getString("Message");
//            Log.e("response", response);
//            Log.e("massage", message);
//            // Log.e("Insert Response: ", result + "");
//            if (message.equalsIgnoreCase("Student Updated Successfully.")) {
//                Toast.makeText(RedThemeParentContectActivity.this,"Saved Successfully.",Toast.LENGTH_LONG).show();
//                Intent intent=new Intent(RedThemeParentContectActivity.this, DashBoardSixthStandardActivity.class);
//                startActivity(intent);
//            }
//            else {
//                Toast.makeText(this, "Updatedation Failed.Pls Try Again", Toast.LENGTH_LONG).show();
//                //progressDialog.dismiss();
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//    }
//
    private void checkOrientation() {
        if (getResources().getBoolean(R.bool.portrait_only)) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
    }
    @Override
    public void onBackPressed() {
        finish();
//        Intent intent=new Intent(RedThemeParentContectActivity.this, DashBoardSixthStandardActivity.class);
//    startActivity(intent);
    }

    @Override
    public void finish() {
        Intent intent=new Intent(ParentContectUsActivity.this, DashBoardActivity.class);
        intent.putExtra("DataDashBoard6th", you_Chapter);
        //  in.putExtra("LandDetail", landDetailModel);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        setResult(RESULT_OK, intent);
        super.finish();
    }

    private void themeDynamicAdd() {

        //Bitmap myImage = getBitmapFromURL(image);
        try {
            Background=getBitmapFromURL(base_image);
            Drawable dr = new BitmapDrawable((Background));
            parent_content_background.setBackgroundDrawable(dr);

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
        Bitmap mobile_bitmap;
        String mobile_shrd=AarambhThemeSharedPrefreence.loadMobileIconFromPreference(this);
        try {
            mobile_bitmap=getBitmapFromURL(mobile_shrd);
            Drawable dr = new BitmapDrawable((mobile_bitmap));
            parent_mobile_image.setBackgroundDrawable(dr);

        }catch (Exception e){
            e.printStackTrace();
        }
        Bitmap user_image;
        String user_shrd=AarambhThemeSharedPrefreence.loadUserIconFromPreference(this);
        try {
            user_image=getBitmapFromURL(user_shrd);
            Drawable dr = new BitmapDrawable((user_image));
            parent_user_image.setBackgroundDrawable(dr);

        }catch (Exception e){
            e.printStackTrace();
        }
        Bitmap email_bitmap;
        String email_shrd=AarambhThemeSharedPrefreence.loadEmailIconFromPreference(this);
        try {
            email_bitmap=getBitmapFromURL(email_shrd);
            Drawable dr = new BitmapDrawable((email_bitmap));
            email_profile_image.setBackgroundDrawable(dr);

        }catch (Exception e){
            e.printStackTrace();
        }
        Bitmap address_bitmap;
        String address_shrd=AarambhThemeSharedPrefreence.loadAddressIconFromPreference(this);
        try {
            address_bitmap=getBitmapFromURL(address_shrd);
            Drawable dr = new BitmapDrawable((address_bitmap));
            parent_address_image.setBackgroundDrawable(dr);

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
