module sealey.javafxinventorysystem {
    requires javafx.controls;
    requires javafx.fxml;


    opens sealey.javafxinventorysystem to javafx.fxml;
    exports sealey.javafxinventorysystem;
}