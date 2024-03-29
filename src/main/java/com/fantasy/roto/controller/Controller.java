package com.fantasy.roto.controller;

import com.fantasy.roto.model.Player;
import com.fantasy.roto.service.DataManipulator;
import com.fantasy.roto.service.Excel_IO;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class Controller {
    private final DataManipulator dataManipulator = new DataManipulator();

    public void run(int weekNumber){
        Excel_IO excelIO = new Excel_IO("stats.xlsx");
        Collection<Collection<Player>> hittingCollections = excelIO.readSheet("Sheet1");

        Map<String, Double> playerFinalHittingRank =  rank(hittingCollections, false);
        List<Player> sortedHittingRank = dataManipulator.createPlayersWithHitting(playerFinalHittingRank);

//        for (Player each : sortedHittingRank){
//            System.out.println(each.name + " - " + each.);
//        }


        Collection<Collection<Player>> pitchingCollections = excelIO.readSheet("Sheet2");
        Map<String, Double> playerFinalPitchingRank = rank(pitchingCollections, true);
        List<Player> sortedPitchingRank = dataManipulator.addPitching(playerFinalPitchingRank, sortedHittingRank);

        List<Player> finalPlayerRanks = dataManipulator.combineHittingAndPitching(sortedPitchingRank);
        for (Player player : finalPlayerRanks){
            System.out.println(player.name + " \t " + player.total + " \t " + player.hitting + " \t " + player.pitching);
        }
        dataManipulator.addPosition(finalPlayerRanks);
//        double total =  finalPlayerRanks.stream().map(v -> v.total).reduce(0.0, (sum, v) -> sum += v);
//        System.out.println();
//        System.out.println("Total is 1260 : " + total);

        compareToLastWeek(finalPlayerRanks, weekNumber);

        excelIO.write(finalPlayerRanks, "Week" + weekNumber);

    }

//    public void writeEachRank(int weekNumber){
////        Excel_IO excelIO = new Excel_IO("stats.xlsx");
//        sortAndRankEachCategory()
////        excelIO.write(finalPlayerRanks, "Week" + weekNumber);
//    }

    public void compareToLastWeek(List<Player> finalPlayerRanks, int weekNumber){
        int lastWeek = weekNumber - 1;
        try {

            Excel_IO excelIO = new Excel_IO("results.xlsx");

            Collection<Collection<Player>> lastWeeksTotal = excelIO.readSheet("Week" + lastWeek);
            System.out.println("Sheet has been read ------------------- ");
            List<Player> lastWeeksRanks = dataManipulator.convertToPlayerList(lastWeeksTotal);
            System.out.println("Sheet has been converted into Player List ***************** ");
            dataManipulator.calculateChange(lastWeeksRanks, finalPlayerRanks);
        } catch (Exception e){
            System.out.println("Error calculating change from last week - " + e);
//            dataManipulator.calculateChange(finalPlayerRanks);
        }
    }

    public void sortAndRankEachCategory(int lastWeek){
        try {

            Excel_IO excelIO = new Excel_IO("results.xlsx");

            Collection<Collection<Player>> lastWeeksTotal = excelIO.readSheet("Week" + lastWeek);
            System.out.println("Sheet has been read ------------------- ");
            List<Player> playerRanks = dataManipulator.convertToPlayerList(lastWeeksTotal);
            System.out.println("Sheet has been converted into Player List ***************** ");
            dataManipulator.rankHittingPoints(playerRanks);
            AtomicInteger count = new AtomicInteger(0);
            playerRanks.forEach(v -> System.out.println(count.incrementAndGet() + ". " + v.name + " - " + v.hitting));
            count.set(0);
            dataManipulator.rankPitchingPoints(playerRanks);
            playerRanks.forEach(v -> System.out.println(count.incrementAndGet() + ". " + v.name + " - " + v.pitching));
        }catch (Exception e){
            System.out.println("Error ranking each cat - " + e);
        }
    }

    public Map<String, Double> rank(Collection<Collection<Player>> playerCollections, boolean isPitching){
//        Map<String, List<Double>> playerStats = dataManipulator.convertToMap(playerCollections);
        dataManipulator.convertToMap(playerCollections);
        Map<String, List<Double>> playerRanks = dataManipulator.rankAllColumns(isPitching);
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
//    public void run(){
//        Excel_IO excelIO = new Excel_IO();
////               Collection<Collection<Hitting>> hittingCollections = reader.read("hitting-test.xlsx");
//        Collection<Collection<Hitting>> hittingCollections = excelIO.read("batting.xlsx");
//        Map<String, Double> playerFinalHittingRank =  rank(hittingCollections, false);
//
//        List<Player> sortedHittingRank = dataManipulator.createPlayersWithHittingSort(playerFinalHittingRank);
//        for (Player player : sortedHittingRank){
//            System.out.println(player.name + " \t " + player.hitting);
//        }
//        System.out.println();
//
//        Collection<Collection<Hitting>> pitchingCollections = excelIO.read("pitching.xlsx");
//        Map<String, Double> playerFinalPitchingRank = rank(pitchingCollections, true);
//
//        List<Player> sortedPitchingRank = dataManipulator.addAndSortByPitching(playerFinalPitchingRank, sortedHittingRank);
//        for (Player player : sortedPitchingRank){
//            System.out.println(player.name + " \t " + player.pitching);
//        }
//        System.out.println();
//
//        List<Player> finalPlayerRanks = dataManipulator.combineHittingAndPitching(sortedPitchingRank);
//        for (Player player : finalPlayerRanks){
//            System.out.println(player.name + " \t " + player.total);
//        }
//
//        double total =  finalPlayerRanks.stream().map(v -> v.total).reduce(0.0, (sum, v) -> sum += v);
//        System.out.println();
//        System.out.println("Total is 1260 : " + total);
//
//    }
