
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
    private class getEkonomi extends DB_Connect {
        @Override
        protected void onPostExecute(String result) {

            try {
                Transaction_Parse Eparser = new Transaction_Parse();
                ekonom = Eparser.parse(result);
                String s = "";

                EkonomiAdapter adapter = new EkonomiAdapter(getContext(),R.layout.ekonomi_item, ekonom);
                myListVieww = (ListView)view.findViewById(R.id.myListVieww);
                myListVieww.setAdapter(adapter);

                //myListVieww.smoothScrollToPosition(0);
                //myListVieww.scrollTo(0, myListVieww.getHeight());
                //myListVieww.setSelection(adapter.getCount() - 1);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }


}


