package com.example.magnus.menufragment;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class LagerFragment extends android.support.v4.app.Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.lager_fragment, container, false);
        return view;
    }
/*
    button1.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String stringUrl = "http://spaaket.no-ip.org:1080/GitarrAppAPI/webresources/rest.transactionproduct";

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
    */
}
