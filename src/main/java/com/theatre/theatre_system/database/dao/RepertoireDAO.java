package com.theatre.theatre_system.database.dao;

import com.theatre.theatre_system.MainRecord;
import com.theatre.theatre_system.models.Employee;
import com.theatre.theatre_system.models.Repertoire;
import com.theatre.theatre_system.repositories.derivative.RepertoireRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

import java.sql.*;

public class RepertoireDAO implements RepertoireRepository {
    public static final ObservableList<Repertoire> REPERTOIRES = FXCollections.observableArrayList();
    Connection connection = MainRecord.connection;
    PreparedStatement preparedStatement;
    ResultSet rs;
    String query;

    public static void deleteById(int id) {
        try {
            MainRecord.connection.createStatement().executeUpdate("DELETE FROM repertoires WHERE id = " + id);
        } catch (SQLException e) {
            if (e.getMessage().contains("нарушает ограничение внешнего ключа")){
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setContentText(e.getMessage());
                alert.show();
            }
        }
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
    public void update(int id, Repertoire entity) throws SQLException {
        query = "UPDATE repertoires SET performance_id = ?, show_date = ?, show_time = ?, is_premiere = ?, period = ?, price = ? WHERE id = " + id;
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
    public ResultSet findByParameters(String... parameters) {

        return null;
    }

    @Override
    public ResultSet findAll() throws SQLException {
        return connection.createStatement().executeQuery("SELECT * FROM repertoires;");
    }

}
