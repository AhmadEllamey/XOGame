package com.example.xogame;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXToggleButton;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import static java.lang.Thread.sleep;

public class OnlineGameScreenController implements Initializable {


    @FXML
    private JFXButton exitButton;

    @FXML
    private TableView<RowData> onlineUsersList = new TableView<>();

    @FXML
    private TableColumn<RowData, String> userNameColumn;

    @FXML
    private TableColumn<RowData, String> ipColumn;

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
    private JFXToggleButton recordGameButton;

    @FXML
    private JFXButton surrenderButton;


    @FXML
    private Label usernameLabel;


    @FXML
    private GridPane gamePanel;


    // general info
    private static boolean runningGameFlag = false ;
    private static int boardCounter;
    private static String[] gameBoardMatrix = new String[9] ;
    public static String WinnerOrLoserVid = null;
    boolean flagToSave ;
    Stage videoStage = new Stage();
    String myIP;
    String opponentIP;
    String playerAvatar ;
    String playerTurn;
    Stage recordedGame = new Stage();


    public static int classFlag = 0 ;

    public void setOpponentIP(String opponentIP) {
        this.opponentIP = opponentIP;
    }

    public JFXButton getExitButton() {
        return exitButton;
    }
    public static Handler handler ;

    private static OnlineGameScreenController onlineGameScreenController ;

    public static OnlineGameScreenController getTheOnlineObject(){
        return onlineGameScreenController;
    }


    // function is ready fpr use .


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        handler = Handler.getTheObject();
        userNameColumn.setCellValueFactory(new PropertyValueFactory<>("userName"));
        ipColumn.setCellValueFactory(new PropertyValueFactory<>("userIP"));
        myIP = handler.getMyName() ;
        flagToSave = false ;

        // ToDO Set On Close Action For The App .

        TheMainClass.getMainStage().getScene().getWindow().setOnCloseRequest(windowEvent -> {

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Exit Confirmation !");
            alert.setHeaderText("Warning , Are You Sure ?");
            alert.setContentText("Are you Want To Exit The Application ?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK){
                // ... user chose OK
                // close the Stage


                TheMainClass.getMainStage().close();
                closeTheConnection();
                System.exit(0);
                Platform.exit();
            }else{
                windowEvent.consume();
            }

        });




        //ToDo Add An implementation for the record button

        /*
         new Thread(()->{
            String messageToTheServer = "{\"FunctionMode\" : \"getTheOnlinePlayersOnTheServerRequest\"" +
                    " ,\"From\" : \"myIP\",\"To\" : \"opponentIP\" }";
            while (true){
                ps.println(messageToTheServer);
                try {
                    sleep(60000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }

        }).start();

         */

        usernameLabel.setText(handler.getMyRealName());
        surrenderButton.setDisable(true);
        rematchButton.setDisable(true);
        recordGameButton.setDisable(true);
        gamePanel.setDisable(true);


        onlineGameScreenController = this;

