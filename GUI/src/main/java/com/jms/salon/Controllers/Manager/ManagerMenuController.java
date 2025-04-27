package com.jms.salon.Controllers.Manager;

import com.jms.salon.Models.Model;
import com.jms.salon.Views.ManagerMenuOption;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.awt.*;
import java.net.URI;
import java.net.URL;
import java.util.ResourceBundle;

public class ManagerMenuController implements Initializable {

    @FXML
    private Label tgLabel;
    @FXML
    private Label instLabel;
    @FXML
    private Button exitBtn;
    @FXML
    private Button productBtn;
    @FXML
    private Button profileBtn;
    @FXML
    private Button reportBtn;
    @FXML
    private Button reportHistoryBtn;
    @FXML
    private Button statisticsBtn;

    private void addListeners() {
        productBtn.setOnAction(event -> {
            onProducts();
        });

        profileBtn.setOnAction(event -> {
            onProfile();
        });

        reportBtn.setOnAction(event -> {
            onReport();
        });

        reportHistoryBtn.setOnAction(event -> {
            onReportHistory();
        });

        statisticsBtn.setOnAction(event -> {
            onStatistics();
        });

        exitBtn.setOnAction(event -> {
            onExit();
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
        Model.getInstance().getViewFactory().getManagerSelectedMenuItem().set(ManagerMenuOption.Profile);
    }

    private void onExit() {
        Model.getInstance().getViewFactory().getManagerSelectedMenuItem().set(ManagerMenuOption.Exit);
    }

    private void onProducts() {
        Model.getInstance().getViewFactory().getManagerSelectedMenuItem().set(ManagerMenuOption.Products);
    }

    private void onReport() {
        Model.getInstance().getViewFactory().getManagerSelectedMenuItem().set(ManagerMenuOption.Report);
    }

    private void onReportHistory() {
        Model.getInstance().getViewFactory().getManagerSelectedMenuItem().set(ManagerMenuOption.HistoryReport);
    }

    private void onStatistics() {
        Model.getInstance().getViewFactory().getManagerSelectedMenuItem().set(ManagerMenuOption.Statistics);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addListeners();
    }
}