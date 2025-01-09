package com.theatre.theatre_system.controllers;

import com.theatre.theatre_system.Main;
import com.theatre.theatre_system.MainRecord;
import com.theatre.theatre_system.TableViewTools;
import com.theatre.theatre_system.controllers.forms.*;
import com.theatre.theatre_system.database.Queries;
import com.theatre.theatre_system.database.dao.*;
import com.theatre.theatre_system.models.Actor;
import com.theatre.theatre_system.search_system.Search;
import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.TranslateTransition;
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
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class MainController {
    public Button exitButton;
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
    private Button menuButton;

    @FXML
    private Button searchButton;

    @FXML
    private Button filterButton;

    @FXML
    private Rectangle overlayPane;

    @FXML
    private TableView<ObservableList<Object>> tableOutData;

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
    private List<Hyperlink> menuHyperlinks = new ArrayList<>();
    private List<String> tableNames = new ArrayList<>();
    private List<String> queries = new ArrayList<>();
    private Hyperlink currentHyperlink;
    private String currentTable;

    final double[] offsetX = {0}, offsetY = {0};

    private boolean isSearchVisible = false;

    Logger logger = Logger.getLogger(getClass().getName());


    @FXML
    void initialize() throws SQLException {
        menuHyperlinks.addAll(List.of(new Hyperlink("Актеры"), new Hyperlink("Работники"), new Hyperlink("Музыканты"), new Hyperlink("Спектакли"), new Hyperlink("Репертуары"), new Hyperlink("Роли"), new Hyperlink("Билеты"), new Hyperlink("Гастроли")));
        tableNames.addAll(List.of("actors", "employees", "musicians", "performances", "repertoires", "roles", "tickets", "tours"));
        queries.addAll(List.of(Queries.ACTOR_QUERY, Queries.EMPLOYEE_QUERY, Queries.MUSICIAN_QUERY, Queries.PERFORMANCE_QUERY, Queries.REPERTOIRE_QUERY, Queries.ROLE_QUERY, Queries.TICKET_QUERY, Queries.TOUR_QUERY));

        MainRecord.form = form;
        MainRecord.table = tableOutData;

        setSettingsForHyperlinks(menuHyperlinks);
        openMenuHyperlinks();

        setEffects();

        menuPane.setTranslateX(-278);

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
                if (searchTextField.getText().isEmpty()) {
                    switch (currentTable) {
                        case "actors" -> TableViewTools.fillTableView(tableOutData, Queries.ACTOR_QUERY);
                        case "employees" -> TableViewTools.fillTableView(tableOutData, Queries.EMPLOYEE_QUERY);
                        case "musicians" -> TableViewTools.fillTableView(tableOutData, Queries.MUSICIAN_QUERY);
                        case "performances" -> TableViewTools.fillTableView(tableOutData, Queries.PERFORMANCE_QUERY);
                        case "repertoires" -> TableViewTools.fillTableView(tableOutData, Queries.REPERTOIRE_QUERY);
                        case "tickets" -> TableViewTools.fillTableView(tableOutData, Queries.TICKET_QUERY);
                        case "roles" -> TableViewTools.fillTableView(tableOutData, Queries.ROLE_QUERY);
                        case "tours" -> TableViewTools.fillTableView(tableOutData, Queries.TOUR_QUERY);
                    }
                } else {
                    ResultSet rs = search.searchByParameter(currentTable, parametersBox.getSelectionModel().getSelectedItem().toString(), searchTextField.getText());
                    if (rs != null) TableViewTools.fillTableView(tableOutData, rs);
                }
            } catch (SQLException e) {
                throw new IllegalArgumentException(e);
            }
        });
    }

    private void setSettingsForHyperlinks(List<Hyperlink> hyperlinks) {
        int counter = 0;
        for (Hyperlink hp : hyperlinks) {
            hp.getStyleClass().add("menuText");
            String name = tableNames.get(counter);
            String query = queries.get(counter);
            hp.setOnAction(event -> {
                try {
                    switchTables(hp, name, query);
                    switchAvailableFilters(!hp.getText().equals("Роли"));
                    setDisableSearchField();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            });
            counter++;
        }
    }

    private void openMenuHyperlinks() throws SQLException {
        List<Hyperlink> current = new ArrayList<>();
        switch (MainRecord.user) {
            case "administrator" -> {
                menuVBox.getChildren().addAll(menuHyperlinks);
                switchTables(menuHyperlinks.getFirst(), "actors", Queries.ACTOR_QUERY);
            }
            case "hr" -> {
                for (Hyperlink hp : menuHyperlinks) {
                    if (hp.getText().equals("Актеры") || hp.getText().equals("Работники") || hp.getText().equals("Музыканты"))
                        current.add(hp);
                }
                menuVBox.getChildren().addAll(current);
                switchTables(current.getFirst(), "actors", Queries.ACTOR_QUERY);
            }
            case "accountant" -> {
                for (Hyperlink hp : menuHyperlinks) {
                    if (hp.getText().equals("Билеты")) {
                        menuVBox.getChildren().add(hp);
                    }
                }
                switchTables(menuHyperlinks.get(6), "tickets", Queries.TICKET_QUERY);
            }
            case "producer" -> {
                for (Hyperlink hp : menuHyperlinks)
                    if (hp.getText().equals("Спектакли") || hp.getText().equals("Репертуары") || hp.getText().equals("Роли") || hp.getText().equals("Актеры") || hp.getText().equals("Музыканты") || hp.getText().equals("Работники"))
                        current.add(hp);
                menuVBox.getChildren().addAll(current);
                switchTables(current.getFirst(), "actors", Queries.ACTOR_QUERY);
            }
            default -> {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("PERMISSION DENIED!");
                alert.setOnCloseRequest(event -> System.exit(0));
                alert.show();
            }
        }

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

    private void switchTables(Hyperlink hyperlink, String table, String query) throws SQLException {
        actionsPane.setDisable(false);

        if (currentHyperlink != null) {
            currentHyperlink.setStyle("-fx-opacity: 0.35;");
            currentHyperlink.setDisable(false);
        }
        currentHyperlink = hyperlink;
        currentHyperlink.setStyle("-fx-opacity: 1.0;");
        currentHyperlink.setDisable(true);
        tableName.setText(currentHyperlink.getText());
        currentTable = table;
        TableViewTools.fillTableView(MainRecord.table, query, currentTable);

        setSearchParameters();

        if (currentHyperlink.getText().equals("Работники") && MainRecord.user.equals("producer")) {
            actionsPane.setDisable(true);
        }
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

    private void setSearchParameters() throws SQLException {
        parametersBox.getSelectionModel().clearSelection();
        parametersBox.getItems().clear();

        for (TableColumn col : tableOutData.getColumns()) {
            if (col.isVisible()) {
                parametersBox.getItems().add(col.getText());
            }
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
            String[] objectId = MainRecord.table.getSelectionModel().getSelectedItem().toString().replace("[", "").replace("]", "").split(", ");

            switch (currentHyperlink.getText()) {
                case "Актеры":
                    ActorDAO.deleteById(Integer.parseInt(objectId[5]));
                    TableViewTools.fillTableView(tableOutData, Queries.ACTOR_QUERY);
                    break;
                case "Работники":
                    EmployeeDAO.deleteById(Integer.parseInt(objectId[0]));
                    TableViewTools.fillTableView(tableOutData, Queries.EMPLOYEE_QUERY);
                    break;
                case "Музыканты":
                    MusicianDAO.deleteById(Integer.parseInt(objectId[5]));
                    TableViewTools.fillTableView(tableOutData, Queries.MUSICIAN_QUERY);
                    break;
                case "Спектакли":
                    PerformanceDAO.deleteById(Integer.parseInt(objectId[0]));
                    TableViewTools.fillTableView(tableOutData, Queries.PERFORMANCE_QUERY);
                    break;
                case "Репертуары":
                    RepertoireDAO.deleteById(Integer.parseInt(objectId[2]));
                    TableViewTools.fillTableView(tableOutData, Queries.REPERTOIRE_QUERY);
                    break;
                case "Роли":
                    RoleDAO.deleteById(Integer.parseInt(objectId[0]));
                    TableViewTools.fillTableView(tableOutData, Queries.ROLE_QUERY);
                    break;
                case "Билеты":
                    TicketDAO.deleteById(Integer.parseInt(objectId[0]));
                    TableViewTools.fillTableView(tableOutData, Queries.TICKET_QUERY);
                    break;
                case "Гастроли":
                    TourDAO.deleteById(Integer.parseInt(objectId[6]));
                    TableViewTools.fillTableView(tableOutData, Queries.TOUR_QUERY);
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
//
    }

    @FXML
    private void clearFilters(ActionEvent actionEvent) throws SQLException {
        String query = "";
        switch (currentHyperlink.getText()) {
            case "Актеры":
                query = Queries.ACTOR_QUERY;
                break;
            case "Работники":
                query = Queries.EMPLOYEE_QUERY;
                break;
            case "Музыканты":
                query = Queries.MUSICIAN_QUERY;
                break;
            case "Спектакли":
                query = Queries.PERFORMANCE_QUERY;
                break;
            case "Репертуары":
                query = Queries.REPERTOIRE_QUERY;
                break;
            case "Роли":
                query = Queries.ROLE_QUERY;
                break;
            case "Билеты":
                query = Queries.TICKET_QUERY;
                break;
            case "Гастроли":
                query = Queries.TOUR_QUERY;
                break;
            default:
                break;
        }
        TableViewTools.fillTableView(tableOutData, query);
    }

    @FXML
    private void exitAction(ActionEvent actionEvent) throws IOException {
        Main.getPrimaryStage().close();
        Main.setPrimaryStage(new Stage());
        Main.switchScenes(Main.getPrimaryStage(), "FXML/forms/authorization-view.fxml", "Авторизация");
    }
}
