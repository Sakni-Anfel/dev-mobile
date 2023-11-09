package com.example.formation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class SignUpActivity extends AppCompatActivity {
private TextView goToSignIn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        goToSignIn = findViewById(R.id.goToSignIn);

        goToSignIn.setOnClickListener(v ->{
            startActivity(new Intent(SignUpActivity.this,SignInActivity.class));
        });
    }
}