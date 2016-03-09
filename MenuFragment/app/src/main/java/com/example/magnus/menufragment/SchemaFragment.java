
package com.example.magnus.menufragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class SchemaFragment extends android.support.v4.app.Fragment {
    /*
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.schema_fragment, container, false);

        final TextView textView = (TextView) view.findViewById(R.id.dateDisplay);

        CalendarView myCalendar = (CalendarView) view.findViewById(R.id.calendarView);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String selectedDate = sdf.format(new Date(myCalendar.getDate()));
        textView.setText(selectedDate);

        CalendarView.OnDateChangeListener myCalendarListener = new CalendarView.OnDateChangeListener(){
            public void onSelectedDayChange(CalendarView view, int year, int month, int day){
                month = month + 1;
                String m = "" + month;
                String d = "" + day;

                if (month < 10) {
                    m = "0" + month;
                }
                if (day < 10) {
                    d = "0" + day;
                }
                String newDate = year + "-" + m + "-" + d;
                textView.setText(newDate);
            }
        };
        myCalendar.setOnDateChangeListener(myCalendarListener);

        return view;
    }
*/
}
