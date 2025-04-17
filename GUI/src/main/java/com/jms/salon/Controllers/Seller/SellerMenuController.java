package com.jms.salon.Controllers.Seller;

import com.jms.salon.Models.Model;
import com.jms.salon.Views.AdminMenuOption;
import com.jms.salon.Views.SellerMenuOption;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;

import java.net.URL;
import java.util.ResourceBundle;

public class SellerMenuController implements Initializable {

    @FXML
    private Button exitBtn;

    @FXML
    private Button managerBtn;

    @FXML
    private Button productBtn;

    @FXML
    private Button profileBtn;

    @FXML
    private Button sellerBtn;

    private void addListeners() {

        sellerBtn.setOnAction(event -> {
            onSellers();
            //System.out.println(Model.getInstance().getViewFactory().getAdminSelectedMenuItem().get()+"Sel");

            return;
        });

        managerBtn.setOnAction(event -> {
            onManagers();
           // System.out.println(Model.getInstance().getViewFactory().getAdminSelectedMenuItem().get()+"Man");
            return;

        });

        productBtn.setOnAction(event -> {
            onProducts();
            //System.out.println(Model.getInstance().getViewFactory().getAdminSelectedMenuItem().get()+"Prod");
            return;
        });


        exitBtn.setOnAction(event -> {
            onExit();
           // System.out.println(Model.getInstance().getViewFactory().getAdminSelectedMenuItem().get()+"Ex");
            return;
        });

        profileBtn.setOnAction(event -> {
            onProfile();
            // System.out.println(Model.getInstance().getViewFactory().getAdminSelectedMenuItem().get()+"Prof");
            return;

        });
    }

    private void onProfile() {
        Model.getInstance().getViewFactory().getSellerSelectedMenuItem().set(SellerMenuOption.Profile);
    }

    private void onExit() {
        Model.getInstance().getViewFactory().getSellerSelectedMenuItem().set(SellerMenuOption.Exit);
    }


    private void onProducts() {
        Model.getInstance().getViewFactory().getSellerSelectedMenuItem().set(SellerMenuOption.Products);
    }

    private void onManagers() {
        Model.getInstance().getViewFactory().getSellerSelectedMenuItem().set(SellerMenuOption.HistorySell);
    }

    private void onSellers() {
        Model.getInstance().getViewFactory().getSellerSelectedMenuItem().set(SellerMenuOption.Sell);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addListeners();
    }

}
