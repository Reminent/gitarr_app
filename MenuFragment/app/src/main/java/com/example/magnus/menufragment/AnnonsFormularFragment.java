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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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


    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        view = inflater.inflate(R.layout.annons_formular_fragment, container, false);
        Button klarBtn = (Button)view.findViewById(R.id.klar);
        klarBtn.setOnClickListener(this);
        Button AvbrytBtn = (Button)view.findViewById(R.id.avbryt);
        AvbrytBtn.setOnClickListener(this);


        ImageButton kameraBtn = (ImageButton)view.findViewById(R.id.kamerasymbol);
        kameraBtn.setOnClickListener(this);

        ImageButton galleriBtn = (ImageButton)view.findViewById(R.id.gallerisymbol);
        galleriBtn.setOnClickListener(this);

        return view;
    }

    private void takePhoto(View v){
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formattedDate = df.format(c.getTime()); //Now formattedDate has current date/time


        File photo = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),formattedDate + ".jpg");
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
                Log.d("inside ta bild", "inside1");
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

            case SELECT_PICTURE: //runs even if i click back button
                Log.d("inside välj bild", "inside2");

                try {
                    selectedImage = intent.getData(); //crashes when clicked back on gallery here.
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

                    EditText inputTxtBeskrivning = (EditText) view.findViewById(R.id.editTextBeskrivning);
                    String beskrivningStr = inputTxtBeskrivning.getText().toString();
                    Log.d("Beskrivningsträng", beskrivningStr); //Skicka beskrivning till databasen

                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(cr, selectedImage);
                    }catch (Exception e){
                        Log.e(logtag, e.toString());
                        Log.d("Du", "Suger");
                    }
                    Log.d("Image uri: ", selectedImage.toString()); //Skicka bild till databasen

                    getFragmentManager().popBackStack();
                    fm.commit();
                } else {
                    Toast.makeText(getContext().getApplicationContext(),
                            "Du måste välja titel, beskrivning och bild" , Toast.LENGTH_LONG).show();
                }

                //((TextView)view.findViewById(R.id.annons_titel_1)).setText("Supe du klicke på knapp!");
                // get EditText by id
                EditText inputTxtTitel = (EditText)view.findViewById(R.id.editTextTitel);
                // Store EditText in Variable
                String titelStr = inputTxtTitel.getText().toString();
                Log.d("Titelsträng", titelStr); //Skicka titel till databasen


                // get EditText by id
                EditText inputTxtBeskrivning = (EditText)view.findViewById(R.id.editTextBeskrivning);
                // Store EditText in Variable
                String beskrivningStr = inputTxtBeskrivning.getText().toString();
                Log.d("Beskrivningsträng", beskrivningStr); //Skicka beskrivning till databasen

                Log.d("Image uri: ", selectedImage.toString()); //Skicka bild till databasen


                getFragmentManager().popBackStack();
                fm.commit();
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
