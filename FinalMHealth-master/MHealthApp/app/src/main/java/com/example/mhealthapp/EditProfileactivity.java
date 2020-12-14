package com.example.mhealthapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

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

public class EditProfileactivity extends AppCompatActivity  {

    private EditText nickName, age, height, weight;

    private Button Submit;

    private FirebaseAuth mAuth;

    private FirebaseDatabase mFirebaseDatabase;

    private FirebaseAuth.AuthStateListener mAuthListner;
    private DatabaseReference myref;
    private String usersid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_edit_profile);

        nickName = (EditText) findViewById(R.id.nameETedit);
        age = (EditText) findViewById(R.id.ageETedit);
        height = (EditText) findViewById(R.id.heightETedit);
        weight = (EditText) findViewById(R.id.weightETedit);

        mAuth = FirebaseAuth.getInstance();
        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myref = mFirebaseDatabase.getReference();
        final FirebaseUser user = mAuth.getCurrentUser();
        usersid = user.getUid();

        myref.child("Users").child(usersid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String nkName = dataSnapshot.child("Name").getValue().toString();
                String oage = dataSnapshot.child("Age").getValue().toString();
                String oheight = dataSnapshot.child("Height").getValue().toString();
                String oweight = dataSnapshot.child("Weight").getValue().toString();

                nickName.setText(nkName);
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

        Submit = (Button) findViewById(R.id.submitBtn);

        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                editconditions();

            }
        });


    }

    public void editProfile() {
        //Toast.makeText(this, sex, Toast.LENGTH_SHORT).show();

        String userId = mAuth.getCurrentUser().getUid();
        DatabaseReference current_user_db = FirebaseDatabase.getInstance().getReference().child("Users").child(userId);

        //Toast.makeText(this, userId, Toast.LENGTH_SHORT).show();

        String nkname = nickName.getText().toString();
        String editage = age.getText().toString();
        String editweight = weight.getText().toString();
        String editheight = height.getText().toString();

        current_user_db.child("Name").setValue(nkname);
        current_user_db.child("Age").setValue(editage);
        current_user_db.child("Weight").setValue(editweight);
        current_user_db.child("Height").setValue(editheight);



        Toast.makeText(this, "Changes Saved", Toast.LENGTH_SHORT).show();


    }

    public void editconditions() {

        String eage = age.getText().toString();
        String eweight = weight.getText().toString();
        String nkname = nickName.getText().toString();

        String eheight = height.getText().toString();

        boolean cancel = false;
        View focusView = null;


        if (TextUtils.isEmpty(nkname)) {

            Toast.makeText(this, "Please ENTER this field", Toast.LENGTH_SHORT).show();
            focusView = nickName;
            cancel = true;
        }

        if (TextUtils.isEmpty(eage)) {
            Toast.makeText(this, "Please ENTER this field", Toast.LENGTH_SHORT).show();
            focusView = age;
            cancel = true;
        }
        if (TextUtils.isEmpty(eweight)) {
            Toast.makeText(this, "Please ENTER this field", Toast.LENGTH_SHORT).show();
            focusView = weight;
            cancel = true;
        }
        if (TextUtils.isEmpty(eheight)) {
            Toast.makeText(this, "Please ENTER this field", Toast.LENGTH_SHORT).show();
            focusView = height;
            cancel = true;
        }
        //For error
        if (cancel) {
            focusView.requestFocus();
        } else {

            editProfile();

        }
    }
}
