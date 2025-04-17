package com.jms.salon.Controllers.Seller;

import com.jms.salon.Models.Model;
import com.jms.salon.Views.AdminMenuOption;
import com.jms.salon.Views.SellerMenuOption;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class SellerProfileController implements Initializable {
    @FXML
    private ImageView avatarImage;
    @FXML private Button changeAvatarButton;
    @FXML private Label nameLabel;
    @FXML private Label loginLabel;
    @FXML private Label emailLabel;
    @FXML private Button logoutButton;


    public void initialize(URL url, ResourceBundle resourceBundle) {
        changeAvatarButton.setOnAction(event -> onChangeAvatar());
        logoutButton.setOnAction(event -> onLogout());
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
        if (selectedFile != null) {
            Image image = new Image(selectedFile.toURI().toString());
            avatarImage.setImage(image);
        }
    }

    private Stage getStage() {
        return (Stage) avatarImage.getScene().getWindow();
    }

}
