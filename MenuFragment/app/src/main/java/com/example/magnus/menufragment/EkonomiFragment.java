package com.example.magnus.menufragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


public class EkonomiFragment extends android.support.v4.app.Fragment implements AsyncResponse {

    DB_Connect asyncTask =new DB_Connect(new AsyncResponse(){

        @Override
        void processFinish(String output){
            //Here you will receive the result fired from async class
            //of onPostExecute(result) method.
        }
    }).execute();


    private static final String DEBUG_TAG = "HttpExample";
    private TextView textView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.ekonomi_fragment, container, false);
        textView = (TextView) view.findViewById(R.id.test_textView);
        Button button = (Button) view.findViewById(R.id.Ekonomi_Button_Ny_Inkommst);
        Button button1 = (Button) view.findViewById(R.id.Ekonomi_Button_Ny_Utgift);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String stringUrl = "http://sknz.no-ip.org/api/public/json";
                new DownloadWebpageTask().execute(stringUrl);
            }
        });

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String stringUrl = "http://reminent.no-ip.org/slimapi/public/json";
                new DownloadWebpageTask().execute(stringUrl);
            }
        });

        return view;
    }
}
