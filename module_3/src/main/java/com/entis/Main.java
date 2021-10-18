package com.entis;

import com.entis.controller.BusinessController;
import com.entis.controller.ConsoleMenu;

import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        args=new String[5];
        args[0]="postgres";args[1]="123456";args[2]="1";
        try {
            BusinessController controller=new BusinessController(args);
            ConsoleMenu menu=new ConsoleMenu(controller,"addOperation","saveOperationsDuringPeriodToCSV");
            menu.loop();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
