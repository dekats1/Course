package com.salon.Server.Services.Export;

import java.io.Serializable;
import java.util.Date;

public class Sale implements Serializable{
    private final int saleId;
    private final String productName;
    private  int quantity;
    private  double price;
    private  double cost;
    private final Date date;
    private final String sellerName;

    public Sale(){
        this.saleId = -1;
        this.productName = null;
        this.quantity = 0;
        this.price = 0;
        this.date = null;
        this.sellerName = null;
    }

    public Sale(int saleId, String productName, int quantity, double price, Date date, String sellerName) {
        this.saleId = saleId;
        this.productName = productName;
        this.quantity = quantity;
        this.price = price;
        this.date = date;
        this.sellerName = sellerName;
        this.cost = 0;
    }
    public Sale(int saleId, String productName, int quantity, double price, Date date, String sellerName, double cost) {
        this.saleId = saleId;
        this.productName = productName;
        this.quantity = quantity;
        this.price = price;
        this.date = date;
        this.sellerName = sellerName;
        this.cost = cost;
    }

    public Sale(String productName, double price) {
        this.saleId = -1;
        this.productName = productName;
        this.quantity = 0;
        this.price = price;
        this.cost = 0;
        this.date = null;
        this.sellerName = null;
    }


    public int getSaleId() {
        return saleId;
    }
    public String getProductName(){
        return productName;
    }
    public int getQuantity(){
        return quantity;
    }
    public double getPrice(){
        return price;
    }
    public Date getDate(){
        return date;
    }
    public String getSellerName(){
        return sellerName;
    }
    public double getCost(){
        return cost;
    }


    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setPrice(double price) {
        this.price = price;
    }
    public void setCost(double cost) {
        this.cost = cost;
    }
}
