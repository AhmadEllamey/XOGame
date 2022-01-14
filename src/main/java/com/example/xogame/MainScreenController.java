package com.example.xogame;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Screen;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;


public class MainScreenController implements Initializable{


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
    private JFXButton rematchButton;

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
    private JFXButton viewRecordsButton;


    @FXML
    private AnchorPane c0;

    @FXML
    private AnchorPane c1;

    @FXML
    private AnchorPane c2;

    @FXML
    private AnchorPane c3;

    @FXML
    private AnchorPane c4;

    @FXML
    private AnchorPane c5;

    @FXML
    private AnchorPane c6;

    @FXML
    private AnchorPane c7;

    @FXML
    private AnchorPane c8;

    @FXML
    private JFXButton recordGameButton;

    @FXML
    private JFXButton surrenderButton;

    @FXML
    private ImageView userImageView;

    @FXML
    private Label usernameLabel;


    @FXML
    private GridPane gamePanel;


    @FXML
    private ScrollPane onlinePlayersPanel;

    // general info
    private static boolean playingOnlineMode = false ;
    private static boolean runningGameFlag = false ;
    private static String gameOrder;
    private static int boardCounter;
    private static String[] gameBoardMatrix = new String[9] ;
    Stage collectPlayersInfoScreen = new Stage();
    // player 1 information
    private static String playerOneName ;
    private static String PlayerOneShape;
    // player 2 information
    private static String playerTwoName ;
    private static String PlayerTwoShape;

    public static void setBoardCounter(int boardCounter) {
        MainScreenController.boardCounter = boardCounter;
    }

    public static void setGameOrder(String gameOrder) {
        MainScreenController.gameOrder = gameOrder;
    }

    public static String getPlayerOneShape() {
        return PlayerOneShape;
    }

    public static void setPlayerOneName(String playerOneName) {
        MainScreenController.playerOneName = playerOneName;
    }

    public static void setPlayerOneShape(String playerOneShape) {
        PlayerOneShape = playerOneShape;
    }

    public static void setPlayerTwoName(String playerTwoName) {
        MainScreenController.playerTwoName = playerTwoName;
    }

    public static void setPlayerTwoShape(String playerTwoShape) {
        PlayerTwoShape = playerTwoShape;
    }

    public static void setPlayingOnlineMode(boolean playingOnlineMode) {
        MainScreenController.playingOnlineMode = playingOnlineMode;
    }

    public static void setRunningGameFlag(boolean runningGameFlag) {
        MainScreenController.runningGameFlag = runningGameFlag;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        if(!playingOnlineMode){
            welcomeTheGuestUser();
        }else {
            welcomeTheOnlineUser();
        }

    }


    public void welcomeTheGuestUser(){

        // make the game board disabled
        gamePanel.setDisable(true);

        // make the record game disabled
        recordGameButton.setDisable(true);

        // make the online panel and play button disabled
        onlinePlayersPanel.setDisable(true);
        requestButton.setDisable(true);

        // make view record button disabled
        viewRecordsButton.setDisable(true);

        // make the image disabled
        userImageView.setDisable(true);

        // make the surrender button disabled
        surrenderButton.setDisable(true);

        // make the rematch button disabled
        rematchButton.setDisable(true);

        // set the information of the main screen

        usernameLabel.setText("Guest Player");

        playerOneNameLabel.setText("Player 1 : ");
        playerTwoNameLabel.setText("Player 2 : ");

        playerOneScoreLabel.setText(" 0 ");
        playerTwoScoreLabel.setText(" 0 ");

    }

    public void welcomeTheOnlineUser(){}

