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

    private Handler(){


        OnlineGameScreenController onlineGameScreenController = new OnlineGameScreenController();
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
                                    if(functionModeResult.equals("PassedTheLogIn")){
                                        // switch the screen to the online playing game .
                                        MyRealName = result.getString("UserName") ;
                                        Platform.runLater(() -> new LoginController().SwitchOnTheScreens());
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


        System.out.println("new object");
    }




}
