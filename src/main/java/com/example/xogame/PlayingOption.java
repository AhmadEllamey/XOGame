package com.example.xogame;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import java.net.URL;
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

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


    }
}
