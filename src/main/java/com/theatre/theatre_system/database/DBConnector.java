package com.theatre.theatre_system.database;

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
            if (usr.equals(getUser()) && pwd.equals(getPassword())) {
                Class.forName(getDriver());
                connection = DriverManager.getConnection(getURL(), usr, pwd);

                log.info("Connection established successfully");
            } else {
                log.info("Invalid");
                connection = null;
            }
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
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
