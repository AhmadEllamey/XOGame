package com.example.xogame;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.scene.input.MouseEvent;

public class LoginController {

    @FXML
    private JFXTextField UsernameText;

    @FXML
    private JFXPasswordField PasswordText;

    @FXML
    private Label ForgotPasswordLabel;

    @FXML
    private JFXButton LoginButton;

    @FXML
    private Label CreateAccountLabel;

    @FXML
    private Label PlayAsAGuestLabel;



    @FXML
    void manageCreateAccount(MouseEvent event) {
        System.out.println("Create Account");
    }

    @FXML
    void manageForgotPassword(MouseEvent event) {
        System.out.println("Forgot password");
    }

    @FXML
    void manageLoginButton(ActionEvent event) {
        System.out.println("Login Button");
    }

    @FXML
    void managePlayAsGuest(MouseEvent event) {
        System.out.println("Play as a guest");
    }


}