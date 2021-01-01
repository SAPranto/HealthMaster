package com.example.mhealthapp;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.fitness.Fitness;
import com.google.android.gms.fitness.FitnessOptions;
import com.google.android.gms.fitness.data.DataSet;
import com.google.android.gms.fitness.data.DataType;
import com.google.android.gms.fitness.data.Field;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class StepsWithApiFrag extends Fragment  {

    private static double dis;

    private FirebaseAuth mAuth;

    public static final String TAG = "StepCounter";
    private static final int REQUEST_OAUTH_REQUEST_CODE = 0x1001;

    TextView stepcounter, dstMeasurre, goals, avg, totalSteps;
    EditText setGoalsEditText;
    String StringsetGoals;
    long numOfSetgoals , average,total ,totalStepsCount;


    Button setGoals, viewGraph ;

    public StepsWithApiFrag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_steps_with_api, container, false);
    }

    @Override


    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)  {


        stepcounter = getActivity().findViewById(R.id.tv_steps);
        dstMeasurre = getActivity().findViewById(R.id.distance_idTV);
        goals = (TextView) getActivity().findViewById(R.id.goalsStepsTV);
        avg = getActivity().findViewById(R.id.avgTV);
        setGoals = getActivity().findViewById(R.id.setGoalsbtn);
        viewGraph = getActivity().findViewById(R.id.graphBtn);
        setGoalsEditText =(EditText) getActivity().findViewById(R.id.setGoalsET);
        totalSteps = getActivity().findViewById(R.id.totalStepsTV);


        setGoals.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                StringsetGoals = setGoalsEditText.getText().toString();
                goals.setText(StringsetGoals);
                numOfSetgoals = Long.parseLong(StringsetGoals);
                setGoalsEditText.getText().clear();


            }
        });


        viewGraph.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // go to new activity to show graph
            }
        });


        FitnessOptions fitnessOptions = FitnessOptions.builder()
                .addDataType(DataType.TYPE_STEP_COUNT_CUMULATIVE)
                .addDataType(DataType.TYPE_CALORIES_EXPENDED)
                .addDataType(DataType.TYPE_STEP_COUNT_DELTA)
                .build();


        if (!GoogleSignIn.hasPermissions(GoogleSignIn.getLastSignedInAccount(getActivity()), fitnessOptions)) {
            googleSignIn(fitnessOptions);

        } else {
            subscribe();
        }

        readDataFromApiusingGoogleFit();


    }

    public void googleSignIn( FitnessOptions fitnessOptions){

        GoogleSignIn.requestPermissions(
                this,
                REQUEST_OAUTH_REQUEST_CODE,
                GoogleSignIn.getLastSignedInAccount(getActivity()),
                fitnessOptions);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_OAUTH_REQUEST_CODE) {
                subscribe();
            }
        }
    }




    public void subscribe() {


        Fitness.getRecordingClient(getActivity(), GoogleSignIn.getLastSignedInAccount(getActivity()))
                .subscribe(DataType.TYPE_STEP_COUNT_CUMULATIVE)
                .addOnCompleteListener(
                        new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                checktask( task);
                            }
                        });
    }


    public void checktask(@NonNull Task<Void> task){
        if (task.isSuccessful()) {
            Log.i(TAG, " subscribed!");
        } else {
            Log.w(TAG, " Problem subscribing.", task.getException());
        }


    }




    public  void dataCalculation(DataSet dataSet){

        int daycounter = 1;
        totalStepsCount = 0;

        total =
                dataSet.isEmpty()
                        ? 0
                        : dataSet.getDataPoints().get(0).getValue(Field.FIELD_STEPS).asInt();


        double distance = (total * .0008);
        String dis = String.valueOf(distance);
        stepcounter.setText(String.valueOf(total));
        dstMeasurre.setText(dis + " km");
        while(total != 0){
            totalStepsCount = totalStepsCount + total;

        }
        //day counting to calculate avg steps per day
        if (total == 0 ){
            daycounter = daycounter+1;
        }


        if(total == numOfSetgoals && total>0){
            Toast.makeText(getActivity(), "Hurrah You have reached Your Goal", Toast.LENGTH_SHORT).show();
        }
        //total
        totalSteps.setText(String.valueOf(totalStepsCount)+ "Steps");
        //avg
        int average =(int) Math.ceil (total /daycounter);
        avg.setText(String.valueOf(average)+"Steps /Day");

    }

    //firebaseDataEntry

    public void insertDataintoDatabase(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        String userId = mAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference myRef = database.getReference("StepsUSer").child(userId);

        String stepcString = String.valueOf(total);
        String avgString = String.valueOf(average);
        String TotalStepsTillnow = String.valueOf(totalStepsCount);

        Map newPost = new HashMap();
        newPost.put("Step Count",stepcString);
        newPost.put("Distance",dis);
        newPost.put("Average",avgString);
        newPost.put("TotalSteps tillNow",TotalStepsTillnow);

        myRef.setValue(newPost);
    }



    public void readDataFromApiusingGoogleFit() {

        Fitness.getHistoryClient(getActivity(), GoogleSignIn.getLastSignedInAccount(getActivity()))
                .readDailyTotal(DataType.TYPE_STEP_COUNT_DELTA)
                .addOnSuccessListener(
                        new OnSuccessListener<DataSet>() {
                            @Override
                            public void onSuccess(DataSet dataSet) {

                                dataCalculation(dataSet);
                                insertDataintoDatabase();


                            }
                        })
                .addOnFailureListener(
                        new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {

                                Toast.makeText(getActivity(), "Problem with stepCounting ", Toast.LENGTH_SHORT).show();

                                Log.w(TAG, "Problem getting the stepcounting value.", e);
                            }
                        });
    }
}
