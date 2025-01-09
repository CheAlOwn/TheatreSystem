package com.theatre.theatre_system.database.dao;

import com.theatre.theatre_system.MainRecord;
import com.theatre.theatre_system.models.Employee;
import com.theatre.theatre_system.models.Ticket;
import com.theatre.theatre_system.repositories.derivative.TicketRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

import java.sql.*;

public class TicketDAO implements TicketRepository {
    public static final ObservableList<Ticket> TICKETS = FXCollections.observableArrayList();
    Connection connection = MainRecord.connection;
    PreparedStatement preparedStatement;
    ResultSet rs;
    String query;

    public static void deleteById(int id) throws SQLException {
        try {
            MainRecord.connection.createStatement().executeUpdate("DELETE FROM tickets WHERE ticket_id = " + id);
        } catch (SQLException e) {
            if (e.getMessage().contains("нарушает ограничение внешнего ключа")){
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setContentText(e.getMessage());
                alert.show();
            }
        }
    }

    @Override
    public void insert(Ticket entity) throws SQLException {
        if (entity.getSaleDate() != null) {
            query = "INSERT INTO tickets(repertoire_id, seat, price, status, sale_date) VALUES (?, ?, ?, ?, ?)";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, entity.getRepertoireId());
            preparedStatement.setString(2, entity.getSeat());
            preparedStatement.setFloat(3, entity.getPrice());
            preparedStatement.setString(4, entity.getStatus());
            preparedStatement.setDate(5, Date.valueOf(entity.getSaleDate()));
        } else {
            query = "INSERT INTO tickets(repertoire_id, seat, price, status) VALUES (?, ?, ?, ?)";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, entity.getRepertoireId());
            preparedStatement.setString(2, entity.getSeat());
            preparedStatement.setFloat(3, entity.getPrice());
            preparedStatement.setString(4, entity.getStatus());
        }

        preparedStatement.executeUpdate();
    }

    @Override
    public void update(int id, Ticket entity) throws SQLException {
        if (entity.getSaleDate() != null) {
            query = "UPDATE tickets SET repertoire_id = ?, seat = ?, price = ?, status = ?, sale_date = ? WHERE ticket_id = " + id;
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, entity.getRepertoireId());
            preparedStatement.setString(2, entity.getSeat());
            preparedStatement.setFloat(3, entity.getPrice());
            preparedStatement.setString(4, entity.getStatus());
            preparedStatement.setDate(5, Date.valueOf(entity.getSaleDate()));
        } else {
            query = "UPDATE tickets SET repertoire_id = ?, seat = ?, price = ?, status = ? WHERE ticket_id = " + id;
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, entity.getRepertoireId());
            preparedStatement.setString(2, entity.getSeat());
            preparedStatement.setFloat(3, entity.getPrice());
            preparedStatement.setString(4, entity.getStatus());
        }

        preparedStatement.executeUpdate();
    }

    @Override
    public ResultSet findByParameters(String... parameters) {

        return null;
    }

    @Override
    public ResultSet findAll() throws SQLException {
        return connection.createStatement().executeQuery("SELECT * FROM tickets;");
    }
}
