package com.theatre.theatre_system.controllers.filters;

import com.theatre.theatre_system.MainRecord;
import com.theatre.theatre_system.TableViewTools;
import com.theatre.theatre_system.controllers.MainController;
import com.theatre.theatre_system.database.Queries;
import com.theatre.theatre_system.database.dao.EmployeeDAO;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class EmployeesFilterController extends MainController {
    @FXML
    private TextField startSalary, endSalary, startCount, endCount, startExp, endExp;

    @FXML
    private TextField startBirthdate, endBirthdate, startAge, endAge;

    @FXML
    private FlowPane actorChips, musicianChips, directorChips, staffChips;

    @FXML
    private AnchorPane employeesFilter;

    @FXML
    private ScrollPane chipsScrollPane, mainScrollPane;

    @FXML
    private RadioButton allRadioButton, femaleRadioButton, maleRadioButton;

    Logger log = Logger.getLogger(getClass().getName());

    private final EmployeeDAO employeeDAO = new EmployeeDAO();
    private static final String BASE_QUERY = Queries.EMPLOYEE_QUERY + " WHERE ";
    private String query = "";

    private final List<FlowPane> categories = new ArrayList<>();
    private final List<RadioButton> genders = new ArrayList<>();

    @FXML
    void initialize() {
        configureScrolling();
        configureRadioButtons();
        configureCategories();
        bindTextFields(startSalary, endSalary, startCount, endCount, startExp, endExp,
                startBirthdate, endBirthdate, startAge, endAge);
    }

    private void configureScrolling() {
        chipsScrollPane.setOnScroll(event -> {
            if (chipsScrollPane.contains(event.getX(), event.getY())) {
                event.consume();
                chipsScrollPane.setHvalue(chipsScrollPane.getHvalue() - event.getDeltaY() / chipsScrollPane.getWidth());
            } else {
                mainScrollPane.setVvalue(mainScrollPane.getVvalue() - event.getDeltaY() / mainScrollPane.getHeight());
            }
        });
    }

    private void configureRadioButtons() {
        ToggleGroup genderGroup = new ToggleGroup();
        allRadioButton.setToggleGroup(genderGroup);
        allRadioButton.setSelected(true);
        femaleRadioButton.setToggleGroup(genderGroup);
        maleRadioButton.setToggleGroup(genderGroup);

        genders.add(allRadioButton);
        genders.add(femaleRadioButton);
        genders.add(maleRadioButton);

        genders.forEach(rb -> rb.selectedProperty().addListener(event -> applyFilters()));
    }

    private void configureCategories() {
        categories.addAll(List.of(actorChips, staffChips, directorChips, musicianChips));
        categories.forEach(chip -> chip.setOnMouseClicked(this::toggleChip));
    }

    private void bindTextFields(TextField... fields) {
        for (TextField field : fields) {
            field.textProperty().addListener(event -> applyFilters());
        }
    }

    private void toggleChip(MouseEvent event) {
        Node source = (Node) event.getSource();
        String activeClass = "active";

        if (source.getStyleClass().contains(activeClass)) {
            source.getStyleClass().remove(activeClass);
        } else {
            source.getStyleClass().add(activeClass);
        }
        applyFilters();
    }

    private void applyFilters() {
        try {
            List<String> conditions = new ArrayList<>();

            addCategoryConditions(conditions);
            addGenderCondition(conditions);
            addRangeCondition(conditions, startAge.getText(), endAge.getText(), "DATE_PART('year', AGE(birthday))");
            addRangeCondition(conditions, startBirthdate.getText(), endBirthdate.getText(), "EXTRACT(YEAR FROM birthday)");
            addRangeCondition(conditions, startExp.getText(), endExp.getText(), "experience");
            addRangeCondition(conditions, startCount.getText(), endCount.getText(), "children_count");
            addRangeCondition(conditions, startSalary.getText(), endSalary.getText(), "salary");

            if (conditions.isEmpty()) {
                TableViewTools.fillTableView(MainRecord.table, Queries.EMPLOYEE_QUERY);
            } else {
                query = BASE_QUERY + String.join(" AND ", conditions);
                TableViewTools.fillTableView(MainRecord.table, query);
            }
        } catch (SQLException e) {
            log.info(e.getMessage());
        }
    }

    private void addCategoryConditions(List<String> conditions) {
        List<String> selectedCategories = new ArrayList<>();

        for (FlowPane chip : categories) {
            if (chip.getStyleClass().contains("active")) {
                selectedCategories.add("category = '" + chip.getAccessibleText() + "'");
            }
        }

        if (!selectedCategories.isEmpty()) {
            conditions.add("(" + String.join(" OR ", selectedCategories) + ")");
        }
    }

    private void addGenderCondition(List<String> conditions) {
        RadioButton selectedButton = genders.stream().filter(RadioButton::isSelected).findFirst().orElse(null);

        if (selectedButton != null && !"Все".equals(selectedButton.getAccessibleText())) {
            conditions.add("gender = '" + selectedButton.getAccessibleText() + "'");
        }
    }

    private void addRangeCondition(List<String> conditions, String start, String end, String column) {
        if (!start.isEmpty() || !end.isEmpty()) {
            StringBuilder condition = new StringBuilder();

            if (!start.isEmpty()) {
                condition.append(column).append(" >= ").append(start);
            }

            if (!end.isEmpty()) {
                if (condition.length() > 0) {
                    condition.append(" AND ");
                }
                condition.append(column).append(" <= ").append(end);
            }

            conditions.add("(" + condition + ")");
        }
    }
}
