package com.example.magnus.menufragment;

import android.app.DialogFragment;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.magnus.menufragment.DB_Connect.DB_Connect;


public class nyInkomstFragment extends android.support.v4.app.Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    @Nullable
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ny_inkomst, container, false);
        Button btn = (Button)view.findViewById(R.id.btnStop);
        btn.setOnClickListener(this);
        Button btnD = (Button)view.findViewById(R.id.btnDateChange);
        btn.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {

        String stringUrl = "http://reminent.no-ip.org/slimapi/public/json";
        ConnectivityManager connMgr;
        connMgr = (ConnectivityManager)
                getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            try{
                EkoGet task = new EkoGet();
                task.execute(stringUrl);
            } catch(Throwable e){
            }
        } else {
            //textView.setText("No network connection available.");
        }



        //Fragment fragment;
        //FragmentTransaction fm = getFragmentManager().beginTransaction();
        Fragment fragment;
        FragmentTransaction fm = getFragmentManager().beginTransaction();
        datePickFragment db;
        FragmentTransaction dm = getFragmentManager().beginTransaction();
        switch(v.getId()){
            case R.id.btnStop:
                fragment = new EkonomiFragment();
                fm.replace(R.id.content, fragment);
                fm.commit();
                //((TextView)view.findViewById(R.id.annons_titel_1)).setText("Supe du klicke på knapp!");

                //int id = item.getItemId();
                //fragment = new nyInkomstFragment();
                //fm.replace(R.id.content, fragment);
                //fm.commit();
                //setTitle(item.getTitle());
                //fm.addToBackStack(this); //Kan vara bra för när man ska stänga formuläret./koppla formuläret mot annonssidan.

                break;

            case R.id.btnDateChange:
                db = new datePickFragment();
                break;

        }
    }
    private class EkoGet extends DB_Connect {
        @Override
        protected void onPostExecute(String result) {
            textView.setText(result);
        }
    }
}
