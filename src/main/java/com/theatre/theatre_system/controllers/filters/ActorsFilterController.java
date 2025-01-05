package com.theatre.theatre_system.controllers.filters;

import com.theatre.theatre_system.controllers.MainController;
import com.theatre.theatre_system.database.dao.ActorDAO;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class ActorsFilterController extends MainController {
    @FXML
    private TextField startAge, endAge, startHeight, endHeight;

    @FXML
    private Label tenorText, baritoneText, bassText, contraltoText, mezzoSopranoText, sopranoText;

    @FXML
    private FlowPane baritonePane, bassPane, contraltoPane, mezzoSopranoPane, sopranoPane, tenorPane;

    @FXML
    private AnchorPane actorsFilter;

    @FXML
    private RadioButton allRadioButton, femaleRadioButton, maleRadioButton;

    @FXML
    private CheckBox contraltoCheckBox, mezzoSopranoCheckBox, sopranoCheckBox, bassCheckBox, baritoneCheckBox, tenorCheckBox;

    Logger log = Logger.getLogger(getClass().getName());

    private final ActorDAO actorDAO = new ActorDAO();
    private final List<CheckBox> timbres = new ArrayList<>();
    private final List<RadioButton> genders = new ArrayList<>();
    private String query = "";
    private static final String WARP = "SELECT a.* FROM actors a JOIN employees e ON a.employee_id = e.employee_id WHERE ";

    @FXML
    void initialize() {
        configureGenderToggleGroup(allRadioButton, femaleRadioButton, maleRadioButton);
        configureCheckBoxList(contraltoCheckBox, mezzoSopranoCheckBox, sopranoCheckBox, bassCheckBox, baritoneCheckBox, tenorCheckBox);
        bindTextFieldListeners(startAge, endAge, startHeight, endHeight);

        timbres.forEach(box -> box.selectedProperty().addListener(event -> applyFiltersSafely()));
        genders.forEach(rb -> rb.selectedProperty().addListener(event -> applyFiltersSafely()));
    }

    private void configureGenderToggleGroup(RadioButton... buttons) {
        ToggleGroup genderGroup = new ToggleGroup();
        for (RadioButton button : buttons) {
            button.setToggleGroup(genderGroup);
        }
        allRadioButton.setSelected(true);
        genders.addAll(List.of(buttons));
    }

    private void configureCheckBoxList(CheckBox... boxes) {
        timbres.addAll(List.of(boxes));
    }

    private void bindTextFieldListeners(TextField... textFields) {
        for (TextField tf : textFields) {
            tf.textProperty().addListener(event -> applyFiltersSafely());
        }
    }

    private void applyFiltersSafely() {
        try {
            applyFilters();
        } catch (SQLException e) {
            log.info(e.getMessage());
        }
    }

    private void applyFilters() throws SQLException {
        query = buildQuery();
        if (query.isEmpty()) {
            setColumns(actorDAO.findAll());
        } else {
            setColumns(getDataByQuery(query));
        }
        System.out.println(query);
    }

    private String buildQuery() throws SQLException {
        List<String> conditions = new ArrayList<>();

        String timbreCondition = buildTimbreCondition();
        if (!timbreCondition.isEmpty()) conditions.add(timbreCondition);

        String genderCondition = buildGenderCondition();
        if (!genderCondition.isEmpty()) conditions.add(genderCondition);

        String heightCondition = buildRangeCondition("a.height", startHeight.getText(), endHeight.getText());
        if (!heightCondition.isEmpty()) conditions.add(heightCondition);

        String ageCondition = buildRangeCondition("DATE_PART('year', AGE(e.birthday))", startAge.getText(), endAge.getText());
        if (!ageCondition.isEmpty()) conditions.add(ageCondition);

        return conditions.isEmpty() ? "" : WARP + String.join(" AND ", conditions);
    }

    private String buildTimbreCondition() {
        List<String> selectedTimbres = timbres.stream()
                .filter(CheckBox::isSelected)
                .filter(box -> !box.isDisable())
                .map(box -> "a.voice_type = '" + box.getAccessibleText() + "'")
                .collect(Collectors.toList());

        return selectedTimbres.isEmpty() ? "" : "(" + String.join(" OR ", selectedTimbres) + ")";
    }

    private String buildGenderCondition() {
        return genders.stream()
                .filter(RadioButton::isSelected)
                .filter(button -> !button.getAccessibleText().equals("Все"))
                .map(button -> "e.gender = '" + button.getAccessibleText() + "'")
                .findFirst()
                .orElse("");
    }

    private String buildRangeCondition(String columnName, String startValue, String endValue) {
        List<String> conditions = new ArrayList<>();
        if (!startValue.isEmpty()) conditions.add(columnName + " >= " + startValue.trim());
        if (!endValue.isEmpty()) conditions.add(columnName + " <= " + endValue.trim());
        return conditions.isEmpty() ? "" : "(" + String.join(" AND ", conditions) + ")";
    }
}
