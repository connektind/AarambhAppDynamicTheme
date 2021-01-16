package com.example.aarambhappdynamictheme.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.pm.ActivityInfo;
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
import com.example.aarambhappdynamictheme.adapter.PraticesChapterRulesAdapter;
import com.example.aarambhappdynamictheme.adapter.TestChapterRulesAdapter;
import com.example.aarambhappdynamictheme.model.PraticesChapterListModel;
import com.example.aarambhappdynamictheme.model.TestChapterRulesModel;
import com.example.aarambhappdynamictheme.model.VideoDetails;
import com.example.aarambhappdynamictheme.sheardPrefrence.AarambhSharedPreference;
import com.example.aarambhappdynamictheme.sheardPrefrence.AarambhThemeSharedPrefreence;
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

public class TestListActivity extends AppCompatActivity {
    Button start_test;
    ImageView back_btn_test_rules;
    TextView subscribe_name;
    VideoDetails getVideoDetails;
    RecyclerView chapter_wise_test;
    TestChapterRulesAdapter testChapterRulesAdapter;
    ArrayList<TestChapterRulesModel> testChapterRulesModelArrayList;
    TestChapterRulesModel testChapterRulesModel;
    LinearLayout test_list_background,test_list_transprent;
    String base_image,transparent_color,base_color_one,base_color_two;
    Bitmap Background;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_list);
        checkOrientation();
        base_image= AarambhThemeSharedPrefreence.loadBaseImageFromPreference(this);
        transparent_color=AarambhThemeSharedPrefreence.loadBaseImageTransparentFromPreference(this);
        Log.e("transprent",transparent_color);
        base_color_one=AarambhThemeSharedPrefreence.loadBaseColorOneFromPreference(this);
        base_color_two=AarambhThemeSharedPrefreence.loadBaseColorTwoFromPreference(this);
        init();
        listner();
        getDummyChapterData();
    }
    private void checkOrientation() {
        if (getResources().getBoolean(R.bool.portrait_only)) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
    }

    private void getDummyChapterData() {
        {
            if (!CommonUtilities.isOnline(this)) {
                Toast.makeText(TestListActivity.this, "Please On Your Internet Connection", Toast.LENGTH_LONG).show();
                return;
            }
            final String class_id = AarambhSharedPreference.loadClassIdFromPreference(this);
            final String course_id = AarambhSharedPreference.loadCourseIdFromPreference(this);
            final String chapter_id = AarambhSharedPreference.loadTopicIdFromPreference(this);

//            http://35.200.220.64:1500/aarambhTesting/getAllChapterPractice?pageNo=1&size=5&ChapterId=56
            String url = Global.WEBBASE_URL + "getAllChapterTest?pageNo=1&size=5&ChapterId=5";
            final String string_json = "Result";
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    String res = response.toString();
                    parseResponseTestApi(res);
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
                            parseResponseTestApi(response.toString());

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
//                    params.put("ClassId", class_id);
//                    params.put("CourseId", course_id);
//                    params.put("ChapterId", chapter_id);
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
                    headers.put("Authorization", "Bearer " + AarambhSharedPreference.loadUserTokenFromPreference(TestListActivity.this));
                    return headers;
                }
            };
            VolleySingleton.getInstance(TestListActivity.this).addToRequestQueue(stringRequest, string_json);
