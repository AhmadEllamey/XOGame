package com.example.xogame;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ViewRankController implements Initializable {

    @FXML
    private JFXButton backBtn;

    @FXML
    private JFXListView<String> playersRankList;


    public static Handler handlerForVRC ;

    public static int classFlagVRC = 0 ;

    private static ViewRankController viewRankController ;

    public static ViewRankController getTheOnlineObject(){
        return viewRankController;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        handlerForVRC = Handler.getTheObject();
        String dataToSever = "{\"FunctionMode\": \"getTheLeaderBoardPlayers\",\"From\": \"Null\",\"To\": \"Null\"}";
        handlerForVRC.getPs().println(dataToSever);
        viewRankController = this ;
        classFlagVRC = 1 ;
    }
    @FXML
    void manageBackImg(MouseEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ProfileScreen.fxml"));
        try {
            Parent root = loader.load();
            ProfileController.getStage().setTitle("Profile Screen");
            ProfileController.getStage().setScene(new Scene(root,450,470));
            ProfileController.getStage().show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void whenTheServerAnswer(String msg){
        // ToDo write your code here
        // the msg will be a json array started looping from one .
    }


}
