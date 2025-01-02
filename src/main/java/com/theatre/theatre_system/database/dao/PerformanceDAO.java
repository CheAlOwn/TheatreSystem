package com.theatre.theatre_system.database.dao;

import com.theatre.theatre_system.MainRecord;
import com.theatre.theatre_system.models.Performance;
import com.theatre.theatre_system.repositories.derivative.PerformanceRepository;

import java.sql.*;

public class PerformanceDAO implements PerformanceRepository {
    Connection connection = MainRecord.connection;
    PreparedStatement preparedStatement;
    ResultSet rs;
    String query;

    @Override
    public void delete(Performance entity) throws SQLException {

    }

    @Override
    public void deleteById(int id) throws SQLException {

    }

    @Override
    public boolean existsById(int id) throws SQLException {
        return false;
    }

    @Override
    public Iterable<Performance> findAll() {
        return null;
    }

    @Override
    public Performance findById(int id) throws SQLException {
        return null;
    }

    @Override
    public void insert(Performance entity) throws SQLException {
        query = "INSERT INTO performances(name, genre, author, director_id, set_designer_id, conductor_id) VALUES (?, ?, ?, ?, ?, ?)";
        preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, entity.getName());
        preparedStatement.setString(2, entity.getGenre());
        preparedStatement.setString(3, entity.getAuthor());
        preparedStatement.setInt(4, entity.getDirectorId());
        preparedStatement.setInt(5, entity.getSetDesignerId());
        preparedStatement.setInt(6, entity.getConductorId());

        preparedStatement.executeUpdate();
    }

    @Override
    public void update(int id) {

    }

    @Override
    public void saveAll(Iterable<Performance> entities) {

    }
}
