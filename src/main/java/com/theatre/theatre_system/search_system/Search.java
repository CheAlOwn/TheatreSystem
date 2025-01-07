package com.theatre.theatre_system.search_system;

import com.theatre.theatre_system.MainRecord;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Objects;
import java.util.logging.Logger;

public class Search {
    Connection connection = MainRecord.connection;
    static final String SELECT = "SELECT * FROM ";
    Logger log = Logger.getLogger(getClass().getName());

    public ResultSet searchByParameter(String table, String parameter, String value) {
        String query = "";
        ResultSet rs = null;

        try {
            if (!value.isBlank()) {
                String type = type(table, parameter);

                switch (type) {
                    case "serial", "numeric", "int4", "bool" ->
                            query = SELECT + table + " WHERE " + parameter + " = " + value + ";";
                    case "varchar", "bpchar", "text" ->
                            query = SELECT + table + " WHERE " + parameter + " LIKE '%" + value + "%';";
                    case "date", "timestamp", "timestamp without time zone", "time", "time without time zone" ->
                            query = SELECT + table + " WHERE " + parameter + " = '" + value + "';";
                    default -> query = null;
                }
            } else {
                query = SELECT + table;
            }
        } catch (SQLException e) {
            log.info(e.getMessage());
        }

        try {
            rs = connection.createStatement().executeQuery(query);
        } catch (SQLException e) {
            log.info("Данные написаны не полностью или ошибка в запросе");
        }

        return rs;
    }

    private String type(String table, String column) throws SQLException {
        String query = SELECT + table;
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
}