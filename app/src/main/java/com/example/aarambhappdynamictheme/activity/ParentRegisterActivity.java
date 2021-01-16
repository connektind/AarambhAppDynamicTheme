package com.example.aarambhappdynamictheme.activity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
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
import com.example.aarambhappdynamictheme.R;
import com.example.aarambhappdynamictheme.model.RegisterationFirstModel;
import com.example.aarambhappdynamictheme.util.CommonUtilities;
import com.example.aarambhappdynamictheme.util.Global;
import com.example.aarambhappdynamictheme.util.VolleySingleton;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ParentRegisterActivity extends AppCompatActivity {
    TextView login;
    Button sumit_btn;
    EditText stud_full_name, stud_mobile_number, stud_email, stud_address, reg_parent_password;
    RegisterationFirstModel registerationFirstModel;
    ProgressDialog progressDialog;
    CheckBox chkTerms;

    TextView txtTerms;
    String terms = "0";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent_register);
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        checkOrientation();
        init();
        listner();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(ParentRegisterActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
        // super.onBackPressed();
    }

    private void checkOrientation() {
        if (getResources().getBoolean(R.bool.portrait_only)) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
    }

    private void listner() {
        chkTerms.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked == true) {
                    terms = "1";
                } else {
                    terms = "0";
                }
            }
        });
        txtTerms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog d = new Dialog(ParentRegisterActivity.this, R.style.FullScreenDialogStyleTransparent);
                d.setContentView(R.layout.dialog_terms);
                WebView wv = d.findViewById(R.id.webView);
                ImageView img = d.findViewById(R.id.btn_cancel);

                wv.loadUrl("https://aarambhapp.com/termsofservicesapp.html");
                wv.setWebViewClient(new WebViewClient() {
                    @Override
                    public boolean shouldOverrideUrlLoading(WebView view, String url) {
                        view.loadUrl(url);
                        return true;
                    }
                });
                img.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        d.dismiss();
                    }
                });
                d.show();
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login.setClickable(false);
                Intent intent = new Intent(ParentRegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
        sumit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submit();

            }
        });
    }

    private void submit() {
        String parent_name = stud_full_name.getText().toString().trim();
        String stud_mobile = stud_mobile_number.getText().toString().trim();
        String email = stud_email.getText().toString().trim();
        String address = stud_address.getText().toString().trim();
        String password = reg_parent_password.getText().toString().trim();
        String mobilep = "[6-9]{1}[0-9]{9}";
        String validemail = "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" + "\\@" + "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" + "(" + "\\." + "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" + ")+";
        Matcher matcherObj = Pattern.compile(validemail).matcher(email);
        if (parent_name.isEmpty()) {
            stud_full_name.setError("Please Enter Parent Full Name");
            stud_full_name.requestFocus();

        } else if (stud_mobile.isEmpty()) {
            stud_mobile_number.setError("Please Enter Mobile Number");
            stud_mobile_number.requestFocus();
        } else if (stud_mobile.length() < 10) {
            stud_mobile_number.setError("Please Enter 10 Digit Mobile Number");
            stud_mobile_number.requestFocus();

        } else if (!stud_mobile.matches(mobilep)) {
            stud_mobile_number.setError("Mobile Number Should Start With 6,7,8 or 9.Please Enter Valid Mobile Number.");
            stud_mobile_number.requestFocus();
        } else if (password.isEmpty()) {
            reg_parent_password.setError("Please Enter Password");
            reg_parent_password.requestFocus();
        } else if (password.length() < 6) {
            reg_parent_password.requestFocus();
            reg_parent_password.setError("Please Create 6 Digit Password");
        } else if (!email.isEmpty()&&!matcherObj.matches()) {
            stud_email.setError("Please Enter Valid Email");
            stud_email.requestFocus();
        }

//        else if (email.matches(emailPattern) && email.length() > 0){
//            stud_email.setError("Please Enter vaild Email");
//            stud_email.requestFocus();
//        }
        else if (address.isEmpty()) {
            stud_address.setError("Please Enter City/Address");
            stud_address.requestFocus();
        } else if (terms.equals("0")) {
            Toast.makeText(ParentRegisterActivity.this, "Please Select Terms and Conditions.", Toast.LENGTH_LONG).show();
        } else {
//                progressDialog = new ProgressDialog(this);
//                progressDialog.setCancelable(false);
//                progressDialog.setMessage("Loading...");
//                progressDialog.setTitle("Wait...");
//                progressDialog.show();
            getparentRegistartion(parent_name, stud_mobile, password, email, address);

            //  registerationFirstModel=new RegisterationFirstModel(stud_name,stud_mobile,email,address);
//            Log.e("student_name",stud_name);
//            sumit_btn.setClickable(false);
//            Intent intent=new Intent(RegisterationActivity.this,RegisterationPartTwoActivity.class);
//           intent.putExtra("registrationDetail",registerationFirstModel);
//            startActivity(intent);
            //progressDialog.dismiss();
        }
    }


    private void init () {
        txtTerms = findViewById(R.id.txtTerms);
        chkTerms = findViewById(R.id.chkTerms);
        login = findViewById(R.id.login);
        sumit_btn = (Button) findViewById(R.id.btn_submit);
        stud_full_name = (EditText) findViewById(R.id.reg_student_full_name);
        stud_mobile_number = (EditText) findViewById(R.id.reg_stud_mobile_no);
        stud_email = (EditText) findViewById(R.id.reg_stud_email);
        stud_address = (EditText) findViewById(R.id.address);
        reg_parent_password = (EditText) findViewById(R.id.reg_parent_password);
    }

    private void getparentRegistartion (String parent_name, String stud_mobile, String
            password, String email, String address){

        if (!CommonUtilities.isOnline(this)) {
            Toast.makeText(ParentRegisterActivity.this, "Please On Your Internet Connection", Toast.LENGTH_LONG).show();
            return;
        }
        String url = Global.WEBBASE_URL + "insertParentDetail";
        final String string_json = "Result";
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading...");
        progressDialog.setTitle("Wait...");
        progressDialog.show();
        JSONObject params = new JSONObject();
        String mRequestBody = "";
        try {

            params.put("ParentName", parent_name);
            Log.e("ParentName", parent_name);

            params.put("ParentMobile", stud_mobile);
            Log.e("ParentMobile", stud_mobile);

            params.put("ParentPassword", password);
            Log.e("ParentPassword", password);
            try {
                if (email.contains("")) {
                    params.put("ParentEmail", null);
                } else {
                    params.put("ParentEmail", email);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            //  Log.e("ParentEmail", null);

            params.put("ParentAddress", address);
            Log.e("ParentAddress", address);
            params.put("FBToken", "");
            Log.e("FBToken", "");

            params.put("StatusId", "1");
            params.put("SchoolId",Global.AARAMBH_ID);
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
                parseResponseParentRegister(res, progressDialog);
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
                        parseResponseParentRegister(response.toString(), progressDialog);

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
        VolleySingleton.getInstance(ParentRegisterActivity.this).addToRequestQueue(jsonObjectRequest, string_json);


    }

    private void parseResponseParentRegister (String response, ProgressDialog progressDialog){
        progressDialog.show();
        try {
            JSONObject obj = new JSONObject(response);
            String status=obj.getString("status");
            boolean success = obj.getBoolean("success");

            Log.e("response", response);

            if (success==false){
                String error = obj.getString("error");
                Log.e("error", error);
                // progressDialog.dismiss();
                Toast.makeText(this, error+"", Toast.LENGTH_LONG).show();
                progressDialog.dismiss();
            }else if (success==true){
                String message = obj.getString("Message");
                Log.e("massage", message);
                if (message.equalsIgnoreCase("Parent Details saved successfully.")) {
                    Toast.makeText(this, "Registration Successfully.", Toast.LENGTH_LONG).show();
                    Intent intent2 = new Intent(ParentRegisterActivity.this, LoginActivity.class);
                    startActivity(intent2);
                    progressDialog.dismiss();
                }else{
                    progressDialog.dismiss();
                    Toast.makeText(this, message+"", Toast.LENGTH_LONG).show();
                }
            } else {
                progressDialog.dismiss();
                Toast.makeText(this, "Registration Failed.Pls Try Again", Toast.LENGTH_LONG).show();
                //progressDialog.dismiss();
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
