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
    /*TODO: ВСЕ ЭТО МЕГА ПОДОЗРИТЕЛЬНО И НУЖНО ВСЕ БОЛЕЕ ДЕТАЛЬНО ПРОРАБОТАТЬ*/

    public EmployeeDAO() {
    }


    @Override
    public void delete(Employee entity) throws SQLException {
        query = "DELETE FROM employees WHERE employee_id=?, last_name=?, first_name=?, middle_name=?, birthday=?, gender=?, hire_year=?, category=?, post=?, salary=?, phone=?, address=?, experience=?, children_count=?";
        preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, entity.getEmployeeId());
        preparedStatement.setString(2, entity.getLastName());
        preparedStatement.setString(3, entity.getFirstName());
        preparedStatement.setString(4, entity.getMiddleName());
        preparedStatement.setDate(5, Date.valueOf(entity.getBirthday()));
        preparedStatement.setString(6, entity.getGender());
        preparedStatement.setInt(7, entity.getHireYear());
        preparedStatement.setString(8, entity.getCategory());
        preparedStatement.setString(9, entity.getPost());
        preparedStatement.setFloat(10, entity.getSalary());
        preparedStatement.setString(11, entity.getPhone());
        preparedStatement.setString(12, entity.getAddress());
        preparedStatement.setInt(13, entity.getExperience());
        preparedStatement.setInt(14, entity.getChildrenCount());
        preparedStatement.executeUpdate();
    }

    public static void deleteById(int id) throws SQLException {
        MainRecord.connection.createStatement().executeUpdate("DELETE FROM employees WHERE employee_id = " + id);
    }

    @Override
    public boolean existsById(int id) throws SQLException {
        query = "SELECT * FROM employees WHERE id=?";
        preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, id);
        rs = preparedStatement.executeQuery();

        byte counter = 0;
        while (rs.next()) counter++;

        return counter > 0;
    }

    @Override
    public Iterable<Employee> findAll() {
        return null;
    }

    @Override
    public Employee findById(int id) throws SQLException {
        Employee employee = null;
        query = "SELECT * FROM employees WHERE id=?";
        preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, id);
        rs = preparedStatement.executeQuery();

        while (rs.next()) {
            employee = new Employee(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getDate(5).toLocalDate(), rs.getString(6), rs.getInt(7), rs.getString(8), rs.getString(9), rs.getFloat(10), rs.getString(11), rs.getString(12), rs.getInt(13), rs.getInt(14));
        }

        return employee;
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
    public void saveAll(Iterable<Employee> entities) {

    }

}
