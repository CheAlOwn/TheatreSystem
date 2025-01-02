package com.theatre.theatre_system.database.dao;

import com.theatre.theatre_system.MainRecord;
import com.theatre.theatre_system.models.Role;
import com.theatre.theatre_system.repositories.derivative.RoleRepository;

import java.sql.*;

public class RoleDAO implements RoleRepository {
    Connection connection = MainRecord.connection;
    PreparedStatement preparedStatement;
    ResultSet rs;
    String query;

    @Override
    public void delete(Role entity) throws SQLException {

    }

    @Override
    public void deleteById(int id) throws SQLException {

    }

    @Override
    public boolean existsById(int id) throws SQLException {
        return false;
    }

    @Override
    public Iterable<Role> findAll() {
        return null;
    }

    @Override
    public Role findById(int id) throws SQLException {
        return null;
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
    public void update(int id) {

    }

    @Override
    public void saveAll(Iterable<Role> entities) {

    }
}
