package com.theatre.theatre_system.controllers.forms;

import com.theatre.theatre_system.Main;
import com.theatre.theatre_system.MainRecord;
import com.theatre.theatre_system.controllers.MainController;
import com.theatre.theatre_system.database.dao.MusicianDAO;
import com.theatre.theatre_system.models.Actor;
import com.theatre.theatre_system.models.Musician;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

public class MusiciansFormController extends MainController {
    @FXML
    private TextField employeeIdField;

    @FXML
    private ComboBox instrumentBox;

    @FXML
    private Hyperlink addForRecordHyperlink;

    @FXML
    private Hyperlink editForRecordHyperlink;

    MusicianDAO musicianDAO = new MusicianDAO();
    private Connection connection = MainRecord.connection;
    private String id;

    @FXML
    private void initialize() throws SQLException {
    }

    @FXML
    private void addNewMusician(ActionEvent actionEvent) throws SQLException {
        musicianDAO.insert(new Musician(Integer.parseInt(employeeIdField.getText()), instrumentBox.getSelectionModel().getSelectedItem().toString()));
        setColumns(musicianDAO.findAll());
    }

    public void load(String[] selected) {
        addForRecordHyperlink.setVisible(false);
        editForRecordHyperlink.setVisible(true);

        id = selected[0];
        employeeIdField.setText(selected[1]);
        employeeIdField.setDisable(true);

        instrumentBox.getSelectionModel().select(selected[2]);
    }

    @FXML
    private void editMusician(ActionEvent actionEvent) throws SQLException {
        musicianDAO.update(Integer.parseInt(id), new Musician(Integer.parseInt(employeeIdField.getText()), instrumentBox.getSelectionModel().getSelectedItem().toString()));
        setColumns(musicianDAO.findAll());
    }

    public ComboBox getInstrumentBox() {
        return instrumentBox;
    }
}
