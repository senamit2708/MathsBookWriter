package com.example.mathsbookwriter.model;

import com.google.firebase.firestore.IgnoreExtraProperties;

@IgnoreExtraProperties
public class ProductModel {

    private String productNumber;
    private String productName;
    private String category;
    private String brand;
    private int availableQuantity;
    private float purchasePrice;
    private float sellPriceOne;
    private float sellPriceTwo;
    private float sellPriceThree;
    private String description;

    //for entering product before billing
    private int orderedQuantity;
    private float sellingPrice;

    //default constructor for safety
    public ProductModel() {
    }

    public ProductModel(String productNumber, String productName, int orderedQuantity, float sellingPrice) {
        this.productNumber = productNumber;
        this.productName = productName;
        this.orderedQuantity = orderedQuantity;
        this.sellingPrice = sellingPrice;
    }

    public ProductModel(String productNumber, String productName, int availableQuantity, int orderedQuantity,
                        float sellPriceOne, float sellingPrice) {
        this.productNumber = productNumber;
        this.productName = productName;
        this.availableQuantity = availableQuantity;
        this.orderedQuantity = orderedQuantity;
        this.sellPriceOne = sellPriceOne;
        this.sellingPrice = sellingPrice;
    }

    //this is used to load and retrive details of product from firestore
    public ProductModel(String productNumber, String productName, String category, String brand, int availableQuantity, float purchasePrice, float sellPriceOne, float sellPriceTwo, float sellPriceThree, String description) {
        this.productNumber = productNumber;
        this.productName = productName;
        this.category = category;
        this.brand = brand;
        this.availableQuantity = availableQuantity;
        this.purchasePrice = purchasePrice;
        this.sellPriceOne = sellPriceOne;
        this.sellPriceTwo = sellPriceTwo;
        this.sellPriceThree = sellPriceThree;
        this.description = description;
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public int getAvailableQuantity() {
        return availableQuantity;
    }

    public void setAvailableQuantity(int availableQuantity) {
        this.availableQuantity = availableQuantity;
    }

    public float getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(float purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public float getSellPriceOne() {
        return sellPriceOne;
    }

    public void setSellPriceOne(float sellPriceOne) {
        this.sellPriceOne = sellPriceOne;
    }

    public float getSellPriceTwo() {
        return sellPriceTwo;
    }

    public void setSellPriceTwo(float sellPriceTwo) {
        this.sellPriceTwo = sellPriceTwo;
    }

    public float getSellPriceThree() {
        return sellPriceThree;
    }

    public void setSellPriceThree(float sellPriceThree) {
        this.sellPriceThree = sellPriceThree;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
