package com.example.aarambhappdynamictheme.adapter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;


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
import com.example.aarambhappdynamictheme.activity.SubjectActivity;
import com.example.aarambhappdynamictheme.model.ChapterModel;
import com.example.aarambhappdynamictheme.model.CourseModel;
import com.example.aarambhappdynamictheme.model.TopicModel;
import com.example.aarambhappdynamictheme.model.VideoDetails;
import com.example.aarambhappdynamictheme.model.YouTubePlayList;
import com.example.aarambhappdynamictheme.model.YouTubeTitle;
import com.example.aarambhappdynamictheme.sheardPrefrence.AarambhSharedPreference;
import com.example.aarambhappdynamictheme.sheardPrefrence.AarambhThemeSharedPrefreence;
import com.example.aarambhappdynamictheme.textGradient.CircleTransform;
import com.example.aarambhappdynamictheme.util.CommonUtilities;
import com.example.aarambhappdynamictheme.util.Global;
import com.example.aarambhappdynamictheme.util.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Map;

import static com.example.aarambhappdynamictheme.util.Global.urlProfileImg;

public class SubjectNameAdapter extends RecyclerView.Adapter<SubjectNameAdapter.MyViewHolder> {
    Context context;
    //ArrayList<SubjectNameModel> subjectNameModelArrayList;
    ArrayList<String> subjectNameModelArrayList;
    ArrayList<String> chapterName, videoName,filteredPlayListChapter;
    String class_postion;
    ArrayList<VideoDetails> videoDetails, filteredVideoDetails;
    YouTubeTitle chap;
    ArrayList<YouTubePlayList> playListArrayList;
    ArrayList<CourseModel> courseModelArrayList;

//    public SubjectNameAdapter(Context context, ArrayList<String> subjectNameModelArrayList, ArrayList<YouTubePlayList> playListArrayList) {
//        this.context = context;
//        this.subjectNameModelArrayList = subjectNameModelArrayList;
//        this.playListArrayList = playListArrayList;
//        this.class_postion = AarambhSharedPreference.loadClassIdFromPreference(context);
//        this.chap = new YouTubeTitle();
//        //chap.setChapter(chapterName);
//    }

    public SubjectNameAdapter(Context context, ArrayList<CourseModel> courseModelArrayList){
        this.context = context;
        this.courseModelArrayList = courseModelArrayList;
    }

//    public SubjectNameAdapter(Context context, ArrayList<String> subjectNameModelArrayList, ArrayList<String> videoName, ArrayList<VideoDetails> videoDetails) {
//        this.context = context;
//        this.subjectNameModelArrayList = subjectNameModelArrayList;
//        this.videoName = videoName;
//        this.videoDetails = videoDetails;
//        this.class_postion = AarambhSharedPreference.loadClassIdFromPreference(context);
//        this.chap = new YouTubeTitle();
//        //chap.setChapter(chapterName);
//    }

//    public SubjectNameAdapter(Context context, ArrayList<SubjectNameModel> subjectNameModelArrayList) {
//        this.context=context;
//        this.subjectNameModelArrayList=subjectNameModelArrayList;
//        this.class_postion= AarambhSharedPreference.loadClassIdFromPreference(context);
//    }

    @NonNull
    @Override
    public SubjectNameAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.subject_back, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        try{
            final CourseModel cm = courseModelArrayList.get(position);
            holder.subject_name.setText(cm.getCourseName());


            Glide.with(context).load(Global.course_url + cm.getCourseImage())
                    .crossFade()
                    .thumbnail(0.5f)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(holder.subject_icon);

            holder.red_theme_cardview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AarambhSharedPreference.saveSubjectNameToPreference(context,cm.getCourseName());
                    Intent intent=new Intent(context,SubjectActivity.class);
                    context.startActivity(intent);
                }
            });
        }catch (Exception e){e.printStackTrace();}



