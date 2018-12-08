package com.example.mathsbookwriter.model;

import com.google.firebase.firestore.IgnoreExtraProperties;

@IgnoreExtraProperties
public class OrderedProductModel {

    private String productNumber;
    private String productName;
    private int orderedQuantity;
    private float sellingPrice;

    public OrderedProductModel(String productNumber, String productName, int orderedQuantity, float sellingPrice) {
        this.productNumber = productNumber;
        this.productName = productName;
        this.orderedQuantity = orderedQuantity;
        this.sellingPrice = sellingPrice;
    }

    public OrderedProductModel() {
    }

    public String getProductNumber() {
        return productNumber;
    }

    public void setProductNumber(String productNumber) {
        this.productNumber = productNumber;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getOrderedQuantity() {
        return orderedQuantity;
    }

    public void setOrderedQuantity(int orderedQuantity) {
        this.orderedQuantity = orderedQuantity;
    }

    public float getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(float sellingPrice) {
        this.sellingPrice = sellingPrice;
    }
}
