package com.salon.Server.Handlers;

import com.salon.Server.Services.Admin.AdminRequest;
import com.salon.Server.Services.Admin.Response.ManagerResponse;
import com.salon.Server.Services.Admin.Response.Profile;
import com.salon.Server.Services.Admin.Response.SellerResponse;
import com.salon.Server.Services.Export.Manager;
import com.salon.Server.Services.Export.Product;
import com.salon.Server.Services.Export.Seller;
import com.salon.Server.Services.Product.ProductsResponse;

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
                    List<Seller> sellers = SellerResponse.takeAllSellers();
                    out.writeObject(sellers);
                    break;
                }

                case "AddSeller"->{
                    out.writeObject(SellerResponse.addSeller(request.getSeller()));
                    break;
                }
                case "DelSeller"->{
                    SellerResponse.delSeller(request.getName());
                    break;
                }
                case "AllManagers"->{
                    List<Manager> managers = ManagerResponse.takeAllManagers();
                    out.writeObject(managers);
                    break;
                }
                case "AddManager"->{
                    out.writeObject(ManagerResponse.addManager(request.getManager()));
                    break;
                }
                case "DelManager"->{
                    ManagerResponse.delManager(request.getName());
                    break;
                }
                case "AllProducts"->{
                    List<Product> products = ProductsResponse.takeAllProducts();
                    List<String> cat = Product.getCategories();

                    out.writeObject(products);
                    break;
                }

                case "AllCategories"->{
                    List<String> cat = Product.getCategories();
                    out.writeObject(cat);
                }
                case "AddProduct"->{
                    out.writeObject(ProductsResponse.addProduct(request.getProduct()));
                    break;
                }
                case "DelProduct"->{
                    ProductsResponse.deleteProduct(request.getName());
                    break;
                }
                case "EditProduct"->{
                    ProductsResponse.editProduct(request.getProduct());
                    request.setProduct(null);
                }
                case "GetPhoto"->{
                    out.writeObject(Profile.getPhoto(request.getName()));
                    break;
                }
                case "SetPhoto"->{
                    Profile.updateUserPhoto(request.getName(),request.getPhotoPath());
                }
                case "ChangePassword"->{
                    out.writeObject(Profile.changePassword(request.getName(),request.getPassword(),request.getNewPassword()));
                }
                case "Exit"->{
                    exit = true;
                    break;
                }
            }
        }

    }
}
