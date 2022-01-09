package com.example.xogame;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class TheMainClass extends Application {

    private static Stage mainStage ;

    public static Stage getMainStage() {
        return mainStage;
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(TheMainClass.class.getResource("PlayingOption.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 500);
        stage.setTitle("XO Game Login !");
        stage.setScene(scene);
        mainStage = stage ;
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }


}