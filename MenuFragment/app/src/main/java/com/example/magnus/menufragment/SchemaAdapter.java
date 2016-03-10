package com.example.magnus.menufragment;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.magnus.menufragment.XML_Parsing.Consultation;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class SchemaAdapter extends ArrayAdapter<Consultation> {

    Context context;
    int layoutResourceId;
    private List<Consultation> data;
    LinearLayout ll;

    static class SchemaHolder {
        TextView time;
        TextView customer;
        TextView description;
    }

    public SchemaAdapter(Context context, int layoutResourceId, List<Consultation> data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
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
            row.setTag(holder);
        }
        else
        {
            holder = (SchemaHolder) row.getTag();
        }

        final Consultation consultation = data.get(position);

        // Splitta datum
        String endTime = consultation.getEndDateAndTime();
        String startTime = consultation.getStartDateAndTime();

        String[] endSplit = endTime.split("T");
        String[] startSplit = startTime.split("T");

        endSplit = endSplit[1].split(":");
        startSplit = startSplit[1].split(":");

        String endHour = endSplit[0];
        String endMinute = endSplit[1];
        String startHour = startSplit[0];
        String startMinute = startSplit[1];

        holder.time.setText(startHour + ":" + startMinute + " - " + endHour + ":" + endMinute);
        holder.customer.setText(consultation.getCustomerName());

        holder.description.setText(consultation.getConsultationDescription());
        
        return row;
    }
}
