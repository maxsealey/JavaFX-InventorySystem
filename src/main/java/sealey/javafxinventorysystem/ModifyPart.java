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

    @FXML
    void onActionCompanyLabel(ActionEvent event) {

        machineIDLabel.setText("Company Name");
    }
    @FXML
    public void onActionMachineLabel(ActionEvent actionEvent) {

        machineIDLabel.setText("Machine ID");
    }

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

    @FXML
    void onActionCancel(ActionEvent event) throws IOException {

        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("MainWindow.fxml")));
        stage.setScene(new Scene(scene));
        stage.setTitle("Inventory Management System");
        stage.show();
    }
    @FXML
    void onActionSave(ActionEvent event) throws IOException {

        int id = Integer.parseInt(partIDText.getText());
        String name = partNameText.getText();
        int inv = Integer.parseInt(inventoryText.getText());
        double price = Double.parseDouble(priceText.getText());
        int max = Integer.parseInt(maxText.getText());
        int min = Integer.parseInt(minText.getText());
        Part temp;

        if(inhouseRadio.isSelected()){

            temp = new InHouse(id,name,price,inv,min,max);
            ((InHouse) temp).setMachineId(Integer.parseInt(machineIDText.getText()));
        } else {
            temp = new OutSourced(id,name,price,inv,min,max);
            ((OutSourced) temp).setCompanyName(machineIDText.getText());
        }

        Inventory.updatePart(temp);

        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("MainWindow.fxml")));
        stage.setScene(new Scene(scene));
        stage.setTitle("Inventory Management System");
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        System.out.println("initialized");
    }
}
