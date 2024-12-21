package com.theatre.theatre_system.controllers;

import com.theatre.theatre_system.MainRecord;
import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.io.IOException;
import java.sql.*;
import java.util.logging.Logger;

public class MainController {
    @FXML
    private Button closeFiltersButton;

    @FXML
    private Button clearFiltersButton;

    @FXML
    private AnchorPane fPane;

    @FXML
    private HBox searchBox;

    @FXML
    private AnchorPane mainPane;

    @FXML
    private Label tableName;

    @FXML
    private Hyperlink toursHyperlink;

    @FXML
    private Hyperlink ticketsHyperlink;

    @FXML
    private Hyperlink roleHyperlink;

    @FXML
    private Hyperlink repertoireHyperlink;

    @FXML
    private Hyperlink performancesHyperlink;

    @FXML
    private Hyperlink musiciansHyperlink;

    @FXML
    private Hyperlink employeesHyperlink;

    @FXML
    private Hyperlink actorsHyperlink;

    @FXML
    private Button menuButton;

    @FXML
    private Button searchButton;

    @FXML
    private Button filterButton;

    @FXML
    private Rectangle overlayPane;

    @FXML
    private TableView tableOutData;

    @FXML
    private Button closeMenuButton;

    @FXML
    private FlowPane actionsPane;

    @FXML
    private Button addObjectButton;

    @FXML
    private AnchorPane menuPane;

    @FXML
    private VBox menuVBox;

    @FXML
    private AnchorPane filtersPane;

    @FXML
    private AnchorPane formsPane;

    @FXML
    private ComboBox parametersBox;

    @FXML
    private TextField searchTextField;

    @FXML
    private FlowPane addRecordPane;

    @FXML
    private Button editObjectButton;

    @FXML
    private Button removeObjectButton;

    @FXML
    private Pane floatPane;

    private final Connection connection = MainRecord.connection;
    private Statement statement;
    private String query;
    private Hyperlink currentHyperlink;

    final double[] offsetX = {0};
    final double[] offsetY = {0};

    private boolean isSearchVisible = false;

    Logger logger = Logger.getLogger(getClass().getName());


    @FXML
    void initialize() throws SQLException {
        setEffects();

        menuPane.setTranslateX(-278);

        switchTables(actorsHyperlink, "actors");

        addRecordPane.setOnMousePressed(event -> {
            offsetX[0] = event.getSceneX() - addRecordPane.getLayoutX();
            offsetY[0] = event.getSceneY() - addRecordPane.getLayoutY();
        });

        addRecordPane.setOnMouseDragged(event -> {
            double newX = event.getSceneX() - offsetX[0];
            double newY = event.getSceneY() - offsetY[0];

            // Ограничение перемещения внутри окна
            if (newX >= 0 && newX + addRecordPane.getWidth() <= mainPane.getWidth()) {
                addRecordPane.setLayoutX(newX);
            }
            if (newY >= 0 && newY + addRecordPane.getHeight() <= mainPane.getHeight()) {
                addRecordPane.setLayoutY(newY);
            }
        });

        searchBox.setVisible(false);
        searchBox.setOpacity(0);

        MainRecord.overlayPane = overlayPane;
        MainRecord.filtersPane = filtersPane;
    }

    private void setEffects() {
        GaussianBlur gaussianBlur = new GaussianBlur();
        gaussianBlur.setRadius(20);

        DropShadow dropShadow = new DropShadow();
        dropShadow.setOffsetX(0);
        dropShadow.setOffsetY(0);
        dropShadow.setBlurType(BlurType.GAUSSIAN);
        dropShadow.setColor(Color.rgb(0, 0, 0, 0.25));
        dropShadow.setSpread(-20);
        dropShadow.setRadius(30);

        actionsPane.setEffect(gaussianBlur);
        actionsPane.setEffect(dropShadow);

        addRecordPane.setEffect(gaussianBlur);
        addRecordPane.setEffect(dropShadow);
    }

