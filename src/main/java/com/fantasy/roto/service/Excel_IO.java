package com.fantasy.roto.service;

import com.ebay.xcelite.Xcelite;
import com.ebay.xcelite.options.XceliteOptions;
import com.ebay.xcelite.policies.MissingCellPolicy;
import com.ebay.xcelite.reader.SheetReader;
import com.ebay.xcelite.sheet.XceliteSheet;
import com.ebay.xcelite.writer.SheetWriter;
import com.fantasy.roto.model.Player;

import java.io.File;
import java.util.Collection;
import java.util.List;

public class Excel_IO {
    private String inputFile;

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

    public void write(List<Player> players, String sheetName){
        Xcelite xcelite = new Xcelite();
        XceliteOptions xceliteOptions = xcelite.getOptions();
        xceliteOptions.setMissingCellPolicy(MissingCellPolicy.RETURN_NULL_AND_BLANK);
//        xceliteOptions.setMissingRowPolicy();
//        XceliteOptions.first
        XceliteSheet sheet = xcelite.createSheet(sheetName);

        SheetWriter<Player> writer = sheet.getBeanWriter(Player.class);

        writer.write(players);
        xcelite.write(new File("results.xlsx"));

        System.out.println("done");
    }


    public String getInputFile() {
        return inputFile;
    }

    public void setInputFile(String inputFile) {
        this.inputFile = inputFile;
    }
}
