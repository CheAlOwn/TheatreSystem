package com.theatre.theatre_system.database.dao;

import com.theatre.theatre_system.MainRecord;
import com.theatre.theatre_system.models.Actor;
import com.theatre.theatre_system.repositories.derivative.ActorRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

import java.sql.*;

public class ActorDAO implements ActorRepository {
    public static final ObservableList<Actor> ACTORS = FXCollections.observableArrayList();
    Connection connection = MainRecord.connection;
    PreparedStatement preparedStatement;
    String query;

    public static void deleteById(int id) throws SQLException {
        try {
            MainRecord.connection.createStatement().executeUpdate("DELETE FROM actors WHERE actor_id = " + id);
        } catch (SQLException e) {
            if (e.getMessage().contains("нарушает ограничение внешнего ключа")){
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setContentText(e.getMessage());
                alert.show();
            }
        }
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
    public ResultSet findByParameters(String... parameters) {

        return null;
    }

    @Override
    public ResultSet findAll() throws SQLException {
        return connection.createStatement().executeQuery("SELECT * FROM actors;");

    }
}
