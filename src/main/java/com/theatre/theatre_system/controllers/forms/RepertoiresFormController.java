package com.theatre.theatre_system.controllers.forms;

import com.theatre.theatre_system.controllers.MainController;
import com.theatre.theatre_system.database.dao.RepertoireDAO;
import com.theatre.theatre_system.models.Repertoire;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextField;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class RepertoiresFormController extends MainController {
    @FXML
    private CheckBox premier;

    @FXML
    private TextField idPerformance;

    @FXML
    private TextField dateShow;

    @FXML
    private TextField hours;

    @FXML
    private TextField minutes;

    @FXML
    private TextField startShow;

    @FXML
    private TextField endShow;

    @FXML
    private TextField price;

    @FXML
    private Hyperlink addForRecordHyperlink;

    @FXML
    private Hyperlink editForRecordHyperlink;

    RepertoireDAO repertoireDAO = new RepertoireDAO();

    @FXML
    private void initialize() {

    }

    @FXML
    private void addNewRepertoire(ActionEvent actionEvent) throws SQLException {
        repertoireDAO.insert(new Repertoire(Integer.parseInt(idPerformance.getText()), LocalDate.parse(dateShow.getText(), DateTimeFormatter.ofPattern("yyyy-MM-dd")), LocalTime.parse(hours.getText() + ":" + minutes.getText()), premier.isSelected(), startShow.getText() + " - " + endShow.getText(), Float.parseFloat(price.getText())));
        setColumns(getData("repertoires"));
    }
}
