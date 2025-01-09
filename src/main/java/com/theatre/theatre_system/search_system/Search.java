package com.theatre.theatre_system.search_system;

import com.theatre.theatre_system.MainRecord;
import com.theatre.theatre_system.database.Queries;

import java.sql.*;
import java.util.Objects;
import java.util.logging.Logger;

public class Search {
    Connection connection = MainRecord.connection;
    Logger log = Logger.getLogger(getClass().getName());

    public ResultSet searchByParameter(String entity, String parameter, String value) {
        String baseQuery = getBaseQuery(entity);
        if (baseQuery == null) {
            log.warning("Неизвестная сущность: " + entity);
            return null;
        }

        String query = baseQuery;

        try {
            if (value != null && !value.isBlank()) {
                String columnType = type(entity, parameter);
                if (columnType == null) {
                    log.warning("Не удалось определить тип колонки: " + parameter);
                    return null;
                }

                switch (columnType) {
                    case "serial", "numeric", "int4", "bool" ->
                            query += " WHERE " + parameter + " = " + value + ";";
                    case "varchar", "bpchar", "text" ->
                            query += " WHERE " + parameter + " LIKE '%" + value + "%';";
                    case "date", "timestamp", "timestamp without time zone", "time", "time without time zone" ->
                            query += " WHERE " + parameter + " = '" + value + "';";
                    default -> {
                        log.warning("Неизвестный тип данных: " + columnType);
                        return null;
                    }
                }
            }
        } catch (SQLException e) {
            log.warning("Ошибка при обработке параметров: " + e.getMessage());
            return null;
        }
        System.out.println(query);
        try {

            return connection.createStatement().executeQuery(query);
        } catch (SQLException e) {
            log.info("Данные написаны не полностью или ошибка в запросе");
            return null;
        }
    }


    private String type(String table, String column) throws SQLException {
        String query = getBaseQuery(table);
        ResultSet rs = connection.createStatement().executeQuery(query);
        ResultSetMetaData rsmt = rs.getMetaData();
        int columnCount = rsmt.getColumnCount();

        for (int i = 1; i <= columnCount; i++) {
            if (Objects.equals(rsmt.getColumnLabel(i), column)) {
                return rsmt.getColumnTypeName(i);
            }
        }

        return null;
    }

    private String getBaseQuery(String entity) {
        return switch (entity.toLowerCase()) {
            case "actors" -> Queries.ACTOR_QUERY;
            case "musicians" -> Queries.MUSICIAN_QUERY;
            case "tickets" -> Queries.TICKET_QUERY;
            case "performances" -> Queries.PERFORMANCE_QUERY;
            case "employees" -> Queries.EMPLOYEE_QUERY;
            case "repertoires" -> Queries.REPERTOIRE_QUERY;
            case "roles" -> Queries.ROLE_QUERY;
            case "tours" -> Queries.TOUR_QUERY;
            default -> null;
        };
    }
}
