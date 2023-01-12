package sealey.javafxinventorysystem;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import sealey.javafxinventorysystem.models.*;

import java.io.IOException;
import java.net.URL;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

/*
 * @author Max Sealey
 *
 * The ModifyProduct controller controls the components used to edit the values and associated parts of the selected product.
 * */

public class ModifyProduct implements Initializable {

    Stage stage;
    Parent scene;

    @FXML
    private Button addButton;

    @FXML
    private Button cancelButton;

    @FXML
    private TextField inventoryText;

    @FXML
    private TextField maxText;

    @FXML
    private TextField minText;

    @FXML
    private TextField priceText;

    @FXML
    private TextField productIDText;

    @FXML
    private TextField productNameText;

    @FXML
    private Button removeButton;

    @FXML
    private Button saveButton;

    @FXML
    private TextField searchProductText;

    @FXML
    private TableView<Part> table1;

    @FXML
    private TableColumn<Part, Integer> table1IDCol;

    @FXML
    private TableColumn<Part, Integer> table1InvCol;

    @FXML
    private TableColumn<Part, String> table1NameCol;

    @FXML
    private TableColumn<Part, Double> table1PriceCol;

    @FXML
    private TableView<Part> table2;

    @FXML
    private TableColumn<Part, Integer> table2IDCol;

    @FXML
    private TableColumn<Part, Integer> table2InvCol;

    @FXML
    private TableColumn<Part, String> table2NameCol;

    @FXML
    private TableColumn<Part, Double> table2PriceCol;

    Product updatedProduct = new Product();


