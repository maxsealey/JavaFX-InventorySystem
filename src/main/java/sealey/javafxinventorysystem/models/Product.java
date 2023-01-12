package sealey.javafxinventorysystem.models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * The Product class contains the attributes of each product, including a list of all associated parts.
 * @author Max Sealey
 * */
public class Product {

    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;

    /**
     * Product class constructor, called when a Product object is created. Assigns values.
     * @param id id
     * @param name name
     * @param price price
     * @param stock stock
     * @param min min
     * @param max max
     */
    public Product(int id, String name, double price, int stock, int min, int max) {

        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }
    /**
     * Default constructor
     * */

    public Product(){

    }

    /**
     * @return id the id to get
     */
    public int getId() {

        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {

        this.id = id;
    }

    /**
     * @return name the name to get
     */
    public String getName() {

        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {

        this.name = name;
    }

    /**
     * @return price the price to get
     */
    public double getPrice() {

        return price;
    }

    /**
     * @param price the price to set
     */
    public void setPrice(double price) {

        this.price = price;
    }

    /**
     * @return stock the stock to get
     */
    public int getStock() {

        return stock;
    }

    /**
     * @param stock the stock to set
     */
    public void setStock(int stock) {

        this.stock = stock;
    }

    /**
     * @return min the min to get
     */
    public int getMin() {

        return min;
    }

    /**
     * @param min the min to set
     */
    public void setMin(int min) {

        this.min = min;
    }

    /**
     * @return max the max to get
     */
    public int getMax() {

        return max;
    }

    /**
     * @param max the max to set
     */
    public void setMax(int max) {

        this.max = max;
    }

    /**
    * @return associatedParts The entire list of parts associated with the product
    * */
    public ObservableList<Part> getAllAssociatedParts() {

        return associatedParts;
    }

    /**
     * @param newPartsList Assigns the entire list of parts associated with the product
     * */
    public void setAllAssociatedParts(ObservableList<Part> newPartsList) {

        associatedParts = newPartsList;
    }

    /**
    * The addAssociatedPart() method takes in a part object and adds it to the list of associated parts.
    *
    * @param part Part object to be added to list
    * */
    public void addAssociatedPart(Part part) {

        associatedParts.add(part);
    }

    /**
    * The deleteAssociatedPart() method takes in the selected part object and attempts to remove it from
    * the list, returning true if successful.
    *
    * @param selectedAssociatedPart Part object to be deleted
    * @return boolean True if successful, False if unsuccessful
    * */
    public boolean deleteAssociatedPart(Part selectedAssociatedPart) {

        return associatedParts.remove(selectedAssociatedPart);
    }
}