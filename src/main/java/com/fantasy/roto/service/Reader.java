package com.fantasy.roto.service;

import com.ebay.xcelite.Xcelite;
import com.ebay.xcelite.reader.SheetReader;
import com.ebay.xcelite.sheet.XceliteSheet;
import com.fantasy.roto.model.Hitting;

import java.io.File;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class Reader {
    private String filename;
    private DataManipulator dataManipulator;

    public Reader(){
        dataManipulator = new DataManipulator();
    }

    public Map<String, Double> readAndRank(String filename){
        Xcelite xcelite = new Xcelite(new File(filename));

        XceliteSheet sheet = xcelite.getSheet("Sheet1");

        SheetReader<Collection<Hitting>> simpleReader = sheet.getSimpleReader();
//        if (simpleReader.hasHeaderRow()){
//            System.out.println("skipping");
//            simpleReader.skipHeaderRow(true);
//        }
        Collection<Collection<Hitting>> playerCollections = simpleReader.read();

        Map<String, List<Double>> playerStats = dataManipulator.convertToMap(playerCollections);

//        System.out.println(hitters.get("rainmaker"));
//
//        for (String name : hitters.keySet()){
//            System.out.println(name);
//        }
        Map<String, List<Double>> playerRanks = dataManipulator.rankAllColumns();
        for (Map.Entry<String, List<Double>> entry : playerRanks.entrySet()){
            System.out.println(entry.getKey() + " " + entry.getValue().toString());
        }
        Map<String, Double> playerFinalRank = dataManipulator.calculateScore(playerRanks);
        for (Map.Entry<String, Double> entry : playerFinalRank.entrySet()) {
            System.out.println(entry.getKey() + " - " + entry.getValue());
        }
        return playerFinalRank;
    }
}
