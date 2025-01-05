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

    private String id;

    @FXML
    private void initialize() throws SQLException {
    }

    @FXML
    private void addNewActor(ActionEvent actionEvent) throws SQLException {
        actorDAO.insert(new Actor(Integer.parseInt(employeeIdField.getText()), Float.parseFloat(heightIdField.getText()), typeTimbreActorBox.getSelectionModel().getSelectedItem().toString()));
        setColumns(actorDAO.findAll());
    }


    public void load(String[] selected) {
        addForRecordHyperlink.setVisible(false);
        editForRecordHyperlink.setVisible(true);

        id = selected[0];
        employeeIdField.setText(selected[1]);
        employeeIdField.setDisable(true);
        heightIdField.setText(selected[2]);
        typeTimbreActorBox.getSelectionModel().select(selected[3]);
    }

    @FXML
    private void editActor(ActionEvent actionEvent) throws SQLException {
        actorDAO.update(Integer.parseInt(id), new Actor(Integer.parseInt(employeeIdField.getText()), Float.parseFloat(heightIdField.getText()), typeTimbreActorBox.getSelectionModel().getSelectedItem().toString()));
        setColumns(actorDAO.findAll());
    }
}
