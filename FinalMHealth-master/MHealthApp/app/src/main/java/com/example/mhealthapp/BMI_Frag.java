package com.example.mhealthapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class BMI_Frag extends Fragment {

    TextView height , weight , tips1,tips2 ,helloTextView;
    RatingBar ratingBar;
    double doubleHeight=0;
    double doubleWeight=0;

    private FirebaseAuth mAuth;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myref;
    private String uid;


    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_bmi__frag,null);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myref = mFirebaseDatabase.getReference();
        final FirebaseUser user = mAuth.getCurrentUser();
        uid = user.getUid();
        ratingBar = (RatingBar)getActivity().findViewById(R.id.rating_bar);
        height = (TextView) getActivity().findViewById(R.id.height);
        weight = (TextView) getActivity().findViewById(R.id.weight);
        tips1 = (TextView) getActivity().findViewById(R.id.tips_view_id1);
        tips2 = (TextView) getActivity().findViewById(R.id.tips_view_id2);
        helloTextView = (TextView) getActivity().findViewById(R.id.text_view_id);

        myref.child("Users").child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String Sheight = dataSnapshot.child("Height").getValue().toString();
                String Sweight = dataSnapshot.child("Weight").getValue().toString();

                height.setText(Sheight);
                weight.setText(Sweight);

                doubleHeight= Double.parseDouble(Sheight);
                doubleWeight = Double.parseDouble(Sweight);

                double bmi = (doubleWeight/((doubleHeight*doubleHeight)/10000));

                helloTextView.setText("BMI: "+String.format("%.2f",bmi));
                bmiRatings(bmi);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public void bmiRatings(double bmi){

        int level =0 ;

        if(bmi < 18.5){
            ratingBar.setRating((float)1.0);
            level =1;
        }
        else if(bmi > 18.4 && bmi <=25) {
            ratingBar.setRating((float)2.0);
            level = 2;
        }
        else if(bmi > 25 && bmi <=30) {
            ratingBar.setRating((float)3.0);
            level =3;
        }
        else if(bmi > 30 && bmi <=35) {
            ratingBar.setRating((float)4.0);
            level =4;
        }
        else if(bmi > 35 && bmi <=40) {
            ratingBar.setRating((float)5.0);
            level =5;
        }
        else if(bmi > 40) {
            ratingBar.setRating((float)6.0);
            level =6;
        }

        if (level == 1){
            tips1.setText("Underweight");
            tips2.setText("Add some calories to your food habits.\n" +
                            "Eat healthy unsaturated fats such as almonds\n" +"Add carbs and protein to your diet\n");
        }
        else if (level == 2){
            tips1.setText("Healthy Weight");
            tips2.setText("Maintain this weight to keep up with it. " +
                            "Have healthy, nutritional balanced diet ");
        }
        else if (level == 3){
            tips1.setText("Overweight");
            tips2.setText("Avoid junk foods, soda.\n"+
                    " Add Vegetables to your diet such as Cucumber \n"
                    +"Try to Exercise daily: Walking, Swimming , Running");
        }
        else if (level == 4) {
            tips1.setText("Obese");
            tips2.setText("Reduce Carbs to your diet. Avoid Junk Food\n" +
                    "Have olive oil, instead of soyabean oil\n" +
                    "Don't lose hope! Exercise can bring lots of changes\n");
        }
        else if (level == 5) {
            tips1.setText("Very Obese");
            tips2.setText("Reduce Carbs to your diet. Avoid Junk Food\n" +
                    "Have olive oil, instead of soyabean oil\n" +
                    "Don't lose hope! Exercise can bring lots of changes" +
                    "Aerobics will burn calories faster\n");
        }
        else if (level == 6) {
            tips1.setText(" Extremely Obese.");
            tips2.setText("Reduce Carbs to your diet." +
                    " Avoid Junk Food\n" +
                    " Avoid Red Meat Totally\n" +
                    "Have olive oil, instead of soyabean oil\n" +
                    "Don't lose hope! Exercise can bring lots of changes" +
                    "Aerobics and Plank will burn calories faster\n");
        }

    }

}









