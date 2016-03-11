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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.magnus.menufragment.XML_Parsing.Advert;
import com.example.magnus.menufragment.XML_Parsing.Product;

import java.util.List;

/**
 * Created by Mattias on 3/8/2016.
 */
public class LagerAdapter extends ArrayAdapter<Product>{

    Context context;
    int layoutResourceId;
    private List<Product> data;

    static class LagerHolder
    {
        ImageView imgIcon;
        TextView txtName;
        TextView txtAmount;
        Button remove;
        Button change;
    }

    public LagerAdapter(Context context, int layoutResourceId, List<Product> data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View row = convertView;
        LagerHolder holder = null;

        if(row == null)
        {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);

            holder = new LagerHolder();
            holder.imgIcon = (ImageView)row.findViewById(R.id.product_image);
            holder.txtName = (TextView)row.findViewById(R.id.product_name);
            holder.txtAmount = (TextView)row.findViewById(R.id.product_amount);
            holder.change = (Button)row.findViewById(R.id.product_edit_button);
            holder.remove = (Button)row.findViewById(R.id.product_delete_button);

            row.setTag(holder);
        }
        else
        {
            holder = (LagerHolder)row.getTag();
        }

        final Product product = data.get(position);

        holder.txtName.setText(product.getProductName());
        holder.txtName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog alertDialog = new AlertDialog.Builder(getContext()).create();
                alertDialog.setTitle("Om produkten");
                alertDialog.setMessage(product.getGenre());

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

        return row;

    }
}
