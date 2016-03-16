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

import java.util.ArrayList;
import java.util.List;

/**
 * AnnonsFragment class, which is supposed to manage the main advert page for the user. The user
 * is suppose to be able to see it's Adverts in a ListView. In this file the the main fragment is
 * created and reacts to user interface. The user should be able to Edit and Delete it's content.
  */
public class AnnonsFragment extends android.support.v4.app.Fragment implements View.OnClickListener {
    @Nullable

    private ListView myListView;
    private List<Advert> adverts = new ArrayList<>();
    private View view;

    /**
     *
     * @param inflater
     * @param container
     * @param savedInstanceState: Is used to be able to send data to other activities.
     * @return: returns the view that was inflated.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.annons_fragment, container, false);
        Button btn = (Button)view.findViewById(R.id.skapany);
        btn.setOnClickListener(this);

        AnnonsGet testAG = new AnnonsGet();
        String url = "http://spaaket.no-ip.org:1080/GitarrAppAPI/webresources/rest.advert";
        testAG.execute(url);

        return view;
    }

    /**
     * Overrided onClick method to get user into a new Fragment for creating a new Advert. Switch-
     * case implemented for better future use, if the onClick listener should behave in other ways.
     * @param v:
     */
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

    /**
     * Method which extends DB_Connect to call Advert_Parse to be able to parse for data and use
     * it in a Adapter. The adapter is then set to the ListView myListView
     */
    private class AnnonsGet extends DB_Connect {
        @Override
        protected void onPostExecute(String result) {

            try {
                Advert_Parse parser = new Advert_Parse();
                adverts = parser.parse(result);

                AnnonsAdapter adapter = new AnnonsAdapter(getContext(),R.layout.annons_item, adverts);
                myListView = (ListView)view.findViewById(R.id.myListView);
                myListView.setAdapter(adapter);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}