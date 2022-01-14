package com.example.xogame;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.fxml.Initializable;
import javafx.stage.Stage;
import org.json.JSONObject;

import java.io.*;
import java.net.Socket;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class RegistrationController implements Initializable {

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

    public static Handler handlerForRC ;

    public static int classFlagRC = 0 ;

    private static RegistrationController registrationController ;

    public static RegistrationController getTheOnlineObject(){
        return registrationController;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) { //awl md5ol sf7t l registration.
        handlerForRC = Handler.getTheObject();
        registrationController = this ;
        classFlagRC = 1 ;
    }

    @FXML
    void manageLoginLabel(MouseEvent event) {
        goToLogin(); // in the same class
    }

    public void requestAccepted(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Confirmation");
        alert.setContentText("your registration done successfully,Try to login now");//hwdeh l screen login
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            TheMainClass.getMainStage().close();
            goToLogin();
        }

    }

    public void requestRefused(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Alert");
        alert.setContentText("your registration Failed try another username !");//hwdeh l screen login
        alert.show();
    }

    @FXML
    void manageSignUpButton(ActionEvent event) {

            String username = userNameText.getText().trim();
            String email = emailText.getText().trim();
            String password = passwordText.getText().trim();
            String confirmPassword = confirmPasswordText.getText().trim();
            String phone = phoneText.getText().trim();
            String registrationData = "{\"To\": \"null\",\"From\": \"null\",\"FunctionMode\": \"registerRequest\",\"UserName\": \""+username+"\", \"Email\": \""+email+"\", \"Password\": \""+password+"\", \"UserConfirmPassword\": \""+confirmPassword+"\", \"Phone\": \""+phone+"\"}";
            if (!username.isEmpty() && !email.isEmpty() && !password.isEmpty() && !confirmPassword.isEmpty() && !phone.isEmpty()&&acceptanceCheckBox.isSelected()&&password.equals(confirmPassword)) {
                handlerForRC.getPs().println(registrationData);

            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Registration request");
                alert.setContentText("all the required fields should be filled");
                alert.show();
            }


    }

    public void goToLogin(){ // 3shan ann2l mn l registrition screen to login screen after reg is done successful

        FXMLLoader loader = new FXMLLoader(getClass().getResource("LoginScreen.fxml"));//getclass bygeb l controller eli l mlf bt3o dh login...fxml
        try {
            Parent root = loader.load();// ref ay asm msln root mn class parent by3ml load l fxml file 3shan yt3rd 3la screen
            TheMainClass.getMainStage().setTitle("login screen");// l stage l mogoda f main class hstd3eha w a7ot 3leha l new scene
            TheMainClass.getMainStage().setScene(new Scene(root,450,500));
            TheMainClass.getMainStage().show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}

