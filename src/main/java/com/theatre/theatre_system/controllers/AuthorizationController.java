package com.theatre.theatre_system.controllers;

import com.theatre.theatre_system.Main;
import com.theatre.theatre_system.MainRecord;
import com.theatre.theatre_system.database.DBConnector;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public class AuthorizationController {
    @FXML
    private TextField login;

    @FXML
    private PasswordField password;

    @FXML
    private Button loginButton;

    Connection connection;


    @FXML
    public void enter() throws IOException, SQLException {
        DBConnector connector = new DBConnector();
        connection = connector.connect(login.getText(), password.getText());
        MainRecord.connection = connection;
        if (connection != null) {
            Main.getPrimaryStage().close();
            Main.setPrimaryStage(new Stage());
            Main.switchScenes(Main.getPrimaryStage(), "FXML/main-view.fxml", "Главная");
        }
    }
}
