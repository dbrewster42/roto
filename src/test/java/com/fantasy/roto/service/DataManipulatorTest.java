package com.fantasy.roto.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class DataManipulatorTest {
    DataManipulator sut = new DataManipulator();

    @BeforeEach
    void setUp() {
        sut.setThePlayers(generateMap());
    }

//    @Test
//    void convertToMap() {
//    }
//
//    @Test
//    void rankAllColumns() {
//    }

    @Test
    void rankColumn() {
        sut.rankColumn(0, false);
        List<Double> rainmakersRanks = new ArrayList<>(Arrays.asList(4.0, 21.0, 20.0));
        assertEquals(sut.getThePlayers().get("rainmaker"), rainmakersRanks);
    }

    @Test
    void calculateScore() {
    }

    @Test
    void descMapSort() {
    }

    @Test
    void combineHittingAndPitching() {
    }

    Map<String, List<Double>> generateMap(){
        Map<String, List<Double>> thePlayers = new HashMap<>();
//        List<Double> values = new
        thePlayers.put("rainmaker", new ArrayList<Double>(Arrays.asList(20.0, 21.0, 20.0)));
        thePlayers.put("joe", new ArrayList<>(Arrays.asList(10.0, 9.0, 10.0)));
        thePlayers.put("scar", new ArrayList<Double>(Arrays.asList(20.0, 20.0, 22.0)));
        thePlayers.put("other", new ArrayList<Double>(Arrays.asList(8.0, 8.0, 12.0)));
        return thePlayers;
    }
}