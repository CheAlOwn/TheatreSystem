package com.theatre.theatre_system.controllers.forms;

import com.theatre.theatre_system.MainRecord;
import com.theatre.theatre_system.controllers.MainController;
import com.theatre.theatre_system.database.dao.ActorDAO;
import com.theatre.theatre_system.models.Actor;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

public class ActorsFormController extends MainController {
    @FXML
    private TextField employeeIdField;

    @FXML
    private TextField heightIdField;

    @FXML
    private ComboBox typeTimbreActorBox;

    @FXML
    private Hyperlink addForRecordHyperlink;

    @FXML
    private Hyperlink editForRecordHyperlink;

    ActorDAO actorDAO = new ActorDAO();
    private Connection connection = MainRecord.connection;

    @FXML
    private void initialize() throws SQLException {

    }

    @FXML
    private void addNewActor(ActionEvent actionEvent) throws SQLException {
        actorDAO.insert(new Actor(Integer.parseInt(employeeIdField.getText()), Float.parseFloat(heightIdField.getText()), typeTimbreActorBox.getSelectionModel().getSelectedItem().toString()));
        setColumns(getData("actors"));
    }
}
