/*package com.example.magnus.menufragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
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

public class datePickFragment extends android.support.v4.app.Fragment implements DatePickerDialog.OnDateSetListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER s
    //android.support.v4.app.Fragment
    // View view;

    private TextView displayDate;
    private DatePicker resultShow;
    private Button btnD;

    private int year;
    private int month;
    private int day;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_date_pick);
        setCurrentDateToShow();
        addListenerOnButton();
    }


    public void setCurrentDateToShow(){
       resultShow = (TextView) displayDate.setText(new StringBuilder());
        resultShow;
    }

    public void onDateSet(DatePicker view, int year, int month, int day){
        return;
    }

}
*/