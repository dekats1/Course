package com.jms.salon.Controllers.Product;

import com.salon.Server.Services.Export.Product;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class ProductSellForSellerController implements Initializable {

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
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
