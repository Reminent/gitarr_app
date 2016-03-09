package com.example.magnus.menufragment;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.magnus.menufragment.XML_Parsing.Consultation;

import java.util.List;

public class SchemaAdapter extends ArrayAdapter<Consultation>{

    Context context;
    int layoutResourceId;
    private List<Consultation> data;
    LinearLayout ll;
    private String date;

    static class SchemaHolder {
        TextView time;
        TextView customer;
        TextView description;
        TextView date;
    }

    public SchemaAdapter(Context context, int layoutResourceId, List<Consultation> data, String selectedDate) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
        this.date = selectedDate;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View row = convertView;
        SchemaHolder holder = null;

        //ll = (LinearLayout) row.findViewById(R.id.schema_item);

        if(row == null)
        {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);

            holder = new SchemaHolder();
            holder.time = (TextView) row.findViewById(R.id.schema_time);
            holder.customer = (TextView) row.findViewById(R.id.schema_customer);
            holder.description = (TextView) row.findViewById(R.id.schema_description);
            holder.date = (TextView) row.findViewById(R.id.schema_selectedDate);
            row.setTag(holder);
        }
        else
        {
            holder = (SchemaHolder) row.getTag();
        }

        final Consultation consultation = data.get(position);

        // Splitta datum
        holder.time.setText(consultation.getEndDateAndTime());
        holder.customer.setText(consultation.getCustomerName());
        holder.description.setText(consultation.getCustomerPhone());
        holder.date.setText(date);
        
        /*
        ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog alertDialog = new AlertDialog.Builder(getContext()).create();
                alertDialog.setTitle("Beskrivning");
                alertDialog.setMessage(Consultation.getAdvertDescription());

                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                alertDialog.show();
            }
        });
        */


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
}
