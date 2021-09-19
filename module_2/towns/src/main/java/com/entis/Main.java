package com.entis;

import com.entis.controller.ConsoleMenu;
import com.entis.controller.TownsController;

import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        try {
            TownsController controller=new TownsController();
            ConsoleMenu menu=new ConsoleMenu(controller,"minimalDistance");
            menu.loop();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
