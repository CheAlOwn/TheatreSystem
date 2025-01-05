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

    public static void deleteById(int id) throws SQLException {
        MainRecord.connection.createStatement().executeUpdate("DELETE FROM musicians WHERE musician_id = " + id);
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
    public void update(int id, Musician entity) throws SQLException {
        query = "UPDATE musicians SET instrument = ? WHERE musician_id = " + id;
        preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, entity.getInstrument());

        preparedStatement.executeUpdate();
    }

    @Override
    public ResultSet findByParameters(String... parameters) {

        return null;
    }

    @Override
    public ResultSet findAll() throws SQLException {
        return connection.createStatement().executeQuery("SELECT * FROM musicians;");
    }

}
