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
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Optional;
import java.util.ResourceBundle;

public class PlayWithFriendController implements Initializable {
    private static String NameOne;
    private static String NameTwo;
    private String imgName="XImage.png";
    private String character;
    private Pane pane;
    private static int xCount=0;
    private static int oCount=0;
    private int nullCounter;
    private int iterator;
    private HashMap<String, String> mapOfPane;
    public Pane[] arrOfClickedPane;
    public Pane[] arrOfPane;
    private Alert alert;
    private JFXButton clickedButton;
    @FXML
    private JFXButton ExitBtn;
    @FXML
    private Label playerOneNameTxt;

    @FXML
    private Label playerOneScoreLabel;

    @FXML
    private Label playerTowNameTxt;

    @FXML
    private Label playerTwoScoreLabel;

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

    public PlayWithFriendController() {
        nullCounter=0;
        iterator=0;
        mapOfPane = new HashMap<String, String>();
        arrOfClickedPane=new Pane[9];
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        playerOneNameTxt.setText(NameOne);
        playerTowNameTxt.setText(NameTwo);
        ReplayBtn.setDisable(true);
        arrOfPane= new Pane[]{P_0_0, P_0_1, P_0_2, P_1_0, P_1_1, P_1_2, P_2_0, P_2_1, P_2_2};
    }
    private void fillHashMap(){
        if(pane==P_0_0){
            mapOfPane.put("1", character);
        }
        if(pane==P_0_1){
            mapOfPane.put("2", character);
        }
        if(pane==P_0_2){
            mapOfPane.put("3", character);
        }
        if(pane==P_1_0){
            mapOfPane.put("4", character);
        }
        if(pane==P_1_1){
            mapOfPane.put("5", character);
        }
        if(pane==P_1_2){
            mapOfPane.put("6", character);
        }
        if(pane==P_2_0){
            mapOfPane.put("7", character);
        }
        if(pane==P_2_1){
            mapOfPane.put("8", character);
        }
        if(pane==P_2_2){
            mapOfPane.put("9", character);
        }
    }
    private void setImage(MouseEvent event){

        // get the id of the clicked button
        pane = (Pane) event.getSource();
        //String paneIdName = pane.getId();

        arrOfClickedPane[iterator] = pane;
        iterator++;
        // new Image(url)
        Image image = new Image(imgName);
        if(imgName=="XImage.png"){
            character="x";
        }
        else{
            character="o";
        }
        fillHashMap();
        // new BackgroundSize(width, height, widthAsPercentage, heightAsPercentage, contain, cover)
        BackgroundSize backgroundSize = new BackgroundSize(100, 100, true, true, true, false);
        // new BackgroundImage(image, repeatX, repeatY, position, size)
        BackgroundImage backgroundImage = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);
        // new Background(images...)
        Background background = new Background(backgroundImage);

