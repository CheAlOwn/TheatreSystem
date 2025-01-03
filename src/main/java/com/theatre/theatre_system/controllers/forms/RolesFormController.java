package com.theatre.theatre_system.controllers.forms;

import com.theatre.theatre_system.controllers.MainController;
import com.theatre.theatre_system.database.dao.RoleDAO;
import com.theatre.theatre_system.models.Role;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextField;

import java.sql.SQLException;

public class RolesFormController extends MainController {

    @FXML
    private Hyperlink addForRecordHyperlink;

    @FXML
    private Hyperlink editForRecordHyperlink;

    @FXML
    private TextField roleName;

    @FXML
    private TextField idPerformance;

    @FXML
    private TextField idActor;

    @FXML
    private TextField idUnderstudy;

    RoleDAO roleDAO = new RoleDAO();
    String id;

    @FXML
    private void initialize() {

    }

    @FXML
    private void addNewRole(ActionEvent actionEvent) throws SQLException {
        roleDAO.insert(new Role(roleName.getText(), Integer.parseInt(idPerformance.getText()), Integer.parseInt(idActor.getText()), Integer.parseInt(idUnderstudy.getText())));
        setColumns(getData("roles"));
    }

    public void load(String[] selected) {
        addForRecordHyperlink.setVisible(false);
        editForRecordHyperlink.setVisible(true);

        id = selected[0];
        roleName.setText(selected[1]);
        idPerformance.setText(selected[2]);
        idActor.setText(selected[3]);
        idUnderstudy.setText(selected[4]);
    }

    @FXML
    private void editRole(ActionEvent actionEvent) throws SQLException {
        roleDAO.update(Integer.parseInt(id), new Role(roleName.getText(), Integer.parseInt(idPerformance.getText()), Integer.parseInt(idActor.getText()), Integer.parseInt(idUnderstudy.getText())));
        setColumns(getData("roles"));
    }
}
