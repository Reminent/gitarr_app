package com.example.magnus.menufragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link nyInkomstFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link nyInkomstFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class nyInkomstFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    public nyInkomstFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ny_inkomst, container, false);
        return view;
    }
}
