package com.fantasy.roto;

import com.fantasy.roto.controller.Controller;

public class Main {
//    public static void main(String[] args) {
//        Reader reader = new Reader();
//        Map<String, Double> hittingRanks = reader.readAndRank("hitting-test.xlsx");
//        double sum = hittingRanks.values().stream().reduce(0.0, (s, v) -> s += v);
//        System.out.println("TOTAL --------- " + sum);
////        Map<String, Double> pitchingRanks = reader.readAndRank("hitting.xlsx");
////        Map<String, Double> pitchingRanks = reader.readAndRank("pitching.xlsx");
////        Map<String, Double> finalTotalRanks = DataManipulator.combineHittingAndPitching(hittingRanks, pitchingRanks);
//    //        Map<String, Double> finalTotalRanks = DataManipulator.combineHittingAndPitching(hittingRanks, hittingRanks);
//
//    }
    public static void main(String[] args) {
        Controller controller = new Controller();
        controller.run();
    }
}
