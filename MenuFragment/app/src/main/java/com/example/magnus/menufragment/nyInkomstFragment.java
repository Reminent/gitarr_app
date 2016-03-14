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
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Pattern;

import com.example.magnus.menufragment.DB_Upload.DB_Upload;
import com.example.magnus.menufragment.DB_Upload.XML_Generate;

/*
 * Created by Albin and Martin on 2016-03-09.
 */

public class nyInkomstFragment extends android.support.v4.app.Fragment {
    @Nullable
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_ny_inkomst, container, false);

        Button done = (Button) view.findViewById(R.id.btnSkicka);
        Button abort = (Button) view.findViewById(R.id.btnStop);

        done.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                String URL = "http://spaaket.no-ip.org:1080/GitarrAppAPI/webresources/rest.transaction";


                EditText eTextAmount= (EditText)view.findViewById(R.id.txtSetAmount);

                Calendar c = Calendar.getInstance();
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date cDate = new Date();
                String transDate = new SimpleDateFormat("yyyy-MM-dd").format(cDate);

                if(!isEmpty(eTextAmount)&& Pattern.matches("[0-9]+", eTextAmount.getText().toString()) == true) {

                    String id, amount, date;
                    id = transDate;
                    date=transDate;
                    amount = eTextAmount.getText().toString();

                    XML_Generate generator = new XML_Generate();
                    String results = generator.transactionTable(amount, date, id);
                    DB_Upload upload = new DB_Upload();
                    upload.execute(results, URL);
                    Toast.makeText(getContext(), "uppladdat", Toast.LENGTH_LONG).show();

                    Fragment fragment;
                    FragmentTransaction fm = getFragmentManager().beginTransaction();


                switch(v.getId()){
                    case R.id.btnSkicka:

                        fragment = new EkonomiFragment();
                        fm.replace(R.id.content, fragment);
                        fm.addToBackStack(null);
                        fm.commit();

                        break;
                }
            }
                else {
                    Toast.makeText(getContext().getApplicationContext(),
                            "Du måste välja Datum och summa" , Toast.LENGTH_LONG).show();
                }
            }
        });

        abort.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "abort", Toast.LENGTH_LONG).show();
                Fragment fragment;
                FragmentTransaction fm = getFragmentManager().beginTransaction();

                switch(v.getId()){
                    case R.id.btnStop:

                        fragment = new EkonomiFragment();
                        fm.replace(R.id.content, fragment);
                        fm.addToBackStack(null);
                        fm.commit();

                        break;
                }
            }
        });


        return view;
    }


    private boolean isEmpty(EditText etText) {
        return etText.getText().toString().trim().length() == 0;
    }
}


