package com.example.ab0034.firebase;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;

public class HomeActivity extends AppCompatActivity {
    private Button buttonChoose;
    private Button buttonUpload;
    Button buttonShow;
    private static final int PICK_IMAGE_REQUEST = 234;
    private ImageView imageView;
    private Uri filePath;
    private FirebaseAuth auth;
    ImageView setting;
    TextView txt_email;
    EditText txt_filename;
    DatabaseReference mDatabaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        auth = FirebaseAuth.getInstance();
        setting = (ImageView) findViewById(R.id.setting);
        txt_filename = (EditText) findViewById(R.id.txt_filename);
        txt_email = (TextView) findViewById(R.id.txt_email);
        txt_email.setText(auth.getCurrentUser().getEmail());
        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, SettingActivity.class);
                startActivity(intent);
            }
        });
        buttonChoose = (Button) findViewById(R.id.buttonChoose);
        buttonUpload = (Button) findViewById(R.id.buttonUpload);
        buttonShow = (Button) findViewById(R.id.buttonShow);
        imageView = (ImageView) findViewById(R.id.imageView);
        buttonChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFileChooser();
            }
        });
        buttonUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadFile();
            }
        });
        buttonShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(HomeActivity.this, ShowActivity.class);
                startActivity(intent);
            }
        });
    }

    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();
//            if (data.getData() != null) {
//                uploadFile(filePath);
//                }else{
//                    Toast.makeText(this, "No file chosen", Toast.LENGTH_SHORT).show();
//                }
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                imageView.setImageBitmap(bitmap);
//
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }

    private void uploadFile() {
        //if there is a file to upload
        if (filePath != null) {
            //displaying a progress dialog while upload is going on
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading");
            progressDialog.show();
            FirebaseStorage storage = FirebaseStorage.getInstance();
            mDatabaseReference = FirebaseDatabase.getInstance().getReference(Constants.DATABASE_PATH_UPLOADS);
            StorageReference storageReference = storage.getReference();
//            StorageReference riversRef = storageReference.child("images/.jpg");
            StorageReference riversRef = storageReference.child("images/" + txt_filename.getText() + ".jpg");//.child(String.valueOf(txt_filename.getText()));
//            riversRef.child(Constants.STORAGE_PATH_UPLOADS + System.currentTimeMillis() + txt_filename.getText());
//            riversRef.child(String.valueOf(txt_filename.getText()));
            riversRef.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss();

                            Upload upload = new Upload(txt_filename.getText().toString(), taskSnapshot.getDownloadUrl().toString());
                            mDatabaseReference.child(mDatabaseReference.push().getKey()).setValue(upload);
                            //and displaying a success toast
                            Toast.makeText(getApplicationContext(), "File Uploaded ", Toast.LENGTH_LONG).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {

                            progressDialog.dismiss();

                            //and displaying error message
                            Toast.makeText(getApplicationContext(), exception.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            //calculating progress percentage
                            double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();

                            //displaying percentage in progress dialog
                            progressDialog.setMessage("Uploaded " + ((int) progress) + "%...");
                        }
                    });
        }
        //if there is not any file
        else {
            Toast.makeText(this, "Upload Failed", Toast.LENGTH_SHORT).show();
        }
    }
}


