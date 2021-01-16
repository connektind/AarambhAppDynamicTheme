package com.example.aarambhappdynamictheme.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.CountDownTimer;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.content.ContextCompat;

import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
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
import com.example.aarambhappdynamictheme.model.TestSubjectDataModel;
import com.example.aarambhappdynamictheme.sheardPrefrence.AarambhSharedPreference;
import com.example.aarambhappdynamictheme.sheardPrefrence.AarambhTestHelper;
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

public class TestAcivity extends AppCompatActivity {
    Button next_test_btn;
    TextView question_test, test_time, question_count;
    AarambhTestHelper aarambhTestHelper;
    RadioGroup radioGroup, radioGroup_true_falseType;
    //    TestQuestionHandleModel testQuestionHandleModel;
//    List<TestQuestionHandleModel> testQuestionHandleModelArrayList;
    int qid = 1;
    int timeValue ;
    int coinValue = 0;
    CountDownTimer countDownTimer;
    Typeface tb, sb;
    ImageView cancle_btn;
    ArrayList<TestSubjectDataModel> testSubjectDataModelArrayList;
    TestSubjectDataModel testSubjectDataModel;
    TextView test_no_total_question,test_slash;
    ArrayList<String> you_Topic;
    String chapterId,courseId;
    LinearLayout test_background,transprent_test;
    String base_image,transparent_color,base_color_one,base_color_two;
    Bitmap Background;
    TextView test_text;
    String testMasterId,testDurstion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_acivity);
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        checkOrientation();
        try{
            testMasterId=getIntent().getStringExtra("testMasterId");
            testDurstion=getIntent().getStringExtra("testDurstion");
            timeValue= Integer.parseInt(testDurstion);
            timeValue++;
        }catch (Exception e){
            e.printStackTrace();
        }
        base_image= AarambhThemeSharedPrefreence.loadBaseImageFromPreference(this);
        transparent_color=AarambhThemeSharedPrefreence.loadBaseImageTransparentFromPreference(this);
        Log.e("transprent",transparent_color);

        base_color_one=AarambhThemeSharedPrefreence.loadBaseColorOneFromPreference(this);
        base_color_two=AarambhThemeSharedPrefreence.loadBaseColorTwoFromPreference(this);
        init();
        listner();
        getTestData();
    }

    private void getTestData() {
        {
            if (!CommonUtilities.isOnline(this)) {
                Toast.makeText(TestAcivity.this, "Please On Your Internet Connection", Toast.LENGTH_LONG).show();
                return;
            }
            final String class_id = AarambhSharedPreference.loadClassIdFromPreference(this);
            final String course_id = AarambhSharedPreference.loadCourseIdFromPreference(this);
            final String chapter_id = AarambhSharedPreference.loadTopicIdFromPreference(this);
            String url = Global.WEBBASE_URL +"getTestQuestionAccToTestId?testId="+testMasterId ;
            //http://35.200.220.64:1500/aarambhTesting/getTestQuestionAccToTestId?testId=1
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
//                    params.put("CourseId", courseID);
//                    params.put("ChapterId", chapterID);
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
                    headers.put("Authorization", "Bearer " + AarambhSharedPreference.loadUserTokenFromPreference(TestAcivity.this));
                    return headers;
                }
            };
            VolleySingleton.getInstance(TestAcivity.this).addToRequestQueue(stringRequest, string_json);

        }
    }

    private void parseResponseTestApi(String response) {
        Log.e("chapter_res", response);
        try {
            JSONObject jsonObject1 = new JSONObject(response);
            String status=jsonObject1.getString("status");

            boolean success = jsonObject1.getBoolean("success");

            if(success==false){
                String error=jsonObject1.getString("error");
                Toast.makeText(this,error+"",Toast.LENGTH_LONG).show();
            }else if (success==true) {
                String Message = jsonObject1.getString("message");
                if (Message.equalsIgnoreCase("Data Found.")) {

                    //  if(status.equalsIgnoreCase("200")&&success==true&&Message.equalsIgnoreCase("Data Found")) {
                    JSONArray jsonArray = jsonObject1.getJSONArray("data");
                    Log.e("Message", String.valueOf(jsonArray));
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        Log.e("chapter_length", String.valueOf(i));
                        Log.e("json", String.valueOf(jsonObject));
                        String TestDetailId = jsonObject.getString("TestDetailId");
                        String TestMasterId = jsonObject.getString("TestMasterId");
//                        String CourseId = jsonObject.getString("CourseId");
//                        String TopicId = jsonObject.getString("TopicId");
//                        String ChapterId = jsonObject.getString("ChapterId");
                        String TestQuestionType = jsonObject.getString("TestQuestionType");
                        String TestQuestions = jsonObject.getString("TestQuestion");
                        String AnswerOption1 = jsonObject.getString("AnswerOption1");
                        String AnswerOption2 = jsonObject.getString("AnswerOption2");
                        String AnswerOption3 = jsonObject.getString("AnswerOption3");
                        String AnswerOption4 = jsonObject.getString("AnswerOption4");
                        String AnswerOption5 = jsonObject.getString("AnswerOption5");
                        String AnswerOption6 = jsonObject.getString("AnswerOption6");
                        String CorrectAnswer = jsonObject.getString("CorrectAnswer");
                        String StatusId = jsonObject.getString("StatusId");
                        String CreatedById = jsonObject.getString("CreatedById");
                        String ModifiedById = jsonObject.getString("ModifiedById");
                        String CreationDate = jsonObject.getString("CreationDate");
                        String ModificationDate = jsonObject.getString("ModificationDate");
                        testSubjectDataModel = new TestSubjectDataModel(TestDetailId, TestMasterId, TestQuestionType, TestQuestions, AnswerOption1, AnswerOption2, AnswerOption3, AnswerOption4, AnswerOption5, AnswerOption6, CorrectAnswer, StatusId, CreatedById, ModifiedById, CreationDate, ModificationDate, 0, 0, 0, "");
                        testSubjectDataModelArrayList.add(testSubjectDataModel);
                    }
                    next_test_btn.setVisibility(View.VISIBLE);
//            countDownTimer = new CountDownTimer(30000, 1000) {
//                public void onTick(long millisUntilFinished) {
//                    test_time.setText((timeValue) + "");
//                    timeValue -= 1;
//                    Random random = new Random();
//                    int ran_num = random.nextInt();
//                    if (timeValue == 0) {
//                        int p = qid - 1;
//                        testSubjectDataModel = testSubjectDataModelArrayList.get(p);
//                        switch (testSubjectDataModel.getTestQuestionType()) {
//                            case "Fill in the blank.":
//                                typeThreeQuestion();
//                                break;
//                            case "Multiple choice questions.":
//                                typeThreeQuestion();
//                                break;
//                            case "True or False:":
//                                typeThreeQuestion();
//                                break;
//                        }
//                        // disableButton();
//                        //Log.e("Question Type", testSubjectDataModel.getTestQuestionType());
////                        if (testSubjectDataModel.getTestQuestionType().equals("True False")) {
////                            //singal choice question
////                            typeOneQuestion();
////                        } else if (testSubjectDataModel.getTestQuestionType().equals("")) {
////                            //true and false type question
////                            typeTwoQuestion();
////                        } else if (testSubjectDataModel.getTestQuestionType().equals("Fill in the blank.")) {
////                            //multi choice option
////                            typeThreeQuestion();
////                        } else if (testSubjectDataModel.getTestQuestionType().equals("4")) {
////                            //fill in the blanks question
////                            typeOneQuestion();
////                        } else {
////                            Toast.makeText(RedThemeTestActivity.this, "Don't Have any Questions", Toast.LENGTH_LONG).show();
////                        }
//                    }
//                }
//
//                public void onFinish() {
//                    countDownTimer.cancel();
//                }
//            }.start();
                    Log.e("lenghth", String.valueOf(jsonArray.length()));
                    test_no_total_question.setText(String.valueOf(jsonArray.length()));
                    Global.total_question_number = (jsonArray.length());
                    int p = qid - 1;
                    testSubjectDataModel = testSubjectDataModelArrayList.get(p);
                    switch (testSubjectDataModel.getTestQuestionType()) {
                        case "Fill in the blank.":
                            typeThreeQuestion();
                            break;
                        case "Multiple Choice Question":
                            typeThreeQuestion();
                            break;
                        case "True or False:":
                            typeThreeQuestion();
                            break;
                        case "Give One Word For.":
                            typeThreeQuestion();
                            break;
                    }
                }
                else {
                    Toast.makeText(this,Message+"",Toast.LENGTH_LONG).show();
                }
            }
            // Log.e("Question Type", testSubjectDataModel.getTestQuestionType());
        } catch (Exception e) {
            e.printStackTrace();
        }
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

        you_Topic = new ArrayList<>();
        testSubjectDataModelArrayList = new ArrayList<>();
        cancle_btn = findViewById(R.id.cancle_btn);
        radioGroup = findViewById(R.id.radiogroup_btn);
        test_no_total_question = (TextView) findViewById(R.id.test_no_total);
        test_no_total_question.setTextColor(Color.parseColor(base_color_one));
        test_text=findViewById(R.id.test_text);
        TextColorGradient textColorGradient=new TextColorGradient();
        textColorGradient.getColorTextGradient(test_text,base_color_one,base_color_two);
       // radioGroup_true_falseType = findViewById(R.id.true_false_type);
        // testQuestionHandleModelArrayList = new ArrayList<>();
        question_count = findViewById(R.id.test_question_count);
        next_test_btn = findViewById(R.id.next_test);
        next_test_btn.setVisibility(View.GONE);
        getDynamicData();
        test_time = (TextView) findViewById(R.id.test_time);
        test_time.setTextColor(Color.parseColor(base_color_one));
        question_test = (TextView) findViewById(R.id.test_question_red);
        question_count.setTextColor(Color.parseColor(base_color_one));
        try{
            String chapname = getIntent().getStringExtra("ChapterNameTest");
            if(chapname.contains("Fun with Magnets")){
                courseId = "3";
                chapterId = "4";
               // getTestData("3","4");
            }
            if(chapname.contains("Components of Food")){
                courseId = "3";
                chapterId = "7";
                //getTestData("3","7");
            }
            if(chapname.contains("Knowing Our Numbers")){
                courseId = "6";
                chapterId = "5";
                //getTestData("6","5");
            }
            if(chapname.contains("Integers")){
                courseId = "6";
                chapterId = "6";
                //getTestData("6","6");
            }
        }catch (Exception e){e.printStackTrace();}

        try{
            String chapname = getIntent().getStringExtra("7thChapterNameTest");
            if(chapname.contains("Nutrition in animals")){
                courseId = "";
                chapterId = "";
                //getTestData("","");
            }
            if(chapname.contains("Acids, Bases & Salts")){
                courseId = "7";
                chapterId = "11";
                //getTestData("7","11");
            }
            if(chapname.contains("Fractions & Decimals")){
                courseId = "45";
                chapterId = "12";
                //getTestData("45","12");
            }
//            if(chapname.contains("Integers")){
//                courseId = "6";
//                chapterId = "6";
//                getTestData("6","6");
//            }
        }catch (Exception e){e.printStackTrace();}


            try{
                String chapname = getIntent().getStringExtra("8thChapterNameTest");
                if(chapname.contains("Linear Equations")){
                    courseId = "47";
                    chapterId = "8";
                  //  getTestData("47","8");
                }
                if(chapname.contains("Cube & Cube Roots")){
                    courseId = "47";
                    chapterId = "9";
                    //getTestData("47","9");
                }
                if(chapname.contains("Crop Production & Management")){
                    courseId = "56";
                    chapterId = "10";
                    //getTestData("56","10");
                }

            }catch (Exception e){e.printStackTrace();}
        question_count.setText(qid + "");
            int value=timeValue*1000+1;
        countDownTimer = new CountDownTimer(value, 1000) {

            public void onTick(long millisUntilFinished) {
                Log.e("time=",Long.toString((millisUntilFinished/1000)-1));
                test_time.setText(Long.toString((millisUntilFinished/1000)-1));

                timeValue= (int) (millisUntilFinished / 1000);
                //timeValue -= 1;
                //test_time.setText((timeValue) + "");
                Log.e("timeValue", String.valueOf(timeValue));
                if (timeValue <= 1) {
                   // timeValue=31;
                    qid++;
                    Log.e("condition1", String.valueOf(qid>testSubjectDataModelArrayList.size()));
                    if (qid > testSubjectDataModelArrayList.size()) {
                        countDownTimer.cancel();
                        Intent intent = new Intent(TestAcivity.this,ThankYouActivity.class);
                        intent.putExtra("testmodel",testSubjectDataModelArrayList);
                        startActivity(intent);
                    } else {
                        //get the que and 4 option and store in the currentQuestion
                        int pos = qid - 1;
                        testSubjectDataModel = testSubjectDataModelArrayList.get(pos);
                        String question_number = String.valueOf(qid);
                        question_count.setText(question_number);
                        switch (testSubjectDataModel.getTestQuestionType()) {
                            case "Fill in the blank.":
                                typeThreeQuestion();
                                break;
                            case "Multiple Choice Question":
                                typeThreeQuestion();
                                break;
                            case "True or False:":
                                typeThreeQuestion();
                                break;
                            case "Give One Word For.":
                                typeThreeQuestion();
                                break;
                        }
                    }
                }
            }
            public void onFinish() {
                //   countDownTimer.cancel();
                //countDownTimer.start();
            }
        };
        test_background=findViewById(R.id.test_background);
        transprent_test=findViewById(R.id.transprent_test);
        transprent_test.setBackgroundColor(Color.parseColor(transparent_color));
        test_slash=findViewById(R.id.test_slash);
        test_slash.setTextColor(Color.parseColor(base_color_one));
       // cancle_btn.setBackgroundColor(Color.parseColor(base_color_one));
        themeDynamicAdd();
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
        next_test_btn.setBackground(gd);
       // cancle_btn.setBackground(gd1);
    }

    private void themeDynamicAdd() {

        //Bitmap myImage = getBitmapFromURL(image);
        try {
            Background=getBitmapFromURL(base_image);
            Drawable dr = new BitmapDrawable((Background));
            test_background.setBackgroundDrawable(dr);

        }catch (Exception e){
            e.printStackTrace();
        }
        Bitmap cancle_bitmap;
        String back_shrd=AarambhThemeSharedPrefreence.loadCancleIconFromPreference(this);
        try {
            cancle_bitmap=getBitmapFromURL(back_shrd);
            Drawable dr = new BitmapDrawable((cancle_bitmap));
            cancle_btn.setBackgroundDrawable(dr);

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

    private void typeThreeQuestion() {
        RadioGroup item = (RadioGroup) findViewById(R.id.radiogroup_btn);
        item.removeAllViews();
        item.refreshDrawableState();
        View child = getLayoutInflater().inflate(R.layout.practice_fillblank_typeq, null);
        item.addView(child);
        child.refreshDrawableState();
        child.requestLayout();
        Log.e("type_three", String.valueOf(timeValue));

        countDownTimer.cancel();

        countDownTimer.start();
        //countDownTimer.onFinish();
        //  countDownTimer.start();
        multiChoiceQuestion();
    }

    private void multiChoiceQuestion() {
        final CheckBox option_one, option_two, option_three, option_four, option_five, option_six;
        option_one = findViewById(R.id.check_one);
        option_two = findViewById(R.id.check_two);
        option_three = findViewById(R.id.check_three);
        option_four = findViewById(R.id.check_four);
        option_five = findViewById(R.id.check_five);
        option_six = findViewById(R.id.check_six);
        String ans = testSubjectDataModel.getAnswerOption1();
        String ans2 = testSubjectDataModel.getAnswerOption2();
        String ans3 = testSubjectDataModel.getAnswerOption3();
        String ans4 = testSubjectDataModel.getAnswerOption4();
        String ans5 = testSubjectDataModel.getAnswerOption5();
        String ans6 = testSubjectDataModel.getAnswerOption6();
        String answer = testSubjectDataModel.getCorrectAnswer();
//        String answer1 = null, answer2 = null, answer3, answer4, answer5, answer6;
//        String title_arr[] = answer.split(",");
        try {
            question_test.setText(testSubjectDataModel.getTestQuestions());
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (ans3.equals("") && ans4.equals("") && ans5.equals("") && ans6.equals("")) {
            option_three.setVisibility(View.INVISIBLE);
            option_four.setVisibility(View.INVISIBLE);
            option_five.setVisibility(View.INVISIBLE);
            option_six.setVisibility(View.INVISIBLE);
            try {
                option_one.setText(testSubjectDataModel.getAnswerOption1());
                option_two.setText(testSubjectDataModel.getAnswerOption2());
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (ans4.equals("") && ans5.equals("") && ans6.equals("")) {
            option_four.setVisibility(View.INVISIBLE);
            option_five.setVisibility(View.INVISIBLE);
            option_six.setVisibility(View.INVISIBLE);
            try {
                option_one.setText(testSubjectDataModel.getAnswerOption1());
                option_two.setText(testSubjectDataModel.getAnswerOption2());
                option_three.setText(testSubjectDataModel.getAnswerOption3());
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else if (ans5.equals("") && ans6.equals("")) {
            option_five.setVisibility(View.INVISIBLE);
            option_six.setVisibility(View.INVISIBLE);
            try {
                option_one.setText(testSubjectDataModel.getAnswerOption1());
                option_two.setText(testSubjectDataModel.getAnswerOption2());
                option_three.setText(testSubjectDataModel.getAnswerOption3());
                option_four.setText(testSubjectDataModel.getAnswerOption4());
            } catch (Exception e) {
                e.printStackTrace();
            }


        } else if (ans6.equals("")) {
            option_six.setVisibility(View.INVISIBLE);
            try {
                option_one.setText(testSubjectDataModel.getAnswerOption1());
                option_two.setText(testSubjectDataModel.getAnswerOption2());
                option_three.setText(testSubjectDataModel.getAnswerOption3());
                option_four.setText(testSubjectDataModel.getAnswerOption4());
                option_five.setText(testSubjectDataModel.getAnswerOption5());
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            try {
                option_one.setText(testSubjectDataModel.getAnswerOption1());
                option_two.setText(testSubjectDataModel.getAnswerOption2());
                option_three.setText(testSubjectDataModel.getAnswerOption3());
                option_four.setText(testSubjectDataModel.getAnswerOption4());
                option_five.setText(testSubjectDataModel.getAnswerOption5());
                option_six.setText(testSubjectDataModel.getAnswerOption6());
            } catch (Exception e) {
                e.printStackTrace();
            }

        }


//        try {
//            Log.e("answer Arr:- ", title_arr.length + "");
//            Log.e("ans_1", title_arr[0]);
//            answer1 = title_arr[0];
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        try {
//            Log.e("ans_1", title_arr[0]);
//            Log.e("ans_2", title_arr[1]);
//            answer1 = title_arr[0];
//            answer2 = title_arr[1];
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        try {
//            Log.e("ans_1", title_arr[0]);
//            Log.e("ans_2", title_arr[1]);
//            Log.e("ans_3", title_arr[2]);
//            answer1 = title_arr[0];
//            answer2 = title_arr[1];
//            answer3 = title_arr[2];
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        try {
//            Log.e("ans_1", title_arr[0]);
//            Log.e("ans_2", title_arr[1]);
//            Log.e("ans_3", title_arr[2]);
//            Log.e("ans_4", title_arr[2]);
//            answer1 = title_arr[0];
//            answer2 = title_arr[1];
//            answer3 = title_arr[2];
//            answer4 = title_arr[3];
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        try {
            final String finalAnswer = answer;
            option_one.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked == true && finalAnswer.equalsIgnoreCase(String.valueOf(option_one.getText()))) {
                        Log.e("TestOne", "Demo");
                        option_one.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.green));
                        option_one.setEnabled(false);
                        option_two.setEnabled(false);
                        option_three.setEnabled(false);
                        option_four.setEnabled(false);
                        option_five.setEnabled(false);
                        option_six.setEnabled(false);
                        correctDialog(finalAnswer);
//                        if (qid < testSubjectDataModelArrayList.size()) {
//                            option_one.setEnabled(false);
//                            option_two.setEnabled(false);
//                            option_three.setEnabled(false);
//                            option_four.setEnabled(false);
//                            option_five.setEnabled(false);
//                            option_six.setEnabled(false);
//                            correctDialog(finalAnswer);
//                        } else {
//                            Log.e("option1thanku","");
//                            //gameWon();
//                        }
                    } else {
                        option_one.setEnabled(false);
                        option_two.setEnabled(false);
                        option_three.setEnabled(false);
                        option_four.setEnabled(false);
                        option_five.setEnabled(false);
                        option_six.setEnabled(false);
                        gameLostPlayAgain(option_one.getText().toString());
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            final String finalAnswer1 = answer;
            option_two.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked == true && option_two.getText().toString().equalsIgnoreCase(finalAnswer1)) {
                        //option_one.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.green));
                        option_two.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.green));
                        option_one.setEnabled(false);
                        option_two.setEnabled(false);
                        option_three.setEnabled(false);
                        option_four.setEnabled(false);
                        option_five.setEnabled(false);
                        option_six.setEnabled(false);
                        correctDialog(finalAnswer1);

//                        if (qid < testSubjectDataModelArrayList.size()) {
//                            option_one.setEnabled(false);
//                            option_two.setEnabled(false);
//                            option_three.setEnabled(false);
//                            option_four.setEnabled(false);
//                            option_five.setEnabled(false);
//                            option_six.setEnabled(false);
//                        } else {
//                           // gameWon();
//                        }
                    } else {
                        option_one.setEnabled(false);
                        option_two.setEnabled(false);
                        option_three.setEnabled(false);
                        option_four.setEnabled(false);
                        option_five.setEnabled(false);
                        option_six.setEnabled(false);
                        gameLostPlayAgain(option_two.getText().toString());
                    }
                    Log.e("Testtwo", "Demo");
                }
            });
        } catch (
                Exception e) {
            e.printStackTrace();
        }
        try {
            final String finalAnswer1 = answer;
            option_three.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked == true && option_three.getText().toString().equalsIgnoreCase(finalAnswer1)) {
                        //option_one.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.green));
                        option_three.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.green));
                        option_one.setEnabled(false);
                        option_two.setEnabled(false);
                        option_three.setEnabled(false);
                        option_four.setEnabled(false);
                        option_five.setEnabled(false);
                        option_six.setEnabled(false);
                        correctDialog(finalAnswer1);
//                        if (qid < testSubjectDataModelArrayList.size()) {
//                            option_one.setEnabled(false);
//                            option_two.setEnabled(false);
//                            option_three.setEnabled(false);
//                            option_four.setEnabled(false);
//                            option_five.setEnabled(false);
//                            option_six.setEnabled(false);
//                            correctDialog(finalAnswer1);
//                        } else {
//                           // gameWon();
//                        }
                    } else {
                        option_one.setEnabled(false);
                        option_two.setEnabled(false);
                        option_three.setEnabled(false);
                        option_four.setEnabled(false);
                        option_five.setEnabled(false);
                        option_six.setEnabled(false);
                        gameLostPlayAgain(option_three.getText().toString());
                    }
                    Log.e("Testtwo", "Demo");
                }
            });
        } catch (
                Exception e) {
            e.printStackTrace();
        }

        try {
            final String finalAnswer1 = answer;
            option_four.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked == true && option_four.getText().toString().equalsIgnoreCase(finalAnswer1)) {
                        //option_one.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.green));
                        option_four.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.green));
                        option_one.setEnabled(false);
                        option_two.setEnabled(false);
                        option_three.setEnabled(false);
                        option_four.setEnabled(false);
                        option_five.setEnabled(false);
                        option_six.setEnabled(false);
                        correctDialog(finalAnswer1);

