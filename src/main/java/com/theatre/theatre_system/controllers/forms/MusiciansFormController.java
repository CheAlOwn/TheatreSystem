package com.theatre.theatre_system.controllers.forms;

import com.theatre.theatre_system.MainRecord;
import com.theatre.theatre_system.TableViewTools;
import com.theatre.theatre_system.controllers.MainController;
import com.theatre.theatre_system.database.Queries;
import com.theatre.theatre_system.database.dao.MusicianDAO;
import com.theatre.theatre_system.models.Musician;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.SQLException;

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
        TableViewTools.fillTableView(MainRecord.table, Queries.MUSICIAN_QUERY);
    }

    public void load(String[] selected) {
        addForRecordHyperlink.setVisible(false);
        editForRecordHyperlink.setVisible(true);

        id = selected[5];
        employeeIdField.setText(selected[6]);
        employeeIdField.setDisable(true);

        instrumentBox.getSelectionModel().select(selected[7]);
    }

    @FXML
    private void editMusician(ActionEvent actionEvent) throws SQLException {
        musicianDAO.update(Integer.parseInt(id), new Musician(Integer.parseInt(employeeIdField.getText()), instrumentBox.getSelectionModel().getSelectedItem().toString()));
        TableViewTools.fillTableView(MainRecord.table, Queries.MUSICIAN_QUERY);
    }

    public ComboBox getInstrumentBox() {
        return instrumentBox;
    }
}
