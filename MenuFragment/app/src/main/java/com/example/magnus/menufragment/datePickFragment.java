package com.example.magnus.menufragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;


import com.example.magnus.menufragment.EkonomiFragment;
import com.example.magnus.menufragment.R;

import java.util.Calendar;

public static class datePickFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER s
    //android.support.v4.app.Fragment
    // View view;

    @Override
    protected Dialog onCrerateDialog(Bundle savedInstanceState){
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    public void onDateSet(DatePicker view, int year, int month, int day){
        return;
    }

}