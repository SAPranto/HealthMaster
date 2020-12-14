package com.example.mhealthapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class FoodFragment extends Fragment {

    /*- 01 Class Variables -------------------------------------------------------------- */
    private View mainView;
    private Cursor listCursor;

    private String currentId;
    private String currentName;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ((HomeActivity2) getActivity()).setActionBarTitle("Food & Nutritions");
        // Inflate the layout for this fragment
        mainView = inflater.inflate(R.layout.activity_food_fragment, container, false);
        return mainView;
    }

    private void setMenuView(int id){

        LayoutInflater layoutInflater = (LayoutInflater)getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mainView = layoutInflater.inflate(id,null);
        ViewGroup rootview= (ViewGroup)getView();
        rootview.removeAllViews();
        rootview.addView(mainView);
    }

    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater){

        ((HomeActivity2)getActivity()).getMenuInflater().inflate(R.menu.categories_menu,menu);

    }

    public boolean onOptionsItemSelected(MenuItem menuItem){

        int id = menuItem.getItemId();

        if(id == R.id.action_plus){
          addFood();
        }

        return super.onOptionsItemSelected(menuItem);

    }
    public void populateListFood(){

        /* Database */
        DBAdapter db = new DBAdapter(getActivity());
        db.open();

        // Get categories
        String fields[] = new String[] {
                "_id",
                "food_title",
                "food_cal",
                "food_text",
                };

        listCursor = db.select("food", fields, "", "", "food_title", "ASC");
        // Find ListView to populate
        ListView lvItems = (ListView)getActivity().findViewById(R.id.listViewFood);
        // Setup cursor adapter using cursor from last step
        FoodCursorAdapter continentsAdapter = new FoodCursorAdapter(getActivity(), listCursor);
        // Attach cursor adapter to the ListView
        lvItems.setAdapter(continentsAdapter); // uses ContinensCursorAdapter
        // OnClick
        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
               // listItemClicked(arg2);
            }
        });

        // Close db
        db.close();

    }


    public void addFood(){
        /* Database */
        DBAdapter db = new DBAdapter(getActivity());
        db.open();

        /* Change layout */
        int id = R.layout.fragment_food_list_add;
        setMenuView(id);

        // Change title
        ((HomeActivity2)getActivity()).getSupportActionBar().setTitle("Add food");


        /* SubmitButton listener */
        Button buttonEditFood = (Button)getActivity().findViewById(R.id.buttonEditFood);
        buttonEditFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonAddFoodSubmitOnClick();
            }
        });

        /* Close db */
        db.close();
    } // addFood

    public void buttonAddFoodSubmitOnClick(){
        /* Database */
        DBAdapter db = new DBAdapter(getActivity());
        db.open();

        // Error?
        int error = 0;

        /* General */

        // Name
        EditText editTextEditFoodName = (EditText)getActivity().findViewById(R.id.editTextEditFoodName);
        String stringName = editTextEditFoodName.getText().toString();
        String stringNameSQL = db.quoteSmart(stringName);
        if(stringName.equals("")){
            Toast.makeText(getActivity(), "Please fill in a name.", Toast.LENGTH_SHORT).show();
            error = 1;
        }

        // Description
        EditText editTextEditFoodDescription = (EditText)getActivity().findViewById(R.id.editTextEditFoodDescription);
        String stringDescription = editTextEditFoodDescription.getText().toString();
        String stringDescriptionSQL = db.quoteSmart(stringDescription);


        EditText editTextCal = (EditText)getActivity().findViewById(R.id.food_cal_Tv);
        String stringFatPerHundred = editTextCal.getText().toString();

        double doubleFatPerHundred = 0;
        if(stringFatPerHundred.equals("")){
            Toast.makeText(getActivity(), "Please fill in Calories.", Toast.LENGTH_SHORT).show();
            error = 1;
        }
        else{
            try {
                doubleFatPerHundred = Double.parseDouble(stringFatPerHundred);
            }
            catch(NumberFormatException nfe) {
                Toast.makeText(getActivity(), "Calorie is not a number.", Toast.LENGTH_SHORT).show();
                error = 1;
            }
        }
        String stringFatPerHundredSQL = db.quoteSmart(stringFatPerHundred);


        /* Insert */
        if(error == 0){

            String fields =
                    "_id, " +
                            "food_title, " +
                            "food_cal, " +
                            "food_text ";
            String values =
                    "NULL, " +
                            stringNameSQL + ", " +
                            stringFatPerHundredSQL + ", " +
                            stringDescriptionSQL;


            db.insertRecord("food", fields, values);

            // Toast
            Toast.makeText(getActivity(), "Food created", Toast.LENGTH_SHORT).show();

            // Move user back to correct design
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            fragmentManager.beginTransaction().
                    replace(R.id.flMain, new FoodFragment(), FoodFragment.class.getName())
                    .addToBackStack(null).commit();

        } // error == 0


        /* Close db */
        db.close();
    } // buttonAddFoodSubmitOnClick








    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        populateListFood();
        setHasOptionsMenu(true);

    }




}
