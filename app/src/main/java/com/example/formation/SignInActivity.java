package com.example.formation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Checkable;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignInActivity extends AppCompatActivity {
 private TextView goToSignUp;
 private TextView forgetPassword;
 private EditText mail,password;
 private Button signIn;
 private CheckBox remember;



    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        goToSignUp=findViewById(R.id.goToSignUp);
        forgetPassword=findViewById(R.id.forgetPassword);
        mail=findViewById(R.id.mail);
        password=findViewById(R.id.password);
        signIn=findViewById(R.id.signIn);

        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);
        remember=findViewById(R.id.remember);

        SharedPreferences preferences =getSharedPreferences("checkBox",MODE_PRIVATE);
        boolean checkBoxRemember=preferences.getBoolean("remember",false);
        if (checkBoxRemember){
            startActivity(new Intent(SignInActivity.this,Home.class));
        }else{
            Toast.makeText(this, "please login", Toast.LENGTH_SHORT).show();
        }
        remember.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (compoundButton.isChecked()){
                    SharedPreferences preferences =getSharedPreferences("checkBox",MODE_PRIVATE);
                    SharedPreferences.Editor editor= preferences.edit();
                    editor.putBoolean("remember",true);
                    editor.apply();
                }else { SharedPreferences preferences =getSharedPreferences("checkBox",MODE_PRIVATE);
                    SharedPreferences.Editor editor= preferences.edit();
                    editor.putBoolean("remember",false);
                    editor.apply();}

            }

        });

     goToSignUp.setOnClickListener(v -> {
         startActivity(new Intent(SignInActivity.this,SignUpActivity.class));
     });
        forgetPassword.setOnClickListener(v ->{
            startActivity(new Intent(SignInActivity.this,ForgetPassword.class));
        });

            signIn.setOnClickListener(v -> {

                progressDialog.setMessage("Please wait...");
                progressDialog.show();
                firebaseAuth.signInWithEmailAndPassword(mail.getText().toString(),password.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            verifyEmail();
                            progressDialog.dismiss();
                        }else{
                            Toast.makeText(SignInActivity.this, "Please verify that your data is correct", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                    }
                });
            });
        }

        private void verifyEmail() {
            FirebaseUser connectedUser = firebaseAuth.getCurrentUser();
            boolean isEmailFlag = connectedUser.isEmailVerified();
            if (isEmailFlag){
                startActivity(new Intent(SignInActivity.this,Home.class));
            }else{
                Toast.makeText(this, "Please Verify your email's account", Toast.LENGTH_SHORT).show();
                firebaseAuth.signOut();
            }
        }
}