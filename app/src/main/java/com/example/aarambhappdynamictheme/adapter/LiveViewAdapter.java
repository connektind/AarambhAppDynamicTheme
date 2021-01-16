package com.example.aarambhappdynamictheme.adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

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
import com.example.aarambhappdynamictheme.activity.DashBoardActivity;
import com.example.aarambhappdynamictheme.activity.LoginActivity;
import com.example.aarambhappdynamictheme.activity.ViewLiveClassActivity;
import com.example.aarambhappdynamictheme.model.SchoolModel;
import com.example.aarambhappdynamictheme.model.StudentDetailModel;
import com.example.aarambhappdynamictheme.model.SubjChapterTopicListModel;
import com.example.aarambhappdynamictheme.model.VideoDetails;
import com.example.aarambhappdynamictheme.model.YouTubePlayList;
import com.example.aarambhappdynamictheme.model.YouTubeTitle;
import com.example.aarambhappdynamictheme.sheardPrefrence.AarambhSharedPreference;
import com.example.aarambhappdynamictheme.sheardPrefrence.AarambhThemeSharedPrefreence;
import com.example.aarambhappdynamictheme.textGradient.CircleTransform;
import com.example.aarambhappdynamictheme.textGradient.TextColorGradient;
import com.example.aarambhappdynamictheme.util.CommonUtilities;
import com.example.aarambhappdynamictheme.util.Global;
import com.example.aarambhappdynamictheme.util.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.example.aarambhappdynamictheme.util.Global.urlProfileImg;
import static com.example.aarambhappdynamictheme.util.Global.urlStudentImg;

public class LiveViewAdapter extends RecyclerView.Adapter<LiveViewAdapter.MyViewHolder> {

    Context context;
    YouTubeTitle youTube_Details;
    ArrayList<YouTubePlayList> playListArrayList;
    StudentDetailModel studentDetailModel;
    ArrayList<SchoolModel> schoolModelSchoolList;


    public LiveViewAdapter(Context context, YouTubeTitle youTube_Details, ArrayList<YouTubePlayList> playListArrayList, StudentDetailModel studentDetailModel, ArrayList<SchoolModel> schoolModelSchoolList){
        this.context = context;
        this.youTube_Details = youTube_Details;
        this.playListArrayList = playListArrayList;
        this.studentDetailModel = studentDetailModel;
        this.schoolModelSchoolList = schoolModelSchoolList;
    }

