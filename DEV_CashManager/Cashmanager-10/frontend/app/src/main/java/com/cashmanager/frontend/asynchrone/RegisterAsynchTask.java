package com.cashmanager.frontend.asynchrone;

import android.content.ContentValues;
import android.net.UrlQuerySanitizer;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;

public class RegisterAsynchTask extends AsyncTask<String, String, String> {

    protected String doInBackground(String... params) {
        HttpURLConnection httpURLConnection = null;
        BufferedReader reader = null;
        BufferedWriter writer = null;
        InputStream inputStream = null;
        OutputStream outputStream = null;
        try {
           URL url = new URL(params[0]);
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setDoInput(true);
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setRequestMethod("POST");

            httpURLConnection.setRequestProperty("Accept","*/*");

            ContentValues values = new ContentValues();
            values.put("vehiculeID", "5fb2af8ca7484d750ccfa7c1");
            values.put("libellePiece", "vignette");
            values.put("dateDebut", "20:10:2020 b");
            values.put("dateFin", "20:10:2020");
            values.put("autoriteDelivrance", "prefet");
            httpURLConnection.connect();

            outputStream = httpURLConnection.getOutputStream();

            writer = new BufferedWriter(new OutputStreamWriter(outputStream));
            writer.write(getQuery(values));
            writer.flush();

            int statusCode = httpURLConnection.getResponseCode();

            inputStream = httpURLConnection.getInputStream();

            if (statusCode == HttpURLConnection.HTTP_OK) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                int data = inputStreamReader.read();
                String result = "";
                while (data != -1) {
                    char current = (char) data;
                    result += current;
                    data = inputStreamReader.read();
                }

                JSONObject jsonObject = new JSONObject(result);

                Log.e("Result",String.valueOf(jsonObject));

            } else {
                Log.e("Result","false");
            }

        } catch (Exception e) {

            e.printStackTrace();
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
                if (outputStream != null) {
                    outputStream.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }
    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        Log.e("resultat", "result");
    }

    private String getQuery(ContentValues values) throws UnsupportedEncodingException
    {
        StringBuilder result = new StringBuilder();
        boolean first = true;

        for (Map.Entry<String, Object> entry : values.valueSet())
        {
            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(String.valueOf(entry.getValue()), "UTF-8"));
        }

        Log.i("Result",result.toString());

        return result.toString();
    }
}
