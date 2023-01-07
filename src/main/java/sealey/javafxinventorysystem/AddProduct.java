package sealey.javafxinventorysystem;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class AddProduct implements Initializable {

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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("initialized");
    }

}