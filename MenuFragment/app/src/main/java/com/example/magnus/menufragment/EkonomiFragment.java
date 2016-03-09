package com.example.magnus.menufragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.magnus.menufragment.DB_Connect.DB_Connect;
import com.example.magnus.menufragment.DB_Upload.DB_Delete;
import com.example.magnus.menufragment.XML_Parsing.Product;
import com.example.magnus.menufragment.XML_Parsing.Product_Parse;

import java.util.ArrayList;
import java.util.List;


public class EkonomiFragment extends android.support.v4.app.Fragment {
    private TextView textView;
    //List<Product> products = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.ekonomi_fragment, container, false);
        textView = (TextView) view.findViewById(R.id.test_textView);
        Button button = (Button) view.findViewById(R.id.Ekonomi_Button_Ny_Inkommst);
        Button button1 = (Button) view.findViewById(R.id.Ekonomi_Button_Ny_Utgift);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // DB_Delete

                String imageId = "1";

                String stringUrl = "http://spaaket.no-ip.org:1080/GitarrAppAPI/webresources/rest.consultation/" + imageId;
                DB_Delete delete = new DB_Delete();

                try {
                    delete.execute(stringUrl);
                } catch (Exception e) {
                    e.printStackTrace();
                }


                // DB_Put
                /*
                String imageUrl = "/images/yesWorks.jpg";
                String imageTitle = "fuckingworks";
                String imageId = "37";

                String stringUrl = "http://spaaket.no-ip.org:1080/GitarrAppAPI/webresources/rest.image/" + imageId;
                DB_Update update = new DB_Update();

                XML_Generate xml_generate = new XML_Generate();

                try {
                    update.execute(xml_generate.imageTable(imageTitle, imageUrl, imageId), stringUrl);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                */

                // DB_Post
                /*
                String stringUrl = "http://spaaket.no-ip.org:1080/GitarrAppAPI/webresources/rest.image";
                DB_Upload upload = new DB_Upload();

                XML_Generate xml_generate = new XML_Generate();

                String imageUrl = "/images/ortegar200.jpg";
                String imageTitle = "Oskarsson";
                String imageId = "1";

                try {
                    upload.execute(xml_generate.imageTable(imageTitle, imageUrl), stringUrl);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                */

                // DB_Connect
                /*
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
                */
            }
        });
        /*
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String stringUrl = "http://spaaket.no-ip.org:1080/GitarrAppAPI/webresources/rest.image";

                ConnectivityManager connMgr = (ConnectivityManager)
                        getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
                if (networkInfo != null && networkInfo.isConnected()) {
                    try{
                        EkoGet task = new EkoGet();
                        task.execute(stringUrl);

                        DB_Upload upload = new DB_Upload();

                        XML_Generate xml_generate = new XML_Generate();

                        String imageUrl = "/images/ortegar200.jpg";
                        String imageTitle = "Ivar";
                        String imageId = "1";

                        try {
                            upload.execute(xml_generate.imageTable(imageTitle, imageUrl, imageId), stringUrl);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    } catch(Throwable e){
                    }
                } else {
                    textView.setText("No network connection available.");
                }
            }
        });*/

        return view;
    }

    private class EkoGet extends DB_Connect {
        @Override
        protected void onPostExecute(String result) {

            // Consultation
            List<Product> products = new ArrayList<>();
            try {
                Product_Parse parser = new Product_Parse();
                products = parser.parse(result);

                String s = "";
                //String s = advert.get(1).getProductName();

                //s = s + " " + advert.get(0).getGenre();

                for(Product model : products) {
                    s += " " + model.getProductName();
                    s += " " + model.getSellingPrice();
                }

                textView.setText(s);
                //textView.setText(advert.size());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}