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

    void errorMessage(String content){

        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setContentText(content);
        alert.setHeaderText("Something went wrong.");
        alert.showAndWait();
    }

    void errorMessage(String title, String content, Alert.AlertType type) {

        Alert alert = new Alert(Alert.AlertType.NONE);
        alert.setAlertType(type);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }

    /*
     * The search() method is a helper function that returns a boolean indicating whether an integer is already being used as an ID number for an existing product.
     * This is only called in the generateID() method to ensure that the auto-generated ID is unique.
     *
     * @param id Integer to be checked for ID uniqueness
     * @return boolean True if ID belongs to existing product, False otherwise
     * */
    private boolean search(int id){

        for(Product p : Inventory.getAllProducts())
        {
            if(p.getId() == id){
                return true;
            }
        }
        return false;
    }

    /*
     * The generateID() method is a helper function that generates an ID number for created part. Always returns the next unique integer in sequential order
     *
     * @return id Integer that is either 1 (if List is empty), or the next unique integer
     * */
    private int generateID() {

        int id = 1;
        for(Part a : Inventory.getAllParts()) {
            if(search(id)){
                id++;
            } else {
                return id;
            }
        }
        return id;
    }

    /*
     * The isInt() method checks whether a provided string can be converted to an integer and returns a boolean.
     *
     * @param str The string to be checked
     * @return boolean Returns true if string is also an integer, false if exception is caught
     */
    private boolean isInt(String str) {

        try {
            Integer.valueOf(str);
            return true;
        } catch (NumberFormatException e) { return false; }
    }

    boolean checkStockValues(int min, int max, int stock){

        return max > stock && min < stock && min >= 1;
    }

    /*
     * The filterParts() method checks a string input (searchPartText TextField)
     * and returns a list of Parts whose name contains the string, and/or whose ID is equal to the string.
     * Returns an ObservableList containing all parts if the TextField is empty. If the search term does not
     * find any matches, error message is displayed in dialog box.
     *
     * @param search String retrieved from searchPartText
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


    @FXML
    void onActionCancel(ActionEvent event) throws IOException {

        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("MainWindow.fxml")));
        stage.setScene(new Scene(scene));
        stage.show();
    }
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

    @FXML
    void onActionAdd(ActionEvent event) throws IOException {

        if(!table1.getSelectionModel().isEmpty()) {
            updatedProduct.addAssociatedPart(table1.getSelectionModel().getSelectedItem());
            populateTable2(updatedProduct.getAllAssociatedParts());
        } else {
            errorMessage("Please select a part.");
        }
    }
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

    private void populateTable1(ObservableList<Part> parts){

        table1.setItems(parts);

        table1IDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        table1NameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        table1InvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        table1PriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

    private void populateTable2(ObservableList<Part> parts) {

        table2.setItems(parts);

        table2IDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        table2NameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        table2InvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        table2PriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        productIDText.setPromptText(String.valueOf(generateID()));
        populateTable1(Inventory.getAllParts());

        searchProductText.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                populateTable1(filterParts());
            }
        });
    }
}