    private void setOpen(Pane pane, int ...shearValue) {
        if (!pane.isVisible()) {
            pane.setVisible(true);

            // Анимация выезда меню
            TranslateTransition slideIn = new TranslateTransition(Duration.millis(300), pane);
            slideIn.setToX(0); // Переместить меню на видимую область

            // Анимация затемнения фона
            overlayPane.setVisible(true); // Показываем затемнение
            FadeTransition fadeIn = new FadeTransition(Duration.millis(300), overlayPane);
            fadeIn.setFromValue(0); // Полностью прозрачный
            fadeIn.setToValue(1); // Полностью непрозрачный

            // Запуск анимации
            slideIn.play();
            fadeIn.play();
        } else {
            pane.setVisible(false);

            // Анимация скрытия меню
            TranslateTransition slideOut = new TranslateTransition(Duration.millis(300), pane);
            slideOut.setToX(shearValue[0]); // Вернуть меню за пределы окна

            // Анимация скрытия затемнения
            FadeTransition fadeOut = new FadeTransition(Duration.millis(300), overlayPane);
            fadeOut.setFromValue(1); // Полностью непрозрачный
            fadeOut.setToValue(0); // Полностью прозрачный
            fadeOut.setOnFinished(event -> overlayPane.setVisible(false)); // Скрыть затемнение после анимации

            // Запуск анимации
            slideOut.play();
            fadeOut.play();
        }
    }

    @FXML
    private void openMenu(ActionEvent actionEvent) {
        setOpen(menuPane);
    }

    @FXML
    private void closeMenu(ActionEvent actionEvent) {
        setOpen(menuPane, -278);
    }

    private ResultSet getData(String table) throws SQLException {
        statement = connection.createStatement();
        query = "SELECT * FROM " + table + ";";
        return statement.executeQuery(query);
    }

    private void setColumns(ResultSet resultSet) throws SQLException {
        tableOutData.getColumns().clear();
        ObservableList<ObservableList<Object>> rows = FXCollections.observableArrayList(); // Хранилище строк. Такой подход используется потому что мы не знаем заранее из какой таблицы будут вытягиваться данные
        ResultSetMetaData rsMetaData = resultSet.getMetaData();
        int columnCount = rsMetaData.getColumnCount();

        for (int i = 1; i < columnCount; i++) {
            final int columnIndex = i - 1; // задаем индекс колонки
            String columnName = rsMetaData.getColumnLabel(i);

            TableColumn<ObservableList<Object>, Object> column = getObservableListObjectTableColumn(columnName, columnIndex, i);

            tableOutData.getColumns().add(column);
        }

        while (resultSet.next()) {
            ObservableList<Object> row = FXCollections.observableArrayList();
            for (int i = 1; i <= columnCount; i++) {
                row.add(resultSet.getObject(i));
            }
            rows.add(row);
        }

        tableOutData.setItems(rows);

    }

    private static TableColumn<ObservableList<Object>, Object> getObservableListObjectTableColumn(String columnName, int columnIndex, int i) {
        TableColumn<ObservableList<Object>, Object> column = new TableColumn<>(columnName);
        column.setCellValueFactory(param -> {
            if (param.getValue().size() > columnIndex) {
                return new javafx.beans.property.SimpleObjectProperty<>(param.getValue().get(columnIndex));
            } else {
                return null;
            }
        });

        if (i == 1) column.setStyle("-fx-background-radius: 12px 0px 0px 12px; -fx-border-radius: 12px 0px 0px 12px;");
        return column;
    }

    private void switchTables(Hyperlink hyperlink, String table) throws SQLException {
        // Если текущая гиперссылка не нулевая, то приглушаем ее цвет и включаем
        if (currentHyperlink != null) {
            currentHyperlink.setStyle("-fx-opacity: 0.35;");
            currentHyperlink.setDisable(false);
        }
        currentHyperlink = hyperlink; // Изменяем текущую гиперссылку на новую
        currentHyperlink.setStyle("-fx-opacity: 1.0;"); // делаем ее активной
        currentHyperlink.setDisable(true); // выключаем ее
        tableName.setText(currentHyperlink.getText()); // устанавливаем правильное название таблицы на главной странице
        setColumns(getData(table)); // Заполняем таблицу колонками
    }

    @FXML
    private void getActorsTable(ActionEvent actionEvent) throws SQLException {
        switchTables(actorsHyperlink, "actors");
        switchAvailableFilters(true);
    }

    @FXML
    private void getEmployeesTable(ActionEvent actionEvent) throws SQLException {
        switchTables(employeesHyperlink, "employees");
        switchAvailableFilters(true);
    }

    @FXML
    private void getMusiciansTable(ActionEvent actionEvent) throws SQLException {
        switchTables(musiciansHyperlink, "musicians");
        switchAvailableFilters(true);
    }

    @FXML
    private void getPerformancesTable(ActionEvent actionEvent) throws SQLException {
        switchTables(performancesHyperlink, "performances");
        switchAvailableFilters(true);
    }

