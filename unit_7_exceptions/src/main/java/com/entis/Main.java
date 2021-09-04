package com.entis;

import com.entis.controller.ConsoleMenu;
import com.entis.controller.TimeController;

import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        try {
            ConsoleMenu menu = new ConsoleMenu(new TimeController(), "setFormat", "differenceInDates", "addTimeToDate", "subtract", "sortTimesFromHighToLow",
                    "sortTimesFromLowToHigh");
            menu.loop();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
