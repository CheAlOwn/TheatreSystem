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
    private String id;

    @FXML
    private void initialize() {

    }

    @FXML
    private void addNewPerformance(ActionEvent actionEvent) throws SQLException {
        performanceDAO.insert(new Performance(performanceTitle.getText().trim(), performanceGenre.getSelectionModel().getSelectedItem().toString(), performanceAuthor.getText().trim(), Integer.parseInt(performanceDirector.getText()), Integer.parseInt(performanceArtist.getText()), Integer.parseInt(performanceConductor.getText())));
        setColumns(performanceDAO.findAll());
    }

    public void load(String[] selected) {
        addForRecordHyperlink.setVisible(false);
        editForRecordHyperlink.setVisible(true);

        id = selected[0];
        performanceTitle.setText(selected[1]);
        performanceGenre.getSelectionModel().select(selected[2]);
        performanceAuthor.setText(selected[3]);
        performanceDirector.setText(selected[4]);
        performanceArtist.setText(selected[5]);
        performanceConductor.setText(selected[6]);
    }

    @FXML
    private void editPerformance(ActionEvent actionEvent) throws SQLException {
        performanceDAO.update(Integer.parseInt(id), new Performance(performanceTitle.getText().trim(), performanceGenre.getSelectionModel().getSelectedItem().toString(), performanceAuthor.getText().trim(), Integer.parseInt(performanceDirector.getText()), Integer.parseInt(performanceArtist.getText()), Integer.parseInt(performanceConductor.getText())));
        setColumns(performanceDAO.findAll());
    }
}
