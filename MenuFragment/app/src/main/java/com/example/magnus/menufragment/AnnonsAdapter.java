package com.example.magnus.menufragment;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.magnus.menufragment.DB_Upload.DB_Delete;
import com.example.magnus.menufragment.XML_Parsing.Advert;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.List;

/**
 * Class AnnonsAdapter, It works like a bridge between the data
 * and the adapterview, which is the view that is being reused multiple times.
 */
public class AnnonsAdapter extends ArrayAdapter<Advert>{

    Context context;
    int layoutResourceId;
    private List<Advert> data;

    /**
     * when you are using this holder,
     * then you can easily access each view
     * without the need for the look-up which saving valuable processor cycles.
     */
    static class AnnonsHolder
    {
        ImageView imgIcon;
        TextView txtTitle;
        Button remove;
        Button change;
    }

    /**
     * Constructor which is called in AnnonsGet
     * @param context
     * @param layoutResourceId
     * @param data
     */
    public AnnonsAdapter(Context context, int layoutResourceId, List<Advert> data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
    }

    /**
     * getView method which makes holders and connects it to the XML icons and fills them with
     * Advert data depending on its position in the ArrayList
     * @param position
     * @param convertView
     * @param parent
     * @return
     */
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View row = convertView;
        AnnonsHolder holder;

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

        /**
         * Sets Title and adds a popup when clicked to show Description to save space in the List-
         * View
         */
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

        /**
         * Sets the holder value change to bundle current data of the item and send to a new
         * Fragment and to Delete the current item. Which is created with a new item id after com-
         * pleted.
         */
        final AnnonsHolder finalHolder = holder;
        holder.change.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Bundle bundle = new Bundle();
                bundle.putString("titel", advert.getAdvertTitle());
                bundle.putString("beskrivning", advert.getAdvertDescription());

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

                AppCompatActivity a = (AppCompatActivity) context; //ful hax a la Stefan
                Fragment fragment;
                FragmentTransaction fm = a.getSupportFragmentManager().beginTransaction();

                switch(v.getId()){
                    case R.id.redigera:
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

        /**
         * Holder remove which is to delete the current item in the ListView
         */
        holder.remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DB_Delete delete = new DB_Delete();
                String URL = "http://spaaket.no-ip.org:1080/GitarrAppAPI/webresources/rest.advert/" + advert.getAdvertid();
                delete.execute(URL);
            }
        });
        return row;
    }

    /**
     * So the program doesnt crash and complain about too much work on main thread.
     */
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
