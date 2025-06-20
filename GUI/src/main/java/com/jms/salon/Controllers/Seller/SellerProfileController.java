package com.jms.salon.Controllers.Seller;

import com.jms.salon.Models.Model;

import com.jms.salon.Views.SellerMenuOption;
import com.salon.Server.Services.Seller.SellerRequest;
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


public class SellerProfileController {
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

        SellerRequest request = new SellerRequest(
                "ChangePassword",
                Model.getInstance().getCurrentUser(),
                oldPassword,
                newPassword,
                Model.getInstance().getCurrentUser()
        );

        Model.getInstance().getConnectionServer().sendObject(request);
        SellerRequest success = (SellerRequest) Model.getInstance().getConnectionServer().receiveObject();

        if (success.getSuccess()) {
            errorLbl.setVisible(true);
            errorLbl.setFill(Color.rgb(0, 255, 0));
            errorLbl.setText("Пароль успешно изменен");
            clearPasswordFields();
        } else {
            errorLbl.setVisible(true);
            errorLbl.setFill(Color.rgb(255, 0, 0));
            errorLbl.setText(success.getErrorMessage());
        }
    }

    private void clearPasswordFields() {
        oldPasswordField.clear();
        newPasswordField.clear();
        confirmPasswordField.clear();
    }


    private void loadUserData() {
        loginLabel.setText(Model.getInstance().getCurrentUser());
        Model.getInstance().getConnectionServer().sendObject(new SellerRequest("UserData", Model.getInstance().getCurrentUser()));
        SellerRequest userData = (SellerRequest) Model.getInstance().getConnectionServer().receiveObject();
        nameLabel.setText(userData.getUserName());
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        regDateLbl.setText(formatter.format( userData.getDateInfo()));

        // Загрузка аватара
        Model.getInstance().getConnectionServer()
                .sendObject(new SellerRequest("GetPhoto", Model.getInstance().getCurrentUser()));
        String path = (String) Model.getInstance().getConnectionServer().receiveObject();
        inputImage(new File(path));
    }


    private void inputImage(File file) {
        //System.out.println(file);
        if (file != null) {
            Image image = new Image(file.toURI().toString());
            avatarImage.setImage(image);
        }
    }

    private void onLogout() {
        Model.getInstance().getViewFactory().getSellerSelectedMenuItem().set(SellerMenuOption.Exit);
    }

    private void onChangeAvatar() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Выбери изображение");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Изображения", "*.png", "*.jpg", "*.jpeg")
        );
        File selectedFile = fileChooser.showOpenDialog(getStage());
        Model.getInstance().getConnectionServer().sendObject(new SellerRequest("SetPhoto", Model.getInstance().getCurrentUser(),selectedFile.getPath(),Model.getInstance().getCurrentUser()));

        inputImage(selectedFile);
    }

    private Stage getStage() {
        return (Stage) avatarImage.getScene().getWindow();
    }
}
