package com.example.ab0034.geofencing;

import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements LocationListener {
    EditText EdtName, EdtAddress;
    Button btn;
    RecyclerView List_view;
    ArrayList<ModelDto> Detail = new ArrayList<>();
    String Name = "";
    String Address = "";
    RecyclerViewAdapter mAdapter;
    LocationManager locationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EdtName = (EditText) findViewById(R.id.EdtName);
        EdtAddress = (EditText) findViewById(R.id.EdtAddress);
        btn = (Button) findViewById(R.id.btn);
        List_view = (RecyclerView) findViewById(R.id.list_item);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        List_view.setLayoutManager(mLayoutManager);
        List_view.setItemAnimator(new DefaultItemAnimator());
        mAdapter = new RecyclerViewAdapter(Detail);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Details();
            }
        });
        List_view.setAdapter(mAdapter);
    }

    public void Details() {
        // Detail = new ArrayList<>();
        // Model model;
        ModelDto model = new ModelDto();
        Name = EdtName.getText().toString();
        Address = EdtAddress.getText().toString();
        model.Name = Name;
        model.Address = (Address);
        Detail.add(model);
        mAdapter = new RecyclerViewAdapter(Detail);
        List_view.setAdapter(mAdapter);
    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {
        Toast.makeText(MainActivity.this, "Please Enable GPS and Internet", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onProviderDisabled(String provider) {

    }


    public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

        private ArrayList<ModelDto> moviesList;


        public class MyViewHolder extends RecyclerView.ViewHolder {
            public TextView txtName, txtAddress;

            public MyViewHolder(View view) {
                super(view);
                txtName = (TextView) view.findViewById(R.id.txtName);
                txtAddress = (TextView) view.findViewById(R.id.txtAddress);
            }
        }


        public RecyclerViewAdapter(ArrayList<ModelDto> moviesList) {
            this.moviesList = moviesList;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_row, parent, false);

            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            final ModelDto movie = moviesList.get(position);
            holder.txtName.setText(movie.Name);
            holder.txtAddress.setText(movie.Address);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, MapsActivity.class);
                    intent.putExtra("Address", movie.Address);
                    startActivity(intent);
                }
            });
        }

        @Override
        public int getItemCount() {
            return moviesList.size();
        }

    }


//    public class DetailsAdapter extends ArrayAdapter<ArrayList<Model>> {
//        ArrayList<Model> DetailList;
//        Context context;
//
//        public DetailsAdapter(ArrayList<Model> DetailList, Context context) {
//            super(context, R.layout.list_row);
//
//            this.DetailList = DetailList;
//            this.context = context;
//        }
//
//        @Override
//        public View getView(int position, View convertView, ViewGroup parent) {
//            Model p = DetailList.get(position);
//            View view = convertView;
//            if (convertView != null) {
//                LayoutInflater inflater = LayoutInflater.from(MainActivity.this);
//                view = inflater.inflate(R.layout.list_row, null);
//            }
//
//            TextView tt1 = (TextView) view.findViewById(R.id.txtName);
//            TextView tt2 = (TextView) view.findViewById(R.id.txtAddress);
//            if (tt1 != null) {
//                tt1.setText(p.getName());
//            }
//            if (tt2 != null) {
//                tt2.setText(p.getAddress());
//            }
//
//            return view;
//        }
//
//        @Override
//        public int getCount() {
//            return DetailList.size();
//        }
}

class ModelDto {
    String Name;
    String Address;
}

