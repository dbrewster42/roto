package com.fantasy.roto.controller;

import com.fantasy.roto.model.Player;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class ControllerTest {
     Controller sut = new Controller();

     @Test
     void compareToLastWeek(){
          sut.compareToLastWeek(generatePlayersFinalRanks(), 16);
     }

     List<Player> generatePlayersFinalRanks(){
          List<Player> players = new ArrayList<>();
          Player player1 = new Player("rainmaker", 26, 25);
          player1.rank = 1;
          player1.total = 51;
          Player player2 = new Player("joe", 21, 20);
          player2.total = 41;
          player2.rank = 2;
          Player player3 = new Player("scar", 15, 27.5);
          player3.total = 42.5;
          player3.rank = 3;
          Player player4 = new Player("osiris", 25, 18.5);
          player4.total = 43.5;
          player4.rank = 2;
          players.add(player1);
          players.add(player2);
          players.add(player3);
          players.add(player4);
          return players;
     }
}
