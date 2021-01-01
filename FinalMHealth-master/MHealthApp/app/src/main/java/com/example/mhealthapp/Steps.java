package com.example.mhealthapp;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass.
 */
public class Steps extends Fragment implements SensorEventListener{

    TextView tv_steps , distance;
    SensorManager sensorManager;
    Sensor sensor;
    String dist;
    boolean running = false;
    private long steps =0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ((HomeActivity2) getActivity()).setActionBarTitle("Steps");

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_steps, container, false);

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        tv_steps = (TextView) getActivity().findViewById ( R.id.tv_steps );
        distance = (TextView) getActivity().findViewById( R.id.distance_id);
        sensorManager = (SensorManager) getActivity().getSystemService ( Context.SENSOR_SERVICE);
       // startActivity(new Intent(getActivity(), StepsActivity.class));

    }
    public float getDistanceRun(long steps){
        float distance = (float)(steps*78)/(float)100000;
        return distance;
    }

    @Override
    public void onResume() {
        super.onResume ();
        running = true;
        Sensor countSensor = sensorManager.getDefaultSensor ( sensor.TYPE_STEP_COUNTER );
        if(countSensor!= null){
            sensorManager.registerListener ( this,countSensor,SensorManager.SENSOR_DELAY_UI );
        }else {
            Toast.makeText (getContext(),"SENSOR NOT FOUND",Toast.LENGTH_SHORT ).show ();
        }
    }
    @Override
    public void onSensorChanged(SensorEvent event) {

        Sensor sensor = event.sensor;
        float[] values = event.values;
        int value = -1;

        if (values.length > 0) {
            value = (int) values[0];
        }

        if (sensor.getType() == Sensor.TYPE_STEP_COUNTER) {
            steps++;
            tv_steps.setText ( String.valueOf ( event.values[0] ) );
        }
        /*if (running){

        }*/
        dist = Float.toString(getDistanceRun(steps));
        distance.setText(dist);

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
    @Override
    public void onStop() {
        super.onStop();
        sensorManager.unregisterListener(this,sensor);
    }



}
