module com.theatre.theatresystem {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.theatre.theatresystem  to javafx.fxml;
    exports com.theatre.theatresystem;

    opens com.theatre.theatresystem.controllers  to javafx.fxml;
    exports com.theatre.theatresystem.controllers;
}