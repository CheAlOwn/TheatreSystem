package com.theatre.theatre_system.repositories;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public interface CrudRepository<T> {
    void insert(T entity) throws SQLException;
    void update(int id, T entity) throws SQLException;
    ResultSet findByParameters(String ...parameters) throws SQLException;
    ResultSet findAll() throws SQLException;
}
