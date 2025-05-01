package com.salon.Server.Handlers;

import com.salon.Server.Services.Export.Product;
import com.salon.Server.Services.Export.Sale;
import com.salon.Server.Services.Product.ProductsService;
import com.salon.Server.Services.Seller.Service.HistoryService;
import com.salon.Server.Services.Seller.Service.ProfileService;
import com.salon.Server.Services.Seller.Service.SaleService;
import com.salon.Server.Services.Seller.SellerRequest;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;

public class SellerHandler extends RoleHandler {

    public SellerHandler(Socket userSocket, ObjectInputStream in, ObjectOutputStream out) {
        super(userSocket, in, out);
    }

    public void handle() throws IOException, ClassNotFoundException {
        while (!exit) {
            SellerRequest request = (SellerRequest) in.readObject();
            switch (request.getRequest()) {

                case "AllProducts" -> {
                    List<Product> products = ProductsService.takeAllProducts();
                    out.writeObject(products);
                    break;
                }
                case "AllCategories" -> {
                    List<String> cat = Product.getCategories();
                    out.writeObject(cat);
                    break;
                }
                case "MakeSale" ->{
                    out.writeObject(SaleService.makeSale(request.getUserName(),request.getProduct(),request.getQuantity(), request.getOwnerName()));
                    break;
                }
                case "HistorySales"->{
                    List<Sale> sales = HistoryService.takeHistorySales(request.getUserName());
                    out.writeObject(sales);
                    break;
                }
                case "GetPhoto"->{
                    out.writeObject(ProfileService.getPhoto(request.getUserName()));
                    break;
                }
                case "SetPhoto"->{
                   ProfileService.updateUserPhoto(request.getUserName(),request.getPhotoPath(), request.getOwnerName());
                }
                case "ChangePassword"->{
                    out.writeObject(ProfileService.changePassword(request.getUserName(),request.getPassword(),request.getNewPassword(), request.getOwnerName()));
                }
                case "UserData"->{
                    out.writeObject(ProfileService.userData(request.getUserName()));
                }
                case "Exit" -> {
                    exit = true;
                    break;
                }
            }
        }
    }
}