//                        if (qid < testSubjectDataModelArrayList.size()) {
//                            option_one.setEnabled(false);
//                            option_two.setEnabled(false);
//                            option_three.setEnabled(false);
//                            option_four.setEnabled(false);
//                            option_five.setEnabled(false);
//                            option_six.setEnabled(false);
//                            correctDialog(finalAnswer1);
//                        } else {
//                          //  gameWon();
//                        }
                    } else {
                        option_one.setEnabled(false);
                        option_two.setEnabled(false);
                        option_three.setEnabled(false);
                        option_four.setEnabled(false);
                        option_five.setEnabled(false);
                        option_six.setEnabled(false);
                        gameLostPlayAgain(option_four.getText().toString());
                    }
                    Log.e("Testtwo", "Demo");
                }
            });
        } catch (
                Exception e) {
            e.printStackTrace();
        }
        try {
            final String finalAnswer1 = answer;
            option_five.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked == true && option_five.getText().toString().equalsIgnoreCase(finalAnswer1)) {
                        //option_one.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.green));
                        option_five.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.green));
                        option_one.setEnabled(false);
                        option_two.setEnabled(false);
                        option_three.setEnabled(false);
                        option_four.setEnabled(false);
                        option_five.setEnabled(false);
                        option_six.setEnabled(false);
                        correctDialog(finalAnswer1);
