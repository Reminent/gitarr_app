package com.example.magnus.menufragment;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.magnus.menufragment.DB_Connect.DB_Connect;
import com.example.magnus.menufragment.XML_Parsing.Advert;
import com.example.magnus.menufragment.XML_Parsing.Advert_Parse;
import com.example.magnus.menufragment.XML_Parsing.Consultation;
import com.example.magnus.menufragment.XML_Parsing.Consultation_Parse;
import com.example.magnus.menufragment.XML_Parsing.Inventory;
import com.example.magnus.menufragment.XML_Parsing.Inventory_Parse;
import com.example.magnus.menufragment.XML_Parsing.Product;
import com.example.magnus.menufragment.XML_Parsing.Product_Parse;

import java.util.List;


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
                String stringUrl = "http://spaaket.no-ip.org:1080/GitarrAppAPI/webresources/rest.consultation";

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
                String stringUrl = "http://spaaket.no-ip.org:1080/GitarrAppAPI/webresources/rest.inventory";
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

    private class EkoGet extends DB_Connect {
        @Override
        protected void onPostExecute(String result) {

            // Consultation

            List<Consultation> consultation = null;

            try {
                Consultation_Parse parser = new Consultation_Parse();
                consultation = parser.parse(result);

                String s = "";
                //String s = advert.get(1).getProductName();

                //s = s + " " + advert.get(0).getGenre();


                for(Consultation model : consultation) {
                    s += " " + model.getConsultationid();
                    s += " " + model.getCustomerName();
                }


                textView.setText(s);
                //textView.setText(advert.size());
            } catch (Exception e) {
                e.printStackTrace();
            }

            /*
            // Inventory

            List<Inventory> inventory = null;

            try {
                Inventory_Parse parser = new Inventory_Parse();
                inventory = parser.parse(result);

                String s = "";
                //String s = advert.get(1).getProductName();

                //s = s + " " + advert.get(0).getGenre();


                for(Inventory model : inventory) {
                    s += " " + model.getInventoryQuantity();
                    s += " " + model.getGenre();
                }


                textView.setText(s);
                //textView.setText(advert.size());
            } catch (Exception e) {
                e.printStackTrace();
            }
            */
            /*
            // Advert

            List<Advert> advert = null;

            try {
                Advert_Parse parser = new Advert_Parse();
                advert = parser.parse(result);

                String s = "";
                //String s = advert.get(1).getProductName();

                //s = s + " " + advert.get(0).getGenre();


                for(Advert model : advert) {
                    s += " " + model.getAdvertTitle();
                }


                textView.setText(s);
                //textView.setText(advert.size());
            } catch (Exception e) {
                e.printStackTrace();
            }
            */
            /*
            // Product

            List<Product> product = null;

            try {
                Product_Parse parser = new Product_Parse();
                product = parser.parse(result);

                String s = "";
                //String s = advert.get(1).getProductName();

                //s = s + " " + advert.get(0).getGenre();


                for(Product model : product) {
                    s += " " + model.getProductName();
                }


                textView.setText(s);
                //textView.setText(advert.size());
            } catch (Exception e) {
                e.printStackTrace();
            }
            */
        }
    }
}
