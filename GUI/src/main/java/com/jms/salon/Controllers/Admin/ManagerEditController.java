package com.jms.salon.Controllers.Admin;

import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class ManagerEditController implements Initializable {

    @FXML
    private ListView<AnchorPane> managerListView;
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
            addManager(firstName, lastName, login, date);
        });
    }

    public void addManager(String name, String surname, String login, String date) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/Admin/ManagerSell.fxml"));
        AnchorPane managerCell;
        try {
            managerCell = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        ManagerSellController controller = loader.getController();
        controller.setManagerInfo(name, surname, login, date);
        controller.setItemPane(managerCell);

        controller.setDeleteAction(() -> {
            FadeTransition ft = new FadeTransition(Duration.millis(300), managerCell);
            ft.setFromValue(1.0);
            ft.setToValue(0.0);
            ft.setOnFinished(event -> managerListView.getItems().remove(managerCell));
            ft.play();
        });

        managerListView.getItems().add(managerCell);

        clearField();
    }

    private void clearField() {
        loginField.clear();
        firstNameField.clear();
        lastNameField.clear();
        passwordField.clear();
    }
}
