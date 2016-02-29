package com.example.magnus.menufragment;


import android.hardware.Camera;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class AnnonsFormularFragment extends android.support.v4.app.Fragment implements View.OnClickListener{
    @Nullable

    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.annons_formular_fragment, container, false);
        Button klarBtn = (Button)view.findViewById(R.id.klar);
        klarBtn.setOnClickListener(this);
        Button AvbrytBtn = (Button)view.findViewById(R.id.avbryt);
        AvbrytBtn.setOnClickListener(this);

        ImageButton kameraBtn = (ImageButton)view.findViewById(R.id.kamerasymbol);
        kameraBtn.setOnClickListener(this);

        ImageButton galleriBtn = (ImageButton)view.findViewById(R.id.gallerisymbol);
        galleriBtn.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        Fragment fragment;
        FragmentTransaction fm = getFragmentManager().beginTransaction();

        switch(v.getId()){
            case R.id.klar:

                //((TextView)view.findViewById(R.id.annons_titel_1)).setText("Supe du klicke på knapp!");
                //Log.d("Case", "klar");
                //int id = item.getItemId();
                fragment = new AnnonsFragment();
                fm.replace(R.id.content, fragment);
                fm.commit();
                //setTitle(item.getTitle());
                //fm.addToBackStack(this); //Kan vara bra för när man ska stänga formuläret./koppla formuläret mot annonssidan.

                break;

            case R.id.avbryt:

                //Log.d("Case", "avbryt");
                //int id = item.getItemId();
                fragment = new AnnonsFragment();
                fm.replace(R.id.content, fragment);
                fm.commit();

                break;
            case R.id.kamerasymbol:

                ((TextView)view.findViewById(R.id.taKort)).setText("Beep!");

                //Log.d("Case", "avbryt");
                //int id = item.getItemId();
                //((TextView)view.findViewById(R.id.annons_titel_1)).setText("Supe du klicke på knapp!");
                //fragment = new AnnonsFragment();
                //fm.replace(R.id.content, fragment);
                //fm.commit();

                break;
            case R.id.gallerisymbol:

                //Log.d("Case", "avbryt");
                //int id = item.getItemId();
                ((TextView)view.findViewById(R.id.väljBild)).setText("Boop!");

               // fragment = new AnnonsFragment();
               // fm.replace(R.id.content, fragment);
               // fm.commit();

                break;
        }
    }


}
