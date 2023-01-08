package sealey.javafxinventorysystem.models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Product {
    private static ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;
    public Product(int id, String name, double price, int stock, int min, int max) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }
    // Getters and Setters
    public static ObservableList<Part> getAssociatedParts() {
        return associatedParts;
    }
    public static void setAssociatedParts(ObservableList<Part> associatedParts) {
        Product.associatedParts = associatedParts;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public double getPrice() {
        return price;
    }
    public void setPrice(double price) {
        this.price = price;
    }
    public int getStock() {
        return stock;
    }
    public void setStock(int stock) {
        this.stock = stock;
    }
    public int getMin() {
        return min;
    }
    public void setMin(int min) {
        this.min = min;
    }
    public int getMax() {
        return max;
    }
    public void setMax(int max) {
        this.max = max;
    }

    // Static methods to get, add to, and delete from associatedParts ObservableList
    public static ObservableList<Part> getAllAssociatedParts() {
        return associatedParts;
    }
    public static void addAssociatedPart(Part part) {
        associatedParts.add(part);
    }
    public static boolean deleteAssociatedPart(Part selectedAssociatedPart) {
        return associatedParts.remove(selectedAssociatedPart);
    }
}