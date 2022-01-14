package com.example.xogame;

import com.jfoenix.controls.JFXButton;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class PlayingOption implements Initializable {

    @FXML
    private JFXButton onlineOption;

    @FXML
    private JFXButton withComputerOption;

    @FXML
    private JFXButton withFriendOption;


    @FXML
    void playOnline(MouseEvent event) {

        Handler handler = Handler.getTheObject();

        if(handler.getFlag()==1){
            try{
                FXMLLoader loader = new FXMLLoader(TheMainClass.class.getResource("LoginScreen.fxml"));
                Parent mainCallWindowFXML = loader.load();
                TheMainClass.getMainStage().setTitle("Home !");
                TheMainClass.getMainStage().setScene(new Scene(mainCallWindowFXML,450,500));
                TheMainClass.getMainStage().show();
            }catch (Exception ee){
                ee.printStackTrace();

            }
        }else {
            Handler.setHandler();
        }


    }

    @FXML
    void playWithComputer(MouseEvent event) {

    }

    @FXML
    void playWithFriend(MouseEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("PlayersNameScreen.fxml"));
        try {
            Parent profileScreen = loader.load();
            Stage stage=new Stage();
            stage.setScene(new Scene(profileScreen,510,285));
            stage.show();
            PlayersNameController.setStage(stage);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


    }
}
