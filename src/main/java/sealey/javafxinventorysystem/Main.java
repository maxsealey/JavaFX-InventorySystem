package sealey.javafxinventorysystem;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sealey.javafxinventorysystem.models.*;

import java.io.IOException;

public class Main extends Application {
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

        InHouse part3 = new InHouse(3, "handlebars", 39.99, 5, 5, 15);
        InHouse part4 = new InHouse(4, "chain", 14.99, 9, 7, 20);

        Product prod1 = new Product(1, "Bicycle", 149.99, 12, 8, 20);
        Product prod2 = new Product(2, "Unicycle", 134.99, 6, 3, 10);
        Product prod3 = new Product(3, "Kid's Trike", 89.99, 15, 8, 30);

        part1.setCompanyName("Headgum");
        part2.setCompanyName("OMSB");

        Inventory.addPart(part1);
        Inventory.addPart(part2);
        Inventory.addPart(part3);
        Inventory.addPart(part4);

        Inventory.addProduct(prod1);
        Inventory.addProduct(prod2);
        Inventory.addProduct(prod3);
    }
    public static void main(String[] args) {
        launch();
    }
}
