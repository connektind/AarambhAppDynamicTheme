package com.example.aarambhappdynamictheme.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.aarambhappdynamictheme.R;

public class VerificationActivity extends AppCompatActivity {
    ImageView back_btn_otp;
    EditText otp_verify;
    Button submit_otp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification);
        checkOrientation();
        init();
        listner();
    }
    private void checkOrientation() {
        if (getResources().getBoolean(R.bool.portrait_only)) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
        }
    }

    private void listner() {
        back_btn_otp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(VerificationActivity.this,ForgetPasswordActivity.class);
                startActivity(intent);
                finish();
            }
        });
        submit_otp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String otp=otp_verify.getText().toString().trim();
                if (otp.isEmpty()){
                    otp_verify.setError("Please Enter OTP.");
                    otp_verify.requestFocus();

                }else{
                    submit_otp.setClickable(false);
                    Intent intent=new Intent(VerificationActivity.this,CreatePasswordActivity.class);
                    startActivity(intent);
                    finish();
                }

            }
        });

    }

    private void init() {
        back_btn_otp=(ImageView)findViewById(R.id.back_btn_otp);
        otp_verify=(EditText)findViewById(R.id.otp_verify);
        submit_otp=(Button)findViewById(R.id.submit_otp);
    }

    @Override
    public void onBackPressed() {
        Intent intent=new Intent(VerificationActivity.this,ForgetPasswordActivity.class);
        startActivity(intent);
        finishAffinity();
    }
}
