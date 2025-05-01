package com.jms.salon.Controllers.Manager;

import com.jms.salon.Models.ConnectionServer;
import com.jms.salon.Models.Model;
import com.jms.salon.Views.ManagerMenuOption;
import com.salon.Server.Services.Manager.ManagerRequest;
import javafx.beans.value.ChangeListener;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class ManagerController implements Initializable {
    public BorderPane managerParent;
    private ChangeListener<ManagerMenuOption> listener;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        listener = (observable, oldValue, newValue) -> {
            switch (newValue) {
                case ManagerMenuOption.Report ->{
                    Model.getInstance().getViewFactory().clearManagerRofRequest();
                    managerParent.setCenter(Model.getInstance().getViewFactory().getReportView());
                }
                case ManagerMenuOption.HistoryReport ->{
                    Model.getInstance().getViewFactory().clearManagerRofRequest();
                    managerParent.setCenter(Model.getInstance().getViewFactory().getReportHistoryView());
                }
                case ManagerMenuOption.Statistics ->{
                    Model.getInstance().getViewFactory().clearManagerRofRequest();
                    managerParent.setCenter(Model.getInstance().getViewFactory().getStatisticsView());
                }
                case ManagerMenuOption.Products ->{
                    Model.getInstance().getViewFactory().clearManagerRofRequest();
                    managerParent.setCenter(Model.getInstance().getViewFactory().getProductView());
                }
                case ManagerMenuOption.AddProduct ->{
                    Model.getInstance().getViewFactory().clearManagerRofRequest();
                    managerParent.setCenter(Model.getInstance().getViewFactory().getAddProductView());
                }
                case ManagerMenuOption.EditProduct ->{
                    Model.getInstance().getViewFactory().clearManagerRofRequest();
                    managerParent.setCenter(Model.getInstance().getViewFactory().getEditProductView());
                }
                case ManagerMenuOption.Profile ->{
                    Model.getInstance().getViewFactory().clearManagerRofRequest();
                    managerParent.setCenter(Model.getInstance().getViewFactory().getManagerProfileView());
                }
                case ManagerMenuOption.Exit -> {
                    // Удаляем listener
                    Model.getInstance().getViewFactory().getManagerSelectedMenuItem().removeListener(listener);

                    // Очищаем все закешированные представления
                    Model.getInstance().getViewFactory().clearManagerCache();

                    ConnectionServer connectionServer = Model.getInstance().getConnectionServer();
                    connectionServer.sendObject(new ManagerRequest("Exit"));

                    Stage stage = (Stage)  managerParent.getScene().getWindow();
                    Model.getInstance().getViewFactory().closeStage(stage);
                    Model.getInstance().getViewFactory().showLoginWindow();
                }
                //default -> managerParent.setCenter(Model.getInstance().getViewFactory().());
            }
        };

        Model.getInstance().getViewFactory().getManagerSelectedMenuItem().addListener(listener);
    }
}
