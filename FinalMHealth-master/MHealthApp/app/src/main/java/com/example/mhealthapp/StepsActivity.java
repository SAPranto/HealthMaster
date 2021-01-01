package com.example.mhealthapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class StepsActivity extends AppCompatActivity implements SensorEventListener {

    TextView tv_steps , distance;
    SensorManager sensorManager;
    Sensor sensor;
    String dist;
    boolean running = false;
    private long steps =0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_steps);

        tv_steps = (TextView) findViewById ( R.id.tv_steps );
        distance = (TextView) findViewById( R.id.distance_id);
        sensorManager = (SensorManager) getSystemService ( Context.SENSOR_SERVICE);
    }
    public float getDistanceRun(long steps){
        float distance = (float)(steps*78)/(float)100000;
        return distance;
    }

    @Override
    protected void onResume() {
        super.onResume ();
        running = true;
        Sensor countSensor = sensorManager.getDefaultSensor ( sensor.TYPE_STEP_COUNTER );
        if(countSensor!= null){
            sensorManager.registerListener ( this,countSensor,SensorManager.SENSOR_DELAY_UI );
        }else {
            Toast.makeText ( this,"SENSOR NOT FOUND",Toast.LENGTH_SHORT ).show ();
        }
    }
    @Override
    protected void onPause() {
        super.onPause ();
        running = false;
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
    protected void onStop() {
        super.onStop();
        sensorManager.unregisterListener(this,sensor);
    }



}
