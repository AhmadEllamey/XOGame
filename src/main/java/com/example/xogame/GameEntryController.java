package com.example.xogame;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class GameEntryController {

    @FXML
    private JFXButton myGamesBtn;

    @FXML
    private JFXButton playBtn;
    private Parent root;
    private Stage window;

    @FXML
    void goToGame(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
        window = (Stage) myGamesBtn.getScene().getWindow();
        window.setScene(new Scene(root,600,500));

    }

    @FXML
    void viewMyGames(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("GameReviewScreen.fxml"));
        window = (Stage) myGamesBtn.getScene().getWindow();
        window.setScene(new Scene(root,600,500));

    }

}
