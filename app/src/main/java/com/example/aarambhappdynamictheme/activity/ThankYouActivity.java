package com.example.aarambhappdynamictheme.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
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
import com.example.aarambhappdynamictheme.model.PraticeSubjectDataModel;
import com.example.aarambhappdynamictheme.model.TestSubjectDataModel;
import com.example.aarambhappdynamictheme.model.VideoDetails;
import com.example.aarambhappdynamictheme.model.YouTubePlayList;
import com.example.aarambhappdynamictheme.model.YouTubeTitle;
import com.example.aarambhappdynamictheme.sheardPrefrence.AarambhSharedPreference;
import com.example.aarambhappdynamictheme.sheardPrefrence.AarambhThemeSharedPrefreence;
import com.example.aarambhappdynamictheme.util.CommonUtilities;
import com.example.aarambhappdynamictheme.util.Global;
import com.example.aarambhappdynamictheme.util.VolleySingleton;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LegendEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.DefaultValueFormatter;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.utils.ViewPortHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;

public class ThankYouActivity extends AppCompatActivity {
    Button close_test;
    String pratice_text;
    TextView practice_txt;
    ArrayList<String> you_Topic;
    TextView total_number_question, total_attempt;
    String total_question;
    int total_attempt_question=0;
    ProgressBar correct_ans, wrong_ans, not_attempt;
    PieChart pieChart;
    PieData pieData;
    PieDataSet pieDataSet;
    ArrayList pieEntries;
    ArrayList PieEntryLabels;
    int[] MY_COLORS;
    ArrayList<Integer> colors;
    //AchartEngine
    private String[] code;
    PraticeSubjectDataModel praticeSubjectDataModel;
    ArrayList<PraticeSubjectDataModel>praticeSubjectDataModelArrayList;
    ArrayList<TestSubjectDataModel>testSubjectDataModelArrayList;
    int correct_count=0,wrong_count=0;
    LinearLayout transparent_thnaku,thanku_background;
   // RelativeLayout thanku_background;
   // RelativeLayout thanku_label;
    String base_image,transparent_color,base_color_one,base_color_two;
    Bitmap Background;
    String testMasterId,praticesMasterId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thank_you);
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        checkOrientation();

        base_image= AarambhThemeSharedPrefreence.loadBaseImageFromPreference(this);
        transparent_color=AarambhThemeSharedPrefreence.loadBaseImageTransparentFromPreference(this);
        Log.e("transprent",transparent_color);

        base_color_one=AarambhThemeSharedPrefreence.loadBaseColorOneFromPreference(this);
        base_color_two=AarambhThemeSharedPrefreence.loadBaseColorTwoFromPreference(this);

        try {
            testSubjectDataModelArrayList= (ArrayList<TestSubjectDataModel>) getIntent().getSerializableExtra("testmodel");
            Log.e("valuePratice_model", String.valueOf(testSubjectDataModelArrayList.size()));

            for (int i=0;i<testSubjectDataModelArrayList.size();i++){
                Log.e("correct_thanku", String.valueOf(testSubjectDataModelArrayList.get(i).getCorrect()));
                Log.e("wrong_thanku", String.valueOf(testSubjectDataModelArrayList.get(i).getWorng()));
                if (testSubjectDataModelArrayList.get(i).getCorrect()==1){
                    correct_count++;
                }
                if (testSubjectDataModelArrayList.get(i).getWorng()==1){
                    wrong_count++;
                }
                testMasterId=testSubjectDataModelArrayList.get(i).getTestMasterId();
            }

            getTestDataCount(correct_count,wrong_count,testSubjectDataModelArrayList.size());

        }catch (Exception e){
            e.printStackTrace();
        }

        try {
            praticeSubjectDataModelArrayList= (ArrayList<PraticeSubjectDataModel>) getIntent().getSerializableExtra("praticesModel");
            Log.e("valuePratice_model", String.valueOf(praticeSubjectDataModelArrayList.size()));

            for (int i=0;i<praticeSubjectDataModelArrayList.size();i++){
                Log.e("correct_thanku", String.valueOf(praticeSubjectDataModelArrayList.get(i).getCorrect()));
                Log.e("wrong_thanku", String.valueOf(praticeSubjectDataModelArrayList.get(i).getWrong()));
                if (praticeSubjectDataModelArrayList.get(i).getCorrect()==1){
                    correct_count++;
                }
                if (praticeSubjectDataModelArrayList.get(i).getWrong()==1){
                    wrong_count++;
                }
                praticesMasterId=praticeSubjectDataModelArrayList.get(i).getPracticeMasterId();

            }
            getPraticesDataCount(correct_count,wrong_count,praticeSubjectDataModelArrayList.size());

        }catch (Exception e){
            e.printStackTrace();
        }

        try {
            total_attempt_question = correct_count+wrong_count;

            //total_attempt_question = Global.correct_answer_test + Global.wrong_answer_test;
            Log.e("thaku_total_question", total_attempt_question + "");
            init();
            listner();
            displayData();
            pieDataSet = new PieDataSet(pieEntries, "");
            pieData = new PieData(pieDataSet);
            pieData.setValueFormatter(new DefaultValueFormatter(0));
            pieData.setValueFormatter(new PercentFormatter(pieChart));
            //  pieDataSet.setValueFormatter(new MyValueFormatter());
            pieChart.setUsePercentValues(true);
            pieChart.setDrawEntryLabels(false);
            pieChart.setRotationEnabled(false);
            pieChart.setDrawEntryLabels(true);
            pieChart.isDrawEntryLabelsEnabled();
            //Chart.setHoleRadius(7);
//            dataSet.setValueLinePart1Length(0.43f);
//            dataSet.setValueLinePart2Length(.1f);
            pieDataSet.setValueTextColor(Color.BLACK);
            pieDataSet.setXValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);

            pieDataSet.setValueLinePart1OffsetPercentage(70.f);
            pieDataSet.setValueLinePart1Length(.70f);
            pieDataSet.setValueLinePart2Length(.20f);

            pieChart.setEntryLabelColor(Color.BLACK);
            Legend legend = pieChart.getLegend();
            legend.setWordWrapEnabled(true);
