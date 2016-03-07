package com.example.magnus.menufragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.os.Bundle;
import android.widget.DatePicker;
import java.util.Calendar;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;


import java.util.Calendar;



//public class datePickFragment extends android.support.v4.app.DialogFragment implements DatePickerDialog.OnDateSetListener {
public class datePickFragment extends android.support.v4.app.DialogFragment implements DatePickerDialog.OnDateSetListener {

    /*
    TODO: Rename parameter arguments, choose names that match
    the fragment initialization parameters, e.g. ARG_ITEM_NUMBER s
    android.support.v4.app.FragmentManager
    */


    @Override
    public Dialog onCreate(Bundle savedInstanceState) {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        return new DatePickerDialog(getActivity(), this, year, month, day);

    }


    public void onDateSet(DatePicker view, int year, int month, int day) {
        //so something with the date chosen by the user

    }
    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new datePickFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");


    }

}



    /*
private OnClickListener showDatePickerDialog(){
    return new OnClickListener() {
        @Override
        public void onClick(View v) {
            DialogFragment newFragment = new datePickFragment(){
                @Override
                public void onDateSet(DatePicker view, int year, int month, int day){

                }
            }
            newFragment.show(getActivity().Activity.getFragmentManager(), "datePicker");
        }
    }

}*/