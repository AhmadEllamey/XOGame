package com.example.xogame;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Consumer;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXToggleButton;
import javafx.fxml.FXML;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

public class GetThePlayersInfoController implements Initializable {


    @FXML
    private JFXTextField playerOneNameText;

    @FXML
    private JFXToggleButton playerOneShape;

    @FXML
    private ToggleGroup ChooseShape;

    @FXML
    private JFXTextField playerTwoNameText;

    @FXML
    private JFXToggleButton playerTwoShape;

    @FXML
    private JFXButton okButton;

    private Consumer<String> callback;


    @FXML
    void manageOkButton(ActionEvent event) {

        if((playerOneShape.isSelected() || playerTwoShape.isSelected()) &&
                !playerOneNameText.getText().trim().isEmpty() &&
                !playerTwoNameText.getText().trim().isEmpty()){

            if(playerOneShape.isSelected()){
                MainScreenController.setPlayerOneShape("X-Turn");
            }else if(playerTwoShape.isSelected()) {
                MainScreenController.setPlayerTwoShape("X-Turn");
            }

            MainScreenController.setPlayerOneName(playerOneNameText.getText().trim());
            MainScreenController.setPlayerTwoName(playerTwoNameText.getText().trim());
            MainScreenController.setRunningGameFlag(true);

            MainScreenController.setGameOrder(MainScreenController.getPlayerOneShape());
            MainScreenController.setBoardCounter(0);
            MainScreenController.resetTheGameMatrix();

            callback.accept("");

            // close the Stage
            Stage stage = (Stage) okButton.getScene().getWindow();
            stage.close();

        }

    }

    public void setup(Consumer<String> callback) {
        this.callback = callback;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        MainScreenController.setPlayerOneShape("O-Turn");
        MainScreenController.setPlayerTwoShape("O-Turn");

    }


}
