package com.theatre.theatre_system.database.dao;

import com.theatre.theatre_system.MainRecord;
import com.theatre.theatre_system.models.Employee;
import com.theatre.theatre_system.repositories.derivative.EmployeeRepository;

import java.sql.*;

public class EmployeeDAO implements EmployeeRepository {
    Connection connection = MainRecord.connection;
    PreparedStatement preparedStatement;
    ResultSet rs;
    String query;

    public static void deleteById(int id) throws SQLException {
        MainRecord.connection.createStatement().executeUpdate("DELETE FROM employees WHERE employee_id = " + id);
    }

    @Override
    public void insert(Employee entity) throws SQLException {
        query = "INSERT INTO employees(last_name, first_name, middle_name, birthday, gender, hire_year, category, post, salary, phone, address, experience, children_count) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, entity.getLastName());
        preparedStatement.setString(2, entity.getFirstName());
        preparedStatement.setString(3, entity.getMiddleName());
        preparedStatement.setDate(4, Date.valueOf(entity.getBirthday()));
        preparedStatement.setString(5, entity.getGender());
        preparedStatement.setInt(6, entity.getHireYear());
        preparedStatement.setString(7, entity.getCategory());
        preparedStatement.setString(8, entity.getPost());
        preparedStatement.setFloat(9, entity.getSalary());
        preparedStatement.setString(10, entity.getPhone());
        preparedStatement.setString(11, entity.getAddress());
        preparedStatement.setInt(12, entity.getExperience());
        preparedStatement.setInt(13, entity.getChildrenCount());
        preparedStatement.executeUpdate();
    }

    @Override
    public void update(int id, Employee entity) throws SQLException {
        query = "UPDATE employees SET last_name = ?, first_name = ?, middle_name = ?, birthday = ?, gender = ?, hire_year = ?, category = ?, post = ?, salary = ?, phone = ?, address = ?, experience = ?, children_count = ? WHERE employee_id = " + id;
        preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, entity.getLastName());
        preparedStatement.setString(2, entity.getFirstName());
        preparedStatement.setString(3, entity.getMiddleName());
        preparedStatement.setDate(4, Date.valueOf(entity.getBirthday()));
        preparedStatement.setString(5, entity.getGender());
        preparedStatement.setInt(6, entity.getHireYear());
        preparedStatement.setString(7, entity.getCategory());
        preparedStatement.setString(8, entity.getPost());
        preparedStatement.setFloat(9, entity.getSalary());
        preparedStatement.setString(10, entity.getPhone());
        preparedStatement.setString(11, entity.getAddress());
        preparedStatement.setInt(12, entity.getExperience());
        preparedStatement.setInt(13, entity.getChildrenCount());
        preparedStatement.executeUpdate();
    }

    @Override
    public ResultSet findByParameters(String... parameters) throws SQLException {
        return connection.createStatement().executeQuery("SELECT * FROM employees WHERE " + String.join(", ", parameters) + ";");
    }

    @Override
    public ResultSet findAll() throws SQLException {
        return connection.createStatement().executeQuery("SELECT * FROM employees;");
    }


}
