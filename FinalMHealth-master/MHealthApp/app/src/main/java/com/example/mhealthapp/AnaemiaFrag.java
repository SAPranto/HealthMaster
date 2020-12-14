package com.example.mhealthapp;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AnaemiaFrag.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AnaemiaFrag#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AnaemiaFrag extends Fragment {


    TextView causes,symptoms,treatment,prevention,food_eat;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private Food_tips.OnFragmentInteractionListener mListener;

    public AnaemiaFrag() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setUpViews();
        causes.setText("Blood Loss\n" +
                "Lack of red blood cell production\n"+
                "Lack of iron in diet\n "
        );
        symptoms.setText("Fatigue\n" +
                "Dizziness\n"+
                "Palpitations\n"+
                "Looking pale"
        );
        treatment.setText("Blood Transfusion\n" +
                "Iron Supplements\n"
        );
        prevention.setText("Eat a diet rich in IRON\n" +
                "Consume foods containing VITAMIN C and ViTAMIN B12\n"+
                "Take B12 Supplements\n "
        );
        food_eat.setText("Papaya\n" +
                "Pomegranate\n" +
                "Pumpkin\n" +
                "Vitamin C\n" +
                "Leafy Greens\n" +
                "Beet Root ");
    }

    public void setUpViews() {
        causes = (TextView) getActivity().findViewById(R.id.Acause);
        symptoms = (TextView) getActivity().findViewById(R.id.Asymptoms);
        treatment = (TextView) getActivity().findViewById(R.id.ATreatment);
        prevention = (TextView) getActivity().findViewById(R.id.Aprevention);
        food_eat = (TextView) getActivity().findViewById(R.id.Afood);
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AnaemiaFrag.
     */
    // TODO: Rename and change types and number of parameters
    public static AnaemiaFrag newInstance(String param1, String param2) {
        AnaemiaFrag fragment = new AnaemiaFrag();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_anaemia, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }



    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
