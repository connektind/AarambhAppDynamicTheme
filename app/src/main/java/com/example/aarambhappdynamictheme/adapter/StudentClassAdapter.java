package com.example.aarambhappdynamictheme.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.aarambhappdynamictheme.R;
import com.example.aarambhappdynamictheme.model.StudentClassModel;

import java.util.ArrayList;

public class StudentClassAdapter extends BaseAdapter {
    Context context;
    ArrayList<StudentClassModel> studentClassModelArrayList;
    public StudentClassAdapter(Context context, ArrayList<StudentClassModel> studentClassModelArrayList) {
        this.context=context;
        this.studentClassModelArrayList=studentClassModelArrayList;

    }

    @Override
    public int getCount() {
        return studentClassModelArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return studentClassModelArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        StudentClassModel studentClassModel = studentClassModelArrayList.get(position);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.search_list, null);
        TextView tv_name = ((TextView) view.findViewById(R.id.tv_name));
        tv_name.setText(studentClassModel.getStudentClass());
        return view;
    }
}
