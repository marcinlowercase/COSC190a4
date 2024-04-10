module org.example.cosc190a4 {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.fasterxml.jackson.databind;
    requires java.sql;

    opens org.example.cosc190a4 to javafx.fxml;
    exports org.example.cosc190a4;
}