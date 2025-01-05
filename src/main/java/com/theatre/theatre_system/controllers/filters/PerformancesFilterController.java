package com.theatre.theatre_system.controllers.filters;

import com.theatre.theatre_system.controllers.MainController;
import com.theatre.theatre_system.database.dao.PerformanceDAO;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.AnchorPane;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class PerformancesFilterController extends MainController {
    @FXML
    private AnchorPane performancesFilter;

    @FXML
    private CheckBox ballet, vaudeville, drama, cabaret, comedy, musical, opera, operetta, tragedy, farce;

    Logger log = Logger.getLogger(getClass().getName());
    private final PerformanceDAO performanceDAO = new PerformanceDAO();
    private final List<CheckBox> genres = new ArrayList<>();
    private static final String WARP = "SELECT * FROM performances WHERE ";
    private String query = "";

    @FXML
    void initialize() {
        addCheckBoxesToList(ballet, cabaret, comedy, vaudeville, drama, musical, opera, operetta, tragedy, farce);

        genres.forEach(box -> box.selectedProperty().addListener(event -> applyFiltersSafely()));
    }

    private void addCheckBoxesToList(CheckBox... boxes) {
        genres.addAll(List.of(boxes));
    }

    private void applyFiltersSafely() {
        try {
            applyFilters();
        } catch (SQLException e) {
            log.info(e.getMessage());
        }
    }

    private void applyFilters() throws SQLException {
        query = buildGenreQuery();
        if (query.isEmpty()) {
            setColumns(performanceDAO.findAll());
        } else {
            setColumns(getDataByQuery(query));
        }
        System.out.println(query); // Для отладки: вывод текущего запроса
    }

    private String buildGenreQuery() {
        List<String> selectedGenres = genres.stream()
                .filter(CheckBox::isSelected)
                .filter(box -> !box.isDisable())
                .map(box -> "genre = '" + box.getAccessibleText() + "'")
                .collect(Collectors.toList());

        return selectedGenres.isEmpty() ? "" : WARP + "(" + String.join(" OR ", selectedGenres) + ")";
    }
}
