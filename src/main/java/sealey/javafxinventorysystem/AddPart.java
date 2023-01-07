package sealey.javafxinventorysystem;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class AddPart implements Initializable {
    @FXML
    protected RadioButton inhouseRadio;
    @FXML
    protected RadioButton outsourcedRadio;
    @FXML
    protected TextField partIDText;
    @FXML
    protected TextField partNameText;
    @FXML
    protected TextField inventoryText;
    @FXML
    protected TextField priceText;
    @FXML
    protected TextField maxText;
    @FXML
    protected TextField minText;
    @FXML
    protected TextField machineIDText;
    @FXML
    protected Label machineIDLabel;
    @FXML
    protected Button saveButton;
    @FXML
    protected Button cancelButton;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("initialized");
    }
}
