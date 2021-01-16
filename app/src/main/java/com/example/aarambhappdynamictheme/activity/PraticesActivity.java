package com.example.aarambhappdynamictheme.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
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
import com.example.aarambhappdynamictheme.model.PraticeSubjectDataModel;
import com.example.aarambhappdynamictheme.sheardPrefrence.AarambhSharedPreference;
import com.example.aarambhappdynamictheme.sheardPrefrence.AarambhTestHelper;
import com.example.aarambhappdynamictheme.sheardPrefrence.AarambhThemeSharedPrefreence;
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

public class PraticesActivity extends AppCompatActivity {
    Button next_btn, previous_btn;
    LinearLayout lLayout;
    View view;
    ImageView cancle_btn;
    TextView question_test, test_time, question_count;
    AarambhTestHelper aarambhTestHelper;
    RadioGroup radioGroup;
    //    TestQuestionHandleModel testQuestionHandleModel;
//    List<TestQuestionHandleModel> testQuestionHandleModelArrayList;
    int qid = 1;
    int coinValue = 0;
    //    CountDownTimer countDownTimer;
    Typeface tb, sb;
    ArrayList<PraticeSubjectDataModel> praticeSubjectDataModelArrayList;
    PraticeSubjectDataModel praticeSubjectDataModel;
    //    ArrayList<TestSubjectDataModel> testSubjectDataModelArrayList;
//    TestSubjectDataModel testSubjectDataModel;
    ArrayList<String> you_Topic;
    TextView pratice_no_total_question;
    int correct_ans = 0, wrong_answer = 0;
    String courseId, chapterId;
    Dialog dialogCorrect;
    LinearLayout pratices_background,transprent_test;
    String base_image,transparent_color,base_color_one,base_color_two;
    Bitmap Background;
    TextView pratices_text,pratices_slash;
    String pratice_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pratices);
        checkOrientation();
        try {
            pratice_id = getIntent().getStringExtra("praticesmasterId");
            Log.e("id", pratice_id);
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
        getPracticeData();
    }

    private void checkOrientation() {
        if (getResources().getBoolean(R.bool.portrait_only)) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
    }

    private void getPracticeData() {
        {
            if (!CommonUtilities.isOnline(this)) {
                Toast.makeText(PraticesActivity.this, "Please On Your Internet Connection", Toast.LENGTH_LONG).show();
                return;
            }
            final String class_id = AarambhSharedPreference.loadClassIdFromPreference(this);
            final String course_id = AarambhSharedPreference.loadCourseIdFromPreference(this);
            final String chapter_id = AarambhSharedPreference.loadTopicIdFromPreference(this);
           // String url = Global.WEBBASE_URL + "getPracticeQuestionAccordingToClassSubjectChapter_New?ClassId=" + class_id + "&CourseId=" + courseID + "&ChapterId=" + chapterID;
            String url = Global.WEBBASE_URL + "getPracticeQuestionAccToPracticeId?practiceId="+pratice_id;
            //http://35.200.220.64:1500/aarambhTesting/getPracticeQuestionAccToPracticeId?practiceId=18
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
                    params.put("practiceId", pratice_id);
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
                    headers.put("Authorization", "Bearer " + AarambhSharedPreference.loadUserTokenFromPreference(PraticesActivity.this));
                    return headers;
                }
            };
            VolleySingleton.getInstance(PraticesActivity.this).addToRequestQueue(stringRequest, string_json);

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
                        String PracticeDetailId = jsonObject.getString("PracticeDetailId");
                        String PracticeMasterId = jsonObject.getString("PracticeMasterId");
//                        String CourseId = jsonObject.getString("CourseId");
//                        String TopicId = jsonObject.getString("TopicId");
//                        String ChapterId = jsonObject.getString("ChapterId");
                        String PracticeQuestionType = jsonObject.getString("PracticeQuestionType");
                        String PracticeQuestion = jsonObject.getString("PracticeQuestion");
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
                        praticeSubjectDataModel = new PraticeSubjectDataModel(PracticeDetailId, PracticeMasterId,PracticeQuestionType, PracticeQuestion, AnswerOption1, AnswerOption2, AnswerOption3, AnswerOption4, AnswerOption5, AnswerOption6, CorrectAnswer, StatusId, CreatedById, ModifiedById, CreationDate, ModificationDate, 0, 0, 0, "");
                        praticeSubjectDataModelArrayList.add(praticeSubjectDataModel);
                    }
                    Log.e("size", String.valueOf(jsonArray.length()));
                    Global.total_question_number = jsonArray.length();
                    pratice_no_total_question.setText(String.valueOf(jsonArray.length()));
                    int p = qid - 1;
                    praticeSubjectDataModel = praticeSubjectDataModelArrayList.get(p);
                    previous_btn.setVisibility(View.VISIBLE);
                    next_btn.setVisibility(View.VISIBLE);
                    switch (praticeSubjectDataModel.getPracticeQuestionType()) {
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
                    // disableButton();
                    //Log.e("Question Type", praticeSubjectDataModel.getTestQuestionType());
//                        if (praticeSubjectDataModel.getTestQuestionType().equals("True False")) {
//                            //singal choice question
//                            typeOneQuestion();
//                        } else if (praticeSubjectDataModel.getTestQuestionType().equals("")) {
//                            //true and false type question
//                            typeTwoQuestion();
//                        } else if (praticeSubjectDataModel.getTestQuestionType().equals("Fill in the blank.")) {
//                            //multi choice option
//                            typeThreeQuestion();
//                        } else if (praticeSubjectDataModel.getTestQuestionType().equals("4")) {
//                            //fill in the blanks question
//                            typeOneQuestion();
//                        } else {
//                            Toast.makeText(RedThemeTestActivity.this, "Don't Have any Questions", Toast.LENGTH_LONG).show();
//                        }

                } else {
                    Toast.makeText(this, Message + "", Toast.LENGTH_LONG).show();

                }
            }
            // Log.e("Question Type", praticeSubjectDataModel.getTestQuestionType());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void listner() {
        next_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //onRestart();
                if (qid >= 1) {
                    int[] colors = {Color.parseColor(base_color_one), Color.parseColor(base_color_two)};

                    //create a new gradient color
                    GradientDrawable gd = new GradientDrawable(
                            GradientDrawable.Orientation.TOP_BOTTOM, colors);


                    gd.setCornerRadius(100f);

                    //apply the button background to newly created drawable gradient
                    previous_btn.setBackground(gd);
                } else {
                    previous_btn.setBackgroundResource(R.drawable.btn_gray_btn);
                }

                qid++;
                //previous_btn.setBackgroundResource(R.drawable.btn_login_bg);

                if (qid > praticeSubjectDataModelArrayList.size()) {
                    Intent intent = new Intent(PraticesActivity.this, ThankYouActivity.class);
                    intent.putExtra("praticesModel",praticeSubjectDataModelArrayList);
                    startActivity(intent);
                } else if (qid>=1){
                    int pos = qid - 1;
                    praticeSubjectDataModel = praticeSubjectDataModelArrayList.get(pos);
                    String question_number = String.valueOf(qid);
                    question_count.setText(question_number);
                    switch (praticeSubjectDataModel.getPracticeQuestionType()) {
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
        });

        previous_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("qid Value", String.valueOf(qid));
                if(qid==1)
                {}
                else{
                    qid--;}
                Log.e("qid Value--", String.valueOf(qid));
