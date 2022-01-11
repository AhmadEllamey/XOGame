package com.example.xogame;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
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
    private Socket socket;
    private DataInputStream dataInputStream;
    private PrintStream printStream;
    private JSONObject result;

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

    private void init() {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //init();
        updateSaveFlag = false;
        userNameText.setText("Mariam Mostafa");
        mailText.setText("MariamMostafa@gmail.com");
        phoneTxt.setText("01020273870");
        passwordTxt.setText("1234567");
        try{
            socket = new Socket("127.0.0.1", 5005);
            dataInputStream = new DataInputStream(socket.getInputStream ());
            printStream = new PrintStream(socket.getOutputStream());
            new Thread(new Runnable() {

                @Override
                public void run() {
                    while(true){
                        try{
                            String str = dataInputStream.readLine();
                            result = new JSONObject(str);

                        }catch(IOException e){
                            e.printStackTrace();
                            closeConnection();
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
                String userData = "{\"FunctionMode\": \"updateUserInfoRequest\",\"UserName\": \"" + userNameText.getText().trim() + "\", \"UserEmail\": \"" + mailText.getText().trim() + "\", \"UserPhone\": \"" + phoneTxt.getText().trim() + "\", \"Password\": \"" + passwordTxt.getText().trim() + "\"}";
                printStream.println(userData);
            }
        } else {
            setTextFieldsEnable();
            updateBtn.setText("Save");
            updateSaveFlag = true;
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

    public void closeConnection(){

        try {
            printStream.close();
            dataInputStream.close();
            socket.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

}
