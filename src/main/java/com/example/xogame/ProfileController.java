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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Screen;
import javafx.stage.Stage;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.DataInputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class ProfileController implements Initializable{
    private boolean updateSaveFlag;
    private static Stage stage ;
    private JSONObject result;
    private static String userName;
    private String userData;


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

    Handler handler;


    public boolean isJSONValid(String test) {
        try {
            new JSONObject(test);
        }catch (Exception e){
            try{
                new JSONArray(test);
                return false;
            }catch (Exception ee){
                System.out.println("error but return true");
            }
        }
        return true;
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        updateSaveFlag = false;
        handler = Handler.getHandler();

        //to get data from server when profile screen open
        userData = "{\"FunctionMode\": \"getUserInfoRequest\",\"UserName\": \""+handler.getMyRealName()+"\" ,\"From\": \"" +handler.getMyName()+ "\", \"To\": \"" + null + "\"}";
        handler.getPs().println(userData);
        new Thread(() -> {
            while(true){
                try{
                    String incomingLine = handler.getDis().readLine();
                    System.out.println(incomingLine);
                    JSONArray jsonArray ;
                    JSONObject jsonObject ;
                    String functionMode ;
                    System.out.println(incomingLine);
                    String x = "{" + incomingLine ;

                    if(isJSONValid(x)){
                        jsonObject = new JSONObject(x);
                        functionMode = jsonObject.getString("FunctionMode");

                    }else {
                        jsonArray = new JSONArray(x);
                        functionMode = jsonArray.getJSONObject(0).getString("FunctionMode");
                    }
                    System.out.println(functionMode);
                    switch (functionMode) {
                        case "getUserInfoRequest" -> Platform.runLater(()->{
                            result=new JSONObject(x);
                            //set data into fields
                            userNameText.setText(result.getString("UserName"));
                            mailText.setText(result.getString("UserEmail"));
                            phoneTxt.setText(result.getString("UserPhone"));
                            passwordTxt.setText(result.getString("UserName"));
                            totalGameTxt.setText(result.getString("TotalGame"));
                            totalScoreTxt.setText(result.getString("TotalScore"));
                        });


                        case "updateUserInfoRequest" -> Platform.runLater(()->{

                        });


                    }
                }catch(IOException e){
                    e.printStackTrace();
                    try {
                        closeTheConnection();
                    }catch (Exception ee){
                        ee.printStackTrace();
                    }
                    // show an alert to make the user choose between close the app or go to the offline mode
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Warning!");
                    alert.setHeaderText("The Server Is Down Try To Log In Later");
                    alert.setContentText("Are Want to exit the game or switch to the offline mode ?");

                    Button okButton = (Button) alert.getDialogPane().lookupButton( ButtonType.OK );
                    okButton.setText("Offline");
                    Button okButton2 = (Button) alert.getDialogPane().lookupButton( ButtonType.CANCEL );
                    okButton2.setText("Exit");

                    Optional<ButtonType> result2 = alert.showAndWait();
                    if (result2.get() == ButtonType.OK){
                        // ... user chose OK
                        // switch the user to the playing options screen
                        try{
                            FXMLLoader loader = new FXMLLoader(TheMainClass.class.getResource("PlayingOption.fxml"));
                            Parent mainCallWindowFXML = loader.load();
                            TheMainClass.getMainStage().setTitle("Home !");
                            TheMainClass.getMainStage().setScene(new Scene(mainCallWindowFXML,450,500));
                            TheMainClass.getMainStage().show();
                        }catch (Exception ee){
                            ee.printStackTrace();
                        }

                    }else if(result2.get() == ButtonType.CANCEL){
                        // exit the game
                        // close the Stage
                        Stage stage = (Stage) updateBtn.getScene().getWindow();
                        stage.close();
                        closeTheConnection();
                        System.exit(0);
                        Platform.exit();
                    }

                    break;
                }

            }
        }).start();


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
                handler.getPs().println(userData);
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
            //(Stage)updateBtn.getScene().getWindow()
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

}
