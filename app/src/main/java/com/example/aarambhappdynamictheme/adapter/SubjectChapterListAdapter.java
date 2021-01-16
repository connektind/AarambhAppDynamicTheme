package com.example.aarambhappdynamictheme.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;

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

import com.example.aarambhappdynamictheme.R;
import com.example.aarambhappdynamictheme.activity.SubjectActivity;
import com.example.aarambhappdynamictheme.activity.VideoListActivity;
import com.example.aarambhappdynamictheme.model.SubjChapterTopicListModel;
import com.example.aarambhappdynamictheme.model.SubjectChapterListModel;
import com.example.aarambhappdynamictheme.model.TopicModel;
import com.example.aarambhappdynamictheme.model.VideoDetails;
import com.example.aarambhappdynamictheme.model.YouTubePlayList;
import com.example.aarambhappdynamictheme.model.YouTubeTitle;
import com.example.aarambhappdynamictheme.sheardPrefrence.AarambhSharedPreference;
import com.example.aarambhappdynamictheme.sheardPrefrence.AarambhThemeSharedPrefreence;
import com.example.aarambhappdynamictheme.textGradient.TextColorGradient;
import com.example.aarambhappdynamictheme.util.CommonUtilities;
import com.example.aarambhappdynamictheme.util.Global;
import com.example.aarambhappdynamictheme.util.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SubjectChapterListAdapter extends RecyclerView.Adapter<SubjectChapterListAdapter.MyViewHolder> {
    Context context;
    // ArrayList<SubjectChapterListModel> subjectChapterListModelArrayList;
    String class_id;
    //    ArrayList<String> sixth_Chapter;
//    ArrayList<String> topicName;
//    YouTubeTitle chap;
//    ArrayList<VideoDetails> videoDetailsArrayList;
//    ArrayList<YouTubePlayList> youTubePlayListArrayList;
    ArrayList<TopicModel> topicModelArrayList;

    public SubjectChapterListAdapter(Context context, ArrayList<TopicModel> topicModelArrayList) {
        this.context = context;
        this.topicModelArrayList = topicModelArrayList;

    }

//
//    public SubjectChapterListAdapter(Context context, ArrayList<SubjectChapterListModel> subjectChapterListModelArrayList) {
//        this.context = context;
//        this.subjectChapterListModelArrayList = subjectChapterListModelArrayList;
//        class_id = AarambhSharedPreference.loadClassIdFromPreference(context);
//    }

//    public SubjectChapterListAdapter(Context context, ArrayList<String> sixth_Chapter,  ArrayList<VideoDetails> videoDetailsArrayList) {
//        this.context = context;
//        this.sixth_Chapter = sixth_Chapter;
//        this.videoDetailsArrayList = videoDetailsArrayList;
//        class_id = AarambhSharedPreference.loadClassIdFromPreference(context);
//        chap = new YouTubeTitle();
//    }

//    public SubjectChapterListAdapter(Context context, ArrayList<String> sixth_Chapter, ArrayList<YouTubePlayList> youTubePlayListArrayList) {
//        this.context = context;
//        this.sixth_Chapter = sixth_Chapter;
//        this.youTubePlayListArrayList = youTubePlayListArrayList;
//        class_id = AarambhSharedPreference.loadClassIdFromPreference(context);
//        chap = new YouTubeTitle();
//    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chapter_list, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        holder.chapter_name.setText(topicModelArrayList.get(position).getTopicName());
        holder.view_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AarambhSharedPreference.saveTopicIdToPreference(context,topicModelArrayList.get(position).getTopicId());
                AarambhSharedPreference.saveTopicNameToPreference(context,topicModelArrayList.get(position).getTopicName());
                Intent intent = new Intent(context, VideoListActivity.class);
                context.startActivity(intent);
            }
        });
