package com.jms.salon.Controllers.Product;

import com.jms.salon.Models.ConnectionServer;
import com.jms.salon.Models.Model;
import com.jms.salon.Views.ManagerMenuOption;
import com.salon.Server.Services.Admin.AdminRequest;
import com.salon.Server.Services.Export.Product;
import com.jms.salon.Views.AdminMenuOption;
import com.salon.Server.Services.Manager.ManagerRequest;
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

    private static Product selectedProduct;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ConnectionServer connectionServer = Model.getInstance().getConnectionServer();
        if(Model.getInstance().getCurrentRole().equals("admin")) {
            connectionServer.sendObject(new AdminRequest("AllProducts"));
            Model.getInstance().getProducts().setAll((List<Product>) connectionServer.receiveObject());
            connectionServer.sendObject(new AdminRequest("AllCategories"));
            Product.setCategories((List<String>) connectionServer.receiveObject());
        }
        else if(Model.getInstance().getCurrentRole().equals("manager")) {
            connectionServer.sendObject(new ManagerRequest("AllProducts"));
            Model.getInstance().getProducts().setAll((List<Product>) connectionServer.receiveObject());
            connectionServer.sendObject(new ManagerRequest("AllCategories"));
            Product.setCategories((List<String>) connectionServer.receiveObject());
        }
        addProductBtn.setOnAction(event -> {
            if (Model.getInstance().getCurrentRole().equals("admin")) {
                Model.getInstance().getViewFactory().getAdminSelectedMenuItem().set(AdminMenuOption.AddProduct);
            }else if(Model.getInstance().getCurrentRole().equals("manager")) {
                Model.getInstance().getViewFactory().getManagerSelectedMenuItem().set(ManagerMenuOption.AddProduct);
            }
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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/Product/ProductСell.fxml"));
            AnchorPane productCell = loader.load();

            ProductСellController controller = loader.getController();
            controller.setProductInfo(product);
            controller.setDeleteAction(() -> {
                if (Model.getInstance().getCurrentRole().equals("admin")) {
                    Model.getInstance().getConnectionServer().sendObject(new AdminRequest("DelProduct", product.getName()));
                }
                else if(Model.getInstance().getCurrentRole().equals("manager")) {
                    Model.getInstance().getConnectionServer().sendObject(new ManagerRequest("DelProduct", product.getName()));
                }
                Model.getInstance().removeProduct(product);
            });

            productListView.getItems().add(productCell);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Product getSelectedProduct() {
        return selectedProduct;
    }

    public static void setSelectedProduct(Product product) {
        ProductController.selectedProduct = product;
    }
}