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
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import sealey.javafxinventorysystem.models.Inventory;
import sealey.javafxinventorysystem.models.Part;
import sealey.javafxinventorysystem.models.Product;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
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
     * The filterParts() method checks a string input (searchPartText TextField)
     * and returns a list of Parts whose name contains the string, and/or whose ID is equal to the string.
     * Returns an ObservableList containing all parts if the TextField is empty. If the search term does not
     * find any matches, error message is displayed in dialog box.
     *
     * @param search String retrieved from searchPartText
     * @return ObservableList of Parts containing all parts whose name contains the search parameter
     * and/or whose id is equal to the search parameter, or list of all Parts.
     * */
    ObservableList<Part> filterParts(){

        String search = searchPartText.getText();
        ObservableList<Part> temp =  Inventory.lookupPart(search);

        if(isInt(search)){
            temp.add(Inventory.lookupPart(Integer.parseInt(search)));
        }

        if(temp.isEmpty()){
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
    ObservableList<Product> filterProducts(){

        String search = searchProductText.getText();
        ObservableList<Product> temp =  Inventory.lookupProduct(search);

        if(isInt(search)){
            temp.add(Inventory.lookupProduct(Integer.parseInt(search)));
        }

        if(temp.isEmpty()){
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
    * @throws IOException Throws error message if there is an issue with the event
    * */
    @FXML
    void onActionDeletePart(ActionEvent event) {

        Inventory.deletePart(partTable.getSelectionModel().getSelectedItem());
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

        Inventory.deleteProduct(productTable.getSelectionModel().getSelectedItem());
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
        modPartController.sendPart(partTable.getSelectionModel().getSelectedItem());

        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = loader.getRoot();
        stage.setScene(new Scene(scene));
        stage.setTitle("Modify Part");
        stage.show();
    }

    /*
     * The onActionModifyProduct() event handler sets the ModifyProduct scene when the Modify Button under the products table is clicked.
     *
     * @param event ActionEvent object for the Modify button
     * @throws IOException Throws error message if there is an issue with the event
     * */
    @FXML
    void onActionModifyProduct(ActionEvent event) throws IOException {

        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("ModifyProduct.fxml")));
        stage.setScene(new Scene(scene));
        stage.setTitle("ModifyProduct");
        stage.show();
    }

    /*
    * The displayData() method sets the values to be displayed in both tables.
    *
    * @param parts ObservableList of parts to be displayed in Parts table
    * @param products ObservableList of products to be displayed in Products table
    * */
    void displayData(ObservableList<Part> parts, ObservableList<Product> products) {

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
