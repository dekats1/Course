package com.jms.salon.Controllers.Admin;

import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class SellerEditController implements Initializable {

    @FXML
    private ListView<AnchorPane> sellerListView;
    @FXML
    private TextField loginField;
    @FXML
    private TextField firstNameField;
    @FXML
    private TextField lastNameField;
    @FXML
    private TextField passwordField;
    @FXML
    private Button addSellerBtn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addSellerBtn.setOnAction(event -> {
            String login = loginField.getText();
            String firstName = firstNameField.getText();
            String lastName = lastNameField.getText();
            String password = passwordField.getText();
            String date = LocalDate.now().toString();
            addSeller(firstName, lastName, login, date);
        });
    }

    public void addSeller(String name, String surname, String login, String date) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/Admin/SellerSell.fxml"));
        AnchorPane sellerCell;
        try {
            sellerCell = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        SellerSellController controller = loader.getController();
        controller.setSellerInfo(name, surname, login, date);
        controller.setItemPane(sellerCell);

        controller.setDeleteAction(() -> {
            FadeTransition ft = new FadeTransition(Duration.millis(300), sellerCell);
            ft.setFromValue(1.0);
            ft.setToValue(0.0);
            ft.setOnFinished(event -> sellerListView.getItems().remove(sellerCell));
            ft.play();
        });

        sellerListView.getItems().add(sellerCell);

        clearField();
    }

    private void clearField() {
        loginField.clear();
        firstNameField.clear();
        lastNameField.clear();
        passwordField.clear();
    }
}