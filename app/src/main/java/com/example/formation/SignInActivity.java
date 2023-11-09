package com.example.formation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class SignInActivity extends AppCompatActivity {
 private TextView goToSignUp;
 private TextView forgetPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        goToSignUp=findViewById(R.id.goToSignUp);
        forgetPassword=findViewById(R.id.forgetPassword);

        forgetPassword.setOnClickListener(v ->{
            startActivity(new Intent(SignInActivity.this,ForgetPassword.class));
        });
    }
}