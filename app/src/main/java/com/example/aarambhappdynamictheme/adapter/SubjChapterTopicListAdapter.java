package com.example.aarambhappdynamictheme.adapter;

import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import com.example.aarambhappdynamictheme.R;
import com.example.aarambhappdynamictheme.activity.PlayVideoActivity;
import com.example.aarambhappdynamictheme.model.VideoDetails;
import com.example.aarambhappdynamictheme.sheardPrefrence.AarambhSharedPreference;

import java.util.ArrayList;

public class SubjChapterTopicListAdapter extends RecyclerView.Adapter<SubjChapterTopicListAdapter.MyViewHolder> {
    Context context;
    ArrayList<VideoDetails> subjChapterTopicListModelArrayList;
    String class_id;

    public SubjChapterTopicListAdapter(Context context, ArrayList<VideoDetails> subjChapterTopicListModelArrayList) {
        this.context = context;
        this.subjChapterTopicListModelArrayList = subjChapterTopicListModelArrayList;
        this.class_id = AarambhSharedPreference.loadClassIdFromPreference(context);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.topic_list_back, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
      //  holder.transparent_color.setImageResource(subjChapterTopicListModelArrayList.get(position).getBlack_transparent_back());
      try {
          Log.e("chapter_name",subjChapterTopicListModelArrayList.get(position).getVideoName());
          holder.topic_name.setText(subjChapterTopicListModelArrayList.get(position).getVideoName());
          Glide.with(context).load(subjChapterTopicListModelArrayList.get(position).getURL()).into(holder.topic_icon);

      }catch (Exception e){
          e.printStackTrace();
      }

        holder.topic_cardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  Log.e("class_id_adpater",class_id);
                switch (class_id) {
                    case "1":
                        Intent intent = new Intent(context, PlayVideoActivity.class);
                        intent.putExtra("topic_name",subjChapterTopicListModelArrayList.get(position).getVideoName());
                        intent.putExtra("recom_back_red","11");
                        try{
                            Log.e("videoId",subjChapterTopicListModelArrayList.get(position).getVideoId());

                        }catch (Exception e){
                            e.printStackTrace();
                        }
                        intent.putExtra("video_link", subjChapterTopicListModelArrayList.get(position).getVideoId());
                        context.startActivity(intent);
                        break;
                    case "2":
                        Intent intent1 = new Intent(context, PlayVideoActivity.class);
                        intent1.putExtra("topic_name",subjChapterTopicListModelArrayList.get(position).getVideoName());
                        intent1.putExtra("recom_back_blue","12");
                        intent1.putExtra("video_link", subjChapterTopicListModelArrayList.get(position).getVideoId());
                        context.startActivity(intent1);
                        break;
                    case "3":
                        Intent intent2 = new Intent(context, PlayVideoActivity.class);
                        intent2.putExtra("topic_name",subjChapterTopicListModelArrayList.get(position).getVideoName());
                        intent2.putExtra("recom_back_purpal","13");
                        intent2.putExtra("video_link", subjChapterTopicListModelArrayList.get(position).getVideoId());
                        context.startActivity(intent2);
                        break;
                    default:
                        Intent intent3 = new Intent(context, PlayVideoActivity.class);
                        intent3.putExtra("topic_name",subjChapterTopicListModelArrayList.get(position).getVideoName());
                        intent3.putExtra("recom_back_red","11");
                        intent3.putExtra("video_link", subjChapterTopicListModelArrayList.get(position).getVideoId());
                        context.startActivity(intent3);
                        break;
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return subjChapterTopicListModelArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView topic_icon, transparent_color,play_btn;
        CardView topic_cardview;
        TextView topic_name;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            topic_icon = itemView.findViewById(R.id.image_topic_icon);
            topic_name = itemView.findViewById(R.id.topic_name);
            transparent_color = itemView.findViewById(R.id.transparent_color);
            topic_cardview = itemView.findViewById(R.id.topic_cardview);
            play_btn=itemView.findViewById(R.id.play_btn);


        }
    }
}
