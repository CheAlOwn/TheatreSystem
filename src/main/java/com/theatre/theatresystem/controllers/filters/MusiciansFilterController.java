package com.theatre.theatresystem.controllers.filters;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;

public class MusiciansFilterController {
    public AnchorPane musiciansFilter;
    public Button closeMenuButton;
    public Button clearFiltersButton;
    public RadioButton allRadioButton;
    public RadioButton femaleRadioButton;
    public RadioButton maleRadioButton;

    @FXML
    void initialize() {
        ToggleGroup genderGroup = new ToggleGroup();

        allRadioButton.setToggleGroup(genderGroup);
        femaleRadioButton.setToggleGroup(genderGroup);
        maleRadioButton.setToggleGroup(genderGroup);
    }
}
