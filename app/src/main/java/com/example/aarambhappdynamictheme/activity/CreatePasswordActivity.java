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
import android.widget.Toast;

import com.example.aarambhappdynamictheme.R;

public class CreatePasswordActivity extends AppCompatActivity {
    EditText new_password,confirm_password;
    Button reset_password;
    ImageView back_btn_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_password);
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        checkOrientation();
        init();
        listner();
    }
    private void listner() {
        reset_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String new_pass=new_password.getText().toString().trim();
                String conf_pass=confirm_password.getText().toString().trim();
                if (new_pass.isEmpty()){
                    new_password.setError("Please Enter Password.");
                    new_password.requestFocus();
                }else if (conf_pass.isEmpty()){
                    confirm_password.setError("Please Confirm Your Password.");
                    confirm_password.requestFocus();

                }else {
                    reset_password.setClickable(false);
                    Toast.makeText(CreatePasswordActivity.this, "Reset Password Succesfull.", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(CreatePasswordActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
        back_btn_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                back_btn_password.setClickable(false);
                Intent intent=new Intent(CreatePasswordActivity.this,VerificationActivity.class);
                startActivity(intent);

            }
        });
    }
    private void init() {
        reset_password=(Button)findViewById(R.id.reset_password);
        back_btn_password=(ImageView)findViewById(R.id.back_btn_password);
        new_password=(EditText)findViewById(R.id.new_password);
        confirm_password=(EditText)findViewById(R.id.new_password);
    }

    @Override
    public void onBackPressed() {
        Intent intent=new Intent(CreatePasswordActivity.this,VerificationActivity.class);
        startActivity(intent);
        finishAffinity();
    }

    private void checkOrientation() {
        if (getResources().getBoolean(R.bool.portrait_only)) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
        }
    }
}
