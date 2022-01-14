package com.example.xogame;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Optional;

import static com.example.xogame.OnlineGameScreenController.classFlag;
import static com.example.xogame.ProfileController.classFlagPC;
import static com.example.xogame.RegistrationController.classFlagRC;
import static com.example.xogame.ViewRankController.classFlagVRC;
import static java.lang.Thread.sleep;

public class Handler {


    private static Handler handler ;

    private Socket mySocket;
    private DataInputStream dis;
    private PrintStream ps;
    private String MyName ;
    private String ToName ;
    private boolean MyIP ;
    private String MyRealName;
    private int flag ;
    Thread thread ;
    Thread MainThread ;



    public static Handler getHandler() {
        return handler;
    }

    public static void setHandler() {
        Handler.handler = null;
    }

    public Socket getMySocket() {
        return mySocket;
    }

    public DataInputStream getDis() {
        return dis;
    }

    public PrintStream getPs() {
        return ps;
    }

    public String getMyName() {
        return MyName;
    }

    public String getToName() {
        return ToName;
    }

    public boolean isMyIP() {
        return MyIP;
    }

    public String getMyRealName() {
        return MyRealName;
    }

    public int getFlag() {
        return flag;
    }



    public static Handler getTheObject(){
        if(handler==null){
            handler = new Handler();
        }
        return handler ;
    }

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

