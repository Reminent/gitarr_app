package com.example.magnus.menufragment;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.Layout;
import android.util.Xml;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;


public class EkonomiFragment extends android.support.v4.app.Fragment {
    private TextView textView;
    private ListView listView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.ekonomi_fragment, container, false);
        textView = (TextView) view.findViewById(R.id.test_textView);
        //listView = (ListView) view.findViewById(R.id.list);
        Button button = (Button) view.findViewById(R.id.Ekonomi_Button_Ny_Inkommst);
        Button button1 = (Button) view.findViewById(R.id.Ekonomi_Button_Ny_Utgift);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String stringUrl = "http://sknz.no-ip.org/api/public/json";

                ConnectivityManager connMgr = (ConnectivityManager)
                        getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
                if (networkInfo != null && networkInfo.isConnected()) {
                        try{
                            EkoGet task = new EkoGet();
                            task.execute(stringUrl);
                        } catch(Throwable e){
                        }
                } else {
                    textView.setText("No network connection available.");
                }
            }
        });

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String stringUrl = "http://spaaket.no-ip.org:1080/GitarrWebbservices/webresources/webresources.product";
                ConnectivityManager connMgr = (ConnectivityManager)
                        getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
                if (networkInfo != null && networkInfo.isConnected()) {
                    try{
                        EkoGet task = new EkoGet();
                        task.execute(stringUrl);
                    } catch(Throwable e){
                    }
                } else {
                    textView.setText("No network connection available.");
                }
            }
        });
        return view;
    }

    private class EkoGet extends DB_Connect{
        @Override
        protected void onPostExecute(String result) {

            List<Advert> advert = new ArrayList<>();
            try {
                Advert_Parse parser = new Advert_Parse();
                advert = parser.parse(result);
                String s = advert.get(1).getProductName();
                s = s + " " + advert.size();
                textView.setText(s);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
