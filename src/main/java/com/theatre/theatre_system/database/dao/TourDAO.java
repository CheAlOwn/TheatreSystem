package com.theatre.theatre_system.database.dao;

import com.theatre.theatre_system.MainRecord;
import com.theatre.theatre_system.models.Tour;
import com.theatre.theatre_system.repositories.derivative.TourRepository;

import java.sql.*;

public class TourDAO implements TourRepository {
    Connection connection = MainRecord.connection;
    PreparedStatement preparedStatement;
    ResultSet rs;
    String query;

    public static void deleteById(int id) throws SQLException {
        MainRecord.connection.createStatement().executeUpdate("DELETE FROM tours WHERE id = " + id);
    }

    @Override
    public void insert(Tour entity) throws SQLException {
        query = "INSERT INTO tours(employee_id, start_date, end_date, location) VALUES (?, ?, ?, ?)";
        preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, entity.getEmployeeId());
        preparedStatement.setDate(2, Date.valueOf(entity.getStartDate()));
        preparedStatement.setDate(3, Date.valueOf(entity.getEndDate()));
        preparedStatement.setString(4, entity.getLocation());

        preparedStatement.executeUpdate();
    }

    @Override
    public void update(int id, Tour entity) throws SQLException {
        query = "UPDATE tours SET employee_id = ?, start_date = ?, end_date = ?, location = ? WHERE id = " + id;
        preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, entity.getEmployeeId());
        preparedStatement.setDate(2, Date.valueOf(entity.getStartDate()));
        preparedStatement.setDate(3, Date.valueOf(entity.getEndDate()));
        preparedStatement.setString(4, entity.getLocation());

        preparedStatement.executeUpdate();
    }

    @Override
    public ResultSet findByParameters(String... parameters) {

        return null;
    }

    @Override
    public ResultSet findAll() throws SQLException {
        return connection.createStatement().executeQuery("SELECT * FROM tours;");
    }

}
