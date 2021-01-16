package com.example.aarambhappdynamictheme.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.aarambhappdynamictheme.R;
import com.example.aarambhappdynamictheme.sheardPrefrence.AarambhThemeSharedPrefreence;
import com.example.aarambhappdynamictheme.textGradient.TextColorGradient;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class TestRulesActivity extends AppCompatActivity {
    Button start_test;
    ImageView back_btn_test_rules;
    LinearLayout test_rules_background,transrent_test_rules;
    String base_image,transparent_color,base_color_one,base_color_two;
    Bitmap Background;
    TextView test_heading;
    String testMasterId,testDurstion;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_rules);
        checkOrientation();
        try{
            testMasterId=getIntent().getStringExtra("testMasterId");
            testDurstion=getIntent().getStringExtra("testDurstion");
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
        start_test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TestRulesActivity.this, TestAcivity.class);
                intent.putExtra("testMasterId",testMasterId);
                intent.putExtra("testDurstion",testDurstion);
                startActivity(intent);
            }
        });
        back_btn_test_rules.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TestRulesActivity.this, TestListActivity.class);
                startActivity(intent);
            }
        });
    }

    private void init() {
        StrictMode.ThreadPolicy policy = new
                StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        start_test = findViewById(R.id.start_test);
        back_btn_test_rules = findViewById(R.id.back_btn_test_rules);
        test_rules_background=findViewById(R.id.test_rules_background);
        transrent_test_rules=findViewById(R.id.transrent_test_rules);
        transrent_test_rules.setBackgroundColor(Color.parseColor(transparent_color));
        test_heading=findViewById(R.id.subscribe_name);
        TextColorGradient textColorGradient=new TextColorGradient();
        textColorGradient.getColorTextGradient(test_heading,base_color_one,base_color_two);
        getDynamicData();
        themeDynamicAdd();

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(TestRulesActivity.this, TestListActivity.class);
        startActivity(intent);
    }
    private void getDynamicData() {
        //Color.parseColor() method allow us to convert
        // a hexadecimal color string to an integer value (int color)
        int[] colors = {Color.parseColor(base_color_one), Color.parseColor(base_color_two)};

        //create a new gradient color
        GradientDrawable gd = new GradientDrawable(
                GradientDrawable.Orientation.TOP_BOTTOM, colors);

        gd.setCornerRadius(100f);
        //apply the button background to newly created drawable gradient
          start_test.setBackground(gd);
    }
    private void themeDynamicAdd() {

        //Bitmap myImage = getBitmapFromURL(image);
        try {
            Background=getBitmapFromURL(base_image);
            Drawable dr = new BitmapDrawable((Background));
            test_rules_background.setBackgroundDrawable(dr);

            Bitmap back_bitmap;
            String back_shrd= AarambhThemeSharedPrefreence.loadBackArrowIconFromPreference(this);
            back_bitmap = getBitmapFromURL(back_shrd);
            Drawable dr1 = new BitmapDrawable((back_bitmap));
            back_btn_test_rules.setBackgroundDrawable(dr1);
        }catch (Exception e){
            e.printStackTrace();
        }

    }
    public static Bitmap getBitmapFromURL(String src) {
        try {
            java.net.URL url = new URL(src);
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