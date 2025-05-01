package com.jms.salon.Controllers.Manager;

import com.jms.salon.Models.Model;
import com.salon.Server.Services.Export.Sale;
import com.salon.Server.Services.Export.Seller;
import com.salon.Server.Services.Manager.ManagerRequest;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.fxml.FXML;

import java.net.URL;
import java.util.ResourceBundle;

public class StatisticsController implements Initializable {
    @FXML
    private Label bestSellingProductCount;

    @FXML
    private Label bestSellingProductName;

    @FXML
    private Label mostProfitableProductName;

    @FXML
    private Label mostProfitableProductProfit;

    @FXML
    private Label topSellerName;

    @FXML
    private Label topSellerProfit;

    @FXML
    private Label topSellerSalesCount;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        Model.getInstance().getConnectionServer().sendObject(new ManagerRequest("GetStatistics"));

        Sale bestProductByCount = (Sale) Model.getInstance().getConnectionServer().receiveObject();
        Sale bestProductByProfit = (Sale) Model.getInstance().getConnectionServer().receiveObject();
        Seller bestSeller = (Seller) Model.getInstance().getConnectionServer().receiveObject();

        bestSellingProductCount.setText(String.valueOf(bestProductByCount.getPrice()));
        bestSellingProductName.setText(bestProductByCount.getProductName());

        mostProfitableProductName.setText(bestProductByProfit.getProductName());
        mostProfitableProductProfit.setText(String.valueOf( bestProductByProfit.getPrice()));

        topSellerName.setText(bestSeller.getUserName());
        topSellerSalesCount.setText(String.valueOf(bestSeller.getSellCount()));
    }



}
