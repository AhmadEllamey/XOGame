package com.example.xogame;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class PlayersNameController implements Initializable {
    private static Stage stage;

    @FXML
    private GridPane gamePanel;

    @FXML
    private Button goBtn;

    @FXML
    private TextField playerOneNameTxt;

    @FXML
    private TextField playerTwoNameTxt;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    @FXML
    void manageGoBtn(ActionEvent event) {

        if(validateFields()==true) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("PlayWithFriendScreen.fxml"));
            PlayWithFriendController.getNames(playerOneNameTxt.getText().trim(),playerTwoNameTxt.getText().trim());
            try {
                Parent root = loader.load();
                PlayersNameController.getStage().close();
                TheMainClass.getMainStage().setTitle("PlayWithFriendScreen");
                TheMainClass.getMainStage().setScene(new Scene(root,545,400));
                TheMainClass.getMainStage().show();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }
    public boolean validateFields(){
        boolean checkValidity=false;
        if(playerOneNameTxt.getText().trim().isEmpty() || playerTwoNameTxt.getText().trim().isEmpty()){
            playerOneNameTxt.setPromptText("*Required");
            playerTwoNameTxt.setPromptText("*Required");
            playerOneNameTxt.setStyle("-fx-prompt-text-fill: #d00e0e");
            playerTwoNameTxt.setStyle("-fx-prompt-text-fill: #d00e0e");
            checkValidity=false;
        }
        else{
            checkValidity=true;
        }
        return checkValidity;
    }
    public static Stage getStage() {
        return stage;
    }
    public static void setStage(Stage s) {
        stage=s;
    }


}
