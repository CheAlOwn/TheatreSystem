package com.theatre.theatre_system.database;

import com.theatre.theatre_system.MainRecord;
import javafx.scene.control.Alert;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;

import java.sql.DriverManager;
import java.util.Properties;
import java.util.logging.Logger;

public class DBConnector {
    private static final Properties PROPERTIES = new Properties();
    Logger log = Logger.getLogger(getClass().getName());

    public DBConnector() throws IOException {
        PROPERTIES.load(new FileReader("src/main/resources/com/theatre/theatre_system/config.properties"));
    }

    public Connection connect(String usr, String pwd) {
        Connection connection;
        try {
            Class.forName(getDriver());
            connection = DriverManager.getConnection(getURL(), usr, pwd);
            log.info("Connection established successfully");
            MainRecord.user = connection.getMetaData().getUserName();

        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Invalid, please try again!");
            alert.show();
            log.info("Invalid");
            connection = null;
        }

        return connection;
    }

    private String getURL() {
        return PROPERTIES.getProperty("url");
    }

    private String getDriver() {
        return PROPERTIES.getProperty("driver");
    }

    private String getUser() {
        return PROPERTIES.getProperty("username");
    }

    private String getPassword() {
        return PROPERTIES.getProperty("password");
    }
}
