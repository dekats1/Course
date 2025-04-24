package com.salon.Server.Services.Admin;

import com.salon.Server.Services.Export.Manager;
import com.salon.Server.Services.Export.Product;
import com.salon.Server.Services.Export.Seller;

import java.util.Date;

public class AdminRequest implements java.io.Serializable {
    private final String request;
    private final Seller seller;
    private final Manager manager;
    private Product product;
    private final String photoPath;
    private final String name;
    private String password;
    private String newPassword;
    private String errorMassage;
    private Boolean isSuccess;
    private Date dateInfo;

    public AdminRequest(String request, Seller seller) {
        this.request = request;
        this.seller = seller;
        this.manager = null;
        this.product = null;
        this.photoPath = null;
        this.name = null;
    }

    public AdminRequest(String request, Manager manager) {
        this.request = request;
        this.seller = null;
        this.manager = manager;
        this.product = null;
        this.photoPath = null;
        this.name = null;
    }

    public AdminRequest(String request, Product product) {
        this.request = request;
        this.seller = null;
        this.manager = null;
        this.product = product;
        this.photoPath = null;
        this.name = null;
    }

    public AdminRequest(String request) {
        this.request = request;
        this.seller = null;
        this.manager = null;
        this.product = null;
        this.photoPath = null;
        this.name = null;
    }

    public AdminRequest(String request, String name, String photoPath) {
        this.request = request;
        this.seller = null;
        this.manager = null;
        this.product = null;
        this.photoPath = photoPath;
        this.name = name;
    }

    public AdminRequest(String request, String name) {
        this.request = request;
        this.seller = null;
        this.manager = null;
        this.product = null;
        this.photoPath = null;
        this.name = name;
        this.isSuccess = false;
        this.errorMassage = null;
    }

    public AdminRequest(Boolean isSuccess, String errorMassage) {
        this.request = null;
        this.seller = null;
        this.manager = null;
        this.product = null;
        this.photoPath = null;
        this.name = null;
        this.isSuccess = isSuccess;
        this.errorMassage = errorMassage;
        this.dateInfo = null;
    }

    public AdminRequest(String request, String name, String password, String newPassword) {
        this.request = request;
        this.seller = null;
        this.manager = null;
        this.product = null;
        this.photoPath = null;
        this.name = name;
        this.password = password;
        this.newPassword = newPassword;
        this.isSuccess = false;
        this.errorMassage = null;
        this.dateInfo = null;
    }

    public AdminRequest(String name, Date date) {
        this.request = null;
        this.seller = null;
        this.manager = null;
        this.product = null;
        this.photoPath = null;
        this.name = name;
        this.dateInfo = date;
        this.isSuccess = false;
        this.errorMassage = null;
    }



    public String getRequest() {
        return request;
    }

    public Seller getSeller() {
        return seller;
    }

    public Manager getManager() {
        return manager;
    }

    public Product getProduct() {
        return product;
    }

    public String getPhotoPath() {
        return photoPath;
    }

    public String getName() {
        return name;
    }

    public String getErrorMassage() {
        return errorMassage;
    }

    public Boolean getSuccess() {
        return isSuccess;
    }

    public String getPassword() {
        return password;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setProduct(Product product) {
        this.product = product;
    }


    public Date getDate() {
        return dateInfo;
    }
}

