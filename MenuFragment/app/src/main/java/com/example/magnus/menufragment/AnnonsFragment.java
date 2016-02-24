package com.example.magnus.menufragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class AnnonsFragment extends android.support.v4.app.Fragment implements View.OnClickListener {
    @Nullable

    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.annons_fragment, container, false);
        Button btn = (Button)view.findViewById(R.id.skapany);
        btn.setOnClickListener(this);

        return view;

    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.skapany:
                ((TextView)view.findViewById(R.id.annons_titel_1)).setText("Supe du klicke på knapp!");
                break;
        }
    }
}
