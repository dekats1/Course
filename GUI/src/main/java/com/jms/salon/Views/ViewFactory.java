package com.jms.salon.Views;

import com.jms.salon.Controllers.Admin.AdminController;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class ViewFactory {

    //Product
    private AnchorPane productView;
    private AnchorPane addProductView;
    private AnchorPane productsForSellerView;


    //Admin View
    private final ObjectProperty<AdminMenuOption> adminSelectedMenuItem;
    private AnchorPane sellerEditView;
    private AnchorPane managerEditView;
    private AnchorPane logView;
    private AnchorPane adminProfileView;


    //Seller View
    private final ObjectProperty<SellerMenuOption> sellerSelectedMenuItem;
    private AnchorPane sellView;
    private AnchorPane sellHistoryView;
    private AnchorPane sellerProfileView;


    //Manager View
    private final ObjectProperty<ManagerMenuOption> managerSelectedMenuItem;




    public ViewFactory() {
        this.adminSelectedMenuItem = new SimpleObjectProperty<>();
        this.managerSelectedMenuItem = new SimpleObjectProperty<>();
        this.sellerSelectedMenuItem = new SimpleObjectProperty<>();
    }

    /*
    * Admins View
     */

    public ObjectProperty<AdminMenuOption> getAdminSelectedMenuItem() {
        return adminSelectedMenuItem;
    }

    public AnchorPane getSellerEditView() {
        if (sellerEditView == null) {
            try {
                sellerEditView = new FXMLLoader(getClass().getResource("/FXML/Admin/SellerEdit.fxml")).load();
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
        return sellerEditView;
    }

    public AnchorPane getManagerEditView() {
        if (managerEditView == null) {
            try {
                managerEditView = new FXMLLoader(getClass().getResource("/FXML/Admin/ManagerEdit.fxml")).load();
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
        return managerEditView;
    }

    public AnchorPane getLogView(){
        if (logView == null) {
            try {
                logView = new FXMLLoader(getClass().getResource("/FXML/Admin/Log.fxml")).load();
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
        return logView;
    }

    public AnchorPane getAdminProfileView(){
        if (adminProfileView == null) {
            try {
                adminProfileView = new FXMLLoader(getClass().getResource("/FXML/Admin/AdminProfile.fxml")).load();
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
        return adminProfileView;
    }

    public void showAdminWindow(){
        System.out.println("showAdminWindow");
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/FXML/Admin/Admin.fxml"));
        createStage(fxmlLoader);
    }

    /*
    * Seller View
     */
    public ObjectProperty<SellerMenuOption> getSellerSelectedMenuItem() {
        return sellerSelectedMenuItem;
    }

     public void showSellerWindow() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/FXML/Seller/Seller.fxml"));
        createStage(fxmlLoader);
    }

    public AnchorPane getSellView(){
        if (sellView == null) {
            try {
                sellView = new FXMLLoader(getClass().getResource("/FXML/Seller/Sale.fxml")).load();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sellView;
    }

    public AnchorPane getSellHistoryView(){
        if (sellHistoryView == null) {
            try {
                sellHistoryView = new FXMLLoader(getClass().getResource("/FXML/Seller/SalesHistory.fxml")).load();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sellHistoryView;
    }

    public AnchorPane getSellerProfileView(){
        if (sellerProfileView == null) {
            try{
                sellerProfileView = new FXMLLoader(getClass().getResource("/FXML/Seller/SellerProfile.fxml")).load();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        return sellerProfileView;
    }


    /*
    *Manager View
     */

    public ObjectProperty<ManagerMenuOption> getManagerSelectedMenuItem() {
        return managerSelectedMenuItem;
    }

    public  void showManagerWindow() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/FXML/Manager/Manager.fxml"));
        createStage(fxmlLoader);
    }

    /*
    *Product View
     */

    public  AnchorPane getProductView() {
        if (productView == null) {
            try {
                productView = new FXMLLoader(getClass().getResource("/FXML/Product/Product.fxml")).load();
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
        return productView;
    }

    public AnchorPane getAddProductView() {
        if (addProductView == null) {
            try {
                addProductView = new FXMLLoader(getClass().getResource("/FXML/Product/AddProduct.fxml")).load();
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
        return addProductView;
    }

    public AnchorPane getProductsForSellerView(){
        if (productsForSellerView == null) {
            try {
                productsForSellerView = new FXMLLoader(getClass().getResource("/FXML/Product/ProductForSeller.fxml")).load();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return productsForSellerView;
    }

    /*
    *Login Window
     */

    public void showLoginWindow() {
        System.out.println("showLoginWindow");
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/FXML/Login.fxml"));
        createStage(fxmlLoader);
    }



    private void createStage(FXMLLoader fxmlLoader) {
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load());
        } catch (Exception e) {
            e.printStackTrace();
        }
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setResizable(false);
            stage.setTitle("Divan4ik");
            stage.getIcons().add(new Image(getClass().getResourceAsStream("/images/logo.png")));
            stage.show();
    }

    public void closeStage(Stage stage) {
        stage.close();
    }

    public void clearAdminCache() {
        sellerEditView = null;
        managerEditView = null;
        logView = null;
        adminProfileView = null;
        adminSelectedMenuItem.set(null);
    }

    public void clearSellerCache() {
        sellView = null;
        sellHistoryView = null;
        sellerProfileView = null;
        productsForSellerView = null;

    }

}
