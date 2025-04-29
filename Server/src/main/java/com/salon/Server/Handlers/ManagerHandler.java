package com.salon.Server.Handlers;

import com.salon.Server.Services.Export.Product;
import com.salon.Server.Services.Export.ReportSale;
import com.salon.Server.Services.Export.Seller;
import com.salon.Server.Services.Manager.ManagerRequest;
import com.salon.Server.Services.Manager.Service.ProfileService;
import com.salon.Server.Services.Manager.Service.ReportHistoryService;
import com.salon.Server.Services.Manager.Service.ReportService;
import com.salon.Server.Services.Product.ProductsServiceForManager;
import com.salon.Server.Services.Seller.Service.HistoryService;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;
import java.util.stream.Collectors;

public class ManagerHandler extends RoleHandler {


    public ManagerHandler(Socket userSocket, ObjectInputStream in, ObjectOutputStream out) {
        super(userSocket, in, out);
    }

    @Override
    public void handle() throws IOException, ClassNotFoundException {
        while (!exit) {
            ManagerRequest request = (ManagerRequest) in.readObject();
            switch (request.getRequest()) {

                case "AllSellers"-> {
                    List<Seller> sellers = ReportService.takeAllSellers();
                    out.writeObject(sellers.stream().map(product -> product.getUserName()).collect(Collectors.toList()));
                    break;
                }

                case "AllProducts"->{
                    List<Product> products = ProductsServiceForManager.takeAllProducts();
                    out.writeObject(products);
                    break;
                }

                case "AllCategories"->{
                    List<String> cat = Product.getCategories();
                    out.writeObject(cat);
                    break;
                }
                case "SellerReport"->{
                    ReportSale reportSale = ReportService.sellerReport(request.getUserName(),request.getStartDate(),request.getEndDate());
                    out.writeObject(reportSale);
                    break;
                }
                case "ProductReport"->{
                    ReportSale reportSale = ReportService.productReport(request.getUserName(),request.getStartDate(),request.getEndDate());
                    out.writeObject(reportSale);
                    break;
                }
                case "SaveReport"->{
                    out.writeObject(ReportService.saveReport(request.getReportType().toString(),request.getErrorMassage(),request.getUserName()));
                }
                case "AddProduct"->{
                    out.writeObject(ProductsServiceForManager.addProduct(request.getProduct()));
                    break;
                }
                case "DelProduct"->{
                    ProductsServiceForManager.deleteProduct(request.getUserName());
                    break;
                }
                case "EditProduct"->{
                    ProductsServiceForManager.editProduct(request.getProduct());
                    //request.setProduct(null);
                }
                case "ReportsForUser"->{
                    out.writeObject(ReportHistoryService.getReportHistory(request.getUserName()));
                    break;
                }
                case "DeleteReport"->{
                    out.writeObject(ReportHistoryService.deleteReport(request.getId()));
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