    /*
    * Displays alert asking for confirmation that item should be deleted, returns true if Ok button clicked, false otherwise
    *
    * @return boolean true or false
    * */
    boolean confirmation(){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Confirm Part Removal");
        alert.setContentText("Click 'Ok' to proceed.");
        alert.setHeaderText("Are you sure you want to remove this part from the product?");

        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == ButtonType.OK) {
            return true;
        } else {
            return false;
        }
    }

    /*
    * Simple error message alert that takes in message and displays warning
    *
    * @param content Message to be displayed in alert
    * */
    void errorMessage(String content){

        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setContentText(content);
        alert.setHeaderText("Something went wrong.");
        alert.showAndWait();
    }

    /*
    * Overloaded errorMessage method that sets title, content, and alert type
    *
    * @param title Alert title
    * @param content Alert message
    * @param type Alert type
    * */
    void errorMessage(String title, String content, Alert.AlertType type) {

        Alert alert = new Alert(Alert.AlertType.NONE);
        alert.setAlertType(type);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }

    /*
     * The isInt() method checks whether a provided string can be converted to an integer and returns a boolean.
     *
     * @param str The string to be checked
     * @return boolean Returns true if string is also an integer, false if exception is caught
     */
    boolean isInt(String str) {

        try {
            Integer.valueOf(str);
            return true;
        } catch (NumberFormatException e) { return false; }
    }

    /*
     * User input validation for Inventory level, max, and min values
     *
     * @param min minimum inventory level
     * @param max maximum inventory level
     * @param stock current inventory level
     *
     * @return boolean True if min is positive and less than stock, and stock is less than max
     * */
    boolean checkStockValues(int min, int max, int stock){

        return max > stock && min < stock && min >= 1;
    }

    /*
     * The filterParts() method checks a string and returns a list of Parts whose name contains the string, and/or whose ID is equal to the string.
     *
     * @return ObservableList of Parts containing all parts whose name contains the search parameter
     * and/or whose id is equal to the search parameter, or list of all Parts.
     * */
    private ObservableList<Part> filterParts(){

        String search = searchProductText.getText();
        ObservableList<Part> temp = Inventory.lookupPart(search);

        if(isInt(search)){
            Part a = Inventory.lookupPart(Integer.parseInt(search));
            if(a != null){
                temp.add(a);
            }
        }

        if(search.isEmpty()){
            return Inventory.getAllParts();
        }
        else if (temp.isEmpty()) {
            errorMessage("404","Your search did not match any results. Please try again.", Alert.AlertType.INFORMATION);
            return Inventory.getAllParts();
        } else {
            return temp;
        }
    }

    /*
     * Event handler that sets scene back to MainWindow when the cancel button is clicked
     *
             * @param event Cancel button event
     * @throws IOException IOException
     * */
    @FXML
    void onActionCancel(ActionEvent event) throws IOException {

        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("MainWindow.fxml")));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /*
     * Event handler that checks user input for validity and then updates the product in inventory
     *
     * @param event Save button event
     * @throws IOException IOException
     * */
    @FXML
    void onActionSave(ActionEvent event) throws IOException {

        try {
            int id = Integer.parseInt(productIDText.getPromptText());
            String name = productNameText.getText();
            double price = Double.parseDouble(priceText.getText());
            int inv = Integer.parseInt(inventoryText.getText());
            int min = Integer.parseInt(minText.getText());
            int max = Integer.parseInt(maxText.getText());

            try {
                if(!checkStockValues(min,max,inv)){
                    throw new NumberFormatException();
                } else {
                    try {
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setTitle("Confirm Save");
                        alert.setContentText("Click 'Ok' to save changes.");
                        alert.setHeaderText("Are you sure you want to save?");

                        Optional<ButtonType> result = alert.showAndWait();

                        if (result.get() == ButtonType.OK) {
                            updatedProduct.setId(id);
                            updatedProduct.setName(name);
                            updatedProduct.setStock(inv);
                            updatedProduct.setPrice(price);
                            updatedProduct.setMin(min);
                            updatedProduct.setMax(max);

                            updatedProduct.setAllAssociatedParts(updatedProduct.getAllAssociatedParts());
                            Inventory.updateProduct(id-1,updatedProduct);

                            stage = (Stage)((Button)event.getSource()).getScene().getWindow();
                            scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("MainWindow.fxml")));
                            stage.setScene(new Scene(scene));
                            stage.setTitle("Inventory Management System");
                            stage.show();
                        }
                    } catch (NoSuchElementException ignored) {
                    }
                }
            } catch(NumberFormatException e) {
                errorMessage("Invalid Input","Inventory Level must be between Min and Max", Alert.AlertType.ERROR);
                errorMessage("Invalid Input","Please enter only valid values the boxes. " +
                        "Inventory Level, Min, Max, and (if applicable) Machine ID must be whole numbers.", Alert.AlertType.ERROR);
            }

        } catch(NumberFormatException e) {
            errorMessage("Invalid Input","Please enter only valid values in each box. " +
                    "Inventory Level, Min, Max, and (if applicable) Machine ID must be whole numbers.", Alert.AlertType.ERROR);
        }
    }

    /*
    * When an item in the top table is selected and the add button is clicked, this event handler will fire and add the item to the bottom table.
    *
    * @param event Add button event
    * @throws IOException IOException
    * */
    @FXML
    void onActionAdd(ActionEvent event) throws IOException {

        if(!table1.getSelectionModel().isEmpty()) {
            updatedProduct.addAssociatedPart(table1.getSelectionModel().getSelectedItem());
            populateTable2(updatedProduct.getAllAssociatedParts());
        } else {
            errorMessage("Please select a part.");
        }
    }

    /*
    * If an item on the bottom table is selected and the remove button is clicked, this event
    * handler will fire and on user confirmation, remove the item from the bottom table.
    *
    * @param actionEvent Remove button event
    * @throws IOException IOException
    * */
    public void onActionRemove(ActionEvent actionEvent) throws IOException {

        boolean success = false;

        try {
            if(!table2.getSelectionModel().isEmpty())
            {
                if(confirmation()){
                    updatedProduct.deleteAssociatedPart(table2.getSelectionModel().getSelectedItem());
                    System.out.println("successful removal");
                    success = true;
                }
            }
            if(!success) {
                if(table2.getSelectionModel().isEmpty()){
                    errorMessage("Please select a part.");
                }
            }

        } catch (NoSuchElementException e) {
            errorMessage("Action canceled", "Nothing was removed.", Alert.AlertType.INFORMATION);
        }

    }

    /*
     * Used to receive data of product to be modified from MainWindow. Sets TextFields and updatedProduct Product object attributes
     *
     * @param product Product to be modified
     * */
    public void sendProduct(Product product){

        updatedProduct.setId(product.getId());
        updatedProduct.setName(product.getName());
        updatedProduct.setStock(product.getStock());
        updatedProduct.setPrice(product.getPrice());
        updatedProduct.setMin(product.getMin());
        updatedProduct.setMax(product.getMax());

        productIDText.setPromptText(String.valueOf(updatedProduct.getId()));
        productNameText.setText(updatedProduct.getName());
        inventoryText.setText(String.valueOf(updatedProduct.getStock()));
        priceText.setText(String.valueOf(updatedProduct.getPrice()));
        maxText.setText(String.valueOf(updatedProduct.getMax()));
        minText.setText(String.valueOf(updatedProduct.getMin()));

        if(!product.getAllAssociatedParts().isEmpty()){
            updatedProduct.setAllAssociatedParts(product.getAllAssociatedParts());
            populateTable2(updatedProduct.getAllAssociatedParts());
        }
    }

    /*
    * Populates table1 with data from each of the parts that are passed in. Table1 should always contain a list of the
    * parts in inventory.
    *
    * @param parts List of parts to be represented in each row
    * */
    private void populateTable1(ObservableList<Part> parts){

        table1.setItems(parts);

        table1IDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        table1NameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        table1InvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        table1PriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

    /*
     * Populates table2 with data from each of the parts that are passed in. Table2 should initially contain the
     * parts in the unmodified product, and update when parts then are added and removed
     *
     * @param parts List of parts to be represented in each row
     * */
    private void populateTable2(ObservableList<Part> parts) {

        table2.setItems(parts);

        table2IDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        table2NameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        table2InvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        table2PriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

    /*
     * Called when initializing Modify Product scene. Calls populateTable1 and sets up event handler to filter parts in top table based on input value
     *
     * @param url location used to resolve relative paths for the root object, or null
     * @param resourceBundle resources used to localize root object or null
     * */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        populateTable1(Inventory.getAllParts());

        searchProductText.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                populateTable1(filterParts());
            }
        });
    }
}
