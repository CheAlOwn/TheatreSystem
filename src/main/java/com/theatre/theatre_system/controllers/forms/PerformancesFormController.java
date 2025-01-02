package com.theatre.theatre_system.controllers.forms;

import com.theatre.theatre_system.controllers.MainController;
import com.theatre.theatre_system.database.dao.PerformanceDAO;
import com.theatre.theatre_system.models.Performance;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.sql.SQLException;

public class PerformancesFormController extends MainController {
    @FXML
    private Button backMenuButton;

    @FXML
    private AnchorPane step1Pane;

    @FXML
    private AnchorPane step2Pane;

    @FXML
    private Hyperlink addForRecordHyperlink;

    @FXML
    private Hyperlink editForRecordHyperlink;

    @FXML
    private TextField performanceTitle;

    @FXML
    private ComboBox performanceGenre;

    @FXML
    private TextField performanceAuthor;

    @FXML
    private Button nextMenuButton;

    @FXML
    private TextField performanceDirector;

    @FXML
    private TextField performanceArtist;

    @FXML
    private TextField performanceConductor;

    PerformanceDAO performanceDAO = new PerformanceDAO();

    @FXML
    private void initialize() {

    }

    @FXML
    private void nextStep(ActionEvent actionEvent) {
        step1Pane.setVisible(false);
        step2Pane.setVisible(true);
    }

    @FXML
    private void addNewPerformance(ActionEvent actionEvent) throws SQLException {
        performanceDAO.insert(new Performance(performanceTitle.getText().trim(), performanceGenre.getSelectionModel().getSelectedItem().toString(), performanceAuthor.getText().trim(), Integer.parseInt(performanceDirector.getText()), Integer.parseInt(performanceArtist.getText()), Integer.parseInt(performanceConductor.getText())));
        setColumns(getData("performances"));
    }

    @FXML
    private void backStep(ActionEvent actionEvent) {
        step2Pane.setVisible(false);
        step1Pane.setVisible(true);
    }
}
