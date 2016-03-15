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

import com.example.magnus.menufragment.DB_Upload.DB_Update;
import com.example.magnus.menufragment.DB_Upload.DB_Upload;
import com.example.magnus.menufragment.DB_Upload.XML_Generate;

public class SchemaUpdateTimeFragment extends android.support.v4.app.Fragment {
    @Nullable
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.schema_update_time_fragment, container, false);
        getActivity().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        Button done = (Button) view.findViewById(R.id.schema_updateTime_done);
        Button abort = (Button) view.findViewById(R.id.schema_updateTime_abort);
        final EditText editStartDate = (EditText) view.findViewById(R.id.schema_updateTime_startTime);
        final EditText editEndDate = (EditText) view.findViewById(R.id.schema_updateTime_endTime);
        final EditText editName = (EditText) view.findViewById(R.id.schema_updateTime_name);
        final EditText editDescription = (EditText) view.findViewById(R.id.schema_updateTime_description);
        final EditText editPhone = (EditText) view.findViewById(R.id.schema_updateTime_phoneNumber);

        Bundle bundle = this.getArguments();
        final String name = bundle.getString("name");
        final String id = bundle.getString("id");
        final String start = bundle.getString("start");
        final String end = bundle.getString("end");
        final String description = bundle.getString("description");
        final String phone = bundle.getString("phone");

        String[] splitEnd = end.split("T");
        String[] splitStart = start.split("T");
        String[] splitEndTime = splitEnd[1].split(":");
        String[] splitstartTime = splitStart[1].split(":");

        editName.setText(name);
        editPhone.setText(phone);
        editDescription.setText(description);
        editStartDate.setText(splitStart[0] + " " + splitstartTime[0] + ":" + splitstartTime[1]);
        editEndDate.setText(splitEnd[0] + " " + splitEndTime[0] + ":" + splitEndTime[1]);

        done.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String URL = "http://spaaket.no-ip.org:1080/GitarrAppAPI/webresources/rest.consultation/" + id;

                String updateName = editName.getText().toString();
                String updatePhone = editPhone.getText().toString();
                String updateDescription = editDescription.getText().toString();
                String updateEnd = editEndDate.getText().toString();
                String updateStart = editStartDate.getText().toString();

                String[] endSplit = updateEnd.split(" ");
                String[] startSplit = updateStart.split(" ");
                String finalStart = startSplit[0] + "T" + startSplit[1] + ":00+01:00";
                String finalEnd = endSplit[0] + "T" + endSplit[1] + ":00+01:00";

                XML_Generate generator = new XML_Generate();
                String results = generator.consultationTable(id,updateName,updatePhone,finalEnd,finalStart,updateDescription);
                DB_Update upload = new DB_Update();
                upload.execute(results, URL);

                Fragment fragment;
                FragmentTransaction fm = getFragmentManager().beginTransaction();

                switch(v.getId()){
                    case R.id.schema_updateTime_done:

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
                    case R.id.schema_updateTime_abort:

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
