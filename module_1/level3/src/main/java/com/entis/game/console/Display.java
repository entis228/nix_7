package com.entis.game.console;

import com.entis.game.core.DisplayDriver;

public class Display {

    private Display() {
    }

    public static DisplayDriver getDriver() {
        DisplayDriver driver;
        driver = new ConsoleDriver();
        return driver;
    }
}