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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;

/**
 * Created by Jonathan on 2016-03-08.
 */
public class AnnonsAdapter extends ArrayAdapter<Advert>{


    Context context;
    int layoutResourceId;
    private List<Advert> data;

    static class AnnonsHolder
    {
        ImageView imgIcon;
        TextView txtTitle;
        Button remove;
        Button change;
    }

    public AnnonsAdapter(Context context, int layoutResourceId, List<Advert> data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View row = convertView;
        AnnonsHolder holder = null;

        if(row == null)
        {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);

            holder = new AnnonsHolder();
            holder.imgIcon = (ImageView)row.findViewById(R.id.annons_item_image);
            holder.txtTitle = (TextView)row.findViewById(R.id.annons_item_title);
            holder.change = (Button)row.findViewById(R.id.redigera);
            holder.remove = (Button)row.findViewById(R.id.ta_bort);

            row.setTag(holder);
        }
        else
        {
            holder = (AnnonsHolder)row.getTag();
        }

        final Advert advert = data.get(position);

        String getFromURL = "http://spaaket.no-ip.org:1080/quercus-4.0.39/";
        String fullURL = getFromURL + advert.getImageUrl();
        new DownloadImageTask(holder.imgIcon).execute(fullURL);

        holder.txtTitle.setText(advert.getAdvertTitle());

        holder.txtTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog alertDialog = new AlertDialog.Builder(getContext()).create();
                alertDialog.setTitle("Beskrivning");
                alertDialog.setMessage(advert.getAdvertDescription());

                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                alertDialog.show();
            }
        });

        final AnnonsHolder finalHolder = holder;
        holder.change.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Redigera annons nr." + position, Toast.LENGTH_LONG).show();
                //TODO: Change this so it changes the database instead.

                Bundle bundle = new Bundle();
                bundle.putString("titel", advert.getAdvertTitle());
                bundle.putString("beskrivning", advert.getAdvertDescription());
                //bundle.put("BILD);


                Bitmap myBm = null;
                try {
                    View mv = finalHolder.imgIcon;
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
                bundle.putByteArray("bild", byteArray);

                Log.d("wtf", "3");

                //TODO:: fixa så man kan redigera bilden.

                AppCompatActivity a = (AppCompatActivity) context; //ful hax a la Stefan
                Fragment fragment;
                FragmentTransaction fm = a.getSupportFragmentManager().beginTransaction();
                switch(v.getId()){
                    case R.id.redigera:

                        Toast.makeText(getContext(), "ändrar annons nr." + position, Toast.LENGTH_LONG).show();
                        //TODO: Update site when an advert is edited. Change from remove+add to edit.

                        DB_Delete delete = new DB_Delete();
                        String URL = "http://spaaket.no-ip.org:1080/GitarrAppAPI/webresources/rest.advert/" + advert.getAdvertid();
                        delete.execute(URL);


                        fragment = new AnnonsFormularRedigera();
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
                String URL = "http://spaaket.no-ip.org:1080/GitarrAppAPI/webresources/rest.advert/" + advert.getAdvertid();
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
