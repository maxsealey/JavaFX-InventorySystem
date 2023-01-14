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

import sealey.javafxinventorysystem.models.*;
import sealey.javafxinventorysystem.utility.*;

import java.io.IOException;
import java.net.URL;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * The ModifyProduct controller controls the components used to edit the values and associated parts of the selected product.
 * @author Max Sealey
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
                            Helpers.setProduct(updatedProduct,id,name,stock,price,min,max);

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
                Alerts.invalidStockValues();
            }

        } catch(NumberFormatException e) {
            Alerts.invalidInput();
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
            updatedProduct.addAssociatedPart(table1.getSelectionModel().getSelectedItem());
            Helpers.setPartTables(updatedProduct.getAllAssociatedParts(), table2, table2IDCol, table2NameCol, table2InvCol, table2PriceCol);
        } else {
            Alerts.errorMessage("Please select a part.");
        }
    }

    /**
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
                if(Alerts.removeConfirmation()){
                    success = updatedProduct.deleteAssociatedPart(table2.getSelectionModel().getSelectedItem());
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
     * Used to receive data of product to be modified from MainWindow. Sets TextFields and updatedProduct Product object attributes
     *
     * @param product Product to be modified
     * */
    public void sendProduct(Product product){

        Helpers.setProduct(updatedProduct, product);

        productIDText.setPromptText(String.valueOf(updatedProduct.getId()));
        productNameText.setText(updatedProduct.getName());
        inventoryText.setText(String.valueOf(updatedProduct.getStock()));
        priceText.setText(String.valueOf(updatedProduct.getPrice()));
        maxText.setText(String.valueOf(updatedProduct.getMax()));
        minText.setText(String.valueOf(updatedProduct.getMin()));

        if(!product.getAllAssociatedParts().isEmpty()){
            updatedProduct.setAllAssociatedParts(product.getAllAssociatedParts());
            Helpers.setPartTables(updatedProduct.getAllAssociatedParts(), table2, table2IDCol, table2NameCol, table2InvCol, table2PriceCol);
        }
    }

    /**
     * Called when initializing Modify Product scene. Calls populateTable1 and sets up event handler to filter parts in top table based on input value
     *
     * @param url location used to resolve relative paths for the root object, or null
     * @param resourceBundle resources used to localize root object or null
     * */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Helpers.setPartTables(Inventory.getAllParts(), table1, table1IDCol, table1NameCol, table1InvCol, table1PriceCol);

        searchProductText.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Helpers.setPartTables(Helpers.filterParts(searchProductText.getText()), table1, table1IDCol, table1NameCol, table1InvCol, table1PriceCol);
            }
        });
    }
}
