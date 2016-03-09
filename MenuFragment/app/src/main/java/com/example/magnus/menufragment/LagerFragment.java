package com.example.magnus.menufragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.magnus.menufragment.DB_Connect.DB_Connect;
import com.example.magnus.menufragment.XML_Parsing.Product;
import com.example.magnus.menufragment.XML_Parsing.Product_Parse;

import java.util.List;

public class LagerFragment extends android.support.v4.app.Fragment {

    List<Product> products = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.lager_fragment, container ,false);
        LagerGet lagerGet = new LagerGet();
        lagerGet.execute("http://spaaket.no-ip.org:1080/GitarrAppAPI/webresources/rest.product");

        return view;
    }

    private class LagerGet extends DB_Connect {

/*
    button1.setOnClickListener(new View.OnClickListener() {
>>>>>>> origin/Annons-Mattias
        @Override
        protected void onPostExecute(String result) {
            //List<Product> products = null;
            try {
                Product_Parse parser = new Product_Parse();
                products = parser.parse(result);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
}
    });
    */
    }