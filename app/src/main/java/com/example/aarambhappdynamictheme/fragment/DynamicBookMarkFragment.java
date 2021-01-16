package com.example.aarambhappdynamictheme.fragment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.aarambhappdynamictheme.R;
import com.example.aarambhappdynamictheme.sheardPrefrence.AarambhSharedPreference;
import com.example.aarambhappdynamictheme.sheardPrefrence.AarambhThemeSharedPrefreence;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DynamicBookMarkFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DynamicBookMarkFragment extends Fragment {
    View view;
    ImageView bookmark_image;
    Bitmap image_bitmap;
    String image_shrd;

    public static DynamicBookMarkFragment newInstance(int val) {
        DynamicBookMarkFragment fragment = new DynamicBookMarkFragment();
        Bundle args = new Bundle();
        args.putInt("someInt", val);
        fragment.setArguments(args);
        return fragment;
    }

    int val;
    TextView c;
    public DynamicBookMarkFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_dynamic_book_mark, container, false);
        val = getArguments().getInt("someInt", 0);
        bookmark_image=view.findViewById(R.id.bookmark_image);
        themeDynamicAdd();



//        if (AarambhSharedPreference.loadClassIdFromPreference(getContext()).equals("1")){
//            bookmark_image.setImageResource(R.drawable.feedbackred);
//        }else if (AarambhSharedPreference.loadClassIdFromPreference(getContext()).equals("2")){
//            bookmark_image.setImageResource(R.drawable.feedbackblue);
//
//        }else if (AarambhSharedPreference.loadClassIdFromPreference(getContext()).equals("3")){
//            bookmark_image.setImageResource(R.drawable.feedbackpurple);
//        }
        return view;
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
    private void themeDynamicAdd() {

        //Bitmap myImage = getBitmapFromURL(image);
        try {
            image_shrd=AarambhThemeSharedPrefreence.loadBookMarkImageFromPreference(getContext());
            image_bitmap=getBitmapFromURL(image_shrd);
            Drawable dr = new BitmapDrawable((image_bitmap));
            bookmark_image.setBackgroundDrawable(dr);


        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
