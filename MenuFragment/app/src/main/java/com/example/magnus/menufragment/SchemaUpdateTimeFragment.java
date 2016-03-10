package com.example.magnus.menufragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.magnus.menufragment.DB_Upload.DB_Upload;
import com.example.magnus.menufragment.DB_Upload.XML_Generate;

public class SchemaUpdateTimeFragment extends android.support.v4.app.Fragment {
    @Nullable
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.schema_new_time_fragment, container, false);

        Button done = (Button) view.findViewById(R.id.schema_newTime_done);
        Button abort = (Button) view.findViewById(R.id.schema_newTime_abort);
        EditText startDate = (EditText) view.findViewById(R.id.schema_newTime_startTime);
        EditText endDate = (EditText) view.findViewById(R.id.schema_newTime_endTime);

        done.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                EditText nameText = (EditText) view.findViewById(R.id.schema_newTime_name);
                EditText phoneText = (EditText) view.findViewById(R.id.schema_newTime_phoneNumber);
                EditText endDateText = (EditText) view.findViewById(R.id.schema_newTime_endTime);
                EditText startDateText = (EditText) view.findViewById(R.id.schema_newTime_startTime);
                EditText descriptionText = (EditText) view.findViewById(R.id.schema_newTime_description);
                String URL = "http://spaaket.no-ip.org:1080/GitarrAppAPI/webresources/rest.consultation";

                String id, name, phone, enddate, startdate, description;
                id = "0";
                name = "oskar andersson";
                phone = "0702222222";
                enddate = "2016-03-15T14:00:00+01:00";
                startdate = "2016-03-15T14:30:00+01:00";
                description = "testar att ladda upp bokningar från appen";
                /*
                name = nameText.getText().toString();
                phone = phoneText.getText().toString();
                enddate = endDateText.getText().toString();
                startdate = startDateText.getText().toString();
                description = descriptionText.getText().toString();
                */

                XML_Generate generator = new XML_Generate();
                String results = generator.consultationTable(id,name,phone,enddate,startdate,description);
                DB_Upload upload = new DB_Upload();
                upload.execute(results, URL);
                Toast.makeText(getContext(), results, Toast.LENGTH_LONG).show();
            }
        });


        abort.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "abort", Toast.LENGTH_LONG).show();
                Fragment fragment;
                FragmentTransaction fm = getFragmentManager().beginTransaction();

                switch(v.getId()){
                    case R.id.schema_newTime_abort:

                        fragment = new SchemaFragment();
                        fm.replace(R.id.content, fragment);
                        fm.addToBackStack(null);
                        fm.commit();

                        break;
                }
            }
        });

        startDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "start", Toast.LENGTH_SHORT).show();
            }
        });

        endDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "end", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

}
