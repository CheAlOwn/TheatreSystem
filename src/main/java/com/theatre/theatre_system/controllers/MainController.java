package com.theatre.theatre_system.controllers;

import com.theatre.theatre_system.MainRecord;
import com.theatre.theatre_system.controllers.forms.*;
import com.theatre.theatre_system.database.dao.*;
import com.theatre.theatre_system.search_system.Search;
import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.TranslateTransition;
import javafx.beans.property.SimpleObjectProperty;
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
import java.util.ArrayList;
import java.util.Objects;
import java.util.logging.Logger;

public class MainController {
    @FXML
    private HBox importantMessage;

    @FXML
    private Label message;

    @FXML
    private Button closeFormButton;

    @FXML
    protected AnchorPane form;

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
    private Button editObjectButton;

    @FXML
    private Button removeObjectButton;

    private final Connection connection = MainRecord.connection;
    private Hyperlink currentHyperlink;
    private String currentTable;

    final double[] offsetX = {0}, offsetY = {0};

    private boolean isSearchVisible = false;

    Logger logger = Logger.getLogger(getClass().getName());


    @FXML
    void initialize() throws SQLException {
        MainRecord.form = form;
        MainRecord.table = tableOutData;

        setEffects();

        menuPane.setTranslateX(-278);

        switchTables(actorsHyperlink, "actors");

        actionsPane.setOnMousePressed(event -> {
            offsetX[0] = event.getSceneX() - actionsPane.getLayoutX();
            offsetY[0] = event.getSceneY() - actionsPane.getLayoutY();
        });

        actionsPane.setOnMouseDragged(event -> {
            double newX = event.getSceneX() - offsetX[0];
            double newY = event.getSceneY() - offsetY[0];

            // Ограничение перемещения внутри окна
            if (newX >= 0 && newX + actionsPane.getWidth() <= mainPane.getWidth()) {
                actionsPane.setLayoutX(newX);
            }
            if (newY >= 0 && newY + actionsPane.getHeight() <= mainPane.getHeight()) {
                actionsPane.setLayoutY(newY);
            }
        });

        searchBox.setVisible(false);
        searchBox.setOpacity(0);

        parametersBox.setOnAction(event -> searchTextField.setDisable(false));

        Search search = new Search();
        searchTextField.textProperty().addListener(text -> {
            try {
                if (searchTextField.getText().isEmpty())
                    setColumns(getData(currentTable));
                else
                    setColumns(search.searchByParameter(currentTable, (String) parametersBox.getSelectionModel().getSelectedItem(), searchTextField.getText()));
            } catch (SQLException e) {
                throw new IllegalArgumentException(e);
            }
        });
    }

    protected void setColumns(ResultSet resultSet) throws SQLException {
        MainRecord.table.getColumns().clear();
        ObservableList<ObservableList<Object>> rows = FXCollections.observableArrayList(); // Хранилище строк. Такой подход используется потому что мы не знаем заранее из какой таблицы будут вытягиваться данные
        ResultSetMetaData rsMetaData = resultSet.getMetaData();
        int columnCount = rsMetaData.getColumnCount();

        for (int i = 1; i <= columnCount; i++) {
            final int columnIndex = i - 1; // задаем индекс колонки
            String columnName = rsMetaData.getColumnLabel(i);

            TableColumn<ObservableList<Object>, Object> column = getObservableListObjectTableColumn(columnName, columnIndex, i);

            MainRecord.table.getColumns().add(column);
        }

        while (resultSet.next()) {
            ObservableList<Object> row = FXCollections.observableArrayList();
            for (int i = 1; i <= columnCount; i++) {
                row.add(resultSet.getObject(i));
            }
            rows.add(row);
        }

        MainRecord.table.setItems(rows);
    }

    private TableColumn<ObservableList<Object>, Object> getObservableListObjectTableColumn(String columnName, int columnIndex, int i) {
        TableColumn<ObservableList<Object>, Object> column = new TableColumn<>(columnName);
        column.setCellValueFactory(param -> {
            if (param.getValue().size() > columnIndex) {
                return new SimpleObjectProperty<>(param.getValue().get(columnIndex));
            } else {
                return null;
            }
        });

        if (i == 1) column.setStyle("-fx-background-radius: 12px 0px 0px 12px; -fx-border-radius: 12px 0px 0px 12px;");
        return column;
    }

