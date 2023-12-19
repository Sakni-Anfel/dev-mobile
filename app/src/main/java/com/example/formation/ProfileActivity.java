package com.example.formation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileActivity extends AppCompatActivity {
 private Button editProfile, logOut;
 private EditText emailProfile, fullNameProfile,phoneNumberProfile,cinProfile;
 private FirebaseUser loggedUser;
 private FirebaseDatabase firebaseDatabase;
 private FirebaseAuth firebaseAuth;
 private DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        editProfile=findViewById(R.id.editProfile);
        logOut=findViewById(R.id.logOut);
        emailProfile=findViewById(R.id.emailProfile);
        cinProfile=findViewById(R.id.cinProfile);
        fullNameProfile=findViewById(R.id.fullNameProfil);
       phoneNumberProfile=findViewById(R.id.phoneNumber);

       firebaseAuth=FirebaseAuth.getInstance();
       firebaseDatabase=FirebaseDatabase.getInstance();
       loggedUser=firebaseAuth.getCurrentUser();
       reference=firebaseDatabase.getReference().child("Users").child(loggedUser.getUid());


       reference.addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot snapshot) {
               String fullNameS = snapshot.child("fullName").getValue().toString();
               String cinS = snapshot.child("ncin").getValue().toString();
               String emailS = snapshot.child("email").getValue().toString();
               String phoneNumberS = snapshot.child("phoneNumber").getValue().toString();
               fullNameProfile.setText(fullNameS);
               emailProfile.setText(emailS);
               cinProfile.setText(cinS);
               phoneNumberProfile.setText(phoneNumberS);
           }

           @Override
           public void onCancelled(@NonNull DatabaseError error) {
               Toast.makeText(ProfileActivity.this, ""+error, Toast.LENGTH_SHORT).show();
           }
       });
       logOut.setOnClickListener(v -> {
           SharedPreferences preferences =getSharedPreferences("checkBox",MODE_PRIVATE);
           SharedPreferences.Editor editor= preferences.edit();
           editor.putBoolean("remember",false);
           editor.apply();
           startActivity(new Intent(ProfileActivity.this,SignInActivity.class));
       });
        editProfile.setOnClickListener( view -> {
            fullNameProfile.setFocusableInTouchMode(true);
            cinProfile.setFocusableInTouchMode(true);
            phoneNumberProfile.setFocusableInTouchMode(true);
            editProfile.setText("save");
        editProfile.setOnClickListener(view1 ->{
            String editFullName=fullNameProfile.getText().toString();
            String editPhone=phoneNumberProfile.getText().toString();
            String editCin=cinProfile.getText().toString();
            reference.child("fullName").setValue(editFullName);
            reference.child("ncin").setValue(editCin);
            reference.child("phoneNumber").setValue(editPhone);
            Toast.makeText(this, "your data has been modified", Toast.LENGTH_SHORT).show();
            fullNameProfile.setFocusableInTouchMode(false);
            cinProfile.setFocusableInTouchMode(false);
            phoneNumberProfile.setFocusableInTouchMode(false);
            editProfile.setText("edit");
            fullNameProfile.clearFocus();
            phoneNumberProfile.clearFocus();
            cinProfile.clearFocus();
        } );
        });

    }
}