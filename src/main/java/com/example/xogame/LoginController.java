package com.example.xogame;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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
import java.util.ResourceBundle;

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

    public static Socket mySocket;
    public static DataInputStream dis ;
    public static PrintStream ps;
    public static String MyName ;
    public static String ToName ;
    public static boolean MyIP ;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        MyIP = false ;
        ToName = null ;
        try{
            // ip in iti 123.321.2.2 - 123.321.2.4 - 123.321.2.3 - 123.321.2.0
            // ip ---> 123.144.23.34
            mySocket = new Socket("127.0.0.1", 5005);
            dis = new DataInputStream(mySocket.getInputStream ());
            ps = new PrintStream(mySocket.getOutputStream());

            new Thread(new Runnable() {

                @Override
                public void run() {
                    ps.println("Please I need My Thread Name !");
                    while(true){
                        try{
                            String str = dis.readLine();
                            if(!MyIP){
                                if(str != null){
                                    MyName = str ;
                                    MyIP = true ;
                                }
                            }else{
                                System.out.println(str);
                                JSONObject result = new JSONObject(str);
                                String functionMode = result.getString("FunctionMode");
                                System.out.println(functionMode);
                                //System.out.println(message);
                                if(functionMode != null ){
                                    if(functionMode.equals("PassedTheLogIn")){
                                        System.out.println("connection done successfully");
                                    }
                                }
                            }
                        }catch(IOException e){
                            e.printStackTrace();
                            closeTheConnection();
                            Platform.exit();
                            System.exit(0);

                        }
                    }
                }

            }).start();

        }catch(IOException e){
            //e.printStackTrace();
            System.out.println("Server Is Down Or Something Went Wrong ! , try again later ...");
            System.exit(0);
        }

    }



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
        if(!UsernameText.getText().trim().isEmpty() && !PasswordText.getText().trim().isEmpty()){

            String MyString = "{\"FunctionMode\": \"loginRequest\", \"From\": \""
                    +MyName+"\", \"To\": \""+ToName+
                    "\", \"UserName\": \""+UsernameText.getText().trim()+"\" , \"Password\": \""+PasswordText.getText().trim()+"\" }";
            //JSONObject json = new JSONObject(MyString);
            ps.println(MyString);

        }





    }

    public void closeTheConnection(){

        try {
            ps.close();
            dis.close();
            mySocket.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

    @FXML
    void managePlayAsGuest(MouseEvent event) {
        System.out.println("Play as a guest");
    }

}