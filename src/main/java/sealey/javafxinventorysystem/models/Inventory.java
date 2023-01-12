package sealey.javafxinventorysystem.models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * The Inventory class contains a list of all products and parts in the inventory, methods to
 * retrieve and set the lists; and methods to update, add, lookup, and delete individual parts and products.
 *
 * @author Max Sealey
 * */

public class Inventory {

    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();

    /**
    * The getAllParts() method returns the list of all parts in the inventory. "Getter" for
    * the allParts list attribute.
    *
    * @return allParts List of parts in the inventory
    * */
    public static ObservableList<Part> getAllParts() {

        return allParts;
    }

    /**
     * The setAllParts() method accepts a list of Part objects and assigns its values to the allParts list attribute.
     * "Setter" for the allParts list attribute.
     *
     * @param allParts List of parts to be stored in the inventory
     * */
    public static void setAllParts(ObservableList<Part> allParts) {

        Inventory.allParts = allParts;
    }

    /**
     * The getAllProducts() method returns a list of all products in the inventory. "Getter" for
     * the allProducts list attribute.
     *
     * @return allProducts List of products in the inventory
     * */
    public static ObservableList<Product> getAllProducts() {

        return allProducts;
    }

    /**
     * The setAllProducts() method accepts a list of Product objects and assigns its values to the allProducts list attribute.
     * "Setter" for the allProducts list attribute.
     *
     * @param allProducts List of products to be stored in the inventory
     * */
    public static void setAllProducts(ObservableList<Product> allProducts) {

        Inventory.allProducts = allProducts;
    }

    /**
    * The addPart() method takes in a Part object and adds it to the allParts list.
    *
    * @param newPart Part to be added to list
    * */
    public static void addPart(Part newPart) {

        allParts.add(newPart);
    }

    /**
    * The addProduct() method takes in a Product object and adds it to the allProducts list.
    *
    * @param newProduct Product to be added to list
    * */
    public static void addProduct(Product newProduct) {

        allProducts.add(newProduct);
    }

    /**
    * lookupPart() is an overloaded method that takes in an integer and searches the Parts list for an object with the matching id number.
    *
    * @param partId Integer to compare with each object's id number
    * @return a Parts object with matching id number or null (if not found)
    * */
    public static Part lookupPart(int partId) {

        for(Part a : getAllParts()) {
            if(a.getId() == partId){ return a; }
        }
        return null;
    }

    /**
     * lookupProduct() is an overloaded method that takes in an integer and searches the Products list for an object with the matching id number.
     *
     * @param productId Integer to compare with each object's id number
     * @return a Product object with matching id number or null  (if not found)
     * */
    public static Product lookupProduct(int productId) {

        for(Product a : getAllProducts()) {
            if(a.getId() == productId){ return a; }
        }
        return null;
    }

    /**
    * lookupPart() ia an overloaded method that searches through the list of Parts and returns a list of all objects
    * containing the search term. Not case-sensitive
    *
    * @param partName search term to look for in each Part's name
    * @return temp List of parts where the search term is contained in the part's name
    * */
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

    /**
     * lookupProduct() ia an overloaded method that searches through the list of Products and returns a list of all objects
     * containing the search term. Not case-sensitive
     *
     * @param productName Search term to look for in each Product's name
     * @return temp List of products where the search term is contained in the product's name
     * */
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

    /**
    * updatePart() takes in the updated part and its index, then replaces the original part
    *
    * @param index Int index of part to be modified
    * @param selectedPart Part object (either InHouse or Outsourced) with updated data
    * */
    public static void updatePart(int index, Part selectedPart) {
        allParts.set(index, selectedPart);
    }

    /**
     * updateProduct() takes in the updated product and its index, then replaces the original product
     *
     * @param index Integer index of part to be modified
     * @param newProduct Product object with updated data
     * */
    public static void updateProduct(int index, Product newProduct) {
        allProducts.set(index, newProduct);
    }

    /**
    * The deletePart() method attempts to remove a part from the list and returns true if successful
    *
    * @param selectedPart Part object to be deleted
    * @return boolean True if successful, False if unsuccessful
    * */
    public static boolean deletePart(Part selectedPart) {

        return Inventory.getAllParts().remove(selectedPart);
    }

    /**
     * The deleteProduct() method attempts to remove a product from the list and returns true if successful
     *
     * @param selectedProduct Part object to be deleted
     * @return boolean True if successful, False if unsuccessful
     * */
    public static boolean deleteProduct(Product selectedProduct) {

        return Inventory.getAllProducts().remove(selectedProduct);
    }
}
