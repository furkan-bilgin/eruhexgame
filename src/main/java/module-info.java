module com.example.eruhexgame {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.eruhexgame to javafx.fxml;
    exports com.eruhexgame;
}