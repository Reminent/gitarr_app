package com.example.magnus.menufragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.magnus.menufragment.DB_Connect.DB_Connect;
import com.example.magnus.menufragment.Interface.ConsultationResultListener;
import com.example.magnus.menufragment.XML_Parsing.Consultation;
import com.example.magnus.menufragment.XML_Parsing.Consultation_Parse;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SchemaFragment extends android.support.v4.app.Fragment implements ConsultationResultListener {
    private List<Consultation> consultation = new ArrayList<>();
    private ListView listView;
    private View view;
    private String selectedDate;
    private String newDate = "";
    private DB_Connect task;
    private String url = "http://spaaket.no-ip.org:1080/GitarrAppAPI/webresources/rest.consultation";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.filter, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.action_filter){
            final CalendarView propLayout = (CalendarView) view.findViewById(R.id.calendarView);
            if (propLayout.getVisibility() == View.VISIBLE) {
                propLayout.setVisibility(View.INVISIBLE);
                ViewGroup.LayoutParams params = propLayout.getLayoutParams();
                params.height = 0;
                propLayout.setLayoutParams(params);
            } else {
                propLayout.setVisibility(View.VISIBLE);
                ViewGroup.LayoutParams params = propLayout.getLayoutParams();
                params.height = 1000;
                propLayout.setLayoutParams(params);
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.schema_fragment, container, false);
        task = new DB_Connect();
        task.delegate = this;
        task.execute(url);

        //final TextView textView = (TextView) view.findViewById(R.id.dateDisplay);

        CalendarView myCalendar = (CalendarView) view.findViewById(R.id.calendarView);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        selectedDate = sdf.format(new Date(myCalendar.getDate()));
        //textView.setText(selectedDate);

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
                newDate = year + "-" + m + "-" + d;
                //textView.setText(newDate);
                task = new DB_Connect();
                task.delegate = SchemaFragment.this;
                task.execute(url);
            }
        };
        myCalendar.setOnDateChangeListener(myCalendarListener);

        /*
        String url = "http://spaaket.no-ip.org:1080/GitarrAppAPI/webresources/rest.consultation";
        SchemaGet task = new SchemaGet();
        task.execute(url);*/

        return view;
    }

    public void processFinish(String result) {
        Consultation_Parse parser = new Consultation_Parse();
        consultation = parser.parse(result);

        SchemaAdapter schemaAdapter = new SchemaAdapter(getContext(), R.layout.schema_item, consultation, newDate);
        listView = (ListView) view.findViewById(R.id.schemaListView);
        listView.setAdapter(schemaAdapter);
    }

    /*
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
    */
}