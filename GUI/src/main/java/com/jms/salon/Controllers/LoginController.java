package com.jms.salon.Controllers;


import com.jms.salon.Models.ConnectionServer;
import com.jms.salon.Models.Model;
import com.salon.Server.Services.AuthRequest;
import com.salon.Server.Services.AuthResponse;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class LoginController implements Initializable {
    @FXML private TextField userNameFld;
    @FXML private PasswordField passwordFld;
    @FXML private Label errorLbl;
    @FXML private Button loginBtn;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loginBtn.setOnAction(event -> handleLogin());
    }

    private void handleLogin() {
        String username = userNameFld.getText().trim();
        String password = passwordFld.getText();

        if (username.isEmpty() || password.isEmpty()) {
            errorLbl.setText("Логин и пароль обязательны");
            return;
        }

        ConnectionServer connection = Model.getInstance().getConnectionServer();
        connection.sendObject(new AuthRequest(username, password));

        AuthResponse response = (AuthResponse) connection.receiveObject();

        if (response.isSuccess()) {
            Model.getInstance().setCurrentRole(response.getRole());
            openAppWindow(response.getRole());
        } else {
            errorLbl.setText(response.getMessage());
        }
    }

    private void openAppWindow(String role) {
        Stage stage = (Stage) loginBtn.getScene().getWindow();
        Model.getInstance().getViewFactory().closeStage(stage);

        switch (role) {
            case "admin" -> Model.getInstance().getViewFactory().showAdminWindow();
           // case "seller" -> Model.getInstance().getViewFactory().showSellerWindow();
           // default -> Model.getInstance().getViewFactory().showErrorWindow("Неизвестная роль: " + role);
        }
    }
}