//            pieChart.setDrawHoleEnabled(false);
//            pieChart.setHoleRadius(10);

            pieChart.setDrawHoleEnabled(true);
            // pieChart.setHoleColorTransparent(true);
            pieChart.setHoleRadius(30);
            pieChart.setTransparentCircleRadius(10);

            // enable rotation of the chart by touch
            pieChart.setRotationAngle(0);
            pieChart.setRotationEnabled(true);

            legend.getCalculatedLineSizes();
            List<LegendEntry> legends = new ArrayList<>();
            LegendEntry green = new LegendEntry();
            green.label = "Correct Answer";
            green.formColor = Color.rgb(0, 102, 0);
            legends.add(green);
            LegendEntry red = new LegendEntry();
            red.label = "InCorrect Answer";
            red.formColor = Color.rgb(255, 0, 0);
            legends.add(red);
            LegendEntry blue = new LegendEntry();
            blue.label = "Not Attempted";
            blue.formColor = Color.rgb(0, 128, 255);
            legends.add(blue);
            pieChart.getLegend().setCustom(legends);
            legend.setForm(Legend.LegendForm.CIRCLE);
            legend.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
            legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
            pieChart.setData(pieData);
            // Log.e("color", String.valueOf(colors));
            pieDataSet.setColors(colors);
            Description d=new Description();
            d.setText("");
            pieChart.setDescription(d);
            pieChart.getCenterText();
            // pieChart.sette("Student Progress");
            // pieDataSet.setValueFormatter(new PercentFormatter());
            pieDataSet.setSliceSpace(2f);
            pieDataSet.setValueTextColor(Color.BLACK);
            pieDataSet.setValueTextSize(10f);
            pieDataSet.setSliceSpace(5);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getPraticesDataCount(int correct_count, int wrong_count, int size) {
        if (!CommonUtilities.isOnline(this)) {
            Toast.makeText(ThankYouActivity.this, "Please On Your Internet Connection", Toast.LENGTH_LONG).show();
            return;
        }
        String url = Global.WEBBASE_URL + "insertStudentPracticeDetail";
//        http://35.200.220.64:1500/aarambhTesting/insertStudentPracticeDetail
        final String string_json = "Result";
//        progressDialog = new ProgressDialog(this);
//        progressDialog.setCancelable(false);
//        progressDialog.setMessage("Loading...");
//        progressDialog.setTitle("Wait...");
//        progressDialog.show();
        JSONObject params = new JSONObject();
        String mRequestBody = "";
        try {

            params.put("PracticeMasterId", praticesMasterId);
            Log.e("PracticeMasterId", praticesMasterId);

            params.put("StudentId", AarambhSharedPreference.loadStudentIdFromPreference(this));
            Log.e("StudentId", AarambhSharedPreference.loadStudentIdFromPreference(this));
            Date c = Calendar.getInstance().getTime();
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            String formattedDate = df.format(c);

            params.put("Date", formattedDate);
            Log.e("Date", formattedDate);
            params.put("Score", correct_count);
            params.put("Total", total_number_question);
            //  Log.e("ParentEmail", null);
            params.put("NoofQuestionAttempted", total_attempt_question);
            params.put("WrongQuestion", wrong_count);
            params.put("RightQuestion", correct_count);
            Log.e("RightQuestion", String.valueOf(this.correct_count));

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
                parseResponsePraticesApi(res);
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
                        parseResponsePraticesApi(res.toString());

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
                headers.put("Authorization", "Bearer "+ AarambhSharedPreference.loadUserTokenFromPreference(ThankYouActivity.this));
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
        VolleySingleton.getInstance(ThankYouActivity.this).addToRequestQueue(jsonObjectRequest, string_json);

    }

    private void parseResponsePraticesApi(String response) {
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
            }else if (success==true){
                String message = obj.getString("Message");
                Log.e("massage", message);
                if (message.equalsIgnoreCase("Practice Details saved successfully.")) {
                    Toast.makeText(this, ""+message, Toast.LENGTH_LONG).show();
//                    Intent intent2 = new Intent(ParentRegisterActivity.this, LoginActivity.class);
//                    startActivity(intent2);
                }else{
                    Toast.makeText(this, message+"", Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(this, "Registration Failed.Pls Try Again", Toast.LENGTH_LONG).show();
                //progressDialog.dismiss();
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void getTestDataCount(int correct_count, int wrong_count, int size) {
        if (!CommonUtilities.isOnline(this)) {
            Toast.makeText(ThankYouActivity.this, "Please On Your Internet Connection", Toast.LENGTH_LONG).show();
            return;
        }
        String url = Global.WEBBASE_URL + "insertStudentTestDetail";
        final String string_json = "Result";
//        progressDialog = new ProgressDialog(this);
//        progressDialog.setCancelable(false);
//        progressDialog.setMessage("Loading...");
//        progressDialog.setTitle("Wait...");
//        progressDialog.show();
        JSONObject params = new JSONObject();
        String mRequestBody = "";
        try {

            params.put("TestMasterId", testMasterId);
            Log.e("TestMasterId", testMasterId);

            params.put("StudentId", AarambhSharedPreference.loadStudentIdFromPreference(this));
            Log.e("StudentId", AarambhSharedPreference.loadStudentIdFromPreference(this));
            Date c = Calendar.getInstance().getTime();
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            String formattedDate = df.format(c);

            params.put("Date", formattedDate);
            Log.e("Date", formattedDate);
            params.put("Score", correct_count);
            params.put("Total", total_number_question);
            //  Log.e("ParentEmail", null);
            params.put("NoofQuestionAttempted", total_attempt_question);
            params.put("WrongQuestion", wrong_count);
            params.put("RightQuestion", correct_count);
            Log.e("RightQuestion", String.valueOf(correct_count));

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
                parseResponseTestApi(res);
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
                        parseResponseTestApi(res.toString());

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
                headers.put("Authorization", "Bearer "+ AarambhSharedPreference.loadUserTokenFromPreference(ThankYouActivity.this));
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
        VolleySingleton.getInstance(ThankYouActivity.this).addToRequestQueue(jsonObjectRequest, string_json);
    }

    private void parseResponseTestApi(String response) {
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
            }else if (success==true){
                String message = obj.getString("Message");
                Log.e("massage", message);
                if (message.equalsIgnoreCase("Practice Details saved successfully.")) {
                    Toast.makeText(this, ""+message, Toast.LENGTH_LONG).show();
//                    Intent intent2 = new Intent(ParentRegisterActivity.this, LoginActivity.class);
//                    startActivity(intent2);
                }else{
                    Toast.makeText(this, message+"", Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(this, "Registration Failed.Pls Try Again", Toast.LENGTH_LONG).show();
                //progressDialog.dismiss();
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void displayData(){
        try {
            pieEntries = new ArrayList<>();

            //  pieEntries.add(new PieEntry((Global.correct_answer_test), "Correct Answer"));
//            pieEntries.add(new PieEntry(Global.wrong_answer_test, "InCorrect Answer"));
            float not_attemp = Global.total_question_number - total_attempt_question;

//            pieEntries.add(new PieEntry(not_attemp, "Not Attempted"));
            int valu = Global.total_question_number;
//            int not_attemp1 = Global.total_question_number - total_attempt_question;
//            int final_cal = not_attemp1*total_attempt_question ;
//            Log.e("final_value", String.valueOf(final_cal));
//            pieEntries.add(new PieEntry(not_attemp, "Not Attempted"));

//            int corr=Global.correct_answer_test;
            int corr=correct_count;

            int Correct_value = corr * 100 / valu;
            Log.e("Correct_value", String.valueOf(Correct_value));

//            int wrong=Global.wrong_answer_test;
            int wrong=wrong_count;

            int Wrong_value = wrong * 100 / valu;
            Log.e("Wrong_value", String.valueOf(Wrong_value));
//            pieEntries.add(new PieEntry(Global.wrong_answer_test, "InCorrect Answer"));

            int not_attemp1 = Global.total_question_number - total_attempt_question;
            int final_cal = not_attemp1*total_attempt_question ;
            Log.e("final_value", String.valueOf(final_cal));
//            pieEntries.add(new PieEntry(not_attemp, "Not Attempted"));


            if (Correct_value==0 && Wrong_value==0 && final_cal==0){
                try {
                    MY_COLORS = new int[]{Color.rgb(0, 128, 255)};
                    colors = new ArrayList<Integer>();
                    for (int c : MY_COLORS) colors.add(c);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            else if (Correct_value==0 && Wrong_value!=0 && final_cal==0){
                try {
                    MY_COLORS = new int[]{Color.rgb(255, 0, 0)};
                    colors = new ArrayList<Integer>();
                    for (int c : MY_COLORS) colors.add(c);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            else if (Correct_value!=0 && Wrong_value==0 && final_cal==0){
                try {
                    MY_COLORS = new int[]{Color.rgb(0, 102, 0), Color.rgb(255, 0, 0)};
                    colors = new ArrayList<Integer>();
                    for (int c : MY_COLORS) colors.add(c);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            else if (Correct_value==0 && Wrong_value!=0 && final_cal!=0){
                try {
                    MY_COLORS = new int[]{Color.rgb(255, 0, 0), Color.rgb(0, 128, 255)};
                    colors = new ArrayList<Integer>();
                    for (int c : MY_COLORS) colors.add(c);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            else if (Correct_value!=0 && Wrong_value!=0 && final_cal==0){
                try {
                    MY_COLORS = new int[]{Color.rgb(0, 102, 0), Color.rgb(255, 0, 0)};
                    colors = new ArrayList<Integer>();
                    for (int c : MY_COLORS) colors.add(c);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            else if (Correct_value!=0 && Wrong_value!=0 && final_cal!=0){
                try {
                    MY_COLORS = new int[]{Color.rgb(0, 102, 0), Color.rgb(255, 0, 0), Color.rgb(0, 128, 255)};
                    colors = new ArrayList<Integer>();
                    for (int c : MY_COLORS) colors.add(c);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }else if (Correct_value!=0 && Wrong_value==0&& final_cal!=0){
                try {
                    MY_COLORS = new int[]{Color.rgb(0, 102, 0),Color.rgb(0, 128, 255)};
                    colors = new ArrayList<Integer>();
                    for (int c : MY_COLORS) colors.add(c);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }





            if (correct_count == 0) {
            } else {
                if (correct_count == 0.0) {
                } else {
                    try {
//                        MY_COLORS = new int[]{Color.GREEN};
//                        colors = new ArrayList<Integer>();
//                        for (int c : MY_COLORS) colors.add(c);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    pieEntries.add(new PieEntry((correct_count), "Correct Answer"));
                }
                // }
            }
            if (wrong_count == 0) {
            } else {
                if (wrong_count == 0.0) {
                } else {
                    try {
//                        MY_COLORS = new int[]{Color.RED};
//                        colors = new ArrayList<Integer>();
//                        for (int c : MY_COLORS) colors.add(c);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    pieEntries.add(new PieEntry(wrong_count, "InCorrect Answer"));
                }
            }
            float not_attemp2 = Global.total_question_number - total_attempt_question;
            if (not_attemp2 == 0) {
            } else {
                if (not_attemp2 == 0.0) {
                } else {
                    try {
//                        MY_COLORS = new int[]{Color.BLUE};
//                        colors = new ArrayList<Integer>();
//                        for (int c : MY_COLORS) colors.add(c);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    pieEntries.add(new PieEntry(not_attemp, "Not Attempted"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }



    private void listner() {
        close_test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  getChannelPlayListVideos();
                Intent intent = new Intent(ThankYouActivity.this, DashBoardActivity.class);
                startActivity(intent);
                // Intent in=new Intent(RedThemeThankuActivity.this, DashBoardSixthStandardActivity.class);
                Global.total_question_number = 0;
//                Global.correct_answer_test = 0;
//                Global.wrong_answer_test = 0;
//                startActivity(in);
            }
        });
    }

    @Override
    public void onBackPressed() {
        //finish();
        Intent intent = new Intent(ThankYouActivity.this, DashBoardActivity.class);
        startActivity(intent);
       // getChannelPlayListVideos();
        // Intent in=new Intent(RedThemeThankuActivity.this, DashBoardSixthStandardActivity.class);
        Global.total_question_number = 0;
//        Global.correct_answer_test = 0;
//        Global.wrong_answer_test = 0;
//
    }

    private void checkOrientation() {
        if (getResources().getBoolean(R.bool.portrait_only)) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
    }

    private void init() {
        StrictMode.ThreadPolicy policy = new
                StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        total_number_question = findViewById(R.id.total_number_question);
        total_number_question.setText(String.valueOf(Global.total_question_number));
        you_Topic = new ArrayList<>();
        close_test = findViewById(R.id.close);
        practice_txt = (TextView) findViewById(R.id.practices_text);
        total_attempt = findViewById(R.id.total_attempt);
        total_attempt.setText(String.valueOf(total_attempt_question));
        //  practice_txt.setText(pratice_text);naina phele woh code dikhayao jisse ko 100% run hota hai call kru?ok
        int total_per = total_attempt_question / 100 * Global.total_question_number;
        Log.e("percentage", String.valueOf(total_per));
//        correct_ans = findViewById(R.id.correct_answer);
//        wrong_ans = findViewById(R.id.wrong_answer);
//        not_attempt=findViewById(R.id.not_attempted);
        pieChart = findViewById(R.id.pieChart);
        thanku_background=findViewById(R.id.thanku_background);
        transparent_thnaku=findViewById(R.id.transparent_thnaku);
      //  transparent_thnaku.setBackgroundColor(Color.parseColor(transparent_color));
       // thanku_label=findViewById(R.id.thanku_label);
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
        GradientDrawable gd1 = new GradientDrawable(
                GradientDrawable.Orientation.TOP_BOTTOM, colors);


        gd1.setCornerRadius(0f);


        //apply the button background to newly created drawable gradient
        close_test.setBackground(gd);
        //thanku_label.setBackground(gd1);
        // cancle_btn.setBackground(gd1);
    }
    private void themeDynamicAdd() {

        //Bitmap myImage = getBitmapFromURL(image);
//        try {
//            Background=getBitmapFromURL(base_image);
//            Drawable dr = new BitmapDrawable((Background));
//            thanku_background.setBackgroundDrawable(dr);
//
//        }catch (Exception e){
//            e.printStackTrace();
//        }
        Bitmap thanku_bitmap;
        String thanku_shrd=AarambhThemeSharedPrefreence.loadThankuBackgroundFromPreference(this);
        try {
            thanku_bitmap=getBitmapFromURL(thanku_shrd);
            Drawable dr = new BitmapDrawable((thanku_bitmap));
            thanku_background.setBackgroundDrawable(dr);

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
    @Override
    public void finish() {
        Intent in = new Intent(ThankYouActivity.this, DashBoardActivity.class);
        in.putExtra("DataSubject", you_Topic);
        //  in.putExtra("LandDetail", landDetailModel);
        in.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        Global.total_question_number = 0;
//        Global.correct_answer_test = 0;
//        Global.wrong_answer_test = 0;
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

                        String class_id = AarambhSharedPreference.loadClassIdFromPreference(ThankYouActivity.this);
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
                    Log.e("classId", AarambhSharedPreference.loadClassIdFromPreference(ThankYouActivity.this));

                    switch (AarambhSharedPreference.loadClassIdFromPreference(ThankYouActivity.this)) {
                        case "1":
                            //btn_login.setClickable(false);
                            Intent intent = new Intent(ThankYouActivity.this, DashBoardActivity.class);
                            //Log.e("6thDetails", String.valueOf(ytt6th.getSubject().size()));
                            intent.putExtra("6thDetails", ytt6th);
                            intent.putExtra("PlayListDetails", playListArrayList);
                            startActivity(intent);
                            //progressdialog.dismiss();
                            break;
                        case "2":
                            //btn_login.setClickable(false);
                            Intent intent1 = new Intent(ThankYouActivity.this, DashBoardActivity.class);
                            intent1.putExtra("7thDetails", ytt7th);
                            intent1.putExtra("PlayListDetails", playListArrayList);
                            startActivity(intent1);
                            //progressdialog.dismiss();
                            break;
                        case "3":
                            //btn_login.setClickable(false);
                            Intent intent2 = new Intent(ThankYouActivity.this, DashBoardActivity.class);
                            intent2.putExtra("8thDetails", ytt8th);
                            intent2.putExtra("PlayListDetails", playListArrayList);
                            startActivity(intent2);
                            //progressdialog.dismiss();
                            break;
                        default:
                            //btn_login.setClickable(false);
                            Intent intent3 = new Intent(ThankYouActivity.this, DashBoardActivity.class);
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

    //    public class MyValueFormatter implements IValueFormatter {
//
//        private DecimalFormat mFormat;
//
//        public MyValueFormatter() {
//            mFormat = new DecimalFormat("###,###,##0.0"); // use one decimal
//        }
//
//        @Override
//        public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
//            // write your logic here
//            return mFormat.format(value) + " %"; // e.g. append a dollar-sign
//        }
//    }
    public class MyValueFormatter extends ValueFormatter {
        @Override
        public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
            return Math.round(value) + "" + ((int) value);
        }
    }
}
