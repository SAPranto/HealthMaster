package com.example.mhealthapp;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.Calendar;

public class Food_diary  extends Fragment {

    TextView addBreakfast,addLunch,addSnack,addDinner ,
            Food_NameB ,Food_SubB ,Food_calB,
            Food_NameL ,Food_SubL ,Food_calL,
            Food_NameS ,Food_SubS ,Food_calS,
            Food_NameD ,Food_SubD ,Food_calD,
            Calories;
    private View mainView;
    private Cursor listCursor;

    // Holder for buttons on toolbar
    private String currentId;
    private String currentName;

    // Holding variables
    private String currentDateYear = "";
    private String currentDateMonth = "";
    private String currentDateDay = "";
    private String stringFdDate ="";
    private String fdCalB = "";
    private String fdCalL = "";
    private String fdCalS = "";
    private String fdCalD = "";
    double fd_cal_B = 0;
    double fd_cal_L = 0;
    double fd_cal_S = 0;
    double fd_cal_D = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ((HomeActivity2) getActivity()).setActionBarTitle("Food & Nutritions");
        // Inflate the layout for this fragment
        mainView = inflater.inflate(R.layout.activity_food_diary, container, false);
        return mainView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /* Set title */
        ((HomeActivity2)getActivity()).getSupportActionBar().setTitle("Food Diary");
        setUpViews();
        initalizeHome();
    }


    public void setUpViews(){
        addBreakfast = (TextView) getActivity().findViewById(R.id.addB);
        addLunch = (TextView) getActivity().findViewById(R.id.addL);
        addSnack = (TextView) getActivity().findViewById(R.id.addS);
        addDinner = (TextView) getActivity().findViewById(R.id.addD);

        Food_NameB = (TextView)getActivity().findViewById(R.id.textViewBreakfastItemsName);
        Food_NameL = (TextView)getActivity().findViewById(R.id.textViewLunchItemsName);
        Food_NameS = (TextView)getActivity().findViewById(R.id.textViewSnacksItemsName);
        Food_NameD = (TextView)getActivity().findViewById(R.id.textViewDinnerItemsName);

        Food_SubB = (TextView)getActivity().findViewById(R.id.textViewBreakfastItemsSub);
        Food_SubL = (TextView)getActivity().findViewById(R.id.textViewLunchItemsSub);
        Food_SubS = (TextView)getActivity().findViewById(R.id.textViewSnacksItemsSub);
        Food_SubD = (TextView)getActivity().findViewById(R.id.textViewDinnerItemsSub);

        Food_calB = (TextView)getActivity().findViewById(R.id.textViewBreakfastItemsEnergy);
        Food_calL = (TextView)getActivity().findViewById(R.id.textViewLunchItemsEnergy);
        Food_calS = (TextView)getActivity().findViewById(R.id.textViewSnacksItemsEnergy);
        Food_calD = (TextView)getActivity().findViewById(R.id.textViewDinnerItemsEnergy);

    }

    // Changing view method in fragmetn
    private void setMainView(int id){
        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mainView = inflater.inflate(id, null);
        ViewGroup rootView = (ViewGroup) getView();
        rootView.removeAllViews();
        rootView.addView(mainView);
    }

    private void initalizeHome(){
        /* Find date */
        if(currentDateYear.equals("") || currentDateMonth.equals("") || currentDateDay.equals("")) {
            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            // Year
            currentDateYear = "" + year;

            // Month
            month = month+1; // Month starts with 0
            if(month < 10){
                currentDateMonth = "0" + month;
            }
            else{
                currentDateMonth = "" + month;
            }
            // Day
            if(day < 10){
                currentDateDay = "0" + day;
            }
            else{
                currentDateDay = "" + day;
            }
        }
        stringFdDate = currentDateYear + "-" + currentDateMonth + "-" + currentDateDay;

        updateTableB(stringFdDate, "0");
        updateTableL(stringFdDate, "1");
        updateTableS(stringFdDate, "2");
        updateTableD(stringFdDate, "3");

        /* Fill table */

        addBreakfast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addFoodB(0);
            }
        });
        addLunch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addFoodL(1);
            }
        });
        addSnack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addFoodS(2);
            }
        });
        addDinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addFoodD(3);
            }
        });
        Calories = (TextView)getActivity().findViewById(R.id.cal_no);
        TextView Remaining_Calories  = (TextView)getActivity().findViewById(R.id.cal_left_no);


        double cal_no = fd_cal_B + fd_cal_D + fd_cal_L + fd_cal_S;
        double remaining = (5170)-(cal_no);
        Calories.setText(Double.toString(cal_no));
        Remaining_Calories.setText(Double.toString(remaining));

    } // initalizeHome

    private void updateTableB(String stringDate, String stringMealNumber){

        /* Database */
        DBAdapter db = new DBAdapter(getActivity());
        db.open();

        String inpFields = "_id, fd_id, fd_date,food_title,food_measurement,food_unit, fd_meal_number,fd_cal";

        // Select
        String fields[] = new String[] {
                "_id",
                "fd_id",
                "fd_date",
                "food_measurement",
                "food_unit",
                "fd_meal_number",
                "fd_cal"
        };
        String stringDateSQL = db.quoteSmart(stringDate);
        Cursor cursorFd = db.select("food_diary_Breakfast", fields, "fd_date", stringDateSQL);

        // Select for food name
        String fieldsFood[] = new String[] {
                "_id",
                "food_title",
        };
        Cursor cursorFood;

        // Loop trough cursor
        int intCursorFdCount = cursorFd.getCount();
        for(int x=0;x<intCursorFdCount;x++){
            String stringFdId = cursorFd.getString(0);
            //Toast.makeText(getActivity(), "ID: " + stringFdId, Toast.LENGTH_SHORT).show();

            // Variables from food diary
            String fdFoodId = cursorFd.getString(1);
            String fdFoodIdSQL = db.quoteSmart(fdFoodId);

            String fdMeasurement = cursorFd.getString(3);
            String fdUnit = cursorFd.getString(4);
            String fdMealNo = cursorFd.getString(5);
            fdCalB = cursorFd.getString(6);

            // Get food name
            cursorFood = db.select("food", fieldsFood, "_id", fdFoodIdSQL);

            // Variables from food
            String foodID = cursorFood.getString(0);
            String foodName = cursorFood.getString(1);
            String subLine = fdMeasurement + " " + fdUnit;

            Food_NameB.setText(foodName);
            Food_calB.setText(fdCalB);
            Food_SubB.setText(subLine);

            fd_cal_B = Double.parseDouble(fdCalB);

            cursorFd.moveToNext();
        }

        /* Close db */
        db.close();
    } // updateTable

    private void updateTableL(String stringDate, String stringMealNumber){

        /* Database */
        DBAdapter db = new DBAdapter(getActivity());
        db.open();

        String inpFields = "_id, fd_id, fd_date,food_title,food_measurement,food_unit, fd_meal_number,fd_cal";

        // Select
        String fields[] = new String[] {
                "_id",
                "fd_id",
                "fd_date",
                "food_measurement",
                "food_unit",
                "fd_meal_number",
                "fd_cal"
        };
        String stringDateSQL = db.quoteSmart(stringDate);
        Cursor cursorFd = db.select("food_diary_Lunch", fields, "fd_date", stringDateSQL);

        // Select for food name
        String fieldsFood[] = new String[] {
                "_id",
                "food_title",
        };
        Cursor cursorFood;

        // Loop trough cursor
        int intCursorFdCount = cursorFd.getCount();
        for(int x=0;x<intCursorFdCount;x++){
            String stringFdId = cursorFd.getString(0);
            //Toast.makeText(getActivity(), "ID: " + stringFdId, Toast.LENGTH_SHORT).show();

            // Variables from food diary
            String fdFoodId = cursorFd.getString(1);
            String fdFoodIdSQL = db.quoteSmart(fdFoodId);

            String fdMeasurement = cursorFd.getString(3);
            String fdUnit = cursorFd.getString(4);
            String fdMealNo = cursorFd.getString(5);
            fdCalL = cursorFd.getString(6);

            // Get food name
            cursorFood = db.select("food", fieldsFood, "_id", fdFoodIdSQL);

            // Variables from food
            String foodID = cursorFood.getString(0);
            String foodName = cursorFood.getString(1);
            String subLine = fdMeasurement + " " + fdUnit;

            cursorFd.moveToNext();

            Food_NameL.setText(foodName);
            Food_calL.setText(fdCalL);
            Food_SubL.setText(subLine);

            fd_cal_L = Double.parseDouble(fdCalL);
        }


        /* Close db */
        db.close();
    } // updateTable
    private void updateTableS(String stringDate, String stringMealNumber){

        /* Database */
        DBAdapter db = new DBAdapter(getActivity());
        db.open();

        String inpFields = "_id, fd_id, fd_date,food_title,food_measurement,food_unit, fd_meal_number,fd_cal";

        // Select
        String fields[] = new String[] {
                "_id",
                "fd_id",
                "fd_date",
                "food_measurement",
                "food_unit",
                "fd_meal_number",
                "fd_cal"
        };
        String stringDateSQL = db.quoteSmart(stringDate);
        Cursor cursorFd = db.select("food_diary_Snacks", fields, "fd_date", stringDateSQL);

        // Select for food name
        String fieldsFood[] = new String[] {
                "_id",
                "food_title",
        };
        Cursor cursorFood;

        // Loop trough cursor
        int intCursorFdCount = cursorFd.getCount();
        for(int x=0;x<intCursorFdCount;x++){
            String stringFdId = cursorFd.getString(0);
            //Toast.makeText(getActivity(), "ID: " + stringFdId, Toast.LENGTH_SHORT).show();

            // Variables from food diary
            String fdFoodId = cursorFd.getString(1);
            String fdFoodIdSQL = db.quoteSmart(fdFoodId);

            String fdMeasurement = cursorFd.getString(3);
            String fdUnit = cursorFd.getString(4);
            String fdMealNo = cursorFd.getString(5);
            fdCalS = cursorFd.getString(6);

            // Get food name
            cursorFood = db.select("food", fieldsFood, "_id", fdFoodIdSQL);

            // Variables from food
            String foodID = cursorFood.getString(0);
            String foodName = cursorFood.getString(1);
            String subLine = fdMeasurement + " " + fdUnit;

            Food_NameS.setText(foodName);
            Food_calS.setText(fdCalS);
            Food_SubS.setText(subLine);

            fd_cal_S = Double.parseDouble(fdCalS);

            cursorFd.moveToNext();
        }


        /* Close db */
        db.close();
    } // updateTable




    private void updateTableD(String stringDate, String stringMealNumber){

        /* Database */
        DBAdapter db = new DBAdapter(getActivity());
        db.open();

        String inpFields = "_id, fd_id, fd_date,food_title,food_measurement,food_unit, fd_meal_number,fd_cal";

        // Select
        String fields[] = new String[] {
                "_id",
                "fd_id",
                "fd_date",
                "food_measurement",
                "food_unit",
                "fd_meal_number",
                "fd_cal"
        };
        String stringDateSQL = db.quoteSmart(stringDate);
        Cursor cursorFd = db.select("food_diary_Dinner", fields, "fd_date", stringDateSQL);

        // Select for food name
        String fieldsFood[] = new String[] {
                "_id",
                "food_title",
        };
        Cursor cursorFood;

        // Loop trough cursor
        int intCursorFdCount = cursorFd.getCount();
        for(int x=0;x<intCursorFdCount;x++){
            String stringFdId = cursorFd.getString(0);
            //Toast.makeText(getActivity(), "ID: " + stringFdId, Toast.LENGTH_SHORT).show();

            // Variables from food diary
            String fdFoodId = cursorFd.getString(1);
            String fdFoodIdSQL = db.quoteSmart(fdFoodId);

            String fdMeasurement = cursorFd.getString(3);
            String fdUnit = cursorFd.getString(4);
            String fdMealNo = cursorFd.getString(5);
            fdCalD = cursorFd.getString(6);

            // Get food name
            cursorFood = db.select("food", fieldsFood, "_id", fdFoodIdSQL);

            // Variables from food
            String foodID = cursorFood.getString(0);
            String foodName = cursorFood.getString(1);
            String subLine = fdMeasurement + " " + fdUnit;
            Food_NameD.setText(foodName);
            Food_calD.setText(fdCalD);
            Food_SubD.setText(subLine);
            fd_cal_D = Double.parseDouble(fdCalD);


            cursorFd.moveToNext();
        }


        /* Close db */
        db.close();
    } // updateTable

    private void addFoodB(int mealNumber){

        /* Inialize fragmet */
        Fragment fragment = null;
        Class fragmentClass = null;
        fragmentClass = FoodFragmentB.class;
        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        // Send variable
        Bundle bundle = new Bundle();
        bundle.putString("mealNumber", ""+ mealNumber); // Put anything what you want
        fragment.setArguments(bundle);
        //
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.flMain, fragment).commit();

    } // initalizeHome

    private void addFoodL(int mealNumber){

        /* Inialize fragmet */
        Fragment fragment = null;
        Class fragmentClass = null;
        fragmentClass = FoodFragmentL.class;
        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        // Send variable
        Bundle bundle = new Bundle();
        bundle.putString("mealNumber", ""+ mealNumber); // Put anything what you want
        fragment.setArguments(bundle);
        //
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.flMain, fragment).commit();

    } // initalizeHome
    private void addFoodS(int mealNumber){

        /* Inialize fragmet */
        Fragment fragment = null;
        Class fragmentClass = null;
        fragmentClass = FoodFragmentS.class;
        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        // Send variable
        Bundle bundle = new Bundle();
        bundle.putString("mealNumber", ""+ mealNumber); // Put anything what you want
        fragment.setArguments(bundle);
        //
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.flMain, fragment).commit();

    } // initalizeHome
    private void addFoodD(int mealNumber){

        /* Inialize fragmet */
        Fragment fragment = null;
        Class fragmentClass = null;
        fragmentClass = FoodFragmentD.class;
        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        // Send variable
        Bundle bundle = new Bundle();
        bundle.putString("mealNumber", ""+ mealNumber); // Put anything what you want
        fragment.setArguments(bundle);
        //
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.flMain, fragment).commit();

    } // initalizeHome


}
