package com.example.mhealthapp;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.gigamole.library.PulseView;
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
public class HeartRate extends Fragment {


    private FirebaseAuth mAuth;

    private String statusOFuser = "";
    TextView uHrtrate, status,stats;
    Button measure;

    camActivity cam = new camActivity();
    String bpmS, label;
    int bpm;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        return inflater.inflate(R.layout.fragment_heart_rate, null);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mAuth = FirebaseAuth.getInstance();
        FirebaseDatabase mFirebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference myref = mFirebaseDatabase.getReference();
        FirebaseUser user = mAuth.getCurrentUser();

        String uid = user.getUid();

        measure = getActivity().findViewById(R.id.Measurement);
        uHrtrate = getActivity().findViewById(R.id.BPMcount);
        status = getActivity().findViewById(R.id.statusForheartrate);
        stats = getActivity().findViewById(R.id.VGraph);
        PulseView pv = getActivity().findViewById(R.id.pv);
        pv.startPulse();


        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myref = mFirebaseDatabase.getReference();
        uid = user.getUid();

        myref.child("HeartRate").child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String heartrate = dataSnapshot.child("Current HeartRate").getValue().toString();
                uHrtrate.setText(heartrate);

                String hrt = uHrtrate.getText().toString();
                int value = Integer.parseInt(hrt);

                if (value > 60 && value < 140) {
                    statusOFuser = "Normal";
                    status.setText(statusOFuser);
                } else {

                    statusOFuser = "Not normal check doctor";
                    status.setText(statusOFuser);


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        measure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), camActivity.class);
                startActivity(intent);

            }


        });
        stats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.flMain, new Graphview()).addToBackStack(null);
                ft.commit();

            }


        });
    }
}
