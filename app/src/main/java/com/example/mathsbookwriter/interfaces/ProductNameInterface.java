package com.example.mathsbookwriter.interfaces;

import com.example.mathsbookwriter.model.ProductModel;

public interface ProductNameInterface {

    //i think this fun is useless to me...
    public void funProductNumber(String productNumber, String productName,
                                 int quantity, float price);

    public void funProductDetails(ProductModel model);
}
