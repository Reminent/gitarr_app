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

        Calendar c = Calendar.getInstance();
        System.out.println("Current time =&gt; "+c.getTime());
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formattedDate = df.format(c.getTime());
        //Now formattedDate has current date/time

        File photo = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),formattedDate + ".jpg");
        imageUri = Uri.fromFile(photo);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri); //puts it in the gallery
        startActivityForResult(intent, TAKE_PICTURE); //We are passing in number 1, which is a request code  == main camera.
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent){
        super.onActivityResult(requestCode, resultCode, intent);

        if(resultCode == Activity.RESULT_OK){ //If clicked ok
            Uri selectedImage = imageUri;

            getActivity().getContentResolver().notifyChange(selectedImage, null);
            ImageView imageView = (ImageView)view.findViewById(R.id.image_camera);
            ContentResolver cr = getActivity().getContentResolver();
            Bitmap bitmap;

            try{
                bitmap = MediaStore.Images.Media.getBitmap(cr, selectedImage);
                imageView.setImageBitmap(bitmap);
                Toast.makeText(getContext().getApplicationContext(), selectedImage.toString(), Toast.LENGTH_LONG).show();
            }catch (Exception e){
                Log.e(logtag, e.toString());
            }
        }

    }


    @Override
    public void onClick(View v) {
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
