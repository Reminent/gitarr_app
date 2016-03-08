package com.example.magnus.menufragment;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.magnus.menufragment.XML_Parsing.Advert;

import java.util.List;

/**
 * Created by Jonathan on 2016-03-08.
 */
public class AnnonsAdapter extends ArrayAdapter<Advert> {

    Context context;
    int layoutResourceId;
    private List<Advert> data;


    public AnnonsAdapter(Context context, int layoutResourceId, List<Advert> data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        AnnonsHolder holder = null;

        if(row == null)
        {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);

            holder = new AnnonsHolder();
            holder.imgIcon = (ImageView)row.findViewById(R.id.annons_item_image);
            holder.txtTitle = (TextView)row.findViewById(R.id.annons_item_title);

            row.setTag(holder);
        }
        else
        {
            holder = (AnnonsHolder)row.getTag();
        }

        Advert advert = data.get(position);
        holder.txtTitle.setText(advert.getAdvertTitle());

        //holder.imgIcon.setImageResource(advert.icon);
       //holder.imgIcon.setImageResource(advert.getImageid()); //TODO: fix this so we can fetch images from db
        return row;
    }

    static class AnnonsHolder
    {
        //String imgIcon;
        ImageView imgIcon;
        TextView txtTitle;
    }
}
