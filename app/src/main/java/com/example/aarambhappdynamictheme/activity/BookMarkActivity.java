package com.example.aarambhappdynamictheme.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;

import com.example.aarambhappdynamictheme.sheardPrefrence.AarambhThemeSharedPrefreence;
import com.google.android.material.tabs.TabLayout;

import androidx.core.content.ContextCompat;
import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.aarambhappdynamictheme.R;
import com.example.aarambhappdynamictheme.adapter.BookMarkPageAdapter;
import com.example.aarambhappdynamictheme.sheardPrefrence.AarambhSharedPreference;
import com.example.aarambhappdynamictheme.textGradient.CircleTransform;
import com.example.aarambhappdynamictheme.textGradient.TextColorGradient;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import static com.example.aarambhappdynamictheme.util.Global.urlProfileImg;

public class BookMarkActivity extends AppCompatActivity {
    TabLayout tab;
    ViewPager viewPager;
    ImageView back_btn;
    TextView bookmark_red_name;
    ImageView profile_subject_tab;
    TextView student_name;
    ArrayList<String> subject_list;
    ArrayList<String> you_Chapter;
    LinearLayout bookmark_background_main,tranparent_layer;
    Bitmap myImage,back_btn_bitmap;
    String base_image,transparent_color,base_color_one,base_color_two,back_shrd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_mark);
        checkOrientation();
        try {
            subject_list=getIntent().getStringArrayListExtra("SubjectList");
            Log.e("datalistBookmark", String.valueOf(subject_list.size()));
        }catch (Exception e){
            e.printStackTrace();
        }

         base_image= AarambhThemeSharedPrefreence.loadBaseImageFromPreference(this);
         transparent_color=AarambhThemeSharedPrefreence.loadBaseImageTransparentFromPreference(this);
         Log.e("transprent",transparent_color);

         base_color_one=AarambhThemeSharedPrefreence.loadBaseColorOneFromPreference(this);
         base_color_two=AarambhThemeSharedPrefreence.loadBaseColorTwoFromPreference(this);
        init();
        listner();
    }
    private void checkOrientation() {
        if (getResources().getBoolean(R.bool.portrait_only)) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
    }


    private void listner() {
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                back_btn.setEnabled(false);
                finish();
//                Intent intent=new Intent(RedThemeBookMarkActivity.this, DashBoardSixthStandardActivity.class);
//                startActivity(intent);
//                finishAffinity();
            }
        });
//        for (int k = 0; k <subject_list.size(); k++) {
//            tab.addTab(tab.newTab().setText(subject_list.get(k)));

//            switch (k){
//                case 0:
//                    tab.addTab(tab.newTab().setText("All"));
//                    break;
//                case 1:
//                    tab.addTab(tab.newTab().setText("Hindi"));
//                    break;
//                case 2:
//                    tab.addTab(tab.newTab().setText("English"));
//                    break;
//                case 3:
//                    tab.addTab(tab.newTab().setText("Maths"));
//                    break;
//                case 4:
//                    tab.addTab(tab.newTab().setText("Science"));
//                    break;
//                case 5:
//                    tab.addTab(tab.newTab().setText("Gk"));
//                    break;
//            }
       // }
//        BookMarkPageAdapter adapter = new BookMarkPageAdapter
//                (getSupportFragmentManager(), tab.getTabCount());
//        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(1);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tab));
//Bonus Code : If your tab layout has more than 2 tabs then tab will scroll other wise they will take whole width of the screen
        if (tab.getTabCount() == 2) {
            tab.setTabMode(TabLayout.MODE_FIXED);
        } else {
            tab.setTabMode(TabLayout.MODE_SCROLLABLE);
        }

    }

    private void init() {
        tranparent_layer=findViewById(R.id.tranparent_layer);
        tranparent_layer.setBackgroundColor(Color.parseColor(transparent_color));
        StrictMode.ThreadPolicy policy = new
                StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        you_Chapter=new ArrayList<>();
//        try {
//            profile_subject_tab=(ImageView) findViewById(R.id.img_profile_tab);
//            student_name=(TextView) findViewById(R.id.student_name_red);
//            student_name.setText(AarambhSharedPreference.loadStudentNameFromPreference(this));
//            Glide.with(this).load(urlProfileImg)
//                    .crossFade()
//                    .thumbnail(0.5f)
//                    .bitmapTransform(new CircleTransform(this))
//                    .diskCacheStrategy(DiskCacheStrategy.ALL)
//                    .into(profile_subject_tab);
//
//        }catch (Exception e){
//            e.printStackTrace();
//        }
        bookmark_red_name=findViewById(R.id.bookmark_red_name);
        TextColorGradient textColorGradient=new TextColorGradient();
        textColorGradient.getColorTextGradient(bookmark_red_name,base_color_one,base_color_two);
        back_btn=findViewById(R.id.back_btn_bookmark);
        tab=findViewById(R.id.tabs);
        viewPager=findViewById(R.id.frameLayout);
        themeDynamicAdd();
        getButtunDynamic();
    }

    private void getButtunDynamic() {
        //Color.parseColor() method allow us to convert
        // a hexadecimal color string to an integer value (int color)
        int[] colors = {Color.parseColor(base_color_one),Color.parseColor(base_color_two)};

        //create a new gradient color
        GradientDrawable gd = new GradientDrawable(
                GradientDrawable.Orientation.TOP_BOTTOM, colors);

        gd.setCornerRadius(100f);

        //tab.setSelectedTabIndicator(gd);
           if (tab.getTabSelectedIndicator().isStateful()){
               tab.setBackgroundResource(R.drawable.tab_background_xml);
           }else{
               tab.setBackground(gd);
           }



        //apply the button background to newly created drawable gradient
        //tab.setBackground(gd);
    }

    @Override
    public void onBackPressed() {
        finish();
    }
    @Override
    public void finish() {
        try {
            Intent in = new Intent(BookMarkActivity.this, DashBoardActivity.class);
            in.putExtra("DataDashBoard6th", you_Chapter);
            //  in.putExtra("LandDetail", landDetailModel);
            in.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            setResult(RESULT_OK, in);
            super.finish();
        }catch (Exception e){
            Toast.makeText(this,"Please On Your Internet Connection",Toast.LENGTH_LONG).show();
            Intent in = new Intent(BookMarkActivity.this, DashBoardActivity.class);
            startActivity(in);
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
    private void themeDynamicAdd() {

        //Bitmap myImage = getBitmapFromURL(image);
        try {
            bookmark_background_main=findViewById(R.id.bookmark_background_main);
            myImage=getBitmapFromURL(base_image);
            Drawable dr = new BitmapDrawable((myImage));
            bookmark_background_main.setBackgroundDrawable(dr);


        }catch (Exception e){
            e.printStackTrace();
        }
        try {
            back_shrd=AarambhThemeSharedPrefreence.loadBackArrowIconFromPreference(this);
            back_btn_bitmap=getBitmapFromURL(back_shrd);
            Drawable dr = new BitmapDrawable((back_btn_bitmap));
            back_btn.setBackgroundDrawable(dr);


        }catch (Exception e){
            e.printStackTrace();
        }

    }

}
