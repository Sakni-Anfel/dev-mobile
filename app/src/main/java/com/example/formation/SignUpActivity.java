package com.example.formation;

import static io.reactivex.internal.util.EndConsumerHelper.validate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.formation.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.zzao;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.inappmessaging.model.Button;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUpActivity extends AppCompatActivity {
private TextView goToSignIn;
private EditText fullNameet,emailet,phoneNumberet,ncinet,yourPasswordet,confirmPasswordet;
private View signUp;
private FirebaseAuth firebaseAuth;
    private String fullNameS,emailS,phoneNumberS,ncinS,yourPasswordS,confirmPasswordS;
    private static final String EMAIL_REGEX=
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9]+)*@"
            +"[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        fullNameet=findViewById(R.id.fullName);
      emailet=findViewById(R.id.email);
      phoneNumberet=findViewById(R.id.phoneNumber);
      ncinet=findViewById(R.id.ncin);
      yourPasswordet=findViewById(R.id.yourPassword);
      confirmPasswordet=findViewById(R.id.confirmPassword);
        signUp=findViewById(R.id.signUp);
        goToSignIn = findViewById(R.id.goToSignIn);
        goToSignIn.setOnClickListener(v ->{
            startActivity(new Intent(SignUpActivity.this,SignInActivity.class));
        });
        firebaseAuth= FirebaseAuth.getInstance();
        signUp.setOnClickListener(v ->{
            if (validate()) {
                firebaseAuth.createUserWithEmailAndPassword(emailS,yourPasswordS).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            sendEmailVerification();
                        }else{
                            Toast.makeText(SignUpActivity.this, "register failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });
    }

    private void sendEmailVerification() {
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if (user != null ){
            user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()){
                        //send user data
                        sendUserData();
                        Toast.makeText(SignUpActivity.this, "registration done pls check your email", Toast.LENGTH_SHORT).show();
                        firebaseAuth.signOut();
                        startActivity(new Intent(SignUpActivity.this,SignInActivity.class));
                        finish();
                    }else {
                        Toast.makeText(SignUpActivity.this, "registration failed", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
    private void sendUserData() {

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();

        DatabaseReference myRef = firebaseDatabase.getReference("Users");

        User user = new User(fullNameS, phoneNumberS, ncinS, emailS, yourPasswordS);

        myRef.child(""+firebaseAuth.getUid()).setValue(user);
    }




    private boolean validate() {
        boolean result=false;
        fullNameS=fullNameet.getText().toString();
        emailS=emailet.getText().toString();
        ncinS=ncinet.getText().toString();
        yourPasswordS=yourPasswordet.getText().toString();
        confirmPasswordS=confirmPasswordet.getText().toString();
        phoneNumberS=phoneNumberet.getText().toString();
        if (fullNameS.isEmpty()||fullNameS.length()<7){
            fullNameet.setError("full name not valide");
        } 
        else if (!isValidEmail(emailS)) {
            emailet.setError("email not valide");
        } else if (phoneNumberS.isEmpty()||phoneNumberS.length()!=8) {
            phoneNumberet.setError("phone number not valide");
        }  else if (ncinS.isEmpty()||ncinS.length()!=8) {
            ncinet.setError("CIN not valide");
            
        } else if (yourPasswordS.isEmpty()||yourPasswordS.length()<7){
           yourPasswordet.setError("password not valide");
        } else if (confirmPasswordS.isEmpty()||!yourPasswordS.equals(confirmPasswordS)) {
            confirmPasswordet.setError("password not match");

        } else {result=true;}
        return result;
    }

    public static boolean isValidEmail(String email){
        Pattern pattern = Pattern.compile(EMAIL_REGEX);
        Matcher matcher= pattern.matcher(email);
        return matcher.matches();
    }
}