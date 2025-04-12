package com.jms.salon.Models;



import com.jms.salon.Views.ViewFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;

public class Model {
    private static Model instance;
    private final ViewFactory viewFactory;
    private ConnectionServer connectionServer;
    private String currentRole;

    private Model() {
        this.viewFactory = new ViewFactory();
        try {
            this.connectionServer = new ConnectionServer();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public synchronized ConnectionServer getConnectionServer() {
        if (connectionServer == null || connectionServer.isClosed()) {
            try {
                connectionServer = new ConnectionServer();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return connectionServer;
    }

    public void setCurrentRole(String role) {
        this.currentRole = role;
    }

    public String getCurrentRole() {
        return currentRole;
    }


    private final ObservableList<Product> products = FXCollections.observableArrayList();

    public ObservableList<Product> getProducts() {
        return products;
    }

    public void addProduct(Product product) {
        products.add(product);
    }

    public void removeProduct(Product product) {
        products.remove(product);
    }


    public synchronized static Model getInstance(){
        if(instance == null){
            instance = new Model();
        }
        return instance;
    }
    public ViewFactory getViewFactory(){
        return viewFactory;
    }
}
