package com.example.mathsbookwriter.model;

import com.google.firebase.firestore.IgnoreExtraProperties;

@IgnoreExtraProperties
public class PartiesModel {
    public String partyName;
    public int gstCheck;
    public String registrationNumber;
    public String address;
    public String mobileNumber;
    public String date;

    public PartiesModel() {
    }

    public PartiesModel(String partyName, String registrationNumber, String mobileNumber) {
        this.partyName = partyName;
        this.registrationNumber = registrationNumber;
        this.mobileNumber = mobileNumber;
    }

    public PartiesModel(String partyName, String registrationNumber, String address, String mobileNumber, String date) {
        this.partyName = partyName;
        this.registrationNumber = registrationNumber;
        this.address = address;
        this.mobileNumber = mobileNumber;
        this.date = date;
    }

    public String getPartyName() {
        return partyName;
    }

    public void setPartyName(String partyName) {
        this.partyName = partyName;
    }

    public int getGstCheck() {
        return gstCheck;
    }

    public void setGstCheck(int gstCheck) {
        this.gstCheck = gstCheck;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
