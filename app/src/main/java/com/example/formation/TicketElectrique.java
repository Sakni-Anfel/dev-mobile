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

public class TicketElectrique extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{


    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ImageView menuDevice;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_electrique);
        drawerLayout=findViewById(R.id.drawer_layout_ticket);
        navigationView=findViewById(R.id.navigation_ticket);
        menuDevice=findViewById(R.id.menuDeviceTicket);

        navigationDrawer();
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.devises:
                        startActivity(new Intent(TicketElectrique.this,Home.class));
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case R.id.ticketE:

                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case R.id.Profile:
                        startActivity(new Intent(TicketElectrique.this,ProfileActivity.class));
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                }
                return true;
            }
        });
    }

    private void navigationDrawer() {
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.ticketE);
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