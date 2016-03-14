package com.example.magnus.menufragment;

import android.content.ContentResolver;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.magnus.menufragment.DB_Connect.DB_Connect;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.magnus.menufragment.DB_Upload.DB_Upload;
import com.example.magnus.menufragment.DB_Upload.XML_Generate;

public class nyUtgiftFragment extends android.support.v4.app.Fragment {
    @Nullable
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_ny_inkomst, container, false);

        Button done = (Button) view.findViewById(R.id.btnSkicka);
        Button abort = (Button) view.findViewById(R.id.btnStop);

        done.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                //EditText descriptionText = (EditText) view.findViewById(R.id.schema_newTime_description);
                String URL = "http://spaaket.no-ip.org:1080/GitarrAppAPI/webresources/rest.transaction";

                EditText eTextDate = (EditText)view.findViewById(R.id.txtSetDate);
                EditText eTextAmount= (EditText)view.findViewById(R.id.txtSetAmount);

                Calendar c = Calendar.getInstance();
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date cDate = new Date();
                String transDate = new SimpleDateFormat("yyyy-MM-dd").format(cDate);


                String id, amount, date;
                id = transDate;
                amount = eTextAmount.getText().toString();
                date = eTextDate.getText().toString();
                amount = "-" + amount;



                XML_Generate generator = new XML_Generate();
                String results = generator.transactionTable(amount, date,id);
                DB_Upload upload = new DB_Upload();
                upload.execute(results, URL);
                Toast.makeText(getContext(), "uppladdat", Toast.LENGTH_LONG).show();

                Fragment fragment;
                FragmentTransaction fm = getFragmentManager().beginTransaction();

                switch(v.getId()){
                    case R.id.btnSkicka:

                        fragment = new EkonomiFragment();
                        fm.replace(R.id.content, fragment);
                        fm.addToBackStack(null);
                        fm.commit();

                        break;
                }
            }
        });

        abort.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "abort", Toast.LENGTH_LONG).show();
                Fragment fragment;
                FragmentTransaction fm = getFragmentManager().beginTransaction();

                switch(v.getId()){
                    case R.id.btnStop:

                        fragment = new EkonomiFragment();
                        fm.replace(R.id.content, fragment);
                        fm.addToBackStack(null);
                        fm.commit();

                        break;
                }
            }
        });

        /*startDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "start", Toast.LENGTH_SHORT).show();
            }
        });

        endDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "end", Toast.LENGTH_SHORT).show();
            }
        });*/

        return view;
    }

}



/*package com.example.magnus.menufragment;

import android.content.ContentResolver;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.magnus.menufragment.DB_Connect.DB_Connect;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;



public class nyInkomstFragment extends android.support.v4.app.Fragment implements View.OnClickListener {

    @Nullable

    private ContentResolver cr;
    private Bitmap bitmap;
    private Context myContext;
    private String transAmount;
    private String transDate;
    private String transId;
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ny_inkomst, container, false);
        Button btn = (Button) view.findViewById(R.id.btnStop);
        btn.setOnClickListener(this);
        Button btn2 = (Button) view.findViewById(R.id.btnSkicka);
        btn2.setOnClickListener(this);
        //Button btnD = (Button) view.findViewById(R.id.btnDateChange);
        //btn.setOnClickListener(this);
        myContext = getContext();

        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date cDate = new Date();
        transDate = new SimpleDateFormat("yyyy-MM-dd").format(cDate);
        return view;

    }

    private boolean isEmpty(EditText etText) {
        return etText.getText().toString().trim().length() == 0;
    }

    @Override
    public void onClick(View v) {
        //Fragment fragment;
        //FragmentTransaction fm = getFragmentManager().beginTransaction();
        Fragment fragment;
        FragmentTransaction fm = getFragmentManager().beginTransaction();
        //datePickFragment db;
        //FragmentTransaction dm = getFragmentManager().beginTransaction();

        EditText eTextDate = (EditText)view.findViewById(R.id.txtSetDate);
        EditText eTextAmount= (EditText)view.findViewById(R.id.txtSetAmount);

        switch (v.getId()) {
            case R.id.btnStop:
                fragment = new EkonomiFragment();
                fm.replace(R.id.content, fragment);
                fm.commit();
                //((TextView)view.findViewById(R.id.annons_titel_1)).setText("Supe du klicke på knapp!");

                //int id = item.getItemId();
                //fragment = new nyInkomstFragment();
                //fm.replace(R.id.content, fragment);
                //fm.commit();
                //setTitle(item.getTitle());
                //fm.addToBackStack(this); //Kan vara bra för när man ska stänga formuläret./koppla formuläret mot annonssidan.

                break;

            case R.id.btnSkicka:

                //FragmentTransaction fm = getFragmentManager().beginTransaction();




                        if(!isEmpty(eTextDate)&& !isEmpty(eTextAmount)) {
                            try {


                                TransactionPut ap = new TransactionPut();
                                String url = "http://spaaket.no-ip.org:1080/quercus-4.0.39/connection.php";
                                ap.execute(url);

                                Toast.makeText(getContext().getApplicationContext(),
                                        "Inkomst skickad till databasen", Toast.LENGTH_LONG).show();

                            }catch (Exception e){
                                e.printStackTrace();
                            }
                            getFragmentManager().popBackStack();
                            fm.commit();
                        } else {
                            Toast.makeText(getContext().getApplicationContext(),
                                    "Du måste skriva in datum och summa" , Toast.LENGTH_LONG).show();
                        }

                break;

        }

        }
    private class TransactionPut extends DB_Connect {
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

                        map.put("transaction_amount", transAmount);
                        map.put("transaction_date", transDate);
                        map.put("transaction_id", transId);


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

*/





