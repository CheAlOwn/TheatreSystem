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

    @Override
    public void delete(Tour entity) throws SQLException {

    }

    public static void deleteById(int id) throws SQLException {
        MainRecord.connection.createStatement().executeUpdate("DELETE FROM tours WHERE id = " + id);
    }

    @Override
    public boolean existsById(int id) throws SQLException {
        return false;
    }

    @Override
    public Iterable<Tour> findAll() {
        return null;
    }

    @Override
    public Tour findById(int id) throws SQLException {
        return null;
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
    public void saveAll(Iterable<Tour> entities) {

    }
}
