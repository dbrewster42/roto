package com.fantasy.roto.service;

import com.fantasy.roto.model.Hitting;
import com.fantasy.roto.model.Pitching;

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

    public Map<String, List<Double>> rankAllColumns(){
        for (int i = 0; i < 6; i++){
            rankColumn(i);
        }
        return thePlayers;
//        List<Integer> stat = new ArrayList<>();
//        for (Collection<Pitching> player : players){
////            stat.add(player.);
//        }
//
//        return players;
    }
    public void rankColumn(int columnNumber){
        List<Double> values = new ArrayList<>();
        for (List<Double> each : thePlayers.values()){
            values.add(each.get(columnNumber));
        }
        values.sort(new Comparator<Double>() {
            @Override
            public int compare(Double o1, Double o2) {
                return Double.compare(o1, o2);
            }
        });
//        values.stream().forEach(System.out::println);
        List<Integer> ties = new ArrayList<>();
        Double previous = null;
        //TODO modify ties
        for (Double value : values){
            if (previous != null){
                if (previous == value){
                    ties.add(values.indexOf(previous));
                    System.out.println(columnNumber + " has a tie for the value of " + value);
                }
                previous = value;
            }
        }
//        for (int i = 0; i < values.size() - 1; i++){
//            if (values.get(i) == values.get(i + 1)){
//                ties.add(i);
//                System.out.println(i);
//            }
//        }
        for (List<Double> each : thePlayers.values()){
            each.set(columnNumber, (double) 1 + values.indexOf(each.get(columnNumber)));
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
//        for (List<Double> player : playerRanks.values()){
//            double sum = 0;
//            for (Double score : player){
//                sum += score;
//            }
//            ranks.put()
//        }
    }
    public Map<String, Double> descMapSort(Map<String, Double> rank){
        //TODO sort map
        return rank;
    }

    public static Map<String, Double> combineHittingAndPitching(Map<String, Double> hittingRanks, Map<String, Double> pitchingRanks){
        for (Map.Entry<String, Double> entry : hittingRanks.entrySet()) {
            Double pitchingScore = pitchingRanks.get(entry.getKey());
            System.out.println(entry.getKey() + " - " + entry.getValue() + " + " + pitchingRanks.get(entry.getKey()));
            entry.setValue(entry.getValue() + pitchingScore);
        }
        return hittingRanks;
    }

    public Object[] convertToObjectArray(Collection<Collection<Hitting>> players){
        Object[] returnValue = new Hitting[14];
        int count = 0;
        for (Collection<Hitting> player : players){
            System.out.println(player.toString());
            returnValue = player.toArray(new Object[7]);
            count++;
        }
        return returnValue;
    }
    public Hitting[] convertToHittingArray(Collection<Collection<Hitting>> players){
        Map<String, List<Double>> hitters = new HashMap<>();
        Hitting[] returnValue = new Hitting[14];
        int count = 0;
        for (Collection<Hitting> player : players){
            System.out.println(player.toString());
            returnValue = player.toArray(new Hitting[count]);
            count++;
        }
        return returnValue;
    }
    public Pitching[] convertToPitchingArray(Collection<Collection<Pitching>> players){
        Pitching[] returnValue = new Pitching[14];
        for (Collection<Pitching> player : players){
            returnValue = player.toArray(new Pitching[0]);
        }
        return returnValue;
    }


}
