package com.example.magnus.menufragment.DB_Connect;

import android.os.AsyncTask;
import android.util.Log;

import com.example.magnus.menufragment.Interface.ConsultationResultListener;

import org.apache.commons.io.ByteOrderMark;
import org.apache.commons.io.input.BOMInputStream;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;

public class DB_Connect extends AsyncTask<String, Void, String> {
    public ConsultationResultListener delegate = null;

    private static final String DEBUG_TAG = "HttpExample";
    @Override
    protected String doInBackground(String... urls) {
        try {
            return downloadUrl(urls[0]);
        } catch (IOException e) {
            return "Unable to retrieve web page. URL may be invalid.";
        }
    }
    @Override
    protected void onPostExecute(String result) {
        delegate.processFinish(result);
    }

    private String downloadUrl(String myurl) throws IOException {
        InputStream is = null;
        try {
            URL url = new URL(myurl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000 /* milliseconds */);
            conn.setConnectTimeout(15000 /* milliseconds */);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            // Starts the query
            conn.connect();
            int response = conn.getResponseCode();
            Log.d(DEBUG_TAG, "The response is: " + response);
            is = conn.getInputStream();

            // Convert the InputStream into a string
            String contentAsString = readIt(is);
            return contentAsString;

            // Makes sure that the InputStream is closed after the app is
            // finished using it.
        } finally {
            if (is != null) {
                is.close();
            }
        }
    }

    public String readIt(InputStream stream) throws IOException, UnsupportedEncodingException {
        BufferedReader r = new BufferedReader(new InputStreamReader(new BOMInputStream(stream, false, ByteOrderMark.UTF_8)));
        StringBuilder total = new StringBuilder();
        String line;
        while ((line = r.readLine()) != null) {
            total.append(line);
        }
        return total.toString();
    }
}

/*
För att anropa databasen så använd denna kod i onClick:
--------------------------------------------------------------
String stringUrl = "http://reminent.no-ip.org/slimapi/public/json";
                ConnectivityManager connMgr = (ConnectivityManager)
                        getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
                if (networkInfo != null && networkInfo.isConnected()) {
                    try{
                        EkoGet task = new EkoGet();
                        task.execute(stringUrl);
                    } catch(Throwable e){
                    }
                } else {
                    textView.setText("No network connection available.");
                }
--------------------------------------------------------------

Sedan så måste man copy pasta in denna del i botten av classen:
--------------------------------------------------------------
private class EkoGet extends DB_Connect{
        @Override
        protected void onPostExecute(String result) {
            textView.setText(result);
        }
    }
--------------------------------------------------------------
Det som behöver ändras är i andra delen på första raden så byter man "EkoGet" till ett passande namn och sedan byter man
även "EkoGet task = new EkoGet();" i första delen till samma namn. Svaret får man i andra stycket på onPostExecute, det
är också där som den nya koden ska in med parsande av XML resultatet, ändring av texter osv. StringURL i första stycket
är den URL som ni ska hämta data från.
Fråga Oskar eller Magnus vid frågor.
 */