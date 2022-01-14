package com.example.xogame;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Screen;
import javafx.stage.Stage;
import org.json.JSONObject;

import java.io.DataInputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

public class ProfileController implements Initializable{
    private boolean updateSaveFlag;
    private static Stage stage ;
    private JSONObject result;
    private ClientHandler clientHandler;
    private static String userName;
    private String userData;
    private static String userDataFromServer;

    @FXML
    private ImageView imageView;

    @FXML
    private JFXTextField mailText;

    @FXML
    private JFXPasswordField passwordTxt;

    @FXML
    private JFXTextField phoneTxt;

    @FXML
    private Label totalGameTxt;

    @FXML
    private Label totalScoreTxt;

    @FXML
    private JFXButton updateBtn;

    @FXML
    private JFXTextField userNameText;

    @FXML
    private Label viewRank;

    public ProfileController() {
        clientHandler=ClientHandler.getClientHandler();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        updateSaveFlag = false;

        //to get data from server when profile screen open
        userData = "{\"FunctionMode\": \"getUserInfoRequest\",\"UserName\": \"" + "Mariam" + "\" ,\"From\": \"" + "M" + "\", \"To\": \"" + null + "\"}";
        clientHandler.sendData(userData);

        //result=new JSONObject(userDataFromServer);

        //set data into fields
        /*userNameText.setText(result.getString("UserName"));
        mailText.setText(result.getString("UserEmail"));
        phoneTxt.setText(result.getString("UserPhone"));
        passwordTxt.setText(result.getString("UserName"));
        totalGameTxt.setText(result.getString("TotalGame"));
        totalScoreTxt.setText(result.getString("TotalScore"));*/

        userNameText.setText(userName);
    }

    @FXML
    void manageImageView(MouseEvent event) {
        FileChooser fileChooser = new FileChooser();
        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            Image image1 = new Image(selectedFile.toURI().toString());
            imageView.setImage(image1);
        }
    }

    @FXML
    void manageUpdateButton(ActionEvent event) {
         if (updateSaveFlag == true) {
            if(validateFields()==true) {
                setTextFieldsDisable();
                updateSaveFlag = false;
                updateBtn.setText("Update");
                //String userData = "{\"FunctionMode\": \"updateUserInfoRequest\",\"UserName\": \"" + userNameText.getText().trim() + "\", \"UserEmail\": \"" + mailText.getText().trim() + "\", \"UserPhone\": \"" + phoneTxt.getText().trim() + "\", \"Password\": \"" + passwordTxt.getText().trim() + "\"}";
                //clientHandler.sendData(userData);
            }
        } else {
            setTextFieldsEnable();
            updateBtn.setText("Save");
            updateSaveFlag = true;
        }
    }
    @FXML
    void manageViewRank(MouseEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ViewRankScreen.fxml"));
        try {
            Parent root = loader.load();
            stage.setTitle("ViewRankScreen");
            stage.setScene(new Scene(root,450,470));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void setTextFieldsEnable(){
        userNameText.setDisable(false);
        imageView.setDisable(false);
        mailText.setDisable(false);
        phoneTxt.setDisable(false);
        passwordTxt.setDisable(false);
        userNameText.setStyle("-fx-prompt-text-fill: #d00e0e");
        mailText.setStyle("-fx-prompt-text-fill: #d00e0e");
        phoneTxt.setStyle("-fx-prompt-text-fill: #d00e0e");
        passwordTxt.setStyle("-fx-prompt-text-fill: #d00e0e");
    }
    public void setTextFieldsDisable(){
        userNameText.setDisable(true);
        imageView.setDisable(true);
        mailText.setDisable(true);
        phoneTxt.setDisable(true);
        passwordTxt.setDisable(true);
    }

    public boolean validateFields(){
        boolean checkValidity=false;
        if(userNameText.getText().trim().isEmpty() || mailText.getText().trim().isEmpty() ||
                phoneTxt.getText().trim().isEmpty() || passwordTxt.getText().trim().isEmpty()){
            checkValidity=false;
        }
        else{
            checkValidity=true;
        }

        return checkValidity;
    }
    public void getText(){

    }
    public static Stage getStage() {
        return stage;
    }
    public static void setStage(Stage s) {
        stage=s;
    }

    public void setUserName(String userName){
        this.userName=userName;
    }

    public static void receiveDataFromServer(String str){
        userDataFromServer=str;
    }
}
