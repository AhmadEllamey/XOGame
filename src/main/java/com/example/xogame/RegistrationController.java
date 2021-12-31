package com.example.xogame;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

public class RegistrationController {


    @FXML
    private CheckBox acceptanceCheckBox;

    @FXML
    private JFXTextField confirmPasswordText;

    @FXML
    private JFXTextField emailText;

    @FXML
    private Label loginLabel;

    @FXML
    private JFXTextField passwordText;

    @FXML
    private JFXTextField phoneText;

    @FXML
    private JFXButton signUpButton;

    @FXML
    private JFXTextField userNameText;




    @FXML
    void manageLoginLabel(MouseEvent event) {
        System.out.println("Login Label");

    }

    @FXML
    void manageSignUpButton(ActionEvent event) {
        System.out.println("Sign Up Button");
    }





}
