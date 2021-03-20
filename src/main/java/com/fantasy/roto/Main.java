package com.fantasy.roto;

import com.fantasy.roto.service.DataManipulator;
import com.fantasy.roto.service.Reader;

import java.util.Map;

public class Main {
    public static void main(String[] args) {
        Reader reader = new Reader();
        Map<String, Double> hittingRanks = reader.readAndRank("hitting-test.xlsx");
//        Map<String, Double> pitchingRanks = reader.readAndRank("hitting.xlsx");
//        Map<String, Double> pitchingRanks = reader.readAndRank("pitching.xlsx");
//        Map<String, Double> finalTotalRanks = DataManipulator.combineHittingAndPitching(hittingRanks, pitchingRanks);
        //TODO uncomment
        Map<String, Double> finalTotalRanks = DataManipulator.combineHittingAndPitching(hittingRanks, hittingRanks);

    }
}