//        Log.e("chapter_name", sixth_Chapter.get(position));
//        final String chapter = sixth_Chapter.get(position);
//        holder.chapter_name.setText(chapter);
//        // holder.getTopicApicalling(subjectChapterListModelArrayList.get(position).getTopicId());
//        //  holder.getYoutubeDatacall();
//        holder.view_all.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Log.e("classIdnnnnnn", class_id);
//                switch (class_id) {
//                    case "1":
//                        Log.e("6thPosition", position + "");
//                        String getChapter = sixth_Chapter.get(position);
//                        //VideoDetails video = videoDetailsArrayList.get(position);
//                        //ArrayList<String> topicName = getTopicNamesBySubject(getChapter,videoDetailsArrayList);
//                        String playListId = getPlayListIdAccordingtoChapter(youTubePlayListArrayList, getChapter);
//                        Log.e("Adapter_PlayListId", playListId);
//                        chap.setTopic(topicName);
//                        AarambhSharedPreference.saveTopicNameToPreference(context, chapter);
//                        Intent intent = new Intent(context, VideoListActivity.class);
//                        intent.putExtra("6th_Details", playListId);
//                        //intent.putExtra("VDAR",videoDetailsArrayList);
//                        ((Activity) context).startActivityForResult(intent, 201);
//                        break;
//                    case "2":
//                        try {
//                            Log.e("7thPosition", position + "");
//                            String getChapter1 = sixth_Chapter.get(position);
//                            //VideoDetails video = videoDetailsArrayList.get(position);
//                            //ArrayList<String> topicName = getTopicNamesBySubject(getChapter,videoDetailsArrayList);
//                            String playListId1 = getPlayListIdAccordingtoChapter(youTubePlayListArrayList, getChapter1);
//                            Log.e("Adapter_PlayListId", playListId1);
//                            chap.setTopic(topicName);
//                            AarambhSharedPreference.saveTopicNameToPreference(context, chapter);
//                            Intent intent1 = new Intent(context, VideoListActivity.class);
//                            intent1.putExtra("7th_Details", playListId1);
//                            // intent1.putExtra("VDAR", videoDetailsArrayList);
//                            ((Activity) context).startActivityForResult(intent1, 202);
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                        break;
//                    case "3":
//                        try {
//                            Log.e("8thPosition", position + "");
//                            String getChapter2 = sixth_Chapter.get(position);
//                            //VideoDetails video = videoDetailsArrayList.get(position);
//                            //ArrayList<String> topicName = getTopicNamesBySubject(getChapter,videoDetailsArrayList);
//                            String playListId2 = getPlayListIdAccordingtoChapter(youTubePlayListArrayList, getChapter2);
//                            Log.e("Adapter_PlayListId", playListId2);
//                            chap.setTopic(topicName);
//                            AarambhSharedPreference.saveTopicNameToPreference(context, chapter);
//                            Intent intent2 = new Intent(context, VideoListActivity.class);
//                            intent2.putExtra("8th_Details", playListId2);
//                            //intent2.putExtra("VDAR", videoDetailsArrayList);
//                            ((Activity) context).startActivityForResult(intent2, 203);
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                        break;
////                    default:
////                        String getChapter3 = sixth_Chapter.get(position);
////                        VideoDetails video3 = videoDetailsArrayList.get(position);
////                        ArrayList<String> topicName3 = getTopicNamesBySubject(getChapter3,videoDetailsArrayList);
////                        chap.setTopic(topicName3);
////                        Intent intent3 = new Intent(context, RamaThemeTopicVideoActivity.class);
////                        intent3.putExtra("7th_Details", chap);
////                        intent3.putExtra("VDAR",video3);
////                        context.startActivity(intent3);
////                        break;
//                }
        //    }
        //  });
    }

    @Override
    public int getItemCount() {
        //return subjectChapterListModelArrayList.size();
        return topicModelArrayList.size();

    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView chapter_name, view_all;
        RecyclerView topic_list_recyclerview;
        SubjChapterTopicListAdapter subjChapterTopicListAdapter;
        ArrayList<SubjChapterTopicListModel> subjChapterTopicListModelArrayList;
        SubjChapterTopicListModel subjChapterTopicListModel;
        ArrayList<VideoDetails> videoDetailsArrayList;
        TextColorGradient textColorGradient = new TextColorGradient();
        String URL = "https://www.googleapis.com/youtube/v3/search?part=snippet&channelId=UCAfot-yupAQvVqAuZw-Pctg&maxResults=25&key=AIzaSyAja-68gRGjnbSCsR5U1IuMKpUUO8rABmo";
        String base_color_one, base_color_two;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            chapter_name = itemView.findViewById(R.id.chapter_name);
            subjChapterTopicListModelArrayList = new ArrayList<>();
            videoDetailsArrayList = new ArrayList<>();
            topic_list_recyclerview = itemView.findViewById(R.id.topic_list_recylerview);
            view_all = itemView.findViewById(R.id.view_all_subject);
            base_color_one = AarambhThemeSharedPrefreence.loadBaseColorOneFromPreference(context);
            base_color_two = AarambhThemeSharedPrefreence.loadBaseColorTwoFromPreference(context);
            textColorGradient.getColorTextGradient(view_all, base_color_one, base_color_two);
        }
    }
}

