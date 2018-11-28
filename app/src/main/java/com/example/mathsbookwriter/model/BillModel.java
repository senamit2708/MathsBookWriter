package com.example.mathsbookwriter.model;

import com.google.firebase.firestore.IgnoreExtraProperties;

import java.util.List;

@IgnoreExtraProperties
public class BillModel {
    String billNumber;
    String partyName;
    List<String> productModels;



    public BillModel() {
    }

    public BillModel(String billNumber, String partyName, List<String> productModels) {
        this.billNumber = billNumber;
        this.partyName = partyName;
        this.productModels = productModels;
    }



    public String getBillNumber() {
        return billNumber;
    }

    public void setBillNumber(String billNumber) {
        this.billNumber = billNumber;
    }

    public String getPartyName() {
        return partyName;
    }

    public void setPartyName(String partyName) {
        this.partyName = partyName;
    }

    public List<String> getProductModels() {
        return productModels;
    }

    public void setProductModels(List<String> productModels) {
        this.productModels = productModels;
    }
}
