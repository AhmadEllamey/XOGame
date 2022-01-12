package com.example.xogame;

import javafx.application.Platform;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;

public class CommunicationDAO {
    private static CommunicationDAO daoInstance;
    private Socket socket;
    private DataInputStream dataInputStream;
    private PrintStream printStream;
    private String str;


    private CommunicationDAO() {
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
                        switch (str){
                            case "Saved Successfully":
                                Platform.runLater(() -> GameRecord.serverReply());

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

    public static CommunicationDAO getDaoInstance() {
        if (daoInstance == null) {
            daoInstance = new CommunicationDAO();
        }
        return daoInstance;
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
