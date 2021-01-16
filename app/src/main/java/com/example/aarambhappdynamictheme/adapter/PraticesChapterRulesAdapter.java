package com.example.aarambhappdynamictheme.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aarambhappdynamictheme.R;
import com.example.aarambhappdynamictheme.activity.PraticesActivity;
import com.example.aarambhappdynamictheme.activity.PraticesListActivity;
import com.example.aarambhappdynamictheme.model.PraticesChapterListModel;
import com.example.aarambhappdynamictheme.model.TestChapterRulesModel;
import com.example.aarambhappdynamictheme.sheardPrefrence.AarambhSharedPreference;

import java.util.ArrayList;

public class PraticesChapterRulesAdapter extends RecyclerView.Adapter<PraticesChapterRulesAdapter.MyViewHolder> {
    Context context;
    ArrayList<PraticesChapterListModel> praticesChapterListModelArrayList;
    String class_postion;

    public PraticesChapterRulesAdapter(Context context, ArrayList<PraticesChapterListModel> praticesChapterListModelArrayList) {
        this.context = context;
        this.praticesChapterListModelArrayList = praticesChapterListModelArrayList;
        class_postion = AarambhSharedPreference.loadClassIdFromPreference(context);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.test_chapter, parent, false);
        return new PraticesChapterRulesAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
//        String pos=null;
//        pos= String.valueOf(testChapterRulesModelArrayList.get(position));
        holder.chapter_test.setText(praticesChapterListModelArrayList.get(position).getPracticeTitle());
        holder.test_cardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, PraticesActivity.class);
                intent.putExtra("praticesmasterId",praticesChapterListModelArrayList.get(position).getPracticeMasterId());
                context.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return praticesChapterListModelArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView chapter_test;
        CardView test_cardview;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            chapter_test = itemView.findViewById(R.id.chapter_test);
            test_cardview = itemView.findViewById(R.id.test_cardview);
        }
    }
}
