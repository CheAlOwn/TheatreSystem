package com.theatre.theatre_system;

import com.theatre.theatre_system.database.DBConnector;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    private static Stage primaryStage;

    @Override
    public void start(Stage stage) throws IOException {
        setPrimaryStage(stage);
        MainRecord.connection = new DBConnector().connect("administrator", "avokado");
        switchScenes(stage, "FXML/main-view.fxml", "Авторизация");
    }

    public static void main(String[] args) {
        launch();
    }

    public static void switchScenes(Stage stage, String path, String title) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(path));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle(title);
        stage.setScene(scene);
        stage.show();
        stage.setResizable(false);
    }

    public static Stage getPrimaryStage() {
        return primaryStage;
    }

    public static void setPrimaryStage(Stage primaryStage) {
        Main.primaryStage = primaryStage;
    }
}

// сделать заливку активной кнопки из панельки действия над таблицей