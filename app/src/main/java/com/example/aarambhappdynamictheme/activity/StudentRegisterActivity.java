package com.example.aarambhappdynamictheme.activity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
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
import com.android.volley.toolbox.StringRequest;
import com.example.aarambhappdynamictheme.R;
import com.example.aarambhappdynamictheme.adapter.StudentClassAdapter;
import com.example.aarambhappdynamictheme.model.RegisterationFirstModel;
import com.example.aarambhappdynamictheme.model.StudentClassModel;
import com.example.aarambhappdynamictheme.sheardPrefrence.AarambhSharedPreference;
import com.example.aarambhappdynamictheme.util.CommonUtilities;
import com.example.aarambhappdynamictheme.util.Global;
import com.example.aarambhappdynamictheme.util.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StudentRegisterActivity extends AppCompatActivity {
    Spinner gender_select;
    int gender_position;
    Button btn_register;
    StudentClassModel studentClassModel;
    ArrayList<StudentClassModel> studentClassModelArrayList;
    StudentClassAdapter studentClassAdapter;
    EditText class_select, create_password, confirm_password,
            student_name, username, student_address_parent, email_student, mobile_student;
    String datetoSend, class_position = "0";
    final Calendar calendar = Calendar.getInstance();
    RegisterationFirstModel registerationFirstModel;
    ProgressDialog progressDialog;
    TextView tv_other, stud_DOB;
    ImageView back_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_register);
        checkOrientation();
        init();
        listner();
    }

    private void checkOrientation() {
        if (getResources().getBoolean(R.bool.portrait_only)) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
    }

    private void init() {
        back_btn = (ImageView) findViewById(R.id.back_btn);
        studentClassModelArrayList = new ArrayList<>();
        gender_select = (Spinner) findViewById(R.id.gender_select);
        class_select = (EditText) findViewById(R.id.class_select);
        btn_register = (Button) findViewById(R.id.btn_register);
        stud_DOB = (TextView) findViewById(R.id.reg_DOB_stud);
        student_name = (EditText) findViewById(R.id.student_name);
        username = (EditText) findViewById(R.id.username);
        create_password = (EditText) findViewById(R.id.create_password);
        confirm_password = (EditText) findViewById(R.id.confirm_password);
        email_student = (EditText) findViewById(R.id.email_student);
        student_address_parent = (EditText) findViewById(R.id.student_address_parent);
        mobile_student = (EditText) findViewById(R.id.mobile_student);

    }

    private void listner() {
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(StudentRegisterActivity.this, StudentListShowActivity.class);
                startActivity(intent);
            }
        });
        class_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Dialog dialog = new Dialog(StudentRegisterActivity.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setCancelable(false);
                dialog.setContentView(R.layout.searchable_list_dialog);
                final ListView lv_listView = (ListView) dialog.findViewById(R.id.listItems);
                // final EditText et_search = (EditText) dialog.findViewById(R.id.search);
                TextView close = (TextView) dialog.findViewById(R.id.close);
                final ProgressBar progressBar = (ProgressBar) dialog.findViewById(R.id.pr_progressbar);
                progressBar.setVisibility(View.VISIBLE);
                tv_other = (TextView) dialog.findViewById(R.id.tv_other);
                tv_other.setVisibility(View.GONE);
                studentClassModelArrayList.clear();
                getClassApiCalling(lv_listView, progressBar);
                tv_other.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //   class_select.setText(et_search.getText().toString().trim());
                        dialog.dismiss();
                        class_select.setError(null);
                        class_select.clearFocus();
                    }
                });
                lv_listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        dialog.cancel();
                        StudentClassModel dd = studentClassModelArrayList.get(position);
                        class_position = dd.getClassId();
                        class_select.setError(null);
                        class_select.clearFocus();
                        Log.e("Name  & Id", dd.getClassId() + ":" + dd.getStudentClass());
                        class_select.setText(dd.getStudentClass());


                    }
                });
                close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.cancel();
                    }
                });
                dialog.show();
            }
        });
        String[] type_gender = {"---Select Student’s Gender---", "Male", "Female"};
        ArrayAdapter<String> arrayAdapter_gender = new ArrayAdapter<String>(StudentRegisterActivity.this, android.R.layout.simple_dropdown_item_1line, type_gender);
        gender_select.setAdapter(arrayAdapter_gender);
        gender_select.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                gender_position = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {


            }
        });
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registeration();
            }
        });
        stud_DOB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(StudentRegisterActivity.this, android.R.style.Theme_Holo_Light_Dialog, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        //et_age.setText(year + "-" + (month + 1) + "-" + dayOfMonth);
                        stud_DOB.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                        stud_DOB.setError(null);
                        stud_DOB.clearFocus();
                        datetoSend = year + "-" + (month + 1) + "-" + dayOfMonth;
                    }
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
                datePickerDialog.setCancelable(false);
                datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
            }
        });
    }

    private void registeration() {
        String student_name_add = student_name.getText().toString().trim();
        String username_create = username.getText().toString().trim();
        String student_DOB = stud_DOB.getText().toString().trim();
        String create_pass = create_password.getText().toString().trim();
        String confirm_pass = confirm_password.getText().toString().trim();
        String email = email_student.getText().toString().trim();
        String address = student_address_parent.getText().toString().trim();
        String mobile = mobile_student.getText().toString().trim();
        String mobilep = "[6-9]{1}[0-9]{9}";
        String validemail = "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" + "\\@" + "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" + "(" + "\\." + "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" + ")+";
        Matcher matcherObj = Pattern.compile(validemail).matcher(email);

//        String PASSWORD_PATTERN = "^(?=.{4,})(?=.*[a-z|A-Z]).*$";
//
//        Matcher matcherObj1 = Pattern.compile(PASSWORD_PATTERN).matcher(username_create);
//
//
//        Matcher matcher = Pattern.compile("((?=.*[a-z])(?=.*\\d)(?=.*[A-Z])(?=.*[@#$%!]).{4,20})").matcher(username_create);

        String PASSWORD_PATTERN = "^(?=.{4,})(?=.*[a-z|A-Z]).*$";
        Matcher matcherObj1 = Pattern.compile(PASSWORD_PATTERN).matcher(username_create);
        Log.e("class_postion", class_position);
        Log.e("username",username_create);
        Log.e("match",matcherObj1.matches()+"");

        if (student_name_add.isEmpty()) {
            student_name.requestFocus();
            student_name.setError("Enter Student Full Name");
        }
        else if (username_create.isEmpty()) {
            username.requestFocus();
            username.setError("Enter UserName");
        }
        else if (!matcherObj1.matches()) {
            username.requestFocus();
            username.setError("Please enter valid username. Username must have at least 1 Alphabet and 4 characters.");
        }
        else if (!mobile.isEmpty() && mobile.length() < 10) {
            mobile_student.setError("Please Enter 10 Digit Mobile Number");
            mobile_student.requestFocus();
        } else if (!mobile.isEmpty() && !mobile.matches(mobilep)) {
            mobile_student.setError("Mobile Number Should Start With 6,7,8 or 9.Please Enter Valid Mobile Number.");
            mobile_student.requestFocus();
        } else if (!email.isEmpty() && !matcherObj.matches()) {
            email_student.setError("Please Enter vaild Email");
            email_student.requestFocus();
        } else if (address.isEmpty()) {
            student_address_parent.requestFocus();
            student_address_parent.setError("Enter Student Address");
            // setEditextError(garudian_alrt_number,"Please Enter Gaurdian Alternative Mobile Number");
        } else if (class_position.equals("0")) {
            class_select.setFocusable(true);
            class_select.setFocusableInTouchMode(true);
            class_select.requestFocus();
            class_select.setError("Please Select Class");
        } else if (student_DOB.isEmpty()) {
            //  focusOnViewDOB();
            stud_DOB.requestFocus();
            stud_DOB.setError("Select Date of Birth");
            //setErr(stud_DOB, "Please Select Date of Birth");
            stud_DOB.requestFocus();
        } else if (gender_position == 0) {
            gender_select.setFocusable(true);
            gender_select.setFocusableInTouchMode(true);
            gender_select.requestFocus();
            ((TextView) gender_select.getChildAt(0)).setError("Message");
            setSpinnerError(gender_select, "Please Select Gender");
        } else if (create_pass.isEmpty()) {
            create_password.requestFocus();
            create_password.setError("Please Create Password");
            create_password.requestFocus();
        } else if (create_pass.length() < 6) {
            create_password.requestFocus();
            create_password.setError("Please Create 6 Digit Password");
        } else if (confirm_pass.isEmpty()) {
            confirm_password.requestFocus();
            confirm_password.setError("Please Confirm Your Enter Password");
            confirm_password.requestFocus();
        } else if (create_pass.length() < 6) {
            create_password.requestFocus();
            create_password.setError("Please Enter 6 Digit Password");
        } else if (!confirm_pass.equals(create_pass)) {
            Log.e("password_missmatch", "true");
            confirm_password.setError("Password Mismatch");
            confirm_password.requestFocus();
        } else {
            getRegistartion(student_name_add, username_create, mobile, email, address, class_position, student_DOB, gender_position, confirm_pass);

        }

    }

    private void setEditextError(EditText editext, String error) {
        String selectview = editext.getText().toString().trim();
        if (selectview.isEmpty()) {
            editext.findFocus();
            editext.setHint(error);
            editext.setHintTextColor(Color.RED);
            editext.performClick();
        }
    }

    private void setSpinnerError(Spinner spinner, String error) {
        View selectedView = spinner.getSelectedView();
        if (selectedView != null && selectedView instanceof TextView) {
            spinner.requestFocus();
            TextView selectedTextView = (TextView) selectedView;
            selectedTextView.setError("Error"); // any name of the error will do
            selectedTextView.setTextColor(Color.RED); //text color in which you want your error message to be displayed
            selectedTextView.setText(error); // actual error message
            spinner.performClick(); // to open the spinner list if error is found.
        }
    }

    private void getClassApiCalling(final ListView listViewdata, final ProgressBar progressBar) {
        if (!CommonUtilities.isOnline(this)) {
            Toast.makeText(StudentRegisterActivity.this, "Please On Your Internet Connection", Toast.LENGTH_LONG).show();
            return;
        }
        String url = Global.WEBBASE_URL + "getAllClassesApp";
        final String string_json = "Result";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                String res = response.toString();
                parseResponseClasses(res, listViewdata, progressBar);
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
                        parseResponseClasses(response.toString(), listViewdata, progressBar);

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
        };
        VolleySingleton.getInstance(StudentRegisterActivity.this).addToRequestQueue(stringRequest, string_json);

    }

    private void parseResponseClasses(String response, ListView listViewdata, ProgressBar progressBar) {
        Log.e("class_res", response);
        try {
            JSONObject obj = new JSONObject(response);
            //boolean result = obj.getBoolean("Result");
            boolean message_Error = obj.getBoolean("Error");
            if (message_Error == false) {
                JSONArray jsonArray = obj.getJSONArray("Message");
                for (int i = 0; i < jsonArray.length(); i++) {
                    Log.e("class_lenth", String.valueOf(i));
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    String classId = jsonObject.getString("ClassId");
                    String StudentClass = jsonObject.getString("StudentClass");
                    studentClassModel = new StudentClassModel(classId, StudentClass);
                    studentClassModelArrayList.add(studentClassModel);
                }
                studentClassAdapter = new StudentClassAdapter(this, studentClassModelArrayList);
                listViewdata.setAdapter(studentClassAdapter);
                progressBar.setVisibility(View.GONE);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void getRegistartion(String student_name_add, String username_create, String mobile, String email, String address, String class_position, String student_dob, int gender_position, String confirm_pass) {
        if (!CommonUtilities.isOnline(this)) {
            Toast.makeText(StudentRegisterActivity.this, "Please On Your Internet Connection", Toast.LENGTH_LONG).show();
            return;
        }
        String url = Global.WEBBASE_URL + "insertStudent";
        final String string_json = "Result";
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading...");
        progressDialog.setTitle("Wait...");
        progressDialog.show();
        JSONObject params = new JSONObject();
        String mRequestBody = "";
        try {

            params.put("StudentName", student_name_add);
            Log.e("StudentName", student_name_add);
            if (gender_position == 1) {
                params.put("StudentGender", "Male");
                Log.e("StudentGender", "Male");
            } else if (gender_position == 2) {
                params.put("StudentGender", "Female");
                Log.e("StudentGender", "Female");
            } else {
                params.put("StudentGender", "Other");
                Log.e("StudentGender", "Female");
            }
            if (mobile.contains("")) {

            } else {
                params.put("StudentMobile", mobile);
                Log.e("StudentMobile", mobile);
            }

            params.put("StudentUsername", username_create);
            Log.e("StudentUsername", username_create);
            if (email.contains("")) {

            } else {
                params.put("StudentEmail", email);
                Log.e("StudentEmail", email);
            }

            params.put("StudentAddress", address);
            Log.e("StudentAddress", address);

            params.put("StudentCity", address);
            Log.e("StudentCity", address);


//            SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd");
//            String formattedDate1 = df1.format(datetoSend);

            params.put("StudentDOB", datetoSend);
            Log.e("StudentDOB", datetoSend);


            Date c = Calendar.getInstance().getTime();
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            String formattedDate = df.format(c);

            params.put("StudentDORegis", formattedDate);
            Log.e("StudentDORegis", formattedDate);


            params.put("StudentPassword", confirm_pass);
            Log.e("StudentPassword", confirm_pass);

            params.put("StudentImage", "");
            Log.e("StudentImage", "");

            params.put("ParentId", AarambhSharedPreference.loadParentIdFromPreference(this));
            Log.e("ParentId", AarambhSharedPreference.loadParentIdFromPreference(this));


            params.put("ClassId", this.class_position);
            Log.e("ClassId", this.class_position);
            params.put("SchoolId", AarambhSharedPreference.loadSchoolIdFromPreference(this));
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
                parseResponseRegister(res, progressDialog);
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
                        parseResponseRegister(response.toString(), progressDialog);

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
                headers.put("Authorization", "Bearer " + AarambhSharedPreference.loadUserTokenFromPreference(StudentRegisterActivity.this));

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
        VolleySingleton.getInstance(StudentRegisterActivity.this).addToRequestQueue(jsonObjectRequest, string_json);
    }

    private void parseResponseRegister(String response, ProgressDialog progressDialog) {
        try {
            progressDialog.show();
            JSONObject obj = new JSONObject(response);
            //boolean result = obj.getBoolean("Result");
            String status = obj.getString("status");
            boolean success = obj.getBoolean("success");
            Log.e("response", response);
            if (success == false) {
                String error = obj.getString("error");
                Toast.makeText(this, error + "", Toast.LENGTH_LONG).show();
                Log.e("error", error);
                progressDialog.dismiss();
            } else if (success == true) {
                String message = obj.getString("Message");
                Log.e("massage", message);
                if (message.equalsIgnoreCase("Student Email Already Exists.")) {
                    Toast.makeText(this, "Email Already Exists.", Toast.LENGTH_LONG).show();
                    progressDialog.dismiss();
                } else if (message.equalsIgnoreCase("Student Username Already Exists.")) {
                    Toast.makeText(this, "Username Already Exists.", Toast.LENGTH_LONG).show();
                    progressDialog.dismiss();
                } else if (message.equalsIgnoreCase("Student saved successfully")) {
                    Toast.makeText(this, "Student Registration Successfully.", Toast.LENGTH_LONG).show();
                    Intent intent2 = new Intent(StudentRegisterActivity.this, StudentListShowActivity.class);
                    startActivity(intent2);
                    progressDialog.dismiss();

                }
            } else {
                Toast.makeText(this, "Registration Failed.Pls Try Again", Toast.LENGTH_LONG).show();
                progressDialog.dismiss();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(StudentRegisterActivity.this, StudentListShowActivity.class);
        startActivity(intent);
    }
}
