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
        double previous = -1.0;
        for (double value : values){
            if (previous != -1.0){
                if (previous % 1 == 0 && previous == value){
                    ties.add(values.indexOf(previous));
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
            if (i < ties.size() -1 && ties.get(i) == ties.get(i + 1) - 1){
                List<Integer> multiTies = new ArrayList<>();
                multiTies.add(i);
                double specialModifier = .5;
//                int j = 1;
                while (ties.get(i) == ties.get(i + 1) - 1){
                    specialModifier += .5;
                    i++;
                    multiTies.add(i);
                    if (i == ties.size() - 2){
                        break;
                    }
                }
                for (List<Double> each : thePlayers.values()) {
                    if (each.get(columnNumber).intValue() == ties.get(i)) {
                        System.out.println("modifying tie at column " + columnNumber + " with the value of " + each.get(columnNumber));
                        each.set(columnNumber, each.get(columnNumber) + 0.5);
                        System.out.println("modified tie at column " + columnNumber + " with the updated value of " + each.get(columnNumber));
                    }
                }
                //applyMultiTies(columnNumber, multiTies, specialModifier);
            }
            else {
                //each.set(columnNumber, (double) 1 + values.indexOf(each.get(columnNumber)));
                for (List<Double> each : thePlayers.values()){
                    if (each.get(columnNumber).intValue()  == ties.get(i)){
                        System.out.println("modifying tie at column " + columnNumber + " with the value of " + each.get(columnNumber));
                        each.set(columnNumber, each.get(columnNumber) + 0.5);
                        System.out.println("modified tie at column " + columnNumber + " with the updated value of " + each.get(columnNumber));
                    }
//                    if (each.get(columnNumber) == Double.valueOf(ties.get(i) + 1)){
//                        each.set(columnNumber, each.get(columnNumber) - MODIFIER);
//                    }
                }
            }
        }
    }
    public void applyMultiTies(int columnNumber, List<Integer> multiTies, double modifier){
//        for (List<Double> each : thePlayers.values()) {
//            if (each.get(columnNumber).intValue() == ties.get(i)) {
//                System.out.println("modifying tie at column " + columnNumber + " with the value of " + each.get(columnNumber));
//                each.set(columnNumber, each.get(columnNumber) + 0.5);
//                System.out.println("modified tie at column " + columnNumber + " with the updated value of " + each.get(columnNumber));
//            }
//        }
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

    public Map<String, List<Double>> getThePlayers() {
        return thePlayers;
    }

    public void setThePlayers(Map<String, List<Double>> thePlayers) {
        this.thePlayers = thePlayers;
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
