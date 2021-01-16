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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import com.example.aarambhappdynamictheme.R;
import com.example.aarambhappdynamictheme.activity.PlayVideoActivity;
import com.example.aarambhappdynamictheme.activity.VideoListActivity;
import com.example.aarambhappdynamictheme.model.ChapterModel;
import com.example.aarambhappdynamictheme.model.VideoDetails;
import com.example.aarambhappdynamictheme.sheardPrefrence.AarambhSharedPreference;

import java.util.ArrayList;

public class TopicvideoListAdapter extends RecyclerView.Adapter<TopicvideoListAdapter.MyViewHolder> {
    Context context;
    //    ArrayList<TopicvideoListModel> topicvideoListModelArrayList;
   // ArrayList<VideoDetails> subjectChapterListModelArrayList;
    String class_postion;
//    ArrayList<String> topicName;
//    ArrayList<VideoDetails> videoDetails;
//    VideoDetails objVideoDetails;
    ArrayList<ChapterModel> chapterModelArrayList;
//    public TopicvideoListAdapter(Context context, ArrayList<TopicvideoListModel> topicvideoListModelArrayList) {
//    this.context=context;
//    this.topicvideoListModelArrayList=topicvideoListModelArrayList;
//    class_postion= AarambhSharedPreference.loadClassIdFromPreference(context);
//    }

//    public TopicvideoListAdapter(Context context, ArrayList<VideoDetails> subjectChapterListModelArrayList) {
//        this.context=context;
//        this.subjectChapterListModelArrayList=subjectChapterListModelArrayList;
//        class_postion= AarambhSharedPreference.loadClassIdFromPreference(context);
//    }

//    public TopicvideoListAdapter(Context context, ArrayList<String> topicName, VideoDetails objVideoDetails) {
//        this.context=context;
//        this.topicName=topicName;
//        this.objVideoDetails = objVideoDetails;
//        class_postion= AarambhSharedPreference.loadClassIdFromPreference(context);
//    }

//    public TopicvideoListAdapter(Context context, ArrayList<String> topicName, ArrayList<VideoDetails> videoDetails) {
//        this.context=context;
//        this.topicName=topicName;
//        this.videoDetails = videoDetails;
//        class_postion= AarambhSharedPreference.loadClassIdFromPreference(context);
//    }

//    public TopicvideoListAdapter(Context context, ArrayList<VideoDetails> videoDetails) {
//        this.context=context;
//        this.videoDetails = videoDetails;
//        class_postion= AarambhSharedPreference.loadClassIdFromPreference(context);
//    }

    public TopicvideoListAdapter(Context context, ArrayList<ChapterModel> chapterModelArrayList) {
        this.context=context;
        this.chapterModelArrayList=chapterModelArrayList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.topic_video_list,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        holder.video_name.setText(chapterModelArrayList.get(position).getChapterName());
        //Glide.with(context).load().into(holder.video_image_back);
//        try {
//            final VideoDetails vd = videoDetails.get(position);
//            String topic = vd.getVideoName();
//            holder.video_name.setText(topic);
//            //Log.e("TopicNameList",topic);
//            Log.e("Topic Video Details:-",vd.getVideoName()+" : "+vd.getVideoDesc());
//
////            holder.video_duration.setText(objVideoDetails.getVideoDesc());
////            Glide.with(context).load(objVideoDetails.getURL()).into(holder.video_image_back);
//            //holder.video_duration.setText(vd.getVideoDesc());
//            Glide.with(context).load(vd.getURL()).into(holder.video_image_back);
//
//            holder.video_click.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    switch (class_postion) {
//                        case "1":
//
//                            Intent intent = new Intent(context, PlayVideoActivity.class);
//                            intent.putExtra("recom_back_red", "21");
//                            intent.putExtra("video_data",vd);
//                            ((Activity) context).startActivityForResult(intent, 301);
//                            break;
//                        case "2":
//                            Intent intent1 = new Intent(context, PlayVideoActivity.class);
//                            intent1.putExtra("recom_back_blue", "22");
//                            intent1.putExtra("video_data",vd);
//                            ((Activity) context).startActivityForResult(intent1, 302);
//
//                            break;
//                        case "3":
//                            Intent intent2 = new Intent(context, PlayVideoActivity.class);
//                            // intent2.putExtra("topic_name", subjectChapterListModelArrayList.get(position).getVideoName());
//                            intent2.putExtra("recom_back_purpal", "23");
//                            intent2.putExtra("video_data",vd);
//                            //intent2.putExtra("video_link", subjectChapterListModelArrayList.get(position).getVideoId());
//                            ((Activity) context).startActivityForResult(intent2, 303);
//                            break;
//
//                        default:
//                            Intent intent3 = new Intent(context, PlayVideoActivity.class);
//                            context.startActivity(intent3);
//                    }
//                }
//            });
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

    @Override
    public int getItemCount() {
        //return subjectChapterListModelArrayList.size();
        //return topicName.size();
        return chapterModelArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView video_image_back,bookmark_icon;
        TextView video_name,video_duration;
        LinearLayout video_click;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            video_image_back=itemView.findViewById(R.id.video_image_link);
            video_name=itemView.findViewById(R.id.topic_video_name);
            video_duration=itemView.findViewById(R.id.video_time_duration);
            video_click=itemView.findViewById(R.id.video_click);
//            bookmark_icon=itemView.findViewById(R.id.bookmark_icon);
//            bookmark_icon.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    bookmark_icon.setEnabled(true);
//                    bookmark_icon.setImageResource(R.drawable.ic_bookmark_fill);
//                }
//            });

        }
    }
}
