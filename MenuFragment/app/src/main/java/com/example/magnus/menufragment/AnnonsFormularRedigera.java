package com.example.magnus.menufragment;


import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
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

    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.annons_formular_redigera, container, false);
        Button klarBtn = (Button)view.findViewById(R.id.klar2);
        klarBtn.setOnClickListener(this);
        Button AvbrytBtn = (Button)view.findViewById(R.id.avbryt2);
        AvbrytBtn.setOnClickListener(this);

        Button kameraBtn = (Button)view.findViewById(R.id.kamerasymbol2);
        kameraBtn.setOnClickListener(this);

        Button galleriBtn = (Button)view.findViewById(R.id.gallerisymbol2);
        galleriBtn.setOnClickListener(this);

        myContext = getContext();

        //Used for things that needs to be named unique (based on date on time)
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date cDate = new Date();
        fDate = new SimpleDateFormat("yyyy-MM-dd").format(cDate);
        formattedDate = df.format(c.getTime()).replace(" ", ""); //Now formattedDate has current date/time
        pictureName = formattedDate + ".jpg";


        Bundle bundle = this.getArguments();

        //Fixes the set title
        String changedTitel = bundle.getString("titel");
        EditText changeTxtTitel = (EditText) view.findViewById(R.id.editTextTitel2);
        changeTxtTitel.setText(changedTitel);

        //Fixes the set description
        String changedBeskrivning = bundle.getString("beskrivning");
        EditText changeTxtBeskrivning= (EditText) view.findViewById(R.id.editTextBeskrivning2);
        changeTxtBeskrivning.setText(changedBeskrivning);

        cr = getActivity().getContentResolver();

        //Fixes the set image
        byte[] byteArray = bundle.getByteArray("bild");
        Bitmap bmp = null;
        if (byteArray != null) {
            bmp = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
        }
        ImageView changeImgView= (ImageView) view.findViewById(R.id.image_camera2);
        changeImgView.setImageBitmap(bmp);

        File changedPhoto = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), pictureName);
        if(saveBitmapToFile(bmp, changedPhoto)) {
            Uri tmpImage = Uri.fromFile(changedPhoto);
            selectedImage = tmpImage;
        }
        return view;
    }

    public void takePhoto(View v){
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        File photo = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), pictureName);
        imageUri = Uri.fromFile(photo);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri); //puts it in the gallery
        startActivityForResult(intent, TAKE_PICTURE); //We are passing in number 1, which is a request code  == main camera.
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent){
        super.onActivityResult(requestCode, resultCode, intent);
        ImageView imageView = (ImageView)view.findViewById(R.id.image_camera2);

        switch (requestCode) {
            case TAKE_PICTURE:
                selectedImage = imageUri;
                getActivity().getContentResolver().notifyChange(selectedImage, null);

                try{
                    int newHeight = 100;
                    int newWidth = 100;
                    bitmap = MediaStore.Images.Media.getBitmap(cr, selectedImage);

                    //Todo Fix so this actually works
                    //bitmap = getResizedBitmap(bitmap, newHeight, newWidth);

                    imageView.setImageBitmap(bitmap);
                }catch (Exception e){
                    e.printStackTrace();
                }
                break;

            case SELECT_PICTURE:

                try {

                    selectedImage = intent.getData();
                    bitmap = MediaStore.Images.Media.getBitmap(cr, selectedImage);
                    imageView.setImageBitmap(bitmap);
                } catch (Exception e){
                    e.printStackTrace();
                }
                break;

            default:
                Log.d("fail", "onActResult failed default case called.");
                break;
        }
    }

    private boolean isEmpty(EditText etText) {
        return etText.getText().toString().trim().length() == 0;
    }

    @Override
    public void onClick(View v) {
        FragmentTransaction fm = getFragmentManager().beginTransaction();
        EditText inputTxtTitel = (EditText)view.findViewById(R.id.editTextTitel2);
        EditText inputTxtBeskrivning= (EditText)view.findViewById(R.id.editTextBeskrivning2);

        switch(v.getId()){
            case R.id.klar2:
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

            case R.id.avbryt2:

                getFragmentManager().popBackStack();
                fm.commit();
                break;

            case R.id.kamerasymbol2:

                takePhoto(v);
                break;

            case R.id.gallerisymbol2:

                Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);//opens gallery
                startActivityForResult(galleryIntent, SELECT_PICTURE); //allows to get back image
                break;

            default:

                Log.d("Default", "Default case running");
                break;
        }
    }

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
                    @Override
                    protected Map<String,String> getParams() throws AuthFailureError {

                        HashMap<String,String> map = new HashMap<>();
                        //Set as nothing
                        map.put("encoded_string", encoded);
                        map.put("image_name", pictureName);
                        map.put("product_name", formattedDate);
                        map.put("advert_date", fDate);
                        map.put("advert_description", beskrivningStr);
                        map.put("advert_title", titelStr);

                        return map;
                    }
                };
                requestQueue.add(request);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

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
