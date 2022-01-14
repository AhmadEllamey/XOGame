package com.example.xogame;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public class SaveGame {
    public static void main(String[] args) {
        ArrayList<String> sequence = new ArrayList<>();
        ArrayList<String> turns = new ArrayList<>();
        sequence.add("b2");
        sequence.add("b4");
        sequence.add("b1");
        turns.add("o");
        turns.add("x");
        turns.add("o");
        String opponent = "mazen";
        int gameNum = 15;

        boolean s = saveGame(sequence, turns,opponent);
        System.out.println(s);

    }
    public static boolean saveGame(ArrayList<String> sequence, ArrayList<String> turns, String opponent){

        boolean saved = false;
        String path = "E:\\COURSES\\JAVA\\PROJECT\\TIC-TOE\\XOGame\\src\\main\\resources\\com\\" + opponent;
        File file = new File(path);
        ArrayList<String> lines = new ArrayList<>();
        FileWriter fileWriter = null;

        //------------------------FILE CREATING-------------------
        try{
            file.createNewFile();
        }
        catch (IOException e) {
            saved = false;
        }

        //---------MOVE LINE GENERATING----------------------------
        Iterator<String> it1 = sequence.iterator();
        Iterator<String> it2 = turns.iterator();
        while(it1.hasNext() && it2.hasNext()){
            String b = it1.next();
            String symbol = it2.next();
            String move = b + "," + symbol;
            lines.add(move);
        }

        //---------- STORING GAME MOVES INTO ARRAY---------------
        String[]linesArray = new String[lines.size()];
        for(int i=0; i<lines.size(); i++){
            linesArray[i] = lines.get(i);
        }

        //--------------- WRITE GAME MOVES INTO THE FILE SEPARATED BY (,)-------------
        try {
            fileWriter = new FileWriter(file);
            for(int i=0; i<linesArray.length;i++){
                fileWriter.write(linesArray[i] + ",");
            }
            saved = true;

        } catch (IOException ex) {
            saved = false;
        }finally{
            try {
                fileWriter.close();
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        }


        return saved;
    }

}
