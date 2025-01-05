package com.theatre.theatre_system.controllers.forms;

import com.theatre.theatre_system.controllers.MainController;
import com.theatre.theatre_system.database.dao.TourDAO;
import com.theatre.theatre_system.models.Ticket;
import com.theatre.theatre_system.models.Tour;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
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
        setColumns(tourDAO.findAll());
    }

    public void load(String[] selected) {
        addForRecordHyperlink.setVisible(false);
        editForRecordHyperlink.setVisible(true);

        id = selected[0];
        idEmployee.setText(selected[1]);
        dateStart.setText(selected[2]);
        dateEnd.setText(selected[3]);
        place.setText(selected[4]);
    }

    @FXML
    private void editTour(ActionEvent actionEvent) throws SQLException {
        tourDAO.update(Integer.parseInt(id), new Tour(Integer.parseInt(idEmployee.getText()), LocalDate.parse(dateStart.getText(), DateTimeFormatter.ofPattern("yyyy-MM-dd")), LocalDate.parse(dateEnd.getText(), DateTimeFormatter.ofPattern("yyyy-MM-dd")), place.getText()));
        setColumns(tourDAO.findAll());
    }
}
