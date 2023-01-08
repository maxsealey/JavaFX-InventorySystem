package sealey.javafxinventorysystem.models;

import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Inventory {
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();

    // Getters and Setters
    public static ObservableList<Part> getAllParts() {
        return allParts;
    }
    public static void setAllParts(ObservableList<Part> allParts) {
        Inventory.allParts = allParts;
    }
    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }
    public static void setAllProducts(ObservableList<Product> allProducts) {
        Inventory.allProducts = allProducts;
    }

    // Static Methods to add to ObservableLists
    public static void addPart(Part part) {
        allParts.add(part);
    }
    public static void addProduct(Product product) {
        allProducts.add(product);
    }
}
