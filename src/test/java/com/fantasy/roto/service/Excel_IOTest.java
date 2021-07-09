package com.fantasy.roto.service;

import com.fantasy.roto.model.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

class Excel_IOTest {
     Excel_IO sut;

     @BeforeEach
     void setUp() {
          sut = new Excel_IO();
     }

     @Test
     void readSheet() {
     }

     @Test
     void readLastWeeksTotal() {
          sut.setInputFile("results.xlsx");
          Collection<Collection<Player>> lastWeek = sut.readSheet("Week3");
          assertNotNull(lastWeek);
     }

     @Test
     void write() {
     }
}