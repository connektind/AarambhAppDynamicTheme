package com.example.aarambhappdynamictheme.activity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.StrictMode;
import android.provider.MediaStore;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.example.aarambhappdynamictheme.multipath.AppHelper;
import com.example.aarambhappdynamictheme.multipath.VolleyMultipartRequest;
import com.example.aarambhappdynamictheme.sheardPrefrence.AarambhSharedPreference;
import com.example.aarambhappdynamictheme.sheardPrefrence.AarambhThemeSharedPrefreence;
import com.example.aarambhappdynamictheme.textGradient.CircleTransform;
import com.example.aarambhappdynamictheme.util.CommonUtilities;
import com.example.aarambhappdynamictheme.util.Global;
import com.example.aarambhappdynamictheme.util.VolleySingleton;
import com.example.aarambhappdynamictheme.util.VolleySingletonImage;

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
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.example.aarambhappdynamictheme.util.Global.urlProfileImg;
import static com.example.aarambhappdynamictheme.util.Global.urlStudentImg;

public class UpdateProfileActivity extends AppCompatActivity {
    ImageView imgProfile,camera_upload;
    ImageButton profile_update_back;
    Button submit_profile;
    EditText user_name,user_mobile,user_email,user_address;
    TextView user_DOB;
    Bitmap profilebitmap;
    Spinner gender_student;
    int gender_position;
    String datetoSend;
    final Calendar calendar = Calendar.getInstance();
    VolleyMultipartRequest.DataPart profile_uri;
    ArrayList<String>you_Chapter;
    String student_profile;
    ProgressDialog progressDialog;
    String gender_update;
    ImageView red_gradient;
    String base_image,transparent_color,base_color_one,base_color_two;
    LinearLayout profile_circle;
    TextView mobile_profile,name_profile,email_profile,gender_profile,dob_profile,address_profile;
    ImageView mobile_profile_image,name_profile_image
    ,email_profile_image,gender_profile_image,Dob_profile_image,address_profile_image;
    String mobile_shrd,name_shrd,email_shrd,gender_shrd,Dob_shrd,address_shrd,camera_shrd;
    Bitmap mobile_bitmap,name_bitmap,email_bitmap,gender_bitmap,Dob_bitmap,address_bitmap,camera_bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);
        checkOrientation();
        base_image= AarambhThemeSharedPrefreence.loadBaseImageFromPreference(this);
        transparent_color=AarambhThemeSharedPrefreence.loadBaseImageTransparentFromPreference(this);
        Log.e("transprent",transparent_color);
        base_color_one=AarambhThemeSharedPrefreence.loadBaseColorOneFromPreference(this);
        base_color_two=AarambhThemeSharedPrefreence.loadBaseColorTwoFromPreference(this);


        init();
        listner();
        student_profile=AarambhSharedPreference.loadStudentProfileFromPreference(this);

        getUserDataApiCalling();
    }
    private void checkOrientation() {
        if (getResources().getBoolean(R.bool.portrait_only)) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
    }
    private void getUserDataApiCalling() {
        if (!CommonUtilities.isOnline(this)) {
            Toast.makeText(UpdateProfileActivity.this, "Please On Your Internet Connection", Toast.LENGTH_LONG).show();
            return;
        }
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
                params.put("studentId", AarambhSharedPreference.loadStudentIdFromPreference(UpdateProfileActivity.this));
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
                headers.put("Authorization", "Bearer "+ AarambhSharedPreference.loadUserTokenFromPreference(UpdateProfileActivity.this));
                return headers;
            }
        };
        VolleySingleton.getInstance(UpdateProfileActivity.this).addToRequestQueue(stringRequest, string_json);

    }

    private void parseResponseProfileApi(String response) {
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
                        //student_name_profile.setText(StudentName);
                        user_name.setText(StudentName);
                        String StudentGender = jsonObject.getString("StudentGender");
                        if (StudentGender.equals("Male")) {
                            String[] type_gender = {"Male", "Female", "Other"};
                            ArrayAdapter<String> arrayAdapter_gender = new ArrayAdapter<String>(UpdateProfileActivity.this, android.R.layout.simple_dropdown_item_1line, type_gender);
                            gender_student.setAdapter(arrayAdapter_gender);
                            gender_student.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    gender_position = position;
                                    if (gender_position == 0) {
                                        gender_update = "Male";
                                    } else if (gender_position == 1) {
                                        gender_update = "Female";
                                    } else if (gender_position == 2) {
                                        gender_update = "Other";

                                    }
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {


                                }
                            });

                        } else if (StudentGender.equals("Female")) {
                            String[] type_gender = {"Female", "Male", "Other"};
                            ArrayAdapter<String> arrayAdapter_gender = new ArrayAdapter<String>(UpdateProfileActivity.this, android.R.layout.simple_dropdown_item_1line, type_gender);
                            gender_student.setAdapter(arrayAdapter_gender);
                            gender_student.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    gender_position = position;
                                    if (gender_position == 0) {
                                        gender_update = "Female";
                                    } else if (gender_position == 1) {
                                        gender_update = "Male";
                                    } else if (gender_position == 2) {
                                        gender_update = "Other";

                                    }
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {


                                }
                            });
                        } else if (StudentGender.equals("Other")) {
                            String[] type_gender = {"Other", "Female", "Male"};
                            ArrayAdapter<String> arrayAdapter_gender = new ArrayAdapter<String>(UpdateProfileActivity.this, android.R.layout.simple_dropdown_item_1line, type_gender);
                            gender_student.setAdapter(arrayAdapter_gender);
                            gender_student.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    gender_position = position;
                                    if (gender_position == 0) {
                                        gender_update = "Other";
                                    } else if (gender_position == 1) {
                                        gender_update = "Female";
                                    } else if (gender_position == 2) {
                                        gender_update = "Male";

                                    }
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {


                                }
                            });
                        }
                        String StudentMobile = jsonObject.getString("StudentMobile");
                        if (StudentMobile.contains("null")) {
                            user_mobile.setText("");
                        } else {
                            user_mobile.setText(StudentMobile);
                        }
                        String StudentUsername = jsonObject.getString("StudentUsername");
                        String StudentEmail = jsonObject.getString("StudentEmail");
                        if (StudentEmail.contains("null")) {
                            user_email.setText("");
                        } else {
                            user_email.setText(StudentEmail);
                        }
                        String StudentAddress = jsonObject.getString("StudentAddress");
                        user_address.setText(StudentAddress);
                        String StudentCity = jsonObject.getString("StudentCity");

                        String StudentDOB = jsonObject.getString("StudentDOB");

                        String firstFourChars = StudentDOB.substring(0, 10);
                        datetoSend = firstFourChars;
                        DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
                        DateFormat outputFormat = new SimpleDateFormat("dd-MM-yyyy");
                        Date date = inputFormat.parse(firstFourChars);
                        String outputDateStr = outputFormat.format(date);
                        // student_DOB.setText(outputDateStr);
                        user_DOB.setText(outputDateStr);
                        String StudentDORegis = jsonObject.getString("StudentDORegis");
                        //  String StudentGuardianName = jsonObject.getString("StudentGuardianName");

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

    private void init() {
        StrictMode.ThreadPolicy policy = new
                StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        you_Chapter=new ArrayList<>();
        imgProfile=(ImageView)findViewById(R.id.img_profile_1);
        profile_update_back=(ImageButton)findViewById(R.id.profile_update_back);
        submit_profile=(Button)findViewById(R.id.submit_profile);
        user_name=(EditText)findViewById(R.id.name_update_profile);
        user_mobile=(EditText)findViewById(R.id.mobile_update_profile);
        user_address=(EditText)findViewById(R.id.address_update_profile);
        user_DOB=(TextView) findViewById(R.id.DOB_update_profile);
        user_email=(EditText)findViewById(R.id.email_update_profile);
        camera_upload=(ImageView)findViewById(R.id.camera_upload);
        gender_student = (Spinner) findViewById(R.id.gender_select);
        //theme dynamic
        red_gradient=findViewById(R.id.red_gradient);
        profile_circle=findViewById(R.id.profile_circle);
        mobile_profile=findViewById(R.id.mobile_profile);
        mobile_profile.setTextColor(Color.parseColor(base_color_one));
        mobile_profile_image=findViewById(R.id.mobile_profile_image);
        name_profile_image=findViewById(R.id.name_profile_image);
        email_profile_image=findViewById(R.id.email_profile_image);
        gender_profile_image=findViewById(R.id.gender_profile_image);
        Dob_profile_image=findViewById(R.id.Dob_profile_image);
        address_profile_image=findViewById(R.id.address_profile_image);
        name_profile=findViewById(R.id.name_profile);
        name_profile.setTextColor(Color.parseColor(base_color_one));
        email_profile=findViewById(R.id.email_profile);
        email_profile.setTextColor(Color.parseColor(base_color_one));
        gender_profile=findViewById(R.id.gender_profile);
        gender_profile.setTextColor(Color.parseColor(base_color_one));
        dob_profile=findViewById(R.id.dob_profile);
        dob_profile.setTextColor(Color.parseColor(base_color_one));
        address_profile=findViewById(R.id.address_profile);
        address_profile.setTextColor(Color.parseColor(base_color_one));
        getDynamicData();
        themeDynamicAdd();

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
            camera_shrd=AarambhThemeSharedPrefreence.loadCameraIconFromPreference(this);
            camera_bitmap=getBitmapFromURL(camera_shrd);
            Drawable dr = new BitmapDrawable((camera_bitmap));
            camera_upload.setBackgroundDrawable(dr);

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

        GradientDrawable gd1 = new GradientDrawable(
                GradientDrawable.Orientation.TOP_BOTTOM, colors);
        gd1.setCornerRadius(100f);

        GradientDrawable gd2 = new GradientDrawable(
                GradientDrawable.Orientation.TOP_BOTTOM, colors);
        gd2.setCornerRadius(50f);

        //apply the button background to newly created drawable gradient
        red_gradient.setBackground(gd);
        //mobile_profile_image.setBackground(gd1);

        submit_profile.setBackground(gd2);
        profile_circle.setBackground(gd1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 101 && resultCode == RESULT_OK) {
            try {
                Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
                imgProfile.setImageBitmap(thumbnail);
                profilebitmap = thumbnail;
//
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (requestCode == 102 && resultCode == RESULT_OK) {
            try {
                Uri selectedImage = data.getData();
                imgProfile.setImageURI(selectedImage);
                Bitmap bit_profile = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
                profilebitmap = bit_profile;
                Log.e("profileThumbnail", String.valueOf(profile_uri));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent=new Intent(UpdateProfileActivity.this,ProfileShowActivity.class);
        startActivity(intent);
    }
    private void selectProfileTypePic() {
        final CharSequence[] items = {"Take Photo", "Choose from Gallery", "Cancel"};

        TextView title = new TextView(UpdateProfileActivity.this);
        title.setText("Add Photo!");
        title.setBackgroundColor(getResources().getColor(R.color.red));
        title.setPadding(10, 15, 15, 10);
        title.setGravity(Gravity.CENTER);
        title.setTextColor(Color.WHITE);
        title.setTextSize(22);

        AlertDialog.Builder builder = new AlertDialog.Builder(
                UpdateProfileActivity.this);
        builder.setCustomTitle(title);

        builder.setItems(items, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals("Take Photo")) {
                    openCameraForProfile();
                } else if (items[item].equals("Choose from Gallery")) {
                    openGalleryForProfile();
                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }

            }
        });
        builder.show();
    }

    public void openCameraForProfile() {
        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, 101);
    }

    public void openGalleryForProfile() {
        Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent, 102);
    }


    private void listner() {
        user_DOB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(UpdateProfileActivity.this, android.R.style.Theme_Holo_Light_Dialog, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        //et_age.setText(year + "-" + (month + 1) + "-" + dayOfMonth);
                        user_DOB.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                        user_DOB.setError(null);
                        user_DOB.clearFocus();
                        datetoSend = year + "-" + (month + 1) + "-" + dayOfMonth;
                    }
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
                datePickerDialog.setCancelable(false);
                datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
            }
        });
