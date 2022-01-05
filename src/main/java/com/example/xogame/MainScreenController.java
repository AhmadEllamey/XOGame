package com.example.xogame;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Text;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class MainScreenController{

    @FXML
    private JFXButton playWithFriendButton;

    @FXML
    private JFXButton playWithPCButton;

    @FXML
    private JFXButton playOnlineButton;

    @FXML
    private JFXButton exitButton;

    @FXML
    private ListView<?> onlineUsersList;

    @FXML
    private Label playerOneNameLabel;

    @FXML
    private Label playerOneScoreLabel;

    @FXML
    private Label playerTwoNameLabel;

    @FXML
    private Label playerTwoScoreLabel;

    @FXML
    private JFXButton requestButton;

    @FXML
    private JFXButton replayButton;


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
    private JFXButton recordGameButton;

    @FXML
    private JFXButton surrenderButton;

    @FXML
    private ImageView userImageView;

    @FXML
    private Label usernameLabel;

    @FXML
    void manageExitButton(ActionEvent event) {

    }

    @FXML
    void managePlayOnlineButton(ActionEvent event) {

    }

    @FXML
    void managePlayWithFriendButton(ActionEvent event) {

    }

    @FXML
    void managePlayWithPCButton(ActionEvent event) {

    }

    @FXML
    void manageRecordGameButton(ActionEvent event) {


    }

    @FXML
    void manageSurrenderButton(ActionEvent event) {

    }

    @FXML
    void manageTheGameBoard(MouseEvent event) {
    }

    @FXML
    void manageRequestButton(ActionEvent event) {

    }

    @FXML
    void manageReplayButton(ActionEvent event) {

    }


}
