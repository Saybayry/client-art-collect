package com.example.client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;//import javafx.scene.layout.AnchorPane;

//import jdk.internal.access.JavaNioAccess;

import java.io.IOException;

public class MainApplication extends Application {
//    private JavaNioAccess loader;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("auth_panel.fxml"));
        Scene scene = new Scene(fxmlLoader.load());


        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}


