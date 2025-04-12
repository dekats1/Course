package com.jms.salon.Models;

import javafx.beans.property.*;

public class Product {
    private final StringProperty name;
    private final StringProperty description;
    private final StringProperty category;
    private final DoubleProperty price;
    private final DoubleProperty cost;
    private final IntegerProperty quantity;

    public Product(String name, String description, String category,
                   double price, double cost, int quantity) {
        this.name = new SimpleStringProperty(name);
        this.description = new SimpleStringProperty(description);
        this.category = new SimpleStringProperty(category);
        this.price = new SimpleDoubleProperty(price);
        this.cost = new SimpleDoubleProperty(cost);
        this.quantity = new SimpleIntegerProperty(quantity);
    }

    // Геттеры и свойства
    public String getName() { return name.get(); }
    public StringProperty nameProperty() { return name; }

    public String getDescription() { return description.get(); }
    public StringProperty descriptionProperty() { return description; }

    public String getCategory() { return category.get(); }
    public StringProperty categoryProperty() { return category; }

    public double getPrice() { return price.get(); }
    public DoubleProperty priceProperty() { return price; }

    public double getCost() { return cost.get(); }
    public DoubleProperty costProperty() { return cost; }

    public int getQuantity() { return quantity.get(); }
    public IntegerProperty quantityProperty() { return quantity; }
}