//
//        testChapterRulesModelArrayList.add(new TestChapterRulesModel("1","chapter One"));
//        testChapterRulesModelArrayList.add(new TestChapterRulesModel("2","chapter two"));
//        testChapterRulesModelArrayList.add(new TestChapterRulesModel("3","chapter Three"));
//        praticesChapterRulesAdapter=new PraticesChapterRulesAdapter(this,testChapterRulesModelArrayList);
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
//        chapter_wise_test.setLayoutManager(linearLayoutManager);
//        chapter_wise_test.setAdapter(praticesChapterRulesAdapter);

        }
    }

    private void parseResponseTestApi(String response) {
        Log.e("chapter_res", response);
        try {
            JSONObject jsonObject1 = new JSONObject(response);
            String status = jsonObject1.getString("status");

            boolean success = jsonObject1.getBoolean("success");
            if (success == false) {
                String error = jsonObject1.getString("error");
                Toast.makeText(this, error + "", Toast.LENGTH_LONG).show();
            } else if (success == true) {
                String Message = jsonObject1.getString("Message");
                //  if(status.equalsIgnoreCase("200")&&success==true&&Message.equalsIgnoreCase("Data Found")) {
                 JSONArray jsonArray = jsonObject1.getJSONArray("Data");
               // JSONArray jsonArray =jsonObject1.getJSONArray("Message");
                Log.e("Message", String.valueOf(jsonArray));
                if (Message.equalsIgnoreCase("Data Found")) {
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        Log.e("chapter_length", String.valueOf(i));
                        Log.e("json", String.valueOf(jsonObject));
                        String TestMasterId = jsonObject.getString("TestMasterId");
                        String ClassId = jsonObject.getString("ClassId");
                        String CourseId = jsonObject.getString("CourseId");
                        String TopicId = jsonObject.getString("TopicId");
                        String ChapterId = jsonObject.getString("ChapterId");
                        String TestTitle = jsonObject.getString("TestTitle");
                        String FromDate = jsonObject.getString("FromDate");
                        String ToDate = jsonObject.getString("ToDate");
                        String TestDuration = jsonObject.getString("TestDuration");
                        String StatusId = jsonObject.getString("StatusId");
                        String CreatedById = jsonObject.getString("CreatedById");
                        String ModifiedById = jsonObject.getString("ModifiedById");
                        String CreationDate = jsonObject.getString("CreationDate");
                        String ModificationDate = jsonObject.getString("ModificationDate");
                        testChapterRulesModel = new TestChapterRulesModel(TestMasterId, ClassId, CourseId, TopicId, ChapterId, TestTitle, FromDate, ToDate, TestDuration, StatusId, CreatedById, ModifiedById, CreationDate, ModificationDate);
                        testChapterRulesModelArrayList.add(testChapterRulesModel);
                    }
                }else{
                    Toast.makeText(TestListActivity.this,""+Message,Toast.LENGTH_LONG).show();
                }
                testChapterRulesAdapter=new TestChapterRulesAdapter(this,testChapterRulesModelArrayList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        chapter_wise_test.setLayoutManager(linearLayoutManager);
        chapter_wise_test.setAdapter(testChapterRulesAdapter);

//                } else {
//                    Toast.makeText(this, Message + "", Toast.LENGTH_LONG).show();
//
//                }
            }
            // Log.e("Question Type", praticeSubjectDataModel.getTestQuestionType());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    private void getDummyChapterData() {

//        testChapterRulesModelArrayList.add(new TestChapterRulesModel("1","chapter One"));
//        testChapterRulesModelArrayList.add(new TestChapterRulesModel("2","chapter two"));
//        testChapterRulesModelArrayList.add(new TestChapterRulesModel("3","chapter Three"));
//        testChapterRulesAdapter=new TestChapterRulesAdapter(this,testChapterRulesModelArrayList);
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
//        chapter_wise_test.setLayoutManager(linearLayoutManager);
//        chapter_wise_test.setAdapter(testChapterRulesAdapter);
  //  }

    private void init() {
        StrictMode.ThreadPolicy policy = new
                StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        start_test=findViewById(R.id.start_test);
        back_btn_test_rules=findViewById(R.id.back_btn_test_rules);
        chapter_wise_test=findViewById(R.id.chapter_wise_test);
        testChapterRulesModelArrayList=new ArrayList<>();
        test_list_background=findViewById(R.id.test_list_background);
        test_list_transprent=findViewById(R.id.test_list_transprent);
        test_list_transprent.setBackgroundColor(Color.parseColor(transparent_color));
        subscribe_name=findViewById(R.id.subscribe_name);
        TextColorGradient textColorGradient=new TextColorGradient();
        textColorGradient.getColorTextGradient(subscribe_name,base_color_one,base_color_two);
       // getDynamicData();
        themeDynamicAdd();
    }

    private void listner() {
        back_btn_test_rules.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in=new Intent(TestListActivity.this, VideoListActivity.class);
                startActivity(in);
            }
        });

    }

    @Override
    public void onBackPressed() {
        Intent in=new Intent(TestListActivity.this, VideoListActivity.class);
        startActivity(in);
    }

    @Override
    public void finish() {
        Intent in=new Intent(TestListActivity.this, VideoListActivity.class);
        in.putExtra("DataSubject", getVideoDetails);
        //  in.putExtra("LandDetail", landDetailModel);
        in.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        setResult(RESULT_OK, in);
        super.finish();
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
      //  start_pratices.setBackground(gd);
    }
    private void themeDynamicAdd() {

        //Bitmap myImage = getBitmapFromURL(image);
        try {
            Background=getBitmapFromURL(base_image);
            Drawable dr = new BitmapDrawable((Background));
            test_list_background.setBackgroundDrawable(dr);

            Bitmap back_bitmap;
            String back_shrd=AarambhThemeSharedPrefreence.loadBackArrowIconFromPreference(this);
            back_bitmap = getBitmapFromURL(back_shrd);
            Drawable dr1 = new BitmapDrawable((back_bitmap));
            back_btn_test_rules.setBackgroundDrawable(dr1);
        }catch (Exception e){
            e.printStackTrace();
        }

    }
    public static Bitmap getBitmapFromURL(String src) {
        try {
            java.net.URL url = new URL(src);
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