//                        if (qid < testSubjectDataModelArrayList.size()) {
//                            option_one.setEnabled(false);
//                            option_two.setEnabled(false);
//                            option_three.setEnabled(false);
//                            option_four.setEnabled(false);
//                            option_five.setEnabled(false);
//                            option_six.setEnabled(false);
//                            correctDialog(finalAnswer1);
//                        } else {
//                           // gameWon();
//                        }
                    } else {
                        option_one.setEnabled(false);
                        option_two.setEnabled(false);
                        option_three.setEnabled(false);
                        option_four.setEnabled(false);
                        option_five.setEnabled(false);
                        option_six.setEnabled(false);
                        gameLostPlayAgain(option_one.getText().toString());
                    }
                    Log.e("Testtwo", "Demo");
                }
            });
        } catch (
                Exception e) {
            e.printStackTrace();
        }
        try {
            final String finalAnswer1 = answer;
            option_six.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked == true && option_six.getText().toString().equalsIgnoreCase(finalAnswer1)) {
                        //option_one.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.green));
                        option_six.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.green));
                        option_one.setEnabled(false);
                        option_two.setEnabled(false);
                        option_three.setEnabled(false);
                        option_four.setEnabled(false);
                        option_five.setEnabled(false);
                        option_six.setEnabled(false);
                        correctDialog(finalAnswer1);

