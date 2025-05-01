package com.salon.Server.Handlers;

import com.salon.Server.Services.Export.Product;
import com.salon.Server.Services.Export.ReportSale;
import com.salon.Server.Services.Export.Seller;
import com.salon.Server.Services.Manager.ManagerRequest;
import com.salon.Server.Services.Manager.Service.ProfileService;
import com.salon.Server.Services.Manager.Service.ReportHistoryService;
import com.salon.Server.Services.Manager.Service.ReportService;
import com.salon.Server.Services.Manager.Service.StatisticsService;
import com.salon.Server.Services.Product.ProductsServiceForManager;

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
                    out.writeObject(ReportService.saveReport(request.getReportType().toString(), request.getErrorMassage(), request.getUserName(), request.getOwnerName()));
                }
                case "AddProduct"->{
                    out.writeObject(ProductsServiceForManager.addProduct(request.getProduct(), request.getOwnerName()));
                    break;
                }
                case "DelProduct"->{
                    ProductsServiceForManager.deleteProduct(request.getUserName(), request.getOwnerName());
                    break;
                }
                case "EditProduct"->{
                    ProductsServiceForManager.editProduct(request.getProduct(), request.getOwnerName());
                    //request.setProduct(null);
                }
                case "ReportsForUser"->{
                    out.writeObject(ReportHistoryService.getReportHistory(request.getUserName()));
                    break;
                }
                case "GetStatistics" -> {
                    out.writeObject(StatisticsService.takeProductByCount());
                    out.writeObject(StatisticsService.takeProductByProfit());
                    out.writeObject(StatisticsService.takeBestSellerByCount());
                }
                case "DeleteReport"->{
                    out.writeObject(ReportHistoryService.deleteReport(request.getId(), request.getOwnerName()));
                }
                case "GetPhoto"->{
                    out.writeObject(ProfileService.getPhoto(request.getUserName()));
                    break;
                }
                case "SetPhoto"->{
                    ProfileService.updateUserPhoto(request.getUserName(), request.getPhotoPath(), request.getOwnerName());
                }
                case "ChangePassword"->{
                    out.writeObject(ProfileService.changePassword(request.getUserName(), request.getPassword(), request.getNewPassword(), request.getOwnerName()));
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
