package com.theatre.theatre_system.controllers.filters;

import com.theatre.theatre_system.MainRecord;
import com.theatre.theatre_system.TableViewTools;
import com.theatre.theatre_system.controllers.MainController;
import com.theatre.theatre_system.database.Queries;
import com.theatre.theatre_system.database.dao.TicketDAO;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import static com.theatre.theatre_system.TableViewTools.fillTableView;
import static com.theatre.theatre_system.TableViewTools.getDataByQuery;

public class TicketsFilterController extends MainController {
    @FXML
    private CheckBox available, sold, reserved;

    @FXML
    private AnchorPane ticketsFilter;

    @FXML
    private TextField minPrice, maxPrice, startDate, endDate;

    Logger log = Logger.getLogger(getClass().getName());

    private final TicketDAO ticketDAO = new TicketDAO();
    private static final String BASE_QUERY = Queries.TICKET_QUERY + " WHERE ";
    private String query = "";

    @FXML
    void initialize() {
        bindActions(minPrice, maxPrice, startDate, endDate);
        bindCheckBoxes(available, sold, reserved);
    }

    private void bindActions(TextField... textFields) {
        for (TextField tf : textFields) {
            tf.textProperty().addListener((obs, oldVal, newVal) -> applyFiltersSafely());
        }
    }

    private void bindCheckBoxes(CheckBox... checkBoxes) {
        for (CheckBox cb : checkBoxes) {
            cb.selectedProperty().addListener((obs, oldVal, newVal) -> applyFiltersSafely());
        }
    }

    private void applyFiltersSafely() {
        applyFilters();
    }

    private void applyFilters() {
        List<String> conditions = new ArrayList<>();
        List<String> statusConditions = new ArrayList<>();

        // Добавляем условия для чекбоксов
        addStatusCondition(statusConditions, "status = 'Доступен'", available.isSelected());
        addStatusCondition(statusConditions, "status = 'Продан'", sold.isSelected());
        addStatusCondition(statusConditions, "status = 'Забронирован'", reserved.isSelected());

        if (!statusConditions.isEmpty()) {
            conditions.add("(" + String.join(" OR ", statusConditions) + ")");
        }

        // Добавляем остальные условия
        addCondition(conditions, "price >= ", minPrice.getText().trim(), false);
        addCondition(conditions, "price <= ", maxPrice.getText().trim(), false);
        addCondition(conditions, "sale_date >= ", startDate.getText().trim(), true);
        addCondition(conditions, "sale_date <= ", endDate.getText().trim(), true);

        try {
            // Если нет условий, выводим все записи
            if (conditions.isEmpty()) {
                TableViewTools.fillTableView(MainRecord.table, Queries.TICKET_QUERY);
            } else {
                query = BASE_QUERY + String.join(" AND ", conditions);
                ResultSet rs = getDataByQuery(query);
                if (rs != null) fillTableView(MainRecord.table, query);
            }
            System.out.println(query);
        } catch (SQLException e) {
            log.info("Данные написаны не полностью или ошибка в запросе");
        }
    }

    private void addStatusCondition(List<String> statusConditions, String condition, boolean isSelected) {
        if (isSelected) {
            statusConditions.add(condition);
        }
    }


    private void addCondition(List<String> conditions, String conditionPrefix, String value, boolean isString) {
        if (!value.isEmpty()) {
            conditions.add(conditionPrefix + (isString ? "'" + value + "'" : value));
        }
    }
}
