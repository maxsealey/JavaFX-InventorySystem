package sealey.javafxinventorysystem;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import sealey.javafxinventorysystem.models.InHouse;
import sealey.javafxinventorysystem.models.Inventory;
import sealey.javafxinventorysystem.models.OutSourced;
import sealey.javafxinventorysystem.models.Part;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

/*
 * @author Max Sealey
 *
 * The AddPart controller controls the components for the AddPart scene, and displays radio buttons to indicate whether the created item is In-House or Outsourced,
 * a disabled TextField containing an auto-generated unique ID #, and TextFields for the item's name, inventory amount, price, max items possible, min items possible,
 * and either the machine ID (when In-House radio button is selected) or company name (when Outsourced radio button is selected). Below that is the save button and the
 * cancel button, both of which navigate back to the MainWindow, though only the save button adds the entered data into the table (upon input validation).
 * */

public class AddPart implements Initializable {

    Stage stage;
    Parent scene;
    @FXML
    private ToggleGroup addpartgroup;

    @FXML
    private Button cancelButton;

    @FXML
    private RadioButton inhouseRadio;

    @FXML
    private TextField inventoryText;

    @FXML
    private Label machineIDLabel;

    @FXML
    private TextField machineIDText;

    @FXML
    private TextField maxText;

    @FXML
    private TextField minText;

    @FXML
    private RadioButton outsourcedRadio;

    @FXML
    private TextField partIDText;

    @FXML
    private TextField partNameText;

    @FXML
    private TextField priceText;

    @FXML
    private Button saveButton;

    /*
    * The search() method is a helper function that returns a boolean indicating whether an integer is already being used as an ID number for an existing part.
    * This is only called in the generateID() method to ensure that the auto-generated ID is unique.
    *
    * @param id Integer to be checked for ID uniqueness
    * @return boolean True if ID belongs to existing part, False otherwise
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

    void errorMessage(String title, String content, Alert.AlertType type) {

        Alert alert = new Alert(Alert.AlertType.NONE);
        alert.setAlertType(type);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }

    boolean checkStockValues(int min, int max, int stock){

        if(max <= stock || min >= stock){
            errorMessage("Invalid Input", "Min should be less than Max, and the Inventory level must be in between", Alert.AlertType.ERROR);
            return false;
        } else {
            return true;
        }
    }

    /*
    * The onActionCancel() event handler navigates back to the MainWindow without adding any data to Inventory When the Cancel button is clicked.
    *
    * @param event ActionEvent object for the Cancel button
    * @throws IOException Throws error message if there is an issue with the event
    * */
    @FXML
    void onActionCancel(ActionEvent event) throws IOException {

        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("MainWindow.fxml")));
        stage.setScene(new Scene(scene));
        stage.setTitle("Inventory Management System");
        stage.show();
    }

    /*
     * The onActionSave() event handler checks for valid input when the Save button is clicked, and if valid, adds Part to Inventory and navigates back to the MainWindow.
     *
     * @param event ActionEvent object for the Save button
     * @throws IOException Throws error message if there is an issue with the event
     * */
    @FXML
    void onActionSave(ActionEvent event) throws IOException {

        int id = Integer.parseInt(partIDText.getPromptText());
        String name = partNameText.getText();
        double price = Double.parseDouble(priceText.getText());
        int inv = Integer.parseInt(inventoryText.getText());
        int min = Integer.parseInt(minText.getText());
        int max = Integer.parseInt(maxText.getText());
        boolean isInHouse = !outsourcedRadio.isSelected();

        if(isInHouse){
            InHouse newPart = new InHouse(id,name,price,inv,max,min);
            newPart.setMachineId(Integer.parseInt(machineIDText.getText()));
            Inventory.addPart(newPart);
        } else {
            OutSourced newPart = new OutSourced(id,name,price,inv,max,min);
            newPart.setCompanyName(machineIDText.getText());
            Inventory.addPart(newPart);
        }

        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("MainWindow.fxml")));
        stage.setScene(new Scene(scene));
        stage.setTitle("Inventory Management System");
        stage.show();
    }

    /*
    * When the In-House radio button is selected, the Machine ID label is displayed.
    *
    * @param event ActionEvent object for the In-House radio button
    * */
    @FXML
    void onActionCompanyLabel(ActionEvent event) {

        machineIDLabel.setText("Company Name");
    }

    /*
     * When the Outsourced radio button is selected, the Company Name label is displayed.
     *
     * @param event ActionEvent object for the Outsourced radio button
     * */
    @FXML
    public void onActionMachineLabel(ActionEvent actionEvent) {

        machineIDLabel.setText("Machine ID");
    }

    /*
    * The initialize() method is called when the AddPart controller is initialized. The prompt text property of the disabled partIDText TextField
    * is set to an auto-generated ID number.
    *
    * @param url location used to resolve relative paths for the root object, or null
    * @param resourceBundle resources used to localize root object or null
    * */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        partIDText.setPromptText(String.valueOf(generateID()));
    }
}
