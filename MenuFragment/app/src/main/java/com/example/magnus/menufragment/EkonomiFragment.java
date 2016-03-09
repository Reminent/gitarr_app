
package com.example.magnus.menufragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.example.magnus.menufragment.DB_Connect.DB_Connect;
import com.example.magnus.menufragment.XML_Parsing.Advert;
import com.example.magnus.menufragment.XML_Parsing.Advert_Parse;
import com.example.magnus.menufragment.XML_Parsing.Transaction_Parse;
import com.example.magnus.menufragment.XML_Parsing.Transaction;

import java.util.ArrayList;
import java.util.List;

public class EkonomiFragment extends android.support.v4.app.Fragment implements View.OnClickListener{

    @Nullable
    private ListView myListVieww;
    private List<Transaction> ekonom = new ArrayList<>();

    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.ekonomi_fragment, container, false);
        Button btnI = (Button)view.findViewById(R.id.Ekonomi_Button_Ny_Inkommst);
        btnI.setOnClickListener(this);
        Button btnU = (Button)view.findViewById(R.id.Ekonomi_Button_Ny_Utgift);
        btnU.setOnClickListener(this);


        getEkonomi testEkonomi = new getEkonomi();
        String testSUrl = "http://spaaket.no-ip.org:1080/GitarrAppAPI/webresources/rest.transaction";
        testEkonomi.execute(testSUrl);
        return view;
    }

    @Override
    public void onClick(View v) {
        Fragment fragment;
        FragmentTransaction fm = getFragmentManager().beginTransaction();

        switch(v.getId()){
            case R.id.Ekonomi_Button_Ny_Inkommst:
                //((TextView)view.findViewById(R.id.annons_titel_1)).setText("Supe du klicke på knapp!");

                //int id = item.getItemId();
                fragment = new nyInkomstFragment();
                fm.replace(R.id.content, fragment);
                fm.commit();
                //setTitle(item.getTitle());
                //fm.addToBackStack(this); //Kan vara bra för när man ska stänga formuläret./koppla formuläret mot annonssidan.

                break;
            case R.id.Ekonomi_Button_Ny_Utgift:

                fragment = new nyUtgiftFragment();
                fm.replace(R.id.content, fragment);
                fm.commit();

                break;
        }
    }
    private class getEkonomi extends DB_Connect {
        @Override
        protected void onPostExecute(String result) {

            try {
                Transaction_Parse Eparser = new Transaction_Parse();
                ekonom = Eparser.parse(result);
                String s = "";

                EkonomiAdapter adapter = new EkonomiAdapter(getContext(),R.layout.annons_item, ekonom);
                myListVieww = (ListView)view.findViewById(R.id.myListVieww);
                myListVieww.setAdapter(adapter);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

//MADE BY OSkAR
/*package com.example.magnus.menufragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
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
        //textView = (TextView) view.findViewById(R.id.test_textView);
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

            }
        });

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
        });

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


                for (Product model : products) {
                    s += " " + model.getProductName();
                    s += " " + model.getSellingPrice();
                }


            }
        }
    }
}
*/
