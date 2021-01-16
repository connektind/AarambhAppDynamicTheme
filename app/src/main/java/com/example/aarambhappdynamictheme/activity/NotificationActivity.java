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

public class NotificationActivity extends AppCompatActivity {
    ImageView back_btn_notification;
    TextView notification_name;
    ImageView notification_icon;
    TextView bookmark_red_name;
    ImageView profile_subject_tab;
    TextView student_name;
    ArrayList<String> you_Chapter;
    LinearLayout notification_background,transprent_notification;
    String base_image,transparent_color,base_color_one,base_color_two,notificationicon;
    Bitmap Background,notificationIcon;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        checkOrientation();
        base_image= AarambhThemeSharedPrefreence.loadBaseImageFromPreference(this);
        transparent_color=AarambhThemeSharedPrefreence.loadBaseImageTransparentFromPreference(this);
        Log.e("transprent",transparent_color);

        base_color_one=AarambhThemeSharedPrefreence.loadBaseColorOneFromPreference(this);
        base_color_two=AarambhThemeSharedPrefreence.loadBaseColorTwoFromPreference(this);
        notificationicon=AarambhThemeSharedPrefreence.loadNotificationImageFromPreference(this);
        Log.e("notification_icon",notificationicon);

        init();
        listner();
    }
    private void checkOrientation() {
        if (getResources().getBoolean(R.bool.portrait_only)) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
    }

    private void listner() {
        back_btn_notification.setOnClickListener(new View.OnClickListener() {
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
        Intent in = new Intent(NotificationActivity.this, DashBoardActivity.class);
        in.putExtra("DataDashBoard6th", you_Chapter);
        //  in.putExtra("LandDetail", landDetailModel);
        in.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        setResult(RESULT_OK, in);
        super.finish();
    }


    private void init() {
        you_Chapter=new ArrayList<>();
        StrictMode.ThreadPolicy policy = new
                StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
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
        notification_icon=(ImageView)findViewById(R.id.notfication_icon_red);
        notification_name=(TextView) findViewById(R.id.notfication_name);
        back_btn_notification = (ImageView) findViewById(R.id.back_btn_notification);
        TextColorGradient textColorGradient=new TextColorGradient();
        textColorGradient.getColorTextGradient(notification_name,base_color_one,base_color_two);
        notification_background=findViewById(R.id.notification_background);
        transprent_notification=findViewById(R.id.transprent_notification);
        transprent_notification.setBackgroundColor(Color.parseColor(transparent_color));
        themeDynamicAdd();
    }
    private void themeDynamicAdd() {

        //Bitmap myImage = getBitmapFromURL(image);
        try {
            Background=getBitmapFromURL(base_image);

            Drawable dr = new BitmapDrawable((Background));
            notification_background.setBackgroundDrawable(dr);

            notificationIcon=getBitmapFromURL(notificationicon);
            Drawable dr1 = new BitmapDrawable((notificationIcon));
            
            notification_icon.setBackgroundDrawable(dr1);

        }catch (Exception e){
            e.printStackTrace();
        }
        Bitmap back_bitmap;
        String back_shrd=AarambhThemeSharedPrefreence.loadBackArrowIconFromPreference(this);
        try {
            back_bitmap=getBitmapFromURL(back_shrd);
            Drawable dr = new BitmapDrawable((back_bitmap));
            back_btn_notification.setBackgroundDrawable(dr);

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
