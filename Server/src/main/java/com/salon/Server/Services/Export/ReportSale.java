package com.salon.Server.Services.Export;

import java.io.Serializable;
import java.util.List;

public class ReportSale implements Serializable {
    private static List<Sale> sales;
    private int totalAmount;
    private double totalPrice;
    private double totalProfit;

    public static List<Sale> getSales() {
        return sales;
    }
    public static void setSales(List<Sale> sales) {
        ReportSale.sales = sales;
    }

    public int getTotalAmount() {
        return totalAmount;
    }
    public double getTotalPrice() {
        return totalPrice;
    }
    public double getTotalProfit() {
        return totalProfit;
    }

    public void calculation(){
        for(Sale sale : sales){
            totalAmount += sale.getQuantity();
            totalPrice += sale.getPrice() * sale.getQuantity();
            totalProfit += (sale.getPrice()-sale.getCost())*sale.getQuantity();
        }
    }

}
