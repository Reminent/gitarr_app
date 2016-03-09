package com.example.magnus.menufragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.magnus.menufragment.DB_Connect.DB_Connect;
import com.example.magnus.menufragment.XML_Parsing.Consultation;
import com.example.magnus.menufragment.XML_Parsing.Consultation_Parse;
import com.example.magnus.menufragment.XML_Parsing.Product;
import com.example.magnus.menufragment.XML_Parsing.Product_Parse;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class SchemaFragment extends android.support.v4.app.Fragment {
    private List<Consultation> consultation = new ArrayList<>();
    private ListView listView;
    private View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.schema_fragment, container, false);

        final TextView textView = (TextView) view.findViewById(R.id.dateDisplay);

        /*CalendarView myCalendar = (CalendarView) view.findViewById(R.id.calendarView);

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
        myCalendar.setOnDateChangeListener(myCalendarListener);*/

        String url = "http://spaaket.no-ip.org:1080/GitarrAppAPI/webresources/rest.consultation";
        SchemaGet task = new SchemaGet();
        task.execute(url);

        return view;
    }

    private class SchemaGet extends DB_Connect {
        @Override
        protected void onPostExecute(String result) {
            try {
                Consultation_Parse parser = new Consultation_Parse();
                consultation = parser.parse(result);

                SchemaAdapter schemaAdapter = new SchemaAdapter(getContext(), R.layout.schema_item, consultation);
                listView = (ListView) view.findViewById(R.id.schemaListView);
                listView.setAdapter(schemaAdapter);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}