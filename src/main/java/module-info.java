module com.theatre.theatre_system {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.postgresql.jdbc;


    opens com.theatre.theatre_system to javafx.fxml;
    exports com.theatre.theatre_system;

    opens com.theatre.theatre_system.controllers  to javafx.fxml;
    exports com.theatre.theatre_system.controllers;
}