package sealey.javafxinventorysystem;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sealey.javafxinventorysystem.models.*;
import sealey.javafxinventorysystem.utility.Helpers;

import java.io.IOException;
/**
* The Main class sets the initial scene and launches the application.
*
* <p>The javadoc folder is located in the top level directory of the project (JavaFX-InventorySystem/javadoc).</p>
*
* <p><b>
* FUTURE ENHANCEMENT: To extend the functionality of the application, I would add a way to save the adjustments
* made to the data upon exiting the program. This could be done via interacting with a relational database or creating a
* csv file to store the data.
* </b></p>
 * @author Max Sealey
* */

public class Main extends Application {

    /**
    * The start() method retrieves the FXML file for the main window and sets the scene. Calls the testData method, which can be removed or replaced.
    *
    * @param stage Stage to contain the scenes
    * @throws IOException Catches issues and displays an error message if there is a problem starting the program
    * */
    @Override
    public void start(Stage stage) throws IOException {

        Helpers.testData();

        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("MainWindow.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 400);
        stage.setTitle("Inventory Management System");
        stage.setScene(scene);
        stage.show();
    }

    /**
    * The main() method is used to run the program.
    *
    * @param args String arguments that may be passed in. Will not be utilized in this program.
    * */
    public static void main(String[] args) {

        launch(args);
    }
}
