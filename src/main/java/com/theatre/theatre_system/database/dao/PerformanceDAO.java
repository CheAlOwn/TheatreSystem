package com.theatre.theatre_system.database.dao;

import com.theatre.theatre_system.MainRecord;
import com.theatre.theatre_system.models.Employee;
import com.theatre.theatre_system.models.Performance;
import com.theatre.theatre_system.repositories.derivative.PerformanceRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

import java.sql.*;

public class PerformanceDAO implements PerformanceRepository {
    public static final ObservableList<Performance> PERFORMANCES = FXCollections.observableArrayList();
    Connection connection = MainRecord.connection;
    PreparedStatement preparedStatement;
    ResultSet rs;
    String query;

    public static void deleteById(int id) throws SQLException {
        try {
            MainRecord.connection.createStatement().executeUpdate("DELETE FROM performances WHERE performance_id = " + id);
        } catch (SQLException e) {
            if (e.getMessage().contains("нарушает ограничение внешнего ключа")){
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setContentText(e.getMessage());
                alert.show();
            }
        }
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
    public void update(int id, Performance entity) throws SQLException {
        query = "UPDATE performances SET name = ?, genre = ?, author = ?, director_id = ?, set_designer_id = ?, conductor_id = ? WHERE performance_id = " + id;
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
    public ResultSet findByParameters(String... parameters) {

        return null;
    }

    @Override
    public ResultSet findAll() throws SQLException {
        return connection.createStatement().executeQuery("SELECT * FROM performances;");
    }

}
