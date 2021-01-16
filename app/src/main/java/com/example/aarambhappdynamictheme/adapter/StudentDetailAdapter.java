package com.example.aarambhappdynamictheme.adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.aarambhappdynamictheme.R;
import com.example.aarambhappdynamictheme.model.StudentDetailModel;

import java.util.ArrayList;

public class StudentDetailAdapter extends RecyclerView.Adapter<StudentDetailAdapter.MyViewHolder> {
    Context context;
    ArrayList<StudentDetailModel> studentDetailModelArrayList;
    public StudentDetailAdapter(Context context, ArrayList<StudentDetailModel> studentDetailModelArrayList) {
    this.context=context;
    this.studentDetailModelArrayList=studentDetailModelArrayList;

    }

    @NonNull
    @Override
    public StudentDetailAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.student_list, parent, false);
        return new StudentDetailAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentDetailAdapter.MyViewHolder holder, int position) {
           holder.studentName.setText(studentDetailModelArrayList.get(position).getStudentName());
           if (studentDetailModelArrayList.get(position).getClassId().equalsIgnoreCase("1")){
               holder.studentClass.setText("6th Grade");
           }else if (studentDetailModelArrayList.get(position).getClassId().equalsIgnoreCase("2")){
               holder.studentClass.setText("7th Grade");
           }else if (studentDetailModelArrayList.get(position).getClassId().equalsIgnoreCase("3"))
           {
               holder.studentClass.setText("8th Grade");
           }
           holder.studentUsername.setText(studentDetailModelArrayList.get(position).getStudentUsername());
    }

    @Override
    public int getItemCount() {
        return studentDetailModelArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView studentName,studentClass,studentUsername;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            studentName=itemView.findViewById(R.id.student_name);
            studentClass=itemView.findViewById(R.id.student_class);
            studentUsername=itemView.findViewById(R.id.student_username);
        }
    }
}
