package com.theatre.theatre_system.controllers.forms;

import com.theatre.theatre_system.MainRecord;
import com.theatre.theatre_system.controllers.MainController;
import com.theatre.theatre_system.database.dao.ActorDAO;
import com.theatre.theatre_system.database.dao.EmployeeDAO;
import com.theatre.theatre_system.database.dao.MusicianDAO;
import com.theatre.theatre_system.models.Actor;
import com.theatre.theatre_system.models.Employee;
import com.theatre.theatre_system.models.Musician;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;

public class EmployeeFormController extends MainController {
    @FXML
    private Pane actorPane;

    @FXML
    private Pane musicianPane;

    @FXML
    private AnchorPane step3;

    @FXML
    private AnchorPane step2;

    @FXML
    private AnchorPane step1;

    @FXML
    private Hyperlink addOnStep3;

    @FXML
    private Hyperlink editOnStep3;

    @FXML
    private Button backToStep32;

    @FXML
    private Button backToStep31;

    @FXML
    private Hyperlink addOnStep41;

    @FXML
    private Hyperlink editOnStep41;

    @FXML
    private Button backToStep3;

    @FXML
    private Button backToStep2;

    @FXML
    private Button backToStep1;

    @FXML
    private Button nextToStep3;

    @FXML
    private Button nextToStep4;

    @FXML
    private Button nextToStep2;

    @FXML
    private TextField lastName;

    @FXML
    private TextField firstName;

    @FXML
    private TextField middleName;

    @FXML
    private RadioButton femaleRadioButton;

    @FXML
    private RadioButton maleRadioButton;

    @FXML
    private ComboBox typeEmployeeBox;

    @FXML
    private TextField postEmployee;

    @FXML
    private TextField addressEmployee;

    @FXML
    private TextField phoneNumberEmployee;

    @FXML
    private TextField experience;

    @FXML
    private TextField salary;

    @FXML
    private TextField countKids;

    @FXML
    private ComboBox typeTimbreActorBox;

    @FXML
    private TextField heightActor;

    @FXML
    private ComboBox instrumentBox;

    @FXML
    private TextField birthday;

    @FXML
    private TextField hireDate;

    ToggleGroup genderGroup = new ToggleGroup();
    EmployeeDAO employeeDAO = new EmployeeDAO();
    ActorDAO actorDAO = new ActorDAO();
    MusicianDAO musicianDAO = new MusicianDAO();
    private Connection connection = MainRecord.connection;

    @FXML
    void initialize() {
        typeEmployeeBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) ->
        {
            nextToStep3.setDisable(newValue == null || newValue.toString().isEmpty());
            if (typeEmployeeBox.getSelectionModel().getSelectedItem().toString().equals("Актер")) {
                postEmployee.setText("Актер");
                postEmployee.setDisable(true);
            } else if (typeEmployeeBox.getSelectionModel().getSelectedItem().toString().equals("Музыкант")) {
                postEmployee.setText("Музыкант");
                postEmployee.setDisable(true);
            } else {
                postEmployee.setText("");
                postEmployee.setDisable(false);
            }
        });


        femaleRadioButton.setToggleGroup(genderGroup);
        maleRadioButton.setToggleGroup(genderGroup);
    }

    //TODO: сделать аниму!!!!!

    @FXML
    private void nextToStep2(ActionEvent actionEvent) throws IOException {
        step1.setVisible(false);
        step2.setVisible(true);
    }

    @FXML
    private void backToStep1(ActionEvent actionEvent) throws IOException {
        step2.setVisible(false);
        step1.setVisible(true);
    }

    @FXML
    private void nextToStep3(ActionEvent actionEvent) {
        actorPane.setVisible(false);
        musicianPane.setVisible(false);
        step2.setVisible(false);
        if (typeEmployeeBox.getSelectionModel().getSelectedItem().toString().equals("Актер")) {
            actorPane.setVisible(true);
        } else if (typeEmployeeBox.getSelectionModel().getSelectedItem().toString().equals("Музыкант")) {
            musicianPane.setVisible(true);
        }
        step3.setVisible(true);
    }

    @FXML
    private void backToStep2(ActionEvent actionEvent) {
        step3.setVisible(false);
        step2.setVisible(true);
    }

    @FXML
    private void addOnStep3(ActionEvent actionEvent) throws SQLException {
        String gender;
        if (femaleRadioButton.isSelected()) {
            gender = "Ж";
        } else {
            gender = "М";
        }

        // ЕСЛИ АКТЕР ИЛИ МУЗЫКАНТ ТО ДОЛЖНОСТЬ АКТЕР ИЛИ МУЗЫКАНТ

        postEmployee.setEditable(false);
        employeeDAO.insert(new Employee(lastName.getText().trim(), firstName.getText().trim(), middleName.getText().trim(), LocalDate.parse(birthday.getText().trim(), DateTimeFormatter.ofPattern("yyyy-MM-dd")), gender.trim(), Integer.parseInt(hireDate.getText().trim()), typeEmployeeBox.getSelectionModel().getSelectedItem().toString().trim(), postEmployee.getText().trim(), Float.parseFloat(salary.getText().trim()), phoneNumberEmployee.getText().trim(), addressEmployee.getText().trim(), Integer.parseInt(experience.getText().trim()), Integer.parseInt(countKids.getText().trim())));
        setColumns(getData("employees"));

        ResultSet rs = connection.createStatement().executeQuery("SELECT currval('employees_employee_id_seq')");
        int employeeId = 0;
        while (rs.next()) employeeId = rs.getInt(1);
        System.out.println(employeeId);

        if (typeEmployeeBox.getSelectionModel().getSelectedItem().toString().equals("Актер"))
            actorDAO.insert(new Actor(employeeId, Float.parseFloat(heightActor.getText().trim()), typeTimbreActorBox.getSelectionModel().getSelectedItem().toString()));
        else if (typeEmployeeBox.getSelectionModel().getSelectedItem().toString().equals("Музыкант"))
            musicianDAO.insert(new Musician(employeeId, instrumentBox.getSelectionModel().getSelectedItem().toString()));
    }
}
