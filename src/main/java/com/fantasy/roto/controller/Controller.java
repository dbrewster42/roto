package com.fantasy.roto.controller;

import com.fantasy.roto.model.Hitting;
import com.fantasy.roto.model.Player;
import com.fantasy.roto.service.DataManipulator;
import com.fantasy.roto.service.Reader;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public class Controller {
    private final DataManipulator dataManipulator = new DataManipulator();

    public void run(){
        Reader reader = new Reader();
//               Collection<Collection<Hitting>> hittingCollections = reader.read("hitting-test.xlsx");
        Collection<Collection<Hitting>> hittingCollections = reader.read("batting.xlsx");
        Map<String, Double> playerFinalHittingRank =  rank(hittingCollections);
        List<Player> sortedHittingRank = dataManipulator.createPlayersWithHittingSort(playerFinalHittingRank);
        for (Player player : sortedHittingRank){
            System.out.println(player.name + " \t " + player.hitting);
        }
        System.out.println();

        Collection<Collection<Hitting>> pitchingCollections = reader.read("pitching.xlsx");
        Map<String, Double> playerFinalPitchingRank = rank(pitchingCollections);

        List<Player> sortedPitchingRank = dataManipulator.addAndSortByPitching(playerFinalPitchingRank, sortedHittingRank);
        for (Player player : sortedPitchingRank){
            System.out.println(player.name + " \t " + player.pitching);
        }
        System.out.println();

        List<Player> finalPlayerRanks = dataManipulator.combineHittingAndPitching(sortedPitchingRank);
        for (Player player : finalPlayerRanks){
            System.out.println(player.name + " \t " + player.total);
        }
//        double sum = playerFinalRank.values().stream().reduce(0.0, (s, v) -> s += v);
//        System.out.println("HITTING TOTAL --------- " + sum);

    }

    public Map<String, Double> rank(Collection<Collection<Hitting>> playerCollections){
        Map<String, List<Double>> playerStats = dataManipulator.convertToMap(playerCollections);

        Map<String, List<Double>> playerRanks = dataManipulator.rankAllColumns();
//        for (Map.Entry<String, List<Double>> entry : playerRanks.entrySet()){
//            System.out.println(entry.getKey() + " " + entry.getValue().toString());
//        }
//        for (int i = 0; i < 6; i++){
//            debugging(playerRanks, i);
//        }
        Map<String, Double> playerFinalRank = dataManipulator.calculateScore(playerRanks);
//        for (Map.Entry<String, Double> entry : playerFinalRank.entrySet()) {
//            System.out.println(entry.getKey() + " - " + entry.getValue());
//        }

        return playerFinalRank;
    }


    public void debugging(Map<String, List<Double>> playerRanks, int column){
        double s =  playerRanks.values().stream().map(v -> v.get(column)).reduce(0.0, (sum, v) -> sum += v);
        System.out.println("COLUMN " + column + " : TOTAL = " + s);
    }
}