    @NonNull
    @Override
    public LiveViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_live_inflate, parent, false);
        return new LiveViewAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        try {
            final SchoolModel schoolModelList = schoolModelSchoolList.get(position);
            //holder.txtID.setText(studentDetailModel.getStudentId());
            holder.studentname.setText(studentDetailModel.getStudentName());
            holder.txtClass.setText(AarambhSharedPreference.loadClassNameFromPreference(context));

            if (studentDetailModel.getStudentMobile().equalsIgnoreCase("null")) {
                holder.txtPhNumber.setText("");
            } else {
                holder.txtPhNumber.setText(studentDetailModel.getStudentMobile());
            }
            //Log.e("StudentImage",studentDetailModel.getStudentImage());
            //Log.e("StudentImage",schoolModelList.getSchoolLogo());
            holder.txtFatherName.setText(studentDetailModel.getParentName());

            if (studentDetailModel.getStudentImage().equalsIgnoreCase("null")){
                Glide.with(context).load(urlProfileImg)
                        .crossFade()
                        .thumbnail(0.5f)
                        .bitmapTransform(new CircleTransform(context))
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(holder.student_image);
            }else {

                Glide.with(context).load(Global.profile_pic + studentDetailModel.getStudentImage())
                        .crossFade()
                        .thumbnail(0.5f)
                        .bitmapTransform(new CircleTransform(context))
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(holder.student_image);
            }


//            Glide.with(context).load(Global.profile_pic + studentDetailModel.getStudentImage())
//                    .crossFade()
//                    .thumbnail(0.5f)
//                    .bitmapTransform(new CircleTransform(context))
//                    .diskCacheStrategy(DiskCacheStrategy.ALL)
//                    .into(holder.student_image);
            Log.e("School Logo", schoolModelList.getSchoolLogo());
            Glide.with(context).load(Global.school_pic + schoolModelList.getSchoolLogo())
                    .crossFade()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(holder.school_logo);


            holder.linearCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Log.e("classId", AarambhSharedPreference.loadClassIdFromPreference(context));
                    AarambhThemeSharedPrefreence.saveSchoolLogoPreference(context,Global.school_pic + schoolModelList.getSchoolLogo());
//                    switch (AarambhSharedPreference.loadClassIdFromPreference(context)) {
//                        case "1":
                            Intent intent = new Intent(context, DashBoardActivity.class);
                            intent.putExtra("SchoolDetail", schoolModelList);
//                            Bundle args = new Bundle();
//                            args.putSerializable("ARRAYLIST",(Serializable)schoolModelSchoolList);
//                            intent.putExtra("BUNDLE6th",args);

                            context.startActivity(intent);
//                            break;
//                        case "2":
//                            Intent intent1 = new Intent(context, DashBoardActivity.class);
//                            intent1.putExtra("7thDetails", youTube_Details);
//                            intent1.putExtra("PlayListDetails", playListArrayList);
//                            intent1.putExtra("SchoolDetail7th", schoolModelList);
////                            Bundle args1 = new Bundle();
////                            args1.putSerializable("ARRAYLIST",(Serializable)schoolModelSchoolList);
////                            intent1.putExtra("BUNDLE7th",args1);
//                            context.startActivity(intent1);
//                            break;
//                        case "3":
//                            Intent intent2 = new Intent(context, DashBoardActivity.class);
//                            intent2.putExtra("8thDetails", youTube_Details);
//                            intent2.putExtra("PlayListDetails", playListArrayList);
//                            intent2.putExtra("SchoolDetail8th", schoolModelList);
////                            Bundle args2 = new Bundle();
////                            args2.putSerializable("ARRAYLIST",(Serializable)schoolModelSchoolList);
////                            intent2.putExtra("BUNDLE8th",args2);
//                            context.startActivity(intent2);
//                            break;
//                        default:
//                            Intent intent3 = new Intent(context, DashBoardActivity.class);
//                            intent3.putExtra("6thDetails", youTube_Details);
//                            intent3.putExtra("PlayListDetails", playListArrayList);
//                            intent3.putExtra("SchoolDetail6th", schoolModelList);
////                            Bundle args3 = new Bundle();
////                            args3.putSerializable("ARRAYLIST",(Serializable)schoolModelSchoolList);
////                            intent3.putExtra("BUNDLE8th",args3);
//                            context.startActivity(intent3);
//                            break;
//                    }


                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        Log.e("Size", schoolModelSchoolList.size()+"");
        return schoolModelSchoolList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView student_image, school_logo;
        TextView txtLive, studentname,txtID,txtClass,txtbloodGroup,txtFatherName,txtPhNumber,txtMotherName;
        LinearLayout linearCard;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            student_image = itemView.findViewById(R.id.student_image);
            school_logo = itemView.findViewById(R.id.school_logo);
            txtLive = itemView.findViewById(R.id.txtLive);
            studentname = itemView.findViewById(R.id.studentname);
            //txtID = itemView.findViewById(R.id.txtID);
            txtClass = itemView.findViewById(R.id.txtClass);
            //txtbloodGroup = itemView.findViewById(R.id.txtbloodGroup);
            txtFatherName = itemView.findViewById(R.id.txtFatherName);
            txtPhNumber = itemView.findViewById(R.id.txtPhNumber);
            //txtMotherName = itemView.findViewById(R.id.txtMotherName);
            linearCard = itemView.findViewById(R.id.linearCard);
            String base_color_one=AarambhThemeSharedPrefreence.loadBaseColorOneFromPreference(context);
            String base_color_two=AarambhThemeSharedPrefreence.loadBaseColorTwoFromPreference(context);
            TextColorGradient textColorGradient = new TextColorGradient();
            textColorGradient.getColorTextGradient(studentname, base_color_one, base_color_two);
        }
    }

