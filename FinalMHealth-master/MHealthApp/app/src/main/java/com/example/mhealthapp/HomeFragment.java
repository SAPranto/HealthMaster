package com.example.mhealthapp;


import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;

import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment  extends Fragment   {

    ImageView image;

    Button hrt , steps ,meal , pill, bmi , sleep;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ((HomeActivity2)getActivity()).setActionBarTitle("Home");
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_home, container, false);


    }

    public void setupViews(){

        hrt = (Button)getActivity().findViewById(R.id.btnHrt);
        steps = (Button)getActivity().findViewById(R.id.btnSteps);
        meal = (Button)getActivity().findViewById(R.id.btnmeal);
        pill = (Button)getActivity().findViewById(R.id.btnreminder);
        bmi = (Button)getActivity().findViewById(R.id.btnBmi);
        sleep = (Button)getActivity().findViewById(R.id.btnsleep);
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        image =(ImageView)getView().findViewById(R.id.img);
        AnimationDrawable animationDrawable = (AnimationDrawable)image.getDrawable();
        animationDrawable.start();

        setupViews();
        hrt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.flMain, new HeartRate()).addToBackStack(null);
                ft.commit();
            }
        });
        steps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.flMain, new StepsWithApiFrag()).addToBackStack(null);
                ft.commit();
            }
        });
        meal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.flMain, new NutritionFragment()).addToBackStack(null);
                ft.commit();
            }
        });
        pill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.flMain, new ListViewFragment()).addToBackStack(null);
                ft.commit();
            }
        });
        bmi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.flMain, new BMI_Frag()).addToBackStack(null);
                ft.commit();
            }
        });
        sleep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.flMain, new Sleep_main()).addToBackStack(null);
                ft.commit();
            }
        });


    }


        }










