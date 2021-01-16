package com.example.aarambhappdynamictheme.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.aarambhappdynamictheme.R;
import com.example.aarambhappdynamictheme.adapter.TestChapterListAdapter;
import com.example.aarambhappdynamictheme.model.TestData;
import com.example.aarambhappdynamictheme.model.YouTubePlayList;
import com.example.aarambhappdynamictheme.model.YouTubeTitle;
import com.example.aarambhappdynamictheme.sheardPrefrence.AarambhSharedPreference;
import com.example.aarambhappdynamictheme.sheardPrefrence.AarambhThemeSharedPrefreence;
import com.example.aarambhappdynamictheme.textGradient.TextColorGradient;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class TestChapterListActivity extends AppCompatActivity {
    RecyclerView chapter_list_recyclerview_sixth_std_test;
    ArrayList<TestData> testDataArrayList;
    TestChapterListAdapter testChapterListAdapter;

    ArrayList<YouTubePlayList> playListArrayList;
    ArrayList<String> you_Chapter;
    YouTubeTitle sixth_Details;
    ImageView back_btn;
    TextView chapter_number,chapter_name;
    LinearLayout background_test_list,transprent_test_list;
    String base_image,transparent_color,base_color_one,base_color_two;
    Bitmap Background;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_chapter_list);
        base_image= AarambhThemeSharedPrefreence.loadBaseImageFromPreference(this);
        transparent_color=AarambhThemeSharedPrefreence.loadBaseImageTransparentFromPreference(this);
        Log.e("transprent",transparent_color);

        base_color_one=AarambhThemeSharedPrefreence.loadBaseColorOneFromPreference(this);
        base_color_two=AarambhThemeSharedPrefreence.loadBaseColorTwoFromPreference(this);
        init();
        listener();
    }

    public void init() {
        StrictMode.ThreadPolicy policy = new
                StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        chapter_number=findViewById(R.id.chapter_numbers_red);
        chapter_name=findViewById(R.id.subj_one_name);
        try {
            back_btn = findViewById(R.id.back_btn);
            sixth_Details = (YouTubeTitle) getIntent().getSerializableExtra("TestChap");
            playListArrayList = (ArrayList<YouTubePlayList>) getIntent().getSerializableExtra("TestPlayList");
            you_Chapter = new ArrayList<>();
            Log.e("SixthDetails Size",sixth_Details.getChapter().size()+"");
            for (int x = 0; x < sixth_Details.getChapter().size(); x++) {
                you_Chapter = (ArrayList<String>) sixth_Details.getChapter().clone();
            }
            Log.e("You Chapter Test",you_Chapter.size()+""); //2
            chapter_number.setText(you_Chapter.size()+" Chapters");
            chapter_name.setText(AarambhSharedPreference.loadSubjectNameFromPreference(this));
            chapter_list_recyclerview_sixth_std_test = findViewById(R.id.chapter_list_recyclerview_sixth_std_test);
            testChapterListAdapter = new TestChapterListAdapter(this,you_Chapter,playListArrayList );
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
            chapter_list_recyclerview_sixth_std_test.setLayoutManager(linearLayoutManager);
            chapter_list_recyclerview_sixth_std_test.setAdapter(testChapterListAdapter);
        } catch (Exception e) {
            e.printStackTrace();
        }
        TextColorGradient textColorGradient=new TextColorGradient();
        background_test_list=findViewById(R.id.background_test_list);
        transprent_test_list=findViewById(R.id.transprent_test_list);
        transprent_test_list.setBackgroundColor(Color.parseColor(transparent_color));
        textColorGradient.getColorTextGradient(chapter_name, base_color_one, base_color_two);


        themeDynamicAdd();
    }

    public void listener() {
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    private void themeDynamicAdd() {

        //Bitmap myImage = getBitmapFromURL(image);
        try {
            Background=getBitmapFromURL(base_image);
            Drawable dr = new BitmapDrawable((Background));
            background_test_list.setBackgroundDrawable(dr);

        }catch (Exception e){
            e.printStackTrace();
        }
        Bitmap back_bitmap;
        String back_shrd=AarambhThemeSharedPrefreence.loadBackArrowIconFromPreference(this);
        try {
            back_bitmap=getBitmapFromURL(back_shrd);
            Drawable dr = new BitmapDrawable((back_bitmap));
            back_btn.setBackgroundDrawable(dr);

        }catch (Exception e){
            e.printStackTrace();
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
