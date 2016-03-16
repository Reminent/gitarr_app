package com.example.magnus.menufragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.example.magnus.menufragment.DB_Upload.DB_Upload;
import com.example.magnus.menufragment.DB_Upload.XML_Generate;

public class SchemaNewTimeFragment extends android.support.v4.app.Fragment {
    @Nullable
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.schema_new_time_fragment, container, false);
        getActivity().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        Button done = (Button) view.findViewById(R.id.schema_newTime_done);
        Button abort = (Button) view.findViewById(R.id.schema_newTime_abort);
        EditText startDate = (EditText) view.findViewById(R.id.schema_newTime_startTime);
        final EditText endDate = (EditText) view.findViewById(R.id.schema_newTime_endTime);

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
                name = nameText.getText().toString();
                phone = phoneText.getText().toString();
                enddate = endDateText.getText().toString();
                startdate = startDateText.getText().toString();
                description = descriptionText.getText().toString();

                String[] endSplit = enddate.split(" ");
                String[] startSplit = startdate.split(" ");
                String finalStart = startSplit[0] + "T" + startSplit[1] + ":00+01:00";
                String finalEnd = endSplit[0] + "T" + endSplit[1] + ":00+01:00";

                XML_Generate generator = new XML_Generate();
                String results = generator.consultationTable(id,name,phone,finalEnd,finalStart,description);
                DB_Upload upload = new DB_Upload();
                upload.execute(results, URL);

                Fragment fragment;
                FragmentTransaction fm = getFragmentManager().beginTransaction();

                switch(v.getId()){
                    case R.id.schema_newTime_done:

                        fragment = new SchemaFragment();
                        fm.replace(R.id.content, fragment);
                        fm.addToBackStack(null);
                        fm.commit();

                        break;
                }
            }
        });

        abort.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
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
        return view;
    }

}