//                if(praticeSubjectDataModel.getSelectedAnswer() != null){
//                    Log.e("Previous Answer", praticeSubjectDataModel.getSelectedAnswer());
//                }

                if (qid == 1) {
                    previous_btn.setBackgroundResource(R.drawable.btn_gray_btn);
                    int p = qid - 1;
                    praticeSubjectDataModel = praticeSubjectDataModelArrayList.get(p);
                    String question_number = String.valueOf(qid);
                    question_count.setText(question_number);
//                    qid=2;
                    switch (praticeSubjectDataModel.getPracticeQuestionType()) {
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
                    // qid=1;
                } else if (qid>=1){
                    int[] colors = {Color.parseColor(base_color_one), Color.parseColor(base_color_two)};

                    //create a new gradient color
                    GradientDrawable gd = new GradientDrawable(
                            GradientDrawable.Orientation.TOP_BOTTOM, colors);


                    gd.setCornerRadius(100f);

                    //apply the button background to newly created drawable gradient
                    previous_btn.setBackground(gd);
                    //get the que and 4 option and store in the currentQuestion
                    int p = qid - 1;
                    praticeSubjectDataModel = praticeSubjectDataModelArrayList.get(p);
                    String question_number = String.valueOf(qid);
                    question_count.setText(question_number);
                    switch (praticeSubjectDataModel.getPracticeQuestionType()) {
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
        });

//        option1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (testQuestionHandleModel.getOption1().equals(testQuestionHandleModel.getAnswer())) {
//                    option1.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.green));
//                    //Check if user has not exceeds the que limit
//                    if (qid < testQuestionHandleModelArrayList.size() - 1) {
//                        disableButton();
//
//                        //Show the dialog that ans is correct
//                        correctDialog();
//                    }
//                    else {
//                        gameWon();
//                    }
//                }
//                else {
//                    gameLostPlayAgain();
//
//                }
//
//            }
//        });
//        option2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (testQuestionHandleModel.getOption2().equals(testQuestionHandleModel.getAnswer())) {
//                    option2.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.green));
//                    if (qid < testQuestionHandleModelArrayList.size() - 1) {
//                        disableButton();
//                        correctDialog();
//                    } else {
//                        gameWon();
//                    }
//                } else {
//                    gameLostPlayAgain();
//                }
//            }
//        });
//        option3.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (testQuestionHandleModel.getOption3().equals(testQuestionHandleModel.getAnswer())) {
//                    option3.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.green));
//                    if (qid < testQuestionHandleModelArrayList.size() - 1) {
//                        disableButton();
//                        correctDialog();
//                    } else {
//                        gameWon();
//                    }
//                } else {
//
//                    gameLostPlayAgain();
//                }
//            }
//        });
//        option4.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (testQuestionHandleModel.getOption4().equals(testQuestionHandleModel.getAnswer())) {
//                    option4.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.green));
//                    if (qid < testQuestionHandleModelArrayList.size() - 1) {
//                        disableButton();
//                        correctDialog();
//                    } else {
//                        gameWon();
//                    }
//                } else {
//                    gameLostPlayAgain();
//                }
//            }
//        });

        cancle_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(PraticesActivity.this);
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
                exitinformation.setText("Do you want to exit from this Practice?");

                ll_cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alert.dismiss();
                    }
                });
                ll_ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Global.total_question_number=0;
//                        Global.wrong_answer_test=0;
//                        Global.correct_answer_test=0;
                        finish();
                    }
                });
