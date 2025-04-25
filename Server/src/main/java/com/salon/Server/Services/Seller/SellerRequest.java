package com.salon.Server.Services.Seller;

import com.salon.Server.Services.Export.Product;

import java.io.Serializable;

public class SellerRequest implements Serializable {
    private final String request;
    private final Product product;
    private final Boolean success;
    private final String errorMessage;

    public SellerRequest(String request) {
        this.request = request;
        this.product = null;
        this.success = null;
        this.errorMessage = null;
    }

    public SellerRequest(String request, Product product) {
        this.request = request;
        this.product = product;
        this.success = null;
        this.errorMessage = null;
    }

    public SellerRequest(Boolean success, String errorMessage) {
        this.request = null;
        this.product = null;
        this.success = success;
        this.errorMessage = errorMessage;
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


}