//                        if (qid < testSubjectDataModelArrayList.size()) {
//                            option_one.setEnabled(false);
//                            option_two.setEnabled(false);
//                            option_three.setEnabled(false);
//                            option_four.setEnabled(false);
//                            option_five.setEnabled(false);
//                            option_six.setEnabled(false);
//                            correctDialog(finalAnswer1);
//                        } else {
//                            //gameWon();
//                        }
                    } else {
                        option_one.setEnabled(false);
                        option_two.setEnabled(false);
                        option_three.setEnabled(false);
                        option_four.setEnabled(false);
                        option_five.setEnabled(false);
                        option_six.setEnabled(false);
                        gameLostPlayAgain(option_six.getText().toString());
                    }
                    Log.e("Testtwo", "Demo");
                }
            });
        } catch (
                Exception e) {
            e.printStackTrace();
        }
        //timeValue = 30;
//        countDownTimer.cancel();
//        countDownTimer.start();
    }


    private void listner() {
        cancle_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(TestAcivity.this);
                builder.setView(R.layout.exitpopup);
                final AlertDialog alert = builder.create();
                alert.show();
                //dialog.getWindow().setAttributes(windo);
                TextView dialog_cancel = (TextView) alert.findViewById(R.id.dialog_cancel);
                TextView dialog_ok = (TextView) alert.findViewById(R.id.dialog_ok);
                TextView dialog_exit = (TextView) alert.findViewById(R.id.tv_exit);
                TextView exitinformation = (TextView) alert.findViewById(R.id.exitinformation);
                LinearLayout ll_cancel = (LinearLayout) alert.findViewById(R.id.ll_cancel);
                LinearLayout ll_ok = (LinearLayout) alert.findViewById(R.id.ll_ok);
                exitinformation.setText("Do you want to exit from this Test?");

                ll_cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alert.dismiss();
                    }
                });

                ll_ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        Intent intent = new Intent(RedThemeTestActivity.this, SubjectsSixthClassActivity.class);
