package com.theatre.theatresystem.controllers.filters;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;

public class TicketsFilterController {
    public AnchorPane ticketsFilter;
    public Button closeMenuButton;
    public Button clearFiltersButton;
    public RadioButton availableRadioButton;
    public RadioButton soldRadioButton;
    public RadioButton reservedRadioButton;

    @FXML
    void initialize() {
        ToggleGroup genderGroup = new ToggleGroup();

        availableRadioButton.setToggleGroup(genderGroup);
        soldRadioButton.setToggleGroup(genderGroup);
        reservedRadioButton.setToggleGroup(genderGroup);
    }
}
