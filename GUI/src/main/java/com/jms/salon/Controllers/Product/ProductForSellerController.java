package com.jms.salon.Controllers.Product;

import com.jms.salon.Models.ConnectionServer;
import com.jms.salon.Models.Model;
import com.salon.Server.Services.Export.Product;
import com.salon.Server.Services.Seller.SellerRequest;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class ProductForSellerController implements Initializable {

    @FXML
    private Button SearchButton;

    @FXML
    private ListView<AnchorPane> productListView;

    @FXML
    private TextField searchField;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        Model.getInstance().getProducts().addListener((javafx.collections.ListChangeListener.Change<? extends Product> change) -> {
            refreshProductList(Model.getInstance().getProducts());
        });

        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            List<Product> filterProducts = Model.getInstance().getProducts().stream().filter(product -> product.getName().toLowerCase().contains(newValue.toLowerCase())).collect(Collectors.toList());
            refreshProductList(filterProducts);
        });

        refreshProductList(Model.getInstance().getProducts());
    }

    private void refreshProductList(List<Product> products) {
        productListView.getItems().clear();
        for (Product product : products) {
            addProductToView(product);
        }
    }

    private void addProductToView(Product product) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/Product/ProductSellForSeller.fxml"));
            AnchorPane productCell = loader.load();

            ProductSellForSellerController controller = loader.getController();
            controller.setProductInfo(product);

            productListView.getItems().add(productCell);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
