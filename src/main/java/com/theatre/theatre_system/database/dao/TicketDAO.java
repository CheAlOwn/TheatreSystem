package com.theatre.theatre_system.database.dao;

import com.theatre.theatre_system.MainRecord;
import com.theatre.theatre_system.models.Ticket;
import com.theatre.theatre_system.repositories.derivative.TicketRepository;

import java.sql.*;

public class TicketDAO implements TicketRepository {
    Connection connection = MainRecord.connection;
    PreparedStatement preparedStatement;
    ResultSet rs;
    String query;

    @Override
    public void delete(Ticket entity) throws SQLException {

    }

    @Override
    public void deleteById(int id) throws SQLException {

    }

    @Override
    public boolean existsById(int id) throws SQLException {
        return false;
    }

    @Override
    public Iterable<Ticket> findAll() {
        return null;
    }

    @Override
    public Ticket findById(int id) throws SQLException {
        return null;
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
    public void update(int id) {

    }

    @Override
    public void saveAll(Iterable<Ticket> entities) {

    }
}
