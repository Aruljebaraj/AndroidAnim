package com.example.ab0034.firebase;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText editTextEmail;
    private EditText editTextPassword;
    private Button buttonSignup;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;
    TextView txt_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        firebaseAuth = FirebaseAuth.getInstance();
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        txt_login = (TextView) findViewById(R.id.txt_login);
        buttonSignup = (Button) findViewById(R.id.buttonSignup);
//        RotatingTextWrapper rotatingTextWrapper = (RotatingTextWrapper) findViewById(R.id.custom_switcher);
//        rotatingTextWrapper.setSize(35);
//
//        Rotatable rotatable = new Rotatable(Color.parseColor("#281a46"), 1000, "Already Having ", "An Account?", "Login..");
//        rotatable.setSize(25);
//        rotatable.setAnimationDuration(500);
//
//        rotatingTextWrapper.setContent("", rotatable);
        progressDialog = new ProgressDialog(this);
        buttonSignup.setOnClickListener(this);
        txt_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    private void registerUser() {

        //getting email and password from edit texts
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        //checking if email and passwords are empty
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Please enter email", Toast.LENGTH_LONG).show();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Please enter password", Toast.LENGTH_LONG).show();
            return;
        }
        progressDialog.setMessage("Registering Please Wait...");
        progressDialog.show();
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //checking if success
                        if (task.isSuccessful()) {
                            //display some message here
                            Toast.makeText(MainActivity.this, "Successfully registered", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                            startActivity(intent);
                        } else {
                            //display some message here
                            Toast.makeText(MainActivity.this, "Registration Error", Toast.LENGTH_LONG).show();
                        }
                        progressDialog.dismiss();
                    }
                });

    }

    @Override
    public void onClick(View v) {
        registerUser();
    }


}
