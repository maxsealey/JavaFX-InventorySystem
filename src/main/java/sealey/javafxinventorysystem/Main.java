package sealey.javafxinventorysystem;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sealey.javafxinventorysystem.models.*;

import java.io.IOException;
/*
* @author Max Sealey
*
* The Main class sets the initial scene and launches the application. The javadoc folder is located
* in the top level directory of the project (JavaFX-InventorySystem/javadoc).
* */

public class Main extends Application {

    /*
    * The start() method retrieves the FXML file for the main window and sets the scene.
    *
    * @param stage Stage that will display the initial scene
    * @throws IOException Catches issues and displays an error message if there is a problem initializing the program
    * */
    @Override
    public void start(Stage stage) throws IOException {

        testData();

        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("MainWindow.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 400);
        stage.setTitle("Inventory Management System");
        stage.setScene(scene);
        stage.show();
    }

    private static void testData() {

        OutSourced part1 = new OutSourced(1, "wheel", 34.99, 16, 5, 30);
        OutSourced part2 = new OutSourced(2, "brake", 29.99, 18, 10, 25);
        OutSourced part3 = new OutSourced(3, "headlight", 49.99, 18, 10, 25);
        InHouse part4 = new InHouse(4, "handlebars", 19.99, 5, 5, 15);
        InHouse part5 = new InHouse(5, "chain", 64.99, 9, 7, 20);
        InHouse part6 = new InHouse(6, "steering wheel", 54.99, 9, 7, 20);

        Product prod1 = new Product(1, "Bicycle", 149.99, 12, 8, 20);
        Product prod2 = new Product(2, "Unicycle", 134.99, 6, 3, 10);
        Product prod3 = new Product(3, "Kid's Trike", 89.99, 15, 8, 30);

        part1.setCompanyName("Headgum");
        part2.setCompanyName("OMSB");
        part3.setCompanyName("Zona Gale");
        part4.setMachineId(87);
        part5.setMachineId(32);
        part6.setMachineId(99);

        prod1.addAssociatedPart(part4);
        prod1.addAssociatedPart(part3);

        prod2.addAssociatedPart(part3);
        prod2.addAssociatedPart(part2);
        prod2.addAssociatedPart(part5);

        prod3.addAssociatedPart(part2);
        prod3.addAssociatedPart(part1);
        prod3.addAssociatedPart(part3);
        prod3.addAssociatedPart(part5);
        prod3.addAssociatedPart(part6);

        ObservableList<Part> parts = FXCollections.observableArrayList(part1,part2,part3,part4,part5,part6);
        Inventory.setAllParts(parts);

        ObservableList<Product> products = FXCollections.observableArrayList(prod1,prod2,prod3);
        Inventory.setAllProducts(products);
    }
    /*
    * The main() method is used to run the program.
    *
    * @param args String arguments that may be passed in. Will not be utilized in this program.
    * */
    public static void main(String[] args) {

        launch(args);
    }
}
