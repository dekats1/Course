package com.salon.Server.Handlers;

import com.salon.Server.Services.Admin.AdminRequest;
import com.salon.Server.Services.Admin.Service.ManagerService;
import com.salon.Server.Services.Admin.Service.ProfileService;
import com.salon.Server.Services.Admin.Service.SellerService;
import com.salon.Server.Services.Export.Manager;
import com.salon.Server.Services.Export.Product;
import com.salon.Server.Services.Export.Seller;
import com.salon.Server.Services.LogService;
import com.salon.Server.Services.Product.ProductsService;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;

public class AdminHandler extends RoleHandler {
    public AdminHandler(Socket clientSocket, ObjectInputStream in, ObjectOutputStream out) {
        super(clientSocket,in,out);
    }

    @Override
    public void handle() throws IOException, ClassNotFoundException {
        while (!exit) {
            AdminRequest request = (AdminRequest) in.readObject();
            switch (request.getRequest()) {
                case "AllSellers"-> {
                    List<Seller> sellers = SellerService.takeAllSellers();
                    out.writeObject(sellers);
                    break;
                }

                case "AddSeller"->{
                    out.writeObject(SellerService.addSeller(request.getSeller(), request.getOwnerName()));
                    break;
                }
                case "DelSeller"->{
                    SellerService.delSeller(request.getName(), request.getOwnerName());
                    break;
                }
                case "AllManagers"->{
                    List<Manager> managers = ManagerService.takeAllManagers();
                    out.writeObject(managers);
                    break;
                }
                case "AddManager"->{
                    out.writeObject(ManagerService.addManager(request.getManager(), request.getOwnerName()));
                    break;
                }
                case "DelManager"->{
                    ManagerService.delManager(request.getName(), request.getOwnerName());
                    break;
                }
                case "AllProducts"->{
                    List<Product> products = ProductsService.takeAllProducts();
                    List<String> cat = Product.getCategories();

                    out.writeObject(products);
                    break;
                }

                case "AllCategories"->{
                    List<String> cat = Product.getCategories();
                    out.writeObject(cat);
                }
                case "AddProduct"->{
                    out.writeObject(ProductsService.addProduct(request.getProduct(), request.getOwnerName()));
                    break;
                }
                case "DelProduct"->{
                    ProductsService.deleteProduct(request.getName(), request.getOwnerName());
                    break;
                }
                case "EditProduct"->{
                    ProductsService.editProduct(request.getProduct(), request.getOwnerName());
                    request.setProduct(null);
                }
                case "GetLogs"->{
                    out.writeObject(LogService.getLogs());
                }
                case "GetPhoto"->{
                    out.writeObject(ProfileService.getPhoto(request.getName()));
                    break;
                }
                case "SetPhoto"->{
                    ProfileService.updateUserPhoto(request.getName(),request.getPhotoPath(), request.getOwnerName());
                }
                case "ChangePassword"->{
                    out.writeObject(ProfileService.changePassword(request.getName(),request.getPassword(),request.getNewPassword(), request.getOwnerName()));
                }
                case "UserData"->{
                    out.writeObject(ProfileService.userData(request.getName()));
                }
                case "Exit"->{
                    exit = true;
                    break;
                }
            }
        }

    }
}
