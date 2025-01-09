package com.theatre.theatre_system.controllers.forms;

import com.theatre.theatre_system.MainRecord;
import com.theatre.theatre_system.TableViewTools;
import com.theatre.theatre_system.controllers.MainController;
import com.theatre.theatre_system.database.Queries;
import com.theatre.theatre_system.database.dao.TourDAO;
import com.theatre.theatre_system.models.Tour;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextField;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ToursFormController extends MainController {
    @FXML
    private TextField place;

    @FXML
    private TextField dateEnd;

    @FXML
    private TextField dateStart;

    @FXML
    private TextField idEmployee;

    @FXML
    private Hyperlink addForRecordHyperlink;

    @FXML
    private Hyperlink editForRecordHyperlink;

    TourDAO tourDAO = new TourDAO();
    String id;

    @FXML
    private void initialize() {

    }

    @FXML
    public void addNewTour(ActionEvent actionEvent) throws SQLException {
        tourDAO.insert(new Tour(Integer.parseInt(idEmployee.getText()), LocalDate.parse(dateStart.getText(), DateTimeFormatter.ofPattern("yyyy-MM-dd")), LocalDate.parse(dateEnd.getText(), DateTimeFormatter.ofPattern("yyyy-MM-dd")), place.getText()));
        TableViewTools.fillTableView(MainRecord.table, Queries.TOUR_QUERY);
    }

    public void load(String[] selected) {
        addForRecordHyperlink.setVisible(false);
        editForRecordHyperlink.setVisible(true);

        id = selected[6];
        idEmployee.setText(selected[7]);
        dateStart.setText(selected[8]);
        dateEnd.setText(selected[9]);
        place.setText(selected[10]);
    }

    @FXML
    private void editTour(ActionEvent actionEvent) throws SQLException {
        tourDAO.update(Integer.parseInt(id), new Tour(Integer.parseInt(idEmployee.getText()), LocalDate.parse(dateStart.getText(), DateTimeFormatter.ofPattern("yyyy-MM-dd")), LocalDate.parse(dateEnd.getText(), DateTimeFormatter.ofPattern("yyyy-MM-dd")), place.getText()));
        TableViewTools.fillTableView(MainRecord.table, Queries.TOUR_QUERY);
    }
}