        classFlag = 1 ;

    }



    // the class is complete and ready for use
    public class RowData {
        private final SimpleStringProperty userName ;
        private final SimpleStringProperty userIP ;

        public RowData(String userName, String userIP) {
            this.userName = new SimpleStringProperty(userName);
            this.userIP = new SimpleStringProperty(userIP);
        }

        public String getUserName() {
            return userName.get();
        }

        public SimpleStringProperty userNameProperty() {
            return userName;
        }

        public String getUserIP() {
            return userIP.get();
        }

        public SimpleStringProperty userIPProperty() {
            return userIP;
        }
    }

    // the function is ready for use
    public void loadPlayerInfo(String msg){

        ObservableList<RowData> items = FXCollections.observableArrayList();

        onlineUsersList.getItems().clear();
        JSONArray array = new JSONArray(msg);
        System.out.println(array);

        for (int i = 1 ;  i < array.length(); i++){

            JSONObject object = array.getJSONObject(i);
            System.out.println(handler.getMyRealName());
            System.out.println("We got Here");
            System.out.println(object.getString("PlayerName"));
            if(!object.getString("PlayerName").equals(handler.getMyRealName())){
                items.add(new RowData(object.getString("PlayerName"),object.getString("PlayerIP")));
                System.out.println("we are in the if ");
            }

        }

        onlineUsersList.setItems(items);

    }

    // function is ready for use
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

    // function is ready for use
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

    // function is ready for use
    public boolean checkTheWinner(){
        return (gameBoardMatrix[0].equals(gameBoardMatrix[1]) && gameBoardMatrix[1].equals(gameBoardMatrix[2])) ||
                (gameBoardMatrix[3].equals(gameBoardMatrix[4]) && gameBoardMatrix[4].equals(gameBoardMatrix[5])) ||
                (gameBoardMatrix[6].equals(gameBoardMatrix[7]) && gameBoardMatrix[7].equals(gameBoardMatrix[8])) ||
                (gameBoardMatrix[0].equals(gameBoardMatrix[3]) && gameBoardMatrix[3].equals(gameBoardMatrix[6])) ||
                (gameBoardMatrix[1].equals(gameBoardMatrix[4]) && gameBoardMatrix[4].equals(gameBoardMatrix[7])) ||
                (gameBoardMatrix[2].equals(gameBoardMatrix[5]) && gameBoardMatrix[5].equals(gameBoardMatrix[8])) ||
                (gameBoardMatrix[0].equals(gameBoardMatrix[4]) && gameBoardMatrix[4].equals(gameBoardMatrix[8])) ||
                (gameBoardMatrix[2].equals(gameBoardMatrix[4]) && gameBoardMatrix[4].equals(gameBoardMatrix[6]));
    }



    // the function is ready for use
    public void closeTheConnection(){
        try {
            handler.getPs().close();
            handler.getDis().close();
            handler.getMySocket().close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }


    // the function is ready
    @FXML
    void manageViewRecordsButton(ActionEvent event) {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("ViewRecordScreen.fxml"));
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            recordedGame.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        Parent popup = loader.getRoot();
        recordedGame.setTitle("View All Recorded Games !");
        recordedGame.setScene(new Scene(popup,600 ,400));
        recordedGame.show();

    }

    // the function is ready
    @FXML
    void manageExitButton(ActionEvent event) {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Exit Confirmation !");
        alert.setHeaderText("Warning , Are You Sure ?");
        alert.setContentText("Are you Want To Exit The Application ?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            // ... user chose OK
            // close the Stage
            Stage stage = (Stage) exitButton.getScene().getWindow();
            stage.close();
            closeTheConnection();
            System.exit(0);
            Platform.exit();
        }

    }

    // the function is ready
    @FXML
    void manageHomeButton(ActionEvent event) {


        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Transaction Confirmation !");
        alert.setHeaderText("Warning , Are You Sure ?");
        alert.setContentText("Are you Want To Go To The Home Page , Any progress here will be lost !");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            // ... user chose OK
            // open the main screen

             try {
                FXMLLoader loader = new FXMLLoader(TheMainClass.class.getResource("PlayingOption.fxml"));
                Parent mainCallWindowFXML = loader.load();
                TheMainClass.getMainStage().setTitle("Home !");
                TheMainClass.getMainStage().setScene(new Scene(mainCallWindowFXML,450,500));
                TheMainClass.getMainStage().show();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }


    }

    // The function is ready
    @FXML
    void managePlayWithFriendButton(ActionEvent event) {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Transaction Confirmation !");
        alert.setHeaderText("Warning , Are You Sure ?");
        alert.setContentText("Are you Want To Go To The Home Page , Any progress here will be lost !");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            // ... user chose OK
            // open the main screen

            try {
                FXMLLoader loader = new FXMLLoader(TheMainClass.class.getResource("PlayingOption.fxml"));
                Parent mainCallWindowFXML = loader.load();
                TheMainClass.getMainStage().setTitle("Home !");
                TheMainClass.getMainStage().setScene(new Scene(mainCallWindowFXML,450,500));
                TheMainClass.getMainStage().show();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }



    }

    // the function is ready
    @FXML
    void managePlayWithPCButton(ActionEvent event) {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Transaction Confirmation !");
        alert.setHeaderText("Warning , Are You Sure ?");
        alert.setContentText("Are you Want To Go To The Home Page , Any progress here will be lost !");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            // ... user chose OK
            // open the main screen

            try {
                FXMLLoader loader = new FXMLLoader(TheMainClass.class.getResource("PlayingOption.fxml"));
                Parent mainCallWindowFXML = loader.load();
                TheMainClass.getMainStage().setTitle("Home !");
                TheMainClass.getMainStage().setScene(new Scene(mainCallWindowFXML,450,500));
                TheMainClass.getMainStage().show();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }

    // the function is ready for use

    /*
    @FXML
    void goToProfilePage(MouseEvent event) {

        try {
            MainThread.stop();
            FXMLLoader loader = new FXMLLoader(TheMainClass.class.getResource("ProfileScreen.fxml"));
            Parent mainCallWindowFXML = loader.load();
            if(!profileGame.isShowing()){
                profileGame.close();
            }
            profileGame.setTitle("Profile !");
            profileGame.setScene(new Scene(mainCallWindowFXML,600,600));
            profileGame.show();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

     */


    // the function is ready for use
    @FXML
    void manageRecordGameButton(ActionEvent event) {

        // call the function that saved the game at the end of the game and pass the sequence , player that saved it and the opponent
        flagToSave = recordGameButton.isSelected();

    }

    // function is ready for use
    @FXML
    void manageSurrenderButton(ActionEvent event) {


        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Surrender Confirmation !");
        alert.setHeaderText("Warning , Are You Sure ?");
        alert.setContentText("Are you Want To Surrender The Game ?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            // ... user chose OK

            ILost();
            // at first send a message to the database to update my score

            String msgToTheServer = "{\"FunctionMode\" : \"endGameRequestWithSurrenderRequest\" ,\"From\" : \""
                    +myIP+"\",\"To\" : \""+opponentIP+"\",\"ShouldDoNow\" : \"IWonYouLose\"}";
            handler.getPs().println(msgToTheServer);

        }

    }


    @FXML
    void manageTheGameBoard(MouseEvent event) {

        String TheRequestForUpdateTheGameBoard = null ;
        int index = 0;
        String paneIdName = null ;


        // get the id of the clicked pane
        AnchorPane pane = (AnchorPane) event.getSource();
        if(pane.getBackground() == null && runningGameFlag && playerTurn.equals(playerAvatar)){
            // new Image(url)
            Image image = null;
            paneIdName = pane.getId();
            index = Integer.parseInt(paneIdName.substring(paneIdName.length() - 1));
            System.out.println(index);
            if(playerAvatar.equals("X")){
                image = new Image(String.valueOf(getClass().getResource("/Ximage.jpg")));
                playerTurn = "O";
            }else {
                image = new Image(String.valueOf(getClass().getResource("/Oimage.jpg")));
                playerTurn = "X";
            }

            // new BackgroundSize(width, height, widthAsPercentage, heightAsPercentage, contain, cover)
            BackgroundSize backgroundSize = new BackgroundSize(100, 100, true, true, true, false);
            // new BackgroundImage(image, repeatX, repeatY, position, size)
            BackgroundImage backgroundImage = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);
            // new Background(images...)
            Background background = new Background(backgroundImage);
            pane.setBackground(background);
            gameBoardMatrix[index] = playerAvatar ;
            boardCounter++;
            // ToDo we need to send player turn, index , panel id that the player put a mark , player avatar that resides there and the boardCounter ....
            TheRequestForUpdateTheGameBoard = "{\"FunctionMode\" : \"sendUpdateTheGameBoardRequest\" ,\"From\" : \""
                    +myIP+"\",\"To\" : \""+opponentIP+"\",\"PlayerTurn\" : \""+playerTurn+"\",\"IndexToPlaceTheImage\" : \""
                    +paneIdName+"\",\"TheIndexInTheArray\" : \""+index+"\" ,\"ShapeToPut\" : \""+playerAvatar+"\",\"ShouldDoNow\" : \"KeepPlaying\"}";



            if(checkTheWinner()){
                // send I won message
                TheRequestForUpdateTheGameBoard = "{\"FunctionMode\" : \"sendIWonRequest\" ,\"From\" : \""
                        +myIP+"\",\"To\" : \""+opponentIP+"\",\"ShouldDoNow\" : \"IWonYouLose\"}";
                IWon();
            }else if(boardCounter == 9 && runningGameFlag) {
                clearTheGameBoard();
                resetTheGameMatrix();
                TheRequestForUpdateTheGameBoard = "{\"FunctionMode\" : \"sendUpdateTheGameBoardRequest\" ,\"From\" : \""
                        +myIP+"\",\"To\" : \""+opponentIP+"\",\"PlayerTurn\" : \""+playerTurn+"\",\"IndexToPlaceTheImage\" : \""
                        +paneIdName+"\",\"TheIndexInTheArray\" : \""+index+"\" ,\"ShapeToPut\" : \""+playerAvatar+"\",\"ShouldDoNow\" : \"ClearAllAndKeepTheMatch\"}";
            }

            handler.getPs().println(TheRequestForUpdateTheGameBoard);

        }



    }

    // the function is ready for use
    public void UpdateTheGameBoard(String msg){

        JSONObject jsonObject = new JSONObject(msg);

        //ToDo give the player the turn
        playerTurn = jsonObject.getString("PlayerTurn");

        if(jsonObject.getString("ShouldDoNow").equals("KeepPlaying")){

            //ToDo update the array
            gameBoardMatrix[Integer.parseInt(jsonObject.getString("TheIndexInTheArray"))] = jsonObject.getString("ShapeToPut");

            //ToDo update the image in the right place
            Image image = null ;
            if(jsonObject.getString("ShapeToPut").equals("X")){
                image = new Image(String.valueOf(getClass().getResource("/Ximage.jpg")));
            }else {
                image = new Image(String.valueOf(getClass().getResource("/Oimage.jpg")));
            }
            // new BackgroundSize(width, height, widthAsPercentage, heightAsPercentage, contain, cover)
            BackgroundSize backgroundSize = new BackgroundSize(100, 100, true, true, true, false);
            // new BackgroundImage(image, repeatX, repeatY, position, size)
            BackgroundImage backgroundImage = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);
            // new Background(images...)
            Background background = new Background(backgroundImage);
            String PlaceHolder = jsonObject.getString("IndexToPlaceTheImage") ;
            switch (PlaceHolder) {
                case "c0" -> c0.setBackground(background);
                case "c1" -> c1.setBackground(background);
                case "c2" -> c2.setBackground(background);
                case "c3" -> c3.setBackground(background);
                case "c4" -> c4.setBackground(background);
                case "c5" -> c5.setBackground(background);
                case "c6" -> c6.setBackground(background);
                case "c7" -> c7.setBackground(background);
                case "c8" -> c8.setBackground(background);
            }

        }else {
            // ToDo Clear The Board
            clearTheGameBoard();
            //ToDo ClearTheMatrix
            resetTheGameMatrix();
        }

    }

    // function is ready for use
    public void showVideo(){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("GameOverScreen.fxml"));
        try {
            loader.load();
            Parent popup = loader.getRoot();
            videoStage.setScene(new Scene(popup,640 ,480));
            videoStage.setResizable(false);
            videoStage.initStyle(StageStyle.UNDECORATED);
            videoStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // the function is ready for use
    public void ILost(){

        WinnerOrLoserVid = "loser.mp4";

        FinishedAGame();

        // third update my score
        int x = Integer.parseInt(playerTwoScoreLabel.getText().trim());
        playerTwoScoreLabel.setText(String.valueOf(x + 3));

        // view me the winner video
        //showVideo();

    }

    // the function is ready for use
    public void IWon(){

        // ToDo >>>>> things when i won

        // at first send a message to the database to update my score

        String msgToTheServer = "{\"FunctionMode\" : \"updateScoreInfoRequest\" ,\"From\" : \""
                +myIP+"\",\"To\" : \""+opponentIP+"\",\"UserName\" : \""+handler.getMyRealName()+"\"}";
        handler.getPs().println(msgToTheServer);

        FinishedAGame();

        // third update my score
        int x = Integer.parseInt(playerOneScoreLabel.getText().trim());
        playerOneScoreLabel.setText(String.valueOf(x + 3));

        // view me the winner video
        WinnerOrLoserVid = "winner.mp4";
        //showVideo();
    }

    // the function is ready for use
    public void FinishedAGame(){

        onlineUsersList.setDisable(false);
        recordGameButton.setDisable(true);
        surrenderButton.setDisable(true);
        rematchButton.setDisable(false);
        requestButton.setDisable(false);
        gamePanel.setDisable(true);
        runningGameFlag = false ;

    }


    // Manage requests

    // the function is ready for use
    @FXML
    void manageRequestButton(ActionEvent event) {

        if(!onlineUsersList.getSelectionModel().getSelectedItems().isEmpty()){
            try{
                //get his Choice
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Choose Confirmation !");
                alert.setHeaderText("Which Symbol Do You Want to Use ? ");
                alert.setContentText("Choose (X) Or (O)");
                Optional<ButtonType> result = alert.showAndWait();
                Button okButton = (Button) alert.getDialogPane().lookupButton( ButtonType.OK );
                okButton.setText("Choose X");
                Button okButton2 = (Button) alert.getDialogPane().lookupButton( ButtonType.CANCEL );
                okButton2.setText("Choose O");
                playerAvatar = null ;
                if (result.get() == ButtonType.OK){
                     // user chose x
                    playerAvatar = "O" ;
                }else if (result.get() == ButtonType.CANCEL){
                    playerAvatar = "X" ;
                }
                if(playerAvatar!=null){
                    // get the selected player from the list
                    RowData selectedItem = onlineUsersList.getSelectionModel().getSelectedItem();
                    opponentIP = selectedItem.getUserIP();
                    // send the request to the server to redirect it to the opponent
                    String messageToTheServer = "{\"FunctionMode\" : \"sendPlayRequest\" ,\"From\" : \""
                            +myIP+"\",\"To\" : \""+opponentIP+"\",\"Answer\" : \"Yes\",\"YourType\" : \""
                            +playerAvatar+"\",\"FromUserName\" : \""+handler.getMyRealName()+"\"}";
                    System.out.println("The SendPlayerRequest"+messageToTheServer);
                    handler.getPs().println(messageToTheServer);
                }else {
                    //get his Choice Confirm
                    Alert alert2 = new Alert(Alert.AlertType.CONFIRMATION);
                    alert2.setTitle("Alert");
                    alert2.setHeaderText("Your request is canceled !");
                    alert2.setContentText("Please Choose X Or O To Continue");
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }

    }

    // the function is ready for use
    @FXML
    void manageRematchButton(ActionEvent event) {

        try{
            //get his Choice
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Choose Confirmation !");
            alert.setHeaderText("Which Symbol Do You Want to Use ? ");
            alert.setContentText("Choose (X) Or (O)");
            Optional<ButtonType> result = alert.showAndWait();
            ((Button) alert.getDialogPane().lookupButton(ButtonType.OK)).setText("Choose X");
            ((Button) alert.getDialogPane().lookupButton(ButtonType.CANCEL)).setText("Choose O");
            playerAvatar = null ;
            if (result.get() == ButtonType.OK){
                // user chose x
                playerAvatar = "O" ;
            }else if (result.get() == ButtonType.CANCEL){
                playerAvatar = "X" ;
            }
            if(playerAvatar!=null){
                // send the request to the server to redirect it to the opponent
                String messageToTheServer = "{\"FunctionMode\" : \"sendRematchRequest\" ,\"From\" : \""
                        +myIP+"\",\"To\" : \""+opponentIP+"\",\"Answer\" : \"Yes\",\"YourType\" : \""
                        +playerAvatar+"\",\"FromUserName\" : \""+handler.getMyRealName()+"\"}";
                System.out.println("The SendPlayerRequest"+messageToTheServer);
                handler.getPs().println(messageToTheServer);
            }else {
                //get his Choice Confirm
                Alert alert2 = new Alert(Alert.AlertType.CONFIRMATION);
                alert2.setTitle("Alert");
                alert2.setHeaderText("Your request is canceled !");
                alert2.setContentText("Please Choose X Or O To Continue");
            }
        }catch (Exception e){
            e.printStackTrace();
        }


    }

    // the function is ready for use
    public void showAPlayingRequest(String msg){

        JSONObject jsonObject = new JSONObject(msg);

        if(!runningGameFlag){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Play Request");
            alert.setHeaderText("Can You Play With Me I'm "+jsonObject.getString("FromUserName")+" ?");
            alert.setContentText("Accept Or Refuse !");
            ((Button) alert.getDialogPane().lookupButton(ButtonType.OK)).setText("Accept");
            ((Button) alert.getDialogPane().lookupButton(ButtonType.CANCEL)).setText("Reject");
            Optional<ButtonType> result = alert.showAndWait();
            String TypeToSend = null ;
            if (result.get() == ButtonType.OK){
                // ... user chose OK
                // open the main screen
                // send the request to the server to redirect it to the opponent
                if(jsonObject.getString("YourType").equals("X")){
                    TypeToSend = "O";
                }else{
                    TypeToSend = "X";
                }
                AcceptTheRequest(jsonObject);
                String messageToTheServer = "{\"FunctionMode\" : \"sendAnswerToPlayRequest\" ,\"From\" : \""
                        +myIP+"\",\"To\" : \""+jsonObject.getString("From")+"\",\"Answer\" : \"Yes\",\"YourType\" : \""
                        +TypeToSend+"\",\"FromUserName\" : \""+handler.getMyRealName()+"\"}";
                System.out.println("The sendAnswerToPlayRequest When No"+messageToTheServer);
                handler.getPs().println(messageToTheServer);
                playerTurn = TypeToSend ;
            }else if (result.get() == ButtonType.CANCEL){
                // ... user chose OK
                // open the main screen
                // send the request to the server to redirect it to the opponent
                String messageToTheServer = "{\"FunctionMode\" : \"sendAnswerToPlayRequest\" ,\"From\" : \""
                        +myIP+"\",\"To\" : \""+jsonObject.getString("From")+"\",\"Answer\" : \"No\",\"YourType\" : \""
                        + null +"\",\"FromUserName\" : \""+handler.getMyRealName()+"\"}";
                System.out.println("The sendAnswerToPlayRequest When No"+messageToTheServer);
                handler.getPs().println(messageToTheServer);
            }
        }else {
            String messageToTheServer = "{\"FunctionMode\" : \"sendAnswerToPlayRequest\" ,\"From\" : \""
                    +myIP+"\",\"To\" : \""+jsonObject.getString("From")+"\",\"Answer\" : \"No\",\"YourType\" : \""
                    +null+"\",\"FromUserName\" : \""+handler.getMyRealName()+"\"}";
            System.out.println("The sendAnswerToPlayRequest When The Player In A Game"+messageToTheServer);
            handler.getPs().println(messageToTheServer);
        }

    }

    // the function is ready for use
    public void showARematchRequest(String msg){

        JSONObject jsonObject = new JSONObject(msg);

        if(!runningGameFlag){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Play Request");
            alert.setHeaderText("Can You Play With Me I'm "+jsonObject.getString("FromUserName")+" ?");
            alert.setContentText("Accept Or Refuse !");
            ((Button) alert.getDialogPane().lookupButton(ButtonType.OK)).setText("Accept");
            ((Button) alert.getDialogPane().lookupButton(ButtonType.CANCEL)).setText("Reject");
            Optional<ButtonType> result = alert.showAndWait();
            String TypeToSend = null ;
            if (result.get() == ButtonType.OK){
                // ... user chose OK
                // open the main screen
                // send the request to the server to redirect it to the opponent
                if(jsonObject.getString("YourType").equals("X")){
                    TypeToSend = "O";
                }else{
                    TypeToSend = "X";
                }
                AcceptARematchRequest(jsonObject);
                String messageToTheServer = "{\"FunctionMode\" : \"getRematchAnswerRequest\" ,\"From\" : \""
                        +myIP+"\",\"To\" : \""+jsonObject.getString("From")+"\",\"Answer\" : \"Yes\",\"YourType\" : \""
                        +TypeToSend+"\",\"FromUserName\" : \""+handler.getMyRealName()+"\"}";
                System.out.println("The sendAnswerToPlayRequest When No"+messageToTheServer);
                handler.getPs().println(messageToTheServer);
                playerTurn = TypeToSend ;
            }else if (result.get() == ButtonType.CANCEL){
                // ... user chose OK
                // open the main screen
                // send the request to the server to redirect it to the opponent
                String messageToTheServer = "{\"FunctionMode\" : \"getRematchAnswerRequest\" ,\"From\" : \""
                        +myIP+"\",\"To\" : \""+jsonObject.getString("From")+"\",\"Answer\" : \"No\",\"YourType\" : \""
                        + null +"\",\"FromUserName\" : \""+handler.getMyRealName()+"\"}";
                System.out.println("The sendAnswerToPlayRequest When No"+messageToTheServer);
                handler.getPs().println(messageToTheServer);
            }
        }else {
            String messageToTheServer = "{\"FunctionMode\" : \"getRematchAnswerRequest\" ,\"From\" : \""
                    +myIP+"\",\"To\" : \""+jsonObject.getString("From")+"\",\"Answer\" : \"No\",\"YourType\" : \""
                    +null+"\",\"FromUserName\" : \""+handler.getMyRealName()+"\"}";
            System.out.println("The sendAnswerToPlayRequest When The Player In A Game"+messageToTheServer);
            handler.getPs().println(messageToTheServer);
        }

    }

    // the function is ready for use
    public void AcceptTheRequest(JSONObject jsonObject){
        // what the sender does when his request is accepted
        opponentIP = jsonObject.getString("From");
        onlineUsersList.setDisable(true);
        recordGameButton.setDisable(false);
        surrenderButton.setDisable(false);
        rematchButton.setDisable(true);
        requestButton.setDisable(true);
        gamePanel.setDisable(false);
        runningGameFlag = true ;
        playerTwoNameLabel.setText(jsonObject.getString("FromUserName"));
        playerOneNameLabel.setText(handler.getMyRealName());
        playerAvatar = jsonObject.getString("YourType");
        playerTurn = playerAvatar ;
        playerOneScoreLabel.setText("0");
        playerTwoScoreLabel.setText("0");
        clearTheGameBoard();
        resetTheGameMatrix();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("The Game Started !");
        alert.setHeaderText("Alert , the game is running !");
        alert.setContentText("Let's Play Now .");
        alert.show();
    }

    // the function is ready for use
    public void AcceptARematchRequest(JSONObject jsonObject){
        onlineUsersList.setDisable(true);
        recordGameButton.setDisable(false);
        surrenderButton.setDisable(false);
        rematchButton.setDisable(true);
        requestButton.setDisable(true);
        gamePanel.setDisable(false);
        runningGameFlag = true ;
        playerAvatar = jsonObject.getString("YourType");
        playerTurn = playerAvatar ;
        clearTheGameBoard();
        resetTheGameMatrix();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("The Game Started !");
        alert.setHeaderText("Alert , the game is running !");
        alert.setContentText("Let's Play Now .");
        alert.show();
    }

    public void considerMeWonTheGameAndTerminateTheConnectionWithThisPlayer(){
        IWon();
        opponentIP = null ;
        rematchButton.setDisable(true);
    }

    public void showAlertAboutTheErrorAndResetTheApp(){
        // reset every thing

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information Alert !");
        alert.setHeaderText("The Session With the server have been rested.");
        alert.setContentText("Something Went Wrong , so we refreshed The Connection");

        // steps to reset the app
        opponentIP = null ;
        flagToSave = false ;
        playerTurn = null ;
        playerAvatar = null ;
        WinnerOrLoserVid = null ;
        clearTheGameBoard();
        resetTheGameMatrix();
        surrenderButton.setDisable(true);
        rematchButton.setDisable(true);
        recordGameButton.setDisable(true);
        gamePanel.setDisable(true);
        playerOneScoreLabel.setText("0");
        playerTwoScoreLabel.setText("0");
        playerTwoNameLabel.setText("Opponent");

    }

    @FXML
    void getUserInfo(MouseEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ProfileScreen.fxml"));
        try {
            Parent profileScreen = loader.load();
            Stage stage=new Stage();
            stage.setTitle("profile Screen");
            stage.setScene(new Scene(profileScreen,450,500));
            stage.show();
            ProfileController.setStage(stage);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }




}
