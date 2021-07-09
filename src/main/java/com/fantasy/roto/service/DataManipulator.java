package com.fantasy.roto.service;

import com.fantasy.roto.model.Player;

import java.util.*;

public class DataManipulator {
    Map<String, List<Double>> thePlayers;

    public Map<String, List<Double>> convertToMap(Collection<Collection<Player>> players){
        Map<String, List<Double>> hitters = new HashMap<>();
        Object[] arr;
        for (Collection<Player> player : players){
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

    public List<Player> convertToPlayerList(Collection<Collection<Player>> collectionOfPlayers){
        List<Player> playerList = new ArrayList<>();
        boolean isFirst = true;
        for (Collection<Player> playerCollection : collectionOfPlayers){
//            System.out.println(playerCollection);
//            for (int i = 1; i < playerCollection.size(); i++){
//                playerList.add(playerCollection);
//            }
            if (isFirst){
                isFirst = false;
                continue;
            } else {
                int count = 1;
                Player player = new Player();
                for (Object playerInfo : playerCollection){
                    switch (count){
                        case 1:
                            player.name = (String) playerInfo;
                            break;
                        case 2:
                            player.total = (double) playerInfo;
                            break;
                        case 3:
                            player.hitting = (double) playerInfo;
                            break;
                        case 4:
                            player.pitching = (double) playerInfo;
                            break;
                    }
                    count++;
                }
                playerList.add(player);
            }

        }

//        collectionOfPlayers.stream().flatMap(v -> v.stream()).forEach(playerList::add);
        return playerList;
    }

    public Map<String, List<Double>> rankAllColumns(boolean isPitching){
        if (isPitching){
            for (int i = 0; i < 6; i++){
                if (i == 2 || i == 3){
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

    public void addPosition(List<Player> players){
        for (int i = 0; i < players.size(); i++){
            double start = i + 1;
            int ties = 0;
            while (i + ties + 1 < players.size() && players.get(i).total == players.get(i + 1 + ties).total){
                start += .5;
                ties++;
            }
            players.get(i).rank = start;
            while (ties > 0){
                i++;
                players.get(i).rank = start;
                ties--;
            }
            start++;
        }
    }
//    public void calculateChange(List<Player> lastWeeksRanks, List<Player> finalPlayerRanks){
//        for (Player player : finalPlayerRanks){
//            double oldTotal;
//            if (lastWeeksRanks.get(0).name.equals(player.name)){
//                oldTotal = lastWeeksRanks.get(0).total;
//                lastWeeksRanks.remove(0);
//            } else {
//                oldTotal = lastWeeksRanks.stream().filter(v -> v.name.equals(player.name)).map(v -> v.total).findAny().orElse(-100.0);
//            }
//            player.totalChange = player.total - oldTotal;
//        }
//    }
    public void calculateChange(List<Player> lastWeeksRanks, List<Player> finalPlayerRanks){
        for (Player player : finalPlayerRanks){
            Player oldPlayer = lastWeeksRanks.get(0);
            if (!oldPlayer.name.equals(player.name)){
                oldPlayer = lastWeeksRanks.stream().filter(v -> v.name.equals(player.name)).findAny().orElse(null);
            }
            player.totalChange = player.total - oldPlayer.total;
            player.hittingChange = player.hitting - oldPlayer.hitting;
            player.pitchingChange = player.pitching - oldPlayer.pitching;
        }
    }
    public void calculateChange(List<Player> finalPlayerRanks){
        for (Player player : finalPlayerRanks){
            player.totalChange = 0;
            player.hittingChange = 0;
            player.pitchingChange = 0;
        }
    }

    public Map<String, List<Double>> getThePlayers() {
        return thePlayers;
    }

    public void setThePlayers(Map<String, List<Double>> thePlayers) {
        this.thePlayers = thePlayers;
    }

}
