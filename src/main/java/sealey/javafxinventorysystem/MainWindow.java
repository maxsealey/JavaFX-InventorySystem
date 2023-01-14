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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import sealey.javafxinventorysystem.models.Inventory;
import sealey.javafxinventorysystem.models.Part;
import sealey.javafxinventorysystem.models.Product;
import sealey.javafxinventorysystem.utility.*;

import java.io.IOException;
import java.net.URL;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * The MainWindow controller controls the components of the initial scene and acts as the "home page" for the application.
 *
 * @author Max Sealey
 * */

public class MainWindow implements Initializable {

    Stage stage;
    Parent scene;

    @FXML
    private Button addPartButton;
    @FXML
    private Button addProductButton;
    @FXML
    private Button deletePartButton;
    @FXML
    private Button deleteProductButton;
    @FXML
    private Button exitButton;
    @FXML
    private Button modifyPartButton;
    @FXML
    private Button modifyProductButton;

    @FXML
    private TableColumn<Part, Integer> partIDCol;
    @FXML
    private TableColumn<Part, Integer> partInvCol;
    @FXML
    private TableColumn<Part, String> partNameCol;
    @FXML
    private TableColumn<Part, Double> partPriceCol;
    @FXML
    private TableView<Part> partTable;
    @FXML
    private TableColumn<Product, Integer> productIDCol;
    @FXML
    private TableColumn<Product, Integer> productInvCol;
    @FXML
    private TableColumn<Product, String> productNameCol;
    @FXML
    private TableColumn<Product, Double> productPriceCol;
    @FXML
    private TableView<Product> productTable;

    @FXML
    private TextField searchPartText;
    @FXML
    private TextField searchProductText;

