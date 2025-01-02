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
            switch (type(table, parameter)) {
                case "serial", "numeric", "int4" ->
                        query = SELECT + table + " WHERE " + parameter + " = " + value + ";";
                case "varchar", "bpchar", "text" ->
                        query = SELECT + table + " WHERE " + parameter + " LIKE '%" + value + "%';";
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


//    • Сделать поиск по таблицам. 90/100
//    • Сделать отображение форм предактирование и добавления новых данных (также добавить кнопку назад на некоторые формы) 20/100
//    • Сделать фильтры (также сделать очистку) 30/100
//    • Видоизменить панельку добавления редактирования и изменения.    
//    • Сделать дизайн комбобокса
//
//
//Не работет на параметре “Параметр” и на birthday, н авкладке hire-year когда стираешь полностью строку. В общем е работает поиск по датам и времени
//
//не обрабатывается исключение при попытке ввести запятую вместо точки в росте и т.д.