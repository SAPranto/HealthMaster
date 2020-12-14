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
 * {@link Faq.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Faq#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Faq extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public Faq() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Faq.
     */
    // TODO: Rename and change types and number of parameters
    public static Faq newInstance(String param1, String param2) {
        Faq fragment = new Faq();
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
        return inflater.inflate(R.layout.fragment_faq, container, false);
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TextView t = getActivity().findViewById(R.id.text);
        t.setText("• Health monitoring system is a life-saving system which helps to  track state of health of a patient and compiles a chronological health history of the patient .\n" +
                "•It uses a multiparametric monitor which periodically and automatically measures and records a plurality of physiological data from sensors in contact with the patient's body.\n" +
                "•For a better health monitor system there should be device from which physiological data can be accumulated and that data need to be processed to develop the system.\n" +
                "• But presently it is not able to control a long range transmission of the processed physiological data to a remote entity.\n" +
                "•Maximum physicians says that our health deteriorates usually as we can’t always monitor about our physiological condition. It is very reluctant to visit physicians, nutrionist monthly in busy scheduled life.\n" +
                "•Generally, if an individual wishes to have physiological signs monitored, the individual must visit a physician or health care provider facility.\n" +
                "•Any physiological sign monitoring system that is currently in existence requires physiological sensors that are uniquely configured to operate only within a closed, specific environment, not within an open networked environment.");
    }



        // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
   /* public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }*/

    //@Override
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
