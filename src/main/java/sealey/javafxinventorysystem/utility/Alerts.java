package sealey.javafxinventorysystem.utility;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;

/**
 * The Alerts class contains the methods used to notify the user of errors and confirm destructive/altering actions
 *
 * @author Max Sealey
 */
public class Alerts {

    /**
     * displays alert and that sets title, content, and alert type
     *
     * @param title Alert title
     * @param content Alert message
     * @param type Alert type
     * */
    public static void genericMessage(String title, String content, Alert.AlertType type) {

        Alert alert = new Alert(Alert.AlertType.NONE);
        alert.setAlertType(type);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }

    /**
     * Calls generic message and creates simple error message
     *
     * @param content message to be displayed
     * */
    public static void errorMessage(String content) {

        genericMessage("Something went wrong.",content, Alert.AlertType.WARNING);
    }

    /**
     * Notifies user that input values are not in the correct range
     */
    public static void invalidStockValues()
    {
        genericMessage("Invalid Input","Inventory Level must be between Min and Max. All must be positive numbers.", Alert.AlertType.ERROR);
    }

    /**
     * Notifies user of bad input
     */
    public static void invalidInput()
    {
        genericMessage("Invalid Input","Please enter only valid values in each box. " +
                "Inventory Level, Min, Max, and (if applicable) Machine ID must be whole numbers.", Alert.AlertType.ERROR);
    }

    /**
     * Notifies user that no results were found
     */
    public static void noResults() {
        genericMessage("404","Your search did not return any results. Please try again.", Alert.AlertType.INFORMATION);
    }

    /**
     * Displays alert asking for confirmation that item should be deleted, returns true if Ok button clicked, false otherwise
     *
     * @return boolean true or false
     * */
    public static boolean deleteConfirmation(){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Confirm Deletion");
        alert.setContentText("Click 'Ok' to proceed.");
        alert.setHeaderText("Are you sure you want to delete this item?");

        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == ButtonType.OK) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Displays alert asking for confirmation that item should be deleted, returns true if Ok button clicked, false otherwise
     *
     * @return boolean true or false
     * */
    public static boolean removeConfirmation(){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Confirm Part Removal");
        alert.setContentText("Click 'Ok' to proceed.");
        alert.setHeaderText("Are you sure you want to remove this part from the product?");

        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == ButtonType.OK) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * couldNotDelete() displays an alert when an item is unable to be deleted. Called in the delete methods
     *
     * @param message delete message
     * */
    public static void couldNotDelete(String message){

        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Unable to delete");
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Prompts the user to confirm save
     *
     * @return alert returns alert object for result
     **/
    public static Alert confirmSave(){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Confirm Save");
        alert.setContentText("Click 'Ok' to save changes.");
        alert.setHeaderText("Are you sure you want to save?");
        return alert;
    }


    /**
     * Prompts the user to confirm whether they want to exit the program.
     */
    public static void confirmExit() {

        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Confirm Exit");
        alert.setHeaderText("Are you sure you want to exit?");
        alert.setContentText("Changes to your data may not be saved. Click 'Ok' to continue.");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            System.exit(0);
        }
    }
}
