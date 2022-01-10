module com.example.xogame {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.jfoenix;
    requires org.json;


    opens com.example.xogame to javafx.fxml;
    exports com.example.xogame;
}