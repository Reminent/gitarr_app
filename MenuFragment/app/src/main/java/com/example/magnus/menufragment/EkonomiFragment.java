package com.example.magnus.menufragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class EkonomiFragment extends android.support.v4.app.Fragment implements View.OnClickListener{

    @Nullable

    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.ekonomi_fragment, container, false);
        Button btnI = (Button)view.findViewById(R.id.nyinkomst);
        btnI.setOnClickListener(this);
        Button btnU = (Button)view.findViewById(R.id.nyutgift);
        btnU.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        Fragment fragment;
        FragmentTransaction fm = getFragmentManager().beginTransaction();

        switch(v.getId()){
            case R.id.nyinkomst:
                //((TextView)view.findViewById(R.id.annons_titel_1)).setText("Supe du klicke på knapp!");

                //int id = item.getItemId();
                fragment = new nyInkomstFragment();
                fm.replace(R.id.content, fragment);
                fm.commit();
                //setTitle(item.getTitle());
                //fm.addToBackStack(this); //Kan vara bra för när man ska stänga formuläret./koppla formuläret mot annonssidan.

                break;
            case R.id.nyutgift:

                fragment = new nyUtgiftFragment();
                fm.replace(R.id.content, fragment);
                fm.commit();

                break;
        }
    }
}
