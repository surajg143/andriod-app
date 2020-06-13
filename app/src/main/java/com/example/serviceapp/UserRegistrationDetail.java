package com.example.serviceapp;

import android.widget.EditText;
import android.widget.RadioButton;

public class UserRegistrationDetail {

    String Name,Phone,Age,Address,Email_id,Identity;

    public UserRegistrationDetail() {
    }
    public UserRegistrationDetail(String name, String phone_no, String age_no, String location, String email_id,  String identity) {
        this.Name=name;
        this.Age=age_no;
        this.Email_id=email_id;
        this.Phone=phone_no;
        this.Identity=identity;
        this.Address=location;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getAge() {
        return Age;
    }

    public void setAge(String age) {
        Age = age;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getEmail_id() {
        return Email_id;
    }

    public void setEmail_id(String email_id) {
        Email_id = email_id;
    }

    public String getIdentity() {
        return Identity;
    }

    public void setIdentity(String identity) {
        Identity = identity;
    }
}