//        private void getYoutubeDatacall() {
//            RequestQueue requestQueue = Volley.newRequestQueue(context);
//            StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
//                @Override
//                public void onResponse(String response) {
//                    try {
//                        Log.e("response_youtube", response);
//                        JSONObject jsonObject = new JSONObject(response);
//                        JSONArray jsonArray = jsonObject.getJSONArray("items");
//                        for (int i = 6; i < 10; i++) {
//                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
//                            JSONObject jsonVideoId = jsonObject1.getJSONObject("id");
//                            //      Log.e("jsonVideoId", String.valueOf(jsonVideoId));
//                            JSONObject jsonsnippet = jsonObject1.getJSONObject("snippet");
//                            //    Log.e("jsonsnippet", String.valueOf(jsonsnippet));
//                            JSONObject jsonObjectdefault = jsonsnippet.getJSONObject("thumbnails").getJSONObject("medium");
//                            //   Log.e("jsonObjectdefault", String.valueOf(jsonObjectdefault));
//                            VideoDetails videoDetails = new VideoDetails();
//                            String id_kind = jsonVideoId.getString("kind");
//                            if (id_kind.equalsIgnoreCase("youtube#playlist")) {
////                            videoDetails.setVideoId(jsonVideoId.getString("playlistId"));
////                            videoDetails.setURL(jsonObjectdefault.getString("url"));
////                            videoDetails.setVideoName(jsonsnippet.getString("title"));
////                            String title = jsonsnippet.getString("title");
//                                Log.e("playlist_id", "null_added");
//                            } else if (id_kind.equalsIgnoreCase("youtube#video")) {
//                                videoDetails.setVideoId(jsonVideoId.getString("videoId"));
//                                videoDetails.setURL(jsonObjectdefault.getString("url"));
//                                videoDetails.setVideoName(jsonsnippet.getString("title"));
//                                Log.e("videoDetails.getVideoName()", videoDetails.getVideoName());
//                                // video_title = jsonsnippet.getString("title");
//                            }
//
//
////                            try {
////                                String videoid=jsonVideoId.getString("videoId");
////                                Log.e("newVideoUrl"," New Video Id" +videoid);
////                                videoDetails.setVideoId(jsonVideoId.getString("videoId"));
////                                Log.e("check_video_id",videoDetails.getVideoId());
////                            }catch (Exception e){
////                                e.printStackTrace();
////                            }
////                            videoDetails.setURL(jsonObjectdefault.getString("url"));
////                            Log.e("videoDetails.getURL()",videoDetails.getURL());
////                            //subjChapterTopicListModel.setChapterURLS(jsonObjectdefault.getString("url"));
////                            videoDetails.setVideoName(jsonsnippet.getString("title"));
//                            //   Log.e("videoDetails.getVideoName()",videoDetails.getVideoName());
//                            // subjChapterTopicListModel.setChapterName(videoDetails.getVideoName());
//
//                            String videoname = videoDetails.getVideoName();
//                            try {
//                                String[] parts = videoname.split(" "); // escape .
//                                String part1 = parts[0];
//                                Log.e("part1", part1);
//                                String part2 = parts[1];
//                                Log.e("part2", part2);
//                                String part3 = parts[2];
//                                Log.e("part3", part3);
//                                String part4 = parts[4];
//                                Log.e("part4", part4);
//                            } catch (Exception e) {
//                                e.printStackTrace();
//                            }
//
//                            //6th_Maths_Chapter 6_ Part 1 Introduction to Integers
//                            videoDetails.setVideoDesc(jsonsnippet.getString("description"));
//                            Log.e("videoDetails.getVideoDesc()", videoDetails.getVideoDesc());
//
//                            //subjectNameModel.setSubj_back_bg(R.drawable.subj_back_bg);
//                            videoDetailsArrayList.add(videoDetails);
////                            subjChapterTopicListModel.setBlack_transparent_back(R.drawable.black_transparent_back);
////                            subjChapterTopicListModelArrayList.add(subjChapterTopicListModel);
//                        }
//                        subjChapterTopicListAdapter = new SubjChapterTopicListAdapter(context, videoDetailsArrayList);
//                        topic_list_recyclerview.setAdapter(subjChapterTopicListAdapter);
//                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, RecyclerView.HORIZONTAL, false);
//                        topic_list_recyclerview.setLayoutManager(linearLayoutManager);
////                    lvVideo.setAdapter(customListAdapter);
////                    customListAdapter.notifyDataSetChanged();
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }, new Response.ErrorListener() {
//                @Override
//                public void onErrorResponse(VolleyError error) {
//                    error.printStackTrace();
//                }
//            });
//            int socketTimeout = 30000;
//            RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
//            stringRequest.setRetryPolicy(policy);
//            requestQueue.add(stringRequest);
//        }


//        private void getTopicApicalling(final String topic_id) {
//            {
//                if (!CommonUtilities.isOnline(context)) {
//                    Toast.makeText(context, "Please On Your Internet Connection", Toast.LENGTH_LONG).show();
//                    return;
//                }
//                Log.e("topic_id", topic_id);
//                String url = Global.WEBBASE_URL + "getChapterByTopicId?TopicId=" + topic_id;
//                Log.e("url", url);
//                final String string_json = "Result";
//                Log.e("string_json", string_json);
//                try {
//                    StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
//                        @Override
//                        public void onResponse(String response) {
//                            String res = response.toString();
//                            parseResponseTopicApi(res);
//                            Log.e("res", res);
//                        }
//                    }, new Response.ErrorListener() {
//                        @Override
//                        public void onErrorResponse(VolleyError error) {
//                            //progressDialog.dismiss();
//                            NetworkResponse response = error.networkResponse;
//
//                            Log.e("com.Aarambh", "error response " + response);
//
//                            if (error instanceof TimeoutError || error instanceof NoConnectionError) {
//                                Log.e("mls", "VolleyError TimeoutError error or NoConnectionError");
//                            } else if (error instanceof AuthFailureError) {                    //TODO
//                                Log.e("mls", "VolleyError AuthFailureError");
//                            } else if (error instanceof ServerError) {
//                                Log.e("mls", "VolleyError ServerError");
//                            } else if (error instanceof NetworkError) {
//                                Log.e("mls", "VolleyError NetworkError");
//                            } else if (error instanceof ParseError || error instanceof VolleyError) {
//                                Log.e("mls", "VolleyError TParseError");
//                                Log.e("Volley Error", error.toString());
//                            }
//                            if (error instanceof ServerError && response != null) {
//                                try {
//                                    String res = new String(response.data, HttpHeaderParser.parseCharset(response.headers, "utf-8"));
//                                    // Now you can use any deserializer to make sense of data
//                                    // progressDialog.show();
//                                    parseResponseTopicApi(response.toString());
//
//                                } catch (UnsupportedEncodingException e1) {
//                                    // Couldn't properly decode data to string
//                                    e1.printStackTrace();
//                                }
//                            }
//                        }
//                    }) {
//                        @Override
//                        public Map<String, String> getParams() throws AuthFailureError {
//                            HashMap<String, String> params = new HashMap<String, String>();
//                            params.put("TopicId", topic_id);
//                            return params;
//                        }
//
//                        @Override
//                        protected VolleyError parseNetworkError(VolleyError volleyError) {
//                            String json;
//                            if (volleyError.networkResponse != null && volleyError.networkResponse.data != null) {
//                                try {
//                                    json = new String(volleyError.networkResponse.data,
//                                            HttpHeaderParser.parseCharset(volleyError.networkResponse.headers));
//                                } catch (UnsupportedEncodingException e) {
//                                    return new VolleyError(e.getMessage());
//                                }
//                                return new VolleyError(json);
//                            }
//                            return volleyError;
//                        }
//
//
//                        @Override
//                        public Map<String, String> getHeaders() throws AuthFailureError {
//                            HashMap<String, String> headers = new HashMap<String, String>();
//                            headers.put("Content-Type", "application/json; charset=utf-8");
//                            headers.put("Authorization", "Bearer " + AarambhSharedPreference.loadUserTokenFromPreference(context));
//                            return headers;
//                        }
//                    };
//                    VolleySingleton.getInstance(context).addToRequestQueue(stringRequest, string_json);
//
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//
//        private void parseResponseTopicApi(String response) {
//            Log.e("topic_res", response);
//            try {
//                JSONArray jsonArray = new JSONArray(response);
//                //boolean result = obj.getBoolean("Result");
//                // boolean message_Error = obj.getBoolean("Error");
////            if (message_Error == false) {
//                // JSONObject object = jsonArray.getJSONObject();
//                for (int i = 0; i < jsonArray.length(); i++) {
//                    Log.e("topic_length", String.valueOf(i));
//                    JSONObject jsonObject = jsonArray.getJSONObject(i);
//                    String ChapterId = jsonObject.getString("ChapterId");
//                    String ChapterName = jsonObject.getString("ChapterName");
//                    Log.e("ChapterName", ChapterName);
//                    String ChapterURLS = jsonObject.getString("ChapterURLS");
//                    Log.e("Video_url", ChapterURLS);
//                    String CourseId = jsonObject.getString("CourseId");
//                    String TopicId = jsonObject.getString("TopicId");
//                    String ClassId = jsonObject.getString("ClassId");
//                    String StatusId = jsonObject.getString("StatusId");
//                    String CreatedById = jsonObject.getString("CreatedById");
//                    String ModifiedById = jsonObject.getString("ModifiedById");
//                    String CreationDate = jsonObject.getString("CreationDate");
//                    String ModificationDate = jsonObject.getString("ModificationDate");
//                    SubjChapterTopicListModel subjChapterTopicListModel;
//                    subjChapterTopicListModel = new SubjChapterTopicListModel(ChapterId, ChapterName, ChapterURLS, CourseId, TopicId, ClassId, StatusId, CreatedById, ModifiedById, CreationDate, ModificationDate, R.drawable.black_transparent_back);
//                    subjChapterTopicListModelArrayList.add(subjChapterTopicListModel);
//                    Log.e("---data", String.valueOf(subjChapterTopicListModelArrayList));
//                }
//                // subjChapterTopicListAdapter = new SubjChapterTopicListAdapter(context, subjChapterTopicListModelArrayList);
//                //topic_list_recyclerview.setAdapter(subjChapterTopicListAdapter);
//                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, RecyclerView.HORIZONTAL, false);
//                topic_list_recyclerview.setLayoutManager(linearLayoutManager);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//    public ArrayList<String> getTopicNamesBySubject(String chapterName, ArrayList<VideoDetails> titleNames) {
//        topicName = new ArrayList<>();
//        for (int i = 0; i < titleNames.size(); i++) {
//            String getVideo = titleNames.get(i).getVideoName();
//            String title[] = getVideo.split("_");
//            String className = title[0];
//            Log.e("className", className);
//
//            Log.e("WhatChapterName", chapterName);
//            Log.e("SubjectchapterName", chapterName);
//            String class_id = AarambhSharedPreference.loadClassIdFromPreference(context);
//
//            if (className.equalsIgnoreCase("6th") && class_id.equalsIgnoreCase("1")) {
//                // 6th_Maths_Chapter 6_ Part 1 Introduction to Integers
//                String subject1 = title[1];
//                String chapter = title[2];
//                String topic[] = title[3].split("_ ");
//                String part = topic[0];
//                //String no = topic[1];
//                //String new_topic = topic[2];
//                String new_topic = part;
//                Log.e("New Topic", new_topic);
//
//
//                //String chapterNamePart = chapter + " " +part ;
//                if (chapter.equalsIgnoreCase(chapterName)) {
//                    Log.e("SubjectChapterAdapter", subject1 + " : " + chapter + " : " + topic);
//                    if (new_topic.contains(".")) {
//                        topicName.add(new_topic);
//                    } else {
//                        topicName.add(new_topic);
//                    }
//                }
//                Log.e("TopicName", topicName.toString());
//            } else if (className.equalsIgnoreCase("7th") && class_id.equalsIgnoreCase("2")) {
//                String subject1 = title[1];
//                String chapter = title[2];
//                String topic[] = title[3].split("_ ");
//                String part = topic[0];
//                //String no = topic[1];
//                //String new_topic = topic[2];
//                String new_topic = part;
//                Log.e("New Topic", new_topic);
//                //String chapterNamePart = chapter + " " +part ;
//                if (chapter.equalsIgnoreCase(chapterName)) {
//                    Log.e("SubjectChapterAdapter", subject1 + " : " + chapter + " : " + topic);
//                    if (new_topic.contains(".")) {
//                        topicName.add(new_topic);
//                    } else {
//                        topicName.add(new_topic);
//                    }
//                }
//                Log.e("TopicName", topicName.toString());
//            } else if (className.equalsIgnoreCase("8th") && class_id.equalsIgnoreCase("3")) {
//                String subject1 = title[1];
//                String chapter = title[2];
//                String topic[] = title[3].split("_ ");
//                String part = topic[0];
//                //String no = topic[1];
//                //String new_topic = topic[2];
//                String new_topic = part;
//                Log.e("New Topic", new_topic);
//                //String chapterNamePart = chapter + " " +part ;
//                if (chapter.equalsIgnoreCase(chapterName)) {
//                    Log.e("SubjectChapterAdapter", subject1 + " : " + chapter + " : " + topic);
//                    if (new_topic.contains(".")) {
//                        topicName.add(new_topic);
//                    } else {
//                        topicName.add(new_topic);
//                    }
//                }
//                Log.e("TopicName", topicName.toString());
//            }
//        }
//        return topicName;
//    }
//
//    public String getPlayListIdAccordingtoChapter(ArrayList<YouTubePlayList> playLists, String chapterName) {
//        try {
//            String className = "", playListId = "";
//            for (int x = 0; x < playLists.size(); x++) {
//                YouTubePlayList vd = playLists.get(x);
//                Log.e("X:- ", vd.getPlayListTitle());
//                String video_title = vd.getPlayListTitle();
//                String title_arr[] = video_title.split("_");
//                Log.e("Title Arr:- ", title_arr.length + "");
//                className = title_arr[0];
//                Log.e("className", className);
//                Log.e("Chapterin PL Title", vd.getPlayListTitle());
//                String class_id = AarambhSharedPreference.loadClassIdFromPreference(context);
//                if (className.equalsIgnoreCase("6th") && class_id.equalsIgnoreCase("1")) {
//                    String subject = title_arr[1].replace(" ", "");
//                    String chapter = title_arr[2];
//                    String name = title_arr[3];
//                    String title = chapter + " " + name;//Chapter 2 Component of Food
//
//                    if (title.equalsIgnoreCase(chapterName)) {
//                        playListId = vd.getPlayListId();
//                        Log.e("PL_ID", playListId);
//                    }
//
//                } else if (className.equalsIgnoreCase("7th") && class_id.equalsIgnoreCase("2")) {
//                    String subject = title_arr[1].replace(" ", "");
//                    String chapter = title_arr[2];
//                    String name = title_arr[3];
//                    String title = chapter + " "+name ;//Chapter 2 Component of Food
//
//                    if (title.equalsIgnoreCase(chapterName)) {
//                        playListId = vd.getPlayListId();
//                        Log.e("PL_ID", playListId);
//                    }
//
//                } else if (className.equalsIgnoreCase("8th") && class_id.equalsIgnoreCase("3")) {
//                    String subject = title_arr[1].replace(" ", "");
//                    String chapter = title_arr[2];
//                    String name = title_arr[3];
//                    String title = chapter + " " + name;//Chapter 2 Component of Food
//
//                    if (title.equalsIgnoreCase(chapterName)) {
//                        playListId = vd.getPlayListId();
//                        Log.e("PL_ID", playListId);
//                    }
//
//                }
//
//            }
//            return playListId;
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//

//}
