package com.example.magnus.menufragment;


import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.magnus.menufragment.DB_Connect.DB_Connect;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Class AnnonsFormularRedigera which handles the formular
 * that pops up when you click on the "Redigera" button in the Advert page.
 */
public class AnnonsFormularRedigera extends android.support.v4.app.Fragment implements View.OnClickListener{
    @Nullable
    private ContentResolver cr;
    private static final int TAKE_PICTURE = 1;
    private Uri imageUri;
    private static final int SELECT_PICTURE = 2;
    private Uri selectedImage;
    private Bitmap bitmap;
    private String pictureName;
    private String titelStr;
    private String beskrivningStr;
    private String encoded;
    private Context myContext;
    private String formattedDate;
    private String fDate;
    private static final String TAG = AnnonsFormularFragment.class.getName();


    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.annons_formular_fragment, container, false);

        Button doneButton = (Button)view.findViewById(R.id.done);
        doneButton.setOnClickListener(this);

        Button cancelButton = (Button)view.findViewById(R.id.cancel);
        cancelButton.setOnClickListener(this);

        Button cameraButton = (Button)view.findViewById(R.id.camera);
        cameraButton.setOnClickListener(this);

        Button galleryButton = (Button)view.findViewById(R.id.gallery);
        galleryButton.setOnClickListener(this);

        TextView formularTitle = (TextView)view.findViewById(R.id.formularTitle);
        formularTitle.setText("Redigera annons");

        myContext = getContext();

        //Used for things that needs to be named unique (based on date on time)
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date cDate = new Date();
        fDate = new SimpleDateFormat("yyyy-MM-dd").format(cDate);
        formattedDate = df.format(c.getTime()).replace(" ", ""); //Now formattedDate has current date/time
        pictureName = formattedDate + ".jpg";

        //Fetches the bundle which is filled in the AnnonsAdapter class.
        Bundle bundle = this.getArguments();

        //Fixes the set title
        String changedTitel = bundle.getString("titel");
        EditText changeTxtTitel = (EditText) view.findViewById(R.id.editTextTitel);
        changeTxtTitel.setText(changedTitel);

        //Fixes the set description
        String changedBeskrivning = bundle.getString("beskrivning");
        EditText changeTxtBeskrivning= (EditText) view.findViewById(R.id.editTextBeskrivning);
        changeTxtBeskrivning.setText(changedBeskrivning);

        cr = getActivity().getContentResolver();

        //Fixes the set image
        byte[] byteArray = bundle.getByteArray("bild");
        Bitmap bmp = null;
        if (byteArray != null) {
            bmp = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
        }
        ImageView changeImgView= (ImageView) view.findViewById(R.id.image_camera);
        changeImgView.setImageBitmap(bmp);

        //Tries to save the bitmap of the image to a variable selectedImage which is used later.
        File changedPhoto = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), pictureName);
        if(saveBitmapToFile(bmp, changedPhoto)) {
            Uri tmpImage = Uri.fromFile(changedPhoto);
            selectedImage = tmpImage;
        }
        return view;
    }

    /**
     * Takes a picture and stores it in the gallery.
     * @param v: The view
     */
    public void takePhoto(View v){
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        File photo = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), pictureName);
        imageUri = Uri.fromFile(photo);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri); //puts it in the gallery
        startActivityForResult(intent, TAKE_PICTURE); //We are passing in number 1, which is a request code  == main camera.
    }

    /**
     * Covers what happens when you click on gallery or imagecapture
     * @param requestCode
     * @param resultCode
     * @param intent
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent){
        super.onActivityResult(requestCode, resultCode, intent);
        ImageView imageView = (ImageView)view.findViewById(R.id.image_camera);

        switch (requestCode) {
            case TAKE_PICTURE:
                selectedImage = imageUri;
                getActivity().getContentResolver().notifyChange(selectedImage, null);

                //Sets the previewImage to the image selected
                try{
                    bitmap = MediaStore.Images.Media.getBitmap(cr, selectedImage);
                    imageView.setImageBitmap(bitmap);
                }catch (Exception e){
                    e.printStackTrace();
                }
                break;

            case SELECT_PICTURE:

                //Sets the previewImage to the image selected
                try {
                    selectedImage = intent.getData();
                    bitmap = MediaStore.Images.Media.getBitmap(cr, selectedImage);
                    imageView.setImageBitmap(bitmap);
                } catch (Exception e){
                    e.printStackTrace();
                }
                break;

            default:
                Log.d( TAG, "onActivityResult default case called.");
                break;
        }
    }

    /**
     * Checks if an edittext is empty or not.
     * @param etText:
     * @return: True or false
     */
    private boolean isEmpty(EditText etText) {
        return etText.getText().toString().trim().length() == 0;
    }

    /**
     * Handles what happens if you click on the buttons Avbryt, Galleri, Kamera och Klar;
     * @param v
     */
    @Override
    public void onClick(View v) {
        FragmentTransaction fm = getFragmentManager().beginTransaction();
        EditText inputTxtTitel = (EditText)view.findViewById(R.id.editTextTitel);
        EditText inputTxtBeskrivning= (EditText)view.findViewById(R.id.editTextBeskrivning);

        switch(v.getId()){
            //Send an advert to the database.
            case R.id.done:
                if(!isEmpty(inputTxtTitel)&& !isEmpty(inputTxtBeskrivning) && selectedImage != null) {
                    try {
                        titelStr = inputTxtTitel.getText().toString();
                        beskrivningStr = inputTxtBeskrivning.getText().toString();

                        bitmap = MediaStore.Images.Media.getBitmap(cr, selectedImage);
                        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                        byte[] byteArray = byteArrayOutputStream .toByteArray();
                        encoded = Base64.encodeToString(byteArray, Base64.DEFAULT);

                        AnnonsPut ap = new AnnonsPut();
                        String url = "http://spaaket.no-ip.org:1080/quercus-4.0.39/connection.php";
                        ap.execute(url);

                        Toast.makeText(getContext().getApplicationContext(),
                                "Annons ändrad i databasen"
                                , Toast.LENGTH_LONG).show();

                    }catch (Exception e){
                        e.printStackTrace();
                    }

                    getFragmentManager().popBackStack();
                    fm.commit();
                } else {
                    Toast.makeText(getContext().getApplicationContext(),
                            "Du måste välja titel, beskrivning och bild" , Toast.LENGTH_LONG).show();
                }
                break;

            case R.id.cancel:

                getFragmentManager().popBackStack();
                fm.commit();
                break;

            case R.id.camera:

                takePhoto(v);
                break;

            case R.id.gallery:

                Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);//opens gallery
                startActivityForResult(galleryIntent, SELECT_PICTURE); //allows to get back image
                break;

            default:

                Log.d("Default", "Default case running");
                break;
        }
    }

    /**
     * Puts an advert to the database
     */
    private class AnnonsPut extends DB_Connect {
        @Override
        protected void onPostExecute(String result) {
            try {

                RequestQueue requestQueue = Volley.newRequestQueue(myContext);
                StringRequest request = new StringRequest(Request.Method.POST, "http://spaaket.no-ip.org:1080/quercus-4.0.39/connection.php",

                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }){
                    /**
                     * Builds a map with the data needed to build an advert
                     * @return: map
                     * @throws AuthFailureError
                     */
                    @Override
                    protected Map<String,String> getParams() throws AuthFailureError {

                        HashMap<String,String> map = new HashMap<>();
                        map.put("encoded_string", encoded);
                        map.put("image_name", pictureName);
                        map.put("product_name", formattedDate);
                        map.put("advert_date", fDate);
                        map.put("advert_description", beskrivningStr);
                        map.put("advert_title", titelStr);

                        return map;
                    }
                };
                //Sends the map to a script which manages it and sends it to the database.
                requestQueue.add(request);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Saves a bitmap to file and returns true if it worked,
     * this makes it so the variable selectedImage only get set if
     * it worked and thus the if-statements which checks if every attribute in
     * the formular was filled works correctly.
     * @param bmp
     * @param filename
     * @return
     */
    private boolean saveBitmapToFile(Bitmap bmp, File filename){
        FileOutputStream out = null;
        boolean returnedCorrectly = true;

        try {
            out = new FileOutputStream(filename);

            bmp.compress(Bitmap.CompressFormat.PNG, 100, out); // bmp is your Bitmap instance
            // PNG is a lossless format, the compression factor (100) is ignored
        } catch (Exception e) {
            returnedCorrectly = false;
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                returnedCorrectly = false;
                e.printStackTrace();
            }
        }
        return returnedCorrectly;
    }
}
