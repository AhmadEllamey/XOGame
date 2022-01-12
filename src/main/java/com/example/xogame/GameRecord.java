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

    public GameRecord(){

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
