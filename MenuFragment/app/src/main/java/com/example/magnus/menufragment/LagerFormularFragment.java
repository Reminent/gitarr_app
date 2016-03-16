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

public class LagerFormularFragment extends android.support.v4.app.Fragment implements View.OnClickListener{
    @Nullable
    View view;
    private ContentResolver cr;
    private Context myContext;
    private static final int TAKE_PICTURE = 1;
    private static final int SELECT_PICTURE = 2;
    private Uri selectedImage;
    private Uri imageUri;
    private Bitmap bitmap;

    private String pictureName;
    private String encoded;
    private String formattedDate;
    private String productManufacturer;
    private String productName;
    private String productPurchasePrice;
    private String productSellingPrice;
    private String productGenre;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.lager_formular_fragment, container, false);

        Button buttonDone = (Button)view.findViewById(R.id.buttonDone);
        buttonDone.setOnClickListener(this);

        Button buttonCancel = (Button)view.findViewById(R.id.buttonCancel);
        buttonCancel.setOnClickListener(this);

        Button buttonCamera = (Button)view.findViewById(R.id.buttonTakePhoto);
        buttonCamera.setOnClickListener(this);

        Button buttonGallery = (Button)view.findViewById(R.id.buttonEnterGallery);
        buttonGallery.setOnClickListener(this);

        myContext = getContext();

        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        // Tagit bort för att det inte används
        //Date cDate = new Date();
        //fDate = new SimpleDateFormat("yyyy-MM-dd").format(cDate);
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
        ImageView imageView = (ImageView)view.findViewById(R.id.productImageView);

        switch (requestCode) {
            case TAKE_PICTURE:

                selectedImage = imageUri;
                getActivity().getContentResolver().notifyChange(selectedImage, null);

                try{
                    bitmap = MediaStore.Images.Media.getBitmap(cr, selectedImage);
                    imageView.setImageBitmap(bitmap);
                    Toast.makeText(getContext().getApplicationContext(), selectedImage.toString(), Toast.LENGTH_LONG).show();
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
                Log.d("Error", "onActivityResult entered default CASE");
                break;
        }
    }

    private boolean isEmpty(EditText etText) {
        return etText.getText().toString().trim().length() == 0;
    }

    @Override
    public void onClick(View v) {

        FragmentTransaction fm = getFragmentManager().beginTransaction();
        EditText editProductName = (EditText)view.findViewById(R.id.editProductName);
        EditText editProductManufacturer = (EditText)view.findViewById(R.id.editManufacturer);
        EditText editProductGenre = (EditText)view.findViewById(R.id.editGenre);
        EditText editProductPurchasePrice = (EditText)view.findViewById(R.id.editPurchaserPrice);
        EditText editProductSellingPrice = (EditText)view.findViewById(R.id.editSellingPrice);

        switch(v.getId()){
            case R.id.buttonDone:
                if(!isEmpty(editProductName) && selectedImage != null) {
                    try {
                        // Gets strings from EditText views
                        productName = editProductName.getText().toString();
                        productManufacturer = editProductManufacturer.getText().toString();
                        productGenre = editProductGenre.getText().toString();
                        productPurchasePrice = editProductPurchasePrice.getText().toString();
                        productSellingPrice = editProductSellingPrice.getText().toString();

                        // Encodes and saves image
                        bitmap = MediaStore.Images.Media.getBitmap(cr, selectedImage);
                        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                        byte[] byteArray = byteArrayOutputStream .toByteArray();
                        encoded = Base64.encodeToString(byteArray, Base64.DEFAULT);


                        LagerPut lagerPut = new LagerPut();
                        String url = "http://spaaket.no-ip.org:1080/quercus-4.0.39/connection2.php";
                        lagerPut.execute(url);

                        Toast.makeText(getContext().getApplicationContext(),
                                "Produkt skickad till databasen"
                                , Toast.LENGTH_LONG).show();

                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    getFragmentManager().popBackStack();
                    fm.commit();
                } else {
                    Toast.makeText(getContext().getApplicationContext(),
                            "Du måste välja unikt produktnamn och bild" , Toast.LENGTH_LONG).show();
                }
                break;

            case R.id.buttonCancel:

                getFragmentManager().popBackStack();
                fm.commit();
                break;

            case R.id.buttonTakePhoto:

                takePhoto(v);
                break;

            case R.id.buttonEnterGallery:

                Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);//opens gallery
                startActivityForResult(galleryIntent, SELECT_PICTURE); //allows to get back image
                break;

            default:

                Log.d("Default", "Default case running");
                break;
        }
    }

    private class LagerPut extends DB_Connect {
        @Override
        protected void onPostExecute(String result) {
            try {

                RequestQueue requestQueue = Volley.newRequestQueue(myContext);
                StringRequest request = new StringRequest(Request.Method.POST, "http://spaaket.no-ip.org:1080/quercus-4.0.39/connection2.php",

                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Is intended to be empty
                    }
                }){
                    @Override
                    protected Map<String,String> getParams() throws AuthFailureError {

                        HashMap<String,String> map = new HashMap<>();
                        map.put("encoded_string", encoded);
                        map.put("image_name", pictureName);
                        map.put("manufacturer", productManufacturer);
                        map.put("product_name", productName);
                        map.put("purchase_price", productPurchasePrice);
                        map.put("selling_price", productSellingPrice);
                        map.put("genre", productGenre);

                        return map;
                    }
                };
                requestQueue.add(request);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}