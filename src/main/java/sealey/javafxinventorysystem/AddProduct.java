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

    /*
     * The search() method is a helper function that returns a boolean indicating whether an integer is already being used as an ID number for an existing product.
     * This is only called in the generateID() method to ensure that the auto-generated ID is unique.
     *
     * @param id Integer to be checked for ID uniqueness
     * @return boolean True if ID belongs to existing product, False otherwise
     * */
    boolean search(int id){
        for(Part p : Inventory.getAllParts())
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
    int generateID() {
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

    @FXML
    void onActionCancel(ActionEvent event) throws IOException {

        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("MainWindow.fxml")));
        stage.setScene(new Scene(scene));
        stage.show();
    }
    @FXML
    void onActionSave(ActionEvent event) throws IOException {

        int id = Integer.parseInt(productIDText.getText());
        String name = productNameText.getText();
        int inv = Integer.parseInt(inventoryText.getText());
        double price = Double.parseDouble(priceText.getText());
        int max = Integer.parseInt(maxText.getText());
        int min = Integer.parseInt(minText.getText());

        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("MainWindow.fxml")));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    private ObservableList<Part> partFilter(String search){

        ObservableList<Part> temp;
        if(search.isEmpty()) return Inventory.getAllParts();
        temp = Inventory.lookupPart(search);
        if(isInt(search)){
            temp.add(Inventory.lookupPart(Integer.parseInt(search)));
        }
        return temp;
    }

    void populateTable1(ObservableList<Part> parts){

        table1.setItems(parts);

        table1IDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        table1NameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        table1InvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
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