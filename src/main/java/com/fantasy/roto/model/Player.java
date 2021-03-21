package com.fantasy.roto.model;

import com.ebay.xcelite.annotations.Column;

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
}
