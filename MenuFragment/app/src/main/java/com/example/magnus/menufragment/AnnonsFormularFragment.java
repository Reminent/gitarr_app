package com.example.magnus.menufragment;


import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.magnus.menufragment.DB_Connect.DB_Connect;
import com.example.magnus.menufragment.DB_Upload.DB_Upload;
import com.example.magnus.menufragment.XML_Parsing.Advert_Parse;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class AnnonsFormularFragment extends android.support.v4.app.Fragment implements View.OnClickListener{
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


        view = inflater.inflate(R.layout.annons_formular_fragment, container, false);
        Button klarBtn = (Button)view.findViewById(R.id.klar);
        klarBtn.setOnClickListener(this);
        Button AvbrytBtn = (Button)view.findViewById(R.id.avbryt);
        AvbrytBtn.setOnClickListener(this);


        Button kameraBtn = (Button)view.findViewById(R.id.kamerasymbol);
        kameraBtn.setOnClickListener(this);

        Button galleriBtn = (Button)view.findViewById(R.id.gallerisymbol);
        galleriBtn.setOnClickListener(this);

        myContext = getContext();

        /* //TODO: Fixa så att man kan redigera en annons genom att skapa en ny vy.
        Bundle bundle = this.getArguments();
        final String titel = bundle.getString("titel");

        Toast.makeText(getContext().getApplicationContext(), titel, Toast.LENGTH_LONG).show();

        //String url = "http://spaaket.no-ip.org:1080/GitarrAppAPI/webresources/rest.advert";
        */

        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date cDate = new Date();
        fDate = new SimpleDateFormat("yyyy-MM-dd").format(cDate);
        formattedDate = df.format(c.getTime()).replace(" ", ""); //Now formattedDate has current date/time
        pictureName = formattedDate + ".jpg";
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
        cr = getActivity().getContentResolver();
        ImageView imageView = (ImageView)view.findViewById(R.id.image_camera);

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
                    Toast.makeText(getContext().getApplicationContext(), selectedImage.toString(), Toast.LENGTH_LONG).show();
                }catch (Exception e){
                    e.printStackTrace();
                }
                break;

            case SELECT_PICTURE:
                Log.d("inside välj bild", "inside2");

                try {


                    selectedImage = intent.getData();
                    bitmap = MediaStore.Images.Media.getBitmap(cr, selectedImage);
                    imageView.setImageBitmap(bitmap);
                } catch (Exception e){
                    e.printStackTrace();
                }
                break;

            default:
                Log.d("fail", "onActResult failed case failed.");
                break;
        }
    }

    private boolean isEmpty(EditText etText) {
        return etText.getText().toString().trim().length() == 0;
    }

    @Override
    public void onClick(View v) {
        FragmentTransaction fm = getFragmentManager().beginTransaction();
        EditText eTextTitel = (EditText)view.findViewById(R.id.editTextTitel);
        EditText eTextBeskrivning= (EditText)view.findViewById(R.id.editTextBeskrivning);

        switch(v.getId()){
            case R.id.klar:
                if(!isEmpty(eTextTitel)&& !isEmpty(eTextBeskrivning) && selectedImage != null) {
                    try {
                        EditText inputTxtTitel = (EditText) view.findViewById(R.id.editTextTitel);
                        titelStr = inputTxtTitel.getText().toString();

                        EditText inputTxtBeskrivning = (EditText) view.findViewById(R.id.editTextBeskrivning);
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
                                "Annons skickad till databasen"
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

            case R.id.avbryt:

                getFragmentManager().popBackStack();
                fm.commit();
                break;

            case R.id.kamerasymbol:

                takePhoto(v);
                break;

            case R.id.gallerisymbol:

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
    public static Bitmap getResizedBitmap(Bitmap image, int newHeight, int newWidth) {
        int width = image.getWidth();
        int height = image.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // create a matrix for the manipulation
        Matrix matrix = new Matrix();
        // resize the bit map
        matrix.postScale(scaleWidth, scaleHeight);
        // recreate the new Bitmap
        Bitmap resizedBitmap = Bitmap.createBitmap(image, 0, 0, width, height,
                matrix, false);
        return resizedBitmap;
    }
}
