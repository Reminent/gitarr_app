package com.example.magnus.menufragment;


import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
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
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class AnnonsFormularFragment extends android.support.v4.app.Fragment implements View.OnClickListener{
    @Nullable
    private ContentResolver cr;
    private static String logtag = "CameraApp8";
    private static final int TAKE_PICTURE = 1;
    private Uri imageUri;
    private static final int SELECT_PICTURE = 2;
    private Uri selectedImage;
    private Bitmap bitmap;
    private String pictureName;


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

        return view;
    }

    public void takePhoto(View v){
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formattedDate = df.format(c.getTime()); //Now formattedDate has current date/time
        pictureName = formattedDate + ".jpg";

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
                    //bitmap ska vara base 64
                    bitmap = MediaStore.Images.Media.getBitmap(cr, selectedImage);
                    imageView.setImageBitmap(bitmap);
                    Toast.makeText(getContext().getApplicationContext(), selectedImage.toString(), Toast.LENGTH_LONG).show();
                }catch (Exception e){
                    Log.e(logtag, e.toString());
                }
                break;

            case SELECT_PICTURE:
                Log.d("inside välj bild", "inside2");

                try {


                    selectedImage = intent.getData();
                    pictureName = selectedImage.toString();
                    Log.d("Picturenamegallery: ", pictureName);
                    bitmap = MediaStore.Images.Media.getBitmap(cr, selectedImage);
                    imageView.setImageBitmap(bitmap);
                } catch (Exception e){
                    Log.e(logtag, e.toString());
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

                    EditText inputTxtTitel = (EditText) view.findViewById(R.id.editTextTitel);
                    String titelStr = inputTxtTitel.getText().toString();
                    Log.d("Titelsträng", titelStr); //Skicka titel till databasen
                    //TODO: Change this so it adds a title in the database instead.


                    EditText inputTxtBeskrivning = (EditText) view.findViewById(R.id.editTextBeskrivning);
                    String beskrivningStr = inputTxtBeskrivning.getText().toString();
                    Log.d("Beskrivningsträng", beskrivningStr); //Skicka beskrivning till databasen
                    //TODO: Change this so it adds a description in the database instead.


                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(cr, selectedImage);

                        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                        byte[] byteArray = byteArrayOutputStream .toByteArray();
                        String encoded = Base64.encodeToString(byteArray, Base64.DEFAULT);
                        //http://spaaket.no-ip.org:1080/quercus-4.0.39/connection.php
                        //han tar encoded string + titel via en POST

                        //post till image>product>advert


                        Log.d("Imagename: ",pictureName );
                        //Log.d("Encoded string: ", encoded); //encodade stringen
                        Toast.makeText(getContext().getApplicationContext(),
                                "Annons skickad till databasen"
                                , Toast.LENGTH_LONG).show();

                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    Log.d("Image uri: ", selectedImage.toString()); //Skicka bild till databasen
                    //TODO: Change this so it adds a picture in the database instead.

                    getFragmentManager().popBackStack();
                    fm.commit();
                } else {
                    Toast.makeText(getContext().getApplicationContext(),
                            "Du måste välja titel, beskrivning och bild" , Toast.LENGTH_LONG).show();
                }

                /*
                EditText inputTxtTitel = (EditText)view.findViewById(R.id.editTextTitel);
                String titelStr = inputTxtTitel.getText().toString();
                Log.d("Titelsträng", titelStr);  //Skicka titel till databasen.

                EditText inputTxtBeskrivning = (EditText)view.findViewById(R.id.editTextBeskrivning);
                String beskrivningStr = inputTxtBeskrivning.getText().toString();
                Log.d("Beskrivningsträng", beskrivningStr); //Skicka beskrivning till databasen

                Log.d("Image uri: ", selectedImage.toString()); //Skicka bild till databasen


                getFragmentManager().popBackStack();
                fm.commit();
                */
                //fm.replace(R.id.content, fragment);
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
}
