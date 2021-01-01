package com.example.mhealthapp;

import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;

import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;

import android.widget.TextView;
import android.widget.Toast;

public class HomeActivity2 extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private FirebaseAuth firebaseAuth;
    private TextView userName, userEmail;
    private DatabaseReference ref;
    private String curUid;


//    GridView gridView;
//    String[] funcNames = {"Activity", "Heart Rate", "Steps", "Food&\nNutrition", "BMI", "Exercise", "Profile", "Reminders", "Sleep", "Settings", "About Us"};
//    int[] funcImg = {R.drawable.activity1, R.drawable.heartbeat, R.drawable.steps, R.drawable.mealplan, R.drawable.bmi, R.drawable.exercise, R.drawable.profile, R.drawable.notification
//            , R.drawable.sleep, R.drawable.settings, R.drawable.aboutus};

    @Override


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home2);


        firebaseAuth = FirebaseAuth.getInstance();
//
//        curUid = firebaseAuth.getCurrentUser().getUid();
//
//        ref = FirebaseDatabase.getInstance().getReference().child(curUid);
        FirebaseDatabase database = FirebaseDatabase.getInstance();

        DatabaseReference myRef = database.getReference("User");
        FirebaseUser user = firebaseAuth.getCurrentUser();
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        View navHeaderView = navigationView.getHeaderView(0);


        userName = (TextView) navHeaderView.findViewById(R.id.userNameTV);
        userEmail = (TextView) navHeaderView.findViewById(R.id.UserEmailTV);


        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);


        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {

                    String name = dataSnapshot.child("name").getValue().toString();

                    FirebaseUser user = firebaseAuth.getCurrentUser();

                    String uemail =user.getEmail();
                    userName.setText(name);
                    userEmail.setText(uemail);


                } else {

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("UserListActivity", "Error occured");
            }
        });

        // default fragment
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.flMain, new HomeFragment());
        ft.commit();

        navigationView.setCheckedItem(R.id.nav_home);


    }


    public void setActionBarTitle(String title){
        getSupportActionBar().setTitle(title);
    }



    public void Logout() {
        firebaseAuth.signOut();
        finish();
        startActivity(new Intent(HomeActivity2.this, MainActivity.class));
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }



    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.


        int id = item.getItemId();
        if (id == R.id.nav_home) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.flMain, new HomeFragment()).addToBackStack(null);
            ft.commit();
        } else if (id == R.id.nav_profile) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.flMain, new ProfileActivityFrag()).addToBackStack(null);
            ft.commit();

        } else if (id == R.id.nav_HeartRateMenu) {

            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.flMain, new HeartRate()).addToBackStack(null);
            ft.commit();


        } else if (id == R.id.nav_ActivityMenu) {

            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.flMain, new StepsWithApiFrag()).addToBackStack(null);
            ft.commit();

        } else if (id == R.id.nav_BMIMenu) {

            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.flMain, new BMI_Frag()).addToBackStack(null);
            ft.commit();

        } else if (id == R.id.nav_MealPlanMenu) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.flMain, new NutritionFragment()).addToBackStack(null);
            ft.commit();

        } else if (id == R.id.nav_ReminderMenu) {

            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.flMain, new ListViewFragment()).addToBackStack(null);
            ft.commit();

        } else if (id == R.id.nav_settings) {

        } else if (id == R.id.nav_logoutMenu) {
            Logout();
        }else if (id == R.id.nav_action_FAQ) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.flMain, new Faq()).addToBackStack(null);
            ft.commit();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
