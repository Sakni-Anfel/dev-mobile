package com.example.formation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;


public class ForgetPassword extends AppCompatActivity {
    private Button goBackIn, btnReset;
    private EditText emailReset;

    private FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        goBackIn = findViewById(R.id.goBackIn);
        btnReset = findViewById(R.id.btnReset);
        emailReset = findViewById(R.id.emailReset);
        firebaseAuth=FirebaseAuth.getInstance();
        goBackIn.setOnClickListener(v -> {
            startActivity(new Intent(ForgetPassword.this, SignInActivity.class));
        });
         btnReset.setOnClickListener(v ->{
             firebaseAuth.sendPasswordResetEmail( emailReset.getText().toString().trim()).addOnCompleteListener(new OnCompleteListener<Void>() {
                 @Override
                 public void onComplete(@NonNull Task<Void> task) {
                     if(task.isSuccessful()){
                         Toast.makeText(ForgetPassword.this, "password reset email sent", Toast.LENGTH_SHORT).show();
                         startActivity(new Intent(ForgetPassword.this, SignInActivity.class));
                     finish();
                     }else Toast.makeText(ForgetPassword.this, "error", Toast.LENGTH_SHORT).show();
                 }
             });

         });
    }
}