package com.example.mhealthapp;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link BodyFrag.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link BodyFrag#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BodyFrag extends Fragment {

    TextView dry,normal,combi,oily;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public BodyFrag() {
        // Required empty public constructor
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setUpViews();
        dry.setText("Use Lukewarm water\n" +
                "Apply moisturizer\n"+
                "Have Dry Fruits\n "
        );
        normal.setText("Use face mask once a week\n" +
                "Drink 8 glasses of water daily\n"+
                "Follow healthy diet\n "
        );
        combi.setText("Stop Soap\n" +
                "Moisturize Often\n"+
                "Exfloaite your skin"
        );
        oily.setText("Salt Face Wash\n" +
                "Cucumber with honey juice Face Pack\n"+
                "Egg white Face Pack\n "
        );
    }
    public void setUpViews() {
        dry = (TextView) getActivity().findViewById(R.id.Dskin);
        normal = (TextView) getActivity().findViewById(R.id.Nskin);
        combi = (TextView) getActivity().findViewById(R.id.Cskin);
        oily = (TextView) getActivity().findViewById(R.id.Oskin);

    }


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BodyFrag.
     */
    // TODO: Rename and change types and number of parameters
    public static BodyFrag newInstance(String param1, String param2) {
        BodyFrag fragment = new BodyFrag();
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
        return inflater.inflate(R.layout.fragment_body, container, false);
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
