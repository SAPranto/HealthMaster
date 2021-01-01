package com.example.mhealthapp;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileActivityFrag extends Fragment  {

    private TextView uName, uEmail, uAge, uHeight, uWeight, uGender, uBlooadgrp ,dob;
    private ImageView editpro;
    private DatabaseReference ref;
    private FirebaseAuth mAuth;
    private String curUid;
    private String useremail;
    private GoogleApiClient mGoogleApiClient;
    private FirebaseDatabase mFirebaseDatabase;

    private FirebaseAuth.AuthStateListener mAuthListner;
    private DatabaseReference myref;
    private String uid;

    public ProfileActivityFrag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ((HomeActivity2)getActivity()).setActionBarTitle("Profile");

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile_activity, container, false);

    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myref = mFirebaseDatabase.getReference();
        FirebaseUser user = mAuth.getCurrentUser();

        useremail = user.getEmail();
        String uid = user.getUid();

        uName = (TextView) getActivity().findViewById(R.id.user_profile_name);

        uAge = (TextView) getActivity().findViewById(R.id.ageTVpro);
        uWeight = (TextView) getActivity().findViewById(R.id.weightTVpro);
        uHeight = (TextView) getActivity().findViewById(R.id.heightTVpro);
        uGender = (TextView) getActivity().findViewById(R.id.genderTVpro);
        uEmail = (TextView) getActivity().findViewById(R.id.emailTVpro);
        uBlooadgrp =(TextView)getActivity().findViewById(R.id.bloodgrpTVpro);
        dob = (TextView) getActivity().findViewById(R.id.dobTvpro);


        ImageView imgBtn = getActivity().findViewById(R.id.EditproBtn);

        imgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),EditProfileactivity.class);
                startActivity(intent);
            }
        });
        myref.child("Users").child(uid).addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String name = dataSnapshot.child("Name").getValue().toString();
                String age = dataSnapshot.child("Age").getValue().toString();
                String height = dataSnapshot.child("Height").getValue().toString();
                String weight = dataSnapshot.child("Weight").getValue().toString();
                String gender = dataSnapshot.child("Gender").getValue().toString();

                String bloodgrp = dataSnapshot.child("Blood Group").getValue().toString() ;
                String udob = dataSnapshot.child("DOB").getValue().toString() ;

                uName.setText(name);
                uAge.setText(age);
                uEmail.setText(useremail);
                uGender.setText(gender);
                uHeight.setText(height);
                uWeight.setText(weight);
                uBlooadgrp.setText(bloodgrp);
                dob.setText(udob);


            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    @Override
    public void onStart() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleApiClient = new GoogleApiClient.Builder(getContext())
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
        mGoogleApiClient.connect();
        super.onStart();
    }



}