    @FXML
    private void getRepertoiresTable(ActionEvent actionEvent) throws SQLException {
        switchTables(repertoireHyperlink, "repertoires");
        switchAvailableFilters(true);
    }

    @FXML
    private void getRolesTable(ActionEvent actionEvent) throws SQLException {
        switchTables(roleHyperlink, "roles");
        switchAvailableFilters(false);
    }

    @FXML
    private void getTicketsTable(ActionEvent actionEvent) throws SQLException {
        switchTables(ticketsHyperlink, "tickets");
        switchAvailableFilters(true);
    }

    @FXML
    private void getToursTable(ActionEvent actionEvent) throws SQLException {
        switchTables(toursHyperlink, "tours");
        switchAvailableFilters(true);
    }

    private void switchAvailableFilters(boolean available) {
        filterButton.setDisable(!available);
        if (available) {
            filterButton.setOpacity(1);
        } else {
            filterButton.setOpacity(0.5);
        }
    }

    @FXML
    private void addNewRecord(ActionEvent actionEvent) {

    }

    private void showSearchBox() {
        // Плавное появление searchBox
        searchBox.setVisible(true);

        FadeTransition fadeIn = new FadeTransition(Duration.millis(300), searchBox);
        fadeIn.setFromValue(0);
        fadeIn.setToValue(1);

        TranslateTransition slideIn = new TranslateTransition(Duration.millis(300), searchBox);
        slideIn.setFromX(50); // Начальное смещение по X
        slideIn.setToX(0);

        TranslateTransition slideSearchIn = new TranslateTransition(Duration.millis(300), searchButton);
        slideSearchIn.setFromX(-160);
        slideSearchIn.setToX(-160);

        ParallelTransition showAnimation = new ParallelTransition(fadeIn, slideIn, slideSearchIn);
        showAnimation.play();
    }

    private void hideSearchBox() {
        // Плавное скрытие searchBox
        FadeTransition fadeOut = new FadeTransition(Duration.millis(300), searchBox);
        fadeOut.setFromValue(1);
        fadeOut.setToValue(0);

        TranslateTransition slideOut = new TranslateTransition(Duration.millis(300), searchBox);
        slideOut.setFromX(0);
        slideOut.setToX(50);

        TranslateTransition slideSearchOut = new TranslateTransition(Duration.millis(300), searchButton);
        slideSearchOut.setFromX(0);
        slideSearchOut.setToX(0);

        ParallelTransition hideAnimation = new ParallelTransition(fadeOut, slideOut, slideSearchOut);
        hideAnimation.setOnFinished(event -> searchBox.setVisible(false)); // Полностью скрыть после анимации
        hideAnimation.play();
    }

    // TODO: доделать
    @FXML
    private void toggleSearch(ActionEvent actionEvent) {
        if (isSearchVisible) {
            hideSearchBox(); // Если открыто - скрыть
        } else {
            showSearchBox(); // Если скрыто - показать
        }
        isSearchVisible = !isSearchVisible;
    }

    private void loadSceneFilter(Pane pane, String path) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
        Node filterNode = loader.load();

        pane.getChildren().clear();
        pane.getChildren().add(filterNode);
    }

    @FXML
    private void openFilter(ActionEvent actionEvent) throws IOException, NullPointerException {
        setOpen(filtersPane);

        switch (currentHyperlink.getText()) {
            case "Актеры" -> loadSceneFilter(fPane, "../FXML/filters/actorsFilter-view.fxml");
            case "Работники" -> loadSceneFilter(fPane, "../FXML/filters/employeesFilter-view.fxml");
            case "Музыканты" -> loadSceneFilter(fPane, "../FXML/filters/musiciansFilter-view.fxml");
            case "Спектакли" -> loadSceneFilter(fPane, "../FXML/filters/performancesFilter-view.fxml");
            case "Репертуары" -> loadSceneFilter(fPane, "../FXML/filters/repertoiresFilter-view.fxml");
            case "Билеты" -> loadSceneFilter(fPane, "../FXML/filters/ticketsFilter-view.fxml");
            case "Гастроли" -> loadSceneFilter(fPane, "../FXML/filters/toursFilter-view.fxml");
            default -> logger.warning("load error");
        }
    }

    @FXML
    private void closeFilter(ActionEvent actionEvent) {
        setOpen(filtersPane, 334);
    }
}
