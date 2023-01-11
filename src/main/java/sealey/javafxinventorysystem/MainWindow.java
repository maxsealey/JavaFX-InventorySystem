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
import sealey.javafxinventorysystem.models.Inventory;
import sealey.javafxinventorysystem.models.Part;
import sealey.javafxinventorysystem.models.Product;

import java.io.IOException;
import java.net.URL;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

/*
* @author Max Sealey
*
* The MainWindow controller controls the components of the initial scene, which acts as the "home page" for the
* application. It displays the Parts and Products tables, and for each respective table buttons to switch to
* Add and Modify scenes, a button to delete an item, and a search bar. It also contains a button to exit the application.
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

    /*
    * notFound() shows a 404 alert dialog box. Called in the filter methods
    * */
    private void notFound() {

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("404");
        alert.setContentText("Your search did not match any results. Please try again.");
        alert.setHeaderText("Not Found");
        alert.showAndWait();
    }

    /*
     * couldNotDelete() displays an alert when an item is unable to be deleted. Called in the delete methods
     * */
    private void couldNotDelete(String message){

        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Unable to delete");
        alert.setContentText(message);
        alert.setHeaderText("Something went wrong.");
        alert.showAndWait();
    }

    private boolean confirmation(){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Confirm Deletion");
        alert.setContentText("Click 'Ok' to proceed.");
        alert.setHeaderText("Are you sure you want to delete this item?");

        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == ButtonType.OK) {
            return true;
        } else {
            return false;
        }
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

        String search = searchPartText.getText();
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
            notFound();
            return Inventory.getAllParts();
        } else {
            return temp;
        }
    }

    /*
     * The filterProducts() method checks a string input (searchProductText TextField)
     * and returns a list of Products whose name contains the string, and/or whose ID is equal to the string.
     * Returns an ObservableList containing all products if the TextField is empty. If the search term does not
     * find any matches, error message is displayed in dialog box.
     *
     * @param search String retrieved from searchProductText
     * @return temp ObservableList of Products containing all products whose name contains the search parameter
     * and/or whose id is equal to the search parameter, or list of all Products.
     * */
    private ObservableList<Product> filterProducts(){

        String search = searchProductText.getText();
        ObservableList<Product> temp =  Inventory.lookupProduct(search);

        if(isInt(search)){
            Product a = Inventory.lookupProduct(Integer.parseInt(search));
            if(a != null){
                temp.add(a);
            }
        }

        if(search.isEmpty()){
            return Inventory.getAllProducts();
        }
        else if (temp.isEmpty()) {
            notFound();
            return Inventory.getAllProducts();
        } else {
            return temp;
        }
    }

    /*
    * The onActionAddPart() event handler sets the AddPart scene when the Add Button under the parts table is clicked.
    *
    * @param event ActionEvent object for the Add button
    * @throws IOException Throws error message if there is an issue with the event
    * */
    @FXML
    void onActionAddPart(ActionEvent event) throws IOException {

        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("AddPart.fxml")));
        stage.setScene(new Scene(scene));
        stage.setTitle("Add Part");
        stage.show();
    }

    /*
     * The onActionAddProduct() event handler sets the AddProduct scene when the Add Button under the products table is clicked.
     *
     * @param event ActionEvent object for the Add button
     * @throws IOException Throws error message if there is an issue with the event
     * */
    @FXML
    void onActionAddProduct(ActionEvent event) throws IOException {

        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("AddProduct.fxml")));
        stage.setScene(new Scene(scene));
        stage.setTitle("Add Product");
        stage.show();
    }

    /*
    * The onActionDeletePart() event handler deletes the selected part(s) when delete button is clicked.
    * If an item is not selected or deleted, a dialog box displays an error message.
    *
    * @param event ActionEvent object for the Delete button
    * */
    @FXML
    void onActionDeletePart(ActionEvent event) {

        boolean success = false;

        try {
            if (!partTable.getSelectionModel().isEmpty() && confirmation()) {
                success = Inventory.deletePart(partTable.getSelectionModel().getSelectedItem());
            }

            if (!success) {
                couldNotDelete("We were unable to delete an item. Please try again.");
            }
        } catch (NoSuchElementException e) {
            couldNotDelete("We did not delete the item.");
        }
    }

    /*
     * The onActionDeleteProduct() event handler deletes the selected product(s) when delete button is clicked.
     * If an item is not selected or deleted, a dialog box displays an error message.
     *
     * @param event ActionEvent object for the Delete button
     * @throws IOException Throws error message if there is an issue with the event
     * */
    @FXML
    void onActionDeleteProduct(ActionEvent event) {

        try {
            if(!productTable.getSelectionModel().isEmpty()) {
                if (productTable.getSelectionModel().getSelectedItem().getAllAssociatedParts().isEmpty() && confirmation()) {
                    Inventory.deleteProduct(productTable.getSelectionModel().getSelectedItem());
                } else if (!productTable.getSelectionModel().getSelectedItem().getAllAssociatedParts().isEmpty()){
                    couldNotDelete("You must remove the parts associated with this product in order to delete it.");
                    throw new NoSuchElementException();
                }
            } else {
                couldNotDelete("Please select a product.");
                throw new NoSuchElementException();
            }
        } catch (NoSuchElementException e) {
            return;
        }
    }

    /*
     * The onActionExit() event handler closes the application when exit button is clicked.
     *
     * @param event ActionEvent object for the Exit button
     * */
    @FXML
    void onActionExit(ActionEvent event) {

        System.exit(0);
    }

    /*
     * The onActionModifyPart() event handler sets the ModifyPart scene when the Modify Button under the parts table is clicked.
     *
     * @param event ActionEvent object for the Modify button
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
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Please select a part.");
            alert.showAndWait();
        }


    }

    /*
     * The onActionModifyProduct() event handler sets the ModifyProduct scene when the Modify Button under the products table is clicked.
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
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Please select a product.");
            alert.showAndWait();
        }
    }

    /*
    * The displayData() method sets the values to be displayed in both tables.
    *
    * @param parts ObservableList of parts to be displayed in Parts table
    * @param products ObservableList of products to be displayed in Products table
    * */
    private void displayData(ObservableList<Part> parts, ObservableList<Product> products) {

        partTable.setItems(parts);
        productTable.setItems(products);

        partIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        productIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        productNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        productInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        productPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

    /*
    * The initialize() method is called when MainWindow controller is initialized. It displays all products currently in the inventory,
    * and then sets event listeners on the search bar TextFields. When the search field is selected and the 'enter' button is clicked,
    * the event handler is fired and the table is updated.
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
                displayData(filterParts(), filterProducts());
            }
        });

        searchProductText.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent actionEvent) {
                displayData(filterParts(), filterProducts());
            }
        });
    }
}
