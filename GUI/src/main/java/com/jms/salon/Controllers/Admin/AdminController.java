package com.jms.salon.Controllers.Admin;

import com.jms.salon.Models.ConnectionServer;
import com.jms.salon.Models.Model;
import com.jms.salon.Views.AdminMenuOption;
import com.salon.Server.Services.Admin.AdminRequest;
import javafx.beans.value.ChangeListener;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;


public class AdminController implements Initializable {
    public BorderPane adminParent;
    private ChangeListener<AdminMenuOption> listener;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        listener = (observable, oldValue, newValue) -> {
            switch (newValue) {
                case AdminMenuOption.Managers -> adminParent.setCenter(Model.getInstance().getViewFactory().getManagerEditView());
                case AdminMenuOption.Products -> adminParent.setCenter(Model.getInstance().getViewFactory().getProductView());
                case AdminMenuOption.AddProduct -> adminParent.setCenter(Model.getInstance().getViewFactory().getAddProductView());
                case AdminMenuOption.Log -> adminParent.setCenter(Model.getInstance().getViewFactory().getLogView());
                case AdminMenuOption.Profile -> adminParent.setCenter(Model.getInstance().getViewFactory().getAdminProfileView());
                case AdminMenuOption.EditProduct -> adminParent.setCenter(Model.getInstance().getViewFactory().getEditProductView());
                case AdminMenuOption.Exit -> {
                    // Удаляем listener
                    Model.getInstance().getViewFactory().getAdminSelectedMenuItem().removeListener(listener);

                    // Очищаем все закешированные представления
                    Model.getInstance().getViewFactory().clearAdminCache();

                    Stage stage = (Stage) adminParent.getScene().getWindow();
                    Model.getInstance().getViewFactory().closeStage(stage);
                    Model.getInstance().getViewFactory().showLoginWindow();
                    ConnectionServer connectionServer = Model.getInstance().getConnectionServer();
                    connectionServer.sendObject(new AdminRequest("Exit"));
                }
                default -> adminParent.setCenter(Model.getInstance().getViewFactory().getSellerEditView());
            }
        };

        Model.getInstance().getViewFactory().getAdminSelectedMenuItem().addListener(listener);
    }
}
