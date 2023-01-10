package sealey.javafxinventorysystem.models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/*
* @author Max Sealey
*
* The Inventory class contains two ObservableLists, one to contain a list of all parts,
* and one to contain a list of all products. It also contains lookup, add, update, and
* delete methods for each list, and methods to get and set the entirety of each individual list.
* */

public class Inventory {
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();

    /*
    * The getAllParts() method returns the list of all parts in the inventory. "Getter" for
    * the allParts list.
    *
    * @return allParts List of parts in the inventory
    * */
    public static ObservableList<Part> getAllParts() {
        return allParts;
    }

    /*
     * The setAllParts() method accepts a list of Part objects and assigns its values to the allParts list attribute.
     * "Setter" for the private allParts list. Destructive, overwrites any data already in the inventory.
     *
     * @param allParts List of parts to be stored in the inventory
     * */
    public static void setAllParts(ObservableList<Part> allParts) {
        Inventory.allParts = allParts;
    }

    /*
     * The getAllProducts() method returns a list of all products in the inventory. "Getter" for
     * the allProducts list.
     *
     * @return allProducts List of products in the inventory
     * */
    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }

    /*
     * The setAllProducts() method accepts a list of Product objects and assigns its values to the allProducts list attribute.
     * "Setter" for the private allProducts list. Destructive, overwrites any data already in the inventory.
     *
     * @param allParts List of parts to be stored in the inventory
     * */
    public static void setAllProducts(ObservableList<Product> allProducts) {
        Inventory.allProducts = allProducts;
    }

    /*
    * The addPart() method takes in a Part object and adds it to the allParts list.
    *
    * @param part Part to be added to list
    * */
    public static void addPart(Part part) {
        allParts.add(part);
    }

    /*
    * The addProduct() method takes in a Product object and adds it to the allProducts list.
    *
    * @param product Product to be added to list
    * */
    public static void addProduct(Product product) {
        allProducts.add(product);
    }

    /*
    * lookupPart() is an overloaded method that takes in an integer and searches the Parts list for an object with the matching id number.
    *
    * @param partID Integer to compare with each object's id number
    * @return a Parts object with matching id number or null  (if not found)
    * */
    public static Part lookupPart(int partID) {
        for(Part a : getAllParts()) {
            if(a.getId() == partID){ return a; }
        }
        return null;
    }

    /*
     * lookupProduct() is an overloaded method that takes in an integer and searches the Products list for an object with the matching id number.
     *
     * @param prodID Integer to compare with each object's id number
     * @return a Product object with matching id number or null  (if not found)
     * */
    public static Product lookupProduct(int prodID) {
        for(Product a : getAllProducts()) {
            if(a.getId() == prodID){ return a; }
        }
        return null;
    }

    /*
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

    /*
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

    /*
    *
    *
    *
    *
    *
    *
    * */
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

    /*
     *
     *
     *
     *
     *
     *
     * */
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

    /*
    * The deletePart() method attempts to remove a part from the list and returns true if successful
    *
    * @param part Part object to be deleted
    * @return boolean True if successful, False if unsuccessful
    * */
    public static boolean deletePart(Part part) {
        return (Boolean)Inventory.getAllParts().remove(part);
    }

    /*
     * The deleteProduct() method attempts to remove a part from the list and returns true if successful
     *
     * @param part Part object to be deleted
     * @return boolean True if successful, False if unsuccessful
     * */
    public static boolean deleteProduct(Product product) {
        return (Boolean)Inventory.getAllProducts().remove(product);
    }
}
