package com.jms.salon.Controllers.Product;

import com.jms.salon.Models.Model;
import com.jms.salon.Views.AdminMenuOption;
import com.salon.Server.Services.Export.Product;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

import static java.lang.System.*;

public class ProductSellController implements Initializable {
    @FXML
    private Label categoryLabel;
    @FXML
    private Label costLabel;
    @FXML
    private Button deleteButton;
    @FXML
    private Label descriptionLabel;
    @FXML
    private Button editButton;
    @FXML
    private Label nameLabel;
    @FXML
    private Label priceLabel;
    @FXML
    private Label quantityLabel;
    @FXML
    private Label salesLabel;
    
    private Runnable deleteAction;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        deleteButton.setOnAction(event -> {
            if (deleteAction != null) {
                deleteAction.run();
            }
        });
        editButton.setOnAction(event -> {
            Product product = new Product(nameLabel.getText(), descriptionLabel.getText(), categoryLabel.getText(), Double.parseDouble(priceLabel.getText()), Double.parseDouble(costLabel.getText()), Integer.parseInt(quantityLabel.getText()));
            ProductController.setProduct(product);
            Model.getInstance().getViewFactory().getAdminSelectedMenuItem().set(AdminMenuOption.EditProduct);
        });
    }

    public void setProductInfo(Product product) {
        nameLabel.setText(product.getName());
        priceLabel.setText(String.format("%.2f", product.getPrice()));
        System.out.println(priceLabel.getText());
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