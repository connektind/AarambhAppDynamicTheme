package com.example.aarambhappdynamictheme.model;

import java.io.Serializable;

public class RegisterationFirstModel implements Serializable {
    String stud_name;
    String stud_mobile;
    String email;
    String address;
public RegisterationFirstModel(){}
    public RegisterationFirstModel(String stud_name, String stud_mobile, String email, String address) {
        this.stud_name = stud_name;
        this.stud_mobile = stud_mobile;
        this.email = email;
        this.address = address;
    }

    public String getStud_name() {
        return stud_name;
    }

    public void setStud_name(String stud_name) {
        this.stud_name = stud_name;
    }

    public String getStud_mobile() {
        return stud_mobile;
    }

    public void setStud_mobile(String stud_mobile) {
        this.stud_mobile = stud_mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
