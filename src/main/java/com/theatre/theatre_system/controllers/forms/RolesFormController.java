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

    @FXML
    private void initialize() {

    }

    @FXML
    private void addNewRole(ActionEvent actionEvent) throws SQLException {
        roleDAO.insert(new Role(roleName.getText(), Integer.parseInt(idPerformance.getText()), Integer.parseInt(idActor.getText()), Integer.parseInt(idUnderstudy.getText())));
        setColumns(getData("roles"));
    }
}
