package com.theatre.theatre_system;

import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;
import javafx.stage.PopupWindow;

import java.sql.Connection;
import java.util.logging.Logger;

public record MainRecord() {
    public static Connection connection;
    public static Rectangle overlayPane;
    public static AnchorPane filtersPane;
}
