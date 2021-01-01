package com.example.mhealthapp;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

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

import java.util.HashMap;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class heartRateWithApi extends Fragment {


    private FirebaseAuth mAuth;

    private String statusOFuser ="";
    TextView uHrtrate , status , lbl;
    Button measure ;

    camActivity cam = new camActivity();
    String bpm , label;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        return inflater.inflate(R.layout.fragment_heart_rate_with_api,null);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        measure= getActivity().findViewById(R.id.Measurement);
        uHrtrate = getActivity().findViewById(R.id.BPMcount);
        PulseView pv = getActivity().findViewById(R.id.pv);
        pv.startPulse();




        measure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "Heartrate", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(),camActivity.class);

                startActivity(intent);

                bpm = getArguments().getString("BPM");
                uHrtrate.setText(bpm);


                int userBpm = Integer.parseInt(bpm);

                if(userBpm >60 && userBpm<140){


                    statusOFuser ="Normal";
                    status.setText(statusOFuser);
                }else{

                    statusOFuser="Not normal check doctor";
                    status.setText(statusOFuser);



                }
//                Bundle mArgs = getArguments();
//                label = mArgs.getString("LABEL");
//                lbl.setText(label);


            }
        });



        insertIntoDb(bpm,statusOFuser,label);


    }

    public void insertIntoDb(String bpm , String statusOFuser, String label){

        bpm = "";
        statusOFuser="";
        label="";
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        String userId = mAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference myRef = database.getReference("HeartRate").child(userId);


        Map newPost = new HashMap();
        newPost.put("Heart Rate",bpm);
        newPost.put("Status",statusOFuser);
        newPost.put("Label",label);

        myRef.setValue(newPost);

    }
//    @Override
//    public void applyTexts(String username, String password) {
//        textViewUsername.setText(username);
//        textViewPassword.setText(password);
//    }

}
