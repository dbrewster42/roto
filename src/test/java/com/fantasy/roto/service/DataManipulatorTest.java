package com.fantasy.roto.service;

import com.fantasy.roto.model.Player;
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
    @Test
    void rankAllColumnsWithPitching() {
        sut.rankAllColumns(true);
        List<Double> rainmakersRanks = new ArrayList<>(Arrays.asList(3.5, 4.0, 3.0, 4.0, 4.0, 4.0));
        assertEquals(sut.getThePlayers().get("rainmaker"), rainmakersRanks);

        List<Double> joesRanks = new ArrayList<>(Arrays.asList(2.0, 2.0, 1.0, 2.0, 2.0, 1.0));
        assertEquals(sut.getThePlayers().get("joe"), joesRanks);
    }
    @Test
    void rankAllColumnsWithHitting() {
        sut.rankAllColumns(false);
        List<Double> rainmakersRanks = new ArrayList<>(Arrays.asList(3.5, 4.0, 3.0, 1.0, 1.0, 4.0));
        assertEquals(sut.getThePlayers().get("rainmaker"), rainmakersRanks);

        List<Double> joesRanks = new ArrayList<>(Arrays.asList(2.0, 2.0, 1.0, 3.0, 3.0, 1.0));
        assertEquals(sut.getThePlayers().get("joe"), joesRanks);

    }

    @Test
    void rankColumn() {
        sut.rankColumn(0, false);
        List<Double> rainmakersRanks = new ArrayList<>(Arrays.asList(3.5, 21.0, 20.0, 0.5, 0.5, 20.0));
        assertEquals(sut.getThePlayers().get("rainmaker"), rainmakersRanks);
    }

    @Test
    void calculateScore() {
        Map<String, Double> ranks = sut.calculateScore(generateMapOfRanks());

        assertEquals(ranks.get("rainmaker"), 22.5);
        assertEquals(ranks.get("joe"), 10.0);
    }

    @Test
    void descMapSort() {
    }

    @Test
    void combineHittingAndPitchingIsRanked() {
        List<Player> players = generatePlayersList();
        sut.combineHittingAndPitching(players);
        assertEquals("rainmaker", players.get(0).name);
        assertEquals(51, players.get(0).total);
        assertEquals("osiris", players.get(1).name);
        assertEquals(43.5, players.get(1).total);
        assertEquals(42.5, players.get(2).total);
    }

    List<Player> generatePlayersList(){
        List<Player> players = new ArrayList<>();
        Player player1 = new Player("rainmaker", 26, 25);
        Player player2 = new Player("joe", 21, 20);
        Player player3 = new Player("scar", 15, 27.5);
        Player player4 = new Player("osiris", 25, 18.5);
        players.add(player1);
        players.add(player2);
        players.add(player3);
        players.add(player4);
        return players;
    }

    Map<String, List<Double>> generateMapOfRanks(){
        Map<String, List<Double>> thePlayers = new HashMap<>();
        thePlayers.put("rainmaker", new ArrayList<Double>(Arrays.asList(3.5, 4.0, 3.0, 4.0, 4.0, 4.0)));
        thePlayers.put("joe", new ArrayList<>(Arrays.asList(2.0, 2.0, 1.0, 2.0, 2.0, 1.0)));
        thePlayers.put("scar", new ArrayList<Double>(Arrays.asList(3.5, 3.0, 4.0, 1.0, 1.0, 2.0)));
        thePlayers.put("other", new ArrayList<Double>(Arrays.asList(1.0, 1.0, 1.0, 3.0, 3.0, 3.0)));
        return thePlayers;
    }
    Map<String, List<Double>> generateMap(){
        Map<String, List<Double>> thePlayers = new HashMap<>();
        thePlayers.put("rainmaker", new ArrayList<Double>(Arrays.asList(20.0, 21.0, 20.0, .5, .5, 20.0)));
        thePlayers.put("joe", new ArrayList<>(Arrays.asList(10.0, 9.0, 10.0, .6, .627, 15.0)));
        thePlayers.put("scar", new ArrayList<Double>(Arrays.asList(20.0, 20.0, 22.0, .7, .63, 17.0)));
        thePlayers.put("other", new ArrayList<Double>(Arrays.asList(8.0, 8.0, 12.0, .55, .523, 19.0)));
        return thePlayers;
    }

    @Test
    void convertToPlayerList(){
        Excel_IO reader = new Excel_IO();
        reader.setInputFile("results.xlsx");
        Collection<Collection<Player>> lastWeek = reader.readLastWeeksTotal("Week3");

        List<Player> players = sut.convertToPlayerList(lastWeek);
        players.stream().forEach(System.out::println);
        assertEquals(players.size(), 14);
    }

    @Test
    void addPosition(){
        List<Player> players = generatePlayersWithRanks();
        sut.addPosition(players);
        players.stream().peek(v -> System.out.println(v.name + " - " + v.rank));

        assertEquals(players.get(0).rank, 1);
        assertEquals(players.get(1).rank, 2.5);
        assertEquals(players.get(2).rank, 2.5);
        assertEquals(players.get(3).rank, 5);
        assertEquals(players.get(4).rank, 5);
        assertEquals(players.get(6).rank, 7);
    }

    List<Player> generatePlayersWithRanks(){
        List<Player> playerList = new ArrayList<>();
        Player player = new Player();
        player.name = "rainmaker";
        player.total = 115.5;

        Player player2 = new Player();
        player2.name = "scar";
        player2.total = 113;

        Player player3 = new Player();
        player3.name = "dj";
        player3.total = 113;

        Player player4 = new Player();
        player4.name = "asdf";
        player4.total = 77;

        Player player5 = new Player();
        player5.name = "djj";
        player5.total = 77;

        Player player6 = new Player();
        player6.name = "qwert";
        player6.total = 77;

        Player player7 = new Player();
        player7.name = "loser";
        player7.total = 76.5;

        playerList.add(player);
        playerList.add(player2);
        playerList.add(player3);
        playerList.add(player4);
        playerList.add(player5);
        playerList.add(player6);
        playerList.add(player7);

        return playerList;
    }
}