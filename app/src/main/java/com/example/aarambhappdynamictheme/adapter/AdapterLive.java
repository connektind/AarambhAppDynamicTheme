package com.example.aarambhappdynamictheme.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.aarambhappdynamictheme.R;
import com.example.aarambhappdynamictheme.activity.DashBoardActivity;
import com.example.aarambhappdynamictheme.activity.LiveVideoActivity;
import com.example.aarambhappdynamictheme.model.LiveData;
import com.example.aarambhappdynamictheme.model.SchoolModel;
import com.example.aarambhappdynamictheme.model.StudentDetailModel;
import com.example.aarambhappdynamictheme.model.YouTubePlayList;
import com.example.aarambhappdynamictheme.model.YouTubeTitle;
import com.example.aarambhappdynamictheme.sheardPrefrence.AarambhThemeSharedPrefreence;
import com.example.aarambhappdynamictheme.util.Global;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class AdapterLive extends RecyclerView.Adapter<AdapterLive.MyViewHolder> {

    Context context;
    ArrayList<SchoolModel> liveDataArrayList;

    public AdapterLive(Context context, ArrayList<SchoolModel> liveDataArrayList) {
        this.context = context;
        this.liveDataArrayList = liveDataArrayList;
    }

    @NonNull
    @Override
    public AdapterLive.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_dashboard_live, parent, false);
        return new AdapterLive.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterLive.MyViewHolder holder, int position) {
        SchoolModel sc = liveDataArrayList.get(position);
        String logo= AarambhThemeSharedPrefreence.loadSchoolLogoFromPreference(context);
        try {
            Glide.with(context).load( logo)
                    .crossFade()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(holder.img_logo);
        } catch (Exception e) {
            e.printStackTrace();
        }


        holder.img_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(context, LiveVideoActivity.class);
                context.startActivity(in);
            }
        });
    }

    @Override
    public int getItemCount() {
        Log.e("Adapter Live Size", liveDataArrayList.size() + "");
        return liveDataArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView img_logo, img_arrow;
        TextView liveDisp, txtWebninars;
        CardView liveCardView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            img_logo = itemView.findViewById(R.id.img_logo);
            img_arrow = itemView.findViewById(R.id.img_arrow);
            liveDisp = itemView.findViewById(R.id.liveDisp);
            txtWebninars = itemView.findViewById(R.id.txtWebninars);
            liveCardView = itemView.findViewById(R.id.liveCardView);
            String live_arrow=AarambhThemeSharedPrefreence.loadLiveArrowFromPreference(context);
            try {
                Bitmap Background = getBitmapFromURL(live_arrow);
                Drawable dr = new BitmapDrawable((Background));
                img_arrow.setBackgroundDrawable(dr);
                // navigationView.setBackgroundDrawable(dr);

            } catch (Exception e) {
                e.printStackTrace();
            }
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


}
