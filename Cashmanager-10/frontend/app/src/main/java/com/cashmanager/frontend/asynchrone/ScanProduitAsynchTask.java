package com.cashmanager.frontend.asynchrone;

import android.content.ContentValues;
import android.os.AsyncTask;
import android.util.Log;

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

public class ScanProduitAsynchTask extends AsyncTask<String, String, String> {

    @Override
    protected String doInBackground(String... params) {
        HttpURLConnection connexion = null;
        BufferedReader reader = null;
        BufferedWriter writer = null;
        try {
            URL url = new URL(params[0]);
            connexion = (HttpURLConnection) url.openConnection();
            connexion.setRequestMethod("POST");
            connexion.setRequestProperty("Content-Type", "application/json; utf-8");
            connexion.setRequestProperty("Accept", "application/json");
            connexion.setDoOutput(true);
            connexion.setDoInput(true);
            connexion.connect();

            ContentValues values = new ContentValues();
            values.put("vehiculeID", "5fb2af8ca7484d750ccfa7c1");
            values.put("compagnieAssuranceID", "5fb2ae25f545d8746a5b9fb8");
            values.put("libelleAssurance", "assuran b");
            values.put("typeAssurance", "fjhf");
            values.put("dateSouscription", "20:10:2020");
            values.put("dateValidite", "20:01:2021");
            values.put("primeAssurance", 1000000);

            OutputStream outputStream = connexion.getOutputStream();
            writer = new BufferedWriter(new OutputStreamWriter((outputStream)));
            writer.write(getQuery(values));
            InputStream inputStream = connexion.getInputStream();
            reader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuffer buffer = new StringBuffer();
            String ligne = "";
            while ((ligne = reader.readLine()) != null) {
                buffer.append(ligne);
            }

            Log.e("data", buffer.toString());
            return buffer.toString();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (connexion != null) {
                connexion.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }

        return null;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        Log.e("resultat", "result");
    }

    private String getQuery(ContentValues values) throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();
        boolean first = true;

        for (Map.Entry<String, Object> entry : values.valueSet()) {
            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(String.valueOf(entry.getValue()), "UTF-8"));
        }

        Log.i("Result", result.toString());

        return result.toString();
    }
}
