package com.example.magnus.menufragment;

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
import com.example.magnus.menufragment.XML_Parsing.Advert;
import com.example.magnus.menufragment.XML_Parsing.Advert_Parse;
import com.example.magnus.menufragment.XML_Parsing.Consultation;
import com.example.magnus.menufragment.XML_Parsing.Consultation_Parse;

import java.util.List;

public class AnnonsFragment extends android.support.v4.app.Fragment implements View.OnClickListener {
    @Nullable

    TextView tv = null;
    TextView advertTitle2 = null;
    TextView advertTitle10 = null;

    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.annons_fragment, container, false);
        Button btn = (Button)view.findViewById(R.id.skapany);
        btn.setOnClickListener(this);
        tv = (TextView)view.findViewById(R.id.annons_titel_1);
        AnnonsGet testAG = new AnnonsGet();
        String testSUrl = "http://spaaket.no-ip.org:1080/GitarrAppAPI/webresources/rest.advert";
        testAG.execute(testSUrl);

        /*
        advertTitle2 = (TextView)view.findViewById(R.id.annons_titel_2);
        advertTitle10 = (TextView)view.findViewById(R.id.annons_titel_10);
        */
        return view;

    }
    @Override
    public void onClick(View v) {
        Fragment fragment;
        FragmentTransaction fm = getFragmentManager().beginTransaction();

        switch(v.getId()){
            case R.id.skapany:
                //((TextView)view.findViewById(R.id.annons_titel_1)).setText("Supe du klicke på knapp!");

                //int id = item.getItemId();
                fragment = new AnnonsFormularFragment();
                fm.replace(R.id.content, fragment);
                fm.addToBackStack(null);
                fm.commit();
                //setTitle(item.getTitle());
                //fm.addToBackStack(this); //Kan vara bra för när man ska stänga formuläret./koppla formuläret mot annonssidan.

                break;
        }
    }

    private class AnnonsGet extends DB_Connect {
        @Override
        protected void onPostExecute(String result) {

            // Consultation

            List<Advert> adverts = null;

            try {
                Advert_Parse parser = new Advert_Parse();
                adverts = parser.parse(result);
                String s = "";
                //String s = advert.get(1).getProductName();

                //s = s + " " + advert.get(0).getGenre();

                tv.setText(adverts.get(0).getAdvertTitle());
                //advertTitle2.setText(adverts.get(1).getAdvertTitle());
                //advertTitle10.setText(adverts.get(2).getAdvertTitle());
                //textView.setText(advert.size());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
