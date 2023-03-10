package sealey.javafxinventorysystem.utility;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import sealey.javafxinventorysystem.models.*;

/**
 * The Helpers class contains a variety of methods used in the controller classes.
 *
 * @author Max Sealey
 */

public class Helpers {

    /**
     * Helper function that returns a boolean indicating whether an integer is already being used as an ID number
     * This is only called in the generateID() method to ensure that the auto-generated ID is unique.
     *
     * @param id Integer to be checked for ID uniqueness
     * @return boolean True if ID belongs to existing part, False otherwise
     */
    private static boolean search(int id) {

        for (Part p : Inventory.getAllParts()) {
            if (p.getId() == id) {
                return true;
            }
        }
        return false;
    }

    /**
     * The generateID() method is a helper function that generates an ID number for created part. Always returns the next positive available ID
     *
     * @return id Unique part ID
     * @return boolean true for part, false for product
     */
    public static int generateID(boolean partOrProduct) {

        int id = 1;

        if (partOrProduct) {
            for (Part a : Inventory.getAllParts()) {
                if (search(id)) {
                    id++;
                } else {
                    return id;
                }
            }
        } else {
            for (Product a : Inventory.getAllProducts()) {
                if (search(id)) {
                    id++;
                } else {
                    return id;
                }
            }
        }
        return id;
    }