//                String[] type_gender = {"---Select Student’s Gender---", "Male", "Female"};
//                ArrayAdapter<String> arrayAdapter_gender = new ArrayAdapter<String>(RedTheme_UpdateProfile.this, android.R.layout.simple_dropdown_item_1line, type_gender);
//                gender_student.setAdapter(arrayAdapter_gender);
//                gender_student.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//                    @Override
//                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                        gender_position = position;
//                    }
//
//                    @Override
//                    public void onNothingSelected(AdapterView<?> parent) {
//
//
//                    }
//                });


        camera_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectProfileTypePic();

            }
        });
        submit_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mobile=user_mobile.getText().toString().trim();
                String name=user_name.getText().toString().trim();
                String email=user_email.getText().toString().trim();
                String dob=user_DOB.getText().toString().trim();
                String add=user_address.getText().toString().trim();
                //String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
                String validemail = "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" + "\\@" + "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" + "(" + "\\." + "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" + ")+";
                Matcher matcherObj = Pattern.compile(validemail).matcher(email);
                String mobilep = "[6-9]{1}[0-9]{9}";
//                if (mobile.isEmpty()){
//                    user_mobile.setError("Please Enter Mobile Number");
//                    user_mobile.requestFocus();
//                } else
                if (!mobile.isEmpty()&&mobile.length() < 10) {
                    user_mobile.setError("Please Enter 10 Digit Mobile Number");
                    user_mobile.requestFocus();

                }else if (!mobile.isEmpty()&&!mobile.matches(mobilep)){
                    user_mobile.setError("Mobile Number Should Start With 6,7,8 or 9.Please Enter Valid Mobile Number.");
                    user_mobile.requestFocus();
                }
                else if (name.isEmpty())
                {
                    user_name.setError("Enter Name");
                    user_name.requestFocus();
                }
                else if (!email.isEmpty()&&!matcherObj.matches()) {
                    user_email.setError("Please Enter vaild Email");
                    user_email.requestFocus();
                }
                else if (gender_position==4){
                    gender_student.setFocusable(true);
                    gender_student.setFocusableInTouchMode(true);
                    gender_student.requestFocus();
                    ((TextView) gender_student.getChildAt(0)).setError("Message");
                    setSpinnerError(gender_student, "Please Select Gender");
                }