//                        startActivity(intent);
//                        Global.correct_answer_test = 0;
//                        Global.wrong_answer_test = 0;
                        Global.total_question_number = 0;
                        finish();
                    }
                });
//
            }
        });
        next_test_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View next_view) {
                radioGroup.clearCheck();
                //timeValue=31;
                qid++;
                Log.e("qid_next_btn", String.valueOf(qid));
                if (qid > Global.total_question_number) {
                    countDownTimer.cancel();
                    Intent intent = new Intent(TestAcivity.this, ThankYouActivity.class);
                    intent.putExtra("testmodel",testSubjectDataModelArrayList);
                    startActivity(intent);
                } else {
                    //get the que and 4 option and store in the currentQuestion
                    countDownTimer.cancel();
                    int pos = qid - 1;
                    testSubjectDataModel = testSubjectDataModelArrayList.get(pos);
                    String question_number = String.valueOf(qid);
                    question_count.setText(question_number);
                    switch (testSubjectDataModel.getTestQuestionType()) {
                        case "Fill in the blank.":
                            typeThreeQuestion();
                            break;
                        case "Multiple Choice Question":
                            typeThreeQuestion();
                            break;
                        case "True or False:":
                            typeThreeQuestion();
                            break;
                    }

                }
            }
        });
    }

    public void gameWon() {
        countDownTimer.cancel();
        Intent intent = new Intent(TestAcivity.this, ThankYouActivity.class);
        intent.putExtra("testmodel",testSubjectDataModelArrayList);
        startActivity(intent);
    }

    //This method is called when user ans is wrong
