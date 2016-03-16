
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
import com.example.magnus.menufragment.XML_Parsing.Transaction_Parse;
import com.example.magnus.menufragment.XML_Parsing.Transaction;

import java.util.ArrayList;
import java.util.List;

/*
 * Created by Albin and Martin on 2016-03-09.
 */

/**
 * EkonomiFragment class, which is supposed to manage the main ekonomi oage for the user. The user
 * is suppose to be able to see it's transaktions in a ListView. In this file the mmain fragment is
 * created and reacts to user interface. The user should be able to create and delete int's content.
 */
public class EkonomiFragment extends android.support.v4.app.Fragment implements View.OnClickListener{
    @Nullable
    private ListView myListVieww;
    private List<Transaction> ekonom = new ArrayList<>();

    View view;

    /**
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return Returns the view that was inflated.
     */
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

    /**
     * Overrided onClick method to get user into a new Fragment for creating a new income and outcome.
     * Swich-case implemented for easy handeling the out/income view.
     * @param v
     */
    @Override
    public void onClick(View v) {
        Fragment fragment;
        FragmentTransaction fm = getFragmentManager().beginTransaction();

        switch(v.getId()){
            case R.id.Ekonomi_Button_Ny_Inkommst:
                fragment = new nyInkomstFragment();
                fm.replace(R.id.content, fragment);
                fm.commit();
                break;
            case R.id.Ekonomi_Button_Ny_Utgift:

                fragment = new nyUtgiftFragment();
                fm.replace(R.id.content, fragment);
                fm.commit();

                break;
        }
    }

    /**
     * Method which extends DB_Connect to call Transaction_Parse to be able to parse for data and use it in a Adapter.
     * The Adapter is then set to the Listwiew ekonomiListView.
     */
    private class getEkonomi extends DB_Connect {
        @Override
        protected void onPostExecute(String result) {

            try {
                Transaction_Parse Eparser = new Transaction_Parse();
                ekonom = Eparser.parse(result);
                String s = "";

                EkonomiAdapter adapter = new EkonomiAdapter(getContext(),R.layout.ekonomi_item, ekonom);
                myListVieww = (ListView)view.findViewById(R.id.ekonomiListView);
                myListVieww.setAdapter(adapter);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}


