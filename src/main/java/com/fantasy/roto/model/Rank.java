package com.fantasy.roto.model;

import com.ebay.xcelite.annotations.Column;
import com.ebay.xcelite.annotations.Row;

@Row(colsOrder = { "rank", "name", "hitting", "space", "rank", "name_", "pitching" })
public class Rank {
    @Column
    public double rank;
    @Column
    public String name;
    @Column
    public double hitting;
    @Column
    public String name_;
    @Column
    public double pitching;
    @Column
    public final String space = null;

    public Rank(double rank, Player hitter, Player pitcher) {
        this.rank = rank;
        this.name = hitter.name;
        this.hitting = hitter.hitting;
        this.name_ = pitcher.name;
        this.pitching = pitcher.pitching;
    }
}
