package com.example.mhealthapp;

import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


public class sleepCounting extends Fragment {

    TextView timer ;
    Button start, stop, reset,back;
    long MillisecondTime, StartTime, TimeBuff, UpdateTime = 0L ;
    Handler handler;
    int Seconds, Minutes, MilliSeconds,Hour ;
    int flag =0;


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        timer = (TextView)getActivity().findViewById(R.id.tvTimer);
        start = (Button)getActivity().findViewById(R.id.startbtn);
        stop = (Button)getActivity().findViewById(R.id.stopbtn);
        // back = (Button)findViewById(R.id.backbtn);
        // reset = (Button)findViewById(R.id.btReset);

        handler = new Handler() ;

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(flag==0) {

                    StartTime = SystemClock.elapsedRealtime();
                    handler.postDelayed(runnable, 0);

                    // reset.setEnabled(false);
                    flag++;
                }
                else if(flag!=0){
                    MillisecondTime = 0L ;
                    StartTime = 0L ;
                    TimeBuff = 0L ;
                    UpdateTime = 0L ;
                    Seconds = 0 ;
                    Minutes = 0 ;
                    MilliSeconds = 0 ;

                    timer.setText("00:00:00");
                }


            }
        });

        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                TimeBuff += MillisecondTime;

                handler.removeCallbacks(runnable);

                // reset.setEnabled(true);

            }
        });
/*back.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Intent myIntent = new Intent(sleepCounting.this, MainActivity.class);
        startActivity(myIntent);
    }
});*/
/*        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                MillisecondTime = 0L ;
                StartTime = 0L ;
                TimeBuff = 0L ;
                UpdateTime = 0L ;
                Seconds = 0 ;
                Minutes = 0 ;
                MilliSeconds = 0 ;

                timer.setText("00:00:00");

            }
        });*/

    }

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_sleep_counting,null);
    }




    public Runnable runnable = new Runnable() {

        public void run() {

            MillisecondTime = SystemClock.elapsedRealtime() - StartTime;

            UpdateTime = TimeBuff + MillisecondTime;

            Seconds = (int) (UpdateTime / 1000);

            Minutes = Seconds / 60;
            Hour=Minutes/60;

            Seconds = Seconds % 60;

            MilliSeconds = (int) (UpdateTime % 1000);

            timer.setText(Hour+":" + Minutes + ":"
                    + String.format("%02d", Seconds)
                     );

            handler.postDelayed(this, 0);
        }

    };






}



