package com.jms.salon.Controllers.Seller;

import com.salon.Server.Services.Export.Sale;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;

import java.net.URL;
import java.util.ResourceBundle;

public class SalesHistorySellController implements Initializable {

    @FXML
    private Label dateLabel;

    @FXML
    private Label priceLabel;

    @FXML
    private Label productNameLabel;

    @FXML
    private Label quantityLabel;

    @FXML
    private Label sellerLoginLabel;

    @FXML
    private Label totalPriceLabel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void setInfo(Sale sale) {
        totalPriceLabel.setText(String.valueOf((sale.getPrice() * sale.getQuantity())));
        productNameLabel.setText(sale.getProductName());
        priceLabel.setText(String.valueOf(sale.getPrice()));
        sellerLoginLabel.setText(sale.getSellerName());
        dateLabel.setText(String.valueOf(sale.getDate()));
        quantityLabel.setText(String.valueOf(sale.getQuantity()));
    }
}
