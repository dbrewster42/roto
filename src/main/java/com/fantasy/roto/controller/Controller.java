package com.fantasy.roto.controller;

import com.fantasy.roto.model.Player;
import com.fantasy.roto.service.DataManipulator;
import com.fantasy.roto.service.Excel_IO;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class Controller {
    private final DataManipulator service = new DataManipulator();
    private Excel_IO excelIO;


    public void run(){
        excelIO = new Excel_IO("stats.xlsx");

        Collection<Collection<Player>> hittingCollections = excelIO.readSheet("Sheet1");
        Map<String, Double> playerFinalHittingRank =  rank(hittingCollections, false);
        List<Player> players = service.createPlayersWithHitting(playerFinalHittingRank);

        Collection<Collection<Player>> pitchingCollections = excelIO.readSheet("Sheet2");
        Map<String, Double> playerFinalPitchingRank = rank(pitchingCollections, true);
        service.addPitching(playerFinalPitchingRank, players);

        List<Player> finalPlayers = service.calculateTotal(players);
        service.sortTotal(finalPlayers);
//        for (Player player : finalPlayerRanks){
//            System.out.println(player.name + " \t " + player.total + " \t " + player.hitting + " \t " + player.pitching);
//        }
        service.calculateRank(finalPlayers);
//        double total =  sortedPlayers.stream().map(v -> v.total).reduce(0.0, (sum, v) -> sum += v);
//        System.out.println();
//        System.out.println("Total is 1260 : " + total);

        compareToLastWeek(finalPlayers);

        excelIO.writeRoto(finalPlayers);
        excelIO.writeRanks(service.convertToRank(players));
    }
    private void printRanks(List<Player> sortedHitters, List<Player> sortedPitchers) {
        AtomicInteger count = new AtomicInteger(0);
        sortedHitters.forEach(v -> System.out.println(count.incrementAndGet() + ". " + v.name + " - " + v.hitting));
        count.set(0);
        sortedPitchers.forEach(v -> System.out.println(count.incrementAndGet() + ". " + v.name + " - " + v.pitching));
    }


    private void parseRanks2(List<Player> sortedHitters, List<Player> sortedPitchers) {
        AtomicInteger count = new AtomicInteger(0);
        List<String> hitters = sortedHitters.stream()
                .map(v -> count.incrementAndGet() +  ". " + v.name + " - " + v.hitting)
                .collect(Collectors.toList());
        count.set(0);
        List<String> pitchers = sortedPitchers.stream()
                .map(v -> count.incrementAndGet() +  ". " + v.name + " - " + v.pitching)
                .collect(Collectors.toList());

        List<List<String>> ranks = new ArrayList<>();
        ranks.add(hitters);
        ranks.add(pitchers);
        excelIO.writePoints(ranks);
    }

//    private List<String> convertToString(List<Player> sortedRank) {
//        AtomicInteger count = new AtomicInteger(0);
//        sortedRank.stream().map(v -> count.incrementAndGet() +  ". " + v.name + " - " + v.hitting);
//        System.out.println("Sheet has been converted into Player List ***************** ");
//        playerRanks.forEach(v -> System.out.println(count.incrementAndGet() + ". " + v.name + " - " + v.hitting));
//        count.set(0);
//        dataManipulator.rankPitchingPoints(playerRanks);
//        playerRanks.forEach(v -> System.out.println(count.incrementAndGet() + ". " + v.name + " - " + v.pitching));
//    }

    public void compareToLastWeek(List<Player> finalPlayerRanks){
        try {
            Excel_IO excelIO = new Excel_IO("results.xlsx");

            List<Player> lastWeeksRanks = service.convertToPlayerList(excelIO.readSheet("Sheet"));
            System.out.println("Sheet has been converted into Player List ***************** ");

            service.calculateChange(lastWeeksRanks, finalPlayerRanks);
        } catch (Exception e){
            System.out.println("Error while calculating changes from last week - " + e);
        }
    }

    public void sortAndRankEachCategory(int lastWeek){
        try {

            Excel_IO excelIO = new Excel_IO("results.xlsx");

            Collection<Collection<Player>> lastWeeksTotal = excelIO.readSheet("Week" + lastWeek);
            System.out.println("Sheet has been read ------------------- ");
            List<Player> playerRanks = service.convertToPlayerList(lastWeeksTotal);
            System.out.println("Sheet has been converted into Player List ***************** ");
            service.sortHitting(playerRanks);
            AtomicInteger count = new AtomicInteger(0);
            playerRanks.forEach(v -> System.out.println(count.incrementAndGet() + ". " + v.name + " - " + v.hitting));
            count.set(0);
            service.sortPitching(playerRanks);
            playerRanks.forEach(v -> System.out.println(count.incrementAndGet() + ". " + v.name + " - " + v.pitching));
        }catch (Exception e){
            System.out.println("Error ranking each cat - " + e);
        }
    }

    public Map<String, Double> rank(Collection<Collection<Player>> playerCollections, boolean isPitching){
//        Map<String, List<Double>> playerStats = dataManipulator.convertToMap(playerCollections);
        service.convertToMap(playerCollections);
        Map<String, List<Double>> playerRanks = service.rankAllColumns(isPitching);
//        for (Map.Entry<String, List<Double>> entry : playerRanks.entrySet()){
//            System.out.println(entry.getKey() + " " + entry.getValue().toString());
//        }
//        for (int i = 0; i < 6; i++){
//            debugging(playerRanks, i);
//        }
        Map<String, Double> playerFinalRank = service.calculateScore(playerRanks);
//        for (Map.Entry<String, Double> entry : playerFinalRank.entrySet()) {
//            System.out.println(entry.getKey() + " - " + entry.getValue());
//        }

        return playerFinalRank;
    }

    public void compareRanks(int week){
        try {

            Excel_IO excelOld = new Excel_IO("compare.xlsx");

            Excel_IO excelCurrent = new Excel_IO("results.xlsx");

            Collection<Collection<Player>> lastWeeksTotal = excelOld.readSheet("Sheet");
            System.out.println("Sheet has been read ------------------- ");
            List<Player> lastWeeksRanks = service.convertToPlayerList(lastWeeksTotal);
            System.out.println("Sheet has been converted into Player List ***************** ");

            List<Player> currentRanks = service.convertToPlayerList(excelCurrent.readSheet("Sheet" + --week));

            service.calculateChange(lastWeeksRanks, currentRanks);
        } catch (Exception e){
            System.out.println("Error calculating change from last week - " + e);
//            dataManipulator.calculateChange(finalPlayerRanks);
        }
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
