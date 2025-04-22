package com.salon.Server.Services.Export;

import java.io.Serializable;


public class Seller extends User {

    private int sellCount;


    public Seller() {}

    public Seller(String userName, String name, String lastName, int sellCount, String dateAt) {
        super(userName, name, lastName, dateAt);
        this.sellCount = sellCount;
    }
    public Seller(String userName, String name, String lastName, String password, String dateAt) {
        super(userName, name, lastName, password, dateAt);
    }

    public int getSellCount() { return sellCount; }
    public void setSellCount(int sellCount) { this.sellCount = sellCount; }


}