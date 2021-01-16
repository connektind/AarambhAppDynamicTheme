package com.example.aarambhappdynamictheme.activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.aarambhappdynamictheme.R;
import com.example.aarambhappdynamictheme.sheardPrefrence.AarambhSharedPreference;
import com.example.aarambhappdynamictheme.sheardPrefrence.AarambhThemeSharedPrefreence;
import com.example.aarambhappdynamictheme.textGradient.CircleTransform;
import com.example.aarambhappdynamictheme.textGradient.TextColorGradient;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import static com.example.aarambhappdynamictheme.util.Global.urlProfileImg;

public class TermsCondiationActivity extends AppCompatActivity {
    ImageView back_btn,tab_profile_icon;
    TextView terms_condition_name,tab_profile_name;
    ArrayList<String> you_Chapter;
    WebView simpleWebView;
    LinearLayout terms_background,transprent_terms;
    String base_image,transparent_color,base_color_one,base_color_two;
    Bitmap Background;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms_condiation);
        checkOrientation();
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
                finish();
            }
        });
    }
    @Override
    public void onBackPressed() {
        finish();
    }
    @Override
    public void finish() {
        Intent in=new Intent(TermsCondiationActivity.this, DashBoardActivity.class);
        in.putExtra("DataDashBoard6th", you_Chapter);
        //  in.putExtra("LandDetail", landDetailModel);
        in.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        setResult(RESULT_OK, in);
        super.finish();
    }


    private void init() {
        StrictMode.ThreadPolicy policy = new
                StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        back_btn = (ImageView) findViewById(R.id.back_btn_terms);
        terms_condition_name = (TextView) findViewById(R.id.terms_condition_name);
        TextColorGradient textColorGradient = new TextColorGradient();
        textColorGradient.getColorTextGradient(terms_condition_name, base_color_one, base_color_two);
//        try {
//            tab_profile_icon = (ImageView) findViewById(R.id.img_profile_tab);
//            tab_profile_name = (TextView) findViewById(R.id.student_name_red);
//            tab_profile_name.setText(AarambhSharedPreference.loadStudentNameFromPreference(this));
//            Glide.with(this).load(urlProfileImg)
//                    .crossFade()
//                    .thumbnail(0.5f)
//                    .bitmapTransform(new CircleTransform(this))
//                    .diskCacheStrategy(DiskCacheStrategy.ALL)
//                    .into(tab_profile_icon);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        simpleWebView = (WebView) findViewById(R.id.simpleWebView);
        simpleWebView.setWebViewClient(new MyWebViewClient());
        String url = "https://aarambhapp.com/termsofservicesapp.html";
        simpleWebView.getSettings().setJavaScriptEnabled(true);
        simpleWebView.loadUrl(url); // load the url on the web view

        terms_background=findViewById(R.id.terms_background);
        transprent_terms=findViewById(R.id.transprent_terms);
        transprent_terms.setBackgroundColor(Color.parseColor(transparent_color));
        themeDynamicAdd();
    }
    // custom web view client class who extends WebViewClient
    private class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url); // load the url
            return true;
        }
    }

    private void themeDynamicAdd() {

        //Bitmap myImage = getBitmapFromURL(image);
        try {
            Background=getBitmapFromURL(base_image);
            Drawable dr = new BitmapDrawable((Background));
            terms_background.setBackgroundDrawable(dr);

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