//                Intent intent=new Intent(RedThemePracticeActivity.this, SubjectsSixthClassActivity.class);
//                startActivity(intent);
            }
        });

    }

    private void gameLostPlayAgain(String wrong_answer) {

        dialogCorrect = new Dialog(PraticesActivity.this);
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

        answer.setText(praticeSubjectDataModel.getCorrectAnswer());

        correctText.setTypeface(sb);
        wrong_emoji.setBackgroundResource(R.drawable.wrong_ans_emoji);
        correctText.setText("Wrong");
        correctText.setTextColor(getResources().getColor(R.color.red));

        if (praticeSubjectDataModel.getWrong()!=1){
            //  Global.wrong_answer_test++;
            praticeSubjectDataModel.setUser_ans(wrong_answer);
            praticeSubjectDataModel.setWrong(1);
            praticeSubjectDataModel.setCorrect(0);
            praticeSubjectDataModel.setNot_attempt(0);
        }else {
            Log.e("value_galat","sjfshfjsdhf");
        }

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

    private void gameWon() {
        Intent intent = new Intent(this, ThankYouActivity.class);
        //  intent.putExtra("pratice_text", "Your Practice Session has been Submited Successfully");
        intent.putExtra("praticesModel",praticeSubjectDataModelArrayList);

        startActivity(intent);
    }

    private void correctDialog(String answer) {
        dialogCorrect = new Dialog(PraticesActivity.this);
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
        if (praticeSubjectDataModel.getCorrect()!=1){
            // Global.correct_answer_test++;
            praticeSubjectDataModel.setUser_ans(answer);
            praticeSubjectDataModel.setCorrect(1);
            praticeSubjectDataModel.setWrong(0);
            praticeSubjectDataModel.setNot_attempt(0);
        }else{

        }

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

    private void getDynamicData() {
        //Color.parseColor() method allow us to convert
        // a hexadecimal color string to an integer value (int color)
        int[] colors = {Color.parseColor(base_color_one), Color.parseColor(base_color_two)};

        //create a new gradient color
        GradientDrawable gd = new GradientDrawable(
                GradientDrawable.Orientation.TOP_BOTTOM, colors);


        gd.setCornerRadius(100f);

        //apply the button background to newly created drawable gradient
        next_btn.setBackground(gd);
       // previous_btn.setBackground(gd);
        // cancle_btn.setBackground(gd1);
    }

    private void themeDynamicAdd() {

        //Bitmap myImage = getBitmapFromURL(image);
        try {
            Background=getBitmapFromURL(base_image);
            Drawable dr = new BitmapDrawable((Background));
            pratices_background.setBackgroundDrawable(dr);

        }catch (Exception e){
            e.printStackTrace();
        }
        Bitmap back_bitmap;
        String back_shrd=AarambhThemeSharedPrefreence.loadCancleIconFromPreference(this);
        try {
            back_bitmap=getBitmapFromURL(back_shrd);
            Drawable dr = new BitmapDrawable((back_bitmap));
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
        View child = getLayoutInflater().inflate(R.layout.practice_fillblank_typeq, null);
        item.addView(child);
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
        String ans = praticeSubjectDataModel.getAnswerOption1();
        String ans2 = praticeSubjectDataModel.getAnswerOption2();
        String ans3 = praticeSubjectDataModel.getAnswerOption3();
        String ans4 = praticeSubjectDataModel.getAnswerOption4();
        String ans5 = praticeSubjectDataModel.getAnswerOption5();
        String ans6 = praticeSubjectDataModel.getAnswerOption6();
        String answer = praticeSubjectDataModel.getCorrectAnswer();
        String answer1 = null, answer2 = null, answer3, answer4, answer5, answer6;
        //  String title_arr[] = answer1.split(",");

        try {
            question_test.setText(praticeSubjectDataModel.getPracticeQuestion());
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (ans3.equals("") && ans4.equals("") && ans5.equals("") && ans6.equals("")) {
            option_three.setVisibility(View.INVISIBLE);
            option_four.setVisibility(View.INVISIBLE);
            option_five.setVisibility(View.INVISIBLE);
            option_six.setVisibility(View.INVISIBLE);
            try {
                option_one.setText(praticeSubjectDataModel.getAnswerOption1());
                option_two.setText(praticeSubjectDataModel.getAnswerOption2());
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (ans4.equals("") && ans5.equals("") && ans6.equals("")) {
            option_four.setVisibility(View.INVISIBLE);
            option_five.setVisibility(View.INVISIBLE);
            option_six.setVisibility(View.INVISIBLE);
            try {
                option_one.setText(praticeSubjectDataModel.getAnswerOption1());
                option_two.setText(praticeSubjectDataModel.getAnswerOption2());
                option_three.setText(praticeSubjectDataModel.getAnswerOption3());
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else if (ans5.equals("") && ans6.equals("")) {
            option_five.setVisibility(View.INVISIBLE);
            option_six.setVisibility(View.INVISIBLE);
            try {
                option_one.setText(praticeSubjectDataModel.getAnswerOption1());
                option_two.setText(praticeSubjectDataModel.getAnswerOption2());
                option_three.setText(praticeSubjectDataModel.getAnswerOption3());
                option_four.setText(praticeSubjectDataModel.getAnswerOption4());
            } catch (Exception e) {
                e.printStackTrace();
            }


        } else if (ans6.equals("")) {
            option_six.setVisibility(View.INVISIBLE);
            try {
                option_one.setText(praticeSubjectDataModel.getAnswerOption1());
                option_two.setText(praticeSubjectDataModel.getAnswerOption2());
                option_three.setText(praticeSubjectDataModel.getAnswerOption3());
                option_four.setText(praticeSubjectDataModel.getAnswerOption4());
                option_five.setText(praticeSubjectDataModel.getAnswerOption5());
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            try {
                option_one.setText(praticeSubjectDataModel.getAnswerOption1());
                option_two.setText(praticeSubjectDataModel.getAnswerOption2());
                option_three.setText(praticeSubjectDataModel.getAnswerOption3());
                option_four.setText(praticeSubjectDataModel.getAnswerOption4());
                option_five.setText(praticeSubjectDataModel.getAnswerOption5());
                option_six.setText(praticeSubjectDataModel.getAnswerOption6());
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

//
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

//                        if (qid < praticeSubjectDataModelArrayList.size()) {
//                            option_one.setEnabled(false);
//                            option_two.setEnabled(false);
//                            option_three.setEnabled(false);
//                            option_four.setEnabled(false);
//                            option_five.setEnabled(false);
//                            option_six.setEnabled(false);
//                            correctDialog(finalAnswer);
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

                        String wrong_ans = option_one.getText().toString();
                        String wrong_ans1 = option_two.getText().toString();
                        String wrong_ans2 = option_three.getText().toString();
                        String wrong_ans3 = option_four.getText().toString();
                        String wrong_ans4 = option_five.getText().toString();
                        String wrong_ans5 = option_six.getText().toString();

                        //if(wrong_ans.equals(finalAnswer) || wrong_ans1.equals(finalAnswer) || wrong_ans2.equals(finalAnswer) || wrong_ans3.equals(finalAnswer) || wrong_ans4.equals(finalAnswer) )

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

//                        if (qid < praticeSubjectDataModelArrayList.size()) {
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

//                        if (qid < praticeSubjectDataModelArrayList.size()) {
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

//                        if (qid < praticeSubjectDataModelArrayList.size()) {
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

//
//                        if (qid < praticeSubjectDataModelArrayList.size()) {
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
                        gameLostPlayAgain(option_five.getText().toString());
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

//                        if (qid < praticeSubjectDataModelArrayList.size()) {
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
                        gameLostPlayAgain(option_six.getText().toString());
                    }
                    Log.e("Testtwo", "Demo");
                }
            });
        } catch (
                Exception e) {
            e.printStackTrace();
        }

        try{
            if(praticeSubjectDataModel.getUser_ans().contains("")&&praticeSubjectDataModel.getCorrect()==(0)&&praticeSubjectDataModel.getWrong()==(0)&&praticeSubjectDataModel.getNot_attempt()==(0)){
                Log.e("Null Anser", praticeSubjectDataModel.getUser_ans());
            }
            else{
                Log.e("Not Null Anser", praticeSubjectDataModel.getUser_ans());
                String selected_ans = praticeSubjectDataModel.getUser_ans();

                String opt1 = option_one.getText().toString();
                String opt2 = option_two.getText().toString();
                String opt3 = option_three.getText().toString();
                String opt4 = option_four.getText().toString();
                String opt5 = option_five.getText().toString();
                String opt6 = option_six.getText().toString();
                if(selected_ans.contains(opt1)){
                    option_one.setText(praticeSubjectDataModel.getUser_ans());
                    option_one.setChecked(true);
                    dialogCorrect.dismiss();
                }
                else if(selected_ans.contains(opt2))
                {
                    option_two.setText(praticeSubjectDataModel.getUser_ans());
                    option_two.setChecked(true);
                    dialogCorrect.dismiss();
                }
                else if(selected_ans.contains(opt3)){
                    option_three.setText(praticeSubjectDataModel.getUser_ans());
                    option_three.setChecked(true);
                    dialogCorrect.dismiss();}

                else if(selected_ans.contains(opt4)){
                    option_four.setText(praticeSubjectDataModel.getUser_ans());
                    option_four.setChecked(true);
                    dialogCorrect.dismiss();
                }

                else if(selected_ans.contains(opt5)){
                    option_five.setText(praticeSubjectDataModel.getUser_ans());
                    option_five.setChecked(true);dialogCorrect.dismiss();}

                else if(selected_ans.contains(opt6)){
                    option_six.setText(praticeSubjectDataModel.getUser_ans());
                    option_six.setChecked(true);

                    dialogCorrect.dismiss();}
            }
        }catch (Exception e){e.printStackTrace();}

//        if(option_one.isSelected()==true){
//            Log.e("try", String.valueOf(option_one.isChecked()));
//            String option1= String.valueOf(option_one.getText());
//            if (answer1.equalsIgnoreCase(option1)){
//                option_one.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.green));
//                    option_two.setEnabled(false);
//                    option_three.setEnabled(false);
//                    option_four.setEnabled(false);
//                    if (qid < praticeSubjectDataModelArrayList.size() - 1) {
//                        //disableButton();
//                        option_two.setEnabled(false);
//                        option_three.setEnabled(false);
//                        option_four.setEnabled(false);
//                        correctDialog();
//                    } else {
//                        gameWon();
//                    }
//                } else {
//                    option_two.setEnabled(false);
//                    option_three.setEnabled(false);
//                    option_four.setEnabled(false);
//                    gameLostPlayAgain();
//                }
//        }
//
//        try{
//            if (option_one.isChecked()==true&&option_two.isChecked()==true){
//                String option1= String.valueOf(option_one.getText());
//                String option2= String.valueOf(option_two.getText());
//                if (answer1.equalsIgnoreCase(option1)&&answer2.equalsIgnoreCase(option2)){
//
//                    option_one.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.green));
//                    option_two.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.green));
//                    option_three.setEnabled(false);
//                    option_four.setEnabled(false);
//                    if (qid < praticeSubjectDataModelArrayList.size() - 1) {
//                        //disableButton();
//                        option_three.setEnabled(false);
//                        option_four.setEnabled(false);
//                        correctDialog();
//                    } else {
//                        gameWon();
//                    }
//                } else {
//                    option_two.setEnabled(false);
//                    option_three.setEnabled(false);
//                    option_four.setEnabled(false);
//                    gameLostPlayAgain();
//                }
//            }
//        }catch (Exception e){
//            e.printStackTrace();
//        }

//        if (answer1){
//
//        }else if(answer1&&answer2){
//
//        }else if (answer1&&answer2&&answer3){
//
//        }else if (answer1&&answer2&&answer3&&answer4){
//
//        }else if (answer1&&answer2&&answer3&&answer4&&answer5){
//
//        }else if (answer1&&answer2&&answer3&&answer4&&answer5&&answer6){
//
//        }
        //coinValue++;
//        option_one.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {

//                if (praticeSubjectDataModel.getAnswerOption1().equals(praticeSubjectDataModel.getCorrectAnswer()) || testQuestionHandleModel.getOption1().equals(testQuestionHandleModel.getAnswer2())) {
//                    option_one.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.green));
//                    option_two.setEnabled(false);
//                    option_three.setEnabled(false);
//                    option_four.setEnabled(false);
//                    if (qid < testQuestionHandleModelArrayList.size() - 1) {
//                        disableButton();
//                        option_two.setEnabled(false);
//                        option_three.setEnabled(false);
//                        option_four.setEnabled(false);
//                        correctDialog();
//                    } else {
//                        gameWon();
//                    }
//                } else {
//                    option_two.setEnabled(false);
//                    option_three.setEnabled(false);
//                    option_four.setEnabled(false);
//                    gameLostPlayAgain();
//                }
//            }
//        });
//        option_two.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (testQuestionHandleModel.getOption2().equals(testQuestionHandleModel.getAnswer()) || testQuestionHandleModel.getOption2().equals(testQuestionHandleModel.getAnswer2())) {
//                    option_two.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.green));
//                    option_one.setEnabled(false);
//                    option_three.setEnabled(false);
//                    option_four.setEnabled(false);
//
//                    //Check if user has not exceeds the que limit
//                    if (qid < testQuestionHandleModelArrayList.size() - 1) {
//                        disableButton();
//                        option_one.setEnabled(false);
//                        option_three.setEnabled(false);
//                        option_four.setEnabled(false);
//                        //Show the dialog that ans is correct
//                        correctDialog();
//                    } else {
//                        gameWon();
//                    }
//                } else {
//                    option_one.setEnabled(false);
//                    option_three.setEnabled(false);
//                    option_four.setEnabled(false);
//                    gameLostPlayAgain();
//                }
//
//            }
//        });
//        option_three.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (testQuestionHandleModel.getOption3().equals(testQuestionHandleModel.getAnswer()) || testQuestionHandleModel.getOption3().equals(testQuestionHandleModel.getAnswer2())) {
//                    option_three.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.green));
//
//                    option_two.setEnabled(false);
//                    option_one.setEnabled(false);
//                    option_four.setEnabled(false);
//                    //Check if user has not exceeds the que limit
//                    if (qid < testQuestionHandleModelArrayList.size() - 1) {
//                        disableButton();
//                        option_two.setEnabled(false);
//                        option_one.setEnabled(false);
//                        option_four.setEnabled(false);
//                        correctDialog();
//                    } else {
//                        gameWon();
//                    }
//                } else {
//                    option_two.setEnabled(false);
//                    option_one.setEnabled(false);
//                    option_four.setEnabled(false);
//                    gameLostPlayAgain();
//                }
//            }
//        });
//        option_four.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (testQuestionHandleModel.getOption4().equals(testQuestionHandleModel.getAnswer()) || testQuestionHandleModel.getOption4().equals(testQuestionHandleModel.getAnswer2())) {
//                    option_four.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.green));
//                    //Check if user has not exceeds the que limit
//                    option_two.setEnabled(false);
//                    option_three.setEnabled(false);
//                    option_one.setEnabled(false);
//
//                    if (qid < testQuestionHandleModelArrayList.size() - 1) {
//                        disableButton();
//                        option_two.setEnabled(false);
//                        option_three.setEnabled(false);
//                        option_one.setEnabled(false);
//                        correctDialog();
//                    } else {
//                        gameWon();
//                    }
//                } else {
//                    option_two.setEnabled(false);
//                    option_three.setEnabled(false);
//                    option_one.setEnabled(false);
//                    gameLostPlayAgain();
//                }
        //       }
//        });
    }


    private void init() {
        StrictMode.ThreadPolicy policy = new
                StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        you_Topic = new ArrayList<>();
        pratice_no_total_question = findViewById(R.id.test_no_total_question);
        pratice_no_total_question.setTextColor(Color.parseColor(base_color_one));
        lLayout = (LinearLayout) findViewById(R.id.linear_layout_pratices);
        next_btn = findViewById(R.id.next_pratices);
        previous_btn = findViewById(R.id.practices_previous_btn);

        cancle_btn = findViewById(R.id.cancle_btn);
        praticeSubjectDataModelArrayList = new ArrayList<>();
        radioGroup = findViewById(R.id.radiogroup_btn);
        // testQuestionHandleModelArrayList=new ArrayList<>();
        question_count = findViewById(R.id.practice_question_count);
        question_count.setTextColor(Color.parseColor(base_color_one));
        test_time = (TextView) findViewById(R.id.test_time);
        question_test = (TextView) findViewById(R.id.pratice_question);
        previous_btn.setVisibility(View.GONE);
        next_btn.setVisibility(View.GONE);
        try{
            String chapname = getIntent().getStringExtra("ChapterNamePractice");
            if(chapname.contains("Fun with Magnets")){
                courseId = "3";
                chapterId = "4";
               // getPracticeData("3","4");
            }
            if(chapname.contains("Components of Food")){
                courseId = "3";
                chapterId = "7";
                //getPracticeData("3","7");
            }
            if(chapname.contains("Knowing Our Numbers")){
                courseId = "6";
                chapterId = "5";
            //    getPracticeData("6","5");
            }
            if(chapname.contains("Integers")){
                courseId = "6";
                chapterId = "6";
              //  getPracticeData("6","6");
            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }

        try{
            String chapname = getIntent().getStringExtra("8thChapterNamePractice");
            if(chapname.contains("Linear Equations")){
                courseId = "47";
                chapterId = "8";
                //getPracticeData("47","8");
            }
            if(chapname.contains("Cube & Cube Roots")){
                courseId = "47";
                chapterId = "9";
            ///    getPracticeData("47","9");
            }
            if(chapname.contains("Crop Production & Management")){
                courseId = "56";
                chapterId = "10";
             //   getPracticeData("56","10");
            }
        }catch (Exception e){e.printStackTrace();}

        try {
            String chapname = getIntent().getStringExtra("7thChapterNamePractice");
            if (chapname.contains("Nutrition in animals")) {
                courseId = "";
                chapterId = "";
             //   getPracticeData("", "");
            }
            if (chapname.contains("Acids, Bases & Salts")) {
                courseId = "7";
                chapterId = "11";
             ///   getPracticeData("7", "11");
            }
            if (chapname.contains("Fractions & Decimals")) {
                courseId = "45";
                chapterId = "12";
                //getPracticeData("45", "12");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        pratices_background=findViewById(R.id.pratices_background);
        transprent_test=findViewById(R.id.transprent_test);
        transprent_test.setBackgroundColor(Color.parseColor(transparent_color));
        pratices_text=findViewById(R.id.pratices_text);
        pratices_text.setTextColor(Color.parseColor(base_color_one));
        pratices_slash=findViewById(R.id.pratices_slash);
        pratices_slash.setTextColor(Color.parseColor(base_color_one));
        themeDynamicAdd();
        getDynamicData();



//            aarambhTestHelper = new AarambhTestHelper(this);
//            //Make db writable
//            aarambhTestHelper.getWritableDatabase();
//
//            if (aarambhTestHelper.getAllOfTheQuestions().size() == 0) {
//                aarambhTestHelper.allQuestion();
//            }
//            testQuestionHandleModelArrayList = aarambhTestHelper.getAllOfTheQuestions();
//            Collections.shuffle(testQuestionHandleModelArrayList);
//        testQuestionHandleModel = testQuestionHandleModelArrayList.get(qid);
//        Log.e("Question Type", testQuestionHandleModel.getType());
//        if (testQuestionHandleModel.getType().equals("1")) {
//            //singal choice question
//            typeOneQuestion();
//        } else if (testQuestionHandleModel.getType().equals("2")) {
//            //true and false type question
//            typeTwoQuestion();
//        } else if (testQuestionHandleModel.getType().equals("3")) {
//            //multi choice option
//            typeThreeQuestion();
//        } else if (testQuestionHandleModel.getType().equals("4")) {
//            //fill in the blanks question
//            typeOneQuestion();
//        } else {
//            Toast.makeText(this, "Don't Have any Questions", Toast.LENGTH_LONG).show();
//        }
//    }
//
//    private void typeThreeQuestion() {
//        RadioGroup item = (RadioGroup) findViewById(R.id.radiogroup_btn);
//        item.removeAllViews();
//        View child = getLayoutInflater().inflate(R.layout.practice_fillblank_typeq, null);
//        item.addView(child);
//        multiChoiceQuestion();
//
//    }
//
//    private void typeTwoQuestion() {
//        RadioGroup item = (RadioGroup) findViewById(R.id.radiogroup_btn);
//        item.removeAllViews();
//        View child = getLayoutInflater().inflate(R.layout.practice_truefalse_typeq, null);
//        item.addView(child);
//        trueAndFalseQuestion();
//
//    }
//
//    private void multiChoiceQuestion() {
//        final CheckBox option_one, option_two, option_three, option_four;
//        final String[] condi1 = new String[1];
//        final String[] condi2 = new String[1];
//        final String[] condi3 = new String[1];
//        final String[] condi4 = new String[1];
//
//        option_one = findViewById(R.id.check_one);
//        option_two = findViewById(R.id.check_two);
//        option_three = findViewById(R.id.check_three);
//        option_four = findViewById(R.id.check_four);
//        final String answer1=testQuestionHandleModel.getAnswer();
//        final String answer2=testQuestionHandleModel.getAnswer2();
//
//        try {
//            question_test.setText(testQuestionHandleModel.getQuestion());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        try {
//            option_one.setText(testQuestionHandleModel.getOption1());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        try {
//            option_two.setText(testQuestionHandleModel.getOption2());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        try {
//            Log.e("option3", testQuestionHandleModel.getOption3());
//            option_three.setText(testQuestionHandleModel.getOption3());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        try {
//            Log.e("option4", testQuestionHandleModel.getOption4());
//            option_four.setText(testQuestionHandleModel.getOption4());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        option_one.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                if (testQuestionHandleModel.getOption1().equals(testQuestionHandleModel.getAnswer())||testQuestionHandleModel.getOption1().equals(testQuestionHandleModel.getAnswer2())) {
//                    option_one.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.green));
//                    option_two.setEnabled(false);
//                    option_three.setEnabled(false);
//                    option_four.setEnabled(false);
//                    if (qid < testQuestionHandleModelArrayList.size() - 1) {
//                        option_two.setEnabled(false);
//                        option_three.setEnabled(false);
//                        option_four.setEnabled(false);
//                        correctDialog();
//                    } else {
//                        gameWon();
//                    }
//                } else {
//                    option_two.setEnabled(false);
//                    option_three.setEnabled(false);
//                    option_four.setEnabled(false);
//                    gameLostPlayAgain();
//                }
//            }
//        });
//        option_two.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (testQuestionHandleModel.getOption2().equals(testQuestionHandleModel.getAnswer())||testQuestionHandleModel.getOption2().equals(testQuestionHandleModel.getAnswer2())) {
//                    option_two.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.green));
//                    option_one.setEnabled(false);
//                    option_three.setEnabled(false);
//                    option_four.setEnabled(false);
//
//                    //Check if user has not exceeds the que limit
//                    if (qid < testQuestionHandleModelArrayList.size() - 1) {
//                        option_one.setEnabled(false);
//                        option_three.setEnabled(false);
//                        option_four.setEnabled(false);
//                        //Show the dialog that ans is correct
//                        correctDialog();
//                    } else {
//                        gameWon();
//                    }
//                } else {
//                    option_one.setEnabled(false);
//                    option_three.setEnabled(false);
//                    option_four.setEnabled(false);
//                    gameLostPlayAgain();
//                }
//
//            }
//        });
//        option_three.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (testQuestionHandleModel.getOption3().equals(testQuestionHandleModel.getAnswer())||testQuestionHandleModel.getOption3().equals(testQuestionHandleModel.getAnswer2())) {
//                    option_three.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.green));
//
//                    option_two.setEnabled(false);
//                    option_one.setEnabled(false);
//                    option_four.setEnabled(false);
//                    //Check if user has not exceeds the que limit
//                    if (qid < testQuestionHandleModelArrayList.size() - 1) {
//                        option_two.setEnabled(false);
//                        option_one.setEnabled(false);
//                        option_four.setEnabled(false);
//                        correctDialog();
//                    } else {
//                        gameWon();
//                    }
//                } else {
//                    option_two.setEnabled(false);
//                    option_one.setEnabled(false);
//                    option_four.setEnabled(false);
//                    gameLostPlayAgain();
//                }
//            }
//        });
//        option_four.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (testQuestionHandleModel.getOption4().equals(testQuestionHandleModel.getAnswer())||testQuestionHandleModel.getOption4().equals(testQuestionHandleModel.getAnswer2())) {
//                    option_four.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.green));
//                    //Check if user has not exceeds the que limit
//                    option_two.setEnabled(false);
//                    option_three.setEnabled(false);
//                    option_one.setEnabled(false);
//
//                    if (qid < testQuestionHandleModelArrayList.size() - 1) {
//                        option_two.setEnabled(false);
//                        option_three.setEnabled(false);
//                        option_one.setEnabled(false);
//                        correctDialog();
//                    } else {
//                        gameWon();
//                    }
//                } else {
//                    option_two.setEnabled(false);
//                    option_three.setEnabled(false);
//                    option_one.setEnabled(false);
//                    gameLostPlayAgain();
//                }
//            }
//        });
//
//    }
//
//    public void trueAndFalseQuestion() {
//        Log.e("type_of_ques", testQuestionHandleModel.getType());
//        final RadioButton true_btn,false_btn;
//        true_btn = findViewById(R.id.true_option);
//        false_btn = findViewById(R.id.false_option);
//
//
//        question_test.setText(testQuestionHandleModel.getQuestion());
//        try {
//            true_btn.setText(testQuestionHandleModel.getOption1());
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//        try {
//            false_btn.setText(testQuestionHandleModel.getOption2());
//        }catch (Exception e){
//
//        }
//        true_btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (testQuestionHandleModel.getOption1().equals(testQuestionHandleModel.getAnswer())) {
//                    true_btn.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.green));
//                    false_btn.setEnabled(false);
//                    //Check if user has not exceeds the que limit
//                    if (qid < testQuestionHandleModelArrayList.size() - 1) {
//                        false_btn.setEnabled(false);
//
//                        correctDialog();
//                    } else {
//                        gameWon();
//                    }
//                } else {
//                    false_btn.setEnabled(false);
//                    gameLostPlayAgain();
//                }
//            }
//        });
//        false_btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (testQuestionHandleModel.getOption2().equals(testQuestionHandleModel.getAnswer())) {
//                    false_btn.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.green));
//                    true_btn.setEnabled(false);
//                    //Check if user has not exceeds the que limit
//                    if (qid < testQuestionHandleModelArrayList.size() - 1) {
//
//                        true_btn.setEnabled(false);
//
//                        correctDialog();
//                    } else {
//                        gameWon();
//                    }
//                } else {
//                    true_btn.setEnabled(false);
//                    gameLostPlayAgain();
//                }
//            }
//        });
//    }
//    private void typeOneQuestion() {
//        RadioGroup item = (RadioGroup) findViewById(R.id.true_false_type);
//        Log.e("itemid", String.valueOf(item));
//        RadioGroup item1 = (RadioGroup) findViewById(R.id.radiogroup_btn);
//        Log.e("itemid1", String.valueOf(item1));
//
//        try {
//            item.removeAllViews();
//            View child = getLayoutInflater().inflate(R.layout.singal_option_quiz, null);
//            item.addView(child);
//            updateQueAndOptions();
//        }catch (Exception e){
//            e.printStackTrace();
//
//        }
//        try {
//            item1.removeAllViews();
//            View child = getLayoutInflater().inflate(R.layout.singal_option_quiz, null);
//            item1.addView(child);
//            updateQueAndOptions();
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//
    }


    @Override
    protected void onRestart() {
        super.onRestart();
    }

    //When activity is destroyed then this will cancel the timer
    @Override
    protected void onStop() {
        super.onStop();
    }

    //This will pause the time
    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    //    public void updateQueAndOptions() {
//        final RadioButton option1, option2, option3, option4;
//        option1 = (RadioButton) findViewById(R.id.option1);
//        option2 = (RadioButton) findViewById(R.id.option2);
//        option3 = (RadioButton) findViewById(R.id.option3);
//        option4 = (RadioButton) findViewById(R.id.option4);
//        Log.e("type_of_ques", testQuestionHandleModel.getType());
//        try {
//            question_test.setText(testQuestionHandleModel.getQuestion());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        try {
//            option1.setText(testQuestionHandleModel.getOption1());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        try {
//            option2.setText(testQuestionHandleModel.getOption2());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        try {
//            Log.e("option3", testQuestionHandleModel.getOption3());
//            option3.setText(testQuestionHandleModel.getOption3());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        try {
//            Log.e("option4", testQuestionHandleModel.getOption4());
//            option4.setText(testQuestionHandleModel.getOption4());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        option1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (testQuestionHandleModel.getOption1().equals(testQuestionHandleModel.getAnswer())) {
//                    option1.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.green));
//                    option2.setEnabled(false);
//                    option3.setEnabled(false);
//                    option4.setEnabled(false);
//                    //Check if user has not exceeds the que limit
//                    if (qid < testQuestionHandleModelArrayList.size() - 1) {
//                        option2.setEnabled(false);
//                        option3.setEnabled(false);
//                        option4.setEnabled(false);
//                        //Show the dialog that ans is correct
//                        correctDialog();
//                    } else {
//                        gameWon();
//                    }
//                } else {
//                    option2.setEnabled(false);
//                    option3.setEnabled(false);
//                    option4.setEnabled(false);
//                    gameLostPlayAgain();
//                }
//            }
//        });
//
//        option2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (testQuestionHandleModel.getOption2().equals(testQuestionHandleModel.getAnswer())) {
//                    option2.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.green));
//                    option1.setEnabled(false);
//                    option3.setEnabled(false);
//                    option4.setEnabled(false);
//                    //Check if user has not exceeds the que limit
//                    if (qid < testQuestionHandleModelArrayList.size() - 1) {
//                        option1.setEnabled(false);
//                        option3.setEnabled(false);
//                        option4.setEnabled(false);
//                        //Show the dialog that ans is correct
//                        correctDialog();
//                    } else {
//                        gameWon();
//                    }
//                } else {
//                    option1.setEnabled(false);
//                    option3.setEnabled(false);
//                    option4.setEnabled(false);
//                    gameLostPlayAgain();
//                }
//            }
//
//        });
//        option3.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (testQuestionHandleModel.getOption3().equals(testQuestionHandleModel.getAnswer())) {
//                    option3.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.green));
//                    option1.setEnabled(false);
//                    option2.setEnabled(false);
//                    option4.setEnabled(false);
//                    //Check if user has not exceeds the que limit
//                    if (qid < testQuestionHandleModelArrayList.size() - 1) {
//                        option1.setEnabled(false);
//                        option2.setEnabled(false);
//                        option4.setEnabled(false);
//                        //Show the dialog that ans is correct
//                        correctDialog();
//                    } else {
//                        gameWon();
//                    }
//                } else {
//                    option1.setEnabled(false);
//                    option2.setEnabled(false);
//                    option4.setEnabled(false);
//                    gameLostPlayAgain();
//                }
//            }
//        });
//        option4.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (testQuestionHandleModel.getOption4().equals(testQuestionHandleModel.getAnswer())) {
//                    option4.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.green));
//                    option1.setEnabled(false);
//                    option2.setEnabled(false);
//                    option3.setEnabled(false);
//                    //Check if user has not exceeds the que limit
//                    if (qid < testQuestionHandleModelArrayList.size() - 1) {
//                        option1.setEnabled(false);
//                        option2.setEnabled(false);
//                        option3.setEnabled(false);
//                        //Show the dialog that ans is correct
//                        correctDialog();
//                    } else {
//                        gameWon();
//                    }
//                } else {
//                    option1.setEnabled(false);
//                    option2.setEnabled(false);
//                    option3.setEnabled(false);
//                    gameLostPlayAgain();
//                }
//
//            }
//        });
//
//
//    }
//    //This method will make button color white again since our one button color was turned green
    @Override
    public void finish() {
        try {
            Intent in = new Intent(PraticesActivity.this, SubjectActivity.class);
            in.putExtra("DataSubject", you_Topic);
            //  in.putExtra("LandDetail", landDetailModel);
            in.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            setResult(RESULT_OK, in);
            super.finish();
        }catch (Exception e){
            e.printStackTrace();
            Toast.makeText(this, "Please On Your Internet Connection", Toast.LENGTH_LONG).show();
            Intent in = new Intent(PraticesActivity.this, SubjectActivity.class);
            startActivity(in);
        }

    }

}
