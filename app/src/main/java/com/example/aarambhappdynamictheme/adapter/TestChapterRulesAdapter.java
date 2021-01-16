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
import com.example.aarambhappdynamictheme.activity.TestRulesActivity;
import com.example.aarambhappdynamictheme.model.TestChapterRulesModel;
import com.example.aarambhappdynamictheme.sheardPrefrence.AarambhSharedPreference;


import java.util.ArrayList;

public class TestChapterRulesAdapter extends RecyclerView.Adapter<TestChapterRulesAdapter.MyViewHolder> {
    Context context;
    ArrayList<TestChapterRulesModel> testChapterRulesModelArrayList;
    String class_postion;

    public TestChapterRulesAdapter(Context context, ArrayList<TestChapterRulesModel> testChapterRulesModelArrayList) {
        this.context = context;
        this.testChapterRulesModelArrayList = testChapterRulesModelArrayList;
        class_postion = AarambhSharedPreference.loadClassIdFromPreference(context);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.test_chapter, parent, false);
        return new TestChapterRulesAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
//        String pos=null;
//        pos= String.valueOf(testChapterRulesModelArrayList.get(position));
        holder.chapter_test.setText(testChapterRulesModelArrayList.get(position).getTestTitle());
        holder.test_cardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Intent intent=new Intent(context, TestRulesActivity.class);
               intent.putExtra("testMasterId",testChapterRulesModelArrayList.get(position).getTestMasterId());
               intent.putExtra("testDurstion",testChapterRulesModelArrayList.get(position).getTestDuration());
               context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return testChapterRulesModelArrayList.size();
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
