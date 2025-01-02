package com.theatre.theatre_system.database.dao;

import com.theatre.theatre_system.MainRecord;
import com.theatre.theatre_system.models.Repertoire;
import com.theatre.theatre_system.repositories.derivative.RepertoireRepository;

import java.sql.*;

public class RepertoireDAO implements RepertoireRepository {
    Connection connection = MainRecord.connection;
    PreparedStatement preparedStatement;
    ResultSet rs;
    String query;

    @Override
    public void delete(Repertoire entity) throws SQLException {

    }

    @Override
    public void deleteById(int id) throws SQLException {

    }

    @Override
    public boolean existsById(int id) throws SQLException {
        return false;
    }

    @Override
    public Iterable<Repertoire> findAll() {
        return null;
    }

    @Override
    public Repertoire findById(int id) throws SQLException {
        return null;
    }

    @Override
    public void insert(Repertoire entity) throws SQLException {
        query = "INSERT INTO repertoires(performance_id, show_date, show_time, is_premiere, period, price) VALUES (?, ?, ?, ?, ?, ?)";
        preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, entity.getPerformanceId());
        preparedStatement.setDate(2, Date.valueOf(entity.getShowDate()));
        preparedStatement.setTime(3, Time.valueOf(entity.getShowTime()));
        preparedStatement.setBoolean(4, entity.isPremiere());
        preparedStatement.setString(5, entity.getPeriod());
        preparedStatement.setFloat(6, entity.getPrice());

        preparedStatement.executeUpdate();
    }

    @Override
    public void update(int id) {

    }

    @Override
    public void saveAll(Iterable<Repertoire> entities) {

    }
}
