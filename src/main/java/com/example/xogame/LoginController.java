package com.example.xogame;


import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.scene.input.MouseEvent;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.json.*;

public class LoginController implements Initializable {

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


    Handler handler ;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        // ToDO Set On Close Action For The App .

        handler = Handler.getTheObject();

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
                try{
                    closeTheConnection();
                }catch (Exception e){
                    e.printStackTrace();
                }
                System.exit(0);
                Platform.exit();
            }else{
                windowEvent.consume();
            }

        });


    }


    public void SwitchOnTheScreens(){

        System.out.println("connection done successfully");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("OnlineGameScreen.fxml"));
        try {
            loader.load();
            Parent popup = loader.getRoot();
            TheMainClass.getMainStage().setTitle("XO Online Game !");
            TheMainClass.getMainStage().setScene(new Scene(popup,800 ,600));
            TheMainClass.getMainStage().show();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    // This Method Is Done And Ready For Real Testing
    @FXML
    void manageCreateAccount(MouseEvent event) {
        System.out.println("Create Account");
        // switch to the SignUpScreen
        // go to playing options
        FXMLLoader loader = new FXMLLoader(getClass().getResource("RegistrationScreen.fxml"));
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Parent popup = loader.getRoot();
        TheMainClass.getMainStage().setTitle("Create New Account !");
        TheMainClass.getMainStage().setScene(new Scene(popup,600 ,600));
        TheMainClass.getMainStage().show();
    }

    // This Method Is Done And Ready For Real Testing
    @FXML
    void manageForgotPassword(MouseEvent event) {
        System.out.println("Forgot password");
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Password Recovery !");
        alert.setHeaderText("What Should I Do If I forgot The Password ?");
        alert.setContentText("Send To The Customer Support And They Will Help You.");
        alert.show();
    }

    // This Method Is Done And Ready For Real Testing
    @FXML
    void manageLoginButton(ActionEvent event) {
        System.out.println("Login Button");
        if(!UsernameText.getText().trim().isEmpty() && !PasswordText.getText().trim().isEmpty()){
            String MyString = "{\"FunctionMode\": \"loginRequest\", \"From\": \""
                    +handler.getMyName()+"\", \"To\": \""+handler.getToName()+
                    "\", \"UserName\": \""+UsernameText.getText().trim()+"\" , \"Password\": \""+PasswordText.getText().trim()+"\" }";
            handler.getPs().println(MyString);
        }
    }

    // This Method Is Done And Ready For Real Testing
    @FXML
    void managePlayAsGuest(MouseEvent event) {
        System.out.println("Play as a guest");
        // switch to the offlineScreen
        // go to playing options
        FXMLLoader loader = new FXMLLoader(getClass().getResource("PlayingOption.fxml"));
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Parent popup = loader.getRoot();
        TheMainClass.getMainStage().setTitle("XO Offline Game !");
        TheMainClass.getMainStage().setScene(new Scene(popup,600 ,400));
        TheMainClass.getMainStage().show();

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
}