package com.fantasy.roto.model;

import com.ebay.xcelite.annotations.Column;

public class Hitting {
    @Column(name="Name")
    private String name;
    @Column(name="Runs")
    private int run;
    @Column(name="HR")
    private int homeRun;
    @Column(name="RBI")
    private int rbi;
    @Column(name="SB")
    private int sb;
    @Column(name="AVG")
    private double avg;
    @Column(name="OPS")
    private double ops;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRun() {
        return run;
    }

    public void setRun(int run) {
        this.run = run;
    }

    public int getHomeRun() {
        return homeRun;
    }

    public void setHomeRun(int homeRun) {
        this.homeRun = homeRun;
    }

    public int getRbi() {
        return rbi;
    }

    public void setRbi(int rbi) {
        this.rbi = rbi;
    }

    public int getSb() {
        return sb;
    }

    public void setSb(int sb) {
        this.sb = sb;
    }

    public double getAvg() {
        return avg;
    }

    public void setAvg(double avg) {
        this.avg = avg;
    }

    public double getOps() {
        return ops;
    }

    public void setOps(double ops) {
        this.ops = ops;
    }
}
