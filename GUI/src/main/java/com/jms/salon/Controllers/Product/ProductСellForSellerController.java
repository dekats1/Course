package com.jms.salon.Controllers.Product;

import com.salon.Server.Services.Export.Product;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class ProductСellForSellerController implements Initializable {

    @FXML
    private Label categoryLabel;
    @FXML
    private Label costLabel;
    @FXML
    private Label descriptionLabel;
    @FXML
    private Label nameLabel;
    @FXML
    private Label priceLabel;
    @FXML
    private Label quantityLabel;
    @FXML
    private Label salesLabel;

    public void setProductInfo(Product product) {
        nameLabel.setText(product.getName());
        descriptionLabel.setText(product.getDescription());
        categoryLabel.setText(product.getCategory());
        priceLabel.setText(String.valueOf(product.getPrice()));
        quantityLabel.setText(String.valueOf(product.getQuantity()));
        costLabel.setText(String.valueOf(product.getCost()));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