    private ResultSet getData(String table) throws SQLException {
        return connection.createStatement().executeQuery("SELECT * FROM " + table + ";");
    }

    public ResultSet getDataByQuery(String query) throws SQLException {
        return connection.createStatement().executeQuery(query);
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
    }

    private void setOpen(Pane pane, int... shearValue) {
        if (!pane.isVisible()) {
            pane.setVisible(true);

            TranslateTransition slideIn = new TranslateTransition(Duration.millis(300), pane);
            slideIn.setToX(0);

            overlayPane.setVisible(true);
            FadeTransition fadeIn = new FadeTransition(Duration.millis(300), overlayPane);
            fadeIn.setFromValue(0);
            fadeIn.setToValue(1);

            slideIn.play();
            fadeIn.play();
        } else {
            pane.setVisible(false);

            TranslateTransition slideOut = new TranslateTransition(Duration.millis(300), pane);
            slideOut.setToX(shearValue[0]);

            FadeTransition fadeOut = new FadeTransition(Duration.millis(300), overlayPane);
            fadeOut.setFromValue(1);
            fadeOut.setToValue(0);
            fadeOut.setOnFinished(event -> overlayPane.setVisible(false));

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
        currentTable = table;

        setSearchParameters(table);
    }

    @FXML
    private void getActorsTable(ActionEvent actionEvent) throws SQLException {
        switchTables(actorsHyperlink, "actors");
        switchAvailableFilters(true);
        setDisableSearchField();
    }

    @FXML
    private void getEmployeesTable(ActionEvent actionEvent) throws SQLException {
        switchTables(employeesHyperlink, "employees");
        switchAvailableFilters(true);
        setDisableSearchField();
    }

    @FXML
    private void getMusiciansTable(ActionEvent actionEvent) throws SQLException {
        switchTables(musiciansHyperlink, "musicians");
        switchAvailableFilters(true);
        setDisableSearchField();
    }

    @FXML
    private void getPerformancesTable(ActionEvent actionEvent) throws SQLException {
        switchTables(performancesHyperlink, "performances");
        switchAvailableFilters(true);
        setDisableSearchField();
    }

    @FXML
    private void getRepertoiresTable(ActionEvent actionEvent) throws SQLException {
        switchTables(repertoireHyperlink, "repertoires");
        switchAvailableFilters(true);
        setDisableSearchField();
    }

    @FXML
    private void getRolesTable(ActionEvent actionEvent) throws SQLException {
        switchTables(roleHyperlink, "roles");
        switchAvailableFilters(false);
        setDisableSearchField();
    }

    @FXML
    private void getTicketsTable(ActionEvent actionEvent) throws SQLException {
        switchTables(ticketsHyperlink, "tickets");
        switchAvailableFilters(true);
        setDisableSearchField();
    }

    @FXML
    private void getToursTable(ActionEvent actionEvent) throws SQLException {
        switchTables(toursHyperlink, "tours");
        switchAvailableFilters(true);
        setDisableSearchField();
    }

    private void setDisableSearchField() {
        searchTextField.setDisable(true);
        searchTextField.clear();
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
    private void addNewRecord(ActionEvent actionEvent) throws IOException {
        // делаем видимой панель с формами
        formsPane.setVisible(true);

        // выводим сообщение об обязательных полях
        importantMessage.setVisible(true);

        TranslateTransition slideIn = new TranslateTransition(Duration.millis(300), importantMessage);
        slideIn.setToY(0);

        overlayPane.setVisible(true);
        FadeTransition fadeIn = new FadeTransition(Duration.millis(300), overlayPane);
        fadeIn.setFromValue(0);
        fadeIn.setToValue(1);

        slideIn.play();
        fadeIn.play();


        switch (currentHyperlink.getText()) {
            case "Актеры" -> loadScene(form, "../FXML/forms/actorsForm-view.fxml");
            case "Работники" -> loadScene(form, "../FXML/forms/employeesForm-view.fxml");
            case "Музыканты" -> loadScene(form, "../FXML/forms/musiciansForm-view.fxml");
            case "Спектакли" -> loadScene(form, "../FXML/forms/performancesForm-view.fxml");
            case "Репертуары" -> loadScene(form, "../FXML/forms/repertoiresForm-view.fxml");
            case "Билеты" -> loadScene(form, "../FXML/forms/ticketsForm-view.fxml");
            case "Гастроли" -> loadScene(form, "../FXML/forms/toursForm-view.fxml");
            case "Роли" -> loadScene(form, "../FXML/forms/rolesForm-view.fxml");
            default -> logger.warning("load error");
        }
    }

    @FXML
    private void closeForm(ActionEvent actionEvent) {
        formsPane.setVisible(false);
        importantMessage.setVisible(false);
        TranslateTransition slideOut = new TranslateTransition(Duration.millis(300), importantMessage);
        slideOut.setToY(-40);

        FadeTransition fadeOut = new FadeTransition(Duration.millis(300), overlayPane);
        fadeOut.setFromValue(1);
        fadeOut.setToValue(0);
        fadeOut.setOnFinished(event -> overlayPane.setVisible(false));

        slideOut.play();
        fadeOut.play();
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

    private void setSearchParameters(String table) throws SQLException {
        parametersBox.getSelectionModel().clearSelection();
        parametersBox.getItems().clear();

        ResultSetMetaData rsMetaData = getData(table).getMetaData();
        int columnCount = rsMetaData.getColumnCount();

        for (int i = 1; i <= columnCount; i++) {
            parametersBox.getItems().add(rsMetaData.getColumnLabel(i));
        }
    }

    //TODO: доделать
    @FXML
    private void toggleSearch(ActionEvent actionEvent) {
        if (isSearchVisible && searchTextField.getText().isEmpty()) {
            hideSearchBox(); // Если открыто - скрыть
            tableName.setVisible(true);
            isSearchVisible = false;
        } else if (!isSearchVisible) {
            showSearchBox();
            tableName.setVisible(false);
            isSearchVisible = true;
        }
    }

    protected void loadScene(Pane pane, String path) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
        Node node = loader.load();

        pane.getChildren().clear();
        pane.getChildren().add(node);

        MainRecord.nodeLoader = loader;
    }

    @FXML
    private void openFilter(ActionEvent actionEvent) throws IOException, NullPointerException {
        setOpen(filtersPane);

        switch (currentHyperlink.getText()) {
            case "Актеры" -> loadScene(fPane, "../FXML/filters/actorsFilter-view.fxml");
            case "Работники" -> loadScene(fPane, "../FXML/filters/employeesFilter-view.fxml");
            case "Музыканты" -> loadScene(fPane, "../FXML/filters/musiciansFilter-view.fxml");
            case "Спектакли" -> loadScene(fPane, "../FXML/filters/performancesFilter-view.fxml");
            case "Репертуары" -> loadScene(fPane, "../FXML/filters/repertoiresFilter-view.fxml");
            case "Билеты" -> loadScene(fPane, "../FXML/filters/ticketsFilter-view.fxml");
            case "Гастроли" -> loadScene(fPane, "../FXML/filters/toursFilter-view.fxml");
            default -> logger.warning("load error");
        }
    }

    @FXML
    private void closeFilter(ActionEvent actionEvent) {
        setOpen(filtersPane, 334);
    }

    @FXML
    private void editThisRecord(ActionEvent actionEvent) throws IOException, SQLException {
        try {
            String[] selected = MainRecord.table.getSelectionModel().getSelectedItem().toString().replace("[", "").replace("]", "").split(", ");

            // устанавливаем видимость и прозрачность затемняющей панели
            overlayPane.setVisible(true);
            overlayPane.setOpacity(0.5);

            // делаем видимой панель с формами
            formsPane.setVisible(true);

            // выводим сообщение об обязательных полях
            importantMessage.setVisible(true);

            TranslateTransition slideIn = new TranslateTransition(Duration.millis(300), importantMessage);
            slideIn.setToY(0);

            overlayPane.setVisible(true);
            FadeTransition fadeIn = new FadeTransition(Duration.millis(300), overlayPane);
            fadeIn.setFromValue(0);
            fadeIn.setToValue(1);

            slideIn.play();
            fadeIn.play();

            switch (currentHyperlink.getText()) {
                case "Актеры":
                    loadScene(form, "../FXML/forms/actorsForm-view.fxml");
                    ActorsFormController actorsFormController = MainRecord.nodeLoader.getController();
                    actorsFormController.load(selected);
                    break;
                case "Работники":
                    loadScene(form, "../FXML/forms/employeesForm-view.fxml");
                    EmployeeFormController employeeFormController = MainRecord.nodeLoader.getController();
                    employeeFormController.load(selected);
                    break;
                case "Музыканты":
                    loadScene(form, "../FXML/forms/musiciansForm-view.fxml");
                    MusiciansFormController musiciansFormController = MainRecord.nodeLoader.getController();
                    musiciansFormController.load(selected);
                    break;
                case "Спектакли":
                    loadScene(form, "../FXML/forms/performancesForm-view.fxml");
                    PerformancesFormController performancesFormController = MainRecord.nodeLoader.getController();
                    performancesFormController.load(selected);
                    break;
                case "Репертуары":
                    loadScene(form, "../FXML/forms/repertoiresForm-view.fxml");
                    RepertoiresFormController repertoiresFormController = MainRecord.nodeLoader.getController();
                    repertoiresFormController.load(selected);
                    break;
                case "Роли":
                    loadScene(form, "../FXML/forms/rolesForm-view.fxml");
                    RolesFormController rolesFormController = MainRecord.nodeLoader.getController();
                    rolesFormController.load(selected);
                    break;
                case "Билеты":
                    loadScene(form, "../FXML/forms/ticketsForm-view.fxml");
                    TicketsFormController ticketsFormController = MainRecord.nodeLoader.getController();
                    ticketsFormController.load(selected);
                    break;
                case "Гастроли":
                    loadScene(form, "../FXML/forms/toursForm-view.fxml");
                    ToursFormController toursFormController = MainRecord.nodeLoader.getController();
                    toursFormController.load(selected);
                    break;
                default:
                    break;
            }

        } catch (NullPointerException npe) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Select a row from the table!");
            alert.show();
        }
    }

    @FXML
    private void deleteThisRecord(ActionEvent actionEvent) {
        try {
            String objectId = MainRecord.table.getSelectionModel().getSelectedItem().toString().replace("[", "").replace("]", "").split(", ")[0];

            switch (currentHyperlink.getText()) {
                case "Актеры":
                    ActorDAO.deleteById(Integer.parseInt(objectId));
                    setColumns(getData("actors"));
                    break;
                case "Работники":
                    EmployeeDAO.deleteById(Integer.parseInt(objectId));
                    setColumns(getData("employees"));
                    break;
                case "Музыканты":
                    MusicianDAO.deleteById(Integer.parseInt(objectId));
                    setColumns(getData("musicians"));
                    break;
                case "Спектакли":
                    PerformanceDAO.deleteById(Integer.parseInt(objectId));
                    setColumns(getData("performances"));
                    break;
                case "Репертуары":
                    RepertoireDAO.deleteById(Integer.parseInt(objectId));
                    setColumns(getData("repertoires"));
                    break;
                case "Роли":
                    RoleDAO.deleteById(Integer.parseInt(objectId));
                    setColumns(getData("roles"));
                    break;
                case "Билеты":
                    TicketDAO.deleteById(Integer.parseInt(objectId));
                    setColumns(getData("tickets"));
                    break;
                case "Гастроли":
                    TourDAO.deleteById(Integer.parseInt(objectId));
                    setColumns(getData("tours"));
                    break;
                default:
                    break;
            }

        } catch (NullPointerException npe) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Select a row from the table!");
            alert.show();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
