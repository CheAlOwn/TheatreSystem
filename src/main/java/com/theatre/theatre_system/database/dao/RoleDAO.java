package com.theatre.theatre_system.database.dao;

import com.theatre.theatre_system.MainRecord;
import com.theatre.theatre_system.models.Employee;
import com.theatre.theatre_system.models.Role;
import com.theatre.theatre_system.repositories.derivative.RoleRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

import java.sql.*;

public class RoleDAO implements RoleRepository {
    public static final ObservableList<Role> ROLES = FXCollections.observableArrayList();
    Connection connection = MainRecord.connection;
    PreparedStatement preparedStatement;
    ResultSet rs;
    String query;

    public static void deleteById(int id) throws SQLException {
        try {
            MainRecord.connection.createStatement().executeUpdate("DELETE FROM roles WHERE id = " + id);
        } catch (SQLException e) {
            if (e.getMessage().contains("нарушает ограничение внешнего ключа")){
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setContentText(e.getMessage());
                alert.show();
            }
        }
    }

    @Override
    public void insert(Role entity) throws SQLException {
        query = "INSERT INTO roles(role_name, performance_id, actor_id, understudy_id) VALUES (?, ?, ?, ?)";
        preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, entity.getRoleName());
        preparedStatement.setInt(2, entity.getPerformanceId());
        preparedStatement.setInt(3, entity.getActorId());
        preparedStatement.setInt(4, entity.getUnderstudyId());

        preparedStatement.executeUpdate();
    }

    @Override
    public void update(int id, Role entity) throws SQLException {
        query = "UPDATE roles SET role_name = ?, performance_id = ?, actor_id = ?, understudy_id = ? WHERE id = " + id;
        preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, entity.getRoleName());
        preparedStatement.setInt(2, entity.getPerformanceId());
        preparedStatement.setInt(3, entity.getActorId());
        preparedStatement.setInt(4, entity.getUnderstudyId());

        preparedStatement.executeUpdate();
    }

    @Override
    public ResultSet findByParameters(String... parameters) {

        return null;
    }

    @Override
    public ResultSet findAll() throws SQLException {
        return connection.createStatement().executeQuery("SELECT * FROM roles;");
    }

}
