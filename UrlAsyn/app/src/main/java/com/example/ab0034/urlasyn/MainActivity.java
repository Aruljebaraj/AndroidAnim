package com.example.ab0034.urlasyn;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.ab0034.urlasyn.Other.DataDto;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    FetchData fetchData = new FetchData();
    ListView listView;
    DataDto datadto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView) findViewById(R.id.ListView);
        listView.setAdapter(new DataAdapter(this, fetchData.GetList()));
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


}


