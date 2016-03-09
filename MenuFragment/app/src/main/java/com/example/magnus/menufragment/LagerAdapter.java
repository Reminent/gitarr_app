package com.example.magnus.menufragment;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.magnus.menufragment.XML_Parsing.Product;

/**
 * Created by Mattias on 3/8/2016.
 */
public class LagerAdapter extends ArrayAdapter<Product>{

    Context context;
    int resource;
    Product objects[] = null;

    public LagerAdapter(Context context, int resource, Product[] objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.objects = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        View row = convertView;
        ProductHolder holder = null;

        if(row == null){
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(resource, parent, false);

            holder = new ProductHolder();
            holder.image = (ImageView)row.findViewById(R.id.product_image);
            holder.title = (TextView)row.findViewById(R.id.product_title);

            row.setTag(holder);
        }
        else
        {
            holder = (ProductHolder)row.getTag();
        }

        Product product = objects[position];
        //holder.image.setImageResource(product.getImageId());
        holder.title.setText(product.getProductName());

        return row;
    }

    static class ProductHolder
    {
        ImageView image;
        TextView title;
    }
}
