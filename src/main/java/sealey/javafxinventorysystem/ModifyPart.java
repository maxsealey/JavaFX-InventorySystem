package sealey.javafxinventorysystem;

import javafx.collections.FXCollections;
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


/**
 * The ModifyPart controller controls the components used to edit the values and subclass (InHouse or Outsourced) of the selected part.
 * @author Max Sealey
 * */

public class ModifyPart implements Initializable {

    Stage stage;
    Parent scene;

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
    private ToggleGroup modifypartgroup;

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

    /**
    * Event handler that changes the text of the MachineID/Company Name Label on selection of Outsourced radio button
    *
    * @param event Outsourced radio button selection event
    * */
    @FXML
    void onActionCompanyLabel(ActionEvent event) {

        machineIDLabel.setText("Company Name");
        machineIDText.clear();
    }

    /**
     * Event handler that changes the text of the MachineID/Company Name Label on selection of In-House radio button
     *
     * @param actionEvent In-House radio button selection event
     * */
    @FXML
    public void onActionMachineLabel(ActionEvent actionEvent) {

        machineIDLabel.setText("Machine ID");
        machineIDText.clear();
    }

    /**
    * Generic error message method - creates and shows alert based on parameters
    *
    * @param title Alert title
    * @param content Alert text
    * @param type Alert type
    * */
    void errorMessage(String title, String content, Alert.AlertType type) {

        Alert alert = new Alert(Alert.AlertType.NONE);
        alert.setAlertType(type);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }

    /**
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

    /**
    * Used to receive data of part to be modified from MainWindow. Sets TextFields and radio button status
    *
    * @param part Part to be modified
    * */
    public void sendPart(Part part){

        partIDText.setPromptText(String.valueOf(part.getId()));
        partNameText.setText(part.getName());
        inventoryText.setText(String.valueOf(part.getStock()));
        priceText.setText(String.valueOf(part.getPrice()));
        maxText.setText(String.valueOf(part.getMax()));
        minText.setText(String.valueOf(part.getMin()));

        if(part instanceof InHouse){
            inhouseRadio.setSelected(true);
            machineIDLabel.setText("Machine ID");
            machineIDText.setText(String.valueOf(((InHouse) part).getMachineId()));
        } else {
            outsourcedRadio.setSelected(true);
            machineIDLabel.setText("Company Name");
            machineIDText.setText(((OutSourced) part).getCompanyName());
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
        stage.setTitle("Inventory Management System");
        stage.show();
    }

    /**
    * Event handler that checks user input for validity and then updates the part in inventory
    *
    * <p><b>
    * RUNTIME ERROR: Whenever a field wasn't filled out or contained an invalid value (a string value entered into a field intended
    * for an integer or double), the program was throwing a NumberFormatException. To counter this, I used a try/catch to display
    * an error message, and not attempt to save the data. Inside that try block, I set up another try/catch with my checkStockValues()
    * method called to validate the inventory user input and throw an exception if it returns false;
    * </b></p>
    *
    * @param event Save button event
    * @throws IOException IOException
    * */
    @FXML
    void onActionSave(ActionEvent event) throws IOException {
        try {
            int id = Integer.parseInt(partIDText.getPromptText());
            String name = partNameText.getText();
            double price = Double.parseDouble(priceText.getText());
            int inv = Integer.parseInt(inventoryText.getText());
            int min = Integer.parseInt(minText.getText());
            int max = Integer.parseInt(maxText.getText());

            try {
                if(!checkStockValues(min,max,inv)){
                    throw new NumberFormatException();
                } else {
                    if(inhouseRadio.isSelected()){
                        InHouse newPart = new InHouse(id,name,price,inv,min,max);
                        newPart.setMachineId(Integer.parseInt(machineIDText.getText()));
                        Inventory.updatePart(id - 1,newPart);
                    } else {
                        OutSourced newPart = new OutSourced(id,name,price,inv,min,max);
                        newPart.setCompanyName(machineIDText.getText());
                        Inventory.updatePart(id - 1,newPart);
                    }
                    stage = (Stage)((Button)event.getSource()).getScene().getWindow();
                    scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("MainWindow.fxml")));
                    stage.setScene(new Scene(scene));
                    stage.setTitle("Inventory Management System");
                    stage.show();
                }
            } catch(NumberFormatException e) {
                errorMessage("Invalid Input","Inventory Level must be between Min and Max", Alert.AlertType.ERROR);
                errorMessage("Invalid Input","Please enter only valid values in each box. " +
                        "Inventory Level, Min, Max, and (if applicable) Machine ID must be whole numbers.", Alert.AlertType.ERROR);
            }

        } catch(NumberFormatException e) {
            errorMessage("Invalid Input","Please enter only valid values the boxes. " +
                    "Inventory Level, Min, Max, and (if applicable) Machine ID must be whole numbers.", Alert.AlertType.ERROR);
        }
    }

    /**
     * Called when initializing Modify Part scene
     *
     * @param url location used to resolve relative paths for the root object, or null
     * @param resourceBundle resources used to localize root object or null
     * */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        System.out.println("initialized");
    }
}
