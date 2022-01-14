package com.example.xogame;


import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import static com.example.xogame.OnlineGameScreenController.WinnerOrLoserVid;

public class GameOverController  {

    /*
    @FXML
    private MediaView viewMediaHolder;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {
            // TODO
            //goes to user Directory
            MediaPlayer mediaplayer ;
            String Dir = "";
            File f = new File(Dir, WinnerOrLoserVid);
            Media media = new Media(f.toURI().toString());
            mediaplayer = new MediaPlayer(media);
            viewMediaHolder.setMediaPlayer(mediaplayer);
            mediaplayer.setAutoPlay(false);
            viewMediaHolder.setVisible(true);
            mediaplayer.play();
            mediaplayer.setOnEndOfMedia(() -> {
                WinnerOrLoserVid = null ;
                Stage stag = (Stage)viewMediaHolder.getScene().getWindow();
                stag.close();
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

     */
}
