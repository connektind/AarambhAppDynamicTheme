package com.example.aarambhappdynamictheme.adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import com.example.aarambhappdynamictheme.R;
import com.example.aarambhappdynamictheme.model.RecommandationVideoModel;

import java.util.ArrayList;

public class RecommandationVideoAdapter extends RecyclerView.Adapter<RecommandationVideoAdapter.MyViewHolder> {
    Context context;
    ArrayList<RecommandationVideoModel> recommandationVideoModelArrayList;

    public RecommandationVideoAdapter(Context context, ArrayList<RecommandationVideoModel> recommandationVideoModelArrayList) {
        this.context = context;
        this.recommandationVideoModelArrayList = recommandationVideoModelArrayList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.recommand_video,parent,false);
    return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
       String url=recommandationVideoModelArrayList.get(position).getVideo_back_image_link();
        Log.e("url_image",url);
        Glide.with(context).load(url).into(holder.recom_image);
        holder.transparent_color.setImageResource(recommandationVideoModelArrayList.get(position).getTransprent_color());
//        holder.play_btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                switch (AarambhSharedPreference.loadClassIdFromPreference(context)){
//                    case "1":
//                        Intent intent=new Intent(context, RedThemeVideoPlayerActivity.class);
//                        intent.putExtra("recom_back_red","1");
//                        intent.putExtra("video_link",recommandationVideoModelArrayList.get(position).getVideo_link());
//                        context.startActivity(intent);
//                        break;
//                    case "2":
//                        Intent intent1=new Intent(context, BlueThemeVideoPlayerActivity.class);
//                        intent1.putExtra("recom_back_blue","2");
//                        intent1.putExtra("video_link",recommandationVideoModelArrayList.get(position).getVideo_link());
//                        context.startActivity(intent1);
//                        break;
//                    case "3":
//                        Intent intent2=new Intent(context, PurpalThemeVideoPlayerActivity.class);
//                        intent2.putExtra("recom_back_purpal","3");
//                        intent2.putExtra("video_link",recommandationVideoModelArrayList.get(position).getVideo_link());
//                        context.startActivity(intent2);
//                        break;
//                    default:
//                        Intent intent3=new Intent(context, RedThemeVideoPlayerActivity.class);
//                        intent3.putExtra("recom_back_red","1");
//                        intent3.putExtra("video_link",recommandationVideoModelArrayList.get(position).getVideo_link());
//                        context.startActivity(intent3);
//                        break;
//
//                }
////                Intent intent=new Intent(context, RedThemeVideoPlayerActivity.class);
////                intent.putExtra("recom_back","1");
////                intent.putExtra("video_link",recommandationVideoModelArrayList.get(position).getVideo_link());
////                context.startActivity(intent);
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return recommandationVideoModelArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView recom_image,transparent_color,play_btn;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            recom_image=itemView.findViewById(R.id.image_recom);
            transparent_color=itemView.findViewById(R.id.transparent_color);
            play_btn=itemView.findViewById(R.id.play_btn);
        }
    }
}
