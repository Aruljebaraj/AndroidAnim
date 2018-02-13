package com.example.ab0034.urlasyn;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

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

public class MainActivity extends AppCompatActivity {

    ArrayList<DataDto> dataDtos;
    ListView listView;
    DataDto datadto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView) findViewById(R.id.ListView);
        new Data().execute();
    }

    public void update() {
        listView.setAdapter(new DataAdapter(this, dataDtos));
    }

    public class DataAdapter extends ArrayAdapter<ArrayList<DataDto>> {
        ArrayList<DataDto> dataDtoArrayList;
        Context context;

        public DataAdapter(Context context, ArrayList<DataDto> dataDtoArrayList) {
            super(context, R.layout.list_row);
            this.context = context;
            this.dataDtoArrayList = dataDtoArrayList;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = convertView;
            LayoutInflater inflater = LayoutInflater.from(getContext());
            view = inflater.inflate(R.layout.list_row, null, false);
            datadto = dataDtoArrayList.get(position);

            TextView TxtName = (TextView) view.findViewById(R.id.TxtName);
            TextView TxtContact = (TextView) view.findViewById(R.id.TxtContact);
            TextView TxtEmail = (TextView) view.findViewById(R.id.TxtEmail);
            TxtName.setText(datadto.getName());
            TxtEmail.setText(datadto.getEmail());
            TxtContact.setText(datadto.getContact());

            return view;
        }

        @Override
        public int getCount() {
            return dataDtoArrayList.size();
        }
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
            dataDtos = new ArrayList<>();
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
                update();
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

    }

}


