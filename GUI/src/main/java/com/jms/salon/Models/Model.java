package com.jms.salon.Models;

import com.jms.salon.Views.ViewFactory;
import com.salon.Server.Services.Export.Manager;
import com.salon.Server.Services.Export.Product;
import com.salon.Server.Services.Export.Seller;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Model {
    private static Model instance;
    private final ViewFactory viewFactory;
    private ConnectionServer connectionServer;
    private String currentRole;
    private String currentUser;

    private final ObservableList<Product> products = FXCollections.observableArrayList();
    private final ObservableList<Seller> sellers = FXCollections.observableArrayList();
    private final ObservableList<Manager> managers = FXCollections.observableArrayList();

    private Model() {
        this.viewFactory = new ViewFactory();
        this.connectionServer = new ConnectionServer();
    }

    public synchronized ConnectionServer getConnectionServer() {
        if (connectionServer == null || connectionServer.isClosed()) {
            connectionServer = new ConnectionServer();
        }
        return connectionServer;
    }

    public void setCurrentRole(String role) {
        this.currentRole = role;
    }

    public String getCurrentRole() {
        return currentRole;
    }

    public void setCurrentUser(String user) {
        this.currentUser = user;
    }

    public String getCurrentUser() {
        return currentUser;
    }




    public ObservableList<Product> getProducts() {
        return products;
    }

    public void addProduct(Product product) {
        products.add(product);
    }

    public void removeProduct(Product product) {
        products.remove(product);
    }

    public ObservableList<Seller> getSellers() {return sellers;}
    public void addSeller(Seller seller) { sellers.add(seller); }
    public void removeSeller(Seller seller) { sellers.remove(seller); }

    public ObservableList<Manager> getManagers() {return managers;}
    public void addManager(Manager manager) { managers.add(manager); }
    public void removeManager(Manager manager) { managers.remove(manager); }

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
