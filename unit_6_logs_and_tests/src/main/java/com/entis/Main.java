package com.entis;

import com.entis.controller.ConsoleMenu;
import com.entis.controller.MenuOrganization;

import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        try {
            MenuOrganization menuOrganization = new MenuOrganization();
            ConsoleMenu mainMenu = new ConsoleMenu(menuOrganization, "students", "courses");
            mainMenu.loop();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
