package com.jms.salon.Controllers.Admin;

import com.jms.salon.Models.Model;
import com.jms.salon.Views.AdminMenuOption;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;

import java.net.URL;
import java.util.ResourceBundle;

public class AdminMenuController implements Initializable {

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
            System.out.println(Model.getInstance().getViewFactory().getAdminSelectedMenuItem().get()+"Sel");

            return;
        });

        managerBtn.setOnAction(event -> {
            onManagers();
            System.out.println(Model.getInstance().getViewFactory().getAdminSelectedMenuItem().get()+"Man");
            return;

        });

       productBtn.setOnAction(event -> {
            onProducts();
           System.out.println(Model.getInstance().getViewFactory().getAdminSelectedMenuItem().get()+"Prod");

           return;

        });

        logBtn.setOnAction(event -> {
            onLog();
            System.out.println(Model.getInstance().getViewFactory().getAdminSelectedMenuItem().get()+"LOg");
            return;

        });

        exitBtn.setOnAction(event -> {
            onExit();
            System.out.println(Model.getInstance().getViewFactory().getAdminSelectedMenuItem().get()+"Ex");
            return;

        });

        profileBtn.setOnAction(event -> {
            onProfile();
            System.out.println(Model.getInstance().getViewFactory().getAdminSelectedMenuItem().get()+"Prof");
            return;

        });
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
