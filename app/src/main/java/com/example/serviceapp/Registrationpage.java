package com.example.serviceapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class Registrationpage extends AppCompatActivity {

    private Button regbtn;
    private EditText email,fullname,age,phone,address;
    private TextView textView;
    private RadioGroup radioGroup;
    private RadioButton selectedbtn;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    boolean exist_email;
    boolean exist_phone;
    boolean cnfrm_id=false;

    public boolean customdialog(View v) {
        if(isNetworkConnected()) {
            if(phone.length()==0 && email.length()==0) {
                phone.setError("Enter your Phone_No");
                email.setError("Enter your Email_Id");
            }
            else if(phone.length()==0)
                phone.setError("Enter your Phone_No");
            else if(email.length()==0)
                phone.setError("Enter your Email_Id");
            else if(!Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches())
                email.setError("Email_Id is Invalid");
            else {
                AlertDialog.Builder builder = new AlertDialog.Builder(Registrationpage.this);
                ViewGroup viewGroup = findViewById(android.R.id.content);
                View dialogView = LayoutInflater.from(v.getContext()).inflate(R.layout.customdialog, viewGroup, false);
                builder.setView(dialogView);
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
                cnfrm_id = true;
            }
        }
        else {
            Toast.makeText(this, "Network is not Available", Toast.LENGTH_SHORT).show();
            cnfrm_id=false;
        }
        return cnfrm_id;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrationpage);

        fullname = (EditText) findViewById(R.id.full_name);
        phone = (EditText) findViewById(R.id.phone);
        age = (EditText) findViewById(R.id.age);
        address = (EditText) findViewById(R.id.address);
        regbtn = (Button) findViewById(R.id.reg_btn);
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        email = (EditText) findViewById(R.id.email);

        regbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (cnfrm_id) {
                    int selectedid = radioGroup.getCheckedRadioButtonId();
                    selectedbtn = (RadioButton) findViewById(selectedid);

                    firebaseDatabase = FirebaseDatabase.getInstance();
                    databaseReference = firebaseDatabase.getReference("user");

                    String name = fullname.getText().toString();
                    String phone_no = phone.getText().toString();
                    String age_no = age.getText().toString();
                    String location = address.getText().toString();
                    String email_id = email.getText().toString();

                    if (email.length() == 0 || fullname.length() == 0 || phone.length() == 0 || age.length() == 0 || address.length() == 0 || radioGroup.getCheckedRadioButtonId() == -1)
                        Toast.makeText(Registrationpage.this, "Please Completly fill the form", Toast.LENGTH_SHORT).show();
                    else {
                        if (Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches()) {
                            if (isNetworkConnected()) {
                                String identity = selectedbtn.getText().toString();
                                if (isUserEmail(email_id) && isUserPhone(phone_no)) {
                                    UserRegistrationDetail userRegistrationDetail = new UserRegistrationDetail(name, phone_no, age_no, location, email_id, identity);
                                    databaseReference.child(phone_no).setValue(userRegistrationDetail);
                                    Toast.makeText(Registrationpage.this, "Registration Successfully....", Toast.LENGTH_SHORT).show();
                                    Intent homepage = new Intent(Registrationpage.this, Customerhomepage.class);
                                    startActivity(homepage);
                                } else {
                                    if (!exist_email && !exist_phone)
                                        Toast.makeText(Registrationpage.this, "Email_Id And Phone_No Already Exists....", Toast.LENGTH_SHORT).show();
                                    else if (!exist_phone)
                                        phone.setError("Phone_No Already Exists....");
                                    else
                                        email.setError("Email_Id Already Exists....");
                                }
                            } else
                                Toast.makeText(Registrationpage.this, "Network is not Available", Toast.LENGTH_SHORT).show();
                        } else
                            email.setError("Email_Id is Invalid");
                    }
                }
                else
                    Toast.makeText(Registrationpage.this, "Please First of all Confirm Your Phone_no", Toast.LENGTH_SHORT).show();
            }
            });
        }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }

    private boolean isUserEmail(String check_email) {
            Query query1 = databaseReference.orderByChild("email_id").equalTo(check_email);
            query1.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    if (dataSnapshot.exists())
                        exist_email = false;
                    else
                        exist_email = true;
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                }
            });
        return exist_email;
    }
    private boolean isUserPhone(String check_phone) {
        Query query = databaseReference.orderByChild("phone").equalTo(check_phone);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists())
                    exist_phone=false;
                else
                    exist_phone=true;
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
        return exist_phone;
    }
}