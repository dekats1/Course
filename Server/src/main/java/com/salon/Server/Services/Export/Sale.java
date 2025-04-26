package com.salon.Server.Services.Export;

import java.io.Serializable;
import java.util.Date;

public class Sale implements Serializable{
    private final int saleId;
    private final String productName;
    private final int quantity;
    private final double price;
    private final Date date;
    private final String sellerName;

    public Sale(int saleId, String productName, int quantity, double price, Date date, String sellerName) {
        this.saleId = saleId;
        this.productName = productName;
        this.quantity = quantity;
        this.price = price;
        this.date = date;
        this.sellerName = sellerName;
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


}