//        String sub = null;
//        try {
//            sub = subjectNameModelArrayList.get(position);
//            Log.e("Sub", sub);
//
//            holder.subject_name.setText(sub); //Maths, Science
//          //  holder.subject_back.setImageResource(YouTubeTitle.getBackground());
//
//            if (sub.contains("Maths")){
//                holder.subject_icon.setImageResource(R.drawable.maths);
//            }else if (sub.contains("Science")){
//                holder.subject_icon.setImageResource(R.drawable.science);
//            }else if (sub.contains("SocialScience")){
//                holder.subject_icon.setImageResource(R.drawable.social);
//            }else if (sub.contains("English")){
//                holder.subject_icon.setImageResource(R.drawable.english);
//            }else if (sub.contains("Hindi")){
//            }
//            else {
//                holder.subject_icon.setImageResource(R.drawable.science);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        final String finalSub = sub;
//        holder.red_theme_cardview.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                switch (class_postion) {
//                    case "1":
//                        //        holder.red_theme_cardview.setClickable(false);
//                        String getS = subjectNameModelArrayList.get(position);
//                        //ArrayList<String> chapName = getChapterNamesBySubject(getS, videoName);
//                        //getChaptersBySubject(getS,videoDetails);
//                        ArrayList<String> chapName = getPlayListChapter(getS, playListArrayList);
//                        chap.setChapter(chapName);
//                        //chap.setVideoTitle(videoName);
//                        if (finalSub.contains("Science")){
//                            AarambhSharedPreference.saveCourseIdToPreference(context,"3");
//                            AarambhSharedPreference.saveTopicIdToPreference(context,"7");
//                        }else if(finalSub.contains("Maths")){
//                            AarambhSharedPreference.saveCourseIdToPreference(context,"6");
//                            AarambhSharedPreference.saveTopicIdToPreference(context,"6");
//                        }
//                        Intent intent = new Intent(context, SubjectActivity.class);
//                        AarambhSharedPreference.saveSubjectNameToPreference(context, finalSub);
//                        intent.putExtra("6thDetails_chapter", chap);
//                        intent.putExtra("PlayList", playListArrayList);
//                        //intent.putExtra("6thDetails_chapter_video", getChaptersBySubjectPlayList(getS, videoDetails));
////                            AarambhSharedPreference.saveSubjectNameToPreference(context,subjectNameModelArrayList.get(position).getCourseName());
////                            AarambhSharedPreference.saveCourseIdToPreference(context,subjectNameModelArrayList.get(position).getCourseId());
//                        ((Activity) context).startActivityForResult(intent, 101);
//                        break;
//                    case "2":
//                        String getS1 = subjectNameModelArrayList.get(position);
//                        ArrayList<String> chapName1 = getPlayListChapter(getS1, playListArrayList);
//                        //ArrayList<String> chapName1 = getChapterNamesBySubject(getS1, videoName);
//                        //getChaptersBySubject(getS,videoDetails);
//                        chap.setChapter(chapName1);
//                        //chap.setVideoTitle(videoName);
//                        if (finalSub.contains("Science")){
//                            AarambhSharedPreference.saveCourseIdToPreference(context,"7");
//                            AarambhSharedPreference.saveTopicIdToPreference(context,"11");
//                        }else if(finalSub.contains("Maths")){
//                            AarambhSharedPreference.saveCourseIdToPreference(context,"45");
//                            AarambhSharedPreference.saveTopicIdToPreference(context,"12");
//                        }
//                        Intent intent1 = new Intent(context, SubjectActivity.class);
//                        AarambhSharedPreference.saveSubjectNameToPreference(context, finalSub);
//                        intent1.putExtra("7thDetails_chapter", chap);
//                        //intent1.putExtra("7thDetails_chapter_video", getChaptersBySubject(getS1, videoDetails));
//                        intent1.putExtra("PlayList", playListArrayList);
//                        //   AarambhSharedPreference.saveSubjectNameToPreference(context,subjectNameModelArrayList.get(position).getCourseName());
////                            AarambhSharedPreference.saveCourseIdToPreference(context,subjectNameModelArrayList.get(position).getCourseId());
//                        ((Activity) context).startActivityForResult(intent1, 102);
//                        break;
//                    case "3":
//                        // holder.red_theme_cardview.setClickable(false);
//                        String getS2 = subjectNameModelArrayList.get(position);
//                        //ArrayList<String> chapName2 = getChapterNamesBySubject(getS2, videoName);
//                        ArrayList<String> chapName2 = getPlayListChapter(getS2, playListArrayList);
//                        //getChaptersBySubject(getS,videoDetails);
//                        chap.setChapter(chapName2);
//                        //chap.setVideoTitle(videoName);
//                        if (finalSub.contains("Science")){
//                            AarambhSharedPreference.saveCourseIdToPreference(context,"56");
//                            AarambhSharedPreference.saveTopicIdToPreference(context,"10");
//                        }else if(finalSub.contains("Maths")){
//                            AarambhSharedPreference.saveCourseIdToPreference(context,"47");
//                            AarambhSharedPreference.saveTopicIdToPreference(context,"8");
//                        }
//                        Intent intent2 = new Intent(context, SubjectActivity.class);
//                        AarambhSharedPreference.saveSubjectNameToPreference(context, finalSub);
//                        // intent2.putExtra("subject_name",chap.getSubject());
//                        intent2.putExtra("8thDetails_chapter", chap);
//                        //intent2.putExtra("8thDetails_chapter_video", getChaptersBySubject(getS2, videoDetails));
//                        intent2.putExtra("PlayList", playListArrayList);
////                            AarambhSharedPreference.saveSubjectNameToPreference(context,subjectNameModelArrayList.get(position).getCourseName());
////                            AarambhSharedPreference.saveCourseIdToPreference(context,subjectNameModelArrayList.get(position).getCourseId());
//                        ((Activity) context).startActivityForResult(intent2, 103);
//                        break;
//                    default:
//                        //  holder.red_theme_cardview.setClickable(false);
//                        String getS3 = subjectNameModelArrayList.get(position);
//                        ArrayList<String> chapName3 = getChapterNamesBySubject(getS3, videoName);
//                        //getChaptersBySubject(getS,videoDetails);
//                        chap.setChapter(chapName3);
//                        chap.setVideoTitle(videoName);
//                        Intent intent3 = new Intent(context, SubjectActivity.class);
//                        intent3.putExtra("6thDetails_chapter", chap);
//                        //intent3.putExtra("6thDetails_chapter_video", getChaptersBySubject(getS3, videoDetails));
//                        intent3.putExtra("PlayList", playListArrayList);
//                        ((Activity) context).startActivityForResult(intent3, 101);
//
//                        break;
//                }
//            }
//        });
    }

    @Override
    public int getItemCount() {
        Log.e("lenghth", courseModelArrayList.size() + "");
        return courseModelArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView subject_name;
        ImageView subject_icon, subject_back;
        CardView red_theme_cardview;
        String base_color_one,base_color_two;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            subject_back = itemView.findViewById(R.id.subject_back);
            subject_name = itemView.findViewById(R.id.subject_name);
            subject_icon = itemView.findViewById(R.id.subject_icon);
            red_theme_cardview = itemView.findViewById(R.id.red_theme_cardview);
            base_color_one = AarambhThemeSharedPrefreence.loadBaseColorOneFromPreference(context);
            base_color_two = AarambhThemeSharedPrefreence.loadBaseColorTwoFromPreference(context);

            //Color.parseColor() method allow us to convert
            // a hexadecimal color string to an integer value (int color)
            int[] colors = {Color.parseColor(base_color_one), Color.parseColor(base_color_two)};

            //create a new gradient color
            GradientDrawable gd = new GradientDrawable(
                    GradientDrawable.Orientation.TOP_BOTTOM, colors);
            gd.setGradientCenter(270, 270);

            //gd.setCornerRadius(0f);
            //apply the button background to newly created drawable gradient
            subject_back.setBackground(gd);
        }
    }

