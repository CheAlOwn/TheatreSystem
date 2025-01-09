package com.theatre.theatre_system;

import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.List;

public class TableViewTools {
    private static final Connection connection = MainRecord.connection;

    // Метод заполнения табличного вида
    public static void fillTableView(TableView<ObservableList<Object>> tableView, String query, String ...currentTable) throws SQLException {
        tableView.getColumns().clear();
        ObservableList<ObservableList<Object>> rows = FXCollections.observableArrayList();
        ResultSet resultSet = getDataByQuery(query);

        ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
        int columnCount = resultSetMetaData.getColumnCount();

        for (int i = 1; i <= columnCount; i++) {
            String columnName = resultSetMetaData.getColumnLabel(i);
            final int columnIndex = i - 1;

            TableColumn<ObservableList<Object>, Object> column = getObservableListObjectTableColumn(columnName, columnIndex, i);

            tableView.getColumns().add(column);

            if ((column.getText().contains("_id") || column.getText().trim().equals("id"))) {
                column.setVisible(false);
            }

            column.setStyle("-fx-background-radius: 12px 0px 0px 12px; -fx-border-width:0px;");
        }

        while (resultSet.next()) {
            ObservableList<Object> row = FXCollections.observableArrayList();
            for (int i = 1; i <= columnCount; i++) {
                row.add(resultSet.getObject(i));
            }
            rows.add(row);
        }

        tableView.setItems(rows);
    }

    public static void fillTableView(TableView<ObservableList<Object>> tableView, ResultSet resultSet, String ...currentTable) throws SQLException {
        tableView.getColumns().clear();
        ObservableList<ObservableList<Object>> rows = FXCollections.observableArrayList();

        ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
        int columnCount = resultSetMetaData.getColumnCount();

        for (int i = 1; i <= columnCount; i++) {
            String columnName = resultSetMetaData.getColumnLabel(i);
            final int columnIndex = i - 1;

            TableColumn<ObservableList<Object>, Object> column = getObservableListObjectTableColumn(columnName, columnIndex, i);

            tableView.getColumns().add(column);

//            if (column.getText().contains("_id") || column.getText().trim().equals("id")) {
//                column.setVisible(false);
//            }

            column.setStyle("-fx-background-radius: 12px 0px 0px 12px; -fx-border-width:0px;");
        }

        while (resultSet.next()) {
            ObservableList<Object> row = FXCollections.observableArrayList();
            for (int i = 1; i <= columnCount; i++) {
                row.add(resultSet.getObject(i));
            }
            rows.add(row);
        }

        tableView.setItems(rows);

    }

        private static TableColumn<ObservableList<Object>, Object> getObservableListObjectTableColumn(String columnName, int columnIndex, int i) {
        TableColumn<ObservableList<Object>, Object> column = new TableColumn<>(columnName);
        column.setCellValueFactory(param -> {
            if (param.getValue().size() > columnIndex) {
                return new SimpleObjectProperty<>(param.getValue().get(columnIndex));
            } else {
                return null;
            }
        });

        return column;
    }

    // Получение данных из запроса
    public static ResultSet getDataByQuery(String query) throws SQLException {
        return connection.createStatement().executeQuery(query);
    }
}