//this method will navigate user to the activity PlayAgain
    public void gameLostPlayAgain(String wrong_answer) {
        final Dialog dialogCorrect = new Dialog(TestAcivity.this);
        dialogCorrect.requestWindowFeature(Window.FEATURE_NO_TITLE);
        if (dialogCorrect.getWindow() != null) {
            ColorDrawable colorDrawable = new ColorDrawable(Color.TRANSPARENT);
            dialogCorrect.getWindow().setBackgroundDrawable(colorDrawable);
        }
        dialogCorrect.setContentView(R.layout.dialog_correct);
        dialogCorrect.setCancelable(true);
        dialogCorrect.show();
        //Since the dialog is show to user just pause the timer in background
        onPause();
        TextView correctText = (TextView) dialogCorrect.findViewById(R.id.correctText);
        ImageView wrong_emoji = (ImageView) dialogCorrect.findViewById(R.id.correct_emoji);
        LinearLayout linearLayout = (LinearLayout) dialogCorrect.findViewById(R.id.correct_ans_id);
        linearLayout.setVisibility(View.VISIBLE);
        TextView answer = (TextView) dialogCorrect.findViewById(R.id.answer_id);

        answer.setText(testSubjectDataModel.getCorrectAnswer());

        correctText.setTypeface(sb);
        wrong_emoji.setBackgroundResource(R.drawable.wrong_ans_emoji);
        correctText.setText("Wrong");
        correctText.setTextColor(getResources().getColor(R.color.red));
        if (testSubjectDataModel.getWorng()!=1){
            // Global.wrong_answer_test++;
            testSubjectDataModel.setUser_ans(wrong_answer);
            testSubjectDataModel.setWorng(1);
            testSubjectDataModel.setCorrect(0);
            testSubjectDataModel.setNot_attempt(0);
        }else {
            Log.e("value_galat","sjfshfjsdhf");
        }


//        Global.wrong_answer_test++;
//        Log.e("sample_worng", String.valueOf(Global.wrong_answer_test));
        // Global.wrong_answer_test=wrong_answer_count+Global.wrong_answer_test;


//        correctText.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View view) {
//                //This will dismiss the dialog
//                dialogCorrect.dismiss();
//                radioGroup.clearCheck();
//                //it will increment the question number
//                qid++;
//                String question_number = String.valueOf(qid);
//                question_count.setText(question_number);
//
//                //get the que and 4 option and store in the currentQuestion
//                testQuestionHandleModel = testQuestionHandleModelArrayList.get(qid);
//                //Now this method will set the new que and 4 options
//
//
//                if (testQuestionHandleModel.getType().equals("1")) {
//                    typeOneQuestion();
//                } else if (testQuestionHandleModel.getType().equals("2")) {
//                    typeTwoQuestion();
//                } else if (testQuestionHandleModel.getType().equals("3")) {
//                    typeThreeQuestion();
//                } else if (testQuestionHandleModel.getType().equals("4")) {
//                    typeOneQuestion();
//
//                } else {
//                    Toast.makeText(RedThemeTestActivity.this, "Don't Have any Questions", Toast.LENGTH_LONG).show();
//                }
//                resetColor();
//                enableButton();
//            }
//        });
    }

    public void timeUp() {
        qid++;
        if (qid > testSubjectDataModelArrayList.size()) {
            Intent intent = new Intent(TestAcivity.this, ThankYouActivity.class);
            intent.putExtra("testmodel",testSubjectDataModelArrayList);
            startActivity(intent);
        } else {
            //get the que and 4 option and store in the currentQuestion
            int pos = qid - 1;
            testSubjectDataModel = testSubjectDataModelArrayList.get(pos);
            String question_number = String.valueOf(qid);
            question_count.setText(question_number);
            switch (testSubjectDataModel.getTestQuestionType()) {
                case "Fill in the blank.":
                    typeThreeQuestion();
                    break;
                case "Multiple Choice Question":
                    typeThreeQuestion();
                    break;
                case "True or False:":
                    typeThreeQuestion();
                    break;
            }
        }
    }


    @Override
    protected void onRestart() {
        super.onRestart();
        //  countDownTimer.start();
    }

    @Override
    protected void onStop() {
        super.onStop();
        countDownTimer.cancel();
    }

    //This will pause the time
    @Override
    protected void onPause() {
        super.onPause();
        countDownTimer.cancel();
    }

    //On BackPressed
    @Override
    public void onBackPressed() {
//        Intent intent = new Intent(this, SubjectsSixthClassActivity.class);
//        startActivity(intent);
        finish();
    }

    @Override
    public void finish() {
        try {
            Intent in = new Intent(TestAcivity.this, SubjectActivity.class);
            in.putExtra("DataSubject", you_Topic);
            //  in.putExtra("LandDetail", landDetailModel);
            in.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            setResult(RESULT_OK, in);
            super.finish();

        } catch (Exception e) {
            e.printStackTrace();
            countDownTimer.onFinish();
            Toast.makeText(this, "Please On Your Internet Connection", Toast.LENGTH_LONG).show();
            Intent in = new Intent(TestAcivity.this, SubjectActivity.class);
            startActivity(in);
        }
    }

    //This dialog is show to the user after he ans correct
    public void correctDialog(String answer) {
        final Dialog dialogCorrect = new Dialog(TestAcivity.this);
        dialogCorrect.requestWindowFeature(Window.FEATURE_NO_TITLE);
        if (dialogCorrect.getWindow() != null) {
            ColorDrawable colorDrawable = new ColorDrawable(Color.TRANSPARENT);
            dialogCorrect.getWindow().setBackgroundDrawable(colorDrawable);
        }
        dialogCorrect.setContentView(R.layout.dialog_correct);
        dialogCorrect.setCancelable(true);
        dialogCorrect.show();

        //Since the dialog is show to user just pause the timer in background
        onPause();


        TextView correctText = (TextView) dialogCorrect.findViewById(R.id.correctText);

        correctText.setTypeface(sb);

        if (testSubjectDataModel.getCorrect()!=1){
            //    Global.correct_answer_test++;
            testSubjectDataModel.setUser_ans(answer);
            testSubjectDataModel.setCorrect(1);
            testSubjectDataModel.setWorng(0);
            testSubjectDataModel.setNot_attempt(0);
        }else{

        }

//        Global.correct_answer_test++;
//        Log.e("sample_correct", String.valueOf(Global.correct_answer_test));
        //Global.correct_answer_test=correct_answer_count+Global.correct_answer_test;
//        correctText.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View view) {
//                //This will dismiss the dialog
//                dialogCorrect.dismiss();
//                radioGroup.clearCheck();
//                //it will increment the question number
//                qid++;
//                String question_number = String.valueOf(qid);
//                question_count.setText(question_number);
//
//
//                //get the que and 4 option and store in the currentQuestion
//                testQuestionHandleModel = testQuestionHandleModelArrayList.get(qid);
//                //Now this method will set the new que and 4 options
//
//                if (testQuestionHandleModel.getType().equals("1")) {
//                   typeOneQuestion();
//
//                } else if (testQuestionHandleModel.getType().equals("2")) {
//                  typeTwoQuestion();
//
//                } else if (testQuestionHandleModel.getType().equals("3")) {
//                   typeThreeQuestion();
//
//                } else if (testQuestionHandleModel.getType().equals("4")) {
//                    typeOneQuestion();
//
//                } else {
//                    Toast.makeText(RedThemeTestActivity.this, "Don't Have any Questions", Toast.LENGTH_LONG).show();
//                }
//                resetColor();
//                enableButton();
//            }
//        });
    }
}
