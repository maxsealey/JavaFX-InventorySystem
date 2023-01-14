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
import sealey.javafxinventorysystem.utility.*;

import java.io.IOException;
import java.net.URL;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 *  The AddPart controller controls the components used to create and add a part to the inventory.
 *
 * @author Max Sealey
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
     * Event handler that checks user input for validity and then adds the part to inventory
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
                if(!Helpers.checkStockValues(min, max, inv)){
                    throw new NumberFormatException();
                } else {
                    try {
                        Alert alert = Alerts.confirmSave();
                        Optional<ButtonType> result = alert.showAndWait();

                        if (result.get() == ButtonType.OK) {
                            if (inhouseRadio.isSelected()) {
                                InHouse newPart = new InHouse(id, name, price, inv, min, max);
                                newPart.setMachineId(Integer.parseInt(machineIDText.getText()));
                                Inventory.addPart(newPart);
                            } else {
                                OutSourced newPart = new OutSourced(id, name, price, inv, min, max);
                                newPart.setCompanyName(machineIDText.getText());
                                Inventory.addPart(newPart);
                            }

                            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                            scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("MainWindow.fxml")));
                            stage.setScene(new Scene(scene));
                            stage.setTitle("Inventory Management System");
                            stage.show();
                        }
                    } catch(NoSuchElementException ignored) {}
                }
            } catch(NumberFormatException e) {
                Alerts.invalidStockValues();
            }

        } catch(NumberFormatException e) {
            Alerts.invalidInput();
        }
    }

    /**
     * Event handler that changes the text of the MachineID/Company Name Label on selection of Outsourced radio button
     *
     * @param event Outsourced radio button selection event
     * */
    @FXML
    void onActionCompanyLabel(ActionEvent event) {

        machineIDLabel.setText("Company Name");
    }

    /**
     * Event handler that changes the text of the MachineID/Company Name Label on selection of In-House radio button
     *
     * @param actionEvent In-House radio button selection event
     * */
    @FXML
    public void onActionMachineLabel(ActionEvent actionEvent) {

        machineIDLabel.setText("Machine ID");
    }

    /**
     * Called when initializing AddPart scene, generates unique part ID
     *
     * @param url location used to resolve relative paths for the root object, or null
     * @param resourceBundle resources used to localize root object or null
     * */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        partIDText.setPromptText(String.valueOf(Helpers.generateID(true)));
    }
}
