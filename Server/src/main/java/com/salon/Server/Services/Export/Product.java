package com.salon.Server.Services.Export;

import java.io.Serializable;
import java.util.List;

public class Product implements Serializable {
    private String name;
    private String description;
    private String category;
    private double price;
    private double cost;
    private int quantity;
    private static List<String> categories;

    public Product(String name, String description, String category,
                   double price, double cost, int quantity) {
        this.name = name;
        this.description = description;
        this.category = category;
        this.price = price;
        this.cost = cost;
        this.quantity = quantity;
    }


    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getCategory() {
        return category;
    }

    public double getPrice() {
        return price;
    }

    public double getCost() {
        return cost;
    }

    public int getQuantity() {
        return quantity;
    }

    public static List<String> getCategories() {
        return categories;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public static void setCategories(List<String> categories) {
        Product.categories = categories;
    }

    @Override
    public String toString() {
        return "Product{" + "name='" + name + '\'' + ", description='" + description + '\'' + ", category='" + category + '\'' + ", price=" + price + ", cost=" + cost + ", quantity=" + quantity + '}';
    }
}