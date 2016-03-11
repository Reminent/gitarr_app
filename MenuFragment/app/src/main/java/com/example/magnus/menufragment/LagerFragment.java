package com.example.magnus.menufragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.magnus.menufragment.DB_Connect.DB_Connect;
import com.example.magnus.menufragment.XML_Parsing.Advert;
import com.example.magnus.menufragment.XML_Parsing.Advert_Parse;
import com.example.magnus.menufragment.XML_Parsing.Product;
import com.example.magnus.menufragment.XML_Parsing.Product_Parse;

import java.util.ArrayList;
import java.util.List;

public class LagerFragment extends android.support.v4.app.Fragment {

    private ListView myListView;
    private List<Product> products = new ArrayList<>();
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.lager_fragment, container ,false);
        LagerGet lagerGet = new LagerGet();
        lagerGet.execute("http://spaaket.no-ip.org:1080/GitarrAppAPI/webresources/rest.product");

        return view;
    }


    private class LagerGet extends DB_Connect {
        @Override
        protected void onPostExecute(String result) {

            try {
                Product_Parse parser = new Product_Parse();
                products = parser.parse(result);

                LagerAdapter adapter = new LagerAdapter(getContext(),R.layout.lager_item_row, products);
                myListView = (ListView)view.findViewById(R.id.lagerListView);
                myListView.setAdapter(adapter);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}