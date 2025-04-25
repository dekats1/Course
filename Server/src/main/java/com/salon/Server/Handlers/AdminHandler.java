package com.salon.Server.Handlers;

import com.salon.Server.Services.Admin.AdminRequest;
import com.salon.Server.Services.Admin.Service.ManagerService;
import com.salon.Server.Services.Admin.Service.ProfileService;
import com.salon.Server.Services.Admin.Service.SellerService;
import com.salon.Server.Services.Export.Manager;
import com.salon.Server.Services.Export.Product;
import com.salon.Server.Services.Export.Seller;
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
                    out.writeObject(SellerService.addSeller(request.getSeller()));
                    break;
                }
                case "DelSeller"->{
                    SellerService.delSeller(request.getName());
                    break;
                }
                case "AllManagers"->{
                    List<Manager> managers = ManagerService.takeAllManagers();
                    out.writeObject(managers);
                    break;
                }
                case "AddManager"->{
                    out.writeObject(ManagerService.addManager(request.getManager()));
                    break;
                }
                case "DelManager"->{
                    ManagerService.delManager(request.getName());
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
                    out.writeObject(ProductsService.addProduct(request.getProduct()));
                    break;
                }
                case "DelProduct"->{
                    ProductsService.deleteProduct(request.getName());
                    break;
                }
                case "EditProduct"->{
                    ProductsService.editProduct(request.getProduct());
                    request.setProduct(null);
                }
                case "GetPhoto"->{
                    out.writeObject(ProfileService.getPhoto(request.getName()));
                    break;
                }
                case "SetPhoto"->{
                    ProfileService.updateUserPhoto(request.getName(),request.getPhotoPath());
                }
                case "ChangePassword"->{
                    out.writeObject(ProfileService.changePassword(request.getName(),request.getPassword(),request.getNewPassword()));
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
