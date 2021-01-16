package com.example.aarambhappdynamictheme.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.aarambhappdynamictheme.R;
import com.example.aarambhappdynamictheme.sheardPrefrence.AarambhSharedPreference;
import com.example.aarambhappdynamictheme.util.CommonUtilities;
import com.example.aarambhappdynamictheme.util.Global;
import com.example.aarambhappdynamictheme.util.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ParentUpdateActivity extends AppCompatActivity {
    EditText mobile_update_profile,name_update_profile,email_update_profile,address_update_profile;
    Button save_detail;
    ImageView back_btn_parent;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent_update);
        checkOrientation();
        init();
        listner();
        getDetailParent();
    }
    private void getDetailParent() {
        if (!CommonUtilities.isOnline(this)) {
            Toast.makeText(ParentUpdateActivity.this, "Please On Your Internet Connection", Toast.LENGTH_LONG).show();
            return;
        }
        String url = Global.WEBBASE_URL + "getParentById?parentId=" + AarambhSharedPreference.loadParentIdFromPreference(this);
        final String string_json = "Result";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
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
        }) {
            @Override
            public Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<String, String>();
                params.put("studentId", AarambhSharedPreference.loadStudentIdFromPreference(ParentUpdateActivity.this));
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
                headers.put("Authorization", "Bearer " + AarambhSharedPreference.loadUserTokenFromPreference(ParentUpdateActivity.this));
                return headers;
            }
        };
        VolleySingleton.getInstance(ParentUpdateActivity.this).addToRequestQueue(stringRequest, string_json);

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
                        name_update_profile.setText(ParentName);
                        String ParentMobile = jsonObject.getString("ParentMobile");
                        mobile_update_profile.setText(ParentMobile);
                        String ParentPassword = jsonObject.getString("ParentPassword");
                        String ParentEmail = jsonObject.getString("ParentEmail");
                        if (ParentEmail.contains("null")) {
                            email_update_profile.setText("");
                        } else {
                            email_update_profile.setText(ParentEmail);
                        }
                        String ParentAddress = jsonObject.getString("ParentAddress");
                        address_update_profile.setText(ParentAddress);
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


    private void listner() {
        back_btn_parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ParentUpdateActivity.this,ParentDashBoardActivity.class);
                startActivity(intent);
            }
        });
        save_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mobile=mobile_update_profile.getText().toString().trim();
                String name=name_update_profile.getText().toString().trim();
                String email=email_update_profile.getText().toString().trim();
                String add=address_update_profile.getText().toString().trim();
                String validemail = "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" + "\\@" + "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" + "(" + "\\." + "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" + ")+";
                Matcher matcherObj = Pattern.compile(validemail).matcher(email);
                String mobilep = "[6-9]{1}[0-9]{9}";
//                if (mobile.isEmpty()){
//                    user_mobile.setError("Please Enter Mobile Number");
//                    user_mobile.requestFocus();
//                } else
                if (mobile.isEmpty()) {
                    mobile_update_profile.setError("Please Enter Mobile Number");
                    mobile_update_profile.requestFocus();

                }
                else if (!mobile.isEmpty()&&mobile.length() < 10) {
                    mobile_update_profile.setError("Please Enter 10 Digit Mobile Number");
                    mobile_update_profile.requestFocus();

                }else if (!mobile.isEmpty()&&!mobile.matches(mobilep)){
                    mobile_update_profile.setError("Mobile Number Should Start With 6,7,8 or 9.Please Enter Valid Mobile Number.");
                    mobile_update_profile.requestFocus();
                }
                else if (name.isEmpty())
                {
                    name_update_profile.setError("Enter Name");
                    name_update_profile.requestFocus();
                }
                else if (!email.isEmpty()&&!matcherObj.matches()) {
                    email_update_profile.setError("Please Enter vaild Email");
                    email_update_profile.requestFocus();
                }
//                else if (email.matches(emailPattern) && email.length() > 0){
//                    user_email.setError("Please Enter vaild Email");
//                    user_email.requestFocus();
//                }
                else if (add.isEmpty()){
                    address_update_profile.setError("Please Enter Address");
                    address_update_profile.requestFocus();

                }
                else{
                    updateApiData(mobile,name,email,add);

                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent=new Intent(ParentUpdateActivity.this,ParentDashBoardActivity.class);
        startActivity(intent);
    }

    //    private void updateApiData(final String mobile, final String name, final String email, final String add) {
