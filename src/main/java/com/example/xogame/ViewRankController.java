package com.example.xogame;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ViewRankController implements Initializable {

    static String dataFromServer;
    private ClientHandler clientHandler;
    String dataToSever;


    @FXML
    private JFXButton backBtn;

    @FXML
    private JFXListView<?> playersRankList;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        clientHandler=ClientHandler.getClientHandler();
        dataToSever = "{\"FunctionMode\": \"" + "viewRank" + "\"}";
        clientHandler.sendData(dataToSever);
        System.out.println(dataFromServer);
        //JSONObject result=new JSONObject(dataFromServer);
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

    public static String ListenToServerData(String str){
        dataFromServer=str;
        return null;
    }


}
