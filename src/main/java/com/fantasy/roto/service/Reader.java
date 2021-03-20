package com.fantasy.roto.service;

import com.ebay.xcelite.Xcelite;
import com.ebay.xcelite.reader.SheetReader;
import com.ebay.xcelite.sheet.XceliteSheet;
import com.fantasy.roto.model.Hitting;

import java.io.File;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class Reader {

    public Collection<Collection<Hitting>> read(String filename) {
        Xcelite xcelite = new Xcelite(new File(filename));

        XceliteSheet sheet = xcelite.getSheet("Sheet1");

        SheetReader<Collection<Hitting>> simpleReader = sheet.getSimpleReader();
//        if (simpleReader.hasHeaderRow()){
//            System.out.println("skipping");
//            simpleReader.skipHeaderRow(true);
//        }
        return simpleReader.read();
    }

}
