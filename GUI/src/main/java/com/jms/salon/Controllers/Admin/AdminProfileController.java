package com.jms.salon.Controllers.Admin;

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

    @FXML
    public void initialize() {
        changeAvatarButton.setOnAction(event -> onChangeAvatar());
    }

    private void onChangeAvatar() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Выбери изображение");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Изображения", "*.png", "*.jpg", "*.jpeg", "*.gif")
        );

        File selectedFile = fileChooser.showOpenDialog(getStage());
        if (selectedFile != null) {
            Image image = new Image(selectedFile.toURI().toString());
            avatarImage.setImage(image);
        }
    }

    private Stage getStage() {
        return (Stage) avatarImage.getScene().getWindow();
    }
}
