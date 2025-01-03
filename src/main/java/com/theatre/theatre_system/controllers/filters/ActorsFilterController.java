package com.theatre.theatre_system.controllers.filters;

import com.theatre.theatre_system.controllers.MainController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;

public class ActorsFilterController {
    @FXML
    private AnchorPane actorsFilter;

    @FXML
    private Button closeMenuButton;

    @FXML
    private Button clearFiltersButton;

    @FXML
    private RadioButton allRadioButton;

    @FXML
    private RadioButton femaleRadioButton;

    @FXML
    private RadioButton maleRadioButton;

    @FXML
    private CheckBox contraltoCheckBox;

    @FXML
    private CheckBox mezzoSopranoCheckBox;

    @FXML
    private CheckBox sopranoCheckBox;

    @FXML
    private CheckBox bassCheckBox;

    @FXML
    private CheckBox baritoneCheckBox;

    @FXML
    private CheckBox tenorCheckBox;

    @FXML
    void initialize() {
        ToggleGroup genderGroup = new ToggleGroup();

        allRadioButton.setToggleGroup(genderGroup);
        femaleRadioButton.setToggleGroup(genderGroup);
        maleRadioButton.setToggleGroup(genderGroup);
    }

}
