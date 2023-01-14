package sealey.javafxinventorysystem;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import sealey.javafxinventorysystem.models.Inventory;
import sealey.javafxinventorysystem.models.Part;
import sealey.javafxinventorysystem.models.Product;
import sealey.javafxinventorysystem.utility.*;

import java.io.IOException;
import java.net.URL;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * The AddProduct controller controls the components used to create and add a new product to the inventory.
 * @author Max Sealey
 * */

public class AddProduct implements Initializable {

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
    private TextField searchPartText;

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

    Product newProduct = new Product();

    /**
     * The filterParts() method checks a string and returns a list of Parts whose name contains the string, and/or whose ID is equal to the string.
     *
     * @return ObservableList of Parts containing all parts whose name contains the search parameter
     * and/or whose id is equal to the search parameter, or list of all Parts.
     * */
    ObservableList<Part> filterParts(){

        String search = searchPartText.getText();
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
            Alerts.genericMessage("404","Your search did not return any results. Please try again.", Alert.AlertType.INFORMATION);
            return Inventory.getAllParts();
        } else {
            return temp;
        }
    }

    /**
     * When an item in the top table is selected and the add button is clicked, this event handler will fire and add the item to the bottom table.
     *
     * @param event Add button event
     * @throws IOException IOException
     * */
    @FXML
    void onActionAdd(ActionEvent event) throws IOException {

        if(!table1.getSelectionModel().isEmpty()) {
            newProduct.addAssociatedPart(table1.getSelectionModel().getSelectedItem());
            Helpers.setPartTables(newProduct.getAllAssociatedParts(), table2, table2IDCol, table2NameCol, table2InvCol, table2PriceCol);
        } else {
            Alerts.errorMessage("Please select a part.");
        }
    }

    /**
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

    /**
     * Event handler that checks user input for validity and then adds the product in inventory
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
            int stock = Integer.parseInt(inventoryText.getText());
            int min = Integer.parseInt(minText.getText());
            int max = Integer.parseInt(maxText.getText());

            try {
                if(!Helpers.checkStockValues(min,max, stock)){
                    throw new NumberFormatException();
                } else {
                    try {
                        Alert alert = Alerts.confirmSave();

                        Optional<ButtonType> result = alert.showAndWait();

                        if (result.get() == ButtonType.OK) {
                            Helpers.setProduct(newProduct,id,name,stock,price,min,max);
                            Inventory.addProduct(newProduct);

                            stage = (Stage)((Button)event.getSource()).getScene().getWindow();
                            scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("MainWindow.fxml")));
                            stage.setScene(new Scene(scene));
                            stage.setTitle("Inventory Management System");
                            stage.show();
                        }
                    } catch (NoSuchElementException ignored) {}
                }
            } catch(NumberFormatException e) {
                Alerts.genericMessage("Invalid Input","Inventory Level must be between Min and Max", Alert.AlertType.ERROR);
            }
        } catch(NumberFormatException e) {
            Alerts.genericMessage("Invalid Input","Please enter only valid values in each box. " +
                    "Inventory Level, Min, Max, and (if applicable) Machine ID must be whole numbers.", Alert.AlertType.ERROR);
        }
    }

    /**
     * If an item on the bottom table is selected and the remove button is clicked, this event
     * handler will fire and on user confirmation, remove the item from the bottom table.
     *
     * @param actionEvent Remove button event
     * @throws IOException IOException
     * */
    public void onActionRemoveItem(ActionEvent actionEvent) throws IOException {

        boolean success = false;

        try {
            if(!table2.getSelectionModel().isEmpty())
            {
                if(Alerts.deleteConfirmation()){
                    success = newProduct.deleteAssociatedPart(table2.getSelectionModel().getSelectedItem());
                }
            }
            if(!success) {
                if(table2.getSelectionModel().isEmpty()){
                    Alerts.errorMessage("Please select a part.");
                }
            }
        } catch (NoSuchElementException ignored) {}
    }

    /**
     * Called when initializing Add Product scene. Generates unique ID and calls populateTable1().
     * Also sets up event handler to filter parts in top table based on input value
     *
     * @param url location used to resolve relative paths for the root object, or null
     * @param resourceBundle resources used to localize root object or null
     * */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        productIDText.setPromptText(String.valueOf(Helpers.generateID(false)));
        Helpers.setPartTables(Inventory.getAllParts(), table1, table1IDCol, table1NameCol, table1InvCol, table1PriceCol);

        searchPartText.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Helpers.setPartTables(filterParts(), table1, table1IDCol, table1NameCol, table1InvCol, table1PriceCol);
            }
        });
    }
}