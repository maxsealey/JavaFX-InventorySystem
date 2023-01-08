module sealey.javafxinventorysystem {
    requires javafx.controls;
    requires javafx.fxml;

    opens sealey.javafxinventorysystem to javafx.fxml;
    opens sealey.javafxinventorysystem.models to javafx.base;
    exports sealey.javafxinventorysystem;
}