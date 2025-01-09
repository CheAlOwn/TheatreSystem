package com.theatre.theatre_system.controllers.forms;

import com.theatre.theatre_system.MainRecord;
import com.theatre.theatre_system.TableViewTools;
import com.theatre.theatre_system.controllers.MainController;
import com.theatre.theatre_system.database.Queries;
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
    String id;

    @FXML
    private void initialize() {

    }

    @FXML
    private void addNewRepertoire(ActionEvent actionEvent) throws SQLException {
        repertoireDAO.insert(new Repertoire(Integer.parseInt(idPerformance.getText()), LocalDate.parse(dateShow.getText(), DateTimeFormatter.ofPattern("yyyy-MM-dd")), LocalTime.parse(hours.getText() + ":" + minutes.getText()), premier.isSelected(), startShow.getText() + " - " + endShow.getText(), Float.parseFloat(price.getText())));
        TableViewTools.fillTableView(MainRecord.table, Queries.REPERTOIRE_QUERY);
    }

    public void load(String[] selected) {
        addForRecordHyperlink.setVisible(false);
        editForRecordHyperlink.setVisible(true);

        id = selected[2];
        idPerformance.setText(selected[3]);
        dateShow.setText(selected[4]);
        String[] time = selected[5].split(":");
        hours.setText(time[0]);
        minutes.setText(time[1]);
        premier.setSelected(Boolean.parseBoolean(selected[6]));
        String[] date = selected[7].split(" - ");
        startShow.setText(date[0]);
        endShow.setText(date[1]);
        price.setText(selected[8]);
    }

    @FXML
    private void editRepertoire(ActionEvent actionEvent) throws SQLException {
        repertoireDAO.update(Integer.parseInt(id), new Repertoire(Integer.parseInt(idPerformance.getText()), LocalDate.parse(dateShow.getText(), DateTimeFormatter.ofPattern("yyyy-MM-dd")), LocalTime.parse(hours.getText() + ":" + minutes.getText()), premier.isSelected(), startShow.getText() + " - " + endShow.getText(), Float.parseFloat(price.getText())));
        TableViewTools.fillTableView(MainRecord.table, Queries.REPERTOIRE_QUERY);
    }
}
