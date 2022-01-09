package com.example.xogame;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class ProfileController {


    private boolean txtFieldFlag=false;

    @FXML
    private ImageView imageView;

    @FXML
    private JFXTextField userNameText;

    @FXML
    private JFXTextField mailText;

    @FXML
    private JFXTextField phoneTxt;

    @FXML
    private Label totalGameTxt;

    @FXML
    private Label totalScoreTxt;

    @FXML
    private JFXPasswordField passwordTxt;

    @FXML
    private JFXButton updateBtn;




    @FXML
    void manageImageView(MouseEvent event) {
    }

    @FXML
    void manageUpdateButton(ActionEvent event) {
    }

}
