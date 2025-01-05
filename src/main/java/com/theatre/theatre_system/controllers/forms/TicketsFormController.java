package com.theatre.theatre_system.controllers.forms;

import com.theatre.theatre_system.MainRecord;
import com.theatre.theatre_system.controllers.MainController;
import com.theatre.theatre_system.database.dao.TicketDAO;
import com.theatre.theatre_system.models.Role;
import com.theatre.theatre_system.models.Ticket;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;

public class TicketsFormController extends MainController {
    @FXML
    private TextField dateShow;

    @FXML
    private TextField price;

    @FXML
    private TextField idSeat;

    @FXML
    private TextField idRepertoire;

    @FXML
    private ComboBox ticketSellStatus;

    @FXML
    private Hyperlink addForRecordHyperlink;

    @FXML
    private Hyperlink editForRecordHyperlink;

    TicketDAO ticketDAO = new TicketDAO();
    Connection connection = MainRecord.connection;
    String id;

    @FXML
    private void initialize() {
        ticketSellStatus.getItems().add("Доступен");
        ticketSellStatus.getItems().add("Продан");
        ticketSellStatus.getItems().add("Забронирован");
    }

    @FXML
    private void addNewTicket(ActionEvent actionEvent) throws SQLException {
        LocalDate date;
        if (dateShow.getText().isBlank()) {
            date = null;
        } else {
            date = LocalDate.parse(dateShow.getText(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        }
        ticketDAO.insert(new Ticket(Integer.parseInt(idRepertoire.getText()), idSeat.getText(), Float.parseFloat(price.getText()), ticketSellStatus.getSelectionModel().getSelectedItem().toString(), date));
        setColumns(ticketDAO.findAll());
    }

    public void load(String[] selected) {
        addForRecordHyperlink.setVisible(false);
        editForRecordHyperlink.setVisible(true);

        id = selected[0];
        idRepertoire.setText(selected[1]);
        idSeat.setText(selected[2]);
        price.setText(selected[3]);
        ticketSellStatus.getSelectionModel().select(selected[4]);
        dateShow.setText(selected[5]);
    }

    @FXML
    private void editTicket(ActionEvent actionEvent) throws SQLException {
        LocalDate date;
        if (dateShow.getText().isBlank()) {
            date = null;
        } else {
            date = LocalDate.parse(dateShow.getText(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        }
        ticketDAO.update(Integer.parseInt(id), new Ticket(Integer.parseInt(idRepertoire.getText()), idSeat.getText(), Float.parseFloat(price.getText()), ticketSellStatus.getSelectionModel().getSelectedItem().toString(), date));
        setColumns(ticketDAO.findAll());
    }
}