    /**
    * The onActionAddPart() event handler sets the AddPart scene when the Add Button under the parts table is clicked.
    *
    * @param event Add button event
    * @throws IOException IOException
    * */
    @FXML
    void onActionAddPart(ActionEvent event) throws IOException {

        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("AddPart.fxml")));
        stage.setScene(new Scene(scene));
        stage.setTitle("Add Part");
        stage.show();
    }

    /**
     * The onActionAddProduct() event handler sets the AddProduct scene when the Add Button under the products table is clicked.
     *
     * @param event Add Product button event
     * @throws IOException IOException
     * */
    @FXML
    void onActionAddProduct(ActionEvent event) throws IOException {

        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("AddProduct.fxml")));
        stage.setScene(new Scene(scene));
        stage.setTitle("Add Product");
        stage.show();
    }

    /**
    * The onActionDeletePart() event handler deletes the selected part(s) when delete button is clicked.
    * If an item is not selected or deleted, a dialog box displays an error message.
    *
    * @param event ActionEvent object for the Delete button
    * */
    @FXML
    void onActionDeletePart(ActionEvent event) {

        boolean success = false;

        try {
            if (!partTable.getSelectionModel().isEmpty() && Alerts.deleteConfirmation()) {
                success = Inventory.deletePart(partTable.getSelectionModel().getSelectedItem());
            }

            if (!success) {
                Alerts.couldNotDelete("We were unable to delete an item. Please try again.");
            }
        } catch (NoSuchElementException e) {
            Alerts.couldNotDelete("We did not delete the item.");
        }
    }

    /**
     * The onActionDeleteProduct() event handler deletes the selected product(s) when delete button is clicked.
     * If an item is not selected or deleted, a dialog box displays an error message. Won't let you delete a product with any associated parts.
     *
     * @param event Delete button event
     * */
    @FXML
    void onActionDeleteProduct(ActionEvent event) {

        boolean success = false;
        try {
            if(productTable.getSelectionModel().isEmpty()) {
                Alerts.couldNotDelete("Please select a product.");
               throw new NoSuchElementException();
            } else {
                if(Alerts.deleteConfirmation() && productTable.getSelectionModel().getSelectedItem().getAllAssociatedParts().isEmpty()){
                    success = Inventory.deleteProduct(productTable.getSelectionModel().getSelectedItem());
                } else if(!productTable.getSelectionModel().getSelectedItem().getAllAssociatedParts().isEmpty()){
                    Alerts.couldNotDelete("You must remove the parts associated with this product in order to delete it.");
                    throw new NoSuchElementException();
                } else {
                    throw new NoSuchElementException();
                }
            }
        } catch (NoSuchElementException e) {
            return;
        }
    }

    /**
     * The onActionExit() event handler closes the application when exit button is clicked and user confirms with alert.
     *
     * @param event Exit button event
     * */
    @FXML
    void onActionExit(ActionEvent event) {

        try {
            Alerts.confirmExit();
        } catch (NoSuchElementException e){
            return;
        }
    }

    /**
     * The onActionModifyPart() event handler sets the ModifyPart scene when the Modify Button under the parts table is clicked.
     * If item is selected, sends part's data to ModifyPart controller.
     *
     * @param event Modify button event
     * @throws IOException Throws error message if there is an issue with the event
     * */
    @FXML
    void onActionModifyPart(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Objects.requireNonNull(getClass().getResource("ModifyPart.fxml")));
        loader.load();

        ModifyPart modPartController = loader.getController();
        try {
            modPartController.sendPart(partTable.getSelectionModel().getSelectedItem());

            stage = (Stage)((Button)event.getSource()).getScene().getWindow();
            scene = loader.getRoot();
            stage.setScene(new Scene(scene));
            stage.setTitle("Modify Part");
            stage.show();
        } catch(NullPointerException e) {
            Alerts.errorMessage("Please select a part.");
        }
    }

    /**
     * The onActionModifyProduct() event handler sets the ModifyProduct scene when the Modify Button under the products table is clicked.
     * If item is selected, sends selected product's data to Modify Product controller.
     *
     * @param event ActionEvent object for the Modify button
     * @throws IOException Throws error message if there is an issue with the event
     * */
    @FXML
    void onActionModifyProduct(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Objects.requireNonNull(getClass().getResource("ModifyProduct.fxml")));
        loader.load();

        ModifyProduct modProductController = loader.getController();
        try {
            modProductController.sendProduct(productTable.getSelectionModel().getSelectedItem());

            stage = (Stage)((Button)event.getSource()).getScene().getWindow();
            scene = loader.getRoot();
            stage.setScene(new Scene(scene));
            stage.setTitle("Modify Product");
            stage.show();
        } catch(NullPointerException e) {
            Alerts.errorMessage("Please select a product.");
        }
    }

    /**
    * The displayData() method sets the values to be displayed in both tables.
    *
    * @param parts ObservableList of parts to be displayed in Parts table
    * @param products ObservableList of products to be displayed in Products table
    * */
    private void displayData(ObservableList<Part> parts, ObservableList<Product> products) {

        Helpers.setPartTables(parts, partTable, partIDCol, partNameCol, partInvCol, partPriceCol);
        Helpers.setProductTable(products, productTable, productIDCol, productNameCol, productInvCol, productPriceCol);
    }

    /**
    * Called when MainWindow controller is initialized. It displays all products currently in the inventory,
    * and then sets event listeners on the search bar TextFields. When the search field is selected
    * and the 'enter' button is clicked, the event handler is fired and the table is updated.
    *
    * @param url location used to resolve relative paths for the root object, or null
    * @param resourceBundle resources used to localize root object or null
    * */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        displayData(Inventory.getAllParts(),Inventory.getAllProducts());
        searchPartText.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                displayData(Helpers.filterParts(searchPartText.getText()), Helpers.filterProducts(searchProductText.getText()));
            }
        });

        searchProductText.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent actionEvent) {
                displayData(Helpers.filterParts(searchPartText.getText()), Helpers.filterProducts(searchProductText.getText()));
            }
        });
    }
}
