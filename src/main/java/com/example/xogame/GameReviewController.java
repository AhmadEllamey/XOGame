package com.example.xogame;

import com.jfoenix.controls.JFXButton;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.ResourceBundle;
import java.util.Scanner;

public class GameReviewController implements Initializable {

    @FXML
    private JFXButton b1;

    @FXML
    private JFXButton b2;

    @FXML
    private JFXButton b3;

    @FXML
    private JFXButton b4;

    @FXML
    private JFXButton b5;

    @FXML
    private JFXButton b6;

    @FXML
    private JFXButton b7;

    @FXML
    private JFXButton b8;

    @FXML
    private JFXButton b9;

    @FXML
    private JFXButton exitBtn;

    @FXML
    private JFXButton okBtn;


    @FXML
    private ListView<File> gamesList;
    File[] files;
    ArrayList<JFXButton>boardBtns;
    private Parent root;
    private Stage window;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        boardBtns = new ArrayList<>();
        Collections.addAll(boardBtns, b1,b2,b3,b4,b5,b6,b7,b8,b9);
        for (JFXButton btn : boardBtns){
            btn.setFocusTraversable(false);
            btn.setDisable(true);
        }
        File f= new File("E:\\COURSES\\JAVA\\PROJECT\\TIC-TOE\\XOGame\\src\\main\\resources\\com");
        files = f.listFiles();
        for(int i=0; i<files.length; i++){
            gamesList.getItems().add(files[i]);
        }

    }

    @FXML
    void handleOkBtn(ActionEvent event) throws FileNotFoundException {
        gamesList.setDisable(true);
        for (JFXButton btn : boardBtns){
            btn.setDisable(false);
        }
        File f = gamesList.getSelectionModel().getSelectedItem();
        view(f);

    }

    @FXML
    void handleExitBtn(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("GameEntryScreen.fxml"));
        window = (Stage) exitBtn.getScene().getWindow();
        window.setScene(new Scene(root,600,500));
    }

    public void view(File f){
        new Thread(new Runnable() {
            @Override
            public void run() {
                Scanner sc = null;
                try {
                    sc = new Scanner(f);
                } catch (FileNotFoundException e) {
                    System.out.println("can't reach");
                }
                String str = sc.nextLine();
                String line = str.substring(0,str.length()-1);
                String[] gameArr = line.split(",");
                for(int i=0; i<gameArr.length-1; i+=2){
                    for(JFXButton btn : boardBtns){
                        if (btn.getId().equals(gameArr[i])){
                            int finalI = i;
                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    btn.setText(gameArr[finalI +1]);
                                    try {
                                        Thread.sleep(1000);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }

            }
        }).start();


    }


}
