package com.theatre.theatre_system.controllers.filters;

import com.theatre.theatre_system.controllers.MainController;
import com.theatre.theatre_system.database.dao.RepertoireDAO;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class RepertoiresFilterController extends MainController {
    @FXML
    private AnchorPane repertoiresFilter;

    @FXML
    private TextField startPrice;

    @FXML
    private TextField endPrice;

    @FXML
    private TextField startDate;

    @FXML
    private TextField endDate;

    Logger log = Logger.getLogger(getClass().getName());

    private RepertoireDAO repertoireDAO = new RepertoireDAO();
    private static final String BASE_QUERY = "SELECT * FROM repertoires WHERE ";
    private String query = "";

    @FXML
    private void initialize() {
        bindAction(startDate, endDate, startPrice, endPrice);
    }

    private void bindAction(TextField... textFields) {
        for (TextField tf : textFields) {
            tf.textProperty().addListener((obs, oldVal, newVal) -> apply());
        }
    }

    private void apply() {
        try {
            List<String> conditions = new ArrayList<>();
            buildDateCondition(conditions);
            buildPriceCondition(conditions);
            if (conditions.isEmpty()) {
                setColumns(repertoireDAO.findAll());
            } else {
                query = BASE_QUERY + String.join(" AND ", conditions);
                setColumns(getDataByQuery(query));
            }
        } catch (SQLException e) {
            log.info(e.getMessage());
        }
    }

    private void buildDateCondition(List<String> conditions) {
        String start = startDate.getText().trim();
        String end = endDate.getText().trim();

        if (!start.isEmpty()) {
            conditions.add("show_date >= '" + start + "'");
        }
        if (!end.isEmpty()) {
            conditions.add("show_date <= '" + end + "'");
        }
    }

    private void buildPriceCondition(List<String> conditions) {
        String start = startPrice.getText().trim();
        String end = endPrice.getText().trim();

        if (!start.isEmpty()) {
            conditions.add("price >= " + start);
        }
        if (!end.isEmpty()) {
            conditions.add("price <= " + end);
        }
    }
}
