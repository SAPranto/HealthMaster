package com.example.mhealthapp;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import java.util.HashMap;
import java.util.Map;

import static android.content.ContentValues.TAG;


/**
 * A simple {@link Fragment} subclass.
 */
public class EditProfile extends Fragment {

    private EditText nickName,age,height,weight,bloodGrp;

    private Button Submit;

    private FirebaseAuth mAuth;

    private FirebaseDatabase mFirebaseDatabase;

    private FirebaseAuth.AuthStateListener mAuthListner;
    private DatabaseReference myref;
    private String usersid;
    public EditProfile() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit_profile, container, false);
    }

    public void onViewCreated(View view, Bundle savedInstanceState){

        nickName = (EditText) getActivity().findViewById(R.id.nameETedit);
        bloodGrp = (EditText) getActivity().findViewById(R.id.bloodgrpETedit);
        age = (EditText) getActivity().findViewById(R.id.ageETedit);
        height = (EditText) getActivity().findViewById(R.id.heightETedit);
        weight = (EditText) getActivity().findViewById(R.id.weightETedit);

        mAuth = FirebaseAuth.getInstance();
        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myref = mFirebaseDatabase.getReference();
        final FirebaseUser user = mAuth.getCurrentUser();
        usersid = user.getUid();

        myref.child("Users").child(usersid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String nkName = dataSnapshot.child("First Name").getValue().toString();
                String bloodGrpp = dataSnapshot.child("Last Name").getValue().toString();

                String oage = dataSnapshot.child("Age").getValue().toString();

                String oheight = dataSnapshot.child("Height").getValue().toString();
                String oweight = dataSnapshot.child("Weight").getValue().toString();


                //dataSnapshot.getValue().toString();

                nickName.setText(nkName);
                bloodGrp.setText(bloodGrpp);
                age.setText(oage);

                height.setText(oheight);
                weight.setText(oweight);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w(TAG, "Failed to read value.", databaseError.toException());

            }
        });



        /////////////////////////////////////////////

        Submit = (Button) getActivity().findViewById(R.id.submitBtn);

        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                editconditions();

                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.flMain, new ProfileActivityFrag());
                ft.commit();
            }
        });



    }


    public void editProfile(){
        //Toast.makeText(this, sex, Toast.LENGTH_SHORT).show();

        String userId = mAuth.getCurrentUser().getUid();
        DatabaseReference current_user_db = FirebaseDatabase.getInstance().getReference().child("Users").child(userId);

        //Toast.makeText(this, userId, Toast.LENGTH_SHORT).show();

        String nkname = nickName.getText().toString();
        String bldgrp = bloodGrp.getText().toString();
        String editage = age.getText().toString();
        String editweight = weight.getText().toString();
        String editheight = height.getText().toString();

        Map newPost = new HashMap();
        newPost.put("name",nkname);
        newPost.put("userBloodgrp ",bldgrp);
        newPost.put("userageV",editage);
        newPost.put("userweightV:",editweight);
        newPost.put("userheightV",editheight);

        current_user_db.setValue(newPost);

        Toast.makeText(getActivity(), "Changes Saved", Toast.LENGTH_SHORT).show();


        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.flMain, new ProfileActivityFrag());
        ft.commit();

    }

    public void editconditions(){

        String eage = age.getText().toString();
        String eweight =weight.getText().toString();
        String nkname = nickName.getText().toString();
        String bldgrp = bloodGrp.getText().toString();

        String eheight =height.getText().toString();

        boolean cancel = false;
        View focusView = null;


        if(TextUtils.isEmpty(nkname)){

            Toast.makeText(getActivity(), "pLZ ENTER this field ", Toast.LENGTH_SHORT).show();
            focusView = nickName;
            cancel =true;
        }
        if(TextUtils.isEmpty(bldgrp)){
            Toast.makeText(getActivity(), "pLZ ENTER this field ", Toast.LENGTH_SHORT).show();
            focusView = bloodGrp;
            cancel = true;
        }
        if(TextUtils.isEmpty(eage)){
            Toast.makeText(getActivity(), "pLZ ENTER this field ", Toast.LENGTH_SHORT).show();
            focusView = age;
            cancel = true;
        }
        if(TextUtils.isEmpty(eweight)){
            Toast.makeText(getActivity(), "pLZ ENTER this field ", Toast.LENGTH_SHORT).show();
            focusView =weight;
            cancel = true;
        }
        if(TextUtils.isEmpty(eheight)){
            Toast.makeText(getActivity(), "pLZ ENTER this field ", Toast.LENGTH_SHORT).show();
            focusView = height;
            cancel = true;
        }


        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {

            editProfile();

        }
    }


}