//    public ArrayList<String> getChapterNamesBySubject(String subjectName, ArrayList<String> titleNames)
//    {
//        chapterName =null;
//        for(int i = 0; i < titleNames.size(); i++)
//        {
//            String getVideo = titleNames.get(i);
//            String title[] = getVideo.split("_");
//            String className = title[0];
//            Log.e("className", className);
//            String class_id=AarambhSharedPreference.loadClassIdFromPreference(context);
//            if(className.equalsIgnoreCase("6th") && getVideo.contains(subjectName) && class_id.equalsIgnoreCase("1")){
//                chapterName = new ArrayList<>();
//                String subject1 = title[1];
//                String chapter = title[2];
//                String topic = title[3];
//
//                Log.e("SubjectAdapter",subject1 + " : " + chapter+" : "+ topic);
//                chapterName.add(chapter);
//            }
//            if(className.equalsIgnoreCase("7th") && getVideo.contains(subjectName)&& class_id.equalsIgnoreCase("2")){
//                chapterName = new ArrayList<>();
//                String subject1 = title[1];
//                String chapter = title[2];
//                String topic = title[3];
//
//                Log.e("SubjectAdapter",subject1 + " : " + chapter+" : "+ topic);
//                chapterName.add(chapter);
//            }
//            if(className.equalsIgnoreCase("8th") && getVideo.contains(subjectName)&& class_id.equalsIgnoreCase("3")){
//                chapterName = new ArrayList<>();
//                String subject1 = title[1];
//                String chapter = title[2];
//                String topic = title[3];
//
//                Log.e("SubjectAdapter",subject1 + " : " + chapter+" : "+ topic);
//                chapterName.add(chapter);
//            }
//        }
//        return chapterName;
//    }

    public ArrayList<String> getChapterNamesBySubject(String subjectName, ArrayList<String> titleNames) {
        chapterName = new ArrayList<>();
        for (int i = 0; i < titleNames.size(); i++) {
            String getVideo = titleNames.get(i);
            String title[] = getVideo.split("_");
            String className = title[0];
            Log.e("getChapterNamesBySubjec", className);
            String class_id = AarambhSharedPreference.loadClassIdFromPreference(context);

            if (className.equalsIgnoreCase("6th") && getVideo.contains(subjectName) && class_id.equalsIgnoreCase("1")) {
                //"6th_Maths_Chapter 6_Part 8. Subtracting integers without using number line
                String subject1 = title[1];
                String chapter = title[2];
//                String topic[] = title[3].split(" ");
//                String part = topic[0];
//                String no = topic[1];
//                String chapterNamePart = chapter + " " + part + " " + no.replace(".", "");
                chapterName.add(chapter);
                Log.e("SubjectAdapter", subject1 + " : " + chapter);
                HashSet<String> hashSet = new LinkedHashSet<String>(); // to remove duplicate
                hashSet.addAll(chapterName);
                chapterName.clear();
                chapterName.addAll(hashSet);
                for (int f = 0; f < chapterName.size(); f++) {
                    Log.e("New_Sub", hashSet.toString());
                }
                //chapterName.add(chapterNamePart);
            } else if (className.equalsIgnoreCase("7th") && getVideo.contains(subjectName) && class_id.equalsIgnoreCase("2")) {
                String subject1 = title[1];
                String chapter = title[2];
//                String topic[] = title[3].split(" ");
//                String part = topic[0];
//                String no = topic[1];
//                String chapterNamePart = chapter + " " + part + " " + no.replace(".", "");
                chapterName.add(chapter);
                Log.e("SubjectAdapter", subject1 + " : " + chapter);
                HashSet<String> hashSet = new LinkedHashSet<String>(); // to remove duplicate
                hashSet.addAll(chapterName);
                chapterName.clear();
                chapterName.addAll(hashSet);
                for (int f = 0; f < chapterName.size(); f++) {
                    Log.e("New_Sub", hashSet.toString());
                }
                //chapterName.add(chapterNamePart);
            } else if (className.equalsIgnoreCase("8th") && getVideo.contains(subjectName) && class_id.equalsIgnoreCase("3")) {
                String subject1 = title[1];
                String chapter = title[2];
//                String topic[] = title[3].split(" ");
//                String part = topic[0];
//                String no = topic[1];
//                String chapterNamePart = chapter + " " + part + " " + no.replace(".", "");
                chapterName.add(chapter);
                Log.e("SubjectAdapter", subject1 + " : " + chapter);
                HashSet<String> hashSet = new LinkedHashSet<String>(); // to remove duplicate
                hashSet.addAll(chapterName);
                chapterName.clear();
                chapterName.addAll(hashSet);
                for (int f = 0; f < chapterName.size(); f++) {
                    Log.e("New_Sub", hashSet.toString());
                }
                //chapterName.add(chapterNamePart);
            }
        }
        return chapterName;
    }
