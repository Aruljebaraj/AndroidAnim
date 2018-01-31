package com.example.ab0034.firebase;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ShowActivity extends AppCompatActivity {
    GridView fileGrid;
    Upload upload;
    String[] uploads;
    int i;
    DatabaseReference mDatabaseReference;

    ArrayList<Upload> ArrayuploadList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);

        ArrayuploadList = new ArrayList<>();
        fileGrid = (GridView) findViewById(R.id.fileGrid);
        mDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    upload = postSnapshot.getValue(Upload.class);
                    ArrayuploadList.add(upload);
                }
                uploads = new String[ArrayuploadList.size()];

                for (i = 0; i < uploads.length; i++) {
                    uploads[i] = ArrayuploadList.get(i).getName();
                    fileGrid.setAdapter(new CustomGridAdapter(ShowActivity.this, ArrayuploadList));
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    public class CustomGridAdapter extends BaseAdapter {

        private Context context;
        ArrayList<Upload> UploadList;
        LayoutInflater inflater;

        public CustomGridAdapter(Context context, ArrayList<Upload> UploadList) {
            this.context = context;
            this.UploadList = UploadList;
            inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        public View getView(int position, View convertView, ViewGroup parent) {

            if (convertView == null) {
                convertView = inflater.inflate(R.layout.show_rowitem, null);
            }
            TextView txtCaption = (TextView) convertView.findViewById(R.id.txt_show);
            ImageView img_show = (ImageView) convertView.findViewById(R.id.img_show);
            txtCaption.setText(upload.name);
            Glide.with(getApplicationContext()).load(upload.getUrl()).into(img_show);
            return convertView;
        }

        @Override
        public int getCount() {
            return UploadList.size();
        }

        @Override
        public Object getItem(int position) {
            return UploadList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }
    }

}