    /**
     * The isInt() method checks whether a provided string can be converted to an integer and returns a boolean.
     *
     * @param str The string to be checked
     * @return boolean Returns true if string is also an integer, false if exception is caught
     */
    public static boolean isInt(String str) {

        try {
            Integer.valueOf(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * User input validation for Inventory level, max, and min values
     *
     * @param min   minimum inventory level
     * @param max   maximum inventory level
     * @param stock current inventory level
     * @return boolean True if min is positive and less than stock, and stock is less than max
     */
    public static boolean checkStockValues(int min, int max, int stock) {

        return max > stock && min < stock && min >= 1;
    }

    /**
     * Populates table with data from each of the parts that are passed in. Table1 should always contain a list of the
     * parts in inventory, Table 2 a list of parts associated with the product.
     *
     * @param parts    List of parts to be represented in each row
     * @param table    Table
     * @param idCol    ID column
     * @param nameCol  Name column
     * @param stockCol Stock column
     * @param priceCol Price column
     */
    public static void setPartTables(ObservableList<Part> parts, TableView<Part> table, TableColumn<Part, Integer> idCol, TableColumn<Part, String> nameCol, TableColumn<Part, Integer> stockCol, TableColumn<Part, Double> priceCol) {
        table.setItems(parts);

        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        stockCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

    /**
     * Populates Product table in Main Window with data from each of the parts that are passed in
     *
     * @param products List of parts to be represented in each row
     * @param table    Table
     * @param idCol    ID column
     * @param nameCol  Name column
     * @param stockCol Stock column
     * @param priceCol Price column
     */
    public static void setProductTable(ObservableList<Product> products, TableView<Product> table, TableColumn<Product, Integer> idCol, TableColumn<Product, String> nameCol, TableColumn<Product, Integer> stockCol, TableColumn<Product, Double> priceCol) {
        table.setItems(products);

        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        stockCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

    /**
     * Sets data to a product
     *
     * @param updatedProduct List of parts to be represented in each row
     * @param id id
     * @param name name
     * @param stock stock
     * @param price price
     * @param min min
     * @param max max
     */
    public static void setProduct(Product updatedProduct, int id, String name, int stock, double price, int min, int max) {
        updatedProduct.setId(id);
        updatedProduct.setName(name);
        updatedProduct.setStock(stock);
        updatedProduct.setPrice(price);
        updatedProduct.setMin(min);
        updatedProduct.setMax(max);
    }

/**
 * Sets data to a product from another product
 *
 * @param updatedProduct product that receives data
 * @param oldProduct product that provides data
 */
    public static void setProduct(Product updatedProduct, Product oldProduct) {
        updatedProduct.setId(oldProduct.getId());
        updatedProduct.setName(oldProduct.getName());
        updatedProduct.setStock(oldProduct.getStock());
        updatedProduct.setPrice(oldProduct.getPrice());
        updatedProduct.setMin(oldProduct.getMin());
        updatedProduct.setMax(oldProduct.getMax());
    }

    /**
     * The filterProducts() method checks a string and returns a list of Products whose name contains the string, and/or whose ID is equal to the string.
     *
     * @return ObservableList of Products containing all products whose name contains the search parameter
     * and/or whose id is equal to the search parameter, or list of all Products.
     * */
    public static ObservableList<Product> filterProducts(String search) {

        ObservableList<Product> temp =  Inventory.lookupProduct(search);

        if(Helpers.isInt(search)){
            Product a = Inventory.lookupProduct(Integer.parseInt(search));
            if(a != null){
                temp.add(a);
            }
        }

        if(search.isEmpty()){
            return Inventory.getAllProducts();
        }
        else if (temp.isEmpty()) {
            Alerts.noResults();
            return Inventory.getAllProducts();
        } else {
            return temp;
        }
    }

    /**
     * The filterParts() method checks a string and returns a list of Parts whose name contains the string, and/or whose ID is equal to the string.
     *
     * @return ObservableList of Parts containing all parts whose name contains the search parameter
     * and/or whose id is equal to the search parameter, or list of all Parts.
     * */
    public static ObservableList<Part> filterParts(String search) {

        ObservableList<Part> temp = Inventory.lookupPart(search);

        if(Helpers.isInt(search)){
            Part a = Inventory.lookupPart(Integer.parseInt(search));
            if(a != null){
                temp.add(a);
            }
        }

        if(search.isEmpty()){
            return Inventory.getAllParts();
        }
        else if (temp.isEmpty()) {
            Alerts.noResults();
            return Inventory.getAllParts();
        } else {
            return temp;
        }
    }

    public static void testData() {

        OutSourced part1 = new OutSourced(1, "wheel", 34.99, 16, 5, 30);
        OutSourced part2 = new OutSourced(2, "brake", 29.99, 18, 10, 25);
        OutSourced part3 = new OutSourced(3, "headlight", 49.99, 18, 10, 25);
        InHouse part4 = new InHouse(4, "handlebars", 19.99, 8, 5, 15);
        InHouse part5 = new InHouse(5, "chain", 64.99, 9, 7, 20);
        InHouse part6 = new InHouse(6, "steering wheel", 54.99, 9, 7, 20);

        Product prod1 = new Product(1, "Bicycle", 149.99, 12, 8, 20);
        Product prod2 = new Product(2, "Unicycle", 134.99, 6, 3, 10);
        Product prod3 = new Product(3, "Kid's Trike", 89.99, 15, 8, 30);

        part1.setCompanyName("Headgum");
        part2.setCompanyName("OMSB");
        part3.setCompanyName("Zona Gale");
        part4.setMachineId(87);
        part5.setMachineId(32);
        part6.setMachineId(99);

        prod1.addAssociatedPart(part4);
        prod1.addAssociatedPart(part3);

        prod2.addAssociatedPart(part3);
        prod2.addAssociatedPart(part2);
        prod2.addAssociatedPart(part5);
        prod3.addAssociatedPart(part2);
        prod3.addAssociatedPart(part1);
        prod3.addAssociatedPart(part3);
        prod3.addAssociatedPart(part5);
        prod3.addAssociatedPart(part6);

        ObservableList<Part> parts = FXCollections.observableArrayList(part1,part2,part3,part4,part5,part6);
        Inventory.setAllParts(parts);

        ObservableList<Product> products = FXCollections.observableArrayList(prod1,prod2,prod3);
        Inventory.setAllProducts(products);
    }
}

