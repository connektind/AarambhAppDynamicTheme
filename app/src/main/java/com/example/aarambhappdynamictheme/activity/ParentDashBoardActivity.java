package com.example.aarambhappdynamictheme.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Handler;
import androidx.annotation.NonNull;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.navigation.NavigationView;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.cardview.widget.CardView;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;
import com.example.aarambhappdynamictheme.R;
import com.example.aarambhappdynamictheme.sheardPrefrence.AarambhSharedPreference;

public class ParentDashBoardActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    NavigationView navigationView;
    private View navHeader;
    TextView parent_name,parent_mobile;
    ImageView navigation_cancle_btn,notification_bell;
    TextView logout,add_student_detail,update_parent;
    DrawerLayout drawer;
    private static final String TAG_HOME = "home";
    public static String CURRENT_TAG = TAG_HOME;
    CardView add_student_cardview;
    boolean doubleBackToExitPressedOnce = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent_dash_board);
        checkOrientation();
        init();
        listner();
    }

    private void checkOrientation() {
        if (getResources().getBoolean(R.bool.portrait_only)) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
    }
    private void init() {
        add_student_cardview=(CardView)findViewById(R.id.add_student_cardview);
        navigationView=findViewById(R.id.nav_view);
        navHeader = navigationView.getHeaderView(0);
        parent_mobile=(TextView)navHeader.findViewById(R.id.parent_mobile);
        parent_name=(TextView)navHeader.findViewById(R.id.parent_name_dashboard);
        parent_name.setText(AarambhSharedPreference.loadStudentNameFromPreference(this));
        parent_mobile.setText(AarambhSharedPreference.loadStudentMobileFromPreference(this));

        add_student_detail=(TextView)navHeader.findViewById(R.id.add_student);
        update_parent=(TextView)navHeader.findViewById(R.id.update_parent);
        navigation_cancle_btn=(ImageView)navHeader. findViewById(R.id.navi_cancle_btn);
        logout=(TextView)navHeader. findViewById(R.id.logout);

        drawer = (DrawerLayout) findViewById(R.id.drawerLayout);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setHomeButtonEnabled(true);
//        getSupportActionBar().setHomeAsUpIndicator(R.drawable.navigation_icon);

        CollapsingToolbarLayout collapsingToolbar = findViewById(R.id.collaspingToolbar);
        if (getSupportFragmentManager().findFragmentByTag(CURRENT_TAG) != null) {
            drawer.closeDrawers();

            // show or hide the fab button
            // toggleFab();
            return;
        }
//        actionBar.setDisplayUseLogoEnabled(true);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.hide_bottom_view_on_scroll_behavior, R.string.app_name) {
            @Override
            public void onDrawerClosed(View drawerView) {
                // Code here will be triggered once the drawer closes as we dont want anything to happen so we leave this blank
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                // Code here will be triggered once the drawer open as we dont want anything to happen so we leave this blank
                super.onDrawerOpened(drawerView);
            }
        };
        //Setting the actionbarToggle to drawer layout

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
            getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(false);
            toolbar.setNavigationIcon(R.drawable.ic_icons8_menu);

        }else {
            getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(false);
            toolbar.setNavigationIcon(R.drawable.ic_menu_white);
        }

    }

    private void listner() {
        navigation_cancle_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.closeDrawers();
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ParentDashBoardActivity.this);
                builder.setView(R.layout.exitpopup);
                final AlertDialog alert = builder.create();
                alert.show();
                //dialog.getWindow().setAttributes(windo);
                TextView dialog_cancel = (TextView) alert.findViewById(R.id.dialog_cancel);
                TextView dialog_ok = (TextView) alert.findViewById(R.id.dialog_ok);
                TextView dialog_exit = (TextView) alert.findViewById(R.id.tv_exit);
                TextView exitinformation = (TextView) alert.findViewById(R.id.exitinformation);
                LinearLayout ll_cancel = (LinearLayout) alert.findViewById(R.id.ll_cancel);
                LinearLayout ll_ok = (LinearLayout) alert.findViewById(R.id.ll_ok);
                exitinformation.setText("Do you want to exit from this App?");

                ll_cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alert.dismiss();
                    }
                });
                ll_ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(ParentDashBoardActivity.this, LoginActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        AarambhSharedPreference.saveParentIDToPreference(ParentDashBoardActivity.this, "NA");
                        AarambhSharedPreference.saveStudentNameToPreference(ParentDashBoardActivity.this, "NA");
                        AarambhSharedPreference.saveStudentProfileToPreference(ParentDashBoardActivity.this,"NA");
                        AarambhSharedPreference.saveUserTokenToPreference(ParentDashBoardActivity.this,"NA");
                        startActivity(intent);
                        finish();
                    }
                });
            }
        });
        add_student_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(ParentDashBoardActivity.this, StudentListShowActivity.class);
                startActivity(intent);
            }
        });
        add_student_cardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(ParentDashBoardActivity.this, StudentListShowActivity.class);
                startActivity(intent);
            }
        });
        update_parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ParentDashBoardActivity.this, ParentUpdateActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        return false;
    }
//    @Override
//    public void onBackPressed() {
//        AlertDialog.Builder builder = new AlertDialog.Builder(ParentDashBoard.this);
//        builder.setView(R.layout.exitpopup);
//        final AlertDialog alert = builder.create();
//        alert.show();
//        TextView dialog_cancel = (TextView) alert.findViewById(R.id.dialog_cancel);
//        TextView dialog_ok = (TextView) alert.findViewById(R.id.dialog_ok);
//        TextView dialog_exit = (TextView) alert.findViewById(R.id.tv_exit);
//        TextView exitinformation = (TextView) alert.findViewById(R.id.exitinformation);
//        LinearLayout ll_cancel = (LinearLayout) alert.findViewById(R.id.ll_cancel);
//        LinearLayout ll_ok = (LinearLayout) alert.findViewById(R.id.ll_ok);
//        exitinformation.setText("Do you want to exit from this App?");
//
//        ll_cancel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                alert.dismiss();
//            }
//        });
//        ll_ok.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//                finishAffinity();
//            }
//        });

    //}

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            finish();
            finishAffinity();
            return;
        }
        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(getApplicationContext(), "Tap again to exit",Toast.LENGTH_SHORT).show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }

}
