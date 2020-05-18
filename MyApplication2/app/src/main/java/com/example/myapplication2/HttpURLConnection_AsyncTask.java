package com.example.myapplication2;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Map;

public class HttpURLConnection_AsyncTask extends AsyncTask<Map<String,String>, Void, String> {


    @SafeVarargs
    @Override
    protected final String doInBackground(Map<String, String>... maps) {
        String result = "";
        HttpURLConnection connection = null;
        InputStream is = null;
        try{
            String APIUrl = "https://guidary.000webhostapp.com/director.php";
            URL url = new URL(APIUrl);
            connection = (HttpURLConnection)url.openConnection();
            connection.setRequestMethod("POST");
            Log.d("LOG TEST", "doInBackground: ");
            if (maps.length > 0){
                connection.setDoInput(true);
                Log.d("LOG TEST", "doInBackground: maps length gtr 0");
            }
            connection.setDoOutput(true);
            connection.connect();

            if (maps.length > 0){
                DataOutputStream outputStream = new DataOutputStream(connection.getOutputStream());
                StringBuilder stringBuilder = new StringBuilder();
                Iterator<String> iterator = maps[0].keySet().iterator();
                while (iterator.hasNext()) {
                    String key = iterator.next();
                    stringBuilder.append(key).append("=").append(URLEncoder.encode(maps[0].get(key), "UTF-8")).append("&");
                }

                stringBuilder.deleteCharAt(stringBuilder.length()-1);
                outputStream.writeBytes((stringBuilder.toString()));
                outputStream.flush();
                outputStream.close();
            }

            InputStream inputStream = connection.getInputStream();
            int status = connection.getResponseCode();
            Log.d("LOG TEST", String.valueOf(status));
            if(inputStream != null){
                BufferedReader bufReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
                String line="";
                while((line = bufReader.readLine()) != null){
                    result += (line+"\n");
                }
            }else {
                result = "No Results";
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
    }
}
