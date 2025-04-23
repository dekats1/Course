package com.salon.Server.Services.Admin;

import com.salon.Server.Services.Export.Manager;
import com.salon.Server.Services.Export.Product;
import com.salon.Server.Services.Export.Seller;

public class AdminRequest implements java.io.Serializable {
    private final String request;
    private final Seller seller;
    private final Manager manager;
    private Product product;
    private final String photoPath;
    private final String name;
    private String errorMassage;
    private Boolean isSuccess;

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

    public void setProduct(Product product) {
        this.product = product;
    }

}

