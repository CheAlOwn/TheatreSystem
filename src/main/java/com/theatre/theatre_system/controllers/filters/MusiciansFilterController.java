package com.theatre.theatre_system.controllers.filters;

import com.theatre.theatre_system.controllers.MainController;
import com.theatre.theatre_system.database.dao.MusicianDAO;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class MusiciansFilterController extends MainController {
    @FXML
    private CheckBox aGuitar, cello, guitar, clarinet, organ, piano, violin, trombone, tube, flute, fPiano;

    @FXML
    private AnchorPane musiciansFilter;

    @FXML
    private RadioButton allRadioButton, femaleRadioButton, maleRadioButton;

    Logger log = Logger.getLogger(getClass().getName());

    private final MusicianDAO musicianDAO = new MusicianDAO();
    private final List<CheckBox> instruments = new ArrayList<>();
    private final List<RadioButton> genders = new ArrayList<>();
    private String query = "";
    private static final String WARP = "SELECT m.* FROM musicians m JOIN employees e ON m.employee_id = e.employee_id WHERE ";

    @FXML
    void initialize() {
        configureGenderToggleGroup(allRadioButton, femaleRadioButton, maleRadioButton);
        configureInstrumentsList(aGuitar, cello, guitar, clarinet, organ, piano, violin, trombone, tube, flute, fPiano);

        instruments.forEach(box ->
                box.selectedProperty().addListener(event -> applyFilterSafely()));

        genders.forEach(rb ->
                rb.selectedProperty().addListener(event -> applyFilterSafely()));
    }

    private void configureGenderToggleGroup(RadioButton... buttons) {
        ToggleGroup genderGroup = new ToggleGroup();
        for (RadioButton button : buttons) {
            button.setToggleGroup(genderGroup);
        }
        allRadioButton.setSelected(true);
        genders.addAll(List.of(buttons));
    }

    private void configureInstrumentsList(CheckBox... boxes) {
        instruments.addAll(List.of(boxes));
    }

    private void applyFilterSafely() {
        try {
            applyFilters();
        } catch (SQLException e) {
            log.info(e.getMessage());
        }
    }

    private void applyFilters() throws SQLException {
        query = buildQuery();
        if (query.isEmpty()) {
            setColumns(musicianDAO.findAll());
        } else {
            setColumns(getDataByQuery(query));
        }
        System.out.println(query);
    }

    private String buildQuery() throws SQLException {
        List<String> conditions = new ArrayList<>();

        String instrumentCondition = buildInstrumentCondition();
        if (!instrumentCondition.isEmpty()) {
            conditions.add(instrumentCondition);
        }

        String genderCondition = buildGenderCondition();
        if (!genderCondition.isEmpty()) {
            conditions.add(genderCondition);
        }

        return conditions.isEmpty() ? "" : WARP + String.join(" AND ", conditions);
    }

    private String buildInstrumentCondition() {
        List<String> selectedInstruments = instruments.stream()
                .filter(CheckBox::isSelected)
                .filter(box -> !box.isDisable())
                .map(box -> "m.instrument = '" + box.getAccessibleText() + "'")
                .collect(Collectors.toList());

        return selectedInstruments.isEmpty() ? "" : "(" + String.join(" OR ", selectedInstruments) + ")";
    }

    private String buildGenderCondition() {
        return genders.stream()
                .filter(RadioButton::isSelected)
                .filter(button -> !button.getAccessibleText().equals("Все"))
                .map(button -> "e.gender = '" + button.getAccessibleText() + "'")
                .findFirst()
                .orElse("");
    }
}
