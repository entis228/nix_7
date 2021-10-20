package com.entis;

import com.entis.controller.BusinessController;
import com.entis.controller.ConsoleMenu;

import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        try {
            BusinessController controller = new BusinessController(args);
            ConsoleMenu menu = new ConsoleMenu(controller, "addOperation", "saveOperationsDuringPeriodToCSV", "Exit");
            menu.loop();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
