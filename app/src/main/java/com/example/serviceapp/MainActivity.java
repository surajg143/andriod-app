package com.example.serviceapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    private Button loginbtn;
    private EditText user_email;
    private TextView textView;
    private RadioGroup loginradiogroup;
    private RadioButton selectedbtn;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    public void customdialog(View v) {
        if(isNetworkConnected()) {
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            ViewGroup viewGroup = findViewById(android.R.id.content);
            View dialogView = LayoutInflater.from(v.getContext()).inflate(R.layout.customdialog, viewGroup, false);
            builder.setView(dialogView);
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }
        else
            Toast.makeText(this, "Network is not Avialable", Toast.LENGTH_SHORT).show();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        user_email = (EditText) findViewById(R.id.loginemail);
        loginradiogroup = (RadioGroup) findViewById(R.id.loginradiogrp);
        textView= (TextView) findViewById(R.id.disp);
        loginbtn=(Button) findViewById(R.id.loginbtn);

        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               firebaseDatabase= FirebaseDatabase.getInstance();
               databaseReference= firebaseDatabase.getReference("user");
               databaseReference.setValue("hi");
            }
        });
    }

    /*public void Home_Page(View v) {
        int selected_id = loginradiogroup.getCheckedRadioButtonId();
        selectedbtn = (RadioButton) findViewById(selected_id);
        String email = user_email.getText().toString();
        if (email.length()==0)
            user_email.setError("Please Enter Your Email Id");
        else if (loginradiogroup.getCheckedRadioButtonId() == -1)
            Toast.makeText(this, "Please Select Your Identity", Toast.LENGTH_SHORT).show();
        else{
            String identity= selectedbtn.getText().toString();
            if(isNetworkConnected()) {

                Intent intnt= new Intent(this,Customerhomepage.class);
                startActivity(intnt);
            }
            else
                Toast.makeText(this, "Network is not Avialable", Toast.LENGTH_SHORT).show();
        }

    }*/


    public void registerpage(View view) {
        if(isNetworkConnected()) {
            Intent reg = new Intent(this, Registrationpage.class);
            startActivity(reg);
        }
        else
            Toast.makeText(this, "Network is not Avialable", Toast.LENGTH_SHORT).show();
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }
}