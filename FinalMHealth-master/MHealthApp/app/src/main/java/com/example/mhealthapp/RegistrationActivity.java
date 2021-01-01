package com.example.mhealthapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;


public class RegistrationActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    private Button submit;
    private FirebaseAuth firebaseAuth;
    private EditText nickName, age, height, weight, bloodgroup, DOB, other;
    private RadioGroup gender, heartdiseas, diabatic, bloodprs, pregnant;
    private RadioButton genderBtn, heartDiseaseBtn, diabaticBtn, bloodprsrBtn, pregnantBtn;
    String name, email, userageV, userweightV, userheightV, usergenderV, userDOB, userDiabatic, userBloodprsr, userHeartdisease, userPregnant, userOther, userBloodgrp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        setupViews();

    }


    public void SubmitTodatabase(View view) {

        if (validate()) {
            firebaseAuth = FirebaseAuth.getInstance();
           // sendemailverification();
            sendUserdata();
            Toast.makeText(RegistrationActivity.this, "Success fully entered the data", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(RegistrationActivity.this, HomeActivity2.class));


        } else {

            Toast.makeText(RegistrationActivity.this, "send Data Error", Toast.LENGTH_SHORT).show();
        }


    }


    private void sendUserdata() {

       String userId = firebaseAuth.getCurrentUser().getUid();
       DatabaseReference current_user_db = FirebaseDatabase.getInstance().getReference().child("Users").child(userId);
        int selectedId1 = gender.getCheckedRadioButtonId();
        genderBtn = (RadioButton) findViewById(selectedId1);

        int selectedId2 = heartdiseas.getCheckedRadioButtonId();
        heartDiseaseBtn = (RadioButton) findViewById(selectedId2);

        int selectedId3 = diabatic.getCheckedRadioButtonId();
        diabaticBtn = (RadioButton) findViewById(selectedId3);

        int selectedId4 = bloodprs.getCheckedRadioButtonId();
        bloodprsrBtn = (RadioButton) findViewById(selectedId4);

        int selectedId5 = pregnant.getCheckedRadioButtonId();
        pregnantBtn = (RadioButton) findViewById(selectedId5);



      name = nickName.getText().toString();
      userageV = age.getText().toString();
      usergenderV = genderBtn.getText().toString();
      userheightV = height.getText().toString();
      userweightV = weight.getText().toString();
      userDiabatic = diabaticBtn.getText().toString();

      userBloodprsr = bloodprsrBtn.getText().toString();

      userHeartdisease = heartDiseaseBtn.getText().toString();

      userPregnant = pregnantBtn.getText().toString();

      userOther = other.getText().toString();
      userBloodgrp = bloodgroup.getText().toString();
      userDOB = DOB.getText().toString();

       Map newPost = new HashMap();
        newPost.put("Name",name);
        newPost.put("Age",userageV);
        newPost.put("DOB",userDOB);
        newPost.put("Gender",usergenderV);
        newPost.put("Weight",userweightV);
        newPost.put("Height",userheightV);
        newPost.put("Other",userOther);
        newPost.put("Diabetes",userDiabatic);
        newPost.put("Blood Pressure",userBloodprsr);
        newPost.put("Blood Group",userBloodgrp);
        newPost.put("Pregnant",userPregnant);
        newPost.put("Heart Disease",userHeartdisease);

        current_user_db.setValue(newPost);

    }

    public Boolean validate() {

        Boolean res = false;

        int selectedId1 = gender.getCheckedRadioButtonId();
        genderBtn = (RadioButton) findViewById(selectedId1);

        int selectedId2 = heartdiseas.getCheckedRadioButtonId();
        heartDiseaseBtn = (RadioButton) findViewById(selectedId2);

        int selectedId3 = diabatic.getCheckedRadioButtonId();
        diabaticBtn = (RadioButton) findViewById(selectedId3);

        int selectedId4 = bloodprs.getCheckedRadioButtonId();
        bloodprsrBtn = (RadioButton) findViewById(selectedId4);

        int selectedId5 = pregnant.getCheckedRadioButtonId();
        pregnantBtn = (RadioButton) findViewById(selectedId5);


        name = nickName.getText().toString();
        userageV = age.getText().toString();
        usergenderV = genderBtn.getText().toString();
        userheightV = height.getText().toString();
        userweightV = weight.getText().toString();
        userDiabatic = diabaticBtn.getText().toString();

        userBloodprsr = bloodprsrBtn.getText().toString();

        userHeartdisease = heartDiseaseBtn.getText().toString();

        userPregnant = pregnantBtn.getText().toString();

        userOther = other.getText().toString();
        userBloodgrp = bloodgroup.getText().toString();
        userDOB = DOB.getText().toString();

        if (name.isEmpty() || userBloodgrp.isEmpty() || userageV.isEmpty() || userweightV.isEmpty() || userheightV.isEmpty() || usergenderV.isEmpty() || userDOB.isEmpty() || userOther.isEmpty()) {
            Toast.makeText(this, "Please Enter All The Details!", Toast.LENGTH_LONG).show();
            res = false;
        } else {
            res = true;
        }

        return res;

    }


    private void setupViews() {
        nickName = (EditText) findViewById(R.id.nickNameET);
        submit = (Button) findViewById(R.id.submitBtn);
        age = (EditText) findViewById(R.id.AgeET);
        weight = (EditText) findViewById(R.id.WeightET);
        height = (EditText) findViewById(R.id.heightET);

        bloodgroup = (EditText) findViewById(R.id.bloodGroupET);
        other = (EditText) findViewById(R.id.othersET);
        gender = (RadioGroup) findViewById(R.id.genderRG);
        heartdiseas = (RadioGroup) findViewById(R.id.HeartDiseaseRG);
        diabatic = (RadioGroup) findViewById(R.id.diabaticRG);
        bloodprs = (RadioGroup) findViewById(R.id.BloodPressureRG);
        pregnant = (RadioGroup) findViewById(R.id.PragnantRG);

        DOB = (EditText) findViewById(R.id.DOBET);
        DOB.setFocusable(false);
        DOB.setKeyListener(null);
        DOB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment datepicker = new DatePickerFragment();
                datepicker.show(getSupportFragmentManager(), "date picker");
            }
        });
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, day);
        String currentDateString = DateFormat.getDateInstance().format(c.getTime());
        DOB = (EditText) findViewById(R.id.DOBET);
        DOB.setText(currentDateString);
    }
}