        pane.setBackground(background);
        pane.setOnMouseClicked(null);
    }
    private void switchPlayers(){
        if(imgName=="XImage.png"){
            imgName="OImage.png";
        }
        else {
            imgName="XImage.png";
        }

    }
    private void checkWinnerXorO(){

        alert = new Alert(Alert.AlertType.INFORMATION);
        if(character=="x"){
            alert.setHeaderText(playerOneNameTxt.getText().trim()+" win");
            xCount+=3;
            alert.show();
            setScore();
            mapOfPane.clear();
        }
        else if(character=="o"){
            alert.setHeaderText(playerTowNameTxt.getText().trim()+" win");
            oCount+=3;
            alert.show();
            setScore();
            mapOfPane.clear();
        }
    }

    private void checkWinner(){
        if( (mapOfPane.get("1")==mapOfPane.get("2") && mapOfPane.get("1")==mapOfPane.get("3")&&mapOfPane.get("1")!=null) ||
                (mapOfPane.get("1")==mapOfPane.get("4") && mapOfPane.get("1")==mapOfPane.get("7")&&mapOfPane.get("1")!=null) ||
                (mapOfPane.get("1")==mapOfPane.get("5") && mapOfPane.get("1")==mapOfPane.get("9")&&mapOfPane.get("1")!=null) ||
                (mapOfPane.get("3")==mapOfPane.get("5") && mapOfPane.get("3")==mapOfPane.get("7")&&mapOfPane.get("3")!=null) ||
                (mapOfPane.get("4")==mapOfPane.get("5") && mapOfPane.get("4")==mapOfPane.get("6")&&mapOfPane.get("4")!=null) ||
                (mapOfPane.get("7")==mapOfPane.get("8") && mapOfPane.get("7")==mapOfPane.get("9")&&mapOfPane.get("7")!=null) ||
                (mapOfPane.get("2")==mapOfPane.get("5") && mapOfPane.get("2")==mapOfPane.get("8")&&mapOfPane.get("5")!=null) ||
                (mapOfPane.get("3")==mapOfPane.get("6") && mapOfPane.get("3")==mapOfPane.get("9")&&mapOfPane.get("3")!=null)) {
            checkWinnerXorO();
            alert.show();
            setAllPaneDisable();
            SurrenderBtn.setDisable(true);
            ReplayBtn.setDisable(false);
        }
        else{
            if(!mapOfPane.isEmpty()){
                nullCounter++;
            }
            if(nullCounter==9){
                alert.setContentText("no one win ");
                alert.show();
                mapOfPane.clear();
            }

        }

    }
    private void setScore(){
        playerOneScoreLabel.setText(String.valueOf(xCount));
        playerTwoScoreLabel.setText(String.valueOf(oCount));
    }
    private void playAgain(){
        for (int i=0;i<arrOfClickedPane.length;i++){
            if(arrOfClickedPane[i]!=null){
                arrOfClickedPane[i].setBackground(null);
                arrOfClickedPane[i].setOnMouseClicked(this::manageTheGameBoard);
            }

        }
        for (int i = 0; i < arrOfPane.length; i++) {
            if (arrOfPane[i].getOnMouseClicked() == null) {
                arrOfPane[i].setOnMouseClicked(this::manageTheGameBoard);
            }
        }
        imgName="XImage.png";
        nullCounter=0;
        Arrays.fill(arrOfClickedPane,null);
        iterator=0;

    }
    private void setAllPaneDisable() {
        for (int i = 0; i < arrOfPane.length; i++) {
            if (arrOfPane[i].getOnMouseClicked() != null) {
                arrOfPane[i].setOnMouseClicked(null);
            }
        }
    }
    @FXML
    void manageBackBtn(MouseEvent event) {
        alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Back Confirmation !");
        alert.setHeaderText("Are You Sure to go back and end game!");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            FXMLLoader loader = new FXMLLoader(getClass().getResource("PlayingOption.fxml"));
            try {
                Parent root = loader.load();
                TheMainClass.getMainStage().setScene(new Scene(root,450,470));
                TheMainClass.getMainStage().show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }

    @FXML
    void manageExitBtn(MouseEvent event) {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Exit Confirmation !");
        alert.setHeaderText("Warning , Are You Sure To Exit Application ?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            Stage stage = (Stage) ExitBtn.getScene().getWindow();
            stage.close();
            System.exit(0);
            Platform.exit();
        }
    }

    @FXML
    void manageReplayBtn(MouseEvent event) {
        playAgain();
        SurrenderBtn.setDisable(false);
        ReplayBtn.setDisable(true);
    }

    @FXML
    void manageSurrenderBtn(MouseEvent event) {
        alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Surrender Confirmation !");
        String playerName=null;
        boolean flag=false;
        if(imgName=="XImage.png"){
            playerName=playerOneNameTxt.getText().trim();
            flag=true;
        }
        else {
            playerName=playerTowNameTxt.getText().trim();
            flag=false;
        }
        alert.setHeaderText("Player : "+playerName+" !");
        alert.setContentText("Are You Sure About Surrender The Game ?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            if(flag==true){
                oCount+=3;
            }
            else {
                xCount+=3;
            }
            mapOfPane.clear();
            playAgain();
            setScore();
        }
    }

    @FXML
    void manageTheGameBoard(MouseEvent event) {
        setImage(event);
        switchPlayers();
        checkWinner();
        playerOneScoreLabel.setText(String.valueOf(xCount));
        playerTwoScoreLabel.setText(String.valueOf(oCount));

    }

    public static void getNames(String player1,String player2){
        NameOne=player1;
        NameTwo=player2;
    }


}
