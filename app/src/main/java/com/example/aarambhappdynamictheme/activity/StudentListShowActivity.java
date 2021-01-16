package com.example.aarambhappdynamictheme.activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
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
import com.example.aarambhappdynamictheme.adapter.StudentDetailAdapter;
import com.example.aarambhappdynamictheme.model.StudentDetailModel;
import com.example.aarambhappdynamictheme.sheardPrefrence.AarambhSharedPreference;
import com.example.aarambhappdynamictheme.util.CommonUtilities;
import com.example.aarambhappdynamictheme.util.Global;
import com.example.aarambhappdynamictheme.util.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class StudentListShowActivity extends AppCompatActivity {
    ImageView add_mulitpal_student,back_press;
    RecyclerView student_list_recyclerview;
    ArrayList<StudentDetailModel> studentDetailModelArrayList;
    StudentDetailAdapter studentDetailAdapter;
    TextView no_record_found;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_list_show);

       checkOrientation();
        init();
        listner();
        getStudentData();

    }

    private void checkOrientation() {
        if (getResources().getBoolean(R.bool.portrait_only)) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
    }
    private void init() {
        studentDetailModelArrayList=new ArrayList<>();
        student_list_recyclerview=findViewById(R.id.student_list_recyclerview);
        add_mulitpal_student=findViewById(R.id.add_mulitpal_student);
        back_press=findViewById(R.id.back_press);
        no_record_found=findViewById(R.id.no_record_found);

    }

    private void getStudentData() {
        if (!CommonUtilities.isOnline(this)) {
            Toast.makeText(StudentListShowActivity.this, "Please On Your Internet Connection", Toast.LENGTH_LONG).show();
            return;
        }
        final String parent_id= AarambhSharedPreference.loadParentIdFromPreference(this);
        String url = Global.WEBBASE_URL + "getStudentByParentId?parentId="+parent_id;
        final String string_json = "Result";
        StringRequest stringRequest=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                String res = response.toString();
                parseResponseCourseApi(res);
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
                        parseResponseCourseApi(response.toString());
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
                params.put("ClassId", parent_id);
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
                headers.put("Authorization", "Bearer "+ AarambhSharedPreference.loadUserTokenFromPreference(StudentListShowActivity.this));
                return headers;
            }
        };
        VolleySingleton.getInstance(StudentListShowActivity.this).addToRequestQueue(stringRequest, string_json);
    }
    private void parseResponseCourseApi(String response) {
        Log.e("class_res",response);
        try {
            JSONObject jsonObject1 = new JSONObject(response);
            String status=jsonObject1.getString("status");
            boolean success = jsonObject1.getBoolean("success");
            if(success==false){
                String error=jsonObject1.getString("error");
                Toast.makeText(this,error+"",Toast.LENGTH_LONG).show();
            }else if (success==true) {
                String message=jsonObject1.getString("message");
                if(message.equalsIgnoreCase("No Data Found.")){
                    Toast.makeText(this,message+"",Toast.LENGTH_LONG).show();
                    no_record_found.setVisibility(View.VISIBLE);

                }else if(message.equalsIgnoreCase("Data Found")){

                    //  if(status.equalsIgnoreCase("200")&&success==true&&Message.equalsIgnoreCase("Data Found")) {
                    JSONArray jsonArray = jsonObject1.getJSONArray("studentList");

                    for (int i = 0; i < jsonArray.length(); i++) {
                        Log.e("class_lenth", String.valueOf(i));
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String StudentId = jsonObject.getString("StudentId");
                        Log.e("id", StudentId);
                        String StudentName = jsonObject.getString("StudentName");
                        Log.e("StudentName", StudentName);
                        String StudentGender = jsonObject.getString("StudentGender");
                        String StudentMobile = jsonObject.getString("StudentMobile");
                        String StudentUsername = jsonObject.getString("StudentUsername");
//                        String StudentEmail = jsonObject.getString("StudentEmail");
//                        String StudentAddress = jsonObject.getString("StudentAddress");
                     //   String StudentCity = jsonObject.getString("StudentCity");
                        String StudentDOB = jsonObject.getString("StudentDOB");
                        String StudentDORegis = jsonObject.getString("StudentDORegis");
                        String StudentPassword = jsonObject.getString("StudentPassword");
                        String StudentImage = jsonObject.getString("StudentImage");
                        String ParentId = jsonObject.getString("ParentId");
                        String ClassId = jsonObject.getString("ClassId");
                        String StatusId = jsonObject.getString("StatusId");
                        String CreatedById = jsonObject.getString("CreatedById");
                        String ModifiedById = jsonObject.getString("ModifiedById");
                        String CreationDate = jsonObject.getString("CreationDate");
                        String ModificationDate = jsonObject.getString("ModificationDate");
                        StudentDetailModel studentDetailModel = new StudentDetailModel(StudentId, StudentName, StudentGender, StudentMobile, StudentUsername, StudentDOB, StudentDORegis, StudentPassword, StudentImage, ParentId, ClassId, StatusId, CreatedById, ModifiedById, CreationDate, ModificationDate);
                        studentDetailModelArrayList.add(studentDetailModel);
                    }
                    studentDetailAdapter = new StudentDetailAdapter(this, studentDetailModelArrayList);
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(StudentListShowActivity.this, RecyclerView.VERTICAL, false);
                    student_list_recyclerview.setLayoutManager(linearLayoutManager);
                    student_list_recyclerview.setAdapter(studentDetailAdapter);
//
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void listner() {
        add_mulitpal_student.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(StudentListShowActivity.this,StudentRegisterActivity.class);
                startActivity(intent);
            }
        });
        back_press.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(StudentListShowActivity.this,ParentDashBoardActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent=new Intent(StudentListShowActivity.this,ParentDashBoardActivity.class);
        startActivity(intent);

    }
}
