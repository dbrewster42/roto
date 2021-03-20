package com.fantasy.roto.controller;

import com.fantasy.roto.model.Hitting;
import com.fantasy.roto.service.DataManipulator;
import com.fantasy.roto.service.Reader;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public class Controller {
    private final DataManipulator dataManipulator = new DataManipulator();

    public void run(){
        Reader reader = new Reader();
//        Map<String, Double> hittingRanks = reader.readAndRank("hitting-test.xlsx");
        Collection<Collection<Hitting>> playerCollections = reader.read("hitting-test.xlsx");

        Map<String, List<Double>> playerStats = dataManipulator.convertToMap(playerCollections);

        Map<String, List<Double>> playerRanks = dataManipulator.rankAllColumns();
        for (Map.Entry<String, List<Double>> entry : playerRanks.entrySet()){
            System.out.println(entry.getKey() + " " + entry.getValue().toString());
        }
        for (int i = 0; i < 6; i++){
            debugging(playerRanks, i);
        }

        Map<String, Double> playerFinalRank = dataManipulator.calculateScore(playerRanks);
        for (Map.Entry<String, Double> entry : playerFinalRank.entrySet()) {
            System.out.println(entry.getKey() + " - " + entry.getValue());
        }
//        return playerFinalRank;


        double sum = playerFinalRank.values().stream().reduce(0.0, (s, v) -> s += v);
        System.out.println("TOTAL --------- " + sum);
        //TODO uncomment
//        Map<String, Double> pitchingRanks = reader.readAndRank("hitting.xlsx");
//        Map<String, Double> pitchingRanks = reader.readAndRank("pitching.xlsx");
//        Map<String, Double> finalTotalRanks = DataManipulator.combineHittingAndPitching(hittingRanks, pitchingRanks);
    }
    public void debugging(Map<String, List<Double>> playerRanks, int column){
        double s =  playerRanks.values().stream().map(v -> v.get(column)).reduce(0.0, (sum, v) -> sum += v);
        System.out.println("COLUMN " + column + " : TOTAL = " + s);
    }
}
