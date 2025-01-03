package com.theatre.theatre_system.database.dao;

import com.theatre.theatre_system.MainRecord;
import com.theatre.theatre_system.models.Actor;
import com.theatre.theatre_system.repositories.derivative.ActorRepository;

import java.sql.*;

public class ActorDAO implements ActorRepository {
    Connection connection = MainRecord.connection;
    PreparedStatement preparedStatement;
    ResultSet rs;
    String query;

    @Override
    public void delete(Actor entity) throws SQLException {

    }

    public static void deleteById(int id) throws SQLException {
        MainRecord.connection.createStatement().executeUpdate("DELETE FROM actors WHERE actor_id = " + id);
    }

    @Override
    public boolean existsById(int id) throws SQLException {
        return false;
    }

    @Override
    public Iterable<Actor> findAll() {
        return null;
    }

    @Override
    public Actor findById(int id) throws SQLException {
        return null;
    }

    @Override
    public void insert(Actor entity) throws SQLException {
        query = "INSERT INTO actors(employee_id, height, voice_type) VALUES (?, ?, ?)";
        preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, entity.getEmployeeId());
        preparedStatement.setFloat(2, entity.getHeight());
        preparedStatement.setString(3, entity.getTimbre());

        preparedStatement.executeUpdate();
    }

    @Override
    public void update(int id, Actor entity) throws SQLException{
        query = "UPDATE actors SET height = ?, voice_type = ? WHERE actor_id = " + id;
        preparedStatement = connection.prepareStatement(query);
        preparedStatement.setFloat(1, entity.getHeight());
        preparedStatement.setString(2, entity.getTimbre());

        preparedStatement.executeUpdate();
    }

    @Override
    public void saveAll(Iterable<Actor> entities) {

    }
}
