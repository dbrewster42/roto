package com.fantasy.roto.model;

import com.ebay.xcelite.annotations.Column;
import com.ebay.xcelite.annotations.Row;

@Row(colsOrder = {"name", "total", "hitting", "pitching", "totalChange", "hittingChange", "pitchingChange"})
public class Player {
    @Column
    public String name;
    @Column
    public double total;
    @Column
    public double hitting;
    @Column
    public double pitching;
    @Column
    public double totalChange;
    @Column
    public double hittingChange;
    @Column
    public double pitchingChange;
    @Column
    public double rank;

    public Player(){}

    public Player(String name, double hitting) {
        this.name = name;
        this.hitting = hitting;
    }
    public Player(String name, double hitting, double pitching) {
        this.name = name;
        this.hitting = hitting;
        this.pitching = pitching;
    }
}
