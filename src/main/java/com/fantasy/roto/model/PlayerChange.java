package com.fantasy.roto.model;


public class PlayerChange {
     private String name;
     private double total;
     private double hitting;
     private double pitching;
     private double totalChange;
     private double hittingChange;
     private double pitchingChange;

     public PlayerChange(Player player){
          this.name = player.name;
          this.total = player.total;
          this.hitting = player.hitting;
          this.pitching = player.pitching;
     }
     public PlayerChange(){}

     public String getName() {
          return name;
     }

     public void setName(String name) {
          this.name = name;
     }

     public double getTotal() {
          return total;
     }

     public void setTotal(double total) {
          this.total = total;
     }

     public double getHitting() {
          return hitting;
     }

     public void setHitting(double hitting) {
          this.hitting = hitting;
     }

     public double getPitching() {
          return pitching;
     }

     public void setPitching(double pitching) {
          this.pitching = pitching;
     }

     public double getTotalChange() {
          return totalChange;
     }

     public void setTotalChange(double totalChange) {
          this.totalChange = totalChange;
     }

     public double getHittingChange() {
          return hittingChange;
     }

     public void setHittingChange(double hittingChange) {
          this.hittingChange = hittingChange;
     }

     public double getPitchingChange() {
          return pitchingChange;
     }

     public void setPitchingChange(double pitchingChange) {
          this.pitchingChange = pitchingChange;
     }
}
