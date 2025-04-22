package com.jms.salon.Controllers.Product;

import com.jms.salon.Models.ConnectionServer;
import com.jms.salon.Models.Model;
import com.salon.Server.Services.Admin.AdminRequest;
import com.salon.Server.Services.Export.Product;
import com.jms.salon.Views.AdminMenuOption;
import com.sun.jdi.connect.spi.Connection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ProductController implements Initializable {
    @FXML
    private ListView<AnchorPane> productListView;
    @FXML
    private Button addProductBtn;

    private ObservableList<Product> products = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ConnectionServer connectionServer = Model.getInstance().getConnectionServer();
        connectionServer.sendObject(new AdminRequest("AllProducts"));
        Model.getInstance().getProducts().setAll((List<Product>) connectionServer.receiveObject());
        connectionServer.sendObject(new AdminRequest("AllCategories"));
        Product.setCategories((List<String>) connectionServer.receiveObject());

        addProductBtn.setOnAction(event -> {
            Model.getInstance().getViewFactory().getAdminSelectedMenuItem().set(AdminMenuOption.AddProduct);
        });



        Model.getInstance().getProducts().addListener((javafx.collections.ListChangeListener.Change<? extends Product> change) -> {
            refreshProductList();
        });

        refreshProductList();
    }

    private void refreshProductList() {
        productListView.getItems().clear();
        for (Product product : Model.getInstance().getProducts()) {
            addProductToView(product);
        }
    }

    private void addProductToView(Product product) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/Product/ProductSell.fxml"));
            AnchorPane productCell = loader.load();

            ProductSellController controller = loader.getController();
            controller.setProductInfo(product);
            controller.setDeleteAction(() -> {
                Model.getInstance().getConnectionServer().sendObject(new AdminRequest("DelProduct", product.getName()));
                Model.getInstance().removeProduct(product);
            });

            productListView.getItems().add(productCell);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}