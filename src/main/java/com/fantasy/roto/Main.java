package com.fantasy.roto;

import com.fantasy.roto.controller.Controller;

public class Main {
    public static void main(String[] args) {
        Controller controller = new Controller();
        int week = 13;
        controller.run(week);
    }
}
