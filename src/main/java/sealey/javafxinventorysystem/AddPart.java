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
import sealey.javafxinventorysystem.models.InHouse;
import sealey.javafxinventorysystem.models.Inventory;
import sealey.javafxinventorysystem.models.OutSourced;
import sealey.javafxinventorysystem.models.Part;

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

    // Helper Functions
    @FXML
    boolean search(int id){
        for(Part p : Inventory.getAllParts())
        {
            if(p.getId() == id){
                return true;
            }
        }
        return false;
    }
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

    @FXML
    void onActionCancel(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("MainWindow.fxml")));
        stage.setScene(new Scene(scene));
        stage.show();
    }
    @FXML
    void onActionSave(ActionEvent event) throws IOException {
        int id = Integer.parseInt(partIDText.getPromptText());
        String name = partNameText.getText();
        int inv = Integer.parseInt(inventoryText.getText());
        double price = Double.parseDouble(priceText.getText());
        int max = Integer.parseInt(maxText.getText());
        int min = Integer.parseInt(minText.getText());
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
        stage.show();
    }

    @FXML
    void onActionCompanyLabel(ActionEvent event) {
        machineIDLabel.setText("Company Name");
    }
    @FXML
    public void onActionMachineLabel(ActionEvent actionEvent) {
        machineIDLabel.setText("Machine ID");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        partIDText.setPromptText(String.valueOf(generateID()));
    }
}
