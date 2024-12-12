module gui {
    requires com.google.gson;
    requires javafx.controls;
    requires javafx.fxml;

    opens gui to javafx.fxml;
    opens Services to com.google.gson;
    opens Entity to com.google.gson;
    exports gui;
}
