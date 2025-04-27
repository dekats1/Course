package com.salon.Server.Handlers;

import com.salon.Server.Services.Export.Product;
import com.salon.Server.Services.Manager.ManagerRequest;
import com.salon.Server.Services.Manager.Service.ProfileService;
import com.salon.Server.Services.Product.ProductsService;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;

public class ManagerHandler extends RoleHandler {


    public ManagerHandler(Socket userSocket, ObjectInputStream in, ObjectOutputStream out) {
        super(userSocket, in, out);
    }

    @Override
    public void handle() throws IOException, ClassNotFoundException {
        while (!exit) {
            ManagerRequest request = (ManagerRequest) in.readObject();
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
                case "GetPhoto"->{
                    out.writeObject(ProfileService.getPhoto(request.getUserName()));
                    break;
                }
                case "SetPhoto"->{
                    ProfileService.updateUserPhoto(request.getUserName(),request.getPhotoPath());
                }
                case "ChangePassword"->{
                    out.writeObject(ProfileService.changePassword(request.getUserName(),request.getPassword(),request.getNewPassword()));
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
