package com.entis;

import com.entis.controller.ConsoleMenu;
import com.entis.controller.NamesController;

import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        try {
            NamesController controller=new NamesController();
            ConsoleMenu menu=new ConsoleMenu(controller,"addName","addManyNames","findFirstDistinctName");
            menu.loop();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
