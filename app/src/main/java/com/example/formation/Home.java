package com.example.formation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;


public class Home extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
private Button buttonDevice;
private DrawerLayout drawerLayout;
private NavigationView navigationView;
private ImageView menuDevice;
private EditText deviceValueHome,deviceNameHome;
private FirebaseDatabase firebaseDatabase;
private DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        buttonDevice=findViewById(R.id.buttonDevice);
        drawerLayout=findViewById(R.id.drawer_layout_home);
        navigationView=findViewById(R.id.navigation_home);
        menuDevice=findViewById(R.id.menuDevice);
        deviceNameHome=findViewById(R.id.deviceName);
        deviceValueHome=findViewById(R.id.deviceValue);
        firebaseDatabase=FirebaseDatabase.getInstance();
        reference= firebaseDatabase.getReference();


        buttonDevice.setOnClickListener(view -> {
            String deviceNameS=deviceNameHome.getText().toString();
            String deviceValueS=deviceValueHome.getText().toString();

            HashMap<String,String> deviceMap = new HashMap<>();
            deviceMap.put("name",deviceNameS);
            deviceMap.put("value",deviceValueS);
            reference.child("Devices").push().setValue(deviceMap);
            deviceNameHome.setText("");
            deviceValueHome.setText("");
            deviceNameHome.clearFocus();
            deviceValueHome.clearFocus();
            Toast.makeText(this, "saved device", Toast.LENGTH_SHORT).show();
        });
    navigationDrawer();
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.devises:
                        drawerLayout.closeDrawer(GravityCompat.START);
                    break;
                    case R.id.ticketE:
                        startActivity(new Intent(Home.this,TicketElectrique.class));
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case R.id.Profile:
                        startActivity(new Intent(Home.this,ProfileActivity.class));
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                }
                return true;
            }
        });
    }

    private void navigationDrawer() {
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.devises);
        navigationView.bringToFront();
        menuDevice.setOnClickListener(view -> {
            if(drawerLayout.isDrawerVisible(GravityCompat.START)) {
                drawerLayout.closeDrawer(GravityCompat.START);
            }else drawerLayout.openDrawer(GravityCompat.START);
drawerLayout.setScrimColor(getResources().getColor(R.color.colorApp));
        });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return true;
    }

    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerVisible(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        }else super.onBackPressed();
    }
}