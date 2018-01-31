package com.example.ab0034.fileshare;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.beardedhen.androidbootstrap.BootstrapButton;

public class MainActivity extends AppCompatActivity {
BootstrapButton btnsend,btnreceive;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnsend=(BootstrapButton)findViewById(R.id.btnsend);
//        RotatingTextWrapper rotatingTextWrapper = (RotatingTextWrapper) findViewById(R.id.custom_switcher);
//        rotatingTextWrapper.setSize(35);
//
//        Rotatable rotatable = new Rotatable(Color.parseColor("#FFA036"), 1000, "Share", "Files", "With Friends");
//        rotatable.setSize(35);
//        rotatable.setAnimationDuration(500);
//
//        rotatingTextWrapper.setContent(" ?", rotatable);

        btnreceive =(BootstrapButton)findViewById(R.id.btnreceive);
        btnsend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,FilesActivity.class);
                startActivity(i);
            }
        });
    }
}
