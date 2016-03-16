package com.example.magnus.menufragment;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.magnus.menufragment.DB_Upload.DB_Delete;
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
        TextView phone;
        Button remove;
        Button edit;
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

        if (row == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);

            holder = new SchemaHolder();
            holder.time = (TextView) row.findViewById(R.id.schema_time);
            holder.customer = (TextView) row.findViewById(R.id.schema_customer);
            holder.description = (TextView) row.findViewById(R.id.schema_description);
            holder.phone = (TextView) row.findViewById(R.id.schema_phone);
            holder.remove = (Button) row.findViewById(R.id.schema_delete);
            holder.edit = (Button) row.findViewById(R.id.schema_edit);
            row.setTag(holder);
        } else {
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
        holder.phone.setText(consultation.getCustomerPhone());

        holder.remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DB_Delete delete = new DB_Delete();
                String URL = "http://spaaket.no-ip.org:1080/GitarrAppAPI/webresources/rest.consultation/" + consultation.getConsultationid();
                delete.execute(URL);
            }
        });

        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppCompatActivity a = (AppCompatActivity) context;

                Fragment fragment;
                FragmentTransaction fm = a.getSupportFragmentManager().beginTransaction();

                Bundle bundle = new Bundle();
                bundle.putString("id", consultation.getConsultationid());
                bundle.putString("name", consultation.getCustomerName());
                bundle.putString("phone", consultation.getCustomerPhone());
                bundle.putString("start", consultation.getStartDateAndTime());
                bundle.putString("end", consultation.getEndDateAndTime());
                bundle.putString("description", consultation.getConsultationDescription());

                switch (v.getId()) {
                    case R.id.schema_edit:
                        fragment = new SchemaUpdateTimeFragment();
                        fragment.setArguments(bundle);
                        fm.replace(R.id.content, fragment);
                        fm.addToBackStack(null);
                        fm.commit();
                        break;
                }
            }
        });

        holder.time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog alertDialog = new AlertDialog.Builder(getContext()).create();
                alertDialog.setTitle(consultation.getCustomerName());
                alertDialog.setMessage(consultation.getConsultationDescription() + "\n" + consultation.getCustomerPhone());

                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                alertDialog.show();
            }
        });

        holder.phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                callIntent.setData(Uri.parse("tel:" + consultation.getCustomerPhone()));
                Log.d("phone", "fuck me");
                if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                context.startActivity(callIntent);
            }
        });
        return row;
    }
}
