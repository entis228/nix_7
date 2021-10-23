package com.entis;

import com.entis.controller.ConsoleMenu;
import com.entis.controller.ThreadTasksController;

import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        try {
            ThreadTasksController controller=new ThreadTasksController();
            ConsoleMenu menu=new ConsoleMenu(controller,"getReverseOutputThreadsNames", "countSimpleNumbersInList");
            menu.loop();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
