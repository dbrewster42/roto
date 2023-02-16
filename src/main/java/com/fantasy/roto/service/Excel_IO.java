package com.fantasy.roto.service;

import com.ebay.xcelite.Xcelite;
import com.ebay.xcelite.options.XceliteOptions;
import com.ebay.xcelite.policies.MissingCellPolicy;
import com.ebay.xcelite.policies.MissingRowPolicy;
import com.ebay.xcelite.reader.SheetReader;
import com.ebay.xcelite.sheet.XceliteSheet;
import com.ebay.xcelite.writer.SheetWriter;
import com.fantasy.roto.model.Player;
import com.fantasy.roto.model.Rank;

import java.io.File;
import java.util.Collection;
import java.util.List;

public class Excel_IO {
    private String inputFile;
    private final String outputFilename = "results.xlsx";
    private File file;
    private Xcelite xcelite;

    public Excel_IO(String inputFile){
        this.inputFile = inputFile;
    }
    public Excel_IO(){ }


    public Collection<Collection<Player>> readSheet(String sheetName) {
        Xcelite xcelite = new Xcelite(new File(inputFile));
        XceliteSheet sheet = xcelite.getSheet(sheetName);

        SheetReader<Collection<Player>> simpleReader = sheet.getSimpleReader();

        return simpleReader.read();
    }

    public void writeRoto(List<Player> players){
        file = new File(outputFilename);
        xcelite = new Xcelite();
//        XceliteOptions xceliteOptions = xcelite.getOptions();
//        xceliteOptions.setMissingCellPolicy(MissingCellPolicy.RETURN_NULL_AND_BLANK);
//        xceliteOptions.setFirstDataRowIndex(0);
//        xceliteOptions.setMissingRowPolicy(MissingRowPolicy.SKIP);
//        xceliteOptions.setTrailingEmptyRowPolicy(TrailingEmptyRowPolicy.EMPTY_OBJECT);
//        XceliteOptions.first
        XceliteSheet sheet = xcelite.createSheet("Sheet");

        SheetWriter<Player> writer = sheet.getBeanWriter(Player.class);

        writer.write(players);
        xcelite.write(new File(outputFilename));

        System.out.println("done");
    }

    public void writePoints(List<List<String>> ranks) {
        XceliteOptions xceliteOptions = xcelite.getOptions();
        xceliteOptions.setFirstDataRowIndex(18);
        XceliteSheet sheet = xcelite.getSheet("Sheet");
        SheetWriter writer = sheet.getSimpleWriter();
//        SheetWriter<List<String>> writer = sheet.getSimpleWriter();

        writer.write(ranks);
        xcelite.write(file);
    }

//    public void writeRanks(List<String> hitters, List<String> pitchers) {
//        Xcelite xcelite = new Xcelite();
//        XceliteSheet sheet = xcelite.createSheet("Sheet");
//        SheetWriter writer = sheet.getSimpleWriter();
////        SheetWriter<List<String>> writer = sheet.getSimpleWriter();
//
//        writer.write(List.of(hitters, pitchers));
//        xcelite.write(file);
//    }
    public void writeRanks(List<Rank> ranks) {
        XceliteOptions xceliteOptions = xcelite.getOptions();
        xceliteOptions.setHeaderRowIndex(18);
        XceliteSheet sheet = xcelite.getSheet("Sheet");
        SheetWriter writer = sheet.getBeanWriter(Rank.class);

        writer.write(ranks);
        xcelite.write(file);
    }


    public String getInputFile() {
        return inputFile;
    }

    public void setInputFile(String inputFile) {
        this.inputFile = inputFile;
    }
}
