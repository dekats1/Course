package com.salon.Server.Services.Seller;

import com.salon.Server.Services.Export.Product;
import com.salon.Server.Services.Export.Sale;

import java.io.Serializable;
import java.util.Date;

public class SellerRequest implements Serializable {
    private final String request;
    private final Product product;
    private final Boolean success;
    private final String errorMessage;
    private final Sale sale;
    private String userName;
    private final String photoPath;
    private int quantity;
    private String password;
    private String newPassword;
    private Date dateInfo;

    public SellerRequest(String request) {
        this.request = request;
        this.product = null;
        this.success = null;
        this.errorMessage = null;
        this.sale = null;
        this.photoPath = null;
    }

    public SellerRequest(String request, String userName) {
        this.request = request;
        this.product = null;
        this.success = null;
        this.errorMessage = null;
        this.userName = userName;
        this.sale = null;
        this.password = null;
        this.newPassword = null;
        this.photoPath = null;
    }

    public SellerRequest(Boolean success, String errorMessage) {
        this.request = null;
        this.product = null;
        this.success = success;
        this.errorMessage = errorMessage;
        this.sale = null;
        this.photoPath = null;

    }

    public SellerRequest(String request, String userName, Product product, int quantity) {
        this.request = request;
        this.product = product;
        this.success = null;
        this.errorMessage = null;
        this.userName = userName;
        this.quantity = quantity;
        this.photoPath = null;
        this.sale = null;
    }

    public SellerRequest(Boolean success,String errorMessage, Sale sale) {
        this.request = null;
        this.product = null;
        this.success = success;
        this.errorMessage = errorMessage;
        this.sale = sale;
        this.photoPath = null;
    }

    public SellerRequest(String request, String name, String password, String newPassword) {
        this.request = request;
        this.success = null;
        this.errorMessage = null;
        this.sale = null;
        this.userName = name;
        this.product = null;
        this.password = password;
        this.newPassword = newPassword;
        this.photoPath = null;

    }

    public SellerRequest(String request, String name, String photoPath) {
        this.request = request;
        this.product = null;
        this.photoPath = photoPath;
        this.userName = name;
        this.success = null;
        this.errorMessage = null;
        this.sale = null;
    }

    public SellerRequest(String name, Date date) {
        this.request = null;
        this.success = null;
        this.errorMessage = null;
        this.sale = null;
        this.product = null;
        this.photoPath = null;
        this.userName = name;
        this.dateInfo = date;

    }


    public String getRequest() {
        return request;
    }

    public Product getProduct() {
        return product;
    }

    public Boolean getSuccess() {
        return success;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public String getUserName() {
        return userName;
    }

    public int getQuantity() {
        return quantity;
    }

    public Sale getSale() {
        return sale;
    }

    public String getPassword() {
        return password;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public String getPhotoPath() {
        return photoPath;
    }

    public Date getDateInfo() {
        return dateInfo;
    }
}
