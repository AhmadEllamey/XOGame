package com.example.xogame;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

public class GameRecord {
    CommunicationDAO recordDao;
   /* String s="{\"b1\":\"x\",\"b3\":0,\"b4\":x}";
    Object obj=JSONValue.parse(s);
    JSONObject jsonObject = (JSONObject) obj;
    static Set<String> sequence;
    static Collection turns;
    JSONObject myjObj=new JSONObject();

    Socket clientSocket;
    PrintStream ps;
    DataInputStream dis;
    public GameRecord(){

        Thread th = new Thread(){
            @Override
            public void run(){
                try {
                    String serverReply = dis.readLine();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };

        try {
            clientSocket = new Socket("127.0.0.1",5005);
            dis = new DataInputStream(clientSocket.getInputStream());
            ps = new PrintStream(clientSocket.getOutputStream());
            th.start();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void storeGame(JSONObject jsonObject){
        sequence = jsonObject.keySet();
        turns = jsonObject.values();
        Iterator<String> it1 = sequence.iterator();
        Iterator<String> it2 = turns.iterator();

        myjObj.put("function mode","save game");
        while(it1.hasNext() && it2.hasNext()){
            String cell = it1.next();
            String symbol = it2.next();
            myjObj.put(cell,symbol);
            storeLine(myjObj);
        }
    }
    public void storeLine(JSONObject obj){
        ps.println(obj.toJSONString());
    }*/
    public static void serverReply(){

    }
}
