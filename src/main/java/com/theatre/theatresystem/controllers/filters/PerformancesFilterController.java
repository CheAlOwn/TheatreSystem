package com.theatre.theatresystem.controllers.filters;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;

public class PerformancesFilterController {
    public AnchorPane performancesFilter;
    public Button closeMenuButton;
    public Button clearFiltersButton;
    public RadioButton adultsRadioButton;
    public RadioButton youngerRadioButton;
    public RadioButton kidsRadioButton;

    @FXML
    void initialize() {
        ToggleGroup genderGroup = new ToggleGroup();

        adultsRadioButton.setToggleGroup(genderGroup);
        youngerRadioButton.setToggleGroup(genderGroup);
        kidsRadioButton.setToggleGroup(genderGroup);
    }
}