//--For populating single videoId---
//    public ArrayList<VideoDetails> getChaptersBySubject(String subjectName, ArrayList<VideoDetails> mVideoDetails) {
//        filteredVideoDetails = new ArrayList<>();
//        for (int i = 0; i < mVideoDetails.size(); i++) {
//            String getVideo = mVideoDetails.get(i).getVideoName();
//            String title[] = getVideo.split("_");
//            String className = title[0];
//            Log.e("className", className);
//            Log.e("mVideoDetails before if", mVideoDetails.get(i).getVideoName() + " : " + mVideoDetails.get(i).getVideoId());
//            String class_id = AarambhSharedPreference.loadClassIdFromPreference(context);
//            if (className.equalsIgnoreCase("6th") && getVideo.contains(subjectName) && class_id.equalsIgnoreCase("1")) {
//                String subject1 = title[1];
//                String chapter = title[2];
//                String topic = title[3];
//
//                Log.e("SubjectAdapter", subject1 + " : " + chapter + " : " + topic);
//                filteredVideoDetails.add(mVideoDetails.get(i));
//                Log.e("mVideoDetails: Inside", mVideoDetails.get(i).getVideoName() + " : " + mVideoDetails.get(i).getVideoId());
//            } else if (className.equalsIgnoreCase("7th") && getVideo.contains(subjectName) && class_id.equalsIgnoreCase("2")) {
//                String subject1 = title[1];
//                String chapter = title[2];
//                String topic = title[3];
//
//                Log.e("SubjectAdapter", subject1 + " : " + chapter + " : " + topic);
//                filteredVideoDetails.add(mVideoDetails.get(i));
//                Log.e("mVideoDetails: Inside", mVideoDetails.get(i).getVideoName() + " : " + mVideoDetails.get(i).getVideoId());
//            } else if (className.equalsIgnoreCase("8th") && getVideo.contains(subjectName) && class_id.equalsIgnoreCase("3")) {
//                String subject1 = title[1];
//                String chapter = title[2];
//                String topic = title[3];
//
//                Log.e("SubjectAdapter", subject1 + " : " + chapter + " : " + topic);
//                filteredVideoDetails.add(mVideoDetails.get(i));
//                Log.e("mVideoDetails: Inside", mVideoDetails.get(i).getVideoName() + " : " + mVideoDetails.get(i).getVideoId());
//            }
//        }
//        return filteredVideoDetails;
//    }


    public ArrayList<VideoDetails> getChaptersBySubjectPlayList(String subjectName, ArrayList<VideoDetails> mVideoDetails) {
        filteredVideoDetails = new ArrayList<>();
        for (int i = 0; i < mVideoDetails.size(); i++) {
            String getVideo = mVideoDetails.get(i).getVideoName();
            String title[] = getVideo.split("_");
            String className = title[0];
            Log.e("className", className);
            Log.e("mVideoDetails before if", mVideoDetails.get(i).getVideoName() + " : " + mVideoDetails.get(i).getVideoId() + " : " + mVideoDetails.get(i).getURL());
            String class_id = AarambhSharedPreference.loadClassIdFromPreference(context);
            if (className.equalsIgnoreCase("6th") && getVideo.contains(subjectName) && class_id.equalsIgnoreCase("1")) {
                String subject1 = title[1];
                String chapter = title[2];
                String topic = title[3];

                Log.e("SubjectAdapter", subject1 + " : " + chapter + " : " + topic);
                filteredVideoDetails.add(mVideoDetails.get(i));
                Log.e("mVideoDetails: Inside", mVideoDetails.get(i).getVideoName() + " : " + mVideoDetails.get(i).getVideoId());
            } else if (className.equalsIgnoreCase("7th") && getVideo.contains(subjectName) && class_id.equalsIgnoreCase("2")) {
                String subject1 = title[1];
                String chapter = title[2];
                String topic = title[3];

                Log.e("SubjectAdapter", subject1 + " : " + chapter + " : " + topic);

                filteredVideoDetails.add(mVideoDetails.get(i));
                Log.e("mVideoDetails: Inside", mVideoDetails.get(i).getVideoName() + " : " + mVideoDetails.get(i).getVideoId());
            } else if (className.equalsIgnoreCase("8th") && getVideo.contains(subjectName) && class_id.equalsIgnoreCase("3")) {
                String subject1 = title[1];
                String chapter = title[2];
                String topic = title[3];

                Log.e("SubjectAdapter", subject1 + " : " + chapter + " : " + topic);
                filteredVideoDetails.add(mVideoDetails.get(i));
                Log.e("mVideoDetails: Inside", mVideoDetails.get(i).getVideoName() + " : " + mVideoDetails.get(i).getVideoId());
            }
        }
        return filteredVideoDetails;
    }

    public ArrayList<String> getPlayListChapter(String subjectName, ArrayList<YouTubePlayList> playLists) {
        filteredPlayListChapter = new ArrayList<>();
        String className = "";
        for (int i = 0; i < playLists.size(); i++) {
            YouTubePlayList playListObject = playLists.get(i);
            String video_title = playListObject.getPlayListTitle();
            String title_arr[] = video_title.split("_");
            className = title_arr[0];
            Log.e("classNameSubjectAdapter", className);
            String class_id = AarambhSharedPreference.loadClassIdFromPreference(context);
            if (className.equalsIgnoreCase("6th") && video_title.contains(subjectName) && class_id.equalsIgnoreCase("1")) {
                String subject1 = title_arr[1].replace(" ","");
                String chapter = title_arr[2]; //Chapter2
                String name = title_arr[3]; //Component of Food
                String title = chapter + " " + name;
                Log.e("SubjectAdapter", subject1 + " : " + chapter + " : " + title);
                filteredPlayListChapter.add(title);
                Log.e("mVideoDetails: Inside", playLists.get(i).getPlayListTitle());

            }else if (className.equalsIgnoreCase("7th") && video_title.contains(subjectName) && class_id.equalsIgnoreCase("2")) {
                String subject1 = title_arr[1].replace(" ","");
                String chapter = title_arr[2]; //Chapter2
                String name = title_arr[3]; //Component of Food
                String title = chapter + " " + name;
                Log.e("SubjectAdapter", subject1 + " : " + chapter + " : " + title);
                Collections.sort(filteredPlayListChapter);
                filteredPlayListChapter.add(title);
                Log.e("mVideoDetails: Inside", playLists.get(i).getPlayListTitle());

            }else if (className.equalsIgnoreCase("8th") && video_title.contains(subjectName) && class_id.equalsIgnoreCase("3")) {
                String subject1 = title_arr[1].replace(" ","");
                String chapter = title_arr[2]; //Chapter2
                String name = title_arr[3]; //Component of Food
                String title = chapter + " " + name;
                Log.e("SubjectAdapter", subject1 + " : " + chapter + " : " + title);
                filteredPlayListChapter.add(title);
                Log.e("mVideoDetails: Inside", playLists.get(i).getPlayListTitle());

            }

        }
        return filteredPlayListChapter;
    }










}