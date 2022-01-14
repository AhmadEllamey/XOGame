package com.example.xogame;

import com.jfoenix.controls.JFXButton;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class SingleGameController implements Initializable{

    @FXML
    private JFXButton b_1;

    @FXML
    private JFXButton b_2;

    @FXML
    private JFXButton b_3;

    @FXML
    private JFXButton b_4;

    @FXML
    private JFXButton b_5;

    @FXML
    private JFXButton b_6;

    @FXML
    private JFXButton b_7;

    @FXML
    private JFXButton b_8;

    @FXML
    private JFXButton b_9;

    @FXML
    private JFXButton exitBtn;

    @FXML
    private Label player_1_score;

    @FXML
    private Label player_2_score;

    @FXML
    private JFXButton replayBtn;

    @FXML
    private JFXButton surrenderBtn;

    private boolean xTurn;
    private Alert al;
    private int player_1;
    private int player_2;


    private ArrayList<JFXButton> boardBtns;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        xTurn = true;
        boardBtns = new ArrayList<>();
        Collections.addAll(boardBtns, b_1,b_2,b_3,b_4,b_5,b_6,b_7,b_8,b_9);
        for(JFXButton btn : boardBtns){
            btn.setStyle("-fx-border-color: #89CFF0; -fx-border-width: 1px");
            btn.setFocusTraversable(false);
        }
        JFXButton[] boardbtn = {b_1,b_2,b_3,b_4,b_5,b_6,b_7,b_8,b_9};
        for(JFXButton btn: boardbtn){
            setButtonOnClick(btn, boardbtn);

        }
    }
    //---------set on click----
    public void setButtonOnClick(JFXButton btn, JFXButton[]board){
        btn.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                setTurn(btn);
                btn.setText("X");
                btn.setDisable(true);
                checkGameState();
                int i = generateRandom(board);

                board[i].setText("O");
                checkGameState();

            }
        });
    }
    //-------- set turn x or o -----------------------
    public void setTurn(JFXButton btn){
        if(xTurn){
            btn.setText("X");
            xTurn = false;
        }
        else{
            btn.setText("O");
            xTurn = true;
        }
    }
    //------------generate random-----------
    public int generateRandom(JFXButton[] buttons){
        Random random = new Random();
        int randomIndex = random.nextInt(9);
        while (!buttons[randomIndex].getText().equals("")){
            randomIndex = random.nextInt(9);
        }
        return randomIndex;
    }

    //-----------check if there is a winner----------
    public void checkGameState(){
        String case_1 = b_1.getText() + b_2.getText() + b_3.getText();
        String case_2 = b_4.getText() + b_5.getText() + b_6.getText();
        String case_3 = b_7.getText() + b_8.getText() + b_9.getText();
        String case_4 = b_1.getText() + b_4.getText() + b_7.getText();
        String case_5 = b_2.getText() + b_5.getText() + b_8.getText();
        String case_6 = b_3.getText() + b_6.getText() + b_9.getText();
        String case_7 = b_3.getText() + b_5.getText() + b_7.getText();
        String case_8 = b_1.getText() + b_5.getText() + b_9.getText();

        String []winCases = {case_1,case_2,case_3,case_4,case_5,case_6,case_7,case_8};
        for(String winCase : winCases){
            if(winCase.equals("XXX")){
                xWon();
            }
            if(winCase.equals("OOO")){
                oWon();
            }

        }
    }
    //-------------if x is winner-------
    public void xWon(){
        player_1 += 3;
        player_1_score.setText(String.valueOf(player_1));
        al = new Alert(Alert.AlertType.NONE, "Player X won", ButtonType.NEXT, ButtonType.FINISH);
        Optional<ButtonType> clickedBtn = al.showAndWait();
        ButtonType btn = clickedBtn.orElse(ButtonType.FINISH);
        if(btn == ButtonType.NEXT){
            resetGameBoard();
        }
        else{
            try {
                exitGame();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        xTurn=true;

    }
    //-------------if o is winner-------
    public void oWon(){
        player_2 += 3;
        player_2_score.setText(String.valueOf(player_2));
        al = new Alert(Alert.AlertType.NONE, "Player O won", ButtonType.NEXT, ButtonType.FINISH);
        Optional<ButtonType> clickedBtn = al.showAndWait();
        ButtonType btn = clickedBtn.orElse(ButtonType.FINISH);
        if(btn == ButtonType.NEXT){
            resetGameBoard();
        }
        else{
            try {
                exitGame();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    //---------reset the game----------
    public void resetGameBoard(){
        for(JFXButton btn : boardBtns){
            btn.setDisable(false);
            btn.setText("");
        }

    }
    public void exitGame() throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Exit Confirmation !");
        alert.setHeaderText("Warning , Are You Sure To Exit Application ?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            Stage stage = (Stage) exitBtn.getScene().getWindow();
            stage.close();
            System.exit(0);
            Platform.exit();
        }
    }

    @FXML
    void handeSurrender(ActionEvent event) {
        if(xTurn){
            oWon();
        }
        else{
            xWon();
        }
    }

    @FXML
    void handleExit(ActionEvent event) {
        try {
            exitGame();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void handleReplay(ActionEvent event) {
        resetGameBoard();
    }

}
