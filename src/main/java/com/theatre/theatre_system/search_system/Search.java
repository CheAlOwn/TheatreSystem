package com.theatre.theatre_system.search_system;

import com.theatre.theatre_system.MainRecord;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Objects;

public class Search {
    Connection connection = MainRecord.connection;
    static final String SELECT = "SELECT * FROM ";

    public ResultSet searchByParameter(String table, String parameter, String value) throws SQLException {
        String query;

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

        return connection.createStatement().executeQuery(query);
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


//    • Сделать отображение форм предактирование и добавления новых данных (также добавить кнопку назад на некоторые формы) 99/100
//    • Сделать фильтры (также сделать очистку) 99/100
//    • Сделать дизайн комбобокса
//      сделать scrollbar в scrollarea, чтобы пользователи понимали, что есть возможность прокрутки в некоторых местах
//
//
//Не работет на параметре “Параметр” и на birthday, н авкладке hire-year когда стираешь полностью строку. В общем е работает поиск по датам и времени
//
//не обрабатывается исключение при попытке ввести запятую вместо точки в росте и т.д.