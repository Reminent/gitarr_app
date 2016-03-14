package com.example.magnus.menufragment;

import android.app.Activity;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
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

import com.example.magnus.menufragment.DB_Connect.DB_Connect;
import com.example.magnus.menufragment.DB_Upload.DB_Delete;
import com.example.magnus.menufragment.XML_Parsing.Advert;
import com.example.magnus.menufragment.XML_Parsing.Inventory;
import com.example.magnus.menufragment.XML_Parsing.Product;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;

public class LagerAdapter extends ArrayAdapter<Product>{

    Context context;
    int layoutResourceId;
    private List<Product> data;

    static class LagerHolder
    {
        ImageView image;
        TextView productName;
        TextView productManufacturer;
        TextView productGenre;
        TextView productSellingPrice;
        TextView productBuyingPrice;
        Button edit;
        Button remove;
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
            holder.image = (ImageView)row.findViewById(R.id.productImage);
            holder.productName = (TextView)row.findViewById(R.id.productName);
            holder.productManufacturer = (TextView)row.findViewById(R.id.productManufacturer);
            holder.productGenre = (TextView)row.findViewById(R.id.productGenreName);
            holder.productBuyingPrice = (TextView)row.findViewById(R.id.productBuyingPrice);
            holder.productSellingPrice = (TextView)row.findViewById(R.id.productSellingPrice);
            holder.edit = (Button)row.findViewById(R.id.product_edit_button);
            holder.remove = (Button)row.findViewById(R.id.product_delete_button);

            row.setTag(holder);
        }
        else
        {
            holder = (LagerHolder)row.getTag();
        }

        final Product product = data.get(position);

        String getFromURL = "http://spaaket.no-ip.org:1080/quercus-4.0.39/";
        String fullURL = getFromURL + product.getImageURl();
        new DownloadImageTask(holder.image).execute(fullURL);

        holder.productName.setText(product.getProductName());
        holder.productManufacturer.setText(product.getManufacturer());
        holder.productGenre.setText(product.getGenre());
        holder.productBuyingPrice.setText(product.getPurchasePrice());
        holder.productSellingPrice.setText(product.getSellingPrice());

        final LagerHolder finalHolder = holder;
        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getContext(), "Redigera annons nr." + position, Toast.LENGTH_LONG).show();

                Bundle bundle = new Bundle();
                bundle.putString("productName",product.getProductName());
                bundle.putString("productManufacturer",product.getManufacturer());
                bundle.putString("productGenre",product.getGenre());
                bundle.putString("productPurchaserPrice",product.getPurchasePrice());
                bundle.putString("productSellingPrice", product.getSellingPrice());

                Bitmap myBm = null;
                try {
                    View mv = finalHolder.image;
                    //Code preventing drawingcache from being null
                    mv.setDrawingCacheEnabled(true);
                    mv.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                            View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
                    mv.layout(0, 0, mv.getMeasuredWidth(), mv.getMeasuredHeight());
                    mv.buildDrawingCache(true);
                    myBm = Bitmap.createBitmap(mv.getDrawingCache());
                    mv.setDrawingCacheEnabled(false); // clear drawing cache
                } catch (Exception e){
                    e.printStackTrace();
                }

                //Convert to byte array
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                if (myBm != null) {
                    myBm.compress(Bitmap.CompressFormat.PNG, 100, stream);
                }
                byte[] byteArray = stream.toByteArray();
                bundle.putByteArray("productImage", byteArray);

                AppCompatActivity a = (AppCompatActivity) context; //ful hax a la Stefan
                Fragment fragment;
                FragmentTransaction fm = a.getSupportFragmentManager().beginTransaction();
                switch(v.getId()){
                    case R.id.product_edit_button:

                        fragment = new LagerFormularRedigera();
                        fragment.setArguments(bundle);
                        fm.replace(R.id.content, fragment);
                        fm.addToBackStack(null);
                        fm.commit();

                        break;
                }
            }
        });

        holder.remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Ta bort annons nr." + position, Toast.LENGTH_LONG).show();
                //TODO: Update site when an advert is deleted.
                DB_Delete delete = new DB_Delete();
                String URL = "http://spaaket.no-ip.org:1080/GitarrAppAPI/webresources/rest.product/" + product.getProductId();
                delete.execute(URL);
            }
        });
        return row;
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }
}