module com.example.eruhexgame {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.eruhexgame to javafx.fxml;
    exports com.example.eruhexgame;
}