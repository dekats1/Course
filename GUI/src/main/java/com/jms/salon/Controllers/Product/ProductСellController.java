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

public class ProductСellController implements Initializable {
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
    
    private Runnable deleteAction;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        deleteButton.setOnAction(event -> {
            if (deleteAction != null) {
                deleteAction.run();
            }
        });
        editButton.setOnAction(event -> {
            for(Product product: Model.getInstance().getProducts()){
                if(product.getName().equals(nameLabel.getText())&& product.getDescription().equals(descriptionLabel.getText())){
                    ProductController.setSelectedProduct(product);
                    break;
                }
            }
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
    }


    public void setDeleteAction(Runnable action) {
        this.deleteAction = action;
    }
}