//    public void callGetInformationApi()
//    {
//        if (!CommonUtilities.isOnline(context)) {
//            Toast.makeText(context, "Please On Your Internet Connection", Toast.LENGTH_LONG).show();
//            return;
//        }
////        progressdialog = new ProgressDialog(this);
////        progressdialog.setCancelable(false);
////        progressdialog.setMessage("Loading...");
////        progressdialog.setTitle("Wait...");
////        progressdialog.show();
//        final String string_json = "Result";
//        String url = Global.WEBBASE_URL + "getInformation";
//        StringRequest stringRequest=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                String res = response.toString();
//                parseResponseGetInformation(res);
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                //progressDialog.dismiss();
//                NetworkResponse response = error.networkResponse;
//                Log.e("com.Aarambh", "error response " + response);
//                if (error instanceof TimeoutError || error instanceof NoConnectionError) {
//                    Log.e("mls", "VolleyError TimeoutError error or NoConnectionError");
//                } else if (error instanceof AuthFailureError) {                    //TODO
//                    Log.e("mls", "VolleyError AuthFailureError");
//                } else if (error instanceof ServerError) {
//                    Log.e("mls", "VolleyError ServerError");
//                } else if (error instanceof NetworkError) {
//                    Log.e("mls", "VolleyError NetworkError");
//                } else if (error instanceof ParseError || error instanceof VolleyError) {
//                    Log.e("mls", "VolleyError TParseError");
//                    Log.e("Volley Error", error.toString());
//                }
//                if (error instanceof ServerError && response != null) {
//                    try {
//                        String res = new String(response.data, HttpHeaderParser.parseCharset(response.headers, "utf-8"));
//                        // Now you can use any deserializer to make sense of data
//                        // progressDialog.show();
//                        parseResponseGetInformation(response.toString());
//                    } catch (UnsupportedEncodingException e1) {
//                        // Couldn't properly decode data to string
//                        e1.printStackTrace();
//                    }
//                }
//
//            }
//        }){
//
//            @Override
//            protected VolleyError parseNetworkError(VolleyError volleyError) {
//                String json;
//                if (volleyError.networkResponse != null && volleyError.networkResponse.data != null) {
//                    try {
//                        json = new String(volleyError.networkResponse.data,
//                                HttpHeaderParser.parseCharset(volleyError.networkResponse.headers));
//                    } catch (UnsupportedEncodingException e) {
//                        return new VolleyError(e.getMessage());
//                    }
//                    return new VolleyError(json);
//                }
//                return volleyError;
//            }
//            @Override
//            public Map<String, String> getHeaders() throws AuthFailureError {
//                HashMap<String, String> headers = new HashMap<String, String>();
//                headers.put("Content-Type", "application/json; charset=utf-8");
//                headers.put("Authorization", "Bearer "+ AarambhSharedPreference.loadUserTokenFromPreference(ViewLiveClassActivity.this));
//                return headers;
//            }
//        };
//        VolleySingleton.getInstance(context).addToRequestQueue(stringRequest, string_json);
//
//
//
//    }
//
//    public void parseResponseGetInformation(String response)
//    {
//        Log.e("Response", response);
//        try {
//
//            JSONObject jsonObject1 = new JSONObject(response);
//            String status=jsonObject1.getString("status");
//            boolean success = jsonObject1.getBoolean("success");
//            if(success==false){
//                String error=jsonObject1.getString("error");
//                Toast.makeText(this,error+"",Toast.LENGTH_LONG).show();
//            }
//            else if (success==true) {
//                String message = jsonObject1.getString("message");
//                if (message.equalsIgnoreCase("No Data Found.")) {
//                    Toast.makeText(this, message + "", Toast.LENGTH_LONG).show();
//                } else if (message.equalsIgnoreCase("Data Found")) {
//
//                    JSONObject jsonObjectData = new JSONObject("data");
//                    JSONArray studentArray = jsonObjectData.getJSONArray("student");
//
//                    for (int i = 0; i < studentArray.length(); i++) {
//                        Log.e("class_lenth", String.valueOf(i));
//                        JSONObject jsonObject = studentArray.getJSONObject(i);
//                        String StudentId = jsonObject.getString("StudentId");
//                        String StudentName = jsonObject.getString("StudentName");
//                        String StudentGender = jsonObject.getString("StudentGender");
//                        String StudentMobile = jsonObject.getString("StudentMobile");
//                        String StudentUsername = jsonObject.getString("StudentUsername");
//                        String StudentDOB = jsonObject.getString("StudentDOB");
//                        String StudentDORegis = jsonObject.getString("StudentDORegis");
//                        String StudentPassword = jsonObject.getString("StudentPassword");
//                        String StudentImage = jsonObject.getString("StudentImage");
//                        String ParentId = jsonObject.getString("ParentId");
//                        String ClassId = jsonObject.getString("ClassId");
//                        String StatusId = jsonObject.getString("StatusId");
//                        String CreatedById = jsonObject.getString("CreatedById");
//                        String ModifiedById = jsonObject.getString("ModifiedById");
//                        String CreationDate = jsonObject.getString("CreationDate");
//                        String ModificationDate = jsonObject.getString("ModificationDate");
//                        studentDetailModel = new StudentDetailModel(StudentId, StudentName, StudentGender, StudentMobile, StudentUsername, StudentDOB, StudentDORegis, StudentPassword, StudentImage, ParentId, ClassId, StatusId, CreatedById, ModifiedById, CreationDate, ModificationDate);
//                    }
//
//                    JSONArray schoolArray = jsonObjectData.getJSONArray("school");
//
//                    for (int i = 0; i < schoolArray.length(); i++) {
//                        JSONObject school = schoolArray.getJSONObject(i);
//                        String SchoolId = school.getString("SchoolId");
//                        String SchoolName = school.getString("SchoolName");
//                        String SchoolAddress = school.getString("SchoolAddress");
//                        String SchoolPhone = school.getString("SchoolPhone");
//                        String SchoolBoard = school.getString("SchoolBoard");
//                        String SchoolMail = school.getString("SchoolMail");
//                        String SchoolLogo = school.getString("SchoolLogo");
//                        String YoutubeChannelId = school.getString("YoutubeChannelId");
//                        String YoutubeChannelKey = school.getString("YoutubeChannelKey");
//                        String IncludeAarambh = school.getString("IncludeAarambh");
//                        String Username = school.getString("Username");
//                        String Password = school.getString("Password");
//                        String FirebaseKey = school.getString("FirebaseKey");
//                        String FirebaseSecretKey = school.getString("FirebaseSecretKey");
//
//                        schoolModelSchool = new SchoolModel(SchoolId, SchoolName, SchoolAddress, SchoolPhone,
//                                SchoolBoard, SchoolMail, SchoolLogo, YoutubeChannelId, YoutubeChannelKey,
//                                IncludeAarambh, Username, Password, FirebaseKey, FirebaseSecretKey);
//
//                    }
//
//                    JSONArray aarambhArray = jsonObjectData.getJSONArray("aarambh");
//
//                    for (int i = 0; i < aarambhArray.length(); i++) {
//                        JSONObject school = aarambhArray.getJSONObject(i);
//                        String SchoolId = school.getString("SchoolId");
//                        String SchoolName = school.getString("SchoolName");
//                        String SchoolAddress = school.getString("SchoolAddress");
//                        String SchoolPhone = school.getString("SchoolPhone");
//                        String SchoolBoard = school.getString("SchoolBoard");
//                        String SchoolMail = school.getString("SchoolMail");
//                        String SchoolLogo = school.getString("SchoolLogo");
//                        String YoutubeChannelId = school.getString("YoutubeChannelId");
//                        String YoutubeChannelKey = school.getString("YoutubeChannelKey");
//                        String IncludeAarambh = school.getString("IncludeAarambh");
//                        String Username = school.getString("Username");
//                        String Password = school.getString("Password");
//                        String FirebaseKey = school.getString("FirebaseKey");
//                        String FirebaseSecretKey = school.getString("FirebaseSecretKey");
//
//                        schoolModelAarambh = new SchoolModel(SchoolId, SchoolName, SchoolAddress, SchoolPhone,
//                                SchoolBoard, SchoolMail, SchoolLogo, YoutubeChannelId, YoutubeChannelKey,
//                                IncludeAarambh, Username, Password, FirebaseKey, FirebaseSecretKey);
//
//                    }
//
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//    }


}
