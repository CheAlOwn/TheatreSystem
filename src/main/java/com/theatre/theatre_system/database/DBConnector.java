package com.theatre.theatre_system.database;

import com.theatre.theatre_system.MainRecord;
import javafx.scene.control.Alert;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;

import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Logger;

public class DBConnector {
    private static final Properties PROPERTIES = new Properties();
    private static final List<String> users = new ArrayList<>();
    private static final List<String> pwds = new ArrayList<>();
    Logger log = Logger.getLogger(getClass().getName());

    public DBConnector() throws IOException {
        PROPERTIES.load(new FileReader("src/main/resources/com/theatre/theatre_system/config.properties"));
        users.addAll(List.of(getUsers().split(", ")));
        pwds.addAll(List.of(getPasswords().split(", ")));
    }

    public Connection connect(String usr, String pwd) {
        Connection connection = null;
        try {
            int counter = 0;
            boolean isConnected = false;
            for (String user: users) {
                if (user.equals(usr) && pwd.equals(pwds.get(counter))) {
                    Class.forName(getDriver());
                    connection = DriverManager.getConnection(getURL(), usr, pwd);
                    log.info("Connection established successfully");
                    MainRecord.user = connection.getMetaData().getUserName();
                    isConnected = true;
                    break;
                }
                counter++;
            }

            if (!isConnected) sendAlert("Incorrect login or password, please try again!");

        } catch (Exception e) {
            sendAlert("Invalid connection");
            connection = null;
        }

        return connection;
    }

    private void sendAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setContentText(message);
        alert.show();
        log.info(message);
    }

    private String getURL() {
        return PROPERTIES.getProperty("url");
    }

    private String getDriver() {
        return PROPERTIES.getProperty("driver");
    }

    private String getUsers() {
        return PROPERTIES.getProperty("users");
    }

    private String getPasswords() {
        return PROPERTIES.getProperty("passwords");
    }
}
