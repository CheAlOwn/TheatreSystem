package com.theatre.theatre_system.database.dao;

import com.theatre.theatre_system.MainRecord;
import com.theatre.theatre_system.models.Musician;
import com.theatre.theatre_system.repositories.derivative.MusicianRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MusicianDAO implements MusicianRepository {
    Connection connection = MainRecord.connection;
    PreparedStatement preparedStatement;
    ResultSet rs;
    String query;

    @Override
    public void delete(Musician entity) throws SQLException {

    }

    @Override
    public void deleteById(int id) throws SQLException {

    }

    @Override
    public boolean existsById(int id) throws SQLException {
        return false;
    }

    @Override
    public Iterable<Musician> findAll() {
        return null;
    }

    @Override
    public Musician findById(int id) throws SQLException {
        return null;
    }

    @Override
    public void insert(Musician entity) throws SQLException {
        query = "INSERT INTO musicians(employee_id, instrument) VALUES (?, ?)";
        preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, entity.getEmployeeId());
        preparedStatement.setString(2, entity.getInstrument());

        preparedStatement.executeUpdate();
    }

    @Override
    public void update(int id) {

    }

    @Override
    public void saveAll(Iterable<Musician> entities) {

    }
}