//        if (!CommonUtilities.isOnline(this)) {
//            Toast.makeText(ParentUpdateActivity.this, "Please On Your Internet Connection", Toast.LENGTH_LONG).show();
//            return;
//        }
//       // http://35.200.220.64:1231/aarambh/updateParentMain
//        String url = Global.WEBBASE_URL + "updateParentMain";
//        progressDialog = new ProgressDialog(this);
//        progressDialog.setCancelable(false);
//        progressDialog.setMessage("Loading...");
//        progressDialog.setTitle("Wait...");
//        progressDialog.show();
//
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
//                try {
//                  //  params.put("ParentId", AarambhSharedPreference.loadParentIdFromPreference(ParentUpdateActivity.this));
//                    params.put("ParentName", name);
//
//                    if (mobile.isEmpty()){
//
//                    }else{
//                        params.put("ParentMobile", mobile);
//                    }
//                    if (email.isEmpty()){
//                        params.put("ParentEmail", null);
//
//                    }else {
//                        params.put("ParentEmail", email.trim());
//                    }
//                    params.put("ParentAddress", add);
//                    params.put("StatusId", "1");
//                    params.put("CreatedById", "1");
//                    params.put("CreationDate", "");
//                    params.put("ModifiedById", "1");
//                    params.put("ModificationDate", "2020-02-26");
//                    Log.e("apiData---", (params).toString());
//
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//                return params;
//            }
//
//
//            @Override
//            public Map<String, String> getHeaders() throws AuthFailureError {
//                HashMap<String, String> headers = new HashMap<String, String>();
//                //headers.put("Content-Type", "application/json; charset=utf-8");
//                headers.put("Authorization", "Bearer " + AarambhSharedPreference.loadUserTokenFromPreference(ParentUpdateActivity.this));
//                return headers;
//            }
//        };
//        multipartRequest.setRetryPolicy(new DefaultRetryPolicy(
//                Global.WEBSERVICE_TIMEOUT_VALUE_IN_MILLIS,
//                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
//                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
//        ));
//        multipartRequest.setShouldCache(false);
//        VolleySingletonImage.getInstance(ParentUpdateActivity.this).addToRequestQueue(multipartRequest);
//    }
    private void updateApiData (final String mobile, final String name, final String email, final String add){

        if (!CommonUtilities.isOnline(this)) {
            Toast.makeText(ParentUpdateActivity.this, "Please On Your Internet Connection", Toast.LENGTH_LONG).show();
            return;
        }
        String url = Global.WEBBASE_URL + "updateParentMain";
        final String string_json = "Result";
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading...");
        progressDialog.setTitle("Wait...");
        progressDialog.show();
        JSONObject params = new JSONObject();
        String mRequestBody = "";
        try {

            params.put("ParentName", name);
            Log.e("ParentName", name);

            params.put("ParentMobile", mobile);
            Log.e("ParentMobile", mobile);

            try {
                if (email.isEmpty()) {
                    params.put("ParentEmail", "null");
                } else {
                    params.put("ParentEmail", email);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            //  Log.e("ParentEmail", null);

            params.put("ParentAddress", add);
            Log.e("ParentAddress", add);
            params.put("FBToken", "");
            Log.e("FBToken", "");

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
                parseResponseUpdateProfile(res, progressDialog);
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
                        parseResponseUpdateProfile(response.toString(), progressDialog);

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
                headers.put("Authorization", "Bearer "+ AarambhSharedPreference.loadUserTokenFromPreference(ParentUpdateActivity.this));
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
        VolleySingleton.getInstance(ParentUpdateActivity.this).addToRequestQueue(jsonObjectRequest, string_json);


    }

    private void parseResponseUpdateProfile(String response,ProgressDialog progressDialog) {
        try {
            Log.e("Update Response",response);
            progressDialog.show();
            JSONObject obj = new JSONObject(response);
            String status=obj.getString("status");
            boolean success = obj.getBoolean("success");
            Log.e("response", response);
            if(success==false){
                String error = obj.getString("error");
                Log.e("massage", error);
                Toast.makeText(this, error+"", Toast.LENGTH_LONG).show();
                progressDialog.dismiss();
            }else if(success==true){
                String message = obj.getString("Message");
                Log.e("massage", message);
                if(message.equalsIgnoreCase("Parent Updated Successfully.")){
                    getDetailParent();
                    progressDialog.dismiss();
                    Toast.makeText(ParentUpdateActivity.this,"Saved Successfully.",Toast.LENGTH_LONG).show();
                    AarambhSharedPreference.saveStudentNameToPreference(this,name_update_profile.getText().toString().trim());
                    AarambhSharedPreference.saveStudentMobileToPreference(this,mobile_update_profile.getText().toString().trim());
                    Intent intent=new Intent(ParentUpdateActivity.this,ParentDashBoardActivity.class);
                    startActivity(intent);

                }else{
                    Toast.makeText(this, message+"", Toast.LENGTH_LONG).show();
                    progressDialog.dismiss();
                }

            }else{
                Toast.makeText(this, "Updation Failed.Pls Try Again", Toast.LENGTH_LONG).show();
                progressDialog.dismiss();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void init() {
        mobile_update_profile=findViewById(R.id.mobile_update_profile);
        name_update_profile=findViewById(R.id.name_update_profile);
        email_update_profile=findViewById(R.id.email_update_profile);
        address_update_profile=findViewById(R.id.address_update_profile);
        save_detail=findViewById(R.id.save_detail);
        back_btn_parent=findViewById(R.id.back_btn_parent);

    }

    private void checkOrientation() {
        if (getResources().getBoolean(R.bool.portrait_only)) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
    }
}
