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
        setColumns(getData("tickets"));
    }
}
