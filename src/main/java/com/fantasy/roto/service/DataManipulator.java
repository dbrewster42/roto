package com.fantasy.roto.service;

import com.fantasy.roto.model.Hitting;
import com.fantasy.roto.model.Pitching;
import com.fantasy.roto.model.Player;

import java.util.*;

public class DataManipulator {
    Map<String, List<Double>> thePlayers;

    public Map<String, List<Double>> convertToMap(Collection<Collection<Hitting>> players){
        Map<String, List<Double>> hitters = new HashMap<>();
        Object[] arr;
        for (Collection<Hitting> player : players){
            arr = player.toArray(new Object[7]);
            List<Double> values = new ArrayList<>();
            String key = (String) arr[0];
            for (int i = 1; i < 7; i++){
                values.add((Double) arr[i]);
            }
            hitters.put(key, values);
        }
        thePlayers = hitters;
        return hitters;
    }

    public List<Player> convertToPlayerList(Collection<Collection<Player>> players){
        List<Player> playerList = new ArrayList<>();
//        for (Collection<Player> player : players){
//            arr = player.toArray(new Object[7]);
//            List<Double> values = new ArrayList<>();
//            String key = (String) arr[0];
//            for (int i = 1; i < 7; i++){
//                values.add((Double) arr[i]);
//            }
//            hitters.put(key, values);
//        }
//        thePlayers = hitters;
        return playerList;
    }

    public Map<String, List<Double>> rankAllColumns(boolean isPitching){
        if (isPitching){
            for (int i = 0; i < 6; i++){
                if (i == 3 || i == 4){
                    rankColumn(i, true);
                } else {
                    rankColumn(i, false);
                }
            }
        } else {
            for (int i = 0; i < 6; i++){
                rankColumn(i, false);
            }
        }

        return thePlayers;
    }
    public void rankColumn(int columnNumber, boolean isReversed){
        List<Double> values = new ArrayList<>();
        for (List<Double> each : thePlayers.values()){
            values.add(each.get(columnNumber));
        }
        if (isReversed){
            values.sort(new Comparator<Double>() {
                @Override
                public int compare(Double o1, Double o2) {
                    return Double.compare(o2, o1);
                }
            });
        } else {
            values.sort(new Comparator<Double>() {
                @Override
                public int compare(Double o1, Double o2) {
                    return Double.compare(o1, o2);
                }
            });
        }

        List<Integer> ties = new ArrayList<>();
        double previous = -1.0;
        for (double value : values){
            if (previous != -1.0){
                //FIXME  doubles should not allow ties ie  if (previous % 1 == 0 && previous == value){
                if (previous == value){
                    ties.add(values.indexOf(value) + 1);
                }
            }
            previous = value;
        }
        for (List<Double> each : thePlayers.values()){
            each.set(columnNumber, (double) 1 + values.indexOf(each.get(columnNumber)));
        }
        applyTies(columnNumber, ties);
    }
    public void applyTies(int columnNumber, List<Integer> ties){
        for (int i = 0; i < ties.size(); i++){
            if (i < ties.size() -1 && ties.get(i) == ties.get(i + 1)){
                int multiTie = ties.get(i);
                double specialModifier = .5;
                while (i < ties.size() -1 && ties.get(i) == ties.get(i + 1)){
                    specialModifier += .5;
                    i++;
                }
                for (List<Double> each : thePlayers.values()) {
                    if (each.get(columnNumber).intValue() == multiTie) {
                        each.set(columnNumber, each.get(columnNumber) + specialModifier);
                    }
                }
            }
            else {
                for (List<Double> each : thePlayers.values()){
                    if (each.get(columnNumber).intValue()  == ties.get(i)){
                        each.set(columnNumber, each.get(columnNumber) + 0.5);
                    }
                }
            }
        }
    }

    public Map<String, Double> calculateScore(Map<String, List<Double>> playerRanks){
       Map<String, Double> ranks = new HashMap<>();
        for (Map.Entry<String, List<Double>> player : playerRanks.entrySet()){
            double sum = 0;
            for (Double score : player.getValue()){
                sum += score;
            }
            ranks.put(player.getKey(), sum);
        }

        return ranks;
    }
    public List<Player> createPlayersWithHitting(Map<String, Double> rank){
        List<Player> finalResults = new ArrayList<>();
        for (Map.Entry<String, Double> each : rank.entrySet()){
            Player player = new Player(each.getKey(), each.getValue());
            finalResults.add(player);
        }
        return finalResults;
    }
    public List<Player> addPitching(Map<String, Double> rank, List<Player> players){
        for (Player player : players){
            player.pitching = rank.get(player.name);
        }
        return players;
    }
    public List<Player> createPlayersWithHittingSort(Map<String, Double> rank){
        List<Player> finalResults = new ArrayList<>();
        for (Map.Entry<String, Double> each : rank.entrySet()){
            Player player = new Player(each.getKey(), each.getValue());
            finalResults.add(player);
        }
        Collections.sort(finalResults, new Comparator<Player>() {
            @Override
            public int compare(Player o1, Player o2) {
                return Double.compare(o2.hitting, o1.hitting);
            }
        });
        return finalResults;
    }
    public List<Player> addAndSortByPitching(Map<String, Double> rank, List<Player> players){
        for (Player player : players){
            player.pitching = rank.get(player.name);
        }
        Collections.sort(players, new Comparator<Player>() {
            @Override
            public int compare(Player o1, Player o2) {
                return Double.compare(o2.pitching, o1.pitching);
            }
        });
        return players;
    }
    public List<Player> combineHittingAndPitching(List<Player> players){
        for (Player player : players){
            player.total = player.hitting + player.pitching;
        }
        Collections.sort(players, new Comparator<Player>() {
            @Override
            public int compare(Player o1, Player o2) {
                return Double.compare(o2.total, o1.total);
            }
        });
        return players;
    }

    public Map<String, List<Double>> getThePlayers() {
        return thePlayers;
    }

    public void setThePlayers(Map<String, List<Double>> thePlayers) {
        this.thePlayers = thePlayers;
    }

}
