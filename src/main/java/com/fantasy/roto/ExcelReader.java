package com.fantasy.roto;

import com.ebay.xcelite.Xcelite;
import com.ebay.xcelite.reader.SheetReader;
import com.ebay.xcelite.sheet.XceliteSheet;

import java.io.File;
import java.util.Collection;

public class ExcelReader {
    public static void main(String[] args){
        File file = new File("test2.xlsx");
        System.out.println(file.getAbsolutePath());
        System.out.println(file.getParent() + " asdf " + file.getPath());
        Xcelite xcelite = new Xcelite(file);

        XceliteSheet sheet = xcelite.getSheet("Sheet1");

        SheetReader<Collection<Roto>> simpleReader = sheet.getSimpleReader();
        Collection<Collection<Roto>> playerCollections = simpleReader.read();
//        System.out.println(playerCollections.toString());
        for (Collection<Roto> player : playerCollections){
            System.out.println(player.toString());
            for (Roto stat : player){
                System.out.println(stat.ft);
            }
        }


    }
}