//                else if (email.matches(emailPattern) && email.length() > 0){
//                    user_email.setError("Please Enter vaild Email");
//                    user_email.requestFocus();
//                }
                else if (add.isEmpty()){
                    user_address.setError("Please Enter Address");
                    user_address.requestFocus();

                }
                else{
                    updateApiData(mobile,name,email,gender_update,dob,add);
//                    Toast.makeText(RedTheme_UpdateProfile.this,"Saved Successfully.",Toast.LENGTH_LONG).show();
//                Intent intent=new Intent(RedTheme_UpdateProfile.this, DashBoardSixthStandardActivity.class);
//                startActivity(intent);
                }
            }
        });
        profile_update_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(UpdateProfileActivity.this, ProfileShowActivity.class);
                startActivity(intent);
            }
        });
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

    private void updateApiData(final String mobile, final String name, final String email, final String gender_update, String dob, final String add) {
        if (!CommonUtilities.isOnline(this)) {
            Toast.makeText(UpdateProfileActivity.this, "Please On Your Internet Connection", Toast.LENGTH_LONG).show();
            return;
        }
        String url = Global.WEBBASE_URL + "updateStudentMain";
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading...");
        progressDialog.setTitle("Wait...");
        progressDialog.show();

        VolleyMultipartRequest multipartRequest=new VolleyMultipartRequest(Request.Method.POST, url, new Response.Listener<NetworkResponse>() {
            @Override
            public void onResponse(NetworkResponse response) {
                String resultResponse = new String(response.data);
                parseResponseUpdateProfile(resultResponse);
                Log.e("Response",resultResponse);
                //  parseResponseUpdateProfile(String.valueOf(response));

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                NetworkResponse networkResponse = error.networkResponse;
                String errorMessage = "Unknown error";
                if (networkResponse == null) {
                    if (error.getClass().equals(TimeoutError.class)) {
                        errorMessage = "Request timeout";
                    } else if (error.getClass().equals(NoConnectionError.class)) {
                        errorMessage = "Failed to connect server";
                    }
                } else {
                    try {
                        String result = new String(networkResponse.data);
                        Log.e("Result",result);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                Log.i("Error", errorMessage);
                error.printStackTrace();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                try {
                    params.put("StudentId", AarambhSharedPreference.loadStudentIdFromPreference(UpdateProfileActivity.this));
                    params.put("StudentName", name);

                    params.put("StudentGender", gender_update);
                    if (mobile.isEmpty()){

                    }else{
                        params.put("StudentMobile", mobile);
                    }
                    if (email.isEmpty()){

                    }else {
                        params.put("StudentEmail", email.trim());
                    }
                    params.put("StudentAddress", add);
                    params.put("StudentCity", add);
                    Date c = Calendar.getInstance().getTime();
                    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                    String formattedDate = df.format(c);
                    //  String formattedDate11 = df.format(datetoSend);
                    try {
                        Log.e("red_Dob",datetoSend);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    params.put("StudentDOB", datetoSend);
                    Log.e("StudentDOB", datetoSend);
                    params.put("StudentDORegis", formattedDate);
                    Log.e("StudentDORegis", formattedDate);
                    //  params.put("StudentImage",profile_uri+".PNG") ;
                    params.put("ClassId", AarambhSharedPreference.loadClassIdFromPreference(UpdateProfileActivity.this));
                    Log.e("ClassId", AarambhSharedPreference.loadClassIdFromPreference(UpdateProfileActivity.this));
                    params.put("StatusId", "1");
                    params.put("CreatedById", "1");
                    params.put("CreationDate", "");
                    params.put("ModifiedById", "1");
                    params.put("ModificationDate", "2020-02-26");
                    Log.e("apiData---", (params).toString());

                } catch (Exception e) {
                    e.printStackTrace();
                }
                return params;
            }

            @Override
            protected Map<String, DataPart> getByteData() {
                Map<String, DataPart> params = new HashMap<>();
                // file name could found file base or direct access from real path
                // for now just get bitmap data from ImageView
                Log.e("profile", String.valueOf(profile_uri));
                if (profilebitmap==null){
                    String profile= (AarambhSharedPreference.loadStudentProfileFromPreference(UpdateProfileActivity.this));
                    params.put("StudentImage",new DataPart("img", AppHelper.getBitmap(UpdateProfileActivity.this,getBitmapFromURL(urlStudentImg+profile)), "image/jpeg"));
                }else {
                    profile_uri = new DataPart("img", AppHelper.getBitmap(UpdateProfileActivity.this, profilebitmap), "image/jpeg");
                    params.put("StudentImage", profile_uri);
                    Log.e("profileData", String.valueOf(profile_uri));
                }

                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                //headers.put("Content-Type", "application/json; charset=utf-8");
                headers.put("Authorization", "Bearer " + AarambhSharedPreference.loadUserTokenFromPreference(UpdateProfileActivity.this));
                return headers;
            }
        };
        multipartRequest.setRetryPolicy(new DefaultRetryPolicy(
                Global.WEBSERVICE_TIMEOUT_VALUE_IN_MILLIS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        ));
        multipartRequest.setShouldCache(false);
        VolleySingletonImage.getInstance(UpdateProfileActivity.this).addToRequestQueue(multipartRequest);
    }

    private void parseResponseUpdateProfile(String response) {
        try {
            Log.e("Update Response",response);
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
                if(message.equalsIgnoreCase("Student Updated Successfully.")){
                    getUserDataApiCalling();
                    progressDialog.dismiss();
                    Toast.makeText(UpdateProfileActivity.this,"Saved Successfully.",Toast.LENGTH_LONG).show();
                    AarambhSharedPreference.saveStudentNameToPreference(this,user_name.getText().toString().trim());
                    getChannelPlayListVideos();
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
    @Override
    public void finish() {
        Intent in=new Intent(UpdateProfileActivity.this, DashBoardActivity.class);
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

                        String class_id = AarambhSharedPreference.loadClassIdFromPreference(UpdateProfileActivity.this);
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
                    Log.e("classId", AarambhSharedPreference.loadClassIdFromPreference(UpdateProfileActivity.this));

                    switch (AarambhSharedPreference.loadClassIdFromPreference(UpdateProfileActivity.this)) {
                        case "1":
                            //btn_login.setClickable(false);
                            Intent intent = new Intent(UpdateProfileActivity.this, DashBoardActivity.class);
                            //Log.e("6thDetails", String.valueOf(ytt6th.getSubject().size()));
                            intent.putExtra("6thDetails", ytt6th);
                            intent.putExtra("PlayListDetails", playListArrayList);
                            startActivity(intent);
                            //progressdialog.dismiss();
                            break;
                        case "2":
                            //btn_login.setClickable(false);
                            Intent intent1 = new Intent(UpdateProfileActivity.this, DashBoardActivity.class);
                            intent1.putExtra("7thDetails", ytt7th);
                            intent1.putExtra("PlayListDetails", playListArrayList);
                            startActivity(intent1);
                            //progressdialog.dismiss();
                            break;
                        case "3":
                            //btn_login.setClickable(false);
                            Intent intent2 = new Intent(UpdateProfileActivity.this, DashBoardActivity.class);
                            intent2.putExtra("8thDetails", ytt8th);
                            intent2.putExtra("PlayListDetails", playListArrayList);
                            startActivity(intent2);
                            //progressdialog.dismiss();
                            break;
                        default:
                            //btn_login.setClickable(false);
                            Intent intent3 = new Intent(UpdateProfileActivity.this, DashBoardActivity.class);
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
