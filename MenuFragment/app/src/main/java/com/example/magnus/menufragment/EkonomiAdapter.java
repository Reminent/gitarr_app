package com.example.magnus.menufragment;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.magnus.menufragment.XML_Parsing.Transaction;

import java.util.List;

/*
 * Created by Albin on 2016-03-09.
 */
public class EkonomiAdapter extends ArrayAdapter<Transaction> implements View.OnClickListener {

    Context context;
    int layoutResourceId;
    private List<Transaction> data;


    public EkonomiAdapter(Context context, int layoutResourceId, List<Transaction> data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View row = convertView;
        EkonomiHolder holder = null;

        if(row == null)
        {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);

            holder = new EkonomiHolder();
            holder.txtTitle = (TextView)row.findViewById(R.id.annons_item_title);
           // holder.change = (Button)row.findViewById(R.id.redigera);
           // holder.remove = (Button)row.findViewById(R.id.ta_bort);

            row.setTag(holder);
        }
        else
        {
            holder = (EkonomiHolder)row.getTag();
        }

        final Transaction transaction = data.get(position);
        holder.txtTitle.setText(transaction.getTransactionAmount());

        holder.txtTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog alertDialog = new AlertDialog.Builder(getContext()).create();
                alertDialog.setTitle("Beskrivning");
                alertDialog.setMessage(transaction.getTransactionDate());

                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                alertDialog.show();
            }
        });

        //holder.imgIcon.setImageResource(advert.icon);
        //holder.imgIcon.setImageResource(advert.getImageid()); //TODO: fix this so we can fetch images from db
/*
        holder.change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Redigera annons nr." + position, Toast.LENGTH_LONG).show();
                //TODO: Change this so it changes the database instead.
            }
        });

        holder.remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Ta bort annons nr." + position, Toast.LENGTH_LONG).show();
                //TODO: Change this so it deletes an item in the database instead.
            }
        });
*/
        return row;

    }

    @Override
    public void onClick(View v) {

    }

    static class EkonomiHolder
    {
        //String imgIcon;
        ImageView imgIcon;
        TextView txtTitle;
        //Button remove;
        //Button change;
    }
}