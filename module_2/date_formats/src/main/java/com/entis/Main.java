package com.entis;

import com.entis.controller.ConsoleMenu;
import com.entis.controller.DateController;

import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        try {
            DateController controller=new DateController();
            ConsoleMenu menu=new ConsoleMenu(controller,"dateFormatConvert");
            menu.setOptArrayName(0,"Date format convert");
            menu.loop();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
