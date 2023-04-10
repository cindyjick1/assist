package com.cindyjick.res.assist;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.logging.Level;
import java.util.logging.LogManager;

public class AssistApplication extends Application {

    public static void main(String[] args) {
        LogManager.getLogManager().getLogger("").setLevel(Level.ALL);
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(AssistApplication.class.getResource("home.fxml"));
        var scene = new Scene(fxmlLoader.load());
        primaryStage.setTitle("辅助工具");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
