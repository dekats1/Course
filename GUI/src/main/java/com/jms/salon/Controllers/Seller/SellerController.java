package com.jms.salon.Controllers.Seller;

import com.jms.salon.Models.Model;
import com.jms.salon.Views.SellerMenuOption;
import javafx.beans.value.ChangeListener;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class SellerController implements Initializable {
    public BorderPane sellerParent;
    private ChangeListener<SellerMenuOption> listener;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        listener = (observable, oldValue, newValue) -> {
            switch (newValue) {
                case SellerMenuOption.Sell -> sellerParent.setCenter(Model.getInstance().getViewFactory().getSellView());
                case SellerMenuOption.HistorySell -> sellerParent.setCenter(Model.getInstance().getViewFactory().getSellHistoryView());
                case SellerMenuOption.Products -> sellerParent.setCenter(Model.getInstance().getViewFactory().getProductsForSellerView());
                case SellerMenuOption.Profile -> sellerParent.setCenter(Model.getInstance().getViewFactory().getSellerProfileView());
                case SellerMenuOption.Exit -> {
                    // Удаляем listener
                    Model.getInstance().getViewFactory().getSellerSelectedMenuItem().removeListener(listener);

                    // Очищаем все закешированные представления
                    Model.getInstance().getViewFactory().clearSellerCache();

                    Stage stage = (Stage) sellerParent.getScene().getWindow();
                    Model.getInstance().getViewFactory().closeStage(stage);
                    Model.getInstance().getViewFactory().showLoginWindow();
                }
                default -> sellerParent.setCenter(Model.getInstance().getViewFactory().getSellerEditView());
            }
        };

        Model.getInstance().getViewFactory().getSellerSelectedMenuItem().addListener(listener);
    }
}
