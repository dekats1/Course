package com.jms.salon.Controllers.Admin;

import com.jms.salon.Models.Model;
import com.jms.salon.Views.AdminMenuOption;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;

import java.awt.*;
import java.net.URI;
import java.net.URL;
import java.util.ResourceBundle;

public class AdminMenuController implements Initializable {

    @FXML
    private Label tgLabel;
    @FXML
    private Label instLabel;
    @FXML
    private Button exitBtn;

    @FXML
    private Button logBtn;

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
            return;
        });

        managerBtn.setOnAction(event -> {
            onManagers();
            return;

        });

       productBtn.setOnAction(event -> {
            onProducts();
           return;

        });

        logBtn.setOnAction(event -> {
            onLog();
            return;

        });

        exitBtn.setOnAction(event -> {
            onExit();
            return;

        });

        profileBtn.setOnAction(event -> {
            onProfile();
            return;

        });
        tgLabel.setOnMouseClicked(e -> openUrl("https://t.me/divaniv"));
        instLabel.setOnMouseClicked(e -> openUrl("https://instagram.com/viktory_iv_"));
    }

    private void openUrl(String url) {
        try {
            Desktop.getDesktop().browse(new URI(url));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void onProfile() {
        Model.getInstance().getViewFactory().getAdminSelectedMenuItem().set(AdminMenuOption.Profile);
    }

    private void onExit() {
        Model.getInstance().getViewFactory().getAdminSelectedMenuItem().set(AdminMenuOption.Exit);
    }

    private void onLog() {
        Model.getInstance().getViewFactory().getAdminSelectedMenuItem().set(AdminMenuOption.Log);
    }

    private void onProducts() {
        Model.getInstance().getViewFactory().getAdminSelectedMenuItem().set(AdminMenuOption.Products);
    }

    private void onManagers() {
        Model.getInstance().getViewFactory().getAdminSelectedMenuItem().set(AdminMenuOption.Managers);
    }

    private void onSellers() {
        Model.getInstance().getViewFactory().getAdminSelectedMenuItem().set(AdminMenuOption.Sellers);
    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addListeners();
    }

}
