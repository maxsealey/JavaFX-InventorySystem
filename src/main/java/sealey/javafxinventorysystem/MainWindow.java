package sealey.javafxinventorysystem;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import sealey.javafxinventorysystem.models.Inventory;
import sealey.javafxinventorysystem.models.Part;
import sealey.javafxinventorysystem.models.Product;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class MainWindow implements Initializable {
    Stage stage;
    Parent scene;

    @FXML
    private Button addPartButton;
    @FXML
    private Button addProductButton;
    @FXML
    private Button deletePartButton;
    @FXML
    private Button deleteProductButton;
    @FXML
    private Button exitButton;
    @FXML
    private Button modifyPartButton;
    @FXML
    private Button modifyProductButton;

    @FXML
    private TableColumn<Part, Integer> partIDCol;
    @FXML
    private TableColumn<Part, Integer> partInvCol;
    @FXML
    private TableColumn<Part, String> partNameCol;
    @FXML
    private TableColumn<Part, Double> partPriceCol;
    @FXML
    private TableView<Part> partTable;
    @FXML
    private TableColumn<Product, Integer> productIDCol;
    @FXML
    private TableColumn<Product, Integer> productInvCol;
    @FXML
    private TableColumn<Product, String> productNameCol;
    @FXML
    private TableColumn<Product, Double> productPriceCol;
    @FXML
    private TableView<Product> productTable;

    @FXML
    private TextField searchPartText;
    @FXML
    private TextField searchProductText;

    boolean isInt(String str) {
        try {
            Integer.valueOf(str);
            return true;
        } catch (NumberFormatException e) { return false; }
    }

    private Part selectPart(int id) {
        for(Part p : Inventory.getAllParts()){
            if(p.getId() == id) {
                return p;
            }
        }
        return null;
    }
    private Product selectProduct(int id){
        for(Product p : Inventory.getAllProducts()){
            if(p.getId() == id) {
                return p;
            }
        }
        return null;
    }
    private ObservableList<Part> partFilter(String search){

        ObservableList<Part> temp = FXCollections.observableArrayList();
        if(search.isEmpty()) return Inventory.getAllParts();

        for(Part p : Inventory.getAllParts()) {
            if(p.getName().toLowerCase().contains(search.toLowerCase()))
            {
                temp.add(p);
            }
            if(isInt(search) && p.getId() == Integer.parseInt(search)) {
                temp.add(p);
            }
        }
        return temp;
    }
    private ObservableList<Product> productFilter(String search){

        ObservableList<Product> temp = FXCollections.observableArrayList();
        if(search.isEmpty()) return Inventory.getAllProducts();

        for(Product p : Inventory.getAllProducts()) {
            if(p.getName().toLowerCase().contains(search.toLowerCase()))
            {
                temp.add(p);
            }
            if(isInt(search) && p.getId() == Integer.parseInt(search)) {
                temp.add(p);
            }
        }
        return temp;
    }
    boolean deletePart(int id) {
        for(Part p : Inventory.getAllParts()){
            if(p.getId() == id) {
                return Inventory.getAllParts().remove(p);
            }
        }

        return false;
    }
    boolean deleteProduct(int id) {
        for(Product p : Inventory.getAllProducts()){
            if(p.getId() == id) {
                return Inventory.getAllProducts().remove(p);
            }
        }
        return false;
    }

    @FXML
    void onActionAddPart(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("AddPart.fxml")));
        stage.setScene(new Scene(scene));
        stage.setTitle("Add Part");
        stage.show();
    }

    @FXML
    void onActionAddProduct(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("AddProduct.fxml")));
        stage.setScene(new Scene(scene));
        stage.setTitle("Add Product");
        stage.show();
    }

    @FXML
    void onActionDeletePart(ActionEvent event) {
        System.out.println("Delete Part");
    }

    @FXML
    void onActionDeleteProduct(ActionEvent event) {
        System.out.println("Delete Product");
    }

    @FXML
    void onActionExit(ActionEvent event) {
        System.exit(0);
    }

    @FXML
    void onActionModifyPart(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("ModifyPart.fxml")));
        stage.setScene(new Scene(scene));
        stage.setTitle("Modify Part");
        stage.show();
    }

    @FXML
    void onActionModifyProduct(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("ModifyProduct.fxml")));
        stage.setScene(new Scene(scene));
        stage.setTitle("ModifyProduct");
        stage.show();
    }

    void displayData(ObservableList<Part> parts, ObservableList<Product> products) {
        partTable.setItems(parts);
        productTable.setItems(products);

        partIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        productIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        productNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        productInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        productPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        displayData(Inventory.getAllParts(),Inventory.getAllProducts());
        searchPartText.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                displayData(partFilter(searchPartText.getText()),productFilter(searchProductText.getText()));
            }
        });

        searchProductText.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                displayData(partFilter(searchPartText.getText()),productFilter(searchProductText.getText()));
            }
        });
    }
}
