package com.example.mhealthapp;


import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class ListViewFragment extends Fragment {

    Button newmed;
    private static final String TAG = "ListViewActivity";
    DatabaseHelper mDatabaseHelper;
    private ListView mListView;
    ArrayList<User>userlist;
    User user;
    public ListViewFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list_view, container, false);
    }
    @Override

    public void onViewCreated(View view, Bundle savedInstanceState){

        mListView = (ListView)getActivity().findViewById(R.id.listview) ;
        newmed =(Button)getActivity().findViewById(R.id.New_MED) ;

        mDatabaseHelper = new DatabaseHelper(getActivity().getApplicationContext());

        userlist = new ArrayList<>();
        Cursor data = mDatabaseHelper.getData();
        int numRows = data.getCount();

        if(numRows == 0){
            Toast.makeText(getActivity(),"The Database is empty  :(.",Toast.LENGTH_LONG).show();
        }else{
            while(data.moveToNext()){
                //Getting the data to the column
                user = new User(data.getString(1),data.getString(2),data.getString(3),data.getString(4),data.getString(5),data.getString(6));
                userlist.add(user);
            }
            Column_ListAdapter adapter =  new Column_ListAdapter(getActivity(),R.layout.adapter_view_layout, userlist);
            mListView.setAdapter(adapter);
        }

        newmed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.flMain, new ReminderActivityFrag()).addToBackStack(null);
                ft.commit();
            }
        });



    }
}
