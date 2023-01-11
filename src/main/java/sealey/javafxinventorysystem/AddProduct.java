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
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

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

    private ObservableList<Part> bottomTable = FXCollections.observableArrayList();

    //private int currentID = -1;
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

    private boolean confirmation(){
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

    private void couldNotRemove(){

        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Unable to remove");
        alert.setContentText("We were not able to remove this part.");
        alert.setHeaderText("Something went wrong.");
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

    @FXML
    void onActionAdd(ActionEvent event) throws IOException {
        bottomTable.add(table1.getSelectionModel().getSelectedItem());
        populateTable2(getBottomTable());
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

        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("MainWindow.fxml")));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    public void onActionRemoveItem(ActionEvent actionEvent) {

        boolean success = false;

        if(!table2.getSelectionModel().isEmpty() && confirmation()){
            bottomTable.remove(table2.getSelectionModel().getSelectedItem());
            success = true;
        }

        if(!success) {
            couldNotRemove();
        }
    }

    private ObservableList<Part> getBottomTable() {

        return bottomTable;
    }

    private void setBottomTable(ObservableList<Part> bottomTable) {

        this.bottomTable = bottomTable;
    }

    private void populateTable1(ObservableList<Part> parts){

        table1.setItems(parts);

        table1IDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        table1NameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        table1InvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        table1InvCol.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

    private void populateTable2(ObservableList<Part> parts){

        table2.setItems(parts);

        table2IDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        table2NameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        table2InvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        table1InvCol.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        productIDText.setPromptText(String.valueOf(generateID()));
        table2.getItems().clear();
        populateTable1(Inventory.getAllParts());

        searchPartText.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                populateTable1(filterParts());
            }
        });
    }
}