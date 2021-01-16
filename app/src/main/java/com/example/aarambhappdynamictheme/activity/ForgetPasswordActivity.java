package com.example.aarambhappdynamictheme.activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatDelegate;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.aarambhappdynamictheme.R;

public class ForgetPasswordActivity extends AppCompatActivity {
    Button btn_reset_pass;
    ImageView back_btn_forgot;
    TextView signup;
    EditText login_mobile_no;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        checkOrientation();
        init();
        listner();
    }

    private void init() {
        btn_reset_pass=(Button) findViewById(R.id.btn_reset_pass);
        back_btn_forgot=(ImageView)findViewById(R.id.back_btn_forgot);
        signup=(TextView)findViewById(R.id.signup);
        login_mobile_no=(EditText)findViewById(R.id.login_mobile_no);
    }

    @Override
    public void onBackPressed() {
        Intent intent=new Intent(ForgetPasswordActivity.this,LoginActivity.class);
        startActivity(intent);
        finishAffinity();
    }

    private void listner() {
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup.setClickable(false);
                Intent intent = new Intent(ForgetPasswordActivity.this, ParentRegisterActivity.class);
                startActivity(intent);
                finish();
            }
        });
        back_btn_forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                back_btn_forgot.setClickable(false);
                Intent intent=new Intent(ForgetPasswordActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });
        btn_reset_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String login_mobile=login_mobile_no.getText().toString().trim();
                String mobilep = "[6-9]{1}[0-9]{9}";
                if (login_mobile.isEmpty()){
                    login_mobile_no.setError("We just need registered mobile number to reset your password.");
                    login_mobile_no.requestFocus();
                }else if (!login_mobile.matches(mobilep)) {
                    login_mobile_no.setError("Mobile Number Should Start With 6,7,8 or 9.Please Enter Valid Mobile Number.");
                    login_mobile_no.requestFocus();
                }
                else{
                    btn_reset_pass.setClickable(false);
                    Intent intent = new Intent(ForgetPasswordActivity.this, VerificationActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

    private void checkOrientation() {
        if (getResources().getBoolean(R.bool.portrait_only)) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
        }
    }
}
