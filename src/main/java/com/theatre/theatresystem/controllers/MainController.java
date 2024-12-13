package com.theatre.theatresystem.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class MainController {
    public Button menuButton;
    public Button searchButton;
    public Button filterButton;
    public Pane overlayPane;
    public TableView tableOutData;
    public Button closeMenuButton;
    public FlowPane actionsPane;
    public Button addObjectButton;
    public AnchorPane menuPane;
    public VBox menuVBox;
    public AnchorPane filtersPane;
    public AnchorPane formsPane;
    public ComboBox parametersBox;
    public TextField searchTextField;
    public FlowPane addRecordPane;
    public Button editObjectButton;
    public Button removeObjectButton;

    @FXML
    void initialize() {
        GaussianBlur gaussianBlur = new GaussianBlur();
        gaussianBlur.setRadius(20);

        DropShadow dropShadow = new DropShadow();
        dropShadow.setOffsetX(0);
        dropShadow.setOffsetY(0);
        dropShadow.setBlurType(BlurType.GAUSSIAN);
        dropShadow.setColor(Color.rgb(0, 0, 0, 0.25));
        dropShadow.setSpread(-20);
        dropShadow.setRadius(30);

        actionsPane.setEffect(gaussianBlur);
        actionsPane.setEffect(dropShadow);

        addRecordPane.setEffect(gaussianBlur);
        addRecordPane.setEffect(dropShadow);
    }
}
