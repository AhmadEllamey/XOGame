package com.example.xogame;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class PlayWithFriendController implements Initializable {
    private static String NameOne;
    private static String NameTwo;
    @FXML
    private JFXButton ExitBtn;
    @FXML
    private Label playerOneNameTxt;

    @FXML
    private Label playerTowNameTxt;


    @FXML
    private Pane P_0_0;

    @FXML
    private Pane P_0_1;

    @FXML
    private Pane P_0_2;

    @FXML
    private Pane P_1_0;

    @FXML
    private Pane P_1_1;

    @FXML
    private Pane P_1_2;

    @FXML
    private Pane P_2_0;

    @FXML
    private Pane P_2_1;

    @FXML
    private Pane P_2_2;

    @FXML
    private JFXButton ReplayBtn;

    @FXML
    private JFXButton SurrenderBtn;
    @FXML
    private ImageView backBtn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        playerOneNameTxt.setText(NameOne);
        playerTowNameTxt.setText(NameTwo);
    }

    @FXML
    void manageBackBtn(MouseEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("PlayingOption.fxml"));
        try {
            Parent root = loader.load();
            TheMainClass.getMainStage().setScene(new Scene(root,450,470));
            TheMainClass.getMainStage().show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    void manageExitBtn(MouseEvent event) {

    }

    @FXML
    void manageReplayBtn(MouseEvent event) {

    }

    @FXML
    void manageSurrenderBtn(MouseEvent event) {

    }

    @FXML
    void manageTheGameBoard(MouseEvent event) {

    }


    public static void getNames(String player1,String player2){
        NameOne=player1;
        NameTwo=player2;
    }


}
