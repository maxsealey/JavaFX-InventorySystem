package sealey.javafxinventorysystem.models;

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

    public static Part lookupPart(int partID) {
        for(Part a : getAllParts()) {
            if(a.getId() == partID){ return a; }
        }
        return null;
    }
    public static Product lookupProduct(int prodID) {
        for(Product a : getAllProducts()) {
            if(a.getId() == prodID){ return a; }
        }
        return null;
    }
    public static ObservableList<Part> lookupPart(String partName) {
        ObservableList<Part> temp = FXCollections.observableArrayList();

        for(Part p : Inventory.getAllParts()) {
            if(p.getName().toLowerCase().contains(partName.toLowerCase()))
            {
                temp.add(p);
            }
        }
        return temp;
    }
    public static ObservableList<Product> lookupProduct(String productName) {
        ObservableList<Product> temp = FXCollections.observableArrayList();

        for(Product p : Inventory.getAllProducts()) {
            if(p.getName().toLowerCase().contains(productName.toLowerCase()))
            {
                temp.add(p);
            }
        }
        return temp;
    }
    public static void updatePart(Part part) {
        for(Part a : getAllParts()) {
            if(a.getId() == part.getId()) {
                a.setName(part.getName());
                a.setPrice(part.getPrice());
                a.setStock(part.getStock());
                a.setMax(part.getMax());
                a.setMin(part.getMin());
            }
        }
    }
    public static void updateProduct(Product product) {
        for(Product a : getAllProducts()) {
            if(a.getId() == product.getId()) {
                a.setName(product.getName());
                a.setPrice(product.getPrice());
                a.setStock(product.getStock());
                a.setMax(product.getMax());
                a.setMin(product.getMin());
            }
        }
    }
    public static boolean deletePart(Part part) {
        return Inventory.getAllParts().remove(part);
    }
    public static boolean deleteProduct(Product product) {
        return Inventory.getAllProducts().remove(product);
    }
}
