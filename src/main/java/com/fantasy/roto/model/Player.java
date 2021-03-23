package com.fantasy.roto.model;

import com.ebay.xcelite.annotations.Column;
import com.ebay.xcelite.annotations.Row;

@Row(colsOrder = {"name", "total", "hitting", "pitching"})
public class Player {
    @Column(name="name")
    public String name;
    @Column
    public double total;
    @Column
    public double hitting;
    @Column
    public double pitching;

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
