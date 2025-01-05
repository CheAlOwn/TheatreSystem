package com.theatre.theatre_system.controllers.filters;

import com.theatre.theatre_system.controllers.MainController;
import com.theatre.theatre_system.database.dao.TourDAO;
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class ToursFilterController extends MainController {
    @FXML
    private AnchorPane toursFilter;
    @FXML
    private TextField startDateFrom, startDateTo, endDateFrom, endDateTo;

    Logger log = Logger.getLogger(getClass().getName());

    private TourDAO tourDAO = new TourDAO();
    private static final String BASE_QUERY = "SELECT * FROM tours WHERE ";
    private String query = "";

    @FXML
    void initialize() {
        // Привязываем слушатели к полям
        bindActions(startDateFrom, startDateTo, endDateFrom, endDateTo);
    }

    private void bindActions(TextField... textFields) {
        for (TextField tf : textFields) {
            tf.textProperty().addListener((obs, oldVal, newVal) -> applyFiltersSafely());
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
        List<String> conditions = new ArrayList<>();

        addCondition(conditions, "start_date >= ", startDateFrom.getText().trim(), true);
        addCondition(conditions, "start_date <= ", startDateTo.getText().trim(), true);
        addCondition(conditions, "end_date >= ", endDateFrom.getText().trim(), true);
        addCondition(conditions, "end_date <= ", endDateTo.getText().trim(), true);

        if (conditions.isEmpty()) {
            // Применяем фильтры без условий, например, показываем все записи
            setColumns(tourDAO.findAll());
        } else {
            query = BASE_QUERY + String.join(" AND ", conditions);
            setColumns(getDataByQuery(query));
        }
        System.out.println(query); // Для отладки
    }

    private void addCondition(List<String> conditions, String conditionPrefix, String value, boolean isString) {
        if (!value.isEmpty()) {
            conditions.add(conditionPrefix + (isString ? "'" + value + "'" : value));
        }
    }
}