    public void setTheGameInfoForPlayWithAFriend(){

        // Load the popup
        FXMLLoader loader = new FXMLLoader(getClass().getResource("GetThePlayersInfoScreen.fxml"));
        try {
            loader.load();
            GetThePlayersInfoController controller = loader.getController();
            Parent popup = loader.getRoot();

            // Give popup a callback method
            controller.setup(
                    (value)->{
                        setThePlayersInfo();
                    }
            );

            try {
                collectPlayersInfoScreen.close();
            }catch (Exception e){
                e.printStackTrace();
            }

            collectPlayersInfoScreen.setTitle("Set Players Information !");
            collectPlayersInfoScreen.setScene(new Scene(popup,600 ,400));
            collectPlayersInfoScreen.show();

        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public void setThePlayersInfo(){

        playerOneNameLabel.setText(playerOneName);
        playerTwoNameLabel.setText(playerTwoName);

        // make the game board Enabled
        gamePanel.setDisable(false);
        surrenderButton.setDisable(false);
        clearTheGameBoard();


    }

    public void resetThePlayersInfo(){

        playerOneNameLabel.setText("Player 1 : ");
        playerTwoNameLabel.setText("Player 2 : ");
        playerOneScoreLabel.setText(" 0 ");
        playerTwoScoreLabel.setText(" 0 ");

        runningGameFlag = false ;

        // make the game board disabled
        gamePanel.setDisable(true);
        surrenderButton.setDisable(true);

        boardCounter = 0 ;

        clearTheGameBoard();

    }

    public void clearTheGameBoard(){
        c0.setBackground(null);
        c1.setBackground(null);
        c2.setBackground(null);
        c3.setBackground(null);
        c4.setBackground(null);
        c5.setBackground(null);
        c6.setBackground(null);
        c7.setBackground(null);
        c8.setBackground(null);
        boardCounter = 0 ;
    }

    public void increaseTheScore(int score , Label label){
        int total = 0 ;
        try{
            total = Integer.parseInt(label.getText());
        }catch (Exception e){
            e.printStackTrace();
        }
        total = total + score ;
        label.setText(String.valueOf(total));
    }

    public static void resetTheGameMatrix(){
        gameBoardMatrix[0] = "0";
        gameBoardMatrix[1] = "1";
        gameBoardMatrix[2] = "2";
        gameBoardMatrix[3] = "3";
        gameBoardMatrix[4] = "4";
        gameBoardMatrix[5] = "5";
        gameBoardMatrix[6] = "6";
        gameBoardMatrix[7] = "7";
        gameBoardMatrix[8] = "8";

    }

    public void checkTheWinner(){

        if(     (gameBoardMatrix[0].equals(gameBoardMatrix[1])&&gameBoardMatrix[1].equals(gameBoardMatrix[2])) ||
                (gameBoardMatrix[3].equals(gameBoardMatrix[4])&&gameBoardMatrix[4].equals(gameBoardMatrix[5])) ||
                (gameBoardMatrix[6].equals(gameBoardMatrix[7])&&gameBoardMatrix[7].equals(gameBoardMatrix[8])) ||
                (gameBoardMatrix[0].equals(gameBoardMatrix[3])&&gameBoardMatrix[3].equals(gameBoardMatrix[6])) ||
                (gameBoardMatrix[1].equals(gameBoardMatrix[4])&&gameBoardMatrix[4].equals(gameBoardMatrix[7])) ||
                (gameBoardMatrix[2].equals(gameBoardMatrix[5])&&gameBoardMatrix[5].equals(gameBoardMatrix[8])) ||
                (gameBoardMatrix[0].equals(gameBoardMatrix[4])&&gameBoardMatrix[4].equals(gameBoardMatrix[8])) ||
                (gameBoardMatrix[2].equals(gameBoardMatrix[4])&&gameBoardMatrix[4].equals(gameBoardMatrix[6])) ){

            // the player who just played won the round if gameOrder =  X-Turn then O won the round and vice versa
            String theWinner = "null" ;
            if(gameOrder.equals("X-Turn")){
                theWinner = "O-Turn";
            }else if(gameOrder.equals("O-Turn")){
                theWinner = "X-Turn";
            }

            if(!theWinner.equals("null")){
                if(theWinner.equals(PlayerOneShape)){
                    // player 1 won the game
                    increaseTheScore(3 , playerOneScoreLabel);

                }else {
                    // player 2 won the game
                    increaseTheScore(3 , playerTwoScoreLabel);

                }

                runningGameFlag = false ;
                gameOrder = PlayerOneShape ;
                // make the rematch button enabled
                rematchButton.setDisable(false);

                // make the surrender button disabled
                surrenderButton.setDisable(true);

            }


        }

    }



    @FXML
    void manageExitButton(ActionEvent event) {


        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Exit Confirmation !");
        alert.setHeaderText("Warning , Are You Sure ?");
        alert.setContentText("Are you Want To Exit The Application ?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            // ... user chose OK
            // close the Stage
            Stage stage = (Stage) exitButton.getScene().getWindow();
            stage.close();
        }


    }

    @FXML
    void managePlayOnlineButton(ActionEvent event) {


        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Transaction Confirmation !");
        alert.setHeaderText("Warning , Are You Sure ?");
        alert.setContentText("Are you Want To Go To The Login Page , Any progress here will be lost !");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            // ... user chose OK
            // open the main screen
            FXMLLoader loader = new FXMLLoader(TheMainClass.class.getResource("LoginScreen.fxml"));
            Parent mainCallWindowFXML = null;
            try {
                mainCallWindowFXML = loader.load();
                TheMainClass.getMainStage().setTitle("Login !");
                TheMainClass.getMainStage().setScene(new Scene(mainCallWindowFXML,450,500));
                TheMainClass.getMainStage().show();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }


    }

    @FXML
    void managePlayWithFriendButton(ActionEvent event) {

        if(!runningGameFlag){
            setTheGameInfoForPlayWithAFriend();
        }else {

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("New Game Start !");
            alert.setHeaderText("Warning , Are You Sure ?");
            alert.setContentText("Are you Want To Go To Start A New Game , Any progress here will be lost !");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK){
                // ... user chose OK
                resetThePlayersInfo();
                setTheGameInfoForPlayWithAFriend();
            }

        }

    }

    @FXML
    void managePlayWithPCButton(ActionEvent event) {

    }

    @FXML
    void manageRecordGameButton(ActionEvent event) {


    }

    @FXML
    void manageSurrenderButton(ActionEvent event) {

        if(runningGameFlag){

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Surrender Confirmation !");
            String playerName ;
            int playerNumber = 0 ;
            if(gameOrder.equals(PlayerOneShape)){
                playerName = playerOneName;
                playerNumber = 1 ;
            }else{
                playerName = playerTwoName;
                playerNumber = 2 ;
            }
            alert.setHeaderText("Are You Sure Player : "+playerName+" !");
            alert.setContentText("Are You Sure About Surrender The Game ?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK){
                // ... user chose OK
                runningGameFlag = false ;
                if(playerNumber == 1){
                    // player 1 won the game
                    increaseTheScore(3 , playerTwoScoreLabel);

                }else {
                    // player 2 won the game
                    increaseTheScore(3 , playerOneScoreLabel);

                }

                // make the rematch button enabled
                rematchButton.setDisable(false);

                // make the surrender button disabled
                surrenderButton.setDisable(true);

            }

        }

    }

    @FXML
    void manageRematchButton(ActionEvent event) {

        if(!runningGameFlag){

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Rematch Confirmation !");
            alert.setHeaderText("Are You Want To Play Again !");
            alert.setContentText("Play Again With The Same Info ?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK){
                // ... user chose OK
                clearTheGameBoard();
                resetTheGameMatrix();
                gameOrder = PlayerOneShape ;
                runningGameFlag = true ;
                // make the rematch button disabled
                rematchButton.setDisable(true);
                // make the surrender button enabled
                surrenderButton.setDisable(false);
            }

        }

    }

    @FXML
    void manageTheGameBoard(MouseEvent event) {

        // get the id of the clicked pane
        AnchorPane pane = (AnchorPane) event.getSource();
        if(pane.getBackground() == null && runningGameFlag){
            // new Image(url)
            Image image = null;
            String paneIdName = pane.getId();
            int index = Integer.parseInt(paneIdName.substring(paneIdName.length() - 1));
            System.out.println(index);
            if(gameOrder.equals("X-Turn")){
                image = new Image(String.valueOf(getClass().getResource("/Ximage.jpg")));
                gameBoardMatrix[index] = gameOrder ;
                gameOrder = "O-Turn";
            }else if (gameOrder.equals("O-Turn")){
                image = new Image(String.valueOf(getClass().getResource("/Oimage.jpg")));
                gameBoardMatrix[index] = gameOrder ;
                gameOrder = "X-Turn";
            }
            boardCounter++;

            // new BackgroundSize(width, height, widthAsPercentage, heightAsPercentage, contain, cover)
            BackgroundSize backgroundSize = new BackgroundSize(100, 100, true, true, true, false);
            // new BackgroundImage(image, repeatX, repeatY, position, size)
            BackgroundImage backgroundImage = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);
            // new Background(images...)
            Background background = new Background(backgroundImage);

            pane.setBackground(background);

            checkTheWinner();

        }

        if(boardCounter == 9 && runningGameFlag) {
            clearTheGameBoard();
            resetTheGameMatrix();
        }

    }

    @FXML
    void manageRequestButton(ActionEvent event) {

    }

    @FXML
    void manageViewRecordsButton(ActionEvent event) {

    }



}