    OnlineGameScreenController onlineGameScreenController;
    RegistrationController registrationController ;
    ProfileController profileController ;
    ViewRankController viewRankController ;
    private Handler(){


        flag = 1 ;
        try{
            // ip in iti 123.321.2.2 - 123.321.2.4 - 123.321.2.3 - 123.321.2.0
            // ip ---> 123.144.23.34
            mySocket = new Socket("127.0.0.1", 5005);
            dis = new DataInputStream(mySocket.getInputStream ());
            ps = new PrintStream(mySocket.getOutputStream());
            try{
                new Thread(() -> {
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
                                String functionModeResult = result.getString("FunctionMode");
                                System.out.println(functionModeResult);
                                //System.out.println(message);
                                if(functionModeResult != null ){
                                    if(functionModeResult.equals("RegistrationAccepted")) {

                                        Platform.runLater(()->{
                                            while (true){
                                                if(classFlagRC == 1){
                                                    registrationController = RegistrationController.getTheOnlineObject();
                                                    System.out.println("Done Correctly");
                                                    break;
                                                }
                                            }
                                            registrationController.requestAccepted();
                                        });

                                    }else if(functionModeResult.equals("RegistrationFailed")){
                                        Platform.runLater(()->{
                                            while (true){
                                                if(classFlagRC == 1){
                                                    registrationController = RegistrationController.getTheOnlineObject();
                                                    System.out.println("Done Correctly");
                                                    break;
                                                }
                                            }
                                            registrationController.requestRefused();
                                        });
                                    }
                                    else if(functionModeResult.equals("PassedTheLogIn")){
                                        // switch the screen to the online playing game .
                                        MyRealName = result.getString("UserName") ;
                                        Platform.runLater(() -> new LoginController().SwitchOnTheScreens());

                                        Platform.runLater(()->{
                                            while (true){
                                                if(classFlag == 1){
                                                    onlineGameScreenController = OnlineGameScreenController.getTheOnlineObject();
                                                    System.out.println("Done Correctly");
                                                    break;
                                                }
                                            }
                                        });

                                        MainThread = new Thread(() -> {
                                            while(true){
                                                System.out.println("again");
                                                try{
                                                    String incomingLine = handler.getDis().readLine();
                                                    System.out.println(incomingLine);
                                                    JSONArray jsonArray ;
                                                    JSONObject jsonObject ;
                                                    String functionMode ;
                                                    System.out.println(incomingLine);
                                                    if(isJSONValid(incomingLine)){
                                                        jsonObject = new JSONObject(incomingLine);
                                                        functionMode = jsonObject.getString("FunctionMode");

                                                    }else {
                                                        jsonArray = new JSONArray(incomingLine);
                                                        functionMode = jsonArray.getJSONObject(0).getString("FunctionMode");
                                                    }
                                                    System.out.println(functionMode);
                                                    switch (functionMode) {
                                                        case "TheOnlinePlayers" -> Platform.runLater(()->onlineGameScreenController.loadPlayerInfo(incomingLine));

                                                        case "sendAnswerToPlayRequest" ->
                                                                Platform.runLater(()->{
                                                                    System.out.println(incomingLine);
                                                                    JSONObject jsonObjectx = new JSONObject(incomingLine);
                                                                    System.out.println(jsonObjectx.getString("Answer"));
                                                                    if (jsonObjectx.getString("Answer").equals("Yes")) {
                                                                        onlineGameScreenController.AcceptTheRequest(jsonObjectx);
                                                                    }else {
                                                                        onlineGameScreenController.setOpponentIP(null);
                                                                        //opponentIP = null;
                                                                    }
                                                                });

                                                        case "sendPlayRequest" ->  Platform.runLater(()->onlineGameScreenController.showAPlayingRequest(incomingLine));

                                                        case "sendUpdateTheGameBoardRequest" -> Platform.runLater(()->{
                                                            onlineGameScreenController.UpdateTheGameBoard(incomingLine);
                                                        });

                                                        case "endGameRequestWithSurrenderRequest" -> Platform.runLater(()->onlineGameScreenController.IWon());


                                                        case "sendIWonRequest" -> Platform.runLater(()->onlineGameScreenController.ILost());

                                                        case "sendRematchRequest" -> Platform.runLater(()->{
                                                            onlineGameScreenController.showARematchRequest(incomingLine);
                                                        });

                                                        case "getRematchAnswerRequest" -> Platform.runLater(()->{
                                                            System.out.println(incomingLine);
                                                            JSONObject jsonObjectx = new JSONObject(incomingLine);
                                                            System.out.println(jsonObjectx.getString("Answer"));
                                                            if (jsonObjectx.getString("Answer").equals("Yes")) {
                                                                onlineGameScreenController.AcceptARematchRequest(jsonObjectx);
                                                            }
                                                        });

                                                        case "ErrorCantRedirectTheMessage" -> Platform.runLater(()->onlineGameScreenController.showAlertAboutTheErrorAndResetTheApp());

                                                        case "ErrorCantSendUpdateGameBoard" -> Platform.runLater(()->onlineGameScreenController.considerMeWonTheGameAndTerminateTheConnectionWithThisPlayer());

                                                        case "getUserInfoRequest" -> Platform.runLater(()->{
                                                            while (true){
                                                                if(classFlagPC == 1){
                                                                    profileController = ProfileController.getTheOnlineObject();
                                                                    System.out.println("Done Correctly");
                                                                    break;
                                                                }
                                                            }

                                                            profileController.showTheData(incomingLine);
                                                        });

                                                        case "answerToTheRankRequest" -> Platform.runLater(()->{
                                                            while (true){
                                                                if(classFlagVRC == 1){
                                                                    viewRankController = ViewRankController.getTheOnlineObject();
                                                                    System.out.println("Done Correctly");
                                                                    break;
                                                                }
                                                            }

                                                            viewRankController.whenTheServerAnswer(incomingLine);
                                                        });
                                                    }
                                                }catch(IOException e){
                                                    e.printStackTrace();
                                                    try {
                                                        onlineGameScreenController.closeTheConnection();
                                                    }catch (Exception ee){
                                                        ee.printStackTrace();
                                                    }
                                                    // show an alert to make the user choose between close the app or go to the offline mode
                                                    Platform.runLater(()->{
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
                                                            Stage stage = (Stage) onlineGameScreenController.getExitButton().getScene().getWindow();
                                                            stage.close();
                                                            thread.stop();
                                                            MainThread.stop();
                                                            System.exit(0);
                                                            Platform.exit();
                                                        }
                                                    });

                                                    break;
                                                }
                                            }
                                        });
                                        MainThread.start();
                                        thread = new Thread(() -> {
                                            String messageToTheServer = "{\"FunctionMode\" : \"getTheOnlinePlayersOnTheServerRequest\"" +
                                                    " ,\"From\" : \"myIP\",\"To\" : \"opponentIP\" }";
                                            while (true){
                                                handler.getPs().println(messageToTheServer);
                                                try {
                                                    sleep(60000);
                                                } catch (InterruptedException e) {
                                                    e.printStackTrace();
                                                }

                                            }
                                        });
                                        thread.start();

                                        break;
                                    }else {
                                        Platform.runLater(() -> {
                                            System.out.println("Forgot password");
                                            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                                            alert.setTitle("Alert !");
                                            alert.setHeaderText("Try To Contact The Support !");
                                            alert.setContentText("Wrong Username Or Password");
                                            alert.show();
                                        });

                                    }
                                }
                            }
                        }catch(IOException e){
                            e.printStackTrace();
                        }
                    }
                }).start();
            }catch (Exception e){
                e.printStackTrace();

            }


        }catch(IOException e){
            e.printStackTrace();
            // show an alert to make the user choose between close the app or go to the offline mode
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Warning!");
            alert.setHeaderText("The Server Is Down Try To Log In Later");
            alert.setContentText("Are Want to exit the game or switch to the offline mode ?");

            Button okButton = (Button) alert.getDialogPane().lookupButton( ButtonType.OK );
            okButton.setText("Offline");
            Button okButton2 = (Button) alert.getDialogPane().lookupButton( ButtonType.CANCEL );
            okButton2.setText("Exit");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK){
                flag = 0 ;
            }else if(result.get() == ButtonType.CANCEL){
                // exit the game
                // close the Stage
                System.exit(0);
                Platform.exit();
            }
            System.out.println("Server Is Down Or Something Went Wrong ! , try again later ...");

        }

    }




}
