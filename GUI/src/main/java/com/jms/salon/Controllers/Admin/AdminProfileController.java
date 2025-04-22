package com.jms.salon.Controllers.Admin;

import com.jms.salon.Models.Model;
import com.jms.salon.Views.AdminMenuOption;
import com.salon.Server.Services.Admin.AdminRequest;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class AdminProfileController {

    @FXML private ImageView avatarImage;
    @FXML private Button changeAvatarButton;
    @FXML private Label nameLabel;
    @FXML private Label loginLabel;
    @FXML private Label emailLabel;
    @FXML private Button logoutButton;

    @FXML
    public void initialize() {

        changeAvatarButton.setOnAction(event -> onChangeAvatar());
        logoutButton.setOnAction(event -> onLogout());
        Model.getInstance().getConnectionServer().sendObject(new AdminRequest("GetPhoto", Model.getInstance().getCurrentUser()));
        String path = (String)Model.getInstance().getConnectionServer().receiveObject();
        File file = new File(path);
        inputImage(file);
    }

    private void inputImage(File file) {
        System.out.println(file);
        if (file != null) {
            Image image = new Image(file.toURI().toString());
            avatarImage.setImage(image);
        }
    }

    private void onLogout() {
        Model.getInstance().getViewFactory().getAdminSelectedMenuItem().set(AdminMenuOption.Exit);
    }

    private void onChangeAvatar() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Выбери изображение");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Изображения", "*.png", "*.jpg", "*.jpeg")
        );
        File selectedFile = fileChooser.showOpenDialog(getStage());
        Model.getInstance().getConnectionServer().sendObject(new AdminRequest("SetPhoto", Model.getInstance().getCurrentUser(),selectedFile.getPath()));

        inputImage(selectedFile);
    }

    private Stage getStage() {
        return (Stage) avatarImage.getScene().getWindow();
    }
}
