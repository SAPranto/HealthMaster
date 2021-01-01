package com.example.mhealthapp;


import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TimePicker;

import androidx.fragment.app.FragmentTransaction;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;


/**
 * A simple {@link Fragment} subclass.
 */
public class ReminderActivityFrag extends Fragment {

    private static final String TAG = "ReminderActivityFrag";

    DatabaseHelper mdDatabaseHelper;
    int hour,min;

    EditText medname , medId;
    Button Time1 , Time2 ,Time3 , save , delmed;
    Spinner dose , duration, med ,food ;
    String format ,id, food_p, dose_p ,timeset1, timeset2 ,timeset3  , newEntry;
    TextView Reminder1,Reminder2,Reminder3;
    Calendar c;
    public ReminderActivityFrag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ((HomeActivity2)getActivity()).setActionBarTitle("Reminder");

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_reminder_activity, container, false);

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        setUpview();

        mdDatabaseHelper = new DatabaseHelper(getActivity());
        c = Calendar.getInstance();
        hour = c.get(Calendar.HOUR_OF_DAY);
        min = c.get(Calendar.MINUTE);

        selectTimeFormat(hour);
        Set_Time1();
        Set_Time2();
        Set_Time3();

        Spinner_Dose();
        Spinner_Food();
        SaveMed();
        DeleteMed();
    }

    //Morning Time
    public void Set_Time1(){
        Time1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(),
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker timePicker, int i, int i1) {
                                Reminder1= (TextView)getActivity().findViewById(R.id.Txttime1);
                                Reminder1.setText(i + ":" + i1 + " " + format);
                                timeset1 = Reminder1.getText().toString();
                                c.set(Calendar.HOUR_OF_DAY, i);
                                c.set(Calendar.MINUTE, i1);
                                c.set(Calendar.SECOND, 0);
                                startAlarm(c);
                            }
                        },hour,min,true);
                timePickerDialog.show();
            }
        });

    }
    //Afternoon Time
    public void Set_Time2(){
        Time2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int i, int i1) {
                        Reminder2 = (TextView)getActivity().findViewById(R.id.Txttime2);
                        Reminder2.setText(i + ":" + i1 + " " + format);
                        timeset2 = Reminder2.getText().toString();
                        c.set(Calendar.HOUR_OF_DAY, i);
                        c.set(Calendar.MINUTE, i1);
                        c.set(Calendar.SECOND, 0);
                        startAlarm(c);
                    }
                },hour,min,true);
                timePickerDialog.show();
            }
        });

    }

    //Dinner Time
    public void Set_Time3(){
        Time3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(),
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker timePicker, int i, int i1) {
                                Reminder3 = (TextView)getActivity().findViewById(R.id.Txttime3);
                                Reminder3.setText(i + ":" + i1 + " " + format);
                                timeset3 = Reminder3.getText().toString();
                                c.set(Calendar.HOUR_OF_DAY, i);
                                c.set(Calendar.MINUTE, i1);
                                c.set(Calendar.SECOND, 0);
                                startAlarm(c);
                            }
                        },hour,min,true);
                timePickerDialog.show();
            }
        });
    }


    //Spinner of dose : Half/full/quarter medicines
    public void Spinner_Dose(){
        ArrayAdapter<CharSequence> adapter =ArrayAdapter.createFromResource(getActivity().getBaseContext(),R.array.dose,
                android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dose.setAdapter(adapter);
        dose.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                dose_p = adapterView.getItemAtPosition(i).toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }



    //Spinner of food : Bf or Af
    public void Spinner_Food(){
        ArrayAdapter<CharSequence> adapter4 =ArrayAdapter.createFromResource(getActivity().getBaseContext(),R.array.food,
                android.R.layout.simple_spinner_item);
        adapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        food.setAdapter(adapter4);
        food.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                food_p = adapterView.getItemAtPosition(i).toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    public void SaveMed(){
        //When All Data are Saved
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                newEntry = medname.getText().toString();


                if (medname.length()!= 0){
                    AddData(newEntry,dose_p,food_p,timeset1,timeset2,timeset3);
                    medname.setText(" ");
                    FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.flMain, new ListViewFragment()).addToBackStack(null);
                    ft.commit();
                }else{
                    Toast("Please Enter The Details");
                }
            }
        });

    }

    //Delete Medicine
    public void DeleteMed() {
        delmed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                id = medId.getText().toString();
                Integer deleteRows = mdDatabaseHelper.deleteData(id);
                if(deleteRows > 0){
                    Toast.makeText(getActivity(), "Data Deleted", Toast.LENGTH_LONG).show();
                    FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.flMain, new ListViewFragment());
                    ft.commit();
                }else{
                    Toast.makeText(getActivity(), "Please Enter Medicine ID", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    //Alarm is initiated here
    public void startAlarm(Calendar c){
        AlarmManager alarmManager = (AlarmManager)getActivity().getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(getActivity(), AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getContext(), 1, intent, 0);
        if (c.before(Calendar.getInstance())) {
            c.add(Calendar.DATE, 1);
        }
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(),pendingIntent);
        Toast.makeText(getActivity(), "Done!", Toast.LENGTH_SHORT).show();
    }

    //Time Format
    public void selectTimeFormat( int hour){

        if (hour == 0){
            hour += 12;
            format = "AM";
        }else if(hour == 12){
            format = "PM";
        }else if(hour>12){
            hour -= 12;
            format = "PM";
        }else{
            format = "AM";
        }
    }

    //Saving data local storage
    public void AddData(String newEntry, String dose ,String food,String time1,String time2,String time3){
        boolean insertData = mdDatabaseHelper.addData(newEntry,dose,food,time1 ,time2,time3);
        try{
            if(insertData == true){
                Toast("Data Successfully Inserted");
            }else{
                Toast("Something went wrong");
            }
        }catch(Exception ioe)
        {
            ioe.printStackTrace();
        }
    }

    //Toast Method
    public void Toast (String msg){
        Toast.makeText(getActivity(),msg,Toast.LENGTH_SHORT).show();
    }

    //All id's are declared
    public void setUpview(){
        medname = (EditText)getActivity().findViewById(R.id.medicinename);
        dose = (Spinner) getActivity().findViewById(R.id.spinnerDose);
        save = (Button)getActivity().findViewById(R.id.buttonRemSubmit);
        Time1 = (Button)getActivity().findViewById(R.id.set1);
        Time2 = (Button)getActivity().findViewById(R.id.set2);
        Time3 = (Button)getActivity().findViewById(R.id.set3);
        food = (Spinner)getActivity().findViewById(R.id.spinnerfood);
        delmed = (Button) getActivity().findViewById(R.id.del_MED);
        medId = (EditText)getActivity().findViewById(R.id.med_id);
    }
}