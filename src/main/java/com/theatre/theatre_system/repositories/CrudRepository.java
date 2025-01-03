package com.theatre.theatre_system.repositories;

import java.sql.SQLException;
import java.util.Optional;

public interface CrudRepository<T> {
    void delete(T entity) throws SQLException;
    boolean existsById(int id) throws SQLException;
    Iterable<T> findAll();
    T findById(int id) throws SQLException;
    void insert(T entity) throws SQLException;
    void update(int id, T entity) throws SQLException;
    void saveAll(Iterable<T> entities);
}
