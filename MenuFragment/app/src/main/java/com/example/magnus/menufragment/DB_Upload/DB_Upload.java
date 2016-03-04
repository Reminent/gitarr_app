package com.example.magnus.menufragment.DB_Upload;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;

public class DB_Upload extends AsyncTask<String, Void, String> {

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
    }

    public String downloadUrl(String stringUrl) throws IOException {
        URL url = new URL(stringUrl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        try {
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("content-type", "application/xml");
            conn.setDoInput(true);
            conn.setDoOutput(true);
            String body = "<image>\n" +
                    "<imageTitle>Oskar</imageTitle>\n" +
                    "<imageUrl>/oskar/test.jpg</imageUrl>\n" +
                    "</image>";
            OutputStream output = new BufferedOutputStream(conn.getOutputStream());
            output.write(body.getBytes());
            output.flush();
            output.close();
            conn.connect();
            Log.d("tag",""+conn.getResponseCode());

            return "nice";

        } finally {
            conn.disconnect();
        }
    }
}
