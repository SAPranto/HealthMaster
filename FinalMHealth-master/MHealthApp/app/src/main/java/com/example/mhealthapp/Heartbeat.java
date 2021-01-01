package com.example.mhealthapp;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.Viewport;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.Random;


/**
 * A simple {@link Fragment} subclass.
 */
public class Heartbeat extends Fragment {


    public Heartbeat() {
        // Required empty public constructor
    }


    private static final Random rand = new Random();
    private LineGraphSeries<DataPoint> series;
    private int lastX = 0;
    public boolean isRunning =true;

    private TextView uName,  uAge;
    private DatabaseReference ref;
    private FirebaseAuth auth;
    private String curUid;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ((HomeActivity2)getActivity()).setActionBarTitle("Heart Rate");
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_heartbeat, container, false);
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {


        auth = FirebaseAuth.getInstance();
        curUid = auth.getCurrentUser().getUid();

        ref = FirebaseDatabase.getInstance().getReference().child(curUid);
        uName = (TextView) getActivity().findViewById(R.id.uNameTVhr);
        uAge = (TextView) getActivity().findViewById(R.id.uAgeTVhr);


        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String name = dataSnapshot.child("username").getValue().toString();
                    String age = dataSnapshot.child("userage").getValue().toString();


                    uName.setText(name);
                    uAge.setText(age);

                } else {
                    Toast.makeText(getActivity(), "Error or no data in database", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        // Using the GGraphView Library
        GraphView graph = (GraphView) getActivity().findViewById(R.id.graph);

        //Create the Datapoins
        series = new LineGraphSeries<DataPoint>();
        graph.addSeries(series);

        // customize the viewport
        Viewport viewport = graph.getViewport();
        viewport.setYAxisBoundsManual(true);
        viewport.setMinY(60);
        viewport.setMaxY(100);
        viewport.setScrollable(true);

        // Creating onClickListener for the "RUN" Button
        Button button = (Button) getActivity().findViewById(R.id.run);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                isRunning=true;

                // Create a thread to run the graph
                new Thread(new Runnable() {
                    @Override
                    public void run() {

                        while (isRunning==true) {
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    addEntry();
                                }
                            });
                            // using sleep to slow down the addition of new points
                            try {
                                Thread.sleep(500);
                            } catch (InterruptedException e) {
                                System.out.print(e.getMessage());
                            }
                        }
                    }
                }).start();
            }
        });

        // Creating onClickListener for the "STOP" Button
        Button button1 = (Button) getActivity().findViewById(R.id.stop);
        button1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                isRunning=false;
            }
        });
    }

    // Method that adds new points to the graph
    private void addEntry() {
        series.appendData(new DataPoint(lastX++,60+ rand.nextDouble() * 40d), true, 10);
    }





}
