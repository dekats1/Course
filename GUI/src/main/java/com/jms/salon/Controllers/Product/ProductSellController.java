package com.jms.salon.Controllers.Product;

import com.jms.salon.Models.Product;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.fxml.FXML;

import java.net.URL;
import java.util.ResourceBundle;

public class ProductSellController implements Initializable {
    @FXML private Label categoryLabel;
    @FXML private Label costLabel;
    @FXML private Button deleteButton;
    @FXML private Label descriptionLabel;
    @FXML private Button editButton;
    @FXML private Label nameLabel;
    @FXML private Label priceLabel;
    @FXML private Label quantityLabel;
    @FXML private Label salesLabel;
    
    private Runnable deleteAction;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        deleteButton.setOnAction(event -> {
            if (deleteAction != null) {
                deleteAction.run();
            }
        });
    }

    public void setProductInfo(Product product) {
        nameLabel.setText(product.getName());
        priceLabel.setText(String.format("%.2f", product.getPrice()));
        costLabel.setText(String.format("%.2f", product.getCost()));
        quantityLabel.setText(String.valueOf(product.getQuantity()));
        descriptionLabel.setText(product.getDescription());
        categoryLabel.setText(product.getCategory());
        salesLabel.setText("0"); // дописать
    }


    public void setDeleteAction(Runnable action) {
        this.deleteAction = action;
    }
}