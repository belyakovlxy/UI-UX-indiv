module com.example.indiv {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.xml;


    opens com.example.indiv to javafx.fxml;
    exports com.example.indiv;
    exports com.example.indiv.entities;
    opens com.example.indiv.entities to javafx.fxml;
}