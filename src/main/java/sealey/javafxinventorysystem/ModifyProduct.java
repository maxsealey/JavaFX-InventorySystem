package sealey.javafxinventorysystem;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

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
    private TableView<?> table1;

    @FXML
    private TableColumn<?, ?> table1IDCol;

    @FXML
    private TableColumn<?, ?> table1InvCol;

    @FXML
    private TableColumn<?, ?> table1NameCol;

    @FXML
    private TableColumn<?, ?> table1PriceCol;

    @FXML
    private TableView<?> table2;

    @FXML
    private TableColumn<?, ?> table2IDCol;

    @FXML
    private TableColumn<?, ?> table2InvCol;

    @FXML
    private TableColumn<?, ?> table2NameCol;

    @FXML
    private TableColumn<?, ?> table2PriceCol;

    @FXML
    void onActionCancel(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("MainWindow.fxml")));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("initialized");
    }
}
