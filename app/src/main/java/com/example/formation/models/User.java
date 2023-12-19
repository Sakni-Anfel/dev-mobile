package com.example.formation.models;

public class User {

    private String fullName;
    private String  phoneNumber;
    private String ncin;
    private String email;
    private String yourPassword;
    public User(){}

    public User(String fullName, String phoneNumber, String ncin, String email, String yourPassword) {
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.ncin = ncin;
        this.email = email;
        this.yourPassword=yourPassword;
    }

    public String getFullName() {
        return fullName;
    }
    public void setFullName(String fullName){
        this.fullName=fullName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
    public void setPhoneNumber(String phoneNumber){
        this.phoneNumber=phoneNumber;
    }

    public String getNcin() {
        return ncin;
    }

    public void setNcin(String ncin) {
        this.ncin = ncin;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getYourPassword() {
        return yourPassword;
    }

    public void setYourPassword(String yourPassword) {
        this.yourPassword = yourPassword;
    }
}


