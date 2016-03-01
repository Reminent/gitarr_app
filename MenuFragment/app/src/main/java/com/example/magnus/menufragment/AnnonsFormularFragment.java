package com.example.magnus.menufragment;


import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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

public class AnnonsFormularFragment extends android.support.v4.app.Fragment implements View.OnClickListener{
    @Nullable

    private static String logtag = "CameraApp8";
    private static int TAKE_PICTURE = 1;
    private Uri imageUri;


    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.annons_formular_fragment, container, false);
        Button klarBtn = (Button)view.findViewById(R.id.klar);
        klarBtn.setOnClickListener(this);
        Button AvbrytBtn = (Button)view.findViewById(R.id.avbryt);
        AvbrytBtn.setOnClickListener(this);

        ImageButton kameraBtn = (ImageButton)view.findViewById(R.id.kamerasymbol);
        kameraBtn.setOnClickListener(cameraListener);

        ImageButton galleriBtn = (ImageButton)view.findViewById(R.id.gallerisymbol);
        galleriBtn.setOnClickListener(this);

        return view;
    }
    private View.OnClickListener cameraListener = new View.OnClickListener(){
        public void onClick(View v){
            takePhoto(v);
        }
    };

    private void takePhoto(View v){
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        File photo = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),"picture.jpg"); //change so it will not override picture.jpg
        imageUri = Uri.fromFile(photo);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(intent, TAKE_PICTURE); //We are passing in number 1, which is a request code  == main camera.
    }

    //Osäkert om det funkar, kan inte göra som vanligt eftersom att man inte kan extenda två klasser (extends Activity saknas) Se rad (***)
    Context context;
    public void ContactManager (Context context) {
        this.context = context;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent){ //should be protected, but that doesnt work.
        onActivityResult(requestCode, resultCode, intent); //super.

        if(resultCode == Activity.RESULT_OK){
            Uri selectedImage = imageUri;
            context.getContentResolver().notifyChange(selectedImage, null); //*** "contect." var inte med i guiden.

            ImageView imageView = (ImageView)view.findViewById(R.id.image_camera); //changed, view.
            ContentResolver cr = context.getContentResolver(); //changed, context.
            Bitmap bitmap;

            try{

                bitmap = MediaStore.Images.Media.getBitmap(cr, selectedImage);
                imageView.setImageBitmap(bitmap);
                Toast.makeText(context, selectedImage.toString(), Toast.LENGTH_LONG).show(); //ändrad //context -> mainactivity
            }catch (Exception e){
                Log.e(logtag, e.toString());
            }
        }

    }


    @Override
    public void onClick(View v) {
        Fragment fragment;
        FragmentTransaction fm = getFragmentManager().beginTransaction();

        switch(v.getId()){
            case R.id.klar:

                //((TextView)view.findViewById(R.id.annons_titel_1)).setText("Supe du klicke på knapp!");

                // get EditText by id
                EditText inputTxtTitel = (EditText)view.findViewById(R.id.editTextTitel);
                // Store EditText in Variable
                String titelStr = inputTxtTitel.getText().toString();
                Log.d("Titelsträng", titelStr);


                // get EditText by id
                EditText inputTxtBeskrivning = (EditText)view.findViewById(R.id.editTextBeskrivning);
                // Store EditText in Variable
                String beskrivningStr = inputTxtBeskrivning.getText().toString();
                Log.d("Beskrivningsträng", beskrivningStr);


                getFragmentManager().popBackStack();
                fm.commit();
                //fm.replace(R.id.content, fragment);

                break;

            case R.id.avbryt:

                //Log.d("Case", "avbryt");
                //int id = item.getItemId();
                //fragment = new AnnonsFragment();
                //fm.replace(R.id.content, fragment);
                //((TextView)view.findViewById(R.id.annons_titel_1)).setText("Success!");
               // fm.addToBackStack(null);
                //fm.remove(R.id.content);
                getFragmentManager().popBackStack();
                fm.commit();



                break;
            case R.id.kamerasymbol:

                ((TextView)view.findViewById(R.id.taKort)).setText("Beep!");

                //Log.d("Case", "avbryt");
                //int id = item.getItemId();
                //((TextView)view.findViewById(R.id.annons_titel_1)).setText("Supe du klicke på knapp!");
                //fragment = new AnnonsFragment();
                //fm.replace(R.id.content, fragment);
                //fm.commit();

                break;
            case R.id.gallerisymbol:

                //Log.d("Case", "avbryt");
                //int id = item.getItemId();
                ((TextView)view.findViewById(R.id.väljBild)).setText("Boop!");

               // fragment = new AnnonsFragment();
               // fm.replace(R.id.content, fragment);
               // fm.commit();

                break;
        }
    }


}
