package com.example.mathsbookwriter.model;

import com.google.firebase.firestore.IgnoreExtraProperties;

import java.util.List;

@IgnoreExtraProperties
public class BillModel {
    String billNumber;
    String partyName;
    String billingDate;
    float totalPrice;
    String status;
    List<OrderedProductModel> productList;



    public BillModel() {
    }

    public BillModel(String billNumber, String partyName, String billingDate,float totalPrice,String status, List<OrderedProductModel> productList) {
        this.billNumber = billNumber;
        this.partyName = partyName;
        this.billingDate = billingDate;
        this.totalPrice = totalPrice;
        this.status = status;
        this.productList = productList;
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

    public String getBillingDate() {
        return billingDate;
    }

    public void setBillingDate(String billingDate) {
        this.billingDate = billingDate;
    }

    public List<OrderedProductModel> getProductList() {
        return productList;
    }

    public void setProductList(List<OrderedProductModel> productList) {
        this.productList = productList;
    }

    public float getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(float totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
