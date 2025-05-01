package com.jms.salon.Controllers.Manager;


import com.jms.salon.Models.Model;
import com.jms.salon.Views.ManagerMenuOption;
import com.salon.Server.Services.Manager.ManagerRequest;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.text.SimpleDateFormat;

public class ManagerProfileController {
    @FXML
    public Text errorLbl;
    @FXML
    private ImageView avatarImage;

    @FXML
    private Button changeAvatarButton;

    @FXML
    private Button changePasswordButton;

    @FXML
    private PasswordField confirmPasswordField;

    @FXML
    private Label loginLabel;

    @FXML
    private Button logoutButton;

    @FXML
    private Label nameLabel;

    @FXML
    private PasswordField newPasswordField;

    @FXML
    private PasswordField oldPasswordField;

    @FXML
    private Label regDateLbl;


    @FXML
    public void initialize() {
        changeAvatarButton.setOnAction(event -> onChangeAvatar());

        changePasswordButton.setOnAction(event -> onChangePassword());

        logoutButton.setOnAction(event -> onLogout());
        loadUserData();
    }

    private void onChangePassword() {
        errorLbl.setVisible(false);
        errorLbl.setText("");
        errorLbl.setFill(Color.rgb(255, 0, 0));
        String oldPassword = oldPasswordField.getText();
        String newPassword = newPasswordField.getText();
        String confirmPassword = confirmPasswordField.getText();

        if (oldPassword.isEmpty() || newPassword.isEmpty() || confirmPassword.isEmpty()) {
            errorLbl.setVisible(true);
            errorLbl.setText("Все поля должны быть заполнены");
            return;
        }

        if (!newPassword.equals(confirmPassword)) {
            errorLbl.setVisible(true);
            errorLbl.setText("Новый пароль и подтверждение не совпадают");
            return;
        }

        if (newPassword.length() < 6) {
            errorLbl.setVisible(true);
            errorLbl.setText("Пароль должен содержать минимум 6 символов");
            return;
        }

        ManagerRequest request = new ManagerRequest(
                "ChangePassword",
                Model.getInstance().getCurrentUser(),
                oldPassword,
                newPassword,
                Model.getInstance().getCurrentUser()
        );

        Model.getInstance().getConnectionServer().sendObject(request);
        ManagerRequest success = (ManagerRequest) Model.getInstance().getConnectionServer().receiveObject();

        if (success.getSuccess()) {
            errorLbl.setVisible(true);
            errorLbl.setFill(Color.rgb(0, 255, 0));
            errorLbl.setText("Пароль успешно изменен");
            clearPasswordFields();
        } else {
            errorLbl.setVisible(true);
            errorLbl.setFill(Color.rgb(255, 0, 0));
            errorLbl.setText(success.getErrorMassage());
        }
    }

    private void clearPasswordFields() {
        oldPasswordField.clear();
        newPasswordField.clear();
        confirmPasswordField.clear();
    }


    private void loadUserData() {
        // Загрузка данных пользователя
        loginLabel.setText(Model.getInstance().getCurrentUser());
        String name = Model.getInstance().getCurrentUser();
        Model.getInstance().getConnectionServer().sendObject(new ManagerRequest("UserData", Model.getInstance().getCurrentUser()));
        ManagerRequest userData = (ManagerRequest) Model.getInstance().getConnectionServer().receiveObject();
        nameLabel.setText(userData.getUserName());
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        regDateLbl.setText(formatter.format( userData.getDate()));

        // Загрузка аватара
        Model.getInstance().getConnectionServer()
                .sendObject(new ManagerRequest("GetPhoto", Model.getInstance().getCurrentUser()));
        String path = (String) Model.getInstance().getConnectionServer().receiveObject();
        inputImage(new File(path));
    }


    private void inputImage(File file) {
        System.out.println(file);
        if (file != null) {
            Image image = new Image(file.toURI().toString());
            avatarImage.setImage(image);
        }
    }

    private void onLogout() {
        Model.getInstance().getViewFactory().getManagerSelectedMenuItem().set(ManagerMenuOption.Exit);
    }

    private void onChangeAvatar() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Выбери изображение");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Изображения", "*.png", "*.jpg", "*.jpeg")
        );
        File selectedFile = fileChooser.showOpenDialog(getStage());
        Model.getInstance().getConnectionServer().sendObject(new ManagerRequest("SetPhoto", Model.getInstance().getCurrentUser(),selectedFile.getPath(),Model.getInstance().getCurrentUser()));

        inputImage(selectedFile);
    }

    private Stage getStage() {
        return (Stage) avatarImage.getScene().getWindow();
    }
}
