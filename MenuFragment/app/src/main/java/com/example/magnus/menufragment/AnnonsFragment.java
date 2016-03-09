package com.example.magnus.menufragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.magnus.menufragment.DB_Connect.DB_Connect;
import com.example.magnus.menufragment.XML_Parsing.Advert;
import com.example.magnus.menufragment.XML_Parsing.Advert_Parse;
import java.util.ArrayList;
import java.util.List;

public class AnnonsFragment extends android.support.v4.app.Fragment implements View.OnClickListener {
    @Nullable

    private ListView myListView;
    private List<Advert> adverts = new ArrayList<>();

    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.annons_fragment, container, false);
        Button btn = (Button)view.findViewById(R.id.skapany);
        btn.setOnClickListener(this);

        AnnonsGet testAG = new AnnonsGet();
        String testSUrl = "http://spaaket.no-ip.org:1080/GitarrAppAPI/webresources/rest.advert";
        testAG.execute(testSUrl);

        return view;
    }

    @Override
    public void onClick(View v) {
        Fragment fragment;
        FragmentTransaction fm = getFragmentManager().beginTransaction();

        switch(v.getId()){
            case R.id.skapany:

                fragment = new AnnonsFormularFragment();
                fm.replace(R.id.content, fragment);
                fm.addToBackStack(null);
                fm.commit();

                break;
        }
    }


    private class AnnonsGet extends DB_Connect {
        @Override
        protected void onPostExecute(String result) {

            try {
                Advert_Parse parser = new Advert_Parse();
                adverts = parser.parse(result);
                String s = "";

                AnnonsAdapter adapter = new AnnonsAdapter(getContext(),R.layout.annons_item, adverts);
                myListView = (ListView)view.findViewById(R.id.myListView);
                myListView.setAdapter(adapter);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}