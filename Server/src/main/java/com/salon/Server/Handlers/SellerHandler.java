package com.salon.Server.Handlers;

import com.salon.Server.Services.Admin.AdminRequest;
import com.salon.Server.Services.Admin.Response.ManagerResponse;
import com.salon.Server.Services.Admin.Response.Profile;
import com.salon.Server.Services.Admin.Response.SellerResponse;
import com.salon.Server.Services.Export.Manager;
import com.salon.Server.Services.Export.Product;
import com.salon.Server.Services.Export.Seller;
import com.salon.Server.Services.Product.ProductsResponse;
import com.salon.Server.Services.Seller.Response.Sell;
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
                    List<Product> products = ProductsResponse.takeAllProducts();

                    out.writeObject(products);
                    break;
                }
                case "AllCategories" -> {
                    List<String> cat = Product.getCategories();
                    out.writeObject(cat);
                    break;
                }
                case "MakeSale" ->{
                    out.writeObject(Sell.makeSale(request.getUserName(),request.getProduct(),request.getQuantity()));
                    break;
                }
                case "Exit" -> {
                    exit = true;
                    break;
                }
            }
        }
    }
}
