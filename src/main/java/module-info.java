module com.example.upodhotelproject {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;
    requires java.sql;

    opens com.example.upodhotelproject to javafx.fxml;
    exports com.example.upodhotelproject;
}