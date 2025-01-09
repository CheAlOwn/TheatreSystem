package com.theatre.theatre_system;

import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;
import javafx.stage.PopupWindow;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Logger;

public record MainRecord() {
    public static TableView<ObservableList<Object>> table;
    public static Connection connection;
    public static AnchorPane form;
    public static FXMLLoader nodeLoader;
    public static String user;
}
