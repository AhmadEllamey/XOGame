package com.example.xogame;

import javafx.application.Platform;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import org.json.*;

public class ClientHandler {
    private static ClientHandler clientHandler;
    private Socket socket;
    private DataInputStream dataInputStream;
    private PrintStream printStream;
    private String str;



    private ClientHandler(){
        try {
            socket = new Socket("127.0.0.1", 5005);
            dataInputStream = new DataInputStream(socket.getInputStream());
            printStream = new PrintStream(socket.getOutputStream());
        } catch (IOException e) {
            System.out.println("Server off");
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(true){
                    try {
                        str = dataInputStream.readLine();
                        JSONObject jsonObject= new JSONObject(str);
                        String functionMode = jsonObject.getString("FunctionMode");
                        switch (functionMode){
                            //handle your case here
                            case"getUserInfoRequest","updateUserInfoRequest":
                                ProfileController.receiveDataFromServer(str);
                                break;
                            case"viewRank":
                                ViewRankController.ListenToServerData(str);
                                break;

                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                        closeConnection();
                        Platform.exit();
                        System.exit(0);

                    }
                }

            }
        }).start();
    }

    public static ClientHandler getClientHandler() {
        if (clientHandler == null) {
            clientHandler = new ClientHandler();
        }
        return clientHandler;
    }

    public void sendData(String data){
        printStream.println(data);
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
