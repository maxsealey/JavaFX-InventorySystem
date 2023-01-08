package sealey.javafxinventorysystem;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

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

    @FXML
    void onActionCancel(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("MainWindow.fxml")));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    @FXML
    void onActionCompanyLabel(ActionEvent event) {
        System.out.println("Change machine id label to company name");
    }

    @FXML
    void onActionSave(ActionEvent event) {
        System.out.println("Save button pressed");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("initialized");
    }
}
