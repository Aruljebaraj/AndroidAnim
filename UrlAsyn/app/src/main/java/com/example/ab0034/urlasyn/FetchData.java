package com.example.ab0034.urlasyn;

import android.os.AsyncTask;

import com.example.ab0034.urlasyn.Other.DataDto;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class FetchData {
    ArrayList<DataDto> dataDtos = new ArrayList<>();

    public FetchData() {

    }

    public ArrayList<DataDto> GetList() {
        new Data().execute();
        return dataDtos;
    }

    public class Data extends AsyncTask<String, String, String> {
        String data = "";

        @Override
        protected String doInBackground(String... dataa) {
            try {
                URL url = new URL("https://api.myjson.com/bins/axlfh");

                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String line = "";
                while (line != null) {
                    line = bufferedReader.readLine();
                    data = data + line;
                }


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return data.toString();
        }

        @Override
        protected void onPostExecute(String aVoid) {
            super.onPostExecute(aVoid);
            JSONArray JA = null;
            try {
                JA = new JSONArray(aVoid);
                for (int i = 0; i < JA.length(); i++) {
                    JSONObject JO = (JSONObject) JA.get(i);

                    DataDto dataDto = new DataDto();
                    dataDto.setName(JO.getString("Name"));
                    dataDto.setEmail(JO.getString("Email"));
                    dataDto.setContact(JO.getString("Contact"));
                    dataDtos.add(dataDto);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

    }
}








