package com.theatre.theatresystem.controllers.filters;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;

public class ActorsFilterController {
    public AnchorPane actorsFilter;
    public Button closeMenuButton;
    public Button clearFiltersButton;
    public RadioButton allRadioButton;
    public RadioButton femaleRadioButton;
    public RadioButton maleRadioButton;
    public CheckBox contraltoCheckBox;
    public CheckBox mezzoSopranoCheckBox;
    public CheckBox sopranoCheckBox;
    public CheckBox bassCheckBox;
    public CheckBox baritoneCheckBox;
    public CheckBox tenorCheckBox;

    @FXML
    void initialize() {
        ToggleGroup genderGroup = new ToggleGroup();

        allRadioButton.setToggleGroup(genderGroup);
        femaleRadioButton.setToggleGroup(genderGroup);
        maleRadioButton.setToggleGroup(genderGroup);
    